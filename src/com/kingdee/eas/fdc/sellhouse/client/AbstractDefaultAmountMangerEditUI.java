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
public abstract class AbstractDefaultAmountMangerEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDefaultAmountMangerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerNames;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTelephone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleManNames;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contArgAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDefCalDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransaction;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelevance;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomerNames;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTelephone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleManNames;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtArgAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBusType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDefCalDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTransaction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRefDeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubDeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarryAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRefDeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSubDeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCarryAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo editData = null;
    protected ActionRelevance actionRelevance = null;
    /**
     * output class constructor
     */
    public AbstractDefaultAmountMangerEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDefaultAmountMangerEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRelevance
        this.actionRelevance = new ActionRelevance(this);
        getActionManager().registerAction("actionRelevance", actionRelevance);
         this.actionRelevance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerNames = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTelephone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleManNames = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contArgAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBusType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDefCalDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransaction = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnRelevance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCustomerNames = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTelephone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSaleManNames = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtArgAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBusType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkDefCalDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtTransaction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRefDeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubDeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCarryAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRefDeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSubDeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCarryAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate.setName("contBizDate");
        this.contOrgUnit.setName("contOrgUnit");
        this.contCustomerNames.setName("contCustomerNames");
        this.contTelephone.setName("contTelephone");
        this.contSaleManNames.setName("contSaleManNames");
        this.contContractAmount.setName("contContractAmount");
        this.contArgAmount.setName("contArgAmount");
        this.contBusType.setName("contBusType");
        this.contDefCalDate.setName("contDefCalDate");
        this.contRoom.setName("contRoom");
        this.contPayment.setName("contPayment");
        this.contTransaction.setName("contTransaction");
        this.contProject.setName("contProject");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.btnRelevance.setName("btnRelevance");
        this.contRemark.setName("contRemark");
        this.contNumber.setName("contNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtCustomerNames.setName("txtCustomerNames");
        this.txtTelephone.setName("txtTelephone");
        this.txtSaleManNames.setName("txtSaleManNames");
        this.txtContractAmount.setName("txtContractAmount");
        this.txtArgAmount.setName("txtArgAmount");
        this.txtBusType.setName("txtBusType");
        this.pkDefCalDate.setName("pkDefCalDate");
        this.prmtRoom.setName("prmtRoom");
        this.prmtPayment.setName("prmtPayment");
        this.prmtTransaction.setName("prmtTransaction");
        this.prmtProject.setName("prmtProject");
        this.contRefDeAmount.setName("contRefDeAmount");
        this.contSubDeAmount.setName("contSubDeAmount");
        this.contCarryAmount.setName("contCarryAmount");
        this.txtRefDeAmount.setName("txtRefDeAmount");
        this.txtSubDeAmount.setName("txtSubDeAmount");
        this.txtCarryAmount.setName("txtCarryAmount");
        this.kdtEntry.setName("kdtEntry");
        this.txtRemark.setName("txtRemark");
        this.txtNumber.setName("txtNumber");
        // CoreUI		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);		
        this.contOrgUnit.setVisible(false);
        // contCustomerNames		
        this.contCustomerNames.setBoundLabelText(resHelper.getString("contCustomerNames.boundLabelText"));		
        this.contCustomerNames.setBoundLabelLength(100);		
        this.contCustomerNames.setBoundLabelUnderline(true);
        // contTelephone		
        this.contTelephone.setBoundLabelText(resHelper.getString("contTelephone.boundLabelText"));		
        this.contTelephone.setBoundLabelLength(100);		
        this.contTelephone.setBoundLabelUnderline(true);
        // contSaleManNames		
        this.contSaleManNames.setBoundLabelText(resHelper.getString("contSaleManNames.boundLabelText"));		
        this.contSaleManNames.setBoundLabelLength(100);		
        this.contSaleManNames.setBoundLabelUnderline(true);
        // contContractAmount		
        this.contContractAmount.setBoundLabelText(resHelper.getString("contContractAmount.boundLabelText"));		
        this.contContractAmount.setBoundLabelLength(100);		
        this.contContractAmount.setBoundLabelUnderline(true);
        // contArgAmount		
        this.contArgAmount.setBoundLabelText(resHelper.getString("contArgAmount.boundLabelText"));		
        this.contArgAmount.setBoundLabelLength(100);		
        this.contArgAmount.setBoundLabelUnderline(true);
        // contBusType		
        this.contBusType.setBoundLabelText(resHelper.getString("contBusType.boundLabelText"));		
        this.contBusType.setBoundLabelLength(100);		
        this.contBusType.setBoundLabelUnderline(true);		
        this.contBusType.setEnabled(false);		
        this.contBusType.setVisible(false);
        // contDefCalDate		
        this.contDefCalDate.setBoundLabelText(resHelper.getString("contDefCalDate.boundLabelText"));		
        this.contDefCalDate.setBoundLabelLength(100);		
        this.contDefCalDate.setBoundLabelUnderline(true);		
        this.contDefCalDate.setEnabled(false);		
        this.contDefCalDate.setVisible(false);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contPayment		
        this.contPayment.setBoundLabelText(resHelper.getString("contPayment.boundLabelText"));		
        this.contPayment.setBoundLabelLength(100);		
        this.contPayment.setBoundLabelUnderline(true);
        // contTransaction		
        this.contTransaction.setBoundLabelText(resHelper.getString("contTransaction.boundLabelText"));		
        this.contTransaction.setBoundLabelLength(100);		
        this.contTransaction.setBoundLabelUnderline(true);		
        this.contTransaction.setEnabled(false);		
        this.contTransaction.setVisible(false);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);		
        this.contProject.setVisible(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // btnRelevance
        this.btnRelevance.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelevance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelevance.setText(resHelper.getString("btnRelevance.text"));
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setEnabled(false);		
        this.contNumber.setVisible(false);
        // pkBizDate
        // prmtOrgUnit		
        this.prmtOrgUnit.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FullOrgUnitQuery");
        // txtCustomerNames		
        this.txtCustomerNames.setMaxLength(200);
        // txtTelephone		
        this.txtTelephone.setMaxLength(500);
        // txtSaleManNames		
        this.txtSaleManNames.setMaxLength(200);
        // txtContractAmount
        // txtArgAmount
        // txtBusType		
        this.txtBusType.setMaxLength(100);		
        this.txtBusType.setEnabled(false);		
        this.txtBusType.setVisible(false);
        // pkDefCalDate
        // prmtRoom		
        this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
        // prmtPayment		
        this.prmtPayment.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");
        // prmtTransaction
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtProject.setEnabled(false);		
        this.prmtProject.setVisible(false);
        // contRefDeAmount		
        this.contRefDeAmount.setBoundLabelText(resHelper.getString("contRefDeAmount.boundLabelText"));		
        this.contRefDeAmount.setBoundLabelLength(100);		
        this.contRefDeAmount.setBoundLabelUnderline(true);
        // contSubDeAmount		
        this.contSubDeAmount.setBoundLabelText(resHelper.getString("contSubDeAmount.boundLabelText"));		
        this.contSubDeAmount.setBoundLabelLength(100);		
        this.contSubDeAmount.setBoundLabelUnderline(true);
        // contCarryAmount		
        this.contCarryAmount.setBoundLabelText(resHelper.getString("contCarryAmount.boundLabelText"));		
        this.contCarryAmount.setBoundLabelLength(100);		
        this.contCarryAmount.setBoundLabelUnderline(true);
        // txtRefDeAmount
        // txtSubDeAmount
        // txtCarryAmount
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"argDays\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"argAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"referAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{argDays}</t:Cell><t:Cell>$Resource{argAmount}</t:Cell><t:Cell>$Resource{referAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"moneyDefine","appDate","appAmount","actDate","actAmount","argDays","argAmount","referAmount"});


        // txtRemark		
        this.txtRemark.setMaxLength(200);
        // txtNumber
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
        this.setLayout(null);
        contBizDate.setBounds(new Rectangle(28, 57, 270, 19));
        this.add(contBizDate, null);
        contOrgUnit.setBounds(new Rectangle(733, 599, 270, 19));
        this.add(contOrgUnit, null);
        contCustomerNames.setBounds(new Rectangle(28, 13, 270, 19));
        this.add(contCustomerNames, null);
        contTelephone.setBounds(new Rectangle(376, 13, 618, 19));
        this.add(contTelephone, null);
        contSaleManNames.setBounds(new Rectangle(724, 79, 270, 19));
        this.add(contSaleManNames, null);
        contContractAmount.setBounds(new Rectangle(28, 79, 270, 19));
        this.add(contContractAmount, null);
        contArgAmount.setBounds(new Rectangle(376, 79, 270, 19));
        this.add(contArgAmount, null);
        contBusType.setBounds(new Rectangle(451, 597, 270, 19));
        this.add(contBusType, null);
        contDefCalDate.setBounds(new Rectangle(732, 577, 270, 19));
        this.add(contDefCalDate, null);
        contRoom.setBounds(new Rectangle(28, 35, 270, 19));
        this.add(contRoom, null);
        contPayment.setBounds(new Rectangle(376, 57, 270, 19));
        this.add(contPayment, null);
        contTransaction.setBounds(new Rectangle(162, 602, 270, 19));
        this.add(contTransaction, null);
        contProject.setBounds(new Rectangle(159, 577, 270, 19));
        this.add(contProject, null);
        kDContainer1.setBounds(new Rectangle(20, 112, 980, 68));
        this.add(kDContainer1, null);
        kDContainer2.setBounds(new Rectangle(20, 191, 980, 383));
        this.add(kDContainer2, null);
        btnRelevance.setBounds(new Rectangle(724, 35, 99, 19));
        this.add(btnRelevance, null);
        contRemark.setBounds(new Rectangle(376, 35, 270, 19));
        this.add(contRemark, null);
        contNumber.setBounds(new Rectangle(450, 574, 270, 19));
        this.add(contNumber, null);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contCustomerNames
        contCustomerNames.setBoundEditor(txtCustomerNames);
        //contTelephone
        contTelephone.setBoundEditor(txtTelephone);
        //contSaleManNames
        contSaleManNames.setBoundEditor(txtSaleManNames);
        //contContractAmount
        contContractAmount.setBoundEditor(txtContractAmount);
        //contArgAmount
        contArgAmount.setBoundEditor(txtArgAmount);
        //contBusType
        contBusType.setBoundEditor(txtBusType);
        //contDefCalDate
        contDefCalDate.setBoundEditor(pkDefCalDate);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contPayment
        contPayment.setBoundEditor(prmtPayment);
        //contTransaction
        contTransaction.setBoundEditor(prmtTransaction);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(20, 112, 980, 68));        contRefDeAmount.setBounds(new Rectangle(7, 11, 270, 19));
        kDContainer1.getContentPane().add(contRefDeAmount, new KDLayout.Constraints(7, 11, 270, 19, 0));
        contSubDeAmount.setBounds(new Rectangle(356, 11, 270, 19));
        kDContainer1.getContentPane().add(contSubDeAmount, new KDLayout.Constraints(356, 11, 270, 19, 0));
        contCarryAmount.setBounds(new Rectangle(703, 11, 270, 19));
        kDContainer1.getContentPane().add(contCarryAmount, new KDLayout.Constraints(703, 11, 270, 19, 0));
        //contRefDeAmount
        contRefDeAmount.setBoundEditor(txtRefDeAmount);
        //contSubDeAmount
        contSubDeAmount.setBoundEditor(txtSubDeAmount);
        //contCarryAmount
        contCarryAmount.setBoundEditor(txtCarryAmount);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(20, 191, 980, 383));        kdtEntry.setBounds(new Rectangle(3, 4, 965, 354));
        kDContainer2.getContentPane().add(kdtEntry, new KDLayout.Constraints(3, 4, 965, 354, 0));
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contNumber
        contNumber.setBoundEditor(txtNumber);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("customerNames", String.class, this.txtCustomerNames, "text");
		dataBinder.registerBinding("telephone", String.class, this.txtTelephone, "text");
		dataBinder.registerBinding("saleManNames", String.class, this.txtSaleManNames, "text");
		dataBinder.registerBinding("contractAmount", java.math.BigDecimal.class, this.txtContractAmount, "value");
		dataBinder.registerBinding("argAmount", java.math.BigDecimal.class, this.txtArgAmount, "value");
		dataBinder.registerBinding("busType", String.class, this.txtBusType, "text");
		dataBinder.registerBinding("defCalDate", java.util.Date.class, this.pkDefCalDate, "value");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtRoom, "data");
		dataBinder.registerBinding("payment", com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo.class, this.prmtPayment, "data");
		dataBinder.registerBinding("transaction", com.kingdee.eas.fdc.sellhouse.TransactionInfo.class, this.prmtTransaction, "data");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("refDeAmount", java.math.BigDecimal.class, this.txtRefDeAmount, "value");
		dataBinder.registerBinding("subDeAmount", java.math.BigDecimal.class, this.txtSubDeAmount, "value");
		dataBinder.registerBinding("carryAmount", java.math.BigDecimal.class, this.txtCarryAmount, "value");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.kdtEntry, "moneyDefine.text");
		dataBinder.registerBinding("entry.appDate", java.util.Date.class, this.kdtEntry, "appDate.text");
		dataBinder.registerBinding("entry.appAmount", java.math.BigDecimal.class, this.kdtEntry, "appAmount.text");
		dataBinder.registerBinding("entry.actDate", java.util.Date.class, this.kdtEntry, "actDate.text");
		dataBinder.registerBinding("entry.actAmount", java.math.BigDecimal.class, this.kdtEntry, "actAmount.text");
		dataBinder.registerBinding("entry.argDays", int.class, this.kdtEntry, "argDays.text");
		dataBinder.registerBinding("entry.argAmount", java.math.BigDecimal.class, this.kdtEntry, "argAmount.text");
		dataBinder.registerBinding("entry.referAmount", java.math.BigDecimal.class, this.kdtEntry, "referAmount.text");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo)ov;
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
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerNames", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("telephone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleManNames", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("argAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("busType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("defCalDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transaction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("refDeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("subDeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("carryAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.appDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.appAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.actDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.actAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.argDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.argAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.referAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("customerNames"));
        sic.add(new SelectorItemInfo("telephone"));
        sic.add(new SelectorItemInfo("saleManNames"));
        sic.add(new SelectorItemInfo("contractAmount"));
        sic.add(new SelectorItemInfo("argAmount"));
        sic.add(new SelectorItemInfo("busType"));
        sic.add(new SelectorItemInfo("defCalDate"));
        sic.add(new SelectorItemInfo("room.*"));
        sic.add(new SelectorItemInfo("payment.*"));
        sic.add(new SelectorItemInfo("transaction.*"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("refDeAmount"));
        sic.add(new SelectorItemInfo("subDeAmount"));
        sic.add(new SelectorItemInfo("carryAmount"));
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
        sic.add(new SelectorItemInfo("entry.moneyDefine.*"));
//        sic.add(new SelectorItemInfo("entry.moneyDefine.number"));
    sic.add(new SelectorItemInfo("entry.appDate"));
    sic.add(new SelectorItemInfo("entry.appAmount"));
    sic.add(new SelectorItemInfo("entry.actDate"));
    sic.add(new SelectorItemInfo("entry.actAmount"));
    sic.add(new SelectorItemInfo("entry.argDays"));
    sic.add(new SelectorItemInfo("entry.argAmount"));
    sic.add(new SelectorItemInfo("entry.referAmount"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("number"));
        return sic;
    }        
    	

    /**
     * output actionRelevance_actionPerformed method
     */
    public void actionRelevance_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRelevance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelevance() {
    	return false;
    }

    /**
     * output ActionRelevance class
     */     
    protected class ActionRelevance extends ItemAction {     
    
        public ActionRelevance()
        {
            this(null);
        }

        public ActionRelevance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRelevance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelevance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelevance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerEditUI.this, "ActionRelevance", "actionRelevance_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "DefaultAmountMangerEditUI");
    }




}