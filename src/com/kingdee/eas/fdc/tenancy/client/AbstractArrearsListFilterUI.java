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
public abstract class AbstractArrearsListFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractArrearsListFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBillBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBillEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbDeposit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbRent;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbExpenses;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbOtherPay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAppBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAppEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox contract;
    /**
     * output class constructor
     */
    public AbstractArrearsListFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractArrearsListFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpBillBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpBillEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbDeposit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbRent = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbExpenses = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbOtherPay = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpAppBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpAppEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1.setName("kDPanel1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.dpBillBeginDate.setName("dpBillBeginDate");
        this.dpBillEndDate.setName("dpBillEndDate");
        this.f7Customer.setName("f7Customer");
        this.cbDeposit.setName("cbDeposit");
        this.cbRent.setName("cbRent");
        this.cbExpenses.setName("cbExpenses");
        this.cbOtherPay.setName("cbOtherPay");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.dpAppBeginDate.setName("dpAppBeginDate");
        this.dpAppEndDate.setName("dpAppEndDate");
        this.contract.setName("contract");
        // CustomerQueryPanel
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // dpBillBeginDate
        // dpBillEndDate
        // f7Customer		
        this.f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.f7Customer.setCommitFormat("$number$");		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");
        // cbDeposit		
        this.cbDeposit.setText(resHelper.getString("cbDeposit.text"));		
        this.cbDeposit.setSelected(true);
        this.cbDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbDeposit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbRent		
        this.cbRent.setText(resHelper.getString("cbRent.text"));		
        this.cbRent.setSelected(true);
        // cbExpenses		
        this.cbExpenses.setText(resHelper.getString("cbExpenses.text"));		
        this.cbExpenses.setSelected(true);
        this.cbExpenses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbExpenses_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbOtherPay		
        this.cbOtherPay.setText(resHelper.getString("cbOtherPay.text"));		
        this.cbOtherPay.setSelected(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // dpAppBeginDate
        // dpAppEndDate
        // contract		
        this.contract.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillF7Query");		
        this.contract.setDisplayFormat("$tenancyName$");		
        this.contract.setEditFormat("$number$");		
        this.contract.setCommitFormat("$number$");
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
        this.setBounds(new Rectangle(10, 10, 620, 290));
        this.setLayout(null);
        kDPanel1.setBounds(new Rectangle(2, 3, 616, 63));
        this.add(kDPanel1, null);
        kDLabelContainer3.setBounds(new Rectangle(328, 70, 270, 19));
        this.add(kDLabelContainer3, null);
        kDPanel3.setBounds(new Rectangle(2, 104, 616, 54));
        this.add(kDPanel3, null);
        kDPanel4.setBounds(new Rectangle(2, 170, 616, 60));
        this.add(kDPanel4, null);
        kDLabelContainer7.setBounds(new Rectangle(23, 70, 270, 19));
        this.add(kDLabelContainer7, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(2, 3, 616, 63));        kDLabelContainer1.setBounds(new Rectangle(20, 23, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(20, 23, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(325, 23, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(325, 23, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(dpBillBeginDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(dpBillEndDate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(f7Customer);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(2, 104, 616, 54));        cbDeposit.setBounds(new Rectangle(20, 18, 115, 19));
        kDPanel3.add(cbDeposit, new KDLayout.Constraints(20, 18, 115, 19, 0));
        cbRent.setBounds(new Rectangle(173, 19, 115, 19));
        kDPanel3.add(cbRent, new KDLayout.Constraints(173, 19, 115, 19, 0));
        cbExpenses.setBounds(new Rectangle(326, 19, 115, 19));
        kDPanel3.add(cbExpenses, new KDLayout.Constraints(326, 19, 115, 19, 0));
        cbOtherPay.setBounds(new Rectangle(479, 19, 115, 19));
        kDPanel3.add(cbOtherPay, new KDLayout.Constraints(479, 19, 115, 19, 0));
        //kDPanel4
        kDPanel4.setLayout(null);        kDLabelContainer5.setBounds(new Rectangle(19, 20, 270, 19));
        kDPanel4.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(326, 20, 270, 19));
        kDPanel4.add(kDLabelContainer6, null);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(dpAppBeginDate);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(dpAppEndDate);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(contract);

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
	    return "com.kingdee.eas.fdc.tenancy.app.ArrearsListFilterUIHandler";
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
     * output cbDeposit_actionPerformed method
     */
    protected void cbDeposit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cbExpenses_actionPerformed method
     */
    protected void cbExpenses_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "ArrearsListFilterUI");
    }




}