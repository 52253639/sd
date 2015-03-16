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
public abstract class AbstractTenancyArrearageFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyArrearageFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBillBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBillEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton all;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton delayAndNo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton delayAndYes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton noDelay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDSpinner arrearageDay;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbDeposit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbRent;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAppBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAppEndDate;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbExpenses;
    /**
     * output class constructor
     */
    public AbstractTenancyArrearageFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyArrearageFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpBillBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpBillEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.all = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.delayAndNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.delayAndYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.noDelay = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.arrearageDay = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.cbDeposit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbRent = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpAppBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpAppEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.cbExpenses = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel2.setName("kDPanel2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.dpBillBeginDate.setName("dpBillBeginDate");
        this.dpBillEndDate.setName("dpBillEndDate");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.f7Customer.setName("f7Customer");
        this.kDPanel1.setName("kDPanel1");
        this.all.setName("all");
        this.delayAndNo.setName("delayAndNo");
        this.delayAndYes.setName("delayAndYes");
        this.noDelay.setName("noDelay");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.arrearageDay.setName("arrearageDay");
        this.kDPanel3.setName("kDPanel3");
        this.cbDeposit.setName("cbDeposit");
        this.cbRent.setName("cbRent");
        this.kDPanel4.setName("kDPanel4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.dpAppBeginDate.setName("dpAppBeginDate");
        this.dpAppEndDate.setName("dpAppEndDate");
        this.cbExpenses.setName("cbExpenses");
        // CustomerQueryPanel
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
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
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // f7Customer		
        this.f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.f7Customer.setCommitFormat("$number$");		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // all		
        this.all.setText(resHelper.getString("all.text"));		
        this.all.setSelected(true);
        this.all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    all_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // delayAndNo		
        this.delayAndNo.setText(resHelper.getString("delayAndNo.text"));
        this.delayAndNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    delayAndNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // delayAndYes		
        this.delayAndYes.setText(resHelper.getString("delayAndYes.text"));
        this.delayAndYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    delayAndYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // noDelay		
        this.noDelay.setText(resHelper.getString("noDelay.text"));
        this.noDelay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    noDelay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // arrearageDay
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // cbDeposit		
        this.cbDeposit.setText(resHelper.getString("cbDeposit.text"));		
        this.cbDeposit.setSelected(true);
        // cbRent		
        this.cbRent.setText(resHelper.getString("cbRent.text"));		
        this.cbRent.setSelected(true);
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));
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
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.all);
        this.kDButtonGroup1.add(this.delayAndNo);
        this.kDButtonGroup1.add(this.delayAndYes);
        this.kDButtonGroup1.add(this.noDelay);
        // cbExpenses		
        this.cbExpenses.setText(resHelper.getString("cbExpenses.text"));		
        this.cbExpenses.setSelected(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 620, 290));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 620, 290));
        kDPanel2.setBounds(new Rectangle(1, 2, 616, 63));
        this.add(kDPanel2, new KDLayout.Constraints(1, 2, 616, 63, 0));
        kDLabelContainer3.setBounds(new Rectangle(22, 67, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(22, 67, 270, 19, 0));
        kDPanel1.setBounds(new Rectangle(1, 96, 616, 50));
        this.add(kDPanel1, new KDLayout.Constraints(1, 96, 616, 50, 0));
        kDLabelContainer4.setBounds(new Rectangle(21, 148, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(21, 148, 270, 19, 0));
        kDPanel3.setBounds(new Rectangle(1, 176, 616, 54));
        this.add(kDPanel3, new KDLayout.Constraints(1, 176, 616, 54, 0));
        kDPanel4.setBounds(new Rectangle(1, 230, 616, 60));
        this.add(kDPanel4, new KDLayout.Constraints(1, 230, 616, 60, 0));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(1, 2, 616, 63));        kDLabelContainer1.setBounds(new Rectangle(20, 23, 270, 19));
        kDPanel2.add(kDLabelContainer1, new KDLayout.Constraints(20, 23, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(325, 23, 270, 19));
        kDPanel2.add(kDLabelContainer2, new KDLayout.Constraints(325, 23, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(dpBillBeginDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(dpBillEndDate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(f7Customer);
        //kDPanel1
        kDPanel1.setLayout(null);        all.setBounds(new Rectangle(20, 17, 140, 19));
        kDPanel1.add(all, null);
        delayAndNo.setBounds(new Rectangle(160, 17, 140, 19));
        kDPanel1.add(delayAndNo, null);
        delayAndYes.setBounds(new Rectangle(327, 17, 140, 19));
        kDPanel1.add(delayAndYes, null);
        noDelay.setBounds(new Rectangle(466, 17, 140, 19));
        kDPanel1.add(noDelay, null);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(arrearageDay);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(1, 176, 616, 54));        cbDeposit.setBounds(new Rectangle(20, 18, 140, 19));
        kDPanel3.add(cbDeposit, new KDLayout.Constraints(20, 18, 140, 19, 0));
        cbRent.setBounds(new Rectangle(160, 19, 140, 19));
        kDPanel3.add(cbRent, new KDLayout.Constraints(160, 19, 140, 19, 0));
        cbExpenses.setBounds(new Rectangle(327, 19, 140, 19));
        kDPanel3.add(cbExpenses, new KDLayout.Constraints(327, 19, 140, 19, 0));
        //kDPanel4
        kDPanel4.setLayout(null);        kDLabelContainer5.setBounds(new Rectangle(19, 20, 270, 19));
        kDPanel4.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(326, 20, 270, 19));
        kDPanel4.add(kDLabelContainer6, null);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(dpAppBeginDate);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(dpAppEndDate);

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
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyArrearageFilterUIHandler";
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
     * output all_actionPerformed method
     */
    protected void all_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output delayAndNo_actionPerformed method
     */
    protected void delayAndNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output delayAndYes_actionPerformed method
     */
    protected void delayAndYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output noDelay_actionPerformed method
     */
    protected void noDelay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyArrearageFilterUI");
    }




}