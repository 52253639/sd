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
public abstract class AbstractMarketSurveyEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSurveyEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsurveyName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsurveyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprovince;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprovince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contarea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsurveyPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsurveyPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsurveyMonth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox surveyMonth;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsurveyYear;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kdRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer region;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRegion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSurveyYear;
    protected com.kingdee.eas.fdc.market.MarketSurveyInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMarketSurveyEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketSurveyEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contsurveyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtsurveyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contprovince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtprovince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtarea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contsurveyPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtsurveyPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contsurveyMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.surveyMonth = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contsurveyYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.region = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRegion = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSurveyYear = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.pkBizDate.setName("pkBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.contsurveyName.setName("contsurveyName");
        this.txtsurveyName.setName("txtsurveyName");
        this.contprovince.setName("contprovince");
        this.prmtprovince.setName("prmtprovince");
        this.contarea.setName("contarea");
        this.prmtarea.setName("prmtarea");
        this.contsurveyPerson.setName("contsurveyPerson");
        this.prmtsurveyPerson.setName("prmtsurveyPerson");
        this.contsurveyMonth.setName("contsurveyMonth");
        this.surveyMonth.setName("surveyMonth");
        this.kDTable1.setName("kDTable1");
        this.contsurveyYear.setName("contsurveyYear");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdRemark.setName("kdRemark");
        this.region.setName("region");
        this.prmtRegion.setName("prmtRegion");
        this.txtSurveyYear.setName("txtSurveyYear");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);		
        this.btnSubmit.setEnabled(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.separator1.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.btnCreateTo.setEnabled(false);		
        this.menuItemCreateTo.setVisible(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setForeground(new java.awt.Color(0,0,0));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setForeground(new java.awt.Color(0,0,0));		
        this.prmtCreator.setRequired(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelAlignment(7);		
        this.contCreateTime.setForeground(new java.awt.Color(0,0,0));
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);		
        this.kDDateCreateTime.setForeground(new java.awt.Color(0,0,0));		
        this.kDDateCreateTime.setRequired(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setBoundLabelAlignment(7);		
        this.contLastUpdateUser.setForeground(new java.awt.Color(0,0,0));
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setForeground(new java.awt.Color(0,0,0));		
        this.prmtLastUpdateUser.setRequired(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setBoundLabelAlignment(7);		
        this.contLastUpdateTime.setForeground(new java.awt.Color(0,0,0));
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);		
        this.kDDateLastUpdateTime.setForeground(new java.awt.Color(0,0,0));		
        this.kDDateLastUpdateTime.setRequired(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);		
        this.contNumber.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));		
        this.pkBizDate.setRequired(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(true);		
        this.contDescription.setForeground(new java.awt.Color(0,0,0));
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelAlignment(7);		
        this.contAuditor.setVisible(false);		
        this.contAuditor.setForeground(new java.awt.Color(0,0,0));		
        this.contAuditor.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setVisible(false);		
        this.prmtAuditor.setForeground(new java.awt.Color(0,0,0));		
        this.prmtAuditor.setRequired(false);
        // contsurveyName		
        this.contsurveyName.setBoundLabelText(resHelper.getString("contsurveyName.boundLabelText"));		
        this.contsurveyName.setBoundLabelLength(100);		
        this.contsurveyName.setBoundLabelUnderline(true);		
        this.contsurveyName.setVisible(true);		
        this.contsurveyName.setBoundLabelAlignment(7);		
        this.contsurveyName.setForeground(new java.awt.Color(0,0,0));
        // txtsurveyName		
        this.txtsurveyName.setVisible(true);		
        this.txtsurveyName.setHorizontalAlignment(2);		
        this.txtsurveyName.setMaxLength(100);		
        this.txtsurveyName.setRequired(true);		
        this.txtsurveyName.setEnabled(true);		
        this.txtsurveyName.setForeground(new java.awt.Color(0,0,0));
        // contprovince		
        this.contprovince.setBoundLabelText(resHelper.getString("contprovince.boundLabelText"));		
        this.contprovince.setBoundLabelLength(100);		
        this.contprovince.setBoundLabelUnderline(true);		
        this.contprovince.setVisible(true);		
        this.contprovince.setBoundLabelAlignment(7);		
        this.contprovince.setForeground(new java.awt.Color(0,0,0));
        // prmtprovince		
        this.prmtprovince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");		
        this.prmtprovince.setVisible(true);		
        this.prmtprovince.setEditable(true);		
        this.prmtprovince.setDisplayFormat("$name$");		
        this.prmtprovince.setEditFormat("$number$");		
        this.prmtprovince.setCommitFormat("$number$");		
        this.prmtprovince.setRequired(false);		
        this.prmtprovince.setEnabled(false);		
        this.prmtprovince.setForeground(new java.awt.Color(0,0,0));
        // contarea		
        this.contarea.setBoundLabelText(resHelper.getString("contarea.boundLabelText"));		
        this.contarea.setBoundLabelLength(100);		
        this.contarea.setBoundLabelUnderline(true);		
        this.contarea.setVisible(true);		
        this.contarea.setBoundLabelAlignment(7);		
        this.contarea.setForeground(new java.awt.Color(0,0,0));
        // prmtarea		
        this.prmtarea.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CityQuery");		
        this.prmtarea.setVisible(true);		
        this.prmtarea.setEditable(true);		
        this.prmtarea.setDisplayFormat("$name$");		
        this.prmtarea.setEditFormat("$number$");		
        this.prmtarea.setCommitFormat("$number$");		
        this.prmtarea.setRequired(false);		
        this.prmtarea.setEnabled(false);		
        this.prmtarea.setForeground(new java.awt.Color(0,0,0));
        // contsurveyPerson		
        this.contsurveyPerson.setBoundLabelText(resHelper.getString("contsurveyPerson.boundLabelText"));		
        this.contsurveyPerson.setBoundLabelLength(100);		
        this.contsurveyPerson.setBoundLabelUnderline(true);		
        this.contsurveyPerson.setVisible(true);		
        this.contsurveyPerson.setBoundLabelAlignment(7);		
        this.contsurveyPerson.setForeground(new java.awt.Color(0,0,0));
        // prmtsurveyPerson		
        this.prmtsurveyPerson.setQueryInfo("com.kingdee.eas.base.permission.app.UserListQuery");		
        this.prmtsurveyPerson.setVisible(true);		
        this.prmtsurveyPerson.setEditable(true);		
        this.prmtsurveyPerson.setDisplayFormat("$name$");		
        this.prmtsurveyPerson.setEditFormat("$number$");		
        this.prmtsurveyPerson.setCommitFormat("$number$");		
        this.prmtsurveyPerson.setRequired(false);		
        this.prmtsurveyPerson.setEnabled(true);		
        this.prmtsurveyPerson.setForeground(new java.awt.Color(0,0,0));
        // contsurveyMonth		
        this.contsurveyMonth.setBoundLabelText(resHelper.getString("contsurveyMonth.boundLabelText"));		
        this.contsurveyMonth.setBoundLabelLength(100);		
        this.contsurveyMonth.setBoundLabelUnderline(true);		
        this.contsurveyMonth.setVisible(true);		
        this.contsurveyMonth.setBoundLabelAlignment(7);		
        this.contsurveyMonth.setForeground(new java.awt.Color(0,0,0));
        // surveyMonth		
        this.surveyMonth.setVisible(true);		
        this.surveyMonth.addItems(EnumUtils.getEnumList("com.kingdee.eas.hr.train.MonthEnum").toArray());		
        this.surveyMonth.setRequired(true);		
        this.surveyMonth.setEnabled(true);		
        this.surveyMonth.setForeground(new java.awt.Color(0,0,0));
        this.surveyMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    surveyMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.surveyMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    surveyMonth_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTable1		
        this.kDTable1.setFormatXml(resHelper.getString("kDTable1.formatXml"));

        

        this.kDTable1.checkParsed();
        // contsurveyYear		
        this.contsurveyYear.setBoundLabelText(resHelper.getString("contsurveyYear.boundLabelText"));		
        this.contsurveyYear.setBoundLabelLength(100);		
        this.contsurveyYear.setBoundLabelUnderline(true);		
        this.contsurveyYear.setVisible(true);		
        this.contsurveyYear.setBoundLabelAlignment(7);		
        this.contsurveyYear.setForeground(new java.awt.Color(0,0,0));
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
        // kdtEntrys		
        this.kdtEntrys.setFormatXml(resHelper.getString("kdtEntrys.formatXml"));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","dateMonth","priceStart","priceMax","priceAverage","plateArea","salesArea","salesNum","salesMoney","houseType"});


        this.kdtEntrys.checkParsed();
        KDDatePicker kdtEntrys_dateMonth_DatePicker = new KDDatePicker();
        kdtEntrys_dateMonth_DatePicker.setName("kdtEntrys_dateMonth_DatePicker");
        kdtEntrys_dateMonth_DatePicker.setVisible(true);
        kdtEntrys_dateMonth_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_dateMonth_CellEditor = new KDTDefaultCellEditor(kdtEntrys_dateMonth_DatePicker);
        this.kdtEntrys.getColumn("dateMonth").setEditor(kdtEntrys_dateMonth_CellEditor);
        KDFormattedTextField kdtEntrys_priceStart_TextField = new KDFormattedTextField();
        kdtEntrys_priceStart_TextField.setName("kdtEntrys_priceStart_TextField");
        kdtEntrys_priceStart_TextField.setVisible(true);
        kdtEntrys_priceStart_TextField.setEditable(true);
        kdtEntrys_priceStart_TextField.setHorizontalAlignment(2);
        kdtEntrys_priceStart_TextField.setDataType(1);
        	kdtEntrys_priceStart_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_priceStart_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_priceStart_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_priceStart_CellEditor = new KDTDefaultCellEditor(kdtEntrys_priceStart_TextField);
        this.kdtEntrys.getColumn("priceStart").setEditor(kdtEntrys_priceStart_CellEditor);
        KDFormattedTextField kdtEntrys_priceMax_TextField = new KDFormattedTextField();
        kdtEntrys_priceMax_TextField.setName("kdtEntrys_priceMax_TextField");
        kdtEntrys_priceMax_TextField.setVisible(true);
        kdtEntrys_priceMax_TextField.setEditable(true);
        kdtEntrys_priceMax_TextField.setHorizontalAlignment(2);
        kdtEntrys_priceMax_TextField.setDataType(1);
        	kdtEntrys_priceMax_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_priceMax_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_priceMax_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_priceMax_CellEditor = new KDTDefaultCellEditor(kdtEntrys_priceMax_TextField);
        this.kdtEntrys.getColumn("priceMax").setEditor(kdtEntrys_priceMax_CellEditor);
        KDFormattedTextField kdtEntrys_priceAverage_TextField = new KDFormattedTextField();
        kdtEntrys_priceAverage_TextField.setName("kdtEntrys_priceAverage_TextField");
        kdtEntrys_priceAverage_TextField.setVisible(true);
        kdtEntrys_priceAverage_TextField.setEditable(true);
        kdtEntrys_priceAverage_TextField.setHorizontalAlignment(2);
        kdtEntrys_priceAverage_TextField.setDataType(1);
        	kdtEntrys_priceAverage_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_priceAverage_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_priceAverage_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_priceAverage_CellEditor = new KDTDefaultCellEditor(kdtEntrys_priceAverage_TextField);
        this.kdtEntrys.getColumn("priceAverage").setEditor(kdtEntrys_priceAverage_CellEditor);
        KDFormattedTextField kdtEntrys_plateArea_TextField = new KDFormattedTextField();
        kdtEntrys_plateArea_TextField.setName("kdtEntrys_plateArea_TextField");
        kdtEntrys_plateArea_TextField.setVisible(true);
        kdtEntrys_plateArea_TextField.setEditable(true);
        kdtEntrys_plateArea_TextField.setHorizontalAlignment(2);
        kdtEntrys_plateArea_TextField.setDataType(1);
        	kdtEntrys_plateArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_plateArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_plateArea_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_plateArea_CellEditor = new KDTDefaultCellEditor(kdtEntrys_plateArea_TextField);
        this.kdtEntrys.getColumn("plateArea").setEditor(kdtEntrys_plateArea_CellEditor);
        KDFormattedTextField kdtEntrys_salesArea_TextField = new KDFormattedTextField();
        kdtEntrys_salesArea_TextField.setName("kdtEntrys_salesArea_TextField");
        kdtEntrys_salesArea_TextField.setVisible(true);
        kdtEntrys_salesArea_TextField.setEditable(true);
        kdtEntrys_salesArea_TextField.setHorizontalAlignment(2);
        kdtEntrys_salesArea_TextField.setDataType(1);
        	kdtEntrys_salesArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_salesArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_salesArea_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_salesArea_CellEditor = new KDTDefaultCellEditor(kdtEntrys_salesArea_TextField);
        this.kdtEntrys.getColumn("salesArea").setEditor(kdtEntrys_salesArea_CellEditor);
        KDFormattedTextField kdtEntrys_salesNum_TextField = new KDFormattedTextField();
        kdtEntrys_salesNum_TextField.setName("kdtEntrys_salesNum_TextField");
        kdtEntrys_salesNum_TextField.setVisible(true);
        kdtEntrys_salesNum_TextField.setEditable(true);
        kdtEntrys_salesNum_TextField.setHorizontalAlignment(2);
        kdtEntrys_salesNum_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntrys_salesNum_CellEditor = new KDTDefaultCellEditor(kdtEntrys_salesNum_TextField);
        this.kdtEntrys.getColumn("salesNum").setEditor(kdtEntrys_salesNum_CellEditor);
        KDFormattedTextField kdtEntrys_salesMoney_TextField = new KDFormattedTextField();
        kdtEntrys_salesMoney_TextField.setName("kdtEntrys_salesMoney_TextField");
        kdtEntrys_salesMoney_TextField.setVisible(true);
        kdtEntrys_salesMoney_TextField.setEditable(true);
        kdtEntrys_salesMoney_TextField.setHorizontalAlignment(2);
        kdtEntrys_salesMoney_TextField.setDataType(1);
        	kdtEntrys_salesMoney_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_salesMoney_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_salesMoney_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_salesMoney_CellEditor = new KDTDefaultCellEditor(kdtEntrys_salesMoney_TextField);
        this.kdtEntrys.getColumn("salesMoney").setEditor(kdtEntrys_salesMoney_CellEditor);
        final KDBizPromptBox kdtEntrys_houseType_PromptBox = new KDBizPromptBox();
        kdtEntrys_houseType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.HouseAnlysisQuery");
        kdtEntrys_houseType_PromptBox.setVisible(true);
        kdtEntrys_houseType_PromptBox.setEditable(true);
        kdtEntrys_houseType_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_houseType_PromptBox.setEditFormat("$number$");
        kdtEntrys_houseType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_houseType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_houseType_PromptBox);
        this.kdtEntrys.getColumn("houseType").setEditor(kdtEntrys_houseType_CellEditor);
        ObjectValueRender kdtEntrys_houseType_OVR = new ObjectValueRender();
        kdtEntrys_houseType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("houseType").setRenderer(kdtEntrys_houseType_OVR);
        // kdRemark		
        this.kdRemark.setMaxLength(80);
        // region		
        this.region.setBoundLabelText(resHelper.getString("region.boundLabelText"));		
        this.region.setBoundLabelLength(100);		
        this.region.setBoundLabelUnderline(true);		
        this.region.setEnabled(false);		
        this.region.setVisible(false);
        // prmtRegion		
        this.prmtRegion.setQueryInfo("com.kingdee.eas.basedata.assistant.app.RegionQuery");		
        this.prmtRegion.setDisplayFormat("$name$");		
        this.prmtRegion.setEditFormat("$number$");		
        this.prmtRegion.setCommitFormat("$number$");		
        this.prmtRegion.setVisible(false);		
        this.prmtRegion.setEnabled(false);
        // txtSurveyYear		
        this.txtSurveyYear.setRequired(true);
        this.txtSurveyYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtSurveyYear_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtsurveyName,pkBizDate,prmtRegion,surveyMonth,prmtsurveyPerson,kdRemark}));
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
        this.setBounds(new Rectangle(0, 0, 1015, 680));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1015, 680));
        contCreator.setBounds(new Rectangle(10, 631, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 631, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(9, 654, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(9, 654, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(373, 631, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(373, 631, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(373, 654, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(373, 654, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(11, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(11, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(734, 8, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(734, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(9, 74, 995, 87));
        this.add(contDescription, new KDLayout.Constraints(9, 74, 995, 87, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(662, 655, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(662, 655, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contsurveyName.setBounds(new Rectangle(372, 8, 270, 19));
        this.add(contsurveyName, new KDLayout.Constraints(372, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprovince.setBounds(new Rectangle(11, 30, 270, 19));
        this.add(contprovince, new KDLayout.Constraints(11, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contarea.setBounds(new Rectangle(372, 30, 270, 19));
        this.add(contarea, new KDLayout.Constraints(372, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsurveyPerson.setBounds(new Rectangle(734, 52, 270, 19));
        this.add(contsurveyPerson, new KDLayout.Constraints(734, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contsurveyMonth.setBounds(new Rectangle(372, 52, 270, 19));
        this.add(contsurveyMonth, new KDLayout.Constraints(372, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTable1.setBounds(new Rectangle(11, 576, 994, 51));
        this.add(kDTable1, new KDLayout.Constraints(11, 576, 994, 51, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contsurveyYear.setBounds(new Rectangle(10, 52, 270, 19));
        this.add(contsurveyYear, new KDLayout.Constraints(10, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(9, 165, 996, 379));
        this.add(kDTabbedPane1, new KDLayout.Constraints(9, 165, 996, 379, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(11, 558, 992, 15));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.market.MarketSurveyEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, new KDLayout.Constraints(11, 558, 992, 15, 0));
		kdtEntrys_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
		try {vo.put("dateMonth",java.text.DateFormat.getDateInstance().parse("Month"));} catch (Exception dateMonth_exception) {}
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        region.setBounds(new Rectangle(733, 30, 270, 19));
        this.add(region, new KDLayout.Constraints(733, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(kdRemark);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contsurveyName
        contsurveyName.setBoundEditor(txtsurveyName);
        //contprovince
        contprovince.setBoundEditor(prmtprovince);
        //contarea
        contarea.setBoundEditor(prmtarea);
        //contsurveyPerson
        contsurveyPerson.setBoundEditor(prmtsurveyPerson);
        //contsurveyMonth
        contsurveyMonth.setBoundEditor(surveyMonth);
        //contsurveyYear
        contsurveyYear.setBoundEditor(txtSurveyYear);
        //region
        region.setBoundEditor(prmtRegion);

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
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("surveyName", String.class, this.txtsurveyName, "text");
		dataBinder.registerBinding("province", com.kingdee.eas.basedata.assistant.CityInfo.class, this.prmtprovince, "data");
		dataBinder.registerBinding("area", com.kingdee.eas.basedata.assistant.AreaInfo.class, this.prmtarea, "data");
		dataBinder.registerBinding("surveyPerson", com.kingdee.eas.base.permission.UserInfo.class, this.prmtsurveyPerson, "data");
		dataBinder.registerBinding("surveyMonth", com.kingdee.eas.basedata.master.cssp.MonthOrDayEnum.class, this.surveyMonth, "selectedItem");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.market.MarketSurveyEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.dateMonth", com.kingdee.util.enums.Enum.class, this.kdtEntrys, "dateMonth.text");
		dataBinder.registerBinding("entrys.priceStart", java.math.BigDecimal.class, this.kdtEntrys, "priceStart.text");
		dataBinder.registerBinding("entrys.priceMax", java.math.BigDecimal.class, this.kdtEntrys, "priceMax.text");
		dataBinder.registerBinding("entrys.priceAverage", java.math.BigDecimal.class, this.kdtEntrys, "priceAverage.text");
		dataBinder.registerBinding("entrys.plateArea", java.math.BigDecimal.class, this.kdtEntrys, "plateArea.text");
		dataBinder.registerBinding("entrys.salesArea", java.math.BigDecimal.class, this.kdtEntrys, "salesArea.text");
		dataBinder.registerBinding("entrys.salesNum", int.class, this.kdtEntrys, "salesNum.text");
		dataBinder.registerBinding("entrys.salesMoney", java.math.BigDecimal.class, this.kdtEntrys, "salesMoney.text");
		dataBinder.registerBinding("entrys.houseType", com.kingdee.eas.fdc.market.HouseAnlysisInfo.class, this.kdtEntrys, "houseType.text");
		dataBinder.registerBinding("description", String.class, this.kdRemark, "text");
		dataBinder.registerBinding("region", com.kingdee.eas.basedata.assistant.RegionInfo.class, this.prmtRegion, "data");
		dataBinder.registerBinding("surveyYear", int.class, this.txtSurveyYear, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MarketSurveyEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.MarketSurveyInfo)ov;
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("surveyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("area", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("surveyPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("surveyMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.dateMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.priceStart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.priceMax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.priceAverage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.plateArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.salesArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.salesNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.salesMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.houseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("region", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("surveyYear", ValidateHelper.ON_SAVE);    		
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
     * output surveyMonth_actionPerformed method
     */
    protected void surveyMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output surveyMonth_itemStateChanged method
     */
    protected void surveyMonth_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editValueChanged method
     */
    protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output txtSurveyYear_focusLost method
     */
    protected void txtSurveyYear_focusLost(java.awt.event.FocusEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("surveyName"));
        sic.add(new SelectorItemInfo("province.*"));
        sic.add(new SelectorItemInfo("area.*"));
        sic.add(new SelectorItemInfo("surveyPerson.*"));
        sic.add(new SelectorItemInfo("surveyMonth"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.dateMonth"));
    sic.add(new SelectorItemInfo("entrys.priceStart"));
    sic.add(new SelectorItemInfo("entrys.priceMax"));
    sic.add(new SelectorItemInfo("entrys.priceAverage"));
    sic.add(new SelectorItemInfo("entrys.plateArea"));
    sic.add(new SelectorItemInfo("entrys.salesArea"));
    sic.add(new SelectorItemInfo("entrys.salesNum"));
    sic.add(new SelectorItemInfo("entrys.salesMoney"));
        sic.add(new SelectorItemInfo("entrys.houseType.*"));
//        sic.add(new SelectorItemInfo("entrys.houseType.number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("region.*"));
        sic.add(new SelectorItemInfo("surveyYear"));
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketSurveyEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.MarketSurveyEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.MarketSurveyFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.MarketSurveyInfo objectValue = new com.kingdee.eas.fdc.market.MarketSurveyInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/market/MarketSurvey";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.market.app.MarketSurveyQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kDTable1;
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