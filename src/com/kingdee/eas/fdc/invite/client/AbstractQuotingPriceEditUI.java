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
public abstract class AbstractQuotingPriceEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuotingPriceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBidderNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBidderNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportQuotingExcel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBidder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Bidder;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportErrorInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuExportErrorInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportPrice;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionImportQuotingExcel actionImportQuotingExcel = null;
    protected ActionExportErrorInfo actionExportErrorInfo = null;
    /**
     * output class constructor
     */
    public AbstractQuotingPriceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuotingPriceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionImportQuotingExcel
        this.actionImportQuotingExcel = new ActionImportQuotingExcel(this);
        getActionManager().registerAction("actionImportQuotingExcel", actionImportQuotingExcel);
         this.actionImportQuotingExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportErrorInfo
        this.actionExportErrorInfo = new ActionExportErrorInfo(this);
        getActionManager().registerAction("actionExportErrorInfo", actionExportErrorInfo);
         this.actionExportErrorInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBidderNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBidderNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnImportQuotingExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contBidder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Bidder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnExportErrorInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuExportErrorInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImportPrice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contBidderNumber.setName("contBidderNumber");
        this.txtBidderNumber.setName("txtBidderNumber");
        this.btnImportQuotingExcel.setName("btnImportQuotingExcel");
        this.contBidder.setName("contBidder");
        this.f7Bidder.setName("f7Bidder");
        this.btnExportErrorInfo.setName("btnExportErrorInfo");
        this.menuExportErrorInfo.setName("menuExportErrorInfo");
        this.menuImportPrice.setName("menuImportPrice");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));		
        this.btnSave.setVisible(false);		
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
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setMnemonic(83);		
        this.menuItemSubmit.setMnemonic(0);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // contBidderNumber		
        this.contBidderNumber.setBoundLabelText(resHelper.getString("contBidderNumber.boundLabelText"));		
        this.contBidderNumber.setBoundLabelLength(100);		
        this.contBidderNumber.setBoundLabelUnderline(true);
        // txtBidderNumber		
        this.txtBidderNumber.setMaxLength(66);		
        this.txtBidderNumber.setRequired(true);		
        this.txtBidderNumber.setEnabled(false);
        // btnImportQuotingExcel
        this.btnImportQuotingExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportQuotingExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportQuotingExcel.setText(resHelper.getString("btnImportQuotingExcel.text"));		
        this.btnImportQuotingExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // contBidder		
        this.contBidder.setBoundLabelText(resHelper.getString("contBidder.boundLabelText"));		
        this.contBidder.setBoundLabelLength(100);		
        this.contBidder.setBoundLabelUnderline(true);
        // f7Bidder		
        this.f7Bidder.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.f7Bidder.setRequired(true);		
        this.f7Bidder.setDisplayFormat("$supplier.name$");		
        this.f7Bidder.setEditFormat("$supplier.name$");
        // btnExportErrorInfo
        this.btnExportErrorInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportErrorInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportErrorInfo.setText(resHelper.getString("btnExportErrorInfo.text"));		
        this.btnExportErrorInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // menuExportErrorInfo
        this.menuExportErrorInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportErrorInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuExportErrorInfo.setText(resHelper.getString("menuExportErrorInfo.text"));		
        this.menuExportErrorInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // menuImportPrice
        this.menuImportPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportQuotingExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportPrice.setText(resHelper.getString("menuImportPrice.text"));		
        this.menuImportPrice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
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
        contBidderNumber.setBounds(new Rectangle(355, 9, 270, 19));
        this.add(contBidderNumber, new KDLayout.Constraints(355, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBidder.setBounds(new Rectangle(12, 9, 270, 19));
        this.add(contBidder, new KDLayout.Constraints(12, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contBidderNumber
        contBidderNumber.setBoundEditor(txtBidderNumber);
        //contBidder
        contBidder.setBoundEditor(f7Bidder);

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
        menuBiz.add(menuExportErrorInfo);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.QuotingPriceEditUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
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
            innerActionPerformed("eas", AbstractQuotingPriceEditUI.this, "ActionImportQuotingExcel", "actionImportQuotingExcel_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractQuotingPriceEditUI.this, "ActionExportErrorInfo", "actionExportErrorInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "QuotingPriceEditUI");
    }




}