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
public abstract class AbstractFinalEvaluationEditUI extends com.kingdee.eas.fdc.schedule.client.SelfEvaluationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFinalEvaluationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFinalEvaluationDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox finalEvaluationPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker finalEvaluationDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtfinalEvaluationScore;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFinalDes;
    protected com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractFinalEvaluationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFinalEvaluationEditUI.class.getName());
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
        this.contFinalEvaluationDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.finalEvaluationPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.finalEvaluationDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtfinalEvaluationScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFinalDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contFinalEvaluationDes.setName("contFinalEvaluationDes");
        this.finalEvaluationPerson.setName("finalEvaluationPerson");
        this.finalEvaluationDate.setName("finalEvaluationDate");
        this.txtfinalEvaluationScore.setName("txtfinalEvaluationScore");
        this.txtFinalDes.setName("txtFinalDes");
        // CoreUI		
        this.btnSubmit.setEnabled(true);		
        this.btnSubmit.setVisible(true);		
        this.btnAttachment.setEnabled(true);		
        this.btnAttachment.setVisible(true);		
        this.btnAuditResult.setEnabled(true);		
        this.btnAuditResult.setVisible(true);		
        this.btnWorkFlowG.setEnabled(true);		
        this.btnWorkFlowG.setVisible(true);		
        this.btnAudit.setEnabled(true);		
        this.btnAudit.setVisible(true);		
        this.btnUnAudit.setEnabled(true);		
        this.btnUnAudit.setVisible(true);		
        this.contSelfEvaluationPerson.setBoundLabelText(resHelper.getString("contSelfEvaluationPerson.boundLabelText"));		
        this.contSelfEvaluationDate.setBoundLabelText(resHelper.getString("contSelfEvaluationDate.boundLabelText"));		
        this.contSelfEvaluationDes.setEnabled(false);		
        this.contSelfEvaluationScore.setBoundLabelText(resHelper.getString("contSelfEvaluationScore.boundLabelText"));		
        this.prmtSelfEvaluationPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtSelfEvaluationPerson.setDisplayFormat("$name$");		
        this.prmtSelfEvaluationPerson.setEditFormat("$name$");		
        this.prmtSelfEvaluationPerson.setCommitFormat("$name$");		
        this.prmtSelfEvaluationPerson.setVisible(false);		
        this.pkSelfEvaluationDate.setVisible(false);		
        this.txtSelfDes.setEnabled(false);		
        this.txtSelfEvaluationScore.setVisible(false);
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>##.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>##.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"relateTask\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"adminPerson\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"finishStandard\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"planFinishDate\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"weightRate\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"isCompelete\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"completePercent\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"actualEndDate\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"selfEvaluationScore\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" t:styleID=\"sCol9\" /><t:Column t:key=\"finalEvaluationScore\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" t:styleID=\"sCol10\" /><t:Column t:key=\"finalCompleteDes\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"selfCompleteDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol12\" /><t:Column t:key=\"relateTaskId\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"project\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"taskType\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"comboTaskOrigin\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{relateTask}</t:Cell><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{finishStandard}</t:Cell><t:Cell>$Resource{planFinishDate}</t:Cell><t:Cell>$Resource{weightRate}</t:Cell><t:Cell>$Resource{isCompelete}</t:Cell><t:Cell>$Resource{completePercent}</t:Cell><t:Cell>$Resource{actualEndDate}</t:Cell><t:Cell>$Resource{selfEvaluationScore}</t:Cell><t:Cell>$Resource{finalEvaluationScore}</t:Cell><t:Cell>$Resource{finalCompleteDes}</t:Cell><t:Cell>$Resource{selfCompleteDes}</t:Cell><t:Cell>$Resource{relateTaskId}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{taskType}</t:Cell><t:Cell>$Resource{comboTaskOrigin}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));

        

        // contFinalEvaluationDes		
        this.contFinalEvaluationDes.setBoundLabelText(resHelper.getString("contFinalEvaluationDes.boundLabelText"));		
        this.contFinalEvaluationDes.setBoundLabelLength(80);		
        this.contFinalEvaluationDes.setBoundLabelUnderline(true);
        // finalEvaluationPerson		
        this.finalEvaluationPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.finalEvaluationPerson.setDisplayFormat("$name$");		
        this.finalEvaluationPerson.setEditFormat("$name$");		
        this.finalEvaluationPerson.setCommitFormat("$name$");
        // finalEvaluationDate
        // txtfinalEvaluationScore		
        this.txtfinalEvaluationScore.setVisible(false);
        // txtFinalDes		
        this.txtFinalDes.setMaxLength(200);
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
        contSelfEvaluationPerson.setBounds(new Rectangle(508, 10, 230, 19));
        this.add(contSelfEvaluationPerson, null);
        contSelfEvaluationDate.setBounds(new Rectangle(762, 10, 230, 19));
        this.add(contSelfEvaluationDate, null);
        contSelfEvaluationDes.setBounds(new Rectangle(10, 47, 980, 80));
        this.add(contSelfEvaluationDes, null);
        kDContainer1.setBounds(new Rectangle(10, 241, 982, 379));
        this.add(kDContainer1, null);
        txtNumber.setBounds(new Rectangle(822, 131, 170, 19));
        this.add(txtNumber, null);
        prmtSelfEvaluationPerson.setBounds(new Rectangle(601, 11, 134, 17));
        this.add(prmtSelfEvaluationPerson, null);
        pkSelfEvaluationDate.setBounds(new Rectangle(853, 13, 132, 14));
        this.add(pkSelfEvaluationDate, null);
        contFinalEvaluationDes.setBounds(new Rectangle(10, 145, 980, 80));
        this.add(contFinalEvaluationDes, null);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contScheduleMonth
        contScheduleMonth.setBoundEditor(pkScheduleMonth);
        //contSelfEvaluationPerson
        contSelfEvaluationPerson.setBoundEditor(finalEvaluationPerson);
        //contSelfEvaluationDate
        contSelfEvaluationDate.setBoundEditor(finalEvaluationDate);
        //contSelfEvaluationDes
        contSelfEvaluationDes.setBoundEditor(txtSelfDes);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        contSelfEvaluationScore.setBounds(new Rectangle(56, 312, 270, 19));
        kDContainer1.getContentPane().add(contSelfEvaluationScore, null);
        txtSelfEvaluationScore.setBounds(new Rectangle(161, 320, 112, 10));
        kDContainer1.getContentPane().add(txtSelfEvaluationScore, null);
        kdtEntries.setBounds(new Rectangle(1, 1, 968, 341));
        kDContainer1.getContentPane().add(kdtEntries, null);
        //contSelfEvaluationScore
        contSelfEvaluationScore.setBoundEditor(txtfinalEvaluationScore);
        //contFinalEvaluationDes
        contFinalEvaluationDes.setBoundEditor(txtFinalDes);

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
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnNumberSign);
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
		dataBinder.registerBinding("finalEvaluationPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.finalEvaluationPerson, "data");
		dataBinder.registerBinding("finalEvaluationDate", java.util.Date.class, this.finalEvaluationDate, "value");
		dataBinder.registerBinding("finalEvaluationScore", java.math.BigDecimal.class, this.txtfinalEvaluationScore, "value");
		dataBinder.registerBinding("finalEvaluationDes", String.class, this.txtFinalDes, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FinalEvaluationEditUIHandler";
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
	 * ????????§µ??
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
		getValidateHelper().registerBindProperty("finalEvaluationPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finalEvaluationDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finalEvaluationScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finalEvaluationDes", ValidateHelper.ON_SAVE);    		
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
			sic.add(new SelectorItemInfo("finalEvaluationPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("finalEvaluationPerson.id"));
        	sic.add(new SelectorItemInfo("finalEvaluationPerson.number"));
        	sic.add(new SelectorItemInfo("finalEvaluationPerson.name"));
		}
        sic.add(new SelectorItemInfo("finalEvaluationDate"));
        sic.add(new SelectorItemInfo("finalEvaluationScore"));
        sic.add(new SelectorItemInfo("finalEvaluationDes"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FinalEvaluationEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}