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
public abstract class AbstractVirtualSellControlUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractVirtualSellControlUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSimulateSell;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSignContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSimulate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSignUp;
    protected ActionSimulate actionSimulate = null;
    protected ActionVirtualPurchase actionVirtualPurchase = null;
    protected ActionSign actionSign = null;
    /**
     * output class constructor
     */
    public AbstractVirtualSellControlUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractVirtualSellControlUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionSimulate
        this.actionSimulate = new ActionSimulate(this);
        getActionManager().registerAction("actionSimulate", actionSimulate);
         this.actionSimulate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVirtualPurchase
        this.actionVirtualPurchase = new ActionVirtualPurchase(this);
        getActionManager().registerAction("actionVirtualPurchase", actionVirtualPurchase);
         this.actionVirtualPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSign
        this.actionSign = new ActionSign(this);
        getActionManager().registerAction("actionSign", actionSign);
         this.actionSign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnSimulateSell = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSignContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuSimulate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSignUp = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.sclPanel.setName("sclPanel");
        this.kDPanel1.setName("kDPanel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnSimulateSell.setName("btnSimulateSell");
        this.btnPurchase.setName("btnPurchase");
        this.btnSignContract.setName("btnSignContract");
        this.menuSimulate.setName("menuSimulate");
        this.menuPurchase.setName("menuPurchase");
        this.menuSignUp.setName("menuSignUp");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table></t:Sheet></Table></DocRoot> ";

                this.tblMain.putBindContents("mainQuery",new String[] {"BMCMessage.id"});

		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);		
        this.pnlMain.setDividerLocation(160);		
        this.pnlMain.setOneTouchExpandable(true);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(550);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // sclPanel
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // kDScrollPane1		
        this.kDScrollPane1.setPreferredSize(new Dimension(3,35));
        // btnSimulateSell
        this.btnSimulateSell.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimulate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSimulateSell.setText(resHelper.getString("btnSimulateSell.text"));		
        this.btnSimulateSell.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cost"));
        // btnPurchase
        this.btnPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionVirtualPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchase.setText(resHelper.getString("btnPurchase.text"));		
        this.btnPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // btnSignContract
        this.btnSignContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionSign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSignContract.setText(resHelper.getString("btnSignContract.text"));		
        this.btnSignContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));
        // menuSimulate
        this.menuSimulate.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimulate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSimulate.setText(resHelper.getString("menuSimulate.text"));		
        this.menuSimulate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cost"));
        // menuPurchase
        this.menuPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionVirtualPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuPurchase.setText(resHelper.getString("menuPurchase.text"));		
        this.menuPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // menuSignUp
        this.menuSignUp.setAction((IItemAction)ActionProxyFactory.getProxy(actionVirtualPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSignUp.setText(resHelper.getString("menuSignUp.text"));		
        this.menuSignUp.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        pnlMain.setBounds(new Rectangle(10, 9, 784, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 9, 784, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDSplitPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDSplitPane1
        kDSplitPane1.add(sclPanel, "right");
        kDSplitPane1.add(kDPanel1, "left");
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblMain, BorderLayout.CENTER);
        kDPanel1.add(kDScrollPane1, BorderLayout.SOUTH);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(MenuService);
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
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
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
        menuBiz.add(menuSimulate);
        menuBiz.add(menuPurchase);
        menuBiz.add(menuSignUp);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnSimulateSell);
        this.toolBar.add(btnPurchase);
        this.toolBar.add(btnSignContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.VirtualSellControlUIHandler";
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
        return sic;
    }        
    	

    /**
     * output actionSimulate_actionPerformed method
     */
    public void actionSimulate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVirtualPurchase_actionPerformed method
     */
    public void actionVirtualPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSign_actionPerformed method
     */
    public void actionSign_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSimulate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSimulate() {
    	return false;
    }
	public RequestContext prepareActionVirtualPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVirtualPurchase() {
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

    /**
     * output ActionSimulate class
     */     
    protected class ActionSimulate extends ItemAction {     
    
        public ActionSimulate()
        {
            this(null);
        }

        public ActionSimulate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSimulate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimulate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimulate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractVirtualSellControlUI.this, "ActionSimulate", "actionSimulate_actionPerformed", e);
        }
    }

    /**
     * output ActionVirtualPurchase class
     */     
    protected class ActionVirtualPurchase extends ItemAction {     
    
        public ActionVirtualPurchase()
        {
            this(null);
        }

        public ActionVirtualPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionVirtualPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVirtualPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVirtualPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractVirtualSellControlUI.this, "ActionVirtualPurchase", "actionVirtualPurchase_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractVirtualSellControlUI.this, "ActionSign", "actionSign_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "VirtualSellControlUI");
    }




}