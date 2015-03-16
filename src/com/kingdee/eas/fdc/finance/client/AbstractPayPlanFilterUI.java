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
public abstract class AbstractPayPlanFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayPlanFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDPanel plChangeState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioNoAudit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAudited;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioStateAll;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpinner1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpinner2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    /**
     * output class constructor
     */
    public AbstractPayPlanFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPayPlanFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plChangeState = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.radioNoAudit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAudited = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioStateAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDSpinner1 = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDSpinner2 = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contCompany.setName("contCompany");
        this.contDateFrom.setName("contDateFrom");
        this.plChangeState.setName("plChangeState");
        this.txtCompany.setName("txtCompany");
        this.radioNoAudit.setName("radioNoAudit");
        this.radioAudited.setName("radioAudited");
        this.radioStateAll.setName("radioStateAll");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.kDSpinner1.setName("kDSpinner1");
        this.kDSpinner2.setName("kDSpinner2");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel3.setName("kDLabel3");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // plChangeState		
        this.plChangeState.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plChangeState.border.title")));
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // radioNoAudit		
        this.radioNoAudit.setText(resHelper.getString("radioNoAudit.text"));
        // radioAudited		
        this.radioAudited.setText(resHelper.getString("radioAudited.text"));
        // radioStateAll		
        this.radioStateAll.setText(resHelper.getString("radioStateAll.text"));
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.radioNoAudit);
        this.kDButtonGroup2.add(this.radioAudited);
        this.kDButtonGroup2.add(this.radioStateAll);
        // btnCompanySelect		
        this.btnCompanySelect.setText(resHelper.getString("btnCompanySelect.text"));
        this.btnCompanySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCompanySelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSpinner1
        // kDSpinner2
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
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
        contCompany.setBounds(new Rectangle(10, 10, 270, 21));
        this.add(contCompany, null);
        contDateFrom.setBounds(new Rectangle(10, 37, 174, 19));
        this.add(contDateFrom, null);
        plChangeState.setBounds(new Rectangle(12, 75, 136, 99));
        this.add(plChangeState, null);
        btnCompanySelect.setBounds(new Rectangle(287, 10, 69, 21));
        this.add(btnCompanySelect, null);
        kDSpinner2.setBounds(new Rectangle(207, 37, 55, 19));
        this.add(kDSpinner2, null);
        kDLabel1.setBounds(new Rectangle(191, 37, 27, 19));
        this.add(kDLabel1, null);
        kDLabel3.setBounds(new Rectangle(267, 37, 28, 19));
        this.add(kDLabel3, null);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contDateFrom
        contDateFrom.setBoundEditor(kDSpinner1);
        //plChangeState
        plChangeState.setLayout(null);        radioNoAudit.setBounds(new Rectangle(24, 18, 64, 19));
        plChangeState.add(radioNoAudit, null);
        radioAudited.setBounds(new Rectangle(24, 43, 64, 19));
        plChangeState.add(radioAudited, null);
        radioStateAll.setBounds(new Rectangle(24, 68, 64, 19));
        plChangeState.add(radioStateAll, null);

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
	    return "com.kingdee.eas.fdc.finance.app.PayPlanFilterUIHandler";
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
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PayPlanFilterUI");
    }




}