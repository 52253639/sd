/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractSupplierInputListUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierInputListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDContainer contSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree orgTree;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTree supplierTypeTree;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton importDateFromDateBase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton audit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton unAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBathAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMultiSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddToSysSupplier;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuimportDateFromDateBase;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuUnAudit;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionImportFormDataBase actionImportFormDataBase = null;
    protected ActionBathAssign actionBathAssign = null;
    protected ActionViewAssign actionViewAssign = null;
    protected ActionImport actionImport = null;
    protected ActionMultiSubmit actionMultiSubmit = null;
    protected ActionAddToSysSupplier actionAddToSysSupplier = null;
    protected ActionEditLevel actionEditLevel = null;
    /**
     * output class constructor
     */
    public AbstractSupplierInputListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierInputListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.app", "NewSupplierStockQuery");
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportFormDataBase
        this.actionImportFormDataBase = new ActionImportFormDataBase(this);
        getActionManager().registerAction("actionImportFormDataBase", actionImportFormDataBase);
         this.actionImportFormDataBase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBathAssign
        this.actionBathAssign = new ActionBathAssign(this);
        getActionManager().registerAction("actionBathAssign", actionBathAssign);
         this.actionBathAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAssign
        this.actionViewAssign = new ActionViewAssign(this);
        getActionManager().registerAction("actionViewAssign", actionViewAssign);
         this.actionViewAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMultiSubmit
        this.actionMultiSubmit = new ActionMultiSubmit(this);
        getActionManager().registerAction("actionMultiSubmit", actionMultiSubmit);
         this.actionMultiSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddToSysSupplier
        this.actionAddToSysSupplier = new ActionAddToSysSupplier(this);
        getActionManager().registerAction("actionAddToSysSupplier", actionAddToSysSupplier);
         this.actionAddToSysSupplier.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditLevel
        this.actionEditLevel = new ActionEditLevel(this);
        getActionManager().registerAction("actionEditLevel", actionEditLevel);
         this.actionEditLevel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contSupplierType = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.orgTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.supplierTypeTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.importDateFromDateBase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.audit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.unAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBathAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMultiSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddToSysSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenuimportDateFromDateBase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDContainer1.setName("kDContainer1");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.contOrg.setName("contOrg");
        this.contSupplierType.setName("contSupplierType");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.orgTree.setName("orgTree");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.supplierTypeTree.setName("supplierTypeTree");
        this.importDateFromDateBase.setName("importDateFromDateBase");
        this.audit.setName("audit");
        this.unAudit.setName("unAudit");
        this.btnBathAssign.setName("btnBathAssign");
        this.btnViewAssign.setName("btnViewAssign");
        this.btnImport.setName("btnImport");
        this.btnMultiSubmit.setName("btnMultiSubmit");
        this.btnAddToSysSupplier.setName("btnAddToSysSupplier");
        this.btnEditLevel.setName("btnEditLevel");
        this.kDMenuimportDateFromDateBase.setName("kDMenuimportDateFromDateBase");
        this.kDSeparator3.setName("kDSeparator3");
        this.kDMenuAudit.setName("kDMenuAudit");
        this.kDMenuUnAudit.setName("kDMenuUnAudit");
        // CoreUI		
        this.setAutoscrolls(true);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"name\" t:width=\"280\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"inviteType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"isPass\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"quaLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"level.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"grade.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"purchaseOrgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"supplierFileType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"linkPerson.personName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"linkPerson.phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"linkPerson.workPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"linkPerson.personFax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"linkPerson.isDefault\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"sysSupplier.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"sysSupplier.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{inviteType.name}</t:Cell><t:Cell>$Resource{isPass}</t:Cell><t:Cell>$Resource{quaLevel.name}</t:Cell><t:Cell>$Resource{level.name}</t:Cell><t:Cell>$Resource{grade.name}</t:Cell><t:Cell>$Resource{purchaseOrgUnit.name}</t:Cell><t:Cell>$Resource{supplierFileType.name}</t:Cell><t:Cell>$Resource{linkPerson.personName}</t:Cell><t:Cell>$Resource{linkPerson.phone}</t:Cell><t:Cell>$Resource{linkPerson.workPhone}</t:Cell><t:Cell>$Resource{linkPerson.personFax}</t:Cell><t:Cell>$Resource{linkPerson.isDefault}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{sysSupplier.number}</t:Cell><t:Cell>$Resource{sysSupplier.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"id","CU.id","number","name","inviteType.name","isPass","quaLevel.name","level.name","grade.name","purchaseOrgUnit.name","supplierFileType.name","linkPerson.personName","linkPerson.phone","linkPerson.workPhone","linkPerson.personFax","linkPerson.isDefault","state","creator.name","createTime","sysSupplier.number","sysSupplier.name"});

		
        this.btnLocate.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemLocate.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnQueryScheme.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(300);
        // contOrg		
        this.contOrg.setTitle(resHelper.getString("contOrg.title"));
        // contSupplierType		
        this.contSupplierType.setTitle(resHelper.getString("contSupplierType.title"));
        // kDScrollPane1
        // orgTree
        this.orgTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    orgTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane2
        // supplierTypeTree
        this.supplierTypeTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    supplierTypeTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // importDateFromDateBase
        this.importDateFromDateBase.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFormDataBase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.importDateFromDateBase.setText(resHelper.getString("importDateFromDateBase.text"));		
        this.importDateFromDateBase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // audit
        this.audit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.audit.setText(resHelper.getString("audit.text"));		
        this.audit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // unAudit
        this.unAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.unAudit.setText(resHelper.getString("unAudit.text"));		
        this.unAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnBathAssign
        this.btnBathAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionBathAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBathAssign.setText(resHelper.getString("btnBathAssign.text"));
        // btnViewAssign
        this.btnViewAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAssign.setText(resHelper.getString("btnViewAssign.text"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));
        // btnMultiSubmit
        this.btnMultiSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionMultiSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMultiSubmit.setText(resHelper.getString("btnMultiSubmit.text"));
        // btnAddToSysSupplier
        this.btnAddToSysSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddToSysSupplier, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddToSysSupplier.setText(resHelper.getString("btnAddToSysSupplier.text"));
        // btnEditLevel
        this.btnEditLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditLevel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditLevel.setText(resHelper.getString("btnEditLevel.text"));
        // kDMenuimportDateFromDateBase
        this.kDMenuimportDateFromDateBase.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFormDataBase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuimportDateFromDateBase.setText(resHelper.getString("kDMenuimportDateFromDateBase.text"));
        // kDSeparator3
        // kDMenuAudit
        this.kDMenuAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuAudit.setText(resHelper.getString("kDMenuAudit.text"));
        // kDMenuUnAudit
        this.kDMenuUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuUnAudit.setText(resHelper.getString("kDMenuUnAudit.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 768));
        kDSplitPane1.setBounds(new Rectangle(5, 5, 1001, 759));
        this.add(kDSplitPane1, new KDLayout.Constraints(5, 5, 1001, 759, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDContainer1, "right");
        kDSplitPane1.add(kDSplitPane2, "left");
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDSplitPane2
        kDSplitPane2.add(contOrg, "top");
        kDSplitPane2.add(contSupplierType, "bottom");
        //contOrg
contOrg.getContentPane().setLayout(new BorderLayout(0, 0));        contOrg.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(orgTree, null);
        //contSupplierType
contSupplierType.getContentPane().setLayout(new BorderLayout(0, 0));        contSupplierType.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(supplierTypeTree, null);

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
        menuBiz.add(kDMenuimportDateFromDateBase);
        menuBiz.add(kDSeparator3);
        menuBiz.add(kDMenuAudit);
        menuBiz.add(kDMenuUnAudit);
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
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(importDateFromDateBase);
        this.toolBar.add(audit);
        this.toolBar.add(unAudit);
        this.toolBar.add(btnBathAssign);
        this.toolBar.add(btnViewAssign);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnMultiSubmit);
        this.toolBar.add(btnAddToSysSupplier);
        this.toolBar.add(btnEditLevel);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierInputListUIHandler";
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
     * output tblMain_editValueChanged method
     */
    protected void tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output orgTree_valueChanged method
     */
    protected void orgTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output supplierTypeTree_valueChanged method
     */
    protected void supplierTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isPass"));
        sic.add(new SelectorItemInfo("supplierFileType.name"));
        sic.add(new SelectorItemInfo("linkPerson.personName"));
        sic.add(new SelectorItemInfo("linkPerson.phone"));
        sic.add(new SelectorItemInfo("linkPerson.workPhone"));
        sic.add(new SelectorItemInfo("linkPerson.personFax"));
        sic.add(new SelectorItemInfo("linkPerson.isDefault"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("purchaseOrgUnit.name"));
        sic.add(new SelectorItemInfo("grade.name"));
        sic.add(new SelectorItemInfo("level.name"));
        sic.add(new SelectorItemInfo("quaLevel.name"));
        sic.add(new SelectorItemInfo("sysSupplier.number"));
        sic.add(new SelectorItemInfo("sysSupplier.name"));
        sic.add(new SelectorItemInfo("inviteType.name"));
        return sic;
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
     * output actionImportFormDataBase_actionPerformed method
     */
    public void actionImportFormDataBase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBathAssign_actionPerformed method
     */
    public void actionBathAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAssign_actionPerformed method
     */
    public void actionViewAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMultiSubmit_actionPerformed method
     */
    public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddToSysSupplier_actionPerformed method
     */
    public void actionAddToSysSupplier_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditLevel_actionPerformed method
     */
    public void actionEditLevel_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionImportFormDataBase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportFormDataBase() {
    	return false;
    }
	public RequestContext prepareActionBathAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBathAssign() {
    	return false;
    }
	public RequestContext prepareActionViewAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAssign() {
    	return false;
    }
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionMultiSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMultiSubmit() {
    	return false;
    }
	public RequestContext prepareActionAddToSysSupplier(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddToSysSupplier() {
    	return false;
    }
	public RequestContext prepareActionEditLevel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditLevel() {
    	return false;
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
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionImportFormDataBase class
     */     
    protected class ActionImportFormDataBase extends ItemAction {     
    
        public ActionImportFormDataBase()
        {
            this(null);
        }

        public ActionImportFormDataBase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportFormDataBase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFormDataBase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFormDataBase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionImportFormDataBase", "actionImportFormDataBase_actionPerformed", e);
        }
    }

    /**
     * output ActionBathAssign class
     */     
    protected class ActionBathAssign extends ItemAction {     
    
        public ActionBathAssign()
        {
            this(null);
        }

        public ActionBathAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBathAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBathAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBathAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionBathAssign", "actionBathAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAssign class
     */     
    protected class ActionViewAssign extends ItemAction {     
    
        public ActionViewAssign()
        {
            this(null);
        }

        public ActionViewAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionViewAssign", "actionViewAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionMultiSubmit class
     */     
    protected class ActionMultiSubmit extends ItemAction {     
    
        public ActionMultiSubmit()
        {
            this(null);
        }

        public ActionMultiSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMultiSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionMultiSubmit", "actionMultiSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionAddToSysSupplier class
     */     
    protected class ActionAddToSysSupplier extends ItemAction {     
    
        public ActionAddToSysSupplier()
        {
            this(null);
        }

        public ActionAddToSysSupplier(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddToSysSupplier.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddToSysSupplier.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddToSysSupplier.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionAddToSysSupplier", "actionAddToSysSupplier_actionPerformed", e);
        }
    }

    /**
     * output ActionEditLevel class
     */     
    protected class ActionEditLevel extends ItemAction {     
    
        public ActionEditLevel()
        {
            this(null);
        }

        public ActionEditLevel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditLevel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditLevel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditLevel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierInputListUI.this, "ActionEditLevel", "actionEditLevel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierInputListUI");
    }




}