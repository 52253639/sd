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
public abstract class AbstractSignManageListUI extends com.kingdee.eas.fdc.sellhouse.client.BaseTransactionListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSignManageListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelatePrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelatePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWebMark;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemWebMark;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnOnRecord;
    protected ActionRelatePurchase actionRelatePurchase = null;
    protected ActionOnRecord actionOnRecord = null;
    protected ActionUnOnRecord actionUnOnRecord = null;
    protected ActionWebMark actionWebMark = null;
    protected ActionRelatePrePurchase actionRelatePrePurchase = null;
    /**
     * output class constructor
     */
    public AbstractSignManageListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSignManageListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SignManageQuery");
        //actionRelatePurchase
        this.actionRelatePurchase = new ActionRelatePurchase(this);
        getActionManager().registerAction("actionRelatePurchase", actionRelatePurchase);
         this.actionRelatePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOnRecord
        this.actionOnRecord = new ActionOnRecord(this);
        getActionManager().registerAction("actionOnRecord", actionOnRecord);
         this.actionOnRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnOnRecord
        this.actionUnOnRecord = new ActionUnOnRecord(this);
        getActionManager().registerAction("actionUnOnRecord", actionUnOnRecord);
         this.actionUnOnRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWebMark
        this.actionWebMark = new ActionWebMark(this);
        getActionManager().registerAction("actionWebMark", actionWebMark);
         this.actionWebMark.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRelatePrePurchase
        this.actionRelatePrePurchase = new ActionRelatePrePurchase(this);
        getActionManager().registerAction("actionRelatePrePurchase", actionRelatePrePurchase);
         this.actionRelatePrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnRelatePrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRelatePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWebMark = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnOnRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnOnRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemWebMark = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemOnRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnOnRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnRelatePrePurchase.setName("btnRelatePrePurchase");
        this.btnRelatePurchase.setName("btnRelatePurchase");
        this.btnWebMark.setName("btnWebMark");
        this.btnOnRecord.setName("btnOnRecord");
        this.btnUnOnRecord.setName("btnUnOnRecord");
        this.menuItemWebMark.setName("menuItemWebMark");
        this.menuItemOnRecord.setName("menuItemOnRecord");
        this.menuItemUnOnRecord.setName("menuItemUnOnRecord");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol44\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol45\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol46\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"bizNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"bizState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"changeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"productType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomModel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"room.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"customerPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"customer.certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"customer.mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"isOnRecord\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"agioDesc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"lastAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"pur.bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"pur.busAdscriptionDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"busAdscriptionDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"sellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"valuationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"payType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"bulidingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"actBulidingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"strdBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"actRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"strdRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"strdTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"dealBuildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"dealRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"dealTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"sellAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"attachmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"fitmentTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"saleManNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"signCustomerEntry.isMain\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" t:styleID=\"sCol44\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" t:styleID=\"sCol45\" /><t:Column t:key=\"productType.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol46\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizNumber}</t:Cell><t:Cell>$Resource{bizState}</t:Cell><t:Cell>$Resource{changeDate}</t:Cell><t:Cell>$Resource{productType.name}</t:Cell><t:Cell>$Resource{roomModel.name}</t:Cell><t:Cell>$Resource{room.name}</t:Cell><t:Cell>$Resource{customerNames}</t:Cell><t:Cell>$Resource{customerPhone}</t:Cell><t:Cell>$Resource{customer.certificateNumber}</t:Cell><t:Cell>$Resource{customer.mailAddress}</t:Cell><t:Cell>$Resource{isOnRecord}</t:Cell><t:Cell>$Resource{agioDesc}</t:Cell><t:Cell>$Resource{lastAgio}</t:Cell><t:Cell>$Resource{pur.bizDate}</t:Cell><t:Cell>$Resource{pur.busAdscriptionDate}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{busAdscriptionDate}</t:Cell><t:Cell>$Resource{sellType}</t:Cell><t:Cell>$Resource{valuationType}</t:Cell><t:Cell>$Resource{payType.name}</t:Cell><t:Cell>$Resource{bulidingArea}</t:Cell><t:Cell>$Resource{actBulidingArea}</t:Cell><t:Cell>$Resource{strdBuildingPrice}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actRoomArea}</t:Cell><t:Cell>$Resource{strdRoomPrice}</t:Cell><t:Cell>$Resource{strdTotalAmount}</t:Cell><t:Cell>$Resource{dealBuildPrice}</t:Cell><t:Cell>$Resource{dealRoomPrice}</t:Cell><t:Cell>$Resource{dealTotalAmount}</t:Cell><t:Cell>$Resource{sellAmount}</t:Cell><t:Cell>$Resource{attachmentAmount}</t:Cell><t:Cell>$Resource{fitmentTotalAmount}</t:Cell><t:Cell>$Resource{saleManNames}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{signCustomerEntry.isMain}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{productType.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","bizNumber","bizState","changeDate","productType.name","roomModel.name","room.name","customerNames","customerPhone","customer.certificateNumber","customer.mailAddress","isOnRecord","agioDesc","lastAgio","pur.bizDate","pur.busAdscriptionDate","bizDate","busAdscriptionDate","sellType","valuationType","payType.name","bulidingArea","actBulidingArea","strdBuildingPrice","roomArea","actRoomArea","strdRoomPrice","strdTotalAmount","dealBuildPrice","dealRoomPrice","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount","saleManNames","salesman.name","creator.name","createTime","auditor.name","auditTime","lastUpdateUser.name","lastUpdateTime","description","signCustomerEntry.isMain","sellProject.id","productType.id"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        // btnRelatePrePurchase
        this.btnRelatePrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelatePrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelatePrePurchase.setText(resHelper.getString("btnRelatePrePurchase.text"));
        // btnRelatePurchase
        this.btnRelatePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelatePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelatePurchase.setText(resHelper.getString("btnRelatePurchase.text"));		
        this.btnRelatePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_assistantaccount"));
        // btnWebMark
        this.btnWebMark.setAction((IItemAction)ActionProxyFactory.getProxy(actionWebMark, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWebMark.setText(resHelper.getString("btnWebMark.text"));		
        this.btnWebMark.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_subjectsetting"));
        // btnOnRecord
        this.btnOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOnRecord.setText(resHelper.getString("btnOnRecord.text"));		
        this.btnOnRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));
        // btnUnOnRecord
        this.btnUnOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnOnRecord.setText(resHelper.getString("btnUnOnRecord.text"));		
        this.btnUnOnRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));
        // menuItemWebMark
        this.menuItemWebMark.setAction((IItemAction)ActionProxyFactory.getProxy(actionWebMark, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemWebMark.setText(resHelper.getString("menuItemWebMark.text"));
        // menuItemOnRecord
        this.menuItemOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemOnRecord.setText(resHelper.getString("menuItemOnRecord.text"));
        // menuItemUnOnRecord
        this.menuItemUnOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnOnRecord.setText(resHelper.getString("menuItemUnOnRecord.text"));
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
        pnlMain.setBounds(new Rectangle(10, 6, 996, 616));
        this.add(pnlMain, new KDLayout.Constraints(10, 6, 996, 616, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemInvalid);
        menuBiz.add(menuItemReceiveBill);
        menuBiz.add(menuItemChangeName);
        menuBiz.add(menuItemQuitRoom);
        menuBiz.add(menuItemChangeRoom);
        menuBiz.add(menuItemPriceChange);
        menuBiz.add(menuItemWebMark);
        menuBiz.add(menuItemOnRecord);
        menuBiz.add(menuItemUnOnRecord);
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
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnMultiSubmit);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnInvalid);
        this.toolBar.add(btnReceiveBill);
        this.toolBar.add(btnSpecialBiz);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnRelatePrePurchase);
        this.toolBar.add(btnRelatePurchase);
        this.toolBar.add(btnWebMark);
        this.toolBar.add(btnOnRecord);
        this.toolBar.add(btnUnOnRecord);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SignManageListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
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

			public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("creator.*"));
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("CU.*"));
			sic.add(new SelectorItemInfo("handler.*"));
			sic.add(new SelectorItemInfo("auditor.*"));
			sic.add(new SelectorItemInfo("orgUnit.*"));
			sic.add(new SelectorItemInfo("period.*"));
			sic.add(new SelectorItemInfo("room.*"));
			sic.add(new SelectorItemInfo("insider.*"));
			sic.add(new SelectorItemInfo("agioScheme.*"));
			sic.add(new SelectorItemInfo("sellProject.*"));
			sic.add(new SelectorItemInfo("salesman.*"));
			sic.add(new SelectorItemInfo("payType.*"));
			sic.add(new SelectorItemInfo("fitmentStandard.*"));
			sic.add(new SelectorItemInfo("marketUnit.*"));
			sic.add(new SelectorItemInfo("newCommerceChance.*"));
			sic.add(new SelectorItemInfo("signPayListEntry.*"));
			sic.add(new SelectorItemInfo("signPayListEntry.moneyDefine.*"));
			sic.add(new SelectorItemInfo("signPayListEntry.currency.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.creator.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.CU.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.currency.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.room.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.signAgioEntry.*"));
			sic.add(new SelectorItemInfo("signRoomAttachmentEntry.signAgioEntry.agio.*"));
			sic.add(new SelectorItemInfo("signAgioEntry.*"));
			sic.add(new SelectorItemInfo("signAgioEntry.agio.*"));
			sic.add(new SelectorItemInfo("signCustomerEntry.*"));
			sic.add(new SelectorItemInfo("signCustomerEntry.customer.*"));
			sic.add(new SelectorItemInfo("signCustomerEntry.certificate.*"));
			sic.add(new SelectorItemInfo("signSaleManEntry.*"));
			sic.add(new SelectorItemInfo("signSaleManEntry.user.*"));
			sic.add(new SelectorItemInfo("LoanBank.*"));
			sic.add(new SelectorItemInfo("AcfBank.*"));
			sic.add(new SelectorItemInfo("specialAgio.*"));
			return sic;
		}

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizNumber"));
        sic.add(new SelectorItemInfo("bizState"));
        sic.add(new SelectorItemInfo("productType.name"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("room.name"));
        sic.add(new SelectorItemInfo("customerNames"));
        sic.add(new SelectorItemInfo("customerPhone"));
        sic.add(new SelectorItemInfo("customer.certificateNumber"));
        sic.add(new SelectorItemInfo("customer.mailAddress"));
        sic.add(new SelectorItemInfo("agioDesc"));
        sic.add(new SelectorItemInfo("lastAgio"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("busAdscriptionDate"));
        sic.add(new SelectorItemInfo("sellType"));
        sic.add(new SelectorItemInfo("valuationType"));
        sic.add(new SelectorItemInfo("payType.name"));
        sic.add(new SelectorItemInfo("bulidingArea"));
        sic.add(new SelectorItemInfo("strdBuildingPrice"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("strdRoomPrice"));
        sic.add(new SelectorItemInfo("strdTotalAmount"));
        sic.add(new SelectorItemInfo("dealBuildPrice"));
        sic.add(new SelectorItemInfo("dealRoomPrice"));
        sic.add(new SelectorItemInfo("dealTotalAmount"));
        sic.add(new SelectorItemInfo("attachmentAmount"));
        sic.add(new SelectorItemInfo("fitmentTotalAmount"));
        sic.add(new SelectorItemInfo("saleManNames"));
        sic.add(new SelectorItemInfo("isOnRecord"));
        sic.add(new SelectorItemInfo("onRecordDate"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("signCustomerEntry.isMain"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("productType.id"));
        sic.add(new SelectorItemInfo("sellAmount"));
        sic.add(new SelectorItemInfo("pur.bizDate"));
        sic.add(new SelectorItemInfo("pur.busAdscriptionDate"));
        sic.add(new SelectorItemInfo("actBulidingArea"));
        sic.add(new SelectorItemInfo("actRoomArea"));
        sic.add(new SelectorItemInfo("changeDate"));
        sic.add(new SelectorItemInfo("salesman.name"));
        return sic;
    }        
    	

    /**
     * output actionRelatePurchase_actionPerformed method
     */
    public void actionRelatePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOnRecord_actionPerformed method
     */
    public void actionOnRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnOnRecord_actionPerformed method
     */
    public void actionUnOnRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWebMark_actionPerformed method
     */
    public void actionWebMark_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRelatePrePurchase_actionPerformed method
     */
    public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRelatePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelatePurchase() {
    	return false;
    }
	public RequestContext prepareActionOnRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOnRecord() {
    	return false;
    }
	public RequestContext prepareActionUnOnRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnOnRecord() {
    	return false;
    }
	public RequestContext prepareActionWebMark(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWebMark() {
    	return false;
    }
	public RequestContext prepareActionRelatePrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelatePrePurchase() {
    	return false;
    }

    /**
     * output ActionRelatePurchase class
     */     
    protected class ActionRelatePurchase extends ItemAction {     
    
        public ActionRelatePurchase()
        {
            this(null);
        }

        public ActionRelatePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRelatePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelatePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelatePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSignManageListUI.this, "ActionRelatePurchase", "actionRelatePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionOnRecord class
     */     
    protected class ActionOnRecord extends ItemAction {     
    
        public ActionOnRecord()
        {
            this(null);
        }

        public ActionOnRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOnRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOnRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOnRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSignManageListUI.this, "ActionOnRecord", "actionOnRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionUnOnRecord class
     */     
    protected class ActionUnOnRecord extends ItemAction {     
    
        public ActionUnOnRecord()
        {
            this(null);
        }

        public ActionUnOnRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnOnRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnOnRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnOnRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSignManageListUI.this, "ActionUnOnRecord", "actionUnOnRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionWebMark class
     */     
    protected class ActionWebMark extends ItemAction {     
    
        public ActionWebMark()
        {
            this(null);
        }

        public ActionWebMark(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionWebMark.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWebMark.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWebMark.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSignManageListUI.this, "ActionWebMark", "actionWebMark_actionPerformed", e);
        }
    }

    /**
     * output ActionRelatePrePurchase class
     */     
    protected class ActionRelatePrePurchase extends ItemAction {     
    
        public ActionRelatePrePurchase()
        {
            this(null);
        }

        public ActionRelatePrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRelatePrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelatePrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelatePrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSignManageListUI.this, "ActionRelatePrePurchase", "actionRelatePrePurchase_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SignManageListUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.SignManageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.SignManageInfo objectValue = new com.kingdee.eas.fdc.sellhouse.SignManageInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"id","number","bizNumber","bizState","changeDate","productType.name","roomModel.name","room.name","customerNames","customerPhone","customer.certificateNumber","customer.mailAddress","isOnRecord","agioDesc","lastAgio","pur.bizDate","pur.busAdscriptionDate","bizDate","busAdscriptionDate","sellType","valuationType","payType.name","actBulidingArea","strdBuildingPrice","actRoomArea","strdRoomPrice","strdTotalAmount","dealBuildPrice","dealRoomPrice","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount","saleManNames","salesman.name","creator.name","createTime","auditor.name","auditTime","lastUpdateUser.name","lastUpdateTime","description","sellProject.id","productType.id"};
    }



	protected String getTDFileName() {
    	return "/bim/fdc/sellhouse/SignManage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SignManageQuery");
	}

}