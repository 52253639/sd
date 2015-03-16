/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractMovementPlanEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMovementPlanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker beginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDPanel charge;
    protected com.kingdee.bos.ctrl.swing.KDPanel custom;
    protected com.kingdee.bos.ctrl.swing.KDPanel project;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE4;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE4_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE3;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE3_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDComboBox belongSystem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField totalMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker endDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSelect;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox mmType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDTextField orgName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox sellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDComboBox schemeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDTextField movementTheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.swing.KDTextField discountExp;
    protected com.kingdee.bos.ctrl.swing.KDPanel effect;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE5;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE5_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDPanel media;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE6;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE6_detailPanel = null;
    protected com.kingdee.eas.fdc.market.MovementPlanInfo editData = null;
    protected ActionDelCustomer actionDelCustomer = null;
    protected ActionAddCustomer actionAddCustomer = null;
    /**
     * output class constructor
     */
    public AbstractMovementPlanEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMovementPlanEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelCustomer
        this.actionDelCustomer = new ActionDelCustomer(this);
        getActionManager().registerAction("actionDelCustomer", actionDelCustomer);
         this.actionDelCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddCustomer
        this.actionAddCustomer = new ActionAddCustomer(this);
        getActionManager().registerAction("actionAddCustomer", actionAddCustomer);
         this.actionAddCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.beginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.paneBIZLayerControl20 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.charge = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.custom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.project = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtE4 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.belongSystem = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.totalMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.endDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.isSelect = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.mmType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.orgName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.sellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.schemeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.movementTheme = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.discountExp = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.effect = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtE5 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.media = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtE6 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.beginDate.setName("beginDate");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.dateLastUpdateTime.setName("dateLastUpdateTime");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.dateCreateTime.setName("dateCreateTime");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.bizPromptLastUpdateUser.setName("bizPromptLastUpdateUser");
        this.paneBIZLayerControl20.setName("paneBIZLayerControl20");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtName.setName("txtName");
        this.charge.setName("charge");
        this.custom.setName("custom");
        this.project.setName("project");
        this.kdtE4.setName("kdtE4");
        this.kdtE3.setName("kdtE3");
        this.kdtE2.setName("kdtE2");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.belongSystem.setName("belongSystem");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.totalMoney.setName("totalMoney");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.endDate.setName("endDate");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.txtDescription.setName("txtDescription");
        this.isSelect.setName("isSelect");
        this.mmType.setName("mmType");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.orgName.setName("orgName");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.sellProject.setName("sellProject");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.schemeType.setName("schemeType");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.movementTheme.setName("movementTheme");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.discountExp.setName("discountExp");
        this.effect.setName("effect");
        this.kdtE5.setName("kdtE5");
        this.media.setName("media");
        this.kdtE6.setName("kdtE6");
        // CoreUI
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(false);		
        this.txtNumber.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // beginDate		
        this.beginDate.setVisible(true);		
        this.beginDate.setEnabled(true);		
        this.beginDate.setRequired(false);		
        ((com.kingdee.bos.ctrl.swing.BasicFormattedTextField)this.beginDate.getEditor().getEditorComponent()).setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setVisible(false);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setEnabled(false);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);
        // dateLastUpdateTime		
        this.dateLastUpdateTime.setEnabled(false);		
        this.dateLastUpdateTime.setVisible(false);		
        this.dateLastUpdateTime.setRequired(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);		
        this.dateCreateTime.setRequired(false);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.setVisible(true);
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setVisible(true);		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setCommitFormat("$number$");		
        this.bizPromptAuditor.setRequired(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelAlignment(7);		
        this.kDLabelContainer7.setVisible(true);
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setRequired(false);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setVisible(false);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelAlignment(7);
        // bizPromptLastUpdateUser		
        this.bizPromptLastUpdateUser.setEnabled(false);		
        this.bizPromptLastUpdateUser.setVisible(false);		
        this.bizPromptLastUpdateUser.setEditable(true);		
        this.bizPromptLastUpdateUser.setDisplayFormat("$name$");		
        this.bizPromptLastUpdateUser.setEditFormat("$number$");		
        this.bizPromptLastUpdateUser.setCommitFormat("$number$");		
        this.bizPromptLastUpdateUser.setRequired(false);
        // paneBIZLayerControl20		
        this.paneBIZLayerControl20.setVisible(true);
        this.paneBIZLayerControl20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    paneBIZLayerControl20_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setVisible(true);		
        this.txtName.setEnabled(true);		
        this.txtName.setHorizontalAlignment(2);		
        this.txtName.setRequired(false);		
        this.txtName.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // charge		
        this.charge.setVisible(true);		
        this.charge.setEnabled(false);
        // custom		
        this.custom.setVisible(true);
        // project		
        this.project.setVisible(true);
        // kdtE4		
        this.kdtE4.setFormatXml(resHelper.getString("kdtE4.formatXml"));

                this.kdtE4.putBindContents("editData",new String[] {"startTime","endTime","activeTime","content","responsiblePers","persons"});


        this.kdtE4.checkParsed();
        KDDatePicker kdtE4_startTime_DatePicker = new KDDatePicker();
        kdtE4_startTime_DatePicker.setName("kdtE4_startTime_DatePicker");
        kdtE4_startTime_DatePicker.setVisible(true);
        kdtE4_startTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE4_startTime_CellEditor = new KDTDefaultCellEditor(kdtE4_startTime_DatePicker);
        this.kdtE4.getColumn("startTime").setEditor(kdtE4_startTime_CellEditor);
        KDDatePicker kdtE4_endTime_DatePicker = new KDDatePicker();
        kdtE4_endTime_DatePicker.setName("kdtE4_endTime_DatePicker");
        kdtE4_endTime_DatePicker.setVisible(true);
        kdtE4_endTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE4_endTime_CellEditor = new KDTDefaultCellEditor(kdtE4_endTime_DatePicker);
        this.kdtE4.getColumn("endTime").setEditor(kdtE4_endTime_CellEditor);
        KDTextField kdtE4_activeTime_TextField = new KDTextField();
        kdtE4_activeTime_TextField.setName("kdtE4_activeTime_TextField");
        kdtE4_activeTime_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE4_activeTime_CellEditor = new KDTDefaultCellEditor(kdtE4_activeTime_TextField);
        this.kdtE4.getColumn("activeTime").setEditor(kdtE4_activeTime_CellEditor);
        KDTextField kdtE4_content_TextField = new KDTextField();
        kdtE4_content_TextField.setName("kdtE4_content_TextField");
        kdtE4_content_TextField.setMaxLength(500);
        KDTDefaultCellEditor kdtE4_content_CellEditor = new KDTDefaultCellEditor(kdtE4_content_TextField);
        this.kdtE4.getColumn("content").setEditor(kdtE4_content_CellEditor);
        final KDBizPromptBox kdtE4_responsiblePers_PromptBox = new KDBizPromptBox();
        kdtE4_responsiblePers_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtE4_responsiblePers_PromptBox.setVisible(true);
        kdtE4_responsiblePers_PromptBox.setEditable(true);
        kdtE4_responsiblePers_PromptBox.setDisplayFormat("$number$");
        kdtE4_responsiblePers_PromptBox.setEditFormat("$number$");
        kdtE4_responsiblePers_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE4_responsiblePers_CellEditor = new KDTDefaultCellEditor(kdtE4_responsiblePers_PromptBox);
        this.kdtE4.getColumn("responsiblePers").setEditor(kdtE4_responsiblePers_CellEditor);
        ObjectValueRender kdtE4_responsiblePers_OVR = new ObjectValueRender();
        kdtE4_responsiblePers_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE4.getColumn("responsiblePers").setRenderer(kdtE4_responsiblePers_OVR);
        KDTextField kdtE4_persons_TextField = new KDTextField();
        kdtE4_persons_TextField.setName("kdtE4_persons_TextField");
        kdtE4_persons_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE4_persons_CellEditor = new KDTDefaultCellEditor(kdtE4_persons_TextField);
        this.kdtE4.getColumn("persons").setEditor(kdtE4_persons_CellEditor);
        // kdtE3		
        this.kdtE3.setFormatXml(resHelper.getString("kdtE3.formatXml"));

                this.kdtE3.putBindContents("editData",new String[] {"fdcCustomer.name","fdcCustomer.phone","fdcCustomer.email","fdcCustomer.sex","fdcCustomer.mailAddress","fdcCustomer","fdcCustomer.number"});


        this.kdtE3.checkParsed();
        KDTextField kdtE3_phone_TextField = new KDTextField();
        kdtE3_phone_TextField.setName("kdtE3_phone_TextField");
        kdtE3_phone_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE3_phone_CellEditor = new KDTDefaultCellEditor(kdtE3_phone_TextField);
        this.kdtE3.getColumn("phone").setEditor(kdtE3_phone_CellEditor);
        KDTextField kdtE3_email_TextField = new KDTextField();
        kdtE3_email_TextField.setName("kdtE3_email_TextField");
        kdtE3_email_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE3_email_CellEditor = new KDTDefaultCellEditor(kdtE3_email_TextField);
        this.kdtE3.getColumn("email").setEditor(kdtE3_email_CellEditor);
        KDComboBox kdtE3_sex_ComboBox = new KDComboBox();
        kdtE3_sex_ComboBox.setName("kdtE3_sex_ComboBox");
        kdtE3_sex_ComboBox.setVisible(true);
        kdtE3_sex_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());
        KDTDefaultCellEditor kdtE3_sex_CellEditor = new KDTDefaultCellEditor(kdtE3_sex_ComboBox);
        this.kdtE3.getColumn("sex").setEditor(kdtE3_sex_CellEditor);
        KDTextField kdtE3_address_TextField = new KDTextField();
        kdtE3_address_TextField.setName("kdtE3_address_TextField");
        kdtE3_address_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE3_address_CellEditor = new KDTDefaultCellEditor(kdtE3_address_TextField);
        this.kdtE3.getColumn("address").setEditor(kdtE3_address_CellEditor);
        // kdtE2		
        this.kdtE2.setFormatXml(resHelper.getString("kdtE2.formatXml"));
        kdtE2.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtE2_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.kdtE2.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtE2_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtE2.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtE2_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtE2.putBindContents("editData",new String[] {"chargeType","chargeParent","money"});


        this.kdtE2.checkParsed();
        final KDBizPromptBox kdtE2_chargeType_PromptBox = new KDBizPromptBox();
        kdtE2_chargeType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.scm.common.app.ExpenseItemQuery");
        kdtE2_chargeType_PromptBox.setVisible(true);
        kdtE2_chargeType_PromptBox.setEditable(true);
        kdtE2_chargeType_PromptBox.setDisplayFormat("$number$");
        kdtE2_chargeType_PromptBox.setEditFormat("$number$");
        kdtE2_chargeType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE2_chargeType_CellEditor = new KDTDefaultCellEditor(kdtE2_chargeType_PromptBox);
        this.kdtE2.getColumn("chargeType").setEditor(kdtE2_chargeType_CellEditor);
        ObjectValueRender kdtE2_chargeType_OVR = new ObjectValueRender();
        kdtE2_chargeType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE2.getColumn("chargeType").setRenderer(kdtE2_chargeType_OVR);
        KDTextField kdtE2_chargeParent_TextField = new KDTextField();
        kdtE2_chargeParent_TextField.setName("kdtE2_chargeParent_TextField");
        kdtE2_chargeParent_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE2_chargeParent_CellEditor = new KDTDefaultCellEditor(kdtE2_chargeParent_TextField);
        this.kdtE2.getColumn("chargeParent").setEditor(kdtE2_chargeParent_CellEditor);
        KDFormattedTextField kdtE2_money_TextField = new KDFormattedTextField();
        kdtE2_money_TextField.setName("kdtE2_money_TextField");
        kdtE2_money_TextField.setVisible(true);
        kdtE2_money_TextField.setEditable(true);
        kdtE2_money_TextField.setHorizontalAlignment(2);
        kdtE2_money_TextField.setDataType(1);
        	kdtE2_money_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE2_money_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE2_money_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE2_money_CellEditor = new KDTDefaultCellEditor(kdtE2_money_TextField);
        this.kdtE2.getColumn("money").setEditor(kdtE2_money_CellEditor);
        // kDWorkButton1
        this.kDWorkButton1.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));
        // kDWorkButton2
        this.kDWorkButton2.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelCustomer, new Class[] { IItemAction.class }, getServiceContext()));
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setVisible(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // belongSystem		
        this.belongSystem.setVisible(true);		
        this.belongSystem.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());		
        this.belongSystem.setRequired(false);		
        this.belongSystem.setForeground(new java.awt.Color(0,0,0));		
        this.belongSystem.setEnabled(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);		
        this.kDLabelContainer6.setVisible(true);		
        this.kDLabelContainer6.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setVisible(true);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);		
        this.kDLabelContainer11.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // totalMoney		
        this.totalMoney.setVisible(true);		
        this.totalMoney.setDataType(1);		
        this.totalMoney.setSupportedEmpty(true);		
        this.totalMoney.setRequired(false);		
        this.totalMoney.setEnabled(true);		
        this.totalMoney.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.totalMoney.setPrecision(2);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);		
        this.kDLabelContainer14.setBoundLabelAlignment(7);		
        this.kDLabelContainer14.setVisible(true);		
        this.kDLabelContainer14.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // endDate		
        this.endDate.setVisible(true);		
        this.endDate.setEnabled(true);		
        this.endDate.setRequired(false);		
        ((com.kingdee.bos.ctrl.swing.BasicFormattedTextField)this.endDate.getEditor().getEditorComponent()).setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setVisible(true);		
        this.kDLabelContainer15.setBoundLabelAlignment(7);		
        this.kDLabelContainer15.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtDescription		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setRequired(false);		
        this.txtDescription.setMaxLength(255);
        // isSelect		
        this.isSelect.setText(resHelper.getString("isSelect.text"));		
        this.isSelect.setVisible(true);		
        this.isSelect.setEnabled(true);		
        this.isSelect.setHorizontalAlignment(2);		
        this.isSelect.setForeground(new java.awt.Color(0,0,0));
        this.isSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // mmType		
        this.mmType.setQueryInfo("com.kingdee.eas.fdc.market.app.MarketTypeQuery");		
        this.mmType.setDisplayFormat("$name$");		
        this.mmType.setEditFormat("$number$");		
        this.mmType.setCommitFormat("$number$");		
        this.mmType.setVisible(true);		
        this.mmType.setEnabled(true);		
        this.mmType.setRequired(false);		
        this.mmType.setForeground(new java.awt.Color(0,0,0));
        		mmType.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.market.client.MarketTypeListUI mmType_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (mmType_F7ListUI == null) {
					try {
						mmType_F7ListUI = new com.kingdee.eas.fdc.market.client.MarketTypeListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(mmType_F7ListUI));
					mmType_F7ListUI.setF7Use(true,ctx);
					mmType.setSelector(mmType_F7ListUI);
				}
			}
		});
					
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);		
        this.kDLabelContainer18.setBoundLabelAlignment(7);		
        this.kDLabelContainer18.setVisible(true);		
        this.kDLabelContainer18.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // orgName		
        this.orgName.setMaxLength(80);		
        this.orgName.setVisible(true);		
        this.orgName.setEnabled(false);		
        this.orgName.setHorizontalAlignment(2);		
        this.orgName.setRequired(false);		
        this.orgName.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setVisible(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // sellProject		
        this.sellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.sellProject.setDisplayFormat("$name$");		
        this.sellProject.setEditFormat("$number$");		
        this.sellProject.setCommitFormat("$number$");		
        this.sellProject.setVisible(true);		
        this.sellProject.setEnabled(false);		
        this.sellProject.setRequired(false);		
        this.sellProject.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);		
        this.kDLabelContainer19.setVisible(true);		
        this.kDLabelContainer19.setBoundLabelAlignment(7);		
        this.kDLabelContainer19.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // schemeType		
        this.schemeType.setVisible(true);		
        this.schemeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.SchemeTypeEnum").toArray());		
        this.schemeType.setRequired(false);		
        this.schemeType.setForeground(new java.awt.Color(0,0,0));		
        this.schemeType.setEnabled(true);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);		
        this.kDLabelContainer20.setBoundLabelAlignment(7);		
        this.kDLabelContainer20.setVisible(true);		
        this.kDLabelContainer20.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // movementTheme		
        this.movementTheme.setMaxLength(80);		
        this.movementTheme.setVisible(true);		
        this.movementTheme.setEnabled(true);		
        this.movementTheme.setHorizontalAlignment(2);		
        this.movementTheme.setRequired(false);		
        this.movementTheme.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);		
        this.kDLabelContainer21.setBoundLabelAlignment(7);		
        this.kDLabelContainer21.setVisible(true);		
        this.kDLabelContainer21.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // discountExp		
        this.discountExp.setMaxLength(80);		
        this.discountExp.setVisible(true);		
        this.discountExp.setEnabled(true);		
        this.discountExp.setHorizontalAlignment(2);		
        this.discountExp.setRequired(false);		
        this.discountExp.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // effect		
        this.effect.setVisible(true);
        // kdtE5		
        this.kdtE5.setFormatXml(resHelper.getString("kdtE5.formatXml"));

                this.kdtE5.putBindContents("editData",new String[] {"predictName","predictValue"});


        this.kdtE5.checkParsed();
        KDTextField kdtE5_predictName_TextField = new KDTextField();
        kdtE5_predictName_TextField.setName("kdtE5_predictName_TextField");
        kdtE5_predictName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE5_predictName_CellEditor = new KDTDefaultCellEditor(kdtE5_predictName_TextField);
        this.kdtE5.getColumn("predictName").setEditor(kdtE5_predictName_CellEditor);
        KDFormattedTextField kdtE5_predictValue_TextField = new KDFormattedTextField();
        kdtE5_predictValue_TextField.setName("kdtE5_predictValue_TextField");
        kdtE5_predictValue_TextField.setVisible(true);
        kdtE5_predictValue_TextField.setEditable(true);
        kdtE5_predictValue_TextField.setHorizontalAlignment(2);
        kdtE5_predictValue_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE5_predictValue_CellEditor = new KDTDefaultCellEditor(kdtE5_predictValue_TextField);
        this.kdtE5.getColumn("predictValue").setEditor(kdtE5_predictValue_CellEditor);
        // media		
        this.media.setVisible(true);
        // kdtE6		
        this.kdtE6.setFormatXml(resHelper.getString("kdtE6.formatXml"));
        this.kdtE6.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtE6_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtE6.putBindContents("editData",new String[] {"mediaType","mediaType.mediaType","mediaType.contractMan","mediaType.preferentialMsg"});


        this.kdtE6.checkParsed();
        final KDBizPromptBox kdtE6_mediaType_PromptBox = new KDBizPromptBox();
        kdtE6_mediaType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.MediaQuery");
        kdtE6_mediaType_PromptBox.setVisible(true);
        kdtE6_mediaType_PromptBox.setEditable(true);
        kdtE6_mediaType_PromptBox.setDisplayFormat("$number$");
        kdtE6_mediaType_PromptBox.setEditFormat("$number$");
        kdtE6_mediaType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE6_mediaType_CellEditor = new KDTDefaultCellEditor(kdtE6_mediaType_PromptBox);
        this.kdtE6.getColumn("mediaType").setEditor(kdtE6_mediaType_CellEditor);
        ObjectValueRender kdtE6_mediaType_OVR = new ObjectValueRender();
        kdtE6_mediaType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE6.getColumn("mediaType").setRenderer(kdtE6_mediaType_OVR);
        KDTextField kdtE6_prometionInfo_TextField = new KDTextField();
        kdtE6_prometionInfo_TextField.setName("kdtE6_prometionInfo_TextField");
        kdtE6_prometionInfo_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE6_prometionInfo_CellEditor = new KDTDefaultCellEditor(kdtE6_prometionInfo_TextField);
        this.kdtE6.getColumn("prometionInfo").setEditor(kdtE6_prometionInfo_CellEditor);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtName,movementTheme,orgName,sellProject,schemeType,mmType,discountExp,belongSystem,beginDate,endDate,totalMoney,isSelect,txtDescription,kdtE2,kDWorkButton1,kDWorkButton2,kdtE3,kdtE4,kdtE5,kdtE6,bizPromptCreator,bizPromptAuditor,dateCreateTime,bizPromptLastUpdateUser,dateLastUpdateTime}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(10, 85, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(10, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(730, 579, 224, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(730, 579, 224, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(730, 550, 224, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(730, 550, 224, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(370, 550, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(370, 550, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(10, 550, 225, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 550, 225, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(10, 579, 225, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 579, 225, 19, 0));
        paneBIZLayerControl20.setBounds(new Rectangle(10, 200, 990, 340));
        this.add(paneBIZLayerControl20, new KDLayout.Constraints(10, 200, 990, 340, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(370, 10, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(370, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(730, 60, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(730, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer6.setBounds(new Rectangle(10, 135, 99, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(10, 135, 99, 19, 0));
        kDLabelContainer11.setBounds(new Rectangle(730, 85, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(730, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer14.setBounds(new Rectangle(370, 85, 270, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(370, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(10, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtDescription.setBounds(new Rectangle(109, 135, 891, 48));
        this.add(txtDescription, new KDLayout.Constraints(109, 135, 891, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        isSelect.setBounds(new Rectangle(730, 110, 99, 19));
        this.add(isSelect, new KDLayout.Constraints(730, 110, 99, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer18.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(10, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(370, 35, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(370, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer19.setBounds(new Rectangle(730, 35, 270, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(730, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer20.setBounds(new Rectangle(730, 10, 270, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(730, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer21.setBounds(new Rectangle(370, 60, 270, 19));
        this.add(kDLabelContainer21, new KDLayout.Constraints(370, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(beginDate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(dateLastUpdateTime);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dateCreateTime);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptCreator);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptLastUpdateUser);
        //paneBIZLayerControl20
        paneBIZLayerControl20.add(charge, resHelper.getString("charge.constraints"));
        paneBIZLayerControl20.add(custom, resHelper.getString("custom.constraints"));
        paneBIZLayerControl20.add(project, resHelper.getString("project.constraints"));
        paneBIZLayerControl20.add(effect, resHelper.getString("effect.constraints"));
        paneBIZLayerControl20.add(media, resHelper.getString("media.constraints"));
        //charge
charge.setLayout(new BorderLayout(0, 0));        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.fdc.market.MovementPlanE2Info(),null,false);
        charge.add(kdtE2_detailPanel, BorderLayout.CENTER);
        //custom
        custom.setLayout(null);        kdtE3.setBounds(new Rectangle(0, 60, 990, 400));
        kdtE3_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE3,new com.kingdee.eas.fdc.market.MovementPlanE3Info(),null,false);
        custom.add(kdtE3_detailPanel, null);
        kDWorkButton1.setBounds(new Rectangle(876, 5, 40, 25));
        custom.add(kDWorkButton1, null);
        kDWorkButton2.setBounds(new Rectangle(926, 5, 40, 25));
        custom.add(kDWorkButton2, null);
        //project
project.setLayout(new BorderLayout(0, 0));        kdtE4_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE4,new com.kingdee.eas.fdc.market.MovementPlanE4Info(),null,false);
        project.add(kdtE4_detailPanel, BorderLayout.CENTER);
        //effect
effect.setLayout(new BorderLayout(0, 0));        kdtE5_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE5,new com.kingdee.eas.fdc.market.MovementPlanE5Info(),null,false);
        effect.add(kdtE5_detailPanel, BorderLayout.CENTER);
        //media
media.setLayout(new BorderLayout(0, 0));        kdtE6_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE6,new com.kingdee.eas.fdc.market.MovementPlanE6Info(),null,false);
        media.add(kdtE6_detailPanel, BorderLayout.CENTER);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(belongSystem);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(totalMoney);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(endDate);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(mmType);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(orgName);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(sellProject);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(schemeType);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(movementTheme);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(discountExp);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("beginDate", java.util.Date.class, this.beginDate, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.dateLastUpdateTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptLastUpdateUser, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("E4.responsiblePers", java.lang.Object.class, this.kdtE4, "responsiblePers.text");
		dataBinder.registerBinding("E4", com.kingdee.eas.fdc.market.MovementPlanE4Info.class, this.kdtE4, "userObject");
		dataBinder.registerBinding("E4.persons", String.class, this.kdtE4, "persons.text");
		dataBinder.registerBinding("E4.startTime", java.util.Date.class, this.kdtE4, "startTime.text");
		dataBinder.registerBinding("E4.endTime", java.util.Date.class, this.kdtE4, "endTime.text");
		dataBinder.registerBinding("E4.activeTime", String.class, this.kdtE4, "activeTime.text");
		dataBinder.registerBinding("E4.content", String.class, this.kdtE4, "content.text");
		dataBinder.registerBinding("E3.fdcCustomer", com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo.class, this.kdtE3, "customerId.text");
		dataBinder.registerBinding("E3", com.kingdee.eas.fdc.market.MovementPlanE3Info.class, this.kdtE3, "userObject");
		dataBinder.registerBinding("E3.fdcCustomer.number", String.class, this.kdtE3, "number.text");
		dataBinder.registerBinding("E3.fdcCustomer.phone", String.class, this.kdtE3, "phone.text");
		dataBinder.registerBinding("E3.fdcCustomer.name", String.class, this.kdtE3, "name.text");
		dataBinder.registerBinding("E3.fdcCustomer.email", String.class, this.kdtE3, "email.text");
		dataBinder.registerBinding("E3.fdcCustomer.sex", com.kingdee.eas.fdc.sellhouse.SexEnum.class, this.kdtE3, "sex.text");
		dataBinder.registerBinding("E3.fdcCustomer.mailAddress", String.class, this.kdtE3, "address.text");
		dataBinder.registerBinding("E2.money", java.math.BigDecimal.class, this.kdtE2, "money.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.fdc.market.MovementPlanE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.chargeParent", String.class, this.kdtE2, "chargeParent.text");
		dataBinder.registerBinding("E2.chargeType", com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo.class, this.kdtE2, "chargeType.text");
		dataBinder.registerBinding("belongSystem", com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.class, this.belongSystem, "selectedItem");
		dataBinder.registerBinding("totalMoney", java.math.BigDecimal.class, this.totalMoney, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.endDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("isEnable", boolean.class, this.isSelect, "selected");
		dataBinder.registerBinding("mmType", com.kingdee.eas.fdc.market.MarketTypeInfo.class, this.mmType, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.sellProject, "data");
		dataBinder.registerBinding("schemeType", com.kingdee.eas.fdc.market.SchemeTypeEnum.class, this.schemeType, "selectedItem");
		dataBinder.registerBinding("movementTheme", String.class, this.movementTheme, "text");
		dataBinder.registerBinding("discountExp", String.class, this.discountExp, "text");
		dataBinder.registerBinding("E5.predictName", String.class, this.kdtE5, "predictName.text");
		dataBinder.registerBinding("E5", com.kingdee.eas.fdc.market.MovementPlanE5Info.class, this.kdtE5, "userObject");
		dataBinder.registerBinding("E5.predictValue", int.class, this.kdtE5, "predictValue.text");
		dataBinder.registerBinding("E6", com.kingdee.eas.fdc.market.MovementPlanE6Info.class, this.kdtE6, "userObject");
		dataBinder.registerBinding("E6.mediaType", java.lang.Object.class, this.kdtE6, "mediaType.text");
		dataBinder.registerBinding("E6.mediaType.mediaType", com.kingdee.eas.fdc.market.MediaTypeInfo.class, this.kdtE6, "mediaCategory.text");
		dataBinder.registerBinding("E6.mediaType.contractMan", String.class, this.kdtE6, "contractMan.text");
		dataBinder.registerBinding("E6.mediaType.preferentialMsg", String.class, this.kdtE6, "prometionInfo.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MovementPlanEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.MovementPlanInfo)ov;
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
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beginDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.responsiblePers", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.persons", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.startTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.endTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.activeTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.content", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer.email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer.sex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.fdcCustomer.mailAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.money", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.chargeParent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.chargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("belongSystem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEnable", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mmType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("schemeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("movementTheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("discountExp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E5.predictName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E5", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E5.predictValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.mediaType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.mediaType.mediaType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.mediaType.contractMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.mediaType.preferentialMsg", ValidateHelper.ON_SAVE);    		
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
     * output paneBIZLayerControl20_focusLost method
     */
    protected void paneBIZLayerControl20_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kdtE2_editStopped method
     */
    protected void kdtE2_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtE2_tableClicked method
     */
    protected void kdtE2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output isSelect_actionPerformed method
     */
    protected void isSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtE6_editStopped method
     */
    protected void kdtE6_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }


    /**
     * output txtNumber_validate() method
     */
    public void txtNumber_validate() throws Exception
    {
            if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) 
{
    com.kingdee.eas.util.client.MsgBox.showInfo(this,"编号不能为空!");com.kingdee.eas.util.SysUtil.abort();
}


    }

    /**
     * output txtName_validate() method
     */
    public void txtName_validate() throws Exception
    {
            if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtName.getText())) 
{
    com.kingdee.eas.util.client.MsgBox.showInfo(this,"活动名称不能为空!");com.kingdee.eas.util.SysUtil.abort();
}


    }

    /**
     * output kdtE2_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE2_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("chargeType".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"chargeParent").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"chargeType").getValue(),"parent.name")));

}


    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("beginDate"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("E4.responsiblePers.*"));
//        sic.add(new SelectorItemInfo("E4.responsiblePers.number"));
        sic.add(new SelectorItemInfo("E4.*"));
//        sic.add(new SelectorItemInfo("E4.number"));
    sic.add(new SelectorItemInfo("E4.persons"));
    sic.add(new SelectorItemInfo("E4.startTime"));
    sic.add(new SelectorItemInfo("E4.endTime"));
    sic.add(new SelectorItemInfo("E4.activeTime"));
    sic.add(new SelectorItemInfo("E4.content"));
        sic.add(new SelectorItemInfo("E3.fdcCustomer.*"));
//        sic.add(new SelectorItemInfo("E3.fdcCustomer.number"));
        sic.add(new SelectorItemInfo("E3.*"));
//        sic.add(new SelectorItemInfo("E3.number"));
    sic.add(new SelectorItemInfo("E3.fdcCustomer.number"));
    sic.add(new SelectorItemInfo("E3.fdcCustomer.phone"));
    sic.add(new SelectorItemInfo("E3.fdcCustomer.name"));
    sic.add(new SelectorItemInfo("E3.fdcCustomer.email"));
    sic.add(new SelectorItemInfo("E3.fdcCustomer.sex"));
    sic.add(new SelectorItemInfo("E3.fdcCustomer.mailAddress"));
    sic.add(new SelectorItemInfo("E2.money"));
        sic.add(new SelectorItemInfo("E2.*"));
//        sic.add(new SelectorItemInfo("E2.number"));
    sic.add(new SelectorItemInfo("E2.chargeParent"));
        sic.add(new SelectorItemInfo("E2.chargeType.*"));
//        sic.add(new SelectorItemInfo("E2.chargeType.number"));
        sic.add(new SelectorItemInfo("belongSystem"));
        sic.add(new SelectorItemInfo("totalMoney"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isEnable"));
        sic.add(new SelectorItemInfo("mmType.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("schemeType"));
        sic.add(new SelectorItemInfo("movementTheme"));
        sic.add(new SelectorItemInfo("discountExp"));
    sic.add(new SelectorItemInfo("E5.predictName"));
        sic.add(new SelectorItemInfo("E5.*"));
//        sic.add(new SelectorItemInfo("E5.number"));
    sic.add(new SelectorItemInfo("E5.predictValue"));
        sic.add(new SelectorItemInfo("E6.*"));
//        sic.add(new SelectorItemInfo("E6.number"));
        sic.add(new SelectorItemInfo("E6.mediaType.*"));
//        sic.add(new SelectorItemInfo("E6.mediaType.number"));
        sic.add(new SelectorItemInfo("E6.mediaType.mediaType.*"));
//        sic.add(new SelectorItemInfo("E6.mediaType.mediaType.number"));
    sic.add(new SelectorItemInfo("E6.mediaType.contractMan"));
    sic.add(new SelectorItemInfo("E6.mediaType.preferentialMsg"));
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
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionDelCustomer_actionPerformed method
     */
    public void actionDelCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddCustomer_actionPerformed method
     */
    public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionDelCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelCustomer() {
    	return false;
    }
	public RequestContext prepareActionAddCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCustomer() {
    	return false;
    }

    /**
     * output ActionDelCustomer class
     */
    protected class ActionDelCustomer extends ItemAction
    {
        public ActionDelCustomer()
        {
            this(null);
        }

        public ActionDelCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMovementPlanEditUI.this, "ActionDelCustomer", "actionDelCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionAddCustomer class
     */
    protected class ActionAddCustomer extends ItemAction
    {
        public ActionAddCustomer()
        {
            this(null);
        }

        public ActionAddCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMovementPlanEditUI.this, "ActionAddCustomer", "actionAddCustomer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MovementPlanEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }
    /**
     * output verifyInput method
     */
    protected void verifyInput(ActionEvent e) throws Exception
    {
        super.verifyInput(e);
        txtNumber_validate();
        txtName_validate();

    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.MovementPlanEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.MovementPlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.MovementPlanInfo objectValue = new com.kingdee.eas.fdc.market.MovementPlanInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/market/MovementPlan";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.market.app.MovementPlanQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtE2;
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