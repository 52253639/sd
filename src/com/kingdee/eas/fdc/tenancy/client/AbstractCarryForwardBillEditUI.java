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
public abstract class AbstractCarryForwardBillEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCarryForwardBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPaneRev;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemainDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRemainDepositAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarryForwardType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCarryForwardType;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCarryForwardBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCarryForwardBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.tabPaneRev = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contTenancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemainDepositAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRemainDepositAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7TenancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7TenancyRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCarryForwardType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCarryForwardType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tabPaneRev.setName("tabPaneRev");
        this.contTenancyBill.setName("contTenancyBill");
        this.contRoom.setName("contRoom");
        this.contRemainDepositAmount.setName("contRemainDepositAmount");
        this.txtRemainDepositAmount.setName("txtRemainDepositAmount");
        this.f7TenancyBill.setName("f7TenancyBill");
        this.f7TenancyRoom.setName("f7TenancyRoom");
        this.contCarryForwardType.setName("contCarryForwardType");
        this.comboCarryForwardType.setName("comboCarryForwardType");
        // CoreUI
        // tabPaneRev
        // contTenancyBill		
        this.contTenancyBill.setBoundLabelText(resHelper.getString("contTenancyBill.boundLabelText"));		
        this.contTenancyBill.setBoundLabelUnderline(true);		
        this.contTenancyBill.setBoundLabelLength(100);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contRemainDepositAmount		
        this.contRemainDepositAmount.setBoundLabelText(resHelper.getString("contRemainDepositAmount.boundLabelText"));		
        this.contRemainDepositAmount.setBoundLabelLength(100);		
        this.contRemainDepositAmount.setBoundLabelUnderline(true);
        // txtRemainDepositAmount
        // f7TenancyBill		
        this.f7TenancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7TenancyBill.setCommitFormat("$number$");		
        this.f7TenancyBill.setEditFormat("$number$");		
        this.f7TenancyBill.setDisplayFormat("$tenancyName$");
        // f7TenancyRoom		
        this.f7TenancyRoom.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyRoomEntryQuery");		
        this.f7TenancyRoom.setDisplayFormat("$roomLongNum$");		
        this.f7TenancyRoom.setEditFormat("$roomLongNum$");		
        this.f7TenancyRoom.setCommitFormat("$roomLongNum$");
        // contCarryForwardType		
        this.contCarryForwardType.setBoundLabelText(resHelper.getString("contCarryForwardType.boundLabelText"));		
        this.contCarryForwardType.setBoundLabelUnderline(true);		
        this.contCarryForwardType.setBoundLabelLength(100);
        // comboCarryForwardType		
        this.comboCarryForwardType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.CarryForwardTypeEnum").toArray());
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(null);
        tabPaneRev.setBounds(new Rectangle(11, 78, 1000, 517));
        this.add(tabPaneRev, null);
        contTenancyBill.setBounds(new Rectangle(12, 14, 270, 19));
        this.add(contTenancyBill, null);
        contRoom.setBounds(new Rectangle(371, 14, 270, 19));
        this.add(contRoom, null);
        contRemainDepositAmount.setBounds(new Rectangle(721, 14, 270, 19));
        this.add(contRemainDepositAmount, null);
        contCarryForwardType.setBounds(new Rectangle(12, 46, 270, 19));
        this.add(contCarryForwardType, null);
        //contTenancyBill
        contTenancyBill.setBoundEditor(f7TenancyBill);
        //contRoom
        contRoom.setBoundEditor(f7TenancyRoom);
        //contRemainDepositAmount
        contRemainDepositAmount.setBoundEditor(txtRemainDepositAmount);
        //contCarryForwardType
        contCarryForwardType.setBoundEditor(comboCarryForwardType);

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.CarryForwardBillEditUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "CarryForwardBillEditUI");
    }




}