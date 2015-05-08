/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

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
public abstract class AbstractFindTaskUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFindTaskUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar FindTaskUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDList lstColumns;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContent;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbFuzzy;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbPre;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbBack;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnLocate;
    protected com.kingdee.bos.ctrl.swing.KDButton btnClose;
    protected ActionLocate actionLocate = null;
    protected ActionClose actionClose = null;
    /**
     * output class constructor
     */
    public AbstractFindTaskUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFindTaskUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionLocate
        this.actionLocate = new ActionLocate(this);
        getActionManager().registerAction("actionLocate", actionLocate);
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lstColumns = new com.kingdee.bos.ctrl.swing.KDList();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtContent = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbFuzzy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.rbPre = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbBack = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDSeparator1 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnLocate = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDButton();
        this.setName("FindTaskUI");
        this.toolBar.setName("FindTaskUI_toolbar");
        this.menuBar.setName("FindTaskUI_menubar");
        this.kDLabel1.setName("kDLabel1");
        this.lstColumns.setName("lstColumns");
        this.kDLabel2.setName("kDLabel2");
        this.txtContent.setName("txtContent");
        this.cbFuzzy.setName("cbFuzzy");
        this.rbPre.setName("rbPre");
        this.rbBack.setName("rbBack");
        this.kDSeparator1.setName("kDSeparator1");
        this.btnLocate.setName("btnLocate");
        this.btnClose.setName("btnClose");
        // FindTaskUI		
        this.setPreferredSize(new Dimension(400,200));
        // FindTaskUI_toolbar
        // FindTaskUI_menubar
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // lstColumns		
        this.lstColumns.setListData(resHelper.getArray("lstColumns.items"));		
        this.lstColumns.setSelectedIndex(1);		
        this.lstColumns.setSelectionMode(0);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // txtContent		
        this.txtContent.setMaxLength(120);
        // cbFuzzy		
        this.cbFuzzy.setText(resHelper.getString("cbFuzzy.text"));		
        this.cbFuzzy.setSelected(true);
        // rbPre		
        this.rbPre.setText(resHelper.getString("rbPre.text"));
        // rbBack		
        this.rbBack.setText(resHelper.getString("rbBack.text"));		
        this.rbBack.setSelected(true);
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.rbPre);
        this.kDButtonGroup1.add(this.rbBack);
        // kDSeparator1
        // btnLocate
        this.btnLocate.setAction((IItemAction)ActionProxyFactory.getProxy(actionLocate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLocate.setText(resHelper.getString("btnLocate.text"));
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {lstColumns,txtContent,btnLocate,cbFuzzy,rbPre,rbBack,btnClose}));
        this.setFocusCycleRoot(true);
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
		list.add(this.toolBar);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 200));
        this.setLayout(null);
        kDLabel1.setBounds(new Rectangle(10, 10, 100, 19));
        this.add(kDLabel1, null);
        lstColumns.setBounds(new Rectangle(12, 40, 140, 110));
        this.add(lstColumns, null);
        kDLabel2.setBounds(new Rectangle(170, 10, 100, 19));
        this.add(kDLabel2, null);
        txtContent.setBounds(new Rectangle(170, 40, 220, 19));
        this.add(txtContent, null);
        cbFuzzy.setBounds(new Rectangle(170, 66, 148, 19));
        this.add(cbFuzzy, null);
        rbPre.setBounds(new Rectangle(170, 100, 140, 19));
        this.add(rbPre, null);
        rbBack.setBounds(new Rectangle(170, 125, 140, 19));
        this.add(rbBack, null);
        kDSeparator1.setBounds(new Rectangle(10, 160, 380, 5));
        this.add(kDSeparator1, null);
        btnLocate.setBounds(new Rectangle(225, 170, 73, 21));
        this.add(btnLocate, null);
        btnClose.setBounds(new Rectangle(310, 170, 73, 21));
        this.add(btnClose, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.framework.app.FindTaskUIHandler";
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
        this.lstColumns.requestFocusInWindow();
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionLocate_actionPerformed method
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionLocate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLocate() {
    	return false;
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }

    /**
     * output ActionLocate class
     */     
    protected class ActionLocate extends ItemAction {     
    
        public ActionLocate()
        {
            this(null);
        }

        public ActionLocate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionLocate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLocate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLocate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFindTaskUI.this, "ActionLocate", "actionLocate_actionPerformed", e);
        }
    }

    /**
     * output ActionClose class
     */     
    protected class ActionClose extends ItemAction {     
    
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFindTaskUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.framework.client", "FindTaskUI");
    }




}