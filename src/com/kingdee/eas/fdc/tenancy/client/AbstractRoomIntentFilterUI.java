/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardRentFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardRentTo;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkUnTenancy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkWaitTenancy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkHavaTenancy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkSincerObligate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkKeepTenancy;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsIncludeMatrueRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMutrueDate;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMutrueDate;
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
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiFloorFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtRoomAreaFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardRentFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiFloorTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoomAreaTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardRentTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.chkUnTenancy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkWaitTenancy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkHavaTenancy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkSincerObligate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkKeepTenancy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkIsIncludeMatrueRoom = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contMutrueDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMutrueDate = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contRoomModelType.setName("contRoomModelType");
        this.contFace.setName("contFace");
        this.contFloor.setName("contFloor");
        this.contRoomArea.setName("contRoomArea");
        this.contStandardRent.setName("contStandardRent");
        this.spiFloorFrom.setName("spiFloorFrom");
        this.txtRoomAreaFrom.setName("txtRoomAreaFrom");
        this.txtStandardRentFrom.setName("txtStandardRentFrom");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.spiFloorTo.setName("spiFloorTo");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.f7RoomModelType.setName("f7RoomModelType");
        this.txtRoomAreaTo.setName("txtRoomAreaTo");
        this.txtStandardRentTo.setName("txtStandardRentTo");
        this.chkUnTenancy.setName("chkUnTenancy");
        this.chkWaitTenancy.setName("chkWaitTenancy");
        this.chkHavaTenancy.setName("chkHavaTenancy");
        this.chkSincerObligate.setName("chkSincerObligate");
        this.chkKeepTenancy.setName("chkKeepTenancy");
        this.f7Direction.setName("f7Direction");
        this.chkIsIncludeMatrueRoom.setName("chkIsIncludeMatrueRoom");
        this.contMutrueDate.setName("contMutrueDate");
        this.spiMutrueDate.setName("spiMutrueDate");
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
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelUnderline(true);		
        this.contRoomArea.setBoundLabelLength(100);
        // contStandardRent		
        this.contStandardRent.setBoundLabelText(resHelper.getString("contStandardRent.boundLabelText"));		
        this.contStandardRent.setBoundLabelLength(100);		
        this.contStandardRent.setBoundLabelUnderline(true);
        // spiFloorFrom
        // txtRoomAreaFrom
        // txtStandardRentFrom
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
        // txtRoomAreaTo
        // txtStandardRentTo
        // chkUnTenancy		
        this.chkUnTenancy.setText(resHelper.getString("chkUnTenancy.text"));
        // chkWaitTenancy		
        this.chkWaitTenancy.setText(resHelper.getString("chkWaitTenancy.text"));
        this.chkWaitTenancy.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkWaitTenancy_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkHavaTenancy		
        this.chkHavaTenancy.setText(resHelper.getString("chkHavaTenancy.text"));
        this.chkHavaTenancy.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkHavaTenancy_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkSincerObligate		
        this.chkSincerObligate.setText(resHelper.getString("chkSincerObligate.text"));
        // chkKeepTenancy		
        this.chkKeepTenancy.setText(resHelper.getString("chkKeepTenancy.text"));
        // f7Direction		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");		
        this.f7Direction.setDisplayFormat("$name$");		
        this.f7Direction.setEditFormat("$number$");		
        this.f7Direction.setCommitFormat("$number$");
        // chkIsIncludeMatrueRoom		
        this.chkIsIncludeMatrueRoom.setText(resHelper.getString("chkIsIncludeMatrueRoom.text"));
        this.chkIsIncludeMatrueRoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsIncludeMatrueRoom_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contMutrueDate		
        this.contMutrueDate.setBoundLabelText(resHelper.getString("contMutrueDate.boundLabelText"));		
        this.contMutrueDate.setBoundLabelLength(100);		
        this.contMutrueDate.setBoundLabelUnderline(true);
        // spiMutrueDate
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 400));
        contBuildingProperty.setBounds(new Rectangle(12, 10, 270, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(12, 10, 270, 19, 0));
        contRoomModelType.setBounds(new Rectangle(12, 38, 270, 19));
        this.add(contRoomModelType, new KDLayout.Constraints(12, 38, 270, 19, 0));
        contFace.setBounds(new Rectangle(12, 66, 270, 19));
        this.add(contFace, new KDLayout.Constraints(12, 66, 270, 19, 0));
        contFloor.setBounds(new Rectangle(12, 95, 170, 19));
        this.add(contFloor, new KDLayout.Constraints(12, 95, 170, 19, 0));
        contRoomArea.setBounds(new Rectangle(12, 125, 168, 19));
        this.add(contRoomArea, new KDLayout.Constraints(12, 125, 168, 19, 0));
        contStandardRent.setBounds(new Rectangle(12, 153, 168, 19));
        this.add(contStandardRent, new KDLayout.Constraints(12, 153, 168, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(192, 94, 90, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(192, 94, 90, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(192, 125, 90, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(192, 125, 90, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(192, 153, 90, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(192, 153, 90, 19, 0));
        chkUnTenancy.setBounds(new Rectangle(12, 197, 98, 19));
        this.add(chkUnTenancy, new KDLayout.Constraints(12, 197, 98, 19, 0));
        chkWaitTenancy.setBounds(new Rectangle(127, 197, 98, 19));
        this.add(chkWaitTenancy, new KDLayout.Constraints(127, 197, 98, 19, 0));
        chkHavaTenancy.setBounds(new Rectangle(251, 197, 98, 19));
        this.add(chkHavaTenancy, new KDLayout.Constraints(251, 197, 98, 19, 0));
        chkSincerObligate.setBounds(new Rectangle(12, 236, 98, 19));
        this.add(chkSincerObligate, new KDLayout.Constraints(12, 236, 98, 19, 0));
        chkKeepTenancy.setBounds(new Rectangle(128, 234, 98, 19));
        this.add(chkKeepTenancy, new KDLayout.Constraints(128, 234, 98, 19, 0));
        chkIsIncludeMatrueRoom.setBounds(new Rectangle(13, 276, 140, 19));
        this.add(chkIsIncludeMatrueRoom, new KDLayout.Constraints(13, 276, 140, 19, 0));
        contMutrueDate.setBounds(new Rectangle(13, 309, 270, 19));
        this.add(contMutrueDate, new KDLayout.Constraints(13, 309, 270, 19, 0));
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contRoomModelType
        contRoomModelType.setBoundEditor(f7RoomModelType);
        //contFace
        contFace.setBoundEditor(f7Direction);
        //contFloor
        contFloor.setBoundEditor(spiFloorFrom);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomAreaFrom);
        //contStandardRent
        contStandardRent.setBoundEditor(txtStandardRentFrom);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spiFloorTo);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtRoomAreaTo);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtStandardRentTo);
        //contMutrueDate
        contMutrueDate.setBoundEditor(spiMutrueDate);

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
	    return "com.kingdee.eas.fdc.tenancy.app.RoomIntentFilterUIHandler";
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
     * output chkWaitTenancy_stateChanged method
     */
    protected void chkWaitTenancy_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkHavaTenancy_stateChanged method
     */
    protected void chkHavaTenancy_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsIncludeMatrueRoom_stateChanged method
     */
    protected void chkIsIncludeMatrueRoom_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "RoomIntentFilterUI");
    }




}