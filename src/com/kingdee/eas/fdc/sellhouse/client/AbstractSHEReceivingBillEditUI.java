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
public abstract class AbstractSHEReceivingBillEditUI extends com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHEReceivingBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceipt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiptNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Purchase;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Seller;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SinPurchase;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Receipt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Invoice;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtInvoiceAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceiptNumber;
    protected com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSHEReceivingBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHEReceivingBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSinPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceipt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceiptNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Purchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Seller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SinPurchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Receipt = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Invoice = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInvoiceAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtReceiptNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPurchase.setName("contPurchase");
        this.contSellOrder.setName("contSellOrder");
        this.contSeller.setName("contSeller");
        this.contSinPurchase.setName("contSinPurchase");
        this.contReceipt.setName("contReceipt");
        this.contInvoice.setName("contInvoice");
        this.contInvoiceAmount.setName("contInvoiceAmount");
        this.contReceiptNumber.setName("contReceiptNumber");
        this.f7Purchase.setName("f7Purchase");
        this.f7SellOrder.setName("f7SellOrder");
        this.f7Seller.setName("f7Seller");
        this.f7SinPurchase.setName("f7SinPurchase");
        this.f7Receipt.setName("f7Receipt");
        this.f7Invoice.setName("f7Invoice");
        this.txtInvoiceAmount.setName("txtInvoiceAmount");
        this.txtReceiptNumber.setName("txtReceiptNumber");
        // CoreUI
		String tblEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fromMoneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"fromAccount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"stleCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"stleType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"stleNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"revAccount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"revBankAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"custAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"locAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"actRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"remissionAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"limitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"oppAccount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"supplyOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"supplyDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{fromMoneyDefine}</t:Cell><t:Cell>$Resource{fromAccount}</t:Cell><t:Cell>$Resource{stleCount}</t:Cell><t:Cell>$Resource{stleType}</t:Cell><t:Cell>$Resource{stleNumber}</t:Cell><t:Cell>$Resource{revAccount}</t:Cell><t:Cell>$Resource{revBankAccount}</t:Cell><t:Cell>$Resource{custAccount}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{locAmount}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actRevAmount}</t:Cell><t:Cell>$Resource{remissionAmount}</t:Cell><t:Cell>$Resource{limitAmount}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{oppAccount}</t:Cell><t:Cell>$Resource{supplyOrg}</t:Cell><t:Cell>$Resource{supplyDes}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblEntry.setFormatXml(resHelper.translateString("tblEntry",tblEntryStrXML));
        this.tblEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        
		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);		
        this.contRoomLongNumber.setVisible(false);
        this.f7SellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });		
        this.f7FdcCustomers.setEnabledMultiSelection(true);		
        this.f7Room.setDisplayFormat("$number$");		
        this.f7Room.setEditFormat("$number$");		
        this.f7Room.setCommitFormat("$number$");
        // contPurchase		
        this.contPurchase.setBoundLabelText(resHelper.getString("contPurchase.boundLabelText"));		
        this.contPurchase.setBoundLabelLength(100);		
        this.contPurchase.setBoundLabelUnderline(true);
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelLength(100);		
        this.contSellOrder.setBoundLabelUnderline(true);
        // contSeller		
        this.contSeller.setBoundLabelText(resHelper.getString("contSeller.boundLabelText"));		
        this.contSeller.setBoundLabelUnderline(true);		
        this.contSeller.setBoundLabelLength(100);
        // contSinPurchase		
        this.contSinPurchase.setBoundLabelText(resHelper.getString("contSinPurchase.boundLabelText"));		
        this.contSinPurchase.setBoundLabelLength(100);		
        this.contSinPurchase.setBoundLabelUnderline(true);
        // contReceipt		
        this.contReceipt.setBoundLabelText(resHelper.getString("contReceipt.boundLabelText"));		
        this.contReceipt.setBoundLabelLength(100);		
        this.contReceipt.setBoundLabelUnderline(true);
        // contInvoice		
        this.contInvoice.setBoundLabelText(resHelper.getString("contInvoice.boundLabelText"));		
        this.contInvoice.setBoundLabelLength(100);		
        this.contInvoice.setBoundLabelUnderline(true);		
        this.contInvoice.setEnabled(false);
        // contInvoiceAmount		
        this.contInvoiceAmount.setBoundLabelText(resHelper.getString("contInvoiceAmount.boundLabelText"));		
        this.contInvoiceAmount.setBoundLabelLength(100);		
        this.contInvoiceAmount.setBoundLabelUnderline(true);		
        this.contInvoiceAmount.setEnabled(false);
        // contReceiptNumber		
        this.contReceiptNumber.setBoundLabelText(resHelper.getString("contReceiptNumber.boundLabelText"));		
        this.contReceiptNumber.setBoundLabelLength(100);		
        this.contReceiptNumber.setBoundLabelUnderline(true);
        // f7Purchase		
        this.f7Purchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQueryF7");		
        this.f7Purchase.setCommitFormat("$number$");		
        this.f7Purchase.setDisplayFormat("$number$");		
        this.f7Purchase.setEditFormat("$number$");
        this.f7Purchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Purchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SellOrder		
        this.f7SellOrder.setDisplayFormat("$name$");		
        this.f7SellOrder.setEditFormat("$number$");		
        this.f7SellOrder.setCommitFormat("$number$");		
        this.f7SellOrder.setQueryInfo("joinQuery[com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");		
        this.f7SellOrder.setEnabled(false);
        // f7Seller		
        this.f7Seller.setDisplayFormat("$name$");		
        this.f7Seller.setEditFormat("$number$");		
        this.f7Seller.setCommitFormat("$number$");		
        this.f7Seller.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        // f7SinPurchase		
        this.f7SinPurchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseQuery");		
        this.f7SinPurchase.setCommitFormat("$number$");		
        this.f7SinPurchase.setDisplayFormat("$number$");		
        this.f7SinPurchase.setEditFormat("$number$");
        this.f7SinPurchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SinPurchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Receipt		
        this.f7Receipt.setDisplayFormat("$number$");		
        this.f7Receipt.setEditFormat("$number$");		
        this.f7Receipt.setCommitFormat("$number$");		
        this.f7Receipt.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");
        // f7Invoice		
        this.f7Invoice.setDisplayFormat("$number$");		
        this.f7Invoice.setEditFormat("$number$");		
        this.f7Invoice.setCommitFormat("$number$");		
        this.f7Invoice.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.InvoiceQuery");		
        this.f7Invoice.setEnabled(false);
        // txtInvoiceAmount		
        this.txtInvoiceAmount.setDataType(6);		
        this.txtInvoiceAmount.setPrecision(2);		
        this.txtInvoiceAmount.setSupportedEmpty(true);		
        this.txtInvoiceAmount.setEnabled(false);
        // txtReceiptNumber		
        this.txtReceiptNumber.setMaxLength(40);
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        contCreator.setBounds(new Rectangle(11, 548, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(11, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(371, 548, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 548, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contNumber.setBounds(new Rectangle(11, 38, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(11, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(371, 38, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(371, 38, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contDescription.setBounds(new Rectangle(10, 209, 991, 34));
        this.add(contDescription, new KDLayout.Constraints(10, 209, 991, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(11, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(11, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contAuditTime.setBounds(new Rectangle(371, 573, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(371, 573, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contCompany.setBounds(new Rectangle(11, 10, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(11, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(731, 10, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(731, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurrency.setBounds(new Rectangle(11, 66, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(11, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecAmount.setBounds(new Rectangle(731, 38, 270, 19));
        this.add(contRecAmount, new KDLayout.Constraints(731, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contExchangeRate.setBounds(new Rectangle(371, 66, 270, 19));
        this.add(contExchangeRate, new KDLayout.Constraints(371, 66, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contRecLocAmount.setBounds(new Rectangle(731, 66, 270, 19));
        this.add(contRecLocAmount, new KDLayout.Constraints(731, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRevBillType.setBounds(new Rectangle(11, 94, 270, 19));
        this.add(contRevBillType, new KDLayout.Constraints(11, 94, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRevBizType.setBounds(new Rectangle(371, 94, 270, 19));
        this.add(contRevBizType, new KDLayout.Constraints(371, 94, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        tblEntry.setBounds(new Rectangle(11, 276, 992, 264));
        this.add(tblEntry, new KDLayout.Constraints(11, 276, 992, 264, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSelectRevList.setBounds(new Rectangle(889, 251, 73, 19));
        this.add(btnSelectRevList, new KDLayout.Constraints(889, 251, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        contFdcCustomers.setBounds(new Rectangle(731, 94, 270, 19));
        this.add(contFdcCustomers, new KDLayout.Constraints(731, 94, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomer.setBounds(new Rectangle(731, 150, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(731, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoom.setBounds(new Rectangle(11, 122, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(11, 122, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomLongNumber.setBounds(new Rectangle(424, 127, 270, 19));
        this.add(contRoomLongNumber, new KDLayout.Constraints(424, 127, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contPurchase.setBounds(new Rectangle(371, 150, 270, 19));
        this.add(contPurchase, new KDLayout.Constraints(371, 150, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contSellOrder.setBounds(new Rectangle(371, 122, 270, 19));
        this.add(contSellOrder, new KDLayout.Constraints(371, 122, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contSeller.setBounds(new Rectangle(731, 122, 270, 19));
        this.add(contSeller, new KDLayout.Constraints(731, 122, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSinPurchase.setBounds(new Rectangle(12, 150, 270, 19));
        this.add(contSinPurchase, new KDLayout.Constraints(12, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contReceipt.setBounds(new Rectangle(12, 179, 270, 19));
        this.add(contReceipt, new KDLayout.Constraints(12, 179, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoice.setBounds(new Rectangle(371, 179, 270, 19));
        this.add(contInvoice, new KDLayout.Constraints(371, 179, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contInvoiceAmount.setBounds(new Rectangle(731, 179, 270, 19));
        this.add(contInvoiceAmount, new KDLayout.Constraints(731, 179, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReceiptNumber.setBounds(new Rectangle(731, 150, 270, 19));
        this.add(contReceiptNumber, new KDLayout.Constraints(731, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(f7OrgUnit);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCompany
        contCompany.setBoundEditor(f7Company);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contRecAmount
        contRecAmount.setBoundEditor(txtRecAmount);
        //contExchangeRate
        contExchangeRate.setBoundEditor(txtExchangeRate);
        //contRecLocAmount
        contRecLocAmount.setBoundEditor(txtRecLocAmount);
        //contRevBillType
        contRevBillType.setBoundEditor(comboRevBillType);
        //contRevBizType
        contRevBizType.setBoundEditor(comboRevBizType);
        //contFdcCustomers
        contFdcCustomers.setBoundEditor(f7FdcCustomers);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contRoomLongNumber
        contRoomLongNumber.setBoundEditor(txtRoomLongNumber);
        //contPurchase
        contPurchase.setBoundEditor(f7Purchase);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contSeller
        contSeller.setBoundEditor(f7Seller);
        //contSinPurchase
        contSinPurchase.setBoundEditor(f7SinPurchase);
        //contReceipt
        contReceipt.setBoundEditor(f7Receipt);
        //contInvoice
        contInvoice.setBoundEditor(f7Invoice);
        //contInvoiceAmount
        contInvoiceAmount.setBoundEditor(txtInvoiceAmount);
        //contReceiptNumber
        contReceiptNumber.setBoundEditor(txtReceiptNumber);

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
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuReceive);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnReceive);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("purchaseObj", com.kingdee.eas.fdc.sellhouse.PurchaseInfo.class, this.f7Purchase, "data");
		dataBinder.registerBinding("sincerityObj", com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo.class, this.f7SinPurchase, "data");
		dataBinder.registerBinding("receipt", com.kingdee.eas.fdc.sellhouse.ChequeInfo.class, this.f7Receipt, "data");
		dataBinder.registerBinding("invoice", com.kingdee.eas.fdc.sellhouse.InvoiceInfo.class, this.f7Invoice, "data");
		dataBinder.registerBinding("invoice.amount", java.math.BigDecimal.class, this.txtInvoiceAmount, "text");
		dataBinder.registerBinding("receiptNumber", String.class, this.txtReceiptNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SHEReceivingBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchaseObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sincerityObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receipt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoice.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiptNumber", ValidateHelper.ON_SAVE);    		
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
     * output f7SellProject_dataChanged method
     */
    protected void f7SellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Purchase_dataChanged method
     */
    protected void f7Purchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SinPurchase_dataChanged method
     */
    protected void f7SinPurchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("purchaseObj.*"));
        sic.add(new SelectorItemInfo("sincerityObj.*"));
        sic.add(new SelectorItemInfo("receipt.*"));
        sic.add(new SelectorItemInfo("invoice.*"));
        sic.add(new SelectorItemInfo("invoice.amount"));
        sic.add(new SelectorItemInfo("receiptNumber"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SHEReceivingBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}