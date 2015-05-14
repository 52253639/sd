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
public abstract class AbstractOrgUnitMonthPGReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOrgUnitMonthPGReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYear;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    /**
     * output class constructor
     */
    public AbstractOrgUnitMonthPGReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOrgUnitMonthPGReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contYear.setName("contYear");
        this.kDLabel1.setName("kDLabel1");
        this.spMonth.setName("spMonth");
        this.kDLabel2.setName("kDLabel2");
        this.spYear.setName("spYear");
        // CustomerQueryPanel
        // contYear		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(100);		
        this.contYear.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // spMonth
        this.spMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spMonth_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // spYear
        this.spYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spYear_stateChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 600, 120));
        this.setLayout(null);
        contYear.setBounds(new Rectangle(136, 44, 189, 19));
        this.add(contYear, null);
        kDLabel1.setBounds(new Rectangle(330, 44, 15, 19));
        this.add(kDLabel1, null);
        spMonth.setBounds(new Rectangle(350, 44, 40, 19));
        this.add(spMonth, null);
        kDLabel2.setBounds(new Rectangle(394, 44, 15, 19));
        this.add(kDLabel2, null);
        //contYear
        contYear.setBoundEditor(spYear);

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
	    return "com.kingdee.eas.fdc.finance.app.OrgUnitMonthPGReportFilterUIHandler";
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
     * output spMonth_stateChanged method
     */
    protected void spMonth_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spYear_stateChanged method
     */
    protected void spYear_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "OrgUnitMonthPGReportFilterUI");
    }




}