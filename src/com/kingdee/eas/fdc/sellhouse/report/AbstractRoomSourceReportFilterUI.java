/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

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
public abstract class AbstractRoomSourceReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomSourceReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbSincerPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsellState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contproductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomMode;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbInit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbOnshow;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbKeepDown;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbPurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbSign;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbControl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7productType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomMode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorTo;
    /**
     * output class constructor
     */
    public AbstractRoomSourceReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomSourceReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.cbSincerPurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contsellState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contproductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbInit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbOnshow = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbKeepDown = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbPrePurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbPurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbSign = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7productType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoomMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFloorFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFloorTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbSincerPurchase.setName("cbSincerPurchase");
        this.contsellState.setName("contsellState");
        this.contproductType.setName("contproductType");
        this.contRoomMode.setName("contRoomMode");
        this.cbInit.setName("cbInit");
        this.cbOnshow.setName("cbOnshow");
        this.cbKeepDown.setName("cbKeepDown");
        this.cbPrePurchase.setName("cbPrePurchase");
        this.cbPurchase.setName("cbPurchase");
        this.cbSign.setName("cbSign");
        this.cbControl.setName("cbControl");
        this.contBuilding.setName("contBuilding");
        this.contFloorFrom.setName("contFloorFrom");
        this.contFloorTo.setName("contFloorTo");
        this.f7productType.setName("f7productType");
        this.prmtRoomMode.setName("prmtRoomMode");
        this.prmtBuilding.setName("prmtBuilding");
        this.txtFloorFrom.setName("txtFloorFrom");
        this.txtFloorTo.setName("txtFloorTo");
        // CustomerQueryPanel
        // cbSincerPurchase		
        this.cbSincerPurchase.setText(resHelper.getString("cbSincerPurchase.text"));
        // contsellState		
        this.contsellState.setBoundLabelText(resHelper.getString("contsellState.boundLabelText"));		
        this.contsellState.setBoundLabelLength(100);
        // contproductType		
        this.contproductType.setBoundLabelText(resHelper.getString("contproductType.boundLabelText"));		
        this.contproductType.setBoundLabelLength(100);		
        this.contproductType.setBoundLabelUnderline(true);
        // contRoomMode		
        this.contRoomMode.setBoundLabelText(resHelper.getString("contRoomMode.boundLabelText"));		
        this.contRoomMode.setBoundLabelUnderline(true);		
        this.contRoomMode.setBoundLabelLength(100);
        // cbInit		
        this.cbInit.setText(resHelper.getString("cbInit.text"));
        // cbOnshow		
        this.cbOnshow.setText(resHelper.getString("cbOnshow.text"));
        // cbKeepDown		
        this.cbKeepDown.setText(resHelper.getString("cbKeepDown.text"));
        // cbPrePurchase		
        this.cbPrePurchase.setText(resHelper.getString("cbPrePurchase.text"));
        // cbPurchase		
        this.cbPurchase.setText(resHelper.getString("cbPurchase.text"));
        // cbSign		
        this.cbSign.setText(resHelper.getString("cbSign.text"));
        // cbControl		
        this.cbControl.setText(resHelper.getString("cbControl.text"));
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contFloorFrom		
        this.contFloorFrom.setBoundLabelText(resHelper.getString("contFloorFrom.boundLabelText"));		
        this.contFloorFrom.setBoundLabelLength(100);		
        this.contFloorFrom.setBoundLabelUnderline(true);
        // contFloorTo		
        this.contFloorTo.setBoundLabelText(resHelper.getString("contFloorTo.boundLabelText"));		
        this.contFloorTo.setBoundLabelLength(100);		
        this.contFloorTo.setBoundLabelUnderline(true);
        // f7productType		
        this.f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.f7productType.setEnabledMultiSelection(true);		
        this.f7productType.setDisplayFormat("$name$");		
        this.f7productType.setEditFormat("$name$");		
        this.f7productType.setCommitFormat("$name$");
        // prmtRoomMode		
        this.prmtRoomMode.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");		
        this.prmtRoomMode.setCommitFormat("$name$");		
        this.prmtRoomMode.setDisplayFormat("$name$");		
        this.prmtRoomMode.setEditFormat("$name$");		
        this.prmtRoomMode.setEnabledMultiSelection(true);
        // prmtBuilding		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingNewQuery");		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$name$");		
        this.prmtBuilding.setCommitFormat("$name$");		
        this.prmtBuilding.setEnabledMultiSelection(true);
        // txtFloorFrom
        // txtFloorTo
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
        this.setBounds(new Rectangle(10, 10, 500, 200));
        this.setLayout(null);
        cbSincerPurchase.setBounds(new Rectangle(13, 171, 81, 19));
        this.add(cbSincerPurchase, null);
        contsellState.setBounds(new Rectangle(13, 103, 61, 19));
        this.add(contsellState, null);
        contproductType.setBounds(new Rectangle(13, 9, 468, 19));
        this.add(contproductType, null);
        contRoomMode.setBounds(new Rectangle(13, 31, 468, 19));
        this.add(contRoomMode, null);
        cbInit.setBounds(new Rectangle(13, 128, 69, 19));
        this.add(cbInit, null);
        cbOnshow.setBounds(new Rectangle(101, 128, 69, 19));
        this.add(cbOnshow, null);
        cbKeepDown.setBounds(new Rectangle(13, 149, 69, 19));
        this.add(cbKeepDown, null);
        cbPrePurchase.setBounds(new Rectangle(101, 171, 69, 19));
        this.add(cbPrePurchase, null);
        cbPurchase.setBounds(new Rectangle(176, 171, 69, 19));
        this.add(cbPurchase, null);
        cbSign.setBounds(new Rectangle(247, 171, 69, 19));
        this.add(cbSign, null);
        cbControl.setBounds(new Rectangle(101, 149, 69, 19));
        this.add(cbControl, null);
        contBuilding.setBounds(new Rectangle(13, 53, 468, 19));
        this.add(contBuilding, null);
        contFloorFrom.setBounds(new Rectangle(13, 75, 228, 19));
        this.add(contFloorFrom, null);
        contFloorTo.setBounds(new Rectangle(253, 75, 228, 19));
        this.add(contFloorTo, null);
        //contproductType
        contproductType.setBoundEditor(f7productType);
        //contRoomMode
        contRoomMode.setBoundEditor(prmtRoomMode);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //contFloorFrom
        contFloorFrom.setBoundEditor(txtFloorFrom);
        //contFloorTo
        contFloorTo.setBoundEditor(txtFloorTo);

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
	    return "com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "RoomSourceReportFilterUI");
    }




}