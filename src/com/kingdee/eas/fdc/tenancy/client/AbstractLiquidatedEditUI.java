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
public abstract class AbstractLiquidatedEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLiquidatedEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEffectDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelief;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbRateDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbStandardDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandard;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbReliefDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEffectDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbOccurred;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRelief;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbBit;
    protected com.kingdee.eas.fdc.tenancy.LiquidatedInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractLiquidatedEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLiquidatedEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSimpleName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEffectDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelief = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbRateDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbStandardDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStandard = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbReliefDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkEffectDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbOccurred = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRelief = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbBit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contSimpleName.setName("contSimpleName");
        this.contSellProject.setName("contSellProject");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contEffectDate.setName("contEffectDate");
        this.contStandard.setName("contStandard");
        this.contRate.setName("contRate");
        this.contRelief.setName("contRelief");
        this.contBit.setName("contBit");
        this.cbRateDate.setName("cbRateDate");
        this.cbStandardDate.setName("cbStandardDate");
        this.txtStandard.setName("txtStandard");
        this.cbReliefDate.setName("cbReliefDate");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.txtSimpleName.setName("txtSimpleName");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.pkEffectDate.setName("pkEffectDate");
        this.cbOccurred.setName("cbOccurred");
        this.txtRate.setName("txtRate");
        this.txtRelief.setName("txtRelief");
        this.cbBit.setName("cbBit");
        // CoreUI
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
        // contSimpleName		
        this.contSimpleName.setBoundLabelText(resHelper.getString("contSimpleName.boundLabelText"));		
        this.contSimpleName.setBoundLabelLength(100);		
        this.contSimpleName.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // contEffectDate		
        this.contEffectDate.setBoundLabelText(resHelper.getString("contEffectDate.boundLabelText"));		
        this.contEffectDate.setBoundLabelLength(100);		
        this.contEffectDate.setBoundLabelUnderline(true);
        // contStandard		
        this.contStandard.setBoundLabelText(resHelper.getString("contStandard.boundLabelText"));		
        this.contStandard.setBoundLabelLength(100);		
        this.contStandard.setBoundLabelUnderline(true);
        // contRate		
        this.contRate.setBoundLabelText(resHelper.getString("contRate.boundLabelText"));		
        this.contRate.setBoundLabelLength(100);		
        this.contRate.setBoundLabelUnderline(true);
        // contRelief		
        this.contRelief.setBoundLabelText(resHelper.getString("contRelief.boundLabelText"));		
        this.contRelief.setBoundLabelLength(100);		
        this.contRelief.setBoundLabelUnderline(true);
        // contBit		
        this.contBit.setBoundLabelText(resHelper.getString("contBit.boundLabelText"));		
        this.contBit.setBoundLabelLength(100);		
        this.contBit.setBoundLabelUnderline(true);
        // cbRateDate		
        this.cbRateDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());		
        this.cbRateDate.setEnabled(false);
        // cbStandardDate		
        this.cbStandardDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());		
        this.cbStandardDate.setEnabled(false);
        // txtStandard		
        this.txtStandard.setPrecision(0);
        // cbReliefDate		
        this.cbReliefDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());		
        this.cbReliefDate.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(255);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtDescription
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setEnabled(false);
        // prmtMoneyDefine		
        this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtMoneyDefine.setDisplayFormat("$name$");		
        this.prmtMoneyDefine.setEditFormat("$number$");		
        this.prmtMoneyDefine.setCommitFormat("$number$");
        // pkEffectDate
        // cbOccurred		
        this.cbOccurred.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.OccurreStateEnum").toArray());
        // txtRate		
        this.txtRate.setDataType(1);		
        this.txtRate.setPrecision(2);
        this.txtRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRelief		
        this.txtRelief.setPrecision(0);
        // cbBit		
        this.cbBit.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.MoneyEnum").toArray());
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
        contName.setBounds(new Rectangle(373, 63, 296, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(35, 63, 296, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(35, 230, 632, 90));
        this.add(contDescription, null);
        contSimpleName.setBounds(new Rectangle(35, 99, 296, 19));
        this.add(contSimpleName, null);
        contSellProject.setBounds(new Rectangle(35, 30, 296, 19));
        this.add(contSellProject, null);
        contMoneyDefine.setBounds(new Rectangle(373, 30, 296, 19));
        this.add(contMoneyDefine, null);
        contEffectDate.setBounds(new Rectangle(373, 99, 296, 19));
        this.add(contEffectDate, null);
        contStandard.setBounds(new Rectangle(35, 177, 165, 19));
        this.add(contStandard, null);
        contRate.setBounds(new Rectangle(35, 140, 227, 19));
        this.add(contRate, null);
        contRelief.setBounds(new Rectangle(373, 140, 227, 19));
        this.add(contRelief, null);
        contBit.setBounds(new Rectangle(373, 177, 296, 19));
        this.add(contBit, null);
        cbRateDate.setBounds(new Rectangle(264, 140, 65, 19));
        this.add(cbRateDate, null);
        cbStandardDate.setBounds(new Rectangle(265, 177, 65, 19));
        this.add(cbStandardDate, null);
        txtStandard.setBounds(new Rectangle(202, 177, 60, 19));
        this.add(txtStandard, null);
        cbReliefDate.setBounds(new Rectangle(604, 140, 65, 19));
        this.add(cbReliefDate, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contSimpleName
        contSimpleName.setBoundEditor(txtSimpleName);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);
        //contEffectDate
        contEffectDate.setBoundEditor(pkEffectDate);
        //contStandard
        contStandard.setBoundEditor(cbOccurred);
        //contRate
        contRate.setBoundEditor(txtRate);
        //contRelief
        contRelief.setBoundEditor(txtRelief);
        //contBit
        contBit.setBoundEditor(cbBit);

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
		dataBinder.registerBinding("rateDate", int.class, this.cbRateDate, "selectedItem");
		dataBinder.registerBinding("standardDate", int.class, this.cbStandardDate, "selectedItem");
		dataBinder.registerBinding("standard", int.class, this.txtStandard, "value");
		dataBinder.registerBinding("reliefDate", int.class, this.cbReliefDate, "selectedItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtMoneyDefine, "data");
		dataBinder.registerBinding("effectDate", java.util.Date.class, this.pkEffectDate, "value");
		dataBinder.registerBinding("occurred", int.class, this.cbOccurred, "selectedItem");
		dataBinder.registerBinding("rate", int.class, this.txtRate, "value");
		dataBinder.registerBinding("relief", int.class, this.txtRelief, "value");
		dataBinder.registerBinding("bit", int.class, this.cbBit, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.LiquidatedEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.LiquidatedInfo)ov;
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
		getValidateHelper().registerBindProperty("standardDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reliefDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("occurred", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relief", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bit", ValidateHelper.ON_SAVE);    		
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
     * output txtRate_dataChanged method
     */
    protected void txtRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("standardDate"));
        sic.add(new SelectorItemInfo("standard"));
        sic.add(new SelectorItemInfo("reliefDate"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("moneyDefine.*"));
        sic.add(new SelectorItemInfo("effectDate"));
        sic.add(new SelectorItemInfo("occurred"));
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("relief"));
        sic.add(new SelectorItemInfo("bit"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "LiquidatedEditUI");
    }




}