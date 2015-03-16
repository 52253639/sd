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
public abstract class AbstractCustomerRptFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerRptFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEstablishWay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsInsider;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox EstablishWay;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleMan;
    protected EntityViewInfo queryCommonQuerySolutionQuery = null;
    protected IMetaDataPK queryCommonQuerySolutionQueryPK;
    /**
     * output class constructor
     */
    public AbstractCustomerRptFilterUI() throws Exception
    {
        super();
        this.defaultObjectName = "queryCommonQuerySolutionQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCustomerRptFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        queryCommonQuerySolutionQueryPK = new MetaDataPK("com.kingdee.eas.base.commonquery.app", "CommonQuerySolutionQuery");
        this.contCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEstablishWay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsChooseRoom = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsInsider = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contToCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.EstablishWay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSaleMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCustomerType.setName("contCustomerType");
        this.contEstablishWay.setName("contEstablishWay");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.chkIsChooseRoom.setName("chkIsChooseRoom");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.chkIsInsider.setName("chkIsInsider");
        this.contToCreateTime.setName("contToCreateTime");
        this.contToLastUpdateTime.setName("contToLastUpdateTime");
        this.contName.setName("contName");
        this.contPhone.setName("contPhone");
        this.contSaleMan.setName("contSaleMan");
        this.comboCustomerType.setName("comboCustomerType");
        this.EstablishWay.setName("EstablishWay");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.pkToCreateTime.setName("pkToCreateTime");
        this.pkToLastUpdateTime.setName("pkToLastUpdateTime");
        this.txtName.setName("txtName");
        this.txtPhone.setName("txtPhone");
        this.prmtSaleMan.setName("prmtSaleMan");
        // CustomerQueryPanel
        // contCustomerType		
        this.contCustomerType.setBoundLabelText(resHelper.getString("contCustomerType.boundLabelText"));		
        this.contCustomerType.setBoundLabelUnderline(true);		
        this.contCustomerType.setBoundLabelLength(100);
        // contEstablishWay		
        this.contEstablishWay.setBoundLabelText(resHelper.getString("contEstablishWay.boundLabelText"));		
        this.contEstablishWay.setBoundLabelUnderline(true);		
        this.contEstablishWay.setBoundLabelLength(100);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(100);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setBoundLabelLength(100);
        // chkIsChooseRoom		
        this.chkIsChooseRoom.setText(resHelper.getString("chkIsChooseRoom.text"));
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));
        // chkIsInsider		
        this.chkIsInsider.setText(resHelper.getString("chkIsInsider.text"));
        // contToCreateTime		
        this.contToCreateTime.setBoundLabelText(resHelper.getString("contToCreateTime.boundLabelText"));		
        this.contToCreateTime.setBoundLabelLength(100);		
        this.contToCreateTime.setBoundLabelUnderline(true);
        // contToLastUpdateTime		
        this.contToLastUpdateTime.setBoundLabelText(resHelper.getString("contToLastUpdateTime.boundLabelText"));		
        this.contToLastUpdateTime.setBoundLabelLength(100);		
        this.contToLastUpdateTime.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(100);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // comboCustomerType
        // EstablishWay
        // pkCreateTime
        // pkLastUpdateTime
        // pkToCreateTime
        // pkToLastUpdateTime
        // txtName
        // txtPhone
        // prmtSaleMan		
        this.prmtSaleMan.setCommitFormat("$name$");		
        this.prmtSaleMan.setDisplayFormat("$name$");		
        this.prmtSaleMan.setEditFormat("$name$");		
        this.prmtSaleMan.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.prmtSaleMan.setEnabledMultiSelection(true);
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
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        contCustomerType.setBounds(new Rectangle(17, 54, 270, 19));
        this.add(contCustomerType, null);
        contEstablishWay.setBounds(new Rectangle(316, 54, 270, 19));
        this.add(contEstablishWay, null);
        contCreateTime.setBounds(new Rectangle(17, 76, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateTime.setBounds(new Rectangle(17, 98, 270, 19));
        this.add(contLastUpdateTime, null);
        chkIsChooseRoom.setBounds(new Rectangle(17, 120, 140, 19));
        this.add(chkIsChooseRoom, null);
        chkIsEnabled.setBounds(new Rectangle(166, 120, 140, 19));
        this.add(chkIsEnabled, null);
        chkIsInsider.setBounds(new Rectangle(316, 120, 140, 19));
        this.add(chkIsInsider, null);
        contToCreateTime.setBounds(new Rectangle(316, 76, 270, 19));
        this.add(contToCreateTime, null);
        contToLastUpdateTime.setBounds(new Rectangle(316, 98, 270, 19));
        this.add(contToLastUpdateTime, null);
        contName.setBounds(new Rectangle(17, 10, 270, 19));
        this.add(contName, null);
        contPhone.setBounds(new Rectangle(316, 9, 270, 19));
        this.add(contPhone, null);
        contSaleMan.setBounds(new Rectangle(17, 32, 270, 19));
        this.add(contSaleMan, null);
        //contCustomerType
        contCustomerType.setBoundEditor(comboCustomerType);
        //contEstablishWay
        contEstablishWay.setBoundEditor(EstablishWay);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contToCreateTime
        contToCreateTime.setBoundEditor(pkToCreateTime);
        //contToLastUpdateTime
        contToLastUpdateTime.setBoundEditor(pkToLastUpdateTime);
        //contName
        contName.setBoundEditor(txtName);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contSaleMan
        contSaleMan.setBoundEditor(prmtSaleMan);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerRptFilterUIHandler";
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
        this.queryCommonQuerySolutionQuery = (EntityViewInfo)ov;
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerRptFilterUI");
    }




}