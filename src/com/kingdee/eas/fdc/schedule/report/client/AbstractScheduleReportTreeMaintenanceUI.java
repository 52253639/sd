/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

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
public abstract class AbstractScheduleReportTreeMaintenanceUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleReportTreeMaintenanceUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer tableContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable treeTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton saveBtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton updateBtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton refreshBtn;
    protected actionSave actionSave = null;
    protected actionUpdate actionUpdate = null;
    protected actioRefresh actioRefresh = null;
    /**
     * output class constructor
     */
    public AbstractScheduleReportTreeMaintenanceUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleReportTreeMaintenanceUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new actionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdate
        this.actionUpdate = new actionUpdate(this);
        getActionManager().registerAction("actionUpdate", actionUpdate);
         this.actionUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actioRefresh
        this.actioRefresh = new actioRefresh(this);
        getActionManager().registerAction("actioRefresh", actioRefresh);
         this.actioRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tableContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.treeTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.saveBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.updateBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.refreshBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tableContainer.setName("tableContainer");
        this.treeTable.setName("treeTable");
        this.saveBtn.setName("saveBtn");
        this.updateBtn.setName("updateBtn");
        this.refreshBtn.setName("refreshBtn");
        // CoreUI
        // tableContainer		
        this.tableContainer.setTitle(resHelper.getString("tableContainer.title"));		
        this.tableContainer.setToolTipText(resHelper.getString("tableContainer.toolTipText"));
        // treeTable
		String treeTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"treeCell\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"parentNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"parentName\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"remark\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell /><t:Cell>$Resource{treeCell}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{parentNumber}</t:Cell><t:Cell>$Resource{parentName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.treeTable.setFormatXml(resHelper.translateString("treeTable",treeTableStrXML));

        

        // saveBtn
        this.saveBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.saveBtn.setText(resHelper.getString("saveBtn.text"));
        // updateBtn
        this.updateBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.updateBtn.setText(resHelper.getString("updateBtn.text"));
        // refreshBtn
        this.refreshBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actioRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.refreshBtn.setText(resHelper.getString("refreshBtn.text"));
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
        tableContainer.setBounds(new Rectangle(1, 1, 1013, 628));
        this.add(tableContainer, new KDLayout.Constraints(1, 1, 1013, 628, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tableContainer
tableContainer.getContentPane().setLayout(new BorderLayout(0, 0));        tableContainer.getContentPane().add(treeTable, BorderLayout.CENTER);

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
        this.toolBar.add(saveBtn);
        this.toolBar.add(updateBtn);
        this.toolBar.add(refreshBtn);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.report.app.ScheduleReportTreeMaintenanceUIHandler";
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
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdate_actionPerformed method
     */
    public void actionUpdate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actioRefresh_actionPerformed method
     */
    public void actioRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSave() {
    	return false;
    }
	public RequestContext prepareactionUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionUpdate() {
    	return false;
    }
	public RequestContext prepareactioRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactioRefresh() {
    	return false;
    }

    /**
     * output actionSave class
     */     
    protected class actionSave extends ItemAction {     
    
        public actionSave()
        {
            this(null);
        }

        public actionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeMaintenanceUI.this, "actionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output actionUpdate class
     */     
    protected class actionUpdate extends ItemAction {     
    
        public actionUpdate()
        {
            this(null);
        }

        public actionUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeMaintenanceUI.this, "actionUpdate", "actionUpdate_actionPerformed", e);
        }
    }

    /**
     * output actioRefresh class
     */     
    protected class actioRefresh extends ItemAction {     
    
        public actioRefresh()
        {
            this(null);
        }

        public actioRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actioRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actioRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actioRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeMaintenanceUI.this, "actioRefresh", "actioRefresh_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.report.client", "ScheduleReportTreeMaintenanceUI");
    }




}