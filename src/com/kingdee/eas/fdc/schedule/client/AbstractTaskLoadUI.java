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
public abstract class AbstractTaskLoadUI extends com.kingdee.eas.framework.client.CoreBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTaskLoadUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnScheduleTask;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlLeftTree;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRight;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnFirst;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tbl1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEntryAttachment;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnTree;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnSecond;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tbl2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEntryAttachment;
    protected ActionSave actionSave = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionDeleteRow actionDeleteRow = null;
    protected ActionEntryAttachment actionEntryAttachment = null;
    /**
     * output class constructor
     */
    public AbstractTaskLoadUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTaskLoadUI.class.getName());
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
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.ctnScheduleTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.pnlLeftTree = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRight = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.ctnFirst = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tbl1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelDown = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEntryAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.ctnTree = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.ctnSecond = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tbl2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemEntryAttachment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.ctnScheduleTask.setName("ctnScheduleTask");
        this.pnlLeftTree.setName("pnlLeftTree");
        this.pnlRight.setName("pnlRight");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.ctnFirst.setName("ctnFirst");
        this.tbl1.setName("tbl1");
        this.panelDown.setName("panelDown");
        this.btnSave.setName("btnSave");
        this.btnAddRow.setName("btnAddRow");
        this.btnDeleteRow.setName("btnDeleteRow");
        this.btnEntryAttachment.setName("btnEntryAttachment");
        this.ctnTree.setName("ctnTree");
        this.treeMain.setName("treeMain");
        this.ctnSecond.setName("ctnSecond");
        this.tbl2.setName("tbl2");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        this.menuItemSave.setName("menuItemSave");
        this.menuItemEntryAttachment.setName("menuItemEntryAttachment");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"wbsNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"wbsName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"addPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"addLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"start\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"end\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"effectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"natureTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"wbs.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"wbsLevel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{wbsNumber}</t:Cell><t:Cell>$Resource{wbsName}</t:Cell><t:Cell>$Resource{addPercent}</t:Cell><t:Cell>$Resource{addLoad}</t:Cell><t:Cell>$Resource{start}</t:Cell><t:Cell>$Resource{end}</t:Cell><t:Cell>$Resource{effectTimes}</t:Cell><t:Cell>$Resource{natureTimes}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{wbs.id}</t:Cell><t:Cell>$Resource{wbsLevel}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","",""});


        // pnlMain		
        this.pnlMain.setOneTouchExpandable(true);		
        this.pnlMain.setDividerLocation(240);		
        this.pnlMain.setDividerSize(8);
        // ctnScheduleTask		
        this.ctnScheduleTask.setTitle(resHelper.getString("ctnScheduleTask.title"));
        // pnlLeftTree
        // pnlRight
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(300);		
        this.kDSplitPane1.setDividerSize(8);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // ctnFirst
        // tbl1
		String tbl1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"completeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"confirmDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"confirmer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isConfirm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"percent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"load\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"addPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"addLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"desc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"objectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{completeDate}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{confirmDate}</t:Cell><t:Cell>$Resource{confirmer}</t:Cell><t:Cell>$Resource{isConfirm}</t:Cell><t:Cell>$Resource{percent}</t:Cell><t:Cell>$Resource{load}</t:Cell><t:Cell>$Resource{addPercent}</t:Cell><t:Cell>$Resource{addLoad}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{objectId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tbl1.setFormatXml(resHelper.translateString("tbl1",tbl1StrXML));
        this.tbl1.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tbl1_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tbl1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tbl1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tbl1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tbl1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // panelDown
        this.panelDown.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    panelDown_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.panelDown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    panelDown_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
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
        // ctnTree		
        this.ctnTree.setTitle(resHelper.getString("ctnTree.title"));
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
        // ctnSecond
        // tbl2
		String tbl2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"percent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"load\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"completeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"confirmDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"confirmer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isConfirm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"addPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"addLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"desc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"objectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{percent}</t:Cell><t:Cell>$Resource{load}</t:Cell><t:Cell>$Resource{completeDate}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{confirmDate}</t:Cell><t:Cell>$Resource{confirmer}</t:Cell><t:Cell>$Resource{isConfirm}</t:Cell><t:Cell>$Resource{addPercent}</t:Cell><t:Cell>$Resource{addLoad}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{objectId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tbl2.setFormatXml(resHelper.translateString("tbl2",tbl2StrXML));
        this.tbl2.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tbl2_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tbl2.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tbl2_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tbl2.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tbl2_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // menuItemAddRow
        this.menuItemAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddRow.setText(resHelper.getString("menuItemAddRow.text"));		
        this.menuItemAddRow.setToolTipText(resHelper.getString("menuItemAddRow.toolTipText"));
        // menuItemDeleteRow
        this.menuItemDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeleteRow.setText(resHelper.getString("menuItemDeleteRow.text"));		
        this.menuItemDeleteRow.setToolTipText(resHelper.getString("menuItemDeleteRow.toolTipText"));
        // menuItemSave
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));
        // menuItemEntryAttachment
        this.menuItemEntryAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionEntryAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEntryAttachment.setText(resHelper.getString("menuItemEntryAttachment.text"));		
        this.menuItemEntryAttachment.setToolTipText(resHelper.getString("menuItemEntryAttachment.toolTipText"));
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
        pnlMain.add(pnlLeftTree, "left");
        pnlMain.add(pnlRight, "right");
        //pnlLeftTree
        pnlLeftTree.setLayout(new KDLayout());
        pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(0, 0, 239, 608));        ctnTree.setBounds(new Rectangle(0, 0, 240, 609));
        pnlLeftTree.add(ctnTree, new KDLayout.Constraints(0, 0, 240, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //ctnTree
        ctnTree.getContentPane().setLayout(new KDLayout());
        ctnTree.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 240, 609));        treeMain.setBounds(new Rectangle(0, 0, 240, 608));
        ctnTree.getContentPane().add(treeMain, new KDLayout.Constraints(0, 0, 240, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlRight
        pnlRight.setLayout(new KDLayout());
        pnlRight.putClientProperty("OriginalBounds", new Rectangle(0, 0, 742, 608));        kDSplitPane1.setBounds(new Rectangle(0, 1, 733, 608));
        pnlRight.add(kDSplitPane1, new KDLayout.Constraints(0, 1, 733, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(ctnScheduleTask, "top");
        kDSplitPane1.add(panelDown, "bottom");
        //ctnScheduleTask
        ctnScheduleTask.getContentPane().setLayout(new KDLayout());
        ctnScheduleTask.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 732, 299));        tblMain.setBounds(new Rectangle(0, 0, 733, 281));
        ctnScheduleTask.getContentPane().add(tblMain, new KDLayout.Constraints(0, 0, 733, 281, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //panelDown
        panelDown.add(ctnFirst, resHelper.getString("ctnFirst.constraints"));
        panelDown.add(ctnSecond, resHelper.getString("ctnSecond.constraints"));
        //ctnFirst
        ctnFirst.getContentPane().setLayout(new KDLayout());
        ctnFirst.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 731, 264));        tbl1.setBounds(new Rectangle(0, 0, 729, 250));
        ctnFirst.getContentPane().add(tbl1, new KDLayout.Constraints(0, 0, 729, 250, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //ctnSecond
        ctnSecond.getContentPane().setLayout(new KDLayout());
        ctnSecond.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 731, 264));        tbl2.setBounds(new Rectangle(0, 0, 729, 250));
        ctnSecond.getContentPane().add(tbl2, new KDLayout.Constraints(0, 0, 729, 250, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.TaskLoadUIHandler";
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
        super.setUIController(new TaskLoadUIController(super.getNavigator()));
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
     * output tbl1_tableSelectChanged method
     */
    protected void tbl1_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tbl1_tableClicked method
     */
    protected void tbl1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tbl1_editStopped method
     */
    protected void tbl1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output panelDown_stateChanged method
     */
    protected void panelDown_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output panelDown_mouseClicked method
     */
    protected void panelDown_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tbl2_tableClicked method
     */
    protected void tbl2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tbl2_tableSelectChanged method
     */
    protected void tbl2_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tbl2_editStopped method
     */
    protected void tbl2_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
					((TaskLoadUIController)getUIController()).actionSave();
    }
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
					((TaskLoadUIController)getUIController()).actionAddRow();
    }
    	

    /**
     * output actionDeleteRow_actionPerformed method
     */
    public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception
    {
					((TaskLoadUIController)getUIController()).actionDeleteRow();
    }
    	

    /**
     * output actionEntryAttachment_actionPerformed method
     */
    public void actionEntryAttachment_actionPerformed(ActionEvent e) throws Exception
    {
					((TaskLoadUIController)getUIController()).actionEntryAttachment();
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
            innerActionPerformed("eas", AbstractTaskLoadUI.this, "ActionSave", "actionSave_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractTaskLoadUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractTaskLoadUI.this, "ActionDeleteRow", "actionDeleteRow_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractTaskLoadUI.this, "ActionEntryAttachment", "actionEntryAttachment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "TaskLoadUI");
    }




}