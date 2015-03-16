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
public abstract class AbstractTimeAccountReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTimeAccountReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsLock;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAll;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsFitment;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    /**
     * output class constructor
     */
    public AbstractTimeAccountReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTimeAccountReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsLock = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsFitment = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.btnSelect.setName("btnSelect");
        this.contSellProject.setName("contSellProject");
        this.cbIsLock.setName("cbIsLock");
        this.cbIsAll.setName("cbIsAll");
        this.cbIsFitment.setName("cbIsFitment");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
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
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // cbIsLock		
        this.cbIsLock.setText(resHelper.getString("cbIsLock.text"));
        // cbIsAll		
        this.cbIsAll.setText(resHelper.getString("cbIsAll.text"));
        this.cbIsAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbIsAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbIsFitment		
        this.cbIsFitment.setText(resHelper.getString("cbIsFitment.text"));
        // pkFromDate
        // pkToDate
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
        this.setLayout(null);
        contFromDate.setBounds(new Rectangle(17, 52, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(317, 52, 270, 19));
        this.add(contToDate, null);
        btnSelect.setBounds(new Rectangle(508, 13, 79, 19));
        this.add(btnSelect, null);
        contSellProject.setBounds(new Rectangle(17, 13, 465, 19));
        this.add(contSellProject, null);
        cbIsLock.setBounds(new Rectangle(17, 92, 140, 19));
        this.add(cbIsLock, null);
        cbIsAll.setBounds(new Rectangle(225, 92, 140, 19));
        this.add(cbIsAll, null);
        cbIsFitment.setBounds(new Rectangle(418, 92, 177, 19));
        this.add(cbIsFitment, null);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
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
	    return "com.kingdee.eas.fdc.sellhouse.report.TimeAccountReportFilterUIHandler";
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
     * output cbIsAll_actionPerformed method
     */
    protected void cbIsAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "TimeAccountReportFilterUI");
    }




}