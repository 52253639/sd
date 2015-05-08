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
public abstract class AbstractDeptTaskProgressReportEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeptTaskProgressReportEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompletePrecent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReportor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReportDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton complete;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton noComplete;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompletePrecent;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRealStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRealEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReportor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkReportDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaskType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractDeptTaskProgressReportEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDeptTaskProgressReportEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contTaskName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompletePrecent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReportor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReportDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.complete = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.noComplete = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.txtTaskName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompletePrecent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkRealStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkRealEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtReportor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkReportDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTaskType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contTaskName.setName("contTaskName");
        this.contCompletePrecent.setName("contCompletePrecent");
        this.contRealStartDate.setName("contRealStartDate");
        this.contRealEndDate.setName("contRealEndDate");
        this.contReportor.setName("contReportor");
        this.contReportDate.setName("contReportDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.complete.setName("complete");
        this.noComplete.setName("noComplete");
        this.txtTaskName.setName("txtTaskName");
        this.txtCompletePrecent.setName("txtCompletePrecent");
        this.pkRealStartDate.setName("pkRealStartDate");
        this.pkRealEndDate.setName("pkRealEndDate");
        this.prmtReportor.setName("prmtReportor");
        this.pkReportDate.setName("pkReportDate");
        this.txtTaskType.setName("txtTaskType");
        this.txtDescription.setName("txtDescription");
        // CoreUI
        // contTaskName		
        this.contTaskName.setBoundLabelText(resHelper.getString("contTaskName.boundLabelText"));		
        this.contTaskName.setBoundLabelLength(100);		
        this.contTaskName.setBoundLabelUnderline(true);
        // contCompletePrecent		
        this.contCompletePrecent.setBoundLabelText(resHelper.getString("contCompletePrecent.boundLabelText"));		
        this.contCompletePrecent.setBoundLabelLength(100);		
        this.contCompletePrecent.setBoundLabelUnderline(true);
        // contRealStartDate		
        this.contRealStartDate.setBoundLabelText(resHelper.getString("contRealStartDate.boundLabelText"));		
        this.contRealStartDate.setBoundLabelLength(100);		
        this.contRealStartDate.setBoundLabelUnderline(true);
        // contRealEndDate		
        this.contRealEndDate.setBoundLabelText(resHelper.getString("contRealEndDate.boundLabelText"));		
        this.contRealEndDate.setBoundLabelLength(100);		
        this.contRealEndDate.setBoundLabelUnderline(true);
        // contReportor		
        this.contReportor.setBoundLabelText(resHelper.getString("contReportor.boundLabelText"));		
        this.contReportor.setBoundLabelLength(100);		
        this.contReportor.setBoundLabelUnderline(true);
        // contReportDate		
        this.contReportDate.setBoundLabelText(resHelper.getString("contReportDate.boundLabelText"));		
        this.contReportDate.setBoundLabelLength(100);		
        this.contReportDate.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDButtonGroup1
        // complete		
        this.complete.setText(resHelper.getString("complete.text"));
        // noComplete		
        this.noComplete.setText(resHelper.getString("noComplete.text"));		
        this.noComplete.setSelected(true);
        // txtTaskName		
        this.txtTaskName.setMaxLength(255);
        // txtCompletePrecent		
        this.txtCompletePrecent.setRequired(true);
        // pkRealStartDate
        // pkRealEndDate
        // prmtReportor		
        this.prmtReportor.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.prmtReportor.setDisplayFormat("$name$");		
        this.prmtReportor.setEditFormat("$name$");		
        this.prmtReportor.setCommitFormat("$name$");		
        this.prmtReportor.setEnabled(false);
        // pkReportDate
        // txtTaskType
        // txtDescription		
        this.txtDescription.setMaxLength(200);
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
        this.setBounds(new Rectangle(10, 10, 670, 280));
        this.setLayout(null);
        contTaskName.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contTaskName, null);
        contCompletePrecent.setBounds(new Rectangle(380, 45, 270, 19));
        this.add(contCompletePrecent, null);
        contRealStartDate.setBounds(new Rectangle(10, 85, 270, 19));
        this.add(contRealStartDate, null);
        contRealEndDate.setBounds(new Rectangle(380, 80, 270, 19));
        this.add(contRealEndDate, null);
        contReportor.setBounds(new Rectangle(10, 215, 270, 19));
        this.add(contReportor, null);
        contReportDate.setBounds(new Rectangle(380, 215, 270, 19));
        this.add(contReportDate, null);
        kDLabelContainer1.setBounds(new Rectangle(380, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 118, 641, 76));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(10, 47, 51, 19));
        this.add(kDLabelContainer3, null);
        complete.setBounds(new Rectangle(106, 47, 39, 19));
        this.add(complete, null);
        noComplete.setBounds(new Rectangle(170, 47, 37, 19));
        this.add(noComplete, null);
        //contTaskName
        contTaskName.setBoundEditor(txtTaskName);
        //contCompletePrecent
        contCompletePrecent.setBoundEditor(txtCompletePrecent);
        //contRealStartDate
        contRealStartDate.setBoundEditor(pkRealStartDate);
        //contRealEndDate
        contRealEndDate.setBoundEditor(pkRealEndDate);
        //contReportor
        contReportor.setBoundEditor(prmtReportor);
        //contReportDate
        contReportDate.setBoundEditor(pkReportDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtTaskType);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtDescription);

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
		dataBinder.registerBinding("taskName", String.class, this.txtTaskName, "text");
		dataBinder.registerBinding("completePrecent", java.math.BigDecimal.class, this.txtCompletePrecent, "value");
		dataBinder.registerBinding("realStartDate", java.util.Date.class, this.pkRealStartDate, "value");
		dataBinder.registerBinding("realEndDate", java.util.Date.class, this.pkRealEndDate, "value");
		dataBinder.registerBinding("reportor", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtReportor, "userObject");
		dataBinder.registerBinding("reportDate", java.util.Date.class, this.pkReportDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.DeptTaskProgressReportEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo)ov;
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
		getValidateHelper().registerBindProperty("taskName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completePrecent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("realStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("realEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportDate", ValidateHelper.ON_SAVE);    
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
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("taskName"));
        sic.add(new SelectorItemInfo("completePrecent"));
        sic.add(new SelectorItemInfo("realStartDate"));
        sic.add(new SelectorItemInfo("realEndDate"));
        sic.add(new SelectorItemInfo("reportor"));
        sic.add(new SelectorItemInfo("reportDate"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "DeptTaskProgressReportEditUI");
    }




}