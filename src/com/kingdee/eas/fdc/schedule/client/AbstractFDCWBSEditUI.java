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
public abstract class AbstractFDCWBSEditUI extends com.kingdee.eas.fdc.basedata.client.FDCTreeBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCWBSEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEstimateDays;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTaskType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBasicInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsLocked;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEstimateDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conParentLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conParentAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParentAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParentTaskType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParentAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conParentName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conParentNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contParentRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParentRespDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespDept;
    protected com.kingdee.eas.fdc.schedule.FDCWBSInfo editData = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionInsertLine actionInsertLine = null;
    protected ActionDeleteLine actionDeleteLine = null;
    /**
     * output class constructor
     */
    public AbstractFDCWBSEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCWBSEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsertLine
        this.actionInsertLine = new ActionInsertLine(this);
        getActionManager().registerAction("actionInsertLine", actionInsertLine);
         this.actionInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteLine
        this.actionDeleteLine = new ActionDeleteLine(this);
        getActionManager().registerAction("actionDeleteLine", actionDeleteLine);
         this.actionDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLevel = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEstimateDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtTaskType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlBasicInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsLocked = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contEstimateDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conParentLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conParentAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtParentNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtParentLevel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtParentAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtParentTaskType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtParentAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conParentName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtParentName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conParentNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.conTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contParentRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtParentRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber.setName("txtNumber");
        this.txtLevel.setName("txtLevel");
        this.prmtCurProject.setName("prmtCurProject");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.txtEstimateDays.setName("txtEstimateDays");
        this.prmtTaskType.setName("prmtTaskType");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.kdCreateTime.setName("kdCreateTime");
        this.kdLastUpdateTime.setName("kdLastUpdateTime");
        this.tabPanel.setName("tabPanel");
        this.pnlBasicInfo.setName("pnlBasicInfo");
        this.contNumber.setName("contNumber");
        this.contLevel.setName("contLevel");
        this.contCurProject.setName("contCurProject");
        this.chkIsLocked.setName("chkIsLocked");
        this.contEstimateDays.setName("contEstimateDays");
        this.contAdminPerson.setName("contAdminPerson");
        this.contName.setName("contName");
        this.conParentLevel.setName("conParentLevel");
        this.conParentAdminPerson.setName("conParentAdminPerson");
        this.txtParentNumber.setName("txtParentNumber");
        this.txtParentLevel.setName("txtParentLevel");
        this.prmtParentAdminPerson.setName("prmtParentAdminPerson");
        this.kDLabel1.setName("kDLabel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtParentTaskType.setName("prmtParentTaskType");
        this.prmtParentAdminDept.setName("prmtParentAdminDept");
        this.conParentName.setName("conParentName");
        this.txtParentName.setName("txtParentName");
        this.conParentNumber.setName("conParentNumber");
        this.txtName.setName("txtName");
        this.conTaskType.setName("conTaskType");
        this.contTaskType.setName("contTaskType");
        this.conAdminDept.setName("conAdminDept");
        this.contAdminDept.setName("contAdminDept");
        this.contParentRespDept.setName("contParentRespDept");
        this.contRespDept.setName("contRespDept");
        this.prmtParentRespDept.setName("prmtParentRespDept");
        this.prmtRespDept.setName("prmtRespDept");
        // CoreUI
        // txtNumber		
        this.txtNumber.setEnabled(false);
        // txtLevel		
        this.txtLevel.setEnabled(false);		
        this.txtLevel.setSupportedEmpty(true);
        // prmtCurProject		
        this.prmtCurProject.setEnabled(false);
        // prmtAdminPerson		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.F7PersonQuery");		
        this.prmtAdminPerson.setEditable(true);		
        this.prmtAdminPerson.setDisplayFormat("$name$");		
        this.prmtAdminPerson.setEditFormat("$number$");		
        this.prmtAdminPerson.setCommitFormat("$number$");
        this.prmtAdminPerson.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAdminPerson_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtEstimateDays		
        this.txtEstimateDays.setSupportedEmpty(true);
        // prmtTaskType		
        this.prmtTaskType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7TaskTypeQuery");		
        this.prmtTaskType.setEditable(true);
        this.prmtTaskType.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    prmtTaskType_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtTaskType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtTaskType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAdminDept		
        this.prmtAdminDept.setEditable(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kdCreateTime		
        this.kdCreateTime.setEnabled(false);
        // kdLastUpdateTime		
        this.kdLastUpdateTime.setEnabled(false);
        // tabPanel
        // pnlBasicInfo		
        this.pnlBasicInfo.setBorder(null);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contLevel		
        this.contLevel.setBoundLabelText(resHelper.getString("contLevel.boundLabelText"));		
        this.contLevel.setBoundLabelLength(100);		
        this.contLevel.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);		
        this.contCurProject.setEnabled(false);
        // chkIsLocked		
        this.chkIsLocked.setText(resHelper.getString("chkIsLocked.text"));		
        this.chkIsLocked.setVisible(false);
        // contEstimateDays		
        this.contEstimateDays.setBoundLabelText(resHelper.getString("contEstimateDays.boundLabelText"));		
        this.contEstimateDays.setBoundLabelLength(100);		
        this.contEstimateDays.setBoundLabelUnderline(true);
        // contAdminPerson		
        this.contAdminPerson.setBoundLabelText(resHelper.getString("contAdminPerson.boundLabelText"));		
        this.contAdminPerson.setBoundLabelLength(100);		
        this.contAdminPerson.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // conParentLevel		
        this.conParentLevel.setBoundLabelText(resHelper.getString("conParentLevel.boundLabelText"));		
        this.conParentLevel.setBoundLabelLength(100);		
        this.conParentLevel.setBoundLabelUnderline(true);
        // conParentAdminPerson		
        this.conParentAdminPerson.setBoundLabelText(resHelper.getString("conParentAdminPerson.boundLabelText"));		
        this.conParentAdminPerson.setBoundLabelLength(100);		
        this.conParentAdminPerson.setBoundLabelUnderline(true);
        // txtParentNumber		
        this.txtParentNumber.setEnabled(false);
        // txtParentLevel		
        this.txtParentLevel.setEnabled(false);
        // prmtParentAdminPerson		
        this.prmtParentAdminPerson.setEnabled(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDScrollPane1
        // txtDescription
        // prmtParentTaskType		
        this.prmtParentTaskType.setEnabled(false);
        // prmtParentAdminDept		
        this.prmtParentAdminDept.setEnabled(false);
        // conParentName		
        this.conParentName.setBoundLabelText(resHelper.getString("conParentName.boundLabelText"));		
        this.conParentName.setBoundLabelLength(100);		
        this.conParentName.setBoundLabelUnderline(true);
        // txtParentName		
        this.txtParentName.setEnabled(false);
        // conParentNumber		
        this.conParentNumber.setBoundLabelText(resHelper.getString("conParentNumber.boundLabelText"));		
        this.conParentNumber.setBoundLabelUnderline(true);		
        this.conParentNumber.setBoundLabelLength(100);
        // txtName		
        this.txtName.setRequired(true);
        // conTaskType		
        this.conTaskType.setBoundLabelText(resHelper.getString("conTaskType.boundLabelText"));		
        this.conTaskType.setBoundLabelLength(100);		
        this.conTaskType.setBoundLabelUnderline(true);
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(100);		
        this.contTaskType.setBoundLabelUnderline(true);
        // conAdminDept		
        this.conAdminDept.setBoundLabelText(resHelper.getString("conAdminDept.boundLabelText"));		
        this.conAdminDept.setBoundLabelLength(100);		
        this.conAdminDept.setBoundLabelUnderline(true);
        // contAdminDept		
        this.contAdminDept.setBoundLabelText(resHelper.getString("contAdminDept.boundLabelText"));		
        this.contAdminDept.setBoundLabelLength(100);		
        this.contAdminDept.setBoundLabelUnderline(true);
        // contParentRespDept		
        this.contParentRespDept.setBoundLabelText(resHelper.getString("contParentRespDept.boundLabelText"));		
        this.contParentRespDept.setBoundLabelLength(100);		
        this.contParentRespDept.setBoundLabelUnderline(true);
        // contRespDept		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);
        // prmtParentRespDept		
        this.prmtParentRespDept.setEnabled(false);
        // prmtRespDept		
        this.prmtRespDept.setEditable(true);
        this.prmtRespDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRespDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtEstimateDays,txtNumber,txtLevel,chkIsLocked,prmtAdminPerson,txtDescription}));
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
        this.setBounds(new Rectangle(10, 10, 720, 420));
        this.setLayout(null);
        contLastUpdateTime.setBounds(new Rectangle(430, 393, 270, 19));
        this.add(contLastUpdateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(430, 368, 270, 19));
        this.add(contLastUpdateUser, null);
        contCreateTime.setBounds(new Rectangle(20, 393, 270, 19));
        this.add(contCreateTime, null);
        contCreator.setBounds(new Rectangle(20, 368, 270, 19));
        this.add(contCreator, null);
        tabPanel.setBounds(new Rectangle(10, 9, 700, 350));
        this.add(tabPanel, null);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kdLastUpdateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contCreateTime
        contCreateTime.setBoundEditor(kdCreateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //tabPanel
        tabPanel.add(pnlBasicInfo, resHelper.getString("pnlBasicInfo.constraints"));
        //pnlBasicInfo
        pnlBasicInfo.setLayout(null);        contNumber.setBounds(new Rectangle(420, 35, 270, 19));
        pnlBasicInfo.add(contNumber, null);
        contLevel.setBounds(new Rectangle(420, 60, 270, 19));
        pnlBasicInfo.add(contLevel, null);
        contCurProject.setBounds(new Rectangle(10, 10, 270, 19));
        pnlBasicInfo.add(contCurProject, null);
        chkIsLocked.setBounds(new Rectangle(420, 210, 140, 19));
        pnlBasicInfo.add(chkIsLocked, null);
        contEstimateDays.setBounds(new Rectangle(420, 110, 270, 19));
        pnlBasicInfo.add(contEstimateDays, null);
        contAdminPerson.setBounds(new Rectangle(420, 85, 270, 19));
        pnlBasicInfo.add(contAdminPerson, null);
        contName.setBounds(new Rectangle(420, 10, 270, 19));
        pnlBasicInfo.add(contName, null);
        conParentLevel.setBounds(new Rectangle(10, 85, 270, 19));
        pnlBasicInfo.add(conParentLevel, null);
        conParentAdminPerson.setBounds(new Rectangle(10, 110, 270, 19));
        pnlBasicInfo.add(conParentAdminPerson, null);
        kDLabel1.setBounds(new Rectangle(10, 210, 100, 19));
        pnlBasicInfo.add(kDLabel1, null);
        kDScrollPane1.setBounds(new Rectangle(9, 236, 682, 77));
        pnlBasicInfo.add(kDScrollPane1, null);
        conParentName.setBounds(new Rectangle(10, 35, 270, 19));
        pnlBasicInfo.add(conParentName, null);
        conParentNumber.setBounds(new Rectangle(10, 60, 270, 19));
        pnlBasicInfo.add(conParentNumber, null);
        conTaskType.setBounds(new Rectangle(9, 135, 270, 19));
        pnlBasicInfo.add(conTaskType, null);
        contTaskType.setBounds(new Rectangle(419, 135, 270, 19));
        pnlBasicInfo.add(contTaskType, null);
        conAdminDept.setBounds(new Rectangle(10, 160, 270, 19));
        pnlBasicInfo.add(conAdminDept, null);
        contAdminDept.setBounds(new Rectangle(419, 160, 270, 19));
        pnlBasicInfo.add(contAdminDept, null);
        contParentRespDept.setBounds(new Rectangle(10, 185, 270, 19));
        pnlBasicInfo.add(contParentRespDept, null);
        contRespDept.setBounds(new Rectangle(420, 185, 270, 19));
        pnlBasicInfo.add(contRespDept, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contLevel
        contLevel.setBoundEditor(txtLevel);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contEstimateDays
        contEstimateDays.setBoundEditor(txtEstimateDays);
        //contAdminPerson
        contAdminPerson.setBoundEditor(prmtAdminPerson);
        //contName
        contName.setBoundEditor(txtName);
        //conParentLevel
        conParentLevel.setBoundEditor(txtParentLevel);
        //conParentAdminPerson
        conParentAdminPerson.setBoundEditor(prmtParentAdminPerson);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //conParentName
        conParentName.setBoundEditor(txtParentName);
        //conParentNumber
        conParentNumber.setBoundEditor(txtParentNumber);
        //conTaskType
        conTaskType.setBoundEditor(prmtParentTaskType);
        //contTaskType
        contTaskType.setBoundEditor(prmtTaskType);
        //conAdminDept
        conAdminDept.setBoundEditor(prmtParentAdminDept);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contParentRespDept
        contParentRespDept.setBoundEditor(prmtParentRespDept);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("level", int.class, this.txtLevel, "value");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtAdminPerson, "data");
		dataBinder.registerBinding("estimateDays", int.class, this.txtEstimateDays, "value");
		dataBinder.registerBinding("taskType", com.kingdee.eas.fdc.schedule.TaskTypeInfo.class, this.prmtTaskType, "data");
		dataBinder.registerBinding("adminDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtAdminDept, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kdCreateTime, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kdLastUpdateTime, "value");
		dataBinder.registerBinding("isLocked", boolean.class, this.chkIsLocked, "selected");
		dataBinder.registerBinding("parent.number", String.class, this.txtParentNumber, "text");
		dataBinder.registerBinding("parent.level", int.class, this.txtParentLevel, "text");
		dataBinder.registerBinding("parent.adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtParentAdminPerson, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("parent.taskType", com.kingdee.eas.fdc.schedule.TaskTypeInfo.class, this.prmtParentTaskType, "data");
		dataBinder.registerBinding("parent.adminDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtParentAdminDept, "data");
		dataBinder.registerBinding("parent.name", String.class, this.txtParentName, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("parent.respDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtParentRespDept, "data");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtRespDept, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FDCWBSEditUIHandler";
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
        this.txtEstimateDays.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.schedule.FDCWBSInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("estimateDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isLocked", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    		
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
     * output prmtAdminPerson_willShow method
     */
    protected void prmtAdminPerson_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtTaskType_stateChanged method
     */
    protected void prmtTaskType_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtTaskType_dataChanged method
     */
    protected void prmtTaskType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtRespDept_dataChanged method
     */
    protected void prmtRespDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("adminPerson.*"));
        sic.add(new SelectorItemInfo("estimateDays"));
        sic.add(new SelectorItemInfo("taskType.*"));
        sic.add(new SelectorItemInfo("adminDept.*"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("isLocked"));
        sic.add(new SelectorItemInfo("parent.number"));
        sic.add(new SelectorItemInfo("parent.level"));
        sic.add(new SelectorItemInfo("parent.adminPerson"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("parent.taskType"));
        sic.add(new SelectorItemInfo("parent.adminDept"));
        sic.add(new SelectorItemInfo("parent.name"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("parent.respDept"));
        sic.add(new SelectorItemInfo("respDept.*"));
        return sic;
    }        
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInsertLine_actionPerformed method
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteLine_actionPerformed method
     */
    public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsertLine() {
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
            innerActionPerformed("eas", AbstractFDCWBSEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionInsertLine class
     */     
    protected class ActionInsertLine extends ItemAction {     
    
        public ActionInsertLine()
        {
            this(null);
        }

        public ActionInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSEditUI.this, "ActionInsertLine", "actionInsertLine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCWBSEditUI.this, "ActionDeleteLine", "actionDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FDCWBSEditUI");
    }




}