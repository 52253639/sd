/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

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
public abstract class AbstractMarketAccountViewReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketAccountViewReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDRadioButton yearRadioButton;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton monthRadioButton;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinBeginYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinEndYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinBeginMonth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinEndMonth;
    /**
     * output class constructor
     */
    public AbstractMarketAccountViewReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketAccountViewReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.yearRadioButton = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.monthRadioButton = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spinBeginYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinEndYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinBeginMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinEndMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.yearRadioButton.setName("yearRadioButton");
        this.monthRadioButton.setName("monthRadioButton");
        this.kDPanel1.setName("kDPanel1");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.spinBeginYear.setName("spinBeginYear");
        this.spinEndYear.setName("spinEndYear");
        this.spinBeginMonth.setName("spinBeginMonth");
        this.spinEndMonth.setName("spinEndMonth");
        // CustomerQueryPanel
        // yearRadioButton		
        this.yearRadioButton.setText(resHelper.getString("yearRadioButton.text"));
        this.yearRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    yearRadioButton_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // monthRadioButton		
        this.monthRadioButton.setText(resHelper.getString("monthRadioButton.text"));
        this.monthRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    monthRadioButton_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.yearRadioButton);
        this.kDButtonGroup1.add(this.monthRadioButton);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(20);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(20);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(20);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(20);
        // spinBeginYear
        // spinEndYear
        // spinBeginMonth
        // spinEndMonth
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
        this.setBounds(new Rectangle(10, 10, 556, 359));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 556, 359));
        yearRadioButton.setBounds(new Rectangle(26, 29, 90, 19));
        this.add(yearRadioButton, new KDLayout.Constraints(26, 29, 90, 19, 0));
        monthRadioButton.setBounds(new Rectangle(145, 30, 97, 19));
        this.add(monthRadioButton, new KDLayout.Constraints(145, 30, 97, 19, 0));
        kDPanel1.setBounds(new Rectangle(23, 59, 488, 95));
        this.add(kDPanel1, new KDLayout.Constraints(23, 59, 488, 95, 0));
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabel1.setBounds(new Rectangle(20, 26, 54, 19));
        kDPanel1.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(20, 56, 54, 19));
        kDPanel1.add(kDLabel2, null);
        kDLabelContainer1.setBounds(new Rectangle(81, 26, 115, 19));
        kDPanel1.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(81, 56, 115, 19));
        kDPanel1.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(224, 26, 91, 19));
        kDPanel1.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(224, 56, 91, 19));
        kDPanel1.add(kDLabelContainer4, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spinBeginYear);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(spinEndYear);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(spinBeginMonth);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(spinEndMonth);

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
	    return "com.kingdee.eas.fdc.market.report.MarketAccountViewReportFilterUIHandler";
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
     * output yearRadioButton_actionPerformed method
     */
    protected void yearRadioButton_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output monthRadioButton_actionPerformed method
     */
    protected void monthRadioButton_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.report", "MarketAccountViewReportFilterUI");
    }




}