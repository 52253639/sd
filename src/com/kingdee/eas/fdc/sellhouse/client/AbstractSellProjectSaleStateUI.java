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
public abstract class AbstractSellProjectSaleStateUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellProjectSaleStateUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEndInit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnInit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllEndInit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllUnInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuEndInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuUnInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAllEndInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAllUnInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemInitDataBld;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemInitDataPty;
    protected ActionEndInit actionEndInit = null;
    protected ActionUnInit actionUnInit = null;
    protected ActionAllEndInit actionAllEndInit = null;
    protected ActionAllUnInit actionAllUnInit = null;
    protected ActionInitDataBld actionInitDataBld = null;
    protected ActionInitDataPty actionInitDataPty = null;
    /**
     * output class constructor
     */
    public AbstractSellProjectSaleStateUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSellProjectSaleStateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SellProjectSaleQuery");
        //actionEndInit
        this.actionEndInit = new ActionEndInit(this);
        getActionManager().registerAction("actionEndInit", actionEndInit);
         this.actionEndInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnInit
        this.actionUnInit = new ActionUnInit(this);
        getActionManager().registerAction("actionUnInit", actionUnInit);
         this.actionUnInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllEndInit
        this.actionAllEndInit = new ActionAllEndInit(this);
        getActionManager().registerAction("actionAllEndInit", actionAllEndInit);
         this.actionAllEndInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllUnInit
        this.actionAllUnInit = new ActionAllUnInit(this);
        getActionManager().registerAction("actionAllUnInit", actionAllUnInit);
         this.actionAllUnInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInitDataBld
        this.actionInitDataBld = new ActionInitDataBld(this);
        getActionManager().registerAction("actionInitDataBld", actionInitDataBld);
         this.actionInitDataBld.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInitDataPty
        this.actionInitDataPty = new ActionInitDataPty(this);
        getActionManager().registerAction("actionInitDataPty", actionInitDataPty);
         this.actionInitDataPty.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnEndInit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnInit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllEndInit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllUnInit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuEndInit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuUnInit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAllEndInit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAllUnInit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemInitDataBld = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemInitDataPty = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnEndInit.setName("btnEndInit");
        this.btnUnInit.setName("btnUnInit");
        this.btnAllEndInit.setName("btnAllEndInit");
        this.btnAllUnInit.setName("btnAllUnInit");
        this.menuEndInit.setName("menuEndInit");
        this.menuUnInit.setName("menuUnInit");
        this.menuAllEndInit.setName("menuAllEndInit");
        this.menuAllUnInit.setName("menuAllUnInit");
        this.menuItemInitDataBld.setName("menuItemInitDataBld");
        this.menuItemInitDataPty.setName("menuItemInitDataPty");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"isEndInit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"saleTerm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"saleNowTerm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"disposePerson.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"disposeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isEndInit}</t:Cell><t:Cell>$Resource{saleTerm}</t:Cell><t:Cell>$Resource{saleNowTerm}</t:Cell><t:Cell>$Resource{disposePerson.name}</t:Cell><t:Cell>$Resource{disposeDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","name","isEndInit","saleTerm","saleNowTerm","disposePerson.name","disposeDate"});


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
        // btnEndInit
        this.btnEndInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEndInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEndInit.setText(resHelper.getString("btnEndInit.text"));		
        this.btnEndInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_initialize"));
        // btnUnInit
        this.btnUnInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnInit.setText(resHelper.getString("btnUnInit.text"));		
        this.btnUnInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_finitialize"));
        // btnAllEndInit
        this.btnAllEndInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllEndInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllEndInit.setText(resHelper.getString("btnAllEndInit.text"));		
        this.btnAllEndInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_allfinishinitialize"));
        // btnAllUnInit
        this.btnAllUnInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllUnInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllUnInit.setText(resHelper.getString("btnAllUnInit.text"));		
        this.btnAllUnInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_allfinitialize"));
        // menuEndInit
        this.menuEndInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEndInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuEndInit.setText(resHelper.getString("menuEndInit.text"));		
        this.menuEndInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_initialize"));
        // menuUnInit
        this.menuUnInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuUnInit.setText(resHelper.getString("menuUnInit.text"));		
        this.menuUnInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_finitialize"));
        // menuAllEndInit
        this.menuAllEndInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllEndInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAllEndInit.setText(resHelper.getString("menuAllEndInit.text"));		
        this.menuAllEndInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_allfinishinitialize"));
        // menuAllUnInit
        this.menuAllUnInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllUnInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAllUnInit.setText(resHelper.getString("menuAllUnInit.text"));		
        this.menuAllUnInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_allfinitialize"));
        // menuItemInitDataBld
        this.menuItemInitDataBld.setAction((IItemAction)ActionProxyFactory.getProxy(actionInitDataBld, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemInitDataBld.setText(resHelper.getString("menuItemInitDataBld.text"));		
        this.menuItemInitDataBld.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_initialize"));
        // menuItemInitDataPty
        this.menuItemInitDataPty.setAction((IItemAction)ActionProxyFactory.getProxy(actionInitDataPty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemInitDataPty.setText(resHelper.getString("menuItemInitDataPty.text"));		
        this.menuItemInitDataPty.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_initialize"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 997, 612));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 997, 612, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        menuBiz.add(menuEndInit);
        menuBiz.add(menuUnInit);
        menuBiz.add(menuAllEndInit);
        menuBiz.add(menuAllUnInit);
        menuBiz.add(menuItemInitDataBld);
        menuBiz.add(menuItemInitDataPty);
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
        this.toolBar.add(btnEndInit);
        this.toolBar.add(btnUnInit);
        this.toolBar.add(btnAllEndInit);
        this.toolBar.add(btnAllUnInit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SellProjectSaleStateUIHandler";
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isEndInit"));
        sic.add(new SelectorItemInfo("saleTerm"));
        sic.add(new SelectorItemInfo("saleNowTerm"));
        sic.add(new SelectorItemInfo("disposeDate"));
        sic.add(new SelectorItemInfo("disposePerson.name"));
        sic.add(new SelectorItemInfo("id"));
        return sic;
    }        
    	

    /**
     * output actionEndInit_actionPerformed method
     */
    public void actionEndInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnInit_actionPerformed method
     */
    public void actionUnInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllEndInit_actionPerformed method
     */
    public void actionAllEndInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllUnInit_actionPerformed method
     */
    public void actionAllUnInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInitDataBld_actionPerformed method
     */
    public void actionInitDataBld_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInitDataPty_actionPerformed method
     */
    public void actionInitDataPty_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionEndInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEndInit() {
    	return false;
    }
	public RequestContext prepareActionUnInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnInit() {
    	return false;
    }
	public RequestContext prepareActionAllEndInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllEndInit() {
    	return false;
    }
	public RequestContext prepareActionAllUnInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllUnInit() {
    	return false;
    }
	public RequestContext prepareActionInitDataBld(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInitDataBld() {
    	return false;
    }
	public RequestContext prepareActionInitDataPty(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInitDataPty() {
    	return false;
    }

    /**
     * output ActionEndInit class
     */     
    protected class ActionEndInit extends ItemAction {     
    
        public ActionEndInit()
        {
            this(null);
        }

        public ActionEndInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEndInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEndInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEndInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellProjectSaleStateUI.this, "ActionEndInit", "actionEndInit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnInit class
     */     
    protected class ActionUnInit extends ItemAction {     
    
        public ActionUnInit()
        {
            this(null);
        }

        public ActionUnInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellProjectSaleStateUI.this, "ActionUnInit", "actionUnInit_actionPerformed", e);
        }
    }

    /**
     * output ActionAllEndInit class
     */     
    protected class ActionAllEndInit extends ItemAction {     
    
        public ActionAllEndInit()
        {
            this(null);
        }

        public ActionAllEndInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAllEndInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllEndInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllEndInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellProjectSaleStateUI.this, "ActionAllEndInit", "actionAllEndInit_actionPerformed", e);
        }
    }

    /**
     * output ActionAllUnInit class
     */     
    protected class ActionAllUnInit extends ItemAction {     
    
        public ActionAllUnInit()
        {
            this(null);
        }

        public ActionAllUnInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAllUnInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllUnInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllUnInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellProjectSaleStateUI.this, "ActionAllUnInit", "actionAllUnInit_actionPerformed", e);
        }
    }

    /**
     * output ActionInitDataBld class
     */     
    protected class ActionInitDataBld extends ItemAction {     
    
        public ActionInitDataBld()
        {
            this(null);
        }

        public ActionInitDataBld(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInitDataBld.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInitDataBld.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInitDataBld.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellProjectSaleStateUI.this, "ActionInitDataBld", "actionInitDataBld_actionPerformed", e);
        }
    }

    /**
     * output ActionInitDataPty class
     */     
    protected class ActionInitDataPty extends ItemAction {     
    
        public ActionInitDataPty()
        {
            this(null);
        }

        public ActionInitDataPty(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInitDataPty.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInitDataPty.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInitDataPty.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellProjectSaleStateUI.this, "ActionInitDataPty", "actionInitDataPty_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellProjectSaleStateUI");
    }




}