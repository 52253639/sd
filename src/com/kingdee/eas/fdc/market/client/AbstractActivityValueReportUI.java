/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractActivityValueReportUI extends com.kingdee.eas.framework.report.client.CommRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractActivityValueReportUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuickPic;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHistory;
    protected ActionQuickPic actionQuickPic = null;
    protected ActionHistory actionHistory = null;
    /**
     * output class constructor
     */
    public AbstractActivityValueReportUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractActivityValueReportUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionQuickPic
        this.actionQuickPic = new ActionQuickPic(this);
        getActionManager().registerAction("actionQuickPic", actionQuickPic);
         this.actionQuickPic.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHistory
        this.actionHistory = new ActionHistory(this);
        getActionManager().registerAction("actionHistory", actionHistory);
         this.actionHistory.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeProject = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnQuickPic = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHistory = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.tblMain.setName("tblMain");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeProject.setName("treeProject");
        this.btnQuickPic.setName("btnQuickPic");
        this.btnHistory.setName("btnHistory");
        // CoreUI
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);
        // tblMain
        this.tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDTreeView1
        // treeProject
        this.treeProject.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeProject_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnQuickPic
        this.btnQuickPic.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuickPic, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuickPic.setText(resHelper.getString("btnQuickPic.text"));
        // btnHistory
        this.btnHistory.setAction((IItemAction)ActionProxyFactory.getProxy(actionHistory, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHistory.setText(resHelper.getString("btnHistory.text"));
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
        kDSplitPane1.setBounds(new Rectangle(12, 11, 994, 563));
        this.add(kDSplitPane1, new KDLayout.Constraints(12, 11, 994, 563, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(kDTreeView1, "left");
        //kDTreeView1
        kDTreeView1.setTree(treeProject);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuView);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(kdSeparatorFWFile1);
        //menuView
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(kDSeparator2);
        menuView.add(menuItemChart);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnQuery);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnQuickPic);
        this.toolBar.add(btnHistory);
        this.toolBar.add(separator1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separator2);
        this.toolBar.add(btnChart);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.ActivityValueReportUIHandler";
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        //write your code here123
    }

    	

    /**
     * output actionQuickPic_actionPerformed method
     */
    public void actionQuickPic_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHistory_actionPerformed method
     */
    public void actionHistory_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionQuickPic(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuickPic() {
    	return false;
    }
	public RequestContext prepareActionHistory(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHistory() {
    	return false;
    }

    /**
     * output ActionQuickPic class
     */     
    protected class ActionQuickPic extends ItemAction {     
    
        public ActionQuickPic()
        {
            this(null);
        }

        public ActionQuickPic(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuickPic.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuickPic.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuickPic.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractActivityValueReportUI.this, "ActionQuickPic", "actionQuickPic_actionPerformed", e);
        }
    }

    /**
     * output ActionHistory class
     */     
    protected class ActionHistory extends ItemAction {     
    
        public ActionHistory()
        {
            this(null);
        }

        public ActionHistory(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHistory.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHistory.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHistory.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractActivityValueReportUI.this, "ActionHistory", "actionHistory_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "ActivityValueReportUI");
    }




}