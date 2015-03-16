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
public abstract class AbstractFDCCustomerListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCCustomerListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeCusName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdateCus;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMerge;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeCusName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpdateCus;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMerge;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected ActionChangeCusName actionChangeCusName = null;
    protected ActionUpdateCus actionUpdateCus = null;
    protected ActionShare actionShare = null;
    protected ActionMerge actionMerge = null;
    protected ActionImport actionImport = null;
    /**
     * output class constructor
     */
    public AbstractFDCCustomerListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCCustomerListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basecrm.app", "FDCOrgCustomerQuery");
        //actionChangeCusName
        this.actionChangeCusName = new ActionChangeCusName(this);
        getActionManager().registerAction("actionChangeCusName", actionChangeCusName);
         this.actionChangeCusName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdateCus
        this.actionUpdateCus = new ActionUpdateCus(this);
        getActionManager().registerAction("actionUpdateCus", actionUpdateCus);
         this.actionUpdateCus.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShare
        this.actionShare = new ActionShare(this);
        getActionManager().registerAction("actionShare", actionShare);
         this.actionShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMerge
        this.actionMerge = new ActionMerge(this);
        getActionManager().registerAction("actionMerge", actionMerge);
         this.actionMerge.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnChangeCusName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpdateCus = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMerge = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemChangeCusName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpdateCus = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemMerge = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeMain.setName("treeMain");
        this.btnChangeCusName.setName("btnChangeCusName");
        this.btnUpdateCus.setName("btnUpdateCus");
        this.btnShare.setName("btnShare");
        this.btnMerge.setName("btnMerge");
        this.btnImport.setName("btnImport");
        this.menuItemChangeCusName.setName("menuItemChangeCusName");
        this.menuItemUpdateCus.setName("menuItemUpdateCus");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemMerge.setName("menuItemMerge");
        this.menuItemImport.setName("menuItemImport");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"customerType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"code\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"simpleCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"identity.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"firstLinkman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"postalCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedPlace.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"enterpriceProperty.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"industry.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isInsider\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"corporate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"corporateTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isChooseRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{customerType}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{code}</t:Cell><t:Cell>$Resource{simpleCode}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{identity.name}</t:Cell><t:Cell>$Resource{firstLinkman}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{bookedAddress}</t:Cell><t:Cell>$Resource{postalCode}</t:Cell><t:Cell>$Resource{bookedPlace.name}</t:Cell><t:Cell>$Resource{enterpriceProperty.name}</t:Cell><t:Cell>$Resource{industry.name}</t:Cell><t:Cell>$Resource{isInsider}</t:Cell><t:Cell>$Resource{corporate}</t:Cell><t:Cell>$Resource{corporateTel}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{isChooseRoom}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{createUnit.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{lastUpdateUnit.name}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{createWay}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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
                this.tblMain.putBindContents("mainQuery",new String[] {"id","customerType","number","code","simpleCode","name","sex","identity.name","firstLinkman","phone","email","mailAddress","bookedAddress","postalCode","bookedPlace.name","enterpriceProperty.name","industry.name","isInsider","corporate","corporateTel","description","isEnabled","isChooseRoom","createTime","createUnit.name","creator.name","lastUpdateTime","lastUpdateUnit.name","lastUpdateUser.name","createWay","sellProject.name"});

		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));		
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));		
        this.btnCancelCancel.setVisible(true);
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
        // btnChangeCusName
        this.btnChangeCusName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeCusName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeCusName.setText(resHelper.getString("btnChangeCusName.text"));		
        this.btnChangeCusName.setToolTipText(resHelper.getString("btnChangeCusName.toolTipText"));
        // btnUpdateCus
        this.btnUpdateCus.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateCus, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateCus.setText(resHelper.getString("btnUpdateCus.text"));		
        this.btnUpdateCus.setToolTipText(resHelper.getString("btnUpdateCus.toolTipText"));
        // btnShare
        this.btnShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShare.setText(resHelper.getString("btnShare.text"));		
        this.btnShare.setToolTipText(resHelper.getString("btnShare.toolTipText"));
        // btnMerge
        this.btnMerge.setAction((IItemAction)ActionProxyFactory.getProxy(actionMerge, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMerge.setText(resHelper.getString("btnMerge.text"));		
        this.btnMerge.setToolTipText(resHelper.getString("btnMerge.toolTipText"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));		
        this.btnImport.setToolTipText(resHelper.getString("btnImport.toolTipText"));
        // menuItemChangeCusName
        this.menuItemChangeCusName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeCusName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeCusName.setText(resHelper.getString("menuItemChangeCusName.text"));		
        this.menuItemChangeCusName.setToolTipText(resHelper.getString("menuItemChangeCusName.toolTipText"));
        // menuItemUpdateCus
        this.menuItemUpdateCus.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateCus, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUpdateCus.setText(resHelper.getString("menuItemUpdateCus.text"));		
        this.menuItemUpdateCus.setToolTipText(resHelper.getString("menuItemUpdateCus.toolTipText"));
        // menuItemShare
        this.menuItemShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShare.setText(resHelper.getString("menuItemShare.text"));		
        this.menuItemShare.setToolTipText(resHelper.getString("menuItemShare.toolTipText"));
        // menuItemMerge
        this.menuItemMerge.setAction((IItemAction)ActionProxyFactory.getProxy(actionMerge, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMerge.setText(resHelper.getString("menuItemMerge.text"));		
        this.menuItemMerge.setToolTipText(resHelper.getString("menuItemMerge.toolTipText"));
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setToolTipText(resHelper.getString("menuItemImport.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 995, 609));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 995, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        menuBiz.add(menuItemChangeCusName);
        menuBiz.add(menuItemUpdateCus);
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemMerge);
        menuBiz.add(menuItemImport);
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
        this.toolBar.add(btnChangeCusName);
        this.toolBar.add(btnUpdateCus);
        this.toolBar.add(btnShare);
        this.toolBar.add(btnMerge);
        this.toolBar.add(btnImport);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.FDCCustomerListUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("code"));
        sic.add(new SelectorItemInfo("simpleCode"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("identity.name"));
        sic.add(new SelectorItemInfo("firstLinkman"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("bookedAddress"));
        sic.add(new SelectorItemInfo("postalCode"));
        sic.add(new SelectorItemInfo("bookedPlace.name"));
        sic.add(new SelectorItemInfo("enterpriceProperty.name"));
        sic.add(new SelectorItemInfo("industry.name"));
        sic.add(new SelectorItemInfo("isInsider"));
        sic.add(new SelectorItemInfo("corporate"));
        sic.add(new SelectorItemInfo("corporateTel"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("isChooseRoom"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("lastUpdateUnit.name"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("createWay"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("createUnit.name"));
        return sic;
    }        
    	

    /**
     * output actionChangeCusName_actionPerformed method
     */
    public void actionChangeCusName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdateCus_actionPerformed method
     */
    public void actionUpdateCus_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShare_actionPerformed method
     */
    public void actionShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMerge_actionPerformed method
     */
    public void actionMerge_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionChangeCusName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeCusName() {
    	return false;
    }
	public RequestContext prepareActionUpdateCus(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateCus() {
    	return false;
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
	public RequestContext prepareActionMerge(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMerge() {
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

    /**
     * output ActionChangeCusName class
     */     
    protected class ActionChangeCusName extends ItemAction {     
    
        public ActionChangeCusName()
        {
            this(null);
        }

        public ActionChangeCusName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeCusName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeCusName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeCusName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCCustomerListUI.this, "ActionChangeCusName", "actionChangeCusName_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdateCus class
     */     
    protected class ActionUpdateCus extends ItemAction {     
    
        public ActionUpdateCus()
        {
            this(null);
        }

        public ActionUpdateCus(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpdateCus.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateCus.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateCus.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCCustomerListUI.this, "ActionUpdateCus", "actionUpdateCus_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractFDCCustomerListUI.this, "ActionShare", "actionShare_actionPerformed", e);
        }
    }

    /**
     * output ActionMerge class
     */     
    protected class ActionMerge extends ItemAction {     
    
        public ActionMerge()
        {
            this(null);
        }

        public ActionMerge(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMerge.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMerge.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMerge.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCCustomerListUI.this, "ActionMerge", "actionMerge_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "FDCCustomerListUI");
    }




}