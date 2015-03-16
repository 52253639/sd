/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractMonthSellReportFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMonthSellReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPrePurchaseIntoSaleStat;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsIncludeAttach;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioProductType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioBuilding;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup groupShowType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowSpecial;
    /**
     * output class constructor
     */
    public AbstractMonthSellReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMonthSellReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chkIsPrePurchaseIntoSaleStat = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsIncludeAttach = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.radioProductType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioBuilding = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.groupShowType = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.chkIsShowSpecial = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.spiYearFrom.setName("spiYearFrom");
        this.contYearFrom.setName("contYearFrom");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblYearFrom.setName("lblYearFrom");
        this.kDPanel1.setName("kDPanel1");
        this.chkIsPrePurchaseIntoSaleStat.setName("chkIsPrePurchaseIntoSaleStat");
        this.chkIsIncludeAttach.setName("chkIsIncludeAttach");
        this.kDPanel2.setName("kDPanel2");
        this.radioProductType.setName("radioProductType");
        this.radioBuilding.setName("radioBuilding");
        this.chkIsShowSpecial.setName("chkIsShowSpecial");
        // CustomerQueryPanel
        // spiYearFrom
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // spiMonthFrom
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // chkIsPrePurchaseIntoSaleStat		
        this.chkIsPrePurchaseIntoSaleStat.setText(resHelper.getString("chkIsPrePurchaseIntoSaleStat.text"));
        // chkIsIncludeAttach		
        this.chkIsIncludeAttach.setText(resHelper.getString("chkIsIncludeAttach.text"));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // radioProductType		
        this.radioProductType.setText(resHelper.getString("radioProductType.text"));		
        this.radioProductType.setSelected(true);
        // radioBuilding		
        this.radioBuilding.setText(resHelper.getString("radioBuilding.text"));
        // groupShowType
        this.groupShowType.add(this.radioProductType);
        this.groupShowType.add(this.radioBuilding);
        // chkIsShowSpecial		
        this.chkIsShowSpecial.setText(resHelper.getString("chkIsShowSpecial.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 400));
        this.setLayout(null);
        contYearFrom.setBounds(new Rectangle(15, 28, 180, 19));
        this.add(contYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(218, 28, 49, 19));
        this.add(spiMonthFrom, null);
        lblMonthFrom.setBounds(new Rectangle(279, 28, 29, 19));
        this.add(lblMonthFrom, null);
        lblYearFrom.setBounds(new Rectangle(200, 28, 29, 19));
        this.add(lblYearFrom, null);
        kDPanel1.setBounds(new Rectangle(14, 154, 359, 121));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(14, 69, 359, 80));
        this.add(kDPanel2, null);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //kDPanel1
        kDPanel1.setLayout(null);        chkIsPrePurchaseIntoSaleStat.setBounds(new Rectangle(20, 17, 270, 19));
        kDPanel1.add(chkIsPrePurchaseIntoSaleStat, null);
        chkIsIncludeAttach.setBounds(new Rectangle(21, 46, 270, 19));
        kDPanel1.add(chkIsIncludeAttach, null);
        chkIsShowSpecial.setBounds(new Rectangle(21, 75, 220, 19));
        kDPanel1.add(chkIsShowSpecial, null);
        //kDPanel2
        kDPanel2.setLayout(null);        radioProductType.setBounds(new Rectangle(34, 29, 106, 19));
        kDPanel2.add(radioProductType, null);
        radioBuilding.setBounds(new Rectangle(191, 29, 99, 19));
        kDPanel2.add(radioBuilding, null);

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
	    return "com.kingdee.eas.fdc.market.app.MonthSellReportFilterUIHandler";
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MonthSellReportFilterUI");
    }




}