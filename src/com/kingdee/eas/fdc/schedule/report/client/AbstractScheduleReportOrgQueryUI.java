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
public abstract class AbstractScheduleReportOrgQueryUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleReportOrgQueryUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBlured;
    protected com.kingdee.bos.ctrl.swing.KDButton btnQuery;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbQueryField;
    protected com.kingdee.bos.ctrl.swing.KDTextField queryFieldTxt;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDContainer treeContainer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton allCheckBtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton unAllCheckBtn;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDOrgTreeView;
    protected com.kingdee.bos.ctrl.swing.KDTree kDOrgTree;
    protected actionSearch actionSearch = null;
    protected actionConfirm actionConfirm = null;
    protected actionCancel actionCancel = null;
    protected actionAllCheck actionAllCheck = null;
    protected actionUnAllCheck actionUnAllCheck = null;
    /**
     * output class constructor
     */
    public AbstractScheduleReportOrgQueryUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleReportOrgQueryUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSearch
        this.actionSearch = new actionSearch(this);
        getActionManager().registerAction("actionSearch", actionSearch);
         this.actionSearch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConfirm
        this.actionConfirm = new actionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        this.actionCancel = new actionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllCheck
        this.actionAllCheck = new actionAllCheck(this);
        getActionManager().registerAction("actionAllCheck", actionAllCheck);
         this.actionAllCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAllCheck
        this.actionUnAllCheck = new actionUnAllCheck(this);
        getActionManager().registerAction("actionUnAllCheck", actionUnAllCheck);
         this.actionUnAllCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.checkBlured = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnQuery = new com.kingdee.bos.ctrl.swing.KDButton();
        this.cbQueryField = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.queryFieldTxt = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.treeContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.allCheckBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.unAllCheckBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDOrgTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDOrgTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.checkBlured.setName("checkBlured");
        this.btnQuery.setName("btnQuery");
        this.cbQueryField.setName("cbQueryField");
        this.queryFieldTxt.setName("queryFieldTxt");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.treeContainer.setName("treeContainer");
        this.allCheckBtn.setName("allCheckBtn");
        this.unAllCheckBtn.setName("unAllCheckBtn");
        this.kDOrgTreeView.setName("kDOrgTreeView");
        this.kDOrgTree.setName("kDOrgTree");
        // CoreUI
        // checkBlured		
        this.checkBlured.setText(resHelper.getString("checkBlured.text"));
        // btnQuery
        this.btnQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionSearch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuery.setText(resHelper.getString("btnQuery.text"));
        // cbQueryField
        // queryFieldTxt
        this.queryFieldTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    queryFieldTxt_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        this.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // treeContainer		
        this.treeContainer.setTitle(resHelper.getString("treeContainer.title"));
        // allCheckBtn
        this.allCheckBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.allCheckBtn.setText(resHelper.getString("allCheckBtn.text"));		
        this.allCheckBtn.setToolTipText(resHelper.getString("allCheckBtn.toolTipText"));
        this.allCheckBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    allCheckBtn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // unAllCheckBtn
        this.unAllCheckBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAllCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.unAllCheckBtn.setText(resHelper.getString("unAllCheckBtn.text"));		
        this.unAllCheckBtn.setToolTipText(resHelper.getString("unAllCheckBtn.toolTipText"));
        // kDOrgTreeView		
        this.kDOrgTreeView.setShowButton(false);		
        this.kDOrgTreeView.setShowControlPanel(false);
        // kDOrgTree
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
        this.setBounds(new Rectangle(10, 10, 420, 520));
        this.setLayout(null);
        checkBlured.setBounds(new Rectangle(275, 18, 81, 20));
        this.add(checkBlured, null);
        btnQuery.setBounds(new Rectangle(199, 18, 65, 21));
        this.add(btnQuery, null);
        cbQueryField.setBounds(new Rectangle(10, 18, 63, 19));
        this.add(cbQueryField, null);
        queryFieldTxt.setBounds(new Rectangle(79, 18, 112, 19));
        this.add(queryFieldTxt, null);
        btnConfirm.setBounds(new Rectangle(245, 493, 73, 21));
        this.add(btnConfirm, null);
        btnCancel.setBounds(new Rectangle(331, 493, 73, 21));
        this.add(btnCancel, null);
        treeContainer.setBounds(new Rectangle(9, 42, 403, 443));
        this.add(treeContainer, null);
        allCheckBtn.setBounds(new Rectangle(357, 18, 22, 19));
        this.add(allCheckBtn, null);
        unAllCheckBtn.setBounds(new Rectangle(389, 18, 22, 19));
        this.add(unAllCheckBtn, null);
        //treeContainer
        treeContainer.getContentPane().setLayout(null);        kDOrgTreeView.setBounds(new Rectangle(0, 0, 403, 425));
        treeContainer.getContentPane().add(kDOrgTreeView, null);
        //kDOrgTreeView
        kDOrgTreeView.setTree(kDOrgTree);

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
	    return "com.kingdee.eas.fdc.schedule.report.app.ScheduleReportOrgQueryUIHandler";
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
     * output queryFieldTxt_actionPerformed method
     */
    protected void queryFieldTxt_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output allCheckBtn_actionPerformed method
     */
    protected void allCheckBtn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSearch_actionPerformed method
     */
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception
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
     * output actionAllCheck_actionPerformed method
     */
    public void actionAllCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAllCheck_actionPerformed method
     */
    public void actionUnAllCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionSearch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSearch() {
    	return false;
    }
	public RequestContext prepareactionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionConfirm() {
    	return false;
    }
	public RequestContext prepareactionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionCancel() {
    	return false;
    }
	public RequestContext prepareactionAllCheck(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAllCheck() {
    	return false;
    }
	public RequestContext prepareactionUnAllCheck(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionUnAllCheck() {
    	return false;
    }

    /**
     * output actionSearch class
     */     
    protected class actionSearch extends ItemAction {     
    
        public actionSearch()
        {
            this(null);
        }

        public actionSearch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionSearch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSearch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSearch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportOrgQueryUI.this, "actionSearch", "actionSearch_actionPerformed", e);
        }
    }

    /**
     * output actionConfirm class
     */     
    protected class actionConfirm extends ItemAction {     
    
        public actionConfirm()
        {
            this(null);
        }

        public actionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportOrgQueryUI.this, "actionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output actionCancel class
     */     
    protected class actionCancel extends ItemAction {     
    
        public actionCancel()
        {
            this(null);
        }

        public actionCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportOrgQueryUI.this, "actionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output actionAllCheck class
     */     
    protected class actionAllCheck extends ItemAction {     
    
        public actionAllCheck()
        {
            this(null);
        }

        public actionAllCheck(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionAllCheck.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAllCheck.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAllCheck.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportOrgQueryUI.this, "actionAllCheck", "actionAllCheck_actionPerformed", e);
        }
    }

    /**
     * output actionUnAllCheck class
     */     
    protected class actionUnAllCheck extends ItemAction {     
    
        public actionUnAllCheck()
        {
            this(null);
        }

        public actionUnAllCheck(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionUnAllCheck.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAllCheck.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAllCheck.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportOrgQueryUI.this, "actionUnAllCheck", "actionUnAllCheck_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.report.client", "ScheduleReportOrgQueryUI");
    }




}