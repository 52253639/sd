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
public abstract class AbstractDefaultAmountMangerListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDefaultAmountMangerListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane defaultSplitPane;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane defaultTabbedPane;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel defaultMangerPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel defaultCreatPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel defaultCalPanel;
    protected com.kingdee.bos.ctrl.swing.KDContainer argContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable argMain;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable creatMain;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable CalMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCal;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditCal;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveCal;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreatDefault;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnArgPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnArgPrintView;
    protected ActionAddCal actionAddCal = null;
    protected ActionEditCal actionEditCal = null;
    protected ActionRemoveCal actionRemoveCal = null;
    protected ActionCreatDefault actionCreatDefault = null;
    protected ActionArgPrint actionArgPrint = null;
    protected ActionArgPrintView actionArgPrintView = null;
    /**
     * output class constructor
     */
    public AbstractDefaultAmountMangerListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDefaultAmountMangerListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "DefaultAmountMangerQuery");
        //actionAddCal
        this.actionAddCal = new ActionAddCal(this);
        getActionManager().registerAction("actionAddCal", actionAddCal);
         this.actionAddCal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditCal
        this.actionEditCal = new ActionEditCal(this);
        getActionManager().registerAction("actionEditCal", actionEditCal);
         this.actionEditCal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveCal
        this.actionRemoveCal = new ActionRemoveCal(this);
        getActionManager().registerAction("actionRemoveCal", actionRemoveCal);
         this.actionRemoveCal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreatDefault
        this.actionCreatDefault = new ActionCreatDefault(this);
        getActionManager().registerAction("actionCreatDefault", actionCreatDefault);
         this.actionCreatDefault.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionArgPrint
        this.actionArgPrint = new ActionArgPrint(this);
        getActionManager().registerAction("actionArgPrint", actionArgPrint);
         this.actionArgPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionArgPrintView
        this.actionArgPrintView = new ActionArgPrintView(this);
        getActionManager().registerAction("actionArgPrintView", actionArgPrintView);
         this.actionArgPrintView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.defaultSplitPane = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.defaultTabbedPane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.defaultMangerPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.defaultCreatPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.defaultCalPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.argContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.argMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.creatMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.CalMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddCal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditCal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveCal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreatDefault = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnArgPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnArgPrintView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.defaultSplitPane.setName("defaultSplitPane");
        this.treeView.setName("treeView");
        this.defaultTabbedPane.setName("defaultTabbedPane");
        this.treeMain.setName("treeMain");
        this.defaultMangerPanel.setName("defaultMangerPanel");
        this.defaultCreatPanel.setName("defaultCreatPanel");
        this.defaultCalPanel.setName("defaultCalPanel");
        this.argContainer.setName("argContainer");
        this.argMain.setName("argMain");
        this.creatMain.setName("creatMain");
        this.CalMain.setName("CalMain");
        this.btnAddCal.setName("btnAddCal");
        this.btnEditCal.setName("btnEditCal");
        this.btnRemoveCal.setName("btnRemoveCal");
        this.btnCreatDefault.setName("btnCreatDefault");
        this.btnArgPrint.setName("btnArgPrint");
        this.btnArgPrintView.setName("btnArgPrintView");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"telephone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"argAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleManNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"carryAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"refDeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"subDeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"defCalDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"project.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"payment.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room.name}</t:Cell><t:Cell>$Resource{customerNames}</t:Cell><t:Cell>$Resource{telephone}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{argAmount}</t:Cell><t:Cell>$Resource{saleManNames}</t:Cell><t:Cell>$Resource{carryAmount}</t:Cell><t:Cell>$Resource{refDeAmount}</t:Cell><t:Cell>$Resource{subDeAmount}</t:Cell><t:Cell>$Resource{defCalDate}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{room.id}</t:Cell><t:Cell>$Resource{project.id}</t:Cell><t:Cell>$Resource{payment.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"room.name","customerNames","telephone","bizDate","remark","contractAmount","argAmount","saleManNames","carryAmount","refDeAmount","subDeAmount","defCalDate","id","room.id","project.id","payment.id"});

		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);
        // defaultSplitPane		
        this.defaultSplitPane.setDividerLocation(200);
        // treeView
        // defaultTabbedPane
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
        // defaultMangerPanel
        this.defaultMangerPanel.addAncestorListener(new com.kingdee.bos.ctrl.swing.event.AncestorAdapter() {
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                try {
                    defaultMangerPanel_ancestorAdded(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // defaultCreatPanel
        this.defaultCreatPanel.addAncestorListener(new com.kingdee.bos.ctrl.swing.event.AncestorAdapter() {
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                try {
                    defaultCreatPanel_ancestorAdded(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // defaultCalPanel
        this.defaultCalPanel.addAncestorListener(new com.kingdee.bos.ctrl.swing.event.AncestorAdapter() {
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                try {
                    defaultCalPanel_ancestorAdded(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // argContainer		
        this.argContainer.setTitle(resHelper.getString("argContainer.title"));
        // argMain
		String argMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"argDays\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"argAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"referAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{argDays}</t:Cell><t:Cell>$Resource{argAmount}</t:Cell><t:Cell>$Resource{referAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.argMain.setFormatXml(resHelper.translateString("argMain",argMainStrXML));

        

        // creatMain
		String creatMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"argDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"argformula\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"operator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"explain\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{argDate}</t:Cell><t:Cell>$Resource{argformula}</t:Cell><t:Cell>$Resource{operator}</t:Cell><t:Cell>$Resource{explain}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.creatMain.setFormatXml(resHelper.translateString("creatMain",creatMainStrXML));
        this.creatMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    creatMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.creatMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    creatMain_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // CalMain
		String CalMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"calWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"keepDig\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ingWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"projectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{calWay}</t:Cell><t:Cell>$Resource{keepDig}</t:Cell><t:Cell>$Resource{ingWay}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{projectId}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.CalMain.setFormatXml(resHelper.translateString("CalMain",CalMainStrXML));
        this.CalMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    CalMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.CalMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    CalMain_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnAddCal
        this.btnAddCal.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCal.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.btnAddCal.setToolTipText(resHelper.getString("btnAddCal.toolTipText"));		
        this.btnAddCal.setText(resHelper.getString("btnAddCal.text"));
        // btnEditCal
        this.btnEditCal.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditCal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditCal.setText(resHelper.getString("btnEditCal.text"));		
        this.btnEditCal.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnRemoveCal
        this.btnRemoveCal.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveCal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveCal.setText(resHelper.getString("btnRemoveCal.text"));		
        this.btnRemoveCal.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_delete"));
        // btnCreatDefault
        this.btnCreatDefault.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreatDefault, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreatDefault.setText(resHelper.getString("btnCreatDefault.text"));		
        this.btnCreatDefault.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_post"));
        // btnArgPrint
        this.btnArgPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionArgPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnArgPrint.setText(resHelper.getString("btnArgPrint.text"));		
        this.btnArgPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnArgPrintView
        this.btnArgPrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionArgPrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnArgPrintView.setText(resHelper.getString("btnArgPrintView.text"));		
        this.btnArgPrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_printpreview"));
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
        defaultSplitPane.setBounds(new Rectangle(10, 10, 997, 610));
        this.add(defaultSplitPane, new KDLayout.Constraints(10, 10, 997, 610, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //defaultSplitPane
        defaultSplitPane.add(treeView, "left");
        defaultSplitPane.add(defaultTabbedPane, "right");
        //treeView
        treeView.setTree(treeMain);
        //defaultTabbedPane
        defaultTabbedPane.add(defaultMangerPanel, resHelper.getString("defaultMangerPanel.constraints"));
        defaultTabbedPane.add(defaultCreatPanel, resHelper.getString("defaultCreatPanel.constraints"));
        defaultTabbedPane.add(defaultCalPanel, resHelper.getString("defaultCalPanel.constraints"));
        //defaultMangerPanel
        defaultMangerPanel.setLayout(new KDLayout());
        defaultMangerPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 576));        tblMain.setBounds(new Rectangle(1, 2, 780, 573));
        defaultMangerPanel.add(tblMain, new KDLayout.Constraints(1, 2, 780, 573, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        argContainer.setBounds(new Rectangle(433, 420, 345, 79));
        defaultMangerPanel.add(argContainer, new KDLayout.Constraints(433, 420, 345, 79, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //argContainer
        argContainer.getContentPane().setLayout(new KDLayout());
        argContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(433, 420, 345, 79));        argMain.setBounds(new Rectangle(2, 6, 773, 293));
        argContainer.getContentPane().add(argMain, new KDLayout.Constraints(2, 6, 773, 293, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //defaultCreatPanel
        defaultCreatPanel.setLayout(new KDLayout());
        defaultCreatPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 576));        creatMain.setBounds(new Rectangle(2, 3, 775, 572));
        defaultCreatPanel.add(creatMain, new KDLayout.Constraints(2, 3, 775, 572, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //defaultCalPanel
        defaultCalPanel.setLayout(new KDLayout());
        defaultCalPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 576));        CalMain.setBounds(new Rectangle(5, 26, 777, 572));
        defaultCalPanel.add(CalMain, new KDLayout.Constraints(5, 26, 777, 572, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddCal.setBounds(new Rectangle(453, 2, 58, 19));
        defaultCalPanel.add(btnAddCal, new KDLayout.Constraints(453, 2, 58, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEditCal.setBounds(new Rectangle(516, 2, 61, 19));
        defaultCalPanel.add(btnEditCal, new KDLayout.Constraints(516, 2, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveCal.setBounds(new Rectangle(582, 2, 64, 19));
        defaultCalPanel.add(btnRemoveCal, new KDLayout.Constraints(582, 2, 64, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.toolBar.add(btnCreatDefault);
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
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnArgPrint);
        this.toolBar.add(btnArgPrintView);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerListUIHandler";
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
     * output defaultMangerPanel_ancestorAdded method
     */
    protected void defaultMangerPanel_ancestorAdded(javax.swing.event.AncestorEvent e) throws Exception
    {
    }

    /**
     * output defaultCreatPanel_ancestorAdded method
     */
    protected void defaultCreatPanel_ancestorAdded(javax.swing.event.AncestorEvent e) throws Exception
    {
    }

    /**
     * output defaultCalPanel_ancestorAdded method
     */
    protected void defaultCalPanel_ancestorAdded(javax.swing.event.AncestorEvent e) throws Exception
    {
    }

    /**
     * output creatMain_editValueChanged method
     */
    protected void creatMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output creatMain_tableClicked method
     */
    protected void creatMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output CalMain_editValueChanged method
     */
    protected void CalMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output CalMain_tableClicked method
     */
    protected void CalMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("room.name"));
        sic.add(new SelectorItemInfo("customerNames"));
        sic.add(new SelectorItemInfo("telephone"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("contractAmount"));
        sic.add(new SelectorItemInfo("argAmount"));
        sic.add(new SelectorItemInfo("busType"));
        sic.add(new SelectorItemInfo("saleManNames"));
        sic.add(new SelectorItemInfo("carryAmount"));
        sic.add(new SelectorItemInfo("refDeAmount"));
        sic.add(new SelectorItemInfo("subDeAmount"));
        sic.add(new SelectorItemInfo("defCalDate"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("project.id"));
        sic.add(new SelectorItemInfo("payment.id"));
        return sic;
    }        
    	

    /**
     * output actionAddCal_actionPerformed method
     */
    public void actionAddCal_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditCal_actionPerformed method
     */
    public void actionEditCal_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveCal_actionPerformed method
     */
    public void actionRemoveCal_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCreatDefault_actionPerformed method
     */
    public void actionCreatDefault_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionArgPrint_actionPerformed method
     */
    public void actionArgPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionArgPrintView_actionPerformed method
     */
    public void actionArgPrintView_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddCal(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCal() {
    	return false;
    }
	public RequestContext prepareActionEditCal(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditCal() {
    	return false;
    }
	public RequestContext prepareActionRemoveCal(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveCal() {
    	return false;
    }
	public RequestContext prepareActionCreatDefault(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreatDefault() {
    	return false;
    }
	public RequestContext prepareActionArgPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionArgPrint() {
    	return false;
    }
	public RequestContext prepareActionArgPrintView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionArgPrintView() {
    	return false;
    }

    /**
     * output ActionAddCal class
     */     
    protected class ActionAddCal extends ItemAction {     
    
        public ActionAddCal()
        {
            this(null);
        }

        public ActionAddCal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerListUI.this, "ActionAddCal", "actionAddCal_actionPerformed", e);
        }
    }

    /**
     * output ActionEditCal class
     */     
    protected class ActionEditCal extends ItemAction {     
    
        public ActionEditCal()
        {
            this(null);
        }

        public ActionEditCal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditCal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditCal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditCal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerListUI.this, "ActionEditCal", "actionEditCal_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveCal class
     */     
    protected class ActionRemoveCal extends ItemAction {     
    
        public ActionRemoveCal()
        {
            this(null);
        }

        public ActionRemoveCal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveCal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveCal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveCal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerListUI.this, "ActionRemoveCal", "actionRemoveCal_actionPerformed", e);
        }
    }

    /**
     * output ActionCreatDefault class
     */     
    protected class ActionCreatDefault extends ItemAction {     
    
        public ActionCreatDefault()
        {
            this(null);
        }

        public ActionCreatDefault(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCreatDefault.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreatDefault.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreatDefault.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerListUI.this, "ActionCreatDefault", "actionCreatDefault_actionPerformed", e);
        }
    }

    /**
     * output ActionArgPrint class
     */     
    protected class ActionArgPrint extends ItemAction {     
    
        public ActionArgPrint()
        {
            this(null);
        }

        public ActionArgPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionArgPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionArgPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionArgPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerListUI.this, "ActionArgPrint", "actionArgPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionArgPrintView class
     */     
    protected class ActionArgPrintView extends ItemAction {     
    
        public ActionArgPrintView()
        {
            this(null);
        }

        public ActionArgPrintView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionArgPrintView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionArgPrintView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionArgPrintView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountMangerListUI.this, "ActionArgPrintView", "actionArgPrintView_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "DefaultAmountMangerListUI");
    }




}