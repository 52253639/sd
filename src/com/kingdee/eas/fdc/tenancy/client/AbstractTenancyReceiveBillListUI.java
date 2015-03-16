/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyReceiveBillListUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyReceiveBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearInvoice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCreateInvoice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClearInvoice;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRec;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRec;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBillPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBillPrintView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBillPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBillPrintView;
    protected ActionCreateInvoice actionCreateInvoice = null;
    protected actionClearInvoice actionClearInvoice = null;
    protected ActionRec actionRec = null;
    protected ActionAudit actionAudit = null;
    protected ActionReceiveBillPrint actionReceiveBillPrint = null;
    protected ActionReceiveBillPrintView actionReceiveBillPrintView = null;
    /**
     * output class constructor
     */
    public AbstractTenancyReceiveBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyReceiveBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "FDCReceiveBillQuery");
        //actionCreateInvoice
        this.actionCreateInvoice = new ActionCreateInvoice(this);
        getActionManager().registerAction("actionCreateInvoice", actionCreateInvoice);
         this.actionCreateInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearInvoice
        this.actionClearInvoice = new actionClearInvoice(this);
        getActionManager().registerAction("actionClearInvoice", actionClearInvoice);
         this.actionClearInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRec
        this.actionRec = new ActionRec(this);
        getActionManager().registerAction("actionRec", actionRec);
         this.actionRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBillPrint
        this.actionReceiveBillPrint = new ActionReceiveBillPrint(this);
        getActionManager().registerAction("actionReceiveBillPrint", actionReceiveBillPrint);
         this.actionReceiveBillPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveBillPrintView
        this.actionReceiveBillPrintView = new ActionReceiveBillPrintView(this);
        getActionManager().registerAction("actionReceiveBillPrintView", actionReceiveBillPrintView);
         this.actionReceiveBillPrintView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnCreateInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCreateInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClearInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.btnRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRec = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnReceiveBillPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceiveBillPrintView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemReceiveBillPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBillPrintView = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnCreateInvoice.setName("btnCreateInvoice");
        this.btnClearInvoice.setName("btnClearInvoice");
        this.menuItemCreateInvoice.setName("menuItemCreateInvoice");
        this.menuItemClearInvoice.setName("menuItemClearInvoice");
        this.pnlMain.setName("pnlMain");
        this.treeMain.setName("treeMain");
        this.treeView.setName("treeView");
        this.btnRec.setName("btnRec");
        this.btnAudit.setName("btnAudit");
        this.menuItemRec.setName("menuItemRec");
        this.menuItemAudit.setName("menuItemAudit");
        this.btnReceiveBillPrint.setName("btnReceiveBillPrint");
        this.btnReceiveBillPrintView.setName("btnReceiveBillPrintView");
        this.menuItemReceiveBillPrint.setName("menuItemReceiveBillPrint");
        this.menuItemReceiveBillPrintView.setName("menuItemReceiveBillPrintView");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","billStatus","number","bizDate","room.number","purchase.number","moneyDefine.name","amount","payerName","fdcReceiveBill.receiptNumber","invoice.number","invoice.amount","fdcReceiveBill.payQuomodo"});

		
        this.btnAttachment.setVisible(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnCreateTo.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        // btnCreateInvoice
        this.btnCreateInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateInvoice.setText(resHelper.getString("btnCreateInvoice.text"));		
        this.btnCreateInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));
        // btnClearInvoice
        this.btnClearInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearInvoice.setText(resHelper.getString("btnClearInvoice.text"));		
        this.btnClearInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_clear"));
        // menuItemCreateInvoice
        this.menuItemCreateInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCreateInvoice.setText(resHelper.getString("menuItemCreateInvoice.text"));		
        this.menuItemCreateInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));		
        this.menuItemCreateInvoice.setToolTipText(resHelper.getString("menuItemCreateInvoice.toolTipText"));
        // menuItemClearInvoice
        this.menuItemClearInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClearInvoice.setText(resHelper.getString("menuItemClearInvoice.text"));		
        this.menuItemClearInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_clear"));		
        this.menuItemClearInvoice.setToolTipText(resHelper.getString("menuItemClearInvoice.toolTipText"));
        // pnlMain		
        this.pnlMain.setDividerLocation(200);
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // treeView
        // btnRec
        this.btnRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRec.setText(resHelper.getString("btnRec.text"));		
        this.btnRec.setToolTipText(resHelper.getString("btnRec.toolTipText"));		
        this.btnRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemRec
        this.menuItemRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRec.setText(resHelper.getString("menuItemRec.text"));		
        this.menuItemRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));		
        this.menuItemRec.setToolTipText(resHelper.getString("menuItemRec.toolTipText"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        // btnReceiveBillPrint
        this.btnReceiveBillPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBillPrint.setText(resHelper.getString("btnReceiveBillPrint.text"));		
        this.btnReceiveBillPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnReceiveBillPrintView
        this.btnReceiveBillPrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBillPrintView.setText(resHelper.getString("btnReceiveBillPrintView.text"));		
        this.btnReceiveBillPrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // menuItemReceiveBillPrint
        this.menuItemReceiveBillPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBillPrint.setText(resHelper.getString("menuItemReceiveBillPrint.text"));		
        this.menuItemReceiveBillPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // menuItemReceiveBillPrintView
        this.menuItemReceiveBillPrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBillPrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBillPrintView.setText(resHelper.getString("menuItemReceiveBillPrintView.text"));		
        this.menuItemReceiveBillPrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 582));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 582, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

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
        this.menuBar.add(menuWorkFlow);
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
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCreateInvoice);
        menuBiz.add(menuItemClearInvoice);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemRec);
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemReceiveBillPrint);
        menuBiz.add(menuItemReceiveBillPrintView);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateInvoice);
        this.toolBar.add(btnClearInvoice);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnRec);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnReceiveBillPrint);
        this.toolBar.add(btnReceiveBillPrintView);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyReceiveBillListUIHandler";
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("purchase.number"));
        sic.add(new SelectorItemInfo("moneyDefine.name"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("payerName"));
        sic.add(new SelectorItemInfo("invoice.number"));
        sic.add(new SelectorItemInfo("invoice.amount"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("fdcReceiveBill.payQuomodo"));
        sic.add(new SelectorItemInfo("fdcReceiveBill.receiptNumber"));
        return sic;
    }        
    	

    /**
     * output actionCreateInvoice_actionPerformed method
     */
    public void actionCreateInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearInvoice_actionPerformed method
     */
    public void actionClearInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRec_actionPerformed method
     */
    public void actionRec_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBillPrint_actionPerformed method
     */
    public void actionReceiveBillPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveBillPrintView_actionPerformed method
     */
    public void actionReceiveBillPrintView_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCreateInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateInvoice() {
    	return false;
    }
	public RequestContext prepareactionClearInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionClearInvoice() {
    	return false;
    }
	public RequestContext prepareActionRec(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRec() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionReceiveBillPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBillPrint() {
    	return false;
    }
	public RequestContext prepareActionReceiveBillPrintView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBillPrintView() {
    	return false;
    }

    /**
     * output ActionCreateInvoice class
     */
    protected class ActionCreateInvoice extends ItemAction
    {
        public ActionCreateInvoice()
        {
            this(null);
        }

        public ActionCreateInvoice(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCreateInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillListUI.this, "ActionCreateInvoice", "actionCreateInvoice_actionPerformed", e);
        }
    }

    /**
     * output actionClearInvoice class
     */
    protected class actionClearInvoice extends ItemAction
    {
        public actionClearInvoice()
        {
            this(null);
        }

        public actionClearInvoice(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("actionClearInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClearInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClearInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillListUI.this, "actionClearInvoice", "actionClearInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionRec class
     */
    protected class ActionRec extends ItemAction
    {
        public ActionRec()
        {
            this(null);
        }

        public ActionRec(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillListUI.this, "ActionRec", "actionRec_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */
    protected class ActionAudit extends ItemAction
    {
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveBillPrint class
     */
    protected class ActionReceiveBillPrint extends ItemAction
    {
        public ActionReceiveBillPrint()
        {
            this(null);
        }

        public ActionReceiveBillPrint(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveBillPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillListUI.this, "ActionReceiveBillPrint", "actionReceiveBillPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveBillPrintView class
     */
    protected class ActionReceiveBillPrintView extends ItemAction
    {
        public ActionReceiveBillPrintView()
        {
            this(null);
        }

        public ActionReceiveBillPrintView(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReceiveBillPrintView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrintView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBillPrintView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillListUI.this, "ActionReceiveBillPrintView", "actionReceiveBillPrintView_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyReceiveBillListUI");
    }




}