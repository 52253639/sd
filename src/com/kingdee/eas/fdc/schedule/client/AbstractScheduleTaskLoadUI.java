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
public abstract class AbstractScheduleTaskLoadUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleTaskLoadUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlTask;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelDown;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tbl1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tbl2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected ActionSave actionSave = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionDeleteRow actionDeleteRow = null;
    /**
     * output class constructor
     */
    public AbstractScheduleTaskLoadUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractScheduleTaskLoadUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteRow
        this.actionDeleteRow = new ActionDeleteRow(this);
        getActionManager().registerAction("actionDeleteRow", actionDeleteRow);
         this.actionDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlTask = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.panelDown = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tbl1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tbl2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pnlTask.setName("pnlTask");
        this.panelDown.setName("panelDown");
        this.tbl1.setName("tbl1");
        this.tbl2.setName("tbl2");
        this.btnAddRow.setName("btnAddRow");
        this.btnDeleteRow.setName("btnDeleteRow");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","",""});

		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));
        // pnlTask		
        this.pnlTask.setDividerLocation(300);		
        this.pnlTask.setDividerSize(8);		
        this.pnlTask.setOrientation(0);
        // panelDown
        // tbl1

        

        // tbl2

        

        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));
        // btnDeleteRow
        this.btnDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteRow.setText(resHelper.getString("btnDeleteRow.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(pnlTask, "right");
        //treeView
        treeView.setTree(treeMain);
        //pnlTask
        pnlTask.add(tblMain, "top");
        pnlTask.add(panelDown, "bottom");
        //panelDown
        panelDown.add(tbl1, resHelper.getString("tbl1.constraints"));
        panelDown.add(tbl2, resHelper.getString("tbl2.constraints"));

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAddRow);
        this.toolBar.add(btnDeleteRow);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ScheduleTaskLoadUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteRow_actionPerformed method
     */
    public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRow() {
    	return false;
    }
	public RequestContext prepareActionDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteRow() {
    	return false;
    }

    /**
     * output ActionSave class
     */
    protected class ActionSave extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractScheduleTaskLoadUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRow class
     */
    protected class ActionAddRow extends ItemAction
    {
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleTaskLoadUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteRow class
     */
    protected class ActionDeleteRow extends ItemAction
    {
        public ActionDeleteRow()
        {
            this(null);
        }

        public ActionDeleteRow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleTaskLoadUI.this, "ActionDeleteRow", "actionDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ScheduleTaskLoadUI");
    }




}