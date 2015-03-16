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
public abstract class AbstractWorkLoadConfirmBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWorkLoadConfirmBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkLoad;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConfirmDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPartB;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlWorkLoad;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractBill;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtWorkLoad;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkConfirmDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPartB;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSplit;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlWorkLoadDetail;
    protected com.kingdee.bos.ctrl.swing.KDContainer conWorkLoadSplit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEntrys;
    protected com.kingdee.bos.ctrl.swing.KDContainer conWorkLoadDetail;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDetail;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConPrjBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemConPrjBill;
    protected com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo editData = null;
    protected ActionSplit actionSplit = null;
    protected ActionConPrjBill actionConPrjBill = null;
    /**
     * output class constructor
     */
    public AbstractWorkLoadConfirmBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractWorkLoadConfirmBillEditUI.class.getName());
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
        //actionUnAudit
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConPrjBill
        this.actionConPrjBill = new ActionConPrjBill(this);
        getActionManager().registerAction("actionConPrjBill", actionConPrjBill);
         this.actionConPrjBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkLoad = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConfirmDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlWorkLoad = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtOrgUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCurProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtWorkLoad = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkConfirmDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtPartB = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlSplit = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlWorkLoadDetail = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conWorkLoadSplit = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conWorkLoadDetail = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblDetail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnConPrjBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemConPrjBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contAuditTime.setName("contAuditTime");
        this.contPeriod.setName("contPeriod");
        this.contCurProject.setName("contCurProject");
        this.contContractBill.setName("contContractBill");
        this.contWorkLoad.setName("contWorkLoad");
        this.contConfirmDate.setName("contConfirmDate");
        this.contPartB.setName("contPartB");
        this.pnlWorkLoad.setName("pnlWorkLoad");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtOrgUnit.setName("txtOrgUnit");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtPeriod.setName("prmtPeriod");
        this.txtCurProject.setName("txtCurProject");
        this.prmtContractBill.setName("prmtContractBill");
        this.txtWorkLoad.setName("txtWorkLoad");
        this.pkConfirmDate.setName("pkConfirmDate");
        this.prmtPartB.setName("prmtPartB");
        this.pnlSplit.setName("pnlSplit");
        this.pnlWorkLoadDetail.setName("pnlWorkLoadDetail");
        this.conWorkLoadSplit.setName("conWorkLoadSplit");
        this.tblEntrys.setName("tblEntrys");
        this.conWorkLoadDetail.setName("conWorkLoadDetail");
        this.tblDetail.setName("tblDetail");
        this.btnSplit.setName("btnSplit");
        this.btnConPrjBill.setName("btnConPrjBill");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemConPrjBill.setName("menuItemConPrjBill");
        // CoreUI		
        this.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.btnVoucher.setVisible(true);		
        this.btnDelVoucher.setVisible(true);		
        this.MenuItemVoucher.setVisible(true);		
        this.menuItemDelVoucher.setVisible(true);
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
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // contPeriod		
        this.contPeriod.setBoundLabelText(resHelper.getString("contPeriod.boundLabelText"));		
        this.contPeriod.setBoundLabelLength(100);		
        this.contPeriod.setBoundLabelUnderline(true);		
        this.contPeriod.setEnabled(false);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);		
        this.contCurProject.setEnabled(false);
        // contContractBill		
        this.contContractBill.setBoundLabelText(resHelper.getString("contContractBill.boundLabelText"));		
        this.contContractBill.setBoundLabelLength(100);		
        this.contContractBill.setBoundLabelUnderline(true);		
        this.contContractBill.setEnabled(false);
        // contWorkLoad		
        this.contWorkLoad.setBoundLabelText(resHelper.getString("contWorkLoad.boundLabelText"));		
        this.contWorkLoad.setBoundLabelLength(100);		
        this.contWorkLoad.setBoundLabelUnderline(true);
        // contConfirmDate		
        this.contConfirmDate.setBoundLabelText(resHelper.getString("contConfirmDate.boundLabelText"));		
        this.contConfirmDate.setBoundLabelLength(100);		
        this.contConfirmDate.setBoundLabelUnderline(true);
        // contPartB		
        this.contPartB.setBoundLabelText(resHelper.getString("contPartB.boundLabelText"));		
        this.contPartB.setBoundLabelLength(100);		
        this.contPartB.setBoundLabelUnderline(true);		
        this.contPartB.setEnabled(false);
        // pnlWorkLoad
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");
        // txtOrgUnit		
        this.txtOrgUnit.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtPeriod		
        this.prmtPeriod.setEnabled(false);
        // txtCurProject		
        this.txtCurProject.setEnabled(false);
        // prmtContractBill		
        this.prmtContractBill.setEnabled(false);		
        this.prmtContractBill.setDisplayFormat("$name$");
        // txtWorkLoad		
        this.txtWorkLoad.setRequired(true);
        // pkConfirmDate		
        this.pkConfirmDate.setRequired(true);
        // prmtPartB		
        this.prmtPartB.setDisplayFormat("$name$");		
        this.prmtPartB.setEditFormat("$number$");		
        this.prmtPartB.setCommitFormat("$name$");		
        this.prmtPartB.setEnabled(false);
        // pnlSplit
        // pnlWorkLoadDetail
        // conWorkLoadSplit		
        this.conWorkLoadSplit.setEnableActive(false);
        // tblEntrys
		String tblEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"account\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccount\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"curProject\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{curProject}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblEntrys.setFormatXml(resHelper.translateString("tblEntrys",tblEntrysStrXML));

        

        // conWorkLoadDetail		
        this.conWorkLoadDetail.setEnableActive(false);
        // tblDetail
		String tblDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"fillDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"task.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"task.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"accWorkLoadAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"accWorkLoadPct\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"completeAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"completePct\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"task.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"fillBillId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{fillDate}</t:Cell><t:Cell>$Resource{task.number}</t:Cell><t:Cell>$Resource{task.name}</t:Cell><t:Cell>$Resource{accWorkLoadAmt}</t:Cell><t:Cell>$Resource{accWorkLoadPct}</t:Cell><t:Cell>$Resource{completeAmt}</t:Cell><t:Cell>$Resource{completePct}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{task.id}</t:Cell><t:Cell>$Resource{fillBillId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblDetail.setFormatXml(resHelper.translateString("tblDetail",tblDetailStrXML));

        

        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // btnConPrjBill
        this.btnConPrjBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionConPrjBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConPrjBill.setText(resHelper.getString("btnConPrjBill.text"));		
        this.btnConPrjBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.btnConPrjBill.setToolTipText(resHelper.getString("btnConPrjBill.toolTipText"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));		
        this.menuItemSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // menuItemConPrjBill
        this.menuItemConPrjBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionConPrjBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemConPrjBill.setText(resHelper.getString("menuItemConPrjBill.text"));		
        this.menuItemConPrjBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.menuItemConPrjBill.setToolTipText(resHelper.getString("menuItemConPrjBill.toolTipText"));
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
        contCreator.setBounds(new Rectangle(16, 170, 450, 19));
        this.add(contCreator, new KDLayout.Constraints(16, 170, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(541, 170, 450, 19));
        this.add(contCreateTime, new KDLayout.Constraints(541, 170, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(16, 106, 450, 19));
        this.add(contNumber, new KDLayout.Constraints(16, 106, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(16, 74, 450, 19));
        this.add(contBizDate, new KDLayout.Constraints(16, 74, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(541, 138, 450, 19));
        this.add(contDescription, new KDLayout.Constraints(541, 138, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(16, 203, 450, 19));
        this.add(contAuditor, new KDLayout.Constraints(16, 203, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(16, 10, 450, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(16, 10, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(541, 203, 450, 19));
        this.add(contAuditTime, new KDLayout.Constraints(541, 203, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPeriod.setBounds(new Rectangle(540, 74, 450, 19));
        this.add(contPeriod, new KDLayout.Constraints(540, 74, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(540, 10, 450, 19));
        this.add(contCurProject, new KDLayout.Constraints(540, 10, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractBill.setBounds(new Rectangle(16, 42, 450, 19));
        this.add(contContractBill, new KDLayout.Constraints(16, 42, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWorkLoad.setBounds(new Rectangle(541, 106, 450, 19));
        this.add(contWorkLoad, new KDLayout.Constraints(541, 106, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConfirmDate.setBounds(new Rectangle(16, 138, 450, 19));
        this.add(contConfirmDate, new KDLayout.Constraints(16, 138, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPartB.setBounds(new Rectangle(540, 42, 450, 19));
        this.add(contPartB, new KDLayout.Constraints(540, 42, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlWorkLoad.setBounds(new Rectangle(7, 240, 989, 374));
        this.add(pnlWorkLoad, new KDLayout.Constraints(7, 240, 989, 374, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(txtOrgUnit);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contPeriod
        contPeriod.setBoundEditor(prmtPeriod);
        //contCurProject
        contCurProject.setBoundEditor(txtCurProject);
        //contContractBill
        contContractBill.setBoundEditor(prmtContractBill);
        //contWorkLoad
        contWorkLoad.setBoundEditor(txtWorkLoad);
        //contConfirmDate
        contConfirmDate.setBoundEditor(pkConfirmDate);
        //contPartB
        contPartB.setBoundEditor(prmtPartB);
        //pnlWorkLoad
        pnlWorkLoad.add(pnlSplit, resHelper.getString("pnlSplit.constraints"));
        pnlWorkLoad.add(pnlWorkLoadDetail, resHelper.getString("pnlWorkLoadDetail.constraints"));
        //pnlSplit
pnlSplit.setLayout(new BorderLayout(0, 0));        pnlSplit.add(conWorkLoadSplit, BorderLayout.CENTER);
        //conWorkLoadSplit
conWorkLoadSplit.getContentPane().setLayout(new BorderLayout(0, 0));        conWorkLoadSplit.getContentPane().add(tblEntrys, BorderLayout.CENTER);
        //pnlWorkLoadDetail
pnlWorkLoadDetail.setLayout(new BorderLayout(0, 0));        pnlWorkLoadDetail.add(conWorkLoadDetail, BorderLayout.CENTER);
        //conWorkLoadDetail
conWorkLoadDetail.getContentPane().setLayout(new BorderLayout(0, 0));        conWorkLoadDetail.getContentPane().add(tblDetail, BorderLayout.CENTER);

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
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemConPrjBill);
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
        this.toolBar.add(btnSplit);
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
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnConPrjBill);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("orgUnit.displayName", String.class, this.txtOrgUnit, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtPeriod, "data");
		dataBinder.registerBinding("curProject.displayName", String.class, this.txtCurProject, "text");
		dataBinder.registerBinding("contractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtContractBill, "data");
		dataBinder.registerBinding("workLoad", java.math.BigDecimal.class, this.txtWorkLoad, "value");
		dataBinder.registerBinding("confirmDate", java.util.Date.class, this.pkConfirmDate, "value");
		dataBinder.registerBinding("contractBill.partB.name", String.class, this.prmtPartB, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.WorkLoadConfirmBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo)ov;
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
		getValidateHelper().registerBindProperty("orgUnit.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workLoad", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("confirmDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.partB.name", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("orgUnit.displayName"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("period.*"));
        sic.add(new SelectorItemInfo("curProject.displayName"));
        sic.add(new SelectorItemInfo("contractBill.*"));
        sic.add(new SelectorItemInfo("workLoad"));
        sic.add(new SelectorItemInfo("confirmDate"));
        sic.add(new SelectorItemInfo("contractBill.partB.name"));
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
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConPrjBill_actionPerformed method
     */
    public void actionConPrjBill_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionConPrjBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConPrjBill() {
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
            innerActionPerformed("eas", AbstractWorkLoadConfirmBillEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionConPrjBill class
     */     
    protected class ActionConPrjBill extends ItemAction {     
    
        public ActionConPrjBill()
        {
            this(null);
        }

        public ActionConPrjBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConPrjBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConPrjBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConPrjBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWorkLoadConfirmBillEditUI.this, "ActionConPrjBill", "actionConPrjBill_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "WorkLoadConfirmBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}