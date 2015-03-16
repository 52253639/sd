/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyControlUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyControlUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRejiggerTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuitTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHandTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnKeepRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApplyTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnContinuedTenancy;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemApplyTenancy;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContinuedTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRejiggerTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemHandTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemKeepRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelTenancy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane spnlTenancy;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTenStat;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton solidSideRadioBtn;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton planeRadioBtn;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup imageGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerZoom;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBoxZoom;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton effectRadioBtn;
    protected ActionReceiveBill actionReceiveBill = null;
    protected ActionCancelTenancy actionCancelTenancy = null;
    protected ActionHandleTenancy actionHandleTenancy = null;
    protected ActionApplyTenancy actionApplyTenancy = null;
    protected ActionContinueTenancy actionContinueTenancy = null;
    protected ActionRejiggerTenancy actionRejiggerTenancy = null;
    protected ActionQuitTenancy actionQuitTenancy = null;
    protected ActionKeepRoom actionKeepRoom = null;
    protected ActionChangeName actionChangeName = null;
    /**
     * output class constructor
     */
    public AbstractTenancyControlUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyControlUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionReceiveBill
        this.actionReceiveBill = new ActionReceiveBill(this);
        getActionManager().registerAction("actionReceiveBill", actionReceiveBill);
         this.actionReceiveBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelTenancy
        this.actionCancelTenancy = new ActionCancelTenancy(this);
        getActionManager().registerAction("actionCancelTenancy", actionCancelTenancy);
         this.actionCancelTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHandleTenancy
        this.actionHandleTenancy = new ActionHandleTenancy(this);
        getActionManager().registerAction("actionHandleTenancy", actionHandleTenancy);
         this.actionHandleTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApplyTenancy
        this.actionApplyTenancy = new ActionApplyTenancy(this);
        getActionManager().registerAction("actionApplyTenancy", actionApplyTenancy);
         this.actionApplyTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContinueTenancy
        this.actionContinueTenancy = new ActionContinueTenancy(this);
        getActionManager().registerAction("actionContinueTenancy", actionContinueTenancy);
         this.actionContinueTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRejiggerTenancy
        this.actionRejiggerTenancy = new ActionRejiggerTenancy(this);
        getActionManager().registerAction("actionRejiggerTenancy", actionRejiggerTenancy);
         this.actionRejiggerTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuitTenancy
        this.actionQuitTenancy = new ActionQuitTenancy(this);
        getActionManager().registerAction("actionQuitTenancy", actionQuitTenancy);
         this.actionQuitTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionKeepRoom
        this.actionKeepRoom = new ActionKeepRoom(this);
        getActionManager().registerAction("actionKeepRoom", actionKeepRoom);
         this.actionKeepRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeName
        this.actionChangeName = new ActionChangeName(this);
        getActionManager().registerAction("actionChangeName", actionChangeName);
         this.actionChangeName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnRejiggerTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuitTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHandTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnKeepRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApplyTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnContinuedTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnCancelTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemApplyTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnChangeTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemChangeTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemContinuedTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRejiggerTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemHandTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemKeepRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelTenancy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnReceiveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemReceiveBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.spnlTenancy = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblTenStat = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.solidSideRadioBtn = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.planeRadioBtn = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.imageGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDLabelContainerZoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboBoxZoom = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.effectRadioBtn = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnRejiggerTenancy.setName("btnRejiggerTenancy");
        this.btnQuitTenancy.setName("btnQuitTenancy");
        this.btnHandTenancy.setName("btnHandTenancy");
        this.btnKeepRoom.setName("btnKeepRoom");
        this.btnApplyTenancy.setName("btnApplyTenancy");
        this.btnContinuedTenancy.setName("btnContinuedTenancy");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.btnCancelTenancy.setName("btnCancelTenancy");
        this.btnChangeName.setName("btnChangeName");
        this.menuItemApplyTenancy.setName("menuItemApplyTenancy");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnChangeTenancy.setName("btnChangeTenancy");
        this.menuItemChangeTenancy.setName("menuItemChangeTenancy");
        this.menuItemContinuedTenancy.setName("menuItemContinuedTenancy");
        this.menuItemRejiggerTenancy.setName("menuItemRejiggerTenancy");
        this.menuItemQuitTenancy.setName("menuItemQuitTenancy");
        this.menuItemHandTenancy.setName("menuItemHandTenancy");
        this.menuItemKeepRoom.setName("menuItemKeepRoom");
        this.menuItemCancelTenancy.setName("menuItemCancelTenancy");
        this.menuItemChangeName.setName("menuItemChangeName");
        this.btnReceiveBill.setName("btnReceiveBill");
        this.menuItemReceiveBill.setName("menuItemReceiveBill");
        this.spnlTenancy.setName("spnlTenancy");
        this.kDPanel1.setName("kDPanel1");
        this.tblTenStat.setName("tblTenStat");
        this.sclPanel.setName("sclPanel");
        this.kDPanel2.setName("kDPanel2");
        this.solidSideRadioBtn.setName("solidSideRadioBtn");
        this.planeRadioBtn.setName("planeRadioBtn");
        this.kDLabelContainerZoom.setName("kDLabelContainerZoom");
        this.comboBoxZoom.setName("comboBoxZoom");
        this.effectRadioBtn.setName("effectRadioBtn");
        // CoreUI
                this.tblMain.putBindContents("mainQuery",new String[] {"BMCMessage.id"});

		
        this.pnlMain.setDividerLocation(160);		
        this.pnlMain.setOneTouchExpandable(true);
        // btnRejiggerTenancy
        this.btnRejiggerTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionRejiggerTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRejiggerTenancy.setText(resHelper.getString("btnRejiggerTenancy.text"));		
        this.btnRejiggerTenancy.setToolTipText(resHelper.getString("btnRejiggerTenancy.toolTipText"));		
        this.btnRejiggerTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
        // btnQuitTenancy
        this.btnQuitTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuitTenancy.setText(resHelper.getString("btnQuitTenancy.text"));		
        this.btnQuitTenancy.setToolTipText(resHelper.getString("btnQuitTenancy.toolTipText"));		
        this.btnQuitTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_logoutuser"));
        // btnHandTenancy
        this.btnHandTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHandTenancy.setText(resHelper.getString("btnHandTenancy.text"));		
        this.btnHandTenancy.setToolTipText(resHelper.getString("btnHandTenancy.toolTipText"));		
        this.btnHandTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_purviewlookupuser"));
        // btnKeepRoom
        this.btnKeepRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeepRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnKeepRoom.setText(resHelper.getString("btnKeepRoom.text"));		
        this.btnKeepRoom.setToolTipText(resHelper.getString("btnKeepRoom.toolTipText"));		
        this.btnKeepRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_downview"));
        // btnApplyTenancy
        this.btnApplyTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionApplyTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApplyTenancy.setText(resHelper.getString("btnApplyTenancy.text"));		
        this.btnApplyTenancy.setToolTipText(resHelper.getString("btnApplyTenancy.toolTipText"));		
        this.btnApplyTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_releasebyuser"));
        // btnContinuedTenancy
        this.btnContinuedTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionContinueTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnContinuedTenancy.setText(resHelper.getString("btnContinuedTenancy.text"));		
        this.btnContinuedTenancy.setToolTipText(resHelper.getString("btnContinuedTenancy.toolTipText"));		
        this.btnContinuedTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_releasebymdanduser"));
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(550);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // btnCancelTenancy
        this.btnCancelTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelTenancy.setToolTipText(resHelper.getString("btnCancelTenancy.toolTipText"));		
        this.btnCancelTenancy.setText(resHelper.getString("btnCancelTenancy.text"));		
        this.btnCancelTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_yhnoarrive"));
        // btnChangeName
        this.btnChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeName.setText(resHelper.getString("btnChangeName.text"));		
        this.btnChangeName.setToolTipText(resHelper.getString("btnChangeName.toolTipText"));		
        this.btnChangeName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_persondistribute"));
        // menuItemApplyTenancy
        this.menuItemApplyTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionApplyTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemApplyTenancy.setText(resHelper.getString("menuItemApplyTenancy.text"));		
        this.menuItemApplyTenancy.setToolTipText(resHelper.getString("menuItemApplyTenancy.toolTipText"));		
        this.menuItemApplyTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_releasebyuser"));
        // kDScrollPane1		
        this.kDScrollPane1.setPreferredSize(new Dimension(3,35));
        // btnChangeTenancy		
        this.btnChangeTenancy.setText(resHelper.getString("btnChangeTenancy.text"));		
        this.btnChangeTenancy.setToolTipText(resHelper.getString("btnChangeTenancy.toolTipText"));
        // menuItemChangeTenancy		
        this.menuItemChangeTenancy.setText(resHelper.getString("menuItemChangeTenancy.text"));		
        this.menuItemChangeTenancy.setToolTipText(resHelper.getString("menuItemChangeTenancy.toolTipText"));
        // menuItemContinuedTenancy
        this.menuItemContinuedTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionContinueTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContinuedTenancy.setText(resHelper.getString("menuItemContinuedTenancy.text"));		
        this.menuItemContinuedTenancy.setToolTipText(resHelper.getString("menuItemContinuedTenancy.toolTipText"));		
        this.menuItemContinuedTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_releasebymdanduser"));
        // menuItemRejiggerTenancy
        this.menuItemRejiggerTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionRejiggerTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRejiggerTenancy.setText(resHelper.getString("menuItemRejiggerTenancy.text"));		
        this.menuItemRejiggerTenancy.setToolTipText(resHelper.getString("menuItemRejiggerTenancy.toolTipText"));		
        this.menuItemRejiggerTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
        // menuItemQuitTenancy
        this.menuItemQuitTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuitTenancy.setText(resHelper.getString("menuItemQuitTenancy.text"));		
        this.menuItemQuitTenancy.setToolTipText(resHelper.getString("menuItemQuitTenancy.toolTipText"));		
        this.menuItemQuitTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_logoutuser"));
        // menuItemHandTenancy
        this.menuItemHandTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHandTenancy.setText(resHelper.getString("menuItemHandTenancy.text"));		
        this.menuItemHandTenancy.setToolTipText(resHelper.getString("menuItemHandTenancy.toolTipText"));		
        this.menuItemHandTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_purviewlookupuser"));
        // menuItemKeepRoom
        this.menuItemKeepRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeepRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemKeepRoom.setText(resHelper.getString("menuItemKeepRoom.text"));		
        this.menuItemKeepRoom.setToolTipText(resHelper.getString("menuItemKeepRoom.toolTipText"));		
        this.menuItemKeepRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_downview"));
        // menuItemCancelTenancy
        this.menuItemCancelTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelTenancy.setText(resHelper.getString("menuItemCancelTenancy.text"));		
        this.menuItemCancelTenancy.setToolTipText(resHelper.getString("menuItemCancelTenancy.toolTipText"));		
        this.menuItemCancelTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_yhnoarrive"));
        // menuItemChangeName
        this.menuItemChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeName.setText(resHelper.getString("menuItemChangeName.text"));		
        this.menuItemChangeName.setToolTipText(resHelper.getString("menuItemChangeName.toolTipText"));		
        this.menuItemChangeName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_persondistribute"));
        // btnReceiveBill
        this.btnReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReceiveBill.setText(resHelper.getString("btnReceiveBill.text"));		
        this.btnReceiveBill.setToolTipText(resHelper.getString("btnReceiveBill.toolTipText"));		
        this.btnReceiveBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // menuItemReceiveBill
        this.menuItemReceiveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReceiveBill.setText(resHelper.getString("menuItemReceiveBill.text"));		
        this.menuItemReceiveBill.setToolTipText(resHelper.getString("menuItemReceiveBill.toolTipText"));		
        this.menuItemReceiveBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
        // spnlTenancy		
        this.spnlTenancy.setOrientation(0);		
        this.spnlTenancy.setDividerLocation(480);
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // tblTenStat		
        this.tblTenStat.setFormatXml(resHelper.getString("tblTenStat.formatXml"));

        

        // sclPanel
        // kDPanel2		
        this.kDPanel2.setPreferredSize(new Dimension(10,28));		
        this.kDPanel2.setMinimumSize(new Dimension(22,22));
        // solidSideRadioBtn		
        this.solidSideRadioBtn.setText(resHelper.getString("solidSideRadioBtn.text"));
        this.solidSideRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    solidSideRadioBtn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // planeRadioBtn		
        this.planeRadioBtn.setText(resHelper.getString("planeRadioBtn.text"));		
        this.planeRadioBtn.setSelected(true);
        this.planeRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    planeRadioBtn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // imageGroup
        this.imageGroup.add(this.solidSideRadioBtn);
        this.imageGroup.add(this.planeRadioBtn);
        this.imageGroup.add(this.effectRadioBtn);
        // kDLabelContainerZoom		
        this.kDLabelContainerZoom.setBoundLabelText(resHelper.getString("kDLabelContainerZoom.boundLabelText"));		
        this.kDLabelContainerZoom.setBoundLabelLength(100);		
        this.kDLabelContainerZoom.setBoundLabelUnderline(true);
        // comboBoxZoom
        this.comboBoxZoom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBoxZoom_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // effectRadioBtn		
        this.effectRadioBtn.setText(resHelper.getString("effectRadioBtn.text"));
        this.effectRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    effectRadioBtn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


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
        pnlMain.add(kDSplitPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDSplitPane1
        kDSplitPane1.add(spnlTenancy, "left");
        kDSplitPane1.add(sclPanel, "right");
        //spnlTenancy
        spnlTenancy.add(kDPanel1, "top");
        spnlTenancy.add(tblTenStat, "bottom");
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblMain, BorderLayout.CENTER);
        kDPanel1.add(kDScrollPane1, BorderLayout.SOUTH);
        kDPanel1.add(kDPanel2, BorderLayout.NORTH);
        //kDPanel2
        kDPanel2.setLayout(null);        solidSideRadioBtn.setBounds(new Rectangle(86, 3, 71, 19));
        kDPanel2.add(solidSideRadioBtn, null);
        planeRadioBtn.setBounds(new Rectangle(5, 3, 71, 19));
        kDPanel2.add(planeRadioBtn, null);
        kDLabelContainerZoom.setBounds(new Rectangle(254, 1, 270, 19));
        kDPanel2.add(kDLabelContainerZoom, null);
        effectRadioBtn.setBounds(new Rectangle(167, 3, 71, 19));
        kDPanel2.add(effectRadioBtn, null);
        //kDLabelContainerZoom
        kDLabelContainerZoom.setBoundEditor(comboBoxZoom);

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
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemApplyTenancy);
        menuBiz.add(menuItemChangeTenancy);
        menuBiz.add(menuItemContinuedTenancy);
        menuBiz.add(menuItemRejiggerTenancy);
        menuBiz.add(menuItemQuitTenancy);
        menuBiz.add(menuItemHandTenancy);
        menuBiz.add(menuItemKeepRoom);
        menuBiz.add(menuItemCancelTenancy);
        menuBiz.add(menuItemChangeName);
        menuBiz.add(menuItemReceiveBill);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnApplyTenancy);
        this.toolBar.add(btnChangeTenancy);
        this.toolBar.add(btnContinuedTenancy);
        this.toolBar.add(btnRejiggerTenancy);
        this.toolBar.add(btnQuitTenancy);
        this.toolBar.add(btnHandTenancy);
        this.toolBar.add(btnKeepRoom);
        this.toolBar.add(btnCancelTenancy);
        this.toolBar.add(btnChangeName);
        this.toolBar.add(btnReceiveBill);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyControlUIHandler";
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
     * output solidSideRadioBtn_actionPerformed method
     */
    protected void solidSideRadioBtn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output planeRadioBtn_actionPerformed method
     */
    protected void planeRadioBtn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboBoxZoom_itemStateChanged method
     */
    protected void comboBoxZoom_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output effectRadioBtn_actionPerformed method
     */
    protected void effectRadioBtn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionReceiveBill_actionPerformed method
     */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelTenancy_actionPerformed method
     */
    public void actionCancelTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHandleTenancy_actionPerformed method
     */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApplyTenancy_actionPerformed method
     */
    public void actionApplyTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContinueTenancy_actionPerformed method
     */
    public void actionContinueTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRejiggerTenancy_actionPerformed method
     */
    public void actionRejiggerTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuitTenancy_actionPerformed method
     */
    public void actionQuitTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionKeepRoom_actionPerformed method
     */
    public void actionKeepRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeName_actionPerformed method
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionReceiveBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveBill() {
    	return false;
    }
	public RequestContext prepareActionCancelTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelTenancy() {
    	return false;
    }
	public RequestContext prepareActionHandleTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHandleTenancy() {
    	return false;
    }
	public RequestContext prepareActionApplyTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApplyTenancy() {
    	return false;
    }
	public RequestContext prepareActionContinueTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContinueTenancy() {
    	return false;
    }
	public RequestContext prepareActionRejiggerTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRejiggerTenancy() {
    	return false;
    }
	public RequestContext prepareActionQuitTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitTenancy() {
    	return false;
    }
	public RequestContext prepareActionKeepRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionKeepRoom() {
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

    /**
     * output ActionReceiveBill class
     */     
    protected class ActionReceiveBill extends ItemAction {     
    
        public ActionReceiveBill()
        {
            this(null);
        }

        public ActionReceiveBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));
            _tempStr = resHelper.getString("ActionReceiveBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionReceiveBill", "actionReceiveBill_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelTenancy class
     */     
    protected class ActionCancelTenancy extends ItemAction {     
    
        public ActionCancelTenancy()
        {
            this(null);
        }

        public ActionCancelTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionCancelTenancy", "actionCancelTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionHandleTenancy class
     */     
    protected class ActionHandleTenancy extends ItemAction {     
    
        public ActionHandleTenancy()
        {
            this(null);
        }

        public ActionHandleTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHandleTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHandleTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHandleTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionHandleTenancy", "actionHandleTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionApplyTenancy class
     */     
    protected class ActionApplyTenancy extends ItemAction {     
    
        public ActionApplyTenancy()
        {
            this(null);
        }

        public ActionApplyTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionApplyTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApplyTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApplyTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionApplyTenancy", "actionApplyTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionContinueTenancy class
     */     
    protected class ActionContinueTenancy extends ItemAction {     
    
        public ActionContinueTenancy()
        {
            this(null);
        }

        public ActionContinueTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionContinueTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContinueTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContinueTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionContinueTenancy", "actionContinueTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionRejiggerTenancy class
     */     
    protected class ActionRejiggerTenancy extends ItemAction {     
    
        public ActionRejiggerTenancy()
        {
            this(null);
        }

        public ActionRejiggerTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRejiggerTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRejiggerTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRejiggerTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionRejiggerTenancy", "actionRejiggerTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionQuitTenancy class
     */     
    protected class ActionQuitTenancy extends ItemAction {     
    
        public ActionQuitTenancy()
        {
            this(null);
        }

        public ActionQuitTenancy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionQuitTenancy", "actionQuitTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionKeepRoom class
     */     
    protected class ActionKeepRoom extends ItemAction {     
    
        public ActionKeepRoom()
        {
            this(null);
        }

        public ActionKeepRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionKeepRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeepRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeepRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionKeepRoom", "actionKeepRoom_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractTenancyControlUI.this, "ActionChangeName", "actionChangeName_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyControlUI");
    }




}