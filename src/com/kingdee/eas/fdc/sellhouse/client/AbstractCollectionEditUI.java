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
public abstract class AbstractCollectionEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCollectionEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCalculateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFixedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHold;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnScript;
    protected com.kingdee.bos.ctrl.swing.KDButton btnClear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFormulaDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsStdFormulaScript;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCalculateType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtFixedAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHold;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFactor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOperate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtComparaValue;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFormulaDescription;
    protected com.kingdee.eas.fdc.sellhouse.CollectionInfo editData = null;
    protected actionSure actionSure = null;
    protected actionScript actionScript = null;
    protected actionClear actionClear = null;
    /**
     * output class constructor
     */
    public AbstractCollectionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCollectionEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSure
        this.actionSure = new actionSure(this);
        getActionManager().registerAction("actionSure", actionSure);
         this.actionSure.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionScript
        this.actionScript = new actionScript(this);
        getActionManager().registerAction("actionScript", actionScript);
         this.actionScript.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClear
        this.actionClear = new actionClear(this);
        getActionManager().registerAction("actionClear", actionClear);
         this.actionClear.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contMoneyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCalculateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntegerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFixedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHold = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnScript = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnClear = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contFormulaDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtsStdFormulaScript = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtMoneyName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCalculateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboIntegerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtFixedAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.comboHold = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboFactor = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboOperate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtComparaValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtFormulaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.contMoneyName.setName("contMoneyName");
        this.contProject.setName("contProject");
        this.contCalculateType.setName("contCalculateType");
        this.contIntegerType.setName("contIntegerType");
        this.contFixedAmount.setName("contFixedAmount");
        this.contHold.setName("contHold");
        this.kDContainer1.setName("kDContainer1");
        this.btnScript.setName("btnScript");
        this.btnClear.setName("btnClear");
        this.contFormulaDescription.setName("contFormulaDescription");
        this.txtsStdFormulaScript.setName("txtsStdFormulaScript");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.prmtMoneyName.setName("prmtMoneyName");
        this.prmtProject.setName("prmtProject");
        this.comboCalculateType.setName("comboCalculateType");
        this.comboIntegerType.setName("comboIntegerType");
        this.txtFixedAmount.setName("txtFixedAmount");
        this.comboHold.setName("comboHold");
        this.comboFactor.setName("comboFactor");
        this.comboOperate.setName("comboOperate");
        this.txtComparaValue.setName("txtComparaValue");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.txtFormulaDescription.setName("txtFormulaDescription");
        // CoreUI		
        this.setMaximumSize(new Dimension(650,380));		
        this.setMinimumSize(new Dimension(650,380));		
        this.setPreferredSize(new Dimension(650,380));		
        this.btnSave.setVisible(false);		
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
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setVisible(false);
        // contMoneyName		
        this.contMoneyName.setBoundLabelText(resHelper.getString("contMoneyName.boundLabelText"));		
        this.contMoneyName.setBoundLabelLength(100);		
        this.contMoneyName.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);
        // contCalculateType		
        this.contCalculateType.setBoundLabelText(resHelper.getString("contCalculateType.boundLabelText"));		
        this.contCalculateType.setBoundLabelLength(100);		
        this.contCalculateType.setBoundLabelUnderline(true);
        // contIntegerType		
        this.contIntegerType.setBoundLabelText(resHelper.getString("contIntegerType.boundLabelText"));		
        this.contIntegerType.setBoundLabelLength(100);		
        this.contIntegerType.setBoundLabelUnderline(true);
        // contFixedAmount		
        this.contFixedAmount.setBoundLabelText(resHelper.getString("contFixedAmount.boundLabelText"));		
        this.contFixedAmount.setBoundLabelLength(100);		
        this.contFixedAmount.setBoundLabelUnderline(true);		
        this.contFixedAmount.setVisible(false);
        // contHold		
        this.contHold.setBoundLabelText(resHelper.getString("contHold.boundLabelText"));		
        this.contHold.setBoundLabelLength(100);		
        this.contHold.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnScript
        this.btnScript.setAction((IItemAction)ActionProxyFactory.getProxy(actionScript, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScript.setText(resHelper.getString("btnScript.text"));		
        this.btnScript.setVisible(false);
        this.btnScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnScript_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnClear
        this.btnClear.setAction((IItemAction)ActionProxyFactory.getProxy(actionClear, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClear.setText(resHelper.getString("btnClear.text"));		
        this.btnClear.setVisible(false);
        // contFormulaDescription		
        this.contFormulaDescription.setBoundLabelText(resHelper.getString("contFormulaDescription.boundLabelText"));		
        this.contFormulaDescription.setBoundLabelLength(100);		
        this.contFormulaDescription.setBoundLabelUnderline(true);		
        this.contFormulaDescription.setVisible(false);		
        this.contFormulaDescription.setEnabled(false);
        // txtsStdFormulaScript		
        this.txtsStdFormulaScript.setVisible(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtDescription
        // prmtMoneyName		
        this.prmtMoneyName.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CollectionMoneyDefineQuery");		
        this.prmtMoneyName.setRequired(true);		
        this.prmtMoneyName.setCommitFormat("$number$");
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtProject.setEnabled(false);		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setCommitFormat("$number$");
        // comboCalculateType		
        this.comboCalculateType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum").toArray());
        this.comboCalculateType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCalculateType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboIntegerType		
        this.comboIntegerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum").toArray());
        // txtFixedAmount		
        this.txtFixedAmount.setDataType(6);		
        this.txtFixedAmount.setPrecision(2);
        // comboHold		
        this.comboHold.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HoldEnum").toArray());
        // comboFactor		
        this.comboFactor.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.FactorEnum").toArray());
        // comboOperate		
        this.comboOperate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.OperateEnum").toArray());
        // txtComparaValue		
        this.txtComparaValue.setDataType(1);		
        this.txtComparaValue.setPrecision(2);
        this.txtComparaValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtComparaValue_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // txtFormulaDescription		
        this.txtFormulaDescription.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 650, 380));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 380));
        contNumber.setBounds(new Rectangle(9, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(9, 11, 270, 19, 0));
        contDescription.setBounds(new Rectangle(14, 89, 581, 74));
        this.add(contDescription, new KDLayout.Constraints(14, 89, 581, 74, 0));
        chkIsEnabled.setBounds(new Rectangle(348, 165, 140, 19));
        this.add(chkIsEnabled, new KDLayout.Constraints(348, 165, 140, 19, 0));
        contMoneyName.setBounds(new Rectangle(325, 9, 270, 19));
        this.add(contMoneyName, new KDLayout.Constraints(325, 9, 270, 19, 0));
        contProject.setBounds(new Rectangle(9, 34, 270, 19));
        this.add(contProject, new KDLayout.Constraints(9, 34, 270, 19, 0));
        contCalculateType.setBounds(new Rectangle(325, 35, 270, 19));
        this.add(contCalculateType, new KDLayout.Constraints(325, 35, 270, 19, 0));
        contIntegerType.setBounds(new Rectangle(325, 62, 270, 19));
        this.add(contIntegerType, new KDLayout.Constraints(325, 62, 270, 19, 0));
        contFixedAmount.setBounds(new Rectangle(9, 62, 270, 19));
        this.add(contFixedAmount, new KDLayout.Constraints(9, 62, 270, 19, 0));
        contHold.setBounds(new Rectangle(9, 63, 270, 19));
        this.add(contHold, new KDLayout.Constraints(9, 63, 270, 19, 0));
        kDContainer1.setBounds(new Rectangle(16, 181, 579, 87));
        this.add(kDContainer1, new KDLayout.Constraints(16, 181, 579, 87, 0));
        btnScript.setBounds(new Rectangle(397, 312, 83, 23));
        this.add(btnScript, new KDLayout.Constraints(397, 312, 83, 23, 0));
        btnClear.setBounds(new Rectangle(503, 310, 88, 23));
        this.add(btnClear, new KDLayout.Constraints(503, 310, 88, 23, 0));
        contFormulaDescription.setBounds(new Rectangle(15, 183, 578, 120));
        this.add(contFormulaDescription, new KDLayout.Constraints(15, 183, 578, 120, 0));
        txtsStdFormulaScript.setBounds(new Rectangle(162, 312, 72, 21));
        this.add(txtsStdFormulaScript, new KDLayout.Constraints(162, 312, 72, 21, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contMoneyName
        contMoneyName.setBoundEditor(prmtMoneyName);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contCalculateType
        contCalculateType.setBoundEditor(comboCalculateType);
        //contIntegerType
        contIntegerType.setBoundEditor(comboIntegerType);
        //contFixedAmount
        contFixedAmount.setBoundEditor(txtFixedAmount);
        //contHold
        contHold.setBoundEditor(comboHold);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(16, 181, 579, 87));        comboFactor.setBounds(new Rectangle(36, 38, 128, 19));
        kDContainer1.getContentPane().add(comboFactor, new KDLayout.Constraints(36, 38, 128, 19, 0));
        comboOperate.setBounds(new Rectangle(219, 37, 81, 19));
        kDContainer1.getContentPane().add(comboOperate, new KDLayout.Constraints(219, 37, 81, 19, 0));
        txtComparaValue.setBounds(new Rectangle(357, 38, 130, 19));
        kDContainer1.getContentPane().add(txtComparaValue, new KDLayout.Constraints(357, 38, 130, 19, 0));
        kDLabel1.setBounds(new Rectangle(87, 8, 50, 19));
        kDContainer1.getContentPane().add(kDLabel1, new KDLayout.Constraints(87, 8, 50, 19, 0));
        kDLabel2.setBounds(new Rectangle(235, 7, 50, 19));
        kDContainer1.getContentPane().add(kDLabel2, new KDLayout.Constraints(235, 7, 50, 19, 0));
        kDLabel3.setBounds(new Rectangle(385, 8, 50, 19));
        kDContainer1.getContentPane().add(kDLabel3, new KDLayout.Constraints(385, 8, 50, 19, 0));
        //contFormulaDescription
        contFormulaDescription.setBoundEditor(txtFormulaDescription);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkIsEnabled, "selected");
		dataBinder.registerBinding("stdFormulaScript", String.class, this.txtsStdFormulaScript, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("moneyName", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtMoneyName, "data");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("calculateType", com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum.class, this.comboCalculateType, "selectedItem");
		dataBinder.registerBinding("integerType", com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum.class, this.comboIntegerType, "selectedItem");
		dataBinder.registerBinding("fixedAmount", java.math.BigDecimal.class, this.txtFixedAmount, "text");
		dataBinder.registerBinding("hold", com.kingdee.eas.fdc.sellhouse.HoldEnum.class, this.comboHold, "selectedItem");
		dataBinder.registerBinding("factor", com.kingdee.eas.fdc.sellhouse.FactorEnum.class, this.comboFactor, "selectedItem");
		dataBinder.registerBinding("operate", com.kingdee.eas.fdc.sellhouse.OperateEnum.class, this.comboOperate, "selectedItem");
		dataBinder.registerBinding("comparaValue", java.math.BigDecimal.class, this.txtComparaValue, "value");
		dataBinder.registerBinding("formulaDescription", String.class, this.txtFormulaDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CollectionEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CollectionInfo)ov;
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
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stdFormulaScript", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("calculateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("integerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fixedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hold", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("comparaValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("formulaDescription", ValidateHelper.ON_SAVE);    		
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
     * output btnScript_actionPerformed method
     */
    protected void btnScript_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboCalculateType_itemStateChanged method
     */
    protected void comboCalculateType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtComparaValue_actionPerformed method
     */
    protected void txtComparaValue_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("stdFormulaScript"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("moneyName.*"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("calculateType"));
        sic.add(new SelectorItemInfo("integerType"));
        sic.add(new SelectorItemInfo("fixedAmount"));
        sic.add(new SelectorItemInfo("hold"));
        sic.add(new SelectorItemInfo("factor"));
        sic.add(new SelectorItemInfo("operate"));
        sic.add(new SelectorItemInfo("comparaValue"));
        sic.add(new SelectorItemInfo("formulaDescription"));
        return sic;
    }        
    	

    /**
     * output actionSure_actionPerformed method
     */
    public void actionSure_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionScript_actionPerformed method
     */
    public void actionScript_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClear_actionPerformed method
     */
    public void actionClear_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionSure(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSure() {
    	return false;
    }
	public RequestContext prepareactionScript(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionScript() {
    	return false;
    }
	public RequestContext prepareactionClear(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionClear() {
    	return false;
    }

    /**
     * output actionSure class
     */     
    protected class actionSure extends ItemAction {     
    
        public actionSure()
        {
            this(null);
        }

        public actionSure(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionSure.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSure.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSure.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCollectionEditUI.this, "actionSure", "actionSure_actionPerformed", e);
        }
    }

    /**
     * output actionScript class
     */     
    protected class actionScript extends ItemAction {     
    
        public actionScript()
        {
            this(null);
        }

        public actionScript(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionScript.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionScript.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionScript.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCollectionEditUI.this, "actionScript", "actionScript_actionPerformed", e);
        }
    }

    /**
     * output actionClear class
     */     
    protected class actionClear extends ItemAction {     
    
        public actionClear()
        {
            this(null);
        }

        public actionClear(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionClear.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClear.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClear.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCollectionEditUI.this, "actionClear", "actionClear_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CollectionEditUI");
    }




}