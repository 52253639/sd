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
public abstract class AbstractDeptMonthlyTaskExecEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeptMonthlyTaskExecEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDSchReport;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWeightRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskOrigin;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkScheduleMonth;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRequiredResource;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFinishStandard;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtAdminDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompletePrecent;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker realStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker realEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker intendEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskName;
    protected com.kingdee.bos.ctrl.swing.KDTextField taskType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtWeightRate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanEndDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtProjectPeriod;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField comboTaskOrigin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField completePrecent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBizType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRelatedTask;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker planStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker planEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSchDelete;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSchReport;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable schTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExit;
    protected com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo editData = null;
    protected ActionSchReport actionSchReport = null;
    protected ActionSchDelete actionSchDelete = null;
    /**
     * output class constructor
     */
    public AbstractDeptMonthlyTaskExecEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDeptMonthlyTaskExecEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSchReport
        this.actionSchReport = new ActionSchReport(this);
        getActionManager().registerAction("actionSchReport", actionSchReport);
         this.actionSchReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSchDelete
        this.actionSchDelete = new ActionSchDelete(this);
        getActionManager().registerAction("actionSchDelete", actionSchDelete);
         this.actionSchDelete.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSchReport = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWeightRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanFinishDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskOrigin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pkScheduleMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtRequiredResource = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtFinishStandard = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCompletePrecent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.realStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.realEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.intendEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTaskName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.taskType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWeightRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPlanEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtProjectPeriod = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPlanStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboTaskOrigin = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.completePrecent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDBizPromptBox2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBizType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRelatedTask = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.planStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.planEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnSchDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSchReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.schTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnExit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel3.setName("kDPanel3");
        this.kDSchReport.setName("kDSchReport");
        this.kDPanel2.setName("kDPanel2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.contTaskName.setName("contTaskName");
        this.contTaskType.setName("contTaskType");
        this.contWeightRate.setName("contWeightRate");
        this.contPlanFinishDate.setName("contPlanFinishDate");
        this.contProjectPeriod.setName("contProjectPeriod");
        this.contPlanStartDate.setName("contPlanStartDate");
        this.contTaskOrigin.setName("contTaskOrigin");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.pkScheduleMonth.setName("pkScheduleMonth");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtRequiredResource.setName("txtRequiredResource");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtFinishStandard.setName("txtFinishStandard");
        this.txtAdminDept.setName("txtAdminDept");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.txtCompletePrecent.setName("txtCompletePrecent");
        this.realStartDate.setName("realStartDate");
        this.realEndDate.setName("realEndDate");
        this.intendEndDate.setName("intendEndDate");
        this.txtTaskName.setName("txtTaskName");
        this.taskType.setName("taskType");
        this.txtWeightRate.setName("txtWeightRate");
        this.pkPlanEndDate.setName("pkPlanEndDate");
        this.txtProjectPeriod.setName("txtProjectPeriod");
        this.pkPlanStartDate.setName("pkPlanStartDate");
        this.comboTaskOrigin.setName("comboTaskOrigin");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.contProject.setName("contProject");
        this.completePrecent.setName("completePrecent");
        this.kDBizPromptBox2.setName("kDBizPromptBox2");
        this.txtBizType.setName("txtBizType");
        this.prmtRelatedTask.setName("prmtRelatedTask");
        this.planStartDate.setName("planStartDate");
        this.planEndDate.setName("planEndDate");
        this.prmtProject.setName("prmtProject");
        this.btnSchDelete.setName("btnSchDelete");
        this.btnSchReport.setName("btnSchReport");
        this.schTable.setName("schTable");
        this.btnExit.setName("btnExit");
        // CoreUI		
        this.menuFile.setVisible(false);		
        this.menuHelp.setVisible(false);		
        this.menuTool.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuBiz.setVisible(false);
        // kDPanel1		
        this.kDPanel1.setBorder(null);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // kDSchReport		
        this.kDSchReport.setTitle(resHelper.getString("kDSchReport.title"));
        // kDPanel2		
        this.kDPanel2.setEnabled(false);		
        this.kDPanel2.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);		
        this.kDLabelContainer3.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.setForeground(new java.awt.Color(0,0,0));		
        this.kDLabelContainer5.setMinimumSize(new Dimension(10,10));		
        this.kDLabelContainer5.setPreferredSize(new Dimension(10,10));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);
        // contTaskName		
        this.contTaskName.setBoundLabelText(resHelper.getString("contTaskName.boundLabelText"));		
        this.contTaskName.setBoundLabelLength(100);		
        this.contTaskName.setBoundLabelUnderline(true);
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(100);		
        this.contTaskType.setBoundLabelUnderline(true);
        // contWeightRate		
        this.contWeightRate.setBoundLabelText(resHelper.getString("contWeightRate.boundLabelText"));		
        this.contWeightRate.setBoundLabelLength(100);		
        this.contWeightRate.setBoundLabelUnderline(true);
        // contPlanFinishDate		
        this.contPlanFinishDate.setBoundLabelText(resHelper.getString("contPlanFinishDate.boundLabelText"));		
        this.contPlanFinishDate.setBoundLabelLength(100);		
        this.contPlanFinishDate.setBoundLabelUnderline(true);
        // contProjectPeriod		
        this.contProjectPeriod.setBoundLabelText(resHelper.getString("contProjectPeriod.boundLabelText"));		
        this.contProjectPeriod.setBoundLabelLength(100);		
        this.contProjectPeriod.setBoundLabelUnderline(true);
        // contPlanStartDate		
        this.contPlanStartDate.setBoundLabelText(resHelper.getString("contPlanStartDate.boundLabelText"));		
        this.contPlanStartDate.setBoundLabelLength(100);		
        this.contPlanStartDate.setBoundLabelUnderline(true);
        // contTaskOrigin		
        this.contTaskOrigin.setBoundLabelText(resHelper.getString("contTaskOrigin.boundLabelText"));		
        this.contTaskOrigin.setBoundLabelLength(100);		
        this.contTaskOrigin.setBoundLabelUnderline(true);
        // kDTabbedPane1		
        this.kDTabbedPane1.setVisible(false);		
        this.kDTabbedPane1.setEnabled(false);
        // pkScheduleMonth		
        this.pkScheduleMonth.setEnabled(false);
        this.pkScheduleMonth.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkScheduleMonth_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane1
        // txtRequiredResource		
        this.txtRequiredResource.setVisible(true);		
        this.txtRequiredResource.setRequired(true);		
        this.txtRequiredResource.setMaxLength(2000);		
        this.txtRequiredResource.setForeground(new java.awt.Color(0,0,0));		
        this.txtRequiredResource.setLineWrap(true);		
        this.txtRequiredResource.setWrapStyleWord(true);		
        this.txtRequiredResource.setEnabled(false);
        // kDScrollPane2
        // txtFinishStandard		
        this.txtFinishStandard.setVisible(true);		
        this.txtFinishStandard.setRequired(true);		
        this.txtFinishStandard.setMaxLength(2000);		
        this.txtFinishStandard.setForeground(new java.awt.Color(0,0,0));		
        this.txtFinishStandard.setLineWrap(true);		
        this.txtFinishStandard.setWrapStyleWord(true);		
        this.txtFinishStandard.setEnabled(false);
        // txtAdminDept		
        this.txtAdminDept.setDisplayFormat("$name$");		
        this.txtAdminDept.setEditFormat("$name$");		
        this.txtAdminDept.setCommitFormat("$name$");		
        this.txtAdminDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.txtAdminDept.setEnabled(false);
        // prmtAdminPerson		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtAdminPerson.setDisplayFormat("$name$");		
        this.prmtAdminPerson.setEditFormat("$name$");		
        this.prmtAdminPerson.setCommitFormat("$name$");
        // txtCompletePrecent		
        this.txtCompletePrecent.setDataType(1);		
        this.txtCompletePrecent.setPrecision(2);		
        this.txtCompletePrecent.setSupportedEmpty(true);		
        this.txtCompletePrecent.setEnabled(false);
        // realStartDate		
        this.realStartDate.setEnabled(false);
        // realEndDate		
        this.realEndDate.setEnabled(false);
        // intendEndDate		
        this.intendEndDate.setEnabled(false);
        // txtTaskName		
        this.txtTaskName.setMaxLength(255);		
        this.txtTaskName.setEnabled(false);
        // taskType		
        this.taskType.setEnabled(false);
        // txtWeightRate		
        this.txtWeightRate.setEnabled(false);
        // pkPlanEndDate		
        this.pkPlanEndDate.setEnabled(false);
        // txtProjectPeriod		
        this.txtProjectPeriod.setEnabled(false);
        // pkPlanStartDate		
        this.pkPlanStartDate.setEnabled(false);
        // comboTaskOrigin		
        this.comboTaskOrigin.setMaxLength(255);		
        this.comboTaskOrigin.setEnabled(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // completePrecent		
        this.completePrecent.setDataType(1);		
        this.completePrecent.setPrecision(2);		
        this.completePrecent.setSupportedEmpty(true);		
        this.completePrecent.setEnabled(false);
        // kDBizPromptBox2
        // txtBizType		
        this.txtBizType.setMaxLength(255);		
        this.txtBizType.setEnabled(false);
        // prmtRelatedTask		
        this.prmtRelatedTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskQuery");		
        this.prmtRelatedTask.setDisplayFormat("$name$");		
        this.prmtRelatedTask.setEditFormat("$name$");		
        this.prmtRelatedTask.setCommitFormat("$name$");		
        this.prmtRelatedTask.setEnabled(false);
        // planStartDate		
        this.planStartDate.setEnabled(false);
        // planEndDate		
        this.planEndDate.setEnabled(false);
        // prmtProject		
        this.prmtProject.setMaxLength(255);		
        this.prmtProject.setEnabled(false);
        // btnSchDelete
        this.btnSchDelete.setAction((IItemAction)ActionProxyFactory.getProxy(actionSchDelete, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSchDelete.setText(resHelper.getString("btnSchDelete.text"));		
        this.btnSchDelete.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // btnSchReport
        this.btnSchReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionSchReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSchReport.setText(resHelper.getString("btnSchReport.text"));		
        this.btnSchReport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // schTable
		String schTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"reportor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"reportDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"completePrecent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"finishDescirption\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"progressEdition\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">汇报人</t:Cell><t:Cell t:configured=\"false\">汇报日期</t:Cell><t:Cell t:configured=\"false\">完成进度(%)</t:Cell><t:Cell t:configured=\"false\">完成情况说明</t:Cell><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">版本</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.schTable.setFormatXml(resHelper.translateString("schTable",schTableStrXML));

        

        // btnExit
        this.btnExit.setAction((IItemAction)ActionProxyFactory.getProxy(actionExitCurrent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExit.setText(resHelper.getString("btnExit.text"));		
        this.btnExit.setToolTipText(resHelper.getString("btnExit.toolTipText"));
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
        this.setLayout(null);
        kDPanel1.setBounds(new Rectangle(10, 10, 986, 294));
        this.add(kDPanel1, null);
        kDPanel3.setBounds(new Rectangle(10, 486, 981, 115));
        this.add(kDPanel3, null);
        kDSchReport.setBounds(new Rectangle(10, 316, 974, 158));
        this.add(kDSchReport, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(10, 10, 986, 294));        kDPanel2.setBounds(new Rectangle(920, 316, 15, 24));
        kDPanel1.add(kDPanel2, new KDLayout.Constraints(920, 316, 15, 24, 0));
        kDLabelContainer1.setBounds(new Rectangle(353, 10, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(353, 10, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(10, 223, 962, 64));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(10, 223, 962, 64, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(10, 145, 962, 64));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(10, 145, 962, 64, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(10, 10, 270, 19));
        kDPanel1.add(kDLabelContainer5, new KDLayout.Constraints(10, 10, 270, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(10, 62, 270, 19));
        kDPanel1.add(kDLabelContainer10, new KDLayout.Constraints(10, 62, 270, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(697, 39, 270, 19));
        kDPanel1.add(kDLabelContainer13, new KDLayout.Constraints(697, 39, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(10, 116, 270, 19));
        kDPanel1.add(kDLabelContainer14, new KDLayout.Constraints(10, 116, 270, 19, 0));
        kDLabelContainer15.setBounds(new Rectangle(354, 116, 270, 19));
        kDPanel1.add(kDLabelContainer15, new KDLayout.Constraints(354, 116, 270, 19, 0));
        kDLabelContainer16.setBounds(new Rectangle(697, 116, 270, 19));
        kDPanel1.add(kDLabelContainer16, new KDLayout.Constraints(697, 116, 270, 19, 0));
        contTaskName.setBounds(new Rectangle(10, 36, 270, 19));
        kDPanel1.add(contTaskName, new KDLayout.Constraints(10, 36, 270, 19, 0));
        contTaskType.setBounds(new Rectangle(354, 39, 270, 19));
        kDPanel1.add(contTaskType, new KDLayout.Constraints(354, 39, 270, 19, 0));
        contWeightRate.setBounds(new Rectangle(354, 64, 270, 19));
        kDPanel1.add(contWeightRate, new KDLayout.Constraints(354, 64, 270, 19, 0));
        contPlanFinishDate.setBounds(new Rectangle(354, 89, 270, 19));
        kDPanel1.add(contPlanFinishDate, new KDLayout.Constraints(354, 89, 270, 19, 0));
        contProjectPeriod.setBounds(new Rectangle(697, 89, 270, 19));
        kDPanel1.add(contProjectPeriod, new KDLayout.Constraints(697, 89, 270, 19, 0));
        contPlanStartDate.setBounds(new Rectangle(10, 88, 270, 19));
        kDPanel1.add(contPlanStartDate, new KDLayout.Constraints(10, 88, 270, 19, 0));
        contTaskOrigin.setBounds(new Rectangle(697, 10, 270, 19));
        kDPanel1.add(contTaskOrigin, new KDLayout.Constraints(697, 10, 270, 19, 0));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(920, 316, 15, 24));        kDTabbedPane1.setBounds(new Rectangle(-10, -2, 17, 55));
        kDPanel2.add(kDTabbedPane1, new KDLayout.Constraints(-10, -2, 17, 55, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkScheduleMonth);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtRequiredResource, null);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtFinishStandard, null);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtAdminDept);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(prmtAdminPerson);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtCompletePrecent);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(realStartDate);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(realEndDate);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(intendEndDate);
        //contTaskName
        contTaskName.setBoundEditor(txtTaskName);
        //contTaskType
        contTaskType.setBoundEditor(taskType);
        //contWeightRate
        contWeightRate.setBoundEditor(txtWeightRate);
        //contPlanFinishDate
        contPlanFinishDate.setBoundEditor(pkPlanEndDate);
        //contProjectPeriod
        contProjectPeriod.setBoundEditor(txtProjectPeriod);
        //contPlanStartDate
        contPlanStartDate.setBoundEditor(pkPlanStartDate);
        //contTaskOrigin
        contTaskOrigin.setBoundEditor(comboTaskOrigin);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(10, 486, 981, 115));        kDLabelContainer2.setBounds(new Rectangle(692, 71, 270, 19));
        kDPanel3.add(kDLabelContainer2, new KDLayout.Constraints(692, 71, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(602, 325, 270, 19));
        kDPanel3.add(kDLabelContainer6, new KDLayout.Constraints(602, 325, 270, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(353, 31, 270, 19));
        kDPanel3.add(kDLabelContainer8, new KDLayout.Constraints(353, 31, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(20, 31, 270, 19));
        kDPanel3.add(kDLabelContainer9, new KDLayout.Constraints(20, 31, 270, 19, 0));
        kDLabelContainer11.setBounds(new Rectangle(20, 71, 270, 19));
        kDPanel3.add(kDLabelContainer11, new KDLayout.Constraints(20, 71, 270, 19, 0));
        kDLabelContainer12.setBounds(new Rectangle(353, 71, 270, 19));
        kDPanel3.add(kDLabelContainer12, new KDLayout.Constraints(353, 71, 270, 19, 0));
        contProject.setBounds(new Rectangle(692, 31, 270, 19));
        kDPanel3.add(contProject, new KDLayout.Constraints(692, 31, 270, 19, 0));
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(completePrecent);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(kDBizPromptBox2);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtBizType);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(prmtRelatedTask);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(planStartDate);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(planEndDate);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //kDSchReport
        kDSchReport.getContentPane().setLayout(new KDLayout());
        kDSchReport.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 316, 974, 158));        btnSchDelete.setBounds(new Rectangle(791, 63, 22, 19));
        kDSchReport.getContentPane().add(btnSchDelete, new KDLayout.Constraints(791, 63, 22, 19, 0));
        btnSchReport.setBounds(new Rectangle(792, 28, 22, 19));
        kDSchReport.getContentPane().add(btnSchReport, new KDLayout.Constraints(792, 28, 22, 19, 0));
        schTable.setBounds(new Rectangle(0, 0, 973, 139));
        kDSchReport.getContentPane().add(schTable, new KDLayout.Constraints(0, 0, 973, 139, 0));

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
        this.menuBar.add(menuTool);
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
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnExit);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("requiredResource", String.class, this.txtRequiredResource, "text");
		dataBinder.registerBinding("finishStandard", String.class, this.txtFinishStandard, "text");
		dataBinder.registerBinding("relatedTask.adminDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.txtAdminDept, "data");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtAdminPerson, "data");
		dataBinder.registerBinding("taskName", String.class, this.txtTaskName, "text");
		dataBinder.registerBinding("taskType", com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.class, this.taskType, "text");
		dataBinder.registerBinding("weightRate", java.math.BigDecimal.class, this.txtWeightRate, "value");
		dataBinder.registerBinding("planFinishDate", java.util.Date.class, this.pkPlanEndDate, "value");
		dataBinder.registerBinding("projectPeriod", java.math.BigDecimal.class, this.txtProjectPeriod, "value");
		dataBinder.registerBinding("planStartDate", java.util.Date.class, this.pkPlanStartDate, "value");
		dataBinder.registerBinding("taskOrigin", com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum.class, this.comboTaskOrigin, "text");
		dataBinder.registerBinding("relatedTask.complete", java.math.BigDecimal.class, this.completePrecent, "value");
		dataBinder.registerBinding("relatedTask.taskType", com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.class, this.txtBizType, "text");
		dataBinder.registerBinding("relatedTask", com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo.class, this.prmtRelatedTask, "data");
		dataBinder.registerBinding("relatedTask.start", java.util.Date.class, this.planStartDate, "value");
		dataBinder.registerBinding("relatedTask.end", java.util.Date.class, this.planEndDate, "value");
		dataBinder.registerBinding("relatedTask.schedule.project.name", String.class, this.prmtProject, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.DeptMonthlyTaskExecEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("requiredResource", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finishStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("weightRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskOrigin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.complete", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.start", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.end", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.schedule.project.name", ValidateHelper.ON_SAVE);    		
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
     * output pkScheduleMonth_dataChanged method
     */
    protected void pkScheduleMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtRelatedTask_dataChanged method
     */
    protected void prmtRelatedTask_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("requiredResource"));
        sic.add(new SelectorItemInfo("finishStandard"));
        sic.add(new SelectorItemInfo("relatedTask.adminDept"));
        sic.add(new SelectorItemInfo("adminPerson.*"));
        sic.add(new SelectorItemInfo("taskName"));
        sic.add(new SelectorItemInfo("taskType"));
        sic.add(new SelectorItemInfo("weightRate"));
        sic.add(new SelectorItemInfo("planFinishDate"));
        sic.add(new SelectorItemInfo("projectPeriod"));
        sic.add(new SelectorItemInfo("planStartDate"));
        sic.add(new SelectorItemInfo("taskOrigin"));
        sic.add(new SelectorItemInfo("relatedTask.complete"));
        sic.add(new SelectorItemInfo("relatedTask.taskType"));
        sic.add(new SelectorItemInfo("relatedTask.*"));
        sic.add(new SelectorItemInfo("relatedTask.start"));
        sic.add(new SelectorItemInfo("relatedTask.end"));
        sic.add(new SelectorItemInfo("relatedTask.schedule.project.name"));
        return sic;
    }        
    	

    /**
     * output actionSchReport_actionPerformed method
     */
    public void actionSchReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSchDelete_actionPerformed method
     */
    public void actionSchDelete_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSchReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSchReport() {
    	return false;
    }
	public RequestContext prepareActionSchDelete(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSchDelete() {
    	return false;
    }

    /**
     * output ActionSchReport class
     */     
    protected class ActionSchReport extends ItemAction {     
    
        public ActionSchReport()
        {
            this(null);
        }

        public ActionSchReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSchReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSchReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSchReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecEditUI.this, "ActionSchReport", "actionSchReport_actionPerformed", e);
        }
    }

    /**
     * output ActionSchDelete class
     */     
    protected class ActionSchDelete extends ItemAction {     
    
        public ActionSchDelete()
        {
            this(null);
        }

        public ActionSchDelete(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSchDelete.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSchDelete.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSchDelete.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecEditUI.this, "ActionSchDelete", "actionSchDelete_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "DeptMonthlyTaskExecEditUI");
    }




}