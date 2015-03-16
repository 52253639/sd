/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractOverdueDescribeEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOverdueDescribeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCauseDescribe;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOverdueSort;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReturnPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResolve;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransOviewId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLatestDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommercialStage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCauseDescribe;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOverdueSort;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLoanBank;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboReturnPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtResolve;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTransOviewId;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCommercialStage;
    protected com.kingdee.eas.fdc.sellhouse.OverdueDescribeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractOverdueDescribeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOverdueDescribeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCauseDescribe = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOverdueSort = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReturnPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contResolve = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransOviewId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLatestDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommercialStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCauseDescribe = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtOverdueSort = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLoanBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboReturnPeriod = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtResolve = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtTransOviewId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCommercialStage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contCauseDescribe.setName("contCauseDescribe");
        this.contOverdueSort.setName("contOverdueSort");
        this.contLoanBank.setName("contLoanBank");
        this.contReturnPeriod.setName("contReturnPeriod");
        this.contResolve.setName("contResolve");
        this.contTransOviewId.setName("contTransOviewId");
        this.contLatestDate.setName("contLatestDate");
        this.contNumber.setName("contNumber");
        this.contCommercialStage.setName("contCommercialStage");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtCauseDescribe.setName("txtCauseDescribe");
        this.prmtOverdueSort.setName("prmtOverdueSort");
        this.prmtLoanBank.setName("prmtLoanBank");
        this.comboReturnPeriod.setName("comboReturnPeriod");
        this.txtResolve.setName("txtResolve");
        this.txtTransOviewId.setName("txtTransOviewId");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.txtNumber.setName("txtNumber");
        this.comboCommercialStage.setName("comboCommercialStage");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,320));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contCauseDescribe		
        this.contCauseDescribe.setBoundLabelText(resHelper.getString("contCauseDescribe.boundLabelText"));		
        this.contCauseDescribe.setBoundLabelLength(100);		
        this.contCauseDescribe.setBoundLabelUnderline(true);
        // contOverdueSort		
        this.contOverdueSort.setBoundLabelText(resHelper.getString("contOverdueSort.boundLabelText"));		
        this.contOverdueSort.setBoundLabelLength(100);		
        this.contOverdueSort.setBoundLabelUnderline(true);
        // contLoanBank		
        this.contLoanBank.setBoundLabelText(resHelper.getString("contLoanBank.boundLabelText"));		
        this.contLoanBank.setBoundLabelLength(100);		
        this.contLoanBank.setBoundLabelUnderline(true);
        // contReturnPeriod		
        this.contReturnPeriod.setBoundLabelText(resHelper.getString("contReturnPeriod.boundLabelText"));		
        this.contReturnPeriod.setBoundLabelLength(100);		
        this.contReturnPeriod.setBoundLabelUnderline(true);		
        this.contReturnPeriod.setEnabled(false);		
        this.contReturnPeriod.setVisible(false);
        // contResolve		
        this.contResolve.setBoundLabelText(resHelper.getString("contResolve.boundLabelText"));		
        this.contResolve.setBoundLabelLength(100);		
        this.contResolve.setBoundLabelUnderline(true);
        // contTransOviewId		
        this.contTransOviewId.setBoundLabelText(resHelper.getString("contTransOviewId.boundLabelText"));		
        this.contTransOviewId.setBoundLabelLength(100);		
        this.contTransOviewId.setBoundLabelUnderline(true);		
        this.contTransOviewId.setEnabled(false);		
        this.contTransOviewId.setVisible(false);
        // contLatestDate		
        this.contLatestDate.setBoundLabelText(resHelper.getString("contLatestDate.boundLabelText"));		
        this.contLatestDate.setBoundLabelLength(100);		
        this.contLatestDate.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contCommercialStage		
        this.contCommercialStage.setBoundLabelText(resHelper.getString("contCommercialStage.boundLabelText"));		
        this.contCommercialStage.setBoundLabelLength(100);		
        this.contCommercialStage.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtCauseDescribe
        // prmtOverdueSort		
        this.prmtOverdueSort.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.OverdueCauseQuery");
        // prmtLoanBank		
        this.prmtLoanBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");
        // comboReturnPeriod		
        this.comboReturnPeriod.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PeriodEnum").toArray());		
        this.comboReturnPeriod.setEnabled(false);		
        this.comboReturnPeriod.setVisible(false);
        // txtResolve
        // txtTransOviewId		
        this.txtTransOviewId.setMaxLength(100);		
        this.txtTransOviewId.setEnabled(false);		
        this.txtTransOviewId.setVisible(false);
        // kDDatePicker1
        // txtNumber
        // comboCommercialStage		
        this.comboCommercialStage.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CommercialStageEnum").toArray());
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
        this.setBounds(new Rectangle(10, 10, 700, 320));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 320));
        contCreator.setBounds(new Rectangle(23, 272, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(23, 272, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(405, 272, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(405, 272, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCauseDescribe.setBounds(new Rectangle(23, 82, 652, 92));
        this.add(contCauseDescribe, new KDLayout.Constraints(23, 82, 652, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOverdueSort.setBounds(new Rectangle(405, 15, 270, 19));
        this.add(contOverdueSort, new KDLayout.Constraints(405, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLoanBank.setBounds(new Rectangle(405, 37, 270, 19));
        this.add(contLoanBank, new KDLayout.Constraints(405, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReturnPeriod.setBounds(new Rectangle(340, 411, 270, 19));
        this.add(contReturnPeriod, new KDLayout.Constraints(340, 411, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contResolve.setBounds(new Rectangle(23, 177, 652, 92));
        this.add(contResolve, new KDLayout.Constraints(23, 177, 652, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransOviewId.setBounds(new Rectangle(345, 381, 270, 19));
        this.add(contTransOviewId, new KDLayout.Constraints(345, 381, 270, 19, 0));
        contLatestDate.setBounds(new Rectangle(23, 37, 270, 19));
        this.add(contLatestDate, new KDLayout.Constraints(23, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(23, 15, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(23, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCommercialStage.setBounds(new Rectangle(23, 59, 452, 19));
        this.add(contCommercialStage, new KDLayout.Constraints(23, 59, 452, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCauseDescribe
        contCauseDescribe.setBoundEditor(txtCauseDescribe);
        //contOverdueSort
        contOverdueSort.setBoundEditor(prmtOverdueSort);
        //contLoanBank
        contLoanBank.setBoundEditor(prmtLoanBank);
        //contReturnPeriod
        contReturnPeriod.setBoundEditor(comboReturnPeriod);
        //contResolve
        contResolve.setBoundEditor(txtResolve);
        //contTransOviewId
        contTransOviewId.setBoundEditor(txtTransOviewId);
        //contLatestDate
        contLatestDate.setBoundEditor(kDDatePicker1);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contCommercialStage
        contCommercialStage.setBoundEditor(comboCommercialStage);

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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("causeDescribe", String.class, this.txtCauseDescribe, "text");
		dataBinder.registerBinding("overdueSort", com.kingdee.eas.fdc.sellhouse.OverdueCauseInfo.class, this.prmtOverdueSort, "data");
		dataBinder.registerBinding("loanBank", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.prmtLoanBank, "data");
		dataBinder.registerBinding("returnPeriod", com.kingdee.eas.fdc.sellhouse.PeriodEnum.class, this.comboReturnPeriod, "selectedItem");
		dataBinder.registerBinding("resolve", String.class, this.txtResolve, "text");
		dataBinder.registerBinding("transOviewId", String.class, this.txtTransOviewId, "text");
		dataBinder.registerBinding("latestDate", java.util.Date.class, this.kDDatePicker1, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("commercialStage", com.kingdee.eas.fdc.sellhouse.CommercialStageEnum.class, this.comboCommercialStage, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.OverdueDescribeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.OverdueDescribeInfo)ov;
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
		getValidateHelper().registerBindProperty("causeDescribe", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("overdueSort", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("returnPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("resolve", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transOviewId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("latestDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commercialStage", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("causeDescribe"));
        sic.add(new SelectorItemInfo("overdueSort.*"));
        sic.add(new SelectorItemInfo("loanBank.*"));
        sic.add(new SelectorItemInfo("returnPeriod"));
        sic.add(new SelectorItemInfo("resolve"));
        sic.add(new SelectorItemInfo("transOviewId"));
        sic.add(new SelectorItemInfo("latestDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("commercialStage"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "OverdueDescribeEditUI");
    }




}