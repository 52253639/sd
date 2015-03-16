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
public abstract class AbstractSelectQuestionEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSelectQuestionEditUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtItem;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtItem_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbQuestion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.eas.fdc.market.SelectQuestionInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSelectQuestionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSelectQuestionEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kdtItem = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cmbQuestion = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtItem.setName("kdtItem");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.cmbQuestion.setName("cmbQuestion");
        this.txtName.setName("txtName");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kdtItem		
        this.kdtItem.setFormatXml(resHelper.getString("kdtItem.formatXml"));

                this.kdtItem.putBindContents("editData",new String[] {"name","IsTitle"});


        this.kdtItem.checkParsed();
        KDTextField kdtItem_name_TextField = new KDTextField();
        kdtItem_name_TextField.setName("kdtItem_name_TextField");
        kdtItem_name_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtItem_name_CellEditor = new KDTDefaultCellEditor(kdtItem_name_TextField);
        this.kdtItem.getColumn("name").setEditor(kdtItem_name_CellEditor);
        KDCheckBox kdtItem_IsTitle_CheckBox = new KDCheckBox();
        kdtItem_IsTitle_CheckBox.setName("kdtItem_IsTitle_CheckBox");
        KDTDefaultCellEditor kdtItem_IsTitle_CellEditor = new KDTDefaultCellEditor(kdtItem_IsTitle_CheckBox);
        this.kdtItem.getColumn("IsTitle").setEditor(kdtItem_IsTitle_CellEditor);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(60);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // cmbQuestion		
        this.cmbQuestion.setVisible(true);		
        this.cmbQuestion.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.QuestionStyle").toArray());		
        this.cmbQuestion.setRequired(false);		
        this.cmbQuestion.setEnabled(false);		
        this.cmbQuestion.setForeground(new java.awt.Color(0,0,0));		
        this.cmbQuestion.setActionCommand("");
        // txtName
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kdtItem}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 520, 420));
        this.setLayout(null);
        kdtItem.setBounds(new Rectangle(15, 104, 489, 300));
        kdtItem_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtItem,new com.kingdee.eas.fdc.market.SelectQuestionItemInfo(),null,false);
        this.add(kdtItem_detailPanel, null);
        kDLabelContainer1.setBounds(new Rectangle(11, 12, 489, 19));
        this.add(kDLabelContainer1, null);
        txtName.setBounds(new Rectangle(71, 36, 429, 61));
        this.add(txtName, null);
        kDLabelContainer2.setBounds(new Rectangle(13, 37, 60, 19));
        this.add(kDLabelContainer2, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(cmbQuestion);

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
		dataBinder.registerBinding("Item", com.kingdee.eas.fdc.market.SelectQuestionItemInfo.class, this.kdtItem, "userObject");
		dataBinder.registerBinding("Item.name", String.class, this.kdtItem, "name.text");
		dataBinder.registerBinding("Item.IsTitle", boolean.class, this.kdtItem, "IsTitle.text");
		dataBinder.registerBinding("questionStyle", String.class, this.cmbQuestion, "selectedItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.SelectQuestionEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.kdtItem.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.SelectQuestionInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("Item", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Item.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Item.IsTitle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("questionStyle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("Item.*"));
//        sic.add(new SelectorItemInfo("Item.number"));
    sic.add(new SelectorItemInfo("Item.name"));
    sic.add(new SelectorItemInfo("Item.IsTitle"));
        sic.add(new SelectorItemInfo("questionStyle"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "SelectQuestionEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.SelectQuestionEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.SelectQuestionFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.SelectQuestionInfo objectValue = new com.kingdee.eas.fdc.market.SelectQuestionInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtItem;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("questionStyle","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}