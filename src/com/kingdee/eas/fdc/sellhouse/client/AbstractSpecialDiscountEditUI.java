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
public abstract class AbstractSpecialDiscountEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSpecialDiscountEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnitPriceName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommended;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOnePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTwoPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtUnitPriceName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanecaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCaseInfo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbRelate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommended;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioDesc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseAgio;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgioDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOnePerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTwoPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo editData = null;
    protected AuditAction auditAction = null;
    protected UnAuditAction unAuditAction = null;
    protected ActionRoomSelect actionRoomSelect = null;
    protected ActionRoomDelete actionRoomDelete = null;
    /**
     * output class constructor
     */
    public AbstractSpecialDiscountEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSpecialDiscountEditUI.class.getName());
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
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //auditAction
        this.auditAction = new AuditAction(this);
        getActionManager().registerAction("auditAction", auditAction);
         this.auditAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //unAuditAction
        this.unAuditAction = new UnAuditAction(this);
        getActionManager().registerAction("unAuditAction", unAuditAction);
         this.unAuditAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRoomSelect
        this.actionRoomSelect = new ActionRoomSelect(this);
        getActionManager().registerAction("actionRoomSelect", actionRoomSelect);
         this.actionRoomSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRoomDelete
        this.actionRoomDelete = new ActionRoomDelete(this);
        getActionManager().registerAction("actionRoomDelete", actionRoomDelete);
         this.actionRoomDelete.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnitPriceName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCaseInfo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommended = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMailAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contOnePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTwoPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtUnitPriceName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.scrollPanecaseInfo = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtCaseInfo = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbRelate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRecommended = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMailAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAgioDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnChooseAgio = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtAgioDesc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOnePerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTwoPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contAuditor.setName("contAuditor");
        this.contUnitPriceName.setName("contUnitPriceName");
        this.contsellProject.setName("contsellProject");
        this.contAuditTime.setName("contAuditTime");
        this.contEntry.setName("contEntry");
        this.contCaseInfo.setName("contCaseInfo");
        this.contCustomer.setName("contCustomer");
        this.contRelate.setName("contRelate");
        this.contRecommended.setName("contRecommended");
        this.contPhone.setName("contPhone");
        this.contMailAddress.setName("contMailAddress");
        this.contCertificateNumber.setName("contCertificateNumber");
        this.kDContainer1.setName("kDContainer1");
        this.contOnePerson.setName("contOnePerson");
        this.contTwoPerson.setName("contTwoPerson");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtUnitPriceName.setName("txtUnitPriceName");
        this.prmtSellProject.setName("prmtSellProject");
        this.pkAuditTime.setName("pkAuditTime");
        this.kdtEntry.setName("kdtEntry");
        this.scrollPanecaseInfo.setName("scrollPanecaseInfo");
        this.txtCaseInfo.setName("txtCaseInfo");
        this.prmtCustomer.setName("prmtCustomer");
        this.cbRelate.setName("cbRelate");
        this.txtRecommended.setName("txtRecommended");
        this.txtPhone.setName("txtPhone");
        this.txtMailAddress.setName("txtMailAddress");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.contAgioDesc.setName("contAgioDesc");
        this.btnChooseAgio.setName("btnChooseAgio");
        this.txtAgioDesc.setName("txtAgioDesc");
        this.txtOnePerson.setName("txtOnePerson");
        this.txtTwoPerson.setName("txtTwoPerson");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.menuItemCreateTo.setVisible(true);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemCopyLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contUnitPriceName		
        this.contUnitPriceName.setBoundLabelText(resHelper.getString("contUnitPriceName.boundLabelText"));		
        this.contUnitPriceName.setBoundLabelLength(100);		
        this.contUnitPriceName.setBoundLabelUnderline(true);		
        this.contUnitPriceName.setVisible(true);
        // contsellProject		
        this.contsellProject.setBoundLabelText(resHelper.getString("contsellProject.boundLabelText"));		
        this.contsellProject.setBoundLabelLength(100);		
        this.contsellProject.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contEntry		
        this.contEntry.setTitle(resHelper.getString("contEntry.title"));
        // contCaseInfo		
        this.contCaseInfo.setBoundLabelText(resHelper.getString("contCaseInfo.boundLabelText"));		
        this.contCaseInfo.setBoundLabelLength(100);		
        this.contCaseInfo.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);		
        this.contCustomer.setVisible(true);
        // contRelate		
        this.contRelate.setBoundLabelText(resHelper.getString("contRelate.boundLabelText"));		
        this.contRelate.setBoundLabelLength(100);		
        this.contRelate.setBoundLabelUnderline(true);
        // contRecommended		
        this.contRecommended.setBoundLabelText(resHelper.getString("contRecommended.boundLabelText"));		
        this.contRecommended.setBoundLabelLength(100);		
        this.contRecommended.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contMailAddress		
        this.contMailAddress.setBoundLabelText(resHelper.getString("contMailAddress.boundLabelText"));		
        this.contMailAddress.setBoundLabelLength(100);		
        this.contMailAddress.setBoundLabelUnderline(true);
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(100);		
        this.contCertificateNumber.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contOnePerson		
        this.contOnePerson.setBoundLabelText(resHelper.getString("contOnePerson.boundLabelText"));		
        this.contOnePerson.setBoundLabelLength(100);		
        this.contOnePerson.setBoundLabelUnderline(true);
        // contTwoPerson		
        this.contTwoPerson.setBoundLabelText(resHelper.getString("contTwoPerson.boundLabelText"));		
        this.contTwoPerson.setBoundLabelLength(100);		
        this.contTwoPerson.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtUnitPriceName		
        this.txtUnitPriceName.setHorizontalAlignment(2);		
        this.txtUnitPriceName.setMaxLength(200);		
        this.txtUnitPriceName.setRequired(false);		
        this.txtUnitPriceName.setEnabled(false);
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setEditable(true);		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setRequired(false);		
        this.prmtSellProject.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.0000;-#,##0.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.0000;-#,##0.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection locked=\"true\" hidden=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"buildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"agioPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"agioAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"discountAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"discountPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"discountAfBPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"discountAfAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"baseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"basePercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"baseDiscountAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"purBizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"planSignDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"contractTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"payType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{buildingPrice}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{agioPrice}</t:Cell><t:Cell>$Resource{agioAmount}</t:Cell><t:Cell>$Resource{discountAmount}</t:Cell><t:Cell>$Resource{discountPercent}</t:Cell><t:Cell>$Resource{discountAfBPrice}</t:Cell><t:Cell>$Resource{discountAfAmount}</t:Cell><t:Cell>$Resource{baseStandardPrice}</t:Cell><t:Cell>$Resource{basePercent}</t:Cell><t:Cell>$Resource{baseDiscountAmount}</t:Cell><t:Cell>$Resource{purBizDate}</t:Cell><t:Cell>$Resource{planSignDate}</t:Cell><t:Cell>$Resource{contractTotalAmount}</t:Cell><t:Cell>$Resource{payType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"room","buildingArea","buildingPrice","standardTotalAmount","agioPrice","agioAmount","discountAmount","discountPercent","discountAfBPrice","discountAfAmount","baseStandardPrice","basePercent","baseDiscountAmount","","","","payType"});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_room_PromptBox = new KDBizPromptBox();
        kdtEntry_room_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery");
        kdtEntry_room_PromptBox.setVisible(true);
        kdtEntry_room_PromptBox.setEditable(true);
        kdtEntry_room_PromptBox.setDisplayFormat("$number$");
        kdtEntry_room_PromptBox.setEditFormat("$number$");
        kdtEntry_room_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_room_CellEditor = new KDTDefaultCellEditor(kdtEntry_room_PromptBox);
        this.kdtEntry.getColumn("room").setEditor(kdtEntry_room_CellEditor);
        ObjectValueRender kdtEntry_room_OVR = new ObjectValueRender();
        kdtEntry_room_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("room").setRenderer(kdtEntry_room_OVR);
        KDFormattedTextField kdtEntry_buildingArea_TextField = new KDFormattedTextField();
        kdtEntry_buildingArea_TextField.setName("kdtEntry_buildingArea_TextField");
        kdtEntry_buildingArea_TextField.setVisible(true);
        kdtEntry_buildingArea_TextField.setEditable(true);
        kdtEntry_buildingArea_TextField.setHorizontalAlignment(2);
        kdtEntry_buildingArea_TextField.setDataType(1);
        	kdtEntry_buildingArea_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_buildingArea_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_buildingArea_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_buildingArea_CellEditor = new KDTDefaultCellEditor(kdtEntry_buildingArea_TextField);
        this.kdtEntry.getColumn("buildingArea").setEditor(kdtEntry_buildingArea_CellEditor);
        KDFormattedTextField kdtEntry_buildingPrice_TextField = new KDFormattedTextField();
        kdtEntry_buildingPrice_TextField.setName("kdtEntry_buildingPrice_TextField");
        kdtEntry_buildingPrice_TextField.setVisible(true);
        kdtEntry_buildingPrice_TextField.setEditable(true);
        kdtEntry_buildingPrice_TextField.setHorizontalAlignment(2);
        kdtEntry_buildingPrice_TextField.setDataType(1);
        	kdtEntry_buildingPrice_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_buildingPrice_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_buildingPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_buildingPrice_CellEditor = new KDTDefaultCellEditor(kdtEntry_buildingPrice_TextField);
        this.kdtEntry.getColumn("buildingPrice").setEditor(kdtEntry_buildingPrice_CellEditor);
        KDFormattedTextField kdtEntry_standardTotalAmount_TextField = new KDFormattedTextField();
        kdtEntry_standardTotalAmount_TextField.setName("kdtEntry_standardTotalAmount_TextField");
        kdtEntry_standardTotalAmount_TextField.setVisible(true);
        kdtEntry_standardTotalAmount_TextField.setEditable(true);
        kdtEntry_standardTotalAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_standardTotalAmount_TextField.setDataType(1);
        	kdtEntry_standardTotalAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_standardTotalAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_standardTotalAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_standardTotalAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_standardTotalAmount_TextField);
        this.kdtEntry.getColumn("standardTotalAmount").setEditor(kdtEntry_standardTotalAmount_CellEditor);
        KDFormattedTextField kdtEntry_agioPrice_TextField = new KDFormattedTextField();
        kdtEntry_agioPrice_TextField.setName("kdtEntry_agioPrice_TextField");
        kdtEntry_agioPrice_TextField.setVisible(true);
        kdtEntry_agioPrice_TextField.setEditable(true);
        kdtEntry_agioPrice_TextField.setHorizontalAlignment(2);
        kdtEntry_agioPrice_TextField.setDataType(1);
        	kdtEntry_agioPrice_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_agioPrice_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_agioPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_agioPrice_CellEditor = new KDTDefaultCellEditor(kdtEntry_agioPrice_TextField);
        this.kdtEntry.getColumn("agioPrice").setEditor(kdtEntry_agioPrice_CellEditor);
        KDFormattedTextField kdtEntry_agioAmount_TextField = new KDFormattedTextField();
        kdtEntry_agioAmount_TextField.setName("kdtEntry_agioAmount_TextField");
        kdtEntry_agioAmount_TextField.setVisible(true);
        kdtEntry_agioAmount_TextField.setEditable(true);
        kdtEntry_agioAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_agioAmount_TextField.setDataType(1);
        	kdtEntry_agioAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_agioAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_agioAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_agioAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_agioAmount_TextField);
        this.kdtEntry.getColumn("agioAmount").setEditor(kdtEntry_agioAmount_CellEditor);
        KDFormattedTextField kdtEntry_discountAmount_TextField = new KDFormattedTextField();
        kdtEntry_discountAmount_TextField.setName("kdtEntry_discountAmount_TextField");
        kdtEntry_discountAmount_TextField.setVisible(true);
        kdtEntry_discountAmount_TextField.setEditable(true);
        kdtEntry_discountAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_discountAmount_TextField.setDataType(1);
        	kdtEntry_discountAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_discountAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_discountAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_discountAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_discountAmount_TextField);
        this.kdtEntry.getColumn("discountAmount").setEditor(kdtEntry_discountAmount_CellEditor);
        KDFormattedTextField kdtEntry_discountPercent_TextField = new KDFormattedTextField();
        kdtEntry_discountPercent_TextField.setName("kdtEntry_discountPercent_TextField");
        kdtEntry_discountPercent_TextField.setVisible(true);
        kdtEntry_discountPercent_TextField.setEditable(true);
        kdtEntry_discountPercent_TextField.setHorizontalAlignment(2);
        kdtEntry_discountPercent_TextField.setDataType(1);
        	kdtEntry_discountPercent_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_discountPercent_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_discountPercent_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_discountPercent_CellEditor = new KDTDefaultCellEditor(kdtEntry_discountPercent_TextField);
        this.kdtEntry.getColumn("discountPercent").setEditor(kdtEntry_discountPercent_CellEditor);
        KDFormattedTextField kdtEntry_discountAfBPrice_TextField = new KDFormattedTextField();
        kdtEntry_discountAfBPrice_TextField.setName("kdtEntry_discountAfBPrice_TextField");
        kdtEntry_discountAfBPrice_TextField.setVisible(true);
        kdtEntry_discountAfBPrice_TextField.setEditable(true);
        kdtEntry_discountAfBPrice_TextField.setHorizontalAlignment(2);
        kdtEntry_discountAfBPrice_TextField.setDataType(1);
        	kdtEntry_discountAfBPrice_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_discountAfBPrice_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_discountAfBPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_discountAfBPrice_CellEditor = new KDTDefaultCellEditor(kdtEntry_discountAfBPrice_TextField);
        this.kdtEntry.getColumn("discountAfBPrice").setEditor(kdtEntry_discountAfBPrice_CellEditor);
        KDFormattedTextField kdtEntry_discountAfAmount_TextField = new KDFormattedTextField();
        kdtEntry_discountAfAmount_TextField.setName("kdtEntry_discountAfAmount_TextField");
        kdtEntry_discountAfAmount_TextField.setVisible(true);
        kdtEntry_discountAfAmount_TextField.setEditable(true);
        kdtEntry_discountAfAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_discountAfAmount_TextField.setDataType(1);
        	kdtEntry_discountAfAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_discountAfAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_discountAfAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_discountAfAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_discountAfAmount_TextField);
        this.kdtEntry.getColumn("discountAfAmount").setEditor(kdtEntry_discountAfAmount_CellEditor);
        KDFormattedTextField kdtEntry_baseStandardPrice_TextField = new KDFormattedTextField();
        kdtEntry_baseStandardPrice_TextField.setName("kdtEntry_baseStandardPrice_TextField");
        kdtEntry_baseStandardPrice_TextField.setVisible(true);
        kdtEntry_baseStandardPrice_TextField.setEditable(true);
        kdtEntry_baseStandardPrice_TextField.setHorizontalAlignment(2);
        kdtEntry_baseStandardPrice_TextField.setDataType(1);
        	kdtEntry_baseStandardPrice_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_baseStandardPrice_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_baseStandardPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_baseStandardPrice_CellEditor = new KDTDefaultCellEditor(kdtEntry_baseStandardPrice_TextField);
        this.kdtEntry.getColumn("baseStandardPrice").setEditor(kdtEntry_baseStandardPrice_CellEditor);
        KDFormattedTextField kdtEntry_basePercent_TextField = new KDFormattedTextField();
        kdtEntry_basePercent_TextField.setName("kdtEntry_basePercent_TextField");
        kdtEntry_basePercent_TextField.setVisible(true);
        kdtEntry_basePercent_TextField.setEditable(true);
        kdtEntry_basePercent_TextField.setHorizontalAlignment(2);
        kdtEntry_basePercent_TextField.setDataType(1);
        	kdtEntry_basePercent_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_basePercent_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_basePercent_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_basePercent_CellEditor = new KDTDefaultCellEditor(kdtEntry_basePercent_TextField);
        this.kdtEntry.getColumn("basePercent").setEditor(kdtEntry_basePercent_CellEditor);
        KDFormattedTextField kdtEntry_baseDiscountAmount_TextField = new KDFormattedTextField();
        kdtEntry_baseDiscountAmount_TextField.setName("kdtEntry_baseDiscountAmount_TextField");
        kdtEntry_baseDiscountAmount_TextField.setVisible(true);
        kdtEntry_baseDiscountAmount_TextField.setEditable(true);
        kdtEntry_baseDiscountAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_baseDiscountAmount_TextField.setDataType(1);
        	kdtEntry_baseDiscountAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_baseDiscountAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_baseDiscountAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_baseDiscountAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_baseDiscountAmount_TextField);
        this.kdtEntry.getColumn("baseDiscountAmount").setEditor(kdtEntry_baseDiscountAmount_CellEditor);
        // scrollPanecaseInfo
        // txtCaseInfo		
        this.txtCaseInfo.setRequired(true);		
        this.txtCaseInfo.setMaxLength(1000);
        // prmtCustomer		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.prmtCustomer.setEditable(true);		
        this.prmtCustomer.setDisplayFormat("$name$");		
        this.prmtCustomer.setEditFormat("$number$");		
        this.prmtCustomer.setCommitFormat("$number$");		
        this.prmtCustomer.setRequired(true);
        this.prmtCustomer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCustomer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbRelate		
        this.cbRelate.setRequired(true);		
        this.cbRelate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum").toArray());
        // txtRecommended		
        this.txtRecommended.setEnabled(false);
        // txtPhone		
        this.txtPhone.setEnabled(false);
        // txtMailAddress		
        this.txtMailAddress.setEnabled(false);
        // txtCertificateNumber		
        this.txtCertificateNumber.setEnabled(false);
        // contAgioDesc		
        this.contAgioDesc.setBoundLabelText(resHelper.getString("contAgioDesc.boundLabelText"));		
        this.contAgioDesc.setBoundLabelLength(100);		
        this.contAgioDesc.setBoundLabelUnderline(true);		
        this.contAgioDesc.setEnabled(false);
        // btnChooseAgio		
        this.btnChooseAgio.setText(resHelper.getString("btnChooseAgio.text"));
        this.btnChooseAgio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChooseAgio_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtAgioDesc		
        this.txtAgioDesc.setEnabled(false);
        // txtOnePerson		
        this.txtOnePerson.setRequired(true);
        // txtTwoPerson		
        this.txtTwoPerson.setRequired(true);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(auditAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(unAuditAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtUnitPriceName,pkBizDate,prmtCustomer,txtCaseInfo,prmtSellProject,prmtAuditor,prmtCreator,pkCreateTime}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contCreator.setBounds(new Rectangle(15, 578, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(15, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(15, 600, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(15, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(15, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(15, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(15, 164, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(15, 164, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(374, 578, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(374, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUnitPriceName.setBounds(new Rectangle(374, 10, 270, 19));
        this.add(contUnitPriceName, new KDLayout.Constraints(374, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsellProject.setBounds(new Rectangle(734, 10, 270, 19));
        this.add(contsellProject, new KDLayout.Constraints(734, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(374, 600, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(374, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEntry.setBounds(new Rectangle(6, 305, 1003, 262));
        this.add(contEntry, new KDLayout.Constraints(6, 305, 1003, 262, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCaseInfo.setBounds(new Rectangle(374, 32, 629, 152));
        this.add(contCaseInfo, new KDLayout.Constraints(374, 32, 629, 152, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomer.setBounds(new Rectangle(15, 32, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(15, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRelate.setBounds(new Rectangle(15, 54, 270, 19));
        this.add(contRelate, new KDLayout.Constraints(15, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommended.setBounds(new Rectangle(15, 76, 270, 19));
        this.add(contRecommended, new KDLayout.Constraints(15, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPhone.setBounds(new Rectangle(15, 98, 270, 19));
        this.add(contPhone, new KDLayout.Constraints(15, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMailAddress.setBounds(new Rectangle(15, 120, 270, 19));
        this.add(contMailAddress, new KDLayout.Constraints(15, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCertificateNumber.setBounds(new Rectangle(15, 142, 270, 19));
        this.add(contCertificateNumber, new KDLayout.Constraints(15, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(6, 234, 1003, 60));
        this.add(kDContainer1, new KDLayout.Constraints(6, 234, 1003, 60, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOnePerson.setBounds(new Rectangle(15, 186, 270, 19));
        this.add(contOnePerson, new KDLayout.Constraints(15, 186, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTwoPerson.setBounds(new Rectangle(15, 208, 270, 19));
        this.add(contTwoPerson, new KDLayout.Constraints(15, 208, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contUnitPriceName
        contUnitPriceName.setBoundEditor(txtUnitPriceName);
        //contsellProject
        contsellProject.setBoundEditor(prmtSellProject);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contCaseInfo
        contCaseInfo.setBoundEditor(scrollPanecaseInfo);
        //scrollPanecaseInfo
        scrollPanecaseInfo.getViewport().add(txtCaseInfo, null);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contRelate
        contRelate.setBoundEditor(cbRelate);
        //contRecommended
        contRecommended.setBoundEditor(txtRecommended);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contMailAddress
        contMailAddress.setBoundEditor(txtMailAddress);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNumber);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(6, 234, 1003, 60));        contAgioDesc.setBounds(new Rectangle(8, 11, 709, 19));
        kDContainer1.getContentPane().add(contAgioDesc, new KDLayout.Constraints(8, 11, 709, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnChooseAgio.setBounds(new Rectangle(735, 11, 97, 19));
        kDContainer1.getContentPane().add(btnChooseAgio, new KDLayout.Constraints(735, 11, 97, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contAgioDesc
        contAgioDesc.setBoundEditor(txtAgioDesc);
        //contOnePerson
        contOnePerson.setBoundEditor(txtOnePerson);
        //contTwoPerson
        contTwoPerson.setBoundEditor(txtTwoPerson);

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
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("unitPriceName", String.class, this.txtUnitPriceName, "text");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entrys.room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.kdtEntry, "room.text");
		dataBinder.registerBinding("entrys.buildingArea", java.math.BigDecimal.class, this.kdtEntry, "buildingArea.text");
		dataBinder.registerBinding("entrys.buildingPrice", java.math.BigDecimal.class, this.kdtEntry, "buildingPrice.text");
		dataBinder.registerBinding("entrys.standardTotalAmount", java.math.BigDecimal.class, this.kdtEntry, "standardTotalAmount.text");
		dataBinder.registerBinding("entrys.payType", com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo.class, this.kdtEntry, "payType.text");
		dataBinder.registerBinding("entrys.discountAmount", java.math.BigDecimal.class, this.kdtEntry, "discountAmount.text");
		dataBinder.registerBinding("entrys.discountAfBPrice", java.math.BigDecimal.class, this.kdtEntry, "discountAfBPrice.text");
		dataBinder.registerBinding("entrys.discountAfAmount", java.math.BigDecimal.class, this.kdtEntry, "discountAfAmount.text");
		dataBinder.registerBinding("entrys.baseStandardPrice", java.math.BigDecimal.class, this.kdtEntry, "baseStandardPrice.text");
		dataBinder.registerBinding("entrys.basePercent", java.math.BigDecimal.class, this.kdtEntry, "basePercent.text");
		dataBinder.registerBinding("entrys.discountPercent", java.math.BigDecimal.class, this.kdtEntry, "discountPercent.text");
		dataBinder.registerBinding("entrys.baseDiscountAmount", java.math.BigDecimal.class, this.kdtEntry, "baseDiscountAmount.text");
		dataBinder.registerBinding("entrys.agioPrice", java.math.BigDecimal.class, this.kdtEntry, "agioPrice.text");
		dataBinder.registerBinding("entrys.agioAmount", java.math.BigDecimal.class, this.kdtEntry, "agioAmount.text");
		dataBinder.registerBinding("caseInfo", String.class, this.txtCaseInfo, "text");
		dataBinder.registerBinding("customer", com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo.class, this.prmtCustomer, "data");
		dataBinder.registerBinding("relate", com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum.class, this.cbRelate, "selectedItem");
		dataBinder.registerBinding("agioDesc", String.class, this.txtAgioDesc, "text");
		dataBinder.registerBinding("onePerson", String.class, this.txtOnePerson, "text");
		dataBinder.registerBinding("twoPerson", String.class, this.txtTwoPerson, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SpecialDiscountEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
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
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitPriceName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.buildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.buildingPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.standardTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.payType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.discountAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.discountAfBPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.discountAfAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.baseStandardPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.basePercent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.discountPercent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.baseDiscountAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.agioPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.agioAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("caseInfo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agioDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("onePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("twoPerson", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtCustomer_dataChanged method
     */
    protected void prmtCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnChooseAgio_actionPerformed method
     */
    protected void btnChooseAgio_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("unitPriceName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sellProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sellProject.id"));
        	sic.add(new SelectorItemInfo("sellProject.number"));
        	sic.add(new SelectorItemInfo("sellProject.name"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.room.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.room.id"));
			sic.add(new SelectorItemInfo("entrys.room.name"));
        	sic.add(new SelectorItemInfo("entrys.room.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.buildingArea"));
    	sic.add(new SelectorItemInfo("entrys.buildingPrice"));
    	sic.add(new SelectorItemInfo("entrys.standardTotalAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.payType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.payType.id"));
			sic.add(new SelectorItemInfo("entrys.payType.name"));
        	sic.add(new SelectorItemInfo("entrys.payType.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.discountAmount"));
    	sic.add(new SelectorItemInfo("entrys.discountAfBPrice"));
    	sic.add(new SelectorItemInfo("entrys.discountAfAmount"));
    	sic.add(new SelectorItemInfo("entrys.baseStandardPrice"));
    	sic.add(new SelectorItemInfo("entrys.basePercent"));
    	sic.add(new SelectorItemInfo("entrys.discountPercent"));
    	sic.add(new SelectorItemInfo("entrys.baseDiscountAmount"));
    	sic.add(new SelectorItemInfo("entrys.agioPrice"));
    	sic.add(new SelectorItemInfo("entrys.agioAmount"));
        sic.add(new SelectorItemInfo("caseInfo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("customer.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("customer.id"));
        	sic.add(new SelectorItemInfo("customer.number"));
        	sic.add(new SelectorItemInfo("customer.name"));
		}
        sic.add(new SelectorItemInfo("relate"));
        sic.add(new SelectorItemInfo("agioDesc"));
        sic.add(new SelectorItemInfo("onePerson"));
        sic.add(new SelectorItemInfo("twoPerson"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output auditAction_actionPerformed method
     */
    public void auditAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output unAuditAction_actionPerformed method
     */
    public void unAuditAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRoomSelect_actionPerformed method
     */
    public void actionRoomSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRoomDelete_actionPerformed method
     */
    public void actionRoomDelete_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareAuditAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAuditAction() {
    	return false;
    }
	public RequestContext prepareUnAuditAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareUnAuditAction() {
    	return false;
    }
	public RequestContext prepareActionRoomSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRoomSelect() {
    	return false;
    }
	public RequestContext prepareActionRoomDelete(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRoomDelete() {
    	return false;
    }

    /**
     * output AuditAction class
     */     
    protected class AuditAction extends ItemAction {     
    
        public AuditAction()
        {
            this(null);
        }

        public AuditAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("AuditAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AuditAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AuditAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSpecialDiscountEditUI.this, "AuditAction", "auditAction_actionPerformed", e);
        }
    }

    /**
     * output UnAuditAction class
     */     
    protected class UnAuditAction extends ItemAction {     
    
        public UnAuditAction()
        {
            this(null);
        }

        public UnAuditAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("UnAuditAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("UnAuditAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("UnAuditAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSpecialDiscountEditUI.this, "UnAuditAction", "unAuditAction_actionPerformed", e);
        }
    }

    /**
     * output ActionRoomSelect class
     */     
    protected class ActionRoomSelect extends ItemAction {     
    
        public ActionRoomSelect()
        {
            this(null);
        }

        public ActionRoomSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRoomSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSpecialDiscountEditUI.this, "ActionRoomSelect", "actionRoomSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionRoomDelete class
     */     
    protected class ActionRoomDelete extends ItemAction {     
    
        public ActionRoomDelete()
        {
            this(null);
        }

        public ActionRoomDelete(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRoomDelete.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomDelete.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomDelete.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSpecialDiscountEditUI.this, "ActionRoomDelete", "actionRoomDelete_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SpecialDiscountEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.SpecialDiscountEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo objectValue = new com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/sellhouse/SpecialDiscount";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SpecialDiscountQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}