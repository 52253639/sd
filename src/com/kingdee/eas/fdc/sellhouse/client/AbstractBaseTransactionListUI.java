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
public abstract class AbstractBaseTransactionListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBaseTransactionListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMultiSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInvalid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSpecialBiz;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemInvalid;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPriceChange;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionInvalid actionInvalid = null;
    protected ActionReceiveBill actionReceiveBill = null;
    protected ActionChangeName actionChangeName = null;
    protected ActionQuitRoom actionQuitRoom = null;
    protected ActionChangeRoom actionChangeRoom = null;
    protected ActionPriceChange actionPriceChange = null;
    protected ActionMultiSubmit actionMultiSubmit = null;
    protected ActionImport actionImport = null;
    /**
     * output class constructor
     */
    public AbstractBaseTransactionListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBaseTransactionListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvalid
        this.actionInvalid = new ActionInvalid(this);
        getActionManager().registerAction("actionInvalid", actionInvalid);
         this.actionInvalid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBill
        this.actionReceiveBill = new ActionReceiveBill(this);
        getActionManager().registerAction("actionReceiveBill", actionReceiveBill);
         this.actionReceiveBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeName
        this.actionChangeName = new ActionChangeName(this);
        getActionManager().registerAction("actionChangeName", actionChangeName);
         this.actionChangeName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuitRoom
        this.actionQuitRoom = new ActionQuitRoom(this);
        getActionManager().registerAction("actionQuitRoom", actionQuitRoom);
         this.actionQuitRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeRoom
        this.actionChangeRoom = new ActionChangeRoom(this);
        getActionManager().registerAction("actionChangeRoom", actionChangeRoom);
         this.actionChangeRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPriceChange
        this.actionPriceChange = new ActionPriceChange(this);
        getActionManager().registerAction("actionPriceChange", actionPriceChange);
         this.actionPriceChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMultiSubmit
        this.actionMultiSubmit = new ActionMultiSubmit(this);
        getActionManager().registerAction("actionMultiSubmit", actionMultiSubmit);
         this.actionMultiSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnMultiSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInvalid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceiveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSpecialBiz = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemInvalid = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPriceChange = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnMultiSubmit.setName("btnMultiSubmit");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnInvalid.setName("btnInvalid");
        this.btnReceiveBill.setName("btnReceiveBill");
        this.btnSpecialBiz.setName("btnSpecialBiz");
        this.btnImport.setName("btnImport");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemInvalid.setName("menuItemInvalid");
        this.menuItemReceiveBill.setName("menuItemReceiveBill");
        this.menuItemChangeName.setName("menuItemChangeName");
        this.menuItemQuitRoom.setName("menuItemQuitRoom");
        this.menuItemChangeRoom.setName("menuItemChangeRoom");
        this.menuItemPriceChange.setName("menuItemPriceChange");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table></t:Sheet></Table></DocRoot> ";

                this.tblMain.putBindContents("mainQuery",new String[] {"BMCMessage.id"});


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
        // btnMultiSubmit
        this.btnMultiSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionMultiSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMultiSubmit.setText(resHelper.getString("btnMultiSubmit.text"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnInvalid
        this.btnInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInvalid.setText(resHelper.getString("btnInvalid.text"));		
        this.btnInvalid.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // btnReceiveBill
        this.btnReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBill.setText(resHelper.getString("btnReceiveBill.text"));		
        this.btnReceiveBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // btnSpecialBiz		
        this.btnSpecialBiz.setText(resHelper.getString("btnSpecialBiz.text"));		
        this.btnSpecialBiz.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));
        // menuItemInvalid
        this.menuItemInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemInvalid.setText(resHelper.getString("menuItemInvalid.text"));
        // menuItemReceiveBill
        this.menuItemReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBill.setText(resHelper.getString("menuItemReceiveBill.text"));
        // menuItemChangeName
        this.menuItemChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeName.setText(resHelper.getString("menuItemChangeName.text"));
        // menuItemQuitRoom
        this.menuItemQuitRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuitRoom.setText(resHelper.getString("menuItemQuitRoom.text"));
        // menuItemChangeRoom
        this.menuItemChangeRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeRoom.setText(resHelper.getString("menuItemChangeRoom.text"));
        // menuItemPriceChange
        this.menuItemPriceChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionPriceChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPriceChange.setText(resHelper.getString("menuItemPriceChange.text"));
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
        pnlMain.setBounds(new Rectangle(10, 6, 996, 616));
        this.add(pnlMain, new KDLayout.Constraints(10, 6, 996, 616, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        this.menuBar.add(menuWorkFlow);
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
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemInvalid);
        menuBiz.add(menuItemReceiveBill);
        menuBiz.add(menuItemChangeName);
        menuBiz.add(menuItemQuitRoom);
        menuBiz.add(menuItemChangeRoom);
        menuBiz.add(menuItemPriceChange);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnMultiSubmit);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnInvalid);
        this.toolBar.add(btnReceiveBill);
        this.toolBar.add(btnSpecialBiz);
        this.toolBar.add(btnImport);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.BaseTransactionListUIHandler";
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvalid_actionPerformed method
     */
    public void actionInvalid_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBill_actionPerformed method
     */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeName_actionPerformed method
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuitRoom_actionPerformed method
     */
    public void actionQuitRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeRoom_actionPerformed method
     */
    public void actionChangeRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPriceChange_actionPerformed method
     */
    public void actionPriceChange_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMultiSubmit_actionPerformed method
     */
    public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionInvalid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInvalid() {
    	return false;
    }
	public RequestContext prepareActionReceiveBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBill() {
    	return false;
    }
	public RequestContext prepareActionChangeName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeName() {
    	return false;
    }
	public RequestContext prepareActionQuitRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitRoom() {
    	return false;
    }
	public RequestContext prepareActionChangeRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeRoom() {
    	return false;
    }
	public RequestContext prepareActionPriceChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPriceChange() {
    	return false;
    }
	public RequestContext prepareActionMultiSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMultiSubmit() {
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
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionInvalid class
     */     
    protected class ActionInvalid extends ItemAction {     
    
        public ActionInvalid()
        {
            this(null);
        }

        public ActionInvalid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInvalid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvalid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvalid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionInvalid", "actionInvalid_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveBill class
     */     
    protected class ActionReceiveBill extends ItemAction {     
    
        public ActionReceiveBill()
        {
            this(null);
        }

        public ActionReceiveBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionReceiveBill", "actionReceiveBill_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeName class
     */     
    protected class ActionChangeName extends ItemAction {     
    
        public ActionChangeName()
        {
            this(null);
        }

        public ActionChangeName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionChangeName", "actionChangeName_actionPerformed", e);
        }
    }

    /**
     * output ActionQuitRoom class
     */     
    protected class ActionQuitRoom extends ItemAction {     
    
        public ActionQuitRoom()
        {
            this(null);
        }

        public ActionQuitRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionQuitRoom", "actionQuitRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeRoom class
     */     
    protected class ActionChangeRoom extends ItemAction {     
    
        public ActionChangeRoom()
        {
            this(null);
        }

        public ActionChangeRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionChangeRoom", "actionChangeRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionPriceChange class
     */     
    protected class ActionPriceChange extends ItemAction {     
    
        public ActionPriceChange()
        {
            this(null);
        }

        public ActionPriceChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPriceChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPriceChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPriceChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionPriceChange", "actionPriceChange_actionPerformed", e);
        }
    }

    /**
     * output ActionMultiSubmit class
     */     
    protected class ActionMultiSubmit extends ItemAction {     
    
        public ActionMultiSubmit()
        {
            this(null);
        }

        public ActionMultiSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMultiSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionMultiSubmit", "actionMultiSubmit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractBaseTransactionListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BaseTransactionListUI");
    }




}