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
public abstract class AbstractPaymentNoCostSplitEditUI extends com.kingdee.eas.fdc.basedata.client.FDCNoCostSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentNoCostSplitEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContrName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContrName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPayment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewPayment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAotoMatchSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAutoMatchSplit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplitBaseOnProp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSplitInvoice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSplitInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplitBaseOnProp;
    protected com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo editData = null;
    protected ActionSplit actionSplit = null;
    protected ActionViewPayment actionViewPayment = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionAutoMatchSplit actionAutoMatchSplit = null;
    protected ActionSplitBaseOnProp actionSplitBaseOnProp = null;
    /**
     * output class constructor
     */
    public AbstractPaymentNoCostSplitEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentNoCostSplitEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPayment
        this.actionViewPayment = new ActionViewPayment(this);
        getActionManager().registerAction("actionViewPayment", actionViewPayment);
         this.actionViewPayment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAutoMatchSplit
        this.actionAutoMatchSplit = new ActionAutoMatchSplit(this);
        getActionManager().registerAction("actionAutoMatchSplit", actionAutoMatchSplit);
         this.actionAutoMatchSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitBaseOnProp
        this.actionSplitBaseOnProp = new ActionSplitBaseOnProp(this);
        getActionManager().registerAction("actionSplitBaseOnProp", actionSplitBaseOnProp);
         this.actionSplitBaseOnProp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contContrName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContrName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCostAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnViewPayment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewPayment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAotoMatchSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAutoMatchSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnSplitBaseOnProp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contInvoice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contSplitInvoice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSplitInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.menuItemSplitBaseOnProp = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contContrName.setName("contContrName");
        this.txtContrName.setName("txtContrName");
        this.btnSplit.setName("btnSplit");
        this.menuItemSplit.setName("menuItemSplit");
        this.contCostAmt.setName("contCostAmt");
        this.txtCompletePrjAmt.setName("txtCompletePrjAmt");
        this.btnViewPayment.setName("btnViewPayment");
        this.btnViewContract.setName("btnViewContract");
        this.menuItemViewPayment.setName("menuItemViewPayment");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.menuItemAotoMatchSplit.setName("menuItemAotoMatchSplit");
        this.btnAutoMatchSplit.setName("btnAutoMatchSplit");
        this.contPayType.setName("contPayType");
        this.prmtPayType.setName("prmtPayType");
        this.btnSplitBaseOnProp.setName("btnSplitBaseOnProp");
        this.contInvoice.setName("contInvoice");
        this.txtInvoiceAmt.setName("txtInvoiceAmt");
        this.contSplitInvoice.setName("contSplitInvoice");
        this.txtSplitInvoiceAmt.setName("txtSplitInvoiceAmt");
        this.menuItemSplitBaseOnProp.setName("menuItemSplitBaseOnProp");
        // CoreUI		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setEnabled(true);		
        this.menuView.setVisible(true);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnAddLine.setVisible(true);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemNextPerson.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.MenuItemWFG.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setVisible(false);		
        this.kDLabelContainer4.setEnabled(false);		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"curProject.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"accountView.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"accountView.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"contractAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"changeAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"costAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"shouldPayAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"splitedCostAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"splitedInvoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"splitedPayedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"splitedQualityAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"invoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"payedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"standard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"product\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"directAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"directInvoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"directPayedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"accountView.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"splitType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"apportionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"apportionValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"directAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"apportionValueTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"directAmountTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"otherRatioTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{curProject.number}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{accountView.number}</t:Cell><t:Cell>$Resource{accountView.name}</t:Cell><t:Cell>$Resource{contractAmt}</t:Cell><t:Cell>$Resource{changeAmt}</t:Cell><t:Cell>$Resource{costAmt}</t:Cell><t:Cell>$Resource{shouldPayAmt}</t:Cell><t:Cell>$Resource{splitedCostAmt}</t:Cell><t:Cell>$Resource{splitedInvoiceAmt}</t:Cell><t:Cell>$Resource{splitedPayedAmt}</t:Cell><t:Cell>$Resource{splitedQualityAmt}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{invoiceAmt}</t:Cell><t:Cell>$Resource{payedAmt}</t:Cell><t:Cell>$Resource{standard}</t:Cell><t:Cell>$Resource{product}</t:Cell><t:Cell>$Resource{directAmt}</t:Cell><t:Cell>$Resource{directInvoiceAmt}</t:Cell><t:Cell>$Resource{directPayedAmt}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell><t:Cell>$Resource{accountView.id}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{splitType}</t:Cell><t:Cell>$Resource{apportionType.name}</t:Cell><t:Cell>$Resource{apportionValue}</t:Cell><t:Cell>$Resource{directAmount}</t:Cell><t:Cell>$Resource{apportionValueTotal}</t:Cell><t:Cell>$Resource{directAmountTotal}</t:Cell><t:Cell>$Resource{otherRatioTotal}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","","","","","contractAmt","changeAmt","costAmt","shouldPayAmt","splitedCostAmt","splitedInvoiceAmt","splitedPayedAmt","splitQualityAmt","amount","invoiceAmt","payedAmt","","product","directAmt","directInvoiceAmt","directPayedAmt","curProject.id","accountView.id","level","splitType","apportionType","apportionValue","directAmount","apportionValueTotal","directAmountTotal","otherRatioTotal"});

		
        this.btnAcctSelect.setText(resHelper.getString("btnAcctSelect.text"));		
        this.btnAcctSelect.setToolTipText(resHelper.getString("btnAcctSelect.toolTipText"));		
        this.menuItemAcctSelect.setText(resHelper.getString("menuItemAcctSelect.text"));
        // contContrName		
        this.contContrName.setBoundLabelText(resHelper.getString("contContrName.boundLabelText"));		
        this.contContrName.setBoundLabelLength(100);		
        this.contContrName.setBoundLabelUnderline(true);		
        this.contContrName.setEnabled(false);
        // txtContrName		
        this.txtContrName.setEnabled(false);
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));
        // contCostAmt		
        this.contCostAmt.setBoundLabelText(resHelper.getString("contCostAmt.boundLabelText"));		
        this.contCostAmt.setBoundLabelLength(100);		
        this.contCostAmt.setBoundLabelUnderline(true);
        // txtCompletePrjAmt		
        this.txtCompletePrjAmt.setEnabled(false);		
        this.txtCompletePrjAmt.setSupportedEmpty(true);		
        this.txtCompletePrjAmt.setPrecision(2);		
        this.txtCompletePrjAmt.setDataType(1);
        // btnViewPayment
        this.btnViewPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPayment.setText(resHelper.getString("btnViewPayment.text"));		
        this.btnViewPayment.setToolTipText(resHelper.getString("btnViewPayment.toolTipText"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));		
        this.btnViewContract.setToolTipText(resHelper.getString("btnViewContract.toolTipText"));
        // menuItemViewPayment
        this.menuItemViewPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewPayment.setText(resHelper.getString("menuItemViewPayment.text"));
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));
        // menuItemAotoMatchSplit
        this.menuItemAotoMatchSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAutoMatchSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAotoMatchSplit.setText(resHelper.getString("menuItemAotoMatchSplit.text"));		
        this.menuItemAotoMatchSplit.setToolTipText(resHelper.getString("menuItemAotoMatchSplit.toolTipText"));		
        this.menuItemAotoMatchSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // btnAutoMatchSplit
        this.btnAutoMatchSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAutoMatchSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAutoMatchSplit.setText(resHelper.getString("btnAutoMatchSplit.text"));		
        this.btnAutoMatchSplit.setToolTipText(resHelper.getString("btnAutoMatchSplit.toolTipText"));		
        this.btnAutoMatchSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);
        // prmtPayType		
        this.prmtPayType.setDisplayFormat("$number$ $name$");		
        this.prmtPayType.setEditFormat("$number$");		
        this.prmtPayType.setEnabled(false);
        // btnSplitBaseOnProp
        this.btnSplitBaseOnProp.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitBaseOnProp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplitBaseOnProp.setText(resHelper.getString("btnSplitBaseOnProp.text"));
        // contInvoice		
        this.contInvoice.setBoundLabelText(resHelper.getString("contInvoice.boundLabelText"));		
        this.contInvoice.setBoundLabelLength(100);		
        this.contInvoice.setBoundLabelUnderline(true);
        // txtInvoiceAmt		
        this.txtInvoiceAmt.setDataType(10);		
        this.txtInvoiceAmt.setEnabled(false);
        // contSplitInvoice		
        this.contSplitInvoice.setBoundLabelText(resHelper.getString("contSplitInvoice.boundLabelText"));		
        this.contSplitInvoice.setBoundLabelLength(100);		
        this.contSplitInvoice.setBoundLabelUnderline(true);
        // txtSplitInvoiceAmt		
        this.txtSplitInvoiceAmt.setDataType(1);		
        this.txtSplitInvoiceAmt.setEnabled(false);
        // menuItemSplitBaseOnProp
        this.menuItemSplitBaseOnProp.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitBaseOnProp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplitBaseOnProp.setText(resHelper.getString("menuItemSplitBaseOnProp.text"));		
        this.menuItemSplitBaseOnProp.setToolTipText(resHelper.getString("menuItemSplitBaseOnProp.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(733, 571, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(733, 571, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 470, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 10, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(703, 55, 300, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(703, 55, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(100, 32, 20, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(100, 32, 20, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(357, 55, 300, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(357, 55, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(533, 10, 470, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(533, 10, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(10, 80, 993, 461));
        this.add(kdtEntrys, new KDLayout.Constraints(10, 80, 993, 461, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(729, 527, 225, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(729, 527, 225, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(10, 549, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(723, 490, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(723, 490, 270, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(10, 571, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(10, 571, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(733, 549, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(733, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(446, 477, 224, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(446, 477, 224, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(372, 551, 270, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(372, 551, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(10, 490, 270, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(10, 490, 270, 19, 0));
        contContrName.setBounds(new Rectangle(10, 32, 470, 19));
        this.add(contContrName, new KDLayout.Constraints(10, 32, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostAmt.setBounds(new Rectangle(10, 55, 300, 19));
        this.add(contCostAmt, new KDLayout.Constraints(10, 55, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayType.setBounds(new Rectangle(533, 32, 470, 19));
        this.add(contPayType, new KDLayout.Constraints(533, 32, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInvoice.setBounds(new Rectangle(10, 55, 300, 19));
        this.add(contInvoice, new KDLayout.Constraints(10, 55, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSplitInvoice.setBounds(new Rectangle(357, 55, 300, 19));
        this.add(contSplitInvoice, new KDLayout.Constraints(357, 55, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(dateAuditTime);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCostBillNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtAmount);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtUnSplitAmount);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtSplitedAmount);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtCostBillName);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptLastUpdateUser);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptCreator);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtDescription);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(dateCreateTime);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(dateLastUpdateTime);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtCompany);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtNumber);
        //contContrName
        contContrName.setBoundEditor(txtContrName);
        //contCostAmt
        contCostAmt.setBoundEditor(txtCompletePrjAmt);
        //contPayType
        contPayType.setBoundEditor(prmtPayType);
        //contInvoice
        contInvoice.setBoundEditor(txtInvoiceAmt);
        //contSplitInvoice
        contSplitInvoice.setBoundEditor(txtSplitInvoiceAmt);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuItemAcctSelect);
        menuEdit.add(menuItemSplitProj);
        menuEdit.add(menuItemSplitBotUp);
        menuEdit.add(menuItemSplitProd);
        menuEdit.add(menuItemImpContrSplit);
        menuEdit.add(menuItemSplit);
        menuEdit.add(menuItemAotoMatchSplit);
        menuEdit.add(menuItemSplitBaseOnProp);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemViewPayment);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewContract);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnAcctSelect);
        this.toolBar.add(separator4);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSplitProj);
        this.toolBar.add(btnSplitBotUp);
        this.toolBar.add(btnSplitProd);
        this.toolBar.add(separator5);
        this.toolBar.add(btnImpContrSplit);
        this.toolBar.add(separator6);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnAutoMatchSplit);
        this.toolBar.add(btnSplitBaseOnProp);
        this.toolBar.add(btnViewPayment);
        this.toolBar.add(btnViewContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.dateAuditTime, "value");
		dataBinder.registerBinding("paymentBill.number", String.class, this.txtCostBillNumber, "text");
		dataBinder.registerBinding("payAmount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtSplitedAmount, "value");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.product", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.kdtEntrys, "product.text");
		dataBinder.registerBinding("entrys.level", int.class, this.kdtEntrys, "level.text");
		dataBinder.registerBinding("entrys.apportionType", com.kingdee.eas.fdc.basedata.ApportionTypeInfo.class, this.kdtEntrys, "apportionType.name.text");
		dataBinder.registerBinding("entrys.apportionValue", java.math.BigDecimal.class, this.kdtEntrys, "apportionValue.text");
		dataBinder.registerBinding("entrys.directAmount", java.math.BigDecimal.class, this.kdtEntrys, "directAmount.text");
		dataBinder.registerBinding("entrys.apportionValueTotal", java.math.BigDecimal.class, this.kdtEntrys, "apportionValueTotal.text");
		dataBinder.registerBinding("entrys.directAmountTotal", java.math.BigDecimal.class, this.kdtEntrys, "directAmountTotal.text");
		dataBinder.registerBinding("entrys.otherRatioTotal", java.math.BigDecimal.class, this.kdtEntrys, "otherRatioTotal.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.contractAmt", java.math.BigDecimal.class, this.kdtEntrys, "contractAmt.text");
		dataBinder.registerBinding("entrys.changeAmt", java.math.BigDecimal.class, this.kdtEntrys, "changeAmt.text");
		dataBinder.registerBinding("entrys.costAmt", java.math.BigDecimal.class, this.kdtEntrys, "costAmt.text");
		dataBinder.registerBinding("entrys.splitedCostAmt", java.math.BigDecimal.class, this.kdtEntrys, "splitedCostAmt.text");
		dataBinder.registerBinding("entrys.directAmt", java.math.BigDecimal.class, this.kdtEntrys, "directAmt.text");
		dataBinder.registerBinding("entrys.curProject.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "curProject.id.text");
		dataBinder.registerBinding("entrys.accountView.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "accountView.id.text");
		dataBinder.registerBinding("entrys.splitType", com.kingdee.eas.fdc.basedata.CostSplitTypeEnum.class, this.kdtEntrys, "splitType.text");
		dataBinder.registerBinding("entrys.payedAmt", java.math.BigDecimal.class, this.kdtEntrys, "payedAmt.text");
		dataBinder.registerBinding("entrys.splitedPayedAmt", java.math.BigDecimal.class, this.kdtEntrys, "splitedPayedAmt.text");
		dataBinder.registerBinding("entrys.shouldPayAmt", java.math.BigDecimal.class, this.kdtEntrys, "shouldPayAmt.text");
		dataBinder.registerBinding("entrys.splitQualityAmt", java.math.BigDecimal.class, this.kdtEntrys, "splitedQualityAmt.text");
		dataBinder.registerBinding("entrys.splitedInvoiceAmt", java.math.BigDecimal.class, this.kdtEntrys, "splitedInvoiceAmt.text");
		dataBinder.registerBinding("entrys.directInvoiceAmt", java.math.BigDecimal.class, this.kdtEntrys, "directInvoiceAmt.text");
		dataBinder.registerBinding("entrys.invoiceAmt", java.math.BigDecimal.class, this.kdtEntrys, "invoiceAmt.text");
		dataBinder.registerBinding("entrys.directPayedAmt", java.math.BigDecimal.class, this.kdtEntrys, "directPayedAmt.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("completePrjAmt", java.math.BigDecimal.class, this.txtCompletePrjAmt, "value");
		dataBinder.registerBinding("paymentBill.fdcPayType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.prmtPayType, "data");
		dataBinder.registerBinding("invoiceAmt", java.math.BigDecimal.class, this.txtInvoiceAmt, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.PaymentNoCostSplitEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo)ov;
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
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentBill.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.product", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionValueTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmountTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.otherRatioTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitedCostAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.curProject.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.accountView.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.payedAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitedPayedAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.shouldPayAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitQualityAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitedInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directPayedAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completePrjAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentBill.fdcPayType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceAmt", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("paymentBill.number"));
        sic.add(new SelectorItemInfo("payAmount"));
        sic.add(new SelectorItemInfo("amount"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
        sic.add(new SelectorItemInfo("entrys.product.*"));
//        sic.add(new SelectorItemInfo("entrys.product.number"));
    sic.add(new SelectorItemInfo("entrys.level"));
        sic.add(new SelectorItemInfo("entrys.apportionType.*"));
//        sic.add(new SelectorItemInfo("entrys.apportionType.number"));
    sic.add(new SelectorItemInfo("entrys.apportionValue"));
    sic.add(new SelectorItemInfo("entrys.directAmount"));
    sic.add(new SelectorItemInfo("entrys.apportionValueTotal"));
    sic.add(new SelectorItemInfo("entrys.directAmountTotal"));
    sic.add(new SelectorItemInfo("entrys.otherRatioTotal"));
    sic.add(new SelectorItemInfo("entrys.amount"));
    sic.add(new SelectorItemInfo("entrys.contractAmt"));
    sic.add(new SelectorItemInfo("entrys.changeAmt"));
    sic.add(new SelectorItemInfo("entrys.costAmt"));
    sic.add(new SelectorItemInfo("entrys.splitedCostAmt"));
    sic.add(new SelectorItemInfo("entrys.directAmt"));
    sic.add(new SelectorItemInfo("entrys.curProject.id"));
    sic.add(new SelectorItemInfo("entrys.accountView.id"));
    sic.add(new SelectorItemInfo("entrys.splitType"));
    sic.add(new SelectorItemInfo("entrys.payedAmt"));
    sic.add(new SelectorItemInfo("entrys.splitedPayedAmt"));
    sic.add(new SelectorItemInfo("entrys.shouldPayAmt"));
    sic.add(new SelectorItemInfo("entrys.splitQualityAmt"));
    sic.add(new SelectorItemInfo("entrys.splitedInvoiceAmt"));
    sic.add(new SelectorItemInfo("entrys.directInvoiceAmt"));
    sic.add(new SelectorItemInfo("entrys.invoiceAmt"));
    sic.add(new SelectorItemInfo("entrys.directPayedAmt"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("completePrjAmt"));
        sic.add(new SelectorItemInfo("paymentBill.fdcPayType"));
        sic.add(new SelectorItemInfo("invoiceAmt"));
        return sic;
    }        
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
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
     * output actionAutoMatchSplit_actionPerformed method
     */
    public void actionAutoMatchSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitBaseOnProp_actionPerformed method
     */
    public void actionSplitBaseOnProp_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionAutoMatchSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAutoMatchSplit() {
    	return false;
    }
	public RequestContext prepareActionSplitBaseOnProp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplitBaseOnProp() {
    	return false;
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractPaymentNoCostSplitEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPaymentNoCostSplitEditUI.this, "ActionViewPayment", "actionViewPayment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPaymentNoCostSplitEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionAutoMatchSplit class
     */     
    protected class ActionAutoMatchSplit extends ItemAction {     
    
        public ActionAutoMatchSplit()
        {
            this(null);
        }

        public ActionAutoMatchSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAutoMatchSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAutoMatchSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAutoMatchSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentNoCostSplitEditUI.this, "ActionAutoMatchSplit", "actionAutoMatchSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionSplitBaseOnProp class
     */     
    protected class ActionSplitBaseOnProp extends ItemAction {     
    
        public ActionSplitBaseOnProp()
        {
            this(null);
        }

        public ActionSplitBaseOnProp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSplitBaseOnProp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitBaseOnProp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitBaseOnProp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentNoCostSplitEditUI.this, "ActionSplitBaseOnProp", "actionSplitBaseOnProp_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentNoCostSplitEditUI");
    }




}