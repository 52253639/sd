/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractAimMeasureCostEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAimMeasureCostEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane plTables;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMeasureStage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer versionType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMeasureStage;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboVersionType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportTemplate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportApportion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportData;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportAll;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionDeleteRow actionDeleteRow = null;
    protected ActionImportApportion actionImportApportion = null;
    protected ActionImportTemplate actionImportTemplate = null;
    protected ActionExportAllToExcel actionExportAllToExcel = null;
    protected ActionImportData actionImportData = null;
    /**
     * output class constructor
     */
    public AbstractAimMeasureCostEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAimMeasureCostEditUI.class.getName());
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
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
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
        //actionPre
        actionPre.setEnabled(false);
        actionPre.setDaemonRun(false);

        actionPre.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl <"));
        _tempStr = resHelper.getString("ActionPre.SHORT_DESCRIPTION");
        actionPre.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPre.LONG_DESCRIPTION");
        actionPre.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPre.NAME");
        actionPre.putValue(ItemAction.NAME, _tempStr);
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionNext
        actionNext.setEnabled(false);
        actionNext.setDaemonRun(false);

        actionNext.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl >"));
        _tempStr = resHelper.getString("ActionNext.SHORT_DESCRIPTION");
        actionNext.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionNext.LONG_DESCRIPTION");
        actionNext.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionNext.NAME");
        actionNext.putValue(ItemAction.NAME, _tempStr);
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionLast
        actionLast.setEnabled(false);
        actionLast.setDaemonRun(false);

        actionLast.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl >"));
        _tempStr = resHelper.getString("ActionLast.SHORT_DESCRIPTION");
        actionLast.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLast.LONG_DESCRIPTION");
        actionLast.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLast.NAME");
        actionLast.putValue(ItemAction.NAME, _tempStr);
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmitOption
        actionSubmitOption.setEnabled(true);
        actionSubmitOption.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionSubmitOption.SHORT_DESCRIPTION");
        actionSubmitOption.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmitOption.LONG_DESCRIPTION");
        actionSubmitOption.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmitOption.NAME");
        actionSubmitOption.putValue(ItemAction.NAME, _tempStr);
         this.actionSubmitOption.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteRow
        this.actionDeleteRow = new ActionDeleteRow(this);
        getActionManager().registerAction("actionDeleteRow", actionDeleteRow);
         this.actionDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportApportion
        this.actionImportApportion = new ActionImportApportion(this);
        getActionManager().registerAction("actionImportApportion", actionImportApportion);
         this.actionImportApportion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportTemplate
        this.actionImportTemplate = new ActionImportTemplate(this);
        getActionManager().registerAction("actionImportTemplate", actionImportTemplate);
         this.actionImportTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportAllToExcel
        this.actionExportAllToExcel = new ActionExportAllToExcel(this);
        getActionManager().registerAction("actionExportAllToExcel", actionExportAllToExcel);
         this.actionExportAllToExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportData
        this.actionImportData = new ActionImportData(this);
        getActionManager().registerAction("actionImportData", actionImportData);
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.plTables = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contVersionNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMeasureStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.versionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVersionNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVersionName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProjectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboMeasureStage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboVersionType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnImportTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportApportion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImportData = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExportAll = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportTemplate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.plTables.setName("plTables");
        this.contVersionNumber.setName("contVersionNumber");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contMeasureStage.setName("contMeasureStage");
        this.versionType.setName("versionType");
        this.txtVersionNumber.setName("txtVersionNumber");
        this.txtVersionName.setName("txtVersionName");
        this.prmtProject.setName("prmtProject");
        this.prmtProjectType.setName("prmtProjectType");
        this.comboMeasureStage.setName("comboMeasureStage");
        this.comboVersionType.setName("comboVersionType");
        this.btnImportTemplate.setName("btnImportTemplate");
        this.btnImport.setName("btnImport");
        this.btnExportAll.setName("btnExportAll");
        this.btnAddRow.setName("btnAddRow");
        this.btnDeleteRow.setName("btnDeleteRow");
        this.btnImportApportion.setName("btnImportApportion");
        this.menuItemImportData.setName("menuItemImportData");
        this.menuItemExportAll.setName("menuItemExportAll");
        this.menuItemImportTemplate.setName("menuItemImportTemplate");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuItemPrint.setVisible(true);		
        this.menuItemPrintPreview.setVisible(true);		
        this.menuEdit.setVisible(false);		
        this.menuEdit.setEnabled(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setEnabled(false);		
        this.menuView.setVisible(false);		
        this.rMenuItemSubmit.setText(resHelper.getString("rMenuItemSubmit.text"));		
        this.rMenuItemSubmit.setToolTipText(resHelper.getString("rMenuItemSubmit.toolTipText"));		
        this.rMenuItemSubmitAndAddNew.setText(resHelper.getString("rMenuItemSubmitAndAddNew.text"));		
        this.rMenuItemSubmitAndAddNew.setToolTipText(resHelper.getString("rMenuItemSubmitAndAddNew.toolTipText"));		
        this.menuSubmitOption.setText(resHelper.getString("menuSubmitOption.text"));		
        this.chkMenuItemSubmitAndAddNew.setText(resHelper.getString("chkMenuItemSubmitAndAddNew.text"));		
        this.menuBiz.setVisible(false);		
        this.menuBiz.setEnabled(false);
        // plTables
        this.plTables.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    plTables_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contVersionNumber		
        this.contVersionNumber.setBoundLabelText(resHelper.getString("contVersionNumber.boundLabelText"));		
        this.contVersionNumber.setBoundLabelLength(100);		
        this.contVersionNumber.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contMeasureStage		
        this.contMeasureStage.setBoundLabelText(resHelper.getString("contMeasureStage.boundLabelText"));		
        this.contMeasureStage.setBoundLabelLength(100);		
        this.contMeasureStage.setBoundLabelUnderline(true);		
        this.contMeasureStage.setToolTipText(resHelper.getString("contMeasureStage.toolTipText"));
        // versionType		
        this.versionType.setBoundLabelText(resHelper.getString("versionType.boundLabelText"));		
        this.versionType.setBoundLabelLength(100);		
        this.versionType.setBoundLabelUnderline(true);
        // txtVersionNumber
        // txtVersionName		
        this.txtVersionName.setRequired(true);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setDisplayFormat("$number$-$name$");		
        this.prmtProject.setCommitFormat("$number$");
        // prmtProjectType		
        this.prmtProjectType.setCommitFormat("$number$");		
        this.prmtProjectType.setEditFormat("$number$");		
        this.prmtProjectType.setDisplayFormat("$number$-$name$");		
        this.prmtProjectType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProjectTypeQuery");		
        this.prmtProjectType.setRequired(true);
        // comboMeasureStage		
        this.comboMeasureStage.setRequired(true);
        this.comboMeasureStage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboMeasureStage_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboVersionType		
        this.comboVersionType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.aimcost.VersionTypeEnum").toArray());
        // btnImportTemplate
        this.btnImportTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportTemplate.setText(resHelper.getString("btnImportTemplate.text"));		
        this.btnImportTemplate.setToolTipText(resHelper.getString("btnImportTemplate.toolTipText"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));		
        this.btnImport.setToolTipText(resHelper.getString("btnImport.toolTipText"));
        // btnExportAll
        this.btnExportAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportAllToExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportAll.setText(resHelper.getString("btnExportAll.text"));		
        this.btnExportAll.setToolTipText(resHelper.getString("btnExportAll.toolTipText"));
        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));
        // btnDeleteRow
        this.btnDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteRow.setText(resHelper.getString("btnDeleteRow.text"));
        // btnImportApportion
        this.btnImportApportion.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportApportion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportApportion.setText(resHelper.getString("btnImportApportion.text"));
        // menuItemImportData
        this.menuItemImportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportData.setText(resHelper.getString("menuItemImportData.text"));		
        this.menuItemImportData.setToolTipText(resHelper.getString("menuItemImportData.toolTipText"));		
        this.menuItemImportData.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // menuItemExportAll
        this.menuItemExportAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportAllToExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExportAll.setText(resHelper.getString("menuItemExportAll.text"));		
        this.menuItemExportAll.setToolTipText(resHelper.getString("menuItemExportAll.toolTipText"));
        // menuItemImportTemplate
        this.menuItemImportTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportTemplate.setText(resHelper.getString("menuItemImportTemplate.text"));		
        this.menuItemImportTemplate.setToolTipText(resHelper.getString("menuItemImportTemplate.toolTipText"));
        // menuItemAddRow
        this.menuItemAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddRow.setText(resHelper.getString("menuItemAddRow.text"));
        // menuItemDeleteRow
        this.menuItemDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeleteRow.setText(resHelper.getString("menuItemDeleteRow.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtVersionName,prmtProjectType,prmtProject}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        plTables.setBounds(new Rectangle(10, 64, 995, 556));
        this.add(plTables, new KDLayout.Constraints(10, 64, 995, 556, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contVersionNumber.setBounds(new Rectangle(372, 41, 270, 19));
        this.add(contVersionNumber, new KDLayout.Constraints(372, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(735, 41, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(735, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer3.setBounds(new Rectangle(735, 14, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(735, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(372, 14, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(372, 14, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMeasureStage.setBounds(new Rectangle(10, 14, 270, 19));
        this.add(contMeasureStage, new KDLayout.Constraints(10, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        versionType.setBounds(new Rectangle(10, 41, 270, 19));
        this.add(versionType, new KDLayout.Constraints(10, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contVersionNumber
        contVersionNumber.setBoundEditor(txtVersionNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtVersionName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(prmtProject);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtProjectType);
        //contMeasureStage
        contMeasureStage.setBoundEditor(comboMeasureStage);
        //versionType
        versionType.setBoundEditor(comboVersionType);

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
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportAll);
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
        menuEdit.add(menuItemImportTemplate);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(menuItemAddRow);
        menuEdit.add(menuItemDeleteRow);
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
        this.toolBar.add(btnImportTemplate);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnExportAll);
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
        this.toolBar.add(btnAddRow);
        this.toolBar.add(btnDeleteRow);
        this.toolBar.add(btnImportApportion);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.AimMeasureCostEditUIHandler";
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
        this.txtVersionName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
     * output plTables_stateChanged method
     */
    protected void plTables_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output comboMeasureStage_itemStateChanged method
     */
    protected void comboMeasureStage_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPre_actionPerformed method
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }
    	

    /**
     * output actionNext_actionPerformed method
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }
    	

    /**
     * output actionLast_actionPerformed method
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }
    	

    /**
     * output actionSubmitOption_actionPerformed method
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteRow_actionPerformed method
     */
    public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportApportion_actionPerformed method
     */
    public void actionImportApportion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportTemplate_actionPerformed method
     */
    public void actionImportTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportAllToExcel_actionPerformed method
     */
    public void actionExportAllToExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportData_actionPerformed method
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionPre(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPre(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPre() {
    	return false;
    }
	public RequestContext prepareActionNext(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionNext(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNext() {
    	return false;
    }
	public RequestContext prepareActionLast(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionLast(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLast() {
    	return false;
    }
	public RequestContext prepareActionSubmitOption(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmitOption(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitOption() {
    	return false;
    }
	public RequestContext prepareActionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRow() {
    	return false;
    }
	public RequestContext prepareActionDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteRow() {
    	return false;
    }
	public RequestContext prepareActionImportApportion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportApportion() {
    	return false;
    }
	public RequestContext prepareActionImportTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportTemplate() {
    	return false;
    }
	public RequestContext prepareActionExportAllToExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportAllToExcel() {
    	return false;
    }
	public RequestContext prepareActionImportData(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportData() {
    	return false;
    }

    /**
     * output ActionAddRow class
     */     
    protected class ActionAddRow extends ItemAction {     
    
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimMeasureCostEditUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteRow class
     */     
    protected class ActionDeleteRow extends ItemAction {     
    
        public ActionDeleteRow()
        {
            this(null);
        }

        public ActionDeleteRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimMeasureCostEditUI.this, "ActionDeleteRow", "actionDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output ActionImportApportion class
     */     
    protected class ActionImportApportion extends ItemAction {     
    
        public ActionImportApportion()
        {
            this(null);
        }

        public ActionImportApportion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportApportion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportApportion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportApportion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimMeasureCostEditUI.this, "ActionImportApportion", "actionImportApportion_actionPerformed", e);
        }
    }

    /**
     * output ActionImportTemplate class
     */     
    protected class ActionImportTemplate extends ItemAction {     
    
        public ActionImportTemplate()
        {
            this(null);
        }

        public ActionImportTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimMeasureCostEditUI.this, "ActionImportTemplate", "actionImportTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionExportAllToExcel class
     */     
    protected class ActionExportAllToExcel extends ItemAction {     
    
        public ActionExportAllToExcel()
        {
            this(null);
        }

        public ActionExportAllToExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportAllToExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportAllToExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportAllToExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimMeasureCostEditUI.this, "ActionExportAllToExcel", "actionExportAllToExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionImportData class
     */     
    protected class ActionImportData extends ItemAction {     
    
        public ActionImportData()
        {
            this(null);
        }

        public ActionImportData(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportData.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportData.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportData.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimMeasureCostEditUI.this, "ActionImportData", "actionImportData_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "AimMeasureCostEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}