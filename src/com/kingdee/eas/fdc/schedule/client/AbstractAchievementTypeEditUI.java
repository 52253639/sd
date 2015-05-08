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
public abstract class AbstractAchievementTypeEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAchievementTypeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProfession;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDocDirectory;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox kDBizMultiLangBox1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtStage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProfession;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDocDirectory;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.eas.fdc.schedule.AchievementTypeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractAchievementTypeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAchievementTypeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProfession = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDocDirectory = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDBizMultiLangBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtStage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProfession = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDocDirectory = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contStage.setName("contStage");
        this.contProfession.setName("contProfession");
        this.contDocDirectory.setName("contDocDirectory");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDBizMultiLangBox1.setName("kDBizMultiLangBox1");
        this.txtNumber.setName("txtNumber");
        this.prmtStage.setName("prmtStage");
        this.prmtProfession.setName("prmtProfession");
        this.prmtDocDirectory.setName("prmtDocDirectory");
        this.kDTextArea1.setName("kDTextArea1");
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
        // contStage		
        this.contStage.setBoundLabelText(resHelper.getString("contStage.boundLabelText"));		
        this.contStage.setBoundLabelLength(100);		
        this.contStage.setBoundLabelUnderline(true);
        // contProfession		
        this.contProfession.setBoundLabelText(resHelper.getString("contProfession.boundLabelText"));		
        this.contProfession.setBoundLabelLength(100);		
        this.contProfession.setBoundLabelUnderline(true);
        // contDocDirectory		
        this.contDocDirectory.setBoundLabelText(resHelper.getString("contDocDirectory.boundLabelText"));		
        this.contDocDirectory.setBoundLabelLength(100);		
        this.contDocDirectory.setBoundLabelUnderline(true);
        // kDScrollPane1
        // kDBizMultiLangBox1		
        this.kDBizMultiLangBox1.setMaxLength(100);		
        this.kDBizMultiLangBox1.setRequired(true);
        // txtNumber		
        this.txtNumber.setMaxLength(4);		
        this.txtNumber.setRequired(true);
        // prmtStage		
        this.prmtStage.setRequired(true);
        // prmtProfession		
        this.prmtProfession.setRequired(true);
        // prmtDocDirectory
        // kDTextArea1		
        this.kDTextArea1.setMaxLength(500);
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
        this.setBounds(new Rectangle(10, 10, 580, 240));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(9, 100, 270, 19));
        this.add(contDescription, null);
        contStage.setBounds(new Rectangle(11, 40, 270, 19));
        this.add(contStage, null);
        contProfession.setBounds(new Rectangle(300, 39, 270, 19));
        this.add(contProfession, null);
        contDocDirectory.setBounds(new Rectangle(10, 70, 560, 17));
        this.add(contDocDirectory, null);
        kDScrollPane1.setBounds(new Rectangle(10, 126, 560, 101));
        this.add(kDScrollPane1, null);
        //contName
        contName.setBoundEditor(kDBizMultiLangBox1);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contStage
        contStage.setBoundEditor(prmtStage);
        //contProfession
        contProfession.setBoundEditor(prmtProfession);
        //contDocDirectory
        contDocDirectory.setBoundEditor(prmtDocDirectory);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTextArea1, null);

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
        menuFile.add(kdSeparatorFWFile1);
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
        this.toolBar.add(kDSeparatorCloud);
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
		dataBinder.registerBinding("name", String.class, this.kDBizMultiLangBox1, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("stage", com.kingdee.eas.fdc.schedule.AchievementStageInfo.class, this.prmtStage, "data");
		dataBinder.registerBinding("profession", com.kingdee.eas.fdc.schedule.AchievementProfessionInfo.class, this.prmtProfession, "data");
		dataBinder.registerBinding("docDirectory", com.kingdee.eas.cp.dm.CategoryInfo.class, this.prmtDocDirectory, "data");
		dataBinder.registerBinding("description", String.class, this.kDTextArea1, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.AchievementTypeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.AchievementTypeInfo)ov;
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
		getValidateHelper().registerBindProperty("stage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("profession", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("docDirectory", ValidateHelper.ON_SAVE);    
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("stage.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("stage.id"));
        	sic.add(new SelectorItemInfo("stage.number"));
        	sic.add(new SelectorItemInfo("stage.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("profession.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("profession.id"));
        	sic.add(new SelectorItemInfo("profession.number"));
        	sic.add(new SelectorItemInfo("profession.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("docDirectory.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("docDirectory.id"));
        	sic.add(new SelectorItemInfo("docDirectory.number"));
        	sic.add(new SelectorItemInfo("docDirectory.name"));
		}
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "AchievementTypeEditUI");
    }




}