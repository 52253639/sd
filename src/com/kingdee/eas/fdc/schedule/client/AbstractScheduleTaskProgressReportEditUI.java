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
public abstract class AbstractScheduleTaskProgressReportEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleTaskProgressReportEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompletePrecent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompleteAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntendEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReportor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReportDate;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton kDRadioButton1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton kDRadioButton2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSrcTaskID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kdsDescCont;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDel;
    protected com.kingdee.bos.ctrl.swing.KDButton kDButton2;
    protected com.kingdee.bos.ctrl.swing.KDButton kDButton1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanStart;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompletePrecent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompleteAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRealStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkIntendEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRealEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReportor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkReportDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQualityMain;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDProject;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdPlanStart;
    protected com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo editData = null;
    protected AchievementAddNew achievementAddNew = null;
    protected AchievementDel achievementDel = null;
    protected ActionQualityAddNew actionQualityAddNew = null;
    protected ActionQualityDelete actionQualityDelete = null;
    /**
     * output class constructor
     */
    public AbstractScheduleTaskProgressReportEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleTaskProgressReportEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setExtendProperty("canForewarn", "true");
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
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
        //achievementAddNew
        this.achievementAddNew = new AchievementAddNew(this);
        getActionManager().registerAction("achievementAddNew", achievementAddNew);
         this.achievementAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //achievementDel
        this.achievementDel = new AchievementDel(this);
        getActionManager().registerAction("achievementDel", achievementDel);
         this.achievementDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityAddNew
        this.actionQualityAddNew = new ActionQualityAddNew(this);
        getActionManager().registerAction("actionQualityAddNew", actionQualityAddNew);
         this.actionQualityAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityDelete
        this.actionQualityDelete = new ActionQualityDelete(this);
        getActionManager().registerAction("actionQualityDelete", actionQualityDelete);
         this.actionQualityDelete.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompletePrecent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompleteAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntendEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReportor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReportDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDRadioButton1 = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDRadioButton2 = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.txtSrcTaskID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdsDescCont = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDButton2 = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDButton1 = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompletePrecent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCompleteAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPlanEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkRealStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkIntendEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkRealEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtReportor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkReportDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQualityMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDProject = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdPlanStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contCompletePrecent.setName("contCompletePrecent");
        this.contCompleteAmount.setName("contCompleteAmount");
        this.contPlanEndDate.setName("contPlanEndDate");
        this.contRealStartDate.setName("contRealStartDate");
        this.contIntendEndDate.setName("contIntendEndDate");
        this.contRealEndDate.setName("contRealEndDate");
        this.contReportor.setName("contReportor");
        this.contReportDate.setName("contReportDate");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDRadioButton1.setName("kDRadioButton1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDRadioButton2.setName("kDRadioButton2");
        this.txtSrcTaskID.setName("txtSrcTaskID");
        this.contDescription.setName("contDescription");
        this.kdsDescCont.setName("kdsDescCont");
        this.btnDel.setName("btnDel");
        this.kDButton2.setName("kDButton2");
        this.kDButton1.setName("kDButton1");
        this.btnAdd.setName("btnAdd");
        this.contRespPerson.setName("contRespPerson");
        this.contPlanStart.setName("contPlanStart");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtCompletePrecent.setName("txtCompletePrecent");
        this.txtCompleteAmount.setName("txtCompleteAmount");
        this.pkPlanEndDate.setName("pkPlanEndDate");
        this.pkRealStartDate.setName("pkRealStartDate");
        this.pkIntendEndDate.setName("pkIntendEndDate");
        this.pkRealEndDate.setName("pkRealEndDate");
        this.prmtReportor.setName("prmtReportor");
        this.pkReportDate.setName("pkReportDate");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDContainer1.setName("kDContainer1");
        this.kDTable1.setName("kDTable1");
        this.kDContainer2.setName("kDContainer2");
        this.tblQualityMain.setName("tblQualityMain");
        this.kDContainer3.setName("kDContainer3");
        this.kDProject.setName("kDProject");
        this.txtDescription.setName("txtDescription");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.kdPlanStart.setName("kdPlanStart");
        // CoreUI		
        this.setPreferredSize(new Dimension(835,580));		
        this.btnAddNew.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contCompletePrecent		
        this.contCompletePrecent.setBoundLabelText(resHelper.getString("contCompletePrecent.boundLabelText"));		
        this.contCompletePrecent.setBoundLabelLength(100);		
        this.contCompletePrecent.setBoundLabelUnderline(true);
        // contCompleteAmount		
        this.contCompleteAmount.setBoundLabelText(resHelper.getString("contCompleteAmount.boundLabelText"));		
        this.contCompleteAmount.setBoundLabelLength(100);		
        this.contCompleteAmount.setBoundLabelUnderline(true);
        // contPlanEndDate		
        this.contPlanEndDate.setBoundLabelText(resHelper.getString("contPlanEndDate.boundLabelText"));		
        this.contPlanEndDate.setBoundLabelLength(100);		
        this.contPlanEndDate.setBoundLabelUnderline(true);		
        this.contPlanEndDate.setEnabled(false);
        // contRealStartDate		
        this.contRealStartDate.setBoundLabelText(resHelper.getString("contRealStartDate.boundLabelText"));		
        this.contRealStartDate.setBoundLabelLength(100);		
        this.contRealStartDate.setBoundLabelUnderline(true);
        // contIntendEndDate		
        this.contIntendEndDate.setBoundLabelText(resHelper.getString("contIntendEndDate.boundLabelText"));		
        this.contIntendEndDate.setBoundLabelLength(100);		
        this.contIntendEndDate.setBoundLabelUnderline(true);
        // contRealEndDate		
        this.contRealEndDate.setBoundLabelText(resHelper.getString("contRealEndDate.boundLabelText"));		
        this.contRealEndDate.setBoundLabelLength(100);		
        this.contRealEndDate.setBoundLabelUnderline(true);
        // contReportor		
        this.contReportor.setBoundLabelText(resHelper.getString("contReportor.boundLabelText"));		
        this.contReportor.setBoundLabelLength(100);		
        this.contReportor.setBoundLabelUnderline(true);
        // contReportDate		
        this.contReportDate.setBoundLabelText(resHelper.getString("contReportDate.boundLabelText"));		
        this.contReportDate.setBoundLabelLength(100);		
        this.contReportDate.setBoundLabelUnderline(true);
        // kDTabbedPane1
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.kDRadioButton1);
        this.kDButtonGroup1.add(this.kDRadioButton2);
        // kDRadioButton1		
        this.kDRadioButton1.setText(resHelper.getString("kDRadioButton1.text"));
        this.kDRadioButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDRadioButton1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    kDRadioButton1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDRadioButton2		
        this.kDRadioButton2.setText(resHelper.getString("kDRadioButton2.text"));
        this.kDRadioButton2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDRadioButton2_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    kDRadioButton2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtSrcTaskID		
        this.txtSrcTaskID.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));
        // kdsDescCont
        // btnDel
        this.btnDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionQualityDelete, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDel.setText(resHelper.getString("btnDel.text"));
        // kDButton2
        this.kDButton2.setAction((IItemAction)ActionProxyFactory.getProxy(achievementDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDButton2.setText(resHelper.getString("kDButton2.text"));
        // kDButton1
        this.kDButton1.setAction((IItemAction)ActionProxyFactory.getProxy(achievementAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDButton1.setText(resHelper.getString("kDButton1.text"));
        // btnAdd
        this.btnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionQualityAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        // contRespPerson		
        this.contRespPerson.setBoundLabelText(resHelper.getString("contRespPerson.boundLabelText"));		
        this.contRespPerson.setBoundLabelUnderline(true);		
        this.contRespPerson.setBoundLabelLength(100);		
        this.contRespPerson.setEnabled(false);
        // contPlanStart		
        this.contPlanStart.setBoundLabelText(resHelper.getString("contPlanStart.boundLabelText"));		
        this.contPlanStart.setBoundLabelUnderline(true);		
        this.contPlanStart.setBoundLabelLength(100);		
        this.contPlanStart.setEnabled(false);
        // txtNumber
        // txtName
        // txtCompletePrecent		
        this.txtCompletePrecent.setPrecision(0);		
        this.txtCompletePrecent.setRequired(true);
        this.txtCompletePrecent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    txtCompletePrecent_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtCompleteAmount
        // pkPlanEndDate		
        this.pkPlanEndDate.setEnabled(false);
        // pkRealStartDate		
        this.pkRealStartDate.setRequired(true);
        // pkIntendEndDate
        // pkRealEndDate		
        this.pkRealEndDate.setRequired(true);
        // prmtReportor		
        this.prmtReportor.setEnabled(false);
        // pkReportDate		
        this.pkReportDate.setEnabled(false);
        // kDPanel1
        // kDPanel2
        // kDPanel3
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"achievementType\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"achievementDoc\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"creator\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"id\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{achievementType}</t:Cell><t:Cell>$Resource{achievementDoc}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDTable1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // tblQualityMain
		String tblQualityMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"checkPoint\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"checkPost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"checkPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"checkResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"subPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"subDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{checkPoint}</t:Cell><t:Cell>$Resource{checkPost}</t:Cell><t:Cell>$Resource{checkPercent}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{checkResult}</t:Cell><t:Cell>$Resource{subPerson}</t:Cell><t:Cell>$Resource{subDate}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblQualityMain.setFormatXml(resHelper.translateString("tblQualityMain",tblQualityMainStrXML));
        this.tblQualityMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblQualityMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kDProject
		String kDProjectStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"imgDescription\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{imgDescription}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDProject.setFormatXml(resHelper.translateString("kDProject",kDProjectStrXML));
        this.kDProject.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDProject_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // txtDescription		
        this.txtDescription.setMaxLength(500);
        // prmtAdminPerson		
        this.prmtAdminPerson.setEnabled(false);
        // kdPlanStart		
        this.kdPlanStart.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 835, 580));
        this.setLayout(null);
        contNumber.setBounds(new Rectangle(555, 10, 270, 19));
        this.add(contNumber, null);
        contName.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contName, null);
        contCompletePrecent.setBounds(new Rectangle(555, 40, 270, 19));
        this.add(contCompletePrecent, null);
        contCompleteAmount.setBounds(new Rectangle(555, 160, 270, 19));
        this.add(contCompleteAmount, null);
        contPlanEndDate.setBounds(new Rectangle(555, 70, 270, 19));
        this.add(contPlanEndDate, null);
        contRealStartDate.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contRealStartDate, null);
        contIntendEndDate.setBounds(new Rectangle(555, 100, 270, 19));
        this.add(contIntendEndDate, null);
        contRealEndDate.setBounds(new Rectangle(555, 100, 270, 19));
        this.add(contRealEndDate, null);
        contReportor.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contReportor, null);
        contReportDate.setBounds(new Rectangle(555, 130, 270, 19));
        this.add(contReportDate, null);
        kDTabbedPane1.setBounds(new Rectangle(15, 284, 815, 286));
        this.add(kDTabbedPane1, null);
        kDRadioButton1.setBounds(new Rectangle(139, 40, 62, 19));
        this.add(kDRadioButton1, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 40, 101, 19));
        this.add(kDLabelContainer1, null);
        kDRadioButton2.setBounds(new Rectangle(196, 40, 68, 21));
        this.add(kDRadioButton2, null);
        txtSrcTaskID.setBounds(new Rectangle(320, 26, 170, 19));
        this.add(txtSrcTaskID, null);
        contDescription.setBounds(new Rectangle(11, 183, 270, 19));
        this.add(contDescription, null);
        kdsDescCont.setBounds(new Rectangle(15, 207, 815, 66));
        this.add(kdsDescCont, null);
        btnDel.setBounds(new Rectangle(312, 185, 73, 21));
        this.add(btnDel, null);
        kDButton2.setBounds(new Rectangle(607, 185, 73, 21));
        this.add(kDButton2, null);
        kDButton1.setBounds(new Rectangle(476, 185, 73, 21));
        this.add(kDButton1, null);
        btnAdd.setBounds(new Rectangle(178, 185, 73, 21));
        this.add(btnAdd, null);
        contRespPerson.setBounds(new Rectangle(10, 160, 270, 19));
        this.add(contRespPerson, null);
        contPlanStart.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contPlanStart, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contCompletePrecent
        contCompletePrecent.setBoundEditor(txtCompletePrecent);
        //contCompleteAmount
        contCompleteAmount.setBoundEditor(txtCompleteAmount);
        //contPlanEndDate
        contPlanEndDate.setBoundEditor(pkPlanEndDate);
        //contRealStartDate
        contRealStartDate.setBoundEditor(pkRealStartDate);
        //contIntendEndDate
        contIntendEndDate.setBoundEditor(pkIntendEndDate);
        //contRealEndDate
        contRealEndDate.setBoundEditor(pkRealEndDate);
        //contReportor
        contReportor.setBoundEditor(prmtReportor);
        //contReportDate
        contReportDate.setBoundEditor(pkReportDate);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDTable1, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(kDContainer2, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblQualityMain, BorderLayout.CENTER);
        //kDPanel3
