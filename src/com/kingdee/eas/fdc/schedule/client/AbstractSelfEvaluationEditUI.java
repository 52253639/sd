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
public abstract class AbstractSelfEvaluationEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSelfEvaluationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheduleMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSelfEvaluationPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSelfEvaluationDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSelfEvaluationDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSelfEvaluationScore;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkScheduleMonth;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSelfEvaluationPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSelfEvaluationDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSelfDes;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSelfEvaluationScore;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSelfEvaluationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSelfEvaluationEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScheduleMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSelfEvaluationPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSelfEvaluationDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSelfEvaluationDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSelfEvaluationScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkScheduleMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSelfEvaluationPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkSelfEvaluationDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSelfDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtSelfEvaluationScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contAdminDept.setName("contAdminDept");
        this.contScheduleMonth.setName("contScheduleMonth");
        this.contSelfEvaluationPerson.setName("contSelfEvaluationPerson");
        this.contSelfEvaluationDate.setName("contSelfEvaluationDate");
        this.contSelfEvaluationDes.setName("contSelfEvaluationDes");
        this.contSelfEvaluationScore.setName("contSelfEvaluationScore");
        this.kDContainer1.setName("kDContainer1");
        this.txtNumber.setName("txtNumber");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.pkScheduleMonth.setName("pkScheduleMonth");
        this.prmtSelfEvaluationPerson.setName("prmtSelfEvaluationPerson");
        this.pkSelfEvaluationDate.setName("pkSelfEvaluationDate");
        this.txtSelfDes.setName("txtSelfDes");
        this.txtSelfEvaluationScore.setName("txtSelfEvaluationScore");
        this.kdtEntries.setName("kdtEntries");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnSubmit.setEnabled(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
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
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnVoucher.setEnabled(false);		
        this.btnDelVoucher.setEnabled(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnAudit.setEnabled(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setEnabled(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setEnabled(false);		
        this.btnCalculator.setVisible(false);
        // contAdminDept		
        this.contAdminDept.setBoundLabelText(resHelper.getString("contAdminDept.boundLabelText"));		
        this.contAdminDept.setBoundLabelLength(80);		
        this.contAdminDept.setBoundLabelUnderline(true);
        // contScheduleMonth		
        this.contScheduleMonth.setBoundLabelText(resHelper.getString("contScheduleMonth.boundLabelText"));		
        this.contScheduleMonth.setBoundLabelLength(80);		
        this.contScheduleMonth.setBoundLabelUnderline(true);
        // contSelfEvaluationPerson		
        this.contSelfEvaluationPerson.setBoundLabelText(resHelper.getString("contSelfEvaluationPerson.boundLabelText"));		
        this.contSelfEvaluationPerson.setBoundLabelLength(80);		
        this.contSelfEvaluationPerson.setBoundLabelUnderline(true);
        // contSelfEvaluationDate		
        this.contSelfEvaluationDate.setBoundLabelText(resHelper.getString("contSelfEvaluationDate.boundLabelText"));		
        this.contSelfEvaluationDate.setBoundLabelLength(80);		
        this.contSelfEvaluationDate.setBoundLabelUnderline(true);
        // contSelfEvaluationDes		
        this.contSelfEvaluationDes.setBoundLabelText(resHelper.getString("contSelfEvaluationDes.boundLabelText"));		
        this.contSelfEvaluationDes.setBoundLabelLength(80);		
        this.contSelfEvaluationDes.setBoundLabelUnderline(true);
        // contSelfEvaluationScore		
        this.contSelfEvaluationScore.setBoundLabelText(resHelper.getString("contSelfEvaluationScore.boundLabelText"));		
        this.contSelfEvaluationScore.setBoundLabelLength(100);		
        this.contSelfEvaluationScore.setBoundLabelUnderline(true);		
        this.contSelfEvaluationScore.setVisible(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // txtNumber		
        this.txtNumber.setVisible(false);
        // prmtAdminDept		
        this.prmtAdminDept.setEnabled(false);
        // pkScheduleMonth		
        this.pkScheduleMonth.setEnabled(false);
        // prmtSelfEvaluationPerson		
        this.prmtSelfEvaluationPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtSelfEvaluationPerson.setDisplayFormat("$name$");		
        this.prmtSelfEvaluationPerson.setEditFormat("$name$");		
        this.prmtSelfEvaluationPerson.setCommitFormat("$name$");
        // pkSelfEvaluationDate
        // txtSelfDes		
        this.txtSelfDes.setMaxLength(200);
        // txtSelfEvaluationScore
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>##.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"adminPerson\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"finishStandard\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"planFinishDate\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"weightRate\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"isCompelete\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"completePercent\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"actualEndDate\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"selfEvaluationScore\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"selfCompleteDes\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol10\" /><t:Column t:key=\"relateTask\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"finalEvaluationScore\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" t:styleID=\"sCol12\" /><t:Column t:key=\"finalCompleteDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" t:styleID=\"sCol13\" /><t:Column t:key=\"relateTaskId\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"project\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"taskType\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"comboTaskOrigin\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">完成标准</t:Cell><t:Cell t:configured=\"false\">计划完成日期</t:Cell><t:Cell t:configured=\"false\">权重(%)</t:Cell><t:Cell t:configured=\"false\">是否完成</t:Cell><t:Cell t:configured=\"false\">完成进度(%)</t:Cell><t:Cell t:configured=\"false\">实际完成日期</t:Cell><t:Cell t:configured=\"false\">自评得分</t:Cell><t:Cell t:configured=\"false\">完成情况说明</t:Cell><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">相关任务</t:Cell><t:Cell t:configured=\"false\">终评得分</t:Cell><t:Cell t:configured=\"false\">完成情况说明(终评)</t:Cell><t:Cell t:configured=\"false\">相关任务ID</t:Cell><t:Cell t:configured=\"false\">所属项目</t:Cell><t:Cell t:configured=\"false\">任务类别</t:Cell><t:Cell t:configured=\"false\">任务来源</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));

        

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
        this.setLayout(null);
        contAdminDept.setBounds(new Rectangle(10, 10, 230, 19));
        this.add(contAdminDept, null);
        contScheduleMonth.setBounds(new Rectangle(260, 10, 230, 19));
        this.add(contScheduleMonth, null);
        contSelfEvaluationPerson.setBounds(new Rectangle(505, 9, 230, 19));
        this.add(contSelfEvaluationPerson, null);
        contSelfEvaluationDate.setBounds(new Rectangle(762, 9, 230, 19));
        this.add(contSelfEvaluationDate, null);
        contSelfEvaluationDes.setBounds(new Rectangle(10, 48, 980, 80));
        this.add(contSelfEvaluationDes, null);
        contSelfEvaluationScore.setBounds(new Rectangle(91, 532, 270, 19));
        this.add(contSelfEvaluationScore, null);
        kDContainer1.setBounds(new Rectangle(10, 151, 983, 444));
        this.add(kDContainer1, null);
        txtNumber.setBounds(new Rectangle(822, 131, 170, 19));
        this.add(txtNumber, null);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contScheduleMonth
        contScheduleMonth.setBoundEditor(pkScheduleMonth);
        //contSelfEvaluationPerson
        contSelfEvaluationPerson.setBoundEditor(prmtSelfEvaluationPerson);
        //contSelfEvaluationDate
        contSelfEvaluationDate.setBoundEditor(pkSelfEvaluationDate);
        //contSelfEvaluationDes
        contSelfEvaluationDes.setBoundEditor(txtSelfDes);
        //contSelfEvaluationScore
        contSelfEvaluationScore.setBoundEditor(txtSelfEvaluationScore);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        kdtEntries.setBounds(new Rectangle(3, 4, 982, 405));
        kDContainer1.getContentPane().add(kdtEntries, null);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("adminDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtAdminDept, "data");
		dataBinder.registerBinding("scheduleMonth", java.util.Date.class, this.pkScheduleMonth, "value");
		dataBinder.registerBinding("selfEvaluationPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtSelfEvaluationPerson, "data");
		dataBinder.registerBinding("selfEvaluationDate", java.util.Date.class, this.pkSelfEvaluationDate, "value");
		dataBinder.registerBinding("selfEvaluationDes", String.class, this.txtSelfDes, "text");
		dataBinder.registerBinding("selfEvaluationScore", java.math.BigDecimal.class, this.txtSelfEvaluationScore, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.SelfEvaluationEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheduleMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfEvaluationPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfEvaluationDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfEvaluationDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfEvaluationScore", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("adminDept.*"));
        sic.add(new SelectorItemInfo("scheduleMonth"));
        sic.add(new SelectorItemInfo("selfEvaluationPerson.*"));
        sic.add(new SelectorItemInfo("selfEvaluationDate"));
        sic.add(new SelectorItemInfo("selfEvaluationDes"));
        sic.add(new SelectorItemInfo("selfEvaluationScore"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "SelfEvaluationEditUI");
    }




}