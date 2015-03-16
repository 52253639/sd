/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractAreaSetEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAreaSetEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTxtNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTxtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtArea1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtArea2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCompare1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCompare2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDesc;
    protected com.kingdee.eas.fdc.market.AreaSetInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractAreaSetEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAreaSetEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contTxtNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTxtName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtArea1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtArea2 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCompare1 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboCompare2 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDesc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contTxtNum.setName("contTxtNum");
        this.contTxtName.setName("contTxtName");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtArea1.setName("txtArea1");
        this.txtArea2.setName("txtArea2");
        this.comboCompare1.setName("comboCompare1");
        this.comboCompare2.setName("comboCompare2");
        this.txtDesc.setName("txtDesc");
        // CoreUI
        // contTxtNum		
        this.contTxtNum.setBoundLabelText(resHelper.getString("contTxtNum.boundLabelText"));		
        this.contTxtNum.setBoundLabelLength(100);		
        this.contTxtNum.setBoundLabelUnderline(true);
        // contTxtName		
        this.contTxtName.setBoundLabelText(resHelper.getString("contTxtName.boundLabelText"));		
        this.contTxtName.setBoundLabelLength(100);		
        this.contTxtName.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(50);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(50);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3
        // kDLabelContainer4
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // txtNumber
        // txtName
        // txtArea1
        // txtArea2
        // comboCompare1		
        this.comboCompare1.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.MarketCompareEnum").toArray());
        // comboCompare2		
        this.comboCompare2.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.MarketCompareEnum").toArray());
        // txtDesc
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
        this.setBounds(new Rectangle(10, 10, 700, 200));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 200));
        contTxtNum.setBounds(new Rectangle(23, 28, 307, 19));
        this.add(contTxtNum, new KDLayout.Constraints(23, 28, 307, 19, 0));
        contTxtName.setBounds(new Rectangle(362, 27, 307, 19));
        this.add(contTxtName, new KDLayout.Constraints(362, 27, 307, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(24, 84, 186, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(24, 84, 186, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(484, 83, 186, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(484, 83, 186, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(219, 84, 101, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(219, 84, 101, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(375, 85, 102, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(375, 85, 102, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(329, 85, 36, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(329, 85, 36, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(23, 139, 648, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(23, 139, 648, 19, 0));
        //contTxtNum
        contTxtNum.setBoundEditor(txtNumber);
        //contTxtName
        contTxtName.setBoundEditor(txtName);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtArea1);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtArea2);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(comboCompare1);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(comboCompare2);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDesc);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("minArea", java.math.BigDecimal.class, this.txtArea1, "text");
		dataBinder.registerBinding("maxArea", java.math.BigDecimal.class, this.txtArea2, "text");
		dataBinder.registerBinding("minCompare", com.kingdee.eas.fdc.market.MarketCompareEnum.class, this.comboCompare1, "selectedItem");
		dataBinder.registerBinding("maxCompare", com.kingdee.eas.fdc.market.MarketCompareEnum.class, this.comboCompare2, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDesc, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.AreaSetEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.AreaSetInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minCompare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxCompare", ValidateHelper.ON_SAVE);    
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("minArea"));
        sic.add(new SelectorItemInfo("maxArea"));
        sic.add(new SelectorItemInfo("minCompare"));
        sic.add(new SelectorItemInfo("maxCompare"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "AreaSetEditUI");
    }




}