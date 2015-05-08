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
public abstract class AbstractF7FDCWBSSelectUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractF7FDCWBSSelectUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane mainSplitPanel;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDContainer conTop;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane tableSplitPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlTop;
    protected com.kingdee.bos.ctrl.swing.KDContainer conBottom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTop;
    protected com.kingdee.bos.ctrl.swing.KDContainer conTree;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBottom;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected ActionOK actionOK = null;
    protected ActionCancel actionCancel = null;
    protected ActionSelect actionSelect = null;
    /**
     * output class constructor
     */
    public AbstractF7FDCWBSSelectUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractF7FDCWBSSelectUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionOK
        this.actionOK = new ActionOK(this);
        getActionManager().registerAction("actionOK", actionOK);
         this.actionOK.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        this.actionCancel = new ActionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelect
        this.actionSelect = new ActionSelect(this);
        getActionManager().registerAction("actionSelect", actionSelect);
         this.actionSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.mainSplitPanel = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.conTop = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tableSplitPanel = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pnlTop = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conBottom = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTop = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conTree = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblBottom = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.mainSplitPanel.setName("mainSplitPanel");
        this.btnOK.setName("btnOK");
        this.btnCancel.setName("btnCancel");
        this.conTop.setName("conTop");
        this.tableSplitPanel.setName("tableSplitPanel");
        this.pnlTop.setName("pnlTop");
        this.conBottom.setName("conBottom");
        this.tblTop.setName("tblTop");
        this.conTree.setName("conTree");
        this.tblBottom.setName("tblBottom");
        this.treeMain.setName("treeMain");
        this.btnSelect.setName("btnSelect");
        // CoreUI
        // mainSplitPanel		
        this.mainSplitPanel.setDividerLocation(240);
        // btnOK
        this.btnOK.setAction((IItemAction)ActionProxyFactory.getProxy(actionOK, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // conTop		
        this.conTop.setEnableActive(false);		
        this.conTop.setTitle(resHelper.getString("conTop.title"));
        // tableSplitPanel		
        this.tableSplitPanel.setOrientation(0);		
        this.tableSplitPanel.setDividerLocation(280);
        // pnlTop
        // conBottom		
        this.conBottom.setEnableActive(false);		
        this.conBottom.setTitle(resHelper.getString("conBottom.title"));
        // tblTop
		String tblTopStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isSelect\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"wbs.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"wbs.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isSelect}</t:Cell><t:Cell>$Resource{wbs.number}</t:Cell><t:Cell>$Resource{wbs.name}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTop.setFormatXml(resHelper.translateString("tblTop",tblTopStrXML));

        

        // conTree		
        this.conTree.setEnableActive(false);		
        this.conTree.setTitle(resHelper.getString("conTree.title"));
        // tblBottom
		String tblBottomStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"wbs.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"wbs.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{wbs.number}</t:Cell><t:Cell>$Resource{wbs.name}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblBottom.setFormatXml(resHelper.translateString("tblBottom",tblBottomStrXML));

        

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
        // btnSelect
        this.btnSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelect.setText(resHelper.getString("btnSelect.text"));		
        this.btnSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_degrade"));
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
        this.setLayout(null);
        mainSplitPanel.setBounds(new Rectangle(10, 10, 780, 555));
        this.add(mainSplitPanel, null);
        btnOK.setBounds(new Rectangle(634, 574, 73, 21));
        this.add(btnOK, null);
        btnCancel.setBounds(new Rectangle(717, 574, 73, 21));
        this.add(btnCancel, null);
        //mainSplitPanel
        mainSplitPanel.add(tableSplitPanel, "right");
        mainSplitPanel.add(conTree, "left");
        //tableSplitPanel
        tableSplitPanel.add(pnlTop, "top");
        tableSplitPanel.add(conBottom, "bottom");
        //pnlTop
        pnlTop.setLayout(new KDLayout());
        pnlTop.putClientProperty("OriginalBounds", new Rectangle(0, 0, 528, 279));        conTop.setBounds(new Rectangle(0, 0, 530, 250));
        pnlTop.add(conTop, new KDLayout.Constraints(0, 0, 530, 250, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSelect.setBounds(new Rectangle(250, 255, 70, 19));
        pnlTop.add(btnSelect, new KDLayout.Constraints(250, 255, 70, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conTop
conTop.getContentPane().setLayout(new BorderLayout(0, 0));        conTop.getContentPane().add(tblTop, BorderLayout.CENTER);
        //conBottom
conBottom.getContentPane().setLayout(new BorderLayout(0, 0));        conBottom.getContentPane().add(tblBottom, BorderLayout.CENTER);
        //conTree
conTree.getContentPane().setLayout(new BorderLayout(0, 0));        conTree.getContentPane().add(treeMain, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.F7FDCWBSSelectUIHandler";
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
     * output actionOK_actionPerformed method
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelect_actionPerformed method
     */
    public void actionSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionOK(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOK() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }
	public RequestContext prepareActionSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelect() {
    	return false;
    }

    /**
     * output ActionOK class
     */     
    protected class ActionOK extends ItemAction {     
    
        public ActionOK()
        {
            this(null);
        }

        public ActionOK(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionOK.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7FDCWBSSelectUI.this, "ActionOK", "actionOK_actionPerformed", e);
        }
    }

    /**
     * output ActionCancel class
     */     
    protected class ActionCancel extends ItemAction {     
    
        public ActionCancel()
        {
            this(null);
        }

        public ActionCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7FDCWBSSelectUI.this, "ActionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output ActionSelect class
     */     
    protected class ActionSelect extends ItemAction {     
    
        public ActionSelect()
        {
            this(null);
        }

        public ActionSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7FDCWBSSelectUI.this, "ActionSelect", "actionSelect_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "F7FDCWBSSelectUI");
    }




}