kDPanel3.setLayout(new BorderLayout(0, 0));        kDPanel3.add(kDContainer3, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(kDProject, BorderLayout.CENTER);
        //kdsDescCont
        kdsDescCont.getViewport().add(txtDescription, null);
        //contRespPerson
        contRespPerson.setBoundEditor(prmtAdminPerson);
        //contPlanStart
        contPlanStart.setBoundEditor(kdPlanStart);

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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("srcRelateTask", String.class, this.txtSrcTaskID, "text");
		dataBinder.registerBinding("completePrecent", java.math.BigDecimal.class, this.txtCompletePrecent, "value");
		dataBinder.registerBinding("completeAmount", java.math.BigDecimal.class, this.txtCompleteAmount, "value");
		dataBinder.registerBinding("realStartDate", java.util.Date.class, this.pkRealStartDate, "value");
		dataBinder.registerBinding("intendEndDate", java.util.Date.class, this.pkIntendEndDate, "value");
		dataBinder.registerBinding("realEndDate", java.util.Date.class, this.pkRealEndDate, "value");
		dataBinder.registerBinding("reportDate", java.util.Date.class, this.pkReportDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ScheduleTaskProgressReportEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo)ov;
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
		getValidateHelper().registerBindProperty("srcRelateTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completePrecent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("realStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intendEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("realEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportDate", ValidateHelper.ON_SAVE);    
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kDRadioButton1_stateChanged method
     */
    protected void kDRadioButton1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kDRadioButton1_mouseClicked method
     */
    protected void kDRadioButton1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kDRadioButton2_stateChanged method
     */
    protected void kDRadioButton2_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kDRadioButton2_mouseClicked method
     */
    protected void kDRadioButton2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtCompletePrecent_keyReleased method
     */
    protected void txtCompletePrecent_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_tableClicked method
     */
    protected void kDTable1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblQualityMain_tableClicked method
     */
    protected void tblQualityMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kDProject_tableClicked method
     */
    protected void kDProject_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("srcRelateTask"));
        sic.add(new SelectorItemInfo("completePrecent"));
        sic.add(new SelectorItemInfo("completeAmount"));
        sic.add(new SelectorItemInfo("realStartDate"));
        sic.add(new SelectorItemInfo("intendEndDate"));
        sic.add(new SelectorItemInfo("realEndDate"));
        sic.add(new SelectorItemInfo("reportDate"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output achievementAddNew_actionPerformed method
     */
    public void achievementAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output achievementDel_actionPerformed method
     */
    public void achievementDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityAddNew_actionPerformed method
     */
    public void actionQualityAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityDelete_actionPerformed method
     */
    public void actionQualityDelete_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareAchievementAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAchievementAddNew() {
    	return false;
    }
	public RequestContext prepareAchievementDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAchievementDel() {
    	return false;
    }
	public RequestContext prepareActionQualityAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityAddNew() {
    	return false;
    }
	public RequestContext prepareActionQualityDelete(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityDelete() {
    	return false;
    }

    /**
     * output AchievementAddNew class
     */     
    protected class AchievementAddNew extends ItemAction {     
    
        public AchievementAddNew()
        {
            this(null);
        }

        public AchievementAddNew(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("AchievementAddNew.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AchievementAddNew.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AchievementAddNew.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleTaskProgressReportEditUI.this, "AchievementAddNew", "achievementAddNew_actionPerformed", e);
        }
    }

    /**
     * output AchievementDel class
     */     
    protected class AchievementDel extends ItemAction {     
    
        public AchievementDel()
        {
            this(null);
        }

        public AchievementDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("AchievementDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AchievementDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("AchievementDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleTaskProgressReportEditUI.this, "AchievementDel", "achievementDel_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityAddNew class
     */     
    protected class ActionQualityAddNew extends ItemAction {     
    
        public ActionQualityAddNew()
        {
            this(null);
        }

        public ActionQualityAddNew(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQualityAddNew.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityAddNew.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityAddNew.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleTaskProgressReportEditUI.this, "ActionQualityAddNew", "actionQualityAddNew_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityDelete class
     */     
    protected class ActionQualityDelete extends ItemAction {     
    
        public ActionQualityDelete()
        {
            this(null);
        }

        public ActionQualityDelete(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQualityDelete.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityDelete.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityDelete.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleTaskProgressReportEditUI.this, "ActionQualityDelete", "actionQualityDelete_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ScheduleTaskProgressReportEditUI");
    }




}