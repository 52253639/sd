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
public abstract class AbstractNewQuotingPriceEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewQuotingPriceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBidder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Bidder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBidderNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBidderNumber;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPnlTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportQuotingExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportErrorInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportPrice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportErrorInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem exportMenuItem;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton exportButton;
    protected com.kingdee.eas.fdc.invite.NewQuotingPriceInfo editData = null;
    protected ActionImportQuotingExcel actionImportQuotingExcel = null;
    protected ActionExportErrorInfo actionExportErrorInfo = null;
    protected ActionExportQuotingExcel actionExportQuotingExcel = null;
    /**
     * output class constructor
     */
    public AbstractNewQuotingPriceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractNewQuotingPriceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionImportQuotingExcel
        this.actionImportQuotingExcel = new ActionImportQuotingExcel(this);
        getActionManager().registerAction("actionImportQuotingExcel", actionImportQuotingExcel);
         this.actionImportQuotingExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportErrorInfo
        this.actionExportErrorInfo = new ActionExportErrorInfo(this);
        getActionManager().registerAction("actionExportErrorInfo", actionExportErrorInfo);
         this.actionExportErrorInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportQuotingExcel
        this.actionExportQuotingExcel = new ActionExportQuotingExcel(this);
        getActionManager().registerAction("actionExportQuotingExcel", actionExportQuotingExcel);
         this.actionExportQuotingExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBidder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Bidder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBidderNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBidderNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tabbedPnlTable = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnImportQuotingExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportErrorInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuImportPrice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExportErrorInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.exportMenuItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.exportButton = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contBidder.setName("contBidder");
        this.f7Bidder.setName("f7Bidder");
        this.contBidderNumber.setName("contBidderNumber");
        this.txtBidderNumber.setName("txtBidderNumber");
        this.tabbedPnlTable.setName("tabbedPnlTable");
        this.btnImportQuotingExcel.setName("btnImportQuotingExcel");
        this.btnExportErrorInfo.setName("btnExportErrorInfo");
        this.menuImportPrice.setName("menuImportPrice");
        this.menuItemExportErrorInfo.setName("menuItemExportErrorInfo");
        this.exportMenuItem.setName("exportMenuItem");
        this.exportButton.setName("exportButton");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // contBidder		
        this.contBidder.setBoundLabelText(resHelper.getString("contBidder.boundLabelText"));		
        this.contBidder.setBoundLabelLength(100);		
        this.contBidder.setBoundLabelUnderline(true);
        // f7Bidder		
        this.f7Bidder.setCommitFormat("$supplier.number$");		
        this.f7Bidder.setRequired(true);		
        this.f7Bidder.setDisplayFormat("$supplier.name$");		
        this.f7Bidder.setEditFormat("$supplier.number$");		
        this.f7Bidder.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectSupplierQuery");
        // contBidderNumber		
        this.contBidderNumber.setBoundLabelText(resHelper.getString("contBidderNumber.boundLabelText"));		
        this.contBidderNumber.setBoundLabelLength(100);		
        this.contBidderNumber.setBoundLabelUnderline(true);
        // txtBidderNumber		
        this.txtBidderNumber.setEnabled(false);
        // tabbedPnlTable
        // btnImportQuotingExcel
        this.btnImportQuotingExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportQuotingExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportQuotingExcel.setText(resHelper.getString("btnImportQuotingExcel.text"));		
        this.btnImportQuotingExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // btnExportErrorInfo
        this.btnExportErrorInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportErrorInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportErrorInfo.setText(resHelper.getString("btnExportErrorInfo.text"));		
        this.btnExportErrorInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // menuImportPrice
        this.menuImportPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportQuotingExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportPrice.setText(resHelper.getString("menuImportPrice.text"));		
        this.menuImportPrice.setDisabledIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // menuItemExportErrorInfo
        this.menuItemExportErrorInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportErrorInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExportErrorInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // exportMenuItem
        this.exportMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportQuotingExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.exportMenuItem.setText(resHelper.getString("exportMenuItem.text"));
        // exportButton
        this.exportButton.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportQuotingExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.exportButton.setText(resHelper.getString("exportButton.text"));		
        this.exportButton.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
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
        contBidder.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contBidder, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contBidderNumber.setBounds(new Rectangle(355, 10, 270, 19));
        this.add(contBidderNumber, new KDLayout.Constraints(355, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        tabbedPnlTable.setBounds(new Rectangle(10, 37, 993, 582));
        this.add(tabbedPnlTable, new KDLayout.Constraints(10, 37, 993, 582, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBidder
        contBidder.setBoundEditor(f7Bidder);
        //contBidderNumber
        contBidderNumber.setBoundEditor(txtBidderNumber);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
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
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuImportPrice);
        menuBiz.add(menuItemExportErrorInfo);
        menuBiz.add(exportMenuItem);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnImportQuotingExcel);
        this.toolBar.add(btnExportErrorInfo);
        this.toolBar.add(exportButton);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.f7Bidder, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.NewQuotingPriceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.NewQuotingPriceInfo)ov;
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
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("supplier.*"));
        return sic;
    }        
    	

    /**
     * output actionImportQuotingExcel_actionPerformed method
     */
    public void actionImportQuotingExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportErrorInfo_actionPerformed method
     */
    public void actionExportErrorInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportQuotingExcel_actionPerformed method
     */
    public void actionExportQuotingExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionImportQuotingExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportQuotingExcel() {
    	return false;
    }
	public RequestContext prepareActionExportErrorInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportErrorInfo() {
    	return false;
    }
	public RequestContext prepareActionExportQuotingExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportQuotingExcel() {
    	return false;
    }

    /**
     * output ActionImportQuotingExcel class
     */     
    protected class ActionImportQuotingExcel extends ItemAction {     
    
        public ActionImportQuotingExcel()
        {
            this(null);
        }

        public ActionImportQuotingExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
            _tempStr = resHelper.getString("ActionImportQuotingExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportQuotingExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportQuotingExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuotingPriceEditUI.this, "ActionImportQuotingExcel", "actionImportQuotingExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionExportErrorInfo class
     */     
    protected class ActionExportErrorInfo extends ItemAction {     
    
        public ActionExportErrorInfo()
        {
            this(null);
        }

        public ActionExportErrorInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExportErrorInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportErrorInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportErrorInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuotingPriceEditUI.this, "ActionExportErrorInfo", "actionExportErrorInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionExportQuotingExcel class
     */     
    protected class ActionExportQuotingExcel extends ItemAction {     
    
        public ActionExportQuotingExcel()
        {
            this(null);
        }

        public ActionExportQuotingExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
            _tempStr = resHelper.getString("ActionExportQuotingExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportQuotingExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportQuotingExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuotingPriceEditUI.this, "ActionExportQuotingExcel", "actionExportQuotingExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "NewQuotingPriceEditUI");
    }




}