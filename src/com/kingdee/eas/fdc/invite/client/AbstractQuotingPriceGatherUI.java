/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractQuotingPriceGatherUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuotingPriceGatherUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportExcel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton refreshButton;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuDisplay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem prePrintMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem printMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem refreshMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected ActionSave actionSave = null;
    protected ActionExportExcel actionExportExcel = null;
    protected PrePrint prePrint = null;
    protected PrintAction printAction = null;
    protected RefreshAction refreshAction = null;
    /**
     * output class constructor
     */
    public AbstractQuotingPriceGatherUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractQuotingPriceGatherUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportExcel
        this.actionExportExcel = new ActionExportExcel(this);
        getActionManager().registerAction("actionExportExcel", actionExportExcel);
         this.actionExportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //prePrint
        this.prePrint = new PrePrint(this);
        getActionManager().registerAction("prePrint", prePrint);
         this.prePrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //printAction
        this.printAction = new PrintAction(this);
        getActionManager().registerAction("printAction", printAction);
         this.printAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //refreshAction
        this.refreshAction = new RefreshAction(this);
        getActionManager().registerAction("refreshAction", refreshAction);
         this.refreshAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnExportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.menuExportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.refreshButton = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuDisplay = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.prePrintMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.printMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.refreshMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnSave.setName("btnSave");
        this.menuSave.setName("menuSave");
        this.btnExportExcel.setName("btnExportExcel");
        this.kDTable1.setName("kDTable1");
        this.menuExportExcel.setName("menuExportExcel");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.refreshButton.setName("refreshButton");
        this.menuDisplay.setName("menuDisplay");
        this.prePrintMenuItem.setName("prePrintMenuItem");
        this.printMenuItem.setName("printMenuItem");
        this.kDSeparator2.setName("kDSeparator2");
        this.refreshMenuItem.setName("refreshMenuItem");
        this.kDSeparator3.setName("kDSeparator3");
        // CoreUI		
        this.setBorder(null);
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // menuSave
        this.menuSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSave.setText(resHelper.getString("menuSave.text"));		
        this.menuSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // btnExportExcel
        this.btnExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportExcel.setText(resHelper.getString("btnExportExcel.text"));		
        this.btnExportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column3\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column4\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column5\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column6\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column7\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"column8\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{column1}</t:Cell><t:Cell>$Resource{column2}</t:Cell><t:Cell>$Resource{column3}</t:Cell><t:Cell>$Resource{column4}</t:Cell><t:Cell>$Resource{column5}</t:Cell><t:Cell>$Resource{column6}</t:Cell><t:Cell>$Resource{column7}</t:Cell><t:Cell>$Resource{column8}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // menuExportExcel
        this.menuExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuExportExcel.setText(resHelper.getString("menuExportExcel.text"));		
        this.menuExportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // kDWorkButton1
        this.kDWorkButton1.setAction((IItemAction)ActionProxyFactory.getProxy(prePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton1.setText(resHelper.getString("kDWorkButton1.text"));		
        this.kDWorkButton1.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // kDWorkButton2
        this.kDWorkButton2.setAction((IItemAction)ActionProxyFactory.getProxy(printAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton2.setText(resHelper.getString("kDWorkButton2.text"));		
        this.kDWorkButton2.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // refreshButton
        this.refreshButton.setAction((IItemAction)ActionProxyFactory.getProxy(refreshAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.refreshButton.setText(resHelper.getString("refreshButton.text"));		
        this.refreshButton.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));
        // menuDisplay		
        this.menuDisplay.setText(resHelper.getString("menuDisplay.text"));		
        this.menuDisplay.setMnemonic(86);
        // prePrintMenuItem
        this.prePrintMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(prePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.prePrintMenuItem.setText(resHelper.getString("prePrintMenuItem.text"));		
        this.prePrintMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // printMenuItem
        this.printMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(printAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.printMenuItem.setText(resHelper.getString("printMenuItem.text"));		
        this.printMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // kDSeparator2
        // refreshMenuItem
        this.refreshMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(refreshAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.refreshMenuItem.setText(resHelper.getString("refreshMenuItem.text"));		
        this.refreshMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));		
        this.refreshMenuItem.setMnemonic(82);
        // kDSeparator3
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
        kDTable1.setBounds(new Rectangle(7, 5, 999, 620));
        this.add(kDTable1, new KDLayout.Constraints(7, 5, 999, 620, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuDisplay);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuSave);
        menuFile.add(kDSeparator1);
        menuFile.add(menuExportExcel);
        menuFile.add(kDSeparator2);
        menuFile.add(prePrintMenuItem);
        menuFile.add(printMenuItem);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuDisplay
        menuDisplay.add(refreshMenuItem);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(menuItemAbout);
        menuHelp.add(kDSeparatorProduct);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(refreshButton);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnExportExcel);
        this.toolBar.add(kDWorkButton1);
        this.toolBar.add(kDWorkButton2);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.QuotingPriceGatherUIHandler";
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
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportExcel_actionPerformed method
     */
    public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output prePrint_actionPerformed method
     */
    public void prePrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output printAction_actionPerformed method
     */
    public void printAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output refreshAction_actionPerformed method
     */
    public void refreshAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	public RequestContext prepareActionExportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportExcel() {
    	return false;
    }
	public RequestContext preparePrePrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPreparePrePrint() {
    	return false;
    }
	public RequestContext preparePrintAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPreparePrintAction() {
    	return false;
    }
	public RequestContext prepareRefreshAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareRefreshAction() {
    	return false;
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceGatherUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionExportExcel class
     */     
    protected class ActionExportExcel extends ItemAction {     
    
        public ActionExportExcel()
        {
            this(null);
        }

        public ActionExportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift E"));
            _tempStr = resHelper.getString("ActionExportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceGatherUI.this, "ActionExportExcel", "actionExportExcel_actionPerformed", e);
        }
    }

    /**
     * output PrePrint class
     */     
    protected class PrePrint extends ItemAction {     
    
        public PrePrint()
        {
            this(null);
        }

        public PrePrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
            _tempStr = resHelper.getString("PrePrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("PrePrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("PrePrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceGatherUI.this, "PrePrint", "prePrint_actionPerformed", e);
        }
    }

    /**
     * output PrintAction class
     */     
    protected class PrintAction extends ItemAction {     
    
        public PrintAction()
        {
            this(null);
        }

        public PrintAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
            _tempStr = resHelper.getString("PrintAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("PrintAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("PrintAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceGatherUI.this, "PrintAction", "printAction_actionPerformed", e);
        }
    }

    /**
     * output RefreshAction class
     */     
    protected class RefreshAction extends ItemAction {     
    
        public RefreshAction()
        {
            this(null);
        }

        public RefreshAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
            _tempStr = resHelper.getString("RefreshAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("RefreshAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("RefreshAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceGatherUI.this, "RefreshAction", "refreshAction_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "QuotingPriceGatherUI");
    }




}