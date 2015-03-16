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
public abstract class AbstractRoomJoinListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomJoinListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBatch;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomJoin;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBatch;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSearch;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomSelect;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddBatch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnJoinNotice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchJoin;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchJoin;
    protected ActionBatchJoin actionBatchJoin = null;
    protected ActionAddBatch actionAddBatch = null;
    protected ActionJoinAlert actionJoinAlert = null;
    /**
     * output class constructor
     */
    public AbstractRoomJoinListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomJoinListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "NewRoomJoinQuery");
        //actionBatchJoin
        this.actionBatchJoin = new ActionBatchJoin(this);
        getActionManager().registerAction("actionBatchJoin", actionBatchJoin);
         this.actionBatchJoin.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddBatch
        this.actionAddBatch = new ActionAddBatch(this);
        getActionManager().registerAction("actionAddBatch", actionAddBatch);
         this.actionAddBatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionJoinAlert
        this.actionJoinAlert = new ActionJoinAlert(this);
        getActionManager().registerAction("actionJoinAlert", actionJoinAlert);
         this.actionJoinAlert.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabbedPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlBatch = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRoomJoin = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblBatch = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDButton();
        this.f7RoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAddBatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnJoinNotice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchJoin = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatchJoin = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tabbedPanel.setName("tabbedPanel");
        this.pnlBatch.setName("pnlBatch");
        this.pnlRoomJoin.setName("pnlRoomJoin");
        this.tblBatch.setName("tblBatch");
        this.contRoom.setName("contRoom");
        this.contCustomer.setName("contCustomer");
        this.btnSearch.setName("btnSearch");
        this.f7RoomSelect.setName("f7RoomSelect");
        this.f7Customer.setName("f7Customer");
        this.btnAddBatch.setName("btnAddBatch");
        this.btnJoinNotice.setName("btnJoinNotice");
        this.btnBatchJoin.setName("btnBatchJoin");
        this.menuItemBatchJoin.setName("menuItemBatchJoin");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"sign.customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"sign.customerPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"batchManage.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"joinState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"apptDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"joinDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"joinDoScheme.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"currentApproach\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"promFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"dealAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"isAllRev\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"room.roomJoinState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"batchManage.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"sign.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"joinDoScheme.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{sign.customerNames}</t:Cell><t:Cell>$Resource{sign.customerPhone}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{batchManage.number}</t:Cell><t:Cell>$Resource{joinState}</t:Cell><t:Cell>$Resource{apptDate}</t:Cell><t:Cell>$Resource{joinDate}</t:Cell><t:Cell>$Resource{joinDoScheme.name}</t:Cell><t:Cell>$Resource{currentApproach}</t:Cell><t:Cell>$Resource{promFinishDate}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{dealAmount}</t:Cell><t:Cell>$Resource{isAllRev}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{room.roomJoinState}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{batchManage.id}</t:Cell><t:Cell>$Resource{sign.id}</t:Cell><t:Cell>$Resource{joinDoScheme.id}</t:Cell><t:Cell>$Resource{room.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"room.name","sign.customerNames","sign.customerPhone","number","batchManage.number","joinState","apptJoinDate","joinDate","roomJoinDoScheme.name","currentApproach","promiseFinishDate","actualFinishDate","sign.dealTotalAmount","isAllRev","id","room.roomJoinState","sellProject.id","batchManage.id","sign.id","roomJoinDoScheme.id","room.id"});

		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnView.setText(resHelper.getString("btnView.text"));		
        this.btnView.setToolTipText(resHelper.getString("btnView.toolTipText"));		
        this.btnRemove.setText(resHelper.getString("btnRemove.text"));		
        this.btnRemove.setToolTipText(resHelper.getString("btnRemove.toolTipText"));		
        this.btnLocate.setEnabled(false);		
        this.btnLocate.setVisible(false);		
        this.menuItemRemove.setText(resHelper.getString("menuItemRemove.text"));		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnQueryScheme.setEnabled(false);		
        this.btnQueryScheme.setVisible(false);		
        this.btnMoveTree.setEnabled(false);
        // tabbedPanel
        this.tabbedPanel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    tabbedPanel_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pnlBatch
        // pnlRoomJoin
        // tblBatch
		String tblBatchStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sellProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"transactor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sellProject}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{transactor}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBatch.setFormatXml(resHelper.translateString("tblBatch",tblBatchStrXML));
        this.tblBatch.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblBatch_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(60);		
        this.contRoom.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(60);		
        this.contCustomer.setBoundLabelUnderline(true);
        // btnSearch		
        this.btnSearch.setText(resHelper.getString("btnSearch.text"));
        this.btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSearch_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // f7RoomSelect		
        this.f7RoomSelect.setDisplayFormat("$name$");		
        this.f7RoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
        // f7Customer		
        this.f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.f7Customer.setDisplayFormat("$name$");
        // btnAddBatch
        this.btnAddBatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddBatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddBatch.setText(resHelper.getString("btnAddBatch.text"));
        // btnJoinNotice
        this.btnJoinNotice.setAction((IItemAction)ActionProxyFactory.getProxy(actionJoinAlert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnJoinNotice.setText(resHelper.getString("btnJoinNotice.text"));
        // btnBatchJoin
        this.btnBatchJoin.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchJoin, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchJoin.setText(resHelper.getString("btnBatchJoin.text"));		
        this.btnBatchJoin.setToolTipText(resHelper.getString("btnBatchJoin.toolTipText"));
        // menuItemBatchJoin
        this.menuItemBatchJoin.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchJoin, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchJoin.setText(resHelper.getString("menuItemBatchJoin.text"));		
        this.menuItemBatchJoin.setMnemonic(66);
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
        pnlMain.add(treeView, "left");
        pnlMain.add(tabbedPanel, "right");
        //treeView
        treeView.setTree(treeMain);
        //tabbedPanel
        tabbedPanel.add(pnlBatch, resHelper.getString("pnlBatch.constraints"));
        tabbedPanel.add(pnlRoomJoin, resHelper.getString("pnlRoomJoin.constraints"));
        //pnlBatch
        pnlBatch.setLayout(new KDLayout());
        pnlBatch.putClientProperty("OriginalBounds", new Rectangle(0, 0, 744, 546));        tblBatch.setBounds(new Rectangle(10, 38, 724, 503));
        pnlBatch.add(tblBatch, new KDLayout.Constraints(10, 38, 724, 503, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(10, 10, 270, 19));
        pnlBatch.add(contRoom, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contCustomer.setBounds(new Rectangle(325, 10, 270, 19));
        pnlBatch.add(contCustomer, new KDLayout.Constraints(325, 10, 270, 19, 0));
        btnSearch.setBounds(new Rectangle(625, 10, 73, 21));
        pnlBatch.add(btnSearch, new KDLayout.Constraints(625, 10, 73, 21, 0));
        //contRoom
        contRoom.setBoundEditor(f7RoomSelect);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //pnlRoomJoin
        pnlRoomJoin.setLayout(new KDLayout());
        pnlRoomJoin.putClientProperty("OriginalBounds", new Rectangle(0, 0, 744, 546));        tblMain.setBounds(new Rectangle(10, 10, 724, 530));
        pnlRoomJoin.add(tblMain, new KDLayout.Constraints(10, 10, 724, 530, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        menuEdit.add(menuItemBatchJoin);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnAddBatch);
        this.toolBar.add(btnJoinNotice);
        this.toolBar.add(btnBatchJoin);
        this.toolBar.add(btnRemove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomJoinListUIHandler";
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
     * output tabbedPanel_stateChanged method
     */
    protected void tabbedPanel_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output tblBatch_tableClicked method
     */
    protected void tblBatch_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnSearch_actionPerformed method
     */
    protected void btnSearch_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("sign.customerNames"));
        sic.add(new SelectorItemInfo("sign.customerPhone"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("batchManage.number"));
        sic.add(new SelectorItemInfo("room.roomJoinState"));
        sic.add(new SelectorItemInfo("apptJoinDate"));
        sic.add(new SelectorItemInfo("joinDate"));
        sic.add(new SelectorItemInfo("roomJoinDoScheme.name"));
        sic.add(new SelectorItemInfo("currentApproach"));
        sic.add(new SelectorItemInfo("promiseFinishDate"));
        sic.add(new SelectorItemInfo("actualFinishDate"));
        sic.add(new SelectorItemInfo("sign.dealTotalAmount"));
        sic.add(new SelectorItemInfo("isAllRev"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("batchManage.id"));
        sic.add(new SelectorItemInfo("sign.id"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("roomJoinDoScheme.id"));
        sic.add(new SelectorItemInfo("joinState"));
        sic.add(new SelectorItemInfo("room.name"));
        return sic;
    }        
    	

    /**
     * output actionBatchJoin_actionPerformed method
     */
    public void actionBatchJoin_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddBatch_actionPerformed method
     */
    public void actionAddBatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionJoinAlert_actionPerformed method
     */
    public void actionJoinAlert_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBatchJoin(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchJoin() {
    	return false;
    }
	public RequestContext prepareActionAddBatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddBatch() {
    	return false;
    }
	public RequestContext prepareActionJoinAlert(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionJoinAlert() {
    	return false;
    }

    /**
     * output ActionBatchJoin class
     */     
    protected class ActionBatchJoin extends ItemAction {     
    
        public ActionBatchJoin()
        {
            this(null);
        }

        public ActionBatchJoin(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
            _tempStr = resHelper.getString("ActionBatchJoin.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchJoin.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchJoin.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomJoinListUI.this, "ActionBatchJoin", "actionBatchJoin_actionPerformed", e);
        }
    }

    /**
     * output ActionAddBatch class
     */     
    protected class ActionAddBatch extends ItemAction {     
    
        public ActionAddBatch()
        {
            this(null);
        }

        public ActionAddBatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddBatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddBatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddBatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomJoinListUI.this, "ActionAddBatch", "actionAddBatch_actionPerformed", e);
        }
    }

    /**
     * output ActionJoinAlert class
     */     
    protected class ActionJoinAlert extends ItemAction {     
    
        public ActionJoinAlert()
        {
            this(null);
        }

        public ActionJoinAlert(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionJoinAlert.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJoinAlert.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJoinAlert.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomJoinListUI.this, "ActionJoinAlert", "actionJoinAlert_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomJoinListUI");
    }




}