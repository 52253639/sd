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
public abstract class AbstractTaskMaterialItemEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTaskMaterialItemEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTitle;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTitle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHappenTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkHappenTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppraise;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAppraise;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane conDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryInfo entityTaskMaterialItemEntry = null;
    protected ActionSave actionSave = null;
    protected ActionSubmit actionSubmit = null;
    /**
     * output class constructor
     */
    public AbstractTaskMaterialItemEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "entityTaskMaterialItemEntry";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractTaskMaterialItemEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contTitle = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTitle = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contHappenTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkHappenTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contAppraise = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAppraise = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.conDescription = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contTitle.setName("contTitle");
        this.txtTitle.setName("txtTitle");
        this.contHappenTime.setName("contHappenTime");
        this.pkHappenTime.setName("pkHappenTime");
        this.contAppraise.setName("contAppraise");
        this.txtAppraise.setName("txtAppraise");
        this.conDescription.setName("conDescription");
        this.kDLabel1.setName("kDLabel1");
        this.txtDescription.setName("txtDescription");
        this.btnSave.setName("btnSave");
        this.btnSubmit.setName("btnSubmit");
        // CoreUI
        // contTitle		
        this.contTitle.setBoundLabelText(resHelper.getString("contTitle.boundLabelText"));		
        this.contTitle.setBoundLabelLength(100);		
        this.contTitle.setBoundLabelUnderline(true);
        // txtTitle		
        this.txtTitle.setRequired(true);
        // contHappenTime		
        this.contHappenTime.setBoundLabelText(resHelper.getString("contHappenTime.boundLabelText"));		
        this.contHappenTime.setBoundLabelLength(100);		
        this.contHappenTime.setBoundLabelUnderline(true);
        // pkHappenTime		
        this.pkHappenTime.setRequired(true);
        // contAppraise		
        this.contAppraise.setBoundLabelText(resHelper.getString("contAppraise.boundLabelText"));		
        this.contAppraise.setBoundLabelLength(100);		
        this.contAppraise.setBoundLabelUnderline(true);
        // txtAppraise		
        this.txtAppraise.setDataType(1);
        // conDescription
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // txtDescription		
        this.txtDescription.setMaxLength(500);
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // btnSubmit
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_submit"));
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
        this.setBounds(new Rectangle(10, 10, 550, 200));
        this.setLayout(null);
        contTitle.setBounds(new Rectangle(10, 10, 240, 19));
        this.add(contTitle, null);
        contHappenTime.setBounds(new Rectangle(300, 10, 240, 19));
        this.add(contHappenTime, null);
        contAppraise.setBounds(new Rectangle(300, 40, 240, 19));
        this.add(contAppraise, null);
        conDescription.setBounds(new Rectangle(10, 70, 530, 120));
        this.add(conDescription, null);
        kDLabel1.setBounds(new Rectangle(10, 40, 100, 19));
        this.add(kDLabel1, null);
        //contTitle
        contTitle.setBoundEditor(txtTitle);
        //contHappenTime
        contHappenTime.setBoundEditor(pkHappenTime);
        //contAppraise
        contAppraise.setBoundEditor(txtAppraise);
        //conDescription
        conDescription.getViewport().add(txtDescription, null);

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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("id", com.kingdee.bos.util.BOSUuid.class, this.txtTitle, "text");
		dataBinder.registerBinding("happenTime", java.util.Date.class, this.pkHappenTime, "value");
		dataBinder.registerBinding("appraise", java.math.BigDecimal.class, this.txtAppraise, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.TaskMaterialItemEditUIHandler";
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
        this.entityTaskMaterialItemEntry = (com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryInfo)ov;
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
		getValidateHelper().registerBindProperty("id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("happenTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("appraise", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("happenTime"));
        sic.add(new SelectorItemInfo("appraise"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractTaskMaterialItemEditUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractTaskMaterialItemEditUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "TaskMaterialItemEditUI");
    }




}