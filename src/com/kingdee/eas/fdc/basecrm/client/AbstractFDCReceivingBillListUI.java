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
public abstract class AbstractFDCReceivingBillListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCReceivingBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceipt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRetakeReceipt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceive;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchReceiving;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCanceReceive;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjust;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuReceive;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuBatchReceiving;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuCanceReceive;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdjust;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuReceipt;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuRetakeReceipt;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuCreateInvoice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuClearInvoice;
    protected ActionAudit actionAudit = null;
    protected ActionReceive actionReceive = null;
    protected ActionBatchReceiving actionBatchReceiving = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionCanceReceive actionCanceReceive = null;
    protected ActionAdjust actionAdjust = null;
    protected ActionRetakeReceipt actionRetakeReceipt = null;
    protected ActionReceipt actionReceipt = null;
    protected ActionCreateInvoice actionCreateInvoice = null;
    protected ActionClearInvoice actionClearInvoice = null;
    /**
     * output class constructor
     */
    public AbstractFDCReceivingBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCReceivingBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceive
        this.actionReceive = new ActionReceive(this);
        getActionManager().registerAction("actionReceive", actionReceive);
         this.actionReceive.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchReceiving
        this.actionBatchReceiving = new ActionBatchReceiving(this);
        getActionManager().registerAction("actionBatchReceiving", actionBatchReceiving);
         this.actionBatchReceiving.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCanceReceive
        this.actionCanceReceive = new ActionCanceReceive(this);
        getActionManager().registerAction("actionCanceReceive", actionCanceReceive);
         this.actionCanceReceive.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAdjust
        this.actionAdjust = new ActionAdjust(this);
        getActionManager().registerAction("actionAdjust", actionAdjust);
         this.actionAdjust.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRetakeReceipt
        this.actionRetakeReceipt = new ActionRetakeReceipt(this);
        getActionManager().registerAction("actionRetakeReceipt", actionRetakeReceipt);
         this.actionRetakeReceipt.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceipt
        this.actionReceipt = new ActionReceipt(this);
        getActionManager().registerAction("actionReceipt", actionReceipt);
         this.actionReceipt.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreateInvoice
        this.actionCreateInvoice = new ActionCreateInvoice(this);
        getActionManager().registerAction("actionCreateInvoice", actionCreateInvoice);
         this.actionCreateInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearInvoice
        this.actionClearInvoice = new ActionClearInvoice(this);
        getActionManager().registerAction("actionClearInvoice", actionClearInvoice);
         this.actionClearInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnReceipt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRetakeReceipt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreateInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceive = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchReceiving = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCanceReceive = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuReceive = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuBatchReceiving = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuCanceReceive = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdjust = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuReceipt = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuRetakeReceipt = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuCreateInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuClearInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnReceipt.setName("btnReceipt");
        this.btnRetakeReceipt.setName("btnRetakeReceipt");
        this.btnCreateInvoice.setName("btnCreateInvoice");
        this.btnClearInvoice.setName("btnClearInvoice");
        this.btnAudit.setName("btnAudit");
        this.btnReceive.setName("btnReceive");
        this.btnBatchReceiving.setName("btnBatchReceiving");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnCanceReceive.setName("btnCanceReceive");
        this.btnAdjust.setName("btnAdjust");
        this.menuAudit.setName("menuAudit");
        this.menuReceive.setName("menuReceive");
        this.menuBatchReceiving.setName("menuBatchReceiving");
        this.menuUnAudit.setName("menuUnAudit");
        this.menuCanceReceive.setName("menuCanceReceive");
        this.menuItemAdjust.setName("menuItemAdjust");
        this.menuReceipt.setName("menuReceipt");
        this.menuRetakeReceipt.setName("menuRetakeReceipt");
        this.menuCreateInvoice.setName("menuCreateInvoice");
        this.menuClearInvoice.setName("menuClearInvoice");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"currency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"originalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"billStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"revBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"revBizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"customer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"entries.revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"entries.bankNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"entries.settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"moneyDefine.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"settlementType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"moneyDefine.moneyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"roomLongNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"room.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"fiVouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{billStatus}</t:Cell><t:Cell>$Resource{revBillType}</t:Cell><t:Cell>$Resource{revBizType}</t:Cell><t:Cell>$Resource{customer.name}</t:Cell><t:Cell>$Resource{entries.revAmount}</t:Cell><t:Cell>$Resource{entries.bankNumber}</t:Cell><t:Cell>$Resource{entries.settlementNumber}</t:Cell><t:Cell>$Resource{moneyDefine.name}</t:Cell><t:Cell>$Resource{settlementType.name}</t:Cell><t:Cell>$Resource{moneyDefine.moneyType}</t:Cell><t:Cell>$Resource{room.id}</t:Cell><t:Cell>$Resource{roomLongNumber}</t:Cell><t:Cell>$Resource{room.name}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{fiVouchered}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","",""});


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
        // btnReceipt
        this.btnReceipt.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceipt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceipt.setText(resHelper.getString("btnReceipt.text"));		
        this.btnReceipt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_replace"));
        // btnRetakeReceipt
        this.btnRetakeReceipt.setAction((IItemAction)ActionProxyFactory.getProxy(actionRetakeReceipt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRetakeReceipt.setText(resHelper.getString("btnRetakeReceipt.text"));		
        this.btnRetakeReceipt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelwriteatback"));
        // btnCreateInvoice
        this.btnCreateInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateInvoice.setText(resHelper.getString("btnCreateInvoice.text"));		
        this.btnCreateInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));
        // btnClearInvoice
        this.btnClearInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearInvoice.setText(resHelper.getString("btnClearInvoice.text"));		
        this.btnClearInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_clear"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnReceive
        this.btnReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceive.setText(resHelper.getString("btnReceive.text"));		
        this.btnReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // btnBatchReceiving
        this.btnBatchReceiving.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchReceiving, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchReceiving.setText(resHelper.getString("btnBatchReceiving.text"));		
        this.btnBatchReceiving.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_BatchAdd"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnCanceReceive
        this.btnCanceReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionCanceReceive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCanceReceive.setText(resHelper.getString("btnCanceReceive.text"));		
        this.btnCanceReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelcase"));
        // btnAdjust
        this.btnAdjust.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjust, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjust.setText(resHelper.getString("btnAdjust.text"));		
        this.btnAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_changecontainer"));
        // menuAudit
        this.menuAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAudit.setText(resHelper.getString("menuAudit.text"));		
        this.menuAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuReceive
        this.menuReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuReceive.setText(resHelper.getString("menuReceive.text"));		
        this.menuReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // menuBatchReceiving
        this.menuBatchReceiving.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchReceiving, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuBatchReceiving.setText(resHelper.getString("menuBatchReceiving.text"));		
        this.menuBatchReceiving.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_BatchAdd"));
        // menuUnAudit
        this.menuUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuUnAudit.setText(resHelper.getString("menuUnAudit.text"));		
        this.menuUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuCanceReceive
        this.menuCanceReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionCanceReceive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuCanceReceive.setText(resHelper.getString("menuCanceReceive.text"));		
        this.menuCanceReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelcase"));
        // menuItemAdjust
        this.menuItemAdjust.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjust, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdjust.setText(resHelper.getString("menuItemAdjust.text"));		
        this.menuItemAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_changecontainer"));
        // menuReceipt
        this.menuReceipt.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceipt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuReceipt.setText(resHelper.getString("menuReceipt.text"));		
        this.menuReceipt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_replace"));
        // menuRetakeReceipt
        this.menuRetakeReceipt.setAction((IItemAction)ActionProxyFactory.getProxy(actionRetakeReceipt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuRetakeReceipt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelwriteatback"));		
        this.menuRetakeReceipt.setText(resHelper.getString("menuRetakeReceipt.text"));
        // menuCreateInvoice
        this.menuCreateInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuCreateInvoice.setText(resHelper.getString("menuCreateInvoice.text"));		
        this.menuCreateInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));
        // menuClearInvoice
        this.menuClearInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuClearInvoice.setText(resHelper.getString("menuClearInvoice.text"));		
        this.menuClearInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_clear"));
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
        pnlMain.setBounds(new Rectangle(8, 10, 993, 610));
        this.add(pnlMain, new KDLayout.Constraints(8, 10, 993, 610, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuAudit);
        menuBiz.add(menuReceive);
        menuBiz.add(menuBatchReceiving);
        menuBiz.add(menuUnAudit);
        menuBiz.add(menuCanceReceive);
        menuBiz.add(menuItemAdjust);
        menuBiz.add(menuReceipt);
        menuBiz.add(menuRetakeReceipt);
        menuBiz.add(menuCreateInvoice);
        menuBiz.add(menuClearInvoice);
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
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnReceive);
        this.toolBar.add(btnBatchReceiving);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCanceReceive);
        this.toolBar.add(btnAdjust);
        this.toolBar.add(btnReceipt);
        this.toolBar.add(btnRetakeReceipt);
        this.toolBar.add(btnCreateInvoice);
        this.toolBar.add(btnClearInvoice);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillListUIHandler";
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
        //write your code here
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceive_actionPerformed method
     */
    public void actionReceive_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchReceiving_actionPerformed method
     */
    public void actionBatchReceiving_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCanceReceive_actionPerformed method
     */
    public void actionCanceReceive_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAdjust_actionPerformed method
     */
    public void actionAdjust_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRetakeReceipt_actionPerformed method
     */
    public void actionRetakeReceipt_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceipt_actionPerformed method
     */
    public void actionReceipt_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionBatchReceiving(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchReceiving() {
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
	public RequestContext prepareActionCanceReceive(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCanceReceive() {
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
	public RequestContext prepareActionClearInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearInvoice() {
    	return false;
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionReceive", "actionReceive_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchReceiving class
     */     
    protected class ActionBatchReceiving extends ItemAction {     
    
        public ActionBatchReceiving()
        {
            this(null);
        }

        public ActionBatchReceiving(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBatchReceiving.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchReceiving.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchReceiving.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionBatchReceiving", "actionBatchReceiving_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionCanceReceive class
     */     
    protected class ActionCanceReceive extends ItemAction {     
    
        public ActionCanceReceive()
        {
            this(null);
        }

        public ActionCanceReceive(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCanceReceive.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCanceReceive.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCanceReceive.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionCanceReceive", "actionCanceReceive_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionAdjust", "actionAdjust_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionRetakeReceipt", "actionRetakeReceipt_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionReceipt", "actionReceipt_actionPerformed", e);
        }
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionCreateInvoice", "actionCreateInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionClearInvoice class
     */     
    protected class ActionClearInvoice extends ItemAction {     
    
        public ActionClearInvoice()
        {
            this(null);
        }

        public ActionClearInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClearInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCReceivingBillListUI.this, "ActionClearInvoice", "actionClearInvoice_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "FDCReceivingBillListUI");
    }




}