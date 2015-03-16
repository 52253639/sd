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
public abstract class AbstractProjectCostCloseUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectCostCloseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeViewOrigin;
    protected com.kingdee.bos.ctrl.swing.KDTree treeOrigin;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeViewSelected;
    protected com.kingdee.bos.ctrl.swing.KDTree treeSelected;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSelect;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlOperation;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlResult;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelete;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbClosePeriod;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbUnClosePeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtResult;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelResult;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbFrozenPeriod;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup grpOperation;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelAll;
    protected ActionAdd actionAdd = null;
    protected ActionDelete actionDelete = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionCancel actionCancel = null;
    protected ActionAddAll actionAddAll = null;
    protected ActionDelAll actionDelAll = null;
    /**
     * output class constructor
     */
    public AbstractProjectCostCloseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectCostCloseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAdd
        this.actionAdd = new ActionAdd(this);
        getActionManager().registerAction("actionAdd", actionAdd);
         this.actionAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelete
        this.actionDelete = new ActionDelete(this);
        getActionManager().registerAction("actionDelete", actionDelete);
         this.actionDelete.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        this.actionCancel = new ActionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddAll
        this.actionAddAll = new ActionAddAll(this);
        getActionManager().registerAction("actionAddAll", actionAddAll);
         this.actionAddAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelAll
        this.actionDelAll = new ActionDelAll(this);
        getActionManager().registerAction("actionDelAll", actionDelAll);
         this.actionDelAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.treeViewOrigin = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeOrigin = new com.kingdee.bos.ctrl.swing.KDTree();
        this.treeViewSelected = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeSelected = new com.kingdee.bos.ctrl.swing.KDTree();
        this.pnlSelect = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlOperation = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlResult = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.rbClosePeriod = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbUnClosePeriod = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.txtResult = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.labelResult = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.rbFrozenPeriod = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.grpOperation = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnAddAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.treeViewOrigin.setName("treeViewOrigin");
        this.treeOrigin.setName("treeOrigin");
        this.treeViewSelected.setName("treeViewSelected");
        this.treeSelected.setName("treeSelected");
        this.pnlSelect.setName("pnlSelect");
        this.pnlOperation.setName("pnlOperation");
        this.pnlResult.setName("pnlResult");
        this.btnAdd.setName("btnAdd");
        this.btnDelete.setName("btnDelete");
        this.kDSeparator2.setName("kDSeparator2");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.rbClosePeriod.setName("rbClosePeriod");
        this.rbUnClosePeriod.setName("rbUnClosePeriod");
        this.txtResult.setName("txtResult");
        this.labelResult.setName("labelResult");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.rbFrozenPeriod.setName("rbFrozenPeriod");
        this.btnAddAll.setName("btnAddAll");
        this.btnDelAll.setName("btnDelAll");
        // CoreUI
        // treeViewOrigin		
        this.treeViewOrigin.setTitle(resHelper.getString("treeViewOrigin.title"));		
        this.treeViewOrigin.setShowButton(false);
        // treeOrigin
        // treeViewSelected		
        this.treeViewSelected.setShowButton(false);		
        this.treeViewSelected.setTitle(resHelper.getString("treeViewSelected.title"));
        // treeSelected
        // pnlSelect
        // pnlOperation
        // pnlResult
        // btnAdd
        this.btnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        // btnDelete
        this.btnDelete.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelete, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelete.setText(resHelper.getString("btnDelete.text"));
        // kDSeparator2
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // rbClosePeriod		
        this.rbClosePeriod.setText(resHelper.getString("rbClosePeriod.text"));
        this.rbClosePeriod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    rbClosePeriod_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // rbUnClosePeriod		
        this.rbUnClosePeriod.setText(resHelper.getString("rbUnClosePeriod.text"));
        this.rbUnClosePeriod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    rbUnClosePeriod_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtResult		
        this.txtResult.setEditable(false);
        // labelResult		
        this.labelResult.setText(resHelper.getString("labelResult.text"));
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtDescription		
        this.txtDescription.setText(resHelper.getString("txtDescription.text"));		
        this.txtDescription.setEditable(false);
        // rbFrozenPeriod		
        this.rbFrozenPeriod.setText(resHelper.getString("rbFrozenPeriod.text"));
        this.rbFrozenPeriod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    rbFrozenPeriod_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // grpOperation
        this.grpOperation.add(this.rbClosePeriod);
        this.grpOperation.add(this.rbUnClosePeriod);
        this.grpOperation.add(this.rbFrozenPeriod);
        // btnAddAll
        this.btnAddAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddAll.setText(resHelper.getString("btnAddAll.text"));
        // btnDelAll
        this.btnDelAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelAll.setText(resHelper.getString("btnDelAll.text"));
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
        this.setBounds(new Rectangle(10, 10, 792, 566));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 792, 566));
        treeViewOrigin.setBounds(new Rectangle(10, 10, 360, 360));
        this.add(treeViewOrigin, new KDLayout.Constraints(10, 10, 360, 360, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        treeViewSelected.setBounds(new Rectangle(422, 10, 360, 360));
        this.add(treeViewSelected, new KDLayout.Constraints(422, 10, 360, 360, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlSelect.setBounds(new Rectangle(370, 10, 52, 360));
        this.add(pnlSelect, new KDLayout.Constraints(370, 10, 52, 360, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        pnlOperation.setBounds(new Rectangle(10, 380, 772, 63));
        this.add(pnlOperation, new KDLayout.Constraints(10, 380, 772, 63, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlResult.setBounds(new Rectangle(10, 453, 772, 75));
        this.add(pnlResult, new KDLayout.Constraints(10, 453, 772, 75, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator2.setBounds(new Rectangle(10, 374, 772, 9));
        this.add(kDSeparator2, new KDLayout.Constraints(10, 374, 772, 9, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnConfirm.setBounds(new Rectangle(565, 535, 73, 21));
        this.add(btnConfirm, new KDLayout.Constraints(565, 535, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM));
        btnCancel.setBounds(new Rectangle(662, 535, 73, 21));
        this.add(btnCancel, new KDLayout.Constraints(662, 535, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM));
        //treeViewOrigin
        treeViewOrigin.setTree(treeOrigin);
        //treeViewSelected
        treeViewSelected.setTree(treeSelected);
        //pnlSelect
        pnlSelect.setLayout(new KDLayout());
        pnlSelect.putClientProperty("OriginalBounds", new Rectangle(370, 10, 52, 360));        btnAdd.setBounds(new Rectangle(15, 173, 26, 19));
        pnlSelect.add(btnAdd, new KDLayout.Constraints(15, 173, 26, 19, 0));
        btnDelete.setBounds(new Rectangle(15, 204, 26, 19));
        pnlSelect.add(btnDelete, new KDLayout.Constraints(15, 204, 26, 19, 0));
        btnAddAll.setBounds(new Rectangle(15, 142, 26, 19));
        pnlSelect.add(btnAddAll, new KDLayout.Constraints(15, 142, 26, 19, 0));
        btnDelAll.setBounds(new Rectangle(14, 235, 25, 19));
        pnlSelect.add(btnDelAll, new KDLayout.Constraints(14, 235, 25, 19, 0));
        //pnlOperation
        pnlOperation.setLayout(null);        rbClosePeriod.setBounds(new Rectangle(0, 22, 68, 19));
        pnlOperation.add(rbClosePeriod, null);
        rbUnClosePeriod.setBounds(new Rectangle(0, 44, 68, 19));
        pnlOperation.add(rbUnClosePeriod, null);
        txtDescription.setBounds(new Rectangle(80, 0, 692, 63));
        pnlOperation.add(txtDescription, null);
        rbFrozenPeriod.setBounds(new Rectangle(0, 0, 68, 19));
        pnlOperation.add(rbFrozenPeriod, null);
        //pnlResult
        pnlResult.setLayout(null);        labelResult.setBounds(new Rectangle(0, 30, 68, 19));
        pnlResult.add(labelResult, null);
        kDScrollPane1.setBounds(new Rectangle(80, 0, 692, 75));
        pnlResult.add(kDScrollPane1, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtResult, null);

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
	    return "com.kingdee.eas.fdc.finance.app.ProjectCostCloseUIHandler";
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
     * output rbClosePeriod_stateChanged method
     */
    protected void rbClosePeriod_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output rbUnClosePeriod_stateChanged method
     */
    protected void rbUnClosePeriod_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output rbFrozenPeriod_stateChanged method
     */
    protected void rbFrozenPeriod_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
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
    	

    /**
     * output actionAddAll_actionPerformed method
     */
    public void actionAddAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelAll_actionPerformed method
     */
    public void actionDelAll_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddAll() {
    	return false;
    }
	public RequestContext prepareActionDelAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelAll() {
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
            innerActionPerformed("eas", AbstractProjectCostCloseUI.this, "ActionAdd", "actionAdd_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractProjectCostCloseUI.this, "ActionDelete", "actionDelete_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractProjectCostCloseUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractProjectCostCloseUI.this, "ActionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output ActionAddAll class
     */     
    protected class ActionAddAll extends ItemAction {     
    
        public ActionAddAll()
        {
            this(null);
        }

        public ActionAddAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectCostCloseUI.this, "ActionAddAll", "actionAddAll_actionPerformed", e);
        }
    }

    /**
     * output ActionDelAll class
     */     
    protected class ActionDelAll extends ItemAction {     
    
        public ActionDelAll()
        {
            this(null);
        }

        public ActionDelAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectCostCloseUI.this, "ActionDelAll", "actionDelAll_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ProjectCostCloseUI");
    }




}