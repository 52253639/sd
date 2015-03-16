///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import org.apache.log4j.*;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
//import javax.swing.border.*;
//import javax.swing.BorderFactory;
//import javax.swing.event.*;
//import javax.swing.KeyStroke;
//
//import com.kingdee.bos.ctrl.swing.*;
//import com.kingdee.bos.ctrl.kdf.table.*;
//import com.kingdee.bos.ctrl.kdf.data.event.*;
//import com.kingdee.bos.dao.*;
//import com.kingdee.bos.dao.query.*;
//import com.kingdee.bos.metadata.*;
//import com.kingdee.bos.metadata.entity.*;
//import com.kingdee.bos.ui.face.*;
//import com.kingdee.bos.ui.util.ResourceBundleHelper;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.bos.service.ServiceContext;
//import com.kingdee.jdbc.rowset.IRowSet;
//import com.kingdee.util.enums.EnumUtils;
//import com.kingdee.bos.ui.face.UIRuleUtil;
//import com.kingdee.bos.ctrl.swing.event.*;
//import com.kingdee.bos.ctrl.kdf.table.event.*;
//import com.kingdee.bos.ctrl.extendcontrols.*;
//import com.kingdee.bos.ctrl.kdf.util.render.*;
//import com.kingdee.bos.ui.face.IItemAction;
//import com.kingdee.eas.framework.batchHandler.RequestContext;
//import com.kingdee.bos.ui.util.IUIActionPostman;
//import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
//import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
//import com.kingdee.bos.appframework.validator.ValidateHelper;
//import com.kingdee.bos.appframework.uip.UINavigator;
//
//
///**
// * output class name
// */
//public abstract class AbstractInsteadCollectOutBillEditUI extends com.kingdee.eas.fdc.propertymgmt.client.PPMProjectBillEditUI
//{
//    private static final Logger logger = CoreUIObject.getLogger(AbstractInsteadCollectOutBillEditUI.class);
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
//    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
//	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyType;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyCollect;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyCollected;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyReturned;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyInsteadCur;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmanInsteadPay;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdateInsteadPay;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer elseEntryID;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer sellProject;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
//    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
//    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
//    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
//    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyType;
//    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmoneyCollect;
//    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmoneyCollected;
//    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmoneyReturned;
//    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmoneyInsteadCur;
//    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmanInsteadPay;
//    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdateInsteadPay;
//    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtElseEntry;
//    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
//    protected com.kingdee.bos.ctrl.swing.KDTextField txtInsteaded;
//    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
//    protected com.kingdee.bos.ctrl.swing.KDTextField txtUnit;
//    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
//    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
//    protected com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo editData = null;
//    /**
//     * output class constructor
//     */
//    public AbstractInsteadCollectOutBillEditUI() throws Exception
//    {
//        super();
//        this.defaultObjectName = "editData";
//        jbInit();
//        
//        initUIP();
//    }
//
//    /**
//     * output jbInit method
//     */
//    private void jbInit() throws Exception
//    {
//        this.resHelper = new ResourceBundleHelper(AbstractInsteadCollectOutBillEditUI.class.getName());
//        this.setUITitle(resHelper.getString("this.title"));
//        //actionSubmit
//        String _tempStr = null;
//        actionSubmit.setEnabled(true);
//        actionSubmit.setDaemonRun(false);
//
//        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
//        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
//        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
//        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//        _tempStr = resHelper.getString("ActionSubmit.NAME");
//        actionSubmit.putValue(ItemAction.NAME, _tempStr);
//        this.actionSubmit.setBindWorkFlow(true);
//         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
//         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
//         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
//        //actionPrint
//        actionPrint.setEnabled(true);
//        actionPrint.setDaemonRun(false);
//
//        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
//        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
//        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
//        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//        _tempStr = resHelper.getString("ActionPrint.NAME");
//        actionPrint.putValue(ItemAction.NAME, _tempStr);
//         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
//         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
//         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
//        //actionPrintPreview
//        actionPrintPreview.setEnabled(true);
//        actionPrintPreview.setDaemonRun(false);
//
//        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
//        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
//        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
//        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
//        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
//         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
//         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
//         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
//        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
//        this.contmoneyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contmoneyCollect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contmoneyCollected = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contmoneyReturned = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contmoneyInsteadCur = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contmanInsteadPay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.contdateInsteadPay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.elseEntryID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.sellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
//        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
//        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
//        this.prmtMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
//        this.txtmoneyCollect = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
//        this.txtmoneyCollected = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
//        this.txtmoneyReturned = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
//        this.txtmoneyInsteadCur = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
//        this.prmtmanInsteadPay = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
//        this.pkdateInsteadPay = new com.kingdee.bos.ctrl.swing.KDDatePicker();
//        this.prmtElseEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
//        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
//        this.txtInsteaded = new com.kingdee.bos.ctrl.swing.KDTextField();
//        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
//        this.txtUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
//        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
//        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
//        this.contNumber.setName("contNumber");
//        this.contDescription.setName("contDescription");
//        this.kdtEntrys.setName("kdtEntrys");
//        this.contmoneyType.setName("contmoneyType");
//        this.contmoneyCollect.setName("contmoneyCollect");
//        this.contmoneyCollected.setName("contmoneyCollected");
//        this.contmoneyReturned.setName("contmoneyReturned");
//        this.contmoneyInsteadCur.setName("contmoneyInsteadCur");
//        this.contmanInsteadPay.setName("contmanInsteadPay");
//        this.contdateInsteadPay.setName("contdateInsteadPay");
//        this.elseEntryID.setName("elseEntryID");
//        this.sellProject.setName("sellProject");
//        this.kDLabelContainer1.setName("kDLabelContainer1");
//        this.kDLabelContainer2.setName("kDLabelContainer2");
//        this.kDLabelContainer3.setName("kDLabelContainer3");
//        this.kDLabelContainer4.setName("kDLabelContainer4");
//        this.kDLabelContainer5.setName("kDLabelContainer5");
//        this.txtNumber.setName("txtNumber");
//        this.txtRemark.setName("txtRemark");
//        this.prmtMoneyType.setName("prmtMoneyType");
//        this.txtmoneyCollect.setName("txtmoneyCollect");
//        this.txtmoneyCollected.setName("txtmoneyCollected");
//        this.txtmoneyReturned.setName("txtmoneyReturned");
//        this.txtmoneyInsteadCur.setName("txtmoneyInsteadCur");
//        this.prmtmanInsteadPay.setName("prmtmanInsteadPay");
//        this.pkdateInsteadPay.setName("pkdateInsteadPay");
//        this.prmtElseEntry.setName("prmtElseEntry");
//        this.prmtSellProject.setName("prmtSellProject");
//        this.txtInsteaded.setName("txtInsteaded");
//        this.txtBuilding.setName("txtBuilding");
//        this.txtUnit.setName("txtUnit");
//        this.txtRoom.setName("txtRoom");
//        this.txtCustomer.setName("txtCustomer");
//        // CoreUI		
//        this.btnFirst.setEnabled(false);		
//        this.btnFirst.setVisible(false);		
//        this.btnPre.setEnabled(false);		
//        this.btnPre.setVisible(false);		
//        this.btnNext.setEnabled(false);		
//        this.btnNext.setVisible(false);		
//        this.btnLast.setEnabled(false);		
//        this.btnLast.setVisible(false);		
//        this.btnPrint.setEnabled(false);		
//        this.btnPrint.setVisible(false);		
//        this.btnPrintPreview.setEnabled(false);		
//        this.btnPrintPreview.setVisible(false);		
//        this.btnAttachment.setEnabled(false);		
//        this.btnAttachment.setVisible(false);		
//        this.btnAddLine.setVisible(false);		
//        this.btnInsertLine.setVisible(false);		
//        this.btnRemoveLine.setVisible(false);		
//        this.btnCreateFrom.setEnabled(false);		
//        this.btnCreateFrom.setVisible(false);		
//        this.btnTraceUp.setVisible(false);		
//        this.btnTraceDown.setVisible(false);		
//        this.btnAuditResult.setVisible(false);		
//        this.separator1.setVisible(false);		
//        this.separator3.setVisible(false);		
//        this.menuItemTraceUp.setVisible(false);		
//        this.menuItemTraceDown.setVisible(false);		
//        this.btnMultiapprove.setEnabled(false);		
//        this.btnMultiapprove.setVisible(false);		
//        this.menuItemViewSubmitProccess.setVisible(false);		
//        this.menuItemViewDoProccess.setVisible(false);		
//        this.btnNextPerson.setEnabled(false);		
//        this.btnNextPerson.setVisible(false);		
//        this.menuItemAuditResult.setVisible(false);		
//        this.btnWorkFlowG.setEnabled(false);		
//        this.btnWorkFlowG.setVisible(false);		
//        this.menuItemAddLine.setVisible(false);		
//        this.menuItemInsertLine.setVisible(false);		
//        this.menuItemRemoveLine.setVisible(false);		
//        this.btnCreateTo.setEnabled(false);		
//        this.menuItemCreateTo.setVisible(true);		
//        this.btnCalculator.setEnabled(false);		
//        this.btnCalculator.setVisible(false);
//        // contNumber		
//        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
//        this.contNumber.setBoundLabelLength(100);		
//        this.contNumber.setBoundLabelUnderline(true);		
//        this.contNumber.setBoundLabelAlignment(7);		
//        this.contNumber.setVisible(true);		
//        this.contNumber.setForeground(new java.awt.Color(0,0,0));
//        // contDescription		
//        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
//        this.contDescription.setBoundLabelLength(100);		
//        this.contDescription.setBoundLabelUnderline(true);
//        // kdtEntrys
//		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
//		
//        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));		
//        this.kdtEntrys.setVisible(false);
//
//                this.kdtEntrys.putBindContents("editData",new String[] {"id"});
//
//
//        this.kdtEntrys.checkParsed();
//        // contmoneyType		
//        this.contmoneyType.setBoundLabelText(resHelper.getString("contmoneyType.boundLabelText"));		
//        this.contmoneyType.setBoundLabelLength(100);		
//        this.contmoneyType.setBoundLabelUnderline(true);		
//        this.contmoneyType.setVisible(true);
//        // contmoneyCollect		
//        this.contmoneyCollect.setBoundLabelText(resHelper.getString("contmoneyCollect.boundLabelText"));		
//        this.contmoneyCollect.setBoundLabelLength(100);		
//        this.contmoneyCollect.setBoundLabelUnderline(true);		
//        this.contmoneyCollect.setVisible(true);		
//        this.contmoneyCollect.setBoundLabelAlignment(7);		
//        this.contmoneyCollect.setForeground(new java.awt.Color(0,0,0));
//        // contmoneyCollected		
//        this.contmoneyCollected.setBoundLabelText(resHelper.getString("contmoneyCollected.boundLabelText"));		
//        this.contmoneyCollected.setBoundLabelLength(100);		
//        this.contmoneyCollected.setBoundLabelUnderline(true);		
//        this.contmoneyCollected.setVisible(true);		
//        this.contmoneyCollected.setBoundLabelAlignment(7);		
//        this.contmoneyCollected.setForeground(new java.awt.Color(0,0,0));
//        // contmoneyReturned		
//        this.contmoneyReturned.setBoundLabelText(resHelper.getString("contmoneyReturned.boundLabelText"));		
//        this.contmoneyReturned.setBoundLabelLength(100);		
//        this.contmoneyReturned.setBoundLabelUnderline(true);		
//        this.contmoneyReturned.setVisible(true);		
//        this.contmoneyReturned.setBoundLabelAlignment(7);		
//        this.contmoneyReturned.setForeground(new java.awt.Color(0,0,0));
//        // contmoneyInsteadCur		
//        this.contmoneyInsteadCur.setBoundLabelText(resHelper.getString("contmoneyInsteadCur.boundLabelText"));		
//        this.contmoneyInsteadCur.setBoundLabelLength(100);		
//        this.contmoneyInsteadCur.setBoundLabelUnderline(true);		
//        this.contmoneyInsteadCur.setVisible(true);		
//        this.contmoneyInsteadCur.setBoundLabelAlignment(7);		
//        this.contmoneyInsteadCur.setForeground(new java.awt.Color(0,0,0));
//        // contmanInsteadPay		
//        this.contmanInsteadPay.setBoundLabelText(resHelper.getString("contmanInsteadPay.boundLabelText"));		
//        this.contmanInsteadPay.setBoundLabelLength(100);		
//        this.contmanInsteadPay.setBoundLabelUnderline(true);		
//        this.contmanInsteadPay.setVisible(true);		
//        this.contmanInsteadPay.setBoundLabelAlignment(7);		
//        this.contmanInsteadPay.setForeground(new java.awt.Color(0,0,0));
//        // contdateInsteadPay		
//        this.contdateInsteadPay.setBoundLabelText(resHelper.getString("contdateInsteadPay.boundLabelText"));		
//        this.contdateInsteadPay.setBoundLabelLength(100);		
//        this.contdateInsteadPay.setBoundLabelUnderline(true);		
//        this.contdateInsteadPay.setVisible(true);		
//        this.contdateInsteadPay.setBoundLabelAlignment(7);		
//        this.contdateInsteadPay.setForeground(new java.awt.Color(0,0,0));
//        // elseEntryID		
//        this.elseEntryID.setBoundLabelText(resHelper.getString("elseEntryID.boundLabelText"));		
//        this.elseEntryID.setVisible(false);		
//        this.elseEntryID.setEnabled(false);
//        // sellProject		
//        this.sellProject.setBoundLabelText(resHelper.getString("sellProject.boundLabelText"));		
//        this.sellProject.setBoundLabelUnderline(true);
//        // kDLabelContainer1		
//        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
//        this.kDLabelContainer1.setBoundLabelLength(100);		
//        this.kDLabelContainer1.setBoundLabelUnderline(true);
//        // kDLabelContainer2		
//        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
//        this.kDLabelContainer2.setBoundLabelLength(100);		
//        this.kDLabelContainer2.setBoundLabelUnderline(true);
//        // kDLabelContainer3		
//        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
//        this.kDLabelContainer3.setBoundLabelLength(100);		
//        this.kDLabelContainer3.setBoundLabelUnderline(true);
//        // kDLabelContainer4		
//        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
//        this.kDLabelContainer4.setBoundLabelLength(100);		
//        this.kDLabelContainer4.setBoundLabelUnderline(true);
//        // kDLabelContainer5		
//        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
//        this.kDLabelContainer5.setBoundLabelUnderline(true);		
//        this.kDLabelContainer5.setBoundLabelLength(100);
//        // txtNumber		
//        this.txtNumber.setMaxLength(80);		
//        this.txtNumber.setVisible(true);		
//        this.txtNumber.setEnabled(true);		
//        this.txtNumber.setHorizontalAlignment(2);		
//        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
//        this.txtNumber.setRequired(true);
//        // txtRemark		
//        this.txtRemark.setMaxLength(255);
//        // prmtMoneyType		
//        this.prmtMoneyType.setEnabled(false);
//        // txtmoneyCollect		
//        this.txtmoneyCollect.setVisible(true);		
//        this.txtmoneyCollect.setHorizontalAlignment(2);		
//        this.txtmoneyCollect.setDataType(1);		
//        this.txtmoneyCollect.setSupportedEmpty(true);		
//        this.txtmoneyCollect.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
//        this.txtmoneyCollect.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
//        this.txtmoneyCollect.setPrecision(2);		
//        this.txtmoneyCollect.setRequired(false);		
//        this.txtmoneyCollect.setEnabled(false);		
//        this.txtmoneyCollect.setForeground(new java.awt.Color(0,0,0));
//        // txtmoneyCollected		
//        this.txtmoneyCollected.setVisible(true);		
//        this.txtmoneyCollected.setHorizontalAlignment(2);		
//        this.txtmoneyCollected.setDataType(1);		
//        this.txtmoneyCollected.setSupportedEmpty(true);		
//        this.txtmoneyCollected.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
//        this.txtmoneyCollected.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
//        this.txtmoneyCollected.setPrecision(2);		
//        this.txtmoneyCollected.setRequired(false);		
//        this.txtmoneyCollected.setEnabled(false);		
//        this.txtmoneyCollected.setForeground(new java.awt.Color(0,0,0));
//        // txtmoneyReturned		
//        this.txtmoneyReturned.setVisible(true);		
//        this.txtmoneyReturned.setHorizontalAlignment(2);		
//        this.txtmoneyReturned.setDataType(1);		
//        this.txtmoneyReturned.setSupportedEmpty(true);		
//        this.txtmoneyReturned.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
//        this.txtmoneyReturned.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
//        this.txtmoneyReturned.setPrecision(2);		
//        this.txtmoneyReturned.setRequired(false);		
//        this.txtmoneyReturned.setEnabled(false);		
//        this.txtmoneyReturned.setForeground(new java.awt.Color(0,0,0));
//        // txtmoneyInsteadCur		
//        this.txtmoneyInsteadCur.setVisible(true);		
//        this.txtmoneyInsteadCur.setHorizontalAlignment(2);		
//        this.txtmoneyInsteadCur.setDataType(1);		
//        this.txtmoneyInsteadCur.setSupportedEmpty(true);		
//        this.txtmoneyInsteadCur.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
//        this.txtmoneyInsteadCur.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
//        this.txtmoneyInsteadCur.setPrecision(2);		
//        this.txtmoneyInsteadCur.setRequired(true);		
//        this.txtmoneyInsteadCur.setEnabled(true);		
//        this.txtmoneyInsteadCur.setForeground(new java.awt.Color(0,0,0));
//        this.txtmoneyInsteadCur.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
//            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
//                try {
//                    txtmoneyInsteadCur_dataChanged(e);
//                } catch (Exception exc) {
//                    handUIException(exc);
//                } finally {
//                }
//            }
//        });
//        this.txtmoneyInsteadCur.addFocusListener(new java.awt.event.FocusAdapter() {
//            public void focusLost(java.awt.event.FocusEvent e) {
//                try {
//                    txtmoneyInsteadCur_focusLost(e);
//                } catch(Exception exc) {
//                    handUIException(exc);
//                }
//            }
//        });
//        // prmtmanInsteadPay		
//        this.prmtmanInsteadPay.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
//        this.prmtmanInsteadPay.setVisible(true);		
//        this.prmtmanInsteadPay.setEditable(true);		
//        this.prmtmanInsteadPay.setDisplayFormat("$name$");		
//        this.prmtmanInsteadPay.setEditFormat("$number$");		
//        this.prmtmanInsteadPay.setCommitFormat("$number$");		
//        this.prmtmanInsteadPay.setRequired(true);		
//        this.prmtmanInsteadPay.setEnabled(true);		
//        this.prmtmanInsteadPay.setForeground(new java.awt.Color(0,0,0));
//        // pkdateInsteadPay		
//        this.pkdateInsteadPay.setVisible(true);		
//        this.pkdateInsteadPay.setRequired(false);		
//        this.pkdateInsteadPay.setEnabled(true);		
//        this.pkdateInsteadPay.setForeground(new java.awt.Color(0,0,0));
//        // prmtElseEntry		
//        this.prmtElseEntry.setDisplayFormat("$id$");		
//        this.prmtElseEntry.setEditFormat("$number$");		
//        this.prmtElseEntry.setCommitFormat("$number$");		
//        this.prmtElseEntry.setVisible(false);		
//        this.prmtElseEntry.setEnabled(false);
//        // prmtSellProject		
//        this.prmtSellProject.setEnabled(false);
//        // txtInsteaded		
//        this.txtInsteaded.setEnabled(false);		
//        this.txtInsteaded.setHorizontalAlignment(4);
//        // txtBuilding		
//        this.txtBuilding.setEnabled(false);
//        // txtUnit		
//        this.txtUnit.setEnabled(false);
//        // txtRoom		
//        this.txtRoom.setEnabled(false);
//        // txtCustomer		
//        this.txtCustomer.setEnabled(false);
//        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtmoneyInsteadCur,prmtmanInsteadPay,pkdateInsteadPay,txtRemark}));
//        this.setFocusCycleRoot(true);
//		//Register control's property binding
//		registerBindings();
//		registerUIState();
//
//
//    }
//
//	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
//		java.util.List list = new java.util.ArrayList();
//		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
//		if (bars != null) {
//			list.addAll(java.util.Arrays.asList(bars));
//		}
//		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
//	}
//
//
//
//
//    /**
//     * output initUIContentLayout method
//     */
//    public void initUIContentLayout()
//    {
//        this.setBounds(new Rectangle(0, 0, 620, 300));
//        this.setLayout(new KDLayout());
//        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 620, 300));
//        contNumber.setBounds(new Rectangle(20, 6, 270, 19));
//        this.add(contNumber, new KDLayout.Constraints(20, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contDescription.setBounds(new Rectangle(20, 216, 572, 50));
//        this.add(contDescription, new KDLayout.Constraints(20, 216, 572, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
//        kdtEntrys.setBounds(new Rectangle(323, 275, 37, 12));
//        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryInfo(),null,false);
//        this.add(kdtEntrys_detailPanel, new KDLayout.Constraints(323, 275, 37, 12, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
//        contmoneyType.setBounds(new Rectangle(20, 96, 270, 19));
//        this.add(contmoneyType, new KDLayout.Constraints(20, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contmoneyCollect.setBounds(new Rectangle(321, 96, 270, 19));
//        this.add(contmoneyCollect, new KDLayout.Constraints(321, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contmoneyCollected.setBounds(new Rectangle(20, 125, 270, 19));
//        this.add(contmoneyCollected, new KDLayout.Constraints(20, 125, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contmoneyReturned.setBounds(new Rectangle(321, 125, 270, 19));
//        this.add(contmoneyReturned, new KDLayout.Constraints(321, 125, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contmoneyInsteadCur.setBounds(new Rectangle(321, 154, 270, 19));
//        this.add(contmoneyInsteadCur, new KDLayout.Constraints(321, 154, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contmanInsteadPay.setBounds(new Rectangle(20, 183, 270, 19));
//        this.add(contmanInsteadPay, new KDLayout.Constraints(20, 183, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contdateInsteadPay.setBounds(new Rectangle(321, 183, 270, 19));
//        this.add(contdateInsteadPay, new KDLayout.Constraints(321, 183, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        elseEntryID.setBounds(new Rectangle(33, 272, 270, 19));
//        this.add(elseEntryID, new KDLayout.Constraints(33, 272, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
//        sellProject.setBounds(new Rectangle(320, 5, 270, 19));
//        this.add(sellProject, new KDLayout.Constraints(320, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        kDLabelContainer1.setBounds(new Rectangle(19, 154, 270, 19));
//        this.add(kDLabelContainer1, new KDLayout.Constraints(19, 154, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        kDLabelContainer2.setBounds(new Rectangle(20, 37, 270, 19));
//        this.add(kDLabelContainer2, new KDLayout.Constraints(20, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        kDLabelContainer3.setBounds(new Rectangle(321, 37, 270, 19));
//        this.add(kDLabelContainer3, new KDLayout.Constraints(321, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        kDLabelContainer4.setBounds(new Rectangle(20, 66, 270, 19));
//        this.add(kDLabelContainer4, new KDLayout.Constraints(20, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        kDLabelContainer5.setBounds(new Rectangle(321, 66, 270, 19));
//        this.add(kDLabelContainer5, new KDLayout.Constraints(321, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        //contNumber
//        contNumber.setBoundEditor(txtNumber);
//        //contDescription
//        contDescription.setBoundEditor(txtRemark);
//        //contmoneyType
//        contmoneyType.setBoundEditor(prmtMoneyType);
//        //contmoneyCollect
//        contmoneyCollect.setBoundEditor(txtmoneyCollect);
//        //contmoneyCollected
//        contmoneyCollected.setBoundEditor(txtmoneyCollected);
//        //contmoneyReturned
//        contmoneyReturned.setBoundEditor(txtmoneyReturned);
//        //contmoneyInsteadCur
//        contmoneyInsteadCur.setBoundEditor(txtmoneyInsteadCur);
//        //contmanInsteadPay
//        contmanInsteadPay.setBoundEditor(prmtmanInsteadPay);
//        //contdateInsteadPay
//        contdateInsteadPay.setBoundEditor(pkdateInsteadPay);
//        //elseEntryID
//        elseEntryID.setBoundEditor(prmtElseEntry);
//        //sellProject
//        sellProject.setBoundEditor(prmtSellProject);
//        //kDLabelContainer1
//        kDLabelContainer1.setBoundEditor(txtInsteaded);
//        //kDLabelContainer2
//        kDLabelContainer2.setBoundEditor(txtBuilding);
//        //kDLabelContainer3
//        kDLabelContainer3.setBoundEditor(txtUnit);
//        //kDLabelContainer4
//        kDLabelContainer4.setBoundEditor(txtRoom);
//        //kDLabelContainer5
//        kDLabelContainer5.setBoundEditor(txtCustomer);
//
//    }
//
//
//    /**
//     * output initUIMenuBarLayout method
//     */
//    public void initUIMenuBarLayout()
//    {
//        this.menuBar.add(menuFile);
//        this.menuBar.add(menuEdit);
//        this.menuBar.add(MenuService);
//        this.menuBar.add(menuView);
//        this.menuBar.add(menuBiz);
//        this.menuBar.add(menuTable1);
//        this.menuBar.add(menuTool);
//        this.menuBar.add(menuWorkflow);
//        this.menuBar.add(menuHelp);
//        //menuFile
//        menuFile.add(menuItemAddNew);
//        menuFile.add(kDSeparator1);
//        menuFile.add(menuItemSave);
//        menuFile.add(menuItemSubmit);
//        menuFile.add(menuSubmitOption);
//        menuFile.add(rMenuItemSubmit);
//        menuFile.add(rMenuItemSubmitAndAddNew);
//        menuFile.add(rMenuItemSubmitAndPrint);
//        menuFile.add(separatorFile1);
//        menuFile.add(MenuItemAttachment);
//        menuFile.add(kDSeparator2);
//        menuFile.add(menuItemPageSetup);
//        menuFile.add(menuItemPrint);
//        menuFile.add(menuItemPrintPreview);
//        menuFile.add(kDSeparator6);
//        menuFile.add(menuItemSendMail);
//        menuFile.add(kDSeparator3);
//        menuFile.add(menuItemExitCurrent);
//        //menuSubmitOption
//        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
//        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
//        //menuEdit
//        menuEdit.add(menuItemCopy);
//        menuEdit.add(menuItemEdit);
//        menuEdit.add(menuItemRemove);
//        menuEdit.add(kDSeparator4);
//        menuEdit.add(menuItemReset);
//        menuEdit.add(separator1);
//        menuEdit.add(menuItemCreateFrom);
//        menuEdit.add(menuItemCreateTo);
//        menuEdit.add(menuItemCopyFrom);
//        menuEdit.add(separatorEdit1);
//        menuEdit.add(menuItemEnterToNextRow);
//        menuEdit.add(separator2);
//        //MenuService
//        MenuService.add(MenuItemKnowStore);
//        MenuService.add(MenuItemAnwser);
//        MenuService.add(SepratorService);
//        MenuService.add(MenuItemRemoteAssist);
//        //menuView
//        menuView.add(menuItemFirst);
//        menuView.add(menuItemPre);
//        menuView.add(menuItemNext);
//        menuView.add(menuItemLast);
//        menuView.add(separator3);
//        menuView.add(menuItemTraceUp);
//        menuView.add(menuItemTraceDown);
//        menuView.add(kDSeparator7);
//        menuView.add(menuItemLocate);
//        //menuBiz
//        menuBiz.add(menuItemCancelCancel);
//        menuBiz.add(menuItemCancel);
//        menuBiz.add(MenuItemVoucher);
//        menuBiz.add(menuItemDelVoucher);
//        menuBiz.add(menuItemAudit);
//        menuBiz.add(menuItemUnAudit);
//        //menuTable1
//        menuTable1.add(menuItemAddLine);
//        menuTable1.add(menuItemCopyLine);
//        menuTable1.add(menuItemInsertLine);
//        menuTable1.add(menuItemRemoveLine);
//        //menuTool
//        menuTool.add(menuItemSendMessage);
//        menuTool.add(menuItemMsgFormat);
//        menuTool.add(menuItemCalculator);
//        //menuWorkflow
//        menuWorkflow.add(menuItemStartWorkFlow);
//        menuWorkflow.add(separatorWF1);
//        menuWorkflow.add(menuItemViewSubmitProccess);
//        menuWorkflow.add(menuItemViewDoProccess);
//        menuWorkflow.add(MenuItemWFG);
//        menuWorkflow.add(menuItemWorkFlowList);
//        menuWorkflow.add(separatorWF2);
//        menuWorkflow.add(menuItemMultiapprove);
//        menuWorkflow.add(menuItemNextPerson);
//        menuWorkflow.add(menuItemAuditResult);
//        menuWorkflow.add(kDSeparator5);
//        menuWorkflow.add(kDMenuItemSendMessage);
//        //menuHelp
//        menuHelp.add(menuItemHelp);
//        menuHelp.add(kDSeparator12);
//        menuHelp.add(menuItemRegPro);
//        menuHelp.add(menuItemPersonalSite);
//        menuHelp.add(helpseparatorDiv);
//        menuHelp.add(menuitemProductval);
//        menuHelp.add(kDSeparatorProduct);
//        menuHelp.add(menuItemAbout);
//
//    }
//
//    /**
//     * output initUIToolBarLayout method
//     */
//    public void initUIToolBarLayout()
//    {
//        this.toolBar.add(btnAddNew);
//        this.toolBar.add(btnEdit);
//        this.toolBar.add(btnSave);
//        this.toolBar.add(btnSubmit);
//        this.toolBar.add(btnReset);
//        this.toolBar.add(btnCopy);
//        this.toolBar.add(btnRemove);
//        this.toolBar.add(btnCancelCancel);
//        this.toolBar.add(btnCancel);
//        this.toolBar.add(btnAttachment);
//        this.toolBar.add(separatorFW1);
//        this.toolBar.add(btnPageSetup);
//        this.toolBar.add(btnPrint);
//        this.toolBar.add(btnPrintPreview);
//        this.toolBar.add(separatorFW2);
//        this.toolBar.add(btnFirst);
//        this.toolBar.add(btnPre);
//        this.toolBar.add(btnNext);
//        this.toolBar.add(btnLast);
//        this.toolBar.add(separatorFW3);
//        this.toolBar.add(btnTraceUp);
//        this.toolBar.add(btnTraceDown);
//        this.toolBar.add(btnWorkFlowG);
//        this.toolBar.add(separatorFW4);
//        this.toolBar.add(separatorFW7);
//        this.toolBar.add(btnSignature);
//        this.toolBar.add(btnCreateFrom);
//        this.toolBar.add(btnViewSignature);
//        this.toolBar.add(btnCopyFrom);
//        this.toolBar.add(separatorFW5);
//        this.toolBar.add(separatorFW8);
//        this.toolBar.add(btnAddLine);
//        this.toolBar.add(btnInsertLine);
//        this.toolBar.add(btnCreateTo);
//        this.toolBar.add(btnRemoveLine);
//        this.toolBar.add(separatorFW6);
//        this.toolBar.add(separatorFW9);
//        this.toolBar.add(btnCopyLine);
//        this.toolBar.add(btnVoucher);
//        this.toolBar.add(btnDelVoucher);
//        this.toolBar.add(btnAuditResult);
//        this.toolBar.add(btnMultiapprove);
//        this.toolBar.add(btnWFViewdoProccess);
//        this.toolBar.add(btnWFViewSubmitProccess);
//        this.toolBar.add(btnNextPerson);
//        this.toolBar.add(btnAudit);
//        this.toolBar.add(btnUnAudit);
//        this.toolBar.add(btnCalculator);
//
//
//    }
//
//	//Regiester control's property binding.
//	private void registerBindings(){
//		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
//		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryInfo.class, this.kdtEntrys, "userObject");
//		dataBinder.registerBinding("purchaseElsePayListEntry", com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo.class, this.kdtEntrys, "elseEntryID.text");
//		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
//		dataBinder.registerBinding("description", String.class, this.txtRemark, "text");
//		dataBinder.registerBinding("moneyInsteadCur", java.math.BigDecimal.class, this.txtmoneyInsteadCur, "value");
//		dataBinder.registerBinding("manInsteadPay", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtmanInsteadPay, "data");
//		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkdateInsteadPay, "value");
//		dataBinder.registerBinding("purchaseElsePayListEntry", com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo.class, this.prmtElseEntry, "data");
//		dataBinder.registerBinding("sellproject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");		
//	}
//	//Regiester UI State
//	private void registerUIState(){		
//	}
//	public String getUIHandlerClassName() {
//	    return "com.kingdee.eas.fdc.sellhouse.app.InsteadCollectOutBillEditUIHandler";
//	}
//	public IUIActionPostman prepareInit() {
//		IUIActionPostman clientHanlder = super.prepareInit();
//		if (clientHanlder != null) {
//			RequestContext request = new RequestContext();
//    		request.setClassName(getUIHandlerClassName());
//			clientHanlder.setRequestContext(request);
//		}
//		return clientHanlder;
//    }
//	
//	public boolean isPrepareInit() {
//    	return false;
//    }
//    protected void initUIP() {
//        super.initUIP();
//    }
//
//
//    /**
//     * output onShow method
//     */
//    public void onShow() throws Exception
//    {
//        super.onShow();
//        this.txtNumber.requestFocusInWindow();
//    }
//
//	
//	
//
//    /**
//     * output setDataObject method
//     */
//    public void setDataObject(IObjectValue dataObject)
//    {
//        IObjectValue ov = dataObject;        	    	
//        super.setDataObject(ov);
//        this.editData = (com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo)ov;
//    }
//    protected void removeByPK(IObjectPK pk) throws Exception {
//    	IObjectValue editData = this.editData;
//    	super.removeByPK(pk);
//    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
//    }
//    
//    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
//        if (!StringUtils.isEmpty(number))
//        {
//            try {
//            	String companyID = null;            
//            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
//				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
//					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
//				}
//				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
//					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
//            	}				
//				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
//					iCodingRuleManager.recycleNumber(editData,companyID,number);					
//				}
//            }
//            catch (Exception e)
//            {
//                handUIException(e);
//            }
//        }
//    }
//    protected void setAutoNumberByOrg(String orgType) {
//    	if (editData == null) return;
//		if (editData.getNumber() == null) {
//            try {
//            	String companyID = null;
//				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
//					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
//				}
//				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
//					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
//            	}
//				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
//		        if (iCodingRuleManager.isExist(editData, companyID)) {
//		            if (iCodingRuleManager.isAddView(editData, companyID)) {
//		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
//		            }
//	                txtNumber.setEnabled(false);
//		        }
//            }
//            catch (Exception e) {
//                handUIException(e);
//                this.oldData = editData;
//                com.kingdee.eas.util.SysUtil.abort();
//            } 
//        } 
//        else {
//            if (editData.getNumber().trim().length() > 0) {
//                txtNumber.setText(editData.getNumber());
//            }
//        }
//    }
//
//    /**
//     * output loadFields method
//     */
//    public void loadFields()
//    {
//        		setAutoNumberByOrg("NONE");
//        dataBinder.loadFields();
//    }
//		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
//		{
//			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
//			oufip.getModel().setIsCUFilter(true);
//			f7.setFilterInfoProducer(oufip);
//		}
//
//    /**
//     * output storeFields method
//     */
//    public void storeFields()
//    {
//		dataBinder.storeFields();
//    }
//
//	/**
//	 * ????????§µ??
//	 */
//	protected void registerValidator() {
//    	getValidateHelper().setCustomValidator( getValidator() );
//		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("purchaseElsePayListEntry", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("moneyInsteadCur", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("manInsteadPay", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("purchaseElsePayListEntry", ValidateHelper.ON_SAVE);    
//		getValidateHelper().registerBindProperty("sellproject", ValidateHelper.ON_SAVE);    		
//	}
//
//
//
//    /**
//     * output setOprtState method
//     */
//    public void setOprtState(String oprtType)
//    {
//        super.setOprtState(oprtType);
//        if (STATUS_ADDNEW.equals(this.oprtState)) {
//        } else if (STATUS_EDIT.equals(this.oprtState)) {
//        } else if (STATUS_VIEW.equals(this.oprtState)) {
//        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
//        }
//    }
//
//    /**
//     * output txtmoneyInsteadCur_focusLost method
//     */
//    protected void txtmoneyInsteadCur_focusLost(java.awt.event.FocusEvent e) throws Exception
//    {
//    }
//
//    /**
//     * output txtmoneyInsteadCur_dataChanged method
//     */
//    protected void txtmoneyInsteadCur_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
//    {
//    }
//
//    /**
//     * output getSelectors method
//     */
//    public SelectorItemCollection getSelectors()
//    {
//        SelectorItemCollection sic = new SelectorItemCollection();
//    sic.add(new SelectorItemInfo("entrys.id"));
//        sic.add(new SelectorItemInfo("entrys.*"));
////        sic.add(new SelectorItemInfo("entrys.number"));
//        sic.add(new SelectorItemInfo("purchaseElsePayListEntry.*"));
////        sic.add(new SelectorItemInfo("purchaseElsePayListEntry.number"));
//        sic.add(new SelectorItemInfo("number"));
//        sic.add(new SelectorItemInfo("description"));
//        sic.add(new SelectorItemInfo("moneyInsteadCur"));
//        sic.add(new SelectorItemInfo("manInsteadPay.*"));
//        sic.add(new SelectorItemInfo("bizDate"));
//        sic.add(new SelectorItemInfo("sellproject.*"));
//        return sic;
//    }        
//    	
//
//    /**
//     * output actionSubmit_actionPerformed method
//     */
//    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionSubmit_actionPerformed(e);
//    }
//    	
//
//    /**
//     * output actionPrint_actionPerformed method
//     */
//    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
//    {
//        ArrayList idList = new ArrayList();
//    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
//    		idList.add(editData.getString("id"));
//    	}
//        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
//            return;
//        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
//        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
//        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
//    }
//    	
//
//    /**
//     * output actionPrintPreview_actionPerformed method
//     */
//    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
//    {
//        ArrayList idList = new ArrayList();
//        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
//    		idList.add(editData.getString("id"));
//    	}
//        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
//            return;
//        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
//        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
//        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
//    }
//	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
//			RequestContext request = super.prepareActionSubmit(itemAction);		
//		if (request != null) {
//    		request.setClassName(getUIHandlerClassName());
//		}
//		return request;
//    }
//	
//	public boolean isPrepareActionSubmit() {
//    	return false;
//    }
//	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
//			RequestContext request = super.prepareActionPrint(itemAction);		
//		if (request != null) {
//    		request.setClassName(getUIHandlerClassName());
//		}
//		return request;
//    }
//	
//	public boolean isPrepareActionPrint() {
//    	return false;
//    }
//	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
//			RequestContext request = super.prepareActionPrintPreview(itemAction);		
//		if (request != null) {
//    		request.setClassName(getUIHandlerClassName());
//		}
//		return request;
//    }
//	
//	public boolean isPrepareActionPrintPreview() {
//    	return false;
//    }
//
//    /**
//     * output getMetaDataPK method
//     */
//    public IMetaDataPK getMetaDataPK()
//    {
//        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "InsteadCollectOutBillEditUI");
//    }
//    /**
//     * output isBindWorkFlow method
//     */
//    public boolean isBindWorkFlow()
//    {
//        return true;
//    }
//
//    /**
//     * output getEditUIName method
//     */
//    protected String getEditUIName()
//    {
//        return com.kingdee.eas.fdc.sellhouse.client.InsteadCollectOutBillEditUI.class.getName();
//    }
//
//    /**
//     * output getBizInterface method
//     */
//    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
//    {
//        return com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory.getRemoteInstance();
//    }
//
//    /**
//     * output createNewData method
//     */
//    protected IObjectValue createNewData()
//    {
//        com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo objectValue = new com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo();
//        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
//        return objectValue;
//    }
//
//
//    	protected String getTDFileName() {
//    	return "/bim/fdc/sellhouse/InsteadCollectOutBill";
//	}
//    protected IMetaDataPK getTDQueryPK() {
//    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.InsteadCollectOutBillQuery");
//	}
//    
//
//    /**
//     * output getDetailTable method
//     */
//    protected KDTable getDetailTable() {
//        return kdtEntrys;
//	}
//    /**
//     * output applyDefaultValue method
//     */
//    protected void applyDefaultValue(IObjectValue vo) {        
//    }        
//	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
//		super.setFieldsNull(arg0);
//		arg0.put("number",null);
//	}
//
//}