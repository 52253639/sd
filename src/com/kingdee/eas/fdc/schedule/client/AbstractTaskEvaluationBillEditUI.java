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
public abstract class AbstractTaskEvaluationBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTaskEvaluationBillEditUI.class);
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEvaluationPerson;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlTaskEvaluation;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlScheduleInfo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEvaluationDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEvaluationResult;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEvaluationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtEvaluationDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEvaluationResult;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboEvaluationType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalWorkLoad;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanStart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualStart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualEnd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtTotalPercent;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtTotalWorkload;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpPlanStart;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpPlanEnd;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpActualStart;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpActualEnd;
    protected com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractTaskEvaluationBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTaskEvaluationBillEditUI.class.getName());
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
        this.prmtEvaluationPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlTaskEvaluation = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlScheduleInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contEvaluationDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEvaluationResult = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEvaluationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtEvaluationDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtEvaluationResult = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboEvaluationType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contTaskName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalWorkLoad = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTaskName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaskNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTotalPercent = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtTotalWorkload = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.kdpPlanStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpPlanEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpActualStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpActualEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtEvaluationPerson.setName("prmtEvaluationPerson");
        this.pnlTaskEvaluation.setName("pnlTaskEvaluation");
        this.pnlScheduleInfo.setName("pnlScheduleInfo");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contEvaluationDes.setName("contEvaluationDes");
        this.contEvaluationResult.setName("contEvaluationResult");
        this.contEvaluationType.setName("contEvaluationType");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.txtEvaluationDes.setName("txtEvaluationDes");
        this.prmtEvaluationResult.setName("prmtEvaluationResult");
        this.comboEvaluationType.setName("comboEvaluationType");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtCreator.setName("prmtCreator");
        this.contTaskName.setName("contTaskName");
        this.contTaskNumber.setName("contTaskNumber");
        this.contAdminPerson.setName("contAdminPerson");
        this.contAdminDept.setName("contAdminDept");
        this.contTaskType.setName("contTaskType");
        this.contTotalWorkLoad.setName("contTotalWorkLoad");
        this.contPlanStart.setName("contPlanStart");
        this.contPlanEnd.setName("contPlanEnd");
        this.contActualStart.setName("contActualStart");
        this.contActualEnd.setName("contActualEnd");
        this.txtTaskName.setName("txtTaskName");
        this.txtTaskNumber.setName("txtTaskNumber");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.txtTotalPercent.setName("txtTotalPercent");
        this.txtTotalWorkload.setName("txtTotalWorkload");
        this.kdpPlanStart.setName("kdpPlanStart");
        this.kdpPlanEnd.setName("kdpPlanEnd");
        this.kdpActualStart.setName("kdpActualStart");
        this.kdpActualEnd.setName("kdpActualEnd");
        // CoreUI		
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
        // prmtEvaluationPerson		
        this.prmtEvaluationPerson.setVisible(false);
        // pnlTaskEvaluation		
        this.pnlTaskEvaluation.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlTaskEvaluation.border.title")));
        // pnlScheduleInfo		
        this.pnlScheduleInfo.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlScheduleInfo.border.title")));
        // kDScrollPane1
        // contEvaluationDes		
        this.contEvaluationDes.setBoundLabelText(resHelper.getString("contEvaluationDes.boundLabelText"));		
        this.contEvaluationDes.setBoundLabelLength(100);		
        this.contEvaluationDes.setBoundLabelUnderline(true);
        // contEvaluationResult		
        this.contEvaluationResult.setBoundLabelText(resHelper.getString("contEvaluationResult.boundLabelText"));		
        this.contEvaluationResult.setBoundLabelLength(100);		
        this.contEvaluationResult.setBoundLabelUnderline(true);
        // contEvaluationType		
        this.contEvaluationType.setBoundLabelText(resHelper.getString("contEvaluationType.boundLabelText"));		
        this.contEvaluationType.setBoundLabelLength(100);		
        this.contEvaluationType.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // txtEvaluationDes
        // prmtEvaluationResult		
        this.prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQuery");
        // comboEvaluationType		
        this.comboEvaluationType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum").toArray());
        this.comboEvaluationType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboEvaluationType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkCreateTime
        // prmtCreator		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.UserInfoQuery");
        // contTaskName		
        this.contTaskName.setBoundLabelText(resHelper.getString("contTaskName.boundLabelText"));		
        this.contTaskName.setBoundLabelUnderline(true);		
        this.contTaskName.setBoundLabelLength(100);
        // contTaskNumber		
        this.contTaskNumber.setBoundLabelText(resHelper.getString("contTaskNumber.boundLabelText"));		
        this.contTaskNumber.setBoundLabelUnderline(true);		
        this.contTaskNumber.setBoundLabelLength(100);
        // contAdminPerson		
        this.contAdminPerson.setBoundLabelText(resHelper.getString("contAdminPerson.boundLabelText"));		
        this.contAdminPerson.setBoundLabelUnderline(true);		
        this.contAdminPerson.setBoundLabelLength(100);
        // contAdminDept		
        this.contAdminDept.setBoundLabelText(resHelper.getString("contAdminDept.boundLabelText"));		
        this.contAdminDept.setBoundLabelUnderline(true);		
        this.contAdminDept.setBoundLabelLength(100);
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(100);		
        this.contTaskType.setBoundLabelUnderline(true);
        // contTotalWorkLoad		
        this.contTotalWorkLoad.setBoundLabelText(resHelper.getString("contTotalWorkLoad.boundLabelText"));		
        this.contTotalWorkLoad.setBoundLabelUnderline(true);		
        this.contTotalWorkLoad.setBoundLabelLength(100);
        // contPlanStart		
        this.contPlanStart.setBoundLabelText(resHelper.getString("contPlanStart.boundLabelText"));		
        this.contPlanStart.setBoundLabelUnderline(true);		
        this.contPlanStart.setBoundLabelLength(100);
        // contPlanEnd		
        this.contPlanEnd.setBoundLabelText(resHelper.getString("contPlanEnd.boundLabelText"));		
        this.contPlanEnd.setBoundLabelUnderline(true);		
        this.contPlanEnd.setBoundLabelLength(100);
        // contActualStart		
        this.contActualStart.setBoundLabelText(resHelper.getString("contActualStart.boundLabelText"));		
        this.contActualStart.setBoundLabelUnderline(true);		
        this.contActualStart.setBoundLabelLength(100);
        // contActualEnd		
        this.contActualEnd.setBoundLabelText(resHelper.getString("contActualEnd.boundLabelText"));		
        this.contActualEnd.setBoundLabelUnderline(true);		
        this.contActualEnd.setBoundLabelLength(100);
        // txtTaskName		
        this.txtTaskName.setEnabled(false);
        // txtTaskNumber		
        this.txtTaskNumber.setEnabled(false);
        // prmtAdminPerson		
        this.prmtAdminPerson.setEnabled(false);
        // prmtAdminDept		
        this.prmtAdminDept.setEnabled(false);
        // txtTotalPercent		
        this.txtTotalPercent.setDataType(6);		
        this.txtTotalPercent.setPrecision(2);		
        this.txtTotalPercent.setEnabled(false);
        // txtTotalWorkload		
        this.txtTotalWorkload.setEnabled(false);		
        this.txtTotalWorkload.setPrecision(2);
        // kdpPlanStart		
        this.kdpPlanStart.setEnabled(false);
        // kdpPlanEnd		
        this.kdpPlanEnd.setEnabled(false);
        // kdpActualStart		
        this.kdpActualStart.setEnabled(false);
        // kdpActualEnd		
        this.kdpActualEnd.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 700, 520));
        this.setLayout(null);
        prmtEvaluationPerson.setBounds(new Rectangle(457, 523, 170, 19));
        this.add(prmtEvaluationPerson, null);
        pnlTaskEvaluation.setBounds(new Rectangle(8, 233, 684, 277));
        this.add(pnlTaskEvaluation, null);
        pnlScheduleInfo.setBounds(new Rectangle(8, 17, 684, 197));
        this.add(pnlScheduleInfo, null);
        //pnlTaskEvaluation
        pnlTaskEvaluation.setLayout(null);        kDScrollPane1.setBounds(new Rectangle(17, 104, 647, 158));
        pnlTaskEvaluation.add(kDScrollPane1, null);
        contEvaluationDes.setBounds(new Rectangle(17, 80, 60, 19));
        pnlTaskEvaluation.add(contEvaluationDes, null);
        contEvaluationResult.setBounds(new Rectangle(397, 19, 270, 19));
        pnlTaskEvaluation.add(contEvaluationResult, null);
        contEvaluationType.setBounds(new Rectangle(17, 19, 270, 19));
        pnlTaskEvaluation.add(contEvaluationType, null);
        contCreateTime.setBounds(new Rectangle(397, 49, 270, 19));
        pnlTaskEvaluation.add(contCreateTime, null);
        contCreator.setBounds(new Rectangle(17, 49, 270, 19));
        pnlTaskEvaluation.add(contCreator, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtEvaluationDes, null);
        //contEvaluationResult
        contEvaluationResult.setBoundEditor(prmtEvaluationResult);
        //contEvaluationType
        contEvaluationType.setBoundEditor(comboEvaluationType);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //pnlScheduleInfo
        pnlScheduleInfo.setLayout(null);        contTaskName.setBounds(new Rectangle(15, 20, 270, 19));
        pnlScheduleInfo.add(contTaskName, null);
        contTaskNumber.setBounds(new Rectangle(396, 20, 270, 19));
        pnlScheduleInfo.add(contTaskNumber, null);
        contAdminPerson.setBounds(new Rectangle(15, 51, 270, 19));
        pnlScheduleInfo.add(contAdminPerson, null);
        contAdminDept.setBounds(new Rectangle(396, 50, 270, 19));
        pnlScheduleInfo.add(contAdminDept, null);
        contTaskType.setBounds(new Rectangle(15, 81, 270, 19));
        pnlScheduleInfo.add(contTaskType, null);
        contTotalWorkLoad.setBounds(new Rectangle(396, 81, 270, 19));
        pnlScheduleInfo.add(contTotalWorkLoad, null);
        contPlanStart.setBounds(new Rectangle(18, 114, 270, 19));
        pnlScheduleInfo.add(contPlanStart, null);
        contPlanEnd.setBounds(new Rectangle(396, 117, 270, 19));
        pnlScheduleInfo.add(contPlanEnd, null);
        contActualStart.setBounds(new Rectangle(19, 146, 270, 19));
        pnlScheduleInfo.add(contActualStart, null);
        contActualEnd.setBounds(new Rectangle(396, 153, 270, 19));
        pnlScheduleInfo.add(contActualEnd, null);
        //contTaskName
        contTaskName.setBoundEditor(txtTaskName);
        //contTaskNumber
        contTaskNumber.setBoundEditor(txtTaskNumber);
        //contAdminPerson
        contAdminPerson.setBoundEditor(prmtAdminPerson);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contTaskType
        contTaskType.setBoundEditor(txtTotalPercent);
        //contTotalWorkLoad
        contTotalWorkLoad.setBoundEditor(txtTotalWorkload);
        //contPlanStart
        contPlanStart.setBoundEditor(kdpPlanStart);
        //contPlanEnd
        contPlanEnd.setBoundEditor(kdpPlanEnd);
        //contActualStart
        contActualStart.setBoundEditor(kdpActualStart);
        //contActualEnd
        contActualEnd.setBoundEditor(kdpActualEnd);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("evalucationPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtEvaluationPerson, "data");
		dataBinder.registerBinding("evaluationDes", String.class, this.txtEvaluationDes, "text");
		dataBinder.registerBinding("evaluationResult", com.kingdee.eas.fdc.schedule.TaskEvaluationInfo.class, this.prmtEvaluationResult, "data");
		dataBinder.registerBinding("evaluationType", com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum.class, this.comboEvaluationType, "selectedItem");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.TaskEvaluationBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo)ov;
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
		getValidateHelper().registerBindProperty("evalucationPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaluationDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaluationResult", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaluationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    		
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
     * output comboEvaluationType_itemStateChanged method
     */
    protected void comboEvaluationType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("evalucationPerson.*"));
        sic.add(new SelectorItemInfo("evaluationDes"));
        sic.add(new SelectorItemInfo("evaluationResult.*"));
        sic.add(new SelectorItemInfo("evaluationType"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("creator.*"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "TaskEvaluationBillEditUI");
    }




}