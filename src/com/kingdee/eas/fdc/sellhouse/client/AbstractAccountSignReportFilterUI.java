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
public abstract class AbstractAccountSignReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAccountSignReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsPre;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsPur;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtFromDays;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtToDays;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    /**
     * output class constructor
     */
    public AbstractAccountSignReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAccountSignReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.cbIsPre = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsPur = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtFromDays = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtToDays = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.contFromDays.setName("contFromDays");
        this.contToDays.setName("contToDays");
        this.contSellProject.setName("contSellProject");
        this.btnSelect.setName("btnSelect");
        this.cbIsPre.setName("cbIsPre");
        this.cbIsPur.setName("cbIsPur");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.txtFromDays.setName("txtFromDays");
        this.txtToDays.setName("txtToDays");
        this.txtSellProject.setName("txtSellProject");
        // CustomerQueryPanel
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelLength(100);		
        this.contFromDate.setBoundLabelUnderline(true);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // contFromDays		
        this.contFromDays.setBoundLabelText(resHelper.getString("contFromDays.boundLabelText"));		
        this.contFromDays.setBoundLabelLength(100);		
        this.contFromDays.setBoundLabelUnderline(true);
        // contToDays		
        this.contToDays.setBoundLabelText(resHelper.getString("contToDays.boundLabelText"));		
        this.contToDays.setBoundLabelLength(100);		
        this.contToDays.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // btnSelect		
        this.btnSelect.setText(resHelper.getString("btnSelect.text"));
        this.btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbIsPre		
        this.cbIsPre.setText(resHelper.getString("cbIsPre.text"));
        // cbIsPur		
        this.cbIsPur.setText(resHelper.getString("cbIsPur.text"));
        // pkFromDate
        // pkToDate
        // txtFromDays		
        this.txtFromDays.setSupportedEmpty(true);
        // txtToDays		
        this.txtToDays.setSupportedEmpty(true);
        // txtSellProject		
        this.txtSellProject.setEnabled(false);
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 120));
        contFromDate.setBounds(new Rectangle(18, 40, 270, 19));
        this.add(contFromDate, new KDLayout.Constraints(18, 40, 270, 19, 0));
        contToDate.setBounds(new Rectangle(309, 40, 270, 19));
        this.add(contToDate, new KDLayout.Constraints(309, 40, 270, 19, 0));
        contFromDays.setBounds(new Rectangle(18, 68, 270, 19));
        this.add(contFromDays, new KDLayout.Constraints(18, 68, 270, 19, 0));
        contToDays.setBounds(new Rectangle(309, 68, 270, 19));
        this.add(contToDays, new KDLayout.Constraints(309, 68, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(18, 12, 465, 19));
        this.add(contSellProject, new KDLayout.Constraints(18, 12, 465, 19, 0));
        btnSelect.setBounds(new Rectangle(500, 12, 79, 19));
        this.add(btnSelect, new KDLayout.Constraints(500, 12, 79, 19, 0));
        cbIsPre.setBounds(new Rectangle(18, 98, 85, 19));
        this.add(cbIsPre, new KDLayout.Constraints(18, 98, 85, 19, 0));
        cbIsPur.setBounds(new Rectangle(148, 98, 102, 19));
        this.add(cbIsPur, new KDLayout.Constraints(148, 98, 102, 19, 0));
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contFromDays
        contFromDays.setBoundEditor(txtFromDays);
        //contToDays
        contToDays.setBoundEditor(txtToDays);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.AccountSignReportFilterUIHandler";
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
     * output btnSelect_actionPerformed method
     */
    protected void btnSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "AccountSignReportFilterUI");
    }




}