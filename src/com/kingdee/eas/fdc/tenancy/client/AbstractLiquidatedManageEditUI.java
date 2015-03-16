/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractLiquidatedManageEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLiquidatedManageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiveDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLiqStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLiqEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbRateDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsGenerate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReliefAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTenancyBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkReceiveDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLiqStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLiqEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReliefAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnGenTenBillOtherPay;
    protected com.kingdee.eas.fdc.tenancy.LiquidatedManageInfo editData = null;
    protected ActionGenTenBillOtherPay actionGenTenBillOtherPay = null;
    /**
     * output class constructor
     */
    public AbstractLiquidatedManageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLiquidatedManageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionGenTenBillOtherPay
        this.actionGenTenBillOtherPay = new ActionGenTenBillOtherPay(this);
        getActionManager().registerAction("actionGenTenBillOtherPay", actionGenTenBillOtherPay);
         this.actionGenTenBillOtherPay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceiveDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLiqStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLiqEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbRateDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbIsGenerate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contReliefAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtTenancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkReceiveDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkLiqStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkLiqEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReliefAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnGenTenBillOtherPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contDescription.setName("contDescription");
        this.contTenancyBill.setName("contTenancyBill");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contReceiveDate.setName("contReceiveDate");
        this.contStartDate.setName("contStartDate");
        this.contEndDate.setName("contEndDate");
        this.contRate.setName("contRate");
        this.contAmount.setName("contAmount");
        this.contLiqStartDate.setName("contLiqStartDate");
        this.contLiqEndDate.setName("contLiqEndDate");
        this.contSellProject.setName("contSellProject");
        this.cbRateDate.setName("cbRateDate");
        this.cbIsGenerate.setName("cbIsGenerate");
        this.contReliefAmount.setName("contReliefAmount");
        this.contActAmount.setName("contActAmount");
        this.txtDescription.setName("txtDescription");
        this.prmtTenancyBill.setName("prmtTenancyBill");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.pkReceiveDate.setName("pkReceiveDate");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        this.txtRate.setName("txtRate");
        this.txtAmount.setName("txtAmount");
        this.pkLiqStartDate.setName("pkLiqStartDate");
        this.pkLiqEndDate.setName("pkLiqEndDate");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtReliefAmount.setName("txtReliefAmount");
        this.txtActAmount.setName("txtActAmount");
        this.btnGenTenBillOtherPay.setName("btnGenTenBillOtherPay");
        // CoreUI
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contTenancyBill		
        this.contTenancyBill.setBoundLabelText(resHelper.getString("contTenancyBill.boundLabelText"));		
        this.contTenancyBill.setBoundLabelLength(120);		
        this.contTenancyBill.setBoundLabelUnderline(true);		
        this.contTenancyBill.setEnabled(false);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(120);		
        this.contMoneyDefine.setBoundLabelUnderline(true);		
        this.contMoneyDefine.setEnabled(false);
        // contReceiveDate		
        this.contReceiveDate.setBoundLabelText(resHelper.getString("contReceiveDate.boundLabelText"));		
        this.contReceiveDate.setBoundLabelLength(120);		
        this.contReceiveDate.setBoundLabelUnderline(true);		
        this.contReceiveDate.setEnabled(false);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(120);		
        this.contStartDate.setBoundLabelUnderline(true);		
        this.contStartDate.setEnabled(false);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(120);		
        this.contEndDate.setBoundLabelUnderline(true);		
        this.contEndDate.setEnabled(false);
        // contRate		
        this.contRate.setBoundLabelText(resHelper.getString("contRate.boundLabelText"));		
        this.contRate.setBoundLabelLength(120);		
        this.contRate.setBoundLabelUnderline(true);		
        this.contRate.setEnabled(false);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(120);		
        this.contAmount.setBoundLabelUnderline(true);
        // contLiqStartDate		
        this.contLiqStartDate.setBoundLabelText(resHelper.getString("contLiqStartDate.boundLabelText"));		
        this.contLiqStartDate.setBoundLabelLength(120);		
        this.contLiqStartDate.setBoundLabelUnderline(true);		
        this.contLiqStartDate.setEnabled(false);
        // contLiqEndDate		
        this.contLiqEndDate.setBoundLabelText(resHelper.getString("contLiqEndDate.boundLabelText"));		
        this.contLiqEndDate.setBoundLabelLength(120);		
        this.contLiqEndDate.setBoundLabelUnderline(true);		
        this.contLiqEndDate.setEnabled(false);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setBoundLabelLength(120);		
        this.contSellProject.setEnabled(false);
        // cbRateDate		
        this.cbRateDate.setEnabled(false);		
        this.cbRateDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());
        // cbIsGenerate		
        this.cbIsGenerate.setText(resHelper.getString("cbIsGenerate.text"));
        // contReliefAmount		
        this.contReliefAmount.setBoundLabelText(resHelper.getString("contReliefAmount.boundLabelText"));		
        this.contReliefAmount.setBoundLabelLength(120);		
        this.contReliefAmount.setBoundLabelUnderline(true);
        // contActAmount		
        this.contActAmount.setBoundLabelText(resHelper.getString("contActAmount.boundLabelText"));		
        this.contActAmount.setBoundLabelLength(120);		
        this.contActAmount.setBoundLabelUnderline(true);		
        this.contActAmount.setEnabled(false);
        // txtDescription
        // prmtTenancyBill		
        this.prmtTenancyBill.setEditFormat("$number$");		
        this.prmtTenancyBill.setDisplayFormat("$name$");		
        this.prmtTenancyBill.setCommitFormat("$number$");		
        this.prmtTenancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillF7Query");		
        this.prmtTenancyBill.setEnabled(false);
        // prmtMoneyDefine		
        this.prmtMoneyDefine.setCommitFormat("$number$");		
        this.prmtMoneyDefine.setEditFormat("$number$");		
        this.prmtMoneyDefine.setDisplayFormat("$name$");		
        this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtMoneyDefine.setEnabled(false);
        // pkReceiveDate		
        this.pkReceiveDate.setEnabled(false);
        // pkStartDate		
        this.pkStartDate.setEnabled(false);
        // pkEndDate		
        this.pkEndDate.setEnabled(false);
        // txtRate		
        this.txtRate.setEnabled(false);		
        this.txtRate.setDataType(1);		
        this.txtRate.setPrecision(2);
        // txtAmount		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setPrecision(2);
        this.txtAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkLiqStartDate		
        this.pkLiqStartDate.setEnabled(false);
        // pkLiqEndDate		
        this.pkLiqEndDate.setEnabled(false);
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setEnabled(false);
        // txtReliefAmount		
        this.txtReliefAmount.setPrecision(2);		
        this.txtReliefAmount.setDataType(1);
        this.txtReliefAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtReliefAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtActAmount		
        this.txtActAmount.setEnabled(false);		
        this.txtActAmount.setPrecision(2);		
        this.txtActAmount.setDataType(1);
        // btnGenTenBillOtherPay
        this.btnGenTenBillOtherPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionGenTenBillOtherPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnGenTenBillOtherPay.setText(resHelper.getString("btnGenTenBillOtherPay.text"));
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
        this.setBounds(new Rectangle(10, 10, 700, 500));
        this.setLayout(null);
        contDescription.setBounds(new Rectangle(37, 243, 615, 82));
        this.add(contDescription, null);
        contTenancyBill.setBounds(new Rectangle(382, 31, 270, 19));
        this.add(contTenancyBill, null);
        contMoneyDefine.setBounds(new Rectangle(37, 64, 270, 19));
        this.add(contMoneyDefine, null);
        contReceiveDate.setBounds(new Rectangle(382, 64, 270, 19));
        this.add(contReceiveDate, null);
        contStartDate.setBounds(new Rectangle(37, 97, 270, 19));
        this.add(contStartDate, null);
        contEndDate.setBounds(new Rectangle(382, 97, 270, 19));
        this.add(contEndDate, null);
        contRate.setBounds(new Rectangle(37, 130, 208, 19));
        this.add(contRate, null);
        contAmount.setBounds(new Rectangle(382, 130, 270, 19));
        this.add(contAmount, null);
        contLiqStartDate.setBounds(new Rectangle(37, 198, 270, 19));
        this.add(contLiqStartDate, null);
        contLiqEndDate.setBounds(new Rectangle(382, 198, 270, 19));
        this.add(contLiqEndDate, null);
        contSellProject.setBounds(new Rectangle(37, 31, 270, 19));
        this.add(contSellProject, null);
        cbRateDate.setBounds(new Rectangle(247, 130, 59, 19));
        this.add(cbRateDate, null);
        cbIsGenerate.setBounds(new Rectangle(34, 361, 140, 19));
        this.add(cbIsGenerate, null);
        contReliefAmount.setBounds(new Rectangle(37, 163, 270, 19));
        this.add(contReliefAmount, null);
        contActAmount.setBounds(new Rectangle(382, 163, 270, 19));
        this.add(contActAmount, null);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contTenancyBill
        contTenancyBill.setBoundEditor(prmtTenancyBill);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);
        //contReceiveDate
        contReceiveDate.setBoundEditor(pkReceiveDate);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //contRate
        contRate.setBoundEditor(txtRate);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contLiqStartDate
        contLiqStartDate.setBoundEditor(pkLiqStartDate);
        //contLiqEndDate
        contLiqEndDate.setBoundEditor(pkLiqEndDate);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contReliefAmount
        contReliefAmount.setBoundEditor(txtReliefAmount);
        //contActAmount
        contActAmount.setBoundEditor(txtActAmount);

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
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnGenTenBillOtherPay);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("rateDate", int.class, this.cbRateDate, "selectedItem");
		dataBinder.registerBinding("isGenerate", boolean.class, this.cbIsGenerate, "selected");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("tenancyBill", com.kingdee.eas.fdc.tenancy.TenancyBillInfo.class, this.prmtTenancyBill, "data");
		dataBinder.registerBinding("moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtMoneyDefine, "data");
		dataBinder.registerBinding("receiveDate", java.util.Date.class, this.pkReceiveDate, "value");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkStartDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkEndDate, "value");
		dataBinder.registerBinding("rate", java.math.BigDecimal.class, this.txtRate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("liqStartDate", java.util.Date.class, this.pkLiqStartDate, "value");
		dataBinder.registerBinding("liqEndDate", java.util.Date.class, this.pkLiqEndDate, "value");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("reliefAmount", java.math.BigDecimal.class, this.txtReliefAmount, "value");
		dataBinder.registerBinding("actAmount", java.math.BigDecimal.class, this.txtActAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.LiquidatedManageEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.LiquidatedManageInfo)ov;
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
		getValidateHelper().registerBindProperty("rateDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isGenerate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiveDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("liqStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("liqEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reliefAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actAmount", ValidateHelper.ON_SAVE);    		
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
     * output txtAmount_dataChanged method
     */
    protected void txtAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtReliefAmount_dataChanged method
     */
    protected void txtReliefAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("rateDate"));
        sic.add(new SelectorItemInfo("isGenerate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("tenancyBill.*"));
        sic.add(new SelectorItemInfo("moneyDefine.*"));
        sic.add(new SelectorItemInfo("receiveDate"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("liqStartDate"));
        sic.add(new SelectorItemInfo("liqEndDate"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("reliefAmount"));
        sic.add(new SelectorItemInfo("actAmount"));
        return sic;
    }        
    	

    /**
     * output actionGenTenBillOtherPay_actionPerformed method
     */
    public void actionGenTenBillOtherPay_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionGenTenBillOtherPay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGenTenBillOtherPay() {
    	return false;
    }

    /**
     * output ActionGenTenBillOtherPay class
     */     
    protected class ActionGenTenBillOtherPay extends ItemAction {     
    
        public ActionGenTenBillOtherPay()
        {
            this(null);
        }

        public ActionGenTenBillOtherPay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionGenTenBillOtherPay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGenTenBillOtherPay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGenTenBillOtherPay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractLiquidatedManageEditUI.this, "ActionGenTenBillOtherPay", "actionGenTenBillOtherPay_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "LiquidatedManageEditUI");
    }




}