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
public abstract class AbstractQuotingPriceOverRangeUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuotingPriceOverRangeUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton refreshButton;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem refreshMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem prePrintMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem printMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDMenu displayMenu;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem exportMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected PrePrintAction prePrintAction = null;
    protected PrintAction printAction = null;
    protected RefreshAction refreshAction = null;
    /**
     * output class constructor
     */
    public AbstractQuotingPriceOverRangeUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuotingPriceOverRangeUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //prePrintAction
        this.prePrintAction = new PrePrintAction(this);
        getActionManager().registerAction("prePrintAction", prePrintAction);
         this.prePrintAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //printAction
        this.printAction = new PrintAction(this);
        getActionManager().registerAction("printAction", printAction);
         this.printAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //refreshAction
        this.refreshAction = new RefreshAction(this);
        getActionManager().registerAction("refreshAction", refreshAction);
         this.refreshAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton3 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.refreshButton = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.refreshMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.prePrintMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.printMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.displayMenu = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.exportMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.kDWorkButton3.setName("kDWorkButton3");
        this.refreshButton.setName("refreshButton");
        this.refreshMenuItem.setName("refreshMenuItem");
        this.prePrintMenuItem.setName("prePrintMenuItem");
        this.printMenuItem.setName("printMenuItem");
        this.displayMenu.setName("displayMenu");
        this.exportMenuItem.setName("exportMenuItem");
        this.kDSeparator2.setName("kDSeparator2");
        // CoreUI
        // kDTabbedPane1
        // kDWorkButton1
        this.kDWorkButton1.setAction((IItemAction)ActionProxyFactory.getProxy(actionExport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton1.setText(resHelper.getString("kDWorkButton1.text"));		
        this.kDWorkButton1.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // kDWorkButton2
        this.kDWorkButton2.setAction((IItemAction)ActionProxyFactory.getProxy(prePrintAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton2.setText(resHelper.getString("kDWorkButton2.text"));		
        this.kDWorkButton2.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // kDWorkButton3
        this.kDWorkButton3.setAction((IItemAction)ActionProxyFactory.getProxy(printAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton3.setText(resHelper.getString("kDWorkButton3.text"));		
        this.kDWorkButton3.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // refreshButton
        this.refreshButton.setAction((IItemAction)ActionProxyFactory.getProxy(refreshAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.refreshButton.setText(resHelper.getString("refreshButton.text"));		
        this.refreshButton.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));
        // refreshMenuItem
        this.refreshMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(refreshAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.refreshMenuItem.setText(resHelper.getString("refreshMenuItem.text"));		
        this.refreshMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));		
        this.refreshMenuItem.setMnemonic(82);
        // prePrintMenuItem
        this.prePrintMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(prePrintAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.prePrintMenuItem.setText(resHelper.getString("prePrintMenuItem.text"));		
        this.prePrintMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));		
        this.prePrintMenuItem.setMnemonic(86);
        // printMenuItem
        this.printMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(printAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.printMenuItem.setText(resHelper.getString("printMenuItem.text"));		
        this.printMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));		
        this.printMenuItem.setMnemonic(80);
        // displayMenu		
        this.displayMenu.setText(resHelper.getString("displayMenu.text"));		
        this.displayMenu.setMnemonic(86);
        // exportMenuItem
        this.exportMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionExport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.exportMenuItem.setText(resHelper.getString("exportMenuItem.text"));		
        this.exportMenuItem.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // kDSeparator2
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
        kDTabbedPane1.setBounds(new Rectangle(5, 7, 1002, 615));
        this.add(kDTabbedPane1, new KDLayout.Constraints(5, 7, 1002, 615, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(displayMenu);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(exportMenuItem);
        menuFile.add(kDSeparator1);
        menuFile.add(prePrintMenuItem);
        menuFile.add(printMenuItem);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //displayMenu
        displayMenu.add(refreshMenuItem);
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
        this.toolBar.add(kDWorkButton1);
        this.toolBar.add(kDWorkButton2);
        this.toolBar.add(kDWorkButton3);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.QuotingPriceOverRangeUIHandler";
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
     * output prePrintAction_actionPerformed method
     */
    public void prePrintAction_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext preparePrePrintAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPreparePrePrintAction() {
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
     * output PrePrintAction class
     */     
    protected class PrePrintAction extends ItemAction {     
    
        public PrePrintAction()
        {
            this(null);
        }

        public PrePrintAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
            _tempStr = resHelper.getString("PrePrintAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("PrePrintAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("PrePrintAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceOverRangeUI.this, "PrePrintAction", "prePrintAction_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractQuotingPriceOverRangeUI.this, "PrintAction", "printAction_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractQuotingPriceOverRangeUI.this, "RefreshAction", "refreshAction_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "QuotingPriceOverRangeUI");
    }




}