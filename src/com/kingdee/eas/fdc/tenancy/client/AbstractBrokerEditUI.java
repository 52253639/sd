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
public abstract class AbstractBrokerEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBrokerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPassword;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIdNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWeChatNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountName;
    protected com.kingdee.bos.ctrl.swing.KDPanel photePanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIdentity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgency;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPassword;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbSex;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtIdNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWeChatNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAccountNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAccountName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtIdentity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgency;
    protected com.kingdee.eas.fdc.tenancy.BrokerInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBrokerEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBrokerEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPassword = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIdNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWeChatNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccountNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccountName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.photePanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contIdentity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPassword = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbSex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtIdNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWeChatNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAccountNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAccountName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtIdentity = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAgency = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.contPassword.setName("contPassword");
        this.contPhone.setName("contPhone");
        this.contCreateTime.setName("contCreateTime");
        this.contSex.setName("contSex");
        this.contIdNum.setName("contIdNum");
        this.contWeChatNum.setName("contWeChatNum");
        this.contBank.setName("contBank");
        this.contAccountNum.setName("contAccountNum");
        this.contAccountName.setName("contAccountName");
        this.photePanel.setName("photePanel");
        this.contIdentity.setName("contIdentity");
        this.contAgency.setName("contAgency");
        this.txtName.setName("txtName");
        this.txtPassword.setName("txtPassword");
        this.txtPhone.setName("txtPhone");
        this.pkCreateTime.setName("pkCreateTime");
        this.cbSex.setName("cbSex");
        this.txtIdNum.setName("txtIdNum");
        this.txtWeChatNum.setName("txtWeChatNum");
        this.txtBank.setName("txtBank");
        this.txtAccountNum.setName("txtAccountNum");
        this.txtAccountName.setName("txtAccountName");
        this.txtIdentity.setName("txtIdentity");
        this.txtAgency.setName("txtAgency");
        // CoreUI		
        this.setPreferredSize(new Dimension(297,329));
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(120);		
        this.contName.setBoundLabelUnderline(true);
        // contPassword		
        this.contPassword.setBoundLabelText(resHelper.getString("contPassword.boundLabelText"));		
        this.contPassword.setBoundLabelLength(120);		
        this.contPassword.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(120);		
        this.contPhone.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(120);
        // contSex		
        this.contSex.setBoundLabelText(resHelper.getString("contSex.boundLabelText"));		
        this.contSex.setBoundLabelLength(120);		
        this.contSex.setBoundLabelUnderline(true);
        // contIdNum		
        this.contIdNum.setBoundLabelText(resHelper.getString("contIdNum.boundLabelText"));		
        this.contIdNum.setBoundLabelLength(120);		
        this.contIdNum.setBoundLabelUnderline(true);
        // contWeChatNum		
        this.contWeChatNum.setBoundLabelText(resHelper.getString("contWeChatNum.boundLabelText"));		
        this.contWeChatNum.setBoundLabelLength(120);		
        this.contWeChatNum.setBoundLabelUnderline(true);
        // contBank		
        this.contBank.setBoundLabelText(resHelper.getString("contBank.boundLabelText"));		
        this.contBank.setBoundLabelLength(120);		
        this.contBank.setBoundLabelUnderline(true);
        // contAccountNum		
        this.contAccountNum.setBoundLabelText(resHelper.getString("contAccountNum.boundLabelText"));		
        this.contAccountNum.setBoundLabelLength(120);		
        this.contAccountNum.setBoundLabelUnderline(true);
        // contAccountName		
        this.contAccountName.setBoundLabelText(resHelper.getString("contAccountName.boundLabelText"));		
        this.contAccountName.setBoundLabelLength(120);		
        this.contAccountName.setBoundLabelUnderline(true);
        // photePanel		
        this.photePanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // contIdentity		
        this.contIdentity.setBoundLabelText(resHelper.getString("contIdentity.boundLabelText"));		
        this.contIdentity.setBoundLabelLength(120);		
        this.contIdentity.setBoundLabelUnderline(true);
        // contAgency		
        this.contAgency.setBoundLabelText(resHelper.getString("contAgency.boundLabelText"));		
        this.contAgency.setBoundLabelLength(120);		
        this.contAgency.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // txtPassword		
        this.txtPassword.setRequired(true);		
        this.txtPassword.setEnabled(false);
        // txtPhone		
        this.txtPhone.setRequired(true);		
        this.txtPhone.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setVisible(true);
        // cbSex		
        this.cbSex.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());		
        this.cbSex.setEnabled(false);
        // txtIdNum		
        this.txtIdNum.setRequired(true);		
        this.txtIdNum.setEnabled(false);
        // txtWeChatNum		
        this.txtWeChatNum.setRequired(true);		
        this.txtWeChatNum.setEnabled(false);
        // txtBank		
        this.txtBank.setRequired(true);		
        this.txtBank.setEnabled(false);
        // txtAccountNum		
        this.txtAccountNum.setRequired(true);		
        this.txtAccountNum.setEnabled(false);
        // txtAccountName		
        this.txtAccountName.setRequired(true);		
        this.txtAccountName.setEnabled(false);
        // txtIdentity		
        this.txtIdentity.setRequired(true);		
        this.txtIdentity.setEnabled(false);
        // txtAgency		
        this.txtAgency.setRequired(true);		
        this.txtAgency.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 297, 340));
        this.setLayout(null);
        contName.setBounds(new Rectangle(16, 16, 270, 19));
        this.add(contName, null);
        contPassword.setBounds(new Rectangle(16, 41, 270, 19));
        this.add(contPassword, null);
        contPhone.setBounds(new Rectangle(16, 66, 270, 19));
        this.add(contPhone, null);
        contCreateTime.setBounds(new Rectangle(16, 297, 270, 19));
        this.add(contCreateTime, null);
        contSex.setBounds(new Rectangle(16, 91, 270, 19));
        this.add(contSex, null);
        contIdNum.setBounds(new Rectangle(16, 116, 270, 19));
        this.add(contIdNum, null);
        contWeChatNum.setBounds(new Rectangle(16, 166, 270, 19));
        this.add(contWeChatNum, null);
        contBank.setBounds(new Rectangle(16, 191, 270, 19));
        this.add(contBank, null);
        contAccountNum.setBounds(new Rectangle(16, 216, 270, 19));
        this.add(contAccountNum, null);
        contAccountName.setBounds(new Rectangle(16, 241, 270, 19));
        this.add(contAccountName, null);
        photePanel.setBounds(new Rectangle(298, 16, 468, 287));
        this.add(photePanel, null);
        contIdentity.setBounds(new Rectangle(16, 141, 270, 19));
        this.add(contIdentity, null);
        contAgency.setBounds(new Rectangle(16, 266, 270, 19));
        this.add(contAgency, null);
        //contName
        contName.setBoundEditor(txtName);
        //contPassword
        contPassword.setBoundEditor(txtPassword);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contSex
        contSex.setBoundEditor(cbSex);
        //contIdNum
        contIdNum.setBoundEditor(txtIdNum);
        //contWeChatNum
        contWeChatNum.setBoundEditor(txtWeChatNum);
        //contBank
        contBank.setBoundEditor(txtBank);
        //contAccountNum
        contAccountNum.setBoundEditor(txtAccountNum);
        //contAccountName
        contAccountName.setBoundEditor(txtAccountName);
        photePanel.setLayout(null);        //contIdentity
        contIdentity.setBoundEditor(txtIdentity);
        //contAgency
        contAgency.setBoundEditor(txtAgency);

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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
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
		dataBinder.registerBinding("password", String.class, this.txtPassword, "text");
		dataBinder.registerBinding("phone", String.class, this.txtPhone, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("sex", com.kingdee.eas.fdc.sellhouse.SexEnum.class, this.cbSex, "selectedItem");
		dataBinder.registerBinding("idNum", String.class, this.txtIdNum, "text");
		dataBinder.registerBinding("weChatNum", String.class, this.txtWeChatNum, "text");
		dataBinder.registerBinding("bank", String.class, this.txtBank, "text");
		dataBinder.registerBinding("accountNum", String.class, this.txtAccountNum, "text");
		dataBinder.registerBinding("accountName", String.class, this.txtAccountName, "text");
		dataBinder.registerBinding("identity", String.class, this.txtIdentity, "text");
		dataBinder.registerBinding("agency", String.class, this.txtAgency, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.BrokerEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.BrokerInfo)ov;
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
		getValidateHelper().registerBindProperty("password", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("idNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("weChatNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("identity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agency", ValidateHelper.ON_SAVE);    		
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("password"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("idNum"));
        sic.add(new SelectorItemInfo("weChatNum"));
        sic.add(new SelectorItemInfo("bank"));
        sic.add(new SelectorItemInfo("accountNum"));
        sic.add(new SelectorItemInfo("accountName"));
        sic.add(new SelectorItemInfo("identity"));
        sic.add(new SelectorItemInfo("agency"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "BrokerEditUI");
    }




}