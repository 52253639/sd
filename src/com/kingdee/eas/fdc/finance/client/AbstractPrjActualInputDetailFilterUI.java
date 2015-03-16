/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractPrjActualInputDetailFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPrjActualInputDetailFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdCheNotFilterByDate;
    /**
     * output class constructor
     */
    public AbstractPrjActualInputDetailFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPrjActualInputDetailFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contCostAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnCostAccount = new com.kingdee.bos.ctrl.swing.KDButton();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtCostAccount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdCheNotFilterByDate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.bizProject.setName("bizProject");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.spiMonthTo.setName("spiMonthTo");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.spiYearFrom.setName("spiYearFrom");
        this.contCostAccount.setName("contCostAccount");
        this.btnCostAccount.setName("btnCostAccount");
        this.spiYearTo.setName("spiYearTo");
        this.kDLabel3.setName("kDLabel3");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.kDLabel4.setName("kDLabel4");
        this.txtCostAccount.setName("txtCostAccount");
        this.kdCheNotFilterByDate.setName("kdCheNotFilterByDate");
        // CustomerQueryPanel
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // bizProject
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // spiMonthTo
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // spiYearFrom
        // contCostAccount		
        this.contCostAccount.setBoundLabelText(resHelper.getString("contCostAccount.boundLabelText"));		
        this.contCostAccount.setBoundLabelLength(100);		
        this.contCostAccount.setBoundLabelUnderline(true);
        // btnCostAccount		
        this.btnCostAccount.setText(resHelper.getString("btnCostAccount.text"));
        this.btnCostAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCostAccount_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // spiYearTo
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // spiMonthFrom
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // txtCostAccount		
        this.txtCostAccount.setEnabled(false);
        this.txtCostAccount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtCostAccount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kdCheNotFilterByDate		
        this.kdCheNotFilterByDate.setText(resHelper.getString("kdCheNotFilterByDate.text"));
        this.kdCheNotFilterByDate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kdCheNotFilterByDate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        btnProjectSelect.setBounds(new Rectangle(288, 10, 73, 21));
        this.add(btnProjectSelect, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 70, 164, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(10, 100, 164, 19));
        this.add(kDLabelContainer3, null);
        spiMonthTo.setBounds(new Rectangle(206, 100, 45, 19));
        this.add(spiMonthTo, null);
        kDLabel1.setBounds(new Rectangle(181, 70, 21, 19));
        this.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(266, 70, 21, 19));
        this.add(kDLabel2, null);
        contCostAccount.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contCostAccount, null);
        btnCostAccount.setBounds(new Rectangle(290, 40, 73, 21));
        this.add(btnCostAccount, null);
        kDLabel3.setBounds(new Rectangle(179, 100, 21, 19));
        this.add(kDLabel3, null);
        spiMonthFrom.setBounds(new Rectangle(206, 70, 43, 19));
        this.add(spiMonthFrom, null);
        kDLabel4.setBounds(new Rectangle(266, 100, 21, 19));
        this.add(kDLabel4, null);
        kdCheNotFilterByDate.setBounds(new Rectangle(10, 130, 140, 19));
        this.add(kdCheNotFilterByDate, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(bizProject);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(spiYearFrom);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(spiYearTo);
        //contCostAccount
        contCostAccount.setBoundEditor(txtCostAccount);

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
	    return "com.kingdee.eas.fdc.finance.app.PrjActualInputDetailFilterUIHandler";
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
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCostAccount_actionPerformed method
     */
    protected void btnCostAccount_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtCostAccount_focusLost method
     */
    protected void txtCostAccount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kdCheNotFilterByDate_stateChanged method
     */
    protected void kdCheNotFilterByDate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PrjActualInputDetailFilterUI");
    }




}