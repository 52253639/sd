/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractMonthScheduleEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMonthScheduleEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPlanType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMonth;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInputMonthTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInputUltimoTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNewTempTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteTempTask;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTempTaskEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTaskEntrys;
    protected com.kingdee.eas.fdc.schedule.MonthScheduleInfo editData = null;
    protected ActionInputMonthTask actionInputMonthTask = null;
    protected ActionInputUltimoTask actionInputUltimoTask = null;
    protected ActionDeleteTask actionDeleteTask = null;
    protected ActionNewTempTask actionNewTempTask = null;
    protected ActionDeleteTempTask actionDeleteTempTask = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractMonthScheduleEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMonthScheduleEditUI.class.getName());
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
        //actionInputMonthTask
        this.actionInputMonthTask = new ActionInputMonthTask(this);
        getActionManager().registerAction("actionInputMonthTask", actionInputMonthTask);
         this.actionInputMonthTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInputUltimoTask
        this.actionInputUltimoTask = new ActionInputUltimoTask(this);
        getActionManager().registerAction("actionInputUltimoTask", actionInputUltimoTask);
         this.actionInputUltimoTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteTask
        this.actionDeleteTask = new ActionDeleteTask(this);
        getActionManager().registerAction("actionDeleteTask", actionDeleteTask);
         this.actionDeleteTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNewTempTask
        this.actionNewTempTask = new ActionNewTempTask(this);
        getActionManager().registerAction("actionNewTempTask", actionNewTempTask);
         this.actionNewTempTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteTempTask
        this.actionDeleteTempTask = new ActionDeleteTempTask(this);
        getActionManager().registerAction("actionDeleteTempTask", actionDeleteTempTask);
         this.actionDeleteTempTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contPlanType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboPlanType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnInputMonthTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInputUltimoTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNewTempTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteTempTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtTempTaskEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtTaskEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contCurProject.setName("contCurProject");
        this.prmtCurProject.setName("prmtCurProject");
        this.contPlanType.setName("contPlanType");
        this.comboPlanType.setName("comboPlanType");
        this.contAdminPerson.setName("contAdminPerson");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.contMonth.setName("contMonth");
        this.pkMonth.setName("pkMonth");
        this.contState.setName("contState");
        this.comboState.setName("comboState");
        this.contAuditDate.setName("contAuditDate");
        this.pkAuditDate.setName("pkAuditDate");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.btnInputMonthTask.setName("btnInputMonthTask");
        this.btnInputUltimoTask.setName("btnInputUltimoTask");
        this.btnDeleteTask.setName("btnDeleteTask");
        this.btnNewTempTask.setName("btnNewTempTask");
        this.btnDeleteTempTask.setName("btnDeleteTempTask");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kdtTempTaskEntrys.setName("kdtTempTaskEntrys");
        this.kdtTaskEntrys.setName("kdtTaskEntrys");
        // CoreUI		
        this.menuSubmitOption.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setRequired(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // pkCreateTime
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // prmtAuditor
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // prmtCurProject
        // contPlanType		
        this.contPlanType.setBoundLabelText(resHelper.getString("contPlanType.boundLabelText"));		
        this.contPlanType.setBoundLabelLength(100);		
        this.contPlanType.setBoundLabelUnderline(true);
        // comboPlanType		
        this.comboPlanType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.PlanTypeEnum").toArray());		
        this.comboPlanType.setRequired(true);
        this.comboPlanType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    planType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contAdminPerson		
        this.contAdminPerson.setBoundLabelText(resHelper.getString("contAdminPerson.boundLabelText"));		
        this.contAdminPerson.setBoundLabelLength(100);		
        this.contAdminPerson.setBoundLabelUnderline(true);
        // prmtAdminPerson		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtAdminPerson.setOpaque(true);		
        this.prmtAdminPerson.setDisplayFormat("$name$");		
        this.prmtAdminPerson.setEditFormat("$number$");		
        this.prmtAdminPerson.setCommitFormat("$number$");
        // contMonth		
        this.contMonth.setBoundLabelText(resHelper.getString("contMonth.boundLabelText"));		
        this.contMonth.setBoundLabelLength(100);		
        this.contMonth.setBoundLabelUnderline(true);
        // pkMonth		
        this.pkMonth.setRequired(true);
        this.pkMonth.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    month_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum").toArray());
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelLength(100);		
        this.contAuditDate.setBoundLabelUnderline(true);
        // pkAuditDate
        // kDTabbedPane1
        // btnInputMonthTask
        this.btnInputMonthTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionInputMonthTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInputMonthTask.setText(resHelper.getString("btnInputMonthTask.text"));		
        this.btnInputMonthTask.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnInputUltimoTask
        this.btnInputUltimoTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionInputUltimoTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInputUltimoTask.setText(resHelper.getString("btnInputUltimoTask.text"));		
        this.btnInputUltimoTask.setToolTipText(resHelper.getString("btnInputUltimoTask.toolTipText"));		
        this.btnInputUltimoTask.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_insert"));
        // btnDeleteTask
        this.btnDeleteTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteTask.setText(resHelper.getString("btnDeleteTask.text"));		
        this.btnDeleteTask.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // btnNewTempTask
        this.btnNewTempTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionNewTempTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNewTempTask.setText(resHelper.getString("btnNewTempTask.text"));		
        this.btnNewTempTask.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnDeleteTempTask
        this.btnDeleteTempTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteTempTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteTempTask.setText(resHelper.getString("btnDeleteTempTask.text"));		
        this.btnDeleteTempTask.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);
        // kdtTempTaskEntrys
		String kdtTempTaskEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>YYYY-mm-DD</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>YYYY-mm-DD</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"effectTimes1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"desc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{effectTimes1}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{ID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtTempTaskEntrys.setFormatXml(resHelper.translateString("kdtTempTaskEntrys",kdtTempTaskEntrysStrXML));
        this.kdtTempTaskEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tempTaskEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kdtTaskEntrys
		String kdtTaskEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,##00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,##00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"wbsNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"theMonthRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"lastMonthRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"effectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"natureTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"parent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"curProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"taskId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"taskNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{wbsNumber}</t:Cell><t:Cell>$Resource{theMonthRate}</t:Cell><t:Cell>$Resource{lastMonthRate}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{effectTimes}</t:Cell><t:Cell>$Resource{natureTimes}</t:Cell><t:Cell>$Resource{parent}</t:Cell><t:Cell>$Resource{curProject}</t:Cell><t:Cell>$Resource{taskId}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{taskNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtTaskEntrys.setFormatXml(resHelper.translateString("kdtTaskEntrys",kdtTaskEntrysStrXML));
        this.kdtTaskEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    taskEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtTaskEntrys.putBindContents("editData",new String[] {"task","task.wbs.longNumber","theMonthRate","lastMonthRate","task.start","task.end","task.effectTimes","task.natureTimes","","task.schedule.project.name","id","","task.number"});


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
        contCreator.setBounds(new Rectangle(370, 35, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(370, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(589, 1347, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(589, 1347, 270, 19, 0));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(457, 1331, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(457, 1331, 270, 19, 0));
        contName.setBounds(new Rectangle(370, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(370, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurProject.setBounds(new Rectangle(583, 1315, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(583, 1315, 270, 19, 0));
        contPlanType.setBounds(new Rectangle(720, 10, 270, 19));
        this.add(contPlanType, new KDLayout.Constraints(720, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAdminPerson.setBounds(new Rectangle(720, 35, 270, 19));
        this.add(contAdminPerson, new KDLayout.Constraints(720, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMonth.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contMonth, new KDLayout.Constraints(10, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(603, 1289, 270, 19));
        this.add(contState, new KDLayout.Constraints(603, 1289, 270, 19, 0));
        contAuditDate.setBounds(new Rectangle(141, 1336, 270, 19));
        this.add(contAuditDate, new KDLayout.Constraints(141, 1336, 270, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(10, 63, 980, 550));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 63, 980, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnInputMonthTask.setBounds(new Rectangle(303, 7, 22, 19));
        this.add(btnInputMonthTask, new KDLayout.Constraints(303, 7, 22, 19, 0));
        btnInputUltimoTask.setBounds(new Rectangle(303, 29, 22, 19));
        this.add(btnInputUltimoTask, new KDLayout.Constraints(303, 29, 22, 19, 0));
        btnDeleteTask.setBounds(new Rectangle(333, 27, 22, 19));
        this.add(btnDeleteTask, new KDLayout.Constraints(333, 27, 22, 19, 0));
        btnNewTempTask.setBounds(new Rectangle(669, 17, 22, 19));
        this.add(btnNewTempTask, new KDLayout.Constraints(669, 17, 22, 19, 0));
        btnDeleteTempTask.setBounds(new Rectangle(684, 43, 22, 19));
        this.add(btnDeleteTempTask, new KDLayout.Constraints(684, 43, 22, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contName
        contName.setBoundEditor(txtName);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contPlanType
        contPlanType.setBoundEditor(comboPlanType);
        //contAdminPerson
        contAdminPerson.setBoundEditor(prmtAdminPerson);
        //contMonth
        contMonth.setBoundEditor(pkMonth);
        //contState
        contState.setBoundEditor(comboState);
        //contAuditDate
        contAuditDate.setBoundEditor(pkAuditDate);
        //kDTabbedPane1
        kDTabbedPane1.add(kDContainer1, resHelper.getString("kDContainer1.constraints"));
        kDTabbedPane1.add(kDContainer2, resHelper.getString("kDContainer2.constraints"));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtTaskEntrys, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kdtTempTaskEntrys, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("planType", com.kingdee.eas.fdc.schedule.PlanTypeEnum.class, this.comboPlanType, "selectedItem");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtAdminPerson, "data");
		dataBinder.registerBinding("month", java.util.Date.class, this.pkMonth, "value");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkAuditDate, "value");
		dataBinder.registerBinding("taskEntrys", com.kingdee.eas.fdc.schedule.FDCMonthTaskEntryInfo.class, this.kdtTaskEntrys, "userObject");
		dataBinder.registerBinding("taskEntrys.task.start", java.util.Date.class, this.kdtTaskEntrys, "startDate.text");
		dataBinder.registerBinding("taskEntrys.task.end", java.util.Date.class, this.kdtTaskEntrys, "endDate.text");
		dataBinder.registerBinding("taskEntrys.task.effectTimes", java.math.BigDecimal.class, this.kdtTaskEntrys, "effectTimes.text");
		dataBinder.registerBinding("taskEntrys.task.natureTimes", java.math.BigDecimal.class, this.kdtTaskEntrys, "natureTimes.text");
		dataBinder.registerBinding("taskEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtTaskEntrys, "taskId.text");
		dataBinder.registerBinding("taskEntrys.task", com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo.class, this.kdtTaskEntrys, "taskName.text");
		dataBinder.registerBinding("taskEntrys.theMonthRate", java.math.BigDecimal.class, this.kdtTaskEntrys, "theMonthRate.text");
		dataBinder.registerBinding("taskEntrys.task.number", String.class, this.kdtTaskEntrys, "taskNumber.text");
		dataBinder.registerBinding("taskEntrys.task.wbs.longNumber", String.class, this.kdtTaskEntrys, "wbsNumber.text");
		dataBinder.registerBinding("taskEntrys.task.schedule.project.name", String.class, this.kdtTaskEntrys, "curProject.text");
		dataBinder.registerBinding("taskEntrys.lastMonthRate", java.math.BigDecimal.class, this.kdtTaskEntrys, "lastMonthRate.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.MonthScheduleEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.MonthScheduleInfo)ov;
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
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.start", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.end", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.effectTimes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.natureTimes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.theMonthRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.wbs.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.task.schedule.project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskEntrys.lastMonthRate", ValidateHelper.ON_SAVE);    		
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
     * output planType_itemStateChanged method
     */
    protected void planType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output month_dataChanged method
     */
    protected void month_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tempTaskEntrys_editStopped method
     */
    protected void tempTaskEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output taskEntrys_editStopped method
     */
    protected void taskEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("planType"));
        sic.add(new SelectorItemInfo("adminPerson.*"));
        sic.add(new SelectorItemInfo("month"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("taskEntrys.*"));
//        sic.add(new SelectorItemInfo("taskEntrys.number"));
    sic.add(new SelectorItemInfo("taskEntrys.task.start"));
    sic.add(new SelectorItemInfo("taskEntrys.task.end"));
    sic.add(new SelectorItemInfo("taskEntrys.task.effectTimes"));
    sic.add(new SelectorItemInfo("taskEntrys.task.natureTimes"));
    sic.add(new SelectorItemInfo("taskEntrys.id"));
        sic.add(new SelectorItemInfo("taskEntrys.task.*"));
//        sic.add(new SelectorItemInfo("taskEntrys.task.number"));
    sic.add(new SelectorItemInfo("taskEntrys.theMonthRate"));
    sic.add(new SelectorItemInfo("taskEntrys.task.number"));
    sic.add(new SelectorItemInfo("taskEntrys.task.wbs.longNumber"));
    sic.add(new SelectorItemInfo("taskEntrys.task.schedule.project.name"));
    sic.add(new SelectorItemInfo("taskEntrys.lastMonthRate"));
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
     * output actionInputMonthTask_actionPerformed method
     */
    public void actionInputMonthTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInputUltimoTask_actionPerformed method
     */
    public void actionInputUltimoTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteTask_actionPerformed method
     */
    public void actionDeleteTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNewTempTask_actionPerformed method
     */
    public void actionNewTempTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteTempTask_actionPerformed method
     */
    public void actionDeleteTempTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionInputMonthTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInputMonthTask() {
    	return false;
    }
	public RequestContext prepareActionInputUltimoTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInputUltimoTask() {
    	return false;
    }
	public RequestContext prepareActionDeleteTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteTask() {
    	return false;
    }
	public RequestContext prepareActionNewTempTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNewTempTask() {
    	return false;
    }
	public RequestContext prepareActionDeleteTempTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteTempTask() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output ActionInputMonthTask class
     */     
    protected class ActionInputMonthTask extends ItemAction {     
    
        public ActionInputMonthTask()
        {
            this(null);
        }

        public ActionInputMonthTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInputMonthTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInputMonthTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInputMonthTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMonthScheduleEditUI.this, "ActionInputMonthTask", "actionInputMonthTask_actionPerformed", e);
        }
    }

    /**
     * output ActionInputUltimoTask class
     */     
    protected class ActionInputUltimoTask extends ItemAction {     
    
        public ActionInputUltimoTask()
        {
            this(null);
        }

        public ActionInputUltimoTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInputUltimoTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInputUltimoTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInputUltimoTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMonthScheduleEditUI.this, "ActionInputUltimoTask", "actionInputUltimoTask_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteTask class
     */     
    protected class ActionDeleteTask extends ItemAction {     
    
        public ActionDeleteTask()
        {
            this(null);
        }

        public ActionDeleteTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMonthScheduleEditUI.this, "ActionDeleteTask", "actionDeleteTask_actionPerformed", e);
        }
    }

    /**
     * output ActionNewTempTask class
     */     
    protected class ActionNewTempTask extends ItemAction {     
    
        public ActionNewTempTask()
        {
            this(null);
        }

        public ActionNewTempTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNewTempTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNewTempTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNewTempTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMonthScheduleEditUI.this, "ActionNewTempTask", "actionNewTempTask_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteTempTask class
     */     
    protected class ActionDeleteTempTask extends ItemAction {     
    
        public ActionDeleteTempTask()
        {
            this(null);
        }

        public ActionDeleteTempTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteTempTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteTempTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteTempTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMonthScheduleEditUI.this, "ActionDeleteTempTask", "actionDeleteTempTask_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMonthScheduleEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "MonthScheduleEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}