/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractQuotingPriceListUI extends com.kingdee.eas.fdc.invite.client.InviteDetailListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuotingPriceListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseSupplier;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportOriginalPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportModifyPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectBidder;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssess;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCompositor;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnKeyItemSet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReportExport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnBid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInviteExecuteInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportPrice;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenu2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuEditModify;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenu1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuViewModify;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSelectBidder;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssess;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCompesitor;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemItemSetting;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuExport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem5;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem6;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem7;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem8;
    protected ActionSelectBidder actionSelectBidder = null;
    protected ActionAssess actionAssess = null;
    protected ActionCompositor actionCompositor = null;
    protected ActionReportExport actionReportExport = null;
    protected ActionOverRangeQuotingPrice actionOverRangeQuotingPrice = null;
    protected ActionQuotingPriceGather actionQuotingPriceGather = null;
    protected ActionKeyItemAnalyse actionKeyItemAnalyse = null;
    protected ActionKeyItemSet actionKeyItemSet = null;
    protected ActionPriceModify actionPriceModify = null;
    protected ActionAnalyseListUI actionAnalyseListUI = null;
    protected ActionImportListUI actionImportListUI = null;
    protected ActionQuotingPriceSum actionQuotingPriceSum = null;
    protected ActionUnBid actionUnBid = null;
    protected ActionExportPrice actionExportPrice = null;
    protected ActionImportModifyPrice actionImportModifyPrice = null;
    protected ActionInviteExecuteInfo actionInviteExecuteInfo = null;
    /**
     * output class constructor
     */
    public AbstractQuotingPriceListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuotingPriceListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.invite.app", "NewListingQuery");
        //actionView
        String _tempStr = null;
        actionView.setEnabled(true);
        actionView.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionView.SHORT_DESCRIPTION");
        actionView.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.LONG_DESCRIPTION");
        actionView.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.NAME");
        actionView.putValue(ItemAction.NAME, _tempStr);
         this.actionView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionEdit
        actionEdit.setEnabled(true);
        actionEdit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSelectBidder
        this.actionSelectBidder = new ActionSelectBidder(this);
        getActionManager().registerAction("actionSelectBidder", actionSelectBidder);
         this.actionSelectBidder.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssess
        this.actionAssess = new ActionAssess(this);
        getActionManager().registerAction("actionAssess", actionAssess);
         this.actionAssess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCompositor
        this.actionCompositor = new ActionCompositor(this);
        getActionManager().registerAction("actionCompositor", actionCompositor);
         this.actionCompositor.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReportExport
        this.actionReportExport = new ActionReportExport(this);
        getActionManager().registerAction("actionReportExport", actionReportExport);
         this.actionReportExport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOverRangeQuotingPrice
        this.actionOverRangeQuotingPrice = new ActionOverRangeQuotingPrice(this);
        getActionManager().registerAction("actionOverRangeQuotingPrice", actionOverRangeQuotingPrice);
         this.actionOverRangeQuotingPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuotingPriceGather
        this.actionQuotingPriceGather = new ActionQuotingPriceGather(this);
        getActionManager().registerAction("actionQuotingPriceGather", actionQuotingPriceGather);
         this.actionQuotingPriceGather.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionKeyItemAnalyse
        this.actionKeyItemAnalyse = new ActionKeyItemAnalyse(this);
        getActionManager().registerAction("actionKeyItemAnalyse", actionKeyItemAnalyse);
         this.actionKeyItemAnalyse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionKeyItemSet
        this.actionKeyItemSet = new ActionKeyItemSet(this);
        getActionManager().registerAction("actionKeyItemSet", actionKeyItemSet);
         this.actionKeyItemSet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPriceModify
        this.actionPriceModify = new ActionPriceModify(this);
        getActionManager().registerAction("actionPriceModify", actionPriceModify);
         this.actionPriceModify.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAnalyseListUI
        this.actionAnalyseListUI = new ActionAnalyseListUI(this);
        getActionManager().registerAction("actionAnalyseListUI", actionAnalyseListUI);
         this.actionAnalyseListUI.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportListUI
        this.actionImportListUI = new ActionImportListUI(this);
        getActionManager().registerAction("actionImportListUI", actionImportListUI);
         this.actionImportListUI.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuotingPriceSum
        this.actionQuotingPriceSum = new ActionQuotingPriceSum(this);
        getActionManager().registerAction("actionQuotingPriceSum", actionQuotingPriceSum);
         this.actionQuotingPriceSum.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnBid
        this.actionUnBid = new ActionUnBid(this);
        getActionManager().registerAction("actionUnBid", actionUnBid);
         this.actionUnBid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportPrice
        this.actionExportPrice = new ActionExportPrice(this);
        getActionManager().registerAction("actionExportPrice", actionExportPrice);
         this.actionExportPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportModifyPrice
        this.actionImportModifyPrice = new ActionImportModifyPrice(this);
        getActionManager().registerAction("actionImportModifyPrice", actionImportModifyPrice);
         this.actionImportModifyPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInviteExecuteInfo
        this.actionInviteExecuteInfo = new ActionInviteExecuteInfo(this);
        getActionManager().registerAction("actionInviteExecuteInfo", actionInviteExecuteInfo);
         this.actionInviteExecuteInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnChooseSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportOriginalPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportModifyPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSelectBidder = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAssess = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCompositor = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnKeyItemSet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReportExport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnBid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInviteExecuteInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemExportPrice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenu2 = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuEditModify = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenu1 = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuViewModify = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSelectBidder = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAssess = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCompesitor = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemItemSetting = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuExport = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.kDMenuItem5 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuItem6 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuItem7 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuItem8 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnChooseSupplier.setName("btnChooseSupplier");
        this.btnImportOriginalPrice.setName("btnImportOriginalPrice");
        this.btnImportModifyPrice.setName("btnImportModifyPrice");
        this.btnImportPrice.setName("btnImportPrice");
        this.btnExportPrice.setName("btnExportPrice");
        this.btnSelectBidder.setName("btnSelectBidder");
        this.btnAssess.setName("btnAssess");
        this.btnCompositor.setName("btnCompositor");
        this.btnKeyItemSet.setName("btnKeyItemSet");
        this.btnReportExport.setName("btnReportExport");
        this.btnUnBid.setName("btnUnBid");
        this.btnInviteExecuteInfo.setName("btnInviteExecuteInfo");
        this.menuItemExportPrice.setName("menuItemExportPrice");
        this.kDMenu2.setName("kDMenu2");
        this.menuEditModify.setName("menuEditModify");
        this.kDMenu1.setName("kDMenu1");
        this.menuViewModify.setName("menuViewModify");
        this.menuItemSelectBidder.setName("menuItemSelectBidder");
        this.menuItemAssess.setName("menuItemAssess");
        this.menuItemCompesitor.setName("menuItemCompesitor");
        this.menuItemItemSetting.setName("menuItemItemSetting");
        this.menuExport.setName("menuExport");
        this.kDMenuItem5.setName("kDMenuItem5");
        this.kDMenuItem6.setName("kDMenuItem6");
        this.kDMenuItem7.setName("kDMenuItem7");
        this.kDMenuItem8.setName("kDMenuItem8");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"inviteType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"inviteAuditState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"curProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"inviteproject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appraise.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"version\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isLastVersion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{inviteType.name}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{inviteAuditState}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{inviteproject}</t:Cell><t:Cell>$Resource{appraise.id}</t:Cell><t:Cell>$Resource{version}</t:Cell><t:Cell>$Resource{isLastVersion}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","inviteType.name","state","inviteAuditState","number","name","curProject.name","creator.name","createTime","inviteProject.name","appraise.id","version","isLastVersion"});

		
        this.btnLocate.setVisible(false);		
        this.btnQuery.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setMnemonic(0);		
        this.menuItemView.setText(resHelper.getString("menuItemView.text"));		
        this.menuItemView.setMnemonic(0);		
        this.menuItemLocate.setVisible(false);		
        this.menuItemQuery.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.separatorView1.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.btnVoucher.setVisible(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnCreateTo.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemCreateTo.setVisible(false);		
        this.menuItemCopyTo.setVisible(false);		
        this.kDSeparator5.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuWorkFlow.setVisible(false);		
        this.menuItemVoucher.setVisible(false);		
        this.menuItemDelVoucher.setVisible(false);		
        this.separatorFW4.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.pnlSplit.setDividerLocation(250);		
        this.contMainBill.setEnableActive(false);		
        this.contDetalBill.setTitle(resHelper.getString("contDetalBill.title"));		
        this.contDetalBill.setEnableActive(false);
		String tblDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supplier.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"supplier.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"inviteListing.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"totoalPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"creator.createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"hasEffected\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supplier.number}</t:Cell><t:Cell>$Resource{supplier.name}</t:Cell><t:Cell>$Resource{inviteListing.name}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{totoalPrice}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{creator.createTime}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{hasEffected}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblDetail.setFormatXml(resHelper.translateString("tblDetail",tblDetailStrXML));
        this.tblDetail.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblDetail_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblDetail.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblDetail_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnChooseSupplier
        this.btnChooseSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChooseSupplier.setText(resHelper.getString("btnChooseSupplier.text"));
        // btnImportOriginalPrice
        this.btnImportOriginalPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportOriginalPrice.setText(resHelper.getString("btnImportOriginalPrice.text"));
        // btnImportModifyPrice
        this.btnImportModifyPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportModifyPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportModifyPrice.setText(resHelper.getString("btnImportModifyPrice.text"));
        // btnImportPrice		
        this.btnImportPrice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.btnImportPrice.setText(resHelper.getString("btnImportPrice.text"));		
        this.btnImportPrice.setVisible(false);
        // btnExportPrice
        this.btnExportPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportPrice.setText(resHelper.getString("btnExportPrice.text"));
        // btnSelectBidder
        this.btnSelectBidder.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectBidder, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelectBidder.setText(resHelper.getString("btnSelectBidder.text"));
        // btnAssess
        this.btnAssess.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssess.setText(resHelper.getString("btnAssess.text"));
        // btnCompositor
        this.btnCompositor.setAction((IItemAction)ActionProxyFactory.getProxy(actionCompositor, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCompositor.setText(resHelper.getString("btnCompositor.text"));
        // btnKeyItemSet
        this.btnKeyItemSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeyItemSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnKeyItemSet.setText(resHelper.getString("btnKeyItemSet.text"));		
        this.btnKeyItemSet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // btnReportExport
        this.btnReportExport.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuotingPriceGather, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReportExport.setText(resHelper.getString("btnReportExport.text"));		
        this.btnReportExport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // btnUnBid
        this.btnUnBid.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnBid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnBid.setText(resHelper.getString("btnUnBid.text"));		
        this.btnUnBid.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelcollocate"));
        // btnInviteExecuteInfo
        this.btnInviteExecuteInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionInviteExecuteInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInviteExecuteInfo.setText(resHelper.getString("btnInviteExecuteInfo.text"));		
        this.btnInviteExecuteInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
        // menuItemExportPrice
        this.menuItemExportPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExportPrice.setText(resHelper.getString("menuItemExportPrice.text"));
        // kDMenu2		
        this.kDMenu2.setText(resHelper.getString("kDMenu2.text"));
        // menuEditModify
        this.menuEditModify.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuEditModify.setText(resHelper.getString("menuEditModify.text"));		
        this.menuEditModify.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // kDMenu1		
        this.kDMenu1.setText(resHelper.getString("kDMenu1.text"));
        // menuViewModify
        this.menuViewModify.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuViewModify.setText(resHelper.getString("menuViewModify.text"));		
        this.menuViewModify.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // menuItemSelectBidder
        this.menuItemSelectBidder.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectBidder, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSelectBidder.setText(resHelper.getString("menuItemSelectBidder.text"));
        // menuItemAssess
        this.menuItemAssess.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssess.setText(resHelper.getString("menuItemAssess.text"));
        // menuItemCompesitor
        this.menuItemCompesitor.setAction((IItemAction)ActionProxyFactory.getProxy(actionCompositor, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCompesitor.setText(resHelper.getString("menuItemCompesitor.text"));
        // menuItemItemSetting
        this.menuItemItemSetting.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeyItemSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemItemSetting.setText(resHelper.getString("menuItemItemSetting.text"));
        // menuExport		
        this.menuExport.setText(resHelper.getString("menuExport.text"));
        // kDMenuItem5
        this.kDMenuItem5.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuotingPriceGather, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem5.setText(resHelper.getString("kDMenuItem5.text"));
        // kDMenuItem6
        this.kDMenuItem6.setAction((IItemAction)ActionProxyFactory.getProxy(actionKeyItemAnalyse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem6.setText(resHelper.getString("kDMenuItem6.text"));
        // kDMenuItem7
        this.kDMenuItem7.setAction((IItemAction)ActionProxyFactory.getProxy(actionPriceModify, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem7.setText(resHelper.getString("kDMenuItem7.text"));
        // kDMenuItem8
        this.kDMenuItem8.setAction((IItemAction)ActionProxyFactory.getProxy(actionOverRangeQuotingPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem8.setText(resHelper.getString("kDMenuItem8.text"));
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
        pnlSplit.setBounds(new Rectangle(12, 13, 989, 604));
        this.add(pnlSplit, new KDLayout.Constraints(12, 13, 989, 604, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlSplit
        pnlSplit.add(leftSplit, "left");
        pnlSplit.add(splitBill, "right");
        //leftSplit
        leftSplit.add(contOrg, "top");
        leftSplit.add(contInviteType, "bottom");
        //contOrg
contOrg.getContentPane().setLayout(new BorderLayout(0, 0));        contOrg.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeOrg, null);
        //contInviteType
contInviteType.getContentPane().setLayout(new BorderLayout(0, 0));        contInviteType.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeInviteType, null);
        //splitBill
        splitBill.add(contMainBill, "top");
        splitBill.add(contDetalBill, "bottom");
        //contMainBill
contMainBill.getContentPane().setLayout(new BorderLayout(0, 0));        contMainBill.getContentPane().add(tblMain, BorderLayout.CENTER);
        //contDetalBill
contDetalBill.getContentPane().setLayout(new BorderLayout(0, 0));        contDetalBill.getContentPane().add(tblDetail, BorderLayout.CENTER);

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
        menuFile.add(menuItemExportPrice);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(kDMenu2);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //kDMenu2
        kDMenu2.add(menuEditModify);
        kDMenu2.add(menuItemEdit);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(kDMenu1);
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
        //kDMenu1
        kDMenu1.add(menuViewModify);
        kDMenu1.add(menuItemView);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemSelectBidder);
        menuBiz.add(menuItemAssess);
        menuBiz.add(menuItemCompesitor);
        menuBiz.add(menuItemItemSetting);
        menuBiz.add(menuExport);
        //menuExport
        menuExport.add(kDMenuItem5);
        menuExport.add(kDMenuItem6);
        menuExport.add(kDMenuItem7);
        menuExport.add(kDMenuItem8);
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
        this.toolBar.add(btnChooseSupplier);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnImportOriginalPrice);
        this.toolBar.add(btnImportModifyPrice);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnImportPrice);
        this.toolBar.add(btnExportPrice);
        this.toolBar.add(btnSelectBidder);
        this.toolBar.add(btnAssess);
        this.toolBar.add(btnCompositor);
        this.toolBar.add(btnKeyItemSet);
        this.toolBar.add(btnReportExport);
        this.toolBar.add(btnUnBid);
        this.toolBar.add(btnInviteExecuteInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.QuotingPriceListUIHandler";
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
     * output tblDetail_tableClicked method
     */
    protected void tblDetail_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblDetail_tableSelectChanged method
     */
    protected void tblDetail_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("inviteType.name"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("curProject.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("inviteAuditState"));
        sic.add(new SelectorItemInfo("inviteProject.name"));
        sic.add(new SelectorItemInfo("appraise.id"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("isLastVersion"));
        return sic;
    }        
    	

    /**
     * output actionView_actionPerformed method
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }
    	

    /**
     * output actionSelectBidder_actionPerformed method
     */
    public void actionSelectBidder_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssess_actionPerformed method
     */
    public void actionAssess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCompositor_actionPerformed method
     */
    public void actionCompositor_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReportExport_actionPerformed method
     */
    public void actionReportExport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOverRangeQuotingPrice_actionPerformed method
     */
    public void actionOverRangeQuotingPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuotingPriceGather_actionPerformed method
     */
    public void actionQuotingPriceGather_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionKeyItemAnalyse_actionPerformed method
     */
    public void actionKeyItemAnalyse_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionKeyItemSet_actionPerformed method
     */
    public void actionKeyItemSet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPriceModify_actionPerformed method
     */
    public void actionPriceModify_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAnalyseListUI_actionPerformed method
     */
    public void actionAnalyseListUI_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportListUI_actionPerformed method
     */
    public void actionImportListUI_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuotingPriceSum_actionPerformed method
     */
    public void actionQuotingPriceSum_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnBid_actionPerformed method
     */
    public void actionUnBid_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportPrice_actionPerformed method
     */
    public void actionExportPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportModifyPrice_actionPerformed method
     */
    public void actionImportModifyPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInviteExecuteInfo_actionPerformed method
     */
    public void actionInviteExecuteInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionView(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView() {
    	return false;
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionEdit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
    }
	public RequestContext prepareActionSelectBidder(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelectBidder() {
    	return false;
    }
	public RequestContext prepareActionAssess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssess() {
    	return false;
    }
	public RequestContext prepareActionCompositor(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCompositor() {
    	return false;
    }
	public RequestContext prepareActionReportExport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReportExport() {
    	return false;
    }
	public RequestContext prepareActionOverRangeQuotingPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOverRangeQuotingPrice() {
    	return false;
    }
	public RequestContext prepareActionQuotingPriceGather(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuotingPriceGather() {
    	return false;
    }
	public RequestContext prepareActionKeyItemAnalyse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionKeyItemAnalyse() {
    	return false;
    }
	public RequestContext prepareActionKeyItemSet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionKeyItemSet() {
    	return false;
    }
	public RequestContext prepareActionPriceModify(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPriceModify() {
    	return false;
    }
	public RequestContext prepareActionAnalyseListUI(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAnalyseListUI() {
    	return false;
    }
	public RequestContext prepareActionImportListUI(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportListUI() {
    	return false;
    }
	public RequestContext prepareActionQuotingPriceSum(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuotingPriceSum() {
    	return false;
    }
	public RequestContext prepareActionUnBid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnBid() {
    	return false;
    }
	public RequestContext prepareActionExportPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportPrice() {
    	return false;
    }
	public RequestContext prepareActionImportModifyPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportModifyPrice() {
    	return false;
    }
	public RequestContext prepareActionInviteExecuteInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInviteExecuteInfo() {
    	return false;
    }

    /**
     * output ActionSelectBidder class
     */     
    protected class ActionSelectBidder extends ItemAction {     
    
        public ActionSelectBidder()
        {
            this(null);
        }

        public ActionSelectBidder(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl B"));
            _tempStr = resHelper.getString("ActionSelectBidder.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectBidder.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectBidder.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionSelectBidder", "actionSelectBidder_actionPerformed", e);
        }
    }

    /**
     * output ActionAssess class
     */     
    protected class ActionAssess extends ItemAction {     
    
        public ActionAssess()
        {
            this(null);
        }

        public ActionAssess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl A"));
            _tempStr = resHelper.getString("ActionAssess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionAssess", "actionAssess_actionPerformed", e);
        }
    }

    /**
     * output ActionCompositor class
     */     
    protected class ActionCompositor extends ItemAction {     
    
        public ActionCompositor()
        {
            this(null);
        }

        public ActionCompositor(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl C"));
            _tempStr = resHelper.getString("ActionCompositor.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompositor.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompositor.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionCompositor", "actionCompositor_actionPerformed", e);
        }
    }

    /**
     * output ActionReportExport class
     */     
    protected class ActionReportExport extends ItemAction {     
    
        public ActionReportExport()
        {
            this(null);
        }

        public ActionReportExport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl X"));
            _tempStr = resHelper.getString("ActionReportExport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReportExport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReportExport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionReportExport", "actionReportExport_actionPerformed", e);
        }
    }

    /**
     * output ActionOverRangeQuotingPrice class
     */     
    protected class ActionOverRangeQuotingPrice extends ItemAction {     
    
        public ActionOverRangeQuotingPrice()
        {
            this(null);
        }

        public ActionOverRangeQuotingPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOverRangeQuotingPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOverRangeQuotingPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOverRangeQuotingPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionOverRangeQuotingPrice", "actionOverRangeQuotingPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionQuotingPriceGather class
     */     
    protected class ActionQuotingPriceGather extends ItemAction {     
    
        public ActionQuotingPriceGather()
        {
            this(null);
        }

        public ActionQuotingPriceGather(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuotingPriceGather.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuotingPriceGather.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuotingPriceGather.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionQuotingPriceGather", "actionQuotingPriceGather_actionPerformed", e);
        }
    }

    /**
     * output ActionKeyItemAnalyse class
     */     
    protected class ActionKeyItemAnalyse extends ItemAction {     
    
        public ActionKeyItemAnalyse()
        {
            this(null);
        }

        public ActionKeyItemAnalyse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionKeyItemAnalyse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeyItemAnalyse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeyItemAnalyse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionKeyItemAnalyse", "actionKeyItemAnalyse_actionPerformed", e);
        }
    }

    /**
     * output ActionKeyItemSet class
     */     
    protected class ActionKeyItemSet extends ItemAction {     
    
        public ActionKeyItemSet()
        {
            this(null);
        }

        public ActionKeyItemSet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionKeyItemSet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeyItemSet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeyItemSet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionKeyItemSet", "actionKeyItemSet_actionPerformed", e);
        }
    }

    /**
     * output ActionPriceModify class
     */     
    protected class ActionPriceModify extends ItemAction {     
    
        public ActionPriceModify()
        {
            this(null);
        }

        public ActionPriceModify(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPriceModify.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPriceModify.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPriceModify.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionPriceModify", "actionPriceModify_actionPerformed", e);
        }
    }

    /**
     * output ActionAnalyseListUI class
     */     
    protected class ActionAnalyseListUI extends ItemAction {     
    
        public ActionAnalyseListUI()
        {
            this(null);
        }

        public ActionAnalyseListUI(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAnalyseListUI.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAnalyseListUI.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAnalyseListUI.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionAnalyseListUI", "actionAnalyseListUI_actionPerformed", e);
        }
    }

    /**
     * output ActionImportListUI class
     */     
    protected class ActionImportListUI extends ItemAction {     
    
        public ActionImportListUI()
        {
            this(null);
        }

        public ActionImportListUI(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportListUI.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportListUI.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportListUI.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionImportListUI", "actionImportListUI_actionPerformed", e);
        }
    }

    /**
     * output ActionQuotingPriceSum class
     */     
    protected class ActionQuotingPriceSum extends ItemAction {     
    
        public ActionQuotingPriceSum()
        {
            this(null);
        }

        public ActionQuotingPriceSum(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuotingPriceSum.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuotingPriceSum.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuotingPriceSum.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionQuotingPriceSum", "actionQuotingPriceSum_actionPerformed", e);
        }
    }

    /**
     * output ActionUnBid class
     */     
    protected class ActionUnBid extends ItemAction {     
    
        public ActionUnBid()
        {
            this(null);
        }

        public ActionUnBid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnBid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnBid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnBid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionUnBid", "actionUnBid_actionPerformed", e);
        }
    }

    /**
     * output ActionExportPrice class
     */     
    protected class ActionExportPrice extends ItemAction {     
    
        public ActionExportPrice()
        {
            this(null);
        }

        public ActionExportPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionExportPrice", "actionExportPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionImportModifyPrice class
     */     
    protected class ActionImportModifyPrice extends ItemAction {     
    
        public ActionImportModifyPrice()
        {
            this(null);
        }

        public ActionImportModifyPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportModifyPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportModifyPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportModifyPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionImportModifyPrice", "actionImportModifyPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionInviteExecuteInfo class
     */     
    protected class ActionInviteExecuteInfo extends ItemAction {     
    
        public ActionInviteExecuteInfo()
        {
            this(null);
        }

        public ActionInviteExecuteInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInviteExecuteInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteExecuteInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteExecuteInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuotingPriceListUI.this, "ActionInviteExecuteInfo", "actionInviteExecuteInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "QuotingPriceListUI");
    }




}