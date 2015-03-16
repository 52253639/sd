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
public abstract class AbstractRoomPropertyBookListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPropertyBookListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdPaneBatch;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdPaneBook;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBatch;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSearch;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomSelect;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddBatch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBookAlert;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchBook;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatch;
    protected ActionBatchBook actionBatchBook = null;
    protected ActionAddBatch actionAddBatch = null;
    protected ActionBookAlert actionBookAlert = null;
    /**
     * output class constructor
     */
    public AbstractRoomPropertyBookListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPropertyBookListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "NewRoomPropertyBookQuery");
        //actionBatchBook
        this.actionBatchBook = new ActionBatchBook(this);
        getActionManager().registerAction("actionBatchBook", actionBatchBook);
         this.actionBatchBook.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddBatch
        this.actionAddBatch = new ActionAddBatch(this);
        getActionManager().registerAction("actionAddBatch", actionAddBatch);
         this.actionAddBatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBookAlert
        this.actionBookAlert = new ActionBookAlert(this);
        getActionManager().registerAction("actionBookAlert", actionBookAlert);
         this.actionBookAlert.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kdPaneBatch = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdPaneBook = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblBatch = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDButton();
        this.f7RoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAddBatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBookAlert = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchBook = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kdPaneBatch.setName("kdPaneBatch");
        this.kdPaneBook.setName("kdPaneBook");
        this.tblBatch.setName("tblBatch");
        this.contRoom.setName("contRoom");
        this.contCustomer.setName("contCustomer");
        this.btnSearch.setName("btnSearch");
        this.f7RoomSelect.setName("f7RoomSelect");
        this.f7Customer.setName("f7Customer");
        this.btnAddBatch.setName("btnAddBatch");
        this.btnBookAlert.setName("btnBookAlert");
        this.btnBatchBook.setName("btnBatchBook");
        this.menuItemBatch.setName("menuItemBatch");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"purchase.customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"purchase.customerPhones\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"batch.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomPropertyBook.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"step\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"promFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"propState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"propertyState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"propertyDoScheme.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"roomPropertyBook.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"roomBookState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"scheme.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"batch.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"sellProjectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{purchase.customerNames}</t:Cell><t:Cell>$Resource{purchase.customerPhones}</t:Cell><t:Cell>$Resource{batch.number}</t:Cell><t:Cell>$Resource{roomPropertyBook.number}</t:Cell><t:Cell>$Resource{step}</t:Cell><t:Cell>$Resource{promFinishDate}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{propState}</t:Cell><t:Cell>$Resource{propertyState}</t:Cell><t:Cell>$Resource{propertyDoScheme.name}</t:Cell><t:Cell>$Resource{roomPropertyBook.id}</t:Cell><t:Cell>$Resource{roomBookState}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{scheme.id}</t:Cell><t:Cell>$Resource{batch.id}</t:Cell><t:Cell>$Resource{sellProjectId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","room.number","sign.customerNames","sign.customerPhone","batchManage.number","number","step","promiseFinishDate","actualFinishDate","propState","propertyState","propertyDoScheme.name","","room.roomBookState","","propertyDoScheme.id","batchManage.id","sellProject.id"});

		
        this.btnRemove.setText(resHelper.getString("btnRemove.text"));		
        this.btnRemove.setToolTipText(resHelper.getString("btnRemove.toolTipText"));
        // kDTabbedPane1
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdPaneBatch
        // kdPaneBook
        // tblBatch
		String tblBatchStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"batch\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"transactor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectId}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{batch}</t:Cell><t:Cell>$Resource{transactor}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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
        this.f7RoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");		
        this.f7RoomSelect.setDisplayFormat("$name$");
        // f7Customer		
        this.f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.f7Customer.setDisplayFormat("$name$");
        // btnAddBatch
        this.btnAddBatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddBatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddBatch.setText(resHelper.getString("btnAddBatch.text"));
        // btnBookAlert
        this.btnBookAlert.setAction((IItemAction)ActionProxyFactory.getProxy(actionBookAlert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBookAlert.setText(resHelper.getString("btnBookAlert.text"));
        // btnBatchBook
        this.btnBatchBook.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchBook, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchBook.setText(resHelper.getString("btnBatchBook.text"));		
        this.btnBatchBook.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));		
        this.btnBatchBook.setToolTipText(resHelper.getString("btnBatchBook.toolTipText"));
        // menuItemBatch
        this.menuItemBatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchBook, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatch.setText(resHelper.getString("menuItemBatch.text"));		
        this.menuItemBatch.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_BatchAdd"));		
        this.menuItemBatch.setToolTipText(resHelper.getString("menuItemBatch.toolTipText"));
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
        pnlMain.add(kDTabbedPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDTabbedPane1
        kDTabbedPane1.add(kdPaneBatch, resHelper.getString("kdPaneBatch.constraints"));
        kDTabbedPane1.add(kdPaneBook, resHelper.getString("kdPaneBook.constraints"));
        //kdPaneBatch
        kdPaneBatch.setLayout(new KDLayout());
        kdPaneBatch.putClientProperty("OriginalBounds", new Rectangle(0, 0, 744, 546));        tblBatch.setBounds(new Rectangle(0, 38, 742, 509));
        kdPaneBatch.add(tblBatch, new KDLayout.Constraints(0, 38, 742, 509, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(10, 9, 270, 19));
        kdPaneBatch.add(contRoom, new KDLayout.Constraints(10, 9, 270, 19, 0));
        contCustomer.setBounds(new Rectangle(337, 9, 270, 19));
        kdPaneBatch.add(contCustomer, new KDLayout.Constraints(337, 9, 270, 19, 0));
        btnSearch.setBounds(new Rectangle(640, 9, 73, 21));
        kdPaneBatch.add(btnSearch, new KDLayout.Constraints(640, 9, 73, 21, 0));
        //contRoom
        contRoom.setBoundEditor(f7RoomSelect);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //kdPaneBook
kdPaneBook.setLayout(new BorderLayout(0, 0));        kdPaneBook.add(tblMain, BorderLayout.CENTER);

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
        menuEdit.add(menuItemBatch);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAddBatch);
        this.toolBar.add(btnBookAlert);
        this.toolBar.add(btnBatchBook);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBookListUIHandler";
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
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("sign.customerNames"));
        sic.add(new SelectorItemInfo("sign.customerPhone"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("step"));
        sic.add(new SelectorItemInfo("promiseFinishDate"));
        sic.add(new SelectorItemInfo("actualFinishDate"));
        sic.add(new SelectorItemInfo("room.roomBookState"));
        sic.add(new SelectorItemInfo("propertyState"));
        sic.add(new SelectorItemInfo("propertyDoScheme.name"));
        sic.add(new SelectorItemInfo("propertyDoScheme.id"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("batchManage.number"));
        sic.add(new SelectorItemInfo("batchManage.id"));
        sic.add(new SelectorItemInfo("propState"));
        return sic;
    }        
    	

    /**
     * output actionBatchBook_actionPerformed method
     */
    public void actionBatchBook_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddBatch_actionPerformed method
     */
    public void actionAddBatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBookAlert_actionPerformed method
     */
    public void actionBookAlert_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBatchBook(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchBook() {
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
	public RequestContext prepareActionBookAlert(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBookAlert() {
    	return false;
    }

    /**
     * output ActionBatchBook class
     */     
    protected class ActionBatchBook extends ItemAction {     
    
        public ActionBatchBook()
        {
            this(null);
        }

        public ActionBatchBook(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchBook.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchBook.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchBook.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPropertyBookListUI.this, "ActionBatchBook", "actionBatchBook_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractRoomPropertyBookListUI.this, "ActionAddBatch", "actionAddBatch_actionPerformed", e);
        }
    }

    /**
     * output ActionBookAlert class
     */     
    protected class ActionBookAlert extends ItemAction {     
    
        public ActionBookAlert()
        {
            this(null);
        }

        public ActionBookAlert(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBookAlert.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBookAlert.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBookAlert.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPropertyBookListUI.this, "ActionBookAlert", "actionBookAlert_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPropertyBookListUI");
    }




}