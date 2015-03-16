/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

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
public abstract class AbstractEnterpriseDevReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEnterpriseDevReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer confromBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkfromBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktoBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer confromEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkfromEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktoEndDate;
    /**
     * output class constructor
     */
    public AbstractEnterpriseDevReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEnterpriseDevReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.confromBeginDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoBeginDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkfromBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pktoBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.confromEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkfromEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pktoEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.confromBeginDate.setName("confromBeginDate");
        this.contoBeginDate.setName("contoBeginDate");
        this.pkfromBeginDate.setName("pkfromBeginDate");
        this.pktoBeginDate.setName("pktoBeginDate");
        this.confromEndDate.setName("confromEndDate");
        this.contoEndDate.setName("contoEndDate");
        this.pkfromEndDate.setName("pkfromEndDate");
        this.pktoEndDate.setName("pktoEndDate");
        // CustomerQueryPanel
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // confromBeginDate		
        this.confromBeginDate.setBoundLabelText(resHelper.getString("confromBeginDate.boundLabelText"));		
        this.confromBeginDate.setBoundLabelLength(40);
        // contoBeginDate		
        this.contoBeginDate.setBoundLabelText(resHelper.getString("contoBeginDate.boundLabelText"));		
        this.contoBeginDate.setBoundLabelLength(40);
        // pkfromBeginDate
        // pktoBeginDate
        // confromEndDate		
        this.confromEndDate.setBoundLabelText(resHelper.getString("confromEndDate.boundLabelText"));		
        this.confromEndDate.setBoundLabelLength(40);
        // contoEndDate		
        this.contoEndDate.setBoundLabelText(resHelper.getString("contoEndDate.boundLabelText"));		
        this.contoEndDate.setBoundLabelLength(40);
        // pkfromEndDate
        // pktoEndDate
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
        this.setBounds(new Rectangle(10, 10, 556, 359));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 556, 359));
        kDPanel1.setBounds(new Rectangle(22, 25, 513, 58));
        this.add(kDPanel1, new KDLayout.Constraints(22, 25, 513, 58, 0));
        kDPanel2.setBounds(new Rectangle(22, 96, 513, 58));
        this.add(kDPanel2, new KDLayout.Constraints(22, 96, 513, 58, 0));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(22, 25, 513, 58));        confromBeginDate.setBounds(new Rectangle(16, 20, 213, 19));
        kDPanel1.add(confromBeginDate, new KDLayout.Constraints(16, 20, 213, 19, 0));
        contoBeginDate.setBounds(new Rectangle(252, 20, 213, 19));
        kDPanel1.add(contoBeginDate, new KDLayout.Constraints(252, 20, 213, 19, 0));
        //confromBeginDate
        confromBeginDate.setBoundEditor(pkfromBeginDate);
        //contoBeginDate
        contoBeginDate.setBoundEditor(pktoBeginDate);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(22, 96, 513, 58));        confromEndDate.setBounds(new Rectangle(16, 20, 213, 19));
        kDPanel2.add(confromEndDate, new KDLayout.Constraints(16, 20, 213, 19, 0));
        contoEndDate.setBounds(new Rectangle(252, 20, 213, 19));
        kDPanel2.add(contoEndDate, new KDLayout.Constraints(252, 20, 213, 19, 0));
        //confromEndDate
        confromEndDate.setBoundEditor(pkfromEndDate);
        //contoEndDate
        contoEndDate.setBoundEditor(pktoEndDate);

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
	    return "com.kingdee.eas.fdc.market.report.EnterpriseDevReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.market.report", "EnterpriseDevReportFilterUI");
    }




}