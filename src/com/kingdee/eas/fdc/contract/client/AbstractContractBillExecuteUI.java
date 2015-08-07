/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractBillExecuteUI extends com.kingdee.eas.fdc.basedata.client.FDCRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillExecuteUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPayment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExpand;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShorten;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplayAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplayNoText;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplayContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRecense;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevert;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVersionInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpression;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFirstVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPreVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNextVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemLastVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemApportion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewPayment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpand;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShorten;
    protected ActionViewContract actionViewContract = null;
    protected ActionViewPayment actionViewPayment = null;
    protected ActionExpand actionExpand = null;
    protected ActionShorten actionShorten = null;
    protected ActionViewPayPlan actionViewPayPlan = null;
    protected ActionDisplayAll actionDisplayAll = null;
    protected ActionDisplayConNoText actionDisplayConNoText = null;
    protected ActionDisplayContract actionDisplayContract = null;
    /**
     * output class constructor
     */
    public AbstractContractBillExecuteUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillExecuteUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillExecuteQuery");
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPayment
        this.actionViewPayment = new ActionViewPayment(this);
        getActionManager().registerAction("actionViewPayment", actionViewPayment);
         this.actionViewPayment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExpand
        this.actionExpand = new ActionExpand(this);
        getActionManager().registerAction("actionExpand", actionExpand);
         this.actionExpand.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShorten
        this.actionShorten = new ActionShorten(this);
        getActionManager().registerAction("actionShorten", actionShorten);
         this.actionShorten.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPayPlan
        this.actionViewPayPlan = new ActionViewPayPlan(this);
        getActionManager().registerAction("actionViewPayPlan", actionViewPayPlan);
         this.actionViewPayPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayAll
        this.actionDisplayAll = new ActionDisplayAll(this);
        getActionManager().registerAction("actionDisplayAll", actionDisplayAll);
         this.actionDisplayAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayConNoText
        this.actionDisplayConNoText = new ActionDisplayConNoText(this);
        getActionManager().registerAction("actionDisplayConNoText", actionDisplayConNoText);
         this.actionDisplayConNoText.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayContract
        this.actionDisplayContract = new ActionDisplayContract(this);
        getActionManager().registerAction("actionDisplayContract", actionDisplayContract);
         this.actionDisplayContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewPayment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPayPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExpand = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShorten = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisplayAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisplayNoText = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisplayContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRecense = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevert = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemVersionInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpression = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFirstVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPreVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNextVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemLastVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemApportion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewPayment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewPayPlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpand = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShorten = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewContract.setName("btnViewContract");
        this.btnViewPayment.setName("btnViewPayment");
        this.btnPayPlan.setName("btnPayPlan");
        this.btnExpand.setName("btnExpand");
        this.btnShorten.setName("btnShorten");
        this.btnDisplayAll.setName("btnDisplayAll");
        this.btnDisplayNoText.setName("btnDisplayNoText");
        this.btnDisplayContract.setName("btnDisplayContract");
        this.menuItemSubmit.setName("menuItemSubmit");
        this.menuItemRecense.setName("menuItemRecense");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        this.menuItemRevert.setName("menuItemRevert");
        this.menuItemVersionInfo.setName("menuItemVersionInfo");
        this.menuItemExpression.setName("menuItemExpression");
        this.menuItemFirstVersion.setName("menuItemFirstVersion");
        this.menuItemPreVersion.setName("menuItemPreVersion");
        this.menuItemNextVersion.setName("menuItemNextVersion");
        this.menuItemLastVersion.setName("menuItemLastVersion");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemApportion.setName("menuItemApportion");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.menuItemViewPayment.setName("menuItemViewPayment");
        this.menuItemViewPayPlan.setName("menuItemViewPayPlan");
        this.menuItemExpand.setName("menuItemExpand");
        this.menuItemShorten.setName("menuItemShorten");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"contractBill.contractType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"contractBill.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"contractBill.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"partB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"contractBill.bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"contractBill.auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"currency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"contractBill.hasSettled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"contractBill.oriAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"contractBill.amt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"contractLastSrcAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"contractBillLastAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"reAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"reBookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"reOriAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"reAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"payRealDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"payRealSrcAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"payRealAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"payRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"notSrcPayed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"notPayed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"createDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"contract.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"paymentBill.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project.name}</t:Cell><t:Cell>$Resource{contractBill.contractType}</t:Cell><t:Cell>$Resource{contractBill.number}</t:Cell><t:Cell>$Resource{contractBill.name}</t:Cell><t:Cell>$Resource{partB}</t:Cell><t:Cell>$Resource{contractBill.bookedDate}</t:Cell><t:Cell>$Resource{contractBill.auditTime}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{contractBill.hasSettled}</t:Cell><t:Cell>$Resource{contractBill.oriAmt}</t:Cell><t:Cell>$Resource{contractBill.amt}</t:Cell><t:Cell>$Resource{contractLastSrcAmt}</t:Cell><t:Cell>$Resource{contractBillLastAmt}</t:Cell><t:Cell>$Resource{reAmount}</t:Cell><t:Cell>$Resource{reBookedDate}</t:Cell><t:Cell>$Resource{reOriAmt}</t:Cell><t:Cell>$Resource{reAmt}</t:Cell><t:Cell>$Resource{payRealDate}</t:Cell><t:Cell>$Resource{payRealSrcAmt}</t:Cell><t:Cell>$Resource{payRealAmt}</t:Cell><t:Cell>$Resource{payRate}</t:Cell><t:Cell>$Resource{notSrcPayed}</t:Cell><t:Cell>$Resource{notPayed}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDept}</t:Cell><t:Cell>$Resource{contract.id}</t:Cell><t:Cell>$Resource{paymentBill.id}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project.name_Row2}</t:Cell><t:Cell>$Resource{contractBill.contractType_Row2}</t:Cell><t:Cell>$Resource{contractBill.number_Row2}</t:Cell><t:Cell>$Resource{contractBill.name_Row2}</t:Cell><t:Cell>$Resource{partB_Row2}</t:Cell><t:Cell>$Resource{contractBill.bookedDate_Row2}</t:Cell><t:Cell>$Resource{contractBill.auditTime_Row2}</t:Cell><t:Cell>$Resource{currency.name_Row2}</t:Cell><t:Cell>$Resource{contractBill.hasSettled_Row2}</t:Cell><t:Cell>$Resource{contractBill.oriAmt_Row2}</t:Cell><t:Cell>$Resource{contractBill.amt_Row2}</t:Cell><t:Cell>$Resource{contractLastSrcAmt_Row2}</t:Cell><t:Cell>$Resource{contractBillLastAmt_Row2}</t:Cell><t:Cell>$Resource{reAmount_Row2}</t:Cell><t:Cell>$Resource{reBookedDate_Row2}</t:Cell><t:Cell>$Resource{reOriAmt_Row2}</t:Cell><t:Cell>$Resource{reAmt_Row2}</t:Cell><t:Cell>$Resource{payRealDate_Row2}</t:Cell><t:Cell>$Resource{payRealSrcAmt_Row2}</t:Cell><t:Cell>$Resource{payRealAmt_Row2}</t:Cell><t:Cell>$Resource{payRate_Row2}</t:Cell><t:Cell>$Resource{notSrcPayed_Row2}</t:Cell><t:Cell>$Resource{notPayed_Row2}</t:Cell><t:Cell>$Resource{creator_Row2}</t:Cell><t:Cell>$Resource{createDept_Row2}</t:Cell><t:Cell>$Resource{contract.id_Row2}</t:Cell><t:Cell>$Resource{paymentBill.id_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"0\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"0\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"0\" t:right=\"20\" /><t:Block t:top=\"0\" t:left=\"21\" t:bottom=\"0\" t:right=\"22\" /><t:Block t:top=\"0\" t:left=\"23\" t:bottom=\"1\" t:right=\"23\" /><t:Block t:top=\"0\" t:left=\"24\" t:bottom=\"1\" t:right=\"24\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","",""});

		
        this.menuItemAddNew.setVisible(false);		
        this.MenuItemAttachment.setEnabled(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);		
        this.pnlMain.setDividerLocation(180);		
        this.pnlMain.setOneTouchExpandable(true);
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));
        // btnViewPayment
        this.btnViewPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPayment.setText(resHelper.getString("btnViewPayment.text"));
        // btnPayPlan
        this.btnPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPayPlan.setText(resHelper.getString("btnPayPlan.text"));		
        this.btnPayPlan.setToolTipText(resHelper.getString("btnPayPlan.toolTipText"));
        // btnExpand
        this.btnExpand.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpand, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExpand.setText(resHelper.getString("btnExpand.text"));
        // btnShorten
        this.btnShorten.setAction((IItemAction)ActionProxyFactory.getProxy(actionShorten, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShorten.setText(resHelper.getString("btnShorten.text"));
        // btnDisplayAll
        this.btnDisplayAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplayAll.setText(resHelper.getString("btnDisplayAll.text"));		
        this.btnDisplayAll.setToolTipText(resHelper.getString("btnDisplayAll.toolTipText"));
        // btnDisplayNoText
        this.btnDisplayNoText.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayConNoText, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplayNoText.setText(resHelper.getString("btnDisplayNoText.text"));		
        this.btnDisplayNoText.setToolTipText(resHelper.getString("btnDisplayNoText.toolTipText"));
        // btnDisplayContract
        this.btnDisplayContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplayContract.setText(resHelper.getString("btnDisplayContract.text"));		
        this.btnDisplayContract.setToolTipText(resHelper.getString("btnDisplayContract.toolTipText"));
        // menuItemSubmit		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));
        // menuItemRecense		
        this.menuItemRecense.setText(resHelper.getString("menuItemRecense.text"));		
        this.menuItemRecense.setToolTipText(resHelper.getString("menuItemRecense.toolTipText"));
        // menuItemAddRow		
        this.menuItemAddRow.setText(resHelper.getString("menuItemAddRow.text"));
        // menuItemDeleteRow		
        this.menuItemDeleteRow.setText(resHelper.getString("menuItemDeleteRow.text"));
        // menuItemRevert		
        this.menuItemRevert.setText(resHelper.getString("menuItemRevert.text"));		
        this.menuItemRevert.setToolTipText(resHelper.getString("menuItemRevert.toolTipText"));
        // menuItemVersionInfo		
        this.menuItemVersionInfo.setText(resHelper.getString("menuItemVersionInfo.text"));		
        this.menuItemVersionInfo.setToolTipText(resHelper.getString("menuItemVersionInfo.toolTipText"));
        // menuItemExpression		
        this.menuItemExpression.setText(resHelper.getString("menuItemExpression.text"));		
        this.menuItemExpression.setToolTipText(resHelper.getString("menuItemExpression.toolTipText"));
        // menuItemFirstVersion		
        this.menuItemFirstVersion.setText(resHelper.getString("menuItemFirstVersion.text"));
        // menuItemPreVersion		
        this.menuItemPreVersion.setText(resHelper.getString("menuItemPreVersion.text"));
        // menuItemNextVersion		
        this.menuItemNextVersion.setText(resHelper.getString("menuItemNextVersion.text"));
        // menuItemLastVersion		
        this.menuItemLastVersion.setText(resHelper.getString("menuItemLastVersion.text"));
        // menuItemAudit		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setVisible(false);		
        this.menuItemAudit.setEnabled(false);
        // menuItemUnAudit		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setEnabled(false);		
        this.menuItemUnAudit.setVisible(false);
        // menuItemApportion		
        this.menuItemApportion.setText(resHelper.getString("menuItemApportion.text"));		
        this.menuItemApportion.setEnabled(false);		
        this.menuItemApportion.setVisible(false);
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));		
        this.menuItemViewContract.setMnemonic(84);		
        this.menuItemViewContract.setToolTipText(resHelper.getString("menuItemViewContract.toolTipText"));
        // menuItemViewPayment
        this.menuItemViewPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewPayment.setText(resHelper.getString("menuItemViewPayment.text"));		
        this.menuItemViewPayment.setToolTipText(resHelper.getString("menuItemViewPayment.toolTipText"));		
        this.menuItemViewPayment.setMnemonic(77);
        // menuItemViewPayPlan
        this.menuItemViewPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewPayPlan.setText(resHelper.getString("menuItemViewPayPlan.text"));		
        this.menuItemViewPayPlan.setMnemonic(80);		
        this.menuItemViewPayPlan.setToolTipText(resHelper.getString("menuItemViewPayPlan.toolTipText"));
        // menuItemExpand
        this.menuItemExpand.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpand, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpand.setText(resHelper.getString("menuItemExpand.text"));		
        this.menuItemExpand.setMnemonic(69);		
        this.menuItemExpand.setToolTipText(resHelper.getString("menuItemExpand.toolTipText"));
        // menuItemShorten
        this.menuItemShorten.setAction((IItemAction)ActionProxyFactory.getProxy(actionShorten, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShorten.setText(resHelper.getString("menuItemShorten.text"));		
        this.menuItemShorten.setMnemonic(73);		
        this.menuItemShorten.setToolTipText(resHelper.getString("menuItemShorten.toolTipText"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

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
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemRecense);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(menuItemAddRow);
        menuEdit.add(menuItemDeleteRow);
        menuEdit.add(menuItemRevert);
        menuEdit.add(menuItemVersionInfo);
        menuEdit.add(menuItemExpression);
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
        menuView.add(menuItemFirstVersion);
        menuView.add(menuItemPreVersion);
        menuView.add(menuItemNextVersion);
        menuView.add(menuItemLastVersion);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemApportion);
        menuBiz.add(menuItemViewContract);
        menuBiz.add(menuItemViewPayment);
        menuBiz.add(menuItemViewPayPlan);
        menuBiz.add(menuItemExpand);
        menuBiz.add(menuItemShorten);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnViewPayment);
        this.toolBar.add(btnPayPlan);
        this.toolBar.add(btnExpand);
        this.toolBar.add(btnShorten);
        this.toolBar.add(btnDisplayAll);
        this.toolBar.add(btnDisplayNoText);
        this.toolBar.add(btnDisplayContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractBillExecuteUIHandler";
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
     * output tblMain_editStopping method
     */
    protected void tblMain_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewPayment_actionPerformed method
     */
    public void actionViewPayment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExpand_actionPerformed method
     */
    public void actionExpand_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShorten_actionPerformed method
     */
    public void actionShorten_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewPayPlan_actionPerformed method
     */
    public void actionViewPayPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayAll_actionPerformed method
     */
    public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayConNoText_actionPerformed method
     */
    public void actionDisplayConNoText_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayContract_actionPerformed method
     */
    public void actionDisplayContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }
	public RequestContext prepareActionViewPayment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewPayment() {
    	return false;
    }
	public RequestContext prepareActionExpand(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExpand() {
    	return false;
    }
	public RequestContext prepareActionShorten(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShorten() {
    	return false;
    }
	public RequestContext prepareActionViewPayPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewPayPlan() {
    	return false;
    }
	public RequestContext prepareActionDisplayAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayAll() {
    	return false;
    }
	public RequestContext prepareActionDisplayConNoText(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayConNoText() {
    	return false;
    }
	public RequestContext prepareActionDisplayContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayContract() {
    	return false;
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift T"));
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionViewPayment class
     */     
    protected class ActionViewPayment extends ItemAction {     
    
        public ActionViewPayment()
        {
            this(null);
        }

        public ActionViewPayment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift M"));
            _tempStr = resHelper.getString("ActionViewPayment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionViewPayment", "actionViewPayment_actionPerformed", e);
        }
    }

    /**
     * output ActionExpand class
     */     
    protected class ActionExpand extends ItemAction {     
    
        public ActionExpand()
        {
            this(null);
        }

        public ActionExpand(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift E"));
            _tempStr = resHelper.getString("ActionExpand.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExpand.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExpand.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionExpand", "actionExpand_actionPerformed", e);
        }
    }

    /**
     * output ActionShorten class
     */     
    protected class ActionShorten extends ItemAction {     
    
        public ActionShorten()
        {
            this(null);
        }

        public ActionShorten(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift I"));
            _tempStr = resHelper.getString("ActionShorten.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShorten.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShorten.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionShorten", "actionShorten_actionPerformed", e);
        }
    }

    /**
     * output ActionViewPayPlan class
     */     
    protected class ActionViewPayPlan extends ItemAction {     
    
        public ActionViewPayPlan()
        {
            this(null);
        }

        public ActionViewPayPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift J"));
            _tempStr = resHelper.getString("ActionViewPayPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionViewPayPlan", "actionViewPayPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayAll class
     */     
    protected class ActionDisplayAll extends ItemAction {     
    
        public ActionDisplayAll()
        {
            this(null);
        }

        public ActionDisplayAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionDisplayAll", "actionDisplayAll_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayConNoText class
     */     
    protected class ActionDisplayConNoText extends ItemAction {     
    
        public ActionDisplayConNoText()
        {
            this(null);
        }

        public ActionDisplayConNoText(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayConNoText.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayConNoText.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayConNoText.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionDisplayConNoText", "actionDisplayConNoText_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayContract class
     */     
    protected class ActionDisplayContract extends ItemAction {     
    
        public ActionDisplayContract()
        {
            this(null);
        }

        public ActionDisplayContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillExecuteUI.this, "ActionDisplayContract", "actionDisplayContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractBillExecuteUI");
    }




}