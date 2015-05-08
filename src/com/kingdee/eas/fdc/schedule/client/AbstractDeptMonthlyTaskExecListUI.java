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
public abstract class AbstractDeptMonthlyTaskExecListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeptMonthlyTaskExecListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel logoPanel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkScheduleMonth;
    protected com.kingdee.bos.ctrl.swing.KDTextField id;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScheduleReport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelfEvaluation;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFinalEvaluation;
    protected javax.swing.JToolBar.Separator separator2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMonthlyReport;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOutputExcel;
    protected ActionScheduleReport actionScheduleReport = null;
    protected ActionSelfEvaluation actionSelfEvaluation = null;
    protected ActionFinalEvaluation actionFinalEvaluation = null;
    protected ActionMonthlyReport actionMonthlyReport = null;
    protected ActionOutputExcel actionOutputExcel = null;
    /**
     * output class constructor
     */
    public AbstractDeptMonthlyTaskExecListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractDeptMonthlyTaskExecListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.schedule.app", "DeptMonthlyScheduleTaskExecQuery");
        //actionScheduleReport
        this.actionScheduleReport = new ActionScheduleReport(this);
        getActionManager().registerAction("actionScheduleReport", actionScheduleReport);
         this.actionScheduleReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelfEvaluation
        this.actionSelfEvaluation = new ActionSelfEvaluation(this);
        getActionManager().registerAction("actionSelfEvaluation", actionSelfEvaluation);
         this.actionSelfEvaluation.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFinalEvaluation
        this.actionFinalEvaluation = new ActionFinalEvaluation(this);
        getActionManager().registerAction("actionFinalEvaluation", actionFinalEvaluation);
         this.actionFinalEvaluation.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMonthlyReport
        this.actionMonthlyReport = new ActionMonthlyReport(this);
        getActionManager().registerAction("actionMonthlyReport", actionMonthlyReport);
         this.actionMonthlyReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOutputExcel
        this.actionOutputExcel = new ActionOutputExcel(this);
        getActionManager().registerAction("actionOutputExcel", actionOutputExcel);
         this.actionOutputExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.logoPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkScheduleMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.id = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnScheduleReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSelfEvaluation = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFinalEvaluation = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator2 = new javax.swing.JToolBar.Separator();
        this.btnMonthlyReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnOutputExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDContainer1.setName("kDContainer1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contProject.setName("contProject");
        this.contStartDate.setName("contStartDate");
        this.contEndDate.setName("contEndDate");
        this.logoPanel.setName("logoPanel");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.pkScheduleMonth.setName("pkScheduleMonth");
        this.id.setName("id");
        this.prmtProject.setName("prmtProject");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        this.btnScheduleReport.setName("btnScheduleReport");
        this.btnSelfEvaluation.setName("btnSelfEvaluation");
        this.btnFinalEvaluation.setName("btnFinalEvaluation");
        this.separator2.setName("separator2");
        this.btnMonthlyReport.setName("btnMonthlyReport");
        this.separator1.setName("separator1");
        this.btnOutputExcel.setName("btnOutputExcel");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.menuTool.setEnabled(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>##.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>##.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"state\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"taskType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"adminPerson\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"planEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"completePrecent\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"realEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"weightRate\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"selfEvaluationScore\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" t:styleID=\"sCol9\" /><t:Column t:key=\"finalEvaluationScore\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" t:styleID=\"sCol10\" /><t:Column t:key=\"relatedTask\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"project\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" /><t:Column t:key=\"comboTaskOrigin\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" /><t:Column t:key=\"taskState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:configured=\"false\" t:styleID=\"sCol14\" /><t:Column t:key=\"relatetaskid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:configured=\"false\" t:styleID=\"sCol15\" /><t:Column t:key=\"progressEdition\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:configured=\"false\" t:styleID=\"sCol16\" /><t:Column t:key=\"intendEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:configured=\"false\" t:styleID=\"sCol17\" /><t:Column t:key=\"finishStandard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:configured=\"false\" t:styleID=\"sCol18\" /><t:Column t:key=\"projectPeriod\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:configured=\"false\" /><t:Column t:key=\"planStartDate\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:configured=\"false\" /><t:Column t:key=\"realStartDate\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:configured=\"false\" /><t:Column t:key=\"adminDept\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"bizType\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"comment\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">状态</t:Cell><t:Cell t:configured=\"false\">任务类别</t:Cell><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">计划完成日期</t:Cell><t:Cell t:configured=\"false\">完成进度(%)</t:Cell><t:Cell t:configured=\"false\">实际完成日期</t:Cell><t:Cell t:configured=\"false\">权重(%)</t:Cell><t:Cell t:configured=\"false\">自评得分</t:Cell><t:Cell t:configured=\"false\">终评得分</t:Cell><t:Cell t:configured=\"false\">相关任务</t:Cell><t:Cell t:configured=\"false\">所属项目</t:Cell><t:Cell t:configured=\"false\">任务来源</t:Cell><t:Cell t:configured=\"false\">隐藏状态</t:Cell><t:Cell t:configured=\"false\">相关任务ID</t:Cell><t:Cell t:configured=\"false\">汇报版本</t:Cell><t:Cell t:configured=\"false\">预计完成日期</t:Cell><t:Cell t:configured=\"false\">完成标准</t:Cell><t:Cell t:configured=\"false\">工期（天数）</t:Cell><t:Cell t:configured=\"false\">计划开始日期</t:Cell><t:Cell t:configured=\"false\">实际开始日期</t:Cell><t:Cell t:configured=\"false\">责任部门</t:Cell><t:Cell t:configured=\"false\">业务类型</t:Cell><t:Cell t:configured=\"false\">备注</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"tasks.id","","tasks.taskType","tasks.taskName","adminPerson.name","tasks.planFinishDate","progressReport.completePrecent","progressReport.realEndDate","tasks.weightRate","selfAndFinal.selfEvaluationScore","selfAndFinal.finalEvaluationScore","relatedTask.name","project.name","tasks.taskOrigin","tasks.hiddenStatus","relatedTask.id","progressReport.progressEdition","progressReport.intendEndDate","tasks.finishStandard","tasks.projectPeriod","tasks.planStartDate","progressReport.realStartDate","adminDept.name","",""});

		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnView.setEnabled(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.btnRefresh.setEnabled(false);		
        this.btnRefresh.setVisible(false);		
        this.btnLocate.setEnabled(false);		
        this.btnLocate.setVisible(false);		
        this.btnQuery.setEnabled(false);		
        this.btnQuery.setVisible(false);		
        this.menuView.setEnabled(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.menuBiz.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnCreateTo.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuWorkFlow.setEnabled(false);		
        this.menuWorkFlow.setVisible(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(50);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(false);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelUnderline(true);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);		
        this.contStartDate.setBoundLabelUnderline(true);		
        this.contStartDate.setVisible(false);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelUnderline(true);		
        this.contEndDate.setBoundLabelLength(100);		
        this.contEndDate.setVisible(false);
        // logoPanel
        // prmtAdminDept		
        this.prmtAdminDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtAdminDept.setEditFormat("$name$");		
        this.prmtAdminDept.setDisplayFormat("$name$");		
        this.prmtAdminDept.setCommitFormat("$name$");
        this.prmtAdminDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAdminDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkScheduleMonth
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
        // id		
        this.id.setVisible(false);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
        this.prmtProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkStartDate
        this.pkStartDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkStartDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkEndDate		
        this.pkEndDate.setVisible(false);
        this.pkEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnScheduleReport
        this.btnScheduleReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionScheduleReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScheduleReport.setText(resHelper.getString("btnScheduleReport.text"));
        // btnSelfEvaluation
        this.btnSelfEvaluation.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelfEvaluation, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelfEvaluation.setText(resHelper.getString("btnSelfEvaluation.text"));
        // btnFinalEvaluation
        this.btnFinalEvaluation.setAction((IItemAction)ActionProxyFactory.getProxy(actionFinalEvaluation, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFinalEvaluation.setText(resHelper.getString("btnFinalEvaluation.text"));
        // separator2		
        this.separator2.setOrientation(1);		
        this.separator2.setVisible(false);
        // btnMonthlyReport
        this.btnMonthlyReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionMonthlyReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMonthlyReport.setText(resHelper.getString("btnMonthlyReport.text"));
        // separator1		
        this.separator1.setOrientation(1);		
        this.separator1.setVisible(false);
        // btnOutputExcel
        this.btnOutputExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionOutputExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOutputExcel.setText(resHelper.getString("btnOutputExcel.text"));
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
        kDLabelContainer1.setBounds(new Rectangle(17, 11, 376, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(17, 11, 376, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(405, 11, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(405, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(18, 68, 975, 529));
        this.add(kDContainer1, new KDLayout.Constraints(18, 68, 975, 529, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(958, 30, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(958, 30, 270, 19, 0));
        contProject.setBounds(new Rectangle(690, 11, 314, 19));
        this.add(contProject, new KDLayout.Constraints(690, 11, 314, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStartDate.setBounds(new Rectangle(17, 39, 270, 19));
        this.add(contStartDate, new KDLayout.Constraints(17, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEndDate.setBounds(new Rectangle(405, 39, 270, 19));
        this.add(contEndDate, new KDLayout.Constraints(405, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        logoPanel.setBounds(new Rectangle(16, 602, 973, 25));
        this.add(logoPanel, new KDLayout.Constraints(16, 602, 973, 25, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtAdminDept);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(pkScheduleMonth);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(18, 68, 975, 529));        tblMain.setBounds(new Rectangle(0, 1, 976, 521));
        kDContainer1.getContentPane().add(tblMain, new KDLayout.Constraints(0, 1, 976, 521, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(id);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        logoPanel.setLayout(new KDLayout());
        logoPanel.putClientProperty("OriginalBounds", new Rectangle(16, 602, 973, 25));
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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnScheduleReport);
        this.toolBar.add(btnSelfEvaluation);
        this.toolBar.add(btnFinalEvaluation);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separator2);
        this.toolBar.add(btnMonthlyReport);
        this.toolBar.add(separator1);
        this.toolBar.add(btnOutputExcel);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.DeptMonthlyTaskExecListUIHandler";
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
	 * ????????У??
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
     * output prmtAdminDept_dataChanged method
     */
    protected void prmtAdminDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkScheduleMonth_dataChanged method
     */
    protected void pkScheduleMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_dataChanged method
     */
    protected void prmtProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkStartDate_dataChanged method
     */
    protected void pkStartDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkEndDate_dataChanged method
     */
    protected void pkEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("tasks.taskType"));
        sic.add(new SelectorItemInfo("tasks.taskName"));
        sic.add(new SelectorItemInfo("adminPerson.name"));
        sic.add(new SelectorItemInfo("tasks.weightRate"));
        sic.add(new SelectorItemInfo("selfAndFinal.selfEvaluationScore"));
        sic.add(new SelectorItemInfo("selfAndFinal.finalEvaluationScore"));
        sic.add(new SelectorItemInfo("relatedTask.name"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("tasks.id"));
        sic.add(new SelectorItemInfo("tasks.planFinishDate"));
        sic.add(new SelectorItemInfo("tasks.hiddenStatus"));
        sic.add(new SelectorItemInfo("relatedTask.id"));
        sic.add(new SelectorItemInfo("progressReport.realEndDate"));
        sic.add(new SelectorItemInfo("progressReport.progressEdition"));
        sic.add(new SelectorItemInfo("progressReport.realStartDate"));
        sic.add(new SelectorItemInfo("progressReport.intendEndDate"));
        sic.add(new SelectorItemInfo("tasks.finishStandard"));
        sic.add(new SelectorItemInfo("tasks.taskOrigin"));
        sic.add(new SelectorItemInfo("tasks.projectPeriod"));
        sic.add(new SelectorItemInfo("tasks.planStartDate"));
        sic.add(new SelectorItemInfo("adminDept.name"));
        sic.add(new SelectorItemInfo("progressReport.completePrecent"));
        sic.add(new SelectorItemInfo("scheduleMonth"));
        sic.add(new SelectorItemInfo("id"));
        return sic;
    }        
    	

    /**
     * output actionScheduleReport_actionPerformed method
     */
    public void actionScheduleReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelfEvaluation_actionPerformed method
     */
    public void actionSelfEvaluation_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFinalEvaluation_actionPerformed method
     */
    public void actionFinalEvaluation_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMonthlyReport_actionPerformed method
     */
    public void actionMonthlyReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOutputExcel_actionPerformed method
     */
    public void actionOutputExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionScheduleReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionScheduleReport() {
    	return false;
    }
	public RequestContext prepareActionSelfEvaluation(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelfEvaluation() {
    	return false;
    }
	public RequestContext prepareActionFinalEvaluation(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFinalEvaluation() {
    	return false;
    }
	public RequestContext prepareActionMonthlyReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMonthlyReport() {
    	return false;
    }
	public RequestContext prepareActionOutputExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOutputExcel() {
    	return false;
    }

    /**
     * output ActionScheduleReport class
     */     
    protected class ActionScheduleReport extends ItemAction {     
    
        public ActionScheduleReport()
        {
            this(null);
        }

        public ActionScheduleReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionScheduleReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScheduleReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScheduleReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecListUI.this, "ActionScheduleReport", "actionScheduleReport_actionPerformed", e);
        }
    }

    /**
     * output ActionSelfEvaluation class
     */     
    protected class ActionSelfEvaluation extends ItemAction {     
    
        public ActionSelfEvaluation()
        {
            this(null);
        }

        public ActionSelfEvaluation(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSelfEvaluation.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelfEvaluation.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelfEvaluation.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecListUI.this, "ActionSelfEvaluation", "actionSelfEvaluation_actionPerformed", e);
        }
    }

    /**
     * output ActionFinalEvaluation class
     */     
    protected class ActionFinalEvaluation extends ItemAction {     
    
        public ActionFinalEvaluation()
        {
            this(null);
        }

        public ActionFinalEvaluation(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionFinalEvaluation.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFinalEvaluation.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFinalEvaluation.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecListUI.this, "ActionFinalEvaluation", "actionFinalEvaluation_actionPerformed", e);
        }
    }

    /**
     * output ActionMonthlyReport class
     */     
    protected class ActionMonthlyReport extends ItemAction {     
    
        public ActionMonthlyReport()
        {
            this(null);
        }

        public ActionMonthlyReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMonthlyReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthlyReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthlyReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecListUI.this, "ActionMonthlyReport", "actionMonthlyReport_actionPerformed", e);
        }
    }

    /**
     * output ActionOutputExcel class
     */     
    protected class ActionOutputExcel extends ItemAction {     
    
        public ActionOutputExcel()
        {
            this(null);
        }

        public ActionOutputExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionOutputExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutputExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutputExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyTaskExecListUI.this, "ActionOutputExcel", "actionOutputExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "DeptMonthlyTaskExecListUI");
    }




}