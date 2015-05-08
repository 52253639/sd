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
public abstract class AbstractRESchTemplateEditUI extends com.kingdee.eas.fdc.basedata.client.FDCTreeBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRESchTemplateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contParent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddChildTask;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParent;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comTemplateType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTask;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSetStdDuration;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportProject;
    protected com.kingdee.eas.fdc.schedule.RESchTemplateInfo editData = null;
    protected ActionAddTask actionAddTask = null;
    protected ActionRemoveTask actionRemoveTask = null;
    protected ActionSetStdDuration actionSetStdDuration = null;
    protected ActionImportProject actionImportProject = null;
    protected ActionImportTemplate actionImportTemplate = null;
    protected actionExportProject actionExportProject = null;
    protected actionAddChildTask actionAddChildTask = null;
    /**
     * output class constructor
     */
    public AbstractRESchTemplateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRESchTemplateEditUI.class.getName());
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
        //actionAddTask
        this.actionAddTask = new ActionAddTask(this);
        getActionManager().registerAction("actionAddTask", actionAddTask);
         this.actionAddTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveTask
        this.actionRemoveTask = new ActionRemoveTask(this);
        getActionManager().registerAction("actionRemoveTask", actionRemoveTask);
         this.actionRemoveTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSetStdDuration
        this.actionSetStdDuration = new ActionSetStdDuration(this);
        getActionManager().registerAction("actionSetStdDuration", actionSetStdDuration);
         this.actionSetStdDuration.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportProject
        this.actionImportProject = new ActionImportProject(this);
        getActionManager().registerAction("actionImportProject", actionImportProject);
         this.actionImportProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportTemplate
        this.actionImportTemplate = new ActionImportTemplate(this);
        getActionManager().registerAction("actionImportTemplate", actionImportTemplate);
         this.actionImportTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportProject
        this.actionExportProject = new actionExportProject(this);
        getActionManager().registerAction("actionExportProject", actionExportProject);
         this.actionExportProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddChildTask
        this.actionAddChildTask = new actionAddChildTask(this);
        getActionManager().registerAction("actionAddChildTask", actionAddChildTask);
         this.actionAddChildTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contParent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnAddTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddChildTask = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtParent = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comTemplateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblTask = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnImportPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSetStdDuration = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contParent.setName("contParent");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contTask.setName("contTask");
        this.btnAddTask.setName("btnAddTask");
        this.btnRemoveTask.setName("btnRemoveTask");
        this.btnAddChildTask.setName("btnAddChildTask");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.prmtParent.setName("prmtParent");
        this.comTemplateType.setName("comTemplateType");
        this.tblTask.setName("tblTask");
        this.btnImportPlan.setName("btnImportPlan");
        this.btnImportProject.setName("btnImportProject");
        this.btnSetStdDuration.setName("btnSetStdDuration");
        this.btnExportProject.setName("btnExportProject");
        // CoreUI		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
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
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contParent		
        this.contParent.setBoundLabelText(resHelper.getString("contParent.boundLabelText"));		
        this.contParent.setBoundLabelLength(100);		
        this.contParent.setBoundLabelUnderline(true);		
        this.contParent.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setVisible(false);
        // contTask		
        this.contTask.setTitle(resHelper.getString("contTask.title"));
        // btnAddTask
        this.btnAddTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTask.setText(resHelper.getString("btnAddTask.text"));
        // btnRemoveTask
        this.btnRemoveTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveTask.setText(resHelper.getString("btnRemoveTask.text"));
        // btnAddChildTask
        this.btnAddChildTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddChildTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddChildTask.setText(resHelper.getString("btnAddChildTask.text"));
        // txtName		
        this.txtName.setRequired(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtDescription		
        this.txtDescription.setVisible(false);
        // prmtParent		
        this.prmtParent.setRequired(true);
        // comTemplateType
        // tblTask
		String tblTaskStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"taskName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"taskNumber\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"preTask\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"taskType\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"bizType\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"duration\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{taskNumber}</t:Cell><t:Cell>$Resource{preTask}</t:Cell><t:Cell>$Resource{taskType}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{duration}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblTask.setFormatXml(resHelper.translateString("tblTask",tblTaskStrXML));
        this.tblTask.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTask_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnImportPlan
        this.btnImportPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportPlan.setText(resHelper.getString("btnImportPlan.text"));		
        this.btnImportPlan.setVisible(false);		
        this.btnImportPlan.setToolTipText(resHelper.getString("btnImportPlan.toolTipText"));
        // btnImportProject
        this.btnImportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportProject.setText(resHelper.getString("btnImportProject.text"));		
        this.btnImportProject.setToolTipText(resHelper.getString("btnImportProject.toolTipText"));
        // btnSetStdDuration
        this.btnSetStdDuration.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetStdDuration, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSetStdDuration.setText(resHelper.getString("btnSetStdDuration.text"));		
        this.btnSetStdDuration.setVisible(false);
        // btnExportProject
        this.btnExportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportProject.setText(resHelper.getString("btnExportProject.text"));		
        this.btnExportProject.setEnabled(false);		
        this.btnExportProject.setVisible(false);
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
        this.setBounds(new Rectangle(10, 10, 796, 566));
        this.setLayout(null);
        contName.setBounds(new Rectangle(18, 16, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(510, 16, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(123, 561, 560, 82));
        this.add(contDescription, null);
        contParent.setBounds(new Rectangle(-2, 560, 270, 19));
        this.add(contParent, null);
        kDLabelContainer1.setBounds(new Rectangle(711, 563, 270, 19));
        this.add(kDLabelContainer1, null);
        contTask.setBounds(new Rectangle(8, 48, 783, 509));
        this.add(contTask, null);
        btnAddTask.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnAddTask, null);
        btnRemoveTask.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnRemoveTask, null);
        btnAddChildTask.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnAddChildTask, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contParent
        contParent.setBoundEditor(prmtParent);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comTemplateType);
        //contTask
