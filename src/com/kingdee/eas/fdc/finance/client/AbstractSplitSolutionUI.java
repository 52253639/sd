/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractSplitSolutionUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSplitSolutionUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbtnAuto;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbtnImport;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbtnDirect;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    /**
     * output class constructor
     */
    public AbstractSplitSolutionUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSplitSolutionUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDButtonGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.rbtnAuto = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbtnImport = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbtnDirect = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.rbtnAuto.setName("rbtnAuto");
        this.rbtnImport.setName("rbtnImport");
        this.rbtnDirect.setName("rbtnDirect");
        this.kDLabel1.setName("kDLabel1");
        // CoreUI
        // kDButtonGroup
        this.kDButtonGroup.add(this.rbtnAuto);
        this.kDButtonGroup.add(this.rbtnImport);
        this.kDButtonGroup.add(this.rbtnDirect);
        // rbtnAuto		
        this.rbtnAuto.setText(resHelper.getString("rbtnAuto.text"));		
        this.rbtnAuto.setToolTipText(resHelper.getString("rbtnAuto.toolTipText"));		
        this.rbtnAuto.setSelected(true);
        // rbtnImport		
        this.rbtnImport.setText(resHelper.getString("rbtnImport.text"));		
        this.rbtnImport.setToolTipText(resHelper.getString("rbtnImport.toolTipText"));
        // rbtnDirect		
        this.rbtnDirect.setText(resHelper.getString("rbtnDirect.text"));		
        this.rbtnDirect.setToolTipText(resHelper.getString("rbtnDirect.toolTipText"));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
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
        this.setBounds(new Rectangle(10, 10, 350, 220));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 350, 220));
        rbtnAuto.setBounds(new Rectangle(39, 56, 280, 19));
        this.add(rbtnAuto, new KDLayout.Constraints(39, 56, 280, 19, 0));
        rbtnImport.setBounds(new Rectangle(39, 96, 280, 19));
        this.add(rbtnImport, new KDLayout.Constraints(39, 96, 280, 19, 0));
        rbtnDirect.setBounds(new Rectangle(39, 137, 280, 19));
        this.add(rbtnDirect, new KDLayout.Constraints(39, 137, 280, 19, 0));
        kDLabel1.setBounds(new Rectangle(21, 25, 100, 19));
        this.add(kDLabel1, new KDLayout.Constraints(21, 25, 100, 19, 0));

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
	    return "com.kingdee.eas.fdc.finance.app.SplitSolutionUIHandler";
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "SplitSolutionUI");
    }




}