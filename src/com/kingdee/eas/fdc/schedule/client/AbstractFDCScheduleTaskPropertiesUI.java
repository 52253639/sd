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
public abstract class AbstractFDCScheduleTaskPropertiesUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCScheduleTaskPropertiesUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel predecessorsPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel workloadPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel costInfoPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel materialPlanPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel qualityPlanPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel workResultPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel taskLogPanel;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane3;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPnlSchedule;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPnlExecute;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNatureTimes;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkKey;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDComboColor txtColor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelPart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExecor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWBSNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEffectTimes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdministrator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriority;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contShape;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtNatureTimes;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNoter;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Part;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtManager;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtExecor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWBSNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEffectTimes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdministrator;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboxPriority;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboxShape;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRistResPerson;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsMileStone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conMileStoneStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboxMileStoneStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbBizType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualStartDate;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtProcess;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkActualDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkActualStartDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPredecessorPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable predecessorsTable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conPlanWorkload;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDContainer conWorkload;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWorkLoadView;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCompletePercent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanWorkload;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMeasureUnit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblWorkload;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompletePercent;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAimCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conDeviation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conActCost;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCostMemo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAimCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDeviation;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tableSafe;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSafeAddline;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSafeDeleteline;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSafeEditLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAuditResult;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWorkFlow;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tableSafePlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSafePlanAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSafePlanEdit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSafePlanDel;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel preventionPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel qualityCheckPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel qualityFaultPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel bigEventPanel;
    protected com.kingdee.bos.ctrl.swing.KDContainer conPrevention;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPrevention;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQCItem;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQCPosition;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQtyRelt;
    protected com.kingdee.bos.ctrl.swing.KDContainer conBigEvent;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQualityBigEvent;
    protected com.kingdee.bos.ctrl.swing.KDContainer conWorkResult;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblWorkResult;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labTaskLogList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labTaskLogDescription;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTaskLog;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTaskLog;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelTaskLog;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditTaskLog;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtTaskLogDetail;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOK;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaskPre;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaskNext;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttacthment;
    protected ActionOK actionOK = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionDeleteLine actionDeleteLine = null;
    protected ActionQualityAddline actionQualityAddline = null;
    protected ActionQualityDeleteLine actionQualityDeleteLine = null;
    protected ActionSafeAddline actionSafeAddline = null;
    protected ActionSafeDeleteLine actionSafeDeleteLine = null;
    protected ActionTaskPre actionTaskPre = null;
    protected ActionTaskNext actionTaskNext = null;
    protected ActionAuditResult actionAuditResult = null;
    protected ActionQualityEditLine actionQualityEditLine = null;
    protected NewRowAction newRowAction = null;
    protected DeleteRowAction deleteRowAction = null;
    protected ModifyAction modifyAction = null;
    protected ActionSafeEditline actionSafeEditline = null;
    protected ActionSafeAudit actionSafeAudit = null;
    protected ActionSafeUnAudit actionSafeUnAudit = null;
    protected ActionSafeWorkFlowGraphics actionSafeWorkFlowGraphics = null;
    protected ActionSafeWorkFlowLog actionSafeWorkFlowLog = null;
    protected ActionQualityWorkFlowAudit actionQualityWorkFlowAudit = null;
    protected ActionQualityWorkFlowUnAudit actionQualityWorkFlowUnAudit = null;
    protected ActionQualityWorkFlowGraphics actionQualityWorkFlowGraphics = null;
    protected ActionQualityWorkFlowLog actionQualityWorkFlowLog = null;
    protected ActionAttachment actionAttachment = null;
    protected ActionQualityPlanAdd actionQualityPlanAdd = null;
    protected ActionQualityPlanEdit actionQualityPlanEdit = null;
    protected ActionQualityPlanDel actionQualityPlanDel = null;
    protected ActionSafePlanAdd actionSafePlanAdd = null;
    protected ActionSafePlanEdit actionSafePlanEdit = null;
    protected ActionSafePlanDel actionSafePlanDel = null;
    protected ActionMaterialPlanAdd actionMaterialPlanAdd = null;
    protected ActionMaterialPlanEdit actionMaterialPlanEdit = null;
    protected ActionMaterialPlanDel actionMaterialPlanDel = null;
    protected ActionMaterialEventAdd actionMaterialEventAdd = null;
    protected ActionMaterialEventEdit actionMaterialEventEdit = null;
    protected ActionMaterialEventDel actionMaterialEventDel = null;
    protected ActionMaterialEventWorkFlowG actionMaterialEventWorkFlowG = null;
    protected ActionMaterialEventResult actionMaterialEventResult = null;
    protected ActionMaterialEventAudit actionMaterialEventAudit = null;
    protected ActionMaterialEventUnAudit actionMaterialEventUnAudit = null;
    protected ActionAddLogEvent actionAddLogEvent = null;
    protected ActionDelLogEvent actionDelLogEvent = null;
    protected ActionAddTaskLog actionAddTaskLog = null;
    protected ActionDelTaskLog actionDelTaskLog = null;
    protected ActionIsMileStone actionIsMileStone = null;
    /**
     * output class constructor
     */
    public AbstractFDCScheduleTaskPropertiesUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCScheduleTaskPropertiesUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionOK
        this.actionOK = new ActionOK(this);
        getActionManager().registerAction("actionOK", actionOK);
         this.actionOK.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteLine
        this.actionDeleteLine = new ActionDeleteLine(this);
        getActionManager().registerAction("actionDeleteLine", actionDeleteLine);
         this.actionDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityAddline
        this.actionQualityAddline = new ActionQualityAddline(this);
        getActionManager().registerAction("actionQualityAddline", actionQualityAddline);
         this.actionQualityAddline.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityDeleteLine
        this.actionQualityDeleteLine = new ActionQualityDeleteLine(this);
        getActionManager().registerAction("actionQualityDeleteLine", actionQualityDeleteLine);
        this.actionQualityDeleteLine.setBindWorkFlow(true);
         this.actionQualityDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeAddline
        this.actionSafeAddline = new ActionSafeAddline(this);
        getActionManager().registerAction("actionSafeAddline", actionSafeAddline);
         this.actionSafeAddline.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeDeleteLine
        this.actionSafeDeleteLine = new ActionSafeDeleteLine(this);
        getActionManager().registerAction("actionSafeDeleteLine", actionSafeDeleteLine);
        this.actionSafeDeleteLine.setBindWorkFlow(true);
         this.actionSafeDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTaskPre
        this.actionTaskPre = new ActionTaskPre(this);
        getActionManager().registerAction("actionTaskPre", actionTaskPre);
         this.actionTaskPre.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTaskNext
        this.actionTaskNext = new ActionTaskNext(this);
        getActionManager().registerAction("actionTaskNext", actionTaskNext);
         this.actionTaskNext.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAuditResult
        this.actionAuditResult = new ActionAuditResult(this);
        getActionManager().registerAction("actionAuditResult", actionAuditResult);
         this.actionAuditResult.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityEditLine
        this.actionQualityEditLine = new ActionQualityEditLine(this);
        getActionManager().registerAction("actionQualityEditLine", actionQualityEditLine);
         this.actionQualityEditLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //newRowAction
        this.newRowAction = new NewRowAction(this);
        getActionManager().registerAction("newRowAction", newRowAction);
         this.newRowAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //deleteRowAction
        this.deleteRowAction = new DeleteRowAction(this);
        getActionManager().registerAction("deleteRowAction", deleteRowAction);
         this.deleteRowAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //modifyAction
        this.modifyAction = new ModifyAction(this);
        getActionManager().registerAction("modifyAction", modifyAction);
         this.modifyAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeEditline
        this.actionSafeEditline = new ActionSafeEditline(this);
        getActionManager().registerAction("actionSafeEditline", actionSafeEditline);
         this.actionSafeEditline.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeAudit
        this.actionSafeAudit = new ActionSafeAudit(this);
        getActionManager().registerAction("actionSafeAudit", actionSafeAudit);
         this.actionSafeAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeUnAudit
        this.actionSafeUnAudit = new ActionSafeUnAudit(this);
        getActionManager().registerAction("actionSafeUnAudit", actionSafeUnAudit);
         this.actionSafeUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeWorkFlowGraphics
        this.actionSafeWorkFlowGraphics = new ActionSafeWorkFlowGraphics(this);
        getActionManager().registerAction("actionSafeWorkFlowGraphics", actionSafeWorkFlowGraphics);
         this.actionSafeWorkFlowGraphics.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafeWorkFlowLog
        this.actionSafeWorkFlowLog = new ActionSafeWorkFlowLog(this);
        getActionManager().registerAction("actionSafeWorkFlowLog", actionSafeWorkFlowLog);
         this.actionSafeWorkFlowLog.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityWorkFlowAudit
        this.actionQualityWorkFlowAudit = new ActionQualityWorkFlowAudit(this);
        getActionManager().registerAction("actionQualityWorkFlowAudit", actionQualityWorkFlowAudit);
         this.actionQualityWorkFlowAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityWorkFlowUnAudit
        this.actionQualityWorkFlowUnAudit = new ActionQualityWorkFlowUnAudit(this);
        getActionManager().registerAction("actionQualityWorkFlowUnAudit", actionQualityWorkFlowUnAudit);
        this.actionQualityWorkFlowUnAudit.setBindWorkFlow(true);
         this.actionQualityWorkFlowUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityWorkFlowGraphics
        this.actionQualityWorkFlowGraphics = new ActionQualityWorkFlowGraphics(this);
        getActionManager().registerAction("actionQualityWorkFlowGraphics", actionQualityWorkFlowGraphics);
         this.actionQualityWorkFlowGraphics.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityWorkFlowLog
        this.actionQualityWorkFlowLog = new ActionQualityWorkFlowLog(this);
        getActionManager().registerAction("actionQualityWorkFlowLog", actionQualityWorkFlowLog);
         this.actionQualityWorkFlowLog.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        this.actionAttachment = new ActionAttachment(this);
        getActionManager().registerAction("actionAttachment", actionAttachment);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityPlanAdd
        this.actionQualityPlanAdd = new ActionQualityPlanAdd(this);
        getActionManager().registerAction("actionQualityPlanAdd", actionQualityPlanAdd);
         this.actionQualityPlanAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityPlanEdit
        this.actionQualityPlanEdit = new ActionQualityPlanEdit(this);
        getActionManager().registerAction("actionQualityPlanEdit", actionQualityPlanEdit);
         this.actionQualityPlanEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityPlanDel
        this.actionQualityPlanDel = new ActionQualityPlanDel(this);
        getActionManager().registerAction("actionQualityPlanDel", actionQualityPlanDel);
         this.actionQualityPlanDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafePlanAdd
        this.actionSafePlanAdd = new ActionSafePlanAdd(this);
        getActionManager().registerAction("actionSafePlanAdd", actionSafePlanAdd);
         this.actionSafePlanAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafePlanEdit
        this.actionSafePlanEdit = new ActionSafePlanEdit(this);
        getActionManager().registerAction("actionSafePlanEdit", actionSafePlanEdit);
         this.actionSafePlanEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSafePlanDel
        this.actionSafePlanDel = new ActionSafePlanDel(this);
        getActionManager().registerAction("actionSafePlanDel", actionSafePlanDel);
         this.actionSafePlanDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialPlanAdd
        this.actionMaterialPlanAdd = new ActionMaterialPlanAdd(this);
        getActionManager().registerAction("actionMaterialPlanAdd", actionMaterialPlanAdd);
         this.actionMaterialPlanAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialPlanEdit
        this.actionMaterialPlanEdit = new ActionMaterialPlanEdit(this);
        getActionManager().registerAction("actionMaterialPlanEdit", actionMaterialPlanEdit);
         this.actionMaterialPlanEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialPlanDel
        this.actionMaterialPlanDel = new ActionMaterialPlanDel(this);
        getActionManager().registerAction("actionMaterialPlanDel", actionMaterialPlanDel);
         this.actionMaterialPlanDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventAdd
        this.actionMaterialEventAdd = new ActionMaterialEventAdd(this);
        getActionManager().registerAction("actionMaterialEventAdd", actionMaterialEventAdd);
         this.actionMaterialEventAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventEdit
        this.actionMaterialEventEdit = new ActionMaterialEventEdit(this);
        getActionManager().registerAction("actionMaterialEventEdit", actionMaterialEventEdit);
         this.actionMaterialEventEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventDel
        this.actionMaterialEventDel = new ActionMaterialEventDel(this);
        getActionManager().registerAction("actionMaterialEventDel", actionMaterialEventDel);
         this.actionMaterialEventDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventWorkFlowG
        this.actionMaterialEventWorkFlowG = new ActionMaterialEventWorkFlowG(this);
        getActionManager().registerAction("actionMaterialEventWorkFlowG", actionMaterialEventWorkFlowG);
         this.actionMaterialEventWorkFlowG.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventResult
        this.actionMaterialEventResult = new ActionMaterialEventResult(this);
        getActionManager().registerAction("actionMaterialEventResult", actionMaterialEventResult);
         this.actionMaterialEventResult.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventAudit
        this.actionMaterialEventAudit = new ActionMaterialEventAudit(this);
        getActionManager().registerAction("actionMaterialEventAudit", actionMaterialEventAudit);
         this.actionMaterialEventAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialEventUnAudit
        this.actionMaterialEventUnAudit = new ActionMaterialEventUnAudit(this);
        getActionManager().registerAction("actionMaterialEventUnAudit", actionMaterialEventUnAudit);
         this.actionMaterialEventUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddLogEvent
        this.actionAddLogEvent = new ActionAddLogEvent(this);
        getActionManager().registerAction("actionAddLogEvent", actionAddLogEvent);
         this.actionAddLogEvent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelLogEvent
        this.actionDelLogEvent = new ActionDelLogEvent(this);
        getActionManager().registerAction("actionDelLogEvent", actionDelLogEvent);
         this.actionDelLogEvent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTaskLog
        this.actionAddTaskLog = new ActionAddTaskLog(this);
        getActionManager().registerAction("actionAddTaskLog", actionAddTaskLog);
         this.actionAddTaskLog.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelTaskLog
        this.actionDelTaskLog = new ActionDelTaskLog(this);
        getActionManager().registerAction("actionDelTaskLog", actionDelTaskLog);
         this.actionDelTaskLog.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIsMileStone
        this.actionIsMileStone = new ActionIsMileStone(this);
        getActionManager().registerAction("actionIsMileStone", actionIsMileStone);
         this.actionIsMileStone.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.mainPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.predecessorsPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.workloadPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.costInfoPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.materialPlanPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.qualityPlanPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.workResultPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.taskLogPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTabbedPane3 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.mainPnlSchedule = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.mainPnlExecute = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNatureTimes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkKey = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelPart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExecor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWBSNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEffectTimes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdministrator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriority = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contShape = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNatureTimes = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtNoter = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Part = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtManager = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtExecor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtWBSNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEffectTimes = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtAdministrator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboxPriority = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboxShape = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtRistResPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkIsMileStone = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conMileStoneStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboxMileStoneStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbBizType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProcess = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.pkActualDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkActualStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contPredecessorPanel = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.predecessorsTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conPlanWorkload = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conWorkload = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnWorkLoadView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.conCompletePercent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPlanWorkload = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtMeasureUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblWorkload = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtCompletePercent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.conAimCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conDeviation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conActCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCostMemo = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtAimCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDeviation = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tableSafe = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSafeAddline = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSafeDeleteline = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSafeEditLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAuditResult = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWorkFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tableSafePlan = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSafePlanAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSafePlanEdit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSafePlanDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.preventionPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.qualityCheckPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.qualityFaultPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.bigEventPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conPrevention = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblPrevention = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQCItem = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblQCPosition = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQtyRelt = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conBigEvent = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQualityBigEvent = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conWorkResult = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblWorkResult = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.labTaskLogList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labTaskLogDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblTaskLog = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnAddTaskLog = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelTaskLog = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditTaskLog = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtTaskLogDetail = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnTaskPre = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTaskNext = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAttacthment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.mainPanel.setName("mainPanel");
        this.predecessorsPanel.setName("predecessorsPanel");
        this.workloadPanel.setName("workloadPanel");
        this.costInfoPanel.setName("costInfoPanel");
        this.materialPlanPanel.setName("materialPlanPanel");
        this.qualityPlanPanel.setName("qualityPlanPanel");
        this.workResultPanel.setName("workResultPanel");
        this.taskLogPanel.setName("taskLogPanel");
        this.kDTabbedPane3.setName("kDTabbedPane3");
        this.mainPnlSchedule.setName("mainPnlSchedule");
        this.mainPnlExecute.setName("mainPnlExecute");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contNatureTimes.setName("contNatureTimes");
        this.chkKey.setName("chkKey");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.txtColor.setName("txtColor");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.labelPart.setName("labelPart");
        this.labelAdminPerson.setName("labelAdminPerson");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.contExecor.setName("contExecor");
        this.contWBSNumber.setName("contWBSNumber");
        this.contEffectTimes.setName("contEffectTimes");
        this.contAdministrator.setName("contAdministrator");
        this.contPriority.setName("contPriority");
        this.contShape.setName("contShape");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDPanel1.setName("kDPanel1");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.txtName.setName("txtName");
        this.txtNatureTimes.setName("txtNatureTimes");
        this.txtStartDate.setName("txtStartDate");
        this.txtEndDate.setName("txtEndDate");
        this.prmtNoter.setName("prmtNoter");
        this.f7Part.setName("f7Part");
        this.prmtManager.setName("prmtManager");
        this.txtDescription.setName("txtDescription");
        this.prmtExecor.setName("prmtExecor");
        this.txtWBSNumber.setName("txtWBSNumber");
        this.txtEffectTimes.setName("txtEffectTimes");
        this.prmtAdministrator.setName("prmtAdministrator");
        this.comboxPriority.setName("comboxPriority");
        this.comboxShape.setName("comboxShape");
        this.prmtRistResPerson.setName("prmtRistResPerson");
        this.chkIsMileStone.setName("chkIsMileStone");
        this.conMileStoneStatus.setName("conMileStoneStatus");
        this.comboxMileStoneStatus.setName("comboxMileStoneStatus");
        this.cbBizType.setName("cbBizType");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contActualDate.setName("contActualDate");
        this.contActualStartDate.setName("contActualStartDate");
        this.txtProcess.setName("txtProcess");
        this.pkActualDate.setName("pkActualDate");
        this.pkActualStartDate.setName("pkActualStartDate");
        this.contPredecessorPanel.setName("contPredecessorPanel");
        this.predecessorsTable.setName("predecessorsTable");
        this.conPlanWorkload.setName("conPlanWorkload");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.conWorkload.setName("conWorkload");
        this.btnWorkLoadView.setName("btnWorkLoadView");
        this.conCompletePercent.setName("conCompletePercent");
        this.txtPlanWorkload.setName("txtPlanWorkload");
        this.prmtMeasureUnit.setName("prmtMeasureUnit");
        this.tblWorkload.setName("tblWorkload");
        this.txtCompletePercent.setName("txtCompletePercent");
        this.kDLabel1.setName("kDLabel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.conAimCost.setName("conAimCost");
        this.conDeviation.setName("conDeviation");
        this.conActCost.setName("conActCost");
        this.txtCostMemo.setName("txtCostMemo");
        this.txtAimCost.setName("txtAimCost");
        this.txtDeviation.setName("txtDeviation");
        this.txtActCost.setName("txtActCost");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDSeparator2.setName("kDSeparator2");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.tableSafe.setName("tableSafe");
        this.btnSafeAddline.setName("btnSafeAddline");
        this.btnSafeDeleteline.setName("btnSafeDeleteline");
        this.btnSafeEditLine.setName("btnSafeEditLine");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnAudit.setName("btnAudit");
        this.btnAuditResult.setName("btnAuditResult");
        this.btnWorkFlow.setName("btnWorkFlow");
        this.tableSafePlan.setName("tableSafePlan");
        this.btnSafePlanAdd.setName("btnSafePlanAdd");
        this.btnSafePlanEdit.setName("btnSafePlanEdit");
        this.btnSafePlanDel.setName("btnSafePlanDel");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.preventionPanel.setName("preventionPanel");
        this.qualityCheckPanel.setName("qualityCheckPanel");
        this.qualityFaultPanel.setName("qualityFaultPanel");
        this.bigEventPanel.setName("bigEventPanel");
        this.conPrevention.setName("conPrevention");
        this.tblPrevention.setName("tblPrevention");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.tblQCItem.setName("tblQCItem");
        this.tblQCPosition.setName("tblQCPosition");
        this.kDContainer1.setName("kDContainer1");
        this.tblQtyRelt.setName("tblQtyRelt");
        this.conBigEvent.setName("conBigEvent");
        this.tblQualityBigEvent.setName("tblQualityBigEvent");
        this.conWorkResult.setName("conWorkResult");
        this.tblWorkResult.setName("tblWorkResult");
        this.labTaskLogList.setName("labTaskLogList");
        this.labTaskLogDescription.setName("labTaskLogDescription");
        this.tblTaskLog.setName("tblTaskLog");
        this.kDSeparator3.setName("kDSeparator3");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.btnAddTaskLog.setName("btnAddTaskLog");
        this.btnDelTaskLog.setName("btnDelTaskLog");
        this.btnEditTaskLog.setName("btnEditTaskLog");
        this.txtTaskLogDetail.setName("txtTaskLogDetail");
        this.btnOK.setName("btnOK");
        this.separator1.setName("separator1");
        this.btnTaskPre.setName("btnTaskPre");
        this.btnTaskNext.setName("btnTaskNext");
        this.btnAttacthment.setName("btnAttacthment");
        // CoreUI
        // kDTabbedPane1
        // mainPanel		
        this.mainPanel.setBorder(null);
        // predecessorsPanel
        // workloadPanel		
        this.workloadPanel.setToolTipText(resHelper.getString("workloadPanel.toolTipText"));
        // costInfoPanel		
        this.costInfoPanel.setToolTipText(resHelper.getString("costInfoPanel.toolTipText"));
        // materialPlanPanel		
        this.materialPlanPanel.setBorder(null);		
        this.materialPlanPanel.setToolTipText(resHelper.getString("materialPlanPanel.toolTipText"));		
        this.materialPlanPanel.setVisible(false);
        // qualityPlanPanel		
        this.qualityPlanPanel.setToolTipText(resHelper.getString("qualityPlanPanel.toolTipText"));
        // workResultPanel		
        this.workResultPanel.setToolTipText(resHelper.getString("workResultPanel.toolTipText"));
        // taskLogPanel
        // kDTabbedPane3
        // mainPnlSchedule		
        this.mainPnlSchedule.setBorder(null);
        // mainPnlExecute		
        this.mainPnlExecute.setBorder(null);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contNatureTimes		
        this.contNatureTimes.setBoundLabelText(resHelper.getString("contNatureTimes.boundLabelText"));		
        this.contNatureTimes.setBoundLabelLength(100);		
        this.contNatureTimes.setBoundLabelUnderline(true);
        // chkKey		
        this.chkKey.setText(resHelper.getString("chkKey.text"));
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // txtColor
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // labelPart		
        this.labelPart.setBoundLabelText(resHelper.getString("labelPart.boundLabelText"));		
        this.labelPart.setBoundLabelLength(100);		
        this.labelPart.setBoundLabelUnderline(true);
        // labelAdminPerson		
        this.labelAdminPerson.setBoundLabelText(resHelper.getString("labelAdminPerson.boundLabelText"));		
        this.labelAdminPerson.setBoundLabelLength(100);		
        this.labelAdminPerson.setBoundLabelUnderline(true);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);
        // contExecor		
        this.contExecor.setBoundLabelText(resHelper.getString("contExecor.boundLabelText"));		
        this.contExecor.setBoundLabelLength(100);		
        this.contExecor.setBoundLabelUnderline(true);
        // contWBSNumber		
        this.contWBSNumber.setBoundLabelText(resHelper.getString("contWBSNumber.boundLabelText"));		
        this.contWBSNumber.setBoundLabelLength(100);		
        this.contWBSNumber.setBoundLabelUnderline(true);
        // contEffectTimes		
        this.contEffectTimes.setBoundLabelText(resHelper.getString("contEffectTimes.boundLabelText"));		
        this.contEffectTimes.setBoundLabelLength(100);		
        this.contEffectTimes.setBoundLabelUnderline(true);
        // contAdministrator		
        this.contAdministrator.setBoundLabelText(resHelper.getString("contAdministrator.boundLabelText"));		
        this.contAdministrator.setBoundLabelLength(100);		
        this.contAdministrator.setBoundLabelUnderline(true);
        // contPriority		
        this.contPriority.setBoundLabelLength(100);		
        this.contPriority.setBoundLabelText(resHelper.getString("contPriority.boundLabelText"));		
        this.contPriority.setBoundLabelUnderline(true);
        // contShape		
        this.contShape.setBoundLabelText(resHelper.getString("contShape.boundLabelText"));		
        this.contShape.setBoundLabelLength(100);		
        this.contShape.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // txtNatureTimes		
        this.txtNatureTimes.setEnabled(false);
        // txtStartDate		
        this.txtStartDate.setRequired(true);
        // txtEndDate		
        this.txtEndDate.setRequired(true);
        // prmtNoter		
        this.prmtNoter.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
        // f7Part		
        this.f7Part.setCommitFormat("$number$");		
        this.f7Part.setDisplayFormat("$name$");		
        this.f7Part.setEditFormat("$number$");		
        this.f7Part.setRequired(true);
        // prmtManager		
        this.prmtManager.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
        // txtDescription		
        this.txtDescription.setMaxLength(200);
        // prmtExecor		
        this.prmtExecor.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtExecor.setDisplayFormat("$name$");		
        this.prmtExecor.setEditFormat("$number$");		
        this.prmtExecor.setCommitFormat("$number$");		
        this.prmtExecor.setRequired(true);
        // txtWBSNumber		
        this.txtWBSNumber.setEnabled(false);
        // txtEffectTimes		
        this.txtEffectTimes.setDataType(1);		
        this.txtEffectTimes.setPrecision(0);		
        this.txtEffectTimes.setRequired(true);		
        this.txtEffectTimes.setEnabled(false);
        // prmtAdministrator		
        this.prmtAdministrator.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtAdministrator.setDisplayFormat("$name$");		
        this.prmtAdministrator.setEditFormat("$number$");		
        this.prmtAdministrator.setCommitFormat("$number$");
        // comboxPriority
        // comboxShape		
        this.comboxShape.setVerifyInputWhenFocusTarget(false);
        // prmtRistResPerson		
        this.prmtRistResPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
        // chkIsMileStone
        this.chkIsMileStone.setAction((IItemAction)ActionProxyFactory.getProxy(actionIsMileStone, new Class[] { IItemAction.class }, getServiceContext()));		
        this.chkIsMileStone.setText(resHelper.getString("chkIsMileStone.text"));
        // conMileStoneStatus		
        this.conMileStoneStatus.setBoundLabelText(resHelper.getString("conMileStoneStatus.boundLabelText"));		
        this.conMileStoneStatus.setBoundLabelLength(120);		
        this.conMileStoneStatus.setBoundLabelUnderline(true);
        // comboxMileStoneStatus
        // cbBizType
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // contActualDate		
        this.contActualDate.setBoundLabelText(resHelper.getString("contActualDate.boundLabelText"));		
        this.contActualDate.setBoundLabelLength(100);		
        this.contActualDate.setBoundLabelUnderline(true);
        // contActualStartDate		
        this.contActualStartDate.setBoundLabelText(resHelper.getString("contActualStartDate.boundLabelText"));		
        this.contActualStartDate.setBoundLabelLength(100);		
        this.contActualStartDate.setBoundLabelUnderline(true);
        // txtProcess		
        this.txtProcess.setDataType(6);
        // pkActualDate
        // pkActualStartDate
        // contPredecessorPanel
        // predecessorsTable
		String predecessorsTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"linkType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"linkTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{linkType}</t:Cell><t:Cell>$Resource{linkTimes}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.predecessorsTable.setFormatXml(resHelper.translateString("predecessorsTable",predecessorsTableStrXML));

        

        // conPlanWorkload		
        this.conPlanWorkload.setBoundLabelText(resHelper.getString("conPlanWorkload.boundLabelText"));		
        this.conPlanWorkload.setBoundLabelLength(110);		
        this.conPlanWorkload.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(70);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setVisible(false);
        // conWorkload		
        this.conWorkload.setTitle(resHelper.getString("conWorkload.title"));		
        this.conWorkload.setEnableActive(false);
        // btnWorkLoadView		
        this.btnWorkLoadView.setText(resHelper.getString("btnWorkLoadView.text"));
        // conCompletePercent		
        this.conCompletePercent.setBoundLabelText(resHelper.getString("conCompletePercent.boundLabelText"));		
        this.conCompletePercent.setBoundLabelLength(110);		
        this.conCompletePercent.setBoundLabelUnderline(true);
        // txtPlanWorkload		
        this.txtPlanWorkload.setDataType(1);
        // prmtMeasureUnit
        // tblWorkload
		String tblWorkloadStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"completeAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"completePercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"completeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"creatorMemo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{completeAmt}</t:Cell><t:Cell>$Resource{completePercent}</t:Cell><t:Cell>$Resource{completeDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{creatorMemo}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblWorkload.setFormatXml(resHelper.translateString("tblWorkload",tblWorkloadStrXML));

        

        // txtCompletePercent		
        this.txtCompletePercent.setDataType(1);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDScrollPane1
        // conAimCost		
        this.conAimCost.setBoundLabelText(resHelper.getString("conAimCost.boundLabelText"));		
        this.conAimCost.setBoundLabelLength(100);		
        this.conAimCost.setBoundLabelUnderline(true);
        // conDeviation		
        this.conDeviation.setBoundLabelText(resHelper.getString("conDeviation.boundLabelText"));		
        this.conDeviation.setBoundLabelLength(120);		
        this.conDeviation.setBoundLabelUnderline(true);
        // conActCost		
        this.conActCost.setBoundLabelText(resHelper.getString("conActCost.boundLabelText"));		
        this.conActCost.setBoundLabelLength(100);		
        this.conActCost.setBoundLabelUnderline(true);
        // txtCostMemo
        // txtAimCost		
        this.txtAimCost.setDataType(1);
        // txtDeviation		
        this.txtDeviation.setDataType(1);
        // txtActCost		
        this.txtActCost.setDataType(1);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDSeparator2		
        this.kDSeparator2.setOrientation(1);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // tableSafe
		String tableSafeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"date\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tableSafe.setFormatXml(resHelper.translateString("tableSafe",tableSafeStrXML));
        this.tableSafe.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tableSafe_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnSafeAddline
        this.btnSafeAddline.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeAddline, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSafeAddline.setText(resHelper.getString("btnSafeAddline.text"));		
        this.btnSafeAddline.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnSafeDeleteline
        this.btnSafeDeleteline.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeDeleteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSafeDeleteline.setText(resHelper.getString("btnSafeDeleteline.text"));		
        this.btnSafeDeleteline.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // btnSafeEditLine
        this.btnSafeEditLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeEditline, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSafeEditLine.setText(resHelper.getString("btnSafeEditLine.text"));		
        this.btnSafeEditLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnAuditResult
        this.btnAuditResult.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeWorkFlowLog, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAuditResult.setText(resHelper.getString("btnAuditResult.text"));		
        this.btnAuditResult.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_multapproveresult"));
        // btnWorkFlow
        this.btnWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafeWorkFlowGraphics, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWorkFlow.setText(resHelper.getString("btnWorkFlow.text"));		
        this.btnWorkFlow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_flowchart"));
        // tableSafePlan
		String tableSafePlanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tableSafePlan.setFormatXml(resHelper.translateString("tableSafePlan",tableSafePlanStrXML));
        this.tableSafePlan.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tableSafePlan_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnSafePlanAdd
        this.btnSafePlanAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafePlanAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSafePlanAdd.setText(resHelper.getString("btnSafePlanAdd.text"));		
        this.btnSafePlanAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnSafePlanEdit
        this.btnSafePlanEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafePlanEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSafePlanEdit.setText(resHelper.getString("btnSafePlanEdit.text"));		
        this.btnSafePlanEdit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnSafePlanDel
        this.btnSafePlanDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionSafePlanDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSafePlanDel.setText(resHelper.getString("btnSafePlanDel.text"));		
        this.btnSafePlanDel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // kDTabbedPane2
        // preventionPanel
        // qualityCheckPanel
        // qualityFaultPanel
        // bigEventPanel
        // conPrevention		
        this.conPrevention.setEnableActive(false);
        // tblPrevention
		String tblPreventionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prevention\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{prevention}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPrevention.setFormatXml(resHelper.translateString("tblPrevention",tblPreventionStrXML));

        

        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setEnableActive(false);
        // kDContainer3		
        this.kDContainer3.setEnableActive(false);		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // tblQCItem
		String tblQCItemStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"chkCriterion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"description\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{chkCriterion}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblQCItem.setFormatXml(resHelper.translateString("tblQCItem",tblQCItemStrXML));

        

        // tblQCPosition
		String tblQCPositionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"position\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"samplePropotion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"checkFrequency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"qtyAssignID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"qtyCheckEntryID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{position}</t:Cell><t:Cell>$Resource{samplePropotion}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{checkFrequency}</t:Cell><t:Cell>$Resource{qtyAssignID}</t:Cell><t:Cell>$Resource{qtyCheckEntryID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblQCPosition.setFormatXml(resHelper.translateString("tblQCPosition",tblQCPositionStrXML));

        

        // kDContainer1		
        this.kDContainer1.setEnableActive(false);
        // tblQtyRelt
		String tblQtyReltStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"checkItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"checkPostion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"checkPropotion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"result\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"memo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{checkItem}</t:Cell><t:Cell>$Resource{checkPostion}</t:Cell><t:Cell>$Resource{checkPropotion}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{result}</t:Cell><t:Cell>$Resource{memo}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblQtyRelt.setFormatXml(resHelper.translateString("tblQtyRelt",tblQtyReltStrXML));

        

        // conBigEvent		
        this.conBigEvent.setEnableActive(false);
        // tblQualityBigEvent
		String tblQualityBigEventStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"happenTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"directPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"indirectPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"suggestion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{happenTime}</t:Cell><t:Cell>$Resource{directPerson}</t:Cell><t:Cell>$Resource{indirectPerson}</t:Cell><t:Cell>$Resource{suggestion}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblQualityBigEvent.setFormatXml(resHelper.translateString("tblQualityBigEvent",tblQualityBigEventStrXML));

        

        // conWorkResult		
        this.conWorkResult.setEnableActive(false);
        // tblWorkResult
		String tblWorkResultStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"attachmentName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"attachmentID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{attachmentName}</t:Cell><t:Cell>$Resource{attachmentID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblWorkResult.setFormatXml(resHelper.translateString("tblWorkResult",tblWorkResultStrXML));

        

        // labTaskLogList		
        this.labTaskLogList.setBoundLabelText(resHelper.getString("labTaskLogList.boundLabelText"));		
        this.labTaskLogList.setBoundLabelLength(100);		
        this.labTaskLogList.setBoundLabelUnderline(true);
        // labTaskLogDescription		
        this.labTaskLogDescription.setBoundLabelText(resHelper.getString("labTaskLogDescription.boundLabelText"));		
        this.labTaskLogDescription.setBoundLabelLength(100);		
        this.labTaskLogDescription.setBoundLabelUnderline(true);
        // tblTaskLog
		String tblTaskLogStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"happenTime\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"title\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{happenTime}</t:Cell><t:Cell>$Resource{title}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTaskLog.setFormatXml(resHelper.translateString("tblTaskLog",tblTaskLogStrXML));
        this.tblTaskLog.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kDTable1_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDSeparator3		
        this.kDSeparator3.setOrientation(1);
        // kDScrollPane2
        // btnAddTaskLog
        this.btnAddTaskLog.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTaskLog, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTaskLog.setText(resHelper.getString("btnAddTaskLog.text"));		
        this.btnAddTaskLog.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnDelTaskLog
        this.btnDelTaskLog.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTaskLog, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelTaskLog.setText(resHelper.getString("btnDelTaskLog.text"));		
        this.btnDelTaskLog.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // btnEditTaskLog		
        this.btnEditTaskLog.setText(resHelper.getString("btnEditTaskLog.text"));		
        this.btnEditTaskLog.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // txtTaskLogDetail
        // btnOK
        this.btnOK.setAction((IItemAction)ActionProxyFactory.getProxy(actionOK, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOK.setText(resHelper.getString("btnOK.text"));		
        this.btnOK.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));		
        this.btnOK.setToolTipText(resHelper.getString("btnOK.toolTipText"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // separator1
        // btnTaskPre
        this.btnTaskPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionTaskPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaskPre.setText(resHelper.getString("btnTaskPre.text"));		
        this.btnTaskPre.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_previous"));		
        this.btnTaskPre.setToolTipText(resHelper.getString("btnTaskPre.toolTipText"));
        // btnTaskNext
        this.btnTaskNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionTaskNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaskNext.setText(resHelper.getString("btnTaskNext.text"));		
        this.btnTaskNext.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_next"));		
        this.btnTaskNext.setToolTipText(resHelper.getString("btnTaskNext.toolTipText"));
        // btnAttacthment
        this.btnAttacthment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttacthment.setText(resHelper.getString("btnAttacthment.text"));
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
        this.setBounds(new Rectangle(10, 10, 700, 450));
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(4, 5, 692, 440));
        this.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(mainPanel, resHelper.getString("mainPanel.constraints"));
        kDTabbedPane1.add(predecessorsPanel, resHelper.getString("predecessorsPanel.constraints"));
        kDTabbedPane1.add(workloadPanel, resHelper.getString("workloadPanel.constraints"));
        kDTabbedPane1.add(costInfoPanel, resHelper.getString("costInfoPanel.constraints"));
        kDTabbedPane1.add(materialPlanPanel, resHelper.getString("materialPlanPanel.constraints"));
        kDTabbedPane1.add(qualityPlanPanel, resHelper.getString("qualityPlanPanel.constraints"));
        kDTabbedPane1.add(workResultPanel, resHelper.getString("workResultPanel.constraints"));
        kDTabbedPane1.add(taskLogPanel, resHelper.getString("taskLogPanel.constraints"));
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(kDTabbedPane3, BorderLayout.CENTER);
        //kDTabbedPane3
        kDTabbedPane3.add(mainPnlSchedule, resHelper.getString("mainPnlSchedule.constraints"));
        kDTabbedPane3.add(mainPnlExecute, resHelper.getString("mainPnlExecute.constraints"));
        //mainPnlSchedule
        mainPnlSchedule.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(392, 10, 270, 19));
        mainPnlSchedule.add(kDLabelContainer1, null);
        contNatureTimes.setBounds(new Rectangle(392, 64, 270, 19));
        mainPnlSchedule.add(contNatureTimes, null);
        chkKey.setBounds(new Rectangle(392, 172, 146, 21));
        mainPnlSchedule.add(chkKey, null);
        kDLabelContainer4.setBounds(new Rectangle(10, 37, 270, 19));
        mainPnlSchedule.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(10, 64, 270, 19));
        mainPnlSchedule.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(392, 145, 106, 19));
        mainPnlSchedule.add(kDLabelContainer6, null);
        txtColor.setBounds(new Rectangle(492, 145, 170, 19));
        mainPnlSchedule.add(txtColor, null);
        kDLabelContainer7.setBounds(new Rectangle(10, 199, 270, 19));
        mainPnlSchedule.add(kDLabelContainer7, null);
        labelPart.setBounds(new Rectangle(10, 91, 270, 19));
        mainPnlSchedule.add(labelPart, null);
        labelAdminPerson.setBounds(new Rectangle(10, 118, 270, 19));
        mainPnlSchedule.add(labelAdminPerson, null);
        kDLabelContainer20.setBounds(new Rectangle(10, 280, 652, 80));
        mainPnlSchedule.add(kDLabelContainer20, null);
        contExecor.setBounds(new Rectangle(10, 172, 270, 19));
        mainPnlSchedule.add(contExecor, null);
        contWBSNumber.setBounds(new Rectangle(10, 10, 270, 19));
        mainPnlSchedule.add(contWBSNumber, null);
        contEffectTimes.setBounds(new Rectangle(392, 37, 270, 19));
        mainPnlSchedule.add(contEffectTimes, null);
        contAdministrator.setBounds(new Rectangle(10, 145, 270, 19));
        mainPnlSchedule.add(contAdministrator, null);
        contPriority.setBounds(new Rectangle(392, 91, 270, 19));
        mainPnlSchedule.add(contPriority, null);
        contShape.setBounds(new Rectangle(392, 118, 270, 19));
        mainPnlSchedule.add(contShape, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 226, 270, 19));
        mainPnlSchedule.add(kDLabelContainer2, null);
        kDPanel1.setBounds(new Rectangle(380, 190, 300, 80));
        mainPnlSchedule.add(kDPanel1, null);
        kDLabelContainer8.setBounds(new Rectangle(10, 253, 270, 19));
        mainPnlSchedule.add(kDLabelContainer8, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //contNatureTimes
        contNatureTimes.setBoundEditor(txtNatureTimes);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtStartDate);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtEndDate);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(prmtNoter);
        //labelPart
        labelPart.setBoundEditor(f7Part);
        //labelAdminPerson
        labelAdminPerson.setBoundEditor(prmtManager);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtDescription);
        //contExecor
        contExecor.setBoundEditor(prmtExecor);
        //contWBSNumber
        contWBSNumber.setBoundEditor(txtWBSNumber);
        //contEffectTimes
        contEffectTimes.setBoundEditor(txtEffectTimes);
        //contAdministrator
        contAdministrator.setBoundEditor(prmtAdministrator);
        //contPriority
        contPriority.setBoundEditor(comboxPriority);
        //contShape
        contShape.setBoundEditor(comboxShape);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtRistResPerson);
        //kDPanel1
        kDPanel1.setLayout(null);        chkIsMileStone.setBounds(new Rectangle(12, 10, 200, 19));
        kDPanel1.add(chkIsMileStone, null);
        conMileStoneStatus.setBounds(new Rectangle(12, 35, 270, 19));
        kDPanel1.add(conMileStoneStatus, null);
        //conMileStoneStatus
        conMileStoneStatus.setBoundEditor(comboxMileStoneStatus);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(cbBizType);
        //mainPnlExecute
        mainPnlExecute.setLayout(null);        kDLabelContainer3.setBounds(new Rectangle(10, 37, 270, 19));
        mainPnlExecute.add(kDLabelContainer3, null);
        contActualDate.setBounds(new Rectangle(405, 10, 270, 19));
        mainPnlExecute.add(contActualDate, null);
        contActualStartDate.setBounds(new Rectangle(10, 10, 270, 19));
        mainPnlExecute.add(contActualStartDate, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtProcess);
        //contActualDate
        contActualDate.setBoundEditor(pkActualDate);
        //contActualStartDate
        contActualStartDate.setBoundEditor(pkActualStartDate);
        //predecessorsPanel
        predecessorsPanel.setLayout(null);        contPredecessorPanel.setBounds(new Rectangle(10, 4, 673, 401));
        predecessorsPanel.add(contPredecessorPanel, null);
        //contPredecessorPanel
