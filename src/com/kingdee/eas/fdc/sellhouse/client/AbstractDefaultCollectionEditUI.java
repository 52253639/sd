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
public abstract class AbstractDefaultCollectionEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDefaultCollectionEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCalculateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHold;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFormulaDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScript;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEmpty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsStdFormulaScript;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCalculateType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHold;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScale;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtScale;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFormulaDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsStdFormulaScript;
    protected com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo editData = null;
    protected ActionScript actionScript = null;
    protected ActionEmpty actionEmpty = null;
    /**
     * output class constructor
     */
    public AbstractDefaultCollectionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDefaultCollectionEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionScript
        this.actionScript = new ActionScript(this);
        getActionManager().registerAction("actionScript", actionScript);
         this.actionScript.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEmpty
        this.actionEmpty = new ActionEmpty(this);
        getActionManager().registerAction("actionEmpty", actionEmpty);
         this.actionEmpty.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCalculateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHold = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntegerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSimpleName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contFormulaDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnScript = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEmpty = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contsStdFormulaScript = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCalculateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboHold = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboIntegerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contScale = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtScale = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormulaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtsStdFormulaScript = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.contCalculateType.setName("contCalculateType");
        this.contHold.setName("contHold");
        this.contIntegerType.setName("contIntegerType");
        this.contRemark.setName("contRemark");
        this.contProject.setName("contProject");
        this.contDescription.setName("contDescription");
        this.contSimpleName.setName("contSimpleName");
        this.kDContainer1.setName("kDContainer1");
        this.contFormulaDescription.setName("contFormulaDescription");
        this.btnScript.setName("btnScript");
        this.btnEmpty.setName("btnEmpty");
        this.contsStdFormulaScript.setName("contsStdFormulaScript");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.comboCalculateType.setName("comboCalculateType");
        this.comboHold.setName("comboHold");
        this.comboIntegerType.setName("comboIntegerType");
        this.txtRemark.setName("txtRemark");
        this.prmtProject.setName("prmtProject");
        this.txtDescription.setName("txtDescription");
        this.txtSimpleName.setName("txtSimpleName");
        this.contScale.setName("contScale");
        this.txtScale.setName("txtScale");
        this.txtFormulaDescription.setName("txtFormulaDescription");
        this.txtsStdFormulaScript.setName("txtsStdFormulaScript");
        // CoreUI
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setEnabled(false);		
        this.chkIsEnabled.setVisible(false);
        // contCalculateType		
        this.contCalculateType.setBoundLabelText(resHelper.getString("contCalculateType.boundLabelText"));		
        this.contCalculateType.setBoundLabelLength(100);		
        this.contCalculateType.setBoundLabelUnderline(true);
        // contHold		
        this.contHold.setBoundLabelText(resHelper.getString("contHold.boundLabelText"));		
        this.contHold.setBoundLabelLength(100);		
        this.contHold.setBoundLabelUnderline(true);
        // contIntegerType		
        this.contIntegerType.setBoundLabelText(resHelper.getString("contIntegerType.boundLabelText"));		
        this.contIntegerType.setBoundLabelLength(100);		
        this.contIntegerType.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setEnabled(false);		
        this.contDescription.setVisible(false);
        // contSimpleName		
        this.contSimpleName.setBoundLabelText(resHelper.getString("contSimpleName.boundLabelText"));		
        this.contSimpleName.setBoundLabelLength(100);		
        this.contSimpleName.setBoundLabelUnderline(true);		
        this.contSimpleName.setEnabled(false);		
        this.contSimpleName.setVisible(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contFormulaDescription		
        this.contFormulaDescription.setBoundLabelText(resHelper.getString("contFormulaDescription.boundLabelText"));		
        this.contFormulaDescription.setBoundLabelLength(100);		
        this.contFormulaDescription.setBoundLabelUnderline(true);		
        this.contFormulaDescription.setEnabled(false);		
        this.contFormulaDescription.setVisible(false);
        // btnScript
        this.btnScript.setAction((IItemAction)ActionProxyFactory.getProxy(actionScript, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScript.setText(resHelper.getString("btnScript.text"));		
        this.btnScript.setVisible(false);		
        this.btnScript.setEnabled(false);
        // btnEmpty
        this.btnEmpty.setAction((IItemAction)ActionProxyFactory.getProxy(actionEmpty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEmpty.setText(resHelper.getString("btnEmpty.text"));		
        this.btnEmpty.setEnabled(false);		
        this.btnEmpty.setVisible(false);
        // contsStdFormulaScript		
        this.contsStdFormulaScript.setBoundLabelText(resHelper.getString("contsStdFormulaScript.boundLabelText"));		
        this.contsStdFormulaScript.setBoundLabelLength(100);		
        this.contsStdFormulaScript.setVisible(false);
        // txtName
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // comboCalculateType		
        this.comboCalculateType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum").toArray());		
        this.comboCalculateType.setSelectedIndex(1);
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
        // comboHold		
        this.comboHold.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HoldEnum").toArray());		
        this.comboHold.setSelectedIndex(1);
        // comboIntegerType		
        this.comboIntegerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum").toArray());		
        this.comboIntegerType.setSelectedIndex(1);
        // txtRemark
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        // txtDescription		
        this.txtDescription.setMaxLength(255);		
        this.txtDescription.setEnabled(false);		
        this.txtDescription.setVisible(false);
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);		
        this.txtSimpleName.setEnabled(false);		
        this.txtSimpleName.setVisible(false);
        // contScale		
        this.contScale.setBoundLabelText(resHelper.getString("contScale.boundLabelText"));		
        this.contScale.setBoundLabelLength(100);		
        this.contScale.setBoundLabelUnderline(true);
        // txtScale		
        this.txtScale.setDataType(1);		
        this.txtScale.setPrecision(2);		
        this.txtScale.setSupportedEmpty(true);
        // txtFormulaDescription		
        this.txtFormulaDescription.setEnabled(false);		
        this.txtFormulaDescription.setVisible(false);
        // txtsStdFormulaScript		
        this.txtsStdFormulaScript.setVisible(false);
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
        this.setBounds(new Rectangle(10, 10, 600, 420));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 420));
        contName.setBounds(new Rectangle(310, 13, 270, 19));
        this.add(contName, new KDLayout.Constraints(310, 13, 270, 19, 0));
        contNumber.setBounds(new Rectangle(14, 13, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(14, 13, 270, 19, 0));
        chkIsEnabled.setBounds(new Rectangle(31, 358, 140, 19));
        this.add(chkIsEnabled, new KDLayout.Constraints(31, 358, 140, 19, 0));
        contCalculateType.setBounds(new Rectangle(310, 44, 270, 19));
        this.add(contCalculateType, new KDLayout.Constraints(310, 44, 270, 19, 0));
        contHold.setBounds(new Rectangle(14, 75, 270, 19));
        this.add(contHold, new KDLayout.Constraints(14, 75, 270, 19, 0));
        contIntegerType.setBounds(new Rectangle(310, 75, 270, 19));
        this.add(contIntegerType, new KDLayout.Constraints(310, 75, 270, 19, 0));
        contRemark.setBounds(new Rectangle(19, 112, 560, 70));
        this.add(contRemark, new KDLayout.Constraints(19, 112, 560, 70, 0));
        contProject.setBounds(new Rectangle(14, 44, 270, 19));
        this.add(contProject, new KDLayout.Constraints(14, 44, 270, 19, 0));
        contDescription.setBounds(new Rectangle(24, 383, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(24, 383, 270, 19, 0));
        contSimpleName.setBounds(new Rectangle(315, 383, 270, 19));
        this.add(contSimpleName, new KDLayout.Constraints(315, 383, 270, 19, 0));
        kDContainer1.setBounds(new Rectangle(19, 201, 560, 66));
        this.add(kDContainer1, new KDLayout.Constraints(19, 201, 560, 66, 0));
        contFormulaDescription.setBounds(new Rectangle(17, 198, 562, 138));
        this.add(contFormulaDescription, new KDLayout.Constraints(17, 198, 562, 138, 0));
        btnScript.setBounds(new Rectangle(375, 349, 90, 19));
        this.add(btnScript, new KDLayout.Constraints(375, 349, 90, 19, 0));
        btnEmpty.setBounds(new Rectangle(486, 349, 88, 19));
        this.add(btnEmpty, new KDLayout.Constraints(486, 349, 88, 19, 0));
        contsStdFormulaScript.setBounds(new Rectangle(143, 348, 211, 19));
        this.add(contsStdFormulaScript, new KDLayout.Constraints(143, 348, 211, 19, 0));
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contCalculateType
        contCalculateType.setBoundEditor(comboCalculateType);
        //contHold
        contHold.setBoundEditor(comboHold);
        //contIntegerType
        contIntegerType.setBoundEditor(comboIntegerType);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contSimpleName
        contSimpleName.setBoundEditor(txtSimpleName);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(19, 201, 560, 66));        contScale.setBounds(new Rectangle(11, 13, 270, 19));
        kDContainer1.getContentPane().add(contScale, new KDLayout.Constraints(11, 13, 270, 19, 0));
        //contScale
        contScale.setBoundEditor(txtScale);
        //contFormulaDescription
        contFormulaDescription.setBoundEditor(txtFormulaDescription);
        //contsStdFormulaScript
        contsStdFormulaScript.setBoundEditor(txtsStdFormulaScript);

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
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("calculateType", com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum.class, this.comboCalculateType, "selectedItem");
		dataBinder.registerBinding("hold", com.kingdee.eas.fdc.sellhouse.HoldEnum.class, this.comboHold, "selectedItem");
		dataBinder.registerBinding("integerType", com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum.class, this.comboIntegerType, "selectedItem");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("scale", java.math.BigDecimal.class, this.txtScale, "value");
		dataBinder.registerBinding("formulaDescription", String.class, this.txtFormulaDescription, "text");
		dataBinder.registerBinding("stdFormulaScript", String.class, this.txtsStdFormulaScript, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.DefaultCollectionEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("calculateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hold", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("integerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("formulaDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stdFormulaScript", ValidateHelper.ON_SAVE);    		
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
     * output comboCalculateType_itemStateChanged method
     */
    protected void comboCalculateType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("calculateType"));
        sic.add(new SelectorItemInfo("hold"));
        sic.add(new SelectorItemInfo("integerType"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("scale"));
        sic.add(new SelectorItemInfo("formulaDescription"));
        sic.add(new SelectorItemInfo("stdFormulaScript"));
        return sic;
    }        
    	

    /**
     * output actionScript_actionPerformed method
     */
    public void actionScript_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEmpty_actionPerformed method
     */
    public void actionEmpty_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionScript(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionScript() {
    	return false;
    }
	public RequestContext prepareActionEmpty(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEmpty() {
    	return false;
    }

    /**
     * output ActionScript class
     */     
    protected class ActionScript extends ItemAction {     
    
        public ActionScript()
        {
            this(null);
        }

        public ActionScript(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionScript.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScript.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScript.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultCollectionEditUI.this, "ActionScript", "actionScript_actionPerformed", e);
        }
    }

    /**
     * output ActionEmpty class
     */     
    protected class ActionEmpty extends ItemAction {     
    
        public ActionEmpty()
        {
            this(null);
        }

        public ActionEmpty(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEmpty.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEmpty.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEmpty.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultCollectionEditUI.this, "ActionEmpty", "actionEmpty_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "DefaultCollectionEditUI");
    }




}