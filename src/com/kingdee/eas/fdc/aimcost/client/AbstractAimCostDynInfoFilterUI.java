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
public abstract class AbstractAimCostDynInfoFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAimCostDynInfoFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblStartPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblEndPeriod;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizProject;
    /**
     * output class constructor
     */
    public AbstractAimCostDynInfoFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAimCostDynInfoFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.lblProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.lblStartPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblEndPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.bizProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.lblProject.setName("lblProject");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.lblStartPeriod.setName("lblStartPeriod");
        this.lblEndPeriod.setName("lblEndPeriod");
        this.spiYearFrom.setName("spiYearFrom");
        this.spiYearTo.setName("spiYearTo");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.bizProject.setName("bizProject");
        // CustomerQueryPanel
        // lblProject		
        this.lblProject.setBoundLabelText(resHelper.getString("lblProject.boundLabelText"));		
        this.lblProject.setBoundLabelLength(100);		
        this.lblProject.setBoundLabelUnderline(true);
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // lblStartPeriod		
        this.lblStartPeriod.setBoundLabelText(resHelper.getString("lblStartPeriod.boundLabelText"));		
        this.lblStartPeriod.setBoundLabelLength(100);		
        this.lblStartPeriod.setBoundLabelUnderline(true);
        // lblEndPeriod		
        this.lblEndPeriod.setBoundLabelText(resHelper.getString("lblEndPeriod.boundLabelText"));		
        this.lblEndPeriod.setBoundLabelLength(100);		
        this.lblEndPeriod.setBoundLabelUnderline(true);
        // spiYearFrom
        // spiYearTo
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // spiMonthFrom
        // spiMonthTo
        // bizProject
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
        lblProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(lblProject, null);
        btnProjectSelect.setBounds(new Rectangle(288, 10, 69, 19));
        this.add(btnProjectSelect, null);
        lblStartPeriod.setBounds(new Rectangle(10, 40, 164, 19));
        this.add(lblStartPeriod, null);
        lblEndPeriod.setBounds(new Rectangle(10, 70, 164, 19));
        this.add(lblEndPeriod, null);
        kDLabel1.setBounds(new Rectangle(181, 40, 21, 19));
        this.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(181, 70, 21, 19));
        this.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(262, 40, 21, 19));
        this.add(kDLabel3, null);
        kDLabel4.setBounds(new Rectangle(262, 70, 21, 19));
        this.add(kDLabel4, null);
        spiMonthFrom.setBounds(new Rectangle(207, 40, 45, 19));
        this.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(207, 70, 45, 19));
        this.add(spiMonthTo, null);
        //lblProject
        lblProject.setBoundEditor(bizProject);
        //lblStartPeriod
        lblStartPeriod.setBoundEditor(spiYearFrom);
        //lblEndPeriod
        lblEndPeriod.setBoundEditor(spiYearTo);

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
	    return "com.kingdee.eas.fdc.aimcost.app.AimCostDynInfoFilterUIHandler";
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
     * output btnProjectSelectSelect_actionPerformed method
     */
    protected void btnProjectSelectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "AimCostDynInfoFilterUI");
    }




}