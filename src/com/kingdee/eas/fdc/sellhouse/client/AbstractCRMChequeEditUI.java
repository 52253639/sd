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
public abstract class AbstractCRMChequeEditUI extends com.kingdee.eas.fdc.sellhouse.client.CRMChequeBookInUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCRMChequeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCheque;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelDetail;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtblMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMakeInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPicke;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVC;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReback;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInvalid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeInvoice;
    protected com.kingdee.eas.fdc.sellhouse.CRMChequeInfo editData = null;
    protected ActionMakeInvoice actionMakeInvoice = null;
    protected ActionVC actionVC = null;
    protected ActionReback actionReback = null;
    protected ActionInvalid actionInvalid = null;
    protected ActionCancelInvoice actionCancelInvoice = null;
    protected ActionPick actionPick = null;
    protected ActionChangeInvoice actionChangeInvoice = null;
    /**
     * output class constructor
     */
    public AbstractCRMChequeEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCRMChequeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionMakeInvoice
        this.actionMakeInvoice = new ActionMakeInvoice(this);
        getActionManager().registerAction("actionMakeInvoice", actionMakeInvoice);
         this.actionMakeInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVC
        this.actionVC = new ActionVC(this);
        getActionManager().registerAction("actionVC", actionVC);
         this.actionVC.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReback
        this.actionReback = new ActionReback(this);
        getActionManager().registerAction("actionReback", actionReback);
         this.actionReback.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvalid
        this.actionInvalid = new ActionInvalid(this);
        getActionManager().registerAction("actionInvalid", actionInvalid);
         this.actionInvalid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelInvoice
        this.actionCancelInvoice = new ActionCancelInvoice(this);
        getActionManager().registerAction("actionCancelInvoice", actionCancelInvoice);
         this.actionCancelInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPick
        this.actionPick = new ActionPick(this);
        getActionManager().registerAction("actionPick", actionPick);
         this.actionPick.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeInvoice
        this.actionChangeInvoice = new ActionChangeInvoice(this);
        getActionManager().registerAction("actionChangeInvoice", actionChangeInvoice);
         this.actionChangeInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelCheque = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelDetail = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnMakeInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPicke = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVC = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReback = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInvalid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.panelCheque.setName("panelCheque");
        this.panelDetail.setName("panelDetail");
        this.kdtblMain.setName("kdtblMain");
        this.btnMakeInvoice.setName("btnMakeInvoice");
        this.btnPicke.setName("btnPicke");
        this.btnVC.setName("btnVC");
        this.btnReback.setName("btnReback");
        this.btnInvalid.setName("btnInvalid");
        this.btnCancelInvoice.setName("btnCancelInvoice");
        this.btnChangeInvoice.setName("btnChangeInvoice");
        // CoreUI
        this.txtCodeRule.addFocusListener(new java.awt.event.FocusAdapter() {
        });
        // kDTabbedPane1
        // panelCheque
        // panelDetail
        // kdtblMain
		String kdtblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"batchOfCheque\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"chequeState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"chequeType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"resume\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"CAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"writerOffer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"writerOfferDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"picker\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"pickDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"keeper\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"keeperOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"verifyState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"verifyOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"verifyDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"verifier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{batchOfCheque}</t:Cell><t:Cell>$Resource{Number}</t:Cell><t:Cell>$Resource{chequeState}</t:Cell><t:Cell>$Resource{chequeType}</t:Cell><t:Cell>$Resource{resume}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{CAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{writerOffer}</t:Cell><t:Cell>$Resource{writerOfferDate}</t:Cell><t:Cell>$Resource{picker}</t:Cell><t:Cell>$Resource{pickDate}</t:Cell><t:Cell>$Resource{keeper}</t:Cell><t:Cell>$Resource{keeperOrg}</t:Cell><t:Cell>$Resource{verifyState}</t:Cell><t:Cell>$Resource{verifyOrg}</t:Cell><t:Cell>$Resource{verifyDate}</t:Cell><t:Cell>$Resource{verifier}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtblMain.setFormatXml(resHelper.translateString("kdtblMain",kdtblMainStrXML));
        this.kdtblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnMakeInvoice
        this.btnMakeInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionMakeInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMakeInvoice.setText(resHelper.getString("btnMakeInvoice.text"));		
        this.btnMakeInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_createcredence"));
        this.btnMakeInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnMakeInvoice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnPicke
        this.btnPicke.setAction((IItemAction)ActionProxyFactory.getProxy(actionPick, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPicke.setText(resHelper.getString("btnPicke.text"));		
        this.btnPicke.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_alreadycollate"));
        // btnVC
        this.btnVC.setAction((IItemAction)ActionProxyFactory.getProxy(actionVC, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVC.setText(resHelper.getString("btnVC.text"));		
        this.btnVC.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deletecredence"));
        // btnReback
        this.btnReback.setAction((IItemAction)ActionProxyFactory.getProxy(actionReback, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReback.setText(resHelper.getString("btnReback.text"));		
        this.btnReback.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_conversionsave"));
        // btnInvalid
        this.btnInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInvalid.setText(resHelper.getString("btnInvalid.text"));		
        this.btnInvalid.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cashagio"));
        // btnCancelInvoice
        this.btnCancelInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelInvoice.setText(resHelper.getString("btnCancelInvoice.text"));		
        this.btnCancelInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelcollocate"));
        // btnChangeInvoice
        this.btnChangeInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeInvoice.setText(resHelper.getString("btnChangeInvoice.text"));		
        this.btnChangeInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importfromzz"));
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
        this.setBounds(new Rectangle(10, 10, 880, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 880, 600));
        kDTabbedPane1.setBounds(new Rectangle(10, 10, 862, 581));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 10, 862, 581, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(panelCheque, resHelper.getString("panelCheque.constraints"));
        kDTabbedPane1.add(panelDetail, resHelper.getString("panelDetail.constraints"));
        //panelCheque
        panelCheque.setLayout(new KDLayout());
        panelCheque.putClientProperty("OriginalBounds", new Rectangle(0, 0, 861, 548));        contCreator.setBounds(new Rectangle(294, 102, 270, 19));
        panelCheque.add(contCreator, new KDLayout.Constraints(294, 102, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(577, 100, 270, 19));
        panelCheque.add(contCreateTime, new KDLayout.Constraints(577, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(13, 133, 833, 19));
        panelCheque.add(contDescription, new KDLayout.Constraints(13, 133, 833, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contChequeType.setBounds(new Rectangle(294, 11, 270, 19));
        panelCheque.add(contChequeType, new KDLayout.Constraints(294, 11, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(13, 42, 270, 19));
        panelCheque.add(contCurrency, new KDLayout.Constraints(13, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contKeeper.setBounds(new Rectangle(294, 71, 270, 19));
        panelCheque.add(contKeeper, new KDLayout.Constraints(294, 71, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contKeepOrgUnit.setBounds(new Rectangle(577, 69, 270, 19));
        panelCheque.add(contKeepOrgUnit, new KDLayout.Constraints(577, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLimitAmount.setBounds(new Rectangle(577, 11, 270, 19));
        panelCheque.add(contLimitAmount, new KDLayout.Constraints(577, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCount.setBounds(new Rectangle(294, 41, 270, 19));
        panelCheque.add(contCount, new KDLayout.Constraints(294, 41, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStartNum.setBounds(new Rectangle(577, 40, 270, 19));
        panelCheque.add(contStartNum, new KDLayout.Constraints(577, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCodeRule.setBounds(new Rectangle(13, 73, 269, 19));
        panelCheque.add(contCodeRule, new KDLayout.Constraints(13, 73, 269, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtRemark.setBounds(new Rectangle(13, 160, 833, 376));
        panelCheque.add(txtRemark, new KDLayout.Constraints(13, 160, 833, 376, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contChequeBatch.setBounds(new Rectangle(13, 11, 270, 19));
        panelCheque.add(contChequeBatch, new KDLayout.Constraints(13, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(13, 104, 270, 19));
        panelCheque.add(contSellProject, new KDLayout.Constraints(13, 104, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contChequeType
        contChequeType.setBoundEditor(comboChequeType);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contKeeper
        contKeeper.setBoundEditor(f7Keeper);
        //contKeepOrgUnit
        contKeepOrgUnit.setBoundEditor(f7KeepOrgUnit);
        //contLimitAmount
        contLimitAmount.setBoundEditor(txtLimitAmount);
        //contCount
        contCount.setBoundEditor(spinCount);
        //contStartNum
        contStartNum.setBoundEditor(spinStartNum);
        //contCodeRule
        contCodeRule.setBoundEditor(txtCodeRule);
        //contChequeBatch
        contChequeBatch.setBoundEditor(txtChequeBatch);
        //contSellProject
        contSellProject.setBoundEditor(F7SellProject);
        //panelDetail
        panelDetail.setLayout(new KDLayout());
        panelDetail.putClientProperty("OriginalBounds", new Rectangle(0, 0, 861, 548));        kdtblMain.setBounds(new Rectangle(10, 10, 836, 528));
        panelDetail.add(kdtblMain, new KDLayout.Constraints(10, 10, 836, 528, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnMakeInvoice);
        this.toolBar.add(btnPicke);
        this.toolBar.add(btnVC);
        this.toolBar.add(btnReback);
        this.toolBar.add(btnInvalid);
        this.toolBar.add(btnCancelInvoice);
        this.toolBar.add(btnChangeInvoice);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CRMChequeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CRMChequeInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("resume", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chequeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keeper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keepOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("limitAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("numberOfCheque", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("serialNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("codeRule", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chequeBathNumber", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kdtblMain_tableClicked method
     */
    protected void kdtblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnMakeInvoice_actionPerformed method
     */
    protected void btnMakeInvoice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionMakeInvoice_actionPerformed method
     */
    public void actionMakeInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVC_actionPerformed method
     */
    public void actionVC_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReback_actionPerformed method
     */
    public void actionReback_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvalid_actionPerformed method
     */
    public void actionInvalid_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelInvoice_actionPerformed method
     */
    public void actionCancelInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPick_actionPerformed method
     */
    public void actionPick_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeInvoice_actionPerformed method
     */
    public void actionChangeInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionMakeInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMakeInvoice() {
    	return false;
    }
	public RequestContext prepareActionVC(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVC() {
    	return false;
    }
	public RequestContext prepareActionReback(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReback() {
    	return false;
    }
	public RequestContext prepareActionInvalid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInvalid() {
    	return false;
    }
	public RequestContext prepareActionCancelInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelInvoice() {
    	return false;
    }
	public RequestContext prepareActionPick(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPick() {
    	return false;
    }
	public RequestContext prepareActionChangeInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeInvoice() {
    	return false;
    }

    /**
     * output ActionMakeInvoice class
     */     
    protected class ActionMakeInvoice extends ItemAction {     
    
        public ActionMakeInvoice()
        {
            this(null);
        }

        public ActionMakeInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMakeInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMakeInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMakeInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionMakeInvoice", "actionMakeInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionVC class
     */     
    protected class ActionVC extends ItemAction {     
    
        public ActionVC()
        {
            this(null);
        }

        public ActionVC(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionVC.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVC.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVC.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionVC", "actionVC_actionPerformed", e);
        }
    }

    /**
     * output ActionReback class
     */     
    protected class ActionReback extends ItemAction {     
    
        public ActionReback()
        {
            this(null);
        }

        public ActionReback(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReback.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReback.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReback.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionReback", "actionReback_actionPerformed", e);
        }
    }

    /**
     * output ActionInvalid class
     */     
    protected class ActionInvalid extends ItemAction {     
    
        public ActionInvalid()
        {
            this(null);
        }

        public ActionInvalid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInvalid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvalid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvalid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionInvalid", "actionInvalid_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelInvoice class
     */     
    protected class ActionCancelInvoice extends ItemAction {     
    
        public ActionCancelInvoice()
        {
            this(null);
        }

        public ActionCancelInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionCancelInvoice", "actionCancelInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionPick class
     */     
    protected class ActionPick extends ItemAction {     
    
        public ActionPick()
        {
            this(null);
        }

        public ActionPick(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPick.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPick.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPick.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionPick", "actionPick_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeInvoice class
     */     
    protected class ActionChangeInvoice extends ItemAction {     
    
        public ActionChangeInvoice()
        {
            this(null);
        }

        public ActionChangeInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCRMChequeEditUI.this, "ActionChangeInvoice", "actionChangeInvoice_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CRMChequeEditUI");
    }




}