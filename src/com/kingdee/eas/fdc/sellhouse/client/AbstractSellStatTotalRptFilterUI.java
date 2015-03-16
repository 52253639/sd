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
public abstract class AbstractSellStatTotalRptFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellStatTotalRptFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPrePurchaseIntoSaleStat;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsIncludeAttach;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbPreArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbActualArea;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    /**
     * output class constructor
     */
    public AbstractSellStatTotalRptFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSellStatTotalRptFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chkIsPrePurchaseIntoSaleStat = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsIncludeAttach = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.rbBuildArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbRoomArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbPreArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbActualArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.chkIsPrePurchaseIntoSaleStat.setName("chkIsPrePurchaseIntoSaleStat");
        this.chkIsIncludeAttach.setName("chkIsIncludeAttach");
        this.rbBuildArea.setName("rbBuildArea");
        this.rbRoomArea.setName("rbRoomArea");
        this.rbPreArea.setName("rbPreArea");
        this.rbActualArea.setName("rbActualArea");
        // CustomerQueryPanel
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // chkIsPrePurchaseIntoSaleStat		
        this.chkIsPrePurchaseIntoSaleStat.setText(resHelper.getString("chkIsPrePurchaseIntoSaleStat.text"));
        // chkIsIncludeAttach		
        this.chkIsIncludeAttach.setText(resHelper.getString("chkIsIncludeAttach.text"));
        // rbBuildArea		
        this.rbBuildArea.setText(resHelper.getString("rbBuildArea.text"));		
        this.rbBuildArea.setSelected(true);
        // rbRoomArea		
        this.rbRoomArea.setText(resHelper.getString("rbRoomArea.text"));
        // rbPreArea		
        this.rbPreArea.setText(resHelper.getString("rbPreArea.text"));		
        this.rbPreArea.setSelected(true);
        // rbActualArea		
        this.rbActualArea.setText(resHelper.getString("rbActualArea.text"));
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.rbBuildArea);
        this.kDButtonGroup1.add(this.rbRoomArea);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.rbPreArea);
        this.kDButtonGroup2.add(this.rbActualArea);
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
        kDPanel1.setBounds(new Rectangle(14, 144, 359, 91));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(14, 23, 358, 106));
        this.add(kDPanel2, null);
        //kDPanel1
        kDPanel1.setLayout(null);        chkIsPrePurchaseIntoSaleStat.setBounds(new Rectangle(23, 20, 270, 19));
        kDPanel1.add(chkIsPrePurchaseIntoSaleStat, null);
        chkIsIncludeAttach.setBounds(new Rectangle(23, 48, 270, 19));
        kDPanel1.add(chkIsIncludeAttach, null);
        //kDPanel2
        kDPanel2.setLayout(null);        rbBuildArea.setBounds(new Rectangle(19, 24, 140, 19));
        kDPanel2.add(rbBuildArea, null);
        rbRoomArea.setBounds(new Rectangle(184, 24, 140, 19));
        kDPanel2.add(rbRoomArea, null);
        rbPreArea.setBounds(new Rectangle(18, 60, 140, 19));
        kDPanel2.add(rbPreArea, null);
        rbActualArea.setBounds(new Rectangle(184, 59, 140, 19));
        kDPanel2.add(rbActualArea, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.SellStatTotalRptFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellStatTotalRptFilterUI");
    }




}