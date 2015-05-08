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
public abstract class AbstractAchievementManagerEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAchievementManagerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAchievementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelateTask;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer 成果说明;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdbtnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdbtndownload;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdbtnopen;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdbtndel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAchievementType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRelateTask;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPath;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDesc;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable;
    protected com.kingdee.eas.fdc.schedule.AchievementManagerInfo editData = null;
    protected ActionAdd actionAdd = null;
    protected ActionDel actionDel = null;
    protected ActionDownLoad actionDownLoad = null;
    protected ActionOpen actionOpen = null;
    /**
     * output class constructor
     */
    public AbstractAchievementManagerEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAchievementManagerEditUI.class.getName());
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
        //actionAdd
        this.actionAdd = new ActionAdd(this);
        getActionManager().registerAction("actionAdd", actionAdd);
         this.actionAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDel
        this.actionDel = new ActionDel(this);
        getActionManager().registerAction("actionDel", actionDel);
         this.actionDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLoad
        this.actionDownLoad = new ActionDownLoad(this);
        getActionManager().registerAction("actionDownLoad", actionDownLoad);
         this.actionDownLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOpen
        this.actionOpen = new ActionOpen(this);
        getActionManager().registerAction("actionOpen", actionOpen);
         this.actionOpen.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAchievementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelateTask = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.成果说明 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdbtnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdbtndownload = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdbtnopen = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdbtndel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAchievementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRelateTask = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPath = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contState.setName("contState");
        this.contName.setName("contName");
        this.contAchievementType.setName("contAchievementType");
        this.contRelateTask.setName("contRelateTask");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.成果说明.setName("成果说明");
        this.kDContainer.setName("kDContainer");
        this.kdbtnAdd.setName("kdbtnAdd");
        this.kdbtndownload.setName("kdbtndownload");
        this.kdbtnopen.setName("kdbtnopen");
        this.kdbtndel.setName("kdbtndel");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.comboState.setName("comboState");
        this.txtName.setName("txtName");
        this.prmtAchievementType.setName("prmtAchievementType");
        this.prmtRelateTask.setName("prmtRelateTask");
        this.prmtPath.setName("prmtPath");
        this.txtDesc.setName("txtDesc");
        this.kDTable.setName("kDTable");
        // CoreUI		
        this.setPreferredSize(new Dimension(680,600));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contAchievementType		
        this.contAchievementType.setBoundLabelText(resHelper.getString("contAchievementType.boundLabelText"));		
        this.contAchievementType.setBoundLabelLength(100);		
        this.contAchievementType.setBoundLabelUnderline(true);
        // contRelateTask		
        this.contRelateTask.setBoundLabelText(resHelper.getString("contRelateTask.boundLabelText"));		
        this.contRelateTask.setBoundLabelLength(100);		
        this.contRelateTask.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // 成果说明		
        this.成果说明.setBoundLabelText(resHelper.getString("成果说明.boundLabelText"));		
        this.成果说明.setBoundLabelLength(100);		
        this.成果说明.setBoundLabelUnderline(true);
        // kDContainer		
        this.kDContainer.setTitle(resHelper.getString("kDContainer.title"));
        // kdbtnAdd
        this.kdbtnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdbtnAdd.setText(resHelper.getString("kdbtnAdd.text"));
        // kdbtndownload
        this.kdbtndownload.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLoad, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdbtndownload.setText(resHelper.getString("kdbtndownload.text"));
        // kdbtnopen
        this.kdbtnopen.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpen, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdbtnopen.setText(resHelper.getString("kdbtnopen.text"));
        // kdbtndel
        this.kdbtndel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdbtndel.setText(resHelper.getString("kdbtndel.text"));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setEditable(false);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);
        // txtName		
        this.txtName.setRequired(true);
        // prmtAchievementType
        // prmtRelateTask		
        this.prmtRelateTask.setRequired(true);
        this.prmtRelateTask.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRelateTask_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPath		
        this.prmtPath.setDisplayFormat("$docArea.name$ / $name$");		
        this.prmtPath.setQueryInfo("com.kingdee.eas.cp.dm.app.CategoryQuery");
        // txtDesc
        // kDTable
		String kDTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"attName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"attType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"attSize\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"attID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{attName}</t:Cell><t:Cell>$Resource{attType}</t:Cell><t:Cell>$Resource{attSize}</t:Cell><t:Cell>$Resource{attID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable.setFormatXml(resHelper.translateString("kDTable",kDTableStrXML));

        

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
        this.setBounds(new Rectangle(10, 10, 680, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 680, 600));
        contCreator.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(400, 100, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(400, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contState.setBounds(new Rectangle(400, 40, 270, 19));
        this.add(contState, new KDLayout.Constraints(400, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAchievementType.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contAchievementType, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRelateTask.setBounds(new Rectangle(400, 10, 270, 19));
        this.add(contRelateTask, new KDLayout.Constraints(400, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(10, 70, 660, 20));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 70, 660, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        成果说明.setBounds(new Rectangle(10, 140, 660, 86));
        this.add(成果说明, new KDLayout.Constraints(10, 140, 660, 86, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer.setBounds(new Rectangle(10, 250, 660, 338));
        this.add(kDContainer, new KDLayout.Constraints(10, 250, 660, 338, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdbtnAdd.setBounds(new Rectangle(210, 237, 62, 28));
        this.add(kdbtnAdd, new KDLayout.Constraints(210, 237, 62, 28, 0));
        kdbtndownload.setBounds(new Rectangle(344, 239, 61, 28));
        this.add(kdbtndownload, new KDLayout.Constraints(344, 239, 61, 28, 0));
        kdbtnopen.setBounds(new Rectangle(408, 240, 63, 28));
        this.add(kdbtnopen, new KDLayout.Constraints(408, 240, 63, 28, 0));
        kdbtndel.setBounds(new Rectangle(278, 238, 59, 28));
        this.add(kdbtndel, new KDLayout.Constraints(278, 238, 59, 28, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contState
        contState.setBoundEditor(comboState);
        //contName
        contName.setBoundEditor(txtName);
        //contAchievementType
        contAchievementType.setBoundEditor(prmtAchievementType);
        //contRelateTask
        contRelateTask.setBoundEditor(prmtRelateTask);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtPath);
        //成果说明
        成果说明.setBoundEditor(txtDesc);
        //kDContainer
kDContainer.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer.getContentPane().add(kDTable, BorderLayout.CENTER);

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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnAuditResult);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("achievementType", com.kingdee.eas.fdc.schedule.AchievementTypeInfo.class, this.prmtAchievementType, "data");
		dataBinder.registerBinding("relateTask", com.kingdee.eas.fdc.schedule.AchievementProfessionInfo.class, this.prmtRelateTask, "data");
		dataBinder.registerBinding("docPath", com.kingdee.eas.cp.dm.CategoryInfo.class, this.prmtPath, "data");
		dataBinder.registerBinding("description", String.class, this.txtDesc, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.AchievementManagerEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.AchievementManagerInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relateTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("docPath", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
     * output prmtRelateTask_dataChanged method
     */
    protected void prmtRelateTask_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("achievementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("achievementType.id"));
        	sic.add(new SelectorItemInfo("achievementType.number"));
        	sic.add(new SelectorItemInfo("achievementType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("relateTask.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("relateTask.id"));
        	sic.add(new SelectorItemInfo("relateTask.number"));
        	sic.add(new SelectorItemInfo("relateTask.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("docPath.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("docPath.id"));
        	sic.add(new SelectorItemInfo("docPath.number"));
        	sic.add(new SelectorItemInfo("docPath.name"));
		}
        sic.add(new SelectorItemInfo("description"));
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
     * output actionAdd_actionPerformed method
     */
    public void actionAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDel_actionPerformed method
     */
    public void actionDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownLoad_actionPerformed method
     */
    public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOpen_actionPerformed method
     */
    public void actionOpen_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdd() {
    	return false;
    }
	public RequestContext prepareActionDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDel() {
    	return false;
    }
	public RequestContext prepareActionDownLoad(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDownLoad() {
    	return false;
    }
	public RequestContext prepareActionOpen(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOpen() {
    	return false;
    }

    /**
     * output ActionAdd class
     */     
    protected class ActionAdd extends ItemAction {     
    
        public ActionAdd()
        {
            this(null);
        }

        public ActionAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerEditUI.this, "ActionAdd", "actionAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionDel class
     */     
    protected class ActionDel extends ItemAction {     
    
        public ActionDel()
        {
            this(null);
        }

        public ActionDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerEditUI.this, "ActionDel", "actionDel_actionPerformed", e);
        }
    }

    /**
     * output ActionDownLoad class
     */     
    protected class ActionDownLoad extends ItemAction {     
    
        public ActionDownLoad()
        {
            this(null);
        }

        public ActionDownLoad(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDownLoad.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLoad.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLoad.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerEditUI.this, "ActionDownLoad", "actionDownLoad_actionPerformed", e);
        }
    }

    /**
     * output ActionOpen class
     */     
    protected class ActionOpen extends ItemAction {     
    
        public ActionOpen()
        {
            this(null);
        }

        public ActionOpen(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionOpen.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOpen.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOpen.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAchievementManagerEditUI.this, "ActionOpen", "actionOpen_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "AchievementManagerEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}