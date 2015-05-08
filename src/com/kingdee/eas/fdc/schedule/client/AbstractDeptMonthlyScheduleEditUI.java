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
public abstract class AbstractDeptMonthlyScheduleEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeptMonthlyScheduleEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheduleMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdconTasks;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkScheduleMonth;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtYear;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMonth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTasks;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdtTransmitExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton importProjectPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton importUnFinishTask;
    protected com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo editData = null;
    protected ActionExportData actionExportData = null;
    protected ActionView actionView = null;
    protected ActionImportProjectPlan actionImportProjectPlan = null;
    protected ActionImportUnFinishTask actionImportUnFinishTask = null;
    /**
     * output class constructor
     */
    public AbstractDeptMonthlyScheduleEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDeptMonthlyScheduleEditUI.class.getName());
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
        //actionUnAudit
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportData
        this.actionExportData = new ActionExportData(this);
        getActionManager().registerAction("actionExportData", actionExportData);
         this.actionExportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionView
        this.actionView = new ActionView(this);
        getActionManager().registerAction("actionView", actionView);
         this.actionView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportProjectPlan
        this.actionImportProjectPlan = new ActionImportProjectPlan(this);
        getActionManager().registerAction("actionImportProjectPlan", actionImportProjectPlan);
         this.actionImportProjectPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportUnFinishTask
        this.actionImportUnFinishTask = new ActionImportUnFinishTask(this);
        getActionManager().registerAction("actionImportUnFinishTask", actionImportUnFinishTask);
         this.actionImportUnFinishTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contScheduleMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdconTasks = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.pkScheduleMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtYear = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMonth = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kdtTasks = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdtTransmitExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.importProjectPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.importUnFinishTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contScheduleMonth.setName("contScheduleMonth");
        this.contAdminDept.setName("contAdminDept");
        this.contYear.setName("contYear");
        this.contMonth.setName("contMonth");
        this.contState.setName("contState");
        this.kdconTasks.setName("kdconTasks");
        this.pkScheduleMonth.setName("pkScheduleMonth");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.txtYear.setName("txtYear");
        this.txtMonth.setName("txtMonth");
        this.comboState.setName("comboState");
        this.kdtTasks.setName("kdtTasks");
        this.btnView.setName("btnView");
        this.kdtTransmitExcel.setName("kdtTransmitExcel");
        this.importProjectPlan.setName("importProjectPlan");
        this.importUnFinishTask.setName("importUnFinishTask");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemRemove.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contScheduleMonth		
        this.contScheduleMonth.setBoundLabelText(resHelper.getString("contScheduleMonth.boundLabelText"));		
        this.contScheduleMonth.setBoundLabelLength(100);		
        this.contScheduleMonth.setBoundLabelUnderline(true);
        // contAdminDept		
        this.contAdminDept.setBoundLabelText(resHelper.getString("contAdminDept.boundLabelText"));		
        this.contAdminDept.setBoundLabelLength(100);		
        this.contAdminDept.setBoundLabelUnderline(true);
        // contYear		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(100);		
        this.contYear.setBoundLabelUnderline(true);		
        this.contYear.setVisible(false);
        // contMonth		
        this.contMonth.setBoundLabelText(resHelper.getString("contMonth.boundLabelText"));		
        this.contMonth.setBoundLabelLength(100);		
        this.contMonth.setBoundLabelUnderline(true);		
        this.contMonth.setVisible(false);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // kdconTasks		
        this.kdconTasks.setTitle(resHelper.getString("kdconTasks.title"));
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
        // prmtAdminDept
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
        // txtYear
        // txtMonth
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);
        this.comboState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboState_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.comboState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboState_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtTasks
		String kdtTasksStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>#,##0</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskType\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"taskName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminPerson\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"finishStandard\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"planFinishDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"weightRate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"relateTask\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"project\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"taskOrigin\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"projectPeriod\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"planStartDate\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"requiredResource\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"complete\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskType}</t:Cell><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{finishStandard}</t:Cell><t:Cell>$Resource{planFinishDate}</t:Cell><t:Cell>$Resource{weightRate}</t:Cell><t:Cell>$Resource{relateTask}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{taskOrigin}</t:Cell><t:Cell>$Resource{projectPeriod}</t:Cell><t:Cell>$Resource{planStartDate}</t:Cell><t:Cell>$Resource{requiredResource}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{complete}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtTasks.setFormatXml(resHelper.translateString("kdtTasks",kdtTasksStrXML));
        this.kdtTasks.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtTasks_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtTasks.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtTasks_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtTasks.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtTasks_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtTasks.putBindContents("editData",new String[] {"taskType","taskName","","finishStandard","planFinishDate","weightRate","relatedTask.name","project.name","taskOrigin","projectPeriod","planStartDate","requiredResource","id","complete"});


        // btnView
        this.btnView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnView.setText(resHelper.getString("btnView.text"));
        // kdtTransmitExcel
        this.kdtTransmitExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdtTransmitExcel.setText(resHelper.getString("kdtTransmitExcel.text"));		
        this.kdtTransmitExcel.setToolTipText(resHelper.getString("kdtTransmitExcel.toolTipText"));
        // importProjectPlan
        this.importProjectPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportProjectPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.importProjectPlan.setText(resHelper.getString("importProjectPlan.text"));
        // importUnFinishTask
        this.importUnFinishTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportUnFinishTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.importUnFinishTask.setText(resHelper.getString("importUnFinishTask.text"));
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
        contScheduleMonth.setBounds(new Rectangle(350, 25, 270, 19));
        this.add(contScheduleMonth, new KDLayout.Constraints(350, 25, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        contAdminDept.setBounds(new Rectangle(15, 25, 270, 19));
        this.add(contAdminDept, new KDLayout.Constraints(15, 25, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contYear.setBounds(new Rectangle(61, 522, 270, 19));
        this.add(contYear, new KDLayout.Constraints(61, 522, 270, 19, 0));
        contMonth.setBounds(new Rectangle(399, 515, 270, 19));
        this.add(contMonth, new KDLayout.Constraints(399, 515, 270, 19, 0));
        contState.setBounds(new Rectangle(728, 25, 270, 19));
        this.add(contState, new KDLayout.Constraints(728, 25, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdconTasks.setBounds(new Rectangle(15, 68, 980, 550));
        this.add(kdconTasks, new KDLayout.Constraints(15, 68, 980, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contScheduleMonth
        contScheduleMonth.setBoundEditor(pkScheduleMonth);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contYear
        contYear.setBoundEditor(txtYear);
        //contMonth
        contMonth.setBoundEditor(txtMonth);
        //contState
        contState.setBoundEditor(comboState);
        //kdconTasks
        kdconTasks.getContentPane().setLayout(new KDLayout());
        kdconTasks.getContentPane().putClientProperty("OriginalBounds", new Rectangle(15, 68, 980, 550));        kdtTasks.setBounds(new Rectangle(2, 1, 978, 548));
        kdconTasks.getContentPane().add(kdtTasks, new KDLayout.Constraints(2, 1, 978, 548, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnView);
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
        this.toolBar.add(kdtTransmitExcel);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(importProjectPlan);
        this.toolBar.add(importUnFinishTask);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("scheduleMonth", java.util.Date.class, this.pkScheduleMonth, "value");
		dataBinder.registerBinding("adminDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtAdminDept, "data");
		dataBinder.registerBinding("year", int.class, this.txtYear, "value");
		dataBinder.registerBinding("month", int.class, this.txtMonth, "value");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("tasks.taskType", com.kingdee.util.enums.Enum.class, this.kdtTasks, "taskType.text");
		dataBinder.registerBinding("tasks.taskName", String.class, this.kdtTasks, "taskName.text");
		dataBinder.registerBinding("tasks.finishStandard", String.class, this.kdtTasks, "finishStandard.text");
		dataBinder.registerBinding("tasks.planFinishDate", java.util.Date.class, this.kdtTasks, "planFinishDate.text");
		dataBinder.registerBinding("tasks.weightRate", java.math.BigDecimal.class, this.kdtTasks, "weightRate.text");
		dataBinder.registerBinding("tasks.taskOrigin", com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum.class, this.kdtTasks, "taskOrigin.text");
		dataBinder.registerBinding("tasks.projectPeriod", java.math.BigDecimal.class, this.kdtTasks, "projectPeriod.text");
		dataBinder.registerBinding("tasks.planStartDate", java.util.Date.class, this.kdtTasks, "planStartDate.text");
		dataBinder.registerBinding("tasks.requiredResource", String.class, this.kdtTasks, "requiredResource.text");
		dataBinder.registerBinding("tasks", com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo.class, this.kdtTasks, "userObject");
		dataBinder.registerBinding("tasks.id", com.kingdee.bos.util.BOSUuid.class, this.kdtTasks, "id.text");
		dataBinder.registerBinding("tasks.complete", java.math.BigDecimal.class, this.kdtTasks, "complete.text");
		dataBinder.registerBinding("tasks.relatedTask.name", String.class, this.kdtTasks, "relateTask.text");
		dataBinder.registerBinding("tasks.project.name", String.class, this.kdtTasks, "project.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.DeptMonthlyScheduleEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo)ov;
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
		getValidateHelper().registerBindProperty("scheduleMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.taskName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.finishStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.planFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.weightRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.taskOrigin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.projectPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.planStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.requiredResource", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.complete", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.relatedTask.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tasks.project.name", ValidateHelper.ON_SAVE);    		
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
     * output pkScheduleMonth_dataChanged method
     */
    protected void pkScheduleMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtAdminDept_dataChanged method
     */
    protected void prmtAdminDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboState_actionPerformed method
     */
    protected void comboState_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboState_itemStateChanged method
     */
    protected void comboState_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kdtTasks_tableClicked method
     */
    protected void kdtTasks_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtTasks_editStopped method
     */
    protected void kdtTasks_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtTasks_tableSelectChanged method
     */
    protected void kdtTasks_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("scheduleMonth"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("adminDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("adminDept.id"));
        	sic.add(new SelectorItemInfo("adminDept.number"));
        	sic.add(new SelectorItemInfo("adminDept.name"));
		}
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("month"));
        sic.add(new SelectorItemInfo("state"));
    	sic.add(new SelectorItemInfo("tasks.taskType"));
    	sic.add(new SelectorItemInfo("tasks.taskName"));
    	sic.add(new SelectorItemInfo("tasks.finishStandard"));
    	sic.add(new SelectorItemInfo("tasks.planFinishDate"));
    	sic.add(new SelectorItemInfo("tasks.weightRate"));
    	sic.add(new SelectorItemInfo("tasks.taskOrigin"));
    	sic.add(new SelectorItemInfo("tasks.projectPeriod"));
    	sic.add(new SelectorItemInfo("tasks.planStartDate"));
    	sic.add(new SelectorItemInfo("tasks.requiredResource"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("tasks.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("tasks.id"));
    	sic.add(new SelectorItemInfo("tasks.complete"));
    	sic.add(new SelectorItemInfo("tasks.relatedTask.name"));
    	sic.add(new SelectorItemInfo("tasks.project.name"));
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
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionExportData_actionPerformed method
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionView_actionPerformed method
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportProjectPlan_actionPerformed method
     */
    public void actionImportProjectPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportUnFinishTask_actionPerformed method
     */
    public void actionImportUnFinishTask_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionExportData(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportData() {
    	return false;
    }
	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView() {
    	return false;
    }
	public RequestContext prepareActionImportProjectPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportProjectPlan() {
    	return false;
    }
	public RequestContext prepareActionImportUnFinishTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportUnFinishTask() {
    	return false;
    }

    /**
     * output ActionExportData class
     */     
    protected class ActionExportData extends ItemAction {     
    
        public ActionExportData()
        {
            this(null);
        }

        public ActionExportData(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportData.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportData.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportData.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyScheduleEditUI.this, "ActionExportData", "actionExportData_actionPerformed", e);
        }
    }

    /**
     * output ActionView class
     */     
    protected class ActionView extends ItemAction {     
    
        public ActionView()
        {
            this(null);
        }

        public ActionView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyScheduleEditUI.this, "ActionView", "actionView_actionPerformed", e);
        }
    }

    /**
     * output ActionImportProjectPlan class
     */     
    protected class ActionImportProjectPlan extends ItemAction {     
    
        public ActionImportProjectPlan()
        {
            this(null);
        }

        public ActionImportProjectPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportProjectPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportProjectPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportProjectPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyScheduleEditUI.this, "ActionImportProjectPlan", "actionImportProjectPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionImportUnFinishTask class
     */     
    protected class ActionImportUnFinishTask extends ItemAction {     
    
        public ActionImportUnFinishTask()
        {
            this(null);
        }

        public ActionImportUnFinishTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportUnFinishTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportUnFinishTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportUnFinishTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDeptMonthlyScheduleEditUI.this, "ActionImportUnFinishTask", "actionImportUnFinishTask_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "DeptMonthlyScheduleEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}