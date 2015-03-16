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
public abstract class AbstractChooseRoomListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChooseRoomListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseRoomPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseRoomPrintView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSignContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShowRoomDetail;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReserve;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemKeepDown;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSignContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchaseChange;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReclaimRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitOrder;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefundment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBillAdjust;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZhuanKuan;
    protected EntityViewInfo msgQuery = null;
    protected IMetaDataPK msgQueryPK;
    protected ActionPurchase actionPurchase = null;
    protected ActionSignContract actionSignContract = null;
    protected ActionPrePurchase actionPrePurchase = null;
    protected ActionPurchaseChange actionPurchaseChange = null;
    protected ActionCancelChooseRoom actionCancelChooseRoom = null;
    /**
     * output class constructor
     */
    public AbstractChooseRoomListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChooseRoomListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "ChooseRoomQuery");
        msgQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionPurchase
        this.actionPurchase = new ActionPurchase(this);
        getActionManager().registerAction("actionPurchase", actionPurchase);
         this.actionPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSignContract
        this.actionSignContract = new ActionSignContract(this);
        getActionManager().registerAction("actionSignContract", actionSignContract);
         this.actionSignContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrePurchase
        this.actionPrePurchase = new ActionPrePurchase(this);
        getActionManager().registerAction("actionPrePurchase", actionPrePurchase);
         this.actionPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurchaseChange
        this.actionPurchaseChange = new ActionPurchaseChange(this);
        getActionManager().registerAction("actionPurchaseChange", actionPurchaseChange);
         this.actionPurchaseChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelChooseRoom
        this.actionCancelChooseRoom = new ActionCancelChooseRoom(this);
        getActionManager().registerAction("actionCancelChooseRoom", actionCancelChooseRoom);
         this.actionCancelChooseRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.tblMain1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnCancelChooseRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChooseRoomPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChooseRoomPrintView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSignContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemShowRoomDetail = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSinPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrePurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReserve = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemKeepDown = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSignContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchaseChange = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReclaimRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitOrder = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRefundment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBillAdjust = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemZhuanKuan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDPanel2.setName("kDPanel2");
        this.sclPanel.setName("sclPanel");
        this.kDPanel1.setName("kDPanel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.tblMain1.setName("tblMain1");
        this.btnCancelChooseRoom.setName("btnCancelChooseRoom");
        this.btnChooseRoomPrint.setName("btnChooseRoomPrint");
        this.btnChooseRoomPrintView.setName("btnChooseRoomPrintView");
        this.btnPrePurchase.setName("btnPrePurchase");
        this.btnPurchase.setName("btnPurchase");
        this.btnSignContract.setName("btnSignContract");
        this.menuItemShowRoomDetail.setName("menuItemShowRoomDetail");
        this.menuItemSinPurchase.setName("menuItemSinPurchase");
        this.menuItemPrePurchase.setName("menuItemPrePurchase");
        this.menuItemPurchase.setName("menuItemPurchase");
        this.menuItemReserve.setName("menuItemReserve");
        this.menuItemKeepDown.setName("menuItemKeepDown");
        this.menuItemQuitRoom.setName("menuItemQuitRoom");
        this.menuItemChangeRoom.setName("menuItemChangeRoom");
        this.menuItemSignContract.setName("menuItemSignContract");
        this.menuItemReceiveBill.setName("menuItemReceiveBill");
        this.menuItemChangeName.setName("menuItemChangeName");
        this.menuItemPurchaseChange.setName("menuItemPurchaseChange");
        this.menuItemReclaimRoom.setName("menuItemReclaimRoom");
        this.menuItemQuitOrder.setName("menuItemQuitOrder");
        this.menuItemRefundment.setName("menuItemRefundment");
        this.menuItemBillAdjust.setName("menuItemBillAdjust");
        this.menuItemZhuanKuan.setName("menuItemZhuanKuan");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"BMCMessage.id"});

		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));		
        this.btnPrint.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));		
        this.btnPrintPreview.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.pnlMain.setDividerLocation(160);		
        this.pnlMain.setOneTouchExpandable(true);
        // kDTabbedPane1		
        this.kDTabbedPane1.setAutoscrolls(true);
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
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(550);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // kDPanel2
        // sclPanel
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));		
        this.kDPanel1.setAutoscrolls(true);
        // kDScrollPane1		
        this.kDScrollPane1.setPreferredSize(new Dimension(3,75));
        // tblMain1
		String tblMain1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"chooseNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"chooseRoomState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"chooser\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"linkPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"saleMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{chooseNo}</t:Cell><t:Cell>$Resource{chooseRoomState}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{chooser}</t:Cell><t:Cell>$Resource{linkPhone}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{saleMan}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain1.setFormatXml(resHelper.translateString("tblMain1",tblMain1StrXML));
        this.tblMain1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblMain1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain1_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnCancelChooseRoom
        this.btnCancelChooseRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelChooseRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelChooseRoom.setText(resHelper.getString("btnCancelChooseRoom.text"));		
        this.btnCancelChooseRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_returnticket"));
        // btnChooseRoomPrint
        this.btnChooseRoomPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChooseRoomPrint.setText(resHelper.getString("btnChooseRoomPrint.text"));		
        this.btnChooseRoomPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnChooseRoomPrintView
        this.btnChooseRoomPrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChooseRoomPrintView.setText(resHelper.getString("btnChooseRoomPrintView.text"));		
        this.btnChooseRoomPrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_printpreview"));
        // btnPrePurchase
        this.btnPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrePurchase.setText(resHelper.getString("btnPrePurchase.text"));		
        this.btnPrePurchase.setToolTipText(resHelper.getString("btnPrePurchase.toolTipText"));		
        this.btnPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // btnPurchase
        this.btnPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchase.setText(resHelper.getString("btnPurchase.text"));		
        this.btnPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));		
        this.btnPurchase.setToolTipText(resHelper.getString("btnPurchase.toolTipText"));
        // btnSignContract
        this.btnSignContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionSignContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSignContract.setText(resHelper.getString("btnSignContract.text"));		
        this.btnSignContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));		
        this.btnSignContract.setToolTipText(resHelper.getString("btnSignContract.toolTipText"));
        // menuItemShowRoomDetail		
        this.menuItemShowRoomDetail.setVisible(false);		
        this.menuItemShowRoomDetail.setText(resHelper.getString("menuItemShowRoomDetail.text"));
        // menuItemSinPurchase		
        this.menuItemSinPurchase.setText(resHelper.getString("menuItemSinPurchase.text"));
        // menuItemPrePurchase
        this.menuItemPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrePurchase.setText(resHelper.getString("menuItemPrePurchase.text"));		
        this.menuItemPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // menuItemPurchase
        this.menuItemPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchase.setText(resHelper.getString("menuItemPurchase.text"));
        // menuItemReserve		
        this.menuItemReserve.setText(resHelper.getString("menuItemReserve.text"));
        // menuItemKeepDown		
        this.menuItemKeepDown.setText(resHelper.getString("menuItemKeepDown.text"));
        // menuItemQuitRoom		
        this.menuItemQuitRoom.setText(resHelper.getString("menuItemQuitRoom.text"));
        // menuItemChangeRoom		
        this.menuItemChangeRoom.setText(resHelper.getString("menuItemChangeRoom.text"));
        // menuItemSignContract
        this.menuItemSignContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionSignContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSignContract.setText(resHelper.getString("menuItemSignContract.text"));
        // menuItemReceiveBill		
        this.menuItemReceiveBill.setText(resHelper.getString("menuItemReceiveBill.text"));
        // menuItemChangeName		
        this.menuItemChangeName.setText(resHelper.getString("menuItemChangeName.text"));
        // menuItemPurchaseChange
        this.menuItemPurchaseChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchaseChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchaseChange.setText(resHelper.getString("menuItemPurchaseChange.text"));		
        this.menuItemPurchaseChange.setToolTipText(resHelper.getString("menuItemPurchaseChange.toolTipText"));		
        this.menuItemPurchaseChange.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // menuItemReclaimRoom		
        this.menuItemReclaimRoom.setText(resHelper.getString("menuItemReclaimRoom.text"));		
        this.menuItemReclaimRoom.setToolTipText(resHelper.getString("menuItemReclaimRoom.toolTipText"));		
        this.menuItemReclaimRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));
        // menuItemQuitOrder		
        this.menuItemQuitOrder.setText(resHelper.getString("menuItemQuitOrder.text"));		
        this.menuItemQuitOrder.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_requite"));		
        this.menuItemQuitOrder.setToolTipText(resHelper.getString("menuItemQuitOrder.toolTipText"));
        // menuItemRefundment		
        this.menuItemRefundment.setText(resHelper.getString("menuItemRefundment.text"));		
        this.menuItemRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_retract"));
        // menuItemBillAdjust		
        this.menuItemBillAdjust.setText(resHelper.getString("menuItemBillAdjust.text"));		
        this.menuItemBillAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_checkadjust"));		
        this.menuItemBillAdjust.setToolTipText(resHelper.getString("menuItemBillAdjust.toolTipText"));
        // menuItemZhuanKuan		
        this.menuItemZhuanKuan.setText(resHelper.getString("menuItemZhuanKuan.text"));		
        this.menuItemZhuanKuan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_redclash"));
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        pnlMain.setBounds(new Rectangle(10, 9, 784, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 9, 784, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDTabbedPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDTabbedPane1
        kDTabbedPane1.add(kDSplitPane1, resHelper.getString("kDSplitPane1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDSplitPane1
        kDSplitPane1.add(sclPanel, "right");
        kDSplitPane1.add(kDPanel1, "left");
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblMain, BorderLayout.CENTER);
        kDPanel1.add(kDScrollPane1, BorderLayout.SOUTH);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 612, 546));        tblMain1.setBounds(new Rectangle(0, 1, 607, 546));
        kDPanel2.add(tblMain1, new KDLayout.Constraints(0, 1, 607, 546, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        menuView.add(menuItemShowRoomDetail);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemSinPurchase);
        menuBiz.add(menuItemPrePurchase);
        menuBiz.add(menuItemPurchase);
        menuBiz.add(menuItemReserve);
        menuBiz.add(menuItemKeepDown);
        menuBiz.add(menuItemQuitRoom);
        menuBiz.add(menuItemChangeRoom);
        menuBiz.add(menuItemSignContract);
        menuBiz.add(menuItemReceiveBill);
        menuBiz.add(menuItemChangeName);
        menuBiz.add(menuItemPurchaseChange);
        menuBiz.add(menuItemReclaimRoom);
        menuBiz.add(menuItemQuitOrder);
        menuBiz.add(menuItemRefundment);
        menuBiz.add(menuItemBillAdjust);
        menuBiz.add(menuItemZhuanKuan);
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
        this.toolBar.add(btnCancelChooseRoom);
        this.toolBar.add(btnChooseRoomPrint);
        this.toolBar.add(btnChooseRoomPrintView);
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
	    return "com.kingdee.eas.fdc.sellhouse.app.ChooseRoomListUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("msgQuery")) {
            this.msgQuery = (EntityViewInfo)dataObject;
		}
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
     * output tblMain1_tableClicked method
     */
    protected void tblMain1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblMain1_editStopping method
     */
    protected void tblMain1_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("customer.name"));
        sic.add(new SelectorItemInfo("sincerityPurchase.name"));
        sic.add(new SelectorItemInfo("chooser"));
        sic.add(new SelectorItemInfo("linkPhone"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("salesMan.name"));
        sic.add(new SelectorItemInfo("room.name"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("customer.id"));
        sic.add(new SelectorItemInfo("salesMan.id"));
        sic.add(new SelectorItemInfo("sincerityPurchase.id"));
        sic.add(new SelectorItemInfo("id"));
        return sic;
    }        
    	

    /**
     * output actionPurchase_actionPerformed method
     */
    public void actionPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSignContract_actionPerformed method
     */
    public void actionSignContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrePurchase_actionPerformed method
     */
    public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurchaseChange_actionPerformed method
     */
    public void actionPurchaseChange_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelChooseRoom_actionPerformed method
     */
    public void actionCancelChooseRoom_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionSignContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSignContract() {
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
	public RequestContext prepareActionPurchaseChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchaseChange() {
    	return false;
    }
	public RequestContext prepareActionCancelChooseRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelChooseRoom() {
    	return false;
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
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
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
            innerActionPerformed("eas", AbstractChooseRoomListUI.this, "ActionPurchase", "actionPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionSignContract class
     */     
    protected class ActionSignContract extends ItemAction {     
    
        public ActionSignContract()
        {
            this(null);
        }

        public ActionSignContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));
            _tempStr = resHelper.getString("ActionSignContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSignContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSignContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomListUI.this, "ActionSignContract", "actionSignContract_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractChooseRoomListUI.this, "ActionPrePurchase", "actionPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionPurchaseChange class
     */     
    protected class ActionPurchaseChange extends ItemAction {     
    
        public ActionPurchaseChange()
        {
            this(null);
        }

        public ActionPurchaseChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPurchaseChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchaseChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchaseChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomListUI.this, "ActionPurchaseChange", "actionPurchaseChange_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelChooseRoom class
     */     
    protected class ActionCancelChooseRoom extends ItemAction {     
    
        public ActionCancelChooseRoom()
        {
            this(null);
        }

        public ActionCancelChooseRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelChooseRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelChooseRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelChooseRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomListUI.this, "ActionCancelChooseRoom", "actionCancelChooseRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChooseRoomListUI");
    }




}