contPredecessorPanel.getContentPane().setLayout(new BorderLayout(0, 0));        contPredecessorPanel.getContentPane().add(predecessorsTable, BorderLayout.CENTER);
        //workloadPanel
        workloadPanel.setLayout(null);        conPlanWorkload.setBounds(new Rectangle(10, 10, 270, 19));
        workloadPanel.add(conPlanWorkload, null);
        kDLabelContainer9.setBounds(new Rectangle(480, 10, 200, 19));
        workloadPanel.add(kDLabelContainer9, null);
        conWorkload.setBounds(new Rectangle(10, 60, 670, 340));
        workloadPanel.add(conWorkload, null);
        btnWorkLoadView.setBounds(new Rectangle(560, 35, 120, 19));
        workloadPanel.add(btnWorkLoadView, null);
        conCompletePercent.setBounds(new Rectangle(10, 35, 270, 19));
        workloadPanel.add(conCompletePercent, null);
        //conPlanWorkload
        conPlanWorkload.setBoundEditor(txtPlanWorkload);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(prmtMeasureUnit);
        //conWorkload
conWorkload.getContentPane().setLayout(new BorderLayout(0, 0));        conWorkload.getContentPane().add(tblWorkload, BorderLayout.CENTER);
        //conCompletePercent
        conCompletePercent.setBoundEditor(txtCompletePercent);
        //costInfoPanel
        costInfoPanel.setLayout(null);        kDLabel1.setBounds(new Rectangle(10, 80, 100, 19));
        costInfoPanel.add(kDLabel1, null);
        kDScrollPane1.setBounds(new Rectangle(10, 100, 660, 150));
        costInfoPanel.add(kDScrollPane1, null);
        conAimCost.setBounds(new Rectangle(10, 10, 270, 19));
        costInfoPanel.add(conAimCost, null);
        conDeviation.setBounds(new Rectangle(320, 10, 270, 19));
        costInfoPanel.add(conDeviation, null);
        conActCost.setBounds(new Rectangle(10, 40, 270, 19));
        costInfoPanel.add(conActCost, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtCostMemo, null);
        //conAimCost
        conAimCost.setBoundEditor(txtAimCost);
        //conDeviation
        conDeviation.setBoundEditor(txtDeviation);
        //conActCost
        conActCost.setBoundEditor(txtActCost);
        //materialPlanPanel
        materialPlanPanel.setLayout(null);        kDLabelContainer10.setBounds(new Rectangle(10, 5, 60, 19));
        materialPlanPanel.add(kDLabelContainer10, null);
        kDSeparator2.setBounds(new Rectangle(342, 30, 8, 371));
        materialPlanPanel.add(kDSeparator2, null);
        kDLabelContainer11.setBounds(new Rectangle(364, 4, 60, 19));
        materialPlanPanel.add(kDLabelContainer11, null);
        tableSafe.setBounds(new Rectangle(359, 53, 325, 347));
        materialPlanPanel.add(tableSafe, null);
        btnSafeAddline.setBounds(new Rectangle(441, 4, 80, 20));
        materialPlanPanel.add(btnSafeAddline, null);
        btnSafeDeleteline.setBounds(new Rectangle(605, 4, 80, 20));
        materialPlanPanel.add(btnSafeDeleteline, null);
        btnSafeEditLine.setBounds(new Rectangle(523, 4, 80, 20));
        materialPlanPanel.add(btnSafeEditLine, null);
        btnUnAudit.setBounds(new Rectangle(605, 28, 80, 20));
        materialPlanPanel.add(btnUnAudit, null);
        btnAudit.setBounds(new Rectangle(523, 28, 80, 20));
        materialPlanPanel.add(btnAudit, null);
        btnAuditResult.setBounds(new Rectangle(359, 28, 80, 20));
        materialPlanPanel.add(btnAuditResult, null);
        btnWorkFlow.setBounds(new Rectangle(441, 28, 80, 20));
        materialPlanPanel.add(btnWorkFlow, null);
        tableSafePlan.setBounds(new Rectangle(9, 31, 317, 366));
        materialPlanPanel.add(tableSafePlan, null);
        btnSafePlanAdd.setBounds(new Rectangle(76, 4, 80, 20));
        materialPlanPanel.add(btnSafePlanAdd, null);
        btnSafePlanEdit.setBounds(new Rectangle(160, 4, 80, 20));
        materialPlanPanel.add(btnSafePlanEdit, null);
        btnSafePlanDel.setBounds(new Rectangle(244, 4, 80, 20));
        materialPlanPanel.add(btnSafePlanDel, null);
        //qualityPlanPanel
