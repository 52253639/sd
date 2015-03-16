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
public abstract class AbstractPushManageListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPushManageListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPane;
    protected com.kingdee.bos.ctrl.swing.KDPanel pushPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel pushHisEntryPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMainRoom;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer pushContainer;
    protected com.kingdee.bos.ctrl.swing.KDContainer pushEntryContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMainPushHisEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPush;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPull;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPush;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPull;
    protected ActionPush actionPush = null;
    protected ActionPull actionPull = null;
    /**
     * output class constructor
     */
    public AbstractPushManageListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPushManageListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "PushManageQuery");
        //actionPush
        this.actionPush = new ActionPush(this);
        getActionManager().registerAction("actionPush", actionPush);
         this.actionPush.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPull
        this.actionPull = new ActionPull(this);
        getActionManager().registerAction("actionPull", actionPull);
         this.actionPull.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabbedPane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pushPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pushHisEntryPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblMainRoom = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pushContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.pushEntryContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblMainPushHisEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnPush = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPull = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemPush = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPull = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tabbedPane.setName("tabbedPane");
        this.pushPanel.setName("pushPanel");
        this.pushHisEntryPanel.setName("pushHisEntryPanel");
        this.tblMainRoom.setName("tblMainRoom");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.pushContainer.setName("pushContainer");
        this.pushEntryContainer.setName("pushEntryContainer");
        this.tblMainPushHisEntry.setName("tblMainPushHisEntry");
        this.btnPush.setName("btnPush");
        this.btnPull.setName("btnPull");
        this.menuItemPush.setName("menuItemPush");
        this.menuItemPull.setName("menuItemPull");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"sellOrder\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"pushTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"pullTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"pusher\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"orgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"his.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"orgUnit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"ID\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{sellOrder}</t:Cell><t:Cell>$Resource{pushTotal}</t:Cell><t:Cell>$Resource{pullTotal}</t:Cell><t:Cell>$Resource{pusher}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{room.id}</t:Cell><t:Cell>$Resource{his.id}</t:Cell><t:Cell>$Resource{orgUnit.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","sellOrder","pushTotal","pullTotal","pusher.name","orgUnit.name","","","",""});

		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnView.setEnabled(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.btnLocate.setEnabled(false);		
        this.btnLocate.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemEdit.setEnabled(false);		
        this.menuItemRemove.setEnabled(false);		
        this.menuItemView.setEnabled(false);		
        this.menuItemView.setVisible(false);		
        this.menuItemLocate.setEnabled(false);		
        this.menuItemLocate.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.menuBiz.setToolTipText(resHelper.getString("menuBiz.toolTipText"));		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // tabbedPane
        // pushPanel		
        this.pushPanel.setBackground(new java.awt.Color(255,255,255));
        // pushHisEntryPanel
        // tblMainRoom
		String tblMainRoomStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMainRoom.setFormatXml(resHelper.translateString("tblMainRoom",tblMainRoomStrXML));

        

        // kDScrollPane1
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(400);
        // pushContainer
        // pushEntryContainer
        // tblMainPushHisEntry
		String tblMainPushHisEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"openDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"pushDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"pullDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"puller\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"orgUnitName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{openDate}</t:Cell><t:Cell>$Resource{pushDate}</t:Cell><t:Cell>$Resource{pullDate}</t:Cell><t:Cell>$Resource{puller}</t:Cell><t:Cell>$Resource{orgUnitName}</t:Cell><t:Cell>$Resource{room.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMainPushHisEntry.setFormatXml(resHelper.translateString("tblMainPushHisEntry",tblMainPushHisEntryStrXML));

        

        // btnPush
        this.btnPush.setAction((IItemAction)ActionProxyFactory.getProxy(actionPush, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPush.setText(resHelper.getString("btnPush.text"));		
        this.btnPush.setToolTipText(resHelper.getString("btnPush.toolTipText"));
        // btnPull
        this.btnPull.setAction((IItemAction)ActionProxyFactory.getProxy(actionPull, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPull.setText(resHelper.getString("btnPull.text"));		
        this.btnPull.setToolTipText(resHelper.getString("btnPull.toolTipText"));
        // menuItemPush
        this.menuItemPush.setAction((IItemAction)ActionProxyFactory.getProxy(actionPush, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPush.setText(resHelper.getString("menuItemPush.text"));		
        this.menuItemPush.setToolTipText(resHelper.getString("menuItemPush.toolTipText"));		
        this.menuItemPush.setMnemonic(73);
        // menuItemPull
        this.menuItemPull.setAction((IItemAction)ActionProxyFactory.getProxy(actionPull, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPull.setText(resHelper.getString("menuItemPull.text"));		
        this.menuItemPull.setToolTipText(resHelper.getString("menuItemPull.toolTipText"));		
        this.menuItemPull.setMnemonic(79);
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
        pnlMain.add(treeView, "left");
        pnlMain.add(tabbedPane, "right");
        //treeView
        treeView.setTree(treeMain);
        //tabbedPane
        tabbedPane.add(pushPanel, resHelper.getString("pushPanel.constraints"));
        tabbedPane.add(pushHisEntryPanel, resHelper.getString("pushHisEntryPanel.constraints"));
        //pushPanel
        pushPanel.setLayout(new KDLayout());
        pushPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 744, 546));        tblMainRoom.setBounds(new Rectangle(10, 10, 733, 418));
        pushPanel.add(tblMainRoom, new KDLayout.Constraints(10, 10, 733, 418, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(10, 447, 733, 100));
        pushPanel.add(kDScrollPane1, new KDLayout.Constraints(10, 447, 733, 100, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pushHisEntryPanel
pushHisEntryPanel.setLayout(new BorderLayout(0, 0));        pushHisEntryPanel.add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(pushContainer, "top");
        kDSplitPane1.add(pushEntryContainer, "bottom");
        //pushContainer
pushContainer.getContentPane().setLayout(new BorderLayout(0, 0));        pushContainer.getContentPane().add(tblMain, BorderLayout.CENTER);
        //pushEntryContainer
pushEntryContainer.getContentPane().setLayout(new BorderLayout(0, 0));        pushEntryContainer.getContentPane().add(tblMainPushHisEntry, BorderLayout.CENTER);

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
        menuBiz.add(menuItemPush);
        menuBiz.add(menuItemPull);
        menuBiz.add(menuItemCancel);
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
        this.toolBar.add(btnPush);
        this.toolBar.add(btnPull);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PushManageListUIHandler";
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("sellOrder"));
        sic.add(new SelectorItemInfo("pushTotal"));
        sic.add(new SelectorItemInfo("pullTotal"));
        sic.add(new SelectorItemInfo("pusher.name"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        return sic;
    }        
    	

    /**
     * output actionPush_actionPerformed method
     */
    public void actionPush_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPull_actionPerformed method
     */
    public void actionPull_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPush(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPush() {
    	return false;
    }
	public RequestContext prepareActionPull(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPull() {
    	return false;
    }

    /**
     * output ActionPush class
     */     
    protected class ActionPush extends ItemAction {     
    
        public ActionPush()
        {
            this(null);
        }

        public ActionPush(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPush.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPush.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPush.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPushManageListUI.this, "ActionPush", "actionPush_actionPerformed", e);
        }
    }

    /**
     * output ActionPull class
     */     
    protected class ActionPull extends ItemAction {     
    
        public ActionPull()
        {
            this(null);
        }

        public ActionPull(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPull.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPull.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPull.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPushManageListUI.this, "ActionPull", "actionPull_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PushManageListUI");
    }




}