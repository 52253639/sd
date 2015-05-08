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
public abstract class AbstractMainScheduleEditUI extends com.kingdee.eas.fdc.schedule.client.FDCScheduleBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMainScheduleEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectStage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectStage;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSetCheckVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSetCheckVersion;
    protected com.kingdee.eas.fdc.schedule.FDCScheduleInfo editData = null;
    protected ActionSetCheckVersion actionSetCheckVersion = null;
    /**
     * output class constructor
     */
    public AbstractMainScheduleEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMainScheduleEditUI.class.getName());
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
        //actionExportPlanTemplate
        actionExportPlanTemplate.setEnabled(false);
        actionExportPlanTemplate.setDaemonRun(false);

        _tempStr = resHelper.getString("actionExportPlanTemplate.SHORT_DESCRIPTION");
        actionExportPlanTemplate.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionExportPlanTemplate.LONG_DESCRIPTION");
        actionExportPlanTemplate.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionExportPlanTemplate.NAME");
        actionExportPlanTemplate.putValue(ItemAction.NAME, _tempStr);
         this.actionExportPlanTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportPlanTemplate
        actionImportPlanTemplate.setEnabled(true);
        actionImportPlanTemplate.setDaemonRun(false);

        _tempStr = resHelper.getString("actionImportPlanTemplate.SHORT_DESCRIPTION");
        actionImportPlanTemplate.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionImportPlanTemplate.LONG_DESCRIPTION");
        actionImportPlanTemplate.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionImportPlanTemplate.NAME");
        actionImportPlanTemplate.putValue(ItemAction.NAME, _tempStr);
         this.actionImportPlanTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSetCheckVersion
        this.actionSetCheckVersion = new ActionSetCheckVersion(this);
        getActionManager().registerAction("actionSetCheckVersion", actionSetCheckVersion);
         this.actionSetCheckVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contProjectStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProjectStage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnSetCheckVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSetCheckVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contProjectStage.setName("contProjectStage");
        this.prmtProjectStage.setName("prmtProjectStage");
        this.btnSetCheckVersion.setName("btnSetCheckVersion");
        this.menuItemSetCheckVersion.setName("menuItemSetCheckVersion");
        // CoreUI		
        this.contCurProject.setBoundLabelLength(80);		
        this.contVersion.setBoundLabelLength(80);		
        this.contState.setBoundLabelLength(50);		
        this.contState.setEnabled(false);		
        this.prmtCurproject.setRequired(true);		
        this.cbState.setEnabled(false);
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"workDayPre\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"workDayDep\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"workDayDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"planStartPre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"planStartDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"planStartDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"planEndPre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"planEndDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"planEndDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"checkDatePre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"checkDateDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"checkDateDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{workDayPre}</t:Cell><t:Cell>$Resource{workDayDep}</t:Cell><t:Cell>$Resource{workDayDiff}</t:Cell><t:Cell>$Resource{planStartPre}</t:Cell><t:Cell>$Resource{planStartDep}</t:Cell><t:Cell>$Resource{planStartDiff}</t:Cell><t:Cell>$Resource{planEndPre}</t:Cell><t:Cell>$Resource{planEndDep}</t:Cell><t:Cell>$Resource{planEndDiff}</t:Cell><t:Cell>$Resource{checkDatePre}</t:Cell><t:Cell>$Resource{checkDateDep}</t:Cell><t:Cell>$Resource{checkDateDiff}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{level}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName_Row2}</t:Cell><t:Cell>$Resource{workDayPre_Row2}</t:Cell><t:Cell>$Resource{workDayDep_Row2}</t:Cell><t:Cell>$Resource{workDayDiff_Row2}</t:Cell><t:Cell>$Resource{planStartPre_Row2}</t:Cell><t:Cell>$Resource{planStartDep_Row2}</t:Cell><t:Cell>$Resource{planStartDiff_Row2}</t:Cell><t:Cell>$Resource{planEndPre_Row2}</t:Cell><t:Cell>$Resource{planEndDep_Row2}</t:Cell><t:Cell>$Resource{planEndDiff_Row2}</t:Cell><t:Cell>$Resource{checkDatePre_Row2}</t:Cell><t:Cell>$Resource{checkDateDep_Row2}</t:Cell><t:Cell>$Resource{checkDateDiff_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{isLeaf_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"1\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"1\" t:right=\"15\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

        this.btnImportPlanTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportPlanTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportPlanTemplate.setText(resHelper.getString("btnImportPlanTemplate.text"));		
        this.btnImportPlanTemplate.setToolTipText(resHelper.getString("btnImportPlanTemplate.toolTipText"));
        this.btnExportPlanTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPlanTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportPlanTemplate.setText(resHelper.getString("btnExportPlanTemplate.text"));
        // contProjectStage		
        this.contProjectStage.setBoundLabelText(resHelper.getString("contProjectStage.boundLabelText"));		
        this.contProjectStage.setBoundLabelUnderline(true);		
        this.contProjectStage.setBoundLabelLength(50);
        // prmtProjectStage		
        this.prmtProjectStage.setQueryInfo("com.kingdee.eas.fdc.basedata.app.MeasureStageQuery");
        this.prmtProjectStage.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProjectStage_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtProjectStage.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProjectStage_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnSetCheckVersion
        this.btnSetCheckVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetCheckVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSetCheckVersion.setText(resHelper.getString("btnSetCheckVersion.text"));
        // menuItemSetCheckVersion
        this.menuItemSetCheckVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetCheckVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSetCheckVersion.setText(resHelper.getString("menuItemSetCheckVersion.text"));
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
        mainPanel.setBounds(new Rectangle(12, 67, 878, 526));
        this.add(mainPanel, new KDLayout.Constraints(12, 67, 878, 526, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(12, 13, 497, 19));
        this.add(contCurProject, new KDLayout.Constraints(12, 13, 497, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersion.setBounds(new Rectangle(11, 39, 498, 19));
        this.add(contVersion, new KDLayout.Constraints(11, 39, 498, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(574, 39, 316, 19));
        this.add(contState, new KDLayout.Constraints(574, 39, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(295, 551, 270, 19));
        this.add(contProject, new KDLayout.Constraints(295, 551, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAdminDept.setBounds(new Rectangle(14, 548, 270, 19));
        this.add(contAdminDept, new KDLayout.Constraints(14, 548, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAdminPerson.setBounds(new Rectangle(693, 1, 8, 8));
        this.add(contAdminPerson, new KDLayout.Constraints(693, 1, 8, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(126, 77, 767, 300));
        this.add(kDPanel1, new KDLayout.Constraints(126, 77, 767, 300, 0));
        contProjectStage.setBounds(new Rectangle(574, 13, 316, 19));
        this.add(contProjectStage, new KDLayout.Constraints(574, 13, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //contProjectStage
        contProjectStage.setBoundEditor(prmtProjectStage);

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
        menuBiz.add(menuItemSetCheckVersion);
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
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnBatchModifyTaskType);
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
        this.toolBar.add(btnImportPlanTemplate);
        this.toolBar.add(btnExportPlanTemplate);
        this.toolBar.add(btnImportProject);
        this.toolBar.add(btnExportProject);
        this.toolBar.add(btnHisVerion);
        this.toolBar.add(btnCompareVer);
        this.toolBar.add(btnSetCheckVersion);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.MainScheduleEditUIHandler";
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
     * output prmtProjectStage_dataChanged method
     */
    protected void prmtProjectStage_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProjectStage_willShow method
     */
    protected void prmtProjectStage_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
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
     * output actionExportPlanTemplate_actionPerformed method
     */
    public void actionExportPlanTemplate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportPlanTemplate_actionPerformed(e);
    }
    	

    /**
     * output actionImportPlanTemplate_actionPerformed method
     */
    public void actionImportPlanTemplate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportPlanTemplate_actionPerformed(e);
    }
    	

    /**
     * output actionSetCheckVersion_actionPerformed method
     */
    public void actionSetCheckVersion_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareactionExportPlanTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionExportPlanTemplate(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionExportPlanTemplate() {
    	return false;
    }
	public RequestContext prepareactionImportPlanTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionImportPlanTemplate(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImportPlanTemplate() {
    	return false;
    }
	public RequestContext prepareActionSetCheckVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSetCheckVersion() {
    	return false;
    }

    /**
     * output ActionSetCheckVersion class
     */     
    protected class ActionSetCheckVersion extends ItemAction {     
    
        public ActionSetCheckVersion()
        {
            this(null);
        }

        public ActionSetCheckVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSetCheckVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetCheckVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetCheckVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMainScheduleEditUI.this, "ActionSetCheckVersion", "actionSetCheckVersion_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "MainScheduleEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}