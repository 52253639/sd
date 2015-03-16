/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

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
public abstract class AbstractBackAmountCycleReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBackAmountCycleReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromSignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToSignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromActDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToActDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromAppDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToAppDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromSignDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToSignDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromActDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToActDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromAppDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToAppDate;
    /**
     * output class constructor
     */
    public AbstractBackAmountCycleReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBackAmountCycleReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromSignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToSignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromActDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToActDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromAppDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToAppDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkFromSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkFromActDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToActDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkFromAppDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToAppDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contFromSignDate.setName("contFromSignDate");
        this.contToSignDate.setName("contToSignDate");
        this.contFromActDate.setName("contFromActDate");
        this.contToActDate.setName("contToActDate");
        this.contFromAppDate.setName("contFromAppDate");
        this.contToAppDate.setName("contToAppDate");
        this.pkFromSignDate.setName("pkFromSignDate");
        this.pkToSignDate.setName("pkToSignDate");
        this.pkFromActDate.setName("pkFromActDate");
        this.pkToActDate.setName("pkToActDate");
        this.pkFromAppDate.setName("pkFromAppDate");
        this.pkToAppDate.setName("pkToAppDate");
        // CustomerQueryPanel
        // contFromSignDate		
        this.contFromSignDate.setBoundLabelText(resHelper.getString("contFromSignDate.boundLabelText"));		
        this.contFromSignDate.setBoundLabelLength(100);		
        this.contFromSignDate.setBoundLabelUnderline(true);
        // contToSignDate		
        this.contToSignDate.setBoundLabelText(resHelper.getString("contToSignDate.boundLabelText"));		
        this.contToSignDate.setBoundLabelLength(100);		
        this.contToSignDate.setBoundLabelUnderline(true);
        // contFromActDate		
        this.contFromActDate.setBoundLabelText(resHelper.getString("contFromActDate.boundLabelText"));		
        this.contFromActDate.setBoundLabelLength(100);		
        this.contFromActDate.setBoundLabelUnderline(true);
        // contToActDate		
        this.contToActDate.setBoundLabelText(resHelper.getString("contToActDate.boundLabelText"));		
        this.contToActDate.setBoundLabelLength(100);		
        this.contToActDate.setBoundLabelUnderline(true);
        // contFromAppDate		
        this.contFromAppDate.setBoundLabelText(resHelper.getString("contFromAppDate.boundLabelText"));		
        this.contFromAppDate.setBoundLabelLength(100);		
        this.contFromAppDate.setBoundLabelUnderline(true);
        // contToAppDate		
        this.contToAppDate.setBoundLabelText(resHelper.getString("contToAppDate.boundLabelText"));		
        this.contToAppDate.setBoundLabelLength(100);		
        this.contToAppDate.setBoundLabelUnderline(true);
        // pkFromSignDate
        // pkToSignDate
        // pkFromActDate
        // pkToActDate
        // pkFromAppDate
        // pkToAppDate
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
        this.setBounds(new Rectangle(10, 10, 600, 120));
        this.setLayout(null);
        contFromSignDate.setBounds(new Rectangle(17, 22, 270, 19));
        this.add(contFromSignDate, null);
        contToSignDate.setBounds(new Rectangle(314, 23, 270, 19));
        this.add(contToSignDate, null);
        contFromActDate.setBounds(new Rectangle(17, 55, 270, 19));
        this.add(contFromActDate, null);
        contToActDate.setBounds(new Rectangle(314, 55, 270, 19));
        this.add(contToActDate, null);
        contFromAppDate.setBounds(new Rectangle(17, 89, 270, 19));
        this.add(contFromAppDate, null);
        contToAppDate.setBounds(new Rectangle(314, 88, 270, 19));
        this.add(contToAppDate, null);
        //contFromSignDate
        contFromSignDate.setBoundEditor(pkFromSignDate);
        //contToSignDate
        contToSignDate.setBoundEditor(pkToSignDate);
        //contFromActDate
        contFromActDate.setBoundEditor(pkFromActDate);
        //contToActDate
        contToActDate.setBoundEditor(pkToActDate);
        //contFromAppDate
        contFromAppDate.setBoundEditor(pkFromAppDate);
        //contToAppDate
        contToAppDate.setBoundEditor(pkToAppDate);

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
	    return "com.kingdee.eas.fdc.sellhouse.report.BackAmountCycleReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "BackAmountCycleReportFilterUI");
    }




}