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
public abstract class AbstractFDCReceivingBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCReceivingBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecLocAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevBizType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectRevList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFdcCustomers;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomLongNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Auditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7OrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Company;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRecAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRecLocAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRevBillType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRevBizType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7FdcCustomers;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceive;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuReceive;
    protected com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo editData = null;
    protected ActionReceive actionReceive = null;
    /**
     * output class constructor
     */
    public AbstractFDCReceivingBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCReceivingBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionReceive
        this.actionReceive = new ActionReceive(this);
        getActionManager().registerAction("actionReceive", actionReceive);
         this.actionReceive.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecLocAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevBizType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSelectRevList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contFdcCustomers = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.f7Auditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7OrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Company = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRecAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtExchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRecLocAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboRevBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboRevBizType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7FdcCustomers = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoomLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnReceive = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuReceive = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contAuditTime.setName("contAuditTime");
        this.contCompany.setName("contCompany");
        this.contSellProject.setName("contSellProject");
        this.contCurrency.setName("contCurrency");
        this.contRecAmount.setName("contRecAmount");
        this.contExchangeRate.setName("contExchangeRate");
        this.contRecLocAmount.setName("contRecLocAmount");
        this.contRevBillType.setName("contRevBillType");
        this.contRevBizType.setName("contRevBizType");
        this.tblEntry.setName("tblEntry");
        this.btnSelectRevList.setName("btnSelectRevList");
        this.contFdcCustomers.setName("contFdcCustomers");
        this.contCustomer.setName("contCustomer");
        this.contRoom.setName("contRoom");
        this.contRoomLongNumber.setName("contRoomLongNumber");
        this.f7Creator.setName("f7Creator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.f7Auditor.setName("f7Auditor");
        this.f7OrgUnit.setName("f7OrgUnit");
        this.pkAuditTime.setName("pkAuditTime");
        this.f7Company.setName("f7Company");
        this.f7SellProject.setName("f7SellProject");
        this.f7Currency.setName("f7Currency");
        this.txtRecAmount.setName("txtRecAmount");
        this.txtExchangeRate.setName("txtExchangeRate");
        this.txtRecLocAmount.setName("txtRecLocAmount");
        this.comboRevBillType.setName("comboRevBillType");
        this.comboRevBizType.setName("comboRevBizType");
        this.f7FdcCustomers.setName("f7FdcCustomers");
        this.f7Customer.setName("f7Customer");
        this.f7Room.setName("f7Room");
        this.txtRoomLongNumber.setName("txtRoomLongNumber");
        this.btnReceive.setName("btnReceive");
        this.menuReceive.setName("menuReceive");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contRecAmount		
        this.contRecAmount.setBoundLabelText(resHelper.getString("contRecAmount.boundLabelText"));		
        this.contRecAmount.setBoundLabelLength(100);		
        this.contRecAmount.setBoundLabelUnderline(true);
        // contExchangeRate		
        this.contExchangeRate.setBoundLabelText(resHelper.getString("contExchangeRate.boundLabelText"));		
        this.contExchangeRate.setBoundLabelLength(100);		
        this.contExchangeRate.setBoundLabelUnderline(true);
        // contRecLocAmount		
        this.contRecLocAmount.setBoundLabelText(resHelper.getString("contRecLocAmount.boundLabelText"));		
        this.contRecLocAmount.setBoundLabelLength(100);		
        this.contRecLocAmount.setBoundLabelUnderline(true);
        // contRevBillType		
        this.contRevBillType.setBoundLabelText(resHelper.getString("contRevBillType.boundLabelText"));		
        this.contRevBillType.setBoundLabelLength(100);		
        this.contRevBillType.setBoundLabelUnderline(true);
        // contRevBizType		
        this.contRevBizType.setBoundLabelText(resHelper.getString("contRevBizType.boundLabelText"));		
        this.contRevBizType.setBoundLabelLength(100);		
        this.contRevBizType.setBoundLabelUnderline(true);
        // tblEntry
		String tblEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fromMoneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"fromAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"stleCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"stleType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"stleNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"revAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"revBankAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"custAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"locAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"actRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"remissionAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"limitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"oppAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"supplyOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"supplyDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{fromMoneyDefine}</t:Cell><t:Cell>$Resource{fromAccount}</t:Cell><t:Cell>$Resource{stleCount}</t:Cell><t:Cell>$Resource{stleType}</t:Cell><t:Cell>$Resource{stleNumber}</t:Cell><t:Cell>$Resource{revAccount}</t:Cell><t:Cell>$Resource{revBankAccount}</t:Cell><t:Cell>$Resource{custAccount}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{locAmount}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actRevAmount}</t:Cell><t:Cell>$Resource{remissionAmount}</t:Cell><t:Cell>$Resource{limitAmount}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{oppAccount}</t:Cell><t:Cell>$Resource{supplyOrg}</t:Cell><t:Cell>$Resource{supplyDes}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblEntry.setFormatXml(resHelper.translateString("tblEntry",tblEntryStrXML));
        this.tblEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnSelectRevList		
        this.btnSelectRevList.setText(resHelper.getString("btnSelectRevList.text"));
        this.btnSelectRevList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectRevList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contFdcCustomers		
        this.contFdcCustomers.setBoundLabelText(resHelper.getString("contFdcCustomers.boundLabelText"));		
        this.contFdcCustomers.setBoundLabelUnderline(true);		
        this.contFdcCustomers.setBoundLabelLength(100);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelUnderline(true);		
        this.contCustomer.setBoundLabelLength(100);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contRoomLongNumber		
        this.contRoomLongNumber.setBoundLabelText(resHelper.getString("contRoomLongNumber.boundLabelText"));		
        this.contRoomLongNumber.setBoundLabelUnderline(true);		
        this.contRoomLongNumber.setBoundLabelLength(100);
        // f7Creator		
        this.f7Creator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // kDScrollPane1
        // txtDescription
        // f7Auditor		
        this.f7Auditor.setEnabled(false);
        // f7OrgUnit		
        this.f7OrgUnit.setEnabled(false);		
        this.f7OrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.FullOrgUnitQuery");		
        this.f7OrgUnit.setCommitFormat("$number$");		
        this.f7OrgUnit.setEditFormat("$number$");		
        this.f7OrgUnit.setDisplayFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // f7Company		
        this.f7Company.setEnabled(false);		
        this.f7Company.setCommitFormat("$number$");		
        this.f7Company.setEditFormat("$number$");		
        this.f7Company.setDisplayFormat("$name$");		
        this.f7Company.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setRequired(true);		
        this.f7SellProject.setDisplayFormat("$name$");		
        this.f7SellProject.setEditFormat("$number$");		
        this.f7SellProject.setCommitFormat("$number$");
        // f7Currency		
        this.f7Currency.setRequired(true);		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setCommitFormat("$number$");
        this.f7Currency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Currency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRecAmount		
        this.txtRecAmount.setDataType(1);		
        this.txtRecAmount.setPrecision(2);
        // txtExchangeRate		
        this.txtExchangeRate.setDataType(1);		
        this.txtExchangeRate.setEditable(false);		
        this.txtExchangeRate.setPrecision(2);
        // txtRecLocAmount		
        this.txtRecLocAmount.setDataType(1);		
        this.txtRecLocAmount.setPrecision(2);
        // comboRevBillType		
        this.comboRevBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RevBillTypeEnum").toArray());
        // comboRevBizType		
        this.comboRevBizType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RevBizTypeEnum").toArray());
        this.comboRevBizType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboRevBizType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7FdcCustomers		
        this.f7FdcCustomers.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.f7FdcCustomers.setCommitFormat("$number$");		
        this.f7FdcCustomers.setDisplayFormat("$name$");		
        this.f7FdcCustomers.setEditFormat("$number$");
        // f7Customer		
        this.f7Customer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");		
        this.f7Customer.setCommitFormat("$number$");		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");
        // f7Room		
        this.f7Room.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
        // txtRoomLongNumber
        // btnReceive
        this.btnReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceive.setText(resHelper.getString("btnReceive.text"));		
        this.btnReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // menuReceive
        this.menuReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuReceive.setText(resHelper.getString("menuReceive.text"));		
        this.menuReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        contCreator.setBounds(new Rectangle(11, 548, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(11, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(371, 548, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 548, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contNumber.setBounds(new Rectangle(11, 38, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(11, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(371, 38, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(371, 38, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contDescription.setBounds(new Rectangle(8, 188, 991, 47));
        this.add(contDescription, new KDLayout.Constraints(8, 188, 991, 47, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(11, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(11, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contAuditTime.setBounds(new Rectangle(371, 573, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(371, 573, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contCompany.setBounds(new Rectangle(11, 10, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(11, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(731, 10, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(731, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurrency.setBounds(new Rectangle(11, 66, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(11, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecAmount.setBounds(new Rectangle(731, 38, 270, 19));
        this.add(contRecAmount, new KDLayout.Constraints(731, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contExchangeRate.setBounds(new Rectangle(371, 66, 270, 19));
        this.add(contExchangeRate, new KDLayout.Constraints(371, 66, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contRecLocAmount.setBounds(new Rectangle(731, 66, 270, 19));
        this.add(contRecLocAmount, new KDLayout.Constraints(731, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRevBillType.setBounds(new Rectangle(11, 95, 270, 19));
        this.add(contRevBillType, new KDLayout.Constraints(11, 95, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRevBizType.setBounds(new Rectangle(371, 95, 270, 19));
        this.add(contRevBizType, new KDLayout.Constraints(371, 95, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        tblEntry.setBounds(new Rectangle(11, 256, 992, 284));
        this.add(tblEntry, new KDLayout.Constraints(11, 256, 992, 284, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSelectRevList.setBounds(new Rectangle(903, 226, 73, 19));
        this.add(btnSelectRevList, new KDLayout.Constraints(903, 226, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        contFdcCustomers.setBounds(new Rectangle(716, 97, 270, 19));
        this.add(contFdcCustomers, new KDLayout.Constraints(716, 97, 270, 19, 0));
        contCustomer.setBounds(new Rectangle(713, 126, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(713, 126, 270, 19, 0));
        contRoom.setBounds(new Rectangle(13, 128, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(13, 128, 270, 19, 0));
        contRoomLongNumber.setBounds(new Rectangle(371, 124, 270, 19));
        this.add(contRoomLongNumber, new KDLayout.Constraints(371, 124, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(f7OrgUnit);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCompany
        contCompany.setBoundEditor(f7Company);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contRecAmount
        contRecAmount.setBoundEditor(txtRecAmount);
        //contExchangeRate
        contExchangeRate.setBoundEditor(txtExchangeRate);
        //contRecLocAmount
        contRecLocAmount.setBoundEditor(txtRecLocAmount);
        //contRevBillType
        contRevBillType.setBoundEditor(comboRevBillType);
        //contRevBizType
        contRevBizType.setBoundEditor(comboRevBizType);
        //contFdcCustomers
        contFdcCustomers.setBoundEditor(f7FdcCustomers);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contRoomLongNumber
        contRoomLongNumber.setBoundEditor(txtRoomLongNumber);

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
        menuBiz.add(menuReceive);
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
        this.toolBar.add(btnReceive);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.f7Auditor, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.f7OrgUnit, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.f7Company, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.f7SellProject, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.f7Currency, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtRecAmount, "value");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtExchangeRate, "value");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtRecLocAmount, "value");
		dataBinder.registerBinding("revBillType", com.kingdee.eas.fdc.basecrm.RevBillTypeEnum.class, this.comboRevBillType, "selectedItem");
		dataBinder.registerBinding("revBizType", com.kingdee.eas.fdc.basecrm.RevBizTypeEnum.class, this.comboRevBizType, "selectedItem");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.f7Room, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    		
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
     * output tblEntry_editStopped method
     */
    protected void tblEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnSelectRevList_actionPerformed method
     */
    protected void btnSelectRevList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7Currency_dataChanged method
     */
    protected void f7Currency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboRevBizType_itemStateChanged method
     */
    protected void comboRevBizType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("revBillType"));
        sic.add(new SelectorItemInfo("revBizType"));
        sic.add(new SelectorItemInfo("room.*"));
        return sic;
    }        
    	

    /**
     * output actionReceive_actionPerformed method
     */
    public void actionReceive_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionReceive(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceive() {
    	return false;
    }

    /**
     * output ActionReceive class
     */     
    protected class ActionReceive extends ItemAction {     
    
        public ActionReceive()
        {
            this(null);
        }

        public ActionReceive(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReceive.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceive.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceive.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCReceivingBillEditUI.this, "ActionReceive", "actionReceive_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "FDCReceivingBillEditUI");
    }




}