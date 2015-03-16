/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractCompanyTreeSelectUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCompanyTreeSelectUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeOrigin;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelete;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDToolBar CompanyTreeSelectUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBoxHasChildren;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBoxIsTreeModel;
    protected com.kingdee.bos.ctrl.swing.KDTree treeSelected;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDList kDListSelectCompany;
    protected ActionAdd actionAdd = null;
    protected ActionDelete actionDelete = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionCancel actionCancel = null;
    /**
     * output class constructor
     */
    public AbstractCompanyTreeSelectUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCompanyTreeSelectUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAdd
        this.actionAdd = new ActionAdd(this);
        getActionManager().registerAction("actionAdd", actionAdd);
         this.actionAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAdd.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAdd.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelete
        this.actionDelete = new ActionDelete(this);
        getActionManager().registerAction("actionDelete", actionDelete);
         this.actionDelete.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionDelete.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionDelete.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCancel
        this.actionCancel = new ActionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeOrigin = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSeparator1 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kDCheckBoxHasChildren = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBoxIsTreeModel = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.treeSelected = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDTreeView2 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDListSelectCompany = new com.kingdee.bos.ctrl.swing.KDList();
        this.setName("CompanyTreeSelectUI");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeOrigin.setName("treeOrigin");
        this.kDPanel1.setName("kDPanel1");
        this.btnAdd.setName("btnAdd");
        this.btnDelete.setName("btnDelete");
        this.kDSeparator1.setName("kDSeparator1");
        this.btnOK.setName("btnOK");
        this.btnCancel.setName("btnCancel");
        this.toolBar.setName("CompanyTreeSelectUI_toolbar");
        this.menuBar.setName("CompanyTreeSelectUI_menubar");
        this.kDCheckBoxHasChildren.setName("kDCheckBoxHasChildren");
        this.kDCheckBoxIsTreeModel.setName("kDCheckBoxIsTreeModel");
        this.treeSelected.setName("treeSelected");
        this.kDTreeView2.setName("kDTreeView2");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDListSelectCompany.setName("kDListSelectCompany");
        // CompanyTreeSelectUI		
        this.setPreferredSize(new Dimension(640,480));
        // kDTreeView1		
        this.kDTreeView1.setTitle(resHelper.getString("kDTreeView1.title"));		
        this.kDTreeView1.setShowButton(false);
        // treeOrigin
        // kDPanel1
        // btnAdd
        this.btnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        // btnDelete
        this.btnDelete.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelete, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelete.setText(resHelper.getString("btnDelete.text"));
        // kDSeparator1
        // btnOK
        this.btnOK.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // CompanyTreeSelectUI_toolbar
        // CompanyTreeSelectUI_menubar
        // kDCheckBoxHasChildren		
        this.kDCheckBoxHasChildren.setText(resHelper.getString("kDCheckBoxHasChildren.text"));		
        this.kDCheckBoxHasChildren.setSelectState(1);		
        this.kDCheckBoxHasChildren.setVisible(false);		
        this.kDCheckBoxHasChildren.setSelected(true);
        // kDCheckBoxIsTreeModel		
        this.kDCheckBoxIsTreeModel.setText(resHelper.getString("kDCheckBoxIsTreeModel.text"));		
        this.kDCheckBoxIsTreeModel.setSelected(true);
        this.kDCheckBoxIsTreeModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDCheckBoxIsTreeModel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // treeSelected
        // kDTreeView2		
        this.kDTreeView2.setTitle(resHelper.getString("kDTreeView2.title"));		
        this.kDTreeView2.setShowButton(false);
        // kDScrollPane1
        // kDListSelectCompany
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
		list.add(this.toolBar);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 632, 446));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 632, 446));
        kDTreeView1.setBounds(new Rectangle(10, 10, 280, 380));
        this.add(kDTreeView1, new KDLayout.Constraints(10, 10, 280, 380, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(290, 10, 53, 380));
        this.add(kDPanel1, new KDLayout.Constraints(290, 10, 53, 380, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator1.setBounds(new Rectangle(10, 399, 612, 9));
        this.add(kDSeparator1, new KDLayout.Constraints(10, 399, 612, 9, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnOK.setBounds(new Rectangle(469, 413, 73, 21));
        this.add(btnOK, new KDLayout.Constraints(469, 413, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        btnCancel.setBounds(new Rectangle(547, 413, 73, 21));
        this.add(btnCancel, new KDLayout.Constraints(547, 413, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        kDCheckBoxHasChildren.setBounds(new Rectangle(122, 411, 104, 19));
        this.add(kDCheckBoxHasChildren, new KDLayout.Constraints(122, 411, 104, 19, KDLayout.Constraints.ANCHOR_BOTTOM));
        kDCheckBoxIsTreeModel.setBounds(new Rectangle(12, 411, 103, 19));
        this.add(kDCheckBoxIsTreeModel, new KDLayout.Constraints(12, 411, 103, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDTreeView2.setBounds(new Rectangle(345, 10, 280, 380));
        this.add(kDTreeView2, new KDLayout.Constraints(345, 10, 280, 380, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(343, 10, 284, 382));
        this.add(kDScrollPane1, new KDLayout.Constraints(343, 10, 284, 382, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDTreeView1
        kDTreeView1.setTree(treeOrigin);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(290, 10, 53, 380));        btnAdd.setBounds(new Rectangle(17, 173, 22, 19));
        kDPanel1.add(btnAdd, new KDLayout.Constraints(17, 173, 22, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDelete.setBounds(new Rectangle(17, 204, 22, 19));
        kDPanel1.add(btnDelete, new KDLayout.Constraints(17, 204, 22, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTreeView2
        kDTreeView2.setTree(treeSelected);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDListSelectCompany, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.CompanyTreeSelectUIHandler";
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
     * output kDCheckBoxIsTreeModel_actionPerformed method
     */
    protected void kDCheckBoxIsTreeModel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionAdd_actionPerformed method
     */
    public void actionAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelete_actionPerformed method
     */
    public void actionDelete_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionDelete(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelete() {
    	return false;
    }
	public RequestContext prepareActionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirm() {
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
            innerActionPerformed("eas", AbstractCompanyTreeSelectUI.this, "ActionAdd", "actionAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionDelete class
     */     
    protected class ActionDelete extends ItemAction {     
    
        public ActionDelete()
        {
            this(null);
        }

        public ActionDelete(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelete.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelete.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelete.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCompanyTreeSelectUI.this, "ActionDelete", "actionDelete_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirm class
     */     
    protected class ActionConfirm extends ItemAction {     
    
        public ActionConfirm()
        {
            this(null);
        }

        public ActionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCompanyTreeSelectUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCompanyTreeSelectUI.this, "ActionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "CompanyTreeSelectUI");
    }




}