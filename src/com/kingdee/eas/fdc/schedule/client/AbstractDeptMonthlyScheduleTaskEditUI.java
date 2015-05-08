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
public abstract class AbstractDeptMonthlyScheduleTaskEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeptMonthlyScheduleTaskEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskOrigin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWeightRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtPlanMonth;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker planBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDTextField comboTaskOrigin;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtWeightRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtProjectPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFinishStand;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRequiestesource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelatedTask;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFinish;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRelatedTask;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanEndDate;
    protected com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractDeptMonthlyScheduleTaskEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDeptMonthlyScheduleTaskEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskOrigin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWeightRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanFinishDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtPlanMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.planBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAdminDept = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboTaskOrigin = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWeightRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtProjectPeriod = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTaskName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPlanFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtFinishStand = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtRequiestesource = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelatedTask = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFinish = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDBizPromptBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaskType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRelatedTask = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPlanStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPlanEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.contTaskOrigin.setName("contTaskOrigin");
        this.contWeightRate.setName("contWeightRate");
        this.contProjectPeriod.setName("contProjectPeriod");
        this.contTaskName.setName("contTaskName");
        this.contAdminPerson.setName("contAdminPerson");
        this.contPlanFinishDate.setName("contPlanFinishDate");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtPlanMonth.setName("txtPlanMonth");
        this.planBeginDate.setName("planBeginDate");
        this.txtAdminDept.setName("txtAdminDept");
        this.comboTaskOrigin.setName("comboTaskOrigin");
        this.txtWeightRate.setName("txtWeightRate");
        this.txtProjectPeriod.setName("txtProjectPeriod");
        this.txtTaskName.setName("txtTaskName");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.pkPlanFinishDate.setName("pkPlanFinishDate");
        this.txtFinishStand.setName("txtFinishStand");
        this.txtRequiestesource.setName("txtRequiestesource");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contProject.setName("contProject");
        this.contTaskType.setName("contTaskType");
        this.contRelatedTask.setName("contRelatedTask");
        this.contPlanStartDate.setName("contPlanStartDate");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.txtFinish.setName("txtFinish");
        this.kDBizPromptBox1.setName("kDBizPromptBox1");
        this.txtProject.setName("txtProject");
        this.txtTaskType.setName("txtTaskType");
        this.prmtRelatedTask.setName("prmtRelatedTask");
        this.pkPlanStartDate.setName("pkPlanStartDate");
        this.pkPlanEndDate.setName("pkPlanEndDate");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
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
        this.btnAttachment.setVisible(false);
        // kDTabbedPane1
        // kDPanel1
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);		
        this.kDLabelContainer6.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setVisible(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setVisible(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setVisible(true);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);		
        this.kDLabelContainer11.setForeground(new java.awt.Color(0,0,0));
        // contTaskOrigin		
        this.contTaskOrigin.setBoundLabelText(resHelper.getString("contTaskOrigin.boundLabelText"));		
        this.contTaskOrigin.setBoundLabelLength(100);		
        this.contTaskOrigin.setBoundLabelUnderline(true);
        // contWeightRate		
        this.contWeightRate.setBoundLabelText(resHelper.getString("contWeightRate.boundLabelText"));		
        this.contWeightRate.setBoundLabelLength(100);		
        this.contWeightRate.setBoundLabelUnderline(true);
        // contProjectPeriod		
        this.contProjectPeriod.setBoundLabelText(resHelper.getString("contProjectPeriod.boundLabelText"));		
        this.contProjectPeriod.setBoundLabelLength(100);		
        this.contProjectPeriod.setBoundLabelUnderline(true);
        // contTaskName		
        this.contTaskName.setBoundLabelText(resHelper.getString("contTaskName.boundLabelText"));		
        this.contTaskName.setBoundLabelLength(100);		
        this.contTaskName.setBoundLabelUnderline(true);
        // contAdminPerson		
        this.contAdminPerson.setBoundLabelText(resHelper.getString("contAdminPerson.boundLabelText"));		
        this.contAdminPerson.setBoundLabelLength(100);		
        this.contAdminPerson.setBoundLabelUnderline(true);
        // contPlanFinishDate		
        this.contPlanFinishDate.setBoundLabelText(resHelper.getString("contPlanFinishDate.boundLabelText"));		
        this.contPlanFinishDate.setBoundLabelLength(100);		
        this.contPlanFinishDate.setBoundLabelUnderline(true);
        // kDScrollPane2
        // kDScrollPane1
        // txtPlanMonth		
        this.txtPlanMonth.setRequired(true);		
        this.txtPlanMonth.setEnabled(false);
        // planBeginDate
        this.planBeginDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    planBeginDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAdminDept		
        this.txtAdminDept.setMaxLength(255);
        // comboTaskOrigin		
        this.comboTaskOrigin.setMaxLength(255);
        // txtWeightRate
        // txtProjectPeriod
        // txtTaskName		
        this.txtTaskName.setMaxLength(255);
        // prmtAdminPerson		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
        // pkPlanFinishDate		
        this.pkPlanFinishDate.setRequired(true);
        this.pkPlanFinishDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkPlanFinishDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtFinishStand		
        this.txtFinishStand.setVisible(true);		
        this.txtFinishStand.setMaxLength(2000);		
        this.txtFinishStand.setForeground(new java.awt.Color(0,0,0));		
        this.txtFinishStand.setLineWrap(true);		
        this.txtFinishStand.setWrapStyleWord(true);
        // txtRequiestesource		
        this.txtRequiestesource.setVisible(true);		
        this.txtRequiestesource.setMaxLength(2000);		
        this.txtRequiestesource.setForeground(new java.awt.Color(0,0,0));		
        this.txtRequiestesource.setLineWrap(true);		
        this.txtRequiestesource.setWrapStyleWord(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(100);		
        this.contTaskType.setBoundLabelUnderline(true);
        // contRelatedTask		
        this.contRelatedTask.setBoundLabelText(resHelper.getString("contRelatedTask.boundLabelText"));		
        this.contRelatedTask.setBoundLabelLength(100);		
        this.contRelatedTask.setBoundLabelUnderline(true);
        // contPlanStartDate		
        this.contPlanStartDate.setBoundLabelText(resHelper.getString("contPlanStartDate.boundLabelText"));		
        this.contPlanStartDate.setBoundLabelLength(100);		
        this.contPlanStartDate.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // txtFinish		
        this.txtFinish.setMaxLength(255);		
        this.txtFinish.setEnabled(false);
        // kDBizPromptBox1
        // txtProject		
        this.txtProject.setMaxLength(255);
        // txtTaskType		
        this.txtTaskType.setMaxLength(255);
        // prmtRelatedTask		
        this.prmtRelatedTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskQuery");
        this.prmtRelatedTask.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRelatedTask_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkPlanStartDate
        // pkPlanEndDate		
        this.pkPlanEndDate.setEnabled(false);
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
        kDTabbedPane1.setBounds(new Rectangle(8, 6, 1013, 680));
        this.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 647));        kDPanel2.setBounds(new Rectangle(13, 13, 981, 437));
        kDPanel1.add(kDPanel2, new KDLayout.Constraints(13, 13, 981, 437, 0));
        kDPanel3.setBounds(new Rectangle(10, 472, 981, 107));
        kDPanel1.add(kDPanel3, new KDLayout.Constraints(10, 472, 981, 107, 0));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(13, 13, 981, 437));        kDLabelContainer5.setBounds(new Rectangle(349, 29, 270, 19));
        kDPanel2.add(kDLabelContainer5, new KDLayout.Constraints(349, 29, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(15, 93, 270, 19));
        kDPanel2.add(kDLabelContainer6, new KDLayout.Constraints(15, 93, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(16, 279, 270, 19));
        kDPanel2.add(kDLabelContainer9, new KDLayout.Constraints(16, 279, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer10.setBounds(new Rectangle(16, 125, 270, 19));
        kDPanel2.add(kDLabelContainer10, new KDLayout.Constraints(16, 125, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(15, 29, 270, 19));
        kDPanel2.add(kDLabelContainer11, new KDLayout.Constraints(15, 29, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaskOrigin.setBounds(new Rectangle(684, 29, 270, 19));
        kDPanel2.add(contTaskOrigin, new KDLayout.Constraints(684, 29, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWeightRate.setBounds(new Rectangle(684, 61, 270, 19));
        kDPanel2.add(contWeightRate, new KDLayout.Constraints(684, 61, 270, 19, 0));
        contProjectPeriod.setBounds(new Rectangle(684, 93, 270, 19));
        kDPanel2.add(contProjectPeriod, new KDLayout.Constraints(684, 93, 270, 19, 0));
        contTaskName.setBounds(new Rectangle(15, 61, 270, 19));
        kDPanel2.add(contTaskName, new KDLayout.Constraints(15, 61, 270, 19, 0));
        contAdminPerson.setBounds(new Rectangle(349, 61, 270, 19));
        kDPanel2.add(contAdminPerson, new KDLayout.Constraints(349, 61, 270, 19, 0));
        contPlanFinishDate.setBounds(new Rectangle(349, 93, 270, 19));
        kDPanel2.add(contPlanFinishDate, new KDLayout.Constraints(349, 93, 270, 19, 0));
        kDScrollPane2.setBounds(new Rectangle(16, 150, 938, 124));
        kDPanel2.add(kDScrollPane2, new KDLayout.Constraints(16, 150, 938, 124, 0));
        kDScrollPane1.setBounds(new Rectangle(15, 310, 938, 104));
        kDPanel2.add(kDScrollPane1, new KDLayout.Constraints(15, 310, 938, 104, 0));
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtPlanMonth);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(planBeginDate);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtAdminDept);
        //contTaskOrigin
        contTaskOrigin.setBoundEditor(comboTaskOrigin);
        //contWeightRate
        contWeightRate.setBoundEditor(txtWeightRate);
        //contProjectPeriod
        contProjectPeriod.setBoundEditor(txtProjectPeriod);
        //contTaskName
        contTaskName.setBoundEditor(txtTaskName);
        //contAdminPerson
        contAdminPerson.setBoundEditor(prmtAdminPerson);
        //contPlanFinishDate
        contPlanFinishDate.setBoundEditor(pkPlanFinishDate);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtFinishStand, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtRequiestesource, null);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(10, 472, 981, 107));        kDLabelContainer1.setBounds(new Rectangle(683, 64, 270, 19));
        kDPanel3.add(kDLabelContainer1, new KDLayout.Constraints(683, 64, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(602, 325, 270, 19));
        kDPanel3.add(kDLabelContainer2, new KDLayout.Constraints(602, 325, 270, 19, 0));
        contProject.setBounds(new Rectangle(683, 28, 270, 19));
        kDPanel3.add(contProject, new KDLayout.Constraints(683, 28, 270, 19, 0));
        contTaskType.setBounds(new Rectangle(347, 28, 270, 19));
        kDPanel3.add(contTaskType, new KDLayout.Constraints(347, 28, 270, 19, 0));
        contRelatedTask.setBounds(new Rectangle(16, 28, 270, 19));
        kDPanel3.add(contRelatedTask, new KDLayout.Constraints(16, 28, 270, 19, 0));
        contPlanStartDate.setBounds(new Rectangle(16, 64, 270, 19));
        kDPanel3.add(contPlanStartDate, new KDLayout.Constraints(16, 64, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(347, 64, 270, 19));
        kDPanel3.add(kDLabelContainer3, new KDLayout.Constraints(347, 64, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtFinish);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDBizPromptBox1);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contTaskType
        contTaskType.setBoundEditor(txtTaskType);
        //contRelatedTask
        contRelatedTask.setBoundEditor(prmtRelatedTask);
        //contPlanStartDate
        contPlanStartDate.setBoundEditor(pkPlanStartDate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(pkPlanEndDate);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("planStartDate", java.util.Date.class, this.planBeginDate, "value");
		dataBinder.registerBinding("taskOrigin", com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum.class, this.comboTaskOrigin, "text");
		dataBinder.registerBinding("weightRate", java.math.BigDecimal.class, this.txtWeightRate, "value");
		dataBinder.registerBinding("projectPeriod", java.math.BigDecimal.class, this.txtProjectPeriod, "value");
		dataBinder.registerBinding("taskName", String.class, this.txtTaskName, "text");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtAdminPerson, "data");
		dataBinder.registerBinding("planFinishDate", java.util.Date.class, this.pkPlanFinishDate, "value");
		dataBinder.registerBinding("finishStandard", String.class, this.txtFinishStand, "text");
		dataBinder.registerBinding("requiredResource", String.class, this.txtRequiestesource, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.txtProject, "text");
		dataBinder.registerBinding("taskType", com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.class, this.txtTaskType, "text");
		dataBinder.registerBinding("relatedTask", com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo.class, this.prmtRelatedTask, "data");
		dataBinder.registerBinding("relatedTask.start", java.util.Date.class, this.pkPlanStartDate, "value");
		dataBinder.registerBinding("relatedTask.end", java.util.Date.class, this.pkPlanEndDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.DeptMonthlyScheduleTaskEditUIHandler";
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("planStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskOrigin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("weightRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finishStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("requiredResource", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.start", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relatedTask.end", ValidateHelper.ON_SAVE);    		
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
     * output planBeginDate_dataChanged method
     */
    protected void planBeginDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkPlanFinishDate_dataChanged method
     */
    protected void pkPlanFinishDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("planStartDate"));
        sic.add(new SelectorItemInfo("taskOrigin"));
        sic.add(new SelectorItemInfo("weightRate"));
        sic.add(new SelectorItemInfo("projectPeriod"));
        sic.add(new SelectorItemInfo("taskName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("adminPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("adminPerson.id"));
        	sic.add(new SelectorItemInfo("adminPerson.number"));
        	sic.add(new SelectorItemInfo("adminPerson.name"));
		}
        sic.add(new SelectorItemInfo("planFinishDate"));
        sic.add(new SelectorItemInfo("finishStandard"));
        sic.add(new SelectorItemInfo("requiredResource"));
        sic.add(new SelectorItemInfo("project"));
        sic.add(new SelectorItemInfo("taskType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("relatedTask.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("relatedTask.id"));
        	sic.add(new SelectorItemInfo("relatedTask.number"));
        	sic.add(new SelectorItemInfo("relatedTask.name"));
		}
        sic.add(new SelectorItemInfo("relatedTask.start"));
        sic.add(new SelectorItemInfo("relatedTask.end"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "DeptMonthlyScheduleTaskEditUI");
    }




}