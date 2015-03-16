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
public abstract class AbstractSaleManReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSaleManReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProjectId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMarketUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMarketUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFromAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToAmount;
    /**
     * output class constructor
     */
    public AbstractSaleManReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSaleManReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSellProjectId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMarketUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMarketUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFromAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.btnSelect.setName("btnSelect");
        this.contSellProject.setName("contSellProject");
        this.txtSellProjectId.setName("txtSellProjectId");
        this.contUser.setName("contUser");
        this.contMarketUnit.setName("contMarketUnit");
        this.contFromAmount.setName("contFromAmount");
        this.contToAmount.setName("contToAmount");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.txtSellProject.setName("txtSellProject");
        this.prmtUser.setName("prmtUser");
        this.prmtMarketUnit.setName("prmtMarketUnit");
        this.txtFromAmount.setName("txtFromAmount");
        this.txtToAmount.setName("txtToAmount");
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
        // txtSellProjectId		
        this.txtSellProjectId.setEnabled(false);		
        this.txtSellProjectId.setVisible(false);
        // contUser		
        this.contUser.setBoundLabelText(resHelper.getString("contUser.boundLabelText"));		
        this.contUser.setBoundLabelLength(100);		
        this.contUser.setBoundLabelUnderline(true);
        // contMarketUnit		
        this.contMarketUnit.setBoundLabelText(resHelper.getString("contMarketUnit.boundLabelText"));		
        this.contMarketUnit.setBoundLabelLength(100);		
        this.contMarketUnit.setBoundLabelUnderline(true);
        // contFromAmount		
        this.contFromAmount.setBoundLabelText(resHelper.getString("contFromAmount.boundLabelText"));		
        this.contFromAmount.setBoundLabelLength(100);		
        this.contFromAmount.setBoundLabelUnderline(true);
        // contToAmount		
        this.contToAmount.setBoundLabelText(resHelper.getString("contToAmount.boundLabelText"));		
        this.contToAmount.setBoundLabelLength(100);		
        this.contToAmount.setBoundLabelUnderline(true);
        // pkFromDate
        // pkToDate
        // txtSellProject		
        this.txtSellProject.setEnabled(false);
        // prmtUser		
        this.prmtUser.setCommitFormat("$name$");		
        this.prmtUser.setDisplayFormat("$name$");		
        this.prmtUser.setEditFormat("$name$");		
        this.prmtUser.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.prmtUser.setEnabledMultiSelection(true);
        // prmtMarketUnit		
        this.prmtMarketUnit.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MarketingUnitQuery");		
        this.prmtMarketUnit.setCommitFormat("$name$");		
        this.prmtMarketUnit.setDisplayFormat("$name$");		
        this.prmtMarketUnit.setEditFormat("$name$");		
        this.prmtMarketUnit.setEnabledMultiSelection(true);
        // txtFromAmount
        // txtToAmount
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
        contFromDate.setBounds(new Rectangle(17, 32, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(317, 32, 270, 19));
        this.add(contToDate, null);
        btnSelect.setBounds(new Rectangle(508, 10, 79, 19));
        this.add(btnSelect, null);
        contSellProject.setBounds(new Rectangle(17, 10, 465, 19));
        this.add(contSellProject, null);
        txtSellProjectId.setBounds(new Rectangle(455, 104, 170, 19));
        this.add(txtSellProjectId, null);
        contUser.setBounds(new Rectangle(17, 54, 270, 19));
        this.add(contUser, null);
        contMarketUnit.setBounds(new Rectangle(317, 54, 270, 19));
        this.add(contMarketUnit, null);
        contFromAmount.setBounds(new Rectangle(17, 76, 270, 19));
        this.add(contFromAmount, null);
        contToAmount.setBounds(new Rectangle(317, 76, 270, 19));
        this.add(contToAmount, null);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);
        //contUser
        contUser.setBoundEditor(prmtUser);
        //contMarketUnit
        contMarketUnit.setBoundEditor(prmtMarketUnit);
        //contFromAmount
        contFromAmount.setBoundEditor(txtFromAmount);
        //contToAmount
        contToAmount.setBoundEditor(txtToAmount);

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
	    return "com.kingdee.eas.fdc.sellhouse.report.SaleManReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "SaleManReportFilterUI");
    }




}