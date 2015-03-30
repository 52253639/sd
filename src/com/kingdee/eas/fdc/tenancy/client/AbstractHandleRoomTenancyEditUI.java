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
public abstract class AbstractHandleRoomTenancyEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHandleRoomTenancyEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel tenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel tenancyBillCustomer;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tenancyRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBillName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBillState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyTerm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStratDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7tenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancyBillType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancyBillState;
    protected com.kingdee.bos.ctrl.swing.KDTextField f7SaleMan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancyTerm;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable customerTable;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomPanle;
    protected com.kingdee.bos.ctrl.swing.KDPanel attachPanle;
    protected com.kingdee.bos.ctrl.swing.KDPanel joinVoucherPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel customerMoveVoucherPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel payListPanel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable roomTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable attachTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton addJoinVoucher;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable joinVoucherTable1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable customerMoveVoucherTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton customerMoveVoucherButton1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable payListTable;
    protected com.kingdee.bos.ctrl.swing.KDTextField f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker textCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane DescriptionScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea textDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHandleTenancy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInterim;
    protected com.kingdee.eas.fdc.tenancy.HandleTenancyInfo editData = null;
    protected ActionHandleTenancy actionHandleTenancy = null;
    protected ActionInterim actionInterim = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionRemoveLine actionRemoveLine = null;
    /**
     * output class constructor
     */
    public AbstractHandleRoomTenancyEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractHandleRoomTenancyEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionHandleTenancy
        this.actionHandleTenancy = new ActionHandleTenancy(this);
        getActionManager().registerAction("actionHandleTenancy", actionHandleTenancy);
         this.actionHandleTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInterim
        this.actionInterim = new ActionInterim(this);
        getActionManager().registerAction("actionInterim", actionInterim);
         this.actionInterim.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLine
        this.actionRemoveLine = new ActionRemoveLine(this);
        getActionManager().registerAction("actionRemoveLine", actionRemoveLine);
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tenancyBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tenancyBillCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tenancyRoom = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contTenancyBillName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBillState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyTerm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStratDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7tenancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTenancyBillType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTenancyBillState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7SaleMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTenancyTerm = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.customerTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.roomPanle = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.attachPanle = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.joinVoucherPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.customerMoveVoucherPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.payListPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnRemoveLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.attachTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.addJoinVoucher = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.joinVoucherTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.customerMoveVoucherTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.customerMoveVoucherButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.payListTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.f7Creator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.textCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.DescriptionScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.textDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnHandleTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInterim = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tenancyBill.setName("tenancyBill");
        this.tenancyBillCustomer.setName("tenancyBillCustomer");
        this.tenancyRoom.setName("tenancyRoom");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contDescription.setName("contDescription");
        this.btnAddLine.setName("btnAddLine");
        this.contTenancyBillName.setName("contTenancyBillName");
        this.contTenancyBillType.setName("contTenancyBillType");
        this.contTenancyBillState.setName("contTenancyBillState");
        this.contSaleMan.setName("contSaleMan");
        this.contTenancyTerm.setName("contTenancyTerm");
        this.contStratDate.setName("contStratDate");
        this.contEndDate.setName("contEndDate");
        this.f7tenancyBill.setName("f7tenancyBill");
        this.txtTenancyBillType.setName("txtTenancyBillType");
        this.txtTenancyBillState.setName("txtTenancyBillState");
        this.f7SaleMan.setName("f7SaleMan");
        this.txtTenancyTerm.setName("txtTenancyTerm");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        this.customerTable.setName("customerTable");
        this.roomPanle.setName("roomPanle");
        this.attachPanle.setName("attachPanle");
        this.joinVoucherPanel1.setName("joinVoucherPanel1");
        this.customerMoveVoucherPanel1.setName("customerMoveVoucherPanel1");
        this.payListPanel1.setName("payListPanel1");
        this.roomTable.setName("roomTable");
        this.btnRemoveLine.setName("btnRemoveLine");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.attachTable.setName("attachTable");
        this.addJoinVoucher.setName("addJoinVoucher");
        this.joinVoucherTable1.setName("joinVoucherTable1");
        this.customerMoveVoucherTable1.setName("customerMoveVoucherTable1");
        this.customerMoveVoucherButton1.setName("customerMoveVoucherButton1");
        this.payListTable.setName("payListTable");
        this.f7Creator.setName("f7Creator");
        this.textCreateTime.setName("textCreateTime");
        this.DescriptionScrollPane1.setName("DescriptionScrollPane1");
        this.textDescription.setName("textDescription");
        this.btnHandleTenancy.setName("btnHandleTenancy");
        this.btnInterim.setName("btnInterim");
        // CoreUI		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));
        // tenancyBill		
        this.tenancyBill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("tenancyBill.border.title")));
        // tenancyBillCustomer		
        this.tenancyBillCustomer.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("tenancyBillCustomer.border.title")));
        // tenancyRoom
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // btnAddLine
        this.btnAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddLine.setText(resHelper.getString("btnAddLine.text"));
        // contTenancyBillName		
        this.contTenancyBillName.setBoundLabelText(resHelper.getString("contTenancyBillName.boundLabelText"));		
        this.contTenancyBillName.setBoundLabelLength(100);		
        this.contTenancyBillName.setBoundLabelUnderline(true);
        // contTenancyBillType		
        this.contTenancyBillType.setBoundLabelText(resHelper.getString("contTenancyBillType.boundLabelText"));		
        this.contTenancyBillType.setBoundLabelLength(100);		
        this.contTenancyBillType.setBoundLabelUnderline(true);
        // contTenancyBillState		
        this.contTenancyBillState.setBoundLabelText(resHelper.getString("contTenancyBillState.boundLabelText"));		
        this.contTenancyBillState.setBoundLabelLength(100);		
        this.contTenancyBillState.setBoundLabelUnderline(true);		
        this.contTenancyBillState.setVisible(false);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelUnderline(true);		
        this.contSaleMan.setBoundLabelLength(100);
        // contTenancyTerm		
        this.contTenancyTerm.setBoundLabelText(resHelper.getString("contTenancyTerm.boundLabelText"));		
        this.contTenancyTerm.setBoundLabelUnderline(true);		
        this.contTenancyTerm.setBoundLabelLength(100);
        // contStratDate		
        this.contStratDate.setBoundLabelText(resHelper.getString("contStratDate.boundLabelText"));		
        this.contStratDate.setBoundLabelLength(100);		
        this.contStratDate.setBoundLabelUnderline(true);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelUnderline(true);		
        this.contEndDate.setBoundLabelLength(100);
        // f7tenancyBill		
        this.f7tenancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7tenancyBill.setDisplayFormat("$name$");		
        this.f7tenancyBill.setEditFormat("$number$");		
        this.f7tenancyBill.setCommitFormat("$number$");		
        this.f7tenancyBill.setEnabled(false);
        this.f7tenancyBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7tenancyBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTenancyBillType		
        this.txtTenancyBillType.setEnabled(false);
        // txtTenancyBillState		
        this.txtTenancyBillState.setEnabled(false);
        // f7SaleMan		
        this.f7SaleMan.setEnabled(false);
        // txtTenancyTerm		
        this.txtTenancyTerm.setEnabled(false);
        // pkStartDate		
        this.pkStartDate.setEnabled(false);
        // pkEndDate		
        this.pkEndDate.setEnabled(false);
        // customerTable
		String customerTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"proporation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"postalCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"communicateTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"certificateId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"communicateAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{proporation}</t:Cell><t:Cell>$Resource{customerName}</t:Cell><t:Cell>$Resource{postalCode}</t:Cell><t:Cell>$Resource{communicateTel}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateId}</t:Cell><t:Cell>$Resource{communicateAddress}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.customerTable.setFormatXml(resHelper.translateString("customerTable",customerTableStrXML));

        

        // roomPanle		
        this.roomPanle.setBorder(null);
        // attachPanle		
        this.attachPanle.setVisible(false);
        // joinVoucherPanel1		
        this.joinVoucherPanel1.setVisible(false);
        // customerMoveVoucherPanel1		
        this.customerMoveVoucherPanel1.setVisible(false);
        // payListPanel1		
        this.payListPanel1.setVisible(false);		
        this.payListPanel1.setVerifyInputWhenFocusTarget(false);		
        this.payListPanel1.setEnabled(false);
        // roomTable
		String roomTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"handleType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"objectNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"transactState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"finishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{handleType}</t:Cell><t:Cell>$Resource{objectNumber}</t:Cell><t:Cell>$Resource{tenancyType}</t:Cell><t:Cell>$Resource{transactState}</t:Cell><t:Cell>$Resource{finishDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.roomTable.setFormatXml(resHelper.translateString("roomTable",roomTableStrXML));
        this.roomTable.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    roomTable_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnRemoveLine
        this.btnRemoveLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveLine.setText(resHelper.getString("btnRemoveLine.text"));
        // btnSelectRoom		
        this.btnSelectRoom.setText(resHelper.getString("btnSelectRoom.text"));		
        this.btnSelectRoom.setEnabled(false);		
        this.btnSelectRoom.setVisible(false);
        this.btnSelectRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // attachTable
		String attachTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"handleType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"transactState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"finishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{handleType}</t:Cell><t:Cell>$Resource{attachNumber}</t:Cell><t:Cell>$Resource{transactState}</t:Cell><t:Cell>$Resource{finishDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.attachTable.setFormatXml(resHelper.translateString("attachTable",attachTableStrXML));

        

        // addJoinVoucher		
        this.addJoinVoucher.setText(resHelper.getString("addJoinVoucher.text"));		
        this.addJoinVoucher.setToolTipText(resHelper.getString("addJoinVoucher.toolTipText"));
        this.addJoinVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    addJoinVoucher_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // joinVoucherTable1
		String joinVoucherTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"joinType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"joinStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{joinType}</t:Cell><t:Cell>$Resource{joinStatus}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizData}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{contractNo}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.joinVoucherTable1.setFormatXml(resHelper.translateString("joinVoucherTable1",joinVoucherTable1StrXML));

        

        // customerMoveVoucherTable1
		String customerMoveVoucherTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accepter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{accepter}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.customerMoveVoucherTable1.setFormatXml(resHelper.translateString("customerMoveVoucherTable1",customerMoveVoucherTable1StrXML));

        

        // customerMoveVoucherButton1		
        this.customerMoveVoucherButton1.setText(resHelper.getString("customerMoveVoucherButton1.text"));		
        this.customerMoveVoucherButton1.setToolTipText(resHelper.getString("customerMoveVoucherButton1.toolTipText"));
        this.customerMoveVoucherButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    customerMoveVoucherButton1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // payListTable
		String payListTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>#.##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#.##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>#.##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actPayDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"actRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"limitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{actPayDate}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actRevAmount}</t:Cell><t:Cell>$Resource{limitAmount}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.payListTable.setFormatXml(resHelper.translateString("payListTable",payListTableStrXML));

        

        // f7Creator		
        this.f7Creator.setEnabled(false);
        // textCreateTime		
        this.textCreateTime.setEnabled(false);
        // DescriptionScrollPane1
        // textDescription
        // btnHandleTenancy
        this.btnHandleTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHandleTenancy.setText(resHelper.getString("btnHandleTenancy.text"));		
        this.btnHandleTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_persondistribute"));
        // btnInterim
        this.btnInterim.setAction((IItemAction)ActionProxyFactory.getProxy(actionInterim, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInterim.setText(resHelper.getString("btnInterim.text"));
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
        this.setBounds(new Rectangle(10, 10, 920, 550));
        this.setLayout(null);
        tenancyBill.setBounds(new Rectangle(11, 4, 902, 95));
        this.add(tenancyBill, null);
        tenancyBillCustomer.setBounds(new Rectangle(10, 107, 903, 158));
        this.add(tenancyBillCustomer, null);
        tenancyRoom.setBounds(new Rectangle(9, 271, 902, 177));
        this.add(tenancyRoom, null);
        contCreator.setBounds(new Rectangle(11, 522, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(643, 522, 270, 19));
        this.add(contCreateTime, null);
        contDescription.setBounds(new Rectangle(11, 453, 902, 57));
        this.add(contDescription, null);
        btnAddLine.setBounds(new Rectangle(520, 520, 78, 19));
        this.add(btnAddLine, null);
        //tenancyBill
        tenancyBill.setLayout(null);        contTenancyBillName.setBounds(new Rectangle(15, 22, 270, 19));
        tenancyBill.add(contTenancyBillName, null);
        contTenancyBillType.setBounds(new Rectangle(314, 22, 270, 19));
        tenancyBill.add(contTenancyBillType, null);
        contTenancyBillState.setBounds(new Rectangle(303, 88, 270, 19));
        tenancyBill.add(contTenancyBillState, null);
        contSaleMan.setBounds(new Rectangle(611, 60, 270, 19));
        tenancyBill.add(contSaleMan, null);
        contTenancyTerm.setBounds(new Rectangle(611, 22, 270, 19));
        tenancyBill.add(contTenancyTerm, null);
        contStratDate.setBounds(new Rectangle(15, 60, 270, 19));
        tenancyBill.add(contStratDate, null);
        contEndDate.setBounds(new Rectangle(314, 60, 270, 19));
        tenancyBill.add(contEndDate, null);
        //contTenancyBillName
        contTenancyBillName.setBoundEditor(f7tenancyBill);
        //contTenancyBillType
        contTenancyBillType.setBoundEditor(txtTenancyBillType);
        //contTenancyBillState
        contTenancyBillState.setBoundEditor(txtTenancyBillState);
        //contSaleMan
        contSaleMan.setBoundEditor(f7SaleMan);
        //contTenancyTerm
        contTenancyTerm.setBoundEditor(txtTenancyTerm);
        //contStratDate
        contStratDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //tenancyBillCustomer
        tenancyBillCustomer.setLayout(null);        customerTable.setBounds(new Rectangle(13, 18, 879, 120));
        tenancyBillCustomer.add(customerTable, null);
        //tenancyRoom
        tenancyRoom.add(roomPanle, resHelper.getString("roomPanle.constraints"));
        tenancyRoom.add(attachPanle, resHelper.getString("attachPanle.constraints"));
        tenancyRoom.add(joinVoucherPanel1, resHelper.getString("joinVoucherPanel1.constraints"));
        tenancyRoom.add(customerMoveVoucherPanel1, resHelper.getString("customerMoveVoucherPanel1.constraints"));
        tenancyRoom.add(payListPanel1, resHelper.getString("payListPanel1.constraints"));
        //roomPanle
        roomPanle.setLayout(null);        roomTable.setBounds(new Rectangle(11, 27, 876, 110));
        roomPanle.add(roomTable, null);
        btnRemoveLine.setBounds(new Rectangle(771, 4, 98, 19));
        roomPanle.add(btnRemoveLine, null);
        btnSelectRoom.setBounds(new Rectangle(662, 4, 98, 19));
        roomPanle.add(btnSelectRoom, null);
        //attachPanle
        attachPanle.setLayout(null);        attachTable.setBounds(new Rectangle(12, 8, 875, 129));
        attachPanle.add(attachTable, null);
        //joinVoucherPanel1
        joinVoucherPanel1.setLayout(null);        addJoinVoucher.setBounds(new Rectangle(10, 3, 86, 19));
        joinVoucherPanel1.add(addJoinVoucher, null);
        joinVoucherTable1.setBounds(new Rectangle(11, 23, 881, 115));
        joinVoucherPanel1.add(joinVoucherTable1, null);
        //customerMoveVoucherPanel1
        customerMoveVoucherPanel1.setLayout(null);        customerMoveVoucherTable1.setBounds(new Rectangle(11, 21, 874, 117));
        customerMoveVoucherPanel1.add(customerMoveVoucherTable1, null);
        customerMoveVoucherButton1.setBounds(new Rectangle(10, 1, 84, 19));
        customerMoveVoucherPanel1.add(customerMoveVoucherButton1, null);
        //payListPanel1
payListPanel1.setLayout(new BorderLayout(0, 0));        payListPanel1.add(payListTable, BorderLayout.CENTER);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(textCreateTime);
        //contDescription
        contDescription.setBoundEditor(DescriptionScrollPane1);
        //DescriptionScrollPane1
        DescriptionScrollPane1.getViewport().add(textDescription, null);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnHandleTenancy);
        this.toolBar.add(btnInterim);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "userObject");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.textCreateTime, "value");
		dataBinder.registerBinding("description", String.class, this.textDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.HandleRoomTenancyEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.HandleTenancyInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
     * output f7tenancyBill_dataChanged method
     */
    protected void f7tenancyBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output roomTable_editStopped method
     */
    protected void roomTable_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output addJoinVoucher_actionPerformed method
     */
    protected void addJoinVoucher_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output customerMoveVoucherButton1_actionPerformed method
     */
    protected void customerMoveVoucherButton1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("creator"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionHandleTenancy_actionPerformed method
     */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInterim_actionPerformed method
     */
    public void actionInterim_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveLine_actionPerformed method
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionInterim(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInterim() {
    	return false;
    }
	public RequestContext prepareActionAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLine() {
    	return false;
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractHandleRoomTenancyEditUI.this, "ActionHandleTenancy", "actionHandleTenancy_actionPerformed", e);
        }
    }

    /**
     * output ActionInterim class
     */     
    protected class ActionInterim extends ItemAction {     
    
        public ActionInterim()
        {
            this(null);
        }

        public ActionInterim(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInterim.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInterim.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInterim.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHandleRoomTenancyEditUI.this, "ActionInterim", "actionInterim_actionPerformed", e);
        }
    }

    /**
     * output ActionAddLine class
     */     
    protected class ActionAddLine extends ItemAction {     
    
        public ActionAddLine()
        {
            this(null);
        }

        public ActionAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHandleRoomTenancyEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveLine class
     */     
    protected class ActionRemoveLine extends ItemAction {     
    
        public ActionRemoveLine()
        {
            this(null);
        }

        public ActionRemoveLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHandleRoomTenancyEditUI.this, "ActionRemoveLine", "actionRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "HandleRoomTenancyEditUI");
    }




}