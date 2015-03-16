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
public abstract class AbstractLoanDataEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLoanDataEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanFixedYear;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanLine;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUsedAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContactPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContactPhone;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlLoanLine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanFixedYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanLine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUsedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContactPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContactPhone;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnInterestRate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.eas.fdc.sellhouse.LoanDataInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractLoanDataEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLoanDataEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtLoanFixedYear = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDepositAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLoanLine = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUsedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContactPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContactPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pnlLoanLine = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanFixedYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDepositAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanLine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUsedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContactPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContactPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnInterestRate = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber.setName("txtNumber");
        this.prmtBank.setName("prmtBank");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtCurrency.setName("prmtCurrency");
        this.txtLoanFixedYear.setName("txtLoanFixedYear");
        this.txtDepositAmount.setName("txtDepositAmount");
        this.txtLoanLine.setName("txtLoanLine");
        this.txtUsedAmount.setName("txtUsedAmount");
        this.txtContactPerson.setName("txtContactPerson");
        this.txtContactPhone.setName("txtContactPhone");
        this.pnlLoanLine.setName("pnlLoanLine");
        this.contNumber.setName("contNumber");
        this.contSellProject.setName("contSellProject");
        this.contBank.setName("contBank");
        this.contCurrency.setName("contCurrency");
        this.contLoanFixedYear.setName("contLoanFixedYear");
        this.contDepositAmount.setName("contDepositAmount");
        this.contLoanLine.setName("contLoanLine");
        this.contUsedAmount.setName("contUsedAmount");
        this.contContactPerson.setName("contContactPerson");
        this.contContactPhone.setName("contContactPhone");
        this.ctnInterestRate.setName("ctnInterestRate");
        this.kdtEntrys.setName("kdtEntrys");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        // CoreUI		
        this.setBorder(null);		
        this.setPreferredSize(new Dimension(600,440));
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // prmtBank		
        this.prmtBank.setDisplayFormat("$name$");		
        this.prmtBank.setEditFormat("$number$");		
        this.prmtBank.setCommitFormat("$number$");		
        this.prmtBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");		
        this.prmtBank.setRequired(true);
        // prmtSellProject		
        this.prmtSellProject.setEnabled(false);
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");
        // txtLoanFixedYear
        // txtDepositAmount		
        this.txtDepositAmount.setPrecision(2);		
        this.txtDepositAmount.setDataType(1);
        // txtLoanLine		
        this.txtLoanLine.setDataType(1);		
        this.txtLoanLine.setPrecision(2);
        // txtUsedAmount		
        this.txtUsedAmount.setPrecision(2);		
        this.txtUsedAmount.setEnabled(false);		
        this.txtUsedAmount.setDataType(1);
        // txtContactPerson		
        this.txtContactPerson.setMaxLength(80);
        // txtContactPhone		
        this.txtContactPhone.setMaxLength(80);
        // pnlLoanLine		
        this.pnlLoanLine.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlLoanLine.border.title")));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contBank		
        this.contBank.setBoundLabelText(resHelper.getString("contBank.boundLabelText"));		
        this.contBank.setBoundLabelLength(100);		
        this.contBank.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contLoanFixedYear		
        this.contLoanFixedYear.setBoundLabelText(resHelper.getString("contLoanFixedYear.boundLabelText"));		
        this.contLoanFixedYear.setBoundLabelLength(100);		
        this.contLoanFixedYear.setBoundLabelUnderline(true);
        // contDepositAmount		
        this.contDepositAmount.setBoundLabelText(resHelper.getString("contDepositAmount.boundLabelText"));		
        this.contDepositAmount.setBoundLabelLength(100);		
        this.contDepositAmount.setBoundLabelUnderline(true);
        // contLoanLine		
        this.contLoanLine.setBoundLabelText(resHelper.getString("contLoanLine.boundLabelText"));		
        this.contLoanLine.setBoundLabelLength(100);		
        this.contLoanLine.setBoundLabelUnderline(true);
        // contUsedAmount		
        this.contUsedAmount.setBoundLabelText(resHelper.getString("contUsedAmount.boundLabelText"));		
        this.contUsedAmount.setBoundLabelLength(100);		
        this.contUsedAmount.setBoundLabelUnderline(true);		
        this.contUsedAmount.setEnabled(false);
        // contContactPerson		
        this.contContactPerson.setBoundLabelText(resHelper.getString("contContactPerson.boundLabelText"));		
        this.contContactPerson.setBoundLabelLength(100);		
        this.contContactPerson.setBoundLabelUnderline(true);
        // contContactPhone		
        this.contContactPhone.setBoundLabelText(resHelper.getString("contContactPhone.boundLabelText"));		
        this.contContactPhone.setBoundLabelLength(100);		
        this.contContactPhone.setBoundLabelUnderline(true);
        // ctnInterestRate		
        this.ctnInterestRate.setEnableActive(false);		
        this.ctnInterestRate.setTitle(resHelper.getString("ctnInterestRate.title"));
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"fixedYear\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"interestRate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{fixedYear}</t:Cell><t:Cell>$Resource{interestRate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id","fixedYear","interestRate"});


        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
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
        this.setBounds(new Rectangle(10, 10, 600, 440));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 440));
        pnlLoanLine.setBounds(new Rectangle(0, 5, 600, 180));
        this.add(pnlLoanLine, new KDLayout.Constraints(0, 5, 600, 180, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        ctnInterestRate.setBounds(new Rectangle(5, 185, 590, 250));
        this.add(ctnInterestRate, new KDLayout.Constraints(5, 185, 590, 250, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlLoanLine
        pnlLoanLine.setLayout(new KDLayout());
        pnlLoanLine.putClientProperty("OriginalBounds", new Rectangle(0, 5, 600, 180));        contNumber.setBounds(new Rectangle(15, 15, 270, 19));
        pnlLoanLine.add(contNumber, new KDLayout.Constraints(15, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(15, 140, 270, 19));
        pnlLoanLine.add(contSellProject, new KDLayout.Constraints(15, 140, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBank.setBounds(new Rectangle(15, 40, 270, 19));
        pnlLoanLine.add(contBank, new KDLayout.Constraints(15, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(15, 65, 270, 19));
        pnlLoanLine.add(contCurrency, new KDLayout.Constraints(15, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLoanFixedYear.setBounds(new Rectangle(320, 40, 270, 19));
        pnlLoanLine.add(contLoanFixedYear, new KDLayout.Constraints(320, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDepositAmount.setBounds(new Rectangle(320, 65, 270, 19));
        pnlLoanLine.add(contDepositAmount, new KDLayout.Constraints(320, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLoanLine.setBounds(new Rectangle(15, 90, 270, 19));
        pnlLoanLine.add(contLoanLine, new KDLayout.Constraints(15, 90, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUsedAmount.setBounds(new Rectangle(320, 90, 270, 19));
        pnlLoanLine.add(contUsedAmount, new KDLayout.Constraints(320, 90, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContactPerson.setBounds(new Rectangle(15, 115, 270, 19));
        pnlLoanLine.add(contContactPerson, new KDLayout.Constraints(15, 115, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContactPhone.setBounds(new Rectangle(320, 115, 270, 19));
        pnlLoanLine.add(contContactPhone, new KDLayout.Constraints(320, 115, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(320, 15, 270, 19));
        pnlLoanLine.add(contName, new KDLayout.Constraints(320, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contBank
        contBank.setBoundEditor(prmtBank);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contLoanFixedYear
        contLoanFixedYear.setBoundEditor(txtLoanFixedYear);
        //contDepositAmount
        contDepositAmount.setBoundEditor(txtDepositAmount);
        //contLoanLine
        contLoanLine.setBoundEditor(txtLoanLine);
        //contUsedAmount
        contUsedAmount.setBoundEditor(txtUsedAmount);
        //contContactPerson
        contContactPerson.setBoundEditor(txtContactPerson);
        //contContactPhone
        contContactPhone.setBoundEditor(txtContactPhone);
        //contName
        contName.setBoundEditor(txtName);
        //ctnInterestRate
ctnInterestRate.getContentPane().setLayout(new BorderLayout(0, 0));        ctnInterestRate.getContentPane().add(kdtEntrys, BorderLayout.CENTER);

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
        this.toolBar.add(btnCopyLine);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtBank, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("loanFixedYear", int.class, this.txtLoanFixedYear, "value");
		dataBinder.registerBinding("depositAmount", java.math.BigDecimal.class, this.txtDepositAmount, "value");
		dataBinder.registerBinding("loanLine", java.math.BigDecimal.class, this.txtLoanLine, "value");
		dataBinder.registerBinding("usedAmount", java.math.BigDecimal.class, this.txtUsedAmount, "value");
		dataBinder.registerBinding("contactPerson", String.class, this.txtContactPerson, "text");
		dataBinder.registerBinding("contactPhone", String.class, this.txtContactPhone, "text");
		dataBinder.registerBinding("entrys.fixedYear", int.class, this.kdtEntrys, "fixedYear.text");
		dataBinder.registerBinding("entrys.interestRate", java.math.BigDecimal.class, this.kdtEntrys, "interestRate.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.sellhouse.LoanDataEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.LoanDataEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)ov;
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
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanFixedYear", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("depositAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanLine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contactPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contactPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.fixedYear", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.interestRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("bank.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("loanFixedYear"));
        sic.add(new SelectorItemInfo("depositAmount"));
        sic.add(new SelectorItemInfo("loanLine"));
        sic.add(new SelectorItemInfo("usedAmount"));
        sic.add(new SelectorItemInfo("contactPerson"));
        sic.add(new SelectorItemInfo("contactPhone"));
    sic.add(new SelectorItemInfo("entrys.fixedYear"));
    sic.add(new SelectorItemInfo("entrys.interestRate"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "LoanDataEditUI");
    }




}