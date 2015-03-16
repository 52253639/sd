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
public abstract class AbstractRptCommercialAnalyseFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptCommercialAnalyseFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton level;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton state;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton buildingPropery;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton productType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton direction;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton areaRequired;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton viewRequired;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton roomType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton productDetail;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton price;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton totalPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton reason;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton intension;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton form;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton floor;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton proportion;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton aim;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    /**
     * output class constructor
     */
    public AbstractRptCommercialAnalyseFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRptCommercialAnalyseFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.level = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.state = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.buildingPropery = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.productType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.direction = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.areaRequired = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.viewRequired = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.roomType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.productDetail = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.price = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.totalPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.reason = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.intension = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.form = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.floor = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.proportion = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.aim = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDPanel1.setName("kDPanel1");
        this.level.setName("level");
        this.state.setName("state");
        this.buildingPropery.setName("buildingPropery");
        this.productType.setName("productType");
        this.direction.setName("direction");
        this.areaRequired.setName("areaRequired");
        this.viewRequired.setName("viewRequired");
        this.roomType.setName("roomType");
        this.productDetail.setName("productDetail");
        this.price.setName("price");
        this.totalPrice.setName("totalPrice");
        this.reason.setName("reason");
        this.intension.setName("intension");
        this.form.setName("form");
        this.floor.setName("floor");
        this.proportion.setName("proportion");
        this.aim.setName("aim");
        // CustomerQueryPanel		
        this.setBorder(null);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // level		
        this.level.setText(resHelper.getString("level.text"));		
        this.level.setSelected(true);
        // state		
        this.state.setText(resHelper.getString("state.text"));
        // buildingPropery		
        this.buildingPropery.setText(resHelper.getString("buildingPropery.text"));
        // productType		
        this.productType.setText(resHelper.getString("productType.text"));
        // direction		
        this.direction.setText(resHelper.getString("direction.text"));
        // areaRequired		
        this.areaRequired.setText(resHelper.getString("areaRequired.text"));
        // viewRequired		
        this.viewRequired.setText(resHelper.getString("viewRequired.text"));
        // roomType		
        this.roomType.setText(resHelper.getString("roomType.text"));
        // productDetail		
        this.productDetail.setText(resHelper.getString("productDetail.text"));
        // price		
        this.price.setText(resHelper.getString("price.text"));
        // totalPrice		
        this.totalPrice.setText(resHelper.getString("totalPrice.text"));
        // reason		
        this.reason.setText(resHelper.getString("reason.text"));
        // intension		
        this.intension.setText(resHelper.getString("intension.text"));
        // form		
        this.form.setText(resHelper.getString("form.text"));
        // floor		
        this.floor.setText(resHelper.getString("floor.text"));
        // proportion		
        this.proportion.setText(resHelper.getString("proportion.text"));
        // aim		
        this.aim.setText(resHelper.getString("aim.text"));
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.level);
        this.kDButtonGroup1.add(this.state);
        this.kDButtonGroup1.add(this.buildingPropery);
        this.kDButtonGroup1.add(this.productType);
        this.kDButtonGroup1.add(this.direction);
        this.kDButtonGroup1.add(this.areaRequired);
        this.kDButtonGroup1.add(this.viewRequired);
        this.kDButtonGroup1.add(this.roomType);
        this.kDButtonGroup1.add(this.productDetail);
        this.kDButtonGroup1.add(this.price);
        this.kDButtonGroup1.add(this.totalPrice);
        this.kDButtonGroup1.add(this.reason);
        this.kDButtonGroup1.add(this.intension);
        this.kDButtonGroup1.add(this.form);
        this.kDButtonGroup1.add(this.floor);
        this.kDButtonGroup1.add(this.proportion);
        this.kDButtonGroup1.add(this.aim);
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
        this.setBounds(new Rectangle(10, 10, 515, 250));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 515, 250));
        kDPanel1.setBounds(new Rectangle(3, 2, 508, 243));
        this.add(kDPanel1, new KDLayout.Constraints(3, 2, 508, 243, 0));
        //kDPanel1
        kDPanel1.setLayout(null);        level.setBounds(new Rectangle(31, 19, 140, 19));
        kDPanel1.add(level, null);
        state.setBounds(new Rectangle(201, 19, 140, 19));
        kDPanel1.add(state, null);
        buildingPropery.setBounds(new Rectangle(363, 19, 140, 19));
        kDPanel1.add(buildingPropery, null);
        productType.setBounds(new Rectangle(31, 55, 140, 19));
        kDPanel1.add(productType, null);
        direction.setBounds(new Rectangle(201, 55, 140, 19));
        kDPanel1.add(direction, null);
        areaRequired.setBounds(new Rectangle(363, 55, 140, 19));
        kDPanel1.add(areaRequired, null);
        viewRequired.setBounds(new Rectangle(31, 91, 140, 19));
        kDPanel1.add(viewRequired, null);
        roomType.setBounds(new Rectangle(201, 91, 140, 19));
        kDPanel1.add(roomType, null);
        productDetail.setBounds(new Rectangle(363, 91, 140, 19));
        kDPanel1.add(productDetail, null);
        price.setBounds(new Rectangle(31, 127, 140, 19));
        kDPanel1.add(price, null);
        totalPrice.setBounds(new Rectangle(201, 127, 140, 19));
        kDPanel1.add(totalPrice, null);
        reason.setBounds(new Rectangle(363, 127, 140, 19));
        kDPanel1.add(reason, null);
        intension.setBounds(new Rectangle(31, 163, 140, 19));
        kDPanel1.add(intension, null);
        form.setBounds(new Rectangle(201, 163, 140, 19));
        kDPanel1.add(form, null);
        floor.setBounds(new Rectangle(363, 163, 140, 19));
        kDPanel1.add(floor, null);
        proportion.setBounds(new Rectangle(31, 201, 140, 19));
        kDPanel1.add(proportion, null);
        aim.setBounds(new Rectangle(201, 201, 140, 19));
        kDPanel1.add(aim, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RptCommercialAnalyseFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RptCommercialAnalyseFilterUI");
    }




}