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
public abstract class AbstractContractBillPayPlanReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillPayPlanReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spSMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spEMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spSYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spEYear;
    /**
     * output class constructor
     */
    public AbstractContractBillPayPlanReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillPayPlanReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spSMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spEMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spSYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spEYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabel1.setName("kDLabel1");
        this.spSMonth.setName("spSMonth");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabel3.setName("kDLabel3");
        this.spEMonth.setName("spEMonth");
        this.kDLabel4.setName("kDLabel4");
        this.spSYear.setName("spSYear");
        this.spEYear.setName("spEYear");
        // CustomerQueryPanel
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // spSMonth
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // spEMonth
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // spSYear
        // spEYear
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
        this.setBounds(new Rectangle(10, 10, 600, 160));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(25, 63, 189, 19));
        this.add(kDLabelContainer1, null);
        kDLabel1.setBounds(new Rectangle(219, 63, 15, 19));
        this.add(kDLabel1, null);
        spSMonth.setBounds(new Rectangle(239, 63, 40, 19));
        this.add(spSMonth, null);
        kDLabel2.setBounds(new Rectangle(283, 63, 15, 19));
        this.add(kDLabel2, null);
        kDLabelContainer2.setBounds(new Rectangle(306, 63, 189, 19));
        this.add(kDLabelContainer2, null);
        kDLabel3.setBounds(new Rectangle(500, 63, 15, 19));
        this.add(kDLabel3, null);
        spEMonth.setBounds(new Rectangle(520, 63, 40, 19));
        this.add(spEMonth, null);
        kDLabel4.setBounds(new Rectangle(564, 63, 15, 19));
        this.add(kDLabel4, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spSYear);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(spEYear);

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
	    return "com.kingdee.eas.fdc.finance.app.ContractBillPayPlanReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ContractBillPayPlanReportFilterUI");
    }




}