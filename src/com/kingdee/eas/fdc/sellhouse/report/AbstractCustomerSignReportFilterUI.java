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
public abstract class AbstractCustomerSignReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerSignReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProjectId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFromAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToBizDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFromDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToDealTotalAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    /**
     * output class constructor
     */
    public AbstractCustomerSignReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerSignReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSellProjectId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFromAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFromAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkFromBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtFromDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.btnSelect.setName("btnSelect");
        this.contSellProject.setName("contSellProject");
        this.txtSellProjectId.setName("txtSellProjectId");
        this.contFromAmount.setName("contFromAmount");
        this.contToAmount.setName("contToAmount");
        this.contFromBizDate.setName("contFromBizDate");
        this.contToBizDate.setName("contToBizDate");
        this.contFromDealTotalAmount.setName("contFromDealTotalAmount");
        this.contToDealTotalAmount.setName("contToDealTotalAmount");
        this.contProductType.setName("contProductType");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.txtSellProject.setName("txtSellProject");
        this.txtFromAmount.setName("txtFromAmount");
        this.txtToAmount.setName("txtToAmount");
        this.pkFromBizDate.setName("pkFromBizDate");
        this.pkToBizDate.setName("pkToBizDate");
        this.txtFromDealTotalAmount.setName("txtFromDealTotalAmount");
        this.txtToDealTotalAmount.setName("txtToDealTotalAmount");
        this.prmtProductType.setName("prmtProductType");
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
        // contFromAmount		
        this.contFromAmount.setBoundLabelText(resHelper.getString("contFromAmount.boundLabelText"));		
        this.contFromAmount.setBoundLabelLength(100);		
        this.contFromAmount.setBoundLabelUnderline(true);
        // contToAmount		
        this.contToAmount.setBoundLabelText(resHelper.getString("contToAmount.boundLabelText"));		
        this.contToAmount.setBoundLabelLength(100);		
        this.contToAmount.setBoundLabelUnderline(true);
        // contFromBizDate		
        this.contFromBizDate.setBoundLabelText(resHelper.getString("contFromBizDate.boundLabelText"));		
        this.contFromBizDate.setBoundLabelLength(100);		
        this.contFromBizDate.setBoundLabelUnderline(true);
        // contToBizDate		
        this.contToBizDate.setBoundLabelText(resHelper.getString("contToBizDate.boundLabelText"));		
        this.contToBizDate.setBoundLabelLength(100);		
        this.contToBizDate.setBoundLabelUnderline(true);
        // contFromDealTotalAmount		
        this.contFromDealTotalAmount.setBoundLabelText(resHelper.getString("contFromDealTotalAmount.boundLabelText"));		
        this.contFromDealTotalAmount.setBoundLabelLength(100);		
        this.contFromDealTotalAmount.setBoundLabelUnderline(true);
        // contToDealTotalAmount		
        this.contToDealTotalAmount.setBoundLabelText(resHelper.getString("contToDealTotalAmount.boundLabelText"));		
        this.contToDealTotalAmount.setBoundLabelLength(100);		
        this.contToDealTotalAmount.setBoundLabelUnderline(true);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // pkFromDate
        // pkToDate
        // txtSellProject		
        this.txtSellProject.setEnabled(false);
        // txtFromAmount
        // txtToAmount
        // pkFromBizDate
        // pkToBizDate
        // txtFromDealTotalAmount		
        this.txtFromDealTotalAmount.setDataType(1);		
        this.txtFromDealTotalAmount.setSupportedEmpty(true);		
        this.txtFromDealTotalAmount.setPrecision(2);
        // txtToDealTotalAmount		
        this.txtToDealTotalAmount.setDataType(1);		
        this.txtToDealTotalAmount.setSupportedEmpty(true);		
        this.txtToDealTotalAmount.setPrecision(2);
        // prmtProductType		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");		
        this.prmtProductType.setCommitFormat("$name$");		
        this.prmtProductType.setDisplayFormat("$name$");		
        this.prmtProductType.setEditFormat("$name$");		
        this.prmtProductType.setEnabledMultiSelection(true);
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
        this.setBounds(new Rectangle(10, 10, 600, 180));
        this.setLayout(null);
        contFromDate.setBounds(new Rectangle(15, 43, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(315, 43, 270, 19));
        this.add(contToDate, null);
        btnSelect.setBounds(new Rectangle(506, 21, 79, 19));
        this.add(btnSelect, null);
        contSellProject.setBounds(new Rectangle(15, 21, 465, 19));
        this.add(contSellProject, null);
        txtSellProjectId.setBounds(new Rectangle(440, 182, 170, 19));
        this.add(txtSellProjectId, null);
        contFromAmount.setBounds(new Rectangle(15, 65, 270, 19));
        this.add(contFromAmount, null);
        contToAmount.setBounds(new Rectangle(315, 65, 270, 19));
        this.add(contToAmount, null);
        contFromBizDate.setBounds(new Rectangle(15, 87, 270, 19));
        this.add(contFromBizDate, null);
        contToBizDate.setBounds(new Rectangle(315, 87, 270, 19));
        this.add(contToBizDate, null);
        contFromDealTotalAmount.setBounds(new Rectangle(15, 109, 270, 19));
        this.add(contFromDealTotalAmount, null);
        contToDealTotalAmount.setBounds(new Rectangle(315, 109, 270, 19));
        this.add(contToDealTotalAmount, null);
        contProductType.setBounds(new Rectangle(15, 131, 270, 19));
        this.add(contProductType, null);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);
        //contFromAmount
        contFromAmount.setBoundEditor(txtFromAmount);
        //contToAmount
        contToAmount.setBoundEditor(txtToAmount);
        //contFromBizDate
        contFromBizDate.setBoundEditor(pkFromBizDate);
        //contToBizDate
        contToBizDate.setBoundEditor(pkToBizDate);
        //contFromDealTotalAmount
        contFromDealTotalAmount.setBoundEditor(txtFromDealTotalAmount);
        //contToDealTotalAmount
        contToDealTotalAmount.setBoundEditor(txtToDealTotalAmount);
        //contProductType
        contProductType.setBoundEditor(prmtProductType);

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
	    return "com.kingdee.eas.fdc.sellhouse.report.CustomerSignReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "CustomerSignReportFilterUI");
    }




}