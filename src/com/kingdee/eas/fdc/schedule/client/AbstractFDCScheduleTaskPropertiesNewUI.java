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
public abstract class AbstractFDCScheduleTaskPropertiesNewUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCScheduleTaskPropertiesNewUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlAdvanced;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTSchedulePane;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanStart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton txtYes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsAssessPoint;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton txtNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccessDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskGuide;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAchievementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkDay;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton schedulereportbtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton schedulereportbtndel;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlExecuteInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheckNode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyDep;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHelpDep;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheduleAppraisePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHelpPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQualityAppraisePerson;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelTaskGuide;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelPreposeTask;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelScheduleReport;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelTaskAppraise;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelStageAchievement;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelQualityInspectPoint;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelFigureSchedule;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelRelevanceSpecialTask;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelRelevanceCompact;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTableTaskGuideA;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTableTaskGuideB;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1TaskGuide;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTablePredecessor;
    protected com.kingdee.bos.ctrl.swing.KDContainer scheduleReportContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable scheduleReportTable;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerTaskApprise;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTaskApprise;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAchievement;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer6;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQuality;
    protected com.kingdee.bos.ctrl.swing.KDContainer contaner;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPSchedule;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerSpe;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSpecially;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerCon;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblContract;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPay;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanStart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBizType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbTaskType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanEnd;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAccessDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTaskGuide;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAchievementType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtWorkDay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conIntendEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkLoad;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompletePercent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalWorkLoad;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdDpActualStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdDpFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtWorkload;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtCompletePercent;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtTotalWorkload;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDesciption;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCheckNode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDutyDep;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHelpDep;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtScheduleAppraisePerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDutyPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHelpPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQualityAppraisePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndSchdule;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealityStart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriorityLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskCalendar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealityEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contShape;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppendEndQuantity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRiskChargePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBelongToSpecial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contColour;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEndSchedule;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRealityStart;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbPriorityLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTaskCalendar;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRealityEnd;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbShape;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAppendEndQuantity;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRiskChargePerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBelongSpecial;
    protected com.kingdee.bos.ctrl.swing.KDComboColor cbColour;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton saveBtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFirst;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPreview;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNext;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLast;
    protected ActionSave actionSave = null;
    protected AddNewScheduleReport addNewScheduleReport = null;
    protected DelScheduleReport delScheduleReport = null;
    protected actionAddTaskApprise actionAddTaskApprise = null;
    protected actionDeleteTaskApprise actionDeleteTaskApprise = null;
    protected actionAddRow actionAddRow = null;
    protected actionRemoveRow actionRemoveRow = null;
    protected actionRelevance actionRelevance = null;
    protected actionRelevanceCancel actionRelevanceCancel = null;
    protected actionAddContractRow actionAddContractRow = null;
    protected ActionSaveReport actionSaveReport = null;
    protected ActionFirst actionFirst = null;
    protected ActionNext actionNext = null;
    protected ActionPreview actionPreview = null;
    protected ActionLast actionLast = null;
    /**
     * output class constructor
     */
    public AbstractFDCScheduleTaskPropertiesNewUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractFDCScheduleTaskPropertiesNewUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //addNewScheduleReport
        this.addNewScheduleReport = new AddNewScheduleReport(this);
        getActionManager().registerAction("addNewScheduleReport", addNewScheduleReport);
         this.addNewScheduleReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //delScheduleReport
        this.delScheduleReport = new DelScheduleReport(this);
        getActionManager().registerAction("delScheduleReport", delScheduleReport);
         this.delScheduleReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTaskApprise
        this.actionAddTaskApprise = new actionAddTaskApprise(this);
        getActionManager().registerAction("actionAddTaskApprise", actionAddTaskApprise);
         this.actionAddTaskApprise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteTaskApprise
        this.actionDeleteTaskApprise = new actionDeleteTaskApprise(this);
        getActionManager().registerAction("actionDeleteTaskApprise", actionDeleteTaskApprise);
         this.actionDeleteTaskApprise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRow
        this.actionAddRow = new actionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveRow
        this.actionRemoveRow = new actionRemoveRow(this);
        getActionManager().registerAction("actionRemoveRow", actionRemoveRow);
         this.actionRemoveRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRelevance
        this.actionRelevance = new actionRelevance(this);
        getActionManager().registerAction("actionRelevance", actionRelevance);
         this.actionRelevance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRelevanceCancel
        this.actionRelevanceCancel = new actionRelevanceCancel(this);
        getActionManager().registerAction("actionRelevanceCancel", actionRelevanceCancel);
         this.actionRelevanceCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddContractRow
        this.actionAddContractRow = new actionAddContractRow(this);
        getActionManager().registerAction("actionAddContractRow", actionAddContractRow);
         this.actionAddContractRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveReport
        this.actionSaveReport = new ActionSaveReport(this);
        getActionManager().registerAction("actionSaveReport", actionSaveReport);
         this.actionSaveReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFirst
        this.actionFirst = new ActionFirst(this);
        getActionManager().registerAction("actionFirst", actionFirst);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNext
        this.actionNext = new ActionNext(this);
        getActionManager().registerAction("actionNext", actionNext);
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPreview
        this.actionPreview = new ActionPreview(this);
        getActionManager().registerAction("actionPreview", actionPreview);
         this.actionPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLast
        this.actionLast = new ActionLast(this);
        getActionManager().registerAction("actionLast", actionLast);
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlAdvanced = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTSchedulePane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contPlanStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contTaskName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contIsAssessPoint = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contPlanEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccessDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskGuide = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAchievementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkDay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.schedulereportbtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.schedulereportbtndel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pnlExecuteInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCheckNode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDutyDep = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHelpDep = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScheduleAppraisePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDutyPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHelpPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQualityAppraisePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDPanelTaskGuide = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelPreposeTask = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelScheduleReport = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelTaskAppraise = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelStageAchievement = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelQualityInspectPoint = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelFigureSchedule = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelRelevanceSpecialTask = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelRelevanceCompact = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTableTaskGuideA = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTableTaskGuideB = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDPanel1TaskGuide = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTablePredecessor = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.scheduleReportContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.scheduleReportTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainerTaskApprise = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTaskApprise = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblAchievement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer6 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQuality = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contaner = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblPSchedule = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainerSpe = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSpecially = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainerCon = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer7 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblPay = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pkPlanStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtBizType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTaskName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbTaskType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkPlanEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAccessDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtTaskGuide = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAchievementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtWorkDay = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contActualStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conIntendEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkLoad = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompletePercent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalWorkLoad = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdDpActualStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdDpFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtWorkload = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtCompletePercent = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtTotalWorkload = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtDesciption = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCheckNode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDutyDep = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHelpDep = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtScheduleAppraisePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDutyPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHelpPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQualityAppraisePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contEndSchdule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealityStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriorityLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskCalendar = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealityEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contShape = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppendEndQuantity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRiskChargePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBelongToSpecial = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contColour = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtEndSchedule = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkRealityStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPriorityLevel = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtTaskCalendar = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkRealityEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbShape = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtAppendEndQuantity = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtRiskChargePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBelongSpecial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbColour = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.saveBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFirst = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNext = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLast = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.pnlBaseInfo.setName("pnlBaseInfo");
        this.pnlAdvanced.setName("pnlAdvanced");
        this.kDTSchedulePane.setName("kDTSchedulePane");
        this.contPlanStart.setName("contPlanStart");
        this.contBizType.setName("contBizType");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.contTaskName.setName("contTaskName");
        this.contNumber.setName("contNumber");
        this.contTaskType.setName("contTaskType");
        this.txtYes.setName("txtYes");
        this.contIsAssessPoint.setName("contIsAssessPoint");
        this.txtNo.setName("txtNo");
        this.contPlanEnd.setName("contPlanEnd");
        this.contAccessDate.setName("contAccessDate");
        this.contTaskGuide.setName("contTaskGuide");
        this.contAchievementType.setName("contAchievementType");
        this.contWorkDay.setName("contWorkDay");
        this.btnAdd.setName("btnAdd");
        this.schedulereportbtn.setName("schedulereportbtn");
        this.schedulereportbtndel.setName("schedulereportbtndel");
        this.pnlExecuteInfo.setName("pnlExecuteInfo");
        this.btnDel.setName("btnDel");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contCheckNode.setName("contCheckNode");
        this.contDutyDep.setName("contDutyDep");
        this.contHelpDep.setName("contHelpDep");
        this.contScheduleAppraisePerson.setName("contScheduleAppraisePerson");
        this.contDutyPerson.setName("contDutyPerson");
        this.contHelpPerson.setName("contHelpPerson");
        this.contQualityAppraisePerson.setName("contQualityAppraisePerson");
        this.kDSeparator2.setName("kDSeparator2");
        this.kDSeparator3.setName("kDSeparator3");
        this.kDPanelTaskGuide.setName("kDPanelTaskGuide");
        this.kDPanelPreposeTask.setName("kDPanelPreposeTask");
        this.kDPanelScheduleReport.setName("kDPanelScheduleReport");
        this.kDPanelTaskAppraise.setName("kDPanelTaskAppraise");
        this.kDPanelStageAchievement.setName("kDPanelStageAchievement");
        this.kDPanelQualityInspectPoint.setName("kDPanelQualityInspectPoint");
        this.kDPanelFigureSchedule.setName("kDPanelFigureSchedule");
        this.kDPanelRelevanceSpecialTask.setName("kDPanelRelevanceSpecialTask");
        this.kDPanelRelevanceCompact.setName("kDPanelRelevanceCompact");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDContainer4.setName("kDContainer4");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.kDTableTaskGuideA.setName("kDTableTaskGuideA");
        this.kDTableTaskGuideB.setName("kDTableTaskGuideB");
        this.kDPanel1TaskGuide.setName("kDPanel1TaskGuide");
        this.kDContainer1.setName("kDContainer1");
        this.kDTablePredecessor.setName("kDTablePredecessor");
        this.scheduleReportContainer.setName("scheduleReportContainer");
        this.scheduleReportTable.setName("scheduleReportTable");
        this.kDContainerTaskApprise.setName("kDContainerTaskApprise");
        this.tblTaskApprise.setName("tblTaskApprise");
        this.kDContainer5.setName("kDContainer5");
        this.tblAchievement.setName("tblAchievement");
        this.kDContainer6.setName("kDContainer6");
        this.tblQuality.setName("tblQuality");
        this.contaner.setName("contaner");
        this.tblPSchedule.setName("tblPSchedule");
        this.kDContainerSpe.setName("kDContainerSpe");
        this.tblSpecially.setName("tblSpecially");
        this.kDContainerCon.setName("kDContainerCon");
        this.kDContainer7.setName("kDContainer7");
        this.tblContract.setName("tblContract");
        this.tblPay.setName("tblPay");
        this.pkPlanStart.setName("pkPlanStart");
        this.prmtBizType.setName("prmtBizType");
        this.txtTaskName.setName("txtTaskName");
        this.txtNumber.setName("txtNumber");
        this.cbTaskType.setName("cbTaskType");
        this.pkPlanEnd.setName("pkPlanEnd");
        this.pkAccessDate.setName("pkAccessDate");
        this.prmtTaskGuide.setName("prmtTaskGuide");
        this.prmtAchievementType.setName("prmtAchievementType");
        this.txtWorkDay.setName("txtWorkDay");
        this.contActualStartDate.setName("contActualStartDate");
        this.conIntendEndDate.setName("conIntendEndDate");
        this.contWorkLoad.setName("contWorkLoad");
        this.contCompletePercent.setName("contCompletePercent");
        this.contTotalWorkLoad.setName("contTotalWorkLoad");
        this.kdDpActualStartDate.setName("kdDpActualStartDate");
        this.kdDpFinishDate.setName("kdDpFinishDate");
        this.txtWorkload.setName("txtWorkload");
        this.txtCompletePercent.setName("txtCompletePercent");
        this.txtTotalWorkload.setName("txtTotalWorkload");
        this.txtDesciption.setName("txtDesciption");
        this.prmtCheckNode.setName("prmtCheckNode");
        this.prmtDutyDep.setName("prmtDutyDep");
        this.prmtHelpDep.setName("prmtHelpDep");
        this.prmtScheduleAppraisePerson.setName("prmtScheduleAppraisePerson");
        this.prmtDutyPerson.setName("prmtDutyPerson");
        this.prmtHelpPerson.setName("prmtHelpPerson");
        this.prmtQualityAppraisePerson.setName("prmtQualityAppraisePerson");
        this.contEndSchdule.setName("contEndSchdule");
        this.contRealityStart.setName("contRealityStart");
        this.contPriorityLevel.setName("contPriorityLevel");
        this.contTaskCalendar.setName("contTaskCalendar");
        this.contRealityEnd.setName("contRealityEnd");
        this.contShape.setName("contShape");
        this.contAppendEndQuantity.setName("contAppendEndQuantity");
        this.contRiskChargePerson.setName("contRiskChargePerson");
        this.contBelongToSpecial.setName("contBelongToSpecial");
        this.contColour.setName("contColour");
        this.txtEndSchedule.setName("txtEndSchedule");
        this.pkRealityStart.setName("pkRealityStart");
        this.cbPriorityLevel.setName("cbPriorityLevel");
        this.prmtTaskCalendar.setName("prmtTaskCalendar");
        this.pkRealityEnd.setName("pkRealityEnd");
        this.cbShape.setName("cbShape");
        this.txtAppendEndQuantity.setName("txtAppendEndQuantity");
        this.prmtRiskChargePerson.setName("prmtRiskChargePerson");
        this.prmtBelongSpecial.setName("prmtBelongSpecial");
        this.cbColour.setName("cbColour");
        this.saveBtn.setName("saveBtn");
        this.btnReport.setName("btnReport");
        this.btnFirst.setName("btnFirst");
        this.btnPreview.setName("btnPreview");
        this.btnNext.setName("btnNext");
        this.btnLast.setName("btnLast");
        // CoreUI		
        this.setPreferredSize(new Dimension(960,550));
        // kDScrollPane1
        // kDTabbedPane1		
        this.kDTabbedPane1.setPreferredSize(new Dimension(900,650));
        // pnlBaseInfo
        // pnlAdvanced
        // kDTSchedulePane
        // contPlanStart		
        this.contPlanStart.setBoundLabelText(resHelper.getString("contPlanStart.boundLabelText"));		
        this.contPlanStart.setBoundLabelLength(100);		
        this.contPlanStart.setBoundLabelUnderline(true);
        // contBizType		
        this.contBizType.setBoundLabelText(resHelper.getString("contBizType.boundLabelText"));		
        this.contBizType.setBoundLabelLength(100);		
        this.contBizType.setBoundLabelUnderline(true);
        // kDWorkButton1		
        this.kDWorkButton1.setText(resHelper.getString("kDWorkButton1.text"));
        // kDWorkButton2		
        this.kDWorkButton2.setText(resHelper.getString("kDWorkButton2.text"));
        // contTaskName		
        this.contTaskName.setBoundLabelText(resHelper.getString("contTaskName.boundLabelText"));		
        this.contTaskName.setBoundLabelLength(100);		
        this.contTaskName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(100);		
        this.contTaskType.setBoundLabelUnderline(true);
        // txtYes		
        this.txtYes.setText(resHelper.getString("txtYes.text"));
        // contIsAssessPoint		
        this.contIsAssessPoint.setBoundLabelText(resHelper.getString("contIsAssessPoint.boundLabelText"));		
        this.contIsAssessPoint.setBoundLabelLength(100);		
        this.contIsAssessPoint.setBoundLabelUnderline(true);
        // txtNo		
        this.txtNo.setText(resHelper.getString("txtNo.text"));
        // contPlanEnd		
        this.contPlanEnd.setBoundLabelText(resHelper.getString("contPlanEnd.boundLabelText"));		
        this.contPlanEnd.setBoundLabelLength(100);		
        this.contPlanEnd.setBoundLabelUnderline(true);
        // contAccessDate		
        this.contAccessDate.setBoundLabelText(resHelper.getString("contAccessDate.boundLabelText"));		
        this.contAccessDate.setBoundLabelLength(100);		
        this.contAccessDate.setBoundLabelUnderline(true);
        // contTaskGuide		
        this.contTaskGuide.setBoundLabelText(resHelper.getString("contTaskGuide.boundLabelText"));		
        this.contTaskGuide.setBoundLabelLength(100);		
        this.contTaskGuide.setBoundLabelUnderline(true);
        // contAchievementType		
        this.contAchievementType.setBoundLabelText(resHelper.getString("contAchievementType.boundLabelText"));		
        this.contAchievementType.setBoundLabelLength(100);		
        this.contAchievementType.setBoundLabelUnderline(true);
        // contWorkDay		
        this.contWorkDay.setBoundLabelText(resHelper.getString("contWorkDay.boundLabelText"));		
        this.contWorkDay.setBoundLabelLength(100);		
        this.contWorkDay.setBoundLabelUnderline(true);
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.txtYes);
        this.kDButtonGroup1.add(this.txtNo);
        // btnAdd		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));		
        this.btnAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // schedulereportbtn
        this.schedulereportbtn.setAction((IItemAction)ActionProxyFactory.getProxy(addNewScheduleReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.schedulereportbtn.setText(resHelper.getString("schedulereportbtn.text"));
        // schedulereportbtndel
        this.schedulereportbtndel.setAction((IItemAction)ActionProxyFactory.getProxy(delScheduleReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.schedulereportbtndel.setMnemonic(65);		
        this.schedulereportbtndel.setText(resHelper.getString("schedulereportbtndel.text"));
        this.schedulereportbtndel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    schedulereportbtndel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pnlExecuteInfo		
        this.pnlExecuteInfo.setBorder(null);
        // btnDel		
        this.btnDel.setText(resHelper.getString("btnDel.text"));		
        this.btnDel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contCheckNode		
        this.contCheckNode.setBoundLabelText(resHelper.getString("contCheckNode.boundLabelText"));		
        this.contCheckNode.setBoundLabelLength(100);		
        this.contCheckNode.setBoundLabelUnderline(true);
        // contDutyDep		
        this.contDutyDep.setBoundLabelText(resHelper.getString("contDutyDep.boundLabelText"));		
        this.contDutyDep.setBoundLabelLength(100);		
        this.contDutyDep.setBoundLabelUnderline(true);
        // contHelpDep		
        this.contHelpDep.setBoundLabelText(resHelper.getString("contHelpDep.boundLabelText"));		
        this.contHelpDep.setBoundLabelLength(100);		
        this.contHelpDep.setBoundLabelUnderline(true);
        // contScheduleAppraisePerson		
        this.contScheduleAppraisePerson.setBoundLabelText(resHelper.getString("contScheduleAppraisePerson.boundLabelText"));		
        this.contScheduleAppraisePerson.setBoundLabelLength(100);		
        this.contScheduleAppraisePerson.setBoundLabelUnderline(true);
        // contDutyPerson		
        this.contDutyPerson.setBoundLabelText(resHelper.getString("contDutyPerson.boundLabelText"));		
        this.contDutyPerson.setBoundLabelLength(100);		
        this.contDutyPerson.setBoundLabelUnderline(true);
        // contHelpPerson		
        this.contHelpPerson.setBoundLabelText(resHelper.getString("contHelpPerson.boundLabelText"));		
        this.contHelpPerson.setBoundLabelLength(100);		
        this.contHelpPerson.setBoundLabelUnderline(true);
        // contQualityAppraisePerson		
        this.contQualityAppraisePerson.setBoundLabelText(resHelper.getString("contQualityAppraisePerson.boundLabelText"));		
        this.contQualityAppraisePerson.setBoundLabelLength(100);		
        this.contQualityAppraisePerson.setBoundLabelUnderline(true);
        // kDSeparator2
        // kDSeparator3
        // kDPanelTaskGuide
        // kDPanelPreposeTask
        // kDPanelScheduleReport
        // kDPanelTaskAppraise
        // kDPanelStageAchievement
        // kDPanelQualityInspectPoint
        // kDPanelFigureSchedule
        // kDPanelRelevanceSpecialTask
        // kDPanelRelevanceCompact		
        this.kDPanelRelevanceCompact.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(450);
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(160);
        // kDContainer4		
        this.kDContainer4.setEnableActive(false);		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer3		
        this.kDContainer3.setEnableActive(false);		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kDTableTaskGuideA
		String kDTableTaskGuideAStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"isCPFile\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell><t:Cell>$Resource{docID}</t:Cell><t:Cell>$Resource{isCPFile}</t:Cell><t:Cell>$Resource{guideType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTableTaskGuideA.setFormatXml(resHelper.translateString("kDTableTaskGuideA",kDTableTaskGuideAStrXML));
        this.kDTableTaskGuideA.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDTableTaskGuideA_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDTableTaskGuideB
		String kDTableTaskGuideBStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"isCPFile\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"docID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell><t:Cell>$Resource{isCPFile}</t:Cell><t:Cell>$Resource{docID}</t:Cell><t:Cell>$Resource{guideType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTableTaskGuideB.setFormatXml(resHelper.translateString("kDTableTaskGuideB",kDTableTaskGuideBStrXML));
        this.kDTableTaskGuideB.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDTableTaskGuideB_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDPanel1TaskGuide
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDTablePredecessor
		String kDTablePredecessorStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"280\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"linkType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"diff\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{linkType}</t:Cell><t:Cell>$Resource{diff}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTablePredecessor.setFormatXml(resHelper.translateString("kDTablePredecessor",kDTablePredecessorStrXML));

        

        // scheduleReportContainer		
        this.scheduleReportContainer.setTitle(resHelper.getString("scheduleReportContainer.title"));		
        this.scheduleReportContainer.setEnableActive(false);
        // scheduleReportTable
		String scheduleReportTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"reportor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"reportData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"completePrecent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"completeAmount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{reportor}</t:Cell><t:Cell>$Resource{reportData}</t:Cell><t:Cell>$Resource{completePrecent}</t:Cell><t:Cell>$Resource{completeAmount}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.scheduleReportTable.setFormatXml(resHelper.translateString("scheduleReportTable",scheduleReportTableStrXML));
        this.scheduleReportTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    scheduleReportTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainerTaskApprise		
        this.kDContainerTaskApprise.setEnableActive(false);		
        this.kDContainerTaskApprise.setTitle(resHelper.getString("kDContainerTaskApprise.title"));
        // tblTaskApprise
		String tblTaskAppriseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"evaluationResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"evaluationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"ID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{evaluationResult}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{evaluationType}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{ID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblTaskApprise.setFormatXml(resHelper.translateString("tblTaskApprise",tblTaskAppriseStrXML));
        this.tblTaskApprise.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTaskApprise_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer5		
        this.kDContainer5.setTitle(resHelper.getString("kDContainer5.title"));		
        this.kDContainer5.setEnableActive(false);
        // tblAchievement
		String tblAchievementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"adoc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"aType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"submitPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"submitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{adoc}</t:Cell><t:Cell>$Resource{aType}</t:Cell><t:Cell>$Resource{submitPerson}</t:Cell><t:Cell>$Resource{submitDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAchievement.setFormatXml(resHelper.translateString("tblAchievement",tblAchievementStrXML));
        this.tblAchievement.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblAchievement_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer6		
        this.kDContainer6.setTitle(resHelper.getString("kDContainer6.title"));		
        this.kDContainer6.setEnableActive(false);
        // tblQuality
		String tblQualityStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"checkPoint\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"checkPost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"checkPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"checkResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"subPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"subDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{checkPoint}</t:Cell><t:Cell>$Resource{checkPost}</t:Cell><t:Cell>$Resource{checkPercent}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{checkResult}</t:Cell><t:Cell>$Resource{subPerson}</t:Cell><t:Cell>$Resource{subDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblQuality.setFormatXml(resHelper.translateString("tblQuality",tblQualityStrXML));
        this.tblQuality.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblQuality_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contaner		
        this.contaner.setTitle(resHelper.getString("contaner.title"));		
        this.contaner.setEnableActive(false);
        // tblPSchedule
		String tblPScheduleStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"imgDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{imgDescription}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPSchedule.setFormatXml(resHelper.translateString("tblPSchedule",tblPScheduleStrXML));
        this.tblPSchedule.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPSchedule_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainerSpe		
        this.kDContainerSpe.setTitle(resHelper.getString("kDContainerSpe.title"));
        // tblSpecially
		String tblSpeciallyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectSpeci\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"planStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"planEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"projectTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"responPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"responDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{projectSpeci}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{planStartDate}</t:Cell><t:Cell>$Resource{planEndDate}</t:Cell><t:Cell>$Resource{projectTime}</t:Cell><t:Cell>$Resource{responPerson}</t:Cell><t:Cell>$Resource{responDept}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblSpecially.setFormatXml(resHelper.translateString("tblSpecially",tblSpeciallyStrXML));

        

        // kDContainerCon		
        this.kDContainerCon.setTitle(resHelper.getString("kDContainerCon.title"));		
        this.kDContainerCon.setEnableActive(false);
        // kDContainer7		
        this.kDContainer7.setTitle(resHelper.getString("kDContainer7.title"));		
        this.kDContainer7.setEnableActive(false);
        // tblContract
		String tblContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"responPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"responDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"signPartyB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{responPerson}</t:Cell><t:Cell>$Resource{responDept}</t:Cell><t:Cell>$Resource{signPartyB}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblContract.setFormatXml(resHelper.translateString("tblContract",tblContractStrXML));

        

        // tblPay
		String tblPayStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"payMonth\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"payMoneyY\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"payMoneyN\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"currentMonthPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"currentPayY\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{payMonth}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{payMoneyY}</t:Cell><t:Cell>$Resource{payMoneyN}</t:Cell><t:Cell>$Resource{currentMonthPay}</t:Cell><t:Cell>$Resource{currentPayY}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPay.setFormatXml(resHelper.translateString("tblPay",tblPayStrXML));

        

        // pkPlanStart		
        this.pkPlanStart.setRequired(true);
        // prmtBizType		
        this.prmtBizType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.ScheduleBizTypeQuery");		
        this.prmtBizType.setCommitFormat("$name$");		
        this.prmtBizType.setEditFormat("$name$");		
        this.prmtBizType.setDisplayFormat("$name$");
        // txtTaskName		
        this.txtTaskName.setRequired(true);		
        this.txtTaskName.setMaxLength(100);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // cbTaskType		
        this.cbTaskType.setEditable(true);		
        this.cbTaskType.setRequired(true);
        this.cbTaskType.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    cbTaskType_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.cbTaskType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbTaskType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkPlanEnd		
        this.pkPlanEnd.setRequired(true);
        // pkAccessDate
        // prmtTaskGuide		
        this.prmtTaskGuide.setQueryInfo("com.kingdee.eas.fdc.schedule.app.StandardTaskGuideNewQuery");		
        this.prmtTaskGuide.setCommitFormat("$name$");		
        this.prmtTaskGuide.setDisplayFormat("$name$");		
        this.prmtTaskGuide.setEditFormat("$name$");
        this.prmtTaskGuide.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtTaskGuide_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAchievementType		
        this.prmtAchievementType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.AchievementTypeQuery");		
        this.prmtAchievementType.setCommitFormat("$name$");		
        this.prmtAchievementType.setDisplayFormat("$name$");		
        this.prmtAchievementType.setEditFormat("$name$");		
        this.prmtAchievementType.setRequired(true);
        // txtWorkDay		
        this.txtWorkDay.setPrecision(0);		
        this.txtWorkDay.setSupportedEmpty(true);
        // contActualStartDate		
        this.contActualStartDate.setBoundLabelText(resHelper.getString("contActualStartDate.boundLabelText"));		
        this.contActualStartDate.setBoundLabelUnderline(true);		
        this.contActualStartDate.setBoundLabelLength(100);
        // conIntendEndDate		
        this.conIntendEndDate.setBoundLabelText(resHelper.getString("conIntendEndDate.boundLabelText"));		
        this.conIntendEndDate.setBoundLabelLength(110);		
        this.conIntendEndDate.setBoundLabelUnderline(true);
        // contWorkLoad		
        this.contWorkLoad.setBoundLabelText(resHelper.getString("contWorkLoad.boundLabelText"));		
        this.contWorkLoad.setBoundLabelLength(100);		
        this.contWorkLoad.setBoundLabelUnderline(true);
        // contCompletePercent		
        this.contCompletePercent.setBoundLabelText(resHelper.getString("contCompletePercent.boundLabelText"));		
        this.contCompletePercent.setBoundLabelUnderline(true);		
        this.contCompletePercent.setBoundLabelLength(100);
        // contTotalWorkLoad		
        this.contTotalWorkLoad.setBoundLabelText(resHelper.getString("contTotalWorkLoad.boundLabelText"));		
        this.contTotalWorkLoad.setBoundLabelUnderline(true);		
        this.contTotalWorkLoad.setBoundLabelLength(100);
        // kdDpActualStartDate		
        this.kdDpActualStartDate.setEditable(false);
        this.kdDpActualStartDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kdDpActualStartDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdDpFinishDate		
        this.kdDpFinishDate.setEditable(false);
        this.kdDpFinishDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kdDpFinishDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtWorkload
        this.txtWorkload.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtWorkload_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtCompletePercent
        this.txtCompletePercent.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtCompletePercent_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTotalWorkload
        // txtDesciption		
        this.txtDesciption.setMaxLength(250);
        // prmtCheckNode		
        this.prmtCheckNode.setQueryInfo("com.kingdee.eas.fdc.schedule.app.CheckNodeQuery");		
        this.prmtCheckNode.setCommitFormat("$name$");		
        this.prmtCheckNode.setDisplayFormat("$name$");		
        this.prmtCheckNode.setEditFormat("$name$");
        this.prmtCheckNode.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtCheckNode_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtDutyDep		
        this.prmtDutyDep.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");		
        this.prmtDutyDep.setCommitFormat("$name$");		
        this.prmtDutyDep.setDisplayFormat("$name$");		
        this.prmtDutyDep.setEditFormat("$name$");
        // prmtHelpDep		
        this.prmtHelpDep.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtHelpDep.setCommitFormat("$name$");		
        this.prmtHelpDep.setDisplayFormat("$name$");		
        this.prmtHelpDep.setEditFormat("$name$");
        // prmtScheduleAppraisePerson		
        this.prmtScheduleAppraisePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtScheduleAppraisePerson.setCommitFormat("$name$");		
        this.prmtScheduleAppraisePerson.setDisplayFormat("$name$");		
        this.prmtScheduleAppraisePerson.setEditFormat("$name$");
        // prmtDutyPerson		
        this.prmtDutyPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtDutyPerson.setCommitFormat("$name$");		
        this.prmtDutyPerson.setDisplayFormat("$name$");		
        this.prmtDutyPerson.setEditFormat("$name$");
        // prmtHelpPerson		
        this.prmtHelpPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtHelpPerson.setCommitFormat("$name$");		
        this.prmtHelpPerson.setEditFormat("$name$");		
        this.prmtHelpPerson.setDisplayFormat("$name$");
        // prmtQualityAppraisePerson		
        this.prmtQualityAppraisePerson.setEditFormat("$name$");		
        this.prmtQualityAppraisePerson.setDisplayFormat("$name$");		
        this.prmtQualityAppraisePerson.setCommitFormat("$name$");		
        this.prmtQualityAppraisePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        // contEndSchdule		
        this.contEndSchdule.setBoundLabelText(resHelper.getString("contEndSchdule.boundLabelText"));		
        this.contEndSchdule.setBoundLabelLength(110);		
        this.contEndSchdule.setBoundLabelUnderline(true);
        // contRealityStart		
        this.contRealityStart.setBoundLabelText(resHelper.getString("contRealityStart.boundLabelText"));		
        this.contRealityStart.setBoundLabelLength(110);		
        this.contRealityStart.setBoundLabelUnderline(true);
        // contPriorityLevel		
        this.contPriorityLevel.setBoundLabelText(resHelper.getString("contPriorityLevel.boundLabelText"));		
        this.contPriorityLevel.setBoundLabelLength(110);		
        this.contPriorityLevel.setBoundLabelUnderline(true);
        // contTaskCalendar		
        this.contTaskCalendar.setBoundLabelText(resHelper.getString("contTaskCalendar.boundLabelText"));		
        this.contTaskCalendar.setBoundLabelLength(110);		
        this.contTaskCalendar.setBoundLabelUnderline(true);
        // contRealityEnd		
        this.contRealityEnd.setBoundLabelText(resHelper.getString("contRealityEnd.boundLabelText"));		
        this.contRealityEnd.setBoundLabelLength(110);		
        this.contRealityEnd.setBoundLabelUnderline(true);
        // contShape		
        this.contShape.setBoundLabelText(resHelper.getString("contShape.boundLabelText"));		
        this.contShape.setBoundLabelLength(110);		
        this.contShape.setBoundLabelUnderline(true);
        // contAppendEndQuantity		
        this.contAppendEndQuantity.setBoundLabelText(resHelper.getString("contAppendEndQuantity.boundLabelText"));		
        this.contAppendEndQuantity.setBoundLabelLength(110);		
        this.contAppendEndQuantity.setBoundLabelUnderline(true);
        // contRiskChargePerson		
        this.contRiskChargePerson.setBoundLabelText(resHelper.getString("contRiskChargePerson.boundLabelText"));		
        this.contRiskChargePerson.setBoundLabelLength(110);		
        this.contRiskChargePerson.setBoundLabelUnderline(true);
        // contBelongToSpecial		
        this.contBelongToSpecial.setBoundLabelText(resHelper.getString("contBelongToSpecial.boundLabelText"));		
        this.contBelongToSpecial.setBoundLabelUnderline(true);		
        this.contBelongToSpecial.setBoundLabelLength(110);		
        this.contBelongToSpecial.setVisible(false);
        // contColour		
        this.contColour.setBoundLabelText(resHelper.getString("contColour.boundLabelText"));		
        this.contColour.setBoundLabelLength(110);		
        this.contColour.setBoundLabelUnderline(true);
        // txtEndSchedule		
        this.txtEndSchedule.setDataType(1);		
        this.txtEndSchedule.setPrecision(2);		
        this.txtEndSchedule.setSupportedEmpty(true);		
        this.txtEndSchedule.setEnabled(false);
        // pkRealityStart		
        this.pkRealityStart.setEnabled(false);
        // cbPriorityLevel
        // prmtTaskCalendar		
        this.prmtTaskCalendar.setQueryInfo("com.kingdee.eas.fdc.schedule.framework.app.ScheduleCalendarQuery");		
        this.prmtTaskCalendar.setCommitFormat("$name$");		
        this.prmtTaskCalendar.setDisplayFormat("$name$");		
        this.prmtTaskCalendar.setEditFormat("$name$");
        // pkRealityEnd		
        this.pkRealityEnd.setEnabled(false);
        // cbShape
        // txtAppendEndQuantity		
        this.txtAppendEndQuantity.setDataType(1);		
        this.txtAppendEndQuantity.setSupportedEmpty(true);		
        this.txtAppendEndQuantity.setPrecision(2);		
        this.txtAppendEndQuantity.setEnabled(false);
        // prmtRiskChargePerson		
        this.prmtRiskChargePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtRiskChargePerson.setCommitFormat("$name$");		
        this.prmtRiskChargePerson.setEditFormat("$name$");		
        this.prmtRiskChargePerson.setDisplayFormat("$name$");
        // prmtBelongSpecial		
        this.prmtBelongSpecial.setQueryInfo("com.kingdee.eas.fdc.schedule.app.ProjectSpecialQuery");
        this.prmtBelongSpecial.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtBelongSpecial_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbColour
        // saveBtn
        this.saveBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.saveBtn.setText(resHelper.getString("saveBtn.text"));
        // btnReport
        this.btnReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReport.setText(resHelper.getString("btnReport.text"));
        // btnFirst
        this.btnFirst.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirst, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFirst.setText(resHelper.getString("btnFirst.text"));		
        this.btnFirst.setVisible(false);
        // btnPreview
        this.btnPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPreview.setText(resHelper.getString("btnPreview.text"));		
        this.btnPreview.setVisible(false);
        // btnNext
        this.btnNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNext.setText(resHelper.getString("btnNext.text"));		
        this.btnNext.setVisible(false);
        // btnLast
        this.btnLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLast.setText(resHelper.getString("btnLast.text"));		
        this.btnLast.setVisible(false);
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
        this.setBounds(new Rectangle(10, 10, 960, 550));
this.setLayout(new BorderLayout(0, 0));
        this.add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(pnlBaseInfo, resHelper.getString("pnlBaseInfo.constraints"));
        kDTabbedPane1.add(pnlAdvanced, resHelper.getString("pnlAdvanced.constraints"));
        //pnlBaseInfo
        pnlBaseInfo.setLayout(null);        kDTSchedulePane.setBounds(new Rectangle(8, 289, 921, 341));
        pnlBaseInfo.add(kDTSchedulePane, null);
        contPlanStart.setBounds(new Rectangle(8, 51, 270, 19));
        pnlBaseInfo.add(contPlanStart, null);
        contBizType.setBounds(new Rectangle(333, 29, 270, 19));
        pnlBaseInfo.add(contBizType, null);
        kDWorkButton1.setBounds(new Rectangle(610, 351, 22, 19));
        pnlBaseInfo.add(kDWorkButton1, null);
        kDWorkButton2.setBounds(new Rectangle(670, 350, 22, 19));
        pnlBaseInfo.add(kDWorkButton2, null);
        contTaskName.setBounds(new Rectangle(8, 7, 270, 19));
        pnlBaseInfo.add(contTaskName, null);
        contNumber.setBounds(new Rectangle(333, 7, 270, 19));
        pnlBaseInfo.add(contNumber, null);
        contTaskType.setBounds(new Rectangle(8, 29, 270, 19));
        pnlBaseInfo.add(contTaskType, null);
        txtYes.setBounds(new Rectangle(114, 74, 33, 19));
        pnlBaseInfo.add(txtYes, null);
        contIsAssessPoint.setBounds(new Rectangle(8, 74, 100, 19));
        pnlBaseInfo.add(contIsAssessPoint, null);
        txtNo.setBounds(new Rectangle(161, 74, 33, 19));
        pnlBaseInfo.add(txtNo, null);
        contPlanEnd.setBounds(new Rectangle(333, 51, 270, 19));
        pnlBaseInfo.add(contPlanEnd, null);
        contAccessDate.setBounds(new Rectangle(333, 74, 270, 19));
        pnlBaseInfo.add(contAccessDate, null);
        contTaskGuide.setBounds(new Rectangle(658, 7, 270, 19));
        pnlBaseInfo.add(contTaskGuide, null);
        contAchievementType.setBounds(new Rectangle(658, 29, 270, 19));
        pnlBaseInfo.add(contAchievementType, null);
        contWorkDay.setBounds(new Rectangle(658, 51, 270, 19));
        pnlBaseInfo.add(contWorkDay, null);
        btnAdd.setBounds(new Rectangle(626, 305, 22, 19));
        pnlBaseInfo.add(btnAdd, null);
        schedulereportbtn.setBounds(new Rectangle(681, 320, 85, 19));
        pnlBaseInfo.add(schedulereportbtn, null);
        schedulereportbtndel.setBounds(new Rectangle(782, 319, 93, 19));
        pnlBaseInfo.add(schedulereportbtndel, null);
        pnlExecuteInfo.setBounds(new Rectangle(708, 109, 182, 53));
        pnlBaseInfo.add(pnlExecuteInfo, null);
        btnDel.setBounds(new Rectangle(651, 308, 22, 19));
        pnlBaseInfo.add(btnDel, null);
        kDLabelContainer1.setBounds(new Rectangle(8, 184, 921, 97));
        pnlBaseInfo.add(kDLabelContainer1, null);
        contCheckNode.setBounds(new Rectangle(658, 74, 270, 19));
        pnlBaseInfo.add(contCheckNode, null);
        contDutyDep.setBounds(new Rectangle(8, 106, 270, 19));
        pnlBaseInfo.add(contDutyDep, null);
        contHelpDep.setBounds(new Rectangle(8, 128, 270, 19));
        pnlBaseInfo.add(contHelpDep, null);
        contScheduleAppraisePerson.setBounds(new Rectangle(8, 150, 270, 19));
        pnlBaseInfo.add(contScheduleAppraisePerson, null);
        contDutyPerson.setBounds(new Rectangle(333, 106, 270, 19));
        pnlBaseInfo.add(contDutyPerson, null);
        contHelpPerson.setBounds(new Rectangle(333, 128, 270, 19));
        pnlBaseInfo.add(contHelpPerson, null);
        contQualityAppraisePerson.setBounds(new Rectangle(333, 150, 270, 19));
        pnlBaseInfo.add(contQualityAppraisePerson, null);
        kDSeparator2.setBounds(new Rectangle(1, 98, 953, 10));
        pnlBaseInfo.add(kDSeparator2, null);
        kDSeparator3.setBounds(new Rectangle(1, 176, 953, 10));
        pnlBaseInfo.add(kDSeparator3, null);
        //kDTSchedulePane
        kDTSchedulePane.add(kDPanelTaskGuide, resHelper.getString("kDPanelTaskGuide.constraints"));
        kDTSchedulePane.add(kDPanelPreposeTask, resHelper.getString("kDPanelPreposeTask.constraints"));
        kDTSchedulePane.add(kDPanelScheduleReport, resHelper.getString("kDPanelScheduleReport.constraints"));
        kDTSchedulePane.add(kDPanelTaskAppraise, resHelper.getString("kDPanelTaskAppraise.constraints"));
        kDTSchedulePane.add(kDPanelStageAchievement, resHelper.getString("kDPanelStageAchievement.constraints"));
        kDTSchedulePane.add(kDPanelQualityInspectPoint, resHelper.getString("kDPanelQualityInspectPoint.constraints"));
        kDTSchedulePane.add(kDPanelFigureSchedule, resHelper.getString("kDPanelFigureSchedule.constraints"));
        kDTSchedulePane.add(kDPanelRelevanceSpecialTask, resHelper.getString("kDPanelRelevanceSpecialTask.constraints"));
        kDTSchedulePane.add(kDPanelRelevanceCompact, resHelper.getString("kDPanelRelevanceCompact.constraints"));
        //kDPanelTaskGuide
kDPanelTaskGuide.setLayout(new BorderLayout(0, 0));        kDPanelTaskGuide.add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(kDSplitPane2, "left");
        kDSplitPane1.add(kDContainer4, "right");
        //kDSplitPane2
        kDSplitPane2.add(kDContainer2, "top");
        kDSplitPane2.add(kDContainer3, "bottom");
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kDTableTaskGuideA, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(kDTableTaskGuideB, BorderLayout.CENTER);
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer4.getContentPane().add(kDPanel1TaskGuide, BorderLayout.CENTER);
        kDPanel1TaskGuide.setLayout(null);        //kDPanelPreposeTask
kDPanelPreposeTask.setLayout(new BorderLayout(0, 0));        kDPanelPreposeTask.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDTablePredecessor, BorderLayout.CENTER);
        //kDPanelScheduleReport
kDPanelScheduleReport.setLayout(new BorderLayout(0, 0));        kDPanelScheduleReport.add(scheduleReportContainer, BorderLayout.CENTER);
        //scheduleReportContainer
scheduleReportContainer.getContentPane().setLayout(new BorderLayout(0, 0));        scheduleReportContainer.getContentPane().add(scheduleReportTable, BorderLayout.CENTER);
        //kDPanelTaskAppraise
kDPanelTaskAppraise.setLayout(new BorderLayout(0, 0));        kDPanelTaskAppraise.add(kDContainerTaskApprise, BorderLayout.CENTER);
        //kDContainerTaskApprise
kDContainerTaskApprise.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerTaskApprise.getContentPane().add(tblTaskApprise, BorderLayout.CENTER);
        //kDPanelStageAchievement
kDPanelStageAchievement.setLayout(new BorderLayout(0, 0));        kDPanelStageAchievement.add(kDContainer5, BorderLayout.CENTER);
        //kDContainer5
kDContainer5.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer5.getContentPane().add(tblAchievement, BorderLayout.CENTER);
        //kDPanelQualityInspectPoint
kDPanelQualityInspectPoint.setLayout(new BorderLayout(0, 0));        kDPanelQualityInspectPoint.add(kDContainer6, BorderLayout.CENTER);
        //kDContainer6
kDContainer6.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer6.getContentPane().add(tblQuality, BorderLayout.CENTER);
        //kDPanelFigureSchedule
kDPanelFigureSchedule.setLayout(new BorderLayout(0, 0));        kDPanelFigureSchedule.add(contaner, BorderLayout.CENTER);
        //contaner
contaner.getContentPane().setLayout(new BorderLayout(0, 0));        contaner.getContentPane().add(tblPSchedule, BorderLayout.CENTER);
        //kDPanelRelevanceSpecialTask
kDPanelRelevanceSpecialTask.setLayout(new BorderLayout(0, 0));        kDPanelRelevanceSpecialTask.add(kDContainerSpe, BorderLayout.CENTER);
        //kDContainerSpe
kDContainerSpe.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerSpe.getContentPane().add(tblSpecially, BorderLayout.CENTER);
        //kDPanelRelevanceCompact
kDPanelRelevanceCompact.setLayout(new BorderLayout(0, 0));        kDPanelRelevanceCompact.add(kDContainerCon, BorderLayout.NORTH);
        kDPanelRelevanceCompact.add(kDContainer7, BorderLayout.CENTER);
        //kDContainerCon
kDContainerCon.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerCon.getContentPane().add(tblContract, BorderLayout.CENTER);
        //kDContainer7
kDContainer7.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer7.getContentPane().add(tblPay, BorderLayout.CENTER);
        //contPlanStart
        contPlanStart.setBoundEditor(pkPlanStart);
        //contBizType
        contBizType.setBoundEditor(prmtBizType);
        //contTaskName
        contTaskName.setBoundEditor(txtTaskName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contTaskType
        contTaskType.setBoundEditor(cbTaskType);
        //contPlanEnd
        contPlanEnd.setBoundEditor(pkPlanEnd);
        //contAccessDate
        contAccessDate.setBoundEditor(pkAccessDate);
        //contTaskGuide
        contTaskGuide.setBoundEditor(prmtTaskGuide);
        //contAchievementType
        contAchievementType.setBoundEditor(prmtAchievementType);
        //contWorkDay
        contWorkDay.setBoundEditor(txtWorkDay);
        //pnlExecuteInfo
        pnlExecuteInfo.setLayout(new KDLayout());
        pnlExecuteInfo.putClientProperty("OriginalBounds", new Rectangle(708, 109, 182, 53));        contActualStartDate.setBounds(new Rectangle(6, 5, 270, 19));
        pnlExecuteInfo.add(contActualStartDate, new KDLayout.Constraints(6, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conIntendEndDate.setBounds(new Rectangle(315, 4, 270, 19));
        pnlExecuteInfo.add(conIntendEndDate, new KDLayout.Constraints(315, 4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWorkLoad.setBounds(new Rectangle(638, 6, 270, 19));
        pnlExecuteInfo.add(contWorkLoad, new KDLayout.Constraints(638, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompletePercent.setBounds(new Rectangle(6, 27, 270, 19));
        pnlExecuteInfo.add(contCompletePercent, new KDLayout.Constraints(6, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalWorkLoad.setBounds(new Rectangle(638, 27, 270, 19));
        pnlExecuteInfo.add(contTotalWorkLoad, new KDLayout.Constraints(638, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contActualStartDate
        contActualStartDate.setBoundEditor(kdDpActualStartDate);
        //conIntendEndDate
        conIntendEndDate.setBoundEditor(kdDpFinishDate);
        //contWorkLoad
        contWorkLoad.setBoundEditor(txtWorkload);
        //contCompletePercent
        contCompletePercent.setBoundEditor(txtCompletePercent);
        //contTotalWorkLoad
        contTotalWorkLoad.setBoundEditor(txtTotalWorkload);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtDesciption);
        //contCheckNode
        contCheckNode.setBoundEditor(prmtCheckNode);
        //contDutyDep
        contDutyDep.setBoundEditor(prmtDutyDep);
        //contHelpDep
        contHelpDep.setBoundEditor(prmtHelpDep);
        //contScheduleAppraisePerson
        contScheduleAppraisePerson.setBoundEditor(prmtScheduleAppraisePerson);
        //contDutyPerson
        contDutyPerson.setBoundEditor(prmtDutyPerson);
        //contHelpPerson
        contHelpPerson.setBoundEditor(prmtHelpPerson);
        //contQualityAppraisePerson
        contQualityAppraisePerson.setBoundEditor(prmtQualityAppraisePerson);
        //pnlAdvanced
        pnlAdvanced.setLayout(null);        contEndSchdule.setBounds(new Rectangle(658, 10, 270, 19));
        pnlAdvanced.add(contEndSchdule, null);
        contRealityStart.setBounds(new Rectangle(10, 10, 270, 19));
        pnlAdvanced.add(contRealityStart, null);
        contPriorityLevel.setBounds(new Rectangle(10, 32, 270, 19));
        pnlAdvanced.add(contPriorityLevel, null);
        contTaskCalendar.setBounds(new Rectangle(10, 54, 270, 19));
        pnlAdvanced.add(contTaskCalendar, null);
        contRealityEnd.setBounds(new Rectangle(334, 10, 270, 19));
        pnlAdvanced.add(contRealityEnd, null);
        contShape.setBounds(new Rectangle(334, 32, 270, 19));
        pnlAdvanced.add(contShape, null);
        contAppendEndQuantity.setBounds(new Rectangle(658, 32, 270, 19));
        pnlAdvanced.add(contAppendEndQuantity, null);
        contRiskChargePerson.setBounds(new Rectangle(334, 54, 270, 19));
        pnlAdvanced.add(contRiskChargePerson, null);
        contBelongToSpecial.setBounds(new Rectangle(10, 76, 270, 19));
        pnlAdvanced.add(contBelongToSpecial, null);
        contColour.setBounds(new Rectangle(658, 54, 270, 19));
        pnlAdvanced.add(contColour, null);
        //contEndSchdule
        contEndSchdule.setBoundEditor(txtEndSchedule);
        //contRealityStart
        contRealityStart.setBoundEditor(pkRealityStart);
        //contPriorityLevel
        contPriorityLevel.setBoundEditor(cbPriorityLevel);
        //contTaskCalendar
        contTaskCalendar.setBoundEditor(prmtTaskCalendar);
        //contRealityEnd
        contRealityEnd.setBoundEditor(pkRealityEnd);
        //contShape
        contShape.setBoundEditor(cbShape);
        //contAppendEndQuantity
        contAppendEndQuantity.setBoundEditor(txtAppendEndQuantity);
        //contRiskChargePerson
        contRiskChargePerson.setBoundEditor(prmtRiskChargePerson);
        //contBelongToSpecial
        contBelongToSpecial.setBoundEditor(prmtBelongSpecial);
        //contColour
        contColour.setBoundEditor(cbColour);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(saveBtn);
        this.toolBar.add(btnReport);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPreview);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskPropertiesNewUIHandler";
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
     * output schedulereportbtndel_actionPerformed method
     */
    protected void schedulereportbtndel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDTableTaskGuideA_tableClicked method
     */
    protected void kDTableTaskGuideA_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kDTableTaskGuideB_tableClicked method
     */
    protected void kDTableTaskGuideB_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output scheduleReportTable_tableClicked method
     */
    protected void scheduleReportTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblTaskApprise_tableClicked method
     */
    protected void tblTaskApprise_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblAchievement_tableClicked method
     */
    protected void tblAchievement_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblQuality_tableClicked method
     */
    protected void tblQuality_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblPSchedule_tableClicked method
     */
    protected void tblPSchedule_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output cbTaskType_propertyChange method
     */
    protected void cbTaskType_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output cbTaskType_actionPerformed method
     */
    protected void cbTaskType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtTaskGuide_dataChanged method
     */
    protected void prmtTaskGuide_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdDpActualStartDate_dataChanged method
     */
    protected void kdDpActualStartDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdDpFinishDate_dataChanged method
     */
    protected void kdDpFinishDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtWorkload_dataChanged method
     */
    protected void txtWorkload_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtCompletePercent_dataChanged method
     */
    protected void txtCompletePercent_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCheckNode_willShow method
     */
    protected void prmtCheckNode_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtBelongSpecial_willShow method
     */
    protected void prmtBelongSpecial_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output addNewScheduleReport_actionPerformed method
     */
    public void addNewScheduleReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output delScheduleReport_actionPerformed method
     */
    public void delScheduleReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTaskApprise_actionPerformed method
     */
    public void actionAddTaskApprise_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteTaskApprise_actionPerformed method
     */
    public void actionDeleteTaskApprise_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveRow_actionPerformed method
     */
    public void actionRemoveRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRelevance_actionPerformed method
     */
    public void actionRelevance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRelevanceCancel_actionPerformed method
     */
    public void actionRelevanceCancel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddContractRow_actionPerformed method
     */
    public void actionAddContractRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveReport_actionPerformed method
     */
    public void actionSaveReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNext_actionPerformed method
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPreview_actionPerformed method
     */
    public void actionPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLast_actionPerformed method
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	public RequestContext prepareAddNewScheduleReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAddNewScheduleReport() {
    	return false;
    }
	public RequestContext prepareDelScheduleReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareDelScheduleReport() {
    	return false;
    }
	public RequestContext prepareactionAddTaskApprise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddTaskApprise() {
    	return false;
    }
	public RequestContext prepareactionDeleteTaskApprise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionDeleteTaskApprise() {
    	return false;
    }
	public RequestContext prepareactionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddRow() {
    	return false;
    }
	public RequestContext prepareactionRemoveRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionRemoveRow() {
    	return false;
    }
	public RequestContext prepareactionRelevance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionRelevance() {
    	return false;
    }
	public RequestContext prepareactionRelevanceCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionRelevanceCancel() {
    	return false;
    }
	public RequestContext prepareactionAddContractRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddContractRow() {
    	return false;
    }
	public RequestContext prepareActionSaveReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveReport() {
    	return false;
    }
	public RequestContext prepareActionFirst(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirst() {
    	return false;
    }
	public RequestContext prepareActionNext(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNext() {
    	return false;
    }
	public RequestContext prepareActionPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPreview() {
    	return false;
    }
	public RequestContext prepareActionLast(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLast() {
    	return false;
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output AddNewScheduleReport class
     */     
    protected class AddNewScheduleReport extends ItemAction {     
    
        public AddNewScheduleReport()
        {
            this(null);
        }

        public AddNewScheduleReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("AddNewScheduleReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AddNewScheduleReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AddNewScheduleReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "AddNewScheduleReport", "addNewScheduleReport_actionPerformed", e);
        }
    }

    /**
     * output DelScheduleReport class
     */     
    protected class DelScheduleReport extends ItemAction {     
    
        public DelScheduleReport()
        {
            this(null);
        }

        public DelScheduleReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("DelScheduleReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DelScheduleReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DelScheduleReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "DelScheduleReport", "delScheduleReport_actionPerformed", e);
        }
    }

    /**
     * output actionAddTaskApprise class
     */     
    protected class actionAddTaskApprise extends ItemAction {     
    
        public actionAddTaskApprise()
        {
            this(null);
        }

        public actionAddTaskApprise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddTaskApprise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddTaskApprise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddTaskApprise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionAddTaskApprise", "actionAddTaskApprise_actionPerformed", e);
        }
    }

    /**
     * output actionDeleteTaskApprise class
     */     
    protected class actionDeleteTaskApprise extends ItemAction {     
    
        public actionDeleteTaskApprise()
        {
            this(null);
        }

        public actionDeleteTaskApprise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionDeleteTaskApprise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionDeleteTaskApprise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionDeleteTaskApprise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionDeleteTaskApprise", "actionDeleteTaskApprise_actionPerformed", e);
        }
    }

    /**
     * output actionAddRow class
     */     
    protected class actionAddRow extends ItemAction {     
    
        public actionAddRow()
        {
            this(null);
        }

        public actionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output actionRemoveRow class
     */     
    protected class actionRemoveRow extends ItemAction {     
    
        public actionRemoveRow()
        {
            this(null);
        }

        public actionRemoveRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionRemoveRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionRemoveRow", "actionRemoveRow_actionPerformed", e);
        }
    }

    /**
     * output actionRelevance class
     */     
    protected class actionRelevance extends ItemAction {     
    
        public actionRelevance()
        {
            this(null);
        }

        public actionRelevance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionRelevance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRelevance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRelevance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionRelevance", "actionRelevance_actionPerformed", e);
        }
    }

    /**
     * output actionRelevanceCancel class
     */     
    protected class actionRelevanceCancel extends ItemAction {     
    
        public actionRelevanceCancel()
        {
            this(null);
        }

        public actionRelevanceCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionRelevanceCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRelevanceCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRelevanceCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionRelevanceCancel", "actionRelevanceCancel_actionPerformed", e);
        }
    }

    /**
     * output actionAddContractRow class
     */     
    protected class actionAddContractRow extends ItemAction {     
    
        public actionAddContractRow()
        {
            this(null);
        }

        public actionAddContractRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddContractRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddContractRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddContractRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "actionAddContractRow", "actionAddContractRow_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveReport class
     */     
    protected class ActionSaveReport extends ItemAction {     
    
        public ActionSaveReport()
        {
            this(null);
        }

        public ActionSaveReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSaveReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "ActionSaveReport", "actionSaveReport_actionPerformed", e);
        }
    }

    /**
     * output ActionFirst class
     */     
    protected class ActionFirst extends ItemAction {     
    
        public ActionFirst()
        {
            this(null);
        }

        public ActionFirst(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirst.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "ActionFirst", "actionFirst_actionPerformed", e);
        }
    }

    /**
     * output ActionNext class
     */     
    protected class ActionNext extends ItemAction {     
    
        public ActionNext()
        {
            this(null);
        }

        public ActionNext(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNext.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNext.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNext.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "ActionNext", "actionNext_actionPerformed", e);
        }
    }

    /**
     * output ActionPreview class
     */     
    protected class ActionPreview extends ItemAction {     
    
        public ActionPreview()
        {
            this(null);
        }

        public ActionPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "ActionPreview", "actionPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionLast class
     */     
    protected class ActionLast extends ItemAction {     
    
        public ActionLast()
        {
            this(null);
        }

        public ActionLast(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionLast.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLast.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLast.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesNewUI.this, "ActionLast", "actionLast_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FDCScheduleTaskPropertiesNewUI");
    }




}