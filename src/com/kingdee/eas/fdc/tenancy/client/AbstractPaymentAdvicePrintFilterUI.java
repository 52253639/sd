/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractPaymentAdvicePrintFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentAdvicePrintFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppDate2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contComment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isAudit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isLack;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lackDates;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox sellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox contract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox customer;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateTo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea comment;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox moneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField kDNumberTextField1;
    /**
     * output class constructor
     */
    public AbstractPaymentAdvicePrintFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentAdvicePrintFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppDate2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contComment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isAudit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isLack = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.lackDates = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.sellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.comment = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.dateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.moneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDNumberTextField1 = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.contSellProject.setName("contSellProject");
        this.contTenancyBill.setName("contTenancyBill");
        this.contCustomer.setName("contCustomer");
        this.contAppDate2.setName("contAppDate2");
        this.contComment.setName("contComment");
        this.contAppDate.setName("contAppDate");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.isAudit.setName("isAudit");
        this.isLack.setName("isLack");
        this.lackDates.setName("lackDates");
        this.sellProject.setName("sellProject");
        this.contract.setName("contract");
        this.customer.setName("customer");
        this.dateTo.setName("dateTo");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.comment.setName("comment");
        this.dateFrom.setName("dateFrom");
        this.moneyDefine.setName("moneyDefine");
        this.kDNumberTextField1.setName("kDNumberTextField1");
        // CustomerQueryPanel
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contTenancyBill		
        this.contTenancyBill.setBoundLabelText(resHelper.getString("contTenancyBill.boundLabelText"));		
        this.contTenancyBill.setBoundLabelLength(100);		
        this.contTenancyBill.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contAppDate2		
        this.contAppDate2.setBoundLabelText(resHelper.getString("contAppDate2.boundLabelText"));		
        this.contAppDate2.setBoundLabelLength(100);		
        this.contAppDate2.setBoundLabelUnderline(true);
        // contComment		
        this.contComment.setBoundLabelText(resHelper.getString("contComment.boundLabelText"));		
        this.contComment.setBoundLabelLength(100);		
        this.contComment.setBoundLabelUnderline(true);
        // contAppDate		
        this.contAppDate.setBoundLabelText(resHelper.getString("contAppDate.boundLabelText"));		
        this.contAppDate.setBoundLabelLength(100);		
        this.contAppDate.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // isAudit		
        this.isAudit.setText(resHelper.getString("isAudit.text"));
        this.isAudit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isAudit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.isAudit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    isAudit_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // isLack		
        this.isLack.setText(resHelper.getString("isLack.text"));
        this.isLack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    isLack_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // lackDates		
        this.lackDates.setBoundLabelText(resHelper.getString("lackDates.boundLabelText"));		
        this.lackDates.setBoundLabelLength(100);
        // sellProject
        this.sellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    sellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contract
        // customer
        // dateTo
        // kDScrollPane1
        // comment
        // dateFrom
        // moneyDefine
        // kDNumberTextField1
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
        this.setBounds(new Rectangle(10, 10, 400, 360));
        this.setLayout(null);
        contSellProject.setBounds(new Rectangle(17, 42, 270, 19));
        this.add(contSellProject, null);
        contTenancyBill.setBounds(new Rectangle(17, 102, 270, 19));
        this.add(contTenancyBill, null);
        contCustomer.setBounds(new Rectangle(17, 72, 270, 19));
        this.add(contCustomer, null);
        contAppDate2.setBounds(new Rectangle(17, 192, 270, 19));
        this.add(contAppDate2, null);
        contComment.setBounds(new Rectangle(213, 10, 48, 14));
        this.add(contComment, null);
        contAppDate.setBounds(new Rectangle(17, 162, 270, 19));
        this.add(contAppDate, null);
        contMoneyDefine.setBounds(new Rectangle(17, 132, 270, 19));
        this.add(contMoneyDefine, null);
        isAudit.setBounds(new Rectangle(25, 10, 192, 19));
        this.add(isAudit, null);
        isLack.setBounds(new Rectangle(17, 223, 140, 19));
        this.add(isLack, null);
        lackDates.setBounds(new Rectangle(17, 254, 270, 19));
        this.add(lackDates, null);
        //contSellProject
        contSellProject.setBoundEditor(sellProject);
        //contTenancyBill
        contTenancyBill.setBoundEditor(contract);
        //contCustomer
        contCustomer.setBoundEditor(customer);
        //contAppDate2
        contAppDate2.setBoundEditor(dateTo);
        //contComment
        contComment.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(comment, null);
        //contAppDate
        contAppDate.setBoundEditor(dateFrom);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(moneyDefine);
        //lackDates
        lackDates.setBoundEditor(kDNumberTextField1);

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
	    return "com.kingdee.eas.fdc.tenancy.app.PaymentAdvicePrintFilterUIHandler";
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
     * output isAudit_actionPerformed method
     */
    protected void isAudit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output isAudit_mouseClicked method
     */
    protected void isAudit_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output isLack_mouseClicked method
     */
    protected void isLack_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output sellProject_dataChanged method
     */
    protected void sellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "PaymentAdvicePrintFilterUI");
    }




}