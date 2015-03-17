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
public abstract class AbstractCustomerListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModifyCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkShowAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnToSysCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPrint;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerAdapter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModifyCustomerPhone;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClueQueryShow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemModifyCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemModifyCustomerPhone;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSwitchTo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemToSysCustomer;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdapter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClueQueryShow;
    protected ActionToSysCustomer actionToSysCustomer = null;
    protected ActionImportantTrack actionImportantTrack = null;
    protected ActionCancelImportantTrack actionCancelImportantTrack = null;
    protected ActionSwitchTo actionSwitchTo = null;
    protected ActionCustomerAdapter actionCustomerAdapter = null;
    protected ActionCustomerShare actionCustomerShare = null;
    protected ActionCustomerCancelShare actionCustomerCancelShare = null;
    protected ActionAddTrackRecord actionAddTrackRecord = null;
    protected ActionModifyCustomer actionModifyCustomer = null;
    protected ActionModifyPhone actionModifyPhone = null;
    protected ActionClueQueryShow actionClueQueryShow = null;
    protected ActionQuestionPrint actionQuestionPrint = null;
    /**
     * output class constructor
     */
    public AbstractCustomerListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "CustomerQuery");
        //actionToSysCustomer
        this.actionToSysCustomer = new ActionToSysCustomer(this);
        getActionManager().registerAction("actionToSysCustomer", actionToSysCustomer);
         this.actionToSysCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportantTrack
        this.actionImportantTrack = new ActionImportantTrack(this);
        getActionManager().registerAction("actionImportantTrack", actionImportantTrack);
         this.actionImportantTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelImportantTrack
        this.actionCancelImportantTrack = new ActionCancelImportantTrack(this);
        getActionManager().registerAction("actionCancelImportantTrack", actionCancelImportantTrack);
         this.actionCancelImportantTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSwitchTo
        this.actionSwitchTo = new ActionSwitchTo(this);
        getActionManager().registerAction("actionSwitchTo", actionSwitchTo);
         this.actionSwitchTo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerAdapter
        this.actionCustomerAdapter = new ActionCustomerAdapter(this);
        getActionManager().registerAction("actionCustomerAdapter", actionCustomerAdapter);
         this.actionCustomerAdapter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerShare
        this.actionCustomerShare = new ActionCustomerShare(this);
        getActionManager().registerAction("actionCustomerShare", actionCustomerShare);
         this.actionCustomerShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerCancelShare
        this.actionCustomerCancelShare = new ActionCustomerCancelShare(this);
        getActionManager().registerAction("actionCustomerCancelShare", actionCustomerCancelShare);
         this.actionCustomerCancelShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTrackRecord
        this.actionAddTrackRecord = new ActionAddTrackRecord(this);
        getActionManager().registerAction("actionAddTrackRecord", actionAddTrackRecord);
         this.actionAddTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModifyCustomer
        this.actionModifyCustomer = new ActionModifyCustomer(this);
        getActionManager().registerAction("actionModifyCustomer", actionModifyCustomer);
         this.actionModifyCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModifyPhone
        this.actionModifyPhone = new ActionModifyPhone(this);
        getActionManager().registerAction("actionModifyPhone", actionModifyPhone);
         this.actionModifyPhone.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClueQueryShow
        this.actionClueQueryShow = new ActionClueQueryShow(this);
        getActionManager().registerAction("actionClueQueryShow", actionClueQueryShow);
         this.actionClueQueryShow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuestionPrint
        this.actionQuestionPrint = new ActionQuestionPrint(this);
        getActionManager().registerAction("actionQuestionPrint", actionQuestionPrint);
         this.actionQuestionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnModifyCustomerName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkShowAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnToSysCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuestionPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnImportantTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelImportantTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerAdapter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerCancelShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnModifyCustomerPhone = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClueQueryShow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemQuestionPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemModifyCustomerName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemModifyCustomerPhone = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemSwitchTo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemToSysCustomer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemImportantTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelImportantTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdapter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClueQueryShow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnModifyCustomerName.setName("btnModifyCustomerName");
        this.chkShowAll.setName("chkShowAll");
        this.btnToSysCustomer.setName("btnToSysCustomer");
        this.btnQuestionPrint.setName("btnQuestionPrint");
        this.separator1.setName("separator1");
        this.btnImportantTrack.setName("btnImportantTrack");
        this.btnCancelImportantTrack.setName("btnCancelImportantTrack");
        this.btnCustomerAdapter.setName("btnCustomerAdapter");
        this.btnCustomerShare.setName("btnCustomerShare");
        this.btnCustomerCancelShare.setName("btnCustomerCancelShare");
        this.btnAddTrackRecord.setName("btnAddTrackRecord");
        this.btnModifyCustomerPhone.setName("btnModifyCustomerPhone");
        this.btnClueQueryShow.setName("btnClueQueryShow");
        this.menuItemQuestionPrint.setName("menuItemQuestionPrint");
        this.menuItemModifyCustomerName.setName("menuItemModifyCustomerName");
        this.menuItemModifyCustomerPhone.setName("menuItemModifyCustomerPhone");
        this.kDSeparator3.setName("kDSeparator3");
        this.menuItemSwitchTo.setName("menuItemSwitchTo");
        this.menuItemToSysCustomer.setName("menuItemToSysCustomer");
        this.kDSeparator4.setName("kDSeparator4");
        this.menuItemImportantTrack.setName("menuItemImportantTrack");
        this.menuItemCancelImportantTrack.setName("menuItemCancelImportantTrack");
        this.menuItemAdapter.setName("menuItemAdapter");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemCancelShare.setName("menuItemCancelShare");
        this.menuItemClueQueryShow.setName("menuItemClueQueryShow");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol34\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol37\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol38\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol39\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol40\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol41\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol42\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol43\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol44\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol45\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol46\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol47\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol48\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol49\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol50\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol51\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol52\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"level.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"receptionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"firstDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"trackPhase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"saleTrackPhase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"tenancyTrackPhase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"tradeTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"tenTradeTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"isSub\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol20\" /><t:Column t:key=\"isImportantTrack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /><t:Column t:key=\"lastTrackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol23\" /><t:Column t:key=\"customerOrigin.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol24\" /><t:Column t:key=\"customerGrade.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol25\" /><t:Column t:key=\"habitationArea.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol26\" /><t:Column t:key=\"workCompany\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol27\" /><t:Column t:key=\"employment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol28\" /><t:Column t:key=\"bookedPlace\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol29\" /><t:Column t:key=\"province.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol30\" /><t:Column t:key=\"famillyEarning.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol31\" /><t:Column t:key=\"customerType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol32\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol33\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol34\" /><t:Column t:key=\"isForSHE\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol35\" /><t:Column t:key=\"isForTen\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol36\" /><t:Column t:key=\"isForPPM\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol37\" /><t:Column t:key=\"enterpriceProperty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol38\" /><t:Column t:key=\"enterpriceSize\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol39\" /><t:Column t:key=\"enterpriceHomepage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol40\" /><t:Column t:key=\"industry.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol41\" /><t:Column t:key=\"phone2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol42\" /><t:Column t:key=\"officeTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol43\" /><t:Column t:key=\"QQ\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol44\" /><t:Column t:key=\"fax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol45\" /><t:Column t:key=\"country.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol46\" /><t:Column t:key=\"cooperateMode.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol47\" /><t:Column t:key=\"businessNature.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol48\" /><t:Column t:key=\"salesman.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol49\" /><t:Column t:key=\"certificateName.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol50\" /><t:Column t:key=\"certificateName.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol51\" /><t:Column t:key=\"certificateName.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol52\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{level.name}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{project.name}</t:Cell><t:Cell>$Resource{receptionType.name}</t:Cell><t:Cell>$Resource{firstDate}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{trackPhase}</t:Cell><t:Cell>$Resource{saleTrackPhase}</t:Cell><t:Cell>$Resource{tenancyTrackPhase}</t:Cell><t:Cell>$Resource{tradeTime}</t:Cell><t:Cell>$Resource{tenTradeTime}</t:Cell><t:Cell>$Resource{isSub}</t:Cell><t:Cell>$Resource{isImportantTrack}</t:Cell><t:Cell>$Resource{lastTrackDate}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{customerOrigin.name}</t:Cell><t:Cell>$Resource{customerGrade.name}</t:Cell><t:Cell>$Resource{habitationArea.name}</t:Cell><t:Cell>$Resource{workCompany}</t:Cell><t:Cell>$Resource{employment}</t:Cell><t:Cell>$Resource{bookedPlace}</t:Cell><t:Cell>$Resource{province.name}</t:Cell><t:Cell>$Resource{famillyEarning.name}</t:Cell><t:Cell>$Resource{customerType}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isForSHE}</t:Cell><t:Cell>$Resource{isForTen}</t:Cell><t:Cell>$Resource{isForPPM}</t:Cell><t:Cell>$Resource{enterpriceProperty}</t:Cell><t:Cell>$Resource{enterpriceSize}</t:Cell><t:Cell>$Resource{enterpriceHomepage}</t:Cell><t:Cell>$Resource{industry.name}</t:Cell><t:Cell>$Resource{phone2}</t:Cell><t:Cell>$Resource{officeTel}</t:Cell><t:Cell>$Resource{QQ}</t:Cell><t:Cell>$Resource{fax}</t:Cell><t:Cell>$Resource{country.name}</t:Cell><t:Cell>$Resource{cooperateMode.name}</t:Cell><t:Cell>$Resource{businessNature.name}</t:Cell><t:Cell>$Resource{salesman.id}</t:Cell><t:Cell>$Resource{certificateName.id}</t:Cell><t:Cell>$Resource{certificateName.number}</t:Cell><t:Cell>$Resource{certificateName.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","name","salesman.name","level.name","phone","project.name","receptionType.name","firstDate","creator.name","createTime","certificateNumber","mailAddress","postalcode","sex","trackPhase","saleTrackPhase","tenancyTrackPhase","tradeTime","tenTradeTime","isSub","isImportantTrack","lastTrackDate","email","customerOrigin.name","customerGrade.name","habitationArea.name","workCompany","employment","bookedPlace","province.name","famillyEarning.name","customerType","isEnabled","description","isForSHE","isForTen","isForPPM","enterpriceProperty","enterpriceSize","enterpriceHomepage","industry.name","phone2","officeTel","QQ","fax","country.name","cooperateMode.name","businessNature.name","salesman.id","certificateName.id","certificateName.number","certificateName.name"});


        this.tblMain.checkParsed();		
        this.menuItemImportData.setVisible(true);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setVisible(true);
        // treeView
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
        // btnModifyCustomerName
        this.btnModifyCustomerName.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModifyCustomerName.setText(resHelper.getString("btnModifyCustomerName.text"));		
        this.btnModifyCustomerName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // chkShowAll		
        this.chkShowAll.setText(resHelper.getString("chkShowAll.text"));
        this.chkShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkShowAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnToSysCustomer
        this.btnToSysCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionToSysCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnToSysCustomer.setText(resHelper.getString("btnToSysCustomer.text"));		
        this.btnToSysCustomer.setToolTipText(resHelper.getString("btnToSysCustomer.toolTipText"));		
        this.btnToSysCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_JobSysTable"));
        // btnQuestionPrint
        this.btnQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPrint.setText(resHelper.getString("btnQuestionPrint.text"));		
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));		
        this.btnQuestionPrint.setToolTipText(resHelper.getString("btnQuestionPrint.toolTipText"));
        // separator1
        // btnImportantTrack
        this.btnImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportantTrack.setText(resHelper.getString("btnImportantTrack.text"));		
        this.btnImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));		
        this.btnImportantTrack.setToolTipText(resHelper.getString("btnImportantTrack.toolTipText"));
        // btnCancelImportantTrack
        this.btnCancelImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelImportantTrack.setText(resHelper.getString("btnCancelImportantTrack.text"));		
        this.btnCancelImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));		
        this.btnCancelImportantTrack.setToolTipText(resHelper.getString("btnCancelImportantTrack.toolTipText"));
        // btnCustomerAdapter
        this.btnCustomerAdapter.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerAdapter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerAdapter.setText(resHelper.getString("btnCustomerAdapter.text"));		
        this.btnCustomerAdapter.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_citecompany"));
        // btnCustomerShare
        this.btnCustomerShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerShare.setText(resHelper.getString("btnCustomerShare.text"));		
        this.btnCustomerShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sealup"));
        // btnCustomerCancelShare
        this.btnCustomerCancelShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerCancelShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerCancelShare.setText(resHelper.getString("btnCustomerCancelShare.text"));		
        this.btnCustomerCancelShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deletesealup"));
        // btnAddTrackRecord
        this.btnAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTrackRecord.setText(resHelper.getString("btnAddTrackRecord.text"));		
        this.btnAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_additem"));
        // btnModifyCustomerPhone
        this.btnModifyCustomerPhone.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyPhone, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModifyCustomerPhone.setText(resHelper.getString("btnModifyCustomerPhone.text"));		
        this.btnModifyCustomerPhone.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnClueQueryShow
        this.btnClueQueryShow.setAction((IItemAction)ActionProxyFactory.getProxy(actionClueQueryShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClueQueryShow.setText(resHelper.getString("btnClueQueryShow.text"));		
        this.btnClueQueryShow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
        // menuItemQuestionPrint
        this.menuItemQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuestionPrint.setText(resHelper.getString("menuItemQuestionPrint.text"));		
        this.menuItemQuestionPrint.setToolTipText(resHelper.getString("menuItemQuestionPrint.toolTipText"));		
        this.menuItemQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
        // menuItemModifyCustomerName
        this.menuItemModifyCustomerName.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemModifyCustomerName.setText(resHelper.getString("menuItemModifyCustomerName.text"));		
        this.menuItemModifyCustomerName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));		
        this.menuItemModifyCustomerName.setToolTipText(resHelper.getString("menuItemModifyCustomerName.toolTipText"));
        // menuItemModifyCustomerPhone
        this.menuItemModifyCustomerPhone.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyPhone, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemModifyCustomerPhone.setText(resHelper.getString("menuItemModifyCustomerPhone.text"));		
        this.menuItemModifyCustomerPhone.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));		
        this.menuItemModifyCustomerPhone.setToolTipText(resHelper.getString("menuItemModifyCustomerPhone.toolTipText"));
        // kDSeparator3
        // menuItemSwitchTo
        this.menuItemSwitchTo.setAction((IItemAction)ActionProxyFactory.getProxy(actionSwitchTo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSwitchTo.setText(resHelper.getString("menuItemSwitchTo.text"));
        // menuItemToSysCustomer
        this.menuItemToSysCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionToSysCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemToSysCustomer.setText(resHelper.getString("menuItemToSysCustomer.text"));		
        this.menuItemToSysCustomer.setToolTipText(resHelper.getString("menuItemToSysCustomer.toolTipText"));		
        this.menuItemToSysCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_JobSysTable"));
        // kDSeparator4
        // menuItemImportantTrack
        this.menuItemImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportantTrack.setText(resHelper.getString("menuItemImportantTrack.text"));		
        this.menuItemImportantTrack.setToolTipText(resHelper.getString("menuItemImportantTrack.toolTipText"));		
        this.menuItemImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));
        // menuItemCancelImportantTrack
        this.menuItemCancelImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelImportantTrack.setText(resHelper.getString("menuItemCancelImportantTrack.text"));		
        this.menuItemCancelImportantTrack.setToolTipText(resHelper.getString("menuItemCancelImportantTrack.toolTipText"));		
        this.menuItemCancelImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // menuItemAdapter
        this.menuItemAdapter.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerAdapter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdapter.setText(resHelper.getString("menuItemAdapter.text"));		
        this.menuItemAdapter.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_citecompany"));
        // menuItemShare
        this.menuItemShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShare.setText(resHelper.getString("menuItemShare.text"));		
        this.menuItemShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sealup"));
        // menuItemCancelShare
        this.menuItemCancelShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerCancelShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelShare.setText(resHelper.getString("menuItemCancelShare.text"));		
        this.menuItemCancelShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deletesealup"));
        // menuItemClueQueryShow
        this.menuItemClueQueryShow.setAction((IItemAction)ActionProxyFactory.getProxy(actionClueQueryShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClueQueryShow.setText(resHelper.getString("menuItemClueQueryShow.text"));		
        this.menuItemClueQueryShow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
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
        tblMain.setBounds(new Rectangle(211, 10, 791, 610));
        this.add(tblMain, new KDLayout.Constraints(211, 10, 791, 610, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        treeView.setBounds(new Rectangle(6, 10, 200, 610));
        this.add(treeView, new KDLayout.Constraints(6, 10, 200, 610, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        //treeView
        treeView.setTree(treeMain);

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
        menuFile.add(menuItemQuestionPrint);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudShare);
        menuFile.add(separatorFile1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemModifyCustomerName);
        menuEdit.add(menuItemRemove);
        menuEdit.add(menuItemModifyCustomerPhone);
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
        menuBiz.add(kDSeparator3);
        menuBiz.add(menuItemSwitchTo);
        menuBiz.add(menuItemToSysCustomer);
        menuBiz.add(kDSeparator4);
        menuBiz.add(menuItemImportantTrack);
        menuBiz.add(menuItemCancelImportantTrack);
        menuBiz.add(menuItemAdapter);
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemCancelShare);
        menuBiz.add(menuItemClueQueryShow);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnLocate);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnModifyCustomerName);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(chkShowAll);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnToSysCustomer);
        this.toolBar.add(btnQuestionPrint);
        this.toolBar.add(separator1);
        this.toolBar.add(btnImportantTrack);
        this.toolBar.add(btnCancelImportantTrack);
        this.toolBar.add(btnCustomerAdapter);
        this.toolBar.add(btnCustomerShare);
        this.toolBar.add(btnCustomerCancelShare);
        this.toolBar.add(btnAddTrackRecord);
        this.toolBar.add(btnModifyCustomerPhone);
        this.toolBar.add(btnClueQueryShow);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerListUIHandler";
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
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output chkShowAll_actionPerformed method
     */
    protected void chkShowAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("level.name"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("receptionType.name"));
        sic.add(new SelectorItemInfo("firstDate"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("certificateNumber"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("postalcode"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("trackPhase"));
        sic.add(new SelectorItemInfo("saleTrackPhase"));
        sic.add(new SelectorItemInfo("tenancyTrackPhase"));
        sic.add(new SelectorItemInfo("tradeTime"));
        sic.add(new SelectorItemInfo("tenTradeTime"));
        sic.add(new SelectorItemInfo("isSub"));
        sic.add(new SelectorItemInfo("isImportantTrack"));
        sic.add(new SelectorItemInfo("lastTrackDate"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("customerOrigin.name"));
        sic.add(new SelectorItemInfo("customerGrade.name"));
        sic.add(new SelectorItemInfo("habitationArea.name"));
        sic.add(new SelectorItemInfo("workCompany"));
        sic.add(new SelectorItemInfo("employment"));
        sic.add(new SelectorItemInfo("bookedPlace"));
        sic.add(new SelectorItemInfo("province.name"));
        sic.add(new SelectorItemInfo("famillyEarning.name"));
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isForSHE"));
        sic.add(new SelectorItemInfo("isForTen"));
        sic.add(new SelectorItemInfo("isForPPM"));
        sic.add(new SelectorItemInfo("enterpriceProperty"));
        sic.add(new SelectorItemInfo("enterpriceSize"));
        sic.add(new SelectorItemInfo("enterpriceHomepage"));
        sic.add(new SelectorItemInfo("industry.name"));
        sic.add(new SelectorItemInfo("phone2"));
        sic.add(new SelectorItemInfo("officeTel"));
        sic.add(new SelectorItemInfo("QQ"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("country.name"));
        sic.add(new SelectorItemInfo("cooperateMode.name"));
        sic.add(new SelectorItemInfo("businessNature.name"));
        sic.add(new SelectorItemInfo("salesman.id"));
        sic.add(new SelectorItemInfo("certificateName.id"));
        sic.add(new SelectorItemInfo("certificateName.number"));
        sic.add(new SelectorItemInfo("certificateName.name"));
        return sic;
    }        
    	

    /**
     * output actionToSysCustomer_actionPerformed method
     */
    public void actionToSysCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportantTrack_actionPerformed method
     */
    public void actionImportantTrack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelImportantTrack_actionPerformed method
     */
    public void actionCancelImportantTrack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSwitchTo_actionPerformed method
     */
    public void actionSwitchTo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerAdapter_actionPerformed method
     */
    public void actionCustomerAdapter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerShare_actionPerformed method
     */
    public void actionCustomerShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerCancelShare_actionPerformed method
     */
    public void actionCustomerCancelShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTrackRecord_actionPerformed method
     */
    public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModifyCustomer_actionPerformed method
     */
    public void actionModifyCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModifyPhone_actionPerformed method
     */
    public void actionModifyPhone_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClueQueryShow_actionPerformed method
     */
    public void actionClueQueryShow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuestionPrint_actionPerformed method
     */
    public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionToSysCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToSysCustomer() {
    	return false;
    }
	public RequestContext prepareActionImportantTrack(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportantTrack() {
    	return false;
    }
	public RequestContext prepareActionCancelImportantTrack(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelImportantTrack() {
    	return false;
    }
	public RequestContext prepareActionSwitchTo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSwitchTo() {
    	return false;
    }
	public RequestContext prepareActionCustomerAdapter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerAdapter() {
    	return false;
    }
	public RequestContext prepareActionCustomerShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerShare() {
    	return false;
    }
	public RequestContext prepareActionCustomerCancelShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerCancelShare() {
    	return false;
    }
	public RequestContext prepareActionAddTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionModifyCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModifyCustomer() {
    	return false;
    }
	public RequestContext prepareActionModifyPhone(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModifyPhone() {
    	return false;
    }
	public RequestContext prepareActionClueQueryShow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClueQueryShow() {
    	return false;
    }
	public RequestContext prepareActionQuestionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuestionPrint() {
    	return false;
    }

    /**
     * output ActionToSysCustomer class
     */     
    protected class ActionToSysCustomer extends ItemAction {     
    
        public ActionToSysCustomer()
        {
            this(null);
        }

        public ActionToSysCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionToSysCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToSysCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToSysCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionToSysCustomer", "actionToSysCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionImportantTrack class
     */     
    protected class ActionImportantTrack extends ItemAction {     
    
        public ActionImportantTrack()
        {
            this(null);
        }

        public ActionImportantTrack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportantTrack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportantTrack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportantTrack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionImportantTrack", "actionImportantTrack_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelImportantTrack class
     */     
    protected class ActionCancelImportantTrack extends ItemAction {     
    
        public ActionCancelImportantTrack()
        {
            this(null);
        }

        public ActionCancelImportantTrack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelImportantTrack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelImportantTrack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelImportantTrack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionCancelImportantTrack", "actionCancelImportantTrack_actionPerformed", e);
        }
    }

    /**
     * output ActionSwitchTo class
     */     
    protected class ActionSwitchTo extends ItemAction {     
    
        public ActionSwitchTo()
        {
            this(null);
        }

        public ActionSwitchTo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSwitchTo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSwitchTo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSwitchTo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionSwitchTo", "actionSwitchTo_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerAdapter class
     */     
    protected class ActionCustomerAdapter extends ItemAction {     
    
        public ActionCustomerAdapter()
        {
            this(null);
        }

        public ActionCustomerAdapter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCustomerAdapter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerAdapter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerAdapter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionCustomerAdapter", "actionCustomerAdapter_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerShare class
     */     
    protected class ActionCustomerShare extends ItemAction {     
    
        public ActionCustomerShare()
        {
            this(null);
        }

        public ActionCustomerShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCustomerShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionCustomerShare", "actionCustomerShare_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerCancelShare class
     */     
    protected class ActionCustomerCancelShare extends ItemAction {     
    
        public ActionCustomerCancelShare()
        {
            this(null);
        }

        public ActionCustomerCancelShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCustomerCancelShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerCancelShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerCancelShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionCustomerCancelShare", "actionCustomerCancelShare_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTrackRecord class
     */     
    protected class ActionAddTrackRecord extends ItemAction {     
    
        public ActionAddTrackRecord()
        {
            this(null);
        }

        public ActionAddTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionAddTrackRecord", "actionAddTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionModifyCustomer class
     */     
    protected class ActionModifyCustomer extends ItemAction {     
    
        public ActionModifyCustomer()
        {
            this(null);
        }

        public ActionModifyCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionModifyCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionModifyCustomer", "actionModifyCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionModifyPhone class
     */     
    protected class ActionModifyPhone extends ItemAction {     
    
        public ActionModifyPhone()
        {
            this(null);
        }

        public ActionModifyPhone(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionModifyPhone.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyPhone.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyPhone.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionModifyPhone", "actionModifyPhone_actionPerformed", e);
        }
    }

    /**
     * output ActionClueQueryShow class
     */     
    protected class ActionClueQueryShow extends ItemAction {     
    
        public ActionClueQueryShow()
        {
            this(null);
        }

        public ActionClueQueryShow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClueQueryShow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClueQueryShow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClueQueryShow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionClueQueryShow", "actionClueQueryShow_actionPerformed", e);
        }
    }

    /**
     * output ActionQuestionPrint class
     */     
    protected class ActionQuestionPrint extends ItemAction {     
    
        public ActionQuestionPrint()
        {
            this(null);
        }

        public ActionQuestionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuestionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerListUI.this, "ActionQuestionPrint", "actionQuestionPrint_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerListUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.CustomerListUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo objectValue = new com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblMain;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}