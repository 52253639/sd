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
public abstract class AbstractSellHouseEndPeriodUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellHouseEndPeriodUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel labPic;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radUnInit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radEndInit;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfim;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancel;
    protected ActionEndSale actionEndSale = null;
    protected ActionUnEndSale actionUnEndSale = null;
    /**
     * output class constructor
     */
    public AbstractSellHouseEndPeriodUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSellHouseEndPeriodUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionEndSale
        this.actionEndSale = new ActionEndSale(this);
        getActionManager().registerAction("actionEndSale", actionEndSale);
         this.actionEndSale.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnEndSale
        this.actionUnEndSale = new ActionUnEndSale(this);
        getActionManager().registerAction("actionUnEndSale", actionUnEndSale);
         this.actionUnEndSale.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.labPic = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.radUnInit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radEndInit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDTextArea2 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnConfim = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTextArea1.setName("kDTextArea1");
        this.kDPanel1.setName("kDPanel1");
        this.labPic.setName("labPic");
        this.radUnInit.setName("radUnInit");
        this.radEndInit.setName("radEndInit");
        this.kDPanel2.setName("kDPanel2");
        this.kDLabel1.setName("kDLabel1");
        this.kDTextArea2.setName("kDTextArea2");
        this.kDSeparator2.setName("kDSeparator2");
        this.btnConfim.setName("btnConfim");
        this.btnCancel.setName("btnCancel");
        // CoreUI
        // kDTextArea1
        // kDPanel1
        // labPic
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.radUnInit);
        this.kDButtonGroup1.add(this.radEndInit);
        // radUnInit		
        this.radUnInit.setText(resHelper.getString("radUnInit.text"));
        this.radUnInit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    radUnInit_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // radEndInit		
        this.radEndInit.setText(resHelper.getString("radEndInit.text"));
        this.radEndInit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    radEndInit_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel2
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDTextArea2
        // kDSeparator2
        // btnConfim		
        this.btnConfim.setText(resHelper.getString("btnConfim.text"));
        this.btnConfim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfim_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        this.btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        this.setBounds(new Rectangle(10, 10, 500, 320));
        this.setLayout(null);
        kDTextArea1.setBounds(new Rectangle(157, 5, 320, 73));
        this.add(kDTextArea1, null);
        kDPanel1.setBounds(new Rectangle(157, 80, 321, 201));
        this.add(kDPanel1, null);
        labPic.setBounds(new Rectangle(9, 6, 144, 273));
        this.add(labPic, null);
        kDSeparator2.setBounds(new Rectangle(8, 286, 481, 10));
        this.add(kDSeparator2, null);
        btnConfim.setBounds(new Rectangle(353, 294, 66, 19));
        this.add(btnConfim, null);
        btnCancel.setBounds(new Rectangle(422, 294, 64, 19));
        this.add(btnCancel, null);
        //kDPanel1
        kDPanel1.setLayout(null);        kDPanel2.setBounds(new Rectangle(1, 4, 320, 24));
        kDPanel1.add(kDPanel2, null);
        kDLabel1.setBounds(new Rectangle(1, 32, 319, 19));
        kDPanel1.add(kDLabel1, null);
        kDTextArea2.setBounds(new Rectangle(2, 53, 320, 146));
        kDPanel1.add(kDTextArea2, null);
        //kDPanel2
        kDPanel2.setLayout(null);        radUnInit.setBounds(new Rectangle(67, 3, 83, 22));
        kDPanel2.add(radUnInit, null);
        radEndInit.setBounds(new Rectangle(-1, 3, 64, 23));
        kDPanel2.add(radEndInit, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.SellHouseEndPeriodUIHandler";
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
     * output radUnInit_stateChanged method
     */
    protected void radUnInit_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radEndInit_stateChanged method
     */
    protected void radEndInit_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnConfim_actionPerformed method
     */
    protected void btnConfim_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    	

    /**
     * output actionEndSale_actionPerformed method
     */
    public void actionEndSale_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnEndSale_actionPerformed method
     */
    public void actionUnEndSale_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionEndSale(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEndSale() {
    	return false;
    }
	public RequestContext prepareActionUnEndSale(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnEndSale() {
    	return false;
    }

    /**
     * output ActionEndSale class
     */     
    protected class ActionEndSale extends ItemAction {     
    
        public ActionEndSale()
        {
            this(null);
        }

        public ActionEndSale(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEndSale.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEndSale.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEndSale.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellHouseEndPeriodUI.this, "ActionEndSale", "actionEndSale_actionPerformed", e);
        }
    }

    /**
     * output ActionUnEndSale class
     */     
    protected class ActionUnEndSale extends ItemAction {     
    
        public ActionUnEndSale()
        {
            this(null);
        }

        public ActionUnEndSale(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnEndSale.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnEndSale.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnEndSale.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellHouseEndPeriodUI.this, "ActionUnEndSale", "actionUnEndSale_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellHouseEndPeriodUI");
    }




}