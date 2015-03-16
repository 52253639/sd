/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractRptFDCCustomerFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptFDCCustomerFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker bookedDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker bookedDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcSex;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboxSex;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField tradeTime;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbCustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbHabitationArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbReceptionType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbCustomerGrade;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbFamillyEarning;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbTradeTime;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbOwner;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbBooker;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbSex;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcIndustry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Industry;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIndustry;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbEnterpriceSize;
    /**
     * output class constructor
     */
    public AbstractRptFDCCustomerFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRptFDCCustomerFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bookedDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bookedDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcSex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboxSex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tradeTime = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.jrbCustomerOrigin = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbHabitationArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbReceptionType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbCustomerGrade = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbFamillyEarning = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbTradeTime = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbOwner = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbBooker = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbSex = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbProvince = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.lcIndustry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Industry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.rbIndustry = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbEnterpriceSize = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.bookedDateFrom.setName("bookedDateFrom");
        this.bookedDateTo.setName("bookedDateTo");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.lcSex.setName("lcSex");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.cbCustomerType.setName("cbCustomerType");
        this.comboxSex.setName("comboxSex");
        this.tradeTime.setName("tradeTime");
        this.jrbCustomerOrigin.setName("jrbCustomerOrigin");
        this.jrbHabitationArea.setName("jrbHabitationArea");
        this.jrbReceptionType.setName("jrbReceptionType");
        this.jrbCustomerGrade.setName("jrbCustomerGrade");
        this.jrbFamillyEarning.setName("jrbFamillyEarning");
        this.jrbTradeTime.setName("jrbTradeTime");
        this.jrbOwner.setName("jrbOwner");
        this.jrbBooker.setName("jrbBooker");
        this.jrbSex.setName("jrbSex");
        this.jrbProvince.setName("jrbProvince");
        this.lcIndustry.setName("lcIndustry");
        this.f7Industry.setName("f7Industry");
        this.rbIndustry.setName("rbIndustry");
        this.rbEnterpriceSize.setName("rbEnterpriceSize");
        // CustomerQueryPanel
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(50);
        // bookedDateFrom
        // bookedDateTo
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(50);
        // lcSex		
        this.lcSex.setBoundLabelText(resHelper.getString("lcSex.boundLabelText"));		
        this.lcSex.setBoundLabelLength(100);		
        this.lcSex.setBoundLabelUnderline(true);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.jrbCustomerOrigin);
        this.kDButtonGroup2.add(this.jrbHabitationArea);
        this.kDButtonGroup2.add(this.jrbReceptionType);
        this.kDButtonGroup2.add(this.jrbCustomerGrade);
        this.kDButtonGroup2.add(this.jrbFamillyEarning);
        this.kDButtonGroup2.add(this.jrbTradeTime);
        this.kDButtonGroup2.add(this.jrbOwner);
        this.kDButtonGroup2.add(this.jrbBooker);
        this.kDButtonGroup2.add(this.jrbSex);
        this.kDButtonGroup2.add(this.jrbProvince);
        this.kDButtonGroup2.add(this.rbIndustry);
        this.kDButtonGroup2.add(this.rbEnterpriceSize);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // cbCustomerType		
        this.cbCustomerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum").toArray());
        this.cbCustomerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbCustomerType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboxSex		
        this.comboxSex.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());
        // tradeTime		
        this.tradeTime.setDataType(7);
        // jrbCustomerOrigin		
        this.jrbCustomerOrigin.setText(resHelper.getString("jrbCustomerOrigin.text"));		
        this.jrbCustomerOrigin.setSelected(true);
        // jrbHabitationArea		
        this.jrbHabitationArea.setText(resHelper.getString("jrbHabitationArea.text"));
        // jrbReceptionType		
        this.jrbReceptionType.setText(resHelper.getString("jrbReceptionType.text"));
        // jrbCustomerGrade		
        this.jrbCustomerGrade.setText(resHelper.getString("jrbCustomerGrade.text"));
        // jrbFamillyEarning		
        this.jrbFamillyEarning.setText(resHelper.getString("jrbFamillyEarning.text"));
        // jrbTradeTime		
        this.jrbTradeTime.setText(resHelper.getString("jrbTradeTime.text"));
        // jrbOwner		
        this.jrbOwner.setText(resHelper.getString("jrbOwner.text"));
        // jrbBooker		
        this.jrbBooker.setText(resHelper.getString("jrbBooker.text"));
        // jrbSex		
        this.jrbSex.setText(resHelper.getString("jrbSex.text"));
        // jrbProvince		
        this.jrbProvince.setText(resHelper.getString("jrbProvince.text"));
        this.jrbProvince.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    jrbProvince_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // lcIndustry		
        this.lcIndustry.setBoundLabelText(resHelper.getString("lcIndustry.boundLabelText"));		
        this.lcIndustry.setBoundLabelLength(100);		
        this.lcIndustry.setBoundLabelUnderline(true);
        // f7Industry		
        this.f7Industry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.IndustryQuery");		
        this.f7Industry.setDisplayFormat("$name$");		
        this.f7Industry.setEditFormat("$number$");		
        this.f7Industry.setCommitFormat("$number$");
        // rbIndustry		
        this.rbIndustry.setText(resHelper.getString("rbIndustry.text"));
        // rbEnterpriceSize		
        this.rbEnterpriceSize.setText(resHelper.getString("rbEnterpriceSize.text"));
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
        this.setBounds(new Rectangle(10, 10, 600, 360));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 360));
        kDPanel1.setBounds(new Rectangle(0, 136, 599, 219));
        this.add(kDPanel1, new KDLayout.Constraints(0, 136, 599, 219, 0));
        kDPanel2.setBounds(new Rectangle(1, 79, 598, 58));
        this.add(kDPanel2, new KDLayout.Constraints(1, 79, 598, 58, 0));
        lcSex.setBounds(new Rectangle(314, 13, 270, 19));
        this.add(lcSex, new KDLayout.Constraints(314, 13, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(14, 49, 270, 18));
        this.add(kDLabelContainer9, new KDLayout.Constraints(14, 49, 270, 18, 0));
        kDLabelContainer3.setBounds(new Rectangle(13, 13, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(13, 13, 270, 19, 0));
        lcIndustry.setBounds(new Rectangle(314, 13, 270, 19));
        this.add(lcIndustry, new KDLayout.Constraints(314, 13, 270, 19, 0));
        //kDPanel1
        kDPanel1.setLayout(null);        jrbCustomerOrigin.setBounds(new Rectangle(28, 23, 76, 19));
        kDPanel1.add(jrbCustomerOrigin, null);
        jrbHabitationArea.setBounds(new Rectangle(27, 74, 77, 19));
        kDPanel1.add(jrbHabitationArea, null);
        jrbReceptionType.setBounds(new Rectangle(265, 23, 71, 19));
        kDPanel1.add(jrbReceptionType, null);
        jrbCustomerGrade.setBounds(new Rectangle(497, 23, 76, 19));
        kDPanel1.add(jrbCustomerGrade, null);
        jrbFamillyEarning.setBounds(new Rectangle(264, 74, 68, 19));
        kDPanel1.add(jrbFamillyEarning, null);
        jrbTradeTime.setBounds(new Rectangle(496, 74, 73, 19));
        kDPanel1.add(jrbTradeTime, null);
        jrbOwner.setBounds(new Rectangle(26, 125, 92, 19));
        kDPanel1.add(jrbOwner, null);
        jrbBooker.setBounds(new Rectangle(264, 125, 64, 19));
        kDPanel1.add(jrbBooker, null);
        jrbSex.setBounds(new Rectangle(496, 125, 52, 19));
        kDPanel1.add(jrbSex, null);
        jrbProvince.setBounds(new Rectangle(26, 176, 54, 19));
        kDPanel1.add(jrbProvince, null);
        rbIndustry.setBounds(new Rectangle(496, 125, 87, 19));
        kDPanel1.add(rbIndustry, null);
        rbEnterpriceSize.setBounds(new Rectangle(264, 74, 140, 19));
        kDPanel1.add(rbEnterpriceSize, null);
        //kDPanel2
        kDPanel2.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(259, 21, 52, 19));
        kDPanel2.add(kDLabelContainer1, null);
        bookedDateFrom.setBounds(new Rectangle(68, 22, 170, 19));
        kDPanel2.add(bookedDateFrom, null);
        bookedDateTo.setBounds(new Rectangle(310, 21, 170, 19));
        kDPanel2.add(bookedDateTo, null);
        kDLabelContainer2.setBounds(new Rectangle(17, 22, 53, 19));
        kDPanel2.add(kDLabelContainer2, null);
        //lcSex
        lcSex.setBoundEditor(comboxSex);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(tradeTime);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(cbCustomerType);
        //lcIndustry
        lcIndustry.setBoundEditor(f7Industry);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RptFDCCustomerFilterUIHandler";
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
     * output cbCustomerType_itemStateChanged method
     */
    protected void cbCustomerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output jrbProvince_actionPerformed method
     */
    protected void jrbProvince_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RptFDCCustomerFilterUI");
    }




}