contTask.getContentPane().setLayout(new BorderLayout(0, 0));        contTask.getContentPane().add(tblTask, BorderLayout.CENTER);

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
        this.toolBar.add(btnImportPlan);
        this.toolBar.add(btnImportProject);
        this.toolBar.add(btnSetStdDuration);
        this.toolBar.add(btnExportProject);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("parent", com.kingdee.eas.fdc.schedule.RESchTemplateInfo.class, this.prmtParent, "data");
		dataBinder.registerBinding("templateType", com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum.class, this.comTemplateType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.RESchTemplateEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.RESchTemplateInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("templateType", ValidateHelper.ON_SAVE);    		
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
     * output tblTask_tableClicked method
     */
    protected void tblTask_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("parent.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("parent.id"));
        	sic.add(new SelectorItemInfo("parent.number"));
        	sic.add(new SelectorItemInfo("parent.name"));
		}
        sic.add(new SelectorItemInfo("templateType"));
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
     * output actionAddTask_actionPerformed method
     */
    public void actionAddTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveTask_actionPerformed method
     */
    public void actionRemoveTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetStdDuration_actionPerformed method
     */
    public void actionSetStdDuration_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportProject_actionPerformed method
     */
    public void actionImportProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportTemplate_actionPerformed method
     */
    public void actionImportTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportProject_actionPerformed method
     */
    public void actionExportProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddChildTask_actionPerformed method
     */
    public void actionAddChildTask_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTask() {
    	return false;
    }
	public RequestContext prepareActionRemoveTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveTask() {
    	return false;
    }
	public RequestContext prepareActionSetStdDuration(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSetStdDuration() {
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
	public RequestContext prepareactionExportProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionExportProject() {
    	return false;
    }
	public RequestContext prepareactionAddChildTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddChildTask() {
    	return false;
    }

    /**
     * output ActionAddTask class
     */     
    protected class ActionAddTask extends ItemAction {     
    
        public ActionAddTask()
        {
            this(null);
        }

        public ActionAddTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "ActionAddTask", "actionAddTask_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveTask class
     */     
    protected class ActionRemoveTask extends ItemAction {     
    
        public ActionRemoveTask()
        {
            this(null);
        }

        public ActionRemoveTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "ActionRemoveTask", "actionRemoveTask_actionPerformed", e);
        }
    }

    /**
     * output ActionSetStdDuration class
     */     
    protected class ActionSetStdDuration extends ItemAction {     
    
        public ActionSetStdDuration()
        {
            this(null);
        }

        public ActionSetStdDuration(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSetStdDuration.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetStdDuration.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetStdDuration.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "ActionSetStdDuration", "actionSetStdDuration_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "ActionImportProject", "actionImportProject_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "ActionImportTemplate", "actionImportTemplate_actionPerformed", e);
        }
    }

    /**
     * output actionExportProject class
     */     
    protected class actionExportProject extends ItemAction {     
    
        public actionExportProject()
        {
            this(null);
        }

        public actionExportProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionExportProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "actionExportProject", "actionExportProject_actionPerformed", e);
        }
    }

    /**
     * output actionAddChildTask class
     */     
    protected class actionAddChildTask extends ItemAction {     
    
        public actionAddChildTask()
        {
            this(null);
        }

        public actionAddChildTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddChildTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddChildTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddChildTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateEditUI.this, "actionAddChildTask", "actionAddChildTask_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "RESchTemplateEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}