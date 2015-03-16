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
public abstract class AbstractPaymentBillListUI extends com.kingdee.eas.fdc.contract.client.ContractListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer contPayBillList;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPaymentBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPaymentPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPayGroup;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWriteOff;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCommitToBE;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEnrolNote;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEndorseOut;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCommitSettle;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelPay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuPay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemWriteOff;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCommitToBE;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEnrolNote;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEndorseOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelPay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCommitSettle;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected ActionProjectAttachment actionProjectAttachment = null;
    protected ActionPaymentPlan actionPaymentPlan = null;
    protected ActionPay actionPay = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionWriteOff actionWriteOff = null;
    protected ActionCommitToBE actionCommitToBE = null;
    protected ActionEnrolNote actionEnrolNote = null;
    protected ActionEndorseOut actionEndorseOut = null;
    protected ActionCommitSettle actionCommitSettle = null;
    protected ActionCancelPay actionCancelPay = null;
    protected ActionSplit actionSplit = null;
    /**
     * output class constructor
     */
    public AbstractPaymentBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery");
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
        //actionProjectAttachment
        this.actionProjectAttachment = new ActionProjectAttachment(this);
        getActionManager().registerAction("actionProjectAttachment", actionProjectAttachment);
         this.actionProjectAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPaymentPlan
        this.actionPaymentPlan = new ActionPaymentPlan(this);
        getActionManager().registerAction("actionPaymentPlan", actionPaymentPlan);
         this.actionPaymentPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPay
        this.actionPay = new ActionPay(this);
        getActionManager().registerAction("actionPay", actionPay);
         this.actionPay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWriteOff
        this.actionWriteOff = new ActionWriteOff(this);
        getActionManager().registerAction("actionWriteOff", actionWriteOff);
         this.actionWriteOff.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCommitToBE
        this.actionCommitToBE = new ActionCommitToBE(this);
        getActionManager().registerAction("actionCommitToBE", actionCommitToBE);
         this.actionCommitToBE.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEnrolNote
        this.actionEnrolNote = new ActionEnrolNote(this);
        getActionManager().registerAction("actionEnrolNote", actionEnrolNote);
         this.actionEnrolNote.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEndorseOut
        this.actionEndorseOut = new ActionEndorseOut(this);
        getActionManager().registerAction("actionEndorseOut", actionEndorseOut);
         this.actionEndorseOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCommitSettle
        this.actionCommitSettle = new ActionCommitSettle(this);
        getActionManager().registerAction("actionCommitSettle", actionCommitSettle);
         this.actionCommitSettle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelPay
        this.actionCancelPay = new ActionCancelPay(this);
        getActionManager().registerAction("actionCancelPay", actionCancelPay);
         this.actionCancelPay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contPayBillList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblPaymentBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnProjectAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPaymentPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPayGroup = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBgBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWriteOff = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCommitToBE = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEnrolNote = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEndorseOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCommitSettle = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuPay = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemPay = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemWriteOff = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCommitToBE = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemEnrolNote = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemEndorseOut = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelPay = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCommitSettle = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contPayBillList.setName("contPayBillList");
        this.tblPaymentBill.setName("tblPaymentBill");
        this.btnProjectAttachment.setName("btnProjectAttachment");
        this.btnPaymentPlan.setName("btnPaymentPlan");
        this.btnPay.setName("btnPay");
        this.btnPayGroup.setName("btnPayGroup");
        this.btnViewBgBalance.setName("btnViewBgBalance");
        this.btnWriteOff.setName("btnWriteOff");
        this.btnCommitToBE.setName("btnCommitToBE");
        this.btnEnrolNote.setName("btnEnrolNote");
        this.btnEndorseOut.setName("btnEndorseOut");
        this.btnCommitSettle.setName("btnCommitSettle");
        this.btnCancelPay.setName("btnCancelPay");
        this.btnSplit.setName("btnSplit");
        this.menuItemViewBgBalance.setName("menuItemViewBgBalance");
        this.menuPay.setName("menuPay");
        this.menuItemPay.setName("menuItemPay");
        this.menuItemWriteOff.setName("menuItemWriteOff");
        this.menuItemCommitToBE.setName("menuItemCommitToBE");
        this.menuItemEnrolNote.setName("menuItemEnrolNote");
        this.menuItemEndorseOut.setName("menuItemEndorseOut");
        this.menuItemCancelPay.setName("menuItemCancelPay");
        this.menuItemCommitSettle.setName("menuItemCommitSettle");
        this.menuItemSplit.setName("menuItemSplit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>%r{yyyy-M-d}t</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"bookedDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"period\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"state\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasSettle\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"contractType.name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"contractName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"currency\" t:width=\"55\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"originalAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"partB.name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"contractSource\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"signDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol13\" /><t:Column t:key=\"landDeveloper.name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"partC.name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"costProperty\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"contractPropert\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"currency.id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol18\" /><t:Column t:key=\"currency.precision\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol19\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{hasSettle}</t:Cell><t:Cell>$Resource{contractType.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{contractName}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{partB.name}</t:Cell><t:Cell>$Resource{contractSource}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{landDeveloper.name}</t:Cell><t:Cell>$Resource{partC.name}</t:Cell><t:Cell>$Resource{costProperty}</t:Cell><t:Cell>$Resource{contractPropert}</t:Cell><t:Cell>$Resource{currency.id}</t:Cell><t:Cell>$Resource{currency.precision}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","hasSettled","contractType.name","number","name","currency.name","originalAmount","amount","partB.name","contractSourceId.name","signDate","landDeveloper.name","partC.name","costProperty","contractPropert","currency.id","currency.precision"});

		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.btnTraceDown.setVisible(true);		
        this.btnAuditResult.setVisible(true);		
        this.menuItemTraceDown.setVisible(true);
        // contPayBillList		
        this.contPayBillList.setTitle(resHelper.getString("contPayBillList.title"));		
        this.contPayBillList.setEnableActive(false);
        // tblPaymentBill
		String tblPaymentBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol24\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bookedDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"period\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"billStatus\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"settlementStatus\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"fiVouchered\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"payreqbillnum\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"paymentType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"currency\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"localAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"payDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"payeeName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"actPayeeName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"summary\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"descritpion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"payeeBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"payeeAccountBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol24\" /><t:Column t:key=\"isRespite\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{billStatus}</t:Cell><t:Cell>$Resource{settlementStatus}</t:Cell><t:Cell>$Resource{fiVouchered}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{payreqbillnum}</t:Cell><t:Cell>$Resource{paymentType}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{localAmt}</t:Cell><t:Cell>$Resource{payDate}</t:Cell><t:Cell>$Resource{payeeName}</t:Cell><t:Cell>$Resource{actPayeeName}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{summary}</t:Cell><t:Cell>$Resource{descritpion}</t:Cell><t:Cell>$Resource{payeeBank}</t:Cell><t:Cell>$Resource{payeeAccountBank}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell><t:Cell>$Resource{contractId}</t:Cell><t:Cell>$Resource{isRespite}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPaymentBill.setFormatXml(resHelper.translateString("tblPaymentBill",tblPaymentBillStrXML));
        this.tblPaymentBill.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblPaymentBill_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnProjectAttachment
        this.btnProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectAttachment.setText(resHelper.getString("btnProjectAttachment.text"));
        // btnPaymentPlan
        this.btnPaymentPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionPaymentPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPaymentPlan.setText(resHelper.getString("btnPaymentPlan.text"));
        // btnPay
        this.btnPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPay.setText(resHelper.getString("btnPay.text"));		
        this.btnPay.setVisible(false);		
        this.btnPay.setToolTipText(resHelper.getString("btnPay.toolTipText"));
        // btnPayGroup		
        this.btnPayGroup.setText(resHelper.getString("btnPayGroup.text"));
        // btnViewBgBalance
        this.btnViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgBalance.setText(resHelper.getString("btnViewBgBalance.text"));
        // btnWriteOff
        this.btnWriteOff.setAction((IItemAction)ActionProxyFactory.getProxy(actionWriteOff, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWriteOff.setText(resHelper.getString("btnWriteOff.text"));		
        this.btnWriteOff.setVisible(false);		
        this.btnWriteOff.setToolTipText(resHelper.getString("btnWriteOff.toolTipText"));
        // btnCommitToBE
        this.btnCommitToBE.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommitToBE, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCommitToBE.setText(resHelper.getString("btnCommitToBE.text"));		
        this.btnCommitToBE.setVisible(false);		
        this.btnCommitToBE.setToolTipText(resHelper.getString("btnCommitToBE.toolTipText"));
        // btnEnrolNote
        this.btnEnrolNote.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnrolNote, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEnrolNote.setText(resHelper.getString("btnEnrolNote.text"));		
        this.btnEnrolNote.setVisible(false);		
        this.btnEnrolNote.setToolTipText(resHelper.getString("btnEnrolNote.toolTipText"));
        // btnEndorseOut
        this.btnEndorseOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionEndorseOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEndorseOut.setText(resHelper.getString("btnEndorseOut.text"));		
        this.btnEndorseOut.setVisible(false);		
        this.btnEndorseOut.setToolTipText(resHelper.getString("btnEndorseOut.toolTipText"));
        // btnCommitSettle
        this.btnCommitSettle.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommitSettle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCommitSettle.setText(resHelper.getString("btnCommitSettle.text"));
        // btnCancelPay
        this.btnCancelPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelPay.setText(resHelper.getString("btnCancelPay.text"));		
        this.btnCancelPay.setVisible(false);
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));
        // menuItemViewBgBalance
        this.menuItemViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgBalance.setText(resHelper.getString("menuItemViewBgBalance.text"));
        // menuPay		
        this.menuPay.setText(resHelper.getString("menuPay.text"));		
        this.menuPay.setMnemonic(80);
        // menuItemPay
        this.menuItemPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPay.setText(resHelper.getString("menuItemPay.text"));
        // menuItemWriteOff
        this.menuItemWriteOff.setAction((IItemAction)ActionProxyFactory.getProxy(actionWriteOff, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemWriteOff.setText(resHelper.getString("menuItemWriteOff.text"));
        // menuItemCommitToBE
        this.menuItemCommitToBE.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommitToBE, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCommitToBE.setText(resHelper.getString("menuItemCommitToBE.text"));
        // menuItemEnrolNote
        this.menuItemEnrolNote.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnrolNote, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEnrolNote.setText(resHelper.getString("menuItemEnrolNote.text"));
        // menuItemEndorseOut
        this.menuItemEndorseOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionEndorseOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEndorseOut.setText(resHelper.getString("menuItemEndorseOut.text"));
        // menuItemCancelPay
        this.menuItemCancelPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelPay.setText(resHelper.getString("menuItemCancelPay.text"));
        // menuItemCommitSettle
        this.menuItemCommitSettle.setAction((IItemAction)ActionProxyFactory.getProxy(actionCommitSettle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCommitSettle.setText(resHelper.getString("menuItemCommitSettle.text"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));
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
        kDSplitPane2.add(contPayBillList, "bottom");
        //contContrList
contContrList.getContentPane().setLayout(new BorderLayout(0, 0));        contContrList.getContentPane().add(tblMain, BorderLayout.CENTER);
        //contPayBillList
contPayBillList.getContentPane().setLayout(new BorderLayout(0, 0));        contPayBillList.getContentPane().add(tblPaymentBill, BorderLayout.CENTER);

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
        menuView.add(menuItemViewBgBalance);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSetRespite);
        menuBiz.add(menuItemCancelRespite);
        menuBiz.add(menuPay);
        menuBiz.add(menuItemCancelPay);
        menuBiz.add(menuItemCommitSettle);
        menuBiz.add(menuItemSplit);
        //menuPay
        menuPay.add(menuItemPay);
        menuPay.add(menuItemWriteOff);
        menuPay.add(menuItemCommitToBE);
        menuPay.add(menuItemEnrolNote);
        menuPay.add(menuItemEndorseOut);
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
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
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
        this.toolBar.add(btnSetRespite);
        this.toolBar.add(btnCancelRespite);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnPaymentPlan);
        this.toolBar.add(btnPay);
        this.toolBar.add(btnPayGroup);
        this.toolBar.add(btnViewBgBalance);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWriteOff);
        this.toolBar.add(btnCommitToBE);
        this.toolBar.add(btnEnrolNote);
        this.toolBar.add(btnEndorseOut);
        this.toolBar.add(btnCommitSettle);
        this.toolBar.add(btnCancelPay);
        this.toolBar.add(btnSplit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.PaymentBillListUIHandler";
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
     * output tblPaymentBill_tableSelectChanged method
     */
    protected void tblPaymentBill_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("currency.id"));
        sic.add(new SelectorItemInfo("currency.precision"));
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
     * output actionProjectAttachment_actionPerformed method
     */
    public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPaymentPlan_actionPerformed method
     */
    public void actionPaymentPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPay_actionPerformed method
     */
    public void actionPay_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWriteOff_actionPerformed method
     */
    public void actionWriteOff_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCommitToBE_actionPerformed method
     */
    public void actionCommitToBE_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEnrolNote_actionPerformed method
     */
    public void actionEnrolNote_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEndorseOut_actionPerformed method
     */
    public void actionEndorseOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCommitSettle_actionPerformed method
     */
    public void actionCommitSettle_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelPay_actionPerformed method
     */
    public void actionCancelPay_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPaymentPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPaymentPlan() {
    	return false;
    }
	public RequestContext prepareActionPay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPay() {
    	return false;
    }
	public RequestContext prepareActionViewBgBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBgBalance() {
    	return false;
    }
	public RequestContext prepareActionWriteOff(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWriteOff() {
    	return false;
    }
	public RequestContext prepareActionCommitToBE(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCommitToBE() {
    	return false;
    }
	public RequestContext prepareActionEnrolNote(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEnrolNote() {
    	return false;
    }
	public RequestContext prepareActionEndorseOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEndorseOut() {
    	return false;
    }
	public RequestContext prepareActionCommitSettle(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCommitSettle() {
    	return false;
    }
	public RequestContext prepareActionCancelPay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelPay() {
    	return false;
    }
	public RequestContext prepareActionSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplit() {
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionProjectAttachment", "actionProjectAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionPaymentPlan class
     */     
    protected class ActionPaymentPlan extends ItemAction {     
    
        public ActionPaymentPlan()
        {
            this(null);
        }

        public ActionPaymentPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPaymentPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionPaymentPlan", "actionPaymentPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionPay class
     */     
    protected class ActionPay extends ItemAction {     
    
        public ActionPay()
        {
            this(null);
        }

        public ActionPay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionPay", "actionPay_actionPerformed", e);
        }
    }

    /**
     * output ActionViewBgBalance class
     */     
    protected class ActionViewBgBalance extends ItemAction {     
    
        public ActionViewBgBalance()
        {
            this(null);
        }

        public ActionViewBgBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBgBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionWriteOff class
     */     
    protected class ActionWriteOff extends ItemAction {     
    
        public ActionWriteOff()
        {
            this(null);
        }

        public ActionWriteOff(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionWriteOff.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWriteOff.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWriteOff.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionWriteOff", "actionWriteOff_actionPerformed", e);
        }
    }

    /**
     * output ActionCommitToBE class
     */     
    protected class ActionCommitToBE extends ItemAction {     
    
        public ActionCommitToBE()
        {
            this(null);
        }

        public ActionCommitToBE(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCommitToBE.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommitToBE.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommitToBE.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionCommitToBE", "actionCommitToBE_actionPerformed", e);
        }
    }

    /**
     * output ActionEnrolNote class
     */     
    protected class ActionEnrolNote extends ItemAction {     
    
        public ActionEnrolNote()
        {
            this(null);
        }

        public ActionEnrolNote(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEnrolNote.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnrolNote.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnrolNote.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionEnrolNote", "actionEnrolNote_actionPerformed", e);
        }
    }

    /**
     * output ActionEndorseOut class
     */     
    protected class ActionEndorseOut extends ItemAction {     
    
        public ActionEndorseOut()
        {
            this(null);
        }

        public ActionEndorseOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEndorseOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEndorseOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEndorseOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionEndorseOut", "actionEndorseOut_actionPerformed", e);
        }
    }

    /**
     * output ActionCommitSettle class
     */     
    protected class ActionCommitSettle extends ItemAction {     
    
        public ActionCommitSettle()
        {
            this(null);
        }

        public ActionCommitSettle(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCommitSettle.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommitSettle.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCommitSettle.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionCommitSettle", "actionCommitSettle_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelPay class
     */     
    protected class ActionCancelPay extends ItemAction {     
    
        public ActionCancelPay()
        {
            this(null);
        }

        public ActionCancelPay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelPay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelPay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelPay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionCancelPay", "actionCancelPay_actionPerformed", e);
        }
    }

    /**
     * output ActionSplit class
     */     
    protected class ActionSplit extends ItemAction {     
    
        public ActionSplit()
        {
            this(null);
        }

        public ActionSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillListUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentBillListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}