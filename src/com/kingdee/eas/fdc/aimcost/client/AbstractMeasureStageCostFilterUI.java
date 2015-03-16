/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractMeasureStageCostFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMeasureStageCostFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnDevAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDevAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnIndirectAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIndirectAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnLandAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLandAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnAdminAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnSaleAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel10;
    /**
     * output class constructor
     */
    public AbstractMeasureStageCostFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMeasureStageCostFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.ctnDevAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtDevAcct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.ctnIndirectAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtIndirectAcct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.ctnLandAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtLandAcct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.ctnAdminAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAdminAcct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.ctnSaleAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSaleAcct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel10 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.ctnDevAcct.setName("ctnDevAcct");
        this.prmtDevAcct.setName("prmtDevAcct");
        this.ctnIndirectAcct.setName("ctnIndirectAcct");
        this.prmtIndirectAcct.setName("prmtIndirectAcct");
        this.ctnLandAcct.setName("ctnLandAcct");
        this.prmtLandAcct.setName("prmtLandAcct");
        this.ctnAdminAcct.setName("ctnAdminAcct");
        this.prmtAdminAcct.setName("prmtAdminAcct");
        this.ctnSaleAcct.setName("ctnSaleAcct");
        this.prmtSaleAcct.setName("prmtSaleAcct");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel7.setName("kDLabel7");
        this.kDLabel8.setName("kDLabel8");
        this.kDLabel9.setName("kDLabel9");
        this.kDLabel10.setName("kDLabel10");
        // CustomerQueryPanel
        // ctnDevAcct		
        this.ctnDevAcct.setBoundLabelText(resHelper.getString("ctnDevAcct.boundLabelText"));		
        this.ctnDevAcct.setBoundLabelLength(100);		
        this.ctnDevAcct.setBoundLabelUnderline(true);
        // prmtDevAcct		
        this.prmtDevAcct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        // ctnIndirectAcct		
        this.ctnIndirectAcct.setBoundLabelText(resHelper.getString("ctnIndirectAcct.boundLabelText"));		
        this.ctnIndirectAcct.setBoundLabelLength(100);		
        this.ctnIndirectAcct.setBoundLabelUnderline(true);
        // prmtIndirectAcct		
        this.prmtIndirectAcct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        // ctnLandAcct		
        this.ctnLandAcct.setBoundLabelText(resHelper.getString("ctnLandAcct.boundLabelText"));		
        this.ctnLandAcct.setBoundLabelLength(100);		
        this.ctnLandAcct.setBoundLabelUnderline(true);
        // prmtLandAcct		
        this.prmtLandAcct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        // ctnAdminAcct		
        this.ctnAdminAcct.setBoundLabelText(resHelper.getString("ctnAdminAcct.boundLabelText"));		
        this.ctnAdminAcct.setBoundLabelLength(100);		
        this.ctnAdminAcct.setBoundLabelUnderline(true);
        // prmtAdminAcct		
        this.prmtAdminAcct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        // ctnSaleAcct		
        this.ctnSaleAcct.setBoundLabelText(resHelper.getString("ctnSaleAcct.boundLabelText"));		
        this.ctnSaleAcct.setBoundLabelLength(100);		
        this.ctnSaleAcct.setBoundLabelUnderline(true);
        // prmtSaleAcct		
        this.prmtSaleAcct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // kDLabel10		
        this.kDLabel10.setText(resHelper.getString("kDLabel10.text"));
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
        this.setBounds(new Rectangle(10, 10, 400, 400));
        this.setLayout(null);
        ctnDevAcct.setBounds(new Rectangle(43, 11, 270, 19));
        this.add(ctnDevAcct, null);
        ctnIndirectAcct.setBounds(new Rectangle(43, 36, 270, 19));
        this.add(ctnIndirectAcct, null);
        ctnLandAcct.setBounds(new Rectangle(43, 60, 270, 19));
        this.add(ctnLandAcct, null);
        ctnAdminAcct.setBounds(new Rectangle(43, 84, 270, 19));
        this.add(ctnAdminAcct, null);
        ctnSaleAcct.setBounds(new Rectangle(43, 109, 270, 19));
        this.add(ctnSaleAcct, null);
        kDLabel1.setBounds(new Rectangle(43, 154, 100, 19));
        this.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(62, 174, 292, 19));
        this.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(62, 214, 292, 19));
        this.add(kDLabel3, null);
        kDLabel4.setBounds(new Rectangle(62, 234, 292, 19));
        this.add(kDLabel4, null);
        kDLabel5.setBounds(new Rectangle(62, 194, 292, 19));
        this.add(kDLabel5, null);
        kDLabel6.setBounds(new Rectangle(62, 274, 292, 19));
        this.add(kDLabel6, null);
        kDLabel7.setBounds(new Rectangle(62, 254, 292, 19));
        this.add(kDLabel7, null);
        kDLabel8.setBounds(new Rectangle(62, 294, 292, 19));
        this.add(kDLabel8, null);
        kDLabel9.setBounds(new Rectangle(62, 314, 292, 19));
        this.add(kDLabel9, null);
        kDLabel10.setBounds(new Rectangle(62, 334, 292, 19));
        this.add(kDLabel10, null);
        //ctnDevAcct
        ctnDevAcct.setBoundEditor(prmtDevAcct);
        //ctnIndirectAcct
        ctnIndirectAcct.setBoundEditor(prmtIndirectAcct);
        //ctnLandAcct
        ctnLandAcct.setBoundEditor(prmtLandAcct);
        //ctnAdminAcct
        ctnAdminAcct.setBoundEditor(prmtAdminAcct);
        //ctnSaleAcct
        ctnSaleAcct.setBoundEditor(prmtSaleAcct);

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
	    return "com.kingdee.eas.fdc.aimcost.app.MeasureStageCostFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "MeasureStageCostFilterUI");
    }




}