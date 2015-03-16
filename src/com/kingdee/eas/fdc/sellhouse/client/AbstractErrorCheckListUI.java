/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractErrorCheckListUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractErrorCheckListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane splitPaneMain;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane splitPaneSub;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeViewSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTree treeSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeViewError;
    protected com.kingdee.bos.ctrl.swing.KDTree treeError;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblResult;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurDistillUpdate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurAddMarket;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectDataBaseUpdate;
    protected ActionPurDistillUpdate actionPurDistillUpdate = null;
    protected ActionPurAddMarket actionPurAddMarket = null;
    protected ActionProjectDataBaseUpdate actionProjectDataBaseUpdate = null;
    /**
     * output class constructor
     */
    public AbstractErrorCheckListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractErrorCheckListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPurDistillUpdate
        this.actionPurDistillUpdate = new ActionPurDistillUpdate(this);
        getActionManager().registerAction("actionPurDistillUpdate", actionPurDistillUpdate);
         this.actionPurDistillUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurAddMarket
        this.actionPurAddMarket = new ActionPurAddMarket(this);
        getActionManager().registerAction("actionPurAddMarket", actionPurAddMarket);
         this.actionPurAddMarket.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProjectDataBaseUpdate
        this.actionProjectDataBaseUpdate = new ActionProjectDataBaseUpdate(this);
        getActionManager().registerAction("actionProjectDataBaseUpdate", actionProjectDataBaseUpdate);
         this.actionProjectDataBaseUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.splitPaneMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.splitPaneSub = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeViewSellProject = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeSellProject = new com.kingdee.bos.ctrl.swing.KDTree();
        this.treeViewError = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeError = new com.kingdee.bos.ctrl.swing.KDTree();
        this.tblResult = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnPurDistillUpdate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurAddMarket = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProjectDataBaseUpdate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.splitPaneMain.setName("splitPaneMain");
        this.splitPaneSub.setName("splitPaneSub");
        this.treeViewSellProject.setName("treeViewSellProject");
        this.treeSellProject.setName("treeSellProject");
        this.treeViewError.setName("treeViewError");
        this.treeError.setName("treeError");
        this.tblResult.setName("tblResult");
        this.btnPurDistillUpdate.setName("btnPurDistillUpdate");
        this.btnPurAddMarket.setName("btnPurAddMarket");
        this.btnProjectDataBaseUpdate.setName("btnProjectDataBaseUpdate");
        // CoreUI
        // splitPaneMain		
        this.splitPaneMain.setDividerLocation(200);
        // splitPaneSub		
        this.splitPaneSub.setDividerLocation(300);
        // treeViewSellProject
        // treeSellProject
        // treeViewError
        // treeError
        // tblResult
		String tblResultStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column3\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column4\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column5\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{column1}</t:Cell><t:Cell>$Resource{column2}</t:Cell><t:Cell>$Resource{column3}</t:Cell><t:Cell>$Resource{column4}</t:Cell><t:Cell>$Resource{column5}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblResult.setFormatXml(resHelper.translateString("tblResult",tblResultStrXML));

        

        // btnPurDistillUpdate
        this.btnPurDistillUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurDistillUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurDistillUpdate.setText(resHelper.getString("btnPurDistillUpdate.text"));
        this.btnPurDistillUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPurDistillUpdate_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnPurAddMarket
        this.btnPurAddMarket.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurAddMarket, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurAddMarket.setText(resHelper.getString("btnPurAddMarket.text"));
        this.btnPurAddMarket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPurAddMarket_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnProjectDataBaseUpdate
        this.btnProjectDataBaseUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectDataBaseUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectDataBaseUpdate.setText(resHelper.getString("btnProjectDataBaseUpdate.text"));
        this.btnProjectDataBaseUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectDataBaseUpdate_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        splitPaneMain.setBounds(new Rectangle(0, 0, 1016, 600));
        this.add(splitPaneMain, new KDLayout.Constraints(0, 0, 1016, 600, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //splitPaneMain
        splitPaneMain.add(splitPaneSub, "right");
        splitPaneMain.add(treeViewSellProject, "left");
        //splitPaneSub
        splitPaneSub.add(treeViewError, "left");
        splitPaneSub.add(tblResult, "right");
        //treeViewError
        treeViewError.setTree(treeError);
        //treeViewSellProject
        treeViewSellProject.setTree(treeSellProject);

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
        this.toolBar.add(btnPurDistillUpdate);
        this.toolBar.add(btnPurAddMarket);
        this.toolBar.add(btnProjectDataBaseUpdate);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ErrorCheckListUIHandler";
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
     * output btnPurDistillUpdate_actionPerformed method
     */
    protected void btnPurDistillUpdate_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnPurAddMarket_actionPerformed method
     */
    protected void btnPurAddMarket_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnProjectDataBaseUpdate_actionPerformed method
     */
    protected void btnProjectDataBaseUpdate_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    	

    /**
     * output actionPurDistillUpdate_actionPerformed method
     */
    public void actionPurDistillUpdate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurAddMarket_actionPerformed method
     */
    public void actionPurAddMarket_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProjectDataBaseUpdate_actionPerformed method
     */
    public void actionProjectDataBaseUpdate_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPurDistillUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurDistillUpdate() {
    	return false;
    }
	public RequestContext prepareActionPurAddMarket(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurAddMarket() {
    	return false;
    }
	public RequestContext prepareActionProjectDataBaseUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProjectDataBaseUpdate() {
    	return false;
    }

    /**
     * output ActionPurDistillUpdate class
     */     
    protected class ActionPurDistillUpdate extends ItemAction {     
    
        public ActionPurDistillUpdate()
        {
            this(null);
        }

        public ActionPurDistillUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurDistillUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurDistillUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurDistillUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractErrorCheckListUI.this, "ActionPurDistillUpdate", "actionPurDistillUpdate_actionPerformed", e);
        }
    }

    /**
     * output ActionPurAddMarket class
     */     
    protected class ActionPurAddMarket extends ItemAction {     
    
        public ActionPurAddMarket()
        {
            this(null);
        }

        public ActionPurAddMarket(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurAddMarket.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurAddMarket.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurAddMarket.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractErrorCheckListUI.this, "ActionPurAddMarket", "actionPurAddMarket_actionPerformed", e);
        }
    }

    /**
     * output ActionProjectDataBaseUpdate class
     */     
    protected class ActionProjectDataBaseUpdate extends ItemAction {     
    
        public ActionProjectDataBaseUpdate()
        {
            this(null);
        }

        public ActionProjectDataBaseUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionProjectDataBaseUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectDataBaseUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectDataBaseUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractErrorCheckListUI.this, "ActionProjectDataBaseUpdate", "actionProjectDataBaseUpdate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ErrorCheckListUI");
    }




}