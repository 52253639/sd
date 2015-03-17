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
public abstract class AbstractRoomAreaInputNewUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomAreaInputNewUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAreaImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPlanAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRePlanAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPreAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRePreAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReActAudit;
    protected ActionAreaImport actionAreaImport = null;
    protected ActionPlanAudit actionPlanAudit = null;
    protected ActionRePlanAudit actionRePlanAudit = null;
    protected ActionPreAudit actionPreAudit = null;
    protected ActionRePreAudit actionRePreAudit = null;
    protected ActionActureAudit actionActureAudit = null;
    protected ActionReActureAudit actionReActureAudit = null;
    protected ActionSave actionSave = null;
    /**
     * output class constructor
     */
    public AbstractRoomAreaInputNewUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomAreaInputNewUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomForSHEQuery");
        //actionAreaImport
        this.actionAreaImport = new ActionAreaImport(this);
        getActionManager().registerAction("actionAreaImport", actionAreaImport);
         this.actionAreaImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPlanAudit
        this.actionPlanAudit = new ActionPlanAudit(this);
        getActionManager().registerAction("actionPlanAudit", actionPlanAudit);
         this.actionPlanAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRePlanAudit
        this.actionRePlanAudit = new ActionRePlanAudit(this);
        getActionManager().registerAction("actionRePlanAudit", actionRePlanAudit);
         this.actionRePlanAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPreAudit
        this.actionPreAudit = new ActionPreAudit(this);
        getActionManager().registerAction("actionPreAudit", actionPreAudit);
         this.actionPreAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRePreAudit
        this.actionRePreAudit = new ActionRePreAudit(this);
        getActionManager().registerAction("actionRePreAudit", actionRePreAudit);
         this.actionRePreAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionActureAudit
        this.actionActureAudit = new ActionActureAudit(this);
        getActionManager().registerAction("actionActureAudit", actionActureAudit);
         this.actionActureAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReActureAudit
        this.actionReActureAudit = new ActionReActureAudit(this);
        getActionManager().registerAction("actionReActureAudit", actionReActureAudit);
         this.actionReActureAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAreaImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPlanAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRePlanAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPreAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRePreAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnActAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReActAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSave.setName("btnSave");
        this.btnAreaImport.setName("btnAreaImport");
        this.btnPlanAudit.setName("btnPlanAudit");
        this.btnRePlanAudit.setName("btnRePlanAudit");
        this.btnPreAudit.setName("btnPreAudit");
        this.btnRePreAudit.setName("btnRePreAudit");
        this.btnActAudit.setName("btnActAudit");
        this.btnReActAudit.setName("btnReActAudit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"roomModel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"sellAreaType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"tenancyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"planBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"planRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isPlan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"preBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"preRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"isPre\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"isActualAreaAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"roomModel.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"isPlanAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"isPreAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"isActAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"isChangeSellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomModel.name}</t:Cell><t:Cell>$Resource{sellAreaType}</t:Cell><t:Cell>$Resource{tenancyArea}</t:Cell><t:Cell>$Resource{planBuildingArea}</t:Cell><t:Cell>$Resource{planRoomArea}</t:Cell><t:Cell>$Resource{isPlan}</t:Cell><t:Cell>$Resource{preBuildingArea}</t:Cell><t:Cell>$Resource{preRoomArea}</t:Cell><t:Cell>$Resource{isPre}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{isActualAreaAudited}</t:Cell><t:Cell>$Resource{roomModel.id}</t:Cell><t:Cell>$Resource{isPlanAudited}</t:Cell><t:Cell>$Resource{isPreAudited}</t:Cell><t:Cell>$Resource{isActAudited}</t:Cell><t:Cell>$Resource{isChangeSellType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"id","name","roomNo","number","roomModel.name","sellAreaType","tenancyArea","planBuildingArea","planRoomArea","isPlan","buildingArea","roomArea","isPre","actualBuildingArea","actualRoomArea","isActualAreaAudited","roomModel.id","isPlanAudited","isPreAudited","isActAudited","isChangeSellArea"});


        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // btnAreaImport
        this.btnAreaImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionAreaImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAreaImport.setText(resHelper.getString("btnAreaImport.text"));
        // btnPlanAudit
        this.btnPlanAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionPlanAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPlanAudit.setText(resHelper.getString("btnPlanAudit.text"));
        // btnRePlanAudit
        this.btnRePlanAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionRePlanAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRePlanAudit.setText(resHelper.getString("btnRePlanAudit.text"));
        // btnPreAudit
        this.btnPreAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPreAudit.setText(resHelper.getString("btnPreAudit.text"));
        // btnRePreAudit
        this.btnRePreAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionRePreAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRePreAudit.setText(resHelper.getString("btnRePreAudit.text"));
        // btnActAudit
        this.btnActAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionActureAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnActAudit.setText(resHelper.getString("btnActAudit.text"));
        // btnReActAudit
        this.btnReActAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionReActureAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReActAudit.setText(resHelper.getString("btnReActAudit.text"));
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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnRemove);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAreaImport);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnPlanAudit);
        this.toolBar.add(btnRePlanAudit);
        this.toolBar.add(btnPreAudit);
        this.toolBar.add(btnRePreAudit);
        this.toolBar.add(btnActAudit);
        this.toolBar.add(btnReActAudit);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomAreaInputNewUIHandler";
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
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("roomNo"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("tenancyArea"));
        sic.add(new SelectorItemInfo("planBuildingArea"));
        sic.add(new SelectorItemInfo("planRoomArea"));
        sic.add(new SelectorItemInfo("isPlan"));
        sic.add(new SelectorItemInfo("isPre"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("isActualAreaAudited"));
        sic.add(new SelectorItemInfo("roomModel.id"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("isPlanAudited"));
        sic.add(new SelectorItemInfo("isPreAudited"));
        sic.add(new SelectorItemInfo("isActAudited"));
        sic.add(new SelectorItemInfo("sellAreaType"));
        sic.add(new SelectorItemInfo("isChangeSellArea"));
        return sic;
    }        
    	

    /**
     * output actionAreaImport_actionPerformed method
     */
    public void actionAreaImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPlanAudit_actionPerformed method
     */
    public void actionPlanAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRePlanAudit_actionPerformed method
     */
    public void actionRePlanAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPreAudit_actionPerformed method
     */
    public void actionPreAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRePreAudit_actionPerformed method
     */
    public void actionRePreAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionActureAudit_actionPerformed method
     */
    public void actionActureAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReActureAudit_actionPerformed method
     */
    public void actionReActureAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAreaImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAreaImport() {
    	return false;
    }
	public RequestContext prepareActionPlanAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPlanAudit() {
    	return false;
    }
	public RequestContext prepareActionRePlanAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRePlanAudit() {
    	return false;
    }
	public RequestContext prepareActionPreAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPreAudit() {
    	return false;
    }
	public RequestContext prepareActionRePreAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRePreAudit() {
    	return false;
    }
	public RequestContext prepareActionActureAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionActureAudit() {
    	return false;
    }
	public RequestContext prepareActionReActureAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReActureAudit() {
    	return false;
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

    /**
     * output ActionAreaImport class
     */     
    protected class ActionAreaImport extends ItemAction {     
    
        public ActionAreaImport()
        {
            this(null);
        }

        public ActionAreaImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAreaImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAreaImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAreaImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionAreaImport", "actionAreaImport_actionPerformed", e);
        }
    }

    /**
     * output ActionPlanAudit class
     */     
    protected class ActionPlanAudit extends ItemAction {     
    
        public ActionPlanAudit()
        {
            this(null);
        }

        public ActionPlanAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPlanAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPlanAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPlanAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionPlanAudit", "actionPlanAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionRePlanAudit class
     */     
    protected class ActionRePlanAudit extends ItemAction {     
    
        public ActionRePlanAudit()
        {
            this(null);
        }

        public ActionRePlanAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRePlanAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRePlanAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRePlanAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionRePlanAudit", "actionRePlanAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionPreAudit class
     */     
    protected class ActionPreAudit extends ItemAction {     
    
        public ActionPreAudit()
        {
            this(null);
        }

        public ActionPreAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPreAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionPreAudit", "actionPreAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionRePreAudit class
     */     
    protected class ActionRePreAudit extends ItemAction {     
    
        public ActionRePreAudit()
        {
            this(null);
        }

        public ActionRePreAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRePreAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRePreAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRePreAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionRePreAudit", "actionRePreAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionActureAudit class
     */     
    protected class ActionActureAudit extends ItemAction {     
    
        public ActionActureAudit()
        {
            this(null);
        }

        public ActionActureAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionActureAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionActureAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionActureAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionActureAudit", "actionActureAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionReActureAudit class
     */     
    protected class ActionReActureAudit extends ItemAction {     
    
        public ActionReActureAudit()
        {
            this(null);
        }

        public ActionReActureAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReActureAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReActureAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReActureAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionReActureAudit", "actionReActureAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractRoomAreaInputNewUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomAreaInputNewUI");
    }




}