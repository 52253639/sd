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
public abstract class AbstractAttachmentUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAttachmentUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar EcAttachmentUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttachList;
    protected com.kingdee.bos.ctrl.swing.KDList listAttachement;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected ActionAttachmentManager actionAttachmentManager = null;
    /**
     * output class constructor
     */
    public AbstractAttachmentUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAttachmentUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAttachmentManager
        this.actionAttachmentManager = new ActionAttachmentManager(this);
        getActionManager().registerAction("actionAttachmentManager", actionAttachmentManager);
         this.actionAttachmentManager.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnAttachList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.listAttachement = new com.kingdee.bos.ctrl.swing.KDList();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.setName("EcAttachmentUI");
        this.toolBar.setName("EcAttachmentUI_toolbar");
        this.menuBar.setName("EcAttachmentUI_menubar");
        this.kDContainer1.setName("kDContainer1");
        this.btnAttachList.setName("btnAttachList");
        this.listAttachement.setName("listAttachement");
        this.kDScrollPane1.setName("kDScrollPane1");
        // EcAttachmentUI
        // EcAttachmentUI_toolbar
        // EcAttachmentUI_menubar
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnAttachList
        this.btnAttachList.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachmentManager, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachList.setText(resHelper.getString("btnAttachList.text"));
        // listAttachement		
        this.listAttachement.setEnabled(false);
        this.listAttachement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    listAttachement_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.listAttachement.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                try {
                    listAttachement_mouseMoved(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDScrollPane1
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
        this.setBounds(new Rectangle(10, 10, 900, 200));
this.setLayout(new BorderLayout(0, 0));
        this.add(kDContainer1, BorderLayout.CENTER);
        this.add(btnAttachList, BorderLayout.SOUTH);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(listAttachement, null);

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
	    return "com.kingdee.eas.fdc.schedule.app.AttachmentUIHandler";
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
     * output listAttachement_mouseClicked method
     */
    protected void listAttachement_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output listAttachement_mouseMoved method
     */
    protected void listAttachement_mouseMoved(java.awt.event.MouseEvent e) throws Exception
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
     * output actionAttachmentManager_actionPerformed method
     */
    public void actionAttachmentManager_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAttachmentManager(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachmentManager() {
    	return false;
    }

    /**
     * output ActionAttachmentManager class
     */     
    protected class ActionAttachmentManager extends ItemAction {     
    
        public ActionAttachmentManager()
        {
            this(null);
        }

        public ActionAttachmentManager(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAttachmentManager.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachmentManager.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachmentManager.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAttachmentUI.this, "ActionAttachmentManager", "actionAttachmentManager_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "AttachmentUI");
    }




}