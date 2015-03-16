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
public abstract class AbstractRoomIntentListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomIntentListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabNew;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBuyingRoomPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnKeepDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSignContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPayplan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBuyingRoomPlan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemKeepDown;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSignContract;
    protected ActionPayPlan actionPayPlan = null;
    protected ActionBuyingRoomPlan actionBuyingRoomPlan = null;
    protected ActionKeepDown actionKeepDown = null;
    protected ActionSinPurchase actionSinPurchase = null;
    protected ActionPrePurchase actionPrePurchase = null;
    protected ActionPurchase actionPurchase = null;
    protected AcionSignContract acionSignContract = null;
    /**
     * output class constructor
     */
    public AbstractRoomIntentListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomIntentListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomQuery");
        //actionPayPlan
        this.actionPayPlan = new ActionPayPlan(this);
        getActionManager().registerAction("actionPayPlan", actionPayPlan);
         this.actionPayPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBuyingRoomPlan
        this.actionBuyingRoomPlan = new ActionBuyingRoomPlan(this);
        getActionManager().registerAction("actionBuyingRoomPlan", actionBuyingRoomPlan);
         this.actionBuyingRoomPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionKeepDown
        this.actionKeepDown = new ActionKeepDown(this);
        getActionManager().registerAction("actionKeepDown", actionKeepDown);
         this.actionKeepDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSinPurchase
        this.actionSinPurchase = new ActionSinPurchase(this);
        getActionManager().registerAction("actionSinPurchase", actionSinPurchase);
         this.actionSinPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrePurchase
        this.actionPrePurchase = new ActionPrePurchase(this);
        getActionManager().registerAction("actionPrePurchase", actionPrePurchase);
         this.actionPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurchase
        this.actionPurchase = new ActionPurchase(this);
        getActionManager().registerAction("actionPurchase", actionPurchase);
         this.actionPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //acionSignContract
        this.acionSignContract = new AcionSignContract(this);
        getActionManager().registerAction("acionSignContract", acionSignContract);
         this.acionSignContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabNew = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnPayPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBuyingRoomPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnKeepDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSinPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSignContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemPayplan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBuyingRoomPlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemKeepDown = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSinPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrePurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSignContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tabNew.setName("tabNew");
        this.sclPanel.setName("sclPanel");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnPayPlan.setName("btnPayPlan");
        this.btnBuyingRoomPlan.setName("btnBuyingRoomPlan");
        this.btnKeepDown.setName("btnKeepDown");
        this.btnSinPurchase.setName("btnSinPurchase");
        this.btnPrePurchase.setName("btnPrePurchase");
        this.btnPurchase.setName("btnPurchase");
        this.btnSignContract.setName("btnSignContract");
        this.menuItemPayplan.setName("menuItemPayplan");
        this.menuItemBuyingRoomPlan.setName("menuItemBuyingRoomPlan");
        this.menuItemKeepDown.setName("menuItemKeepDown");
        this.menuItemSinPurchase.setName("menuItemSinPurchase");
        this.menuItemPrePurchase.setName("menuItemPrePurchase");
        this.menuItemPurchase.setName("menuItemPurchase");
        this.menuItemSignContract.setName("menuItemSignContract");
        // CoreUI		
        this.setPreferredSize(new Dimension(1016,600));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"sellState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"buildingId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"sellProjectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"roomModel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"buildingProperty.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"face\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"outlook\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"buildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"roomJoinState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"roomBookState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"roomLoanState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"roomCompensateState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{sellState}</t:Cell><t:Cell>$Resource{buildingId}</t:Cell><t:Cell>$Resource{sellProjectId}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomModel.name}</t:Cell><t:Cell>$Resource{buildingProperty.name}</t:Cell><t:Cell>$Resource{face}</t:Cell><t:Cell>$Resource{outlook}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{buildPrice}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{roomJoinState}</t:Cell><t:Cell>$Resource{roomBookState}</t:Cell><t:Cell>$Resource{roomLoanState}</t:Cell><t:Cell>$Resource{roomCompensateState}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","sellState","building.id","sellProject.id","name","roomModel.name","buildingProperty.name","direction.name","sight.name","buildingArea","roomArea","actualBuildingArea","actualRoomArea","buildPrice","roomPrice","standardTotalAmount","roomJoinState","roomBookState","roomLoanState","roomCompensateState"});

		
        this.pnlMain.setDividerLocation(160);		
        this.pnlMain.setPreferredSize(new Dimension(996,580));		
        this.treeView.setShowControlPanel(true);
        // tabNew
        // sclPanel
        // kDScrollPane1
        // btnPayPlan
        this.btnPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionPayPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPayPlan.setText(resHelper.getString("btnPayPlan.text"));		
        this.btnPayPlan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // btnBuyingRoomPlan
        this.btnBuyingRoomPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBuyingRoomPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBuyingRoomPlan.setText(resHelper.getString("btnBuyingRoomPlan.text"));		
        this.btnBuyingRoomPlan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cost"));
        // btnKeepDown
        this.btnKeepDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeepDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnKeepDown.setText(resHelper.getString("btnKeepDown.text"));		
        this.btnKeepDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_downview"));
        // btnSinPurchase
        this.btnSinPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionSinPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSinPurchase.setText(resHelper.getString("btnSinPurchase.text"));		
        this.btnSinPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTree_businessgroup"));
        // btnPrePurchase
        this.btnPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrePurchase.setText(resHelper.getString("btnPrePurchase.text"));		
        this.btnPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // btnPurchase
        this.btnPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchase.setText(resHelper.getString("btnPurchase.text"));		
        this.btnPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // btnSignContract
        this.btnSignContract.setAction((IItemAction)ActionProxyFactory.getProxy(acionSignContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSignContract.setText(resHelper.getString("btnSignContract.text"));		
        this.btnSignContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));
        // menuItemPayplan
        this.menuItemPayplan.setAction((IItemAction)ActionProxyFactory.getProxy(actionPayPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPayplan.setText(resHelper.getString("menuItemPayplan.text"));		
        this.menuItemPayplan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // menuItemBuyingRoomPlan
        this.menuItemBuyingRoomPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBuyingRoomPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBuyingRoomPlan.setText(resHelper.getString("menuItemBuyingRoomPlan.text"));		
        this.menuItemBuyingRoomPlan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cost"));
        // menuItemKeepDown
        this.menuItemKeepDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeepDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemKeepDown.setText(resHelper.getString("menuItemKeepDown.text"));		
        this.menuItemKeepDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_downview"));
        // menuItemSinPurchase
        this.menuItemSinPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionSinPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSinPurchase.setText(resHelper.getString("menuItemSinPurchase.text"));		
        this.menuItemSinPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTree_businessgroup"));
        // menuItemPrePurchase
        this.menuItemPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrePurchase.setText(resHelper.getString("menuItemPrePurchase.text"));		
        this.menuItemPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // menuItemPurchase
        this.menuItemPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchase.setText(resHelper.getString("menuItemPurchase.text"));		
        this.menuItemPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // menuItemSignContract
        this.menuItemSignContract.setAction((IItemAction)ActionProxyFactory.getProxy(acionSignContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSignContract.setText(resHelper.getString("menuItemSignContract.text"));		
        this.menuItemSignContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));
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
        tabNew.setBounds(new Rectangle(9, 9, 1006, 587));
        this.add(tabNew, new KDLayout.Constraints(9, 9, 1006, 587, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabNew
        tabNew.add(sclPanel, resHelper.getString("sclPanel.constraints"));
        tabNew.add(kDScrollPane1, resHelper.getString("kDScrollPane1.constraints"));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(pnlMain, null);
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
        menuBiz.add(menuItemPayplan);
        menuBiz.add(menuItemBuyingRoomPlan);
        menuBiz.add(menuItemKeepDown);
        menuBiz.add(menuItemSinPurchase);
        menuBiz.add(menuItemPrePurchase);
        menuBiz.add(menuItemPurchase);
        menuBiz.add(menuItemSignContract);
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
        this.toolBar.add(btnPayPlan);
        this.toolBar.add(btnBuyingRoomPlan);
        this.toolBar.add(btnKeepDown);
        this.toolBar.add(btnSinPurchase);
        this.toolBar.add(btnPrePurchase);
        this.toolBar.add(btnPurchase);
        this.toolBar.add(btnSignContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomIntentListUIHandler";
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("buildingProperty.name"));
        sic.add(new SelectorItemInfo("direction.name"));
        sic.add(new SelectorItemInfo("sight.name"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("sellState"));
        sic.add(new SelectorItemInfo("buildPrice"));
        sic.add(new SelectorItemInfo("roomPrice"));
        sic.add(new SelectorItemInfo("standardTotalAmount"));
        sic.add(new SelectorItemInfo("roomJoinState"));
        sic.add(new SelectorItemInfo("roomBookState"));
        sic.add(new SelectorItemInfo("roomLoanState"));
        sic.add(new SelectorItemInfo("roomCompensateState"));
        sic.add(new SelectorItemInfo("building.id"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }        
    	

    /**
     * output actionPayPlan_actionPerformed method
     */
    public void actionPayPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBuyingRoomPlan_actionPerformed method
     */
    public void actionBuyingRoomPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionKeepDown_actionPerformed method
     */
    public void actionKeepDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSinPurchase_actionPerformed method
     */
    public void actionSinPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrePurchase_actionPerformed method
     */
    public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurchase_actionPerformed method
     */
    public void actionPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output acionSignContract_actionPerformed method
     */
    public void acionSignContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPayPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPayPlan() {
    	return false;
    }
	public RequestContext prepareActionBuyingRoomPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBuyingRoomPlan() {
    	return false;
    }
	public RequestContext prepareActionKeepDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionKeepDown() {
    	return false;
    }
	public RequestContext prepareActionSinPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSinPurchase() {
    	return false;
    }
	public RequestContext prepareActionPrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrePurchase() {
    	return false;
    }
	public RequestContext prepareActionPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchase() {
    	return false;
    }
	public RequestContext prepareAcionSignContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAcionSignContract() {
    	return false;
    }

    /**
     * output ActionPayPlan class
     */     
    protected class ActionPayPlan extends ItemAction {     
    
        public ActionPayPlan()
        {
            this(null);
        }

        public ActionPayPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPayPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "ActionPayPlan", "actionPayPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionBuyingRoomPlan class
     */     
    protected class ActionBuyingRoomPlan extends ItemAction {     
    
        public ActionBuyingRoomPlan()
        {
            this(null);
        }

        public ActionBuyingRoomPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBuyingRoomPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBuyingRoomPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBuyingRoomPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "ActionBuyingRoomPlan", "actionBuyingRoomPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionKeepDown class
     */     
    protected class ActionKeepDown extends ItemAction {     
    
        public ActionKeepDown()
        {
            this(null);
        }

        public ActionKeepDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionKeepDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeepDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeepDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "ActionKeepDown", "actionKeepDown_actionPerformed", e);
        }
    }

    /**
     * output ActionSinPurchase class
     */     
    protected class ActionSinPurchase extends ItemAction {     
    
        public ActionSinPurchase()
        {
            this(null);
        }

        public ActionSinPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSinPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSinPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSinPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "ActionSinPurchase", "actionSinPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionPrePurchase class
     */     
    protected class ActionPrePurchase extends ItemAction {     
    
        public ActionPrePurchase()
        {
            this(null);
        }

        public ActionPrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "ActionPrePurchase", "actionPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionPurchase class
     */     
    protected class ActionPurchase extends ItemAction {     
    
        public ActionPurchase()
        {
            this(null);
        }

        public ActionPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "ActionPurchase", "actionPurchase_actionPerformed", e);
        }
    }

    /**
     * output AcionSignContract class
     */     
    protected class AcionSignContract extends ItemAction {     
    
        public AcionSignContract()
        {
            this(null);
        }

        public AcionSignContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("AcionSignContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AcionSignContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AcionSignContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomIntentListUI.this, "AcionSignContract", "acionSignContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomIntentListUI");
    }




}