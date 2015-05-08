/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractSchAdjustReqByGrantListUI extends com.kingdee.eas.framework.client.CoreBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSchAdjustReqByGrantListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRight;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane pnlLeftTree;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnScheduleTask;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnAdjustReq;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSchAdjustReq;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEntryAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVersionCompare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEntryAttachment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected ActionSave actionSave = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionDeleteRow actionDeleteRow = null;
    protected ActionEntryAttachment actionEntryAttachment = null;
    protected ActionVersionCompare actionVersionCompare = null;
    protected actionAudit ActionAudit = null;
    protected actionUnAudit ActionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractSchAdjustReqByGrantListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSchAdjustReqByGrantListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteRow
        this.actionDeleteRow = new ActionDeleteRow(this);
        getActionManager().registerAction("actionDeleteRow", actionDeleteRow);
         this.actionDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEntryAttachment
        this.actionEntryAttachment = new ActionEntryAttachment(this);
        getActionManager().registerAction("actionEntryAttachment", actionEntryAttachment);
         this.actionEntryAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVersionCompare
        this.actionVersionCompare = new ActionVersionCompare(this);
        getActionManager().registerAction("actionVersionCompare", actionVersionCompare);
         this.actionVersionCompare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //ActionAudit
        this.ActionAudit = new actionAudit(this);
        getActionManager().registerAction("ActionAudit", ActionAudit);
         this.ActionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //ActionUnAudit
        this.ActionUnAudit = new actionUnAudit(this);
        getActionManager().registerAction("ActionUnAudit", ActionUnAudit);
         this.ActionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pnlRight = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlLeftTree = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.ctnScheduleTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.ctnAdjustReq = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSchAdjustReq = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEntryAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVersionCompare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemEntryAttachment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.pnlRight.setName("pnlRight");
        this.pnlLeftTree.setName("pnlLeftTree");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.ctnScheduleTask.setName("ctnScheduleTask");
        this.ctnAdjustReq.setName("ctnAdjustReq");
        this.tblSchAdjustReq.setName("tblSchAdjustReq");
        this.treeMain.setName("treeMain");
        this.btnSave.setName("btnSave");
        this.btnAddRow.setName("btnAddRow");
        this.btnDeleteRow.setName("btnDeleteRow");
        this.btnEntryAttachment.setName("btnEntryAttachment");
        this.btnVersionCompare.setName("btnVersionCompare");
        this.btnAudit.setName("btnAudit");
        this.menuItemSave.setName("menuItemSave");
        this.menuItemEntryAttachment.setName("menuItemEntryAttachment");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        this.menuItemAudit.setName("menuItemAudit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>#,###</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>#,###</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"curProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isLastestVerion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"version\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"versionReason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"beginDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"effectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"natureTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"workFlowID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectId}</t:Cell><t:Cell>$Resource{curProject}</t:Cell><t:Cell>$Resource{isLastestVerion}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{version}</t:Cell><t:Cell>$Resource{versionReason}</t:Cell><t:Cell>$Resource{beginDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{effectTimes}</t:Cell><t:Cell>$Resource{natureTimes}</t:Cell><t:Cell>$Resource{workFlowID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","",""});


        // pnlMain		
        this.pnlMain.setOneTouchExpandable(true);		
        this.pnlMain.setDividerLocation(240);		
        this.pnlMain.setDividerSize(8);
        // pnlRight
        // pnlLeftTree
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(300);		
        this.kDSplitPane1.setDividerSize(8);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // ctnScheduleTask		
        this.ctnScheduleTask.setTitle(resHelper.getString("ctnScheduleTask.title"));		
        this.ctnScheduleTask.setAutoscrolls(true);
        // ctnAdjustReq		
        this.ctnAdjustReq.setTitle(resHelper.getString("ctnAdjustReq.title"));		
        this.ctnAdjustReq.setAutoscrolls(true);
        // tblSchAdjustReq
		String tblSchAdjustReqStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"adjustType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"adjustReason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"affect\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"reqPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"reqDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{adjustType}</t:Cell><t:Cell>$Resource{adjustReason}</t:Cell><t:Cell>$Resource{affect}</t:Cell><t:Cell>$Resource{reqPerson}</t:Cell><t:Cell>$Resource{reqDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSchAdjustReq.setFormatXml(resHelper.translateString("tblSchAdjustReq",tblSchAdjustReqStrXML));
        this.tblSchAdjustReq.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblSchAdjustReq_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblSchAdjustReq.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblSchAdjustReq_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // treeMain		
        this.treeMain.setAutoscrolls(true);
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
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));
        // btnDeleteRow
        this.btnDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteRow.setText(resHelper.getString("btnDeleteRow.text"));
        // btnEntryAttachment
        this.btnEntryAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionEntryAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEntryAttachment.setText(resHelper.getString("btnEntryAttachment.text"));
        // btnVersionCompare
        this.btnVersionCompare.setAction((IItemAction)ActionProxyFactory.getProxy(actionVersionCompare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVersionCompare.setText(resHelper.getString("btnVersionCompare.text"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(ActionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // menuItemSave
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));
        // menuItemEntryAttachment
        this.menuItemEntryAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionEntryAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEntryAttachment.setText(resHelper.getString("menuItemEntryAttachment.text"));		
        this.menuItemEntryAttachment.setToolTipText(resHelper.getString("menuItemEntryAttachment.toolTipText"));
        // menuItemAddRow
        this.menuItemAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddRow.setText(resHelper.getString("menuItemAddRow.text"));		
        this.menuItemAddRow.setToolTipText(resHelper.getString("menuItemAddRow.toolTipText"));
        // menuItemDeleteRow
        this.menuItemDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeleteRow.setText(resHelper.getString("menuItemDeleteRow.text"));		
        this.menuItemDeleteRow.setToolTipText(resHelper.getString("menuItemDeleteRow.toolTipText"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(ActionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(pnlRight, "right");
        pnlMain.add(pnlLeftTree, "10,10,30,30");
        //pnlRight
        pnlRight.setLayout(new KDLayout());
        pnlRight.putClientProperty("OriginalBounds", new Rectangle(0, 0, 742, 608));        kDSplitPane1.setBounds(new Rectangle(0, 1, 733, 608));
        pnlRight.add(kDSplitPane1, new KDLayout.Constraints(0, 1, 733, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(ctnScheduleTask, "top");
        kDSplitPane1.add(ctnAdjustReq, "bottom");
        //ctnScheduleTask
ctnScheduleTask.getContentPane().setLayout(new BorderLayout(0, 0));        ctnScheduleTask.getContentPane().add(tblMain, BorderLayout.CENTER);
        //ctnAdjustReq
ctnAdjustReq.getContentPane().setLayout(new BorderLayout(0, 0));        ctnAdjustReq.getContentPane().add(tblSchAdjustReq, BorderLayout.CENTER);
        //pnlLeftTree
        pnlLeftTree.getViewport().add(treeMain, null);

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
        menuFile.add(menuItemSave);
        menuFile.add(menuItemEntryAttachment);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemAddRow);
        menuEdit.add(menuItemDeleteRow);
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
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnAddRow);
        this.toolBar.add(btnDeleteRow);
        this.toolBar.add(btnEntryAttachment);
        this.toolBar.add(btnVersionCompare);
        this.toolBar.add(btnAudit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.SchAdjustReqByGrantListUIHandler";
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
        super.setUIController(new SchAdjustReqByGrantListUIController(super.getNavigator()));
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
     * output tblSchAdjustReq_tableClicked method
     */
    protected void tblSchAdjustReq_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblSchAdjustReq_tableSelectChanged method
     */
    protected void tblSchAdjustReq_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
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
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
					((SchAdjustReqByGrantListUIController)getUIController()).actionSave();
    }
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
					((SchAdjustReqByGrantListUIController)getUIController()).actionAddRow();
    }
    	

    /**
     * output actionDeleteRow_actionPerformed method
     */
    public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception
    {
					((SchAdjustReqByGrantListUIController)getUIController()).actionDeleteRow();
    }
    	

    /**
     * output actionEntryAttachment_actionPerformed method
     */
    public void actionEntryAttachment_actionPerformed(ActionEvent e) throws Exception
    {
					((SchAdjustReqByGrantListUIController)getUIController()).actionEntryAttachment();
    }
    	

    /**
     * output actionVersionCompare_actionPerformed method
     */
    public void actionVersionCompare_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRow() {
    	return false;
    }
	public RequestContext prepareActionDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteRow() {
    	return false;
    }
	public RequestContext prepareActionEntryAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEntryAttachment() {
    	return false;
    }
	public RequestContext prepareActionVersionCompare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVersionCompare() {
    	return false;
    }
	public RequestContext prepareactionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAudit() {
    	return false;
    }
	public RequestContext prepareactionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionUnAudit() {
    	return false;
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
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
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
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRow class
     */     
    protected class ActionAddRow extends ItemAction {     
    
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteRow class
     */     
    protected class ActionDeleteRow extends ItemAction {     
    
        public ActionDeleteRow()
        {
            this(null);
        }

        public ActionDeleteRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
            _tempStr = resHelper.getString("ActionDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "ActionDeleteRow", "actionDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output ActionEntryAttachment class
     */     
    protected class ActionEntryAttachment extends ItemAction {     
    
        public ActionEntryAttachment()
        {
            this(null);
        }

        public ActionEntryAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift T"));
            _tempStr = resHelper.getString("ActionEntryAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEntryAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEntryAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "ActionEntryAttachment", "actionEntryAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionVersionCompare class
     */     
    protected class ActionVersionCompare extends ItemAction {     
    
        public ActionVersionCompare()
        {
            this(null);
        }

        public ActionVersionCompare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionVersionCompare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVersionCompare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVersionCompare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "ActionVersionCompare", "actionVersionCompare_actionPerformed", e);
        }
    }

    /**
     * output actionAudit class
     */     
    protected class actionAudit extends ItemAction {     
    
        public actionAudit()
        {
            this(null);
        }

        public actionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("CTRL U"));
            _tempStr = resHelper.getString("actionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "actionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output actionUnAudit class
     */     
    protected class actionUnAudit extends ItemAction {     
    
        public actionUnAudit()
        {
            this(null);
        }

        public actionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("CTRL SHIFT U"));
            _tempStr = resHelper.getString("actionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSchAdjustReqByGrantListUI.this, "actionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "SchAdjustReqByGrantListUI");
    }




}