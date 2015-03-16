/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

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
public abstract class AbstractRenewRelateProgrammingListUI extends com.kingdee.eas.fdc.contract.client.ContractListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRenewRelateProgrammingListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddContent;
    protected javax.swing.JPopupMenu.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddContent;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContent;
    protected javax.swing.JToolBar.Separator separator2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStore;
    protected javax.swing.JToolBar.Separator separator3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConMove;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemConMove;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemStore;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiStore;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiStore;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSave;
    protected ActionProjectAttachment actionProjectAttachment = null;
    protected ActionViewContent actionViewContent = null;
    protected ActionAddContent actionAddContent = null;
    protected ActionStore actionStore = null;
    protected ActionConMove actionConMove = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionAntiStore actionAntiStore = null;
    protected ActionProgram actionProgram = null;
    protected ActionSave actionSave = null;
    /**
     * output class constructor
     */
    public AbstractRenewRelateProgrammingListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRenewRelateProgrammingListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.programming.app", "RenewRelateProgrammingBillQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCancel
        actionCancel.setEnabled(true);
        actionCancel.setDaemonRun(false);

        actionCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl -"));
        _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
        actionCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
        actionCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.NAME");
        actionCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelCancel
        actionCancelCancel.setEnabled(false);
        actionCancelCancel.setDaemonRun(false);

        actionCancelCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl +"));
        _tempStr = resHelper.getString("ActionCancelCancel.SHORT_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.LONG_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.NAME");
        actionCancelCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProjectAttachment
        this.actionProjectAttachment = new ActionProjectAttachment(this);
        getActionManager().registerAction("actionProjectAttachment", actionProjectAttachment);
         this.actionProjectAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddContent
        this.actionAddContent = new ActionAddContent(this);
        getActionManager().registerAction("actionAddContent", actionAddContent);
         this.actionAddContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStore
        this.actionStore = new ActionStore(this);
        getActionManager().registerAction("actionStore", actionStore);
         this.actionStore.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConMove
        this.actionConMove = new ActionConMove(this);
        getActionManager().registerAction("actionConMove", actionConMove);
         this.actionConMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiStore
        this.actionAntiStore = new ActionAntiStore(this);
        getActionManager().registerAction("actionAntiStore", actionAntiStore);
         this.actionAntiStore.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgram
        this.actionProgram = new ActionProgram(this);
        getActionManager().registerAction("actionProgram", actionProgram);
         this.actionProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnProjectAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JPopupMenu.Separator();
        this.menuItemAddContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator2 = new javax.swing.JToolBar.Separator();
        this.btnStore = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator3 = new javax.swing.JToolBar.Separator();
        this.btnConMove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemConMove = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProjectAttachment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemStore = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAntiStore = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAntiStore = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnProjectAttachment.setName("btnProjectAttachment");
        this.btnViewContent.setName("btnViewContent");
        this.btnAddContent.setName("btnAddContent");
        this.separator1.setName("separator1");
        this.menuItemAddContent.setName("menuItemAddContent");
        this.menuItemViewContent.setName("menuItemViewContent");
        this.separator2.setName("separator2");
        this.btnStore.setName("btnStore");
        this.separator3.setName("separator3");
        this.btnConMove.setName("btnConMove");
        this.menuItemConMove.setName("menuItemConMove");
        this.menuItemProjectAttachment.setName("menuItemProjectAttachment");
        this.menuItemStore.setName("menuItemStore");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.btnViewContract.setName("btnViewContract");
        this.menuItemAntiStore.setName("menuItemAntiStore");
        this.btnAntiStore.setName("btnAntiStore");
        this.btnProgram.setName("btnProgram");
        this.btnSave.setName("btnSave");
        this.menuItemSave.setName("menuItemSave");
        // CoreUI		
        this.kDSeparator1.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"period.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"contractType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"currency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"originalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"programmingContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period.number}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{contractType.name}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{programmingContract}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","name","number","contractType.name","currency.name","originalAmount","amount","signDate","programmingContract.name"});

		
        this.btnAddNew.setVisible(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnLocate.setVisible(false);		
        this.btnQuery.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.kDSeparator2.setVisible(false);		
        this.menuEdit.setEnabled(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemView.setVisible(false);		
        this.menuItemView.setEnabled(false);		
        this.menuItemLocate.setEnabled(false);		
        this.menuItemLocate.setVisible(false);		
        this.menuItemQuery.setEnabled(false);		
        this.menuItemQuery.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.separatorFile1.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.MenuItemAttachment.setEnabled(false);		
        this.menuBiz.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.menuItemCancelCancel.setText(resHelper.getString("menuItemCancelCancel.text"));		
        this.menuItemCancelCancel.setToolTipText(resHelper.getString("menuItemCancelCancel.toolTipText"));		
        this.menuItemCancel.setText(resHelper.getString("menuItemCancel.text"));		
        this.menuItemCancel.setToolTipText(resHelper.getString("menuItemCancel.toolTipText"));		
        this.menuItemCancel.setVisible(true);		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));		
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));		
        this.separatorView1.setVisible(false);		
        this.separatorView1.setEnabled(false);		
        this.btnQueryScheme.setVisible(false);		
        this.kDSeparator5.setVisible(false);		
        this.kDSeparator5.setEnabled(false);		
        this.menuWorkFlow.setVisible(false);		
        this.menuWorkFlow.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.kDSplitPane2.setDividerLocation(600);		
        this.kDSplitPane2.setOneTouchExpandable(false);		
        this.kDSplitPane2.setDividerSize(0);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnSetRespite.setVisible(false);		
        this.btnCancelRespite.setVisible(false);
        // btnProjectAttachment
        this.btnProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectAttachment.setText(resHelper.getString("btnProjectAttachment.text"));		
        this.btnProjectAttachment.setToolTipText(resHelper.getString("btnProjectAttachment.toolTipText"));		
        this.btnProjectAttachment.setVisible(false);
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));		
        this.btnViewContent.setVisible(false);
        // btnAddContent
        this.btnAddContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddContent.setText(resHelper.getString("btnAddContent.text"));		
        this.btnAddContent.setToolTipText(resHelper.getString("btnAddContent.toolTipText"));		
        this.btnAddContent.setVisible(false);
        // separator1
        // menuItemAddContent
        this.menuItemAddContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddContent.setText(resHelper.getString("menuItemAddContent.text"));		
        this.menuItemAddContent.setToolTipText(resHelper.getString("menuItemAddContent.toolTipText"));		
        this.menuItemAddContent.setMnemonic(65);		
        this.menuItemAddContent.setVisible(false);
        // menuItemViewContent
        this.menuItemViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContent.setText(resHelper.getString("menuItemViewContent.text"));		
        this.menuItemViewContent.setToolTipText(resHelper.getString("menuItemViewContent.toolTipText"));		
        this.menuItemViewContent.setEnabled(false);		
        this.menuItemViewContent.setVisible(false);
        // separator2
        // btnStore
        this.btnStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStore.setText(resHelper.getString("btnStore.text"));		
        this.btnStore.setToolTipText(resHelper.getString("btnStore.toolTipText"));		
        this.btnStore.setVisible(false);
        // separator3
        // btnConMove
        this.btnConMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionConMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConMove.setText(resHelper.getString("btnConMove.text"));		
        this.btnConMove.setToolTipText(resHelper.getString("btnConMove.toolTipText"));		
        this.btnConMove.setVisible(false);
        // menuItemConMove
        this.menuItemConMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionConMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemConMove.setText(resHelper.getString("menuItemConMove.text"));		
        this.menuItemConMove.setToolTipText(resHelper.getString("menuItemConMove.toolTipText"));
        // menuItemProjectAttachment
        this.menuItemProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProjectAttachment.setText(resHelper.getString("menuItemProjectAttachment.text"));		
        this.menuItemProjectAttachment.setToolTipText(resHelper.getString("menuItemProjectAttachment.toolTipText"));		
        this.menuItemProjectAttachment.setMnemonic(70);		
        this.menuItemProjectAttachment.setVisible(false);
        // menuItemStore
        this.menuItemStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemStore.setText(resHelper.getString("menuItemStore.text"));		
        this.menuItemStore.setToolTipText(resHelper.getString("menuItemStore.toolTipText"));		
        this.menuItemStore.setMnemonic(75);
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));		
        this.menuItemViewContract.setVisible(false);
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));		
        this.btnViewContract.setVisible(false);
        // menuItemAntiStore
        this.menuItemAntiStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiStore.setText(resHelper.getString("menuItemAntiStore.text"));		
        this.menuItemAntiStore.setMnemonic(76);
        // btnAntiStore
        this.btnAntiStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiStore.setText(resHelper.getString("btnAntiStore.text"));		
        this.btnAntiStore.setToolTipText(resHelper.getString("btnAntiStore.toolTipText"));		
        this.btnAntiStore.setMnemonic(76);		
        this.btnAntiStore.setVisible(false);
        // btnProgram
        this.btnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgram.setText(resHelper.getString("btnProgram.text"));		
        this.btnProgram.setVisible(false);
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // menuItemSave
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));		
        this.menuItemSave.setMnemonic(83);
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
        pnlSplit.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(pnlSplit, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlSplit
        pnlSplit.add(pnlLeftTree, "left");
        pnlSplit.add(pnlRight, "right");
        //pnlLeftTree
        pnlLeftTree.setLayout(new KDLayout());
        pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(0, 0, 249, 608));        kDSplitPane1.setBounds(new Rectangle(-1, 3, 250, 606));
        pnlLeftTree.add(kDSplitPane1, new KDLayout.Constraints(-1, 3, 250, 606, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(contProject, "top");
        kDSplitPane1.add(contContrType, "bottom");
        //contProject
contProject.getContentPane().setLayout(new BorderLayout(0, 0));        contProject.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeProject, null);
        //contContrType
contContrType.getContentPane().setLayout(new BorderLayout(0, 0));        contContrType.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeContractType, null);
        //pnlRight
        pnlRight.setLayout(new KDLayout());
        pnlRight.putClientProperty("OriginalBounds", new Rectangle(0, 0, 732, 608));        kDSplitPane2.setBounds(new Rectangle(0, 1, 733, 608));
        pnlRight.add(kDSplitPane2, new KDLayout.Constraints(0, 1, 733, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane2
        kDSplitPane2.add(contContrList, "top");
        //contContrList
contContrList.getContentPane().setLayout(new BorderLayout(0, 0));        contContrList.getContentPane().add(tblMain, BorderLayout.CENTER);

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
        this.menuBar.add(separator1);
        //menuFile
        menuFile.add(menuItemSave);
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemProjectAttachment);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuItemAddContent);
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
        menuView.add(menuItemViewContent);
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
        menuView.add(menuItemViewContract);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSetRespite);
        menuBiz.add(menuItemCancelRespite);
        menuBiz.add(menuItemConMove);
        menuBiz.add(menuItemStore);
        menuBiz.add(menuItemAntiStore);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separator3);
        this.toolBar.add(btnStore);
        this.toolBar.add(btnAntiStore);
        this.toolBar.add(separator2);
        this.toolBar.add(btnSetRespite);
        this.toolBar.add(btnCancelRespite);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnAddContent);
        this.toolBar.add(btnConMove);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnProgram);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.RenewRelateProgrammingListUIHandler";
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
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("contractType.name"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("period.number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("programmingContract.name"));
        sic.add(new SelectorItemInfo("programmingContract.id"));
        return sic;
    }        
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }
    	

    /**
     * output actionCancelCancel_actionPerformed method
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionProjectAttachment_actionPerformed method
     */
    public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddContent_actionPerformed method
     */
    public void actionAddContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionStore_actionPerformed method
     */
    public void actionStore_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConMove_actionPerformed method
     */
    public void actionConMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiStore_actionPerformed method
     */
    public void actionAntiStore_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProgram_actionPerformed method
     */
    public void actionProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }
	public RequestContext prepareActionCancelCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancelCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelCancel() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionProjectAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProjectAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }
	public RequestContext prepareActionAddContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddContent() {
    	return false;
    }
	public RequestContext prepareActionStore(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStore() {
    	return false;
    }
	public RequestContext prepareActionConMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConMove() {
    	return false;
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }
	public RequestContext prepareActionAntiStore(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiStore() {
    	return false;
    }
	public RequestContext prepareActionProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgram() {
    	return false;
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

    /**
     * output ActionProjectAttachment class
     */     
    protected class ActionProjectAttachment extends ItemAction {     
    
        public ActionProjectAttachment()
        {
            this(null);
        }

        public ActionProjectAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift A"));
            _tempStr = resHelper.getString("ActionProjectAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionProjectAttachment", "actionProjectAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContent class
     */     
    protected class ActionViewContent extends ItemAction {     
    
        public ActionViewContent()
        {
            this(null);
        }

        public ActionViewContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
            _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output ActionAddContent class
     */     
    protected class ActionAddContent extends ItemAction {     
    
        public ActionAddContent()
        {
            this(null);
        }

        public ActionAddContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionAddContent", "actionAddContent_actionPerformed", e);
        }
    }

    /**
     * output ActionStore class
     */     
    protected class ActionStore extends ItemAction {     
    
        public ActionStore()
        {
            this(null);
        }

        public ActionStore(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift K"));
            _tempStr = resHelper.getString("ActionStore.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStore.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStore.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionStore", "actionStore_actionPerformed", e);
        }
    }

    /**
     * output ActionConMove class
     */     
    protected class ActionConMove extends ItemAction {     
    
        public ActionConMove()
        {
            this(null);
        }

        public ActionConMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shif M"));
            _tempStr = resHelper.getString("ActionConMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionConMove", "actionConMove_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift T"));
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiStore class
     */     
    protected class ActionAntiStore extends ItemAction {     
    
        public ActionAntiStore()
        {
            this(null);
        }

        public ActionAntiStore(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift L"));
            _tempStr = resHelper.getString("ActionAntiStore.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiStore.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiStore.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionAntiStore", "actionAntiStore_actionPerformed", e);
        }
    }

    /**
     * output ActionProgram class
     */     
    protected class ActionProgram extends ItemAction {     
    
        public ActionProgram()
        {
            this(null);
        }

        public ActionProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionProgram", "actionProgram_actionPerformed", e);
        }
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
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
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
            innerActionPerformed("eas", AbstractRenewRelateProgrammingListUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "RenewRelateProgrammingListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}