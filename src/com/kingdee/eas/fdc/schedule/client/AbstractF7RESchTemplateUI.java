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
public abstract class AbstractF7RESchTemplateUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractF7RESchTemplateUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCatagory;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTemplate;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTask;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOk;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearAll;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblWarning;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCatagory;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combTemplate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTask;
    protected ActionSelectAll actionSelectAll = null;
    protected ActionClearAll actionClearAll = null;
    protected ActionOk actionOk = null;
    protected ActionQuit actionQuit = null;
    /**
     * output class constructor
     */
    public AbstractF7RESchTemplateUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractF7RESchTemplateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSelectAll
        this.actionSelectAll = new ActionSelectAll(this);
        getActionManager().registerAction("actionSelectAll", actionSelectAll);
         this.actionSelectAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearAll
        this.actionClearAll = new ActionClearAll(this);
        getActionManager().registerAction("actionClearAll", actionClearAll);
         this.actionClearAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOk
        this.actionOk = new ActionOk(this);
        getActionManager().registerAction("actionOk", actionOk);
         this.actionOk.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuit
        this.actionQuit = new ActionQuit(this);
        getActionManager().registerAction("actionQuit", actionQuit);
         this.actionQuit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCatagory = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnOk = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnSelectAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lblWarning = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtCatagory = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.combTemplate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblTask = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCatagory.setName("contCatagory");
        this.contTemplate.setName("contTemplate");
        this.contTask.setName("contTask");
        this.kDSeparator2.setName("kDSeparator2");
        this.btnOk.setName("btnOk");
        this.btnCancel.setName("btnCancel");
        this.btnSelectAll.setName("btnSelectAll");
        this.btnClearAll.setName("btnClearAll");
        this.lblWarning.setName("lblWarning");
        this.prmtCatagory.setName("prmtCatagory");
        this.combTemplate.setName("combTemplate");
        this.tblTask.setName("tblTask");
        // CoreUI
        // contCatagory		
        this.contCatagory.setBoundLabelText(resHelper.getString("contCatagory.boundLabelText"));		
        this.contCatagory.setBoundLabelLength(50);		
        this.contCatagory.setBoundLabelUnderline(true);
        // contTemplate		
        this.contTemplate.setBoundLabelText(resHelper.getString("contTemplate.boundLabelText"));		
        this.contTemplate.setBoundLabelLength(50);
        // contTask
        // kDSeparator2
        // btnOk
        this.btnOk.setAction((IItemAction)ActionProxyFactory.getProxy(actionOk, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOk.setText(resHelper.getString("btnOk.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // btnSelectAll
        this.btnSelectAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelectAll.setText(resHelper.getString("btnSelectAll.text"));		
        this.btnSelectAll.setVisible(false);
        // btnClearAll
        this.btnClearAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearAll.setText(resHelper.getString("btnClearAll.text"));		
        this.btnClearAll.setVisible(false);
        // lblWarning
        // prmtCatagory		
        this.prmtCatagory.setQueryInfo("com.kingdee.eas.fdc.schedule.app.RESchTemplateCatagoryQuery");		
        this.prmtCatagory.setEditFormat("$number$");		
        this.prmtCatagory.setDisplayFormat("$name$");
        this.prmtCatagory.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCatagory_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtCatagory.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtCatagory_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // combTemplate
        this.combTemplate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combTemplate_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblTask
		String tblTaskStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"checked\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"preTask\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"taskType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"duration\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{checked}</t:Cell><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{preTask}</t:Cell><t:Cell>$Resource{taskType}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{duration}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblTask.setFormatXml(resHelper.translateString("tblTask",tblTaskStrXML));

        

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
        this.setBounds(new Rectangle(10, 10, 580, 465));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 580, 465));
        contCatagory.setBounds(new Rectangle(14, 14, 200, 19));
        this.add(contCatagory, new KDLayout.Constraints(14, 14, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTemplate.setBounds(new Rectangle(289, 14, 270, 19));
        this.add(contTemplate, new KDLayout.Constraints(289, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTask.setBounds(new Rectangle(9, 39, 562, 356));
        this.add(contTask, new KDLayout.Constraints(9, 39, 562, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator2.setBounds(new Rectangle(-3, 425, 591, 15));
        this.add(kDSeparator2, new KDLayout.Constraints(-3, 425, 591, 15, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnOk.setBounds(new Rectangle(399, 436, 73, 21));
        this.add(btnOk, new KDLayout.Constraints(399, 436, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnCancel.setBounds(new Rectangle(492, 436, 73, 21));
        this.add(btnCancel, new KDLayout.Constraints(492, 436, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSelectAll.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnSelectAll, new KDLayout.Constraints(10, 10, 30, 30, 0));
        btnClearAll.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnClearAll, new KDLayout.Constraints(10, 10, 30, 30, 0));
        lblWarning.setBounds(new Rectangle(15, 404, 100, 19));
        this.add(lblWarning, new KDLayout.Constraints(15, 404, 100, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCatagory
        contCatagory.setBoundEditor(prmtCatagory);
        //contTemplate
        contTemplate.setBoundEditor(combTemplate);
        //contTask
contTask.getContentPane().setLayout(new BorderLayout(0, 0));        contTask.getContentPane().add(tblTask, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.F7RESchTemplateUIHandler";
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
    }

    /**
     * output prmtCatagory_dataChanged method
     */
    protected void prmtCatagory_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCatagory_willShow method
     */
    protected void prmtCatagory_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output combTemplate_itemStateChanged method
     */
    protected void combTemplate_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSelectAll_actionPerformed method
     */
    public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearAll_actionPerformed method
     */
    public void actionClearAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOk_actionPerformed method
     */
    public void actionOk_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuit_actionPerformed method
     */
    public void actionQuit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSelectAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelectAll() {
    	return false;
    }
	public RequestContext prepareActionClearAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearAll() {
    	return false;
    }
	public RequestContext prepareActionOk(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOk() {
    	return false;
    }
	public RequestContext prepareActionQuit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuit() {
    	return false;
    }

    /**
     * output ActionSelectAll class
     */     
    protected class ActionSelectAll extends ItemAction {     
    
        public ActionSelectAll()
        {
            this(null);
        }

        public ActionSelectAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RESchTemplateUI.this, "ActionSelectAll", "actionSelectAll_actionPerformed", e);
        }
    }

    /**
     * output ActionClearAll class
     */     
    protected class ActionClearAll extends ItemAction {     
    
        public ActionClearAll()
        {
            this(null);
        }

        public ActionClearAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionClearAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RESchTemplateUI.this, "ActionClearAll", "actionClearAll_actionPerformed", e);
        }
    }

    /**
     * output ActionOk class
     */     
    protected class ActionOk extends ItemAction {     
    
        public ActionOk()
        {
            this(null);
        }

        public ActionOk(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOk.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOk.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOk.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RESchTemplateUI.this, "ActionOk", "actionOk_actionPerformed", e);
        }
    }

    /**
     * output ActionQuit class
     */     
    protected class ActionQuit extends ItemAction {     
    
        public ActionQuit()
        {
            this(null);
        }

        public ActionQuit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RESchTemplateUI.this, "ActionQuit", "actionQuit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "F7RESchTemplateUI");
    }




}