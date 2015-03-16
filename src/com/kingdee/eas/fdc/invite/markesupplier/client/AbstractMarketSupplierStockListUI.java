/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

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
public abstract class AbstractMarketSupplierStockListUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierStockListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDSupplierCont;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree orgTree;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTree supplierTypeTree;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSupplierState;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton review;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton constract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton appraise;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton infoChange;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton enterHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton constractHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton appraiseHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton changeHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton materialInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton importDateFromDateBase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton audit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton unAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBathAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMultiSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddToSysSupplier;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuReview;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuConstract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuAppraise;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuInfoChange;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuEnterHis;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuConstractHis;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuAppraiseHis;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuChangeHis;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuMaterialInfo;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenuState;
    protected ActionReview actionReview = null;
    protected ActionConstract actionConstract = null;
    protected ActionAppraise actionAppraise = null;
    protected ActionEnterHis actionEnterHis = null;
    protected ActionConstractHis actionConstractHis = null;
    protected ActionAppraiseHis actionAppraiseHis = null;
    protected ActionMaterialInfo actionMaterialInfo = null;
    protected ActionInfoChange actionInfoChange = null;
    protected ActionChangeHis actionChangeHis = null;
    protected actionImportFormDataBase actionImportFormDataBase = null;
    protected actionAudit actionAudit = null;
    protected actionUnAudit actionUnAudit = null;
    protected actionBathAssign actionBathAssign = null;
    protected actionViewAssign actionViewAssign = null;
    protected actionImport actionImport = null;
    protected actionMultiSubmit actionMultiSubmit = null;
    protected actionAddToSysSupplier actionAddToSysSupplier = null;
    protected actionEditLevel actionEditLevel = null;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierStockListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierStockListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.app", "MarketSupplierStockQuery");
        //actionReview
        this.actionReview = new ActionReview(this);
        getActionManager().registerAction("actionReview", actionReview);
         this.actionReview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConstract
        this.actionConstract = new ActionConstract(this);
        getActionManager().registerAction("actionConstract", actionConstract);
         this.actionConstract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAppraise
        this.actionAppraise = new ActionAppraise(this);
        getActionManager().registerAction("actionAppraise", actionAppraise);
         this.actionAppraise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEnterHis
        this.actionEnterHis = new ActionEnterHis(this);
        getActionManager().registerAction("actionEnterHis", actionEnterHis);
         this.actionEnterHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConstractHis
        this.actionConstractHis = new ActionConstractHis(this);
        getActionManager().registerAction("actionConstractHis", actionConstractHis);
         this.actionConstractHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAppraiseHis
        this.actionAppraiseHis = new ActionAppraiseHis(this);
        getActionManager().registerAction("actionAppraiseHis", actionAppraiseHis);
         this.actionAppraiseHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialInfo
        this.actionMaterialInfo = new ActionMaterialInfo(this);
        getActionManager().registerAction("actionMaterialInfo", actionMaterialInfo);
         this.actionMaterialInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInfoChange
        this.actionInfoChange = new ActionInfoChange(this);
        getActionManager().registerAction("actionInfoChange", actionInfoChange);
         this.actionInfoChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeHis
        this.actionChangeHis = new ActionChangeHis(this);
        getActionManager().registerAction("actionChangeHis", actionChangeHis);
         this.actionChangeHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportFormDataBase
        this.actionImportFormDataBase = new actionImportFormDataBase(this);
        getActionManager().registerAction("actionImportFormDataBase", actionImportFormDataBase);
         this.actionImportFormDataBase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new actionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new actionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBathAssign
        this.actionBathAssign = new actionBathAssign(this);
        getActionManager().registerAction("actionBathAssign", actionBathAssign);
         this.actionBathAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAssign
        this.actionViewAssign = new actionViewAssign(this);
        getActionManager().registerAction("actionViewAssign", actionViewAssign);
         this.actionViewAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new actionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMultiSubmit
        this.actionMultiSubmit = new actionMultiSubmit(this);
        getActionManager().registerAction("actionMultiSubmit", actionMultiSubmit);
         this.actionMultiSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddToSysSupplier
        this.actionAddToSysSupplier = new actionAddToSysSupplier(this);
        getActionManager().registerAction("actionAddToSysSupplier", actionAddToSysSupplier);
         this.actionAddToSysSupplier.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditLevel
        this.actionEditLevel = new actionEditLevel(this);
        getActionManager().registerAction("actionEditLevel", actionEditLevel);
         this.actionEditLevel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDSupplierCont = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.orgTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.supplierTypeTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnSupplierState = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.review = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.constract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.appraise = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.infoChange = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.enterHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.constractHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.appraiseHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.changeHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.materialInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.importDateFromDateBase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.audit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.unAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBathAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMultiSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddToSysSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenuReview = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuConstract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuAppraise = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuInfoChange = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuEnterHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuConstractHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuAppraiseHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuChangeHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuMaterialInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuState = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDSupplierCont.setName("kDSupplierCont");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.orgTree.setName("orgTree");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.supplierTypeTree.setName("supplierTypeTree");
        this.btnSupplierState.setName("btnSupplierState");
        this.review.setName("review");
        this.constract.setName("constract");
        this.appraise.setName("appraise");
        this.infoChange.setName("infoChange");
        this.enterHis.setName("enterHis");
        this.constractHis.setName("constractHis");
        this.appraiseHis.setName("appraiseHis");
        this.changeHis.setName("changeHis");
        this.materialInfo.setName("materialInfo");
        this.importDateFromDateBase.setName("importDateFromDateBase");
        this.audit.setName("audit");
        this.unAudit.setName("unAudit");
        this.btnBathAssign.setName("btnBathAssign");
        this.btnViewAssign.setName("btnViewAssign");
        this.btnImport.setName("btnImport");
        this.btnMultiSubmit.setName("btnMultiSubmit");
        this.btnAddToSysSupplier.setName("btnAddToSysSupplier");
        this.btnEditLevel.setName("btnEditLevel");
        this.kDMenuReview.setName("kDMenuReview");
        this.kDMenuConstract.setName("kDMenuConstract");
        this.kDMenuAppraise.setName("kDMenuAppraise");
        this.kDSeparator4.setName("kDSeparator4");
        this.kDMenuInfoChange.setName("kDMenuInfoChange");
        this.kDSeparator5.setName("kDSeparator5");
        this.kDMenuEnterHis.setName("kDMenuEnterHis");
        this.kDMenuConstractHis.setName("kDMenuConstractHis");
        this.kDMenuAppraiseHis.setName("kDMenuAppraiseHis");
        this.kDMenuChangeHis.setName("kDMenuChangeHis");
        this.kDSeparator6.setName("kDSeparator6");
        this.kDMenuMaterialInfo.setName("kDMenuMaterialInfo");
        this.kDSeparator3.setName("kDSeparator3");
        this.kDMenuState.setName("kDMenuState");
        // CoreUI		
        this.menuTool.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"isPass\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"quaLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"grade.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"level.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"purchaseOrgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"supplierFileType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"linkPerson.personName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"linkPerson.phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"linkPerson.workPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"linkPerson.personFax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:configured=\"false\" /><t:Column t:key=\"buildDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:configured=\"false\" /><t:Column t:key=\"SupplierStock.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:configured=\"false\" /><t:Column t:key=\"SupplierStock.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:configured=\"false\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:configured=\"false\" t:styleID=\"sCol18\" /><t:Column t:key=\"Visibility.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol19\" /><t:Column t:key=\"inviteType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol20\" /><t:Column t:key=\"supplierBusinessMode.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol21\" /><t:Column t:key=\"inviteType.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol22\" /><t:Column t:key=\"inviteType.longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol23\" /><t:Column t:key=\"linkPerson.isDefault\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol24\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">供应商编码</t:Cell><t:Cell t:configured=\"false\">供应商名称</t:Cell><t:Cell t:configured=\"false\">是否合格</t:Cell><t:Cell t:configured=\"false\">资质等级</t:Cell><t:Cell t:configured=\"false\">供应商等级</t:Cell><t:Cell t:configured=\"false\">供应商级别</t:Cell><t:Cell t:configured=\"false\">所属组织</t:Cell><t:Cell t:configured=\"false\">档案分类</t:Cell><t:Cell t:configured=\"false\">联系人姓名</t:Cell><t:Cell t:configured=\"false\">手机</t:Cell><t:Cell t:configured=\"false\">办公电话</t:Cell><t:Cell t:configured=\"false\">传真</t:Cell><t:Cell t:configured=\"false\">状态</t:Cell><t:Cell t:configured=\"false\">创建者</t:Cell><t:Cell t:configured=\"false\">创建日期</t:Cell><t:Cell t:configured=\"false\">主数据供应商编码</t:Cell><t:Cell t:configured=\"false\">主数据供应商名称</t:Cell><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">知名度</t:Cell><t:Cell t:configured=\"false\">采购类别</t:Cell><t:Cell t:configured=\"false\">经营模式</t:Cell><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">长编码</t:Cell><t:Cell t:configured=\"false\">是否为授权联系人</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","name","isPass","quaLevel.name","grade.name","level.name","purchaseOrgUnit.name","supplierFileType.name","linkPerson.personName","linkPerson.phone","linkPerson.workPhone","linkPerson.personFax","state","creator.name","buildDate","SupplierStock.number","SupplierStock.name","CU.id","Visibility.name","inviteType.name","supplierBusinessMode.name","inviteType.id","inviteType.longNumber","linkPerson.isDefault"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);		
        this.btnAddNew.setVisible(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnLocate.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemView.setVisible(false);		
        this.menuItemLocate.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnQueryScheme.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // kDSupplierCont		
        this.kDSupplierCont.setTitle(resHelper.getString("kDSupplierCont.title"));		
        this.kDSupplierCont.setEnableActive(false);
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(300);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setToolTipText(resHelper.getString("kDContainer1.toolTipText"));
        // kDContainer2		
        this.kDContainer2.setToolTipText(resHelper.getString("kDContainer2.toolTipText"));		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
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
                    kDSupplierTypeTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnSupplierState		
        this.btnSupplierState.setText(resHelper.getString("btnSupplierState.text"));		
        this.btnSupplierState.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_hiderule"));
        // review
        this.review.setAction((IItemAction)ActionProxyFactory.getProxy(actionReview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.review.setText(resHelper.getString("review.text"));		
        this.review.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_demandcollateresult"));
        // constract
        this.constract.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.constract.setText(resHelper.getString("constract.text"));		
        this.constract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
        // appraise
        this.appraise.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.appraise.setText(resHelper.getString("appraise.text"));		
        this.appraise.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_alreadycollate"));
        // infoChange
        this.infoChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionInfoChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.infoChange.setText(resHelper.getString("infoChange.text"));		
        this.infoChange.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelcollocate"));
        // enterHis
        this.enterHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnterHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.enterHis.setText(resHelper.getString("enterHis.text"));		
        this.enterHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_duizsetting"));
        // constractHis
        this.constractHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstractHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.constractHis.setText(resHelper.getString("constractHis.text"));		
        this.constractHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_estatesetting"));
        // appraiseHis
        this.appraiseHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraiseHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.appraiseHis.setText(resHelper.getString("appraiseHis.text"));		
        this.appraiseHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_makeknown"));
        // changeHis
        this.changeHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.changeHis.setText(resHelper.getString("changeHis.text"));		
        this.changeHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_listaccount"));
        // materialInfo
        this.materialInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionMaterialInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.materialInfo.setText(resHelper.getString("materialInfo.text"));		
        this.materialInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_copyline"));
        // importDateFromDateBase
        this.importDateFromDateBase.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFormDataBase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.importDateFromDateBase.setText(resHelper.getString("importDateFromDateBase.text"));
        // audit
        this.audit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.audit.setText(resHelper.getString("audit.text"));
        // unAudit
        this.unAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.unAudit.setText(resHelper.getString("unAudit.text"));
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
        // kDMenuReview
        this.kDMenuReview.setAction((IItemAction)ActionProxyFactory.getProxy(actionReview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuReview.setText(resHelper.getString("kDMenuReview.text"));
        // kDMenuConstract
        this.kDMenuConstract.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuConstract.setText(resHelper.getString("kDMenuConstract.text"));
        // kDMenuAppraise
        this.kDMenuAppraise.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuAppraise.setText(resHelper.getString("kDMenuAppraise.text"));
        // kDSeparator4
        // kDMenuInfoChange
        this.kDMenuInfoChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionInfoChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuInfoChange.setText(resHelper.getString("kDMenuInfoChange.text"));
        // kDSeparator5
        // kDMenuEnterHis
        this.kDMenuEnterHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnterHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuEnterHis.setText(resHelper.getString("kDMenuEnterHis.text"));
        // kDMenuConstractHis
        this.kDMenuConstractHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstractHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuConstractHis.setText(resHelper.getString("kDMenuConstractHis.text"));
        // kDMenuAppraiseHis
        this.kDMenuAppraiseHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraiseHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuAppraiseHis.setText(resHelper.getString("kDMenuAppraiseHis.text"));
        // kDMenuChangeHis
        this.kDMenuChangeHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuChangeHis.setText(resHelper.getString("kDMenuChangeHis.text"));
        // kDSeparator6
        // kDMenuMaterialInfo
        this.kDMenuMaterialInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionMaterialInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuMaterialInfo.setText(resHelper.getString("kDMenuMaterialInfo.text"));
        // kDSeparator3
        // kDMenuState		
        this.kDMenuState.setText(resHelper.getString("kDMenuState.text"));
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
        kDSplitPane1.setBounds(new Rectangle(6, 7, 1000, 615));
        this.add(kDSplitPane1, new KDLayout.Constraints(6, 7, 1000, 615, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDSupplierCont, "right");
        kDSplitPane1.add(kDSplitPane2, "left");
        //kDSupplierCont
kDSupplierCont.getContentPane().setLayout(new BorderLayout(0, 0));        kDSupplierCont.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDSplitPane2
        kDSplitPane2.add(kDContainer1, "top");
        kDSplitPane2.add(kDContainer2, "bottom");
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        kDScrollPane1.setBounds(new Rectangle(1, 1, 199, 279));
        kDContainer1.getContentPane().add(kDScrollPane1, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(orgTree, null);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(null);        kDScrollPane2.setBounds(new Rectangle(0, -1, 200, 286));
        kDContainer2.getContentPane().add(kDScrollPane2, null);
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
        menuFile.add(kDMenuReview);
        menuFile.add(kDMenuConstract);
        menuFile.add(kDMenuAppraise);
        menuFile.add(kDSeparator4);
        menuFile.add(kDMenuInfoChange);
        menuFile.add(kDSeparator5);
        menuFile.add(kDMenuEnterHis);
        menuFile.add(kDMenuConstractHis);
        menuFile.add(kDMenuAppraiseHis);
        menuFile.add(kDMenuChangeHis);
        menuFile.add(kDSeparator6);
        menuFile.add(kDMenuMaterialInfo);
        menuFile.add(kDSeparator3);
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
        menuView.add(kDMenuState);
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
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnSupplierState);
        this.toolBar.add(review);
        this.toolBar.add(constract);
        this.toolBar.add(appraise);
        this.toolBar.add(infoChange);
        this.toolBar.add(enterHis);
        this.toolBar.add(constractHis);
        this.toolBar.add(appraiseHis);
        this.toolBar.add(changeHis);
        this.toolBar.add(materialInfo);
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
	    return "com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierStockListUIHandler";
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
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Purchase");
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
	 * ????????У??
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
     * output kDSupplierTypeTree_valueChanged method
     */
    protected void kDSupplierTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

			public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("creator.*"));
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("CU.*"));
			sic.add(new SelectorItemInfo("supplierType.*"));
			sic.add(new SelectorItemInfo("linkPerson.*"));
			sic.add(new SelectorItemInfo("yearSale.*"));
			sic.add(new SelectorItemInfo("aptitudeFile.*"));
			sic.add(new SelectorItemInfo("achievement.*"));
			sic.add(new SelectorItemInfo("province.*"));
			sic.add(new SelectorItemInfo("city.*"));
			sic.add(new SelectorItemInfo("srcSupplier.*"));
			sic.add(new SelectorItemInfo("supplierServiceType.*"));
			sic.add(new SelectorItemInfo("supplierServiceType.serviceType.*"));
			sic.add(new SelectorItemInfo("adminCU.*"));
			sic.add(new SelectorItemInfo("supplierSplAreaEntry.*"));
			sic.add(new SelectorItemInfo("supplierSplAreaEntry.fdcSplArea.*"));
			sic.add(new SelectorItemInfo("supplierFileType.*"));
			sic.add(new SelectorItemInfo("supplierBusinessMode.*"));
			sic.add(new SelectorItemInfo("supplierPersonEntry.*"));
			sic.add(new SelectorItemInfo("supplierAttachListEntry.*"));
			sic.add(new SelectorItemInfo("auditor.*"));
			sic.add(new SelectorItemInfo("grade.*"));
			sic.add(new SelectorItemInfo("inviteType.*"));
			sic.add(new SelectorItemInfo("purchaseOrgUnit.*"));
			sic.add(new SelectorItemInfo("sysSupplier.*"));
			sic.add(new SelectorItemInfo("level.*"));
			sic.add(new SelectorItemInfo("quaLevel.*"));
			sic.add(new SelectorItemInfo("Visibility.*"));
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isPass"));
        sic.add(new SelectorItemInfo("quaLevel.name"));
        sic.add(new SelectorItemInfo("level.name"));
        sic.add(new SelectorItemInfo("purchaseOrgUnit.name"));
        sic.add(new SelectorItemInfo("supplierFileType.name"));
        sic.add(new SelectorItemInfo("linkPerson.personName"));
        sic.add(new SelectorItemInfo("linkPerson.phone"));
        sic.add(new SelectorItemInfo("linkPerson.workPhone"));
        sic.add(new SelectorItemInfo("linkPerson.personFax"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("buildDate"));
        sic.add(new SelectorItemInfo("SupplierStock.number"));
        sic.add(new SelectorItemInfo("SupplierStock.name"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("grade.name"));
        sic.add(new SelectorItemInfo("Visibility.name"));
        sic.add(new SelectorItemInfo("inviteType.name"));
        sic.add(new SelectorItemInfo("supplierBusinessMode.name"));
        sic.add(new SelectorItemInfo("inviteType.id"));
        sic.add(new SelectorItemInfo("inviteType.longNumber"));
        sic.add(new SelectorItemInfo("linkPerson.isDefault"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        return sorterFieldList; 
    } 
    protected java.util.List getQueryPKFields() 
    { 
        java.util.List pkList = new ArrayList(); 
        pkList.add("id"); 
        return pkList;
    }
    	

    /**
     * output actionReview_actionPerformed method
     */
    public void actionReview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConstract_actionPerformed method
     */
    public void actionConstract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAppraise_actionPerformed method
     */
    public void actionAppraise_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEnterHis_actionPerformed method
     */
    public void actionEnterHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConstractHis_actionPerformed method
     */
    public void actionConstractHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAppraiseHis_actionPerformed method
     */
    public void actionAppraiseHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialInfo_actionPerformed method
     */
    public void actionMaterialInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInfoChange_actionPerformed method
     */
    public void actionInfoChange_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeHis_actionPerformed method
     */
    public void actionChangeHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportFormDataBase_actionPerformed method
     */
    public void actionImportFormDataBase_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionReview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReview() {
    	return false;
    }
	public RequestContext prepareActionConstract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConstract() {
    	return false;
    }
	public RequestContext prepareActionAppraise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAppraise() {
    	return false;
    }
	public RequestContext prepareActionEnterHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEnterHis() {
    	return false;
    }
	public RequestContext prepareActionConstractHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConstractHis() {
    	return false;
    }
	public RequestContext prepareActionAppraiseHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAppraiseHis() {
    	return false;
    }
	public RequestContext prepareActionMaterialInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialInfo() {
    	return false;
    }
	public RequestContext prepareActionInfoChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInfoChange() {
    	return false;
    }
	public RequestContext prepareActionChangeHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeHis() {
    	return false;
    }
	public RequestContext prepareactionImportFormDataBase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImportFormDataBase() {
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
	public RequestContext prepareactionBathAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionBathAssign() {
    	return false;
    }
	public RequestContext prepareactionViewAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionViewAssign() {
    	return false;
    }
	public RequestContext prepareactionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImport() {
    	return false;
    }
	public RequestContext prepareactionMultiSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionMultiSubmit() {
    	return false;
    }
	public RequestContext prepareactionAddToSysSupplier(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddToSysSupplier() {
    	return false;
    }
	public RequestContext prepareactionEditLevel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionEditLevel() {
    	return false;
    }

    /**
     * output ActionReview class
     */     
    protected class ActionReview extends ItemAction {     
    
        public ActionReview()
        {
            this(null);
        }

        public ActionReview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionReview", "actionReview_actionPerformed", e);
        }
    }

    /**
     * output ActionConstract class
     */     
    protected class ActionConstract extends ItemAction {     
    
        public ActionConstract()
        {
            this(null);
        }

        public ActionConstract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConstract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionConstract", "actionConstract_actionPerformed", e);
        }
    }

    /**
     * output ActionAppraise class
     */     
    protected class ActionAppraise extends ItemAction {     
    
        public ActionAppraise()
        {
            this(null);
        }

        public ActionAppraise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAppraise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionAppraise", "actionAppraise_actionPerformed", e);
        }
    }

    /**
     * output ActionEnterHis class
     */     
    protected class ActionEnterHis extends ItemAction {     
    
        public ActionEnterHis()
        {
            this(null);
        }

        public ActionEnterHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEnterHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionEnterHis", "actionEnterHis_actionPerformed", e);
        }
    }

    /**
     * output ActionConstractHis class
     */     
    protected class ActionConstractHis extends ItemAction {     
    
        public ActionConstractHis()
        {
            this(null);
        }

        public ActionConstractHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConstractHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstractHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstractHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionConstractHis", "actionConstractHis_actionPerformed", e);
        }
    }

    /**
     * output ActionAppraiseHis class
     */     
    protected class ActionAppraiseHis extends ItemAction {     
    
        public ActionAppraiseHis()
        {
            this(null);
        }

        public ActionAppraiseHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAppraiseHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraiseHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraiseHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionAppraiseHis", "actionAppraiseHis_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialInfo class
     */     
    protected class ActionMaterialInfo extends ItemAction {     
    
        public ActionMaterialInfo()
        {
            this(null);
        }

        public ActionMaterialInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMaterialInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionMaterialInfo", "actionMaterialInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionInfoChange class
     */     
    protected class ActionInfoChange extends ItemAction {     
    
        public ActionInfoChange()
        {
            this(null);
        }

        public ActionInfoChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInfoChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInfoChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInfoChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionInfoChange", "actionInfoChange_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeHis class
     */     
    protected class ActionChangeHis extends ItemAction {     
    
        public ActionChangeHis()
        {
            this(null);
        }

        public ActionChangeHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionChangeHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "ActionChangeHis", "actionChangeHis_actionPerformed", e);
        }
    }

    /**
     * output actionImportFormDataBase class
     */     
    protected class actionImportFormDataBase extends ItemAction {     
    
        public actionImportFormDataBase()
        {
            this(null);
        }

        public actionImportFormDataBase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionImportFormDataBase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportFormDataBase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportFormDataBase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionImportFormDataBase", "actionImportFormDataBase_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionAudit", "actionAudit_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output actionBathAssign class
     */     
    protected class actionBathAssign extends ItemAction {     
    
        public actionBathAssign()
        {
            this(null);
        }

        public actionBathAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionBathAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionBathAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionBathAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionBathAssign", "actionBathAssign_actionPerformed", e);
        }
    }

    /**
     * output actionViewAssign class
     */     
    protected class actionViewAssign extends ItemAction {     
    
        public actionViewAssign()
        {
            this(null);
        }

        public actionViewAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionViewAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionViewAssign", "actionViewAssign_actionPerformed", e);
        }
    }

    /**
     * output actionImport class
     */     
    protected class actionImport extends ItemAction {     
    
        public actionImport()
        {
            this(null);
        }

        public actionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output actionMultiSubmit class
     */     
    protected class actionMultiSubmit extends ItemAction {     
    
        public actionMultiSubmit()
        {
            this(null);
        }

        public actionMultiSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionMultiSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionMultiSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionMultiSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionMultiSubmit", "actionMultiSubmit_actionPerformed", e);
        }
    }

    /**
     * output actionAddToSysSupplier class
     */     
    protected class actionAddToSysSupplier extends ItemAction {     
    
        public actionAddToSysSupplier()
        {
            this(null);
        }

        public actionAddToSysSupplier(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddToSysSupplier.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddToSysSupplier.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddToSysSupplier.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionAddToSysSupplier", "actionAddToSysSupplier_actionPerformed", e);
        }
    }

    /**
     * output actionEditLevel class
     */     
    protected class actionEditLevel extends ItemAction {     
    
        public actionEditLevel()
        {
            this(null);
        }

        public actionEditLevel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionEditLevel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionEditLevel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionEditLevel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketSupplierStockListUI.this, "actionEditLevel", "actionEditLevel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.client", "MarketSupplierStockListUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.invite.markesupplier.client.MarketSupplierStockEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"id","number","name","isPass","quaLevel.name","grade.name","level.name","purchaseOrgUnit.name","supplierFileType.name","state","creator.name","buildDate","SupplierStock.number","SupplierStock.name","CU.id","Visibility.name","inviteType.name","supplierBusinessMode.name","inviteType.id","inviteType.longNumber"};
    }



	protected String getTDFileName() {
    	return "/bim/fdc/invite/markesupplier/MarketSupplierStock";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierStockQuery");
	}

}