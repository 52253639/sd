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
public abstract class AbstractProjectCalendarEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectCalendarEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lbProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lbName;
    protected com.kingdee.bos.ctrl.swing.KDLabel labisdef;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton kDRadioButton1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton kDRadioButton2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroupdefaultCal;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox tuesday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox wednesday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox thursday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox friday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox saturday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox sunday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox monday;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox noWeekend;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton bOk;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton bAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton bDeleteLine;
    protected com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo scheduleCalendar = null;
    protected ActionOK actionOK = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionDeleteLine actionDeleteLine = null;
    /**
     * output class constructor
     */
    public AbstractProjectCalendarEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "scheduleCalendar";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractProjectCalendarEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionOK
        this.actionOK = new ActionOK(this);
        getActionManager().registerAction("actionOK", actionOK);
         this.actionOK.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteLine
        this.actionDeleteLine = new ActionDeleteLine(this);
        getActionManager().registerAction("actionDeleteLine", actionDeleteLine);
         this.actionDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.lbProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lbName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labisdef = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDRadioButton1 = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDRadioButton2 = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDButtonGroupdefaultCal = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tuesday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.wednesday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.thursday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.friday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.saturday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.sunday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.monday = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.noWeekend = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bOk = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.bAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.bDeleteLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lbProject.setName("lbProject");
        this.labNumber.setName("labNumber");
        this.lbName.setName("lbName");
        this.labisdef.setName("labisdef");
        this.kDRadioButton1.setName("kDRadioButton1");
        this.kDRadioButton2.setName("kDRadioButton2");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.prmtProject.setName("prmtProject");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.tblMain.setName("tblMain");
        this.tuesday.setName("tuesday");
        this.wednesday.setName("wednesday");
        this.thursday.setName("thursday");
        this.friday.setName("friday");
        this.saturday.setName("saturday");
        this.sunday.setName("sunday");
        this.monday.setName("monday");
        this.noWeekend.setName("noWeekend");
        this.bOk.setName("bOk");
        this.separator1.setName("separator1");
        this.bAddLine.setName("bAddLine");
        this.bDeleteLine.setName("bDeleteLine");
        // CoreUI		
        this.setPreferredSize(new Dimension(480,380));
        // lbProject		
        this.lbProject.setBoundLabelText(resHelper.getString("lbProject.boundLabelText"));		
        this.lbProject.setBoundLabelLength(70);		
        this.lbProject.setBoundLabelUnderline(true);
        // labNumber		
        this.labNumber.setBoundLabelText(resHelper.getString("labNumber.boundLabelText"));		
        this.labNumber.setBoundLabelUnderline(true);		
        this.labNumber.setBoundLabelLength(70);
        // lbName		
        this.lbName.setBoundLabelText(resHelper.getString("lbName.boundLabelText"));		
        this.lbName.setBoundLabelLength(70);		
        this.lbName.setBoundLabelUnderline(true);
        // labisdef		
        this.labisdef.setText(resHelper.getString("labisdef.text"));
        // kDRadioButton1		
        this.kDRadioButton1.setText(resHelper.getString("kDRadioButton1.text"));
        // kDRadioButton2		
        this.kDRadioButton2.setText(resHelper.getString("kDRadioButton2.text"));		
        this.kDRadioButton2.setSelected(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kDButtonGroupdefaultCal
        this.kDButtonGroupdefaultCal.add(this.kDRadioButton1);
        this.kDButtonGroupdefaultCal.add(this.kDRadioButton2);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setEnableActive(false);
        // prmtProject		
        this.prmtProject.setEditable(true);		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$name$");		
        this.prmtProject.setCommitFormat("$name$");		
        this.prmtProject.setPreferredSize(new Dimension(20,19));		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(3);
        // txtName		
        this.txtName.setRequired(true);
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"holidayName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{holidayName}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

                this.tblMain.putBindContents("scheduleCalendar",new String[] {"date","holidayName","id"});


        // tuesday		
        this.tuesday.setText(resHelper.getString("tuesday.text"));
        // wednesday		
        this.wednesday.setText(resHelper.getString("wednesday.text"));
        // thursday		
        this.thursday.setText(resHelper.getString("thursday.text"));
        // friday		
        this.friday.setText(resHelper.getString("friday.text"));
        // saturday		
        this.saturday.setText(resHelper.getString("saturday.text"));
        // sunday		
        this.sunday.setText(resHelper.getString("sunday.text"));
        // monday		
        this.monday.setText(resHelper.getString("monday.text"));
        // noWeekend		
        this.noWeekend.setText(resHelper.getString("noWeekend.text"));		
        this.noWeekend.setVisible(false);
        // bOk
        this.bOk.setAction((IItemAction)ActionProxyFactory.getProxy(actionOK, new Class[] { IItemAction.class }, getServiceContext()));		
        this.bOk.setText(resHelper.getString("bOk.text"));		
        this.bOk.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));		
        this.bOk.setToolTipText(resHelper.getString("bOk.toolTipText"));
        // separator1
        // bAddLine
        this.bAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.bAddLine.setText(resHelper.getString("bAddLine.text"));		
        this.bAddLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));		
        this.bAddLine.setToolTipText(resHelper.getString("bAddLine.toolTipText"));
        // bDeleteLine
        this.bDeleteLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.bDeleteLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));		
        this.bDeleteLine.setText(resHelper.getString("bDeleteLine.text"));		
        this.bDeleteLine.setToolTipText(resHelper.getString("bDeleteLine.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 480, 380));
        this.setLayout(null);
        lbProject.setBounds(new Rectangle(250, 40, 220, 19));
        this.add(lbProject, null);
        labNumber.setBounds(new Rectangle(250, 10, 220, 19));
        this.add(labNumber, null);
        lbName.setBounds(new Rectangle(10, 10, 209, 20));
        this.add(lbName, null);
        labisdef.setBounds(new Rectangle(10, 40, 77, 19));
        this.add(labisdef, null);
        kDRadioButton1.setBounds(new Rectangle(91, 40, 45, 19));
        this.add(kDRadioButton1, null);
        kDRadioButton2.setBounds(new Rectangle(144, 40, 43, 19));
        this.add(kDRadioButton2, null);
        kDContainer1.setBounds(new Rectangle(220, 70, 250, 300));
        this.add(kDContainer1, null);
        kDContainer2.setBounds(new Rectangle(10, 70, 200, 300));
        this.add(kDContainer2, null);
        //lbProject
        lbProject.setBoundEditor(prmtProject);
        //labNumber
        labNumber.setBoundEditor(txtNumber);
        //lbName
        lbName.setBoundEditor(txtName);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(null);        tuesday.setBounds(new Rectangle(39, 51, 140, 19));
        kDContainer2.getContentPane().add(tuesday, null);
        wednesday.setBounds(new Rectangle(39, 84, 140, 19));
        kDContainer2.getContentPane().add(wednesday, null);
        thursday.setBounds(new Rectangle(39, 111, 140, 19));
        kDContainer2.getContentPane().add(thursday, null);
        friday.setBounds(new Rectangle(39, 142, 140, 19));
        kDContainer2.getContentPane().add(friday, null);
        saturday.setBounds(new Rectangle(39, 171, 140, 19));
        kDContainer2.getContentPane().add(saturday, null);
        sunday.setBounds(new Rectangle(39, 200, 140, 19));
        kDContainer2.getContentPane().add(sunday, null);
        monday.setBounds(new Rectangle(39, 20, 140, 19));
        kDContainer2.getContentPane().add(monday, null);
        noWeekend.setBounds(new Rectangle(39, 229, 140, 19));
        kDContainer2.getContentPane().add(noWeekend, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(bOk);
        this.toolBar.add(separator1);
        this.toolBar.add(bAddLine);
        this.toolBar.add(bDeleteLine);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("holidayEntrys", com.kingdee.eas.fdc.schedule.framework.HolidayEntryInfo.class, this.tblMain, "userObject");
		dataBinder.registerBinding("holidayEntrys.date", java.util.Date.class, this.tblMain, "date.text");
		dataBinder.registerBinding("holidayEntrys.holidayName", String.class, this.tblMain, "holidayName.text");
		dataBinder.registerBinding("holidayEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.tblMain, "id.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ProjectCalendarEditUIHandler";
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
        this.scheduleCalendar = (com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo)ov;
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
		getValidateHelper().registerBindProperty("holidayEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("holidayEntrys.date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("holidayEntrys.holidayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("holidayEntrys.id", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("holidayEntrys.*"));
//        sic.add(new SelectorItemInfo("holidayEntrys.number"));
    sic.add(new SelectorItemInfo("holidayEntrys.date"));
    sic.add(new SelectorItemInfo("holidayEntrys.holidayName"));
    sic.add(new SelectorItemInfo("holidayEntrys.id"));
        return sic;
    }        
    	

    /**
     * output actionOK_actionPerformed method
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteLine_actionPerformed method
     */
    public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionOK(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOK() {
    	return false;
    }
	public RequestContext prepareActionAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLine() {
    	return false;
    }
	public RequestContext prepareActionDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteLine() {
    	return false;
    }

    /**
     * output ActionOK class
     */     
    protected class ActionOK extends ItemAction {     
    
        public ActionOK()
        {
            this(null);
        }

        public ActionOK(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOK.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectCalendarEditUI.this, "ActionOK", "actionOK_actionPerformed", e);
        }
    }

    /**
     * output ActionAddLine class
     */     
    protected class ActionAddLine extends ItemAction {     
    
        public ActionAddLine()
        {
            this(null);
        }

        public ActionAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectCalendarEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteLine class
     */     
    protected class ActionDeleteLine extends ItemAction {     
    
        public ActionDeleteLine()
        {
            this(null);
        }

        public ActionDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectCalendarEditUI.this, "ActionDeleteLine", "actionDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ProjectCalendarEditUI");
    }




}