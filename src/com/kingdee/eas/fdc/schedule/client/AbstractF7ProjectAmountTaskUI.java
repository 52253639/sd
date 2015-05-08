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
public abstract class AbstractF7ProjectAmountTaskUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractF7ProjectAmountTaskUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOk;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsLike;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbFilterField;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtValue;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFilter;
    protected ActionBtnOk actionBtnOk = null;
    protected ActionBtnCancel actionBtnCancel = null;
    protected ActionBtnFilter actionBtnFilter = null;
    /**
     * output class constructor
     */
    public AbstractF7ProjectAmountTaskUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractF7ProjectAmountTaskUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionBtnOk
        this.actionBtnOk = new ActionBtnOk(this);
        getActionManager().registerAction("actionBtnOk", actionBtnOk);
         this.actionBtnOk.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnCancel
        this.actionBtnCancel = new ActionBtnCancel(this);
        getActionManager().registerAction("actionBtnCancel", actionBtnCancel);
         this.actionBtnCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnFilter
        this.actionBtnFilter = new ActionBtnFilter(this);
        getActionManager().registerAction("actionBtnFilter", actionBtnFilter);
         this.actionBtnFilter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnOk = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.chkIsLike = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cmbFilterField = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnFilter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblMain.setName("tblMain");
        this.btnOk.setName("btnOk");
        this.btnCancel.setName("btnCancel");
        this.chkIsLike.setName("chkIsLike");
        this.cmbFilterField.setName("cmbFilterField");
        this.txtValue.setName("txtValue");
        this.btnFilter.setName("btnFilter");
        // CoreUI		
        this.menuFile.setVisible(false);		
        this.menuHelp.setVisible(false);		
        this.menuTool.setVisible(false);
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>#,###%</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"wbs\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"tasknumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"taskname\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"complete\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"effecttime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"start\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"end\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isleaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"islastver\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"taskexestate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"natureTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"iskeytask\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{wbs}</t:Cell><t:Cell>$Resource{tasknumber}</t:Cell><t:Cell>$Resource{taskname}</t:Cell><t:Cell>$Resource{complete}</t:Cell><t:Cell>$Resource{effecttime}</t:Cell><t:Cell>$Resource{start}</t:Cell><t:Cell>$Resource{end}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{isleaf}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{islastver}</t:Cell><t:Cell>$Resource{taskexestate}</t:Cell><t:Cell>$Resource{natureTimes}</t:Cell><t:Cell>$Resource{iskeytask}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

        // btnOk
        this.btnOk.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnOk, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOk.setText(resHelper.getString("btnOk.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // chkIsLike		
        this.chkIsLike.setText(resHelper.getString("chkIsLike.text"));		
        this.chkIsLike.setSelected(true);
        // cmbFilterField		
        this.cmbFilterField.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.ScheduleTaskFiledEnum").toArray());
        // txtValue
        // btnFilter
        this.btnFilter.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnFilter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFilter.setText(resHelper.getString("btnFilter.text"));		
        this.btnFilter.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_find"));
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
        this.setBounds(new Rectangle(10, 10, 600, 500));
        this.setLayout(null);
        tblMain.setBounds(new Rectangle(6, 62, 588, 401));
        this.add(tblMain, null);
        btnOk.setBounds(new Rectangle(386, 473, 70, 23));
        this.add(btnOk, null);
        btnCancel.setBounds(new Rectangle(501, 473, 70, 23));
        this.add(btnCancel, null);
        chkIsLike.setBounds(new Rectangle(501, 19, 78, 19));
        this.add(chkIsLike, null);
        cmbFilterField.setBounds(new Rectangle(15, 19, 99, 19));
        this.add(cmbFilterField, null);
        txtValue.setBounds(new Rectangle(148, 19, 199, 19));
        this.add(txtValue, null);
        btnFilter.setBounds(new Rectangle(391, 19, 90, 19));
        this.add(btnFilter, null);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.F7ProjectAmountTaskUIHandler";
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
     * output actionBtnOk_actionPerformed method
     */
    public void actionBtnOk_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnCancel_actionPerformed method
     */
    public void actionBtnCancel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnFilter_actionPerformed method
     */
    public void actionBtnFilter_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBtnOk(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnOk() {
    	return false;
    }
	public RequestContext prepareActionBtnCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnCancel() {
    	return false;
    }
	public RequestContext prepareActionBtnFilter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnFilter() {
    	return false;
    }

    /**
     * output ActionBtnOk class
     */     
    protected class ActionBtnOk extends ItemAction {     
    
        public ActionBtnOk()
        {
            this(null);
        }

        public ActionBtnOk(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnOk.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnOk.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnOk.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7ProjectAmountTaskUI.this, "ActionBtnOk", "actionBtnOk_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnCancel class
     */     
    protected class ActionBtnCancel extends ItemAction {     
    
        public ActionBtnCancel()
        {
            this(null);
        }

        public ActionBtnCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7ProjectAmountTaskUI.this, "ActionBtnCancel", "actionBtnCancel_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnFilter class
     */     
    protected class ActionBtnFilter extends ItemAction {     
    
        public ActionBtnFilter()
        {
            this(null);
        }

        public ActionBtnFilter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBtnFilter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnFilter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnFilter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7ProjectAmountTaskUI.this, "ActionBtnFilter", "actionBtnFilter_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "F7ProjectAmountTaskUI");
    }




}