qualityPlanPanel.setLayout(new BorderLayout(0, 0));        qualityPlanPanel.add(kDTabbedPane2, BorderLayout.CENTER);
        //kDTabbedPane2
        kDTabbedPane2.add(preventionPanel, resHelper.getString("preventionPanel.constraints"));
        kDTabbedPane2.add(qualityCheckPanel, resHelper.getString("qualityCheckPanel.constraints"));
        kDTabbedPane2.add(qualityFaultPanel, resHelper.getString("qualityFaultPanel.constraints"));
        kDTabbedPane2.add(bigEventPanel, resHelper.getString("bigEventPanel.constraints"));
        //preventionPanel
        preventionPanel.setLayout(null);        conPrevention.setBounds(new Rectangle(10, 10, 660, 330));
        preventionPanel.add(conPrevention, null);
        //conPrevention
conPrevention.getContentPane().setLayout(new BorderLayout(0, 0));        conPrevention.getContentPane().add(tblPrevention, BorderLayout.CENTER);
        //qualityCheckPanel
        qualityCheckPanel.setLayout(null);        kDContainer2.setBounds(new Rectangle(10, 10, 645, 150));
        qualityCheckPanel.add(kDContainer2, null);
        kDContainer3.setBounds(new Rectangle(10, 170, 645, 170));
        qualityCheckPanel.add(kDContainer3, null);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblQCItem, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(tblQCPosition, BorderLayout.CENTER);
        //qualityFaultPanel
        qualityFaultPanel.setLayout(null);        kDContainer1.setBounds(new Rectangle(10, 10, 645, 350));
        qualityFaultPanel.add(kDContainer1, null);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblQtyRelt, BorderLayout.CENTER);
        //bigEventPanel
        bigEventPanel.setLayout(null);        conBigEvent.setBounds(new Rectangle(10, 10, 660, 325));
        bigEventPanel.add(conBigEvent, null);
        //conBigEvent
conBigEvent.getContentPane().setLayout(new BorderLayout(0, 0));        conBigEvent.getContentPane().add(tblQualityBigEvent, BorderLayout.CENTER);
        //workResultPanel
        workResultPanel.setLayout(null);        conWorkResult.setBounds(new Rectangle(10, 10, 670, 385));
        workResultPanel.add(conWorkResult, null);
        //conWorkResult
conWorkResult.getContentPane().setLayout(new BorderLayout(0, 0));        conWorkResult.getContentPane().add(tblWorkResult, BorderLayout.CENTER);
        //taskLogPanel
        taskLogPanel.setLayout(null);        labTaskLogList.setBounds(new Rectangle(10, 5, 60, 19));
        taskLogPanel.add(labTaskLogList, null);
        labTaskLogDescription.setBounds(new Rectangle(370, 5, 60, 19));
        taskLogPanel.add(labTaskLogDescription, null);
        tblTaskLog.setBounds(new Rectangle(10, 30, 330, 370));
        taskLogPanel.add(tblTaskLog, null);
        kDSeparator3.setBounds(new Rectangle(350, 5, 8, 400));
        taskLogPanel.add(kDSeparator3, null);
        kDScrollPane2.setBounds(new Rectangle(370, 30, 300, 370));
        taskLogPanel.add(kDScrollPane2, null);
        btnAddTaskLog.setBounds(new Rectangle(90, 5, 80, 19));
        taskLogPanel.add(btnAddTaskLog, null);
        btnDelTaskLog.setBounds(new Rectangle(260, 5, 80, 19));
        taskLogPanel.add(btnDelTaskLog, null);
        btnEditTaskLog.setBounds(new Rectangle(175, 5, 80, 19));
        taskLogPanel.add(btnEditTaskLog, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtTaskLogDetail, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnOK);
        this.toolBar.add(separator1);
        this.toolBar.add(btnTaskPre);
        this.toolBar.add(btnTaskNext);
        this.toolBar.add(btnAttacthment);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskPropertiesUIHandler";
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
     * output tableSafe_tableClicked method
     */
    protected void tableSafe_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tableSafePlan_tableClicked method
     */
    protected void tableSafePlan_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_tableSelectChanged method
     */
    protected void kDTable1_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionOK_actionPerformed method
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteLine_actionPerformed method
     */
    public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityAddline_actionPerformed method
     */
    public void actionQualityAddline_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityDeleteLine_actionPerformed method
     */
    public void actionQualityDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeAddline_actionPerformed method
     */
    public void actionSafeAddline_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeDeleteLine_actionPerformed method
     */
    public void actionSafeDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTaskPre_actionPerformed method
     */
    public void actionTaskPre_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTaskNext_actionPerformed method
     */
    public void actionTaskNext_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAuditResult_actionPerformed method
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityEditLine_actionPerformed method
     */
    public void actionQualityEditLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output newRowAction_actionPerformed method
     */
    public void newRowAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output deleteRowAction_actionPerformed method
     */
    public void deleteRowAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output modifyAction_actionPerformed method
     */
    public void modifyAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeEditline_actionPerformed method
     */
    public void actionSafeEditline_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeAudit_actionPerformed method
     */
    public void actionSafeAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeUnAudit_actionPerformed method
     */
    public void actionSafeUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeWorkFlowGraphics_actionPerformed method
     */
    public void actionSafeWorkFlowGraphics_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafeWorkFlowLog_actionPerformed method
     */
    public void actionSafeWorkFlowLog_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityWorkFlowAudit_actionPerformed method
     */
    public void actionQualityWorkFlowAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityWorkFlowUnAudit_actionPerformed method
     */
    public void actionQualityWorkFlowUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityWorkFlowGraphics_actionPerformed method
     */
    public void actionQualityWorkFlowGraphics_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityWorkFlowLog_actionPerformed method
     */
    public void actionQualityWorkFlowLog_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityPlanAdd_actionPerformed method
     */
    public void actionQualityPlanAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityPlanEdit_actionPerformed method
     */
    public void actionQualityPlanEdit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityPlanDel_actionPerformed method
     */
    public void actionQualityPlanDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafePlanAdd_actionPerformed method
     */
    public void actionSafePlanAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafePlanEdit_actionPerformed method
     */
    public void actionSafePlanEdit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSafePlanDel_actionPerformed method
     */
    public void actionSafePlanDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialPlanAdd_actionPerformed method
     */
    public void actionMaterialPlanAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialPlanEdit_actionPerformed method
     */
    public void actionMaterialPlanEdit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialPlanDel_actionPerformed method
     */
    public void actionMaterialPlanDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventAdd_actionPerformed method
     */
    public void actionMaterialEventAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventEdit_actionPerformed method
     */
    public void actionMaterialEventEdit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventDel_actionPerformed method
     */
    public void actionMaterialEventDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventWorkFlowG_actionPerformed method
     */
    public void actionMaterialEventWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventResult_actionPerformed method
     */
    public void actionMaterialEventResult_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventAudit_actionPerformed method
     */
    public void actionMaterialEventAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialEventUnAudit_actionPerformed method
     */
    public void actionMaterialEventUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddLogEvent_actionPerformed method
     */
    public void actionAddLogEvent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelLogEvent_actionPerformed method
     */
    public void actionDelLogEvent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTaskLog_actionPerformed method
     */
    public void actionAddTaskLog_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelTaskLog_actionPerformed method
     */
    public void actionDelTaskLog_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionIsMileStone_actionPerformed method
     */
    public void actionIsMileStone_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionOK(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOK() {
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
	public RequestContext prepareActionDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteLine() {
    	return false;
    }
	public RequestContext prepareActionQualityAddline(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityAddline() {
    	return false;
    }
	public RequestContext prepareActionQualityDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityDeleteLine() {
    	return false;
    }
	public RequestContext prepareActionSafeAddline(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeAddline() {
    	return false;
    }
	public RequestContext prepareActionSafeDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeDeleteLine() {
    	return false;
    }
	public RequestContext prepareActionTaskPre(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTaskPre() {
    	return false;
    }
	public RequestContext prepareActionTaskNext(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTaskNext() {
    	return false;
    }
	public RequestContext prepareActionAuditResult(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAuditResult() {
    	return false;
    }
	public RequestContext prepareActionQualityEditLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityEditLine() {
    	return false;
    }
	public RequestContext prepareNewRowAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareNewRowAction() {
    	return false;
    }
	public RequestContext prepareDeleteRowAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareDeleteRowAction() {
    	return false;
    }
	public RequestContext prepareModifyAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareModifyAction() {
    	return false;
    }
	public RequestContext prepareActionSafeEditline(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeEditline() {
    	return false;
    }
	public RequestContext prepareActionSafeAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeAudit() {
    	return false;
    }
	public RequestContext prepareActionSafeUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeUnAudit() {
    	return false;
    }
	public RequestContext prepareActionSafeWorkFlowGraphics(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeWorkFlowGraphics() {
    	return false;
    }
	public RequestContext prepareActionSafeWorkFlowLog(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafeWorkFlowLog() {
    	return false;
    }
	public RequestContext prepareActionQualityWorkFlowAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityWorkFlowAudit() {
    	return false;
    }
	public RequestContext prepareActionQualityWorkFlowUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityWorkFlowUnAudit() {
    	return false;
    }
	public RequestContext prepareActionQualityWorkFlowGraphics(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityWorkFlowGraphics() {
    	return false;
    }
	public RequestContext prepareActionQualityWorkFlowLog(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityWorkFlowLog() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }
	public RequestContext prepareActionQualityPlanAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityPlanAdd() {
    	return false;
    }
	public RequestContext prepareActionQualityPlanEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityPlanEdit() {
    	return false;
    }
	public RequestContext prepareActionQualityPlanDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityPlanDel() {
    	return false;
    }
	public RequestContext prepareActionSafePlanAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafePlanAdd() {
    	return false;
    }
	public RequestContext prepareActionSafePlanEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafePlanEdit() {
    	return false;
    }
	public RequestContext prepareActionSafePlanDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSafePlanDel() {
    	return false;
    }
	public RequestContext prepareActionMaterialPlanAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialPlanAdd() {
    	return false;
    }
	public RequestContext prepareActionMaterialPlanEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialPlanEdit() {
    	return false;
    }
	public RequestContext prepareActionMaterialPlanDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialPlanDel() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventAdd() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventEdit() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventDel() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventWorkFlowG(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventWorkFlowG() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventResult(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventResult() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventAudit() {
    	return false;
    }
	public RequestContext prepareActionMaterialEventUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialEventUnAudit() {
    	return false;
    }
	public RequestContext prepareActionAddLogEvent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLogEvent() {
    	return false;
    }
	public RequestContext prepareActionDelLogEvent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelLogEvent() {
    	return false;
    }
	public RequestContext prepareActionAddTaskLog(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTaskLog() {
    	return false;
    }
	public RequestContext prepareActionDelTaskLog(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelTaskLog() {
    	return false;
    }
	public RequestContext prepareActionIsMileStone(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIsMileStone() {
    	return false;
    }

    /**
     * output ActionOK class
     */     
    protected class ActionOK extends ItemAction {     
    
        public ActionOK()
        {
            this(null);
        }

        public ActionOK(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOK.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionOK", "actionOK_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteLine class
     */     
    protected class ActionDeleteLine extends ItemAction {     
    
        public ActionDeleteLine()
        {
            this(null);
        }

        public ActionDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionDeleteLine", "actionDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityAddline class
     */     
    protected class ActionQualityAddline extends ItemAction {     
    
        public ActionQualityAddline()
        {
            this(null);
        }

        public ActionQualityAddline(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityAddline.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityAddline.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityAddline.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityAddline", "actionQualityAddline_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityDeleteLine class
     */     
    protected class ActionQualityDeleteLine extends ItemAction {     
    
        public ActionQualityDeleteLine()
        {
            this(null);
        }

        public ActionQualityDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityDeleteLine", "actionQualityDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeAddline class
     */     
    protected class ActionSafeAddline extends ItemAction {     
    
        public ActionSafeAddline()
        {
            this(null);
        }

        public ActionSafeAddline(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeAddline.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeAddline.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeAddline.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeAddline", "actionSafeAddline_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeDeleteLine class
     */     
    protected class ActionSafeDeleteLine extends ItemAction {     
    
        public ActionSafeDeleteLine()
        {
            this(null);
        }

        public ActionSafeDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeDeleteLine", "actionSafeDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionTaskPre class
     */     
    protected class ActionTaskPre extends ItemAction {     
    
        public ActionTaskPre()
        {
            this(null);
        }

        public ActionTaskPre(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTaskPre.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaskPre.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaskPre.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionTaskPre", "actionTaskPre_actionPerformed", e);
        }
    }

    /**
     * output ActionTaskNext class
     */     
    protected class ActionTaskNext extends ItemAction {     
    
        public ActionTaskNext()
        {
            this(null);
        }

        public ActionTaskNext(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTaskNext.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaskNext.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaskNext.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionTaskNext", "actionTaskNext_actionPerformed", e);
        }
    }

    /**
     * output ActionAuditResult class
     */     
    protected class ActionAuditResult extends ItemAction {     
    
        public ActionAuditResult()
        {
            this(null);
        }

        public ActionAuditResult(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAuditResult.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditResult.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditResult.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionAuditResult", "actionAuditResult_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityEditLine class
     */     
    protected class ActionQualityEditLine extends ItemAction {     
    
        public ActionQualityEditLine()
        {
            this(null);
        }

        public ActionQualityEditLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityEditLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityEditLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityEditLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityEditLine", "actionQualityEditLine_actionPerformed", e);
        }
    }

    /**
     * output NewRowAction class
     */     
    protected class NewRowAction extends ItemAction {     
    
        public NewRowAction()
        {
            this(null);
        }

        public NewRowAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
            _tempStr = resHelper.getString("NewRowAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("NewRowAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("NewRowAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "NewRowAction", "newRowAction_actionPerformed", e);
        }
    }

    /**
     * output DeleteRowAction class
     */     
    protected class DeleteRowAction extends ItemAction {     
    
        public DeleteRowAction()
        {
            this(null);
        }

        public DeleteRowAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
            _tempStr = resHelper.getString("DeleteRowAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DeleteRowAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DeleteRowAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "DeleteRowAction", "deleteRowAction_actionPerformed", e);
        }
    }

    /**
     * output ModifyAction class
     */     
    protected class ModifyAction extends ItemAction {     
    
        public ModifyAction()
        {
            this(null);
        }

        public ModifyAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
            _tempStr = resHelper.getString("ModifyAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ModifyAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ModifyAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ModifyAction", "modifyAction_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeEditline class
     */     
    protected class ActionSafeEditline extends ItemAction {     
    
        public ActionSafeEditline()
        {
            this(null);
        }

        public ActionSafeEditline(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeEditline.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeEditline.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeEditline.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeEditline", "actionSafeEditline_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeAudit class
     */     
    protected class ActionSafeAudit extends ItemAction {     
    
        public ActionSafeAudit()
        {
            this(null);
        }

        public ActionSafeAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeAudit", "actionSafeAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeUnAudit class
     */     
    protected class ActionSafeUnAudit extends ItemAction {     
    
        public ActionSafeUnAudit()
        {
            this(null);
        }

        public ActionSafeUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeUnAudit", "actionSafeUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeWorkFlowGraphics class
     */     
    protected class ActionSafeWorkFlowGraphics extends ItemAction {     
    
        public ActionSafeWorkFlowGraphics()
        {
            this(null);
        }

        public ActionSafeWorkFlowGraphics(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeWorkFlowGraphics.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeWorkFlowGraphics.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeWorkFlowGraphics.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeWorkFlowGraphics", "actionSafeWorkFlowGraphics_actionPerformed", e);
        }
    }

    /**
     * output ActionSafeWorkFlowLog class
     */     
    protected class ActionSafeWorkFlowLog extends ItemAction {     
    
        public ActionSafeWorkFlowLog()
        {
            this(null);
        }

        public ActionSafeWorkFlowLog(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafeWorkFlowLog.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeWorkFlowLog.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafeWorkFlowLog.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafeWorkFlowLog", "actionSafeWorkFlowLog_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityWorkFlowAudit class
     */     
    protected class ActionQualityWorkFlowAudit extends ItemAction {     
    
        public ActionQualityWorkFlowAudit()
        {
            this(null);
        }

        public ActionQualityWorkFlowAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityWorkFlowAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityWorkFlowAudit", "actionQualityWorkFlowAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityWorkFlowUnAudit class
     */     
    protected class ActionQualityWorkFlowUnAudit extends ItemAction {     
    
        public ActionQualityWorkFlowUnAudit()
        {
            this(null);
        }

        public ActionQualityWorkFlowUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityWorkFlowUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityWorkFlowUnAudit", "actionQualityWorkFlowUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityWorkFlowGraphics class
     */     
    protected class ActionQualityWorkFlowGraphics extends ItemAction {     
    
        public ActionQualityWorkFlowGraphics()
        {
            this(null);
        }

        public ActionQualityWorkFlowGraphics(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityWorkFlowGraphics.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowGraphics.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowGraphics.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityWorkFlowGraphics", "actionQualityWorkFlowGraphics_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityWorkFlowLog class
     */     
    protected class ActionQualityWorkFlowLog extends ItemAction {     
    
        public ActionQualityWorkFlowLog()
        {
            this(null);
        }

        public ActionQualityWorkFlowLog(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityWorkFlowLog.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowLog.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityWorkFlowLog.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityWorkFlowLog", "actionQualityWorkFlowLog_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachment class
     */     
    protected class ActionAttachment extends ItemAction {     
    
        public ActionAttachment()
        {
            this(null);
        }

        public ActionAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionAttachment", "actionAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityPlanAdd class
     */     
    protected class ActionQualityPlanAdd extends ItemAction {     
    
        public ActionQualityPlanAdd()
        {
            this(null);
        }

        public ActionQualityPlanAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityPlanAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityPlanAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityPlanAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityPlanAdd", "actionQualityPlanAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityPlanEdit class
     */     
    protected class ActionQualityPlanEdit extends ItemAction {     
    
        public ActionQualityPlanEdit()
        {
            this(null);
        }

        public ActionQualityPlanEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityPlanEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityPlanEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityPlanEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityPlanEdit", "actionQualityPlanEdit_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityPlanDel class
     */     
    protected class ActionQualityPlanDel extends ItemAction {     
    
        public ActionQualityPlanDel()
        {
            this(null);
        }

        public ActionQualityPlanDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQualityPlanDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityPlanDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityPlanDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionQualityPlanDel", "actionQualityPlanDel_actionPerformed", e);
        }
    }

    /**
     * output ActionSafePlanAdd class
     */     
    protected class ActionSafePlanAdd extends ItemAction {     
    
        public ActionSafePlanAdd()
        {
            this(null);
        }

        public ActionSafePlanAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafePlanAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafePlanAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafePlanAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafePlanAdd", "actionSafePlanAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionSafePlanEdit class
     */     
    protected class ActionSafePlanEdit extends ItemAction {     
    
        public ActionSafePlanEdit()
        {
            this(null);
        }

        public ActionSafePlanEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafePlanEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafePlanEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafePlanEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafePlanEdit", "actionSafePlanEdit_actionPerformed", e);
        }
    }

    /**
     * output ActionSafePlanDel class
     */     
    protected class ActionSafePlanDel extends ItemAction {     
    
        public ActionSafePlanDel()
        {
            this(null);
        }

        public ActionSafePlanDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSafePlanDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafePlanDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSafePlanDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionSafePlanDel", "actionSafePlanDel_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialPlanAdd class
     */     
    protected class ActionMaterialPlanAdd extends ItemAction {     
    
        public ActionMaterialPlanAdd()
        {
            this(null);
        }

        public ActionMaterialPlanAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialPlanAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialPlanAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialPlanAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialPlanAdd", "actionMaterialPlanAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialPlanEdit class
     */     
    protected class ActionMaterialPlanEdit extends ItemAction {     
    
        public ActionMaterialPlanEdit()
        {
            this(null);
        }

        public ActionMaterialPlanEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialPlanEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialPlanEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialPlanEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialPlanEdit", "actionMaterialPlanEdit_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialPlanDel class
     */     
    protected class ActionMaterialPlanDel extends ItemAction {     
    
        public ActionMaterialPlanDel()
        {
            this(null);
        }

        public ActionMaterialPlanDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialPlanDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialPlanDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialPlanDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialPlanDel", "actionMaterialPlanDel_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventAdd class
     */     
    protected class ActionMaterialEventAdd extends ItemAction {     
    
        public ActionMaterialEventAdd()
        {
            this(null);
        }

        public ActionMaterialEventAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventAdd", "actionMaterialEventAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventEdit class
     */     
    protected class ActionMaterialEventEdit extends ItemAction {     
    
        public ActionMaterialEventEdit()
        {
            this(null);
        }

        public ActionMaterialEventEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventEdit", "actionMaterialEventEdit_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventDel class
     */     
    protected class ActionMaterialEventDel extends ItemAction {     
    
        public ActionMaterialEventDel()
        {
            this(null);
        }

        public ActionMaterialEventDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventDel", "actionMaterialEventDel_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventWorkFlowG class
     */     
    protected class ActionMaterialEventWorkFlowG extends ItemAction {     
    
        public ActionMaterialEventWorkFlowG()
        {
            this(null);
        }

        public ActionMaterialEventWorkFlowG(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventWorkFlowG.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventWorkFlowG.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventWorkFlowG.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventWorkFlowG", "actionMaterialEventWorkFlowG_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventResult class
     */     
    protected class ActionMaterialEventResult extends ItemAction {     
    
        public ActionMaterialEventResult()
        {
            this(null);
        }

        public ActionMaterialEventResult(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventResult.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventResult.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventResult.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventResult", "actionMaterialEventResult_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventAudit class
     */     
    protected class ActionMaterialEventAudit extends ItemAction {     
    
        public ActionMaterialEventAudit()
        {
            this(null);
        }

        public ActionMaterialEventAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventAudit", "actionMaterialEventAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialEventUnAudit class
     */     
    protected class ActionMaterialEventUnAudit extends ItemAction {     
    
        public ActionMaterialEventUnAudit()
        {
            this(null);
        }

        public ActionMaterialEventUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMaterialEventUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialEventUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionMaterialEventUnAudit", "actionMaterialEventUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionAddLogEvent class
     */     
    protected class ActionAddLogEvent extends ItemAction {     
    
        public ActionAddLogEvent()
        {
            this(null);
        }

        public ActionAddLogEvent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddLogEvent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLogEvent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLogEvent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionAddLogEvent", "actionAddLogEvent_actionPerformed", e);
        }
    }

    /**
     * output ActionDelLogEvent class
     */     
    protected class ActionDelLogEvent extends ItemAction {     
    
        public ActionDelLogEvent()
        {
            this(null);
        }

        public ActionDelLogEvent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelLogEvent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelLogEvent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelLogEvent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionDelLogEvent", "actionDelLogEvent_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTaskLog class
     */     
    protected class ActionAddTaskLog extends ItemAction {     
    
        public ActionAddTaskLog()
        {
            this(null);
        }

        public ActionAddTaskLog(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddTaskLog.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTaskLog.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTaskLog.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionAddTaskLog", "actionAddTaskLog_actionPerformed", e);
        }
    }

    /**
     * output ActionDelTaskLog class
     */     
    protected class ActionDelTaskLog extends ItemAction {     
    
        public ActionDelTaskLog()
        {
            this(null);
        }

        public ActionDelTaskLog(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelTaskLog.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTaskLog.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTaskLog.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionDelTaskLog", "actionDelTaskLog_actionPerformed", e);
        }
    }

    /**
     * output ActionIsMileStone class
     */     
    protected class ActionIsMileStone extends ItemAction {     
    
        public ActionIsMileStone()
        {
            this(null);
        }

        public ActionIsMileStone(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionIsMileStone.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIsMileStone.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIsMileStone.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleTaskPropertiesUI.this, "ActionIsMileStone", "actionIsMileStone_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FDCScheduleTaskPropertiesUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}