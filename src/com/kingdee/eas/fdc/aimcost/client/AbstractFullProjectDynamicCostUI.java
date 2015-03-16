/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractFullProjectDynamicCostUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFullProjectDynamicCostUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChart;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuChat;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChart1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChart2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMonthSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMonthSave;
    protected ActionChart actionChart = null;
    protected ActionSave actionSave = null;
    protected ActionMonthSave actionMonthSave = null;
    /**
     * output class constructor
     */
    public AbstractFullProjectDynamicCostUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFullProjectDynamicCostUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionMoveTree
        String _tempStr = null;
        actionMoveTree.setEnabled(true);
        actionMoveTree.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionMoveTree.SHORT_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.LONG_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.NAME");
        actionMoveTree.putValue(ItemAction.NAME, _tempStr);
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionChart
        this.actionChart = new ActionChart(this);
        getActionManager().registerAction("actionChart", actionChart);
        this.actionChart.setBindWorkFlow(true);
         this.actionChart.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMonthSave
        this.actionMonthSave = new ActionMonthSave(this);
        getActionManager().registerAction("actionMonthSave", actionMonthSave);
         this.actionMonthSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnChart = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuChat = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemChart1 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChart2 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnMonthSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemMonthSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnChart.setName("btnChart");
        this.menuItemSubmit.setName("menuItemSubmit");
        this.menuItemSave.setName("menuItemSave");
        this.btnSave.setName("btnSave");
        this.menuChat.setName("menuChat");
        this.menuItemChart1.setName("menuItemChart1");
        this.menuItemChart2.setName("menuItemChart2");
        this.btnMonthSave.setName("btnMonthSave");
        this.menuItemMonthSave.setName("menuItemMonthSave");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"acctNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" /><t:Column t:key=\"acctName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"noText\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasHappen\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"intendingHappen\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"dynamicCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"diff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"sellPart\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"buildPart\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{noText}</t:Cell><t:Cell>$Resource{hasHappen}</t:Cell><t:Cell>$Resource{hasPay}</t:Cell><t:Cell>$Resource{hasPaid}</t:Cell><t:Cell>$Resource{intendingHappen}</t:Cell><t:Cell>$Resource{dynamicCost}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{diff}</t:Cell><t:Cell>$Resource{sellPart}</t:Cell><t:Cell>$Resource{buildPart}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","",""});

		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.menuBiz.setToolTipText(resHelper.getString("menuBiz.toolTipText"));		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);		
        this.pnlMain.setDividerLocation(180);		
        this.pnlMain.setOneTouchExpandable(true);
        // btnChart
        this.btnChart.setAction((IItemAction)ActionProxyFactory.getProxy(actionChart, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChart.setToolTipText(resHelper.getString("btnChart.toolTipText"));		
        this.btnChart.setText(resHelper.getString("btnChart.text"));
        // menuItemSubmit
        this.menuItemSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionChart, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));
        // menuItemSave
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));
        // menuChat		
        this.menuChat.setText(resHelper.getString("menuChat.text"));
        // menuItemChart1		
        this.menuItemChart1.setText(resHelper.getString("menuItemChart1.text"));		
        this.menuItemChart1.setToolTipText(resHelper.getString("menuItemChart1.toolTipText"));
        // menuItemChart2		
        this.menuItemChart2.setText(resHelper.getString("menuItemChart2.text"));
        // btnMonthSave
        this.btnMonthSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionMonthSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMonthSave.setText(resHelper.getString("btnMonthSave.text"));		
        this.btnMonthSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeinitialize"));
        // menuItemMonthSave
        this.menuItemMonthSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionMonthSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMonthSave.setText(resHelper.getString("menuItemMonthSave.text"));		
        this.menuItemMonthSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeinitialize"));		
        this.menuItemMonthSave.setMnemonic(77);
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
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemSave);
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
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuChat);
        //menuChat
        menuChat.add(menuItemChart1);
        menuChat.add(menuItemChart2);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemMonthSave);
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
        this.toolBar.add(btnSave);
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
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnChart);
        this.toolBar.add(btnMonthSave);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.FullProjectDynamicCostUIHandler";
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
     * output actionMoveTree_actionPerformed method
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }
    	

    /**
     * output actionChart_actionPerformed method
     */
    public void actionChart_actionPerformed(ActionEvent e) throws Exception
    {
        //write your code here
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMonthSave_actionPerformed method
     */
    public void actionMonthSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionMoveTree(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionMoveTree(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveTree() {
    	return false;
    }
	public RequestContext prepareActionChart(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChart() {
    	return false;
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
	public RequestContext prepareActionMonthSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMonthSave() {
    	return false;
    }

    /**
     * output ActionChart class
     */     
    protected class ActionChart extends ItemAction {     
    
        public ActionChart()
        {
            this(null);
        }

        public ActionChart(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift C"));
            _tempStr = resHelper.getString("ActionChart.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChart.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChart.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFullProjectDynamicCostUI.this, "ActionChart", "actionChart_actionPerformed", e);
        }
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractFullProjectDynamicCostUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionMonthSave class
     */     
    protected class ActionMonthSave extends ItemAction {     
    
        public ActionMonthSave()
        {
            this(null);
        }

        public ActionMonthSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMonthSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFullProjectDynamicCostUI.this, "ActionMonthSave", "actionMonthSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "FullProjectDynamicCostUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}