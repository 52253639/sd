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
public abstract class AbstractPlanEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPlanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conState;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSearch;
    protected com.kingdee.bos.ctrl.swing.KDComboBox prmtState;
    protected com.kingdee.bos.ctrl.swing.KDContainer mainContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable mainTable;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProperty;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScheduleRpt;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemScheduleRpt;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProperty;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefresh;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionSearch actionSearch = null;
    protected ActionScheduleReport actionScheduleReport = null;
    protected ActionExportProject actionExportProject = null;
    protected ActionProperty actionProperty = null;
    protected ActionRefresh actionRefresh = null;
    /**
     * output class constructor
     */
    public AbstractPlanEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPlanEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSearch
        this.actionSearch = new ActionSearch(this);
        getActionManager().registerAction("actionSearch", actionSearch);
         this.actionSearch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionScheduleReport
        this.actionScheduleReport = new ActionScheduleReport(this);
        getActionManager().registerAction("actionScheduleReport", actionScheduleReport);
         this.actionScheduleReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportProject
        this.actionExportProject = new ActionExportProject(this);
        getActionManager().registerAction("actionExportProject", actionExportProject);
         this.actionExportProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProperty
        this.actionProperty = new ActionProperty(this);
        getActionManager().registerAction("actionProperty", actionProperty);
         this.actionProperty.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.conProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.mainContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.mainTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnProperty = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnScheduleRpt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemScheduleRpt = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProperty = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRefresh = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.conProject.setName("conProject");
        this.conState.setName("conState");
        this.btnSearch.setName("btnSearch");
        this.prmtState.setName("prmtState");
        this.mainContainer.setName("mainContainer");
        this.mainTable.setName("mainTable");
        this.prmtProject.setName("prmtProject");
        this.btnProperty.setName("btnProperty");
        this.btnScheduleRpt.setName("btnScheduleRpt");
        this.menuItemScheduleRpt.setName("menuItemScheduleRpt");
        this.menuItemProperty.setName("menuItemProperty");
        this.btnRefresh.setName("btnRefresh");
        this.menuItemRefresh.setName("menuItemRefresh");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnAttachment.setVisible(false);
        // conProject		
        this.conProject.setBoundLabelText(resHelper.getString("conProject.boundLabelText"));		
        this.conProject.setBoundLabelUnderline(true);		
        this.conProject.setBoundLabelLength(100);
        // conState		
        this.conState.setBoundLabelText(resHelper.getString("conState.boundLabelText"));		
        this.conState.setBoundLabelUnderline(true);		
        this.conState.setBoundLabelLength(100);
        // btnSearch
        this.btnSearch.setAction((IItemAction)ActionProxyFactory.getProxy(actionSearch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSearch.setText(resHelper.getString("btnSearch.text"));
        // prmtState		
        this.prmtState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.ScheduleTaskStatusEnum").toArray());
        this.prmtState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    prmtState_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // mainContainer		
        this.mainContainer.setTitle(resHelper.getString("mainContainer.title"));		
        this.mainContainer.setPreferredSize(new Dimension(941,700));
        // mainTable		
        this.mainTable.setPreferredSize(new Dimension(955,511));

        

        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setRequired(true);
        // btnProperty
        this.btnProperty.setAction((IItemAction)ActionProxyFactory.getProxy(actionProperty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProperty.setText(resHelper.getString("btnProperty.text"));		
        this.btnProperty.setToolTipText(resHelper.getString("btnProperty.toolTipText"));		
        this.btnProperty.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // btnScheduleRpt
        this.btnScheduleRpt.setAction((IItemAction)ActionProxyFactory.getProxy(actionScheduleReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScheduleRpt.setText(resHelper.getString("btnScheduleRpt.text"));		
        this.btnScheduleRpt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // menuItemScheduleRpt
        this.menuItemScheduleRpt.setAction((IItemAction)ActionProxyFactory.getProxy(actionScheduleReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemScheduleRpt.setText(resHelper.getString("menuItemScheduleRpt.text"));		
        this.menuItemScheduleRpt.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // menuItemProperty
        this.menuItemProperty.setAction((IItemAction)ActionProxyFactory.getProxy(actionProperty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProperty.setText(resHelper.getString("menuItemProperty.text"));		
        this.menuItemProperty.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
        // menuItemRefresh
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setToolTipText(resHelper.getString("menuItemRefresh.toolTipText"));		
        this.menuItemRefresh.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));
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
        conProject.setBounds(new Rectangle(15, 17, 270, 19));
        this.add(conProject, new KDLayout.Constraints(15, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conState.setBounds(new Rectangle(319, 17, 184, 19));
        this.add(conState, new KDLayout.Constraints(319, 17, 184, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSearch.setBounds(new Rectangle(581, 17, 81, 19));
        this.add(btnSearch, new KDLayout.Constraints(581, 17, 81, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        mainContainer.setBounds(new Rectangle(18, 52, 966, 567));
        this.add(mainContainer, new KDLayout.Constraints(18, 52, 966, 567, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conProject
        conProject.setBoundEditor(prmtProject);
        //conState
        conState.setBoundEditor(prmtState);
        //mainContainer
        mainContainer.getContentPane().setLayout(new KDLayout());
        mainContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(18, 52, 966, 567));        mainTable.setBounds(new Rectangle(3, 6, 961, 537));
        mainContainer.getContentPane().add(mainTable, new KDLayout.Constraints(3, 6, 961, 537, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuItemRefresh);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemScheduleRpt);
        menuBiz.add(menuItemProperty);
        //menuTool
        menuTool.add(menuItemMsgFormat);
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
        this.toolBar.add(btnScheduleRpt);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnProperty);
        this.toolBar.add(btnRefresh);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.PlanEditUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output prmtState_actionPerformed method
     */
    protected void prmtState_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionSearch_actionPerformed method
     */
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionScheduleReport_actionPerformed method
     */
    public void actionScheduleReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportProject_actionPerformed method
     */
    public void actionExportProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProperty_actionPerformed method
     */
    public void actionProperty_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSearch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSearch() {
    	return false;
    }
	public RequestContext prepareActionScheduleReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionScheduleReport() {
    	return false;
    }
	public RequestContext prepareActionExportProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportProject() {
    	return false;
    }
	public RequestContext prepareActionProperty(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProperty() {
    	return false;
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }

    /**
     * output ActionSearch class
     */     
    protected class ActionSearch extends ItemAction {     
    
        public ActionSearch()
        {
            this(null);
        }

        public ActionSearch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSearch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSearch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSearch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPlanEditUI.this, "ActionSearch", "actionSearch_actionPerformed", e);
        }
    }

    /**
     * output ActionScheduleReport class
     */     
    protected class ActionScheduleReport extends ItemAction {     
    
        public ActionScheduleReport()
        {
            this(null);
        }

        public ActionScheduleReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionScheduleReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScheduleReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScheduleReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPlanEditUI.this, "ActionScheduleReport", "actionScheduleReport_actionPerformed", e);
        }
    }

    /**
     * output ActionExportProject class
     */     
    protected class ActionExportProject extends ItemAction {     
    
        public ActionExportProject()
        {
            this(null);
        }

        public ActionExportProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExportProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPlanEditUI.this, "ActionExportProject", "actionExportProject_actionPerformed", e);
        }
    }

    /**
     * output ActionProperty class
     */     
    protected class ActionProperty extends ItemAction {     
    
        public ActionProperty()
        {
            this(null);
        }

        public ActionProperty(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProperty.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProperty.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProperty.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPlanEditUI.this, "ActionProperty", "actionProperty_actionPerformed", e);
        }
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPlanEditUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "PlanEditUI");
    }




}