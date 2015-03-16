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
public abstract class AbstractRoomSignContractListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomSignContractListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane11;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWebMark;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStamp;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPullDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnStamp;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnPullDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRevSearch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuRevSearch;
    protected ActionWebMark actionWebMark = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionBlankOut actionBlankOut = null;
    protected ActionOnRecord actionOnRecord = null;
    protected ActionStamp actionStamp = null;
    protected ActionPullDown actionPullDown = null;
    protected ActionRevSearch actionRevSearch = null;
    protected ActionUnOnRecord actionUnOnRecord = null;
    protected ActionUnStamp actionUnStamp = null;
    protected ActionUnPullDown actionUnPullDown = null;
    /**
     * output class constructor
     */
    public AbstractRoomSignContractListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomSignContractListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomSignContractQuery");
        //actionWebMark
        this.actionWebMark = new ActionWebMark(this);
        getActionManager().registerAction("actionWebMark", actionWebMark);
         this.actionWebMark.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBlankOut
        this.actionBlankOut = new ActionBlankOut(this);
        getActionManager().registerAction("actionBlankOut", actionBlankOut);
         this.actionBlankOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOnRecord
        this.actionOnRecord = new ActionOnRecord(this);
        getActionManager().registerAction("actionOnRecord", actionOnRecord);
         this.actionOnRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStamp
        this.actionStamp = new ActionStamp(this);
        getActionManager().registerAction("actionStamp", actionStamp);
         this.actionStamp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPullDown
        this.actionPullDown = new ActionPullDown(this);
        getActionManager().registerAction("actionPullDown", actionPullDown);
         this.actionPullDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRevSearch
        this.actionRevSearch = new ActionRevSearch(this);
        getActionManager().registerAction("actionRevSearch", actionRevSearch);
         this.actionRevSearch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnOnRecord
        this.actionUnOnRecord = new ActionUnOnRecord(this);
        getActionManager().registerAction("actionUnOnRecord", actionUnOnRecord);
         this.actionUnOnRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnStamp
        this.actionUnStamp = new ActionUnStamp(this);
        getActionManager().registerAction("actionUnStamp", actionUnStamp);
         this.actionUnStamp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnPullDown
        this.actionUnPullDown = new ActionUnPullDown(this);
        getActionManager().registerAction("actionUnPullDown", actionUnPullDown);
         this.actionUnPullDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane11 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnWebMark = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBlankOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnOnRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnStamp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPullDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnOnRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnStamp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnPullDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRevSearch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBlankOut = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemOnRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuRevSearch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane11.setName("kDSplitPane11");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeMain.setName("treeMain");
        this.btnWebMark.setName("btnWebMark");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnBlankOut.setName("btnBlankOut");
        this.btnOnRecord.setName("btnOnRecord");
        this.btnStamp.setName("btnStamp");
        this.btnPullDown.setName("btnPullDown");
        this.btnUnOnRecord.setName("btnUnOnRecord");
        this.btnUnStamp.setName("btnUnStamp");
        this.btnUnPullDown.setName("btnUnPullDown");
        this.btnRevSearch.setName("btnRevSearch");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemBlankOut.setName("menuItemBlankOut");
        this.menuItemOnRecord.setName("menuItemOnRecord");
        this.menuRevSearch.setName("menuRevSearch");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"contractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"contractNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"signJoinDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isOnRecord\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"onRecordDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"saleMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"paymentInfo\" t:width=\"115\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"isStamp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"stampDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"isPullDown\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"pullDownDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{contractNum}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{signJoinDate}</t:Cell><t:Cell>$Resource{isOnRecord}</t:Cell><t:Cell>$Resource{onRecordDate}</t:Cell><t:Cell>$Resource{customerNames}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{saleMan}</t:Cell><t:Cell>$Resource{paymentInfo}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isStamp}</t:Cell><t:Cell>$Resource{stampDate}</t:Cell><t:Cell>$Resource{isPullDown}</t:Cell><t:Cell>$Resource{pullDownDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","contractNumber","name","contractNum","state","room.displayName","room.roomNo","signDate","signJoinDate","isOnRecord","onRecordDate","purchase.customerNames","purchase.customerPhones","salesman.name","","creator.name","createTime","description","isStamp","stampDate","isPullDown","pullDownDate"});


        // kDSplitPane11		
        this.kDSplitPane11.setDividerLocation(200);
        // kDTreeView1
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
        // btnWebMark
        this.btnWebMark.setAction((IItemAction)ActionProxyFactory.getProxy(actionWebMark, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWebMark.setText(resHelper.getString("btnWebMark.text"));		
        this.btnWebMark.setToolTipText(resHelper.getString("btnWebMark.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnBlankOut
        this.btnBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBlankOut.setText(resHelper.getString("btnBlankOut.text"));		
        this.btnBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // btnOnRecord
        this.btnOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOnRecord.setText(resHelper.getString("btnOnRecord.text"));		
        this.btnOnRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));		
        this.btnOnRecord.setToolTipText(resHelper.getString("btnOnRecord.toolTipText"));
        // btnStamp
        this.btnStamp.setAction((IItemAction)ActionProxyFactory.getProxy(actionStamp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStamp.setText(resHelper.getString("btnStamp.text"));		
        this.btnStamp.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_subjectsetting"));
        // btnPullDown
        this.btnPullDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionPullDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPullDown.setText(resHelper.getString("btnPullDown.text"));		
        this.btnPullDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_extendform"));
        // btnUnOnRecord
        this.btnUnOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnOnRecord.setText(resHelper.getString("btnUnOnRecord.text"));		
        this.btnUnOnRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));
        // btnUnStamp
        this.btnUnStamp.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnStamp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnStamp.setText(resHelper.getString("btnUnStamp.text"));		
        this.btnUnStamp.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_subjectsetting"));
        // btnUnPullDown
        this.btnUnPullDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnPullDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnPullDown.setText(resHelper.getString("btnUnPullDown.text"));		
        this.btnUnPullDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_extendform"));
        // btnRevSearch
        this.btnRevSearch.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevSearch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRevSearch.setText(resHelper.getString("btnRevSearch.text"));		
        this.btnRevSearch.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuItemBlankOut
        this.menuItemBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBlankOut.setText(resHelper.getString("menuItemBlankOut.text"));		
        this.menuItemBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // menuItemOnRecord
        this.menuItemOnRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionOnRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemOnRecord.setText(resHelper.getString("menuItemOnRecord.text"));		
        this.menuItemOnRecord.setToolTipText(resHelper.getString("menuItemOnRecord.toolTipText"));		
        this.menuItemOnRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));
        // menuRevSearch
        this.menuRevSearch.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevSearch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuRevSearch.setText(resHelper.getString("menuRevSearch.text"));		
        this.menuRevSearch.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
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
        kDSplitPane11.setBounds(new Rectangle(6, 9, 996, 614));
        this.add(kDSplitPane11, new KDLayout.Constraints(6, 9, 996, 614, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane11
        kDSplitPane11.add(tblMain, "right");
        kDSplitPane11.add(kDTreeView1, "left");
        //kDTreeView1
        kDTreeView1.setTree(treeMain);

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
        menuBiz.add(menuItemBlankOut);
        menuBiz.add(menuItemOnRecord);
        menuBiz.add(menuRevSearch);
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
        this.toolBar.add(btnWebMark);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnBlankOut);
        this.toolBar.add(btnOnRecord);
        this.toolBar.add(btnStamp);
        this.toolBar.add(btnPullDown);
        this.toolBar.add(btnUnOnRecord);
        this.toolBar.add(btnUnStamp);
        this.toolBar.add(btnUnPullDown);
        this.toolBar.add(btnRevSearch);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomSignContractListUIHandler";
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("signJoinDate"));
        sic.add(new SelectorItemInfo("isOnRecord"));
        sic.add(new SelectorItemInfo("onRecordDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isBlankOut"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("room.roomNo"));
        sic.add(new SelectorItemInfo("isStamp"));
        sic.add(new SelectorItemInfo("stampDate"));
        sic.add(new SelectorItemInfo("isPullDown"));
        sic.add(new SelectorItemInfo("pullDownDate"));
        sic.add(new SelectorItemInfo("purchase.customerNames"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("contractNum"));
        sic.add(new SelectorItemInfo("contractNumber"));
        sic.add(new SelectorItemInfo("purchase.customerPhones"));
        sic.add(new SelectorItemInfo("room.displayName"));
        return sic;
    }        
    	

    /**
     * output actionWebMark_actionPerformed method
     */
    public void actionWebMark_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBlankOut_actionPerformed method
     */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOnRecord_actionPerformed method
     */
    public void actionOnRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionStamp_actionPerformed method
     */
    public void actionStamp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPullDown_actionPerformed method
     */
    public void actionPullDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRevSearch_actionPerformed method
     */
    public void actionRevSearch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnOnRecord_actionPerformed method
     */
    public void actionUnOnRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnStamp_actionPerformed method
     */
    public void actionUnStamp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnPullDown_actionPerformed method
     */
    public void actionUnPullDown_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionBlankOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBlankOut() {
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
	public RequestContext prepareActionStamp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStamp() {
    	return false;
    }
	public RequestContext prepareActionPullDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPullDown() {
    	return false;
    }
	public RequestContext prepareActionRevSearch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRevSearch() {
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
	public RequestContext prepareActionUnStamp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnStamp() {
    	return false;
    }
	public RequestContext prepareActionUnPullDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnPullDown() {
    	return false;
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
            this.setEnabled(false);
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cycattemper"));
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
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionWebMark", "actionWebMark_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
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
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionBlankOut class
     */     
    protected class ActionBlankOut extends ItemAction {     
    
        public ActionBlankOut()
        {
            this(null);
        }

        public ActionBlankOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBlankOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionBlankOut", "actionBlankOut_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionOnRecord", "actionOnRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionStamp class
     */     
    protected class ActionStamp extends ItemAction {     
    
        public ActionStamp()
        {
            this(null);
        }

        public ActionStamp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionStamp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStamp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStamp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionStamp", "actionStamp_actionPerformed", e);
        }
    }

    /**
     * output ActionPullDown class
     */     
    protected class ActionPullDown extends ItemAction {     
    
        public ActionPullDown()
        {
            this(null);
        }

        public ActionPullDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPullDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPullDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPullDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionPullDown", "actionPullDown_actionPerformed", e);
        }
    }

    /**
     * output ActionRevSearch class
     */     
    protected class ActionRevSearch extends ItemAction {     
    
        public ActionRevSearch()
        {
            this(null);
        }

        public ActionRevSearch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRevSearch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevSearch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevSearch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionRevSearch", "actionRevSearch_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionUnOnRecord", "actionUnOnRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionUnStamp class
     */     
    protected class ActionUnStamp extends ItemAction {     
    
        public ActionUnStamp()
        {
            this(null);
        }

        public ActionUnStamp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnStamp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnStamp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnStamp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionUnStamp", "actionUnStamp_actionPerformed", e);
        }
    }

    /**
     * output ActionUnPullDown class
     */     
    protected class ActionUnPullDown extends ItemAction {     
    
        public ActionUnPullDown()
        {
            this(null);
        }

        public ActionUnPullDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnPullDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnPullDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnPullDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractListUI.this, "ActionUnPullDown", "actionUnPullDown_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomSignContractListUI");
    }




}