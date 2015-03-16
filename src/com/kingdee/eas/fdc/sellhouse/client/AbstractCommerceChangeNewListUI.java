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
public abstract class AbstractCommerceChangeNewListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCommerceChangeNewListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSendsms;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPostSend;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnJoin;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStop;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActive;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWriterPaper;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnToTransaction;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowAll;
    protected ActionShare actionShare = null;
    protected ActionJoin actionJoin = null;
    protected ActionStop actionStop = null;
    protected ActionActive actionActive = null;
    protected ActionAddTrack actionAddTrack = null;
    protected ActionWritePaper actionWritePaper = null;
    protected ActionToTransaction actionToTransaction = null;
    protected ActionToPurchase actionToPurchase = null;
    protected ActionToSign actionToSign = null;
    protected ActionImport actionImport = null;
    protected ActionSend actionsend = null;
    protected ActionPostSend actionPostSend = null;
    /**
     * output class constructor
     */
    public AbstractCommerceChangeNewListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCommerceChangeNewListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "CommerceChangeNewQuery");
        //actionShare
        this.actionShare = new ActionShare(this);
        getActionManager().registerAction("actionShare", actionShare);
         this.actionShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionJoin
        this.actionJoin = new ActionJoin(this);
        getActionManager().registerAction("actionJoin", actionJoin);
         this.actionJoin.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStop
        this.actionStop = new ActionStop(this);
        getActionManager().registerAction("actionStop", actionStop);
         this.actionStop.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionActive
        this.actionActive = new ActionActive(this);
        getActionManager().registerAction("actionActive", actionActive);
         this.actionActive.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTrack
        this.actionAddTrack = new ActionAddTrack(this);
        getActionManager().registerAction("actionAddTrack", actionAddTrack);
         this.actionAddTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWritePaper
        this.actionWritePaper = new ActionWritePaper(this);
        getActionManager().registerAction("actionWritePaper", actionWritePaper);
         this.actionWritePaper.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionToTransaction
        this.actionToTransaction = new ActionToTransaction(this);
        getActionManager().registerAction("actionToTransaction", actionToTransaction);
         this.actionToTransaction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionToPurchase
        this.actionToPurchase = new ActionToPurchase(this);
        getActionManager().registerAction("actionToPurchase", actionToPurchase);
         this.actionToPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionToSign
        this.actionToSign = new ActionToSign(this);
        getActionManager().registerAction("actionToSign", actionToSign);
         this.actionToSign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionsend
        this.actionsend = new ActionSend(this);
        getActionManager().registerAction("actionsend", actionsend);
         this.actionsend.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPostSend
        this.actionPostSend = new ActionPostSend(this);
        getActionManager().registerAction("actionPostSend", actionPostSend);
         this.actionPostSend.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSendsms = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPostSend = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnJoin = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnStop = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnActive = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWriterPaper = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnToTransaction = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShowAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSendsms.setName("btnSendsms");
        this.btnPostSend.setName("btnPostSend");
        this.btnShare.setName("btnShare");
        this.btnJoin.setName("btnJoin");
        this.btnStop.setName("btnStop");
        this.btnActive.setName("btnActive");
        this.btnAddTrack.setName("btnAddTrack");
        this.btnWriterPaper.setName("btnWriterPaper");
        this.btnToTransaction.setName("btnToTransaction");
        this.btnImport.setName("btnImport");
        this.btnShowAll.setName("btnShowAll");
        // CoreUI		
        this.setPreferredSize(new Dimension(1016,600));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"classify.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceChanceStage.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"commerceChanceStage.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"saleMan.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"creator.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"newLevel.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"member.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"shareSaleMan.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"isToPur\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol20\" /><t:Column t:key=\"isToPre\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /><t:Column t:key=\"isToSign\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol23\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{customer.name}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{newLevel.name}</t:Cell><t:Cell>$Resource{classify.name}</t:Cell><t:Cell>$Resource{commerceChanceStage.name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{trackDate}</t:Cell><t:Cell>$Resource{trackContent}</t:Cell><t:Cell>$Resource{customer.id}</t:Cell><t:Cell>$Resource{commerceChanceStage.id}</t:Cell><t:Cell>$Resource{saleMan.id}</t:Cell><t:Cell>$Resource{creator.id}</t:Cell><t:Cell>$Resource{newLevel.id}</t:Cell><t:Cell>$Resource{member.id}</t:Cell><t:Cell>$Resource{shareSaleMan.id}</t:Cell><t:Cell>$Resource{isToPur}</t:Cell><t:Cell>$Resource{isToPre}</t:Cell><t:Cell>$Resource{isToSign}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","name","status","customer.name","phone","newLevel.name","classify.name","commerceChanceStage.name","description","saleMan.name","createDate","trackDate","trackContent","customer.id","commerceChanceStage.id","saleMan.id","creator.id","newLevel.id","member.id","shareSaleMan.id","isToPur","isToPre","isToSign","sellProject.id"});


        // btnSendsms
        this.btnSendsms.setAction((IItemAction)ActionProxyFactory.getProxy(actionsend, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSendsms.setText(resHelper.getString("btnSendsms.text"));		
        this.btnSendsms.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sendmessage"));
        // btnPostSend
        this.btnPostSend.setAction((IItemAction)ActionProxyFactory.getProxy(actionPostSend, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPostSend.setText(resHelper.getString("btnPostSend.text"));		
        this.btnPostSend.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_send"));
        // btnShare
        this.btnShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShare.setText(resHelper.getString("btnShare.text"));
        // btnJoin
        this.btnJoin.setAction((IItemAction)ActionProxyFactory.getProxy(actionJoin, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnJoin.setText(resHelper.getString("btnJoin.text"));
        // btnStop
        this.btnStop.setAction((IItemAction)ActionProxyFactory.getProxy(actionStop, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStop.setText(resHelper.getString("btnStop.text"));
        // btnActive
        this.btnActive.setAction((IItemAction)ActionProxyFactory.getProxy(actionActive, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnActive.setText(resHelper.getString("btnActive.text"));
        // btnAddTrack
        this.btnAddTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTrack.setText(resHelper.getString("btnAddTrack.text"));
        // btnWriterPaper
        this.btnWriterPaper.setAction((IItemAction)ActionProxyFactory.getProxy(actionWritePaper, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWriterPaper.setText(resHelper.getString("btnWriterPaper.text"));
        // btnToTransaction		
        this.btnToTransaction.setText(resHelper.getString("btnToTransaction.text"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));
        // btnShowAll		
        this.btnShowAll.setText(resHelper.getString("btnShowAll.text"));
        this.btnShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnShowAll_actionPerformed(e);
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        this.menuBar.add(MenuService);
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
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
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
        this.toolBar.add(btnSendsms);
        this.toolBar.add(btnPostSend);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnShare);
        this.toolBar.add(btnJoin);
        this.toolBar.add(btnStop);
        this.toolBar.add(btnActive);
        this.toolBar.add(btnAddTrack);
        this.toolBar.add(btnWriterPaper);
        this.toolBar.add(btnToTransaction);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnShowAll);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CommerceChangeNewListUIHandler";
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
     * output btnShowAll_actionPerformed method
     */
    protected void btnShowAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("customer.name"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("newLevel.name"));
        sic.add(new SelectorItemInfo("classify.name"));
        sic.add(new SelectorItemInfo("commerceChanceStage.name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("saleMan.name"));
        sic.add(new SelectorItemInfo("trackDate"));
        sic.add(new SelectorItemInfo("trackContent"));
        sic.add(new SelectorItemInfo("customer.id"));
        sic.add(new SelectorItemInfo("commerceChanceStage.id"));
        sic.add(new SelectorItemInfo("saleMan.id"));
        sic.add(new SelectorItemInfo("creator.id"));
        sic.add(new SelectorItemInfo("newLevel.id"));
        sic.add(new SelectorItemInfo("member.id"));
        sic.add(new SelectorItemInfo("shareSaleMan.id"));
        sic.add(new SelectorItemInfo("isToPur"));
        sic.add(new SelectorItemInfo("isToPre"));
        sic.add(new SelectorItemInfo("isToSign"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("createDate"));
        return sic;
    }        
    	

    /**
     * output actionShare_actionPerformed method
     */
    public void actionShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionJoin_actionPerformed method
     */
    public void actionJoin_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionStop_actionPerformed method
     */
    public void actionStop_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionActive_actionPerformed method
     */
    public void actionActive_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTrack_actionPerformed method
     */
    public void actionAddTrack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWritePaper_actionPerformed method
     */
    public void actionWritePaper_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionToTransaction_actionPerformed method
     */
    public void actionToTransaction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionToPurchase_actionPerformed method
     */
    public void actionToPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionToSign_actionPerformed method
     */
    public void actionToSign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSend_actionPerformed method
     */
    public void actionSend_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPostSend_actionPerformed method
     */
    public void actionPostSend_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShare() {
    	return false;
    }
	public RequestContext prepareActionJoin(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionJoin() {
    	return false;
    }
	public RequestContext prepareActionStop(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStop() {
    	return false;
    }
	public RequestContext prepareActionActive(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionActive() {
    	return false;
    }
	public RequestContext prepareActionAddTrack(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTrack() {
    	return false;
    }
	public RequestContext prepareActionWritePaper(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWritePaper() {
    	return false;
    }
	public RequestContext prepareActionToTransaction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToTransaction() {
    	return false;
    }
	public RequestContext prepareActionToPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToPurchase() {
    	return false;
    }
	public RequestContext prepareActionToSign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToSign() {
    	return false;
    }
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionSend(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSend() {
    	return false;
    }
	public RequestContext prepareActionPostSend(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPostSend() {
    	return false;
    }

    /**
     * output ActionShare class
     */     
    protected class ActionShare extends ItemAction {     
    
        public ActionShare()
        {
            this(null);
        }

        public ActionShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionShare", "actionShare_actionPerformed", e);
        }
    }

    /**
     * output ActionJoin class
     */     
    protected class ActionJoin extends ItemAction {     
    
        public ActionJoin()
        {
            this(null);
        }

        public ActionJoin(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionJoin.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJoin.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJoin.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionJoin", "actionJoin_actionPerformed", e);
        }
    }

    /**
     * output ActionStop class
     */     
    protected class ActionStop extends ItemAction {     
    
        public ActionStop()
        {
            this(null);
        }

        public ActionStop(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionStop.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStop.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStop.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionStop", "actionStop_actionPerformed", e);
        }
    }

    /**
     * output ActionActive class
     */     
    protected class ActionActive extends ItemAction {     
    
        public ActionActive()
        {
            this(null);
        }

        public ActionActive(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionActive.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionActive.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionActive.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionActive", "actionActive_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTrack class
     */     
    protected class ActionAddTrack extends ItemAction {     
    
        public ActionAddTrack()
        {
            this(null);
        }

        public ActionAddTrack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddTrack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTrack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTrack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionAddTrack", "actionAddTrack_actionPerformed", e);
        }
    }

    /**
     * output ActionWritePaper class
     */     
    protected class ActionWritePaper extends ItemAction {     
    
        public ActionWritePaper()
        {
            this(null);
        }

        public ActionWritePaper(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionWritePaper.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWritePaper.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWritePaper.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionWritePaper", "actionWritePaper_actionPerformed", e);
        }
    }

    /**
     * output ActionToTransaction class
     */     
    protected class ActionToTransaction extends ItemAction {     
    
        public ActionToTransaction()
        {
            this(null);
        }

        public ActionToTransaction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionToTransaction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToTransaction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToTransaction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionToTransaction", "actionToTransaction_actionPerformed", e);
        }
    }

    /**
     * output ActionToPurchase class
     */     
    protected class ActionToPurchase extends ItemAction {     
    
        public ActionToPurchase()
        {
            this(null);
        }

        public ActionToPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionToPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionToPurchase", "actionToPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionToSign class
     */     
    protected class ActionToSign extends ItemAction {     
    
        public ActionToSign()
        {
            this(null);
        }

        public ActionToSign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionToSign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToSign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToSign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionToSign", "actionToSign_actionPerformed", e);
        }
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionSend class
     */     
    protected class ActionSend extends ItemAction {     
    
        public ActionSend()
        {
            this(null);
        }

        public ActionSend(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSend.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSend.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSend.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionSend", "actionSend_actionPerformed", e);
        }
    }

    /**
     * output ActionPostSend class
     */     
    protected class ActionPostSend extends ItemAction {     
    
        public ActionPostSend()
        {
            this(null);
        }

        public ActionPostSend(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPostSend.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPostSend.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPostSend.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewListUI.this, "ActionPostSend", "actionPostSend_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CommerceChangeNewListUI");
    }




}