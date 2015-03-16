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
public abstract class AbstractCommerceChanceListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCommerceChanceListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMarketUnit;
    protected com.kingdee.bos.ctrl.swing.KDTree treeSellProject;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane splitPaneRight;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane splitPaneLeft;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTerminate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModifyTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuExcelImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuTerminate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuModifyTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuDelTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerAdapter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdapter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCustomerInfo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQuestion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuestionPrint;
    protected ActionTerminate actionTerminate = null;
    protected ActionAddTrackRecord actionAddTrackRecord = null;
    protected ActionModifyTrackRecord actionModifyTrackRecord = null;
    protected ActionDelTrackRecord actionDelTrackRecord = null;
    protected ActionExcelBatchImport actionExcelBatchImport = null;
    protected ActionImportantTrack actionImportantTrack = null;
    protected ActionCancelImportantTrack actionCancelImportantTrack = null;
    protected ActionCustomerAdapter actionCustomerAdapter = null;
    protected ActionCustomerShare actionCustomerShare = null;
    protected ActionCustomerCancelShare actionCustomerCancelShare = null;
    protected ActionCustomerInfo actionCustomerInfo = null;
    protected ActionQuestionPrint actionQuestionPrint = null;
    /**
     * output class constructor
     */
    public AbstractCommerceChanceListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCommerceChanceListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "CommerceChanceQuery");
        //actionTerminate
        this.actionTerminate = new ActionTerminate(this);
        getActionManager().registerAction("actionTerminate", actionTerminate);
         this.actionTerminate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTrackRecord
        this.actionAddTrackRecord = new ActionAddTrackRecord(this);
        getActionManager().registerAction("actionAddTrackRecord", actionAddTrackRecord);
         this.actionAddTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModifyTrackRecord
        this.actionModifyTrackRecord = new ActionModifyTrackRecord(this);
        getActionManager().registerAction("actionModifyTrackRecord", actionModifyTrackRecord);
         this.actionModifyTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelTrackRecord
        this.actionDelTrackRecord = new ActionDelTrackRecord(this);
        getActionManager().registerAction("actionDelTrackRecord", actionDelTrackRecord);
         this.actionDelTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExcelBatchImport
        this.actionExcelBatchImport = new ActionExcelBatchImport(this);
        getActionManager().registerAction("actionExcelBatchImport", actionExcelBatchImport);
         this.actionExcelBatchImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportantTrack
        this.actionImportantTrack = new ActionImportantTrack(this);
        getActionManager().registerAction("actionImportantTrack", actionImportantTrack);
         this.actionImportantTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelImportantTrack
        this.actionCancelImportantTrack = new ActionCancelImportantTrack(this);
        getActionManager().registerAction("actionCancelImportantTrack", actionCancelImportantTrack);
         this.actionCancelImportantTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        //actionCustomerInfo
        this.actionCustomerInfo = new ActionCustomerInfo(this);
        getActionManager().registerAction("actionCustomerInfo", actionCustomerInfo);
         this.actionCustomerInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuestionPrint
        this.actionQuestionPrint = new ActionQuestionPrint(this);
        getActionManager().registerAction("actionQuestionPrint", actionQuestionPrint);
         this.actionQuestionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeMarketUnit = new com.kingdee.bos.ctrl.swing.KDTree();
        this.treeSellProject = new com.kingdee.bos.ctrl.swing.KDTree();
        this.splitPaneRight = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.splitPaneLeft = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnTerminate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnModifyTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuExcelImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuTerminate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuModifyTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuDelTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnImportantTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelImportantTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerAdapter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerCancelShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImportantTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelImportantTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdapter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnCustomerInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCustomerInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tblTrackRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQuestion = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnQuestionPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemQuestionPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.treeMarketUnit.setName("treeMarketUnit");
        this.treeSellProject.setName("treeSellProject");
        this.splitPaneRight.setName("splitPaneRight");
        this.splitPaneLeft.setName("splitPaneLeft");
        this.btnTerminate.setName("btnTerminate");
        this.btnAddTrackRecord.setName("btnAddTrackRecord");
        this.btnModifyTrackRecord.setName("btnModifyTrackRecord");
        this.btnDelTrackRecord.setName("btnDelTrackRecord");
        this.menuExcelImport.setName("menuExcelImport");
        this.menuTerminate.setName("menuTerminate");
        this.menuAddTrackRecord.setName("menuAddTrackRecord");
        this.menuModifyTrackRecord.setName("menuModifyTrackRecord");
        this.menuDelTrackRecord.setName("menuDelTrackRecord");
        this.btnImportantTrack.setName("btnImportantTrack");
        this.btnCancelImportantTrack.setName("btnCancelImportantTrack");
        this.btnCustomerAdapter.setName("btnCustomerAdapter");
        this.btnCustomerShare.setName("btnCustomerShare");
        this.btnCustomerCancelShare.setName("btnCustomerCancelShare");
        this.menuItemImportantTrack.setName("menuItemImportantTrack");
        this.menuItemCancelImportantTrack.setName("menuItemCancelImportantTrack");
        this.menuItemAdapter.setName("menuItemAdapter");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemCancelShare.setName("menuItemCancelShare");
        this.btnCustomerInfo.setName("btnCustomerInfo");
        this.menuItemCustomerInfo.setName("menuItemCustomerInfo");
        this.tblTrackRecord.setName("tblTrackRecord");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDContainer4.setName("kDContainer4");
        this.kDContainer5.setName("kDContainer5");
        this.tblQuestion.setName("tblQuestion");
        this.btnQuestionPrint.setName("btnQuestionPrint");
        this.menuItemQuestionPrint.setName("menuItemQuestionPrint");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"fdcCustomer.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"fdcCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"commerceStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"commerceDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"sysType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"intentBuildingPro\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"intentProductType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"intentArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"intentDirection\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"intentSight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"intentRoomType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"roomForm.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"intendingMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"firstPayAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"firstPayProportion.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"hopedPitch.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"hopedTotalPrices.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"buyHouseReason.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"hopedUnitPrice.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"productDetail.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"hopedFloor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"room.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"entry.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{fdcCustomer.number}</t:Cell><t:Cell>$Resource{fdcCustomer.name}</t:Cell><t:Cell>$Resource{commerceStatus}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{commerceDate}</t:Cell><t:Cell>$Resource{sysType}</t:Cell><t:Cell>$Resource{intentBuildingPro}</t:Cell><t:Cell>$Resource{intentProductType}</t:Cell><t:Cell>$Resource{intentArea}</t:Cell><t:Cell>$Resource{intentDirection}</t:Cell><t:Cell>$Resource{intentSight}</t:Cell><t:Cell>$Resource{intentRoomType}</t:Cell><t:Cell>$Resource{roomForm.name}</t:Cell><t:Cell>$Resource{intendingMoney}</t:Cell><t:Cell>$Resource{firstPayAmount}</t:Cell><t:Cell>$Resource{firstPayProportion.name}</t:Cell><t:Cell>$Resource{hopedPitch.name}</t:Cell><t:Cell>$Resource{hopedTotalPrices.name}</t:Cell><t:Cell>$Resource{buyHouseReason.name}</t:Cell><t:Cell>$Resource{hopedUnitPrice.name}</t:Cell><t:Cell>$Resource{productDetail.name}</t:Cell><t:Cell>$Resource{hopedFloor.name}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{room.name}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{entry.id}</t:Cell><t:Cell>$Resource{room.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","name","fdcCustomer.number","fdcCustomer.name","commerceStatus","sellProject.name","commerceLevel.name","commerceDate","sysType","hopedBuildingProperty.name","hopedProductType.name","hopedAreaRequirement.name","hopedDirection.name","hopedSightRequirement.name","hopedRoomModelType.name","roomForm.name","intendingMoney","firstPayAmount","firstPayProportion.name","hopedPitch.name","hopedTotalPrices.name","buyHouseReason.name","hopedUnitPrice.name","productDetail.name","hopedFloor.name","saleMan.name","creator.name","createTime","room.number","room.name","CU.id","commerceRoomEntry.id","room.id"});


        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setAutoscrolls(true);		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setAutoscrolls(true);		
        this.kDContainer2.setEnableActive(false);
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kDScrollPane1
        // kDScrollPane2
        // treeMarketUnit
        this.treeMarketUnit.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMarketUnit_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // treeSellProject
        this.treeSellProject.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeSellProject_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // splitPaneRight		
        this.splitPaneRight.setOrientation(0);		
        this.splitPaneRight.setDividerLocation(350);
        // splitPaneLeft		
        this.splitPaneLeft.setOrientation(0);		
        this.splitPaneLeft.setDividerLocation(350);
        // btnTerminate
        this.btnTerminate.setAction((IItemAction)ActionProxyFactory.getProxy(actionTerminate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTerminate.setText(resHelper.getString("btnTerminate.text"));		
        this.btnTerminate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_terminateinstance"));
        // btnAddTrackRecord
        this.btnAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTrackRecord.setText(resHelper.getString("btnAddTrackRecord.text"));		
        this.btnAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // btnModifyTrackRecord
        this.btnModifyTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModifyTrackRecord.setText(resHelper.getString("btnModifyTrackRecord.text"));		
        this.btnModifyTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnDelTrackRecord
        this.btnDelTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelTrackRecord.setText(resHelper.getString("btnDelTrackRecord.text"));		
        this.btnDelTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_delete"));
        // menuExcelImport
        this.menuExcelImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelBatchImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuExcelImport.setText(resHelper.getString("menuExcelImport.text"));		
        this.menuExcelImport.setToolTipText(resHelper.getString("menuExcelImport.toolTipText"));		
        this.menuExcelImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // menuTerminate
        this.menuTerminate.setAction((IItemAction)ActionProxyFactory.getProxy(actionTerminate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuTerminate.setText(resHelper.getString("menuTerminate.text"));		
        this.menuTerminate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_terminateinstance"));
        // menuAddTrackRecord
        this.menuAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddTrackRecord.setText(resHelper.getString("menuAddTrackRecord.text"));		
        this.menuAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // menuModifyTrackRecord
        this.menuModifyTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuModifyTrackRecord.setText(resHelper.getString("menuModifyTrackRecord.text"));		
        this.menuModifyTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // menuDelTrackRecord
        this.menuDelTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuDelTrackRecord.setText(resHelper.getString("menuDelTrackRecord.text"));		
        this.menuDelTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_delete"));
        // btnImportantTrack
        this.btnImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportantTrack.setText(resHelper.getString("btnImportantTrack.text"));		
        this.btnImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));
        // btnCancelImportantTrack
        this.btnCancelImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelImportantTrack.setText(resHelper.getString("btnCancelImportantTrack.text"));		
        this.btnCancelImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
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
        // menuItemImportantTrack
        this.menuItemImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportantTrack.setText(resHelper.getString("menuItemImportantTrack.text"));		
        this.menuItemImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));
        // menuItemCancelImportantTrack
        this.menuItemCancelImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelImportantTrack.setText(resHelper.getString("menuItemCancelImportantTrack.text"));		
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
        // btnCustomerInfo
        this.btnCustomerInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerInfo.setText(resHelper.getString("btnCustomerInfo.text"));		
        this.btnCustomerInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showdata"));
        // menuItemCustomerInfo
        this.menuItemCustomerInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCustomerInfo.setText(resHelper.getString("menuItemCustomerInfo.text"));		
        this.menuItemCustomerInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showdata"));
        // tblTrackRecord
		String tblTrackRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"eventDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"head.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"eventType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"receptionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceChance.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{eventDate}</t:Cell><t:Cell>$Resource{head.name}</t:Cell><t:Cell>$Resource{trackResult}</t:Cell><t:Cell>$Resource{eventType.name}</t:Cell><t:Cell>$Resource{receptionType.name}</t:Cell><t:Cell>$Resource{commerceChance.name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTrackRecord.setFormatXml(resHelper.translateString("tblTrackRecord",tblTrackRecordStrXML));
        this.tblTrackRecord.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTrackRecord_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(350);
        // kDContainer4		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));
        // kDContainer5		
        this.kDContainer5.setTitle(resHelper.getString("kDContainer5.title"));
        // tblQuestion
		String tblQuestionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"printDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operationDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"title\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{printDate}</t:Cell><t:Cell>$Resource{operationDate}</t:Cell><t:Cell>$Resource{title}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblQuestion.setFormatXml(resHelper.translateString("tblQuestion",tblQuestionStrXML));
        this.tblQuestion.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblQuestion_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnQuestionPrint
        this.btnQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPrint.setText(resHelper.getString("btnQuestionPrint.text"));		
        this.btnQuestionPrint.setToolTipText(resHelper.getString("btnQuestionPrint.toolTipText"));		
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
        // menuItemQuestionPrint
        this.menuItemQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuestionPrint.setText(resHelper.getString("menuItemQuestionPrint.text"));		
        this.menuItemQuestionPrint.setToolTipText(resHelper.getString("menuItemQuestionPrint.toolTipText"));		
        this.menuItemQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
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
        splitPaneRight.setBounds(new Rectangle(265, 10, 740, 608));
        this.add(splitPaneRight, new KDLayout.Constraints(265, 10, 740, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        splitPaneLeft.setBounds(new Rectangle(10, 10, 245, 608));
        this.add(splitPaneLeft, new KDLayout.Constraints(10, 10, 245, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        //splitPaneRight
        splitPaneRight.add(kDContainer3, "top");
        splitPaneRight.add(kDSplitPane1, "bottom");
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(kDContainer4, "left");
        kDSplitPane1.add(kDContainer5, "right");
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer4.getContentPane().add(tblTrackRecord, BorderLayout.CENTER);
        //kDContainer5
kDContainer5.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer5.getContentPane().add(tblQuestion, BorderLayout.CENTER);
        //splitPaneLeft
        splitPaneLeft.add(kDContainer1, "top");
        splitPaneLeft.add(kDContainer2, "bottom");
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeMarketUnit, null);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeSellProject, null);

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
        menuFile.add(menuItemQuestionPrint);
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
        menuEdit.add(menuExcelImport);
        menuEdit.add(menuTerminate);
        menuEdit.add(menuAddTrackRecord);
        menuEdit.add(menuModifyTrackRecord);
        menuEdit.add(menuDelTrackRecord);
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
        menuBiz.add(menuItemImportantTrack);
        menuBiz.add(menuItemCancelImportantTrack);
        menuBiz.add(menuItemAdapter);
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemCancelShare);
        menuBiz.add(menuItemCustomerInfo);
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
        this.toolBar.add(btnTerminate);
        this.toolBar.add(btnAddTrackRecord);
        this.toolBar.add(btnModifyTrackRecord);
        this.toolBar.add(btnDelTrackRecord);
        this.toolBar.add(btnImportantTrack);
        this.toolBar.add(btnCancelImportantTrack);
        this.toolBar.add(btnCustomerAdapter);
        this.toolBar.add(btnCustomerShare);
        this.toolBar.add(btnCustomerCancelShare);
        this.toolBar.add(btnCustomerInfo);
        this.toolBar.add(btnQuestionPrint);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CommerceChanceListUIHandler";
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
     * output treeMarketUnit_valueChanged method
     */
    protected void treeMarketUnit_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output treeSellProject_valueChanged method
     */
    protected void treeSellProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tblTrackRecord_tableClicked method
     */
    protected void tblTrackRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblQuestion_tableClicked method
     */
    protected void tblQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("sysType"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("saleMan.name"));
        sic.add(new SelectorItemInfo("commerceLevel.name"));
        sic.add(new SelectorItemInfo("commerceStatus"));
        sic.add(new SelectorItemInfo("fdcCustomer.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("fdcCustomer.number"));
        sic.add(new SelectorItemInfo("commerceDate"));
        sic.add(new SelectorItemInfo("hopedBuildingProperty.name"));
        sic.add(new SelectorItemInfo("hopedProductType.name"));
        sic.add(new SelectorItemInfo("hopedAreaRequirement.name"));
        sic.add(new SelectorItemInfo("hopedDirection.name"));
        sic.add(new SelectorItemInfo("hopedSightRequirement.name"));
        sic.add(new SelectorItemInfo("hopedRoomModelType.name"));
        sic.add(new SelectorItemInfo("roomForm.name"));
        sic.add(new SelectorItemInfo("firstPayProportion.name"));
        sic.add(new SelectorItemInfo("hopedPitch.name"));
        sic.add(new SelectorItemInfo("hopedTotalPrices.name"));
        sic.add(new SelectorItemInfo("buyHouseReason.name"));
        sic.add(new SelectorItemInfo("hopedUnitPrice.name"));
        sic.add(new SelectorItemInfo("productDetail.name"));
        sic.add(new SelectorItemInfo("hopedFloor.name"));
        sic.add(new SelectorItemInfo("firstPayAmount"));
        sic.add(new SelectorItemInfo("intendingMoney"));
        sic.add(new SelectorItemInfo("commerceRoomEntry.id"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("room.name"));
        return sic;
    }        
    	

    /**
     * output actionTerminate_actionPerformed method
     */
    public void actionTerminate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTrackRecord_actionPerformed method
     */
    public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModifyTrackRecord_actionPerformed method
     */
    public void actionModifyTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelTrackRecord_actionPerformed method
     */
    public void actionDelTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExcelBatchImport_actionPerformed method
     */
    public void actionExcelBatchImport_actionPerformed(ActionEvent e) throws Exception
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
     * output actionCustomerInfo_actionPerformed method
     */
    public void actionCustomerInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuestionPrint_actionPerformed method
     */
    public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionTerminate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTerminate() {
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
	public RequestContext prepareActionModifyTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModifyTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionDelTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionExcelBatchImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelBatchImport() {
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
	public RequestContext prepareActionCustomerInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerInfo() {
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
     * output ActionTerminate class
     */     
    protected class ActionTerminate extends ItemAction {     
    
        public ActionTerminate()
        {
            this(null);
        }

        public ActionTerminate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTerminate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTerminate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTerminate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionTerminate", "actionTerminate_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionAddTrackRecord", "actionAddTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionModifyTrackRecord class
     */     
    protected class ActionModifyTrackRecord extends ItemAction {     
    
        public ActionModifyTrackRecord()
        {
            this(null);
        }

        public ActionModifyTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionModifyTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionModifyTrackRecord", "actionModifyTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionDelTrackRecord class
     */     
    protected class ActionDelTrackRecord extends ItemAction {     
    
        public ActionDelTrackRecord()
        {
            this(null);
        }

        public ActionDelTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionDelTrackRecord", "actionDelTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionExcelBatchImport class
     */     
    protected class ActionExcelBatchImport extends ItemAction {     
    
        public ActionExcelBatchImport()
        {
            this(null);
        }

        public ActionExcelBatchImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExcelBatchImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelBatchImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelBatchImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionExcelBatchImport", "actionExcelBatchImport_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionImportantTrack", "actionImportantTrack_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionCancelImportantTrack", "actionCancelImportantTrack_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionCustomerAdapter", "actionCustomerAdapter_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionCustomerShare", "actionCustomerShare_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionCustomerCancelShare", "actionCustomerCancelShare_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerInfo class
     */     
    protected class ActionCustomerInfo extends ItemAction {     
    
        public ActionCustomerInfo()
        {
            this(null);
        }

        public ActionCustomerInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCustomerInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionCustomerInfo", "actionCustomerInfo_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCommerceChanceListUI.this, "ActionQuestionPrint", "actionQuestionPrint_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CommerceChanceListUI");
    }




}