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
public abstract class AbstractFDCScheduleBaseEditUI extends com.kingdee.eas.fdc.schedule.framework.client.ScheduleBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCScheduleBaseEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurproject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersion;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjust;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSaveNewTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRestore;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLocate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatChangeRespDept;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplayAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHideOther;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPlanTemplate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportPlanTemplate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHisVerion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCompareVer;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSaveNewTask;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportProject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuExportProject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuITemBatChangeRespDept;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDisplayAll;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemHideOther;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuViewHis;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAduit;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnClose;
    protected com.kingdee.eas.fdc.schedule.FDCScheduleInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionClose actionClose = null;
    protected ActionUnClose actionUnClose = null;
    protected ActionDisplayAll actionDisplayAll = null;
    protected ActionHideOther actionHideOther = null;
    protected ActionSaveNewTask actionSaveNewTask = null;
    protected ActionImportProject actionImportProject = null;
    protected ActionExportProject actionExportProject = null;
    protected ActionBatChangeRespDept actionBatChangeRespDept = null;
    protected ActionAdjust actionAdjust = null;
    protected ActionRestore actionRestore = null;
    protected actionExportPlanTemplate actionExportPlanTemplate = null;
    protected actionImportPlanTemplate actionImportPlanTemplate = null;
    protected ActionCompareVer actionCompareVer = null;
    /**
     * output class constructor
     */
    public AbstractFDCScheduleBaseEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCScheduleBaseEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnClose
        this.actionUnClose = new ActionUnClose(this);
        getActionManager().registerAction("actionUnClose", actionUnClose);
         this.actionUnClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayAll
        this.actionDisplayAll = new ActionDisplayAll(this);
        getActionManager().registerAction("actionDisplayAll", actionDisplayAll);
         this.actionDisplayAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHideOther
        this.actionHideOther = new ActionHideOther(this);
        getActionManager().registerAction("actionHideOther", actionHideOther);
         this.actionHideOther.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveNewTask
        this.actionSaveNewTask = new ActionSaveNewTask(this);
        getActionManager().registerAction("actionSaveNewTask", actionSaveNewTask);
         this.actionSaveNewTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportProject
        this.actionImportProject = new ActionImportProject(this);
        getActionManager().registerAction("actionImportProject", actionImportProject);
         this.actionImportProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportProject
        this.actionExportProject = new ActionExportProject(this);
        getActionManager().registerAction("actionExportProject", actionExportProject);
         this.actionExportProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatChangeRespDept
        this.actionBatChangeRespDept = new ActionBatChangeRespDept(this);
        getActionManager().registerAction("actionBatChangeRespDept", actionBatChangeRespDept);
         this.actionBatChangeRespDept.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAdjust
        this.actionAdjust = new ActionAdjust(this);
        getActionManager().registerAction("actionAdjust", actionAdjust);
         this.actionAdjust.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRestore
        this.actionRestore = new ActionRestore(this);
        getActionManager().registerAction("actionRestore", actionRestore);
         this.actionRestore.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportPlanTemplate
        this.actionExportPlanTemplate = new actionExportPlanTemplate(this);
        getActionManager().registerAction("actionExportPlanTemplate", actionExportPlanTemplate);
         this.actionExportPlanTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportPlanTemplate
        this.actionImportPlanTemplate = new actionImportPlanTemplate(this);
        getActionManager().registerAction("actionImportPlanTemplate", actionImportPlanTemplate);
         this.actionImportPlanTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCompareVer
        this.actionCompareVer = new ActionCompareVer(this);
        getActionManager().registerAction("actionCompareVer", actionCompareVer);
         this.actionCompareVer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtCurproject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSaveNewTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRestore = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLocate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatChangeRespDept = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisplayAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHideOther = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportPlanTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportPlanTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHisVerion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCompareVer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSaveNewTask = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuImportProject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuExportProject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuITemBatChangeRespDept = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemDisplayAll = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemHideOther = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuViewHis = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAduit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCurProject.setName("contCurProject");
        this.contVersion.setName("contVersion");
        this.contState.setName("contState");
        this.contProject.setName("contProject");
        this.contAdminDept.setName("contAdminDept");
        this.contAdminPerson.setName("contAdminPerson");
        this.kDPanel1.setName("kDPanel1");
        this.prmtCurproject.setName("prmtCurproject");
        this.txtVersion.setName("txtVersion");
        this.cbState.setName("cbState");
        this.prmtProject.setName("prmtProject");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.kDTable1.setName("kDTable1");
        this.btnAdjust.setName("btnAdjust");
        this.btnSaveNewTask.setName("btnSaveNewTask");
        this.btnRestore.setName("btnRestore");
        this.btnLocate.setName("btnLocate");
        this.btnBatChangeRespDept.setName("btnBatChangeRespDept");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnClose.setName("btnClose");
        this.btnUnClose.setName("btnUnClose");
        this.btnDisplayAll.setName("btnDisplayAll");
        this.btnHideOther.setName("btnHideOther");
        this.btnImportProject.setName("btnImportProject");
        this.btnExportProject.setName("btnExportProject");
        this.btnImportPlanTemplate.setName("btnImportPlanTemplate");
        this.btnExportPlanTemplate.setName("btnExportPlanTemplate");
        this.btnHisVerion.setName("btnHisVerion");
        this.btnCompareVer.setName("btnCompareVer");
        this.menuItemSaveNewTask.setName("menuItemSaveNewTask");
        this.kDSeparator9.setName("kDSeparator9");
        this.menuImportProject.setName("menuImportProject");
        this.menuExportProject.setName("menuExportProject");
        this.menuITemBatChangeRespDept.setName("menuITemBatChangeRespDept");
        this.kDSeparator6.setName("kDSeparator6");
        this.menuItemDisplayAll.setName("menuItemDisplayAll");
        this.menuItemHideOther.setName("menuItemHideOther");
        this.kDSeparator10.setName("kDSeparator10");
        this.menuViewHis.setName("menuViewHis");
        this.kDSeparator7.setName("kDSeparator7");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAduit.setName("menuItemUnAduit");
        this.kDSeparator8.setName("kDSeparator8");
        this.menuItemClose.setName("menuItemClose");
        this.menuItemUnClose.setName("menuItemUnClose");
        // CoreUI		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setEnabled(false);		
        this.contCreator.setVisible(false);		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setVisible(false);		
        this.contAuditor.setEnabled(false);		
        this.contAuditor.setVisible(false);		
        this.contAuditTime.setEnabled(false);		
        this.contAuditTime.setVisible(false);		
        this.prmtCreator.setEnabled(false);		
        this.menuItemPERT.setText(resHelper.getString("menuItemPERT.text"));		
        this.menuItemPERT.setMnemonic(78);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);		
        this.contProject.setVisible(false);
        // contAdminDept		
        this.contAdminDept.setBoundLabelText(resHelper.getString("contAdminDept.boundLabelText"));		
        this.contAdminDept.setBoundLabelLength(100);		
        this.contAdminDept.setBoundLabelUnderline(true);		
        this.contAdminDept.setVisible(false);
        // contAdminPerson		
        this.contAdminPerson.setBoundLabelText(resHelper.getString("contAdminPerson.boundLabelText"));		
        this.contAdminPerson.setBoundLabelLength(100);		
        this.contAdminPerson.setBoundLabelUnderline(true);		
        this.contAdminPerson.setVisible(false);
        // kDPanel1		
        this.kDPanel1.setBorder(null);
        // prmtCurproject		
        this.prmtCurproject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtCurproject.setEditFormat("$name$");		
        this.prmtCurproject.setDisplayFormat("$name$");		
        this.prmtCurproject.setCommitFormat("$name$");
        this.prmtCurproject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCurproject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtVersion		
        this.txtVersion.setMaxLength(80);
        // cbState
        // prmtProject		
        this.prmtProject.setEnabled(false);
        // prmtAdminDept		
        this.prmtAdminDept.setQueryInfo("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtAdminDept.setDisplayFormat("$name$");		
        this.prmtAdminDept.setCommitFormat("$number$");		
        this.prmtAdminDept.setEditFormat("$number$");
        // prmtAdminPerson		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtAdminPerson.setDisplayFormat("$name$");		
        this.prmtAdminPerson.setEditFormat("$number$");		
        this.prmtAdminPerson.setCommitFormat("$number$");
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"workDayPre\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"workDayDep\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"workDayDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"planStartPre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"planStartDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"planStartDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"planEndPre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"planEndDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"planEndDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"checkDatePre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"checkDateDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"checkDateDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{workDayPre}</t:Cell><t:Cell>$Resource{workDayDep}</t:Cell><t:Cell>$Resource{workDayDiff}</t:Cell><t:Cell>$Resource{planStartPre}</t:Cell><t:Cell>$Resource{planStartDep}</t:Cell><t:Cell>$Resource{planStartDiff}</t:Cell><t:Cell>$Resource{planEndPre}</t:Cell><t:Cell>$Resource{planEndDep}</t:Cell><t:Cell>$Resource{planEndDiff}</t:Cell><t:Cell>$Resource{checkDatePre}</t:Cell><t:Cell>$Resource{checkDateDep}</t:Cell><t:Cell>$Resource{checkDateDiff}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{level}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName_Row2}</t:Cell><t:Cell>$Resource{workDayPre_Row2}</t:Cell><t:Cell>$Resource{workDayDep_Row2}</t:Cell><t:Cell>$Resource{workDayDiff_Row2}</t:Cell><t:Cell>$Resource{planStartPre_Row2}</t:Cell><t:Cell>$Resource{planStartDep_Row2}</t:Cell><t:Cell>$Resource{planStartDiff_Row2}</t:Cell><t:Cell>$Resource{planEndPre_Row2}</t:Cell><t:Cell>$Resource{planEndDep_Row2}</t:Cell><t:Cell>$Resource{planEndDiff_Row2}</t:Cell><t:Cell>$Resource{checkDatePre_Row2}</t:Cell><t:Cell>$Resource{checkDateDep_Row2}</t:Cell><t:Cell>$Resource{checkDateDiff_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{isLeaf_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"1\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"1\" t:right=\"15\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // btnAdjust
        this.btnAdjust.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjust, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjust.setText(resHelper.getString("btnAdjust.text"));		
        this.btnAdjust.setToolTipText(resHelper.getString("btnAdjust.toolTipText"));
        // btnSaveNewTask
        this.btnSaveNewTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveNewTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSaveNewTask.setText(resHelper.getString("btnSaveNewTask.text"));		
        this.btnSaveNewTask.setToolTipText(resHelper.getString("btnSaveNewTask.toolTipText"));		
        this.btnSaveNewTask.setVisible(false);		
        this.btnSaveNewTask.setEnabled(false);
        // btnRestore
        this.btnRestore.setAction((IItemAction)ActionProxyFactory.getProxy(actionRestore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRestore.setText(resHelper.getString("btnRestore.text"));		
        this.btnRestore.setToolTipText(resHelper.getString("btnRestore.toolTipText"));
        // btnLocate
        this.btnLocate.setAction((IItemAction)ActionProxyFactory.getProxy(actionLocate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLocate.setText(resHelper.getString("btnLocate.text"));		
        this.btnLocate.setToolTipText(resHelper.getString("btnLocate.toolTipText"));
        // btnBatChangeRespDept
        this.btnBatChangeRespDept.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatChangeRespDept, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatChangeRespDept.setText(resHelper.getString("btnBatChangeRespDept.text"));		
        this.btnBatChangeRespDept.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));		
        this.btnBatChangeRespDept.setToolTipText(resHelper.getString("btnBatChangeRespDept.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));		
        this.btnClose.setToolTipText(resHelper.getString("btnClose.toolTipText"));
        // btnUnClose
        this.btnUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnClose.setText(resHelper.getString("btnUnClose.text"));		
        this.btnUnClose.setToolTipText(resHelper.getString("btnUnClose.toolTipText"));
        // btnDisplayAll
        this.btnDisplayAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplayAll.setText(resHelper.getString("btnDisplayAll.text"));		
        this.btnDisplayAll.setToolTipText(resHelper.getString("btnDisplayAll.toolTipText"));
        // btnHideOther
        this.btnHideOther.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideOther, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHideOther.setText(resHelper.getString("btnHideOther.text"));		
        this.btnHideOther.setToolTipText(resHelper.getString("btnHideOther.toolTipText"));
        // btnImportProject
        this.btnImportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportProject.setText(resHelper.getString("btnImportProject.text"));
        // btnExportProject
        this.btnExportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportProject.setText(resHelper.getString("btnExportProject.text"));
        // btnImportPlanTemplate
        this.btnImportPlanTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportPlanTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportPlanTemplate.setText(resHelper.getString("btnImportPlanTemplate.text"));
        // btnExportPlanTemplate
        this.btnExportPlanTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPlanTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportPlanTemplate.setText(resHelper.getString("btnExportPlanTemplate.text"));
        // btnHisVerion		
        this.btnHisVerion.setText(resHelper.getString("btnHisVerion.text"));
        // btnCompareVer
        this.btnCompareVer.setAction((IItemAction)ActionProxyFactory.getProxy(actionCompareVer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCompareVer.setText(resHelper.getString("btnCompareVer.text"));		
        this.btnCompareVer.setToolTipText(resHelper.getString("btnCompareVer.toolTipText"));
        // menuItemSaveNewTask
        this.menuItemSaveNewTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveNewTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSaveNewTask.setText(resHelper.getString("menuItemSaveNewTask.text"));		
        this.menuItemSaveNewTask.setToolTipText(resHelper.getString("menuItemSaveNewTask.toolTipText"));		
        this.menuItemSaveNewTask.setMnemonic(78);		
        this.menuItemSaveNewTask.setVisible(false);
        // kDSeparator9
        // menuImportProject
        this.menuImportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportProject.setText(resHelper.getString("menuImportProject.text"));		
        this.menuImportProject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.menuImportProject.setVisible(false);
        // menuExportProject
        this.menuExportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuExportProject.setText(resHelper.getString("menuExportProject.text"));		
        this.menuExportProject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));		
        this.menuExportProject.setVisible(false);
        // menuITemBatChangeRespDept
        this.menuITemBatChangeRespDept.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatChangeRespDept, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuITemBatChangeRespDept.setText(resHelper.getString("menuITemBatChangeRespDept.text"));		
        this.menuITemBatChangeRespDept.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));		
        this.menuITemBatChangeRespDept.setVisible(false);
        // kDSeparator6
        // menuItemDisplayAll
        this.menuItemDisplayAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDisplayAll.setText(resHelper.getString("menuItemDisplayAll.text"));		
        this.menuItemDisplayAll.setToolTipText(resHelper.getString("menuItemDisplayAll.toolTipText"));
        // menuItemHideOther
        this.menuItemHideOther.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideOther, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHideOther.setText(resHelper.getString("menuItemHideOther.text"));		
        this.menuItemHideOther.setMnemonic(72);		
        this.menuItemHideOther.setToolTipText(resHelper.getString("menuItemHideOther.toolTipText"));
        // kDSeparator10
        // menuViewHis		
        this.menuViewHis.setText(resHelper.getString("menuViewHis.text"));		
        this.menuViewHis.setVisible(false);
        // kDSeparator7
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setMnemonic(65);		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        // menuItemUnAduit
        this.menuItemUnAduit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAduit.setText(resHelper.getString("menuItemUnAduit.text"));		
        this.menuItemUnAduit.setMnemonic(85);
        // kDSeparator8
        // menuItemClose
        this.menuItemClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClose.setText(resHelper.getString("menuItemClose.text"));		
        this.menuItemClose.setToolTipText(resHelper.getString("menuItemClose.toolTipText"));
        // menuItemUnClose
        this.menuItemUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnClose.setText(resHelper.getString("menuItemUnClose.text"));		
        this.menuItemUnClose.setMnemonic(69);		
        this.menuItemUnClose.setToolTipText(resHelper.getString("menuItemUnClose.toolTipText"));		
        this.menuItemUnClose.setVisible(false);
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
        this.setBounds(new Rectangle(10, 10, 900, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 900, 600));
        contCreator.setBounds(new Rectangle(465, 2, 8, 8));
        this.add(contCreator, new KDLayout.Constraints(465, 2, 8, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(11, 571, 371, 19));
        this.add(contCreateTime, new KDLayout.Constraints(11, 571, 371, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(519, 549, 371, 19));
        this.add(contAuditor, new KDLayout.Constraints(519, 549, 371, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(519, 573, 371, 19));
        this.add(contAuditTime, new KDLayout.Constraints(519, 573, 371, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        mainPanel.setBounds(new Rectangle(12, 42, 878, 551));
        this.add(mainPanel, new KDLayout.Constraints(12, 42, 878, 551, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(12, 13, 218, 19));
        this.add(contCurProject, new KDLayout.Constraints(12, 13, 218, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersion.setBounds(new Rectangle(312, 13, 261, 19));
        this.add(contVersion, new KDLayout.Constraints(312, 13, 261, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(655, 13, 233, 19));
        this.add(contState, new KDLayout.Constraints(655, 13, 233, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(295, 551, 270, 19));
        this.add(contProject, new KDLayout.Constraints(295, 551, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAdminDept.setBounds(new Rectangle(14, 548, 270, 19));
        this.add(contAdminDept, new KDLayout.Constraints(14, 548, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAdminPerson.setBounds(new Rectangle(693, 1, 8, 8));
        this.add(contAdminPerson, new KDLayout.Constraints(693, 1, 8, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(126, 77, 767, 300));
        this.add(kDPanel1, new KDLayout.Constraints(126, 77, 767, 300, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
mainPanel.setLayout(new BorderLayout(0, 0));        //contCurProject
        contCurProject.setBoundEditor(prmtCurproject);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contState
        contState.setBoundEditor(cbState);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contAdminPerson
        contAdminPerson.setBoundEditor(prmtAdminPerson);
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDTable1, BorderLayout.CENTER);

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
        menuFile.add(menuItemSaveNewTask);
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
        menuFile.add(kDSeparator9);
        menuFile.add(menuImportProject);
        menuFile.add(menuExportProject);
        menuFile.add(menuITemBatChangeRespDept);
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
        menuView.add(menuItemZoomLeft);
        menuView.add(menuItemZoomCenter);
        menuView.add(menuItemZoomRight);
        menuView.add(menuItemZoomOut);
        menuView.add(menuItemZoomIn);
        menuView.add(kDSeparator6);
        menuView.add(menuItemDisplayAll);
        menuView.add(menuItemHideOther);
        menuView.add(kDSeparator10);
        menuView.add(menuViewHis);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemProperty);
        menuBiz.add(menuItemGraphOption);
        menuBiz.add(menuItemCritical);
        menuBiz.add(menuItemPERT);
        menuBiz.add(kDSeparator7);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAduit);
        menuBiz.add(kDSeparator8);
        menuBiz.add(menuItemClose);
        menuBiz.add(menuItemUnClose);
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
        this.toolBar.add(btnAdjust);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSaveNewTask);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnRestore);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnBatChangeRespDept);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(btnZoomLeft);
        this.toolBar.add(btnZoomCenter);
        this.toolBar.add(btnZoomRight);
        this.toolBar.add(btnZoomOut);
        this.toolBar.add(btnZoomIn);
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
        this.toolBar.add(btnShowByLevel);
        this.toolBar.add(btnBatchModifyTaskType);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnProperty);
        this.toolBar.add(btnCritical);
        this.toolBar.add(btnPert);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnUnClose);
        this.toolBar.add(btnDisplayAll);
        this.toolBar.add(btnHideOther);
        this.toolBar.add(btnImportProject);
        this.toolBar.add(btnExportProject);
        this.toolBar.add(btnImportPlanTemplate);
        this.toolBar.add(btnExportPlanTemplate);
        this.toolBar.add(btnHisVerion);
        this.toolBar.add(btnCompareVer);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurproject, "data");
		dataBinder.registerBinding("versionName", String.class, this.txtVersion, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum.class, this.cbState, "selectedItem");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("adminDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtAdminDept, "data");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtAdminPerson, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FDCScheduleBaseEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.FDCScheduleInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    		
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
     * output prmtCurproject_dataChanged method
     */
    protected void prmtCurproject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("versionName"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("adminDept.*"));
        sic.add(new SelectorItemInfo("adminPerson.*"));
        return sic;
    }        
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnClose_actionPerformed method
     */
    public void actionUnClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayAll_actionPerformed method
     */
    public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHideOther_actionPerformed method
     */
    public void actionHideOther_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveNewTask_actionPerformed method
     */
    public void actionSaveNewTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportProject_actionPerformed method
     */
    public void actionImportProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportProject_actionPerformed method
     */
    public void actionExportProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatChangeRespDept_actionPerformed method
     */
    public void actionBatChangeRespDept_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAdjust_actionPerformed method
     */
    public void actionAdjust_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRestore_actionPerformed method
     */
    public void actionRestore_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportPlanTemplate_actionPerformed method
     */
    public void actionExportPlanTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportPlanTemplate_actionPerformed method
     */
    public void actionImportPlanTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCompareVer_actionPerformed method
     */
    public void actionCompareVer_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }
	public RequestContext prepareActionUnClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnClose() {
    	return false;
    }
	public RequestContext prepareActionDisplayAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayAll() {
    	return false;
    }
	public RequestContext prepareActionHideOther(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHideOther() {
    	return false;
    }
	public RequestContext prepareActionSaveNewTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveNewTask() {
    	return false;
    }
	public RequestContext prepareActionImportProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportProject() {
    	return false;
    }
	public RequestContext prepareActionExportProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportProject() {
    	return false;
    }
	public RequestContext prepareActionBatChangeRespDept(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatChangeRespDept() {
    	return false;
    }
	public RequestContext prepareActionAdjust(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdjust() {
    	return false;
    }
	public RequestContext prepareActionRestore(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRestore() {
    	return false;
    }
	public RequestContext prepareactionExportPlanTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionExportPlanTemplate() {
    	return false;
    }
	public RequestContext prepareactionImportPlanTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImportPlanTemplate() {
    	return false;
    }
	public RequestContext prepareActionCompareVer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCompareVer() {
    	return false;
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionClose class
     */     
    protected class ActionClose extends ItemAction {     
    
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output ActionUnClose class
     */     
    protected class ActionUnClose extends ItemAction {     
    
        public ActionUnClose()
        {
            this(null);
        }

        public ActionUnClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionUnClose", "actionUnClose_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayAll class
     */     
    protected class ActionDisplayAll extends ItemAction {     
    
        public ActionDisplayAll()
        {
            this(null);
        }

        public ActionDisplayAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDisplayAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionDisplayAll", "actionDisplayAll_actionPerformed", e);
        }
    }

    /**
     * output ActionHideOther class
     */     
    protected class ActionHideOther extends ItemAction {     
    
        public ActionHideOther()
        {
            this(null);
        }

        public ActionHideOther(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionHideOther.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHideOther.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHideOther.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionHideOther", "actionHideOther_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveNewTask class
     */     
    protected class ActionSaveNewTask extends ItemAction {     
    
        public ActionSaveNewTask()
        {
            this(null);
        }

        public ActionSaveNewTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSaveNewTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveNewTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveNewTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionSaveNewTask", "actionSaveNewTask_actionPerformed", e);
        }
    }

    /**
     * output ActionImportProject class
     */     
    protected class ActionImportProject extends ItemAction {     
    
        public ActionImportProject()
        {
            this(null);
        }

        public ActionImportProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionImportProject", "actionImportProject_actionPerformed", e);
        }
    }

    /**
     * output ActionExportProject class
     */     
    protected class ActionExportProject extends ItemAction {     
    
        public ActionExportProject()
        {
            this(null);
        }

        public ActionExportProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExportProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionExportProject", "actionExportProject_actionPerformed", e);
        }
    }

    /**
     * output ActionBatChangeRespDept class
     */     
    protected class ActionBatChangeRespDept extends ItemAction {     
    
        public ActionBatChangeRespDept()
        {
            this(null);
        }

        public ActionBatChangeRespDept(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatChangeRespDept.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatChangeRespDept.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatChangeRespDept.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionBatChangeRespDept", "actionBatChangeRespDept_actionPerformed", e);
        }
    }

    /**
     * output ActionAdjust class
     */     
    protected class ActionAdjust extends ItemAction {     
    
        public ActionAdjust()
        {
            this(null);
        }

        public ActionAdjust(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAdjust.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjust.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjust.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionAdjust", "actionAdjust_actionPerformed", e);
        }
    }

    /**
     * output ActionRestore class
     */     
    protected class ActionRestore extends ItemAction {     
    
        public ActionRestore()
        {
            this(null);
        }

        public ActionRestore(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRestore.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRestore.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRestore.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionRestore", "actionRestore_actionPerformed", e);
        }
    }

    /**
     * output actionExportPlanTemplate class
     */     
    protected class actionExportPlanTemplate extends ItemAction {     
    
        public actionExportPlanTemplate()
        {
            this(null);
        }

        public actionExportPlanTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionExportPlanTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportPlanTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportPlanTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "actionExportPlanTemplate", "actionExportPlanTemplate_actionPerformed", e);
        }
    }

    /**
     * output actionImportPlanTemplate class
     */     
    protected class actionImportPlanTemplate extends ItemAction {     
    
        public actionImportPlanTemplate()
        {
            this(null);
        }

        public actionImportPlanTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionImportPlanTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportPlanTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportPlanTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "actionImportPlanTemplate", "actionImportPlanTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionCompareVer class
     */     
    protected class ActionCompareVer extends ItemAction {     
    
        public ActionCompareVer()
        {
            this(null);
        }

        public ActionCompareVer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCompareVer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompareVer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompareVer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCScheduleBaseEditUI.this, "ActionCompareVer", "actionCompareVer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FDCScheduleBaseEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}