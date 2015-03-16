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
public abstract class AbstractRoomIntentFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomIntentFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardAmount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardAmountFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardAmountTo;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkInit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOnShow;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkPurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkSign;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkKeepDown;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Building;
    /**
     * output class constructor
     */
    public AbstractRoomIntentFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomIntentFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiFloorFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuildingAreaFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardAmountFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiFloorTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBuildingAreaTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardAmountTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.chkInit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOnShow = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkPrePurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkPurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkSign = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkKeepDown = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Building = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contRoomModelType.setName("contRoomModelType");
        this.contFace.setName("contFace");
        this.contFloor.setName("contFloor");
        this.contBuildingArea.setName("contBuildingArea");
        this.contStandardAmount.setName("contStandardAmount");
        this.spiFloorFrom.setName("spiFloorFrom");
        this.txtBuildingAreaFrom.setName("txtBuildingAreaFrom");
        this.txtStandardAmountFrom.setName("txtStandardAmountFrom");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.spiFloorTo.setName("spiFloorTo");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.f7RoomModelType.setName("f7RoomModelType");
        this.txtBuildingAreaTo.setName("txtBuildingAreaTo");
        this.txtStandardAmountTo.setName("txtStandardAmountTo");
        this.chkInit.setName("chkInit");
        this.chkOnShow.setName("chkOnShow");
        this.chkPrePurchase.setName("chkPrePurchase");
        this.chkPurchase.setName("chkPurchase");
        this.chkSign.setName("chkSign");
        this.chkKeepDown.setName("chkKeepDown");
        this.f7Direction.setName("f7Direction");
        this.contBuilding.setName("contBuilding");
        this.f7Building.setName("f7Building");
        // CustomerQueryPanel		
        this.setPreferredSize(new Dimension(400,400));
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // contRoomModelType		
        this.contRoomModelType.setBoundLabelText(resHelper.getString("contRoomModelType.boundLabelText"));		
        this.contRoomModelType.setBoundLabelLength(100);		
        this.contRoomModelType.setBoundLabelUnderline(true);
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelUnderline(true);		
        this.contFace.setBoundLabelLength(100);
        // contFloor		
        this.contFloor.setBoundLabelText(resHelper.getString("contFloor.boundLabelText"));		
        this.contFloor.setBoundLabelLength(100);		
        this.contFloor.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelUnderline(true);		
        this.contBuildingArea.setBoundLabelLength(100);
        // contStandardAmount		
        this.contStandardAmount.setBoundLabelText(resHelper.getString("contStandardAmount.boundLabelText"));		
        this.contStandardAmount.setBoundLabelLength(100);		
        this.contStandardAmount.setBoundLabelUnderline(true);
        // spiFloorFrom
        // txtBuildingAreaFrom
        // txtStandardAmountFrom
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(20);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(20);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(20);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // spiFloorTo
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setDisplayFormat("$name$");
        // f7RoomModelType		
        this.f7RoomModelType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");		
        this.f7RoomModelType.setDisplayFormat("$name$");		
        this.f7RoomModelType.setEditFormat("$number$");		
        this.f7RoomModelType.setCommitFormat("$number$");
        // txtBuildingAreaTo
        // txtStandardAmountTo
        // chkInit		
        this.chkInit.setText(resHelper.getString("chkInit.text"));
        // chkOnShow		
        this.chkOnShow.setText(resHelper.getString("chkOnShow.text"));
        // chkPrePurchase		
        this.chkPrePurchase.setText(resHelper.getString("chkPrePurchase.text"));
        // chkPurchase		
        this.chkPurchase.setText(resHelper.getString("chkPurchase.text"));
        // chkSign		
        this.chkSign.setText(resHelper.getString("chkSign.text"));
        // chkKeepDown		
        this.chkKeepDown.setText(resHelper.getString("chkKeepDown.text"));
        // f7Direction		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");		
        this.f7Direction.setDisplayFormat("$name$");		
        this.f7Direction.setEditFormat("$number$");		
        this.f7Direction.setCommitFormat("$number$");
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // f7Building		
        this.f7Building.setDisplayFormat("$name$");		
        this.f7Building.setEditFormat("$number$");		
        this.f7Building.setCommitFormat("$number$");		
        this.f7Building.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 400));
        contBuildingProperty.setBounds(new Rectangle(12, 40, 270, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(12, 40, 270, 19, 0));
        contRoomModelType.setBounds(new Rectangle(12, 68, 270, 19));
        this.add(contRoomModelType, new KDLayout.Constraints(12, 68, 270, 19, 0));
        contFace.setBounds(new Rectangle(12, 96, 270, 19));
        this.add(contFace, new KDLayout.Constraints(12, 96, 270, 19, 0));
        contFloor.setBounds(new Rectangle(12, 125, 170, 19));
        this.add(contFloor, new KDLayout.Constraints(12, 125, 170, 19, 0));
        contBuildingArea.setBounds(new Rectangle(12, 155, 168, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(12, 155, 168, 19, 0));
        contStandardAmount.setBounds(new Rectangle(12, 183, 168, 19));
        this.add(contStandardAmount, new KDLayout.Constraints(12, 183, 168, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(192, 125, 90, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(192, 125, 90, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(192, 155, 90, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(192, 155, 90, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(192, 183, 90, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(192, 183, 90, 19, 0));
        chkInit.setBounds(new Rectangle(12, 227, 98, 19));
        this.add(chkInit, new KDLayout.Constraints(12, 227, 98, 19, 0));
        chkOnShow.setBounds(new Rectangle(127, 227, 98, 19));
        this.add(chkOnShow, new KDLayout.Constraints(127, 227, 98, 19, 0));
        chkPrePurchase.setBounds(new Rectangle(251, 227, 98, 19));
        this.add(chkPrePurchase, new KDLayout.Constraints(251, 227, 98, 19, 0));
        chkPurchase.setBounds(new Rectangle(12, 266, 98, 19));
        this.add(chkPurchase, new KDLayout.Constraints(12, 266, 98, 19, 0));
        chkSign.setBounds(new Rectangle(127, 266, 98, 19));
        this.add(chkSign, new KDLayout.Constraints(127, 266, 98, 19, 0));
        chkKeepDown.setBounds(new Rectangle(251, 266, 98, 19));
        this.add(chkKeepDown, new KDLayout.Constraints(251, 266, 98, 19, 0));
        contBuilding.setBounds(new Rectangle(12, 10, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(12, 10, 270, 19, 0));
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contRoomModelType
        contRoomModelType.setBoundEditor(f7RoomModelType);
        //contFace
        contFace.setBoundEditor(f7Direction);
        //contFloor
        contFloor.setBoundEditor(spiFloorFrom);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingAreaFrom);
        //contStandardAmount
        contStandardAmount.setBoundEditor(txtStandardAmountFrom);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spiFloorTo);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtBuildingAreaTo);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtStandardAmountTo);
        //contBuilding
        contBuilding.setBoundEditor(f7Building);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomIntentFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomIntentFilterUI");
    }




}