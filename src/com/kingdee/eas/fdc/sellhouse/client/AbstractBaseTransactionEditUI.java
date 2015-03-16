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
public abstract class AbstractBaseTransactionEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBaseTransactionEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModifyDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModifier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabInformation;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabBiz;
    protected com.kingdee.bos.ctrl.swing.KDContainer conPayList;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkModifyDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtModifier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCommonInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAttachPropertyInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAdditionInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelFitInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelReceiveInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBizReview;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachPropertyTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentAmount1;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer2;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer4;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhoneNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSellType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAttachPropertyTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentAmount1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachProperty;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAPLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveAPLine;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtTackDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsFitmentToContract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7FitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblReceiveBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBizReview;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBiz;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conRecommendCard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizAdscriptionDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValuationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommendPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseAgio;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommendCard;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Seller;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizAdscriptionDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboValuationType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AgioScheme;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgio;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RecommendPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPayline;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertPayLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemovePayLine;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayList;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmitAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMemberApply;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPointPresent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMemberApply;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPointPresent;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBill;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionCustomerSelect actionCustomerSelect = null;
    protected ActionChooseAgio actionChooseAgio = null;
    protected ActionReceiveBill actionReceiveBill = null;
    protected ActionAddCustomer actionAddCustomer = null;
    protected ActionMemberApply actionMemberApply = null;
    protected ActionPointPresent actionPointPresent = null;
    protected ActionSubmitAudit actionSubmitAudit = null;
    /**
     * output class constructor
     */
    public AbstractBaseTransactionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBaseTransactionEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCustomerSelect
        this.actionCustomerSelect = new ActionCustomerSelect(this);
        getActionManager().registerAction("actionCustomerSelect", actionCustomerSelect);
         this.actionCustomerSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChooseAgio
        this.actionChooseAgio = new ActionChooseAgio(this);
        getActionManager().registerAction("actionChooseAgio", actionChooseAgio);
         this.actionChooseAgio.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBill
        this.actionReceiveBill = new ActionReceiveBill(this);
        getActionManager().registerAction("actionReceiveBill", actionReceiveBill);
         this.actionReceiveBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddCustomer
        this.actionAddCustomer = new ActionAddCustomer(this);
        getActionManager().registerAction("actionAddCustomer", actionAddCustomer);
         this.actionAddCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMemberApply
        this.actionMemberApply = new ActionMemberApply(this);
        getActionManager().registerAction("actionMemberApply", actionMemberApply);
         this.actionMemberApply.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPointPresent
        this.actionPointPresent = new ActionPointPresent(this);
        getActionManager().registerAction("actionPointPresent", actionPointPresent);
         this.actionPointPresent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmitAudit
        this.actionSubmitAudit = new ActionSubmitAudit(this);
        getActionManager().registerAction("actionSubmitAudit", actionSubmitAudit);
         this.actionSubmitAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModifyDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModifier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tabInformation = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tabBiz = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.conPayList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkModifyDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtModifier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.panelCommonInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelAttachPropertyInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelAdditionInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelFitInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelReceiveInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelBizReview = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.labelCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contPhoneNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachPropertyTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentAmount1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelCustomer1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labelCustomer2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labelCustomer3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labelCustomer4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labelCustomer5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtPhoneNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboSellType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRoomModel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttachPropertyTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentAmount1 = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblAttachProperty = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddAPLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveAPLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtTackDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contFitmentStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsFitmentToContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.f7FitmentStandard = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFitmentPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblReceiveBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblBizReview = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelBiz = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conRecommendCard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizAdscriptionDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contValuationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommendPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnChooseAgio = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtRecommendCard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Seller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizAdscriptionDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboValuationType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7PayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7AgioScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAgioDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7RecommendPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAFundAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddPayline = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsertPayLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemovePayLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtAFundAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLoanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnSubmitAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMemberApply = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPointPresent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceiveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAddCustomer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemMemberApply = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPointPresent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreateTime.setName("contCreateTime");
        this.contModifyDate.setName("contModifyDate");
        this.contAuditor.setName("contAuditor");
        this.contAuditDate.setName("contAuditDate");
        this.contModifier.setName("contModifier");
        this.contCreator.setName("contCreator");
        this.tabInformation.setName("tabInformation");
        this.tabBiz.setName("tabBiz");
        this.conPayList.setName("conPayList");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkModifyDate.setName("pkModifyDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditDate.setName("pkAuditDate");
        this.prmtModifier.setName("prmtModifier");
        this.prmtCreator.setName("prmtCreator");
        this.panelCommonInfo.setName("panelCommonInfo");
        this.panelAttachPropertyInfo.setName("panelAttachPropertyInfo");
        this.panelAdditionInfo.setName("panelAdditionInfo");
        this.panelFitInfo.setName("panelFitInfo");
        this.panelReceiveInfo.setName("panelReceiveInfo");
        this.panelBizReview.setName("panelBizReview");
        this.labelCustomer.setName("labelCustomer");
        this.btnSelectCustomer.setName("btnSelectCustomer");
        this.contPhoneNumber.setName("contPhoneNumber");
        this.contRoom.setName("contRoom");
        this.contSellType.setName("contSellType");
        this.contRoomModel.setName("contRoomModel");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contStandardTotalAmount.setName("contStandardTotalAmount");
        this.contBuildingPrice.setName("contBuildingPrice");
        this.contRoomPrice.setName("contRoomPrice");
        this.contAttachPropertyTotalAmount.setName("contAttachPropertyTotalAmount");
        this.contFitmentAmount1.setName("contFitmentAmount1");
        this.labelCustomer1.setName("labelCustomer1");
        this.labelCustomer2.setName("labelCustomer2");
        this.labelCustomer3.setName("labelCustomer3");
        this.kDSeparator8.setName("kDSeparator8");
        this.labelCustomer4.setName("labelCustomer4");
        this.kDSeparator9.setName("kDSeparator9");
        this.labelCustomer5.setName("labelCustomer5");
        this.kDSeparator10.setName("kDSeparator10");
        this.txtPhoneNumber.setName("txtPhoneNumber");
        this.f7Room.setName("f7Room");
        this.comboSellType.setName("comboSellType");
        this.txtRoomModel.setName("txtRoomModel");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.txtBuildingPrice.setName("txtBuildingPrice");
        this.txtRoomPrice.setName("txtRoomPrice");
        this.txtAttachPropertyTotalAmount.setName("txtAttachPropertyTotalAmount");
        this.txtFitmentAmount1.setName("txtFitmentAmount1");
        this.tblAttachProperty.setName("tblAttachProperty");
        this.btnAddAPLine.setName("btnAddAPLine");
        this.btnRemoveAPLine.setName("btnRemoveAPLine");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtTackDesc.setName("txtTackDesc");
        this.contFitmentStandard.setName("contFitmentStandard");
        this.contFitmentPrice.setName("contFitmentPrice");
        this.contFitmentAmount.setName("contFitmentAmount");
        this.chkIsFitmentToContract.setName("chkIsFitmentToContract");
        this.f7FitmentStandard.setName("f7FitmentStandard");
        this.txtFitmentPrice.setName("txtFitmentPrice");
        this.txtFitmentAmount.setName("txtFitmentAmount");
        this.tblReceiveBill.setName("tblReceiveBill");
        this.tblBizReview.setName("tblBizReview");
        this.panelBiz.setName("panelBiz");
        this.conRecommendCard.setName("conRecommendCard");
        this.contSeller.setName("contSeller");
        this.contBizAdscriptionDate.setName("contBizAdscriptionDate");
        this.contNumber.setName("contNumber");
        this.contContractNumber.setName("contContractNumber");
        this.contValuationType.setName("contValuationType");
        this.contBizDate.setName("contBizDate");
        this.contPayType.setName("contPayType");
        this.contAgioScheme.setName("contAgioScheme");
        this.contAgioDes.setName("contAgioDes");
        this.contDealTotalAmount.setName("contDealTotalAmount");
        this.contDealBuildPrice.setName("contDealBuildPrice");
        this.contDealRoomPrice.setName("contDealRoomPrice");
        this.contContractTotalAmount.setName("contContractTotalAmount");
        this.contContractBuildPrice.setName("contContractBuildPrice");
        this.contContractRoomPrice.setName("contContractRoomPrice");
        this.contAgio.setName("contAgio");
        this.contRecommendPerson.setName("contRecommendPerson");
        this.btnChooseAgio.setName("btnChooseAgio");
        this.txtRecommendCard.setName("txtRecommendCard");
        this.f7Seller.setName("f7Seller");
        this.pkBizAdscriptionDate.setName("pkBizAdscriptionDate");
        this.txtNumber.setName("txtNumber");
        this.txtContractNumber.setName("txtContractNumber");
        this.comboValuationType.setName("comboValuationType");
        this.pkBizDate.setName("pkBizDate");
        this.f7PayType.setName("f7PayType");
        this.f7AgioScheme.setName("f7AgioScheme");
        this.txtAgioDes.setName("txtAgioDes");
        this.txtDealTotalAmount.setName("txtDealTotalAmount");
        this.txtDealBuildPrice.setName("txtDealBuildPrice");
        this.txtDealRoomPrice.setName("txtDealRoomPrice");
        this.txtContractTotalAmount.setName("txtContractTotalAmount");
        this.txtContractBuildPrice.setName("txtContractBuildPrice");
        this.txtContractRoomPrice.setName("txtContractRoomPrice");
        this.txtAgio.setName("txtAgio");
        this.f7RecommendPerson.setName("f7RecommendPerson");
        this.contAFundAmount.setName("contAFundAmount");
        this.contLoanAmount.setName("contLoanAmount");
        this.btnAddPayline.setName("btnAddPayline");
        this.btnInsertPayLine.setName("btnInsertPayLine");
        this.btnRemovePayLine.setName("btnRemovePayLine");
        this.tblPayList.setName("tblPayList");
        this.txtAFundAmount.setName("txtAFundAmount");
        this.txtLoanAmount.setName("txtLoanAmount");
        this.btnSubmitAudit.setName("btnSubmitAudit");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.btnMemberApply.setName("btnMemberApply");
        this.btnPointPresent.setName("btnPointPresent");
        this.btnReceiveBill.setName("btnReceiveBill");
        this.menuItemAddCustomer.setName("menuItemAddCustomer");
        this.menuItemMemberApply.setName("menuItemMemberApply");
        this.menuItemPointPresent.setName("menuItemPointPresent");
        this.menuItemReceiveBill.setName("menuItemReceiveBill");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,700));
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(70);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contModifyDate		
        this.contModifyDate.setBoundLabelText(resHelper.getString("contModifyDate.boundLabelText"));		
        this.contModifyDate.setBoundLabelLength(70);		
        this.contModifyDate.setBoundLabelUnderline(true);		
        this.contModifyDate.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(70);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelLength(70);		
        this.contAuditDate.setBoundLabelUnderline(true);		
        this.contAuditDate.setEnabled(false);
        // contModifier		
        this.contModifier.setBoundLabelText(resHelper.getString("contModifier.boundLabelText"));		
        this.contModifier.setBoundLabelLength(70);		
        this.contModifier.setBoundLabelUnderline(true);		
        this.contModifier.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(70);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // tabInformation
        // tabBiz
        // conPayList		
        this.conPayList.setTitle(resHelper.getString("conPayList.title"));
        // pkCreateTime		
        this.pkCreateTime.setRequired(true);		
        this.pkCreateTime.setEnabled(false);
        // pkModifyDate		
        this.pkModifyDate.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setRequired(true);		
        this.prmtAuditor.setEnabled(false);
        // pkAuditDate		
        this.pkAuditDate.setEnabled(false);
        // prmtModifier		
        this.prmtModifier.setRequired(true);		
        this.prmtModifier.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setRequired(true);		
        this.prmtCreator.setEnabled(false);
        // panelCommonInfo
        // panelAttachPropertyInfo
        // panelAdditionInfo
        // panelFitInfo
        // panelReceiveInfo
        // panelBizReview
        // labelCustomer		
        this.labelCustomer.setBoundLabelText(resHelper.getString("labelCustomer.boundLabelText"));		
        this.labelCustomer.setBoundLabelUnderline(true);		
        this.labelCustomer.setBoundLabelLength(30);
        // btnSelectCustomer
        this.btnSelectCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelectCustomer.setText(resHelper.getString("btnSelectCustomer.text"));
        // contPhoneNumber		
        this.contPhoneNumber.setBoundLabelText(resHelper.getString("contPhoneNumber.boundLabelText"));		
        this.contPhoneNumber.setBoundLabelLength(100);		
        this.contPhoneNumber.setBoundLabelUnderline(true);		
        this.contPhoneNumber.setEnabled(false);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contSellType		
        this.contSellType.setBoundLabelText(resHelper.getString("contSellType.boundLabelText"));		
        this.contSellType.setBoundLabelUnderline(true);		
        this.contSellType.setBoundLabelLength(100);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);		
        this.contRoomModel.setEnabled(false);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);		
        this.contBuildingArea.setEnabled(false);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);		
        this.contRoomArea.setEnabled(false);
        // contStandardTotalAmount		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contStandardTotalAmount.setBoundLabelLength(100);		
        this.contStandardTotalAmount.setBoundLabelUnderline(true);		
        this.contStandardTotalAmount.setEnabled(false);
        // contBuildingPrice		
        this.contBuildingPrice.setBoundLabelText(resHelper.getString("contBuildingPrice.boundLabelText"));		
        this.contBuildingPrice.setBoundLabelLength(100);		
        this.contBuildingPrice.setBoundLabelUnderline(true);		
        this.contBuildingPrice.setEnabled(false);
        // contRoomPrice		
        this.contRoomPrice.setBoundLabelText(resHelper.getString("contRoomPrice.boundLabelText"));		
        this.contRoomPrice.setBoundLabelLength(100);		
        this.contRoomPrice.setBoundLabelUnderline(true);		
        this.contRoomPrice.setEnabled(false);
        // contAttachPropertyTotalAmount		
        this.contAttachPropertyTotalAmount.setBoundLabelText(resHelper.getString("contAttachPropertyTotalAmount.boundLabelText"));		
        this.contAttachPropertyTotalAmount.setBoundLabelLength(100);		
        this.contAttachPropertyTotalAmount.setBoundLabelUnderline(true);		
        this.contAttachPropertyTotalAmount.setEnabled(false);
        // contFitmentAmount1		
        this.contFitmentAmount1.setBoundLabelText(resHelper.getString("contFitmentAmount1.boundLabelText"));		
        this.contFitmentAmount1.setBoundLabelLength(100);		
        this.contFitmentAmount1.setBoundLabelUnderline(true);
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
        // kDSeparator8
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
        // kDSeparator9
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
        // kDSeparator10
        // txtPhoneNumber
        // f7Room		
        this.f7Room.setRequired(true);		
        this.f7Room.setDisplayFormat("$name$");		
        this.f7Room.setEditFormat("$name$");		
        this.f7Room.setCommitFormat("$name$");		
        this.f7Room.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectQuery");
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
        // comboSellType		
        this.comboSellType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellTypeEnum").toArray());		
        this.comboSellType.setRequired(true);
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
        // txtRoomModel
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);
        // txtBuildingPrice
        // txtRoomPrice
        // txtAttachPropertyTotalAmount
        // txtFitmentAmount1		
        this.txtFitmentAmount1.setDataType(1);
        // tblAttachProperty
		String tblAttachPropertyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"agio\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isMergeTocontract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"mergeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{buildingPrice}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{agio}</t:Cell><t:Cell>$Resource{isMergeTocontract}</t:Cell><t:Cell>$Resource{mergeAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAttachProperty.setFormatXml(resHelper.translateString("tblAttachProperty",tblAttachPropertyStrXML));
        this.tblAttachProperty.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAttachProperty_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAttachProperty_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnAddAPLine
        this.btnAddAPLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddAPLine.setText(resHelper.getString("btnAddAPLine.text"));
        // btnRemoveAPLine
        this.btnRemoveAPLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveAPLine.setText(resHelper.getString("btnRemoveAPLine.text"));
        // kDScrollPane1
        // txtTackDesc
        // contFitmentStandard		
        this.contFitmentStandard.setBoundLabelText(resHelper.getString("contFitmentStandard.boundLabelText"));		
        this.contFitmentStandard.setBoundLabelUnderline(true);		
        this.contFitmentStandard.setBoundLabelLength(100);
        // contFitmentPrice		
        this.contFitmentPrice.setBoundLabelText(resHelper.getString("contFitmentPrice.boundLabelText"));		
        this.contFitmentPrice.setBoundLabelLength(100);		
        this.contFitmentPrice.setBoundLabelUnderline(true);		
        this.contFitmentPrice.setEnabled(false);
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
        // f7FitmentStandard		
        this.f7FitmentStandard.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.DecorationStandardQuery");		
        this.f7FitmentStandard.setCommitFormat("$number$");		
        this.f7FitmentStandard.setEditFormat("$number$");		
        this.f7FitmentStandard.setDisplayFormat("$name$");
        // txtFitmentPrice		
        this.txtFitmentPrice.setDataType(1);
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
        // tblReceiveBill
		String tblReceiveBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"billType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{revDate}</t:Cell><t:Cell>$Resource{billType}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{revPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblReceiveBill.setFormatXml(resHelper.translateString("tblReceiveBill",tblReceiveBillStrXML));
        this.tblReceiveBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblReceiveBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblBizReview
		String tblBizReviewStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"bizName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"finishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actualFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bizName}</t:Cell><t:Cell>$Resource{finishDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actualFinishDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBizReview.setFormatXml(resHelper.translateString("tblBizReview",tblBizReviewStrXML));
        this.tblBizReview.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBizReview_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // panelBiz
        // conRecommendCard		
        this.conRecommendCard.setBoundLabelText(resHelper.getString("conRecommendCard.boundLabelText"));		
        this.conRecommendCard.setBoundLabelLength(100);		
        this.conRecommendCard.setBoundLabelUnderline(true);		
        this.conRecommendCard.setEnabled(false);
        // contSeller		
        this.contSeller.setBoundLabelText(resHelper.getString("contSeller.boundLabelText"));		
        this.contSeller.setBoundLabelLength(100);		
        this.contSeller.setBoundLabelUnderline(true);
        // contBizAdscriptionDate		
        this.contBizAdscriptionDate.setBoundLabelText(resHelper.getString("contBizAdscriptionDate.boundLabelText"));		
        this.contBizAdscriptionDate.setBoundLabelLength(100);		
        this.contBizAdscriptionDate.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contContractNumber		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contContractNumber.setBoundLabelLength(100);		
        this.contContractNumber.setBoundLabelUnderline(true);
        // contValuationType		
        this.contValuationType.setBoundLabelText(resHelper.getString("contValuationType.boundLabelText"));		
        this.contValuationType.setBoundLabelLength(100);		
        this.contValuationType.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);
        // contAgioScheme		
        this.contAgioScheme.setBoundLabelText(resHelper.getString("contAgioScheme.boundLabelText"));		
        this.contAgioScheme.setBoundLabelLength(100);		
        this.contAgioScheme.setBoundLabelUnderline(true);
        // contAgioDes		
        this.contAgioDes.setBoundLabelText(resHelper.getString("contAgioDes.boundLabelText"));		
        this.contAgioDes.setBoundLabelLength(100);		
        this.contAgioDes.setBoundLabelUnderline(true);		
        this.contAgioDes.setEnabled(false);
        // contDealTotalAmount		
        this.contDealTotalAmount.setBoundLabelText(resHelper.getString("contDealTotalAmount.boundLabelText"));		
        this.contDealTotalAmount.setBoundLabelLength(100);		
        this.contDealTotalAmount.setBoundLabelUnderline(true);		
        this.contDealTotalAmount.setEnabled(false);
        // contDealBuildPrice		
        this.contDealBuildPrice.setBoundLabelText(resHelper.getString("contDealBuildPrice.boundLabelText"));		
        this.contDealBuildPrice.setBoundLabelLength(100);		
        this.contDealBuildPrice.setBoundLabelUnderline(true);		
        this.contDealBuildPrice.setEnabled(false);
        // contDealRoomPrice		
        this.contDealRoomPrice.setBoundLabelText(resHelper.getString("contDealRoomPrice.boundLabelText"));		
        this.contDealRoomPrice.setBoundLabelLength(100);		
        this.contDealRoomPrice.setBoundLabelUnderline(true);		
        this.contDealRoomPrice.setEnabled(false);
        // contContractTotalAmount		
        this.contContractTotalAmount.setBoundLabelText(resHelper.getString("contContractTotalAmount.boundLabelText"));		
        this.contContractTotalAmount.setBoundLabelLength(100);		
        this.contContractTotalAmount.setBoundLabelUnderline(true);		
        this.contContractTotalAmount.setEnabled(false);
        // contContractBuildPrice		
        this.contContractBuildPrice.setBoundLabelText(resHelper.getString("contContractBuildPrice.boundLabelText"));		
        this.contContractBuildPrice.setBoundLabelLength(100);		
        this.contContractBuildPrice.setBoundLabelUnderline(true);		
        this.contContractBuildPrice.setEnabled(false);
        // contContractRoomPrice		
        this.contContractRoomPrice.setBoundLabelText(resHelper.getString("contContractRoomPrice.boundLabelText"));		
        this.contContractRoomPrice.setBoundLabelLength(100);		
        this.contContractRoomPrice.setBoundLabelUnderline(true);		
        this.contContractRoomPrice.setEnabled(false);
        // contAgio		
        this.contAgio.setBoundLabelText(resHelper.getString("contAgio.boundLabelText"));		
        this.contAgio.setBoundLabelLength(100);		
        this.contAgio.setBoundLabelUnderline(true);		
        this.contAgio.setEnabled(false);
        // contRecommendPerson		
        this.contRecommendPerson.setBoundLabelText(resHelper.getString("contRecommendPerson.boundLabelText"));		
        this.contRecommendPerson.setBoundLabelLength(100);		
        this.contRecommendPerson.setBoundLabelUnderline(true);
        // btnChooseAgio
        this.btnChooseAgio.setAction((IItemAction)ActionProxyFactory.getProxy(actionChooseAgio, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChooseAgio.setText(resHelper.getString("btnChooseAgio.text"));
        // txtRecommendCard
        // f7Seller		
        this.f7Seller.setRequired(true);		
        this.f7Seller.setEditFormat("$user.name$");		
        this.f7Seller.setDisplayFormat("$user.name$");		
        this.f7Seller.setCommitFormat("$user.name$");
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
        // pkBizAdscriptionDate		
        this.pkBizAdscriptionDate.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtContractNumber
        // comboValuationType		
        this.comboValuationType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CalcTypeEnum").toArray());		
        this.comboValuationType.setRequired(true);
        this.comboValuationType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboValuationType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        this.pkBizDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7PayType		
        this.f7PayType.setDisplayFormat("$name$");		
        this.f7PayType.setEditFormat("$number$");		
        this.f7PayType.setCommitFormat("$number$");		
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
        // f7AgioScheme		
        this.f7AgioScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AgioSchemeQuery");		
        this.f7AgioScheme.setEditFormat("$number$");		
        this.f7AgioScheme.setDisplayFormat("$name$");		
        this.f7AgioScheme.setCommitFormat("$number$");
        this.f7AgioScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7AgioScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAgioDes		
        this.txtAgioDes.setEnabled(false);
        // txtDealTotalAmount		
        this.txtDealTotalAmount.setDataType(1);		
        this.txtDealTotalAmount.setRequired(true);
        this.txtDealTotalAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtDealTotalAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        // txtContractRoomPrice		
        this.txtContractRoomPrice.setDataType(1);
        // txtAgio		
        this.txtAgio.setDataType(1);
        // f7RecommendPerson		
        this.f7RecommendPerson.setQueryInfo("com.kingdee.eas.fdc.insider.app.InsiderQuery");		
        this.f7RecommendPerson.setCommitFormat("$customerName$");		
        this.f7RecommendPerson.setEditFormat("$customerName$");		
        this.f7RecommendPerson.setDisplayFormat("$customerName$");
        this.f7RecommendPerson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RecommendPerson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contAFundAmount		
        this.contAFundAmount.setBoundLabelText(resHelper.getString("contAFundAmount.boundLabelText"));		
        this.contAFundAmount.setBoundLabelLength(100);		
        this.contAFundAmount.setBoundLabelUnderline(true);		
        this.contAFundAmount.setEnabled(false);
        // contLoanAmount		
        this.contLoanAmount.setBoundLabelText(resHelper.getString("contLoanAmount.boundLabelText"));		
        this.contLoanAmount.setBoundLabelLength(100);		
        this.contLoanAmount.setBoundLabelUnderline(true);		
        this.contLoanAmount.setEnabled(false);
        // btnAddPayline
        this.btnAddPayline.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddPayline.setText(resHelper.getString("btnAddPayline.text"));
        // btnInsertPayLine
        this.btnInsertPayLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsertPayLine.setText(resHelper.getString("btnInsertPayLine.text"));
        // btnRemovePayLine
        this.btnRemovePayLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemovePayLine.setText(resHelper.getString("btnRemovePayLine.text"));
        // tblPayList
		String tblPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"实收日期\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPayList.setFormatXml(resHelper.translateString("tblPayList",tblPayListStrXML));
        this.tblPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // txtAFundAmount		
        this.txtAFundAmount.setDataType(1);		
        this.txtAFundAmount.setEnabled(false);
        // txtLoanAmount		
        this.txtLoanAmount.setDataType(1);		
        this.txtLoanAmount.setEnabled(false);
        // btnSubmitAudit
        this.btnSubmitAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmitAudit.setText(resHelper.getString("btnSubmitAudit.text"));		
        this.btnSubmitAudit.setToolTipText(resHelper.getString("btnSubmitAudit.toolTipText"));
        // btnAddCustomer
        this.btnAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));
        // btnMemberApply
        this.btnMemberApply.setAction((IItemAction)ActionProxyFactory.getProxy(actionMemberApply, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMemberApply.setText(resHelper.getString("btnMemberApply.text"));
        // btnPointPresent
        this.btnPointPresent.setAction((IItemAction)ActionProxyFactory.getProxy(actionPointPresent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPointPresent.setText(resHelper.getString("btnPointPresent.text"));
        // btnReceiveBill
        this.btnReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBill.setText(resHelper.getString("btnReceiveBill.text"));
        // menuItemAddCustomer
        this.menuItemAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddCustomer.setText(resHelper.getString("menuItemAddCustomer.text"));
        // menuItemMemberApply
        this.menuItemMemberApply.setAction((IItemAction)ActionProxyFactory.getProxy(actionMemberApply, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMemberApply.setText(resHelper.getString("menuItemMemberApply.text"));
        // menuItemPointPresent
        this.menuItemPointPresent.setAction((IItemAction)ActionProxyFactory.getProxy(actionPointPresent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPointPresent.setText(resHelper.getString("menuItemPointPresent.text"));
        // menuItemReceiveBill
        this.menuItemReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBill.setText(resHelper.getString("menuItemReceiveBill.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 700));
        contCreateTime.setBounds(new Rectangle(14, 664, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(14, 664, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contModifyDate.setBounds(new Rectangle(728, 664, 270, 19));
        this.add(contModifyDate, new KDLayout.Constraints(728, 664, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(371, 639, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(371, 639, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditDate.setBounds(new Rectangle(371, 664, 270, 19));
        this.add(contAuditDate, new KDLayout.Constraints(371, 664, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contModifier.setBounds(new Rectangle(728, 639, 270, 19));
        this.add(contModifier, new KDLayout.Constraints(728, 639, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(14, 639, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(14, 639, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tabInformation.setBounds(new Rectangle(14, 10, 989, 173));
        this.add(tabInformation, new KDLayout.Constraints(14, 10, 989, 173, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabBiz.setBounds(new Rectangle(14, 195, 985, 257));
        this.add(tabBiz, new KDLayout.Constraints(14, 195, 985, 257, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conPayList.setBounds(new Rectangle(14, 482, 984, 148));
        this.add(conPayList, new KDLayout.Constraints(14, 482, 984, 148, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contModifyDate
        contModifyDate.setBoundEditor(pkModifyDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditDate
        contAuditDate.setBoundEditor(pkAuditDate);
        //contModifier
        contModifier.setBoundEditor(prmtModifier);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //tabInformation
        tabInformation.add(panelCommonInfo, resHelper.getString("panelCommonInfo.constraints"));
        tabInformation.add(panelAttachPropertyInfo, resHelper.getString("panelAttachPropertyInfo.constraints"));
        tabInformation.add(panelAdditionInfo, resHelper.getString("panelAdditionInfo.constraints"));
        tabInformation.add(panelFitInfo, resHelper.getString("panelFitInfo.constraints"));
        tabInformation.add(panelReceiveInfo, resHelper.getString("panelReceiveInfo.constraints"));
        tabInformation.add(panelBizReview, resHelper.getString("panelBizReview.constraints"));
        //panelCommonInfo
        panelCommonInfo.setLayout(new KDLayout());
        panelCommonInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 140));        kDSeparator6.setBounds(new Rectangle(44, 30, 56, 10));
        panelCommonInfo.add(kDSeparator6, new KDLayout.Constraints(44, 30, 56, 10, 0));
        kDSeparator7.setBounds(new Rectangle(101, 30, 56, 10));
        panelCommonInfo.add(kDSeparator7, new KDLayout.Constraints(101, 30, 56, 10, 0));
        labelCustomer.setBounds(new Rectangle(12, 12, 30, 19));
        panelCommonInfo.add(labelCustomer, new KDLayout.Constraints(12, 12, 30, 19, 0));
        btnSelectCustomer.setBounds(new Rectangle(338, 10, 85, 19));
        panelCommonInfo.add(btnSelectCustomer, new KDLayout.Constraints(338, 10, 85, 19, 0));
        contPhoneNumber.setBounds(new Rectangle(450, 10, 515, 19));
        panelCommonInfo.add(contPhoneNumber, new KDLayout.Constraints(450, 10, 515, 19, 0));
        contRoom.setBounds(new Rectangle(12, 34, 270, 19));
        panelCommonInfo.add(contRoom, new KDLayout.Constraints(12, 34, 270, 19, 0));
        contSellType.setBounds(new Rectangle(12, 60, 270, 19));
        panelCommonInfo.add(contSellType, new KDLayout.Constraints(12, 60, 270, 19, 0));
        contRoomModel.setBounds(new Rectangle(357, 60, 270, 19));
        panelCommonInfo.add(contRoomModel, new KDLayout.Constraints(357, 60, 270, 19, 0));
        contBuildingArea.setBounds(new Rectangle(12, 86, 270, 19));
        panelCommonInfo.add(contBuildingArea, new KDLayout.Constraints(12, 86, 270, 19, 0));
        contRoomArea.setBounds(new Rectangle(357, 86, 270, 19));
        panelCommonInfo.add(contRoomArea, new KDLayout.Constraints(357, 86, 270, 19, 0));
        contStandardTotalAmount.setBounds(new Rectangle(697, 60, 270, 19));
        panelCommonInfo.add(contStandardTotalAmount, new KDLayout.Constraints(697, 60, 270, 19, 0));
        contBuildingPrice.setBounds(new Rectangle(12, 114, 270, 19));
        panelCommonInfo.add(contBuildingPrice, new KDLayout.Constraints(12, 114, 270, 19, 0));
        contRoomPrice.setBounds(new Rectangle(357, 114, 270, 19));
        panelCommonInfo.add(contRoomPrice, new KDLayout.Constraints(357, 114, 270, 19, 0));
        contAttachPropertyTotalAmount.setBounds(new Rectangle(697, 86, 270, 19));
        panelCommonInfo.add(contAttachPropertyTotalAmount, new KDLayout.Constraints(697, 86, 270, 19, 0));
        contFitmentAmount1.setBounds(new Rectangle(698, 114, 270, 19));
        panelCommonInfo.add(contFitmentAmount1, new KDLayout.Constraints(698, 114, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        labelCustomer1.setBounds(new Rectangle(44, 10, 56, 19));
        panelCommonInfo.add(labelCustomer1, new KDLayout.Constraints(44, 10, 56, 19, 0));
        labelCustomer2.setBounds(new Rectangle(101, 10, 56, 19));
        panelCommonInfo.add(labelCustomer2, new KDLayout.Constraints(101, 10, 56, 19, 0));
        labelCustomer3.setBounds(new Rectangle(158, 10, 56, 19));
        panelCommonInfo.add(labelCustomer3, new KDLayout.Constraints(158, 10, 56, 19, 0));
        kDSeparator8.setBounds(new Rectangle(158, 30, 56, 10));
        panelCommonInfo.add(kDSeparator8, new KDLayout.Constraints(158, 30, 56, 10, 0));
        labelCustomer4.setBounds(new Rectangle(215, 10, 56, 19));
        panelCommonInfo.add(labelCustomer4, new KDLayout.Constraints(215, 10, 56, 19, 0));
        kDSeparator9.setBounds(new Rectangle(215, 30, 56, 10));
        panelCommonInfo.add(kDSeparator9, new KDLayout.Constraints(215, 30, 56, 10, 0));
        labelCustomer5.setBounds(new Rectangle(273, 10, 56, 19));
        panelCommonInfo.add(labelCustomer5, new KDLayout.Constraints(273, 10, 56, 19, 0));
        kDSeparator10.setBounds(new Rectangle(273, 30, 56, 10));
        panelCommonInfo.add(kDSeparator10, new KDLayout.Constraints(273, 30, 56, 10, 0));
        //contPhoneNumber
        contPhoneNumber.setBoundEditor(txtPhoneNumber);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contSellType
        contSellType.setBoundEditor(comboSellType);
        //contRoomModel
        contRoomModel.setBoundEditor(txtRoomModel);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //contBuildingPrice
        contBuildingPrice.setBoundEditor(txtBuildingPrice);
        //contRoomPrice
        contRoomPrice.setBoundEditor(txtRoomPrice);
        //contAttachPropertyTotalAmount
        contAttachPropertyTotalAmount.setBoundEditor(txtAttachPropertyTotalAmount);
        //contFitmentAmount1
        contFitmentAmount1.setBoundEditor(txtFitmentAmount1);
        //panelAttachPropertyInfo
        panelAttachPropertyInfo.setLayout(new KDLayout());
        panelAttachPropertyInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 140));        tblAttachProperty.setBounds(new Rectangle(4, 35, 975, 102));
        panelAttachPropertyInfo.add(tblAttachProperty, new KDLayout.Constraints(4, 35, 975, 102, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddAPLine.setBounds(new Rectangle(773, 9, 73, 19));
        panelAttachPropertyInfo.add(btnAddAPLine, new KDLayout.Constraints(773, 9, 73, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveAPLine.setBounds(new Rectangle(868, 9, 75, 19));
        panelAttachPropertyInfo.add(btnRemoveAPLine, new KDLayout.Constraints(868, 9, 75, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //panelAdditionInfo
panelAdditionInfo.setLayout(new BorderLayout(0, 0));        panelAdditionInfo.add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtTackDesc, null);
        //panelFitInfo
        panelFitInfo.setLayout(new KDLayout());
        panelFitInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 140));        contFitmentStandard.setBounds(new Rectangle(12, 16, 270, 19));
        panelFitInfo.add(contFitmentStandard, new KDLayout.Constraints(12, 16, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentPrice.setBounds(new Rectangle(354, 16, 270, 19));
        panelFitInfo.add(contFitmentPrice, new KDLayout.Constraints(354, 16, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentAmount.setBounds(new Rectangle(696, 16, 270, 19));
        panelFitInfo.add(contFitmentAmount, new KDLayout.Constraints(696, 16, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsFitmentToContract.setBounds(new Rectangle(12, 54, 169, 19));
        panelFitInfo.add(chkIsFitmentToContract, new KDLayout.Constraints(12, 54, 169, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contFitmentStandard
        contFitmentStandard.setBoundEditor(f7FitmentStandard);
        //contFitmentPrice
        contFitmentPrice.setBoundEditor(txtFitmentPrice);
        //contFitmentAmount
        contFitmentAmount.setBoundEditor(txtFitmentAmount);
        //panelReceiveInfo
panelReceiveInfo.setLayout(new BorderLayout(0, 0));        panelReceiveInfo.add(tblReceiveBill, BorderLayout.CENTER);
        //panelBizReview
panelBizReview.setLayout(new BorderLayout(0, 0));        panelBizReview.add(tblBizReview, BorderLayout.CENTER);
        //tabBiz
        tabBiz.add(panelBiz, resHelper.getString("panelBiz.constraints"));
        //panelBiz
        panelBiz.setLayout(new KDLayout());
        panelBiz.putClientProperty("OriginalBounds", new Rectangle(0, 0, 984, 224));        conRecommendCard.setBounds(new Rectangle(353, 177, 270, 19));
        panelBiz.add(conRecommendCard, new KDLayout.Constraints(353, 177, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSeller.setBounds(new Rectangle(353, 145, 270, 19));
        panelBiz.add(contSeller, new KDLayout.Constraints(353, 145, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizAdscriptionDate.setBounds(new Rectangle(698, 39, 270, 19));
        panelBiz.add(contBizAdscriptionDate, new KDLayout.Constraints(698, 39, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(9, 10, 270, 19));
        panelBiz.add(contNumber, new KDLayout.Constraints(9, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractNumber.setBounds(new Rectangle(353, 10, 270, 19));
        panelBiz.add(contContractNumber, new KDLayout.Constraints(353, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contValuationType.setBounds(new Rectangle(698, 9, 270, 19));
        panelBiz.add(contValuationType, new KDLayout.Constraints(698, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(353, 37, 270, 19));
        panelBiz.add(contBizDate, new KDLayout.Constraints(353, 37, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayType.setBounds(new Rectangle(9, 37, 270, 19));
        panelBiz.add(contPayType, new KDLayout.Constraints(9, 37, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgioScheme.setBounds(new Rectangle(9, 64, 270, 19));
        panelBiz.add(contAgioScheme, new KDLayout.Constraints(9, 64, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgioDes.setBounds(new Rectangle(353, 64, 507, 19));
        panelBiz.add(contAgioDes, new KDLayout.Constraints(353, 64, 507, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealTotalAmount.setBounds(new Rectangle(9, 91, 270, 19));
        panelBiz.add(contDealTotalAmount, new KDLayout.Constraints(9, 91, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealBuildPrice.setBounds(new Rectangle(353, 91, 270, 19));
        panelBiz.add(contDealBuildPrice, new KDLayout.Constraints(353, 91, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealRoomPrice.setBounds(new Rectangle(698, 92, 270, 19));
        panelBiz.add(contDealRoomPrice, new KDLayout.Constraints(698, 92, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractTotalAmount.setBounds(new Rectangle(9, 118, 270, 19));
        panelBiz.add(contContractTotalAmount, new KDLayout.Constraints(9, 118, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractBuildPrice.setBounds(new Rectangle(353, 118, 270, 19));
        panelBiz.add(contContractBuildPrice, new KDLayout.Constraints(353, 118, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractRoomPrice.setBounds(new Rectangle(698, 117, 270, 19));
        panelBiz.add(contContractRoomPrice, new KDLayout.Constraints(698, 117, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAgio.setBounds(new Rectangle(9, 145, 270, 19));
        panelBiz.add(contAgio, new KDLayout.Constraints(9, 145, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommendPerson.setBounds(new Rectangle(9, 177, 270, 19));
        panelBiz.add(contRecommendPerson, new KDLayout.Constraints(9, 177, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnChooseAgio.setBounds(new Rectangle(871, 64, 97, 19));
        panelBiz.add(btnChooseAgio, new KDLayout.Constraints(871, 64, 97, 19, 0));
        //conRecommendCard
        conRecommendCard.setBoundEditor(txtRecommendCard);
        //contSeller
        contSeller.setBoundEditor(f7Seller);
        //contBizAdscriptionDate
        contBizAdscriptionDate.setBoundEditor(pkBizAdscriptionDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contValuationType
        contValuationType.setBoundEditor(comboValuationType);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contAgioScheme
        contAgioScheme.setBoundEditor(f7AgioScheme);
        //contAgioDes
        contAgioDes.setBoundEditor(txtAgioDes);
        //contDealTotalAmount
        contDealTotalAmount.setBoundEditor(txtDealTotalAmount);
        //contDealBuildPrice
        contDealBuildPrice.setBoundEditor(txtDealBuildPrice);
        //contDealRoomPrice
        contDealRoomPrice.setBoundEditor(txtDealRoomPrice);
        //contContractTotalAmount
        contContractTotalAmount.setBoundEditor(txtContractTotalAmount);
        //contContractBuildPrice
        contContractBuildPrice.setBoundEditor(txtContractBuildPrice);
        //contContractRoomPrice
        contContractRoomPrice.setBoundEditor(txtContractRoomPrice);
        //contAgio
        contAgio.setBoundEditor(txtAgio);
        //contRecommendPerson
        contRecommendPerson.setBoundEditor(f7RecommendPerson);
        //conPayList
        conPayList.getContentPane().setLayout(new KDLayout());
        conPayList.getContentPane().putClientProperty("OriginalBounds", new Rectangle(14, 482, 984, 148));        contAFundAmount.setBounds(new Rectangle(305, 13, 270, 19));
        conPayList.getContentPane().add(contAFundAmount, new KDLayout.Constraints(305, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP));
        contLoanAmount.setBounds(new Rectangle(9, 13, 270, 19));
        conPayList.getContentPane().add(contLoanAmount, new KDLayout.Constraints(9, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddPayline.setBounds(new Rectangle(703, 13, 71, 19));
        conPayList.getContentPane().add(btnAddPayline, new KDLayout.Constraints(703, 13, 71, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnInsertPayLine.setBounds(new Rectangle(794, 13, 80, 19));
        conPayList.getContentPane().add(btnInsertPayLine, new KDLayout.Constraints(794, 13, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemovePayLine.setBounds(new Rectangle(895, 13, 71, 19));
        conPayList.getContentPane().add(btnRemovePayLine, new KDLayout.Constraints(895, 13, 71, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        tblPayList.setBounds(new Rectangle(7, 39, 965, 77));
        conPayList.getContentPane().add(tblPayList, new KDLayout.Constraints(7, 39, 965, 77, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contAFundAmount
        contAFundAmount.setBoundEditor(txtAFundAmount);
        //contLoanAmount
        contLoanAmount.setBoundEditor(txtLoanAmount);

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
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemAddCustomer);
        menuBiz.add(menuItemMemberApply);
        menuBiz.add(menuItemPointPresent);
        menuBiz.add(menuItemReceiveBill);
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
        this.toolBar.add(btnSubmitAudit);
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
        this.toolBar.add(btnAddCustomer);
        this.toolBar.add(btnMemberApply);
        this.toolBar.add(btnPointPresent);
        this.toolBar.add(btnReceiveBill);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.BaseTransactionEditUIHandler";
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
	 * ????????У??
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
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
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboSellType_itemStateChanged method
     */
    protected void comboSellType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblAttachProperty_editStopped method
     */
    protected void tblAttachProperty_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblAttachProperty_editStarted method
     */
    protected void tblAttachProperty_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output chkIsFitmentToContract_actionPerformed method
     */
    protected void chkIsFitmentToContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtFitmentAmount_dataChanged method
     */
    protected void txtFitmentAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblReceiveBill_tableClicked method
     */
    protected void tblReceiveBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblBizReview_editStopped method
     */
    protected void tblBizReview_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output f7Seller_dataChanged method
     */
    protected void f7Seller_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboValuationType_itemStateChanged method
     */
    protected void comboValuationType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output pkBizDate_dataChanged method
     */
    protected void pkBizDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
     * output f7AgioScheme_dataChanged method
     */
    protected void f7AgioScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealTotalAmount_dataChanged method
     */
    protected void txtDealTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealBuildPrice_actionPerformed method
     */
    protected void txtDealBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtContractTotalAmount_dataChanged method
     */
    protected void txtContractTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtContractBuildPrice_actionPerformed method
     */
    protected void txtContractBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7RecommendPerson_dataChanged method
     */
    protected void f7RecommendPerson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblPayList_editStopped method
     */
    protected void tblPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
     * output actionCustomerSelect_actionPerformed method
     */
    public void actionCustomerSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChooseAgio_actionPerformed method
     */
    public void actionChooseAgio_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBill_actionPerformed method
     */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddCustomer_actionPerformed method
     */
    public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMemberApply_actionPerformed method
     */
    public void actionMemberApply_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPointPresent_actionPerformed method
     */
    public void actionPointPresent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmitAudit_actionPerformed method
     */
    public void actionSubmitAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCustomerSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerSelect() {
    	return false;
    }
	public RequestContext prepareActionChooseAgio(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChooseAgio() {
    	return false;
    }
	public RequestContext prepareActionReceiveBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBill() {
    	return false;
    }
	public RequestContext prepareActionAddCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCustomer() {
    	return false;
    }
	public RequestContext prepareActionMemberApply(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMemberApply() {
    	return false;
    }
	public RequestContext prepareActionPointPresent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPointPresent() {
    	return false;
    }
	public RequestContext prepareActionSubmitAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitAudit() {
    	return false;
    }

    /**
     * output ActionCustomerSelect class
     */     
    protected class ActionCustomerSelect extends ItemAction {     
    
        public ActionCustomerSelect()
        {
            this(null);
        }

        public ActionCustomerSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCustomerSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionCustomerSelect", "actionCustomerSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionChooseAgio class
     */     
    protected class ActionChooseAgio extends ItemAction {     
    
        public ActionChooseAgio()
        {
            this(null);
        }

        public ActionChooseAgio(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChooseAgio.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseAgio.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseAgio.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionChooseAgio", "actionChooseAgio_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveBill class
     */     
    protected class ActionReceiveBill extends ItemAction {     
    
        public ActionReceiveBill()
        {
            this(null);
        }

        public ActionReceiveBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionReceiveBill", "actionReceiveBill_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionAddCustomer", "actionAddCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionMemberApply class
     */     
    protected class ActionMemberApply extends ItemAction {     
    
        public ActionMemberApply()
        {
            this(null);
        }

        public ActionMemberApply(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMemberApply.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMemberApply.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMemberApply.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionMemberApply", "actionMemberApply_actionPerformed", e);
        }
    }

    /**
     * output ActionPointPresent class
     */     
    protected class ActionPointPresent extends ItemAction {     
    
        public ActionPointPresent()
        {
            this(null);
        }

        public ActionPointPresent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPointPresent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPointPresent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPointPresent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionPointPresent", "actionPointPresent_actionPerformed", e);
        }
    }

    /**
     * output ActionSubmitAudit class
     */     
    protected class ActionSubmitAudit extends ItemAction {     
    
        public ActionSubmitAudit()
        {
            this(null);
        }

        public ActionSubmitAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSubmitAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionEditUI.this, "ActionSubmitAudit", "actionSubmitAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BaseTransactionEditUI");
    }




}