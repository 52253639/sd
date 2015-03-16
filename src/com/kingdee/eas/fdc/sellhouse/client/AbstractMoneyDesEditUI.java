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
public abstract class AbstractMoneyDesEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMoneyDesEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSysType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsMeterItem;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMoneyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSysType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSettlementAdd;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSettlementDel;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo entityMoneyDefine = null;
    protected ActionSettlementShow actionSettlementShow = null;
    protected ActionSettlementDel actionSettlementDel = null;
    /**
     * output class constructor
     */
    public AbstractMoneyDesEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMoneyDesEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSettlementShow
        this.actionSettlementShow = new ActionSettlementShow(this);
        getActionManager().registerAction("actionSettlementShow", actionSettlementShow);
         this.actionSettlementShow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSettlementDel
        this.actionSettlementDel = new ActionSettlementDel(this);
        getActionManager().registerAction("actionSettlementDel", actionSettlementDel);
         this.actionSettlementDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSysType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsMeterItem = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.cbIsAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboMoneyType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboSysType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSettlementAdd = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSettlementDel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contMoneyType.setName("contMoneyType");
        this.contOrgUnit.setName("contOrgUnit");
        this.contSysType.setName("contSysType");
        this.chkIsMeterItem.setName("chkIsMeterItem");
        this.kDContainer1.setName("kDContainer1");
        this.cbIsAmount.setName("cbIsAmount");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.comboMoneyType.setName("comboMoneyType");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.comboSysType.setName("comboSysType");
        this.tblMain.setName("tblMain");
        this.btnAdd.setName("btnAdd");
        this.btnDel.setName("btnDel");
        this.menuItemSettlementAdd.setName("menuItemSettlementAdd");
        this.menuItemSettlementDel.setName("menuItemSettlementDel");
        // CoreUI		
        this.setPreferredSize(new Dimension(300,300));
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
        // contMoneyType		
        this.contMoneyType.setBoundLabelText(resHelper.getString("contMoneyType.boundLabelText"));		
        this.contMoneyType.setBoundLabelLength(100);		
        this.contMoneyType.setBoundLabelUnderline(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // contSysType		
        this.contSysType.setBoundLabelText(resHelper.getString("contSysType.boundLabelText"));		
        this.contSysType.setBoundLabelLength(100);		
        this.contSysType.setBoundLabelUnderline(true);
        // chkIsMeterItem		
        this.chkIsMeterItem.setText(resHelper.getString("chkIsMeterItem.text"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // cbIsAmount		
        this.cbIsAmount.setText(resHelper.getString("cbIsAmount.text"));
        // txtName
        // txtNumber
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // comboMoneyType		
        this.comboMoneyType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum").toArray());
        // prmtOrgUnit		
        this.prmtOrgUnit.setEnabled(false);
        // comboSysType		
        this.comboSysType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());
        this.comboSysType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSysType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isBrought\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"paymentAccount\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isBrought}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{paymentAccount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

        // btnAdd
        this.btnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionSettlementShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        // btnDel
        this.btnDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionSettlementDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDel.setText(resHelper.getString("btnDel.text"));
        // menuItemSettlementAdd
        this.menuItemSettlementAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionSettlementShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSettlementAdd.setText(resHelper.getString("menuItemSettlementAdd.text"));
        // menuItemSettlementDel
        this.menuItemSettlementDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionSettlementDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSettlementDel.setText(resHelper.getString("menuItemSettlementDel.text"));
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        contName.setBounds(new Rectangle(518, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(518, 10, 270, 19, 0));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 97, 776, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 97, 776, 19, 0));
        contMoneyType.setBounds(new Rectangle(518, 39, 270, 19));
        this.add(contMoneyType, new KDLayout.Constraints(518, 39, 270, 19, 0));
        contOrgUnit.setBounds(new Rectangle(10, 68, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(10, 68, 270, 19, 0));
        contSysType.setBounds(new Rectangle(10, 39, 270, 19));
        this.add(contSysType, new KDLayout.Constraints(10, 39, 270, 19, 0));
        chkIsMeterItem.setBounds(new Rectangle(518, 68, 140, 19));
        this.add(chkIsMeterItem, new KDLayout.Constraints(518, 68, 140, 19, 0));
        kDContainer1.setBounds(new Rectangle(10, 126, 776, 443));
        this.add(kDContainer1, new KDLayout.Constraints(10, 126, 776, 443, 0));
        cbIsAmount.setBounds(new Rectangle(692, 68, 96, 19));
        this.add(cbIsAmount, new KDLayout.Constraints(692, 68, 96, 19, 0));
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contMoneyType
        contMoneyType.setBoundEditor(comboMoneyType);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contSysType
        contSysType.setBoundEditor(comboSysType);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 126, 776, 443));        tblMain.setBounds(new Rectangle(0, 29, 776, 410));
        kDContainer1.getContentPane().add(tblMain, new KDLayout.Constraints(0, 29, 776, 410, 0));
        btnAdd.setBounds(new Rectangle(576, 0, 73, 19));
        kDContainer1.getContentPane().add(btnAdd, new KDLayout.Constraints(576, 0, 73, 19, 0));
        btnDel.setBounds(new Rectangle(687, 0, 73, 19));
        kDContainer1.getContentPane().add(btnDel, new KDLayout.Constraints(687, 0, 73, 19, 0));

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
        menuBiz.add(menuItemSettlementAdd);
        menuBiz.add(menuItemSettlementDel);
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
		dataBinder.registerBinding("isMeterItem", boolean.class, this.chkIsMeterItem, "selected");
		dataBinder.registerBinding("isAmount", boolean.class, this.cbIsAmount, "selected");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("moneyType", com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum.class, this.comboMoneyType, "selectedItem");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("sysType", com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.class, this.comboSysType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.MoneyDesEditUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("entityMoneyDefine")) {
            this.entityMoneyDefine = (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)dataObject;
		}
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
		getValidateHelper().registerBindProperty("isMeterItem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sysType", ValidateHelper.ON_SAVE);    		
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
     * output comboSysType_itemStateChanged method
     */
    protected void comboSysType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
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
     * output actionSettlementShow_actionPerformed method
     */
    public void actionSettlementShow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSettlementDel_actionPerformed method
     */
    public void actionSettlementDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSettlementShow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSettlementShow() {
    	return false;
    }
	public RequestContext prepareActionSettlementDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSettlementDel() {
    	return false;
    }

    /**
     * output ActionSettlementShow class
     */     
    protected class ActionSettlementShow extends ItemAction {     
    
        public ActionSettlementShow()
        {
            this(null);
        }

        public ActionSettlementShow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSettlementShow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSettlementShow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSettlementShow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMoneyDesEditUI.this, "ActionSettlementShow", "actionSettlementShow_actionPerformed", e);
        }
    }

    /**
     * output ActionSettlementDel class
     */     
    protected class ActionSettlementDel extends ItemAction {     
    
        public ActionSettlementDel()
        {
            this(null);
        }

        public ActionSettlementDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSettlementDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSettlementDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSettlementDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMoneyDesEditUI.this, "ActionSettlementDel", "actionSettlementDel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MoneyDesEditUI");
    }




}