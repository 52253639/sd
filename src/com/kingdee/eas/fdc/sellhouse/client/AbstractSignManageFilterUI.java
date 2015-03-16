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
public abstract class AbstractSignManageFilterUI extends com.kingdee.eas.fdc.sellhouse.client.BaseTransactionFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSignManageFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActBuildingAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActBuildingAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellAmountFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellAmountTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurbusAdscriptionDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurbusAdscriptionDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActBuildingAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActBuildingAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSellAmountFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSellAmountTo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPurbusAdscriptionDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPurbusAdscriptionDateTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgioFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgioTo;
    /**
     * output class constructor
     */
    public AbstractSignManageFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSignManageFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contActBuildingAreaFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActBuildingAreaTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellAmountFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellAmountTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurbusAdscriptionDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurbusAdscriptionDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtActBuildingAreaFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActBuildingAreaTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSellAmountFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSellAmountTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPurbusAdscriptionDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPurbusAdscriptionDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAgioFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAgioTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contActBuildingAreaFrom.setName("contActBuildingAreaFrom");
        this.contActBuildingAreaTo.setName("contActBuildingAreaTo");
        this.contSellAmountFrom.setName("contSellAmountFrom");
        this.contSellAmountTo.setName("contSellAmountTo");
        this.contPurbusAdscriptionDateFrom.setName("contPurbusAdscriptionDateFrom");
        this.contPurbusAdscriptionDateTo.setName("contPurbusAdscriptionDateTo");
        this.contAgioFrom.setName("contAgioFrom");
        this.contPayType.setName("contPayType");
        this.contAgioTo.setName("contAgioTo");
        this.txtActBuildingAreaFrom.setName("txtActBuildingAreaFrom");
        this.txtActBuildingAreaTo.setName("txtActBuildingAreaTo");
        this.txtSellAmountFrom.setName("txtSellAmountFrom");
        this.txtSellAmountTo.setName("txtSellAmountTo");
        this.pkPurbusAdscriptionDateFrom.setName("pkPurbusAdscriptionDateFrom");
        this.pkPurbusAdscriptionDateTo.setName("pkPurbusAdscriptionDateTo");
        this.txtAgioFrom.setName("txtAgioFrom");
        this.prmtPayType.setName("prmtPayType");
        this.txtAgioTo.setName("txtAgioTo");
        // CustomerQueryPanel		
        this.contSeller.setBoundLabelText(resHelper.getString("contSeller.boundLabelText"));
        // contActBuildingAreaFrom		
        this.contActBuildingAreaFrom.setBoundLabelText(resHelper.getString("contActBuildingAreaFrom.boundLabelText"));		
        this.contActBuildingAreaFrom.setBoundLabelLength(100);		
        this.contActBuildingAreaFrom.setBoundLabelUnderline(true);		
        this.contActBuildingAreaFrom.setEnabled(false);
        // contActBuildingAreaTo		
        this.contActBuildingAreaTo.setBoundLabelText(resHelper.getString("contActBuildingAreaTo.boundLabelText"));		
        this.contActBuildingAreaTo.setBoundLabelLength(100);		
        this.contActBuildingAreaTo.setBoundLabelUnderline(true);		
        this.contActBuildingAreaTo.setEnabled(false);
        // contSellAmountFrom		
        this.contSellAmountFrom.setBoundLabelText(resHelper.getString("contSellAmountFrom.boundLabelText"));		
        this.contSellAmountFrom.setBoundLabelLength(100);		
        this.contSellAmountFrom.setBoundLabelUnderline(true);		
        this.contSellAmountFrom.setEnabled(false);
        // contSellAmountTo		
        this.contSellAmountTo.setBoundLabelText(resHelper.getString("contSellAmountTo.boundLabelText"));		
        this.contSellAmountTo.setBoundLabelLength(100);		
        this.contSellAmountTo.setBoundLabelUnderline(true);		
        this.contSellAmountTo.setEnabled(false);
        // contPurbusAdscriptionDateFrom		
        this.contPurbusAdscriptionDateFrom.setBoundLabelText(resHelper.getString("contPurbusAdscriptionDateFrom.boundLabelText"));		
        this.contPurbusAdscriptionDateFrom.setBoundLabelUnderline(true);		
        this.contPurbusAdscriptionDateFrom.setBoundLabelLength(100);
        // contPurbusAdscriptionDateTo		
        this.contPurbusAdscriptionDateTo.setBoundLabelText(resHelper.getString("contPurbusAdscriptionDateTo.boundLabelText"));		
        this.contPurbusAdscriptionDateTo.setBoundLabelLength(100);		
        this.contPurbusAdscriptionDateTo.setBoundLabelUnderline(true);
        // contAgioFrom		
        this.contAgioFrom.setBoundLabelText(resHelper.getString("contAgioFrom.boundLabelText"));		
        this.contAgioFrom.setBoundLabelLength(100);		
        this.contAgioFrom.setBoundLabelUnderline(true);		
        this.contAgioFrom.setEnabled(false);
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);
        // contAgioTo		
        this.contAgioTo.setBoundLabelText(resHelper.getString("contAgioTo.boundLabelText"));		
        this.contAgioTo.setBoundLabelLength(100);		
        this.contAgioTo.setBoundLabelUnderline(true);		
        this.contAgioTo.setEnabled(false);
        // txtActBuildingAreaFrom		
        this.txtActBuildingAreaFrom.setDataType(1);		
        this.txtActBuildingAreaFrom.setPrecision(2);
        // txtActBuildingAreaTo		
        this.txtActBuildingAreaTo.setDataType(1);		
        this.txtActBuildingAreaTo.setPrecision(2);
        // txtSellAmountFrom		
        this.txtSellAmountFrom.setDataType(1);		
        this.txtSellAmountFrom.setPrecision(2);
        // txtSellAmountTo		
        this.txtSellAmountTo.setDataType(1);		
        this.txtSellAmountTo.setPrecision(2);
        // pkPurbusAdscriptionDateFrom
        // pkPurbusAdscriptionDateTo
        // txtAgioFrom		
        this.txtAgioFrom.setDataType(1);
        // prmtPayType		
        this.prmtPayType.setDisplayFormat("$name$");		
        this.prmtPayType.setEditFormat("$number$");		
        this.prmtPayType.setCommitFormat("$number$");		
        this.prmtPayType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");		
        this.prmtPayType.setEnabledMultiSelection(true);
        // txtAgioTo		
        this.txtAgioTo.setDataType(1);
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
        this.setBounds(new Rectangle(10, 10, 600, 365));
        this.setLayout(null);
        contRoomModel.setBounds(new Rectangle(310, 58, 270, 19));
        this.add(contRoomModel, null);
        contBizAdscriptionDateFrom.setBounds(new Rectangle(20, 190, 270, 19));
        this.add(contBizAdscriptionDateFrom, null);
        contBizAdscriptionDateTo.setBounds(new Rectangle(310, 190, 270, 19));
        this.add(contBizAdscriptionDateTo, null);
        contCustomer.setBounds(new Rectangle(20, 36, 270, 19));
        this.add(contCustomer, null);
        contPhone.setBounds(new Rectangle(310, 36, 270, 19));
        this.add(contPhone, null);
        contRoom.setBounds(new Rectangle(20, 14, 270, 19));
        this.add(contRoom, null);
        contChangeDateFrom.setBounds(new Rectangle(20, 234, 270, 19));
        this.add(contChangeDateFrom, null);
        contChangeDateTo.setBounds(new Rectangle(310, 234, 270, 19));
        this.add(contChangeDateTo, null);
        contProductType.setBounds(new Rectangle(20, 58, 270, 19));
        this.add(contProductType, null);
        contSeller.setBounds(new Rectangle(310, 168, 270, 19));
        this.add(contSeller, null);
        contBuildingAreaFrom.setBounds(new Rectangle(20, 80, 270, 19));
        this.add(contBuildingAreaFrom, null);
        contBuildingAreaTo.setBounds(new Rectangle(310, 80, 270, 19));
        this.add(contBuildingAreaTo, null);
        panelBizState.setBounds(new Rectangle(20, 257, 465, 103));
        this.add(panelBizState, null);
        contActBuildingAreaFrom.setBounds(new Rectangle(20, 102, 270, 19));
        this.add(contActBuildingAreaFrom, null);
        contActBuildingAreaTo.setBounds(new Rectangle(310, 102, 270, 19));
        this.add(contActBuildingAreaTo, null);
        contSellAmountFrom.setBounds(new Rectangle(20, 124, 270, 19));
        this.add(contSellAmountFrom, null);
        contSellAmountTo.setBounds(new Rectangle(310, 124, 270, 19));
        this.add(contSellAmountTo, null);
        contPurbusAdscriptionDateFrom.setBounds(new Rectangle(20, 212, 270, 19));
        this.add(contPurbusAdscriptionDateFrom, null);
        contPurbusAdscriptionDateTo.setBounds(new Rectangle(310, 212, 270, 19));
        this.add(contPurbusAdscriptionDateTo, null);
        contAgioFrom.setBounds(new Rectangle(20, 146, 270, 19));
        this.add(contAgioFrom, null);
        contPayType.setBounds(new Rectangle(20, 168, 270, 19));
        this.add(contPayType, null);
        contAgioTo.setBounds(new Rectangle(310, 146, 270, 19));
        this.add(contAgioTo, null);
        //contRoomModel
        contRoomModel.setBoundEditor(prmtRoomModel);
        //contBizAdscriptionDateFrom
        contBizAdscriptionDateFrom.setBoundEditor(pkBizAdscriptionDateFrom);
        //contBizAdscriptionDateTo
        contBizAdscriptionDateTo.setBoundEditor(pkBizAdscriptionDateTo);
        //contCustomer
        contCustomer.setBoundEditor(txtCustomer);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contRoom
        contRoom.setBoundEditor(txtRoom);
        //contChangeDateFrom
        contChangeDateFrom.setBoundEditor(pkChangeDateFrom);
        //contChangeDateTo
        contChangeDateTo.setBoundEditor(pkChangeDateTo);
        //contProductType
        contProductType.setBoundEditor(prmtProductType);
        //contSeller
        contSeller.setBoundEditor(prmtSeller);
        //contBuildingAreaFrom
        contBuildingAreaFrom.setBoundEditor(txtBuildingAreaFrom);
        //contBuildingAreaTo
        contBuildingAreaTo.setBoundEditor(txtBuildingAreaTo);
        //panelBizState
        panelBizState.setLayout(null);        rbNullify.setBounds(new Rectangle(17, 67, 55, 19));
        panelBizState.add(rbNullify, null);
        rbAuditted.setBounds(new Rectangle(88, 43, 55, 19));
        panelBizState.add(rbAuditted, null);
        rbSaved.setBounds(new Rectangle(17, 21, 55, 19));
        panelBizState.add(rbSaved, null);
        rbSubmitted.setBounds(new Rectangle(88, 21, 55, 19));
        panelBizState.add(rbSubmitted, null);
        rbAuditting.setBounds(new Rectangle(17, 44, 66, 19));
        panelBizState.add(rbAuditting, null);
        rbCNAuditting.setBounds(new Rectangle(168, 21, 84, 19));
        panelBizState.add(rbCNAuditting, null);
        rbQRAuditting.setBounds(new Rectangle(168, 44, 84, 19));
        panelBizState.add(rbQRAuditting, null);
        rbCRAuditting.setBounds(new Rectangle(168, 67, 84, 19));
        panelBizState.add(rbCRAuditting, null);
        rbCRNullify.setBounds(new Rectangle(271, 67, 84, 19));
        panelBizState.add(rbCRNullify, null);
        rbQRNullify.setBounds(new Rectangle(271, 44, 84, 19));
        panelBizState.add(rbQRNullify, null);
        rbCNNullify.setBounds(new Rectangle(271, 21, 84, 19));
        panelBizState.add(rbCNNullify, null);
        rbToPur.setBounds(new Rectangle(376, 23, 66, 19));
        panelBizState.add(rbToPur, null);
        rbToSign.setBounds(new Rectangle(376, 44, 66, 19));
        panelBizState.add(rbToSign, null);
        //contActBuildingAreaFrom
        contActBuildingAreaFrom.setBoundEditor(txtActBuildingAreaFrom);
        //contActBuildingAreaTo
        contActBuildingAreaTo.setBoundEditor(txtActBuildingAreaTo);
        //contSellAmountFrom
        contSellAmountFrom.setBoundEditor(txtSellAmountFrom);
        //contSellAmountTo
        contSellAmountTo.setBoundEditor(txtSellAmountTo);
        //contPurbusAdscriptionDateFrom
        contPurbusAdscriptionDateFrom.setBoundEditor(pkPurbusAdscriptionDateFrom);
        //contPurbusAdscriptionDateTo
        contPurbusAdscriptionDateTo.setBoundEditor(pkPurbusAdscriptionDateTo);
        //contAgioFrom
        contAgioFrom.setBoundEditor(txtAgioFrom);
        //contPayType
        contPayType.setBoundEditor(prmtPayType);
        //contAgioTo
        contAgioTo.setBoundEditor(txtAgioTo);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.SignManageFilterUIHandler";
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
     * output f7PayType_dataChanged method
     */
    protected void f7PayType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SignManageFilterUI");
    }




}