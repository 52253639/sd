/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractAchievementManagerListUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAchievementManagerListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTree1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddNew;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuEdit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpdate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDel;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuView;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuSys;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdd;
    protected ActionAdd actionAdd = null;
    protected ActionDel actiondel = null;
    protected ActionView actionView = null;
    protected ActionUpdate actionUpdate = null;
    protected ActionPrint actionPrint = null;
    protected ActionPrintPreview actionPrintPreview = null;
    /**
     * output class constructor
     */
    public AbstractAchievementManagerListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAchievementManagerListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAdd
        this.actionAdd = new ActionAdd(this);
        getActionManager().registerAction("actionAdd", actionAdd);
         this.actionAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actiondel
        this.actiondel = new ActionDel(this);
        getActionManager().registerAction("actiondel", actiondel);
         this.actiondel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionView
        this.actionView = new ActionView(this);
        getActionManager().registerAction("actionView", actionView);
         this.actionView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdate
        this.actionUpdate = new ActionUpdate(this);
        getActionManager().registerAction("actionUpdate", actionUpdate);
         this.actionUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrint
        this.actionPrint = new ActionPrint(this);
        getActionManager().registerAction("actionPrint", actionPrint);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintPreview
        this.actionPrintPreview = new ActionPrintPreview(this);
        getActionManager().registerAction("actionPrintPreview", actionPrintPreview);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTree1 = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnAddNew = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpdate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuEdit = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemView = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpdate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuView = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuSys = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemAdd = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDTreeView1.setName("kDTreeView1");
        this.kDContainer1.setName("kDContainer1");
        this.tblMain.setName("tblMain");
        this.kDTree1.setName("kDTree1");
        this.btnAddNew.setName("btnAddNew");
        this.btnView.setName("btnView");
        this.btnUpdate.setName("btnUpdate");
        this.btnDel.setName("btnDel");
        this.btnPrint.setName("btnPrint");
        this.btnPrintPreview.setName("btnPrintPreview");
        this.menuEdit.setName("menuEdit");
        this.menuItemView.setName("menuItemView");
        this.menuItemUpdate.setName("menuItemUpdate");
        this.menuItemDel.setName("menuItemDel");
        this.menuView.setName("menuView");
        this.menuSys.setName("menuSys");
        this.menuItemAdd.setName("menuItemAdd");
        // CoreUI		
        this.menuTool.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);
        // kDPanel1
        // kDTreeView1		
        this.kDTreeView1.setShowButton(false);		
        this.kDTreeView1.setTitle(resHelper.getString("kDTreeView1.title"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"asID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"asName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell /><t:Cell>$Resource{asID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDTree1
        this.kDTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kDTree1_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAddNew
        this.btnAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNew.setText(resHelper.getString("btnAddNew.text"));
        // btnView
        this.btnView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnView.setText(resHelper.getString("btnView.text"));
        // btnUpdate
        this.btnUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdate.setText(resHelper.getString("btnUpdate.text"));
        // btnDel
        this.btnDel.setAction((IItemAction)ActionProxyFactory.getProxy(actiondel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDel.setText(resHelper.getString("btnDel.text"));
        // btnPrint
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));
        // btnPrintPreview
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));
        // menuEdit		
        this.menuEdit.setText(resHelper.getString("menuEdit.text"));
        // menuItemView
        this.menuItemView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemView.setText(resHelper.getString("menuItemView.text"));
        // menuItemUpdate
        this.menuItemUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUpdate.setText(resHelper.getString("menuItemUpdate.text"));
        // menuItemDel
        this.menuItemDel.setAction((IItemAction)ActionProxyFactory.getProxy(actiondel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDel.setText(resHelper.getString("menuItemDel.text"));
        this.menuItemDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemDel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // menuView		
        this.menuView.setText(resHelper.getString("menuView.text"));
        // menuSys		
        this.menuSys.setText(resHelper.getString("menuSys.text"));
        // menuItemAdd
        this.menuItemAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdd.setText(resHelper.getString("menuItemAdd.text"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 607));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 607, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDPanel1, "right");
        kDSplitPane1.add(kDTreeView1, "left");
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDTreeView1
        kDTreeView1.setTree(kDTree1);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        this.menuBar.add(menuView);
        this.menuBar.add(menuSys);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemView);
        menuEdit.add(menuItemUpdate);
        menuEdit.add(menuItemDel);
        //menuTool
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);
        //menuSys
        menuSys.add(menuItemAdd);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnUpdate);
        this.toolBar.add(btnDel);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.AchievementManagerListUIHandler";
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kDTree1_valueChanged method
     */
    protected void kDTree1_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output menuItemDel_actionPerformed method
     */
    protected void menuItemDel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionAdd_actionPerformed method
     */
    public void actionAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDel_actionPerformed method
     */
    public void actionDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionView_actionPerformed method
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdate_actionPerformed method
     */
    public void actionUpdate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdd() {
    	return false;
    }
	public RequestContext prepareActionDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDel() {
    	return false;
    }
	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView() {
    	return false;
    }
	public RequestContext prepareActionUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdate() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }

    /**
     * output ActionAdd class
     */     
    protected class ActionAdd extends ItemAction {     
    
        public ActionAdd()
        {
            this(null);
        }

        public ActionAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerListUI.this, "ActionAdd", "actionAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionDel class
     */     
    protected class ActionDel extends ItemAction {     
    
        public ActionDel()
        {
            this(null);
        }

        public ActionDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerListUI.this, "ActionDel", "actionDel_actionPerformed", e);
        }
    }

    /**
     * output ActionView class
     */     
    protected class ActionView extends ItemAction {     
    
        public ActionView()
        {
            this(null);
        }

        public ActionView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerListUI.this, "ActionView", "actionView_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdate class
     */     
    protected class ActionUpdate extends ItemAction {     
    
        public ActionUpdate()
        {
            this(null);
        }

        public ActionUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerListUI.this, "ActionUpdate", "actionUpdate_actionPerformed", e);
        }
    }

    /**
     * output ActionPrint class
     */     
    protected class ActionPrint extends ItemAction {     
    
        public ActionPrint()
        {
            this(null);
        }

        public ActionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerListUI.this, "ActionPrint", "actionPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintPreview class
     */     
    protected class ActionPrintPreview extends ItemAction {     
    
        public ActionPrintPreview()
        {
            this(null);
        }

        public ActionPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerListUI.this, "ActionPrintPreview", "actionPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "AchievementManagerListUI");
    }




}