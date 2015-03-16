/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractSupplierEvaluateEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierEvaluateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAttchment;
    protected com.kingdee.bos.ctrl.swing.KDContainer conDemo;
    protected com.kingdee.bos.ctrl.swing.KDContainer conIsGrades;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBusinessDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtScore;
    protected com.kingdee.bos.ctrl.swing.KDTextField textSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDemo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtIsGrade;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSupplier;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMould;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSupplier;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemModel;
    protected com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo editData = null;
    protected ActionSupplierInfo actionSupplierInfo = null;
    protected ActionModelInfo actionModelInfo = null;
    /**
     * output class constructor
     */
    public AbstractSupplierEvaluateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierEvaluateEditUI.class.getName());
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
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionAudit
        actionAudit.setEnabled(false);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSupplierInfo
        this.actionSupplierInfo = new ActionSupplierInfo(this);
        getActionManager().registerAction("actionSupplierInfo", actionSupplierInfo);
         this.actionSupplierInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModelInfo
        this.actionModelInfo = new ActionModelInfo(this);
        getActionManager().registerAction("actionModelInfo", actionModelInfo);
         this.actionModelInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conSupplierType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conAttchment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conDemo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.conIsGrades = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBusinessDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.textSupplier = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSupplierType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtDemo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtIsGrade = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMould = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSupplier = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemModel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contProject.setName("contProject");
        this.contScore.setName("contScore");
        this.contSupplier.setName("contSupplier");
        this.conSupplierType.setName("conSupplierType");
        this.conAttchment.setName("conAttchment");
        this.conDemo.setName("conDemo");
        this.conIsGrades.setName("conIsGrades");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkBusinessDate.setName("pkBusinessDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtProject.setName("prmtProject");
        this.txtScore.setName("txtScore");
        this.textSupplier.setName("textSupplier");
        this.prmtSupplierType.setName("prmtSupplierType");
        this.kdtDemo.setName("kdtDemo");
        this.kdtIsGrade.setName("kdtIsGrade");
        this.btnSupplier.setName("btnSupplier");
        this.btnMould.setName("btnMould");
        this.menuItemSupplier.setName("menuItemSupplier");
        this.menuItemModel.setName("menuItemModel");
        // CoreUI		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnNext.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemPrint.setVisible(true);		
        this.menuItemPrintPreview.setVisible(true);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setDoubleBuffered(true);		
        this.btnAttachment.setAutoscrolls(true);		
        this.menuSubmitOption.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.kDMenuItemSendMessage.setVisible(false);		
        this.menuItemWorkFlowList.setVisible(false);		
        this.menuItemSendMail.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contScore		
        this.contScore.setBoundLabelText(resHelper.getString("contScore.boundLabelText"));		
        this.contScore.setBoundLabelLength(100);		
        this.contScore.setBoundLabelUnderline(true);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);
        // conSupplierType		
        this.conSupplierType.setBoundLabelText(resHelper.getString("conSupplierType.boundLabelText"));		
        this.conSupplierType.setBoundLabelUnderline(true);		
        this.conSupplierType.setBoundLabelLength(100);
        // conAttchment		
        this.conAttchment.setBoundLabelText(resHelper.getString("conAttchment.boundLabelText"));		
        this.conAttchment.setBoundLabelUnderline(true);		
        this.conAttchment.setBoundLabelLength(1000);
        // conDemo		
        this.conDemo.setTitle(resHelper.getString("conDemo.title"));		
        this.conDemo.setEnableActive(false);
        // conIsGrades		
        this.conIsGrades.setTitle(resHelper.getString("conIsGrades.title"));		
        this.conIsGrades.setEnableActive(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkBusinessDate		
        this.pkBusinessDate.setEditable(false);		
        this.pkBusinessDate.setRequired(true);
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtProject
        // txtScore		
        this.txtScore.setDataType(1);
        // textSupplier		
        this.textSupplier.setEnabled(false);
        // prmtSupplierType		
        this.prmtSupplierType.setRequired(true);
        this.prmtSupplierType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSupplierType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtSupplierType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSupplierType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtDemo
		String kdtDemoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"lat\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"index\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"percent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"criterion\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"syndic\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dept\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"date\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"info\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{lat}</t:Cell><t:Cell>$Resource{index}</t:Cell><t:Cell>$Resource{percent}</t:Cell><t:Cell>$Resource{criterion}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{syndic}</t:Cell><t:Cell>$Resource{dept}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{info}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtDemo.setFormatXml(resHelper.translateString("kdtDemo",kdtDemoStrXML));
        this.kdtDemo.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtDemo_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtDemo.putBindContents("editData",new String[] {"templateEntry.guideType","templateEntry.splAuditIndex","templateEntry.weight","templateEntry.fullNum","score","auditPerson","auditDept","auditTime","templateEntry"});


        // kdtIsGrade
		String kdtIsGradeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supplierType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"historyState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"isAutidor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"grade\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supplierType}</t:Cell><t:Cell>$Resource{historyState}</t:Cell><t:Cell>$Resource{isAutidor}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{grade}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtIsGrade.setFormatXml(resHelper.translateString("kdtIsGrade",kdtIsGradeStrXML));
        this.kdtIsGrade.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtIsGrade_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtIsGrade.putBindContents("editData",new String[] {"supplierType","beforeState","isAudit","score","grade"});


        // btnSupplier
        this.btnSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplierInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSupplier.setText(resHelper.getString("btnSupplier.text"));		
        this.btnSupplier.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sequencecheck"));
        // btnMould
        this.btnMould.setAction((IItemAction)ActionProxyFactory.getProxy(actionModelInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMould.setText(resHelper.getString("btnMould.text"));		
        this.btnMould.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_copy"));
        // menuItemSupplier
        this.menuItemSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplierInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSupplier.setText(resHelper.getString("menuItemSupplier.text"));		
        this.menuItemSupplier.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sequencecheck"));
        // menuItemModel
        this.menuItemModel.setAction((IItemAction)ActionProxyFactory.getProxy(actionModelInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemModel.setText(resHelper.getString("menuItemModel.text"));		
        this.menuItemModel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_copy"));
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
        contCreator.setBounds(new Rectangle(21, 557, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(21, 557, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(21, 593, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(21, 593, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(704, 43, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(704, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(21, 199, 961, 19));
        this.add(contDescription, new KDLayout.Constraints(21, 199, 961, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(707, 558, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(707, 558, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(707, 593, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(707, 593, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(21, 43, 270, 19));
        this.add(contProject, new KDLayout.Constraints(21, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScore.setBounds(new Rectangle(371, 43, 270, 19));
        this.add(contScore, new KDLayout.Constraints(371, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplier.setBounds(new Rectangle(21, 13, 270, 19));
        this.add(contSupplier, new KDLayout.Constraints(21, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conSupplierType.setBounds(new Rectangle(371, 13, 270, 19));
        this.add(conSupplierType, new KDLayout.Constraints(371, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conAttchment.setBounds(new Rectangle(21, 229, 961, 19));
        this.add(conAttchment, new KDLayout.Constraints(21, 229, 961, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conDemo.setBounds(new Rectangle(22, 259, 957, 287));
        this.add(conDemo, new KDLayout.Constraints(22, 259, 957, 287, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conIsGrades.setBounds(new Rectangle(22, 73, 956, 115));
        this.add(conIsGrades, new KDLayout.Constraints(22, 73, 956, 115, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contBizDate
        contBizDate.setBoundEditor(pkBusinessDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contScore
        contScore.setBoundEditor(txtScore);
        //contSupplier
        contSupplier.setBoundEditor(textSupplier);
        //conSupplierType
        conSupplierType.setBoundEditor(prmtSupplierType);
        //conDemo
conDemo.getContentPane().setLayout(new BorderLayout(0, 0));        conDemo.getContentPane().add(kdtDemo, BorderLayout.CENTER);
        //conIsGrades
conIsGrades.getContentPane().setLayout(new BorderLayout(0, 0));        conIsGrades.getContentPane().add(kdtIsGrade, BorderLayout.CENTER);

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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
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
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSupplier);
        menuBiz.add(menuItemModel);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
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
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnSupplier);
        this.toolBar.add(btnMould);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("businessDate", java.util.Date.class, this.pkBusinessDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("score", java.math.BigDecimal.class, this.txtScore, "value");
		dataBinder.registerBinding("supplier.name", String.class, this.textSupplier, "text");
		dataBinder.registerBinding("auditValue", com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditIndexValueInfo.class, this.kdtDemo, "userObject");
		dataBinder.registerBinding("auditValue.templateEntry.guideType", String.class, this.kdtDemo, "lat.text");
		dataBinder.registerBinding("auditValue.templateEntry.weight", java.math.BigDecimal.class, this.kdtDemo, "percent.text");
		dataBinder.registerBinding("auditValue.templateEntry.fullNum", String.class, this.kdtDemo, "criterion.text");
		dataBinder.registerBinding("auditValue.score", java.math.BigDecimal.class, this.kdtDemo, "score.text");
		dataBinder.registerBinding("auditValue.auditPerson", String.class, this.kdtDemo, "syndic.text");
		dataBinder.registerBinding("auditValue.auditDept", String.class, this.kdtDemo, "dept.text");
		dataBinder.registerBinding("auditValue.auditTime", java.util.Date.class, this.kdtDemo, "date.text");
		dataBinder.registerBinding("auditValue.templateEntry", com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo.class, this.kdtDemo, "info.text");
		dataBinder.registerBinding("auditValue.templateEntry.splAuditIndex", com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo.class, this.kdtDemo, "index.text");
		dataBinder.registerBinding("auditBill.supplierType", com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo.class, this.kdtIsGrade, "supplierType.text");
		dataBinder.registerBinding("auditBill.beforeState", String.class, this.kdtIsGrade, "historyState.text");
		dataBinder.registerBinding("auditBill.isAudit", boolean.class, this.kdtIsGrade, "isAutidor.text");
		dataBinder.registerBinding("auditBill.score", java.math.BigDecimal.class, this.kdtIsGrade, "score.text");
		dataBinder.registerBinding("auditBill.grade", String.class, this.kdtIsGrade, "grade.text");
		dataBinder.registerBinding("auditBill", com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryInfo.class, this.kdtIsGrade, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierEvaluateEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo)ov;
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
		getValidateHelper().registerBindProperty("businessDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.templateEntry.guideType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.templateEntry.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.templateEntry.fullNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.auditPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.auditDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.templateEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.templateEntry.splAuditIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.supplierType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.beforeState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.isAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.grade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill", ValidateHelper.ON_SAVE);    		
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
     * output prmtSupplierType_dataChanged method
     */
    protected void prmtSupplierType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSupplierType_willShow method
     */
    protected void prmtSupplierType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output kdtDemo_editStopped method
     */
    protected void kdtDemo_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtIsGrade_editStopped method
     */
    protected void kdtIsGrade_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("businessDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("score"));
        sic.add(new SelectorItemInfo("supplier.name"));
        sic.add(new SelectorItemInfo("auditValue.*"));
//        sic.add(new SelectorItemInfo("auditValue.number"));
    sic.add(new SelectorItemInfo("auditValue.templateEntry.guideType"));
    sic.add(new SelectorItemInfo("auditValue.templateEntry.weight"));
    sic.add(new SelectorItemInfo("auditValue.templateEntry.fullNum"));
    sic.add(new SelectorItemInfo("auditValue.score"));
    sic.add(new SelectorItemInfo("auditValue.auditPerson"));
    sic.add(new SelectorItemInfo("auditValue.auditDept"));
    sic.add(new SelectorItemInfo("auditValue.auditTime"));
        sic.add(new SelectorItemInfo("auditValue.templateEntry.*"));
//        sic.add(new SelectorItemInfo("auditValue.templateEntry.number"));
        sic.add(new SelectorItemInfo("auditValue.templateEntry.splAuditIndex.*"));
//        sic.add(new SelectorItemInfo("auditValue.templateEntry.splAuditIndex.number"));
        sic.add(new SelectorItemInfo("auditBill.supplierType.*"));
//        sic.add(new SelectorItemInfo("auditBill.supplierType.number"));
    sic.add(new SelectorItemInfo("auditBill.beforeState"));
    sic.add(new SelectorItemInfo("auditBill.isAudit"));
    sic.add(new SelectorItemInfo("auditBill.score"));
    sic.add(new SelectorItemInfo("auditBill.grade"));
        sic.add(new SelectorItemInfo("auditBill.*"));
//        sic.add(new SelectorItemInfo("auditBill.number"));
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionSupplierInfo_actionPerformed method
     */
    public void actionSupplierInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModelInfo_actionPerformed method
     */
    public void actionModelInfo_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionSupplierInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSupplierInfo() {
    	return false;
    }
	public RequestContext prepareActionModelInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModelInfo() {
    	return false;
    }

    /**
     * output ActionSupplierInfo class
     */     
    protected class ActionSupplierInfo extends ItemAction {     
    
        public ActionSupplierInfo()
        {
            this(null);
        }

        public ActionSupplierInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSupplierInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplierInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplierInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierEvaluateEditUI.this, "ActionSupplierInfo", "actionSupplierInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionModelInfo class
     */     
    protected class ActionModelInfo extends ItemAction {     
    
        public ActionModelInfo()
        {
            this(null);
        }

        public ActionModelInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionModelInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModelInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModelInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierEvaluateEditUI.this, "ActionModelInfo", "actionModelInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierEvaluateEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}