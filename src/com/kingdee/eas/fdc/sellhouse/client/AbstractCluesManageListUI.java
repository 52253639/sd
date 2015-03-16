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
public abstract class AbstractCluesManageListUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCluesManageListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeliver;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFollow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTrade;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeliver;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFollow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCustomer;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuTrade;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSincerity;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSign;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected ActionShare actionShare = null;
    protected ActionCommerceChance actionCommerceChance = null;
    protected ActionSincerity actionSincerity = null;
    protected ActionImport actionImport = null;
    protected ActionDeliver actionDeliver = null;
    protected ActionQuestionPrint actionQuestionPrint = null;
    protected ActionFollow actionFollow = null;
    protected ActionPrePurchase actionPrePurchase = null;
    protected ActionPurchase actionPurchase = null;
    protected ActionSign actionSign = null;
    protected ActionCustomer actionCustomer = null;
    /**
     * output class constructor
     */
    public AbstractCluesManageListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCluesManageListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "CluesManageQuery");
        //actionShare
        this.actionShare = new ActionShare(this);
        getActionManager().registerAction("actionShare", actionShare);
         this.actionShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCommerceChance
        this.actionCommerceChance = new ActionCommerceChance(this);
        getActionManager().registerAction("actionCommerceChance", actionCommerceChance);
         this.actionCommerceChance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSincerity
        this.actionSincerity = new ActionSincerity(this);
        getActionManager().registerAction("actionSincerity", actionSincerity);
         this.actionSincerity.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeliver
        this.actionDeliver = new ActionDeliver(this);
        getActionManager().registerAction("actionDeliver", actionDeliver);
         this.actionDeliver.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuestionPrint
        this.actionQuestionPrint = new ActionQuestionPrint(this);
        getActionManager().registerAction("actionQuestionPrint", actionQuestionPrint);
         this.actionQuestionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFollow
        this.actionFollow = new ActionFollow(this);
        getActionManager().registerAction("actionFollow", actionFollow);
         this.actionFollow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrePurchase
        this.actionPrePurchase = new ActionPrePurchase(this);
        getActionManager().registerAction("actionPrePurchase", actionPrePurchase);
         this.actionPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurchase
        this.actionPurchase = new ActionPurchase(this);
        getActionManager().registerAction("actionPurchase", actionPurchase);
         this.actionPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSign
        this.actionSign = new ActionSign(this);
        getActionManager().registerAction("actionSign", actionSign);
         this.actionSign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomer
        this.actionCustomer = new ActionCustomer(this);
        getActionManager().registerAction("actionCustomer", actionCustomer);
         this.actionCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeliver = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuestionPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFollow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCommerceChance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTrade = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenuItem1 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeliver = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuQuestionPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFollow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCustomer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCommerceChance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuTrade = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemSincerity = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrePurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeMain.setName("treeMain");
        this.btnShare.setName("btnShare");
        this.btnDeliver.setName("btnDeliver");
        this.btnQuestionPrint.setName("btnQuestionPrint");
        this.btnFollow.setName("btnFollow");
        this.btnCustomer.setName("btnCustomer");
        this.btnCommerceChance.setName("btnCommerceChance");
        this.btnTrade.setName("btnTrade");
        this.btnImport.setName("btnImport");
        this.kDMenuItem1.setName("kDMenuItem1");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemDeliver.setName("menuItemDeliver");
        this.menuQuestionPrint.setName("menuQuestionPrint");
        this.menuItemFollow.setName("menuItemFollow");
        this.menuItemCustomer.setName("menuItemCustomer");
        this.menuItemCommerceChance.setName("menuItemCommerceChance");
        this.menuTrade.setName("menuTrade");
        this.menuItemSincerity.setName("menuItemSincerity");
        this.menuItemPrePurchase.setName("menuItemPrePurchase");
        this.menuItemPurchase.setName("menuItemPurchase");
        this.menuItemSign.setName("menuItemSign");
        this.menuItemImport.setName("menuItemImport");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"source\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"propertyConsultant.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"cluesStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol9\" /><t:Column t:key=\"lastUpdateUser.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol10\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol11\" /><t:Column t:key=\"propertyConsultant.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol12\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol13\" /><t:Column t:key=\"cognizePath.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{source}</t:Cell><t:Cell>$Resource{propertyConsultant.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{cluesStatus}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{lastUpdateUser.id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{propertyConsultant.id}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{cognizePath.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblMain_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","phone","description","source","propertyConsultant.name","creator.name","createTime","cluesStatus","sellProject.id","lastUpdateUser.id","CU.id","propertyConsultant.id","id","cognizePath.id"});

		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);
        // kDTreeView1
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
        // btnShare
        this.btnShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShare.setText(resHelper.getString("btnShare.text"));		
        this.btnShare.setToolTipText(resHelper.getString("btnShare.toolTipText"));		
        this.btnShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sealup"));
        // btnDeliver
        this.btnDeliver.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeliver, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeliver.setText(resHelper.getString("btnDeliver.text"));		
        this.btnDeliver.setToolTipText(resHelper.getString("btnDeliver.toolTipText"));		
        this.btnDeliver.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deliverto"));
        // btnQuestionPrint
        this.btnQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPrint.setText(resHelper.getString("btnQuestionPrint.text"));		
        this.btnQuestionPrint.setToolTipText(resHelper.getString("btnQuestionPrint.toolTipText"));		
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
        // btnFollow
        this.btnFollow.setAction((IItemAction)ActionProxyFactory.getProxy(actionFollow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFollow.setText(resHelper.getString("btnFollow.text"));
        // btnCustomer
        this.btnCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomer.setText(resHelper.getString("btnCustomer.text"));		
        this.btnCustomer.setToolTipText(resHelper.getString("btnCustomer.toolTipText"));
        // btnCommerceChance
        this.btnCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCommerceChance.setText(resHelper.getString("btnCommerceChance.text"));		
        this.btnCommerceChance.setToolTipText(resHelper.getString("btnCommerceChance.toolTipText"));		
        this.btnCommerceChance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // btnTrade		
        this.btnTrade.setText(resHelper.getString("btnTrade.text"));		
        this.btnTrade.setToolTipText(resHelper.getString("btnTrade.toolTipText"));		
        this.btnTrade.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_conversionsave"));
        this.btnTrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnTrade_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));		
        this.btnImport.setToolTipText(resHelper.getString("btnImport.toolTipText"));		
        this.btnImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // kDMenuItem1
        this.kDMenuItem1.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem1.setText(resHelper.getString("kDMenuItem1.text"));		
        this.kDMenuItem1.setToolTipText(resHelper.getString("kDMenuItem1.toolTipText"));		
        this.kDMenuItem1.setMnemonic(67);
        // menuItemShare
        this.menuItemShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShare.setText(resHelper.getString("menuItemShare.text"));		
        this.menuItemShare.setToolTipText(resHelper.getString("menuItemShare.toolTipText"));
        // menuItemDeliver
        this.menuItemDeliver.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeliver, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeliver.setText(resHelper.getString("menuItemDeliver.text"));		
        this.menuItemDeliver.setToolTipText(resHelper.getString("menuItemDeliver.toolTipText"));
        // menuQuestionPrint
        this.menuQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuQuestionPrint.setText(resHelper.getString("menuQuestionPrint.text"));		
        this.menuQuestionPrint.setToolTipText(resHelper.getString("menuQuestionPrint.toolTipText"));
        // menuItemFollow
        this.menuItemFollow.setAction((IItemAction)ActionProxyFactory.getProxy(actionFollow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFollow.setText(resHelper.getString("menuItemFollow.text"));		
        this.menuItemFollow.setToolTipText(resHelper.getString("menuItemFollow.toolTipText"));
        // menuItemCustomer
        this.menuItemCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCustomer.setText(resHelper.getString("menuItemCustomer.text"));		
        this.menuItemCustomer.setToolTipText(resHelper.getString("menuItemCustomer.toolTipText"));
        // menuItemCommerceChance
        this.menuItemCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCommerceChance.setText(resHelper.getString("menuItemCommerceChance.text"));		
        this.menuItemCommerceChance.setToolTipText(resHelper.getString("menuItemCommerceChance.toolTipText"));
        // menuTrade		
        this.menuTrade.setText(resHelper.getString("menuTrade.text"));		
        this.menuTrade.setToolTipText(resHelper.getString("menuTrade.toolTipText"));
        // menuItemSincerity
        this.menuItemSincerity.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerity, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSincerity.setText(resHelper.getString("menuItemSincerity.text"));		
        this.menuItemSincerity.setToolTipText(resHelper.getString("menuItemSincerity.toolTipText"));
        // menuItemPrePurchase
        this.menuItemPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrePurchase.setText(resHelper.getString("menuItemPrePurchase.text"));
        // menuItemPurchase
        this.menuItemPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchase.setText(resHelper.getString("menuItemPurchase.text"));		
        this.menuItemPurchase.setToolTipText(resHelper.getString("menuItemPurchase.toolTipText"));
        // menuItemSign
        this.menuItemSign.setAction((IItemAction)ActionProxyFactory.getProxy(actionSign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSign.setText(resHelper.getString("menuItemSign.text"));		
        this.menuItemSign.setToolTipText(resHelper.getString("menuItemSign.toolTipText"));
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setToolTipText(resHelper.getString("menuItemImport.toolTipText"));		
        this.menuItemImport.setMnemonic(67);
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 996, 609));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 996, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(kDTreeView1, "left");
        //kDTreeView1
        kDTreeView1.setTree(treeMain);

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
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemDeliver);
        menuBiz.add(menuQuestionPrint);
        menuBiz.add(menuItemFollow);
        menuBiz.add(menuItemCustomer);
        menuBiz.add(menuItemCommerceChance);
        menuBiz.add(menuTrade);
        menuBiz.add(menuItemImport);
        //menuTrade
        menuTrade.add(menuItemSincerity);
        menuTrade.add(menuItemPrePurchase);
        menuTrade.add(menuItemPurchase);
        menuTrade.add(menuItemSign);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnShare);
        this.toolBar.add(btnDeliver);
        this.toolBar.add(btnQuestionPrint);
        this.toolBar.add(btnFollow);
        this.toolBar.add(btnCustomer);
        this.toolBar.add(btnCommerceChance);
        this.toolBar.add(btnTrade);
        this.toolBar.add(btnImport);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CluesManageListUIHandler";
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output btnTrade_actionPerformed method
     */
    protected void btnTrade_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("source"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("propertyConsultant.name"));
        sic.add(new SelectorItemInfo("propertyConsultant.id"));
        sic.add(new SelectorItemInfo("cluesStatus"));
        sic.add(new SelectorItemInfo("cognizePath.name"));
        sic.add(new SelectorItemInfo("cognizePath.id"));
        return sic;
    }        
    	

    /**
     * output actionShare_actionPerformed method
     */
    public void actionShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCommerceChance_actionPerformed method
     */
    public void actionCommerceChance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSincerity_actionPerformed method
     */
    public void actionSincerity_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeliver_actionPerformed method
     */
    public void actionDeliver_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuestionPrint_actionPerformed method
     */
    public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFollow_actionPerformed method
     */
    public void actionFollow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrePurchase_actionPerformed method
     */
    public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurchase_actionPerformed method
     */
    public void actionPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSign_actionPerformed method
     */
    public void actionSign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomer_actionPerformed method
     */
    public void actionCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShare() {
    	return false;
    }
	public RequestContext prepareActionCommerceChance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCommerceChance() {
    	return false;
    }
	public RequestContext prepareActionSincerity(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSincerity() {
    	return false;
    }
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionDeliver(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeliver() {
    	return false;
    }
	public RequestContext prepareActionQuestionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuestionPrint() {
    	return false;
    }
	public RequestContext prepareActionFollow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFollow() {
    	return false;
    }
	public RequestContext prepareActionPrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrePurchase() {
    	return false;
    }
	public RequestContext prepareActionPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchase() {
    	return false;
    }
	public RequestContext prepareActionSign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSign() {
    	return false;
    }
	public RequestContext prepareActionCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomer() {
    	return false;
    }

    /**
     * output ActionShare class
     */     
    protected class ActionShare extends ItemAction {     
    
        public ActionShare()
        {
            this(null);
        }

        public ActionShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionShare", "actionShare_actionPerformed", e);
        }
    }

    /**
     * output ActionCommerceChance class
     */     
    protected class ActionCommerceChance extends ItemAction {     
    
        public ActionCommerceChance()
        {
            this(null);
        }

        public ActionCommerceChance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCommerceChance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommerceChance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommerceChance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionCommerceChance", "actionCommerceChance_actionPerformed", e);
        }
    }

    /**
     * output ActionSincerity class
     */     
    protected class ActionSincerity extends ItemAction {     
    
        public ActionSincerity()
        {
            this(null);
        }

        public ActionSincerity(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSincerity.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerity.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerity.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionSincerity", "actionSincerity_actionPerformed", e);
        }
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionDeliver class
     */     
    protected class ActionDeliver extends ItemAction {     
    
        public ActionDeliver()
        {
            this(null);
        }

        public ActionDeliver(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDeliver.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeliver.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeliver.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionDeliver", "actionDeliver_actionPerformed", e);
        }
    }

    /**
     * output ActionQuestionPrint class
     */     
    protected class ActionQuestionPrint extends ItemAction {     
    
        public ActionQuestionPrint()
        {
            this(null);
        }

        public ActionQuestionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuestionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionQuestionPrint", "actionQuestionPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionFollow class
     */     
    protected class ActionFollow extends ItemAction {     
    
        public ActionFollow()
        {
            this(null);
        }

        public ActionFollow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFollow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFollow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFollow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionFollow", "actionFollow_actionPerformed", e);
        }
    }

    /**
     * output ActionPrePurchase class
     */     
    protected class ActionPrePurchase extends ItemAction {     
    
        public ActionPrePurchase()
        {
            this(null);
        }

        public ActionPrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionPrePurchase", "actionPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionPurchase class
     */     
    protected class ActionPurchase extends ItemAction {     
    
        public ActionPurchase()
        {
            this(null);
        }

        public ActionPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionPurchase", "actionPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionSign class
     */     
    protected class ActionSign extends ItemAction {     
    
        public ActionSign()
        {
            this(null);
        }

        public ActionSign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionSign", "actionSign_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomer class
     */     
    protected class ActionCustomer extends ItemAction {     
    
        public ActionCustomer()
        {
            this(null);
        }

        public ActionCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCluesManageListUI.this, "ActionCustomer", "actionCustomer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CluesManageListUI");
    }




}