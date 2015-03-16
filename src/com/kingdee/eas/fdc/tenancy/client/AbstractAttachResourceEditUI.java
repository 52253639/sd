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
public abstract class AbstractAttachResourceEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAttachResourceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubareaName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AttachResType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubareaName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Building;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProjectName;
    protected com.kingdee.eas.fdc.tenancy.AttachResourceInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractAttachResourceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAttachResourceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubareaName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.f7AttachResType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboTenancyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtSellProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSubareaName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Building = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSellProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contAttachType.setName("contAttachType");
        this.contTenancyState.setName("contTenancyState");
        this.contSellProjectNumber.setName("contSellProjectNumber");
        this.contSubareaName.setName("contSubareaName");
        this.contBuilding.setName("contBuilding");
        this.contSellProjectName.setName("contSellProjectName");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.f7AttachResType.setName("f7AttachResType");
        this.comboTenancyState.setName("comboTenancyState");
        this.txtSellProjectNumber.setName("txtSellProjectNumber");
        this.txtSubareaName.setName("txtSubareaName");
        this.f7Building.setName("f7Building");
        this.txtSellProjectName.setName("txtSellProjectName");
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
        // contAttachType		
        this.contAttachType.setBoundLabelText(resHelper.getString("contAttachType.boundLabelText"));		
        this.contAttachType.setBoundLabelLength(100);		
        this.contAttachType.setBoundLabelUnderline(true);
        // contTenancyState		
        this.contTenancyState.setBoundLabelText(resHelper.getString("contTenancyState.boundLabelText"));		
        this.contTenancyState.setBoundLabelLength(100);		
        this.contTenancyState.setBoundLabelUnderline(true);
        // contSellProjectNumber		
        this.contSellProjectNumber.setBoundLabelText(resHelper.getString("contSellProjectNumber.boundLabelText"));		
        this.contSellProjectNumber.setBoundLabelLength(100);		
        this.contSellProjectNumber.setBoundLabelUnderline(true);
        // contSubareaName		
        this.contSubareaName.setBoundLabelText(resHelper.getString("contSubareaName.boundLabelText"));		
        this.contSubareaName.setBoundLabelLength(100);		
        this.contSubareaName.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contSellProjectName		
        this.contSellProjectName.setBoundLabelText(resHelper.getString("contSellProjectName.boundLabelText"));		
        this.contSellProjectName.setBoundLabelLength(100);		
        this.contSellProjectName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(255);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // f7AttachResType		
        this.f7AttachResType.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.AttachResTypeQuery");		
        this.f7AttachResType.setCommitFormat("$number$");		
        this.f7AttachResType.setEditFormat("$number$");		
        this.f7AttachResType.setDisplayFormat("$name$");
        // comboTenancyState		
        this.comboTenancyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.TenancyStateEnum").toArray());
        // txtSellProjectNumber
        // txtSubareaName
        // f7Building		
        this.f7Building.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.f7Building.setCommitFormat("$number$");		
        this.f7Building.setEditFormat("$number$");		
        this.f7Building.setDisplayFormat("$name$");
        // txtSellProjectName
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
        this.setBounds(new Rectangle(10, 10, 600, 230));
        this.setLayout(null);
        contName.setBounds(new Rectangle(299, 73, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(10, 74, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(10, 138, 564, 62));
        this.add(contDescription, null);
        contAttachType.setBounds(new Rectangle(300, 100, 270, 19));
        this.add(contAttachType, null);
        contTenancyState.setBounds(new Rectangle(10, 103, 270, 19));
        this.add(contTenancyState, null);
        contSellProjectNumber.setBounds(new Rectangle(8, 9, 270, 19));
        this.add(contSellProjectNumber, null);
        contSubareaName.setBounds(new Rectangle(9, 41, 270, 19));
        this.add(contSubareaName, null);
        contBuilding.setBounds(new Rectangle(298, 39, 270, 19));
        this.add(contBuilding, null);
        contSellProjectName.setBounds(new Rectangle(297, 7, 270, 19));
        this.add(contSellProjectName, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAttachType
        contAttachType.setBoundEditor(f7AttachResType);
        //contTenancyState
        contTenancyState.setBoundEditor(comboTenancyState);
        //contSellProjectNumber
        contSellProjectNumber.setBoundEditor(txtSellProjectNumber);
        //contSubareaName
        contSubareaName.setBoundEditor(txtSubareaName);
        //contBuilding
        contBuilding.setBoundEditor(f7Building);
        //contSellProjectName
        contSellProjectName.setBoundEditor(txtSellProjectName);

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
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("attachResType", com.kingdee.eas.fdc.tenancy.AttachResTypeInfo.class, this.f7AttachResType, "data");
		dataBinder.registerBinding("tenancyState", com.kingdee.eas.fdc.tenancy.TenancyStateEnum.class, this.comboTenancyState, "selectedItem");
		dataBinder.registerBinding("building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.f7Building, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.AttachResourceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.AttachResourceInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachResType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("building", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("attachResType.*"));
        sic.add(new SelectorItemInfo("tenancyState"));
        sic.add(new SelectorItemInfo("building.*"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "AttachResourceEditUI");
    }




}