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
public abstract class AbstractMortagageEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMortagageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMortagageDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkMortagageDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMorBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMorRoomArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Bank;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMorBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMorRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMorCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMorCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMorTotalPrices;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMorTotalPrices;
    protected com.kingdee.eas.fdc.sellhouse.RoomMortagageInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMortagageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMortagageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMortagageDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkMortagageDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMorBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMorRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Bank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtMorBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMorRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contMorCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtMorCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contMorTotalPrices = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtMorTotalPrices = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.contRoom.setName("contRoom");
        this.contMortagageDate.setName("contMortagageDate");
        this.pkMortagageDate.setName("pkMortagageDate");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.pkCreateTime.setName("pkCreateTime");
        this.contBank.setName("contBank");
        this.contMorBuildingArea.setName("contMorBuildingArea");
        this.contMorRoomArea.setName("contMorRoomArea");
        this.f7Bank.setName("f7Bank");
        this.txtMorBuildingArea.setName("txtMorBuildingArea");
        this.txtMorRoomArea.setName("txtMorRoomArea");
        this.contMorCount.setName("contMorCount");
        this.txtMorCount.setName("txtMorCount");
        this.contMorTotalPrices.setName("contMorTotalPrices");
        this.txtMorTotalPrices.setName("txtMorTotalPrices");
        // CoreUI		
        this.setPreferredSize(new Dimension(580,300));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contMortagageDate		
        this.contMortagageDate.setBoundLabelText(resHelper.getString("contMortagageDate.boundLabelText"));		
        this.contMortagageDate.setBoundLabelLength(100);		
        this.contMortagageDate.setBoundLabelUnderline(true);
        // pkMortagageDate
        // txtRoomNumber		
        this.txtRoomNumber.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // contBank		
        this.contBank.setBoundLabelText(resHelper.getString("contBank.boundLabelText"));		
        this.contBank.setBoundLabelLength(100);		
        this.contBank.setBoundLabelUnderline(true);
        // contMorBuildingArea		
        this.contMorBuildingArea.setBoundLabelText(resHelper.getString("contMorBuildingArea.boundLabelText"));		
        this.contMorBuildingArea.setBoundLabelUnderline(true);		
        this.contMorBuildingArea.setBoundLabelLength(100);
        // contMorRoomArea		
        this.contMorRoomArea.setBoundLabelText(resHelper.getString("contMorRoomArea.boundLabelText"));		
        this.contMorRoomArea.setBoundLabelLength(100);		
        this.contMorRoomArea.setBoundLabelUnderline(true);
        // f7Bank		
        this.f7Bank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");		
        this.f7Bank.setCommitFormat("$number$");		
        this.f7Bank.setEditFormat("$number$");		
        this.f7Bank.setDisplayFormat("$name$");
        this.f7Bank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Bank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtMorBuildingArea		
        this.txtMorBuildingArea.setDataType(1);
        // txtMorRoomArea		
        this.txtMorRoomArea.setDataType(1);
        // contMorCount		
        this.contMorCount.setBoundLabelText(resHelper.getString("contMorCount.boundLabelText"));		
        this.contMorCount.setBoundLabelUnderline(true);		
        this.contMorCount.setBoundLabelLength(100);
        // txtMorCount
        // contMorTotalPrices		
        this.contMorTotalPrices.setBoundLabelText(resHelper.getString("contMorTotalPrices.boundLabelText"));		
        this.contMorTotalPrices.setBoundLabelUnderline(true);		
        this.contMorTotalPrices.setBoundLabelLength(100);
        // txtMorTotalPrices		
        this.txtMorTotalPrices.setDataType(1);
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
        this.setBounds(new Rectangle(10, 10, 580, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 580, 300));
        contCreator.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 130, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(300, 130, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(300, 130, 270, 19, 0));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 160, 559, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 160, 559, 19, 0));
        contRoom.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(300, 10, 270, 19, 0));
        contMortagageDate.setBounds(new Rectangle(300, 40, 270, 19));
        this.add(contMortagageDate, new KDLayout.Constraints(300, 40, 270, 19, 0));
        contBank.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contBank, new KDLayout.Constraints(10, 40, 270, 19, 0));
        contMorBuildingArea.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contMorBuildingArea, new KDLayout.Constraints(10, 100, 270, 19, 0));
        contMorRoomArea.setBounds(new Rectangle(300, 100, 270, 19));
        this.add(contMorRoomArea, new KDLayout.Constraints(300, 100, 270, 19, 0));
        contMorCount.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contMorCount, new KDLayout.Constraints(10, 70, 270, 19, 0));
        contMorTotalPrices.setBounds(new Rectangle(300, 70, 270, 19));
        this.add(contMorTotalPrices, new KDLayout.Constraints(300, 70, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contRoom
        contRoom.setBoundEditor(txtRoomNumber);
        //contMortagageDate
        contMortagageDate.setBoundEditor(pkMortagageDate);
        //contBank
        contBank.setBoundEditor(f7Bank);
        //contMorBuildingArea
        contMorBuildingArea.setBoundEditor(txtMorBuildingArea);
        //contMorRoomArea
        contMorRoomArea.setBoundEditor(txtMorRoomArea);
        //contMorCount
        contMorCount.setBoundEditor(txtMorCount);
        //contMorTotalPrices
        contMorTotalPrices.setBoundEditor(txtMorTotalPrices);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("mortagageDate", java.util.Date.class, this.pkMortagageDate, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("bank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.f7Bank, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.MortagageEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomMortagageInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mortagageDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    		
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
     * output f7Bank_dataChanged method
     */
    protected void f7Bank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("mortagageDate"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("bank.*"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MortagageEditUI");
    }




}