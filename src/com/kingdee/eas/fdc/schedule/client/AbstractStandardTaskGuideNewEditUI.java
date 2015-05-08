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
public abstract class AbstractStandardTaskGuideNewEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractStandardTaskGuideNewEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardDuration;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTaskType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardDuration;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractStandardTaskGuideNewEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractStandardTaskGuideNewEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardDuration = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboTaskType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStandardDuration = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contTaskType.setName("contTaskType");
        this.contStandardDuration.setName("contStandardDuration");
        this.txtDescription.setName("txtDescription");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.comboTaskType.setName("comboTaskType");
        this.txtStandardDuration.setName("txtStandardDuration");
        this.kdtEntrys.setName("kdtEntrys");
        this.kDTable1.setName("kDTable1");
        this.kDPanel1.setName("kDPanel1");
        this.txtLongNumber.setName("txtLongNumber");
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
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(100);		
        this.contTaskType.setBoundLabelUnderline(true);
        // contStandardDuration		
        this.contStandardDuration.setBoundLabelText(resHelper.getString("contStandardDuration.boundLabelText"));		
        this.contStandardDuration.setBoundLabelLength(100);		
        this.contStandardDuration.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));		
        this.kDContainer3.setEnableActive(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // txtName
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // comboTaskType		
        this.comboTaskType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum").toArray());
        // txtStandardDuration
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"docPath\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"docName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"docPath","docName"});


        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"docPath\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"docName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // kDPanel1
        // txtLongNumber		
        this.txtLongNumber.setMaxLength(255);
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(369, 11, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(9, 121, 270, 19));
        this.add(contDescription, null);
        contTaskType.setBounds(new Rectangle(10, 49, 270, 19));
        this.add(contTaskType, null);
        contStandardDuration.setBounds(new Rectangle(703, 47, 270, 19));
        this.add(contStandardDuration, null);
        txtDescription.setBounds(new Rectangle(108, 84, 789, 55));
        this.add(txtDescription, null);
        kDContainer1.setBounds(new Rectangle(33, 190, 386, 174));
        this.add(kDContainer1, null);
        kDContainer2.setBounds(new Rectangle(37, 405, 386, 174));
        this.add(kDContainer2, null);
        kDContainer3.setBounds(new Rectangle(523, 188, 423, 354));
        this.add(kDContainer3, null);
        kDLabelContainer1.setBounds(new Rectangle(654, 7, 270, 19));
        this.add(kDLabelContainer1, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contTaskType
        contTaskType.setBoundEditor(comboTaskType);
        //contStandardDuration
        contStandardDuration.setBoundEditor(txtStandardDuration);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        kdtEntrys.setBounds(new Rectangle(3, 0, 247, 100));
        kDContainer1.getContentPane().add(kdtEntrys, null);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(null);        kDTable1.setBounds(new Rectangle(7, 2, 246, 123));
        kDContainer2.getContentPane().add(kDTable1, null);
        //kDContainer3
        kDContainer3.getContentPane().setLayout(null);        kDPanel1.setBounds(new Rectangle(15, 5, 393, 306));
        kDContainer3.getContentPane().add(kDPanel1, null);
        kDPanel1.setLayout(null);        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtLongNumber);

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
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("taskType", com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.class, this.comboTaskType, "selectedItem");
		dataBinder.registerBinding("standardDuration", java.math.BigDecimal.class, this.txtStandardDuration, "value");
		dataBinder.registerBinding("entrys.docPath", String.class, this.kdtEntrys, "docPath.text");
		dataBinder.registerBinding("entrys.docName", String.class, this.kdtEntrys, "docName.text");
		dataBinder.registerBinding("entrys.isCPFile", boolean.class, this.kdtEntrys, "isCPFile.text");
		dataBinder.registerBinding("entrys.docID", String.class, this.kdtEntrys, "docID.text");
		dataBinder.registerBinding("entrys.guideType", com.kingdee.util.enums.Enum.class, this.kdtEntrys, "guideType.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("longNumber", String.class, this.txtLongNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.StandardTaskGuideNewEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standardDuration", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.docPath", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.docName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isCPFile", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.docID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.guideType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("longNumber", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("taskType"));
        sic.add(new SelectorItemInfo("standardDuration"));
    sic.add(new SelectorItemInfo("entrys.docPath"));
    sic.add(new SelectorItemInfo("entrys.docName"));
    sic.add(new SelectorItemInfo("entrys.isCPFile"));
    sic.add(new SelectorItemInfo("entrys.docID"));
    sic.add(new SelectorItemInfo("entrys.guideType"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
        sic.add(new SelectorItemInfo("longNumber"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "StandardTaskGuideNewEditUI");
    }




}