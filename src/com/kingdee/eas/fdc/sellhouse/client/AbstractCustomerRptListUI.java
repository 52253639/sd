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
public abstract class AbstractCustomerRptListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerRptListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSendsms;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCusTransform;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPaper;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsider;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeliver;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMerge;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddToSysCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowAll;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuestion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpdate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeliver;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMerge;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddToSysCustomer;
    protected ActionCommerceChance actionCommerceChance = null;
    protected ActionTrack actionTrack = null;
    protected ActionInsider actionInsider = null;
    protected ActionChangeName actionChangeName = null;
    protected ActionUpdate actionUpdate = null;
    protected ActionShare actionShare = null;
    protected ActionDeliver actionDeliver = null;
    protected ActionMerge actionMerge = null;
    protected ActionQuestionPaper actionQuestionPaper = null;
    protected ActionImport actionImport = null;
    protected ActionCusTansform actionCusTansform = null;
    protected ActionAddToSysCustomer actionAddToSysCustomer = null;
    protected ActionSend actionSend = null;
    /**
     * output class constructor
     */
    public AbstractCustomerRptListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerRptListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHECustomerQuery");
        //actionCommerceChance
        this.actionCommerceChance = new ActionCommerceChance(this);
        getActionManager().registerAction("actionCommerceChance", actionCommerceChance);
         this.actionCommerceChance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTrack
        this.actionTrack = new ActionTrack(this);
        getActionManager().registerAction("actionTrack", actionTrack);
         this.actionTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsider
        this.actionInsider = new ActionInsider(this);
        getActionManager().registerAction("actionInsider", actionInsider);
         this.actionInsider.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeName
        this.actionChangeName = new ActionChangeName(this);
        getActionManager().registerAction("actionChangeName", actionChangeName);
         this.actionChangeName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdate
        this.actionUpdate = new ActionUpdate(this);
        getActionManager().registerAction("actionUpdate", actionUpdate);
         this.actionUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShare
        this.actionShare = new ActionShare(this);
        getActionManager().registerAction("actionShare", actionShare);
         this.actionShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeliver
        this.actionDeliver = new ActionDeliver(this);
        getActionManager().registerAction("actionDeliver", actionDeliver);
         this.actionDeliver.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMerge
        this.actionMerge = new ActionMerge(this);
        getActionManager().registerAction("actionMerge", actionMerge);
         this.actionMerge.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuestionPaper
        this.actionQuestionPaper = new ActionQuestionPaper(this);
        getActionManager().registerAction("actionQuestionPaper", actionQuestionPaper);
         this.actionQuestionPaper.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCusTansform
        this.actionCusTansform = new ActionCusTansform(this);
        getActionManager().registerAction("actionCusTansform", actionCusTansform);
         this.actionCusTansform.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddToSysCustomer
        this.actionAddToSysCustomer = new ActionAddToSysCustomer(this);
        getActionManager().registerAction("actionAddToSysCustomer", actionAddToSysCustomer);
         this.actionAddToSysCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSend
        this.actionSend = new ActionSend(this);
        getActionManager().registerAction("actionSend", actionSend);
         this.actionSend.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnSendsms = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCusTransform = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCommerceChance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuestionPaper = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsider = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpdate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeliver = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMerge = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddToSysCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShowAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCommerceChance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuestion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpdate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeliver = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemMerge = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddToSysCustomer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDTreeView1.setName("kDTreeView1");
        this.treeMain.setName("treeMain");
        this.btnSendsms.setName("btnSendsms");
        this.btnCusTransform.setName("btnCusTransform");
        this.btnCommerceChance.setName("btnCommerceChance");
        this.btnTrack.setName("btnTrack");
        this.btnQuestionPaper.setName("btnQuestionPaper");
        this.btnInsider.setName("btnInsider");
        this.btnChangeName.setName("btnChangeName");
        this.btnUpdate.setName("btnUpdate");
        this.btnShare.setName("btnShare");
        this.btnDeliver.setName("btnDeliver");
        this.btnMerge.setName("btnMerge");
        this.btnImport.setName("btnImport");
        this.btnAddToSysCustomer.setName("btnAddToSysCustomer");
        this.btnShowAll.setName("btnShowAll");
        this.menuItemCommerceChance.setName("menuItemCommerceChance");
        this.menuItemTrack.setName("menuItemTrack");
        this.menuItemQuestion.setName("menuItemQuestion");
        this.menuItemChangeName.setName("menuItemChangeName");
        this.menuItemUpdate.setName("menuItemUpdate");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemDeliver.setName("menuItemDeliver");
        this.menuItemMerge.setName("menuItemMerge");
        this.menuItemImport.setName("menuItemImport");
        this.menuItemAddToSysCustomer.setName("menuItemAddToSysCustomer");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"customerType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"code\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"simpleCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"identity.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"firstLinkman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"recommended\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"bookedAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"postalCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"bookedPlace.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"enterpriceProperty.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"industry.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"isInsider\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"corporate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"corporateTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"isChooseRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"firstConsultant.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"trackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"createUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"lastUpdateUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"createWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{customerType}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{code}</t:Cell><t:Cell>$Resource{simpleCode}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{identity.name}</t:Cell><t:Cell>$Resource{firstLinkman}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{recommended}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{bookedAddress}</t:Cell><t:Cell>$Resource{postalCode}</t:Cell><t:Cell>$Resource{bookedPlace.name}</t:Cell><t:Cell>$Resource{enterpriceProperty.name}</t:Cell><t:Cell>$Resource{industry.name}</t:Cell><t:Cell>$Resource{isInsider}</t:Cell><t:Cell>$Resource{corporate}</t:Cell><t:Cell>$Resource{corporateTel}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{isChooseRoom}</t:Cell><t:Cell>$Resource{firstConsultant.name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{trackDate}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{createUnit.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{lastUpdateUnit.name}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{createWay}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblMain_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"id","customerType","number","code","simpleCode","name","sex","identity.name","firstLinkman","phone","certificateNumber","recommended","email","mailAddress","bookedAddress","postalCode","bookedPlace.name","enterpriceProperty.name","industry.name","isInsider","corporate","corporateTel","description","isEnabled","isChooseRoom","propertyConsultant.name","commerceLevel.name","trackDate","createTime","createUnit.name","creator.name","lastUpdateTime","lastUpdateUnit.name","lastUpdateUser.name","createWay","sellProject.name"});

		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));		
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));		
        this.btnCancelCancel.setVisible(true);
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
        // btnSendsms
        this.btnSendsms.setAction((IItemAction)ActionProxyFactory.getProxy(actionSend, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSendsms.setText(resHelper.getString("btnSendsms.text"));		
        this.btnSendsms.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sendmessage"));
        // btnCusTransform
        this.btnCusTransform.setAction((IItemAction)ActionProxyFactory.getProxy(actionCusTansform, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCusTransform.setText(resHelper.getString("btnCusTransform.text"));
        // btnCommerceChance
        this.btnCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCommerceChance.setText(resHelper.getString("btnCommerceChance.text"));		
        this.btnCommerceChance.setToolTipText(resHelper.getString("btnCommerceChance.toolTipText"));
        // btnTrack
        this.btnTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTrack.setText(resHelper.getString("btnTrack.text"));		
        this.btnTrack.setToolTipText(resHelper.getString("btnTrack.toolTipText"));
        // btnQuestionPaper
        this.btnQuestionPaper.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPaper, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPaper.setText(resHelper.getString("btnQuestionPaper.text"));		
        this.btnQuestionPaper.setToolTipText(resHelper.getString("btnQuestionPaper.toolTipText"));
        // btnInsider
        this.btnInsider.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsider, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsider.setText(resHelper.getString("btnInsider.text"));		
        this.btnInsider.setToolTipText(resHelper.getString("btnInsider.toolTipText"));
        // btnChangeName
        this.btnChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeName.setText(resHelper.getString("btnChangeName.text"));		
        this.btnChangeName.setToolTipText(resHelper.getString("btnChangeName.toolTipText"));
        // btnUpdate
        this.btnUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdate.setText(resHelper.getString("btnUpdate.text"));		
        this.btnUpdate.setToolTipText(resHelper.getString("btnUpdate.toolTipText"));
        // btnShare
        this.btnShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShare.setText(resHelper.getString("btnShare.text"));		
        this.btnShare.setToolTipText(resHelper.getString("btnShare.toolTipText"));
        // btnDeliver
        this.btnDeliver.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeliver, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeliver.setText(resHelper.getString("btnDeliver.text"));		
        this.btnDeliver.setToolTipText(resHelper.getString("btnDeliver.toolTipText"));
        // btnMerge
        this.btnMerge.setAction((IItemAction)ActionProxyFactory.getProxy(actionMerge, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMerge.setText(resHelper.getString("btnMerge.text"));		
        this.btnMerge.setToolTipText(resHelper.getString("btnMerge.toolTipText"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));		
        this.btnImport.setToolTipText(resHelper.getString("btnImport.toolTipText"));		
        this.btnImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // btnAddToSysCustomer
        this.btnAddToSysCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddToSysCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddToSysCustomer.setText(resHelper.getString("btnAddToSysCustomer.text"));		
        this.btnAddToSysCustomer.setToolTipText(resHelper.getString("btnAddToSysCustomer.toolTipText"));
        // btnShowAll		
        this.btnShowAll.setText(resHelper.getString("btnShowAll.text"));
        this.btnShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnShowAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // menuItemCommerceChance
        this.menuItemCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCommerceChance.setText(resHelper.getString("menuItemCommerceChance.text"));		
        this.menuItemCommerceChance.setToolTipText(resHelper.getString("menuItemCommerceChance.toolTipText"));
        // menuItemTrack
        this.menuItemTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTrack.setText(resHelper.getString("menuItemTrack.text"));		
        this.menuItemTrack.setToolTipText(resHelper.getString("menuItemTrack.toolTipText"));
        // menuItemQuestion
        this.menuItemQuestion.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPaper, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuestion.setToolTipText(resHelper.getString("menuItemQuestion.toolTipText"));		
        this.menuItemQuestion.setText(resHelper.getString("menuItemQuestion.text"));
        // menuItemChangeName
        this.menuItemChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeName.setText(resHelper.getString("menuItemChangeName.text"));		
        this.menuItemChangeName.setToolTipText(resHelper.getString("menuItemChangeName.toolTipText"));
        // menuItemUpdate
        this.menuItemUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUpdate.setText(resHelper.getString("menuItemUpdate.text"));		
        this.menuItemUpdate.setToolTipText(resHelper.getString("menuItemUpdate.toolTipText"));
        // menuItemShare
        this.menuItemShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShare.setText(resHelper.getString("menuItemShare.text"));		
        this.menuItemShare.setToolTipText(resHelper.getString("menuItemShare.toolTipText"));
        // menuItemDeliver
        this.menuItemDeliver.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeliver, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeliver.setText(resHelper.getString("menuItemDeliver.text"));		
        this.menuItemDeliver.setToolTipText(resHelper.getString("menuItemDeliver.toolTipText"));
        // menuItemMerge
        this.menuItemMerge.setAction((IItemAction)ActionProxyFactory.getProxy(actionMerge, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMerge.setText(resHelper.getString("menuItemMerge.text"));		
        this.menuItemMerge.setToolTipText(resHelper.getString("menuItemMerge.toolTipText"));
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setToolTipText(resHelper.getString("menuItemImport.toolTipText"));
        // menuItemAddToSysCustomer
        this.menuItemAddToSysCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddToSysCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddToSysCustomer.setText(resHelper.getString("menuItemAddToSysCustomer.text"));		
        this.menuItemAddToSysCustomer.setToolTipText(resHelper.getString("menuItemAddToSysCustomer.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(9, 4, 996, 600));
        this.add(kDSplitPane1, new KDLayout.Constraints(9, 4, 996, 600, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
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
        menuBiz.add(menuItemCommerceChance);
        menuBiz.add(menuItemTrack);
        menuBiz.add(menuItemQuestion);
        menuBiz.add(menuItemChangeName);
        menuBiz.add(menuItemUpdate);
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemDeliver);
        menuBiz.add(menuItemMerge);
        menuBiz.add(menuItemImport);
        menuBiz.add(menuItemAddToSysCustomer);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnSendsms);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCusTransform);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCommerceChance);
        this.toolBar.add(btnTrack);
        this.toolBar.add(btnQuestionPaper);
        this.toolBar.add(btnInsider);
        this.toolBar.add(btnChangeName);
        this.toolBar.add(btnUpdate);
        this.toolBar.add(btnShare);
        this.toolBar.add(btnDeliver);
        this.toolBar.add(btnMerge);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnAddToSysCustomer);
        this.toolBar.add(btnShowAll);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerRptListUIHandler";
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
     * output btnShowAll_actionPerformed method
     */
    protected void btnShowAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("code"));
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("officeTel"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("otherTel"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("certificateType"));
        sic.add(new SelectorItemInfo("certificateNumber"));
        sic.add(new SelectorItemInfo("dayOfbirth"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("bookedAddress"));
        sic.add(new SelectorItemInfo("postalCode"));
        sic.add(new SelectorItemInfo("corporate"));
        sic.add(new SelectorItemInfo("corporateTel"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("isChooseRoom"));
        sic.add(new SelectorItemInfo("isInsider"));
        sic.add(new SelectorItemInfo("createWay"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("region.name"));
        sic.add(new SelectorItemInfo("region.id"));
        sic.add(new SelectorItemInfo("country.name"));
        sic.add(new SelectorItemInfo("country.id"));
        sic.add(new SelectorItemInfo("identity.name"));
        sic.add(new SelectorItemInfo("identity.id"));
        sic.add(new SelectorItemInfo("createUnit.name"));
        sic.add(new SelectorItemInfo("createUnit.id"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("linkmanList.name"));
        sic.add(new SelectorItemInfo("linkmanList.id"));
        sic.add(new SelectorItemInfo("industry.name"));
        sic.add(new SelectorItemInfo("industry.id"));
        sic.add(new SelectorItemInfo("city.name"));
        sic.add(new SelectorItemInfo("city.id"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("creator.id"));
        sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        sic.add(new SelectorItemInfo("insider.insiderName"));
        sic.add(new SelectorItemInfo("insider.id"));
        sic.add(new SelectorItemInfo("province.name"));
        sic.add(new SelectorItemInfo("province.id"));
        sic.add(new SelectorItemInfo("lastUpdateUnit.name"));
        sic.add(new SelectorItemInfo("lastUpdateUnit.id"));
        sic.add(new SelectorItemInfo("customerOrigin.name"));
        sic.add(new SelectorItemInfo("customerOrigin.id"));
        sic.add(new SelectorItemInfo("simpleCode"));
        sic.add(new SelectorItemInfo("workCompany"));
        sic.add(new SelectorItemInfo("nativePlace"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("firstLinkman"));
        sic.add(new SelectorItemInfo("bookedPlace.name"));
        sic.add(new SelectorItemInfo("enterpriceProperty.name"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("isPublic"));
        sic.add(new SelectorItemInfo("propertyConsultant.name"));
        sic.add(new SelectorItemInfo("recommended"));
        sic.add(new SelectorItemInfo("commerceLevel.name"));
        sic.add(new SelectorItemInfo("trackDate"));
        return sic;
    }        
    	

    /**
     * output actionCommerceChance_actionPerformed method
     */
    public void actionCommerceChance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTrack_actionPerformed method
     */
    public void actionTrack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInsider_actionPerformed method
     */
    public void actionInsider_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeName_actionPerformed method
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdate_actionPerformed method
     */
    public void actionUpdate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShare_actionPerformed method
     */
    public void actionShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeliver_actionPerformed method
     */
    public void actionDeliver_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMerge_actionPerformed method
     */
    public void actionMerge_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuestionPaper_actionPerformed method
     */
    public void actionQuestionPaper_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCusTansform_actionPerformed method
     */
    public void actionCusTansform_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddToSysCustomer_actionPerformed method
     */
    public void actionAddToSysCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSend_actionPerformed method
     */
    public void actionSend_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCommerceChance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCommerceChance() {
    	return false;
    }
	public RequestContext prepareActionTrack(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTrack() {
    	return false;
    }
	public RequestContext prepareActionInsider(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsider() {
    	return false;
    }
	public RequestContext prepareActionChangeName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeName() {
    	return false;
    }
	public RequestContext prepareActionUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdate() {
    	return false;
    }
	public RequestContext prepareActionShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShare() {
    	return false;
    }
	public RequestContext prepareActionDeliver(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeliver() {
    	return false;
    }
	public RequestContext prepareActionMerge(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMerge() {
    	return false;
    }
	public RequestContext prepareActionQuestionPaper(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuestionPaper() {
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
	public RequestContext prepareActionCusTansform(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCusTansform() {
    	return false;
    }
	public RequestContext prepareActionAddToSysCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddToSysCustomer() {
    	return false;
    }
	public RequestContext prepareActionSend(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSend() {
    	return false;
    }

    /**
     * output ActionCommerceChance class
     */     
    protected class ActionCommerceChance extends ItemAction {     
    
        public ActionCommerceChance()
        {
            this(null);
        }

        public ActionCommerceChance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCommerceChance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommerceChance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommerceChance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionCommerceChance", "actionCommerceChance_actionPerformed", e);
        }
    }

    /**
     * output ActionTrack class
     */     
    protected class ActionTrack extends ItemAction {     
    
        public ActionTrack()
        {
            this(null);
        }

        public ActionTrack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTrack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTrack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTrack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionTrack", "actionTrack_actionPerformed", e);
        }
    }

    /**
     * output ActionInsider class
     */     
    protected class ActionInsider extends ItemAction {     
    
        public ActionInsider()
        {
            this(null);
        }

        public ActionInsider(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInsider.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsider.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsider.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionInsider", "actionInsider_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeName class
     */     
    protected class ActionChangeName extends ItemAction {     
    
        public ActionChangeName()
        {
            this(null);
        }

        public ActionChangeName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionChangeName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionChangeName", "actionChangeName_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdate class
     */     
    protected class ActionUpdate extends ItemAction {     
    
        public ActionUpdate()
        {
            this(null);
        }

        public ActionUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionUpdate", "actionUpdate_actionPerformed", e);
        }
    }

    /**
     * output ActionShare class
     */     
    protected class ActionShare extends ItemAction {     
    
        public ActionShare()
        {
            this(null);
        }

        public ActionShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionShare", "actionShare_actionPerformed", e);
        }
    }

    /**
     * output ActionDeliver class
     */     
    protected class ActionDeliver extends ItemAction {     
    
        public ActionDeliver()
        {
            this(null);
        }

        public ActionDeliver(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDeliver.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeliver.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeliver.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionDeliver", "actionDeliver_actionPerformed", e);
        }
    }

    /**
     * output ActionMerge class
     */     
    protected class ActionMerge extends ItemAction {     
    
        public ActionMerge()
        {
            this(null);
        }

        public ActionMerge(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMerge.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMerge.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMerge.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionMerge", "actionMerge_actionPerformed", e);
        }
    }

    /**
     * output ActionQuestionPaper class
     */     
    protected class ActionQuestionPaper extends ItemAction {     
    
        public ActionQuestionPaper()
        {
            this(null);
        }

        public ActionQuestionPaper(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuestionPaper.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPaper.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPaper.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionQuestionPaper", "actionQuestionPaper_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionCusTansform class
     */     
    protected class ActionCusTansform extends ItemAction {     
    
        public ActionCusTansform()
        {
            this(null);
        }

        public ActionCusTansform(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCusTansform.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCusTansform.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCusTansform.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionCusTansform", "actionCusTansform_actionPerformed", e);
        }
    }

    /**
     * output ActionAddToSysCustomer class
     */     
    protected class ActionAddToSysCustomer extends ItemAction {     
    
        public ActionAddToSysCustomer()
        {
            this(null);
        }

        public ActionAddToSysCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddToSysCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddToSysCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddToSysCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionAddToSysCustomer", "actionAddToSysCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionSend class
     */     
    protected class ActionSend extends ItemAction {     
    
        public ActionSend()
        {
            this(null);
        }

        public ActionSend(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSend.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSend.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSend.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptListUI.this, "ActionSend", "actionSend_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerRptListUI");
    }




}