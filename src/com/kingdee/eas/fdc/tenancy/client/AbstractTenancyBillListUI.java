/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyBillListUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHandleTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefundment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCarryForward;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFlagAtTerm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSpecial;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRepairStartDate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemHandleTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefundment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCarryForward;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFlagAtTerm;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuSpecial;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContinueTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRejiggerTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuRepairStartDate;
    protected ActionContinueTenancy actionContinueTenancy = null;
    protected ActionRejiggerTenancy actionRejiggerTenancy = null;
    protected ActionChangeName actionChangeName = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionBlankOut actionBlankOut = null;
    protected ActionQuitTenancy actionQuitTenancy = null;
    protected ActionHandleTenancy actionHandleTenancy = null;
    protected ActionReceiveBill actionReceiveBill = null;
    protected ActionRefundment actionRefundment = null;
    protected ActionCarryForward actionCarryForward = null;
    protected ActionFlagAtTerm actionFlagAtTerm = null;
    protected ActionRepairStartDate actionRepairStartDate = null;
    protected ActionPrintBill actionPrintBill = null;
    protected ActionPrintPreviewBill actionPrintPreviewBill = null;
    /**
     * output class constructor
     */
    public AbstractTenancyBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.tenancy.app", "TenancyBillQuery");
        //actionContinueTenancy
        this.actionContinueTenancy = new ActionContinueTenancy(this);
        getActionManager().registerAction("actionContinueTenancy", actionContinueTenancy);
         this.actionContinueTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRejiggerTenancy
        this.actionRejiggerTenancy = new ActionRejiggerTenancy(this);
        getActionManager().registerAction("actionRejiggerTenancy", actionRejiggerTenancy);
         this.actionRejiggerTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeName
        this.actionChangeName = new ActionChangeName(this);
        getActionManager().registerAction("actionChangeName", actionChangeName);
         this.actionChangeName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBlankOut
        this.actionBlankOut = new ActionBlankOut(this);
        getActionManager().registerAction("actionBlankOut", actionBlankOut);
         this.actionBlankOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuitTenancy
        this.actionQuitTenancy = new ActionQuitTenancy(this);
        getActionManager().registerAction("actionQuitTenancy", actionQuitTenancy);
         this.actionQuitTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHandleTenancy
        this.actionHandleTenancy = new ActionHandleTenancy(this);
        getActionManager().registerAction("actionHandleTenancy", actionHandleTenancy);
         this.actionHandleTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBill
        this.actionReceiveBill = new ActionReceiveBill(this);
        getActionManager().registerAction("actionReceiveBill", actionReceiveBill);
         this.actionReceiveBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefundment
        this.actionRefundment = new ActionRefundment(this);
        getActionManager().registerAction("actionRefundment", actionRefundment);
         this.actionRefundment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCarryForward
        this.actionCarryForward = new ActionCarryForward(this);
        getActionManager().registerAction("actionCarryForward", actionCarryForward);
         this.actionCarryForward.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFlagAtTerm
        this.actionFlagAtTerm = new ActionFlagAtTerm(this);
        getActionManager().registerAction("actionFlagAtTerm", actionFlagAtTerm);
         this.actionFlagAtTerm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRepairStartDate
        this.actionRepairStartDate = new ActionRepairStartDate(this);
        getActionManager().registerAction("actionRepairStartDate", actionRepairStartDate);
         this.actionRepairStartDate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintBill
        this.actionPrintBill = new ActionPrintBill(this);
        getActionManager().registerAction("actionPrintBill", actionPrintBill);
         this.actionPrintBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintPreviewBill
        this.actionPrintPreviewBill = new ActionPrintPreviewBill(this);
        getActionManager().registerAction("actionPrintPreviewBill", actionPrintPreviewBill);
         this.actionPrintPreviewBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBlankOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHandleTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceiveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefundment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCarryForward = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFlagAtTerm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSpecial = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRepairStartDate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBlankOut = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemHandleTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRefundment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCarryForward = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFlagAtTerm = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSpecial = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemContinueTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRejiggerTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuRepairStartDate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnBlankOut.setName("btnBlankOut");
        this.btnHandleTenancy.setName("btnHandleTenancy");
        this.btnReceiveBill.setName("btnReceiveBill");
        this.btnRefundment.setName("btnRefundment");
        this.btnCarryForward.setName("btnCarryForward");
        this.btnFlagAtTerm.setName("btnFlagAtTerm");
        this.btnSpecial.setName("btnSpecial");
        this.btnRepairStartDate.setName("btnRepairStartDate");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemBlankOut.setName("menuItemBlankOut");
        this.menuItemHandleTenancy.setName("menuItemHandleTenancy");
        this.menuItemReceiveBill.setName("menuItemReceiveBill");
        this.menuItemRefundment.setName("menuItemRefundment");
        this.menuItemCarryForward.setName("menuItemCarryForward");
        this.menuItemFlagAtTerm.setName("menuItemFlagAtTerm");
        this.menuSpecial.setName("menuSpecial");
        this.menuItemContinueTenancy.setName("menuItemContinueTenancy");
        this.menuItemRejiggerTenancy.setName("menuItemRejiggerTenancy");
        this.menuItemChangeName.setName("menuItemChangeName");
        this.menuItemQuitTenancy.setName("menuItemQuitTenancy");
        this.menuRepairStartDate.setName("menuRepairStartDate");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"tenancyState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"tenancyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"tenRoomsDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"tenAttachesDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"tenCustomerDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"tenancyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"oldTenancyBill.tenancyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"leaseCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"leaseTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"flagAtTerm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"tenancyAdviser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"agency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"dealTotalRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"standardTotalRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"depositAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"firstPayRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"deliveryRoomDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"specialClause\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol25\" /><t:Column t:key=\"submitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{tenancyState}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{tenancyName}</t:Cell><t:Cell>$Resource{tenRoomsDes}</t:Cell><t:Cell>$Resource{tenAttachesDes}</t:Cell><t:Cell>$Resource{tenCustomerDes}</t:Cell><t:Cell>$Resource{tenancyType}</t:Cell><t:Cell>$Resource{oldTenancyBill.tenancyName}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{leaseCount}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{leaseTime}</t:Cell><t:Cell>$Resource{flagAtTerm}</t:Cell><t:Cell>$Resource{tenancyAdviser.name}</t:Cell><t:Cell>$Resource{agency.name}</t:Cell><t:Cell>$Resource{dealTotalRent}</t:Cell><t:Cell>$Resource{standardTotalRent}</t:Cell><t:Cell>$Resource{depositAmount}</t:Cell><t:Cell>$Resource{firstPayRent}</t:Cell><t:Cell>$Resource{deliveryRoomDate}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{specialClause}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{submitDate}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","tenancyState","number","tenancyName","tenRoomsDes","tenAttachesDes","tenCustomerDes","tenancyType","oldTenancyBill.tenancyName","startDate","leaseCount","endDate","leaseTime","flagAtTerm","tenancyAdviser.name","agency.name","dealTotalRent","standardTotalRent","depositAmount","firstPayRent","deliveryRoomDate","description","specialClause","creator.name","createTime","state","lastUpdateTime","auditTime"});


        // pnlMain		
        this.pnlMain.setDividerLocation(200);
        // treeView
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnBlankOut
        this.btnBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBlankOut.setText(resHelper.getString("btnBlankOut.text"));		
        this.btnBlankOut.setToolTipText(resHelper.getString("btnBlankOut.toolTipText"));		
        this.btnBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // btnHandleTenancy
        this.btnHandleTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHandleTenancy.setText(resHelper.getString("btnHandleTenancy.text"));		
        this.btnHandleTenancy.setToolTipText(resHelper.getString("btnHandleTenancy.toolTipText"));		
        this.btnHandleTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_purviewlookupuser"));
        // btnReceiveBill
        this.btnReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBill.setText(resHelper.getString("btnReceiveBill.text"));		
        this.btnReceiveBill.setToolTipText(resHelper.getString("btnReceiveBill.toolTipText"));		
        this.btnReceiveBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // btnRefundment
        this.btnRefundment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefundment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefundment.setText(resHelper.getString("btnRefundment.text"));		
        this.btnRefundment.setToolTipText(resHelper.getString("btnRefundment.toolTipText"));		
        this.btnRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refuse"));
        // btnCarryForward
        this.btnCarryForward.setAction((IItemAction)ActionProxyFactory.getProxy(actionCarryForward, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCarryForward.setText(resHelper.getString("btnCarryForward.text"));		
        this.btnCarryForward.setToolTipText(resHelper.getString("btnCarryForward.toolTipText"));		
        this.btnCarryForward.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // btnFlagAtTerm
        this.btnFlagAtTerm.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlagAtTerm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFlagAtTerm.setText(resHelper.getString("btnFlagAtTerm.text"));		
        this.btnFlagAtTerm.setToolTipText(resHelper.getString("btnFlagAtTerm.toolTipText"));
        // btnSpecial		
        this.btnSpecial.setText(resHelper.getString("btnSpecial.text"));		
        this.btnSpecial.setToolTipText(resHelper.getString("btnSpecial.toolTipText"));		
        this.btnSpecial.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_disassemble"));
        // btnRepairStartDate
        this.btnRepairStartDate.setAction((IItemAction)ActionProxyFactory.getProxy(actionRepairStartDate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRepairStartDate.setText(resHelper.getString("btnRepairStartDate.text"));		
        this.btnRepairStartDate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_reset"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));		
        this.menuItemUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuItemBlankOut
        this.menuItemBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBlankOut.setText(resHelper.getString("menuItemBlankOut.text"));		
        this.menuItemBlankOut.setToolTipText(resHelper.getString("menuItemBlankOut.toolTipText"));		
        this.menuItemBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // menuItemHandleTenancy
        this.menuItemHandleTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHandleTenancy.setText(resHelper.getString("menuItemHandleTenancy.text"));		
        this.menuItemHandleTenancy.setToolTipText(resHelper.getString("menuItemHandleTenancy.toolTipText"));		
        this.menuItemHandleTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_purviewlookupuser"));
        // menuItemReceiveBill
        this.menuItemReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBill.setText(resHelper.getString("menuItemReceiveBill.text"));		
        this.menuItemReceiveBill.setToolTipText(resHelper.getString("menuItemReceiveBill.toolTipText"));		
        this.menuItemReceiveBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // menuItemRefundment
        this.menuItemRefundment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefundment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefundment.setText(resHelper.getString("menuItemRefundment.text"));		
        this.menuItemRefundment.setToolTipText(resHelper.getString("menuItemRefundment.toolTipText"));		
        this.menuItemRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refuse"));
        // menuItemCarryForward
        this.menuItemCarryForward.setAction((IItemAction)ActionProxyFactory.getProxy(actionCarryForward, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCarryForward.setText(resHelper.getString("menuItemCarryForward.text"));		
        this.menuItemCarryForward.setToolTipText(resHelper.getString("menuItemCarryForward.toolTipText"));		
        this.menuItemCarryForward.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // menuItemFlagAtTerm
        this.menuItemFlagAtTerm.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlagAtTerm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFlagAtTerm.setText(resHelper.getString("menuItemFlagAtTerm.text"));		
        this.menuItemFlagAtTerm.setToolTipText(resHelper.getString("menuItemFlagAtTerm.toolTipText"));
        // menuSpecial		
        this.menuSpecial.setText(resHelper.getString("menuSpecial.text"));		
        this.menuSpecial.setToolTipText(resHelper.getString("menuSpecial.toolTipText"));
        // menuItemContinueTenancy
        this.menuItemContinueTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionContinueTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContinueTenancy.setText(resHelper.getString("menuItemContinueTenancy.text"));		
        this.menuItemContinueTenancy.setToolTipText(resHelper.getString("menuItemContinueTenancy.toolTipText"));		
        this.menuItemContinueTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_releasebymdanduser"));
        // menuItemRejiggerTenancy
        this.menuItemRejiggerTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionRejiggerTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRejiggerTenancy.setText(resHelper.getString("menuItemRejiggerTenancy.text"));		
        this.menuItemRejiggerTenancy.setToolTipText(resHelper.getString("menuItemRejiggerTenancy.toolTipText"));		
        this.menuItemRejiggerTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
        // menuItemChangeName
        this.menuItemChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeName.setText(resHelper.getString("menuItemChangeName.text"));		
        this.menuItemChangeName.setToolTipText(resHelper.getString("menuItemChangeName.toolTipText"));		
        this.menuItemChangeName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_persondistribute"));
        // menuItemQuitTenancy
        this.menuItemQuitTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuitTenancy.setText(resHelper.getString("menuItemQuitTenancy.text"));		
        this.menuItemQuitTenancy.setToolTipText(resHelper.getString("menuItemQuitTenancy.toolTipText"));		
        this.menuItemQuitTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_logoutuser"));
        // menuRepairStartDate
        this.menuRepairStartDate.setAction((IItemAction)ActionProxyFactory.getProxy(actionRepairStartDate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuRepairStartDate.setText(resHelper.getString("menuRepairStartDate.text"));		
        this.menuRepairStartDate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_reset"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 992, 586));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 992, 586, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        this.menuBar.add(menuWorkFlow);
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
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemBlankOut);
        menuBiz.add(menuItemHandleTenancy);
        menuBiz.add(menuItemReceiveBill);
        menuBiz.add(menuItemRefundment);
        menuBiz.add(menuItemCarryForward);
        menuBiz.add(menuItemFlagAtTerm);
        menuBiz.add(menuSpecial);
        menuBiz.add(menuRepairStartDate);
        //menuSpecial
        menuSpecial.add(menuItemContinueTenancy);
        menuSpecial.add(menuItemRejiggerTenancy);
        menuSpecial.add(menuItemChangeName);
        menuSpecial.add(menuItemQuitTenancy);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnBlankOut);
        this.toolBar.add(btnHandleTenancy);
        this.toolBar.add(btnReceiveBill);
        this.toolBar.add(btnRefundment);
        this.toolBar.add(btnCarryForward);
        this.toolBar.add(btnFlagAtTerm);
        this.toolBar.add(btnSpecial);
        this.toolBar.add(btnRepairStartDate);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyBillListUIHandler";
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("tenancyState"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("tenancyName"));
        sic.add(new SelectorItemInfo("tenRoomsDes"));
        sic.add(new SelectorItemInfo("tenAttachesDes"));
        sic.add(new SelectorItemInfo("tenCustomerDes"));
        sic.add(new SelectorItemInfo("tenancyType"));
        sic.add(new SelectorItemInfo("oldTenancyBill.tenancyName"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("leaseCount"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("leaseTime"));
        sic.add(new SelectorItemInfo("flagAtTerm"));
        sic.add(new SelectorItemInfo("tenancyAdviser.name"));
        sic.add(new SelectorItemInfo("agency.name"));
        sic.add(new SelectorItemInfo("dealTotalRent"));
        sic.add(new SelectorItemInfo("standardTotalRent"));
        sic.add(new SelectorItemInfo("depositAmount"));
        sic.add(new SelectorItemInfo("firstPayRent"));
        sic.add(new SelectorItemInfo("deliveryRoomDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("specialClause"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("auditTime"));
        return sic;
    }        
    	

    /**
     * output actionContinueTenancy_actionPerformed method
     */
    public void actionContinueTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRejiggerTenancy_actionPerformed method
     */
    public void actionRejiggerTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeName_actionPerformed method
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBlankOut_actionPerformed method
     */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuitTenancy_actionPerformed method
     */
    public void actionQuitTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHandleTenancy_actionPerformed method
     */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBill_actionPerformed method
     */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefundment_actionPerformed method
     */
    public void actionRefundment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCarryForward_actionPerformed method
     */
    public void actionCarryForward_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFlagAtTerm_actionPerformed method
     */
    public void actionFlagAtTerm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRepairStartDate_actionPerformed method
     */
    public void actionRepairStartDate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintBill_actionPerformed method
     */
    public void actionPrintBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintPreviewBill_actionPerformed method
     */
    public void actionPrintPreviewBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionContinueTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContinueTenancy() {
    	return false;
    }
	public RequestContext prepareActionRejiggerTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRejiggerTenancy() {
    	return false;
    }
	public RequestContext prepareActionChangeName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeName() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionBlankOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBlankOut() {
    	return false;
    }
	public RequestContext prepareActionQuitTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitTenancy() {
    	return false;
    }
	public RequestContext prepareActionHandleTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHandleTenancy() {
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
	public RequestContext prepareActionRefundment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefundment() {
    	return false;
    }
	public RequestContext prepareActionCarryForward(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCarryForward() {
    	return false;
    }
	public RequestContext prepareActionFlagAtTerm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFlagAtTerm() {
    	return false;
    }
	public RequestContext prepareActionRepairStartDate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRepairStartDate() {
    	return false;
    }
	public RequestContext prepareActionPrintBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintBill() {
    	return false;
    }
	public RequestContext prepareActionPrintPreviewBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreviewBill() {
    	return false;
    }

    /**
     * output ActionContinueTenancy class
     */     
    protected class ActionContinueTenancy extends ItemAction {     
    
        public ActionContinueTenancy()
        {
            this(null);
        }

        public ActionContinueTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionContinueTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContinueTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContinueTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionContinueTenancy", "actionContinueTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionRejiggerTenancy class
     */     
    protected class ActionRejiggerTenancy extends ItemAction {     
    
        public ActionRejiggerTenancy()
        {
            this(null);
        }

        public ActionRejiggerTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRejiggerTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRejiggerTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRejiggerTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionRejiggerTenancy", "actionRejiggerTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeName class
     */     
    protected class ActionChangeName extends ItemAction {     
    
        public ActionChangeName()
        {
            this(null);
        }

        public ActionChangeName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionChangeName", "actionChangeName_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionBlankOut class
     */     
    protected class ActionBlankOut extends ItemAction {     
    
        public ActionBlankOut()
        {
            this(null);
        }

        public ActionBlankOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBlankOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionBlankOut", "actionBlankOut_actionPerformed", e);
        }
    }

    /**
     * output ActionQuitTenancy class
     */     
    protected class ActionQuitTenancy extends ItemAction {     
    
        public ActionQuitTenancy()
        {
            this(null);
        }

        public ActionQuitTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionQuitTenancy", "actionQuitTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionHandleTenancy class
     */     
    protected class ActionHandleTenancy extends ItemAction {     
    
        public ActionHandleTenancy()
        {
            this(null);
        }

        public ActionHandleTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHandleTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHandleTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHandleTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionHandleTenancy", "actionHandleTenancy_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionReceiveBill", "actionReceiveBill_actionPerformed", e);
        }
    }

    /**
     * output ActionRefundment class
     */     
    protected class ActionRefundment extends ItemAction {     
    
        public ActionRefundment()
        {
            this(null);
        }

        public ActionRefundment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRefundment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefundment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefundment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionRefundment", "actionRefundment_actionPerformed", e);
        }
    }

    /**
     * output ActionCarryForward class
     */     
    protected class ActionCarryForward extends ItemAction {     
    
        public ActionCarryForward()
        {
            this(null);
        }

        public ActionCarryForward(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCarryForward.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCarryForward.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCarryForward.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionCarryForward", "actionCarryForward_actionPerformed", e);
        }
    }

    /**
     * output ActionFlagAtTerm class
     */     
    protected class ActionFlagAtTerm extends ItemAction {     
    
        public ActionFlagAtTerm()
        {
            this(null);
        }

        public ActionFlagAtTerm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFlagAtTerm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlagAtTerm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlagAtTerm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionFlagAtTerm", "actionFlagAtTerm_actionPerformed", e);
        }
    }

    /**
     * output ActionRepairStartDate class
     */     
    protected class ActionRepairStartDate extends ItemAction {     
    
        public ActionRepairStartDate()
        {
            this(null);
        }

        public ActionRepairStartDate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRepairStartDate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRepairStartDate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRepairStartDate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionRepairStartDate", "actionRepairStartDate_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintBill class
     */     
    protected class ActionPrintBill extends ItemAction {     
    
        public ActionPrintBill()
        {
            this(null);
        }

        public ActionPrintBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrintBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionPrintBill", "actionPrintBill_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintPreviewBill class
     */     
    protected class ActionPrintPreviewBill extends ItemAction {     
    
        public ActionPrintPreviewBill()
        {
            this(null);
        }

        public ActionPrintPreviewBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrintPreviewBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreviewBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreviewBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillListUI.this, "ActionPrintPreviewBill", "actionPrintPreviewBill_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyBillListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}