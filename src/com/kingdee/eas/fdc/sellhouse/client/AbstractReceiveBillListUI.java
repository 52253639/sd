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
public abstract class AbstractReceiveBillListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractReceiveBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearInvoice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCreateInvoice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClearInvoice;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRec;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRec;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBillPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBillPrintView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBillPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBillPrintView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchRec;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchRec;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefundment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem mentItemRefundment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchSett;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuBatchSett;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjust;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdjust;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelRec;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelRec;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceipt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRetakeReceipt;
    protected ActionCreateInvoice actionCreateInvoice = null;
    protected actionClearInvoice actionClearInvoice = null;
    protected ActionRec actionRec = null;
    protected ActionAudit actionAudit = null;
    protected ActionReceiveBillPrint actionReceiveBillPrint = null;
    protected ActionReceiveBillPrintView actionReceiveBillPrintView = null;
    protected ActionViewBill actionViewBill = null;
    protected ActionBatchRec actionBatchRec = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionRefundment actionRefundment = null;
    protected ActionBatchSett actionBatchSett = null;
    protected ActionAdjust actionAdjust = null;
    protected ActionCancelRec actionCancelRec = null;
    protected ActionReceipt actionReceipt = null;
    protected ActionRetakeReceipt actionRetakeReceipt = null;
    /**
     * output class constructor
     */
    public AbstractReceiveBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractReceiveBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "FDCReceiveBillQuery");
        //actionCreateInvoice
        this.actionCreateInvoice = new ActionCreateInvoice(this);
        getActionManager().registerAction("actionCreateInvoice", actionCreateInvoice);
         this.actionCreateInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearInvoice
        this.actionClearInvoice = new actionClearInvoice(this);
        getActionManager().registerAction("actionClearInvoice", actionClearInvoice);
         this.actionClearInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRec
        this.actionRec = new ActionRec(this);
        getActionManager().registerAction("actionRec", actionRec);
         this.actionRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBillPrint
        this.actionReceiveBillPrint = new ActionReceiveBillPrint(this);
        getActionManager().registerAction("actionReceiveBillPrint", actionReceiveBillPrint);
         this.actionReceiveBillPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBillPrintView
        this.actionReceiveBillPrintView = new ActionReceiveBillPrintView(this);
        getActionManager().registerAction("actionReceiveBillPrintView", actionReceiveBillPrintView);
         this.actionReceiveBillPrintView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBill
        this.actionViewBill = new ActionViewBill(this);
        getActionManager().registerAction("actionViewBill", actionViewBill);
         this.actionViewBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchRec
        this.actionBatchRec = new ActionBatchRec(this);
        getActionManager().registerAction("actionBatchRec", actionBatchRec);
         this.actionBatchRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefundment
        this.actionRefundment = new ActionRefundment(this);
        getActionManager().registerAction("actionRefundment", actionRefundment);
         this.actionRefundment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchSett
        this.actionBatchSett = new ActionBatchSett(this);
        getActionManager().registerAction("actionBatchSett", actionBatchSett);
         this.actionBatchSett.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAdjust
        this.actionAdjust = new ActionAdjust(this);
        getActionManager().registerAction("actionAdjust", actionAdjust);
         this.actionAdjust.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelRec
        this.actionCancelRec = new ActionCancelRec(this);
        getActionManager().registerAction("actionCancelRec", actionCancelRec);
         this.actionCancelRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceipt
        this.actionReceipt = new ActionReceipt(this);
        getActionManager().registerAction("actionReceipt", actionReceipt);
         this.actionReceipt.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRetakeReceipt
        this.actionRetakeReceipt = new ActionRetakeReceipt(this);
        getActionManager().registerAction("actionRetakeReceipt", actionRetakeReceipt);
         this.actionRetakeReceipt.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnCreateInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCreateInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClearInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.btnRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRec = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnReceiveBillPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceiveBillPrintView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemReceiveBillPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBillPrintView = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnBatchRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatchRec = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefundment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.mentItemRefundment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnBatchSett = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuBatchSett = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAdjust = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnCancelRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCancelRec = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnReceipt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRetakeReceipt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreateInvoice.setName("btnCreateInvoice");
        this.btnClearInvoice.setName("btnClearInvoice");
        this.menuItemCreateInvoice.setName("menuItemCreateInvoice");
        this.menuItemClearInvoice.setName("menuItemClearInvoice");
        this.pnlMain.setName("pnlMain");
        this.treeMain.setName("treeMain");
        this.treeView.setName("treeView");
        this.btnRec.setName("btnRec");
        this.btnAudit.setName("btnAudit");
        this.menuItemRec.setName("menuItemRec");
        this.menuItemAudit.setName("menuItemAudit");
        this.btnReceiveBillPrint.setName("btnReceiveBillPrint");
        this.btnReceiveBillPrintView.setName("btnReceiveBillPrintView");
        this.menuItemReceiveBillPrint.setName("menuItemReceiveBillPrint");
        this.menuItemReceiveBillPrintView.setName("menuItemReceiveBillPrintView");
        this.btnViewBill.setName("btnViewBill");
        this.menuItemViewBill.setName("menuItemViewBill");
        this.btnBatchRec.setName("btnBatchRec");
        this.menuItemBatchRec.setName("menuItemBatchRec");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnRefundment.setName("btnRefundment");
        this.mentItemRefundment.setName("mentItemRefundment");
        this.btnBatchSett.setName("btnBatchSett");
        this.menuBatchSett.setName("menuBatchSett");
        this.btnAdjust.setName("btnAdjust");
        this.menuItemAdjust.setName("menuItemAdjust");
        this.btnCancelRec.setName("btnCancelRec");
        this.menuItemCancelRec.setName("menuItemCancelRec");
        this.btnReceipt.setName("btnReceipt");
        this.btnRetakeReceipt.setName("btnRetakeReceipt");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"receiptState\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"billType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"isVourcher\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"building.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"room.unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"room.roomPropNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"purchase.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"tenancyContract.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"moneyDefine.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"ReceiveAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"payerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"invoice.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"invoice.amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"settlementType\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"fdcId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"tenancyAdviser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{receiptState}</t:Cell><t:Cell>$Resource{billType}</t:Cell><t:Cell>$Resource{isVourcher}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{building.name}</t:Cell><t:Cell>$Resource{room.unit}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{room.roomPropNo}</t:Cell><t:Cell>$Resource{purchase.number}</t:Cell><t:Cell>$Resource{tenancyContract.number}</t:Cell><t:Cell>$Resource{moneyDefine.name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{ReceiveAccount}</t:Cell><t:Cell>$Resource{payerName}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell><t:Cell>$Resource{invoice.number}</t:Cell><t:Cell>$Resource{invoice.amount}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{fdcId}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{tenancyAdviser.name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","billStatus","fdcReceiveBill.receiptState","fdcReceiveBill.billTypeEnum","fiVouchered","number","bizDate","building.name","room.unit","room.number","room.roomNo","","purchase.number","tenancyContract.number","moneyDefine.name","fdcReceiveBillEntry.amount","revAccountBank.bankAccountNumber","payerName","fdcReceiveBill.receiptNumber","invoice.number","invoice.amount","settlementType.name","fdcReceiveBill.id","salesman.name","tenancyAdviser.name","description","creator.name","createTime"});

		
        this.btnAttachment.setVisible(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnCreateTo.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        // btnCreateInvoice
        this.btnCreateInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateInvoice.setText(resHelper.getString("btnCreateInvoice.text"));		
        this.btnCreateInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));
        // btnClearInvoice
        this.btnClearInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearInvoice.setText(resHelper.getString("btnClearInvoice.text"));		
        this.btnClearInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_clear"));
        // menuItemCreateInvoice
        this.menuItemCreateInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCreateInvoice.setText(resHelper.getString("menuItemCreateInvoice.text"));		
        this.menuItemCreateInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));		
        this.menuItemCreateInvoice.setToolTipText(resHelper.getString("menuItemCreateInvoice.toolTipText"));
        // menuItemClearInvoice
        this.menuItemClearInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClearInvoice.setText(resHelper.getString("menuItemClearInvoice.text"));		
        this.menuItemClearInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_clear"));		
        this.menuItemClearInvoice.setToolTipText(resHelper.getString("menuItemClearInvoice.toolTipText"));
        // pnlMain		
        this.pnlMain.setDividerLocation(200);
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
        // treeView
        // btnRec
        this.btnRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRec.setText(resHelper.getString("btnRec.text"));		
        this.btnRec.setToolTipText(resHelper.getString("btnRec.toolTipText"));		
        this.btnRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemRec
        this.menuItemRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRec.setText(resHelper.getString("menuItemRec.text"));		
        this.menuItemRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));		
        this.menuItemRec.setToolTipText(resHelper.getString("menuItemRec.toolTipText"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        // btnReceiveBillPrint
        this.btnReceiveBillPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBillPrint.setText(resHelper.getString("btnReceiveBillPrint.text"));		
        this.btnReceiveBillPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnReceiveBillPrintView
        this.btnReceiveBillPrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBillPrintView.setText(resHelper.getString("btnReceiveBillPrintView.text"));		
        this.btnReceiveBillPrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // menuItemReceiveBillPrint
        this.menuItemReceiveBillPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBillPrint.setText(resHelper.getString("menuItemReceiveBillPrint.text"));		
        this.menuItemReceiveBillPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // menuItemReceiveBillPrintView
        this.menuItemReceiveBillPrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBillPrintView.setText(resHelper.getString("menuItemReceiveBillPrintView.text"));		
        this.menuItemReceiveBillPrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // btnViewBill
        this.btnViewBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBill.setText(resHelper.getString("btnViewBill.text"));		
        this.btnViewBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_demandcollateresult"));
        // menuItemViewBill
        this.menuItemViewBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBill.setText(resHelper.getString("menuItemViewBill.text"));		
        this.menuItemViewBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_demandcollateresult"));
        // btnBatchRec
        this.btnBatchRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchRec.setText(resHelper.getString("btnBatchRec.text"));		
        this.btnBatchRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_gathering"));
        // menuItemBatchRec
        this.menuItemBatchRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchRec.setText(resHelper.getString("menuItemBatchRec.text"));		
        this.menuItemBatchRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_gathering"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnRefundment
        this.btnRefundment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefundment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefundment.setText(resHelper.getString("btnRefundment.text"));		
        this.btnRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_nocollate"));
        // mentItemRefundment
        this.mentItemRefundment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefundment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.mentItemRefundment.setText(resHelper.getString("mentItemRefundment.text"));		
        this.mentItemRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_nocollate"));
        // btnBatchSett
        this.btnBatchSett.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSett, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchSett.setText(resHelper.getString("btnBatchSett.text"));		
        this.btnBatchSett.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // menuBatchSett
        this.menuBatchSett.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSett, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuBatchSett.setText(resHelper.getString("menuBatchSett.text"));		
        this.menuBatchSett.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // btnAdjust
        this.btnAdjust.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjust, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjust.setText(resHelper.getString("btnAdjust.text"));		
        this.btnAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_budgetadjust"));		
        this.btnAdjust.setToolTipText(resHelper.getString("btnAdjust.toolTipText"));
        // menuItemAdjust		
        this.menuItemAdjust.setText(resHelper.getString("menuItemAdjust.text"));		
        this.menuItemAdjust.setToolTipText(resHelper.getString("menuItemAdjust.toolTipText"));		
        this.menuItemAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_budgetadjust"));
        // btnCancelRec
        this.btnCancelRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelRec.setText(resHelper.getString("btnCancelRec.text"));		
        this.btnCancelRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_fclose"));
        // menuItemCancelRec
        this.menuItemCancelRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelRec.setText(resHelper.getString("menuItemCancelRec.text"));		
        this.menuItemCancelRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_fclose"));
        // btnReceipt
        this.btnReceipt.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceipt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceipt.setText(resHelper.getString("btnReceipt.text"));		
        this.btnReceipt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_replace"));
        // btnRetakeReceipt
        this.btnRetakeReceipt.setAction((IItemAction)ActionProxyFactory.getProxy(actionRetakeReceipt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRetakeReceipt.setText(resHelper.getString("btnRetakeReceipt.text"));		
        this.btnRetakeReceipt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelwriteatback"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 582));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 582, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCreateInvoice);
        menuBiz.add(menuItemClearInvoice);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemRec);
        menuBiz.add(menuItemCancelRec);
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemViewBill);
        menuBiz.add(menuItemReceiveBillPrint);
        menuBiz.add(menuItemReceiveBillPrintView);
        menuBiz.add(menuItemBatchRec);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(mentItemRefundment);
        menuBiz.add(menuBatchSett);
        menuBiz.add(menuItemAdjust);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnReceipt);
        this.toolBar.add(btnRetakeReceipt);
        this.toolBar.add(btnCreateInvoice);
        this.toolBar.add(btnClearInvoice);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnRec);
        this.toolBar.add(btnCancelRec);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnViewBill);
        this.toolBar.add(btnReceiveBillPrint);
        this.toolBar.add(btnReceiveBillPrintView);
        this.toolBar.add(btnBatchRec);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnRefundment);
        this.toolBar.add(btnBatchSett);
        this.toolBar.add(btnAdjust);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ReceiveBillListUIHandler";
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("purchase.number"));
        sic.add(new SelectorItemInfo("payerName"));
        sic.add(new SelectorItemInfo("invoice.number"));
        sic.add(new SelectorItemInfo("invoice.amount"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("fdcReceiveBill.receiptNumber"));
        sic.add(new SelectorItemInfo("fdcReceiveBill.id"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("room.unit"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("fdcReceiveBillEntry.amount"));
        sic.add(new SelectorItemInfo("settlementType.name"));
        sic.add(new SelectorItemInfo("tenancyContract.number"));
        sic.add(new SelectorItemInfo("tenancyAdviser.name"));
        sic.add(new SelectorItemInfo("moneyDefine.name"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("fdcReceiveBill.billTypeEnum"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("room.roomNo"));
        sic.add(new SelectorItemInfo("fdcReceiveBill.receiptState"));
        sic.add(new SelectorItemInfo("revAccountBank.bankAccountNumber"));
        return sic;
    }        
    	

    /**
     * output actionCreateInvoice_actionPerformed method
     */
    public void actionCreateInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearInvoice_actionPerformed method
     */
    public void actionClearInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRec_actionPerformed method
     */
    public void actionRec_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBillPrint_actionPerformed method
     */
    public void actionReceiveBillPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBillPrintView_actionPerformed method
     */
    public void actionReceiveBillPrintView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBill_actionPerformed method
     */
    public void actionViewBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchRec_actionPerformed method
     */
    public void actionBatchRec_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefundment_actionPerformed method
     */
    public void actionRefundment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchSett_actionPerformed method
     */
    public void actionBatchSett_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAdjust_actionPerformed method
     */
    public void actionAdjust_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelRec_actionPerformed method
     */
    public void actionCancelRec_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceipt_actionPerformed method
     */
    public void actionReceipt_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRetakeReceipt_actionPerformed method
     */
    public void actionRetakeReceipt_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCreateInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateInvoice() {
    	return false;
    }
	public RequestContext prepareactionClearInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionClearInvoice() {
    	return false;
    }
	public RequestContext prepareActionRec(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRec() {
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
	public RequestContext prepareActionReceiveBillPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBillPrint() {
    	return false;
    }
	public RequestContext prepareActionReceiveBillPrintView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBillPrintView() {
    	return false;
    }
	public RequestContext prepareActionViewBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBill() {
    	return false;
    }
	public RequestContext prepareActionBatchRec(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchRec() {
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
	public RequestContext prepareActionBatchSett(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchSett() {
    	return false;
    }
	public RequestContext prepareActionAdjust(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdjust() {
    	return false;
    }
	public RequestContext prepareActionCancelRec(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelRec() {
    	return false;
    }
	public RequestContext prepareActionReceipt(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceipt() {
    	return false;
    }
	public RequestContext prepareActionRetakeReceipt(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRetakeReceipt() {
    	return false;
    }

    /**
     * output ActionCreateInvoice class
     */     
    protected class ActionCreateInvoice extends ItemAction {     
    
        public ActionCreateInvoice()
        {
            this(null);
        }

        public ActionCreateInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCreateInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionCreateInvoice", "actionCreateInvoice_actionPerformed", e);
        }
    }

    /**
     * output actionClearInvoice class
     */     
    protected class actionClearInvoice extends ItemAction {     
    
        public actionClearInvoice()
        {
            this(null);
        }

        public actionClearInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionClearInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClearInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClearInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "actionClearInvoice", "actionClearInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionRec class
     */     
    protected class ActionRec extends ItemAction {     
    
        public ActionRec()
        {
            this(null);
        }

        public ActionRec(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionRec", "actionRec_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveBillPrint class
     */     
    protected class ActionReceiveBillPrint extends ItemAction {     
    
        public ActionReceiveBillPrint()
        {
            this(null);
        }

        public ActionReceiveBillPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveBillPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionReceiveBillPrint", "actionReceiveBillPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveBillPrintView class
     */     
    protected class ActionReceiveBillPrintView extends ItemAction {     
    
        public ActionReceiveBillPrintView()
        {
            this(null);
        }

        public ActionReceiveBillPrintView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReceiveBillPrintView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrintView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrintView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionReceiveBillPrintView", "actionReceiveBillPrintView_actionPerformed", e);
        }
    }

    /**
     * output ActionViewBill class
     */     
    protected class ActionViewBill extends ItemAction {     
    
        public ActionViewBill()
        {
            this(null);
        }

        public ActionViewBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionViewBill", "actionViewBill_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchRec class
     */     
    protected class ActionBatchRec extends ItemAction {     
    
        public ActionBatchRec()
        {
            this(null);
        }

        public ActionBatchRec(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionBatchRec", "actionBatchRec_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionRefundment", "actionRefundment_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchSett class
     */     
    protected class ActionBatchSett extends ItemAction {     
    
        public ActionBatchSett()
        {
            this(null);
        }

        public ActionBatchSett(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBatchSett.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchSett.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchSett.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionBatchSett", "actionBatchSett_actionPerformed", e);
        }
    }

    /**
     * output ActionAdjust class
     */     
    protected class ActionAdjust extends ItemAction {     
    
        public ActionAdjust()
        {
            this(null);
        }

        public ActionAdjust(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAdjust.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjust.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjust.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionAdjust", "actionAdjust_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelRec class
     */     
    protected class ActionCancelRec extends ItemAction {     
    
        public ActionCancelRec()
        {
            this(null);
        }

        public ActionCancelRec(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionCancelRec", "actionCancelRec_actionPerformed", e);
        }
    }

    /**
     * output ActionReceipt class
     */     
    protected class ActionReceipt extends ItemAction {     
    
        public ActionReceipt()
        {
            this(null);
        }

        public ActionReceipt(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceipt.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceipt.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceipt.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionReceipt", "actionReceipt_actionPerformed", e);
        }
    }

    /**
     * output ActionRetakeReceipt class
     */     
    protected class ActionRetakeReceipt extends ItemAction {     
    
        public ActionRetakeReceipt()
        {
            this(null);
        }

        public ActionRetakeReceipt(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRetakeReceipt.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRetakeReceipt.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRetakeReceipt.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillListUI.this, "ActionRetakeReceipt", "actionRetakeReceipt_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ReceiveBillListUI");
    }




}