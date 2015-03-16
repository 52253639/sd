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
public abstract class AbstractCustomerChangeReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerChangeReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProjectId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellMan;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAll;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellMan;
    /**
     * output class constructor
     */
    public AbstractCustomerChangeReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerChangeReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSellProjectId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSellMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnSelect.setName("btnSelect");
        this.contSellProject.setName("contSellProject");
        this.txtSellProjectId.setName("txtSellProjectId");
        this.contName.setName("contName");
        this.contSellMan.setName("contSellMan");
        this.cbIsAll.setName("cbIsAll");
        this.txtSellProject.setName("txtSellProject");
        this.txtName.setName("txtName");
        this.txtSellMan.setName("txtSellMan");
        // CustomerQueryPanel
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
        // txtSellProjectId		
        this.txtSellProjectId.setVisible(false);		
        this.txtSellProjectId.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contSellMan		
        this.contSellMan.setBoundLabelText(resHelper.getString("contSellMan.boundLabelText"));		
        this.contSellMan.setBoundLabelLength(100);		
        this.contSellMan.setBoundLabelUnderline(true);
        // cbIsAll		
        this.cbIsAll.setText(resHelper.getString("cbIsAll.text"));
        // txtSellProject		
        this.txtSellProject.setEnabled(false);
        // txtName
        // txtSellMan
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
        btnSelect.setBounds(new Rectangle(508, 26, 79, 19));
        this.add(btnSelect, null);
        contSellProject.setBounds(new Rectangle(17, 26, 465, 19));
        this.add(contSellProject, null);
        txtSellProjectId.setBounds(new Rectangle(112, 145, 170, 19));
        this.add(txtSellProjectId, null);
        contName.setBounds(new Rectangle(17, 57, 270, 19));
        this.add(contName, null);
        contSellMan.setBounds(new Rectangle(317, 57, 270, 19));
        this.add(contSellMan, null);
        cbIsAll.setBounds(new Rectangle(17, 88, 140, 19));
        this.add(cbIsAll, null);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);
        //contName
        contName.setBoundEditor(txtName);
        //contSellMan
        contSellMan.setBoundEditor(txtSellMan);

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
	    return "com.kingdee.eas.fdc.sellhouse.report.CustomerChangeReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "CustomerChangeReportFilterUI");
    }




}