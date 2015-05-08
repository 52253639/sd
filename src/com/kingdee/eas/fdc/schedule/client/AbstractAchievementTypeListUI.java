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
public abstract class AbstractAchievementTypeListUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAchievementTypeListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView2;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTree1;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTree2;
    protected ActionAdd1 actionAdd1 = null;
    protected ActionView1 actionView1 = null;
    protected ActionEdit1 actionEdit1 = null;
    protected ActionDel1 actionDel1 = null;
    protected ActionAdd2 actionAdd2 = null;
    protected ActionView2 actionView2 = null;
    protected ActionEdit2 actionEdit2 = null;
    protected ActionDel2 actionDel2 = null;
    /**
     * output class constructor
     */
    public AbstractAchievementTypeListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAchievementTypeListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.schedule.app", "AchievementTypeQuery");
        //actionAdd1
        this.actionAdd1 = new ActionAdd1(this);
        getActionManager().registerAction("actionAdd1", actionAdd1);
         this.actionAdd1.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionView1
        this.actionView1 = new ActionView1(this);
        getActionManager().registerAction("actionView1", actionView1);
         this.actionView1.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEdit1
        this.actionEdit1 = new ActionEdit1(this);
        getActionManager().registerAction("actionEdit1", actionEdit1);
         this.actionEdit1.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDel1
        this.actionDel1 = new ActionDel1(this);
        getActionManager().registerAction("actionDel1", actionDel1);
         this.actionDel1.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAdd2
        this.actionAdd2 = new ActionAdd2(this);
        getActionManager().registerAction("actionAdd2", actionAdd2);
         this.actionAdd2.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionView2
        this.actionView2 = new ActionView2(this);
        getActionManager().registerAction("actionView2", actionView2);
         this.actionView2.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEdit2
        this.actionEdit2 = new ActionEdit2(this);
        getActionManager().registerAction("actionEdit2", actionEdit2);
         this.actionEdit2.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDel2
        this.actionDel2 = new ActionDel2(this);
        getActionManager().registerAction("actionDel2", actionDel2);
         this.actionDel2.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDTreeView2 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDTree1 = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDTree2 = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDContainer1.setName("kDContainer1");
        this.kDTreeView1.setName("kDTreeView1");
        this.kDTreeView2.setName("kDTreeView2");
        this.kDTree1.setName("kDTree1");
        this.kDTree2.setName("kDTree2");
        // CoreUI		
        this.menuTool.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"stage.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"profession.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"docDirectory.name\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{stage.name}</t:Cell><t:Cell>$Resource{profession.name}</t:Cell><t:Cell>$Resource{docDirectory.name}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"name","number","stage.name","profession.name","docDirectory.name","id"});

		
        this.menuView.setVisible(false);		
        this.menuBiz.setVisible(false);
        // kDSplitPane2		
        this.kDSplitPane2.setDividerLocation(250);
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(280);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDTreeView1		
        this.kDTreeView1.setTitle(resHelper.getString("kDTreeView1.title"));		
        this.kDTreeView1.setShowButton(false);
        // kDTreeView2		
        this.kDTreeView2.setTitle(resHelper.getString("kDTreeView2.title"));		
        this.kDTreeView2.setShowButton(false);
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
        // kDTree2
        this.kDTree2.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kDTree2_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        kDSplitPane2.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(kDSplitPane2, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane2
        kDSplitPane2.add(kDSplitPane1, "left");
        kDSplitPane2.add(kDContainer1, "right");
        //kDSplitPane1
        kDSplitPane1.add(kDTreeView1, "top");
        kDSplitPane1.add(kDTreeView2, "bottom");
        //kDTreeView1
        kDTreeView1.setTree(kDTree1);
        //kDTreeView2
        kDTreeView2.setTree(kDTree2);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);

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
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
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
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.AchievementTypeListUIHandler";
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
     * output kDTree1_valueChanged method
     */
    protected void kDTree1_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output kDTree2_valueChanged method
     */
    protected void kDTree2_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("stage.name"));
        sic.add(new SelectorItemInfo("profession.name"));
        sic.add(new SelectorItemInfo("docDirectory.name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        return sic;
    }        
    	

    /**
     * output actionAdd1_actionPerformed method
     */
    public void actionAdd1_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionView1_actionPerformed method
     */
    public void actionView1_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEdit1_actionPerformed method
     */
    public void actionEdit1_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDel1_actionPerformed method
     */
    public void actionDel1_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAdd2_actionPerformed method
     */
    public void actionAdd2_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionView2_actionPerformed method
     */
    public void actionView2_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEdit2_actionPerformed method
     */
    public void actionEdit2_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDel2_actionPerformed method
     */
    public void actionDel2_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAdd1(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdd1() {
    	return false;
    }
	public RequestContext prepareActionView1(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView1() {
    	return false;
    }
	public RequestContext prepareActionEdit1(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit1() {
    	return false;
    }
	public RequestContext prepareActionDel1(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDel1() {
    	return false;
    }
	public RequestContext prepareActionAdd2(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdd2() {
    	return false;
    }
	public RequestContext prepareActionView2(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView2() {
    	return false;
    }
	public RequestContext prepareActionEdit2(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit2() {
    	return false;
    }
	public RequestContext prepareActionDel2(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDel2() {
    	return false;
    }

    /**
     * output ActionAdd1 class
     */     
    protected class ActionAdd1 extends ItemAction {     
    
        public ActionAdd1()
        {
            this(null);
        }

        public ActionAdd1(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAdd1.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd1.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd1.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionAdd1", "actionAdd1_actionPerformed", e);
        }
    }

    /**
     * output ActionView1 class
     */     
    protected class ActionView1 extends ItemAction {     
    
        public ActionView1()
        {
            this(null);
        }

        public ActionView1(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionView1.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView1.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView1.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionView1", "actionView1_actionPerformed", e);
        }
    }

    /**
     * output ActionEdit1 class
     */     
    protected class ActionEdit1 extends ItemAction {     
    
        public ActionEdit1()
        {
            this(null);
        }

        public ActionEdit1(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEdit1.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit1.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit1.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionEdit1", "actionEdit1_actionPerformed", e);
        }
    }

    /**
     * output ActionDel1 class
     */     
    protected class ActionDel1 extends ItemAction {     
    
        public ActionDel1()
        {
            this(null);
        }

        public ActionDel1(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDel1.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel1.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel1.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionDel1", "actionDel1_actionPerformed", e);
        }
    }

    /**
     * output ActionAdd2 class
     */     
    protected class ActionAdd2 extends ItemAction {     
    
        public ActionAdd2()
        {
            this(null);
        }

        public ActionAdd2(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAdd2.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd2.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd2.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionAdd2", "actionAdd2_actionPerformed", e);
        }
    }

    /**
     * output ActionView2 class
     */     
    protected class ActionView2 extends ItemAction {     
    
        public ActionView2()
        {
            this(null);
        }

        public ActionView2(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionView2.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView2.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView2.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionView2", "actionView2_actionPerformed", e);
        }
    }

    /**
     * output ActionEdit2 class
     */     
    protected class ActionEdit2 extends ItemAction {     
    
        public ActionEdit2()
        {
            this(null);
        }

        public ActionEdit2(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEdit2.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit2.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit2.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionEdit2", "actionEdit2_actionPerformed", e);
        }
    }

    /**
     * output ActionDel2 class
     */     
    protected class ActionDel2 extends ItemAction {     
    
        public ActionDel2()
        {
            this(null);
        }

        public ActionDel2(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDel2.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel2.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel2.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementTypeListUI.this, "ActionDel2", "actionDel2_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "AchievementTypeListUI");
    }




}