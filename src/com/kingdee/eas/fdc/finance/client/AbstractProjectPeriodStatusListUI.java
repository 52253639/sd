/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractProjectPeriodStatusListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectPeriodStatusListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemColseProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCloseInitAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCloseProject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCloseAll;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnCloseInitAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnCloseProject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnCloseAll;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnColseProject;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected ActionCloseInit actionCloseInit = null;
    protected ActionCloseProject actionCloseProject = null;
    protected ActionCloseInitAll actionCloseInitAll = null;
    protected ActionUnCloseInit actionUnCloseInit = null;
    protected ActionUnCloseProject actionUnCloseProject = null;
    protected ActionUnCloseInitAll actionUnCloseInitAll = null;
    /**
     * output class constructor
     */
    public AbstractProjectPeriodStatusListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectPeriodStatusListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.finance.app", "ProjectPeriodStatusQuery");
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
        //actionCloseInit
        this.actionCloseInit = new ActionCloseInit(this);
        getActionManager().registerAction("actionCloseInit", actionCloseInit);
         this.actionCloseInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCloseProject
        this.actionCloseProject = new ActionCloseProject(this);
        getActionManager().registerAction("actionCloseProject", actionCloseProject);
         this.actionCloseProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCloseInitAll
        this.actionCloseInitAll = new ActionCloseInitAll(this);
        getActionManager().registerAction("actionCloseInitAll", actionCloseInitAll);
         this.actionCloseInitAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnCloseInit
        this.actionUnCloseInit = new ActionUnCloseInit(this);
        getActionManager().registerAction("actionUnCloseInit", actionUnCloseInit);
         this.actionUnCloseInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnCloseProject
        this.actionUnCloseProject = new ActionUnCloseProject(this);
        getActionManager().registerAction("actionUnCloseProject", actionUnCloseProject);
         this.actionUnCloseProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnCloseInitAll
        this.actionUnCloseInitAll = new ActionUnCloseInitAll(this);
        getActionManager().registerAction("actionUnCloseInitAll", actionUnCloseInitAll);
         this.actionUnCloseInitAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemColseProject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnCloseInitAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCloseProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCloseAll = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnUnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnCloseInitAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnCloseProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemUnClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnCloseAll = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnColseProject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnClose.setName("btnClose");
        this.menuItemClose.setName("menuItemClose");
        this.menuItemColseProject.setName("menuItemColseProject");
        this.btnCloseInitAll.setName("btnCloseInitAll");
        this.btnCloseProject.setName("btnCloseProject");
        this.menuItemCloseAll.setName("menuItemCloseAll");
        this.kDSeparator3.setName("kDSeparator3");
        this.btnUnClose.setName("btnUnClose");
        this.btnUnCloseInitAll.setName("btnUnCloseInitAll");
        this.btnUnCloseProject.setName("btnUnCloseProject");
        this.menuItemUnClose.setName("menuItemUnClose");
        this.menuItemUnCloseAll.setName("menuItemUnCloseAll");
        this.menuItemUnColseProject.setName("menuItemUnColseProject");
        this.kDSeparator4.setName("kDSeparator4");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"project.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project.startDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project.isEnabled\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isClosed\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startPeriod.number\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costPeriod.number\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isCostFreeze\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isCostEnd\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"finacialPeriod.number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinaclaFreeze\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinacialEnd\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEnd\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"orgUnit.name\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project.name}</t:Cell><t:Cell>$Resource{project.startDate}</t:Cell><t:Cell>$Resource{project.isEnabled}</t:Cell><t:Cell>$Resource{isClosed}</t:Cell><t:Cell>$Resource{startPeriod.number}</t:Cell><t:Cell>$Resource{costPeriod.number}</t:Cell><t:Cell>$Resource{isCostFreeze}</t:Cell><t:Cell>$Resource{isCostEnd}</t:Cell><t:Cell>$Resource{finacialPeriod.number}</t:Cell><t:Cell>$Resource{isFinaclaFreeze}</t:Cell><t:Cell>$Resource{isFinacialEnd}</t:Cell><t:Cell>$Resource{isEnd}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{project.name_Row2}</t:Cell><t:Cell>$Resource{project.startDate_Row2}</t:Cell><t:Cell>$Resource{project.isEnabled_Row2}</t:Cell><t:Cell>$Resource{isClosed_Row2}</t:Cell><t:Cell>$Resource{startPeriod.number_Row2}</t:Cell><t:Cell>$Resource{costPeriod.number_Row2}</t:Cell><t:Cell>$Resource{isCostFreeze_Row2}</t:Cell><t:Cell>$Resource{isCostEnd_Row2}</t:Cell><t:Cell>$Resource{finacialPeriod.number_Row2}</t:Cell><t:Cell>$Resource{isFinaclaFreeze_Row2}</t:Cell><t:Cell>$Resource{isFinacialEnd_Row2}</t:Cell><t:Cell>$Resource{isEnd_Row2}</t:Cell><t:Cell>$Resource{orgUnit.name_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"0\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"12\" t:bottom=\"1\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"1\" t:right=\"13\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","project.name","project.startDate","project.isEnabled","isClosed","startPeriod.number","costPeriod.number","isCostFreeze","isCostEnd","finacialPeriod.number","isFinaclaFreeze","isFinacialEnd","isEnd","orgUnit.name"});


        this.treeMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    treeMain_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));		
        this.btnClose.setToolTipText(resHelper.getString("btnClose.toolTipText"));
        // menuItemClose
        this.menuItemClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClose.setText(resHelper.getString("menuItemClose.text"));		
        this.menuItemClose.setToolTipText(resHelper.getString("menuItemClose.toolTipText"));
        // menuItemColseProject
        this.menuItemColseProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemColseProject.setText(resHelper.getString("menuItemColseProject.text"));		
        this.menuItemColseProject.setToolTipText(resHelper.getString("menuItemColseProject.toolTipText"));
        // btnCloseInitAll
        this.btnCloseInitAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseInitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCloseInitAll.setText(resHelper.getString("btnCloseInitAll.text"));		
        this.btnCloseInitAll.setToolTipText(resHelper.getString("btnCloseInitAll.toolTipText"));
        // btnCloseProject
        this.btnCloseProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCloseProject.setText(resHelper.getString("btnCloseProject.text"));		
        this.btnCloseProject.setToolTipText(resHelper.getString("btnCloseProject.toolTipText"));
        // menuItemCloseAll
        this.menuItemCloseAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseInitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCloseAll.setText(resHelper.getString("menuItemCloseAll.text"));		
        this.menuItemCloseAll.setToolTipText(resHelper.getString("menuItemCloseAll.toolTipText"));
        // kDSeparator3
        // btnUnClose
        this.btnUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnClose.setText(resHelper.getString("btnUnClose.text"));		
        this.btnUnClose.setToolTipText(resHelper.getString("btnUnClose.toolTipText"));
        // btnUnCloseInitAll
        this.btnUnCloseInitAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseInitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnCloseInitAll.setText(resHelper.getString("btnUnCloseInitAll.text"));		
        this.btnUnCloseInitAll.setToolTipText(resHelper.getString("btnUnCloseInitAll.toolTipText"));
        // btnUnCloseProject
        this.btnUnCloseProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnCloseProject.setText(resHelper.getString("btnUnCloseProject.text"));		
        this.btnUnCloseProject.setToolTipText(resHelper.getString("btnUnCloseProject.toolTipText"));
        // menuItemUnClose
        this.menuItemUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnClose.setText(resHelper.getString("menuItemUnClose.text"));		
        this.menuItemUnClose.setToolTipText(resHelper.getString("menuItemUnClose.toolTipText"));
        // menuItemUnCloseAll
        this.menuItemUnCloseAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseInitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnCloseAll.setText(resHelper.getString("menuItemUnCloseAll.text"));		
        this.menuItemUnCloseAll.setToolTipText(resHelper.getString("menuItemUnCloseAll.toolTipText"));
        // menuItemUnColseProject
        this.menuItemUnColseProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnColseProject.setText(resHelper.getString("menuItemUnColseProject.text"));		
        this.menuItemUnColseProject.setToolTipText(resHelper.getString("menuItemUnColseProject.toolTipText"));
        // kDSeparator4
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
        menuBiz.add(menuItemClose);
        menuBiz.add(menuItemUnClose);
        menuBiz.add(menuItemCloseAll);
        menuBiz.add(menuItemUnCloseAll);
        menuBiz.add(kDSeparator3);
        menuBiz.add(menuItemColseProject);
        menuBiz.add(menuItemUnColseProject);
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
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnUnClose);
        this.toolBar.add(btnCloseInitAll);
        this.toolBar.add(btnUnCloseInitAll);
        this.toolBar.add(kDSeparator4);
        this.toolBar.add(btnCloseProject);
        this.toolBar.add(btnUnCloseProject);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusListUIHandler";
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
     * output treeMain_mouseClicked method
     */
    protected void treeMain_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("isClosed"));
        sic.add(new SelectorItemInfo("isCostFreeze"));
        sic.add(new SelectorItemInfo("isFinaclaFreeze"));
        sic.add(new SelectorItemInfo("isCostEnd"));
        sic.add(new SelectorItemInfo("isFinacialEnd"));
        sic.add(new SelectorItemInfo("isEnd"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("costPeriod.number"));
        sic.add(new SelectorItemInfo("finacialPeriod.number"));
        sic.add(new SelectorItemInfo("startPeriod.number"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("project.startDate"));
        sic.add(new SelectorItemInfo("project.isEnabled"));
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
     * output actionCloseInit_actionPerformed method
     */
    public void actionCloseInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCloseProject_actionPerformed method
     */
    public void actionCloseProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCloseInitAll_actionPerformed method
     */
    public void actionCloseInitAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnCloseInit_actionPerformed method
     */
    public void actionUnCloseInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnCloseProject_actionPerformed method
     */
    public void actionUnCloseProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnCloseInitAll_actionPerformed method
     */
    public void actionUnCloseInitAll_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCloseInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCloseInit() {
    	return false;
    }
	public RequestContext prepareActionCloseProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCloseProject() {
    	return false;
    }
	public RequestContext prepareActionCloseInitAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCloseInitAll() {
    	return false;
    }
	public RequestContext prepareActionUnCloseInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnCloseInit() {
    	return false;
    }
	public RequestContext prepareActionUnCloseProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnCloseProject() {
    	return false;
    }
	public RequestContext prepareActionUnCloseInitAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnCloseInitAll() {
    	return false;
    }

    /**
     * output ActionCloseInit class
     */     
    protected class ActionCloseInit extends ItemAction {     
    
        public ActionCloseInit()
        {
            this(null);
        }

        public ActionCloseInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shif C"));
            _tempStr = resHelper.getString("ActionCloseInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectPeriodStatusListUI.this, "ActionCloseInit", "actionCloseInit_actionPerformed", e);
        }
    }

    /**
     * output ActionCloseProject class
     */     
    protected class ActionCloseProject extends ItemAction {     
    
        public ActionCloseProject()
        {
            this(null);
        }

        public ActionCloseProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl J"));
            _tempStr = resHelper.getString("ActionCloseProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectPeriodStatusListUI.this, "ActionCloseProject", "actionCloseProject_actionPerformed", e);
        }
    }

    /**
     * output ActionCloseInitAll class
     */     
    protected class ActionCloseInitAll extends ItemAction {     
    
        public ActionCloseInitAll()
        {
            this(null);
        }

        public ActionCloseInitAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
            _tempStr = resHelper.getString("ActionCloseInitAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseInitAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseInitAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectPeriodStatusListUI.this, "ActionCloseInitAll", "actionCloseInitAll_actionPerformed", e);
        }
    }

    /**
     * output ActionUnCloseInit class
     */     
    protected class ActionUnCloseInit extends ItemAction {     
    
        public ActionUnCloseInit()
        {
            this(null);
        }

        public ActionUnCloseInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnCloseInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectPeriodStatusListUI.this, "ActionUnCloseInit", "actionUnCloseInit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnCloseProject class
     */     
    protected class ActionUnCloseProject extends ItemAction {     
    
        public ActionUnCloseProject()
        {
            this(null);
        }

        public ActionUnCloseProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnCloseProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectPeriodStatusListUI.this, "ActionUnCloseProject", "actionUnCloseProject_actionPerformed", e);
        }
    }

    /**
     * output ActionUnCloseInitAll class
     */     
    protected class ActionUnCloseInitAll extends ItemAction {     
    
        public ActionUnCloseInitAll()
        {
            this(null);
        }

        public ActionUnCloseInitAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnCloseInitAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseInitAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseInitAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectPeriodStatusListUI.this, "ActionUnCloseInitAll", "actionUnCloseInitAll_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ProjectPeriodStatusListUI");
    }




}