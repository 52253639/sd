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
public abstract class AbstractMortagageControlUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMortagageControlUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMortagage;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiMortagage;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMortagage;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiMortagage;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected ActionAntiMortagage actionAntiMortagage = null;
    protected ActionMortagage actionMortagage = null;
    /**
     * output class constructor
     */
    public AbstractMortagageControlUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMortagageControlUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionAntiMortagage
        this.actionAntiMortagage = new ActionAntiMortagage(this);
        getActionManager().registerAction("actionAntiMortagage", actionAntiMortagage);
         this.actionAntiMortagage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMortagage
        this.actionMortagage = new ActionMortagage(this);
        getActionManager().registerAction("actionMortagage", actionMortagage);
         this.actionMortagage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnMortagage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiMortagage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.menuItemMortagage = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiMortagage = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnMortagage.setName("btnMortagage");
        this.btnAntiMortagage.setName("btnAntiMortagage");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.sclPanel.setName("sclPanel");
        this.menuItemMortagage.setName("menuItemMortagage");
        this.menuItemAntiMortagage.setName("menuItemAntiMortagage");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDPanel1.setName("kDPanel1");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table></t:Sheet></Table></DocRoot> ";

                this.tblMain.putBindContents("mainQuery",new String[] {"BMCMessage.id"});

		
        this.pnlMain.setDividerLocation(160);
        // btnMortagage
        this.btnMortagage.setAction((IItemAction)ActionProxyFactory.getProxy(actionMortagage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMortagage.setText(resHelper.getString("btnMortagage.text"));		
        this.btnMortagage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTree_businessgroup"));
        // btnAntiMortagage
        this.btnAntiMortagage.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiMortagage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiMortagage.setText(resHelper.getString("btnAntiMortagage.text"));		
        this.btnAntiMortagage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(600);
        // sclPanel
        // menuItemMortagage
        this.menuItemMortagage.setAction((IItemAction)ActionProxyFactory.getProxy(actionMortagage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMortagage.setText(resHelper.getString("menuItemMortagage.text"));		
        this.menuItemMortagage.setToolTipText(resHelper.getString("menuItemMortagage.toolTipText"));		
        this.menuItemMortagage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTree_businessgroup"));
        // menuItemAntiMortagage
        this.menuItemAntiMortagage.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiMortagage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiMortagage.setText(resHelper.getString("menuItemAntiMortagage.text"));		
        this.menuItemAntiMortagage.setToolTipText(resHelper.getString("menuItemAntiMortagage.toolTipText"));		
        this.menuItemAntiMortagage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(550);
        // kDPanel1
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setBackground(new java.awt.Color(255,255,128));		
        this.kDLabel1.setHorizontalAlignment(0);		
        this.kDLabel1.setOpaque(true);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setHorizontalAlignment(0);		
        this.kDLabel2.setBackground(new java.awt.Color(255,0,0));		
        this.kDLabel2.setOpaque(true);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));		
        this.kDLabel3.setBackground(new java.awt.Color(255,255,255));		
        this.kDLabel3.setHorizontalAlignment(0);		
        this.kDLabel3.setOpaque(true);
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
        pnlMain.setBounds(new Rectangle(10, 15, 784, 574));
        this.add(pnlMain, new KDLayout.Constraints(10, 15, 784, 574, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDSplitPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDSplitPane1
        kDSplitPane1.add(sclPanel, "right");
        kDSplitPane1.add(kDSplitPane2, "left");
        //kDSplitPane2
        kDSplitPane2.add(tblMain, "top");
        kDSplitPane2.add(kDPanel1, "bottom");
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabel1.setBounds(new Rectangle(25, 21, 70, 33));
        kDPanel1.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(128, 21, 70, 33));
        kDPanel1.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(232, 21, 70, 33));
        kDPanel1.add(kDLabel3, null);

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
        menuBiz.add(menuItemMortagage);
        menuBiz.add(menuItemAntiMortagage);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnMortagage);
        this.toolBar.add(btnAntiMortagage);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.MortagageControlUIHandler";
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
     * output actionAntiMortagage_actionPerformed method
     */
    public void actionAntiMortagage_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMortagage_actionPerformed method
     */
    public void actionMortagage_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAntiMortagage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiMortagage() {
    	return false;
    }
	public RequestContext prepareActionMortagage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMortagage() {
    	return false;
    }

    /**
     * output ActionAntiMortagage class
     */     
    protected class ActionAntiMortagage extends ItemAction {     
    
        public ActionAntiMortagage()
        {
            this(null);
        }

        public ActionAntiMortagage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAntiMortagage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiMortagage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiMortagage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMortagageControlUI.this, "ActionAntiMortagage", "actionAntiMortagage_actionPerformed", e);
        }
    }

    /**
     * output ActionMortagage class
     */     
    protected class ActionMortagage extends ItemAction {     
    
        public ActionMortagage()
        {
            this(null);
        }

        public ActionMortagage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMortagage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMortagage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMortagage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMortagageControlUI.this, "ActionMortagage", "actionMortagage_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MortagageControlUI");
    }




}