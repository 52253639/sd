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
public abstract class AbstractRoomBizInfoUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomBizInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBatchNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Project;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CustomName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPurchaseDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPurchaseNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Seller;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSignDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomerPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBatchNumber;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomBizInfoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomBizInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBatchNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Project = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7CustomName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPurchaseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtPurchaseNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Seller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7PayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCustomerPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBatchNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contProject.setName("contProject");
        this.contRoomNumber.setName("contRoomNumber");
        this.contCustomName.setName("contCustomName");
        this.contPurchaseDate.setName("contPurchaseDate");
        this.contPurchaseNumber.setName("contPurchaseNumber");
        this.contSeller.setName("contSeller");
        this.contSignDate.setName("contSignDate");
        this.contContractNumber.setName("contContractNumber");
        this.contPayType.setName("contPayType");
        this.contTotalAmount.setName("contTotalAmount");
        this.contCustomerPhone.setName("contCustomerPhone");
        this.contBatchNumber.setName("contBatchNumber");
        this.f7Project.setName("f7Project");
        this.f7RoomNumber.setName("f7RoomNumber");
        this.f7CustomName.setName("f7CustomName");
        this.pkPurchaseDate.setName("pkPurchaseDate");
        this.txtPurchaseNumber.setName("txtPurchaseNumber");
        this.f7Seller.setName("f7Seller");
        this.pkSignDate.setName("pkSignDate");
        this.txtContractNumber.setName("txtContractNumber");
        this.f7PayType.setName("f7PayType");
        this.txtTotalAmount.setName("txtTotalAmount");
        this.txtCustomerPhone.setName("txtCustomerPhone");
        this.txtBatchNumber.setName("txtBatchNumber");
        // CoreUI		
        this.setAutoscrolls(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);
        // contRoomNumber		
        this.contRoomNumber.setBoundLabelText(resHelper.getString("contRoomNumber.boundLabelText"));		
        this.contRoomNumber.setBoundLabelLength(100);		
        this.contRoomNumber.setBoundLabelUnderline(true);		
        this.contRoomNumber.setEnabled(false);
        // contCustomName		
        this.contCustomName.setBoundLabelText(resHelper.getString("contCustomName.boundLabelText"));		
        this.contCustomName.setBoundLabelLength(100);		
        this.contCustomName.setBoundLabelUnderline(true);		
        this.contCustomName.setEnabled(false);
        // contPurchaseDate		
        this.contPurchaseDate.setBoundLabelText(resHelper.getString("contPurchaseDate.boundLabelText"));		
        this.contPurchaseDate.setBoundLabelLength(100);		
        this.contPurchaseDate.setBoundLabelUnderline(true);		
        this.contPurchaseDate.setEnabled(false);
        // contPurchaseNumber		
        this.contPurchaseNumber.setBoundLabelText(resHelper.getString("contPurchaseNumber.boundLabelText"));		
        this.contPurchaseNumber.setBoundLabelLength(100);		
        this.contPurchaseNumber.setBoundLabelUnderline(true);		
        this.contPurchaseNumber.setEnabled(false);
        // contSeller		
        this.contSeller.setBoundLabelText(resHelper.getString("contSeller.boundLabelText"));		
        this.contSeller.setBoundLabelLength(100);		
        this.contSeller.setBoundLabelUnderline(true);		
        this.contSeller.setEnabled(false);
        // contSignDate		
        this.contSignDate.setBoundLabelText(resHelper.getString("contSignDate.boundLabelText"));		
        this.contSignDate.setBoundLabelLength(100);		
        this.contSignDate.setBoundLabelUnderline(true);		
        this.contSignDate.setEnabled(false);
        // contContractNumber		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contContractNumber.setBoundLabelLength(100);		
        this.contContractNumber.setBoundLabelUnderline(true);		
        this.contContractNumber.setEnabled(false);
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);		
        this.contPayType.setEnabled(false);
        // contTotalAmount		
        this.contTotalAmount.setBoundLabelText(resHelper.getString("contTotalAmount.boundLabelText"));		
        this.contTotalAmount.setBoundLabelLength(100);		
        this.contTotalAmount.setBoundLabelUnderline(true);		
        this.contTotalAmount.setEnabled(false);
        // contCustomerPhone		
        this.contCustomerPhone.setBoundLabelText(resHelper.getString("contCustomerPhone.boundLabelText"));		
        this.contCustomerPhone.setBoundLabelLength(100);		
        this.contCustomerPhone.setBoundLabelUnderline(true);		
        this.contCustomerPhone.setEnabled(false);
        // contBatchNumber		
        this.contBatchNumber.setBoundLabelText(resHelper.getString("contBatchNumber.boundLabelText"));		
        this.contBatchNumber.setBoundLabelLength(100);		
        this.contBatchNumber.setBoundLabelUnderline(true);		
        this.contBatchNumber.setVisible(false);
        // f7Project		
        this.f7Project.setDisplayFormat("$name$");		
        this.f7Project.setEditFormat("$number$");		
        this.f7Project.setCommitFormat("$number$");		
        this.f7Project.setEnabled(false);
        // f7RoomNumber		
        this.f7RoomNumber.setCommitFormat("$number$");		
        this.f7RoomNumber.setEditFormat("$displayName$");		
        this.f7RoomNumber.setDisplayFormat("$displayName$");		
        this.f7RoomNumber.setEnabled(false);
        // f7CustomName		
        this.f7CustomName.setDisplayFormat("$name$");		
        this.f7CustomName.setEditFormat("$number$");		
        this.f7CustomName.setCommitFormat("$number$");		
        this.f7CustomName.setEnabled(false);
        // pkPurchaseDate		
        this.pkPurchaseDate.setEnabled(false);
        // txtPurchaseNumber		
        this.txtPurchaseNumber.setEnabled(false);
        // f7Seller		
        this.f7Seller.setCommitFormat("$number$");		
        this.f7Seller.setEditFormat("$number$");		
        this.f7Seller.setDisplayFormat("$name$");		
        this.f7Seller.setEnabled(false);
        // pkSignDate		
        this.pkSignDate.setEnabled(false);
        // txtContractNumber		
        this.txtContractNumber.setEnabled(false);
        // f7PayType		
        this.f7PayType.setDisplayFormat("$name$");		
        this.f7PayType.setEditFormat("$number$");		
        this.f7PayType.setCommitFormat("$number$");		
        this.f7PayType.setEnabled(false);
        // txtTotalAmount		
        this.txtTotalAmount.setDataType(1);		
        this.txtTotalAmount.setPrecision(2);		
        this.txtTotalAmount.setEnabled(false);
        // txtCustomerPhone		
        this.txtCustomerPhone.setEnabled(false);		
        this.txtCustomerPhone.setEditable(false);
        // txtBatchNumber		
        this.txtBatchNumber.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 993, 105));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 993, 105));
        contProject.setBounds(new Rectangle(10, 5, 270, 19));
        this.add(contProject, new KDLayout.Constraints(10, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomNumber.setBounds(new Rectangle(361, 5, 270, 19));
        this.add(contRoomNumber, new KDLayout.Constraints(361, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCustomName.setBounds(new Rectangle(10, 27, 270, 19));
        this.add(contCustomName, new KDLayout.Constraints(10, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurchaseDate.setBounds(new Rectangle(10, 49, 270, 19));
        this.add(contPurchaseDate, new KDLayout.Constraints(10, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurchaseNumber.setBounds(new Rectangle(361, 49, 270, 19));
        this.add(contPurchaseNumber, new KDLayout.Constraints(361, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSeller.setBounds(new Rectangle(713, 27, 270, 19));
        this.add(contSeller, new KDLayout.Constraints(713, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSignDate.setBounds(new Rectangle(10, 71, 270, 19));
        this.add(contSignDate, new KDLayout.Constraints(10, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractNumber.setBounds(new Rectangle(361, 71, 270, 19));
        this.add(contContractNumber, new KDLayout.Constraints(361, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayType.setBounds(new Rectangle(713, 49, 270, 19));
        this.add(contPayType, new KDLayout.Constraints(713, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalAmount.setBounds(new Rectangle(713, 71, 270, 19));
        this.add(contTotalAmount, new KDLayout.Constraints(713, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerPhone.setBounds(new Rectangle(361, 27, 270, 19));
        this.add(contCustomerPhone, new KDLayout.Constraints(361, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBatchNumber.setBounds(new Rectangle(713, 5, 270, 19));
        this.add(contBatchNumber, new KDLayout.Constraints(713, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contProject
        contProject.setBoundEditor(f7Project);
        //contRoomNumber
        contRoomNumber.setBoundEditor(f7RoomNumber);
        //contCustomName
        contCustomName.setBoundEditor(f7CustomName);
        //contPurchaseDate
        contPurchaseDate.setBoundEditor(pkPurchaseDate);
        //contPurchaseNumber
        contPurchaseNumber.setBoundEditor(txtPurchaseNumber);
        //contSeller
        contSeller.setBoundEditor(f7Seller);
        //contSignDate
        contSignDate.setBoundEditor(pkSignDate);
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contTotalAmount
        contTotalAmount.setBoundEditor(txtTotalAmount);
        //contCustomerPhone
        contCustomerPhone.setBoundEditor(txtCustomerPhone);
        //contBatchNumber
        contBatchNumber.setBoundEditor(txtBatchNumber);

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomBizInfoUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomBizInfoUI");
    }




}