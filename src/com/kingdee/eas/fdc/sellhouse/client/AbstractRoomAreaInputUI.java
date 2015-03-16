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
public abstract class AbstractRoomAreaInputUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomAreaInputUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAreaAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActualAreaAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelAreaAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelActualAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAreaAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemActualAreaAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuCancelAreaAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuCancelActualAudit;
    protected ActionSubmit actionSubmit = null;
    protected ActionImportExcel actionImportExcel = null;
    protected ActionAreaAudit actionAreaAudit = null;
    protected ActionActualAreaAudit actionActualAreaAudit = null;
    protected ActionCancelAreaAudit actionCancelAreaAudit = null;
    protected ActionCancelActualAudit actionCancelActualAudit = null;
    /**
     * output class constructor
     */
    public AbstractRoomAreaInputUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomAreaInputUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomQuery");
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportExcel
        this.actionImportExcel = new ActionImportExcel(this);
        getActionManager().registerAction("actionImportExcel", actionImportExcel);
         this.actionImportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAreaAudit
        this.actionAreaAudit = new ActionAreaAudit(this);
        getActionManager().registerAction("actionAreaAudit", actionAreaAudit);
         this.actionAreaAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionActualAreaAudit
        this.actionActualAreaAudit = new ActionActualAreaAudit(this);
        getActionManager().registerAction("actionActualAreaAudit", actionActualAreaAudit);
         this.actionActualAreaAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelAreaAudit
        this.actionCancelAreaAudit = new ActionCancelAreaAudit(this);
        getActionManager().registerAction("actionCancelAreaAudit", actionCancelAreaAudit);
         this.actionCancelAreaAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelActualAudit
        this.actionCancelActualAudit = new ActionCancelActualAudit(this);
        getActionManager().registerAction("actionCancelActualAudit", actionCancelActualAudit);
         this.actionCancelActualAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAreaAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnActualAreaAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelAreaAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelActualAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAreaAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemActualAreaAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuCancelAreaAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuCancelActualAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnSubmit.setName("btnSubmit");
        this.btnImportExcel.setName("btnImportExcel");
        this.btnAreaAudit.setName("btnAreaAudit");
        this.btnActualAreaAudit.setName("btnActualAreaAudit");
        this.btnCancelAreaAudit.setName("btnCancelAreaAudit");
        this.btnCancelActualAudit.setName("btnCancelActualAudit");
        this.menuItemImportExcel.setName("menuItemImportExcel");
        this.menuItemAreaAudit.setName("menuItemAreaAudit");
        this.menuItemActualAreaAudit.setName("menuItemActualAreaAudit");
        this.menuCancelAreaAudit.setName("menuCancelAreaAudit");
        this.menuCancelActualAudit.setName("menuCancelActualAudit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"subArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomModel.roomModelType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"tenancyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"isAreaAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"actualBuildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"isActualAreaAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{subArea}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{roomModel.roomModelType.name}</t:Cell><t:Cell>$Resource{tenancyArea}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{isAreaAudited}</t:Cell><t:Cell>$Resource{actualBuildArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{isActualAreaAudited}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","",""});

		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // btnSubmit
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // btnImportExcel
        this.btnImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));		
        this.btnImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // btnAreaAudit
        this.btnAreaAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAreaAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAreaAudit.setText(resHelper.getString("btnAreaAudit.text"));		
        this.btnAreaAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnActualAreaAudit
        this.btnActualAreaAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionActualAreaAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnActualAreaAudit.setText(resHelper.getString("btnActualAreaAudit.text"));		
        this.btnActualAreaAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
        // btnCancelAreaAudit
        this.btnCancelAreaAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelAreaAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelAreaAudit.setText(resHelper.getString("btnCancelAreaAudit.text"));		
        this.btnCancelAreaAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_undo"));
        // btnCancelActualAudit
        this.btnCancelActualAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelActualAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelActualAudit.setText(resHelper.getString("btnCancelActualAudit.text"));		
        this.btnCancelActualAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_undo"));
        // menuItemImportExcel
        this.menuItemImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportExcel.setText(resHelper.getString("menuItemImportExcel.text"));		
        this.menuItemImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // menuItemAreaAudit
        this.menuItemAreaAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAreaAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAreaAudit.setText(resHelper.getString("menuItemAreaAudit.text"));		
        this.menuItemAreaAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemActualAreaAudit
        this.menuItemActualAreaAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionActualAreaAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemActualAreaAudit.setText(resHelper.getString("menuItemActualAreaAudit.text"));		
        this.menuItemActualAreaAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
        // menuCancelAreaAudit
        this.menuCancelAreaAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelAreaAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuCancelAreaAudit.setText(resHelper.getString("menuCancelAreaAudit.text"));		
        this.menuCancelAreaAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_undo"));
        // menuCancelActualAudit
        this.menuCancelActualAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelActualAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuCancelActualAudit.setText(resHelper.getString("menuCancelActualAudit.text"));		
        this.menuCancelActualAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_undo"));
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
        menuEdit.add(menuItemImportExcel);
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
        menuBiz.add(menuItemAreaAudit);
        menuBiz.add(menuItemActualAreaAudit);
        menuBiz.add(menuCancelAreaAudit);
        menuBiz.add(menuCancelActualAudit);
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
        this.toolBar.add(btnSubmit);
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
        this.toolBar.add(btnImportExcel);
        this.toolBar.add(btnAreaAudit);
        this.toolBar.add(btnActualAreaAudit);
        this.toolBar.add(btnCancelAreaAudit);
        this.toolBar.add(btnCancelActualAudit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomAreaInputUIHandler";
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
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
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
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportExcel_actionPerformed method
     */
    public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAreaAudit_actionPerformed method
     */
    public void actionAreaAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionActualAreaAudit_actionPerformed method
     */
    public void actionActualAreaAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelAreaAudit_actionPerformed method
     */
    public void actionCancelAreaAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelActualAudit_actionPerformed method
     */
    public void actionCancelActualAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionImportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportExcel() {
    	return false;
    }
	public RequestContext prepareActionAreaAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAreaAudit() {
    	return false;
    }
	public RequestContext prepareActionActualAreaAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionActualAreaAudit() {
    	return false;
    }
	public RequestContext prepareActionCancelAreaAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelAreaAudit() {
    	return false;
    }
	public RequestContext prepareActionCancelActualAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelActualAudit() {
    	return false;
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionImportExcel class
     */     
    protected class ActionImportExcel extends ItemAction {     
    
        public ActionImportExcel()
        {
            this(null);
        }

        public ActionImportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl K"));
            _tempStr = resHelper.getString("ActionImportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputUI.this, "ActionImportExcel", "actionImportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionAreaAudit class
     */     
    protected class ActionAreaAudit extends ItemAction {     
    
        public ActionAreaAudit()
        {
            this(null);
        }

        public ActionAreaAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAreaAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAreaAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAreaAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputUI.this, "ActionAreaAudit", "actionAreaAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionActualAreaAudit class
     */     
    protected class ActionActualAreaAudit extends ItemAction {     
    
        public ActionActualAreaAudit()
        {
            this(null);
        }

        public ActionActualAreaAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionActualAreaAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionActualAreaAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionActualAreaAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputUI.this, "ActionActualAreaAudit", "actionActualAreaAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelAreaAudit class
     */     
    protected class ActionCancelAreaAudit extends ItemAction {     
    
        public ActionCancelAreaAudit()
        {
            this(null);
        }

        public ActionCancelAreaAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelAreaAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelAreaAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelAreaAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputUI.this, "ActionCancelAreaAudit", "actionCancelAreaAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelActualAudit class
     */     
    protected class ActionCancelActualAudit extends ItemAction {     
    
        public ActionCancelActualAudit()
        {
            this(null);
        }

        public ActionCancelActualAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelActualAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelActualAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelActualAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputUI.this, "ActionCancelActualAudit", "actionCancelActualAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomAreaInputUI");
    }




}