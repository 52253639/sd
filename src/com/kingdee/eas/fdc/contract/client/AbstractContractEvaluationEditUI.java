/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractEvaluationEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractEvaluationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelAll;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane contract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPartB;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPublish;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevise;
    protected com.kingdee.eas.fdc.contract.ContractEvaluationInfo editData = null;
    protected ActionALine actionALine = null;
    protected ActionILine actionILine = null;
    protected ActionRLine actionRLine = null;
    /**
     * output class constructor
     */
    public AbstractContractEvaluationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractEvaluationEditUI.class.getName());
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
        //actionALine
        this.actionALine = new ActionALine(this);
        getActionManager().registerAction("actionALine", actionALine);
         this.actionALine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionILine
        this.actionILine = new ActionILine(this);
        getActionManager().registerAction("actionILine", actionILine);
         this.actionILine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRLine
        this.actionRLine = new ActionRLine(this);
        getActionManager().registerAction("actionRLine", actionRLine);
         this.actionRLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.panelAll = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contract = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contConName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPartB = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCurProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtConName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.menuItemPublish = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevise = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.panelAll.setName("panelAll");
        this.kDPanel1.setName("kDPanel1");
        this.contract.setName("contract");
        this.contAuditTime.setName("contAuditTime");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contCreator.setName("contCreator");
        this.contPartB.setName("contPartB");
        this.contNumber.setName("contNumber");
        this.contConNumber.setName("contConNumber");
        this.contCurProject.setName("contCurProject");
        this.kdtEntry.setName("kdtEntry");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDLabel1.setName("kDLabel1");
        this.contConName.setName("contConName");
        this.pkAuditTime.setName("pkAuditTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtCreator.setName("prmtCreator");
        this.txtPartB.setName("txtPartB");
        this.txtNumber.setName("txtNumber");
        this.txtConNumber.setName("txtConNumber");
        this.txtCurProject.setName("txtCurProject");
        this.txtDescription.setName("txtDescription");
        this.txtConName.setName("txtConName");
        this.menuItemPublish.setName("menuItemPublish");
        this.menuItemRevise.setName("menuItemRevise");
        // CoreUI		
        this.setBorder(null);		
        this.setPreferredSize(new Dimension(1013,628));		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCreateTo.setEnabled(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceUp.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setEnabled(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemMultiapprove.setEnabled(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.btnCalculator.setVisible(false);		
        this.btnCalculator.setEnabled(false);
        // panelAll
        // kDPanel1
        // contract
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(100);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelLength(100);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contPartB		
        this.contPartB.setBoundLabelText(resHelper.getString("contPartB.boundLabelText"));		
        this.contPartB.setBoundLabelLength(130);		
        this.contPartB.setBoundLabelUnderline(true);		
        this.contPartB.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contConNumber		
        this.contConNumber.setBoundLabelText(resHelper.getString("contConNumber.boundLabelText"));		
        this.contConNumber.setBoundLabelLength(100);		
        this.contConNumber.setBoundLabelUnderline(true);		
        this.contConNumber.setEnabled(false);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(130);		
        this.contCurProject.setBoundLabelUnderline(true);		
        this.contCurProject.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"result\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{result}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"type","result"});


        // kDScrollPane1
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // contConName		
        this.contConName.setBoundLabelText(resHelper.getString("contConName.boundLabelText"));		
        this.contConName.setBoundLabelLength(130);		
        this.contConName.setBoundLabelUnderline(true);		
        this.contConName.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setVisible(true);		
        this.pkAuditTime.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setVisible(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setVisible(true);		
        this.prmtAuditor.setEditable(true);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");		
        this.prmtAuditor.setRequired(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setVisible(true);		
        this.prmtCreator.setEditable(true);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setRequired(false);
        // txtPartB		
        this.txtPartB.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtConNumber		
        this.txtConNumber.setEnabled(false);
        // txtCurProject		
        this.txtCurProject.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(500);
        // txtConName		
        this.txtConName.setEnabled(false);
        // menuItemPublish		
        this.menuItemPublish.setText(resHelper.getString("menuItemPublish.text"));
        // menuItemRevise		
        this.menuItemRevise.setText(resHelper.getString("menuItemRevise.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 628));
this.setLayout(new BorderLayout(0, 0));
        this.add(panelAll, BorderLayout.CENTER);
        //panelAll
        panelAll.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        panelAll.add(contract, resHelper.getString("contract.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 595));        contAuditTime.setBounds(new Rectangle(412, 562, 270, 19));
        kDPanel1.add(contAuditTime, new KDLayout.Constraints(412, 562, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(412, 540, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(412, 540, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(19, 562, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(19, 562, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(19, 540, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(19, 540, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPartB.setBounds(new Rectangle(19, 53, 663, 19));
        kDPanel1.add(contPartB, new KDLayout.Constraints(19, 53, 663, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(714, 9, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(714, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConNumber.setBounds(new Rectangle(714, 31, 270, 19));
        kDPanel1.add(contConNumber, new KDLayout.Constraints(714, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(19, 9, 663, 19));
        kDPanel1.add(contCurProject, new KDLayout.Constraints(19, 9, 663, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntry.setBounds(new Rectangle(20, 93, 962, 271));
        kDPanel1.add(kdtEntry, new KDLayout.Constraints(20, 93, 962, 271, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(19, 405, 962, 124));
        kDPanel1.add(kDScrollPane1, new KDLayout.Constraints(19, 405, 962, 124, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel1.setBounds(new Rectangle(19, 381, 196, 19));
        kDPanel1.add(kDLabel1, new KDLayout.Constraints(19, 381, 196, 19, 0));
        contConName.setBounds(new Rectangle(19, 31, 663, 19));
        kDPanel1.add(contConName, new KDLayout.Constraints(19, 31, 663, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contPartB
        contPartB.setBoundEditor(txtPartB);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contConNumber
        contConNumber.setBoundEditor(txtConNumber);
        //contCurProject
        contCurProject.setBoundEditor(txtCurProject);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contConName
        contConName.setBoundEditor(txtConName);

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
        menuBiz.add(menuItemPublish);
        menuBiz.add(menuItemRevise);
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
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.contract.ContractEvaluationEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.type", com.kingdee.eas.fdc.contract.EvaluationTypeInfo.class, this.kdtEntry, "type.text");
		dataBinder.registerBinding("entry.result", com.kingdee.eas.fdc.contract.EvaluationResultInfo.class, this.kdtEntry, "result.text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("contract.partB.name", String.class, this.txtPartB, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("contract.number", String.class, this.txtConNumber, "text");
		dataBinder.registerBinding("contract.curProject.displayName", String.class, this.txtCurProject, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("contract.name", String.class, this.txtConName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractEvaluationEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractEvaluationInfo)ov;
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
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.result", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.partB.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.curProject.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.name", ValidateHelper.ON_SAVE);    		
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
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.type.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.type.id"));
			sic.add(new SelectorItemInfo("entry.type.name"));
        	sic.add(new SelectorItemInfo("entry.type.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.result.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.result.id"));
			sic.add(new SelectorItemInfo("entry.result.name"));
        	sic.add(new SelectorItemInfo("entry.result.number"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
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
        sic.add(new SelectorItemInfo("contract.partB.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("contract.number"));
        sic.add(new SelectorItemInfo("contract.curProject.displayName"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("contract.name"));
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
     * output actionALine_actionPerformed method
     */
    public void actionALine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionILine_actionPerformed method
     */
    public void actionILine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRLine_actionPerformed method
     */
    public void actionRLine_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionALine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionALine() {
    	return false;
    }
	public RequestContext prepareActionILine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionILine() {
    	return false;
    }
	public RequestContext prepareActionRLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRLine() {
    	return false;
    }

    /**
     * output ActionALine class
     */     
    protected class ActionALine extends ItemAction {     
    
        public ActionALine()
        {
            this(null);
        }

        public ActionALine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionALine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionALine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionALine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractEvaluationEditUI.this, "ActionALine", "actionALine_actionPerformed", e);
        }
    }

    /**
     * output ActionILine class
     */     
    protected class ActionILine extends ItemAction {     
    
        public ActionILine()
        {
            this(null);
        }

        public ActionILine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionILine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionILine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionILine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractEvaluationEditUI.this, "ActionILine", "actionILine_actionPerformed", e);
        }
    }

    /**
     * output ActionRLine class
     */     
    protected class ActionRLine extends ItemAction {     
    
        public ActionRLine()
        {
            this(null);
        }

        public ActionRLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractEvaluationEditUI.this, "ActionRLine", "actionRLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractEvaluationEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}