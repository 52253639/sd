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
public abstract class AbstractRoomAreaCompensateListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomAreaCompensateListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchCompensate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmitWorkFlow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExecute;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmitRoomWorkFlow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_Audit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_UnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenu1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSingleSheme;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenu2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchComp;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchEdit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmitWorkFlow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExecute;
    protected ActionBatchCompensate actionBatchCompensate = null;
    protected ActionSingleScheme actionSingleScheme = null;
    protected ActionBatchEdit actionBatchEdit = null;
    protected ActionSubmitWorkFlow actionSubmitWorkFlow = null;
    protected ActionExecute actionExecute = null;
    protected ActionAudit actionAudit = null;
    protected ActionSubmitRoomWorkFlow actionSubmitRoomWorkFlow = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractRoomAreaCompensateListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomAreaCompensateListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomAreaCompensateQuery");
        //actionEdit
        String _tempStr = null;
        actionEdit.setEnabled(true);
        actionEdit.setDaemonRun(false);

        actionEdit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionBatchCompensate
        this.actionBatchCompensate = new ActionBatchCompensate(this);
        getActionManager().registerAction("actionBatchCompensate", actionBatchCompensate);
         this.actionBatchCompensate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSingleScheme
        this.actionSingleScheme = new ActionSingleScheme(this);
        getActionManager().registerAction("actionSingleScheme", actionSingleScheme);
         this.actionSingleScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchEdit
        this.actionBatchEdit = new ActionBatchEdit(this);
        getActionManager().registerAction("actionBatchEdit", actionBatchEdit);
         this.actionBatchEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmitWorkFlow
        this.actionSubmitWorkFlow = new ActionSubmitWorkFlow(this);
        getActionManager().registerAction("actionSubmitWorkFlow", actionSubmitWorkFlow);
         this.actionSubmitWorkFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExecute
        this.actionExecute = new ActionExecute(this);
        getActionManager().registerAction("actionExecute", actionExecute);
         this.actionExecute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmitRoomWorkFlow
        this.actionSubmitRoomWorkFlow = new ActionSubmitRoomWorkFlow(this);
        getActionManager().registerAction("actionSubmitRoomWorkFlow", actionSubmitRoomWorkFlow);
         this.actionSubmitRoomWorkFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnBatchCompensate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSubmitWorkFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExecute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSubmitRoomWorkFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btn_Audit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btn_UnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenu1 = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemSingleSheme = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenu2 = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemBatchComp = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBatchEdit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSubmitWorkFlow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExecute = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeMain.setName("treeMain");
        this.btnBatchCompensate.setName("btnBatchCompensate");
        this.btnSubmitWorkFlow.setName("btnSubmitWorkFlow");
        this.btnExecute.setName("btnExecute");
        this.btnSubmitRoomWorkFlow.setName("btnSubmitRoomWorkFlow");
        this.btn_Audit.setName("btn_Audit");
        this.btn_UnAudit.setName("btn_UnAudit");
        this.kDMenu1.setName("kDMenu1");
        this.menuItemSingleSheme.setName("menuItemSingleSheme");
        this.kDMenu2.setName("kDMenu2");
        this.menuItemBatchComp.setName("menuItemBatchComp");
        this.menuItemBatchEdit.setName("menuItemBatchEdit");
        this.menuItemSubmitWorkFlow.setName("menuItemSubmitWorkFlow");
        this.menuItemExecute.setName("menuItemExecute");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"roomAreaCompensate.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"sellProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"subarea.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"building.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"building.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isCalByRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"purchase.dealPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"purchase.dealAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"roomAreaCompensate.compensateRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"roomAreaCompensate.compensateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"roomAreaCompensate.latestAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"customer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"payType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"roomAreaCompensate.compensateState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"sellState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{roomAreaCompensate.id}</t:Cell><t:Cell>$Resource{sellProject.id}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{subarea.name}</t:Cell><t:Cell>$Resource{building.id}</t:Cell><t:Cell>$Resource{building.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{isCalByRoomArea}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{purchase.dealPrice}</t:Cell><t:Cell>$Resource{purchase.dealAmount}</t:Cell><t:Cell>$Resource{roomAreaCompensate.compensateRate}</t:Cell><t:Cell>$Resource{roomAreaCompensate.compensateAmount}</t:Cell><t:Cell>$Resource{roomAreaCompensate.latestAmount}</t:Cell><t:Cell>$Resource{customer.name}</t:Cell><t:Cell>$Resource{payType.name}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{roomAreaCompensate.compensateState}</t:Cell><t:Cell>$Resource{sellState}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","roomAreaCompensate.id","sellProject.id","sellProject.name","subarea.name","building.id","building.name","displayName","roomNo","isCalByRoomArea","buildingArea","roomArea","actualBuildingArea","actualRoomArea","purchase.dealPrice","purchase.dealAmount","roomAreaCompensate.compensateRate","roomAreaCompensate.compensateAmount","roomAreaCompensate.latestAmount","purchase.customerNames","payType.name","salesman.name","roomAreaCompensate.compensateState","sellState"});

		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setToolTipText(resHelper.getString("menuItemEdit.toolTipText"));		
        this.btnAttachment.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);
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
        // btnBatchCompensate
        this.btnBatchCompensate.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchCompensate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchCompensate.setText(resHelper.getString("btnBatchCompensate.text"));		
        this.btnBatchCompensate.setToolTipText(resHelper.getString("btnBatchCompensate.toolTipText"));
        // btnSubmitWorkFlow
        this.btnSubmitWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitWorkFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmitWorkFlow.setText(resHelper.getString("btnSubmitWorkFlow.text"));		
        this.btnSubmitWorkFlow.setToolTipText(resHelper.getString("btnSubmitWorkFlow.toolTipText"));
        // btnExecute
        this.btnExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExecute.setText(resHelper.getString("btnExecute.text"));		
        this.btnExecute.setToolTipText(resHelper.getString("btnExecute.toolTipText"));
        // btnSubmitRoomWorkFlow
        this.btnSubmitRoomWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitRoomWorkFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmitRoomWorkFlow.setText(resHelper.getString("btnSubmitRoomWorkFlow.text"));		
        this.btnSubmitRoomWorkFlow.setToolTipText(resHelper.getString("btnSubmitRoomWorkFlow.toolTipText"));		
        this.btnSubmitRoomWorkFlow.setVisible(false);
        // btn_Audit
        this.btn_Audit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btn_Audit.setText(resHelper.getString("btn_Audit.text"));		
        this.btn_Audit.setToolTipText(resHelper.getString("btn_Audit.toolTipText"));		
        this.btn_Audit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btn_UnAudit
        this.btn_UnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btn_UnAudit.setText(resHelper.getString("btn_UnAudit.text"));		
        this.btn_UnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));		
        this.btn_UnAudit.setToolTipText(resHelper.getString("btn_UnAudit.toolTipText"));
        // kDMenu1		
        this.kDMenu1.setText(resHelper.getString("kDMenu1.text"));
        // menuItemSingleSheme
        this.menuItemSingleSheme.setAction((IItemAction)ActionProxyFactory.getProxy(actionSingleScheme, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSingleSheme.setText(resHelper.getString("menuItemSingleSheme.text"));		
        this.menuItemSingleSheme.setToolTipText(resHelper.getString("menuItemSingleSheme.toolTipText"));
        // kDMenu2		
        this.kDMenu2.setText(resHelper.getString("kDMenu2.text"));
        // menuItemBatchComp
        this.menuItemBatchComp.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchCompensate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchComp.setText(resHelper.getString("menuItemBatchComp.text"));		
        this.menuItemBatchComp.setMnemonic(67);		
        this.menuItemBatchComp.setToolTipText(resHelper.getString("menuItemBatchComp.toolTipText"));
        // menuItemBatchEdit
        this.menuItemBatchEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchEdit.setText(resHelper.getString("menuItemBatchEdit.text"));		
        this.menuItemBatchEdit.setToolTipText(resHelper.getString("menuItemBatchEdit.toolTipText"));
        // menuItemSubmitWorkFlow
        this.menuItemSubmitWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitWorkFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSubmitWorkFlow.setText(resHelper.getString("menuItemSubmitWorkFlow.text"));		
        this.menuItemSubmitWorkFlow.setToolTipText(resHelper.getString("menuItemSubmitWorkFlow.toolTipText"));
        // menuItemExecute
        this.menuItemExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExecute.setText(resHelper.getString("menuItemExecute.text"));		
        this.menuItemExecute.setToolTipText(resHelper.getString("menuItemExecute.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(6, 9, 996, 614));
        this.add(kDSplitPane1, new KDLayout.Constraints(6, 9, 996, 614, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(kDTreeView1, "left");
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
        menuEdit.add(kDMenu1);
        menuEdit.add(kDMenu2);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemRemove);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //kDMenu1
        kDMenu1.add(menuItemEdit);
        kDMenu1.add(menuItemSingleSheme);
        //kDMenu2
        kDMenu2.add(menuItemBatchComp);
        kDMenu2.add(menuItemBatchEdit);
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
        menuBiz.add(menuItemSubmitWorkFlow);
        menuBiz.add(menuItemExecute);
        menuBiz.add(menuItemDelVoucher);
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
        this.toolBar.add(btnBatchCompensate);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnSubmitWorkFlow);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnExecute);
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
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnSubmitRoomWorkFlow);
        this.toolBar.add(btn_Audit);
        this.toolBar.add(btn_UnAudit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomAreaCompensateListUIHandler";
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
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("subarea.name"));
        sic.add(new SelectorItemInfo("building.id"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("isCalByRoomArea"));
        sic.add(new SelectorItemInfo("purchase.dealPrice"));
        sic.add(new SelectorItemInfo("purchase.dealAmount"));
        sic.add(new SelectorItemInfo("roomAreaCompensate.compensateRate"));
        sic.add(new SelectorItemInfo("roomAreaCompensate.compensateAmount"));
        sic.add(new SelectorItemInfo("roomAreaCompensate.latestAmount"));
        sic.add(new SelectorItemInfo("payType.name"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("roomAreaCompensate.id"));
        sic.add(new SelectorItemInfo("purchase.isSellBySet"));
        sic.add(new SelectorItemInfo("roomAreaCompensate.compensateState"));
        sic.add(new SelectorItemInfo("sellState"));
        sic.add(new SelectorItemInfo("purchase.customerNames"));
        sic.add(new SelectorItemInfo("roomNo"));
        sic.add(new SelectorItemInfo("displayName"));
        return sic;
    }        
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }
    	

    /**
     * output actionBatchCompensate_actionPerformed method
     */
    public void actionBatchCompensate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSingleScheme_actionPerformed method
     */
    public void actionSingleScheme_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchEdit_actionPerformed method
     */
    public void actionBatchEdit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmitWorkFlow_actionPerformed method
     */
    public void actionSubmitWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExecute_actionPerformed method
     */
    public void actionExecute_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmitRoomWorkFlow_actionPerformed method
     */
    public void actionSubmitRoomWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionEdit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
    }
	public RequestContext prepareActionBatchCompensate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchCompensate() {
    	return false;
    }
	public RequestContext prepareActionSingleScheme(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSingleScheme() {
    	return false;
    }
	public RequestContext prepareActionBatchEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchEdit() {
    	return false;
    }
	public RequestContext prepareActionSubmitWorkFlow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitWorkFlow() {
    	return false;
    }
	public RequestContext prepareActionExecute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExecute() {
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
	public RequestContext prepareActionSubmitRoomWorkFlow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitRoomWorkFlow() {
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

    /**
     * output ActionBatchCompensate class
     */     
    protected class ActionBatchCompensate extends ItemAction {     
    
        public ActionBatchCompensate()
        {
            this(null);
        }

        public ActionBatchCompensate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
            _tempStr = resHelper.getString("ActionBatchCompensate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchCompensate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchCompensate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionBatchCompensate", "actionBatchCompensate_actionPerformed", e);
        }
    }

    /**
     * output ActionSingleScheme class
     */     
    protected class ActionSingleScheme extends ItemAction {     
    
        public ActionSingleScheme()
        {
            this(null);
        }

        public ActionSingleScheme(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
            _tempStr = resHelper.getString("ActionSingleScheme.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSingleScheme.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSingleScheme.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionSingleScheme", "actionSingleScheme_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchEdit class
     */     
    protected class ActionBatchEdit extends ItemAction {     
    
        public ActionBatchEdit()
        {
            this(null);
        }

        public ActionBatchEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
            _tempStr = resHelper.getString("ActionBatchEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionBatchEdit", "actionBatchEdit_actionPerformed", e);
        }
    }

    /**
     * output ActionSubmitWorkFlow class
     */     
    protected class ActionSubmitWorkFlow extends ItemAction {     
    
        public ActionSubmitWorkFlow()
        {
            this(null);
        }

        public ActionSubmitWorkFlow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showsubflow"));
            _tempStr = resHelper.getString("ActionSubmitWorkFlow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitWorkFlow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitWorkFlow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionSubmitWorkFlow", "actionSubmitWorkFlow_actionPerformed", e);
        }
    }

    /**
     * output ActionExecute class
     */     
    protected class ActionExecute extends ItemAction {     
    
        public ActionExecute()
        {
            this(null);
        }

        public ActionExecute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
            _tempStr = resHelper.getString("ActionExecute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionExecute", "actionExecute_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionSubmitRoomWorkFlow class
     */     
    protected class ActionSubmitRoomWorkFlow extends ItemAction {     
    
        public ActionSubmitRoomWorkFlow()
        {
            this(null);
        }

        public ActionSubmitRoomWorkFlow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSubmitRoomWorkFlow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitRoomWorkFlow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitRoomWorkFlow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionSubmitRoomWorkFlow", "actionSubmitRoomWorkFlow_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractRoomAreaCompensateListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomAreaCompensateListUI");
    }




}