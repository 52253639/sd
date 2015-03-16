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
public abstract class AbstractLiquidatedTBOPayFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLiquidatedTBOPayFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppDate2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contComment;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isAudit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox sellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox customer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox contract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox moneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateTo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea comment;
    /**
     * output class constructor
     */
    public AbstractLiquidatedTBOPayFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLiquidatedTBOPayFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppDate2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contComment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isAudit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.sellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.moneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.comment = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contSellProject.setName("contSellProject");
        this.contCustomer.setName("contCustomer");
        this.contTenancyBill.setName("contTenancyBill");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contAppDate.setName("contAppDate");
        this.contAppDate2.setName("contAppDate2");
        this.contComment.setName("contComment");
        this.isAudit.setName("isAudit");
        this.sellProject.setName("sellProject");
        this.customer.setName("customer");
        this.contract.setName("contract");
        this.moneyDefine.setName("moneyDefine");
        this.dateFrom.setName("dateFrom");
        this.dateTo.setName("dateTo");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.comment.setName("comment");
        // CustomerQueryPanel
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contTenancyBill		
        this.contTenancyBill.setBoundLabelText(resHelper.getString("contTenancyBill.boundLabelText"));		
        this.contTenancyBill.setBoundLabelLength(100);		
        this.contTenancyBill.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // contAppDate		
        this.contAppDate.setBoundLabelText(resHelper.getString("contAppDate.boundLabelText"));		
        this.contAppDate.setBoundLabelLength(100);		
        this.contAppDate.setBoundLabelUnderline(true);
        // contAppDate2		
        this.contAppDate2.setBoundLabelText(resHelper.getString("contAppDate2.boundLabelText"));		
        this.contAppDate2.setBoundLabelLength(100);		
        this.contAppDate2.setBoundLabelUnderline(true);
        // contComment		
        this.contComment.setBoundLabelText(resHelper.getString("contComment.boundLabelText"));		
        this.contComment.setBoundLabelLength(100);		
        this.contComment.setBoundLabelUnderline(true);
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
        // customer
        // contract
        // moneyDefine
        // dateFrom
        // dateTo
        // kDScrollPane1
        // comment
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
        contSellProject.setBounds(new Rectangle(56, 45, 270, 19));
        this.add(contSellProject, null);
        contCustomer.setBounds(new Rectangle(56, 77, 270, 19));
        this.add(contCustomer, null);
        contTenancyBill.setBounds(new Rectangle(56, 109, 270, 19));
        this.add(contTenancyBill, null);
        contMoneyDefine.setBounds(new Rectangle(56, 141, 270, 19));
        this.add(contMoneyDefine, null);
        contAppDate.setBounds(new Rectangle(56, 173, 270, 19));
        this.add(contAppDate, null);
        contAppDate2.setBounds(new Rectangle(56, 207, 270, 19));
        this.add(contAppDate2, null);
        contComment.setBounds(new Rectangle(54, 240, 270, 100));
        this.add(contComment, null);
        isAudit.setBounds(new Rectangle(57, 16, 192, 19));
        this.add(isAudit, null);
        //contSellProject
        contSellProject.setBoundEditor(sellProject);
        //contCustomer
        contCustomer.setBoundEditor(customer);
        //contTenancyBill
        contTenancyBill.setBoundEditor(contract);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(moneyDefine);
        //contAppDate
        contAppDate.setBoundEditor(dateFrom);
        //contAppDate2
        contAppDate2.setBoundEditor(dateTo);
        //contComment
        contComment.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(comment, null);

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
	    return "com.kingdee.eas.fdc.tenancy.app.LiquidatedTBOPayFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "LiquidatedTBOPayFilterUI");
    }




}