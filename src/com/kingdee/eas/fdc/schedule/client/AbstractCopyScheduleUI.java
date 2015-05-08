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
public abstract class AbstractCopyScheduleUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCopyScheduleUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrentVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectSpecial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurrentVersion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectSpecial;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected ActionSave actionSave = null;
    protected ActionSaveExit actionSaveExit = null;
    /**
     * output class constructor
     */
    public AbstractCopyScheduleUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCopyScheduleUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveExit
        this.actionSaveExit = new ActionSaveExit(this);
        getActionManager().registerAction("actionSaveExit", actionSaveExit);
         this.actionSaveExit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCurrentVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectSpecial = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtCurrentVersion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProjectSpecial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contCurrentVersion.setName("contCurrentVersion");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contProject.setName("contProject");
        this.contProjectSpecial.setName("contProjectSpecial");
        this.contDescription.setName("contDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnSave.setName("btnSave");
        this.btnCancel.setName("btnCancel");
        this.txtCurrentVersion.setName("txtCurrentVersion");
        this.prmtProject.setName("prmtProject");
        this.prmtProjectSpecial.setName("prmtProjectSpecial");
        this.txtDescription.setName("txtDescription");
        // CoreUI		
        this.setMaximumSize(new Dimension(400,300));		
        this.setMinimumSize(new Dimension(400,300));		
        this.setPreferredSize(new Dimension(400,300));
        // contCurrentVersion		
        this.contCurrentVersion.setBoundLabelText(resHelper.getString("contCurrentVersion.boundLabelText"));		
        this.contCurrentVersion.setBoundLabelUnderline(true);		
        this.contCurrentVersion.setBoundLabelLength(60);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(60);		
        this.contProject.setBoundLabelUnderline(true);
        // contProjectSpecial		
        this.contProjectSpecial.setBoundLabelText(resHelper.getString("contProjectSpecial.boundLabelText"));		
        this.contProjectSpecial.setBoundLabelUnderline(true);		
        this.contProjectSpecial.setBoundLabelLength(60);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));
        // kDScrollPane1
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveExit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // txtCurrentVersion
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$name$");
        // prmtProjectSpecial		
        this.prmtProjectSpecial.setQueryInfo("com.kingdee.eas.fdc.schedule.app.ProjectSpecialQuery");		
        this.prmtProjectSpecial.setDisplayFormat("$name$");		
        this.prmtProjectSpecial.setEditFormat("$name$");
        // txtDescription
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
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 300));
        contCurrentVersion.setBounds(new Rectangle(26, 17, 350, 19));
        this.add(contCurrentVersion, new KDLayout.Constraints(26, 17, 350, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(26, 46, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(26, 46, 270, 19, 0));
        contProject.setBounds(new Rectangle(26, 75, 350, 19));
        this.add(contProject, new KDLayout.Constraints(26, 75, 350, 19, 0));
        contProjectSpecial.setBounds(new Rectangle(26, 104, 350, 19));
        this.add(contProjectSpecial, new KDLayout.Constraints(26, 104, 350, 19, 0));
        contDescription.setBounds(new Rectangle(26, 133, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(26, 133, 270, 19, 0));
        kDScrollPane1.setBounds(new Rectangle(26, 156, 350, 107));
        this.add(kDScrollPane1, new KDLayout.Constraints(26, 156, 350, 107, 0));
        btnSave.setBounds(new Rectangle(191, 271, 73, 21));
        this.add(btnSave, new KDLayout.Constraints(191, 271, 73, 21, 0));
        btnCancel.setBounds(new Rectangle(296, 271, 73, 21));
        this.add(btnCancel, new KDLayout.Constraints(296, 271, 73, 21, 0));
        //contCurrentVersion
        contCurrentVersion.setBoundEditor(txtCurrentVersion);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contProjectSpecial
        contProjectSpecial.setBoundEditor(prmtProjectSpecial);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.CopyScheduleUIHandler";
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
     * output actionSaveExit_actionPerformed method
     */
    public void actionSaveExit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	public RequestContext prepareActionSaveExit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveExit() {
    	return false;
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCopyScheduleUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveExit class
     */     
    protected class ActionSaveExit extends ItemAction {     
    
        public ActionSaveExit()
        {
            this(null);
        }

        public ActionSaveExit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSaveExit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveExit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveExit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCopyScheduleUI.this, "ActionSaveExit", "actionSaveExit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "CopyScheduleUI");
    }




}