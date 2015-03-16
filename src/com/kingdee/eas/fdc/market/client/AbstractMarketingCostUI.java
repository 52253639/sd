/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractMarketingCostUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketingCostUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel MarketingCostUI;
    protected com.kingdee.bos.ctrl.swing.KDToolBar MarketingCostUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDMenuBar MarketingCostUI_menubar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer startDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker stDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker endDate;
    /**
     * output class constructor
     */
    public AbstractMarketingCostUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketingCostUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.MarketingCostUI = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.MarketingCostUI_toolbar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.MarketingCostUI_menubar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.startDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.stDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.endDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.MarketingCostUI.setName("MarketingCostUI");
        this.MarketingCostUI_toolbar.setName("MarketingCostUI_toolbar");
        this.MarketingCostUI_menubar.setName("MarketingCostUI_menubar");
        this.startDate.setName("startDate");
        this.kDLabelEndDate.setName("kDLabelEndDate");
        this.stDate.setName("stDate");
        this.endDate.setName("endDate");
        // CustomerQueryPanel
        // MarketingCostUI
        // MarketingCostUI_toolbar
        // MarketingCostUI_menubar
        // startDate		
        this.startDate.setBoundLabelText(resHelper.getString("startDate.boundLabelText"));		
        this.startDate.setBoundLabelLength(100);
        // kDLabelEndDate		
        this.kDLabelEndDate.setBoundLabelText(resHelper.getString("kDLabelEndDate.boundLabelText"));		
        this.kDLabelEndDate.setBoundLabelLength(100);
        // stDate
        // endDate
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 650, 400));
        this.setLayout(null);
        startDate.setBounds(new Rectangle(36, 18, 270, 19));
        this.add(startDate, null);
        kDLabelEndDate.setBounds(new Rectangle(338, 19, 270, 19));
        this.add(kDLabelEndDate, null);
        //startDate
        startDate.setBoundEditor(stDate);
        //kDLabelEndDate
        kDLabelEndDate.setBoundEditor(endDate);

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
	    return "com.kingdee.eas.fdc.market.app.MarketingCostUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketingCostUI");
    }




}