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
public abstract class AbstractSaleBalanceListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSaleBalanceListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemInitDataBld;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemInitDataPty;
    protected ActionSaleBalance actionSaleBalance = null;
    protected ActionUnSaleBalance actionUnSaleBalance = null;
    protected ActionInitDataBld actionInitDataBld = null;
    protected ActionInitDataPty actionInitDataPty = null;
    /**
     * output class constructor
     */
    public AbstractSaleBalanceListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSaleBalanceListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SaleBalanceQuery");
        //actionSaleBalance
        this.actionSaleBalance = new ActionSaleBalance(this);
        getActionManager().registerAction("actionSaleBalance", actionSaleBalance);
         this.actionSaleBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnSaleBalance
        this.actionUnSaleBalance = new ActionUnSaleBalance(this);
        getActionManager().registerAction("actionUnSaleBalance", actionUnSaleBalance);
         this.actionUnSaleBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInitDataBld
        this.actionInitDataBld = new ActionInitDataBld(this);
        getActionManager().registerAction("actionInitDataBld", actionInitDataBld);
         this.actionInitDataBld.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInitDataPty
        this.actionInitDataPty = new ActionInitDataPty(this);
        getActionManager().registerAction("actionInitDataPty", actionInitDataPty);
         this.actionInitDataPty.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.menuItemBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemInitDataBld = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemInitDataPty = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBalance.setName("menuItemBalance");
        this.menuItemUnBalance.setName("menuItemUnBalance");
        this.btnBalance.setName("btnBalance");
        this.btnUnBalance.setName("btnUnBalance");
        this.menuItemInitDataBld.setName("menuItemInitDataBld");
        this.menuItemInitDataPty.setName("menuItemInitDataPty");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"operateType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"balanceDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{operateType}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{balanceDate}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"operateType","startDate","endDate","remark","creator.name","balanceDate","sellProject.id","id"});

		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnView.setEnabled(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuEdit.setEnabled(false);		
        this.menuItemEdit.setEnabled(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemRemove.setEnabled(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);		
        this.separatorEdit1.setEnabled(false);
        // menuItemBalance
        this.menuItemBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaleBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBalance.setText(resHelper.getString("menuItemBalance.text"));		
        this.menuItemBalance.setToolTipText(resHelper.getString("menuItemBalance.toolTipText"));		
        this.menuItemBalance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // menuItemUnBalance
        this.menuItemUnBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnSaleBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnBalance.setText(resHelper.getString("menuItemUnBalance.text"));		
        this.menuItemUnBalance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_mergeandcenter"));		
        this.menuItemUnBalance.setToolTipText(resHelper.getString("menuItemUnBalance.toolTipText"));
        // btnBalance
        this.btnBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaleBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBalance.setText(resHelper.getString("btnBalance.text"));		
        this.btnBalance.setToolTipText(resHelper.getString("btnBalance.toolTipText"));		
        this.btnBalance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // btnUnBalance
        this.btnUnBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnSaleBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnBalance.setText(resHelper.getString("btnUnBalance.text"));		
        this.btnUnBalance.setToolTipText(resHelper.getString("btnUnBalance.toolTipText"));		
        this.btnUnBalance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_mergeandcenter"));
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
        menuBiz.add(menuItemBalance);
        menuBiz.add(menuItemUnBalance);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnBalance);
        this.toolBar.add(btnUnBalance);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SaleBalanceListUIHandler";
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
        sic.add(new SelectorItemInfo("operateType"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("balanceDate"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("id"));
        return sic;
    }        
    	

    /**
     * output actionSaleBalance_actionPerformed method
     */
    public void actionSaleBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnSaleBalance_actionPerformed method
     */
    public void actionUnSaleBalance_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionSaleBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaleBalance() {
    	return false;
    }
	public RequestContext prepareActionUnSaleBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnSaleBalance() {
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
     * output ActionSaleBalance class
     */     
    protected class ActionSaleBalance extends ItemAction {     
    
        public ActionSaleBalance()
        {
            this(null);
        }

        public ActionSaleBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSaleBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaleBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaleBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSaleBalanceListUI.this, "ActionSaleBalance", "actionSaleBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionUnSaleBalance class
     */     
    protected class ActionUnSaleBalance extends ItemAction {     
    
        public ActionUnSaleBalance()
        {
            this(null);
        }

        public ActionUnSaleBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnSaleBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnSaleBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnSaleBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSaleBalanceListUI.this, "ActionUnSaleBalance", "actionUnSaleBalance_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractSaleBalanceListUI.this, "ActionInitDataBld", "actionInitDataBld_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractSaleBalanceListUI.this, "ActionInitDataPty", "actionInitDataPty_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SaleBalanceListUI");
    }




}