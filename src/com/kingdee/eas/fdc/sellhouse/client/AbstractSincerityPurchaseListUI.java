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
public abstract class AbstractSincerityPurchaseListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSincerityPurchaseListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRevAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuitNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrePur;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnToPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSignContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSincerPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSincerPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuBatchPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitNumber;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemToPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevAmount;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSincerPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSincerPrintPreview;
    protected ActionQuitNumber actionQuitNumber = null;
    protected ActionToPurchase actionToPurchase = null;
    protected ActionRevAmount actionRevAmount = null;
    protected ActionQuitAmount actionQuitAmount = null;
    protected ActionBatchPurchase actionBatchPurchase = null;
    protected ActionSincerPrint actionSincerPrint = null;
    protected ActionSincerPrintPreview actionSincerPrintPreview = null;
    protected ActionChangeRecord actionChangeRecord = null;
    protected ActionSignContract actionSignContract = null;
    protected ActionPrePur actionPrePur = null;
    /**
     * output class constructor
     */
    public AbstractSincerityPurchaseListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSincerityPurchaseListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SincerityPurchaseQuery");
        //actionQuitNumber
        this.actionQuitNumber = new ActionQuitNumber(this);
        getActionManager().registerAction("actionQuitNumber", actionQuitNumber);
         this.actionQuitNumber.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionToPurchase
        this.actionToPurchase = new ActionToPurchase(this);
        getActionManager().registerAction("actionToPurchase", actionToPurchase);
         this.actionToPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRevAmount
        this.actionRevAmount = new ActionRevAmount(this);
        getActionManager().registerAction("actionRevAmount", actionRevAmount);
         this.actionRevAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuitAmount
        this.actionQuitAmount = new ActionQuitAmount(this);
        getActionManager().registerAction("actionQuitAmount", actionQuitAmount);
         this.actionQuitAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchPurchase
        this.actionBatchPurchase = new ActionBatchPurchase(this);
        getActionManager().registerAction("actionBatchPurchase", actionBatchPurchase);
         this.actionBatchPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSincerPrint
        this.actionSincerPrint = new ActionSincerPrint(this);
        getActionManager().registerAction("actionSincerPrint", actionSincerPrint);
         this.actionSincerPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSincerPrintPreview
        this.actionSincerPrintPreview = new ActionSincerPrintPreview(this);
        getActionManager().registerAction("actionSincerPrintPreview", actionSincerPrintPreview);
         this.actionSincerPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeRecord
        this.actionChangeRecord = new ActionChangeRecord(this);
        getActionManager().registerAction("actionChangeRecord", actionChangeRecord);
         this.actionChangeRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSignContract
        this.actionSignContract = new ActionSignContract(this);
        getActionManager().registerAction("actionSignContract", actionSignContract);
         this.actionSignContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrePur
        this.actionPrePur = new ActionPrePur(this);
        getActionManager().registerAction("actionPrePur", actionPrePur);
         this.actionPrePur.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnQuitAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRevAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuitNumber = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrePur = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnToPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSignContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSincerPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSincerPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuBatchPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitNumber = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemToPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevAmount = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitAmount = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSincerPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSincerPrintPreview = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnQuitAmount.setName("btnQuitAmount");
        this.btnRevAmount.setName("btnRevAmount");
        this.btnQuitNumber.setName("btnQuitNumber");
        this.btnPrePur.setName("btnPrePur");
        this.btnToPurchase.setName("btnToPurchase");
        this.btnSignContract.setName("btnSignContract");
        this.btnChangeRecord.setName("btnChangeRecord");
        this.btnBatchPurchase.setName("btnBatchPurchase");
        this.btnSincerPrint.setName("btnSincerPrint");
        this.btnSincerPrintPreview.setName("btnSincerPrintPreview");
        this.menuBatchPurchase.setName("menuBatchPurchase");
        this.menuItemQuitNumber.setName("menuItemQuitNumber");
        this.menuItemToPurchase.setName("menuItemToPurchase");
        this.menuItemRevAmount.setName("menuItemRevAmount");
        this.menuItemQuitAmount.setName("menuItemQuitAmount");
        this.menuSincerPrint.setName("menuSincerPrint");
        this.menuSincerPrintPreview.setName("menuSincerPrintPreview");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sincerityState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"appointmentPeople\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"projectNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"validDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"salesMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"pur.bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pur.busAdscriptionDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sign.bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sign.busAdscriptionDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{sincerityState}</t:Cell><t:Cell>$Resource{customer.name}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{appointmentPeople}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{projectNum}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{validDate}</t:Cell><t:Cell>$Resource{salesMan}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{pur.bizDate}</t:Cell><t:Cell>$Resource{pur.busAdscriptionDate}</t:Cell><t:Cell>$Resource{sign.bizDate}</t:Cell><t:Cell>$Resource{sign.busAdscriptionDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","bizState","customerNames","room.name","appointmentPeople","appointmentPhone","projectNum","bizDate","invalidationDate","saleManStr","description","pur.bizDate","pur.busAdscriptionDate","sign.bizDate","sign.busAdscriptionDate"});

		
        this.btnLocate.setEnabled(false);		
        this.btnLocate.setVisible(false);
        // btnQuitAmount
        this.btnQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuitAmount.setText(resHelper.getString("btnQuitAmount.text"));		
        this.btnQuitAmount.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelwriteatback"));		
        this.btnQuitAmount.setEnabled(false);		
        this.btnQuitAmount.setVisible(false);
        // btnRevAmount
        this.btnRevAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRevAmount.setText(resHelper.getString("btnRevAmount.text"));		
        this.btnRevAmount.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // btnQuitNumber
        this.btnQuitNumber.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitNumber, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuitNumber.setText(resHelper.getString("btnQuitNumber.text"));		
        this.btnQuitNumber.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_quit"));
        // btnPrePur
        this.btnPrePur.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePur, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrePur.setText(resHelper.getString("btnPrePur.text"));		
        this.btnPrePur.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_backwriting"));
        // btnToPurchase
        this.btnToPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionToPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnToPurchase.setText(resHelper.getString("btnToPurchase.text"));		
        this.btnToPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_conversionsave"));
        // btnSignContract
        this.btnSignContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionSignContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSignContract.setText(resHelper.getString("btnSignContract.text"));		
        this.btnSignContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));
        // btnChangeRecord
        this.btnChangeRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeRecord.setText(resHelper.getString("btnChangeRecord.text"));		
        this.btnChangeRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_rename"));
        // btnBatchPurchase
        this.btnBatchPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchPurchase.setText(resHelper.getString("btnBatchPurchase.text"));		
        this.btnBatchPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // btnSincerPrint
        this.btnSincerPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSincerPrint.setText(resHelper.getString("btnSincerPrint.text"));		
        this.btnSincerPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnSincerPrintPreview
        this.btnSincerPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSincerPrintPreview.setText(resHelper.getString("btnSincerPrintPreview.text"));		
        this.btnSincerPrintPreview.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // menuBatchPurchase
        this.menuBatchPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuBatchPurchase.setText(resHelper.getString("menuBatchPurchase.text"));		
        this.menuBatchPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));		
        this.menuBatchPurchase.setVisible(false);
        // menuItemQuitNumber
        this.menuItemQuitNumber.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitNumber, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuitNumber.setText(resHelper.getString("menuItemQuitNumber.text"));		
        this.menuItemQuitNumber.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_quit"));
        // menuItemToPurchase
        this.menuItemToPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionToPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemToPurchase.setText(resHelper.getString("menuItemToPurchase.text"));		
        this.menuItemToPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_conversionsave"));
        // menuItemRevAmount
        this.menuItemRevAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRevAmount.setText(resHelper.getString("menuItemRevAmount.text"));		
        this.menuItemRevAmount.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // menuItemQuitAmount
        this.menuItemQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuitAmount.setText(resHelper.getString("menuItemQuitAmount.text"));		
        this.menuItemQuitAmount.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelwriteatback"));		
        this.menuItemQuitAmount.setEnabled(false);		
        this.menuItemQuitAmount.setVisible(false);
        // menuSincerPrint
        this.menuSincerPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSincerPrint.setText(resHelper.getString("menuSincerPrint.text"));		
        this.menuSincerPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));		
        this.menuSincerPrint.setVisible(false);
        // menuSincerPrintPreview
        this.menuSincerPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSincerPrintPreview.setText(resHelper.getString("menuSincerPrintPreview.text"));		
        this.menuSincerPrintPreview.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));		
        this.menuSincerPrintPreview.setVisible(false);
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
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuBatchPurchase);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
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
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemQuitNumber);
        menuBiz.add(menuItemToPurchase);
        menuBiz.add(menuItemRevAmount);
        menuBiz.add(menuItemQuitAmount);
        menuBiz.add(menuSincerPrint);
        menuBiz.add(menuSincerPrintPreview);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(btnQuitAmount);
        this.toolBar.add(btnRevAmount);
        this.toolBar.add(btnQuitNumber);
        this.toolBar.add(btnPrePur);
        this.toolBar.add(btnToPurchase);
        this.toolBar.add(btnSignContract);
        this.toolBar.add(btnChangeRecord);
        this.toolBar.add(btnBatchPurchase);
        this.toolBar.add(btnSincerPrint);
        this.toolBar.add(btnSincerPrintPreview);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseListUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("room.name"));
        sic.add(new SelectorItemInfo("projectNum"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("invalidationDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bizState"));
        sic.add(new SelectorItemInfo("appointmentPeople"));
        sic.add(new SelectorItemInfo("appointmentPhone"));
        sic.add(new SelectorItemInfo("customerNames"));
        sic.add(new SelectorItemInfo("saleManStr"));
        sic.add(new SelectorItemInfo("pur.bizDate"));
        sic.add(new SelectorItemInfo("pur.busAdscriptionDate"));
        sic.add(new SelectorItemInfo("sign.bizDate"));
        sic.add(new SelectorItemInfo("sign.busAdscriptionDate"));
        return sic;
    }        
    	

    /**
     * output actionQuitNumber_actionPerformed method
     */
    public void actionQuitNumber_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionToPurchase_actionPerformed method
     */
    public void actionToPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRevAmount_actionPerformed method
     */
    public void actionRevAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuitAmount_actionPerformed method
     */
    public void actionQuitAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchPurchase_actionPerformed method
     */
    public void actionBatchPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSincerPrint_actionPerformed method
     */
    public void actionSincerPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSincerPrintPreview_actionPerformed method
     */
    public void actionSincerPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeRecord_actionPerformed method
     */
    public void actionChangeRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSignContract_actionPerformed method
     */
    public void actionSignContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrePur_actionPerformed method
     */
    public void actionPrePur_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionQuitNumber(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitNumber() {
    	return false;
    }
	public RequestContext prepareActionToPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToPurchase() {
    	return false;
    }
	public RequestContext prepareActionRevAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRevAmount() {
    	return false;
    }
	public RequestContext prepareActionQuitAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitAmount() {
    	return false;
    }
	public RequestContext prepareActionBatchPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchPurchase() {
    	return false;
    }
	public RequestContext prepareActionSincerPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSincerPrint() {
    	return false;
    }
	public RequestContext prepareActionSincerPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSincerPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionChangeRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeRecord() {
    	return false;
    }
	public RequestContext prepareActionSignContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSignContract() {
    	return false;
    }
	public RequestContext prepareActionPrePur(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrePur() {
    	return false;
    }

    /**
     * output ActionQuitNumber class
     */     
    protected class ActionQuitNumber extends ItemAction {     
    
        public ActionQuitNumber()
        {
            this(null);
        }

        public ActionQuitNumber(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitNumber.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitNumber.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitNumber.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionQuitNumber", "actionQuitNumber_actionPerformed", e);
        }
    }

    /**
     * output ActionToPurchase class
     */     
    protected class ActionToPurchase extends ItemAction {     
    
        public ActionToPurchase()
        {
            this(null);
        }

        public ActionToPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionToPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionToPurchase", "actionToPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionRevAmount class
     */     
    protected class ActionRevAmount extends ItemAction {     
    
        public ActionRevAmount()
        {
            this(null);
        }

        public ActionRevAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRevAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionRevAmount", "actionRevAmount_actionPerformed", e);
        }
    }

    /**
     * output ActionQuitAmount class
     */     
    protected class ActionQuitAmount extends ItemAction {     
    
        public ActionQuitAmount()
        {
            this(null);
        }

        public ActionQuitAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionQuitAmount", "actionQuitAmount_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchPurchase class
     */     
    protected class ActionBatchPurchase extends ItemAction {     
    
        public ActionBatchPurchase()
        {
            this(null);
        }

        public ActionBatchPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionBatchPurchase", "actionBatchPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionSincerPrint class
     */     
    protected class ActionSincerPrint extends ItemAction {     
    
        public ActionSincerPrint()
        {
            this(null);
        }

        public ActionSincerPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSincerPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionSincerPrint", "actionSincerPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionSincerPrintPreview class
     */     
    protected class ActionSincerPrintPreview extends ItemAction {     
    
        public ActionSincerPrintPreview()
        {
            this(null);
        }

        public ActionSincerPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSincerPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionSincerPrintPreview", "actionSincerPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeRecord class
     */     
    protected class ActionChangeRecord extends ItemAction {     
    
        public ActionChangeRecord()
        {
            this(null);
        }

        public ActionChangeRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionChangeRecord", "actionChangeRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionSignContract class
     */     
    protected class ActionSignContract extends ItemAction {     
    
        public ActionSignContract()
        {
            this(null);
        }

        public ActionSignContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSignContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSignContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSignContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionSignContract", "actionSignContract_actionPerformed", e);
        }
    }

    /**
     * output ActionPrePur class
     */     
    protected class ActionPrePur extends ItemAction {     
    
        public ActionPrePur()
        {
            this(null);
        }

        public ActionPrePur(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrePur.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePur.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePur.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseListUI.this, "ActionPrePur", "actionPrePur_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SincerityPurchaseListUI");
    }




}