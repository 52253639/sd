/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractPaymentSplitListUI extends com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentSplitListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPayment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplitAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewInvalid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplitAll;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplitAllFI;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewPayment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuBatchVoucher;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProcess;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSettle;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemGuarante;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoCostProcess;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoCostSettle;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoCostGuarante;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFlowProcess;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFlowSettle;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFlowNoText;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoTextCost;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoTextNoCost;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClearSplit;
    protected ActionTDPrint actionTDPrint = null;
    protected ActionTDPrintPreview actionTDPrintPreview = null;
    protected ActionViewPayment actionViewPayment = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionSplitAll actionSplitAll = null;
    protected ActionViewInvalid actionViewInvalid = null;
    protected ActionProcess actionProcess = null;
    protected ActionSettle actionSettle = null;
    protected ActionGuarante actionGuarante = null;
    protected ActionNoCostProcess actionNoCostProcess = null;
    protected ActionNoCostSettle actionNoCostSettle = null;
    protected ActionNoCostGuarante actionNoCostGuarante = null;
    protected ActionFlowProcess actionFlowProcess = null;
    protected ActionFlowSettle actionFlowSettle = null;
    protected ActionFlowNoText actionFlowNoText = null;
    protected ActionNoTextCost actionNoTextCost = null;
    protected ActionNoTextNoCost actionNoTextNoCost = null;
    protected ActionClearSplit actionClearSplit = null;
    /**
     * output class constructor
     */
    public AbstractPaymentSplitListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentSplitListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.finance.app", "PaymentSplitQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl d"));
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
        //actionTDPrint
        this.actionTDPrint = new ActionTDPrint(this);
        getActionManager().registerAction("actionTDPrint", actionTDPrint);
         this.actionTDPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTDPrintPreview
        this.actionTDPrintPreview = new ActionTDPrintPreview(this);
        getActionManager().registerAction("actionTDPrintPreview", actionTDPrintPreview);
         this.actionTDPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPayment
        this.actionViewPayment = new ActionViewPayment(this);
        getActionManager().registerAction("actionViewPayment", actionViewPayment);
         this.actionViewPayment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitAll
        this.actionSplitAll = new ActionSplitAll(this);
        getActionManager().registerAction("actionSplitAll", actionSplitAll);
         this.actionSplitAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewInvalid
        this.actionViewInvalid = new ActionViewInvalid(this);
        getActionManager().registerAction("actionViewInvalid", actionViewInvalid);
         this.actionViewInvalid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProcess
        this.actionProcess = new ActionProcess(this);
        getActionManager().registerAction("actionProcess", actionProcess);
         this.actionProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSettle
        this.actionSettle = new ActionSettle(this);
        getActionManager().registerAction("actionSettle", actionSettle);
         this.actionSettle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionGuarante
        this.actionGuarante = new ActionGuarante(this);
        getActionManager().registerAction("actionGuarante", actionGuarante);
         this.actionGuarante.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoCostProcess
        this.actionNoCostProcess = new ActionNoCostProcess(this);
        getActionManager().registerAction("actionNoCostProcess", actionNoCostProcess);
         this.actionNoCostProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoCostSettle
        this.actionNoCostSettle = new ActionNoCostSettle(this);
        getActionManager().registerAction("actionNoCostSettle", actionNoCostSettle);
         this.actionNoCostSettle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoCostGuarante
        this.actionNoCostGuarante = new ActionNoCostGuarante(this);
        getActionManager().registerAction("actionNoCostGuarante", actionNoCostGuarante);
         this.actionNoCostGuarante.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFlowProcess
        this.actionFlowProcess = new ActionFlowProcess(this);
        getActionManager().registerAction("actionFlowProcess", actionFlowProcess);
         this.actionFlowProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFlowSettle
        this.actionFlowSettle = new ActionFlowSettle(this);
        getActionManager().registerAction("actionFlowSettle", actionFlowSettle);
         this.actionFlowSettle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFlowNoText
        this.actionFlowNoText = new ActionFlowNoText(this);
        getActionManager().registerAction("actionFlowNoText", actionFlowNoText);
         this.actionFlowNoText.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoTextCost
        this.actionNoTextCost = new ActionNoTextCost(this);
        getActionManager().registerAction("actionNoTextCost", actionNoTextCost);
         this.actionNoTextCost.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoTextNoCost
        this.actionNoTextNoCost = new ActionNoTextNoCost(this);
        getActionManager().registerAction("actionNoTextNoCost", actionNoTextNoCost);
         this.actionNoTextNoCost.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearSplit
        this.actionClearSplit = new ActionClearSplit(this);
        getActionManager().registerAction("actionClearSplit", actionClearSplit);
         this.actionClearSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnViewPayment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplitAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewInvalid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSplitAll = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplitAllFI = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewPayment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuBatchVoucher = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemProcess = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSettle = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemGuarante = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoCostProcess = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoCostSettle = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoCostGuarante = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFlowProcess = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFlowSettle = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFlowNoText = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoTextCost = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoTextNoCost = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClearSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewPayment.setName("btnViewPayment");
        this.btnViewContract.setName("btnViewContract");
        this.btnSplitAll.setName("btnSplitAll");
        this.btnViewInvalid.setName("btnViewInvalid");
        this.btnClearSplit.setName("btnClearSplit");
        this.menuItemSplitAll.setName("menuItemSplitAll");
        this.menuItemSplitAllFI.setName("menuItemSplitAllFI");
        this.menuItemViewPayment.setName("menuItemViewPayment");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.menuItemViewSplit.setName("menuItemViewSplit");
        this.menuBatchVoucher.setName("menuBatchVoucher");
        this.menuItemProcess.setName("menuItemProcess");
        this.menuItemSettle.setName("menuItemSettle");
        this.menuItemGuarante.setName("menuItemGuarante");
        this.menuItemNoCostProcess.setName("menuItemNoCostProcess");
        this.menuItemNoCostSettle.setName("menuItemNoCostSettle");
        this.menuItemNoCostGuarante.setName("menuItemNoCostGuarante");
        this.menuItemFlowProcess.setName("menuItemFlowProcess");
        this.menuItemFlowSettle.setName("menuItemFlowSettle");
        this.menuItemFlowNoText.setName("menuItemFlowNoText");
        this.menuItemNoTextCost.setName("menuItemNoTextCost");
        this.menuItemNoTextNoCost.setName("menuItemNoTextNoCost");
        this.menuItemClearSplit.setName("menuItemClearSplit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol27\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isContractWithoutText\" t:width=\"69\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractBill.isCostSplit\" t:width=\"59\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costSplit.splitState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costSplit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"billStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fdcPayType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fiVouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"voucherRefer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"fdcPayReqNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"contractBill.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"contractBill.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"contractBill.bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"contractBill.auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"currency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"localAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"payDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"costCreator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"costSplit.createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"fdcPayeeName.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"actFdcPayeeName.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"summary\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"payeeBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"payeeAccountBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"costAuditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"costSplit.auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"curProject.isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"costSplit.state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isContractWithoutText}</t:Cell><t:Cell>$Resource{contractBill.isCostSplit}</t:Cell><t:Cell>$Resource{costSplit.splitState}</t:Cell><t:Cell>$Resource{costSplit.id}</t:Cell><t:Cell>$Resource{billStatus}</t:Cell><t:Cell>$Resource{fdcPayType}</t:Cell><t:Cell>$Resource{fiVouchered}</t:Cell><t:Cell>$Resource{voucherRefer}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{fdcPayReqNumber}</t:Cell><t:Cell>$Resource{contractBill.number}</t:Cell><t:Cell>$Resource{contractBill.name}</t:Cell><t:Cell>$Resource{contractBill.bookedDate}</t:Cell><t:Cell>$Resource{contractBill.auditTime}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{localAmt}</t:Cell><t:Cell>$Resource{payDate}</t:Cell><t:Cell>$Resource{costCreator.name}</t:Cell><t:Cell>$Resource{costSplit.createTime}</t:Cell><t:Cell>$Resource{fdcPayeeName.name}</t:Cell><t:Cell>$Resource{actFdcPayeeName.name}</t:Cell><t:Cell>$Resource{summary}</t:Cell><t:Cell>$Resource{payeeBank}</t:Cell><t:Cell>$Resource{payeeAccountBank}</t:Cell><t:Cell>$Resource{costAuditor.name}</t:Cell><t:Cell>$Resource{costSplit.auditTime}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell><t:Cell>$Resource{contractId}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{curProject.isEnabled}</t:Cell><t:Cell>$Resource{costSplit.state}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"isContractWithoutText","contractBill.isCostSplit","costSplit.splitState","costSplit.id","billStatus","fdcPayType.name","costSplit.Fivouchered","voucherRefer","number","fdcPayReqNumber","contractBill.number","contractBill.name","contractBill.bookedDate","contractBill.auditTime","currency.name","amount","exchangeRate","localAmt","payDate","costCreator.name","costSplit.createTime","fdcPayeeName.name","actFdcPayeeName.name","summary","payeeBank","payeeAccountBank","costAuditor.name","costSplit.auditTime","curProject.id","contractId","id","curProject.isEnabled","costSplit.state"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);		
        this.btnVoucher.setVisible(true);		
        this.btnDelVoucher.setVisible(true);		
        this.btnTraceDown.setVisible(true);		
        this.menuItemTraceDown.setVisible(true);		
        this.menuItemVoucher.setVisible(true);		
        this.menuItemDelVoucher.setVisible(true);
        // btnViewPayment
        this.btnViewPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPayment.setText(resHelper.getString("btnViewPayment.text"));		
        this.btnViewPayment.setToolTipText(resHelper.getString("btnViewPayment.toolTipText"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));		
        this.btnViewContract.setToolTipText(resHelper.getString("btnViewContract.toolTipText"));
        // btnSplitAll
        this.btnSplitAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplitAll.setText(resHelper.getString("btnSplitAll.text"));		
        this.btnSplitAll.setToolTipText(resHelper.getString("btnSplitAll.toolTipText"));
        // btnViewInvalid
        this.btnViewInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewInvalid.setText(resHelper.getString("btnViewInvalid.text"));		
        this.btnViewInvalid.setToolTipText(resHelper.getString("btnViewInvalid.toolTipText"));
        // btnClearSplit
        this.btnClearSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearSplit.setText(resHelper.getString("btnClearSplit.text"));		
        this.btnClearSplit.setToolTipText(resHelper.getString("btnClearSplit.toolTipText"));
        // menuItemSplitAll
        this.menuItemSplitAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplitAll.setText(resHelper.getString("menuItemSplitAll.text"));		
        this.menuItemSplitAll.setToolTipText(resHelper.getString("menuItemSplitAll.toolTipText"));
        // menuItemSplitAllFI
        this.menuItemSplitAllFI.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplitAllFI.setText(resHelper.getString("menuItemSplitAllFI.text"));		
        this.menuItemSplitAllFI.setToolTipText(resHelper.getString("menuItemSplitAllFI.toolTipText"));
        // menuItemViewPayment
        this.menuItemViewPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewPayment.setText(resHelper.getString("menuItemViewPayment.text"));
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));
        // menuItemViewSplit
        this.menuItemViewSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewSplit.setText(resHelper.getString("menuItemViewSplit.text"));		
        this.menuItemViewSplit.setToolTipText(resHelper.getString("menuItemViewSplit.toolTipText"));
        // menuBatchVoucher		
        this.menuBatchVoucher.setText(resHelper.getString("menuBatchVoucher.text"));		
        this.menuBatchVoucher.setMnemonic(66);
        // menuItemProcess
        this.menuItemProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProcess.setText(resHelper.getString("menuItemProcess.text"));		
        this.menuItemProcess.setMinimumSize(new Dimension(25,5));		
        this.menuItemProcess.setMnemonic(80);
        // menuItemSettle
        this.menuItemSettle.setAction((IItemAction)ActionProxyFactory.getProxy(actionSettle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSettle.setText(resHelper.getString("menuItemSettle.text"));		
        this.menuItemSettle.setMnemonic(83);
        // menuItemGuarante
        this.menuItemGuarante.setAction((IItemAction)ActionProxyFactory.getProxy(actionGuarante, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemGuarante.setText(resHelper.getString("menuItemGuarante.text"));		
        this.menuItemGuarante.setMnemonic(71);
        // menuItemNoCostProcess
        this.menuItemNoCostProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoCostProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoCostProcess.setText(resHelper.getString("menuItemNoCostProcess.text"));		
        this.menuItemNoCostProcess.setMnemonic(78);
        // menuItemNoCostSettle
        this.menuItemNoCostSettle.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoCostSettle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoCostSettle.setText(resHelper.getString("menuItemNoCostSettle.text"));		
        this.menuItemNoCostSettle.setMnemonic(79);
        // menuItemNoCostGuarante
        this.menuItemNoCostGuarante.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoCostGuarante, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoCostGuarante.setText(resHelper.getString("menuItemNoCostGuarante.text"));		
        this.menuItemNoCostGuarante.setMnemonic(67);
        // menuItemFlowProcess
        this.menuItemFlowProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlowProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFlowProcess.setText(resHelper.getString("menuItemFlowProcess.text"));		
        this.menuItemFlowProcess.setMnemonic(70);
        // menuItemFlowSettle
        this.menuItemFlowSettle.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlowSettle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFlowSettle.setText(resHelper.getString("menuItemFlowSettle.text"));		
        this.menuItemFlowSettle.setMnemonic(76);
        // menuItemFlowNoText
        this.menuItemFlowNoText.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlowNoText, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFlowNoText.setText(resHelper.getString("menuItemFlowNoText.text"));		
        this.menuItemFlowNoText.setMnemonic(87);
        // menuItemNoTextCost
        this.menuItemNoTextCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoTextCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoTextCost.setText(resHelper.getString("menuItemNoTextCost.text"));		
        this.menuItemNoTextCost.setMnemonic(84);
        // menuItemNoTextNoCost
        this.menuItemNoTextNoCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoTextNoCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoTextNoCost.setText(resHelper.getString("menuItemNoTextNoCost.text"));		
        this.menuItemNoTextNoCost.setMnemonic(88);
        // menuItemClearSplit
        this.menuItemClearSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClearSplit.setText(resHelper.getString("menuItemClearSplit.text"));		
        this.menuItemClearSplit.setMnemonic(76);		
        this.menuItemClearSplit.setToolTipText(resHelper.getString("menuItemClearSplit.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(treeProject, "left");
        kDSplitPane1.add(mainPanel, "right");
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(tblMain, BorderLayout.CENTER);
        mainPanel.add(colorPanel, BorderLayout.SOUTH);
        colorPanel.setLayout(null);
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
        menuEdit.add(menuItemCostSplit);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemSplitAll);
        menuEdit.add(menuItemSplitAllFI);
        menuEdit.add(kDSeparator7);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
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
        menuView.add(separatorView1);
        menuView.add(menuItemSwitchView);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        menuView.add(menuItemViewPayment);
        menuView.add(menuItemViewContract);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemViewSplit);
        menuBiz.add(menuBatchVoucher);
        menuBiz.add(menuItemClearSplit);
        //menuBatchVoucher
        menuBatchVoucher.add(menuItemProcess);
        menuBatchVoucher.add(menuItemSettle);
        menuBatchVoucher.add(menuItemGuarante);
        menuBatchVoucher.add(menuItemNoCostProcess);
        menuBatchVoucher.add(menuItemNoCostSettle);
        menuBatchVoucher.add(menuItemNoCostGuarante);
        menuBatchVoucher.add(menuItemFlowProcess);
        menuBatchVoucher.add(menuItemFlowSettle);
        menuBatchVoucher.add(menuItemFlowNoText);
        menuBatchVoucher.add(menuItemNoTextCost);
        menuBatchVoucher.add(menuItemNoTextNoCost);
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
        this.toolBar.add(btnCostSplit);
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
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAddContent);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnViewPayment);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnSplitAll);
        this.toolBar.add(btnViewInvalid);
        this.toolBar.add(btnClearSplit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.PaymentSplitListUIHandler";
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
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
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

			public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("creator.*"));
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("CU.*"));
			sic.add(new SelectorItemInfo("handler.*"));
			sic.add(new SelectorItemInfo("auditor.*"));
			sic.add(new SelectorItemInfo("orgUnit.*"));
			sic.add(new SelectorItemInfo("period.*"));
			sic.add(new SelectorItemInfo("contractBill.*"));
			sic.add(new SelectorItemInfo("curProject.*"));
			sic.add(new SelectorItemInfo("entrys.*"));
			sic.add(new SelectorItemInfo("entrys.costAccount.*"));
			sic.add(new SelectorItemInfo("entrys.product.*"));
			sic.add(new SelectorItemInfo("entrys.apportionType.*"));
			sic.add(new SelectorItemInfo("entrys.accountView.*"));
			sic.add(new SelectorItemInfo("paymentBill.*"));
			sic.add(new SelectorItemInfo("beAccount.*"));
			sic.add(new SelectorItemInfo("hisStatus.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.bankAccount.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.accountView.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.currency.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.paymentBill.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.payRequestBill.*"));
			sic.add(new SelectorItemInfo("voucherEntrys.transitAccount.*"));
			sic.add(new SelectorItemInfo("hisVoucher.*"));
			sic.add(new SelectorItemInfo("contractBaseData.*"));
			sic.add(new SelectorItemInfo("transitAccount.*"));
			sic.add(new SelectorItemInfo("conWithoutText.*"));
			sic.add(new SelectorItemInfo("workLoadConfirmBill.*"));
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
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("costSplit.id"));
        sic.add(new SelectorItemInfo("costSplit.splitState"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("fdcPayReqNumber"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("localAmt"));
        sic.add(new SelectorItemInfo("payDate"));
        sic.add(new SelectorItemInfo("costCreator.name"));
        sic.add(new SelectorItemInfo("costSplit.createTime"));
        sic.add(new SelectorItemInfo("fdcPayeeName.name"));
        sic.add(new SelectorItemInfo("actFdcPayeeName.name"));
        sic.add(new SelectorItemInfo("summary"));
        sic.add(new SelectorItemInfo("payeeBank"));
        sic.add(new SelectorItemInfo("payeeAccountBank"));
        sic.add(new SelectorItemInfo("costAuditor.name"));
        sic.add(new SelectorItemInfo("costSplit.auditTime"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("curProject.isEnabled"));
        sic.add(new SelectorItemInfo("costSplit.state"));
        sic.add(new SelectorItemInfo("contractBill.isCostSplit"));
        sic.add(new SelectorItemInfo("contractBill.name"));
        sic.add(new SelectorItemInfo("contractBill.number"));
        sic.add(new SelectorItemInfo("isContractWithoutText"));
        sic.add(new SelectorItemInfo("costSplit.Fivouchered"));
        sic.add(new SelectorItemInfo("fdcPayType.name"));
        sic.add(new SelectorItemInfo("voucherRefer"));
        sic.add(new SelectorItemInfo("contractBill.auditTime"));
        sic.add(new SelectorItemInfo("contractBill.bookedDate"));
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
     * output actionTDPrint_actionPerformed method
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
        checkSelected();        
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionTDPrintPreview_actionPerformed method
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        checkSelected();
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionViewPayment_actionPerformed method
     */
    public void actionViewPayment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitAll_actionPerformed method
     */
    public void actionSplitAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewInvalid_actionPerformed method
     */
    public void actionViewInvalid_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProcess_actionPerformed method
     */
    public void actionProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSettle_actionPerformed method
     */
    public void actionSettle_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionGuarante_actionPerformed method
     */
    public void actionGuarante_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoCostProcess_actionPerformed method
     */
    public void actionNoCostProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoCostSettle_actionPerformed method
     */
    public void actionNoCostSettle_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoCostGuarante_actionPerformed method
     */
    public void actionNoCostGuarante_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFlowProcess_actionPerformed method
     */
    public void actionFlowProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFlowSettle_actionPerformed method
     */
    public void actionFlowSettle_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFlowNoText_actionPerformed method
     */
    public void actionFlowNoText_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoTextCost_actionPerformed method
     */
    public void actionNoTextCost_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoTextNoCost_actionPerformed method
     */
    public void actionNoTextNoCost_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearSplit_actionPerformed method
     */
    public void actionClearSplit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionTDPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTDPrint() {
    	return false;
    }
	public RequestContext prepareActionTDPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTDPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionViewPayment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewPayment() {
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
	public RequestContext prepareActionSplitAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplitAll() {
    	return false;
    }
	public RequestContext prepareActionViewInvalid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewInvalid() {
    	return false;
    }
	public RequestContext prepareActionProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProcess() {
    	return false;
    }
	public RequestContext prepareActionSettle(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSettle() {
    	return false;
    }
	public RequestContext prepareActionGuarante(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGuarante() {
    	return false;
    }
	public RequestContext prepareActionNoCostProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoCostProcess() {
    	return false;
    }
	public RequestContext prepareActionNoCostSettle(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoCostSettle() {
    	return false;
    }
	public RequestContext prepareActionNoCostGuarante(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoCostGuarante() {
    	return false;
    }
	public RequestContext prepareActionFlowProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFlowProcess() {
    	return false;
    }
	public RequestContext prepareActionFlowSettle(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFlowSettle() {
    	return false;
    }
	public RequestContext prepareActionFlowNoText(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFlowNoText() {
    	return false;
    }
	public RequestContext prepareActionNoTextCost(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoTextCost() {
    	return false;
    }
	public RequestContext prepareActionNoTextNoCost(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoTextNoCost() {
    	return false;
    }
	public RequestContext prepareActionClearSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearSplit() {
    	return false;
    }

    /**
     * output ActionTDPrint class
     */     
    protected class ActionTDPrint extends ItemAction {     
    
        public ActionTDPrint()
        {
            this(null);
        }

        public ActionTDPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTDPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionTDPrint", "actionTDPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionTDPrintPreview class
     */     
    protected class ActionTDPrintPreview extends ItemAction {     
    
        public ActionTDPrintPreview()
        {
            this(null);
        }

        public ActionTDPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTDPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionTDPrintPreview", "actionTDPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionViewPayment class
     */     
    protected class ActionViewPayment extends ItemAction {     
    
        public ActionViewPayment()
        {
            this(null);
        }

        public ActionViewPayment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewPayment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionViewPayment", "actionViewPayment_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionSplitAll class
     */     
    protected class ActionSplitAll extends ItemAction {     
    
        public ActionSplitAll()
        {
            this(null);
        }

        public ActionSplitAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSplitAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionSplitAll", "actionSplitAll_actionPerformed", e);
        }
    }

    /**
     * output ActionViewInvalid class
     */     
    protected class ActionViewInvalid extends ItemAction {     
    
        public ActionViewInvalid()
        {
            this(null);
        }

        public ActionViewInvalid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_lookup"));
            _tempStr = resHelper.getString("ActionViewInvalid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvalid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvalid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionViewInvalid", "actionViewInvalid_actionPerformed", e);
        }
    }

    /**
     * output ActionProcess class
     */     
    protected class ActionProcess extends ItemAction {     
    
        public ActionProcess()
        {
            this(null);
        }

        public ActionProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt E"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionProcess", "actionProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionSettle class
     */     
    protected class ActionSettle extends ItemAction {     
    
        public ActionSettle()
        {
            this(null);
        }

        public ActionSettle(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt S"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionSettle.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSettle.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSettle.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionSettle", "actionSettle_actionPerformed", e);
        }
    }

    /**
     * output ActionGuarante class
     */     
    protected class ActionGuarante extends ItemAction {     
    
        public ActionGuarante()
        {
            this(null);
        }

        public ActionGuarante(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt G"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionGuarante.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGuarante.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGuarante.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionGuarante", "actionGuarante_actionPerformed", e);
        }
    }

    /**
     * output ActionNoCostProcess class
     */     
    protected class ActionNoCostProcess extends ItemAction {     
    
        public ActionNoCostProcess()
        {
            this(null);
        }

        public ActionNoCostProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt N"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionNoCostProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoCostProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoCostProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionNoCostProcess", "actionNoCostProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionNoCostSettle class
     */     
    protected class ActionNoCostSettle extends ItemAction {     
    
        public ActionNoCostSettle()
        {
            this(null);
        }

        public ActionNoCostSettle(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt O"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionNoCostSettle.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoCostSettle.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoCostSettle.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionNoCostSettle", "actionNoCostSettle_actionPerformed", e);
        }
    }

    /**
     * output ActionNoCostGuarante class
     */     
    protected class ActionNoCostGuarante extends ItemAction {     
    
        public ActionNoCostGuarante()
        {
            this(null);
        }

        public ActionNoCostGuarante(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt C"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionNoCostGuarante.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoCostGuarante.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoCostGuarante.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionNoCostGuarante", "actionNoCostGuarante_actionPerformed", e);
        }
    }

    /**
     * output ActionFlowProcess class
     */     
    protected class ActionFlowProcess extends ItemAction {     
    
        public ActionFlowProcess()
        {
            this(null);
        }

        public ActionFlowProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt F"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionFlowProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlowProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlowProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionFlowProcess", "actionFlowProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionFlowSettle class
     */     
    protected class ActionFlowSettle extends ItemAction {     
    
        public ActionFlowSettle()
        {
            this(null);
        }

        public ActionFlowSettle(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt L"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionFlowSettle.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlowSettle.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlowSettle.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionFlowSettle", "actionFlowSettle_actionPerformed", e);
        }
    }

    /**
     * output ActionFlowNoText class
     */     
    protected class ActionFlowNoText extends ItemAction {     
    
        public ActionFlowNoText()
        {
            this(null);
        }

        public ActionFlowNoText(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt W"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionFlowNoText.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlowNoText.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlowNoText.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionFlowNoText", "actionFlowNoText_actionPerformed", e);
        }
    }

    /**
     * output ActionNoTextCost class
     */     
    protected class ActionNoTextCost extends ItemAction {     
    
        public ActionNoTextCost()
        {
            this(null);
        }

        public ActionNoTextCost(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt T"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionNoTextCost.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoTextCost.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoTextCost.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionNoTextCost", "actionNoTextCost_actionPerformed", e);
        }
    }

    /**
     * output ActionNoTextNoCost class
     */     
    protected class ActionNoTextNoCost extends ItemAction {     
    
        public ActionNoTextNoCost()
        {
            this(null);
        }

        public ActionNoTextNoCost(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt X"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scvoucher"));
            _tempStr = resHelper.getString("ActionNoTextNoCost.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoTextNoCost.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoTextNoCost.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionNoTextNoCost", "actionNoTextNoCost_actionPerformed", e);
        }
    }

    /**
     * output ActionClearSplit class
     */     
    protected class ActionClearSplit extends ItemAction {     
    
        public ActionClearSplit()
        {
            this(null);
        }

        public ActionClearSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
            _tempStr = resHelper.getString("ActionClearSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentSplitListUI.this, "ActionClearSplit", "actionClearSplit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentSplitListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.finance.PaymentSplitFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.finance.PaymentSplitInfo objectValue = new com.kingdee.eas.fdc.finance.PaymentSplitInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"billStatus","fdcPayType","number","fdcPayReqNumber","currency.name","amount","exchangeRate","localAmt","payDate","fdcPayeeName.name","actFdcPayeeName.name","summary","payeeBank","payeeAccountBank","curProject.id","contractId","id","curProject.isEnabled"};
    }



	protected String getTDFileName() {
    	return "/bim/fdc/finance/PaymentSplit";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.finance.app.PaymentSplitQuery");
	}

}