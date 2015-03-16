/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyPropertyUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyPropertyUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer containerChargeItem;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblChargeItem;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerEquipment;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEquipment;
    /**
     * output class constructor
     */
    public AbstractTenancyPropertyUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyPropertyUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.containerChargeItem = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblChargeItem = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containerEquipment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblEquipment = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containerChargeItem.setName("containerChargeItem");
        this.tblChargeItem.setName("tblChargeItem");
        this.containerEquipment.setName("containerEquipment");
        this.tblEquipment.setName("tblEquipment");
        // CoreUI
        // containerChargeItem		
        this.containerChargeItem.setTitle(resHelper.getString("containerChargeItem.title"));		
        this.containerChargeItem.setEnableActive(false);
        // tblChargeItem		
        this.tblChargeItem.setFormatXml(resHelper.getString("tblChargeItem.formatXml"));

        

        // containerEquipment		
        this.containerEquipment.setTitle(resHelper.getString("containerEquipment.title"));		
        this.containerEquipment.setEnableActive(false);
        // tblEquipment		
        this.tblEquipment.setFormatXml(resHelper.getString("tblEquipment.formatXml"));

        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 950, 350));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 950, 350));
        containerChargeItem.setBounds(new Rectangle(10, 10, 937, 147));
        this.add(containerChargeItem, new KDLayout.Constraints(10, 10, 937, 147, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        containerEquipment.setBounds(new Rectangle(10, 160, 937, 182));
        this.add(containerEquipment, new KDLayout.Constraints(10, 160, 937, 182, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //containerChargeItem
        containerChargeItem.getContentPane().setLayout(new KDLayout());
        containerChargeItem.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 10, 937, 147));        tblChargeItem.setBounds(new Rectangle(1, 0, 934, 126));
        containerChargeItem.getContentPane().add(tblChargeItem, new KDLayout.Constraints(1, 0, 934, 126, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //containerEquipment
        containerEquipment.getContentPane().setLayout(new KDLayout());
        containerEquipment.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 160, 937, 182));        tblEquipment.setBounds(new Rectangle(1, 0, 931, 162));
        containerEquipment.getContentPane().add(tblEquipment, new KDLayout.Constraints(1, 0, 931, 162, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyPropertyUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyPropertyUI");
    }




}