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
public abstract class AbstractStandardDurationSetUpEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractStandardDurationSetUpEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSaveExit;
    protected com.kingdee.bos.ctrl.swing.KDContainer contStdDuration;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveLine;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblStdDuration;
    protected ActionOK actionOk = null;
    protected ActionSaveAndExit actionSaveAndExit = null;
    protected ActionAddDuration actionAddDuration = null;
    protected ActionRemoveDuration actionRemoveDuration = null;
    /**
     * output class constructor
     */
    public AbstractStandardDurationSetUpEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractStandardDurationSetUpEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionOk
        this.actionOk = new ActionOK(this);
        getActionManager().registerAction("actionOk", actionOk);
         this.actionOk.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveAndExit
        this.actionSaveAndExit = new ActionSaveAndExit(this);
        getActionManager().registerAction("actionSaveAndExit", actionSaveAndExit);
         this.actionSaveAndExit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddDuration
        this.actionAddDuration = new ActionAddDuration(this);
        getActionManager().registerAction("actionAddDuration", actionAddDuration);
         this.actionAddDuration.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveDuration
        this.actionRemoveDuration = new ActionRemoveDuration(this);
        getActionManager().registerAction("actionRemoveDuration", actionRemoveDuration);
         this.actionRemoveDuration.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnSaveExit = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contStdDuration = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblStdDuration = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSeparator2.setName("kDSeparator2");
        this.btnSave.setName("btnSave");
        this.btnSaveExit.setName("btnSaveExit");
        this.contStdDuration.setName("contStdDuration");
        this.btnAddLine.setName("btnAddLine");
        this.btnRemoveLine.setName("btnRemoveLine");
        this.tblStdDuration.setName("tblStdDuration");
        // CoreUI
        // kDSeparator2
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionOk, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // btnSaveExit
        this.btnSaveExit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveAndExit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSaveExit.setText(resHelper.getString("btnSaveExit.text"));
        // contStdDuration
        // btnAddLine
        this.btnAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddDuration, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddLine.setText(resHelper.getString("btnAddLine.text"));
        // btnRemoveLine
        this.btnRemoveLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveDuration, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveLine.setText(resHelper.getString("btnRemoveLine.text"));
        // tblStdDuration
		String tblStdDurationStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"startNode\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"endNode\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"standardDuration\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{startNode}</t:Cell><t:Cell>$Resource{endNode}</t:Cell><t:Cell>$Resource{standardDuration}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblStdDuration.setFormatXml(resHelper.translateString("tblStdDuration",tblStdDurationStrXML));

        

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
        this.setBounds(new Rectangle(10, 10, 580, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 580, 300));
        kDSeparator2.setBounds(new Rectangle(-3, 270, 584, 8));
        this.add(kDSeparator2, new KDLayout.Constraints(-3, 270, 584, 8, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSave.setBounds(new Rectangle(365, 274, 95, 21));
        this.add(btnSave, new KDLayout.Constraints(365, 274, 95, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSaveExit.setBounds(new Rectangle(478, 274, 95, 21));
        this.add(btnSaveExit, new KDLayout.Constraints(478, 274, 95, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStdDuration.setBounds(new Rectangle(9, 9, 564, 249));
        this.add(contStdDuration, new KDLayout.Constraints(9, 9, 564, 249, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddLine.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnAddLine, new KDLayout.Constraints(10, 10, 30, 30, 0));
        btnRemoveLine.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnRemoveLine, new KDLayout.Constraints(10, 10, 30, 30, 0));
        //contStdDuration
contStdDuration.getContentPane().setLayout(new BorderLayout(0, 0));        contStdDuration.getContentPane().add(tblStdDuration, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.StandardDurationSetUpEditUIHandler";
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
     * output actionOK_actionPerformed method
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveAndExit_actionPerformed method
     */
    public void actionSaveAndExit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddDuration_actionPerformed method
     */
    public void actionAddDuration_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveDuration_actionPerformed method
     */
    public void actionRemoveDuration_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionSaveAndExit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveAndExit() {
    	return false;
    }
	public RequestContext prepareActionAddDuration(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddDuration() {
    	return false;
    }
	public RequestContext prepareActionRemoveDuration(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveDuration() {
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
            innerActionPerformed("eas", AbstractStandardDurationSetUpEditUI.this, "ActionOK", "actionOK_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveAndExit class
     */     
    protected class ActionSaveAndExit extends ItemAction {     
    
        public ActionSaveAndExit()
        {
            this(null);
        }

        public ActionSaveAndExit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSaveAndExit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveAndExit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveAndExit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractStandardDurationSetUpEditUI.this, "ActionSaveAndExit", "actionSaveAndExit_actionPerformed", e);
        }
    }

    /**
     * output ActionAddDuration class
     */     
    protected class ActionAddDuration extends ItemAction {     
    
        public ActionAddDuration()
        {
            this(null);
        }

        public ActionAddDuration(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddDuration.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddDuration.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddDuration.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractStandardDurationSetUpEditUI.this, "ActionAddDuration", "actionAddDuration_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveDuration class
     */     
    protected class ActionRemoveDuration extends ItemAction {     
    
        public ActionRemoveDuration()
        {
            this(null);
        }

        public ActionRemoveDuration(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveDuration.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveDuration.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveDuration.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractStandardDurationSetUpEditUI.this, "ActionRemoveDuration", "actionRemoveDuration_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "StandardDurationSetUpEditUI");
    }




}