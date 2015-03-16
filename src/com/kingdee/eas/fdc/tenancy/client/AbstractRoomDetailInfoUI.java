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
public abstract class AbstractRoomDetailInfoUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomDetailInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabBizInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardRentType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStandardRentType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTenancyArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomInfo;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomDetailInfoUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomDetailInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.tabBizInfo = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardRentType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomInfo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboTenancyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboStandardRentType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStandardRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTenancyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomInfo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.contFace.setName("contFace");
        this.contOutlook.setName("contOutlook");
        this.contRoomModel.setName("contRoomModel");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contHouseProperty.setName("contHouseProperty");
        this.kDSeparator5.setName("kDSeparator5");
        this.tabBizInfo.setName("tabBizInfo");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contRoomState.setName("contRoomState");
        this.contStandardRentType.setName("contStandardRentType");
        this.contStandardRent.setName("contStandardRent");
        this.contTenancyArea.setName("contTenancyArea");
        this.contRoomInfo.setName("contRoomInfo");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.f7Direction.setName("f7Direction");
        this.f7Sight.setName("f7Sight");
        this.f7RoomModel.setName("f7RoomModel");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.comboTenancyState.setName("comboTenancyState");
        this.comboStandardRentType.setName("comboStandardRentType");
        this.txtStandardRent.setName("txtStandardRent");
        this.txtTenancyArea.setName("txtTenancyArea");
        this.txtRoomInfo.setName("txtRoomInfo");
        // CoreUI
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(50);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(50);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelLength(50);		
        this.contApportionArea.setBoundLabelUnderline(true);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(50);		
        this.contBalconyArea.setBoundLabelUnderline(true);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(50);		
        this.contFloorHeight.setBoundLabelUnderline(true);
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelLength(50);		
        this.contFace.setBoundLabelUnderline(true);
        // contOutlook		
        this.contOutlook.setBoundLabelText(resHelper.getString("contOutlook.boundLabelText"));		
        this.contOutlook.setBoundLabelLength(50);		
        this.contOutlook.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(50);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(50);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelLength(50);		
        this.contHouseProperty.setBoundLabelUnderline(true);
        // kDSeparator5
        // tabBizInfo
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelLength(50);		
        this.contActualBuildingArea.setBoundLabelUnderline(true);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(50);
        // contRoomState		
        this.contRoomState.setBoundLabelText(resHelper.getString("contRoomState.boundLabelText"));		
        this.contRoomState.setBoundLabelLength(50);		
        this.contRoomState.setBoundLabelUnderline(true);
        // contStandardRentType		
        this.contStandardRentType.setBoundLabelText(resHelper.getString("contStandardRentType.boundLabelText"));		
        this.contStandardRentType.setBoundLabelLength(50);		
        this.contStandardRentType.setBoundLabelUnderline(true);
        // contStandardRent		
        this.contStandardRent.setBoundLabelText(resHelper.getString("contStandardRent.boundLabelText"));		
        this.contStandardRent.setBoundLabelLength(50);		
        this.contStandardRent.setBoundLabelUnderline(true);
        // contTenancyArea		
        this.contTenancyArea.setBoundLabelText(resHelper.getString("contTenancyArea.boundLabelText"));		
        this.contTenancyArea.setBoundLabelLength(50);		
        this.contTenancyArea.setBoundLabelUnderline(true);
        // contRoomInfo		
        this.contRoomInfo.setBoundLabelText(resHelper.getString("contRoomInfo.boundLabelText"));		
        this.contRoomInfo.setBoundLabelLength(50);		
        this.contRoomInfo.setBoundLabelUnderline(true);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);
        // txtApportionArea		
        this.txtApportionArea.setDataType(1);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);
        // txtFloorHeight		
        this.txtFloorHeight.setDataType(1);
        // f7Direction		
        this.f7Direction.setDisplayFormat("$name$");		
        this.f7Direction.setEditFormat("$number$");		
        this.f7Direction.setCommitFormat("$number$");		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // f7Sight		
        this.f7Sight.setCommitFormat("$number$");		
        this.f7Sight.setDisplayFormat("$name$");		
        this.f7Sight.setEditFormat("$number$");		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setCommitFormat("$number$");		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setEditFormat("$number$");
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setCommitFormat("$number$");
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // comboTenancyState		
        this.comboTenancyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.TenancyStateEnum").toArray());
        // comboStandardRentType		
        this.comboStandardRentType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.RentTypeEnum").toArray());
        // txtStandardRent		
        this.txtStandardRent.setDataType(1);
        // txtTenancyArea		
        this.txtTenancyArea.setDataType(1);
        // txtRoomInfo		
        this.txtRoomInfo.setHorizontalAlignment(4);
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
        this.setBounds(new Rectangle(10, 10, 320, 659));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 320, 659));
        contBuildingArea.setBounds(new Rectangle(11, 40, 138, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(11, 40, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomArea.setBounds(new Rectangle(167, 42, 138, 19));
        this.add(contRoomArea, new KDLayout.Constraints(167, 42, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApportionArea.setBounds(new Rectangle(10, 89, 138, 19));
        this.add(contApportionArea, new KDLayout.Constraints(10, 89, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBalconyArea.setBounds(new Rectangle(167, 91, 138, 19));
        this.add(contBalconyArea, new KDLayout.Constraints(167, 91, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFloorHeight.setBounds(new Rectangle(10, 152, 138, 19));
        this.add(contFloorHeight, new KDLayout.Constraints(10, 152, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFace.setBounds(new Rectangle(167, 152, 138, 19));
        this.add(contFace, new KDLayout.Constraints(167, 152, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOutlook.setBounds(new Rectangle(10, 177, 138, 19));
        this.add(contOutlook, new KDLayout.Constraints(10, 177, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(167, 177, 138, 19));
        this.add(contRoomModel, new KDLayout.Constraints(167, 177, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingProperty.setBounds(new Rectangle(10, 201, 138, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(10, 201, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHouseProperty.setBounds(new Rectangle(167, 201, 138, 19));
        this.add(contHouseProperty, new KDLayout.Constraints(167, 201, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator5.setBounds(new Rectangle(7, 146, 308, 10));
        this.add(kDSeparator5, new KDLayout.Constraints(7, 146, 308, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabBizInfo.setBounds(new Rectangle(3, 282, 314, 364));
        this.add(tabBizInfo, new KDLayout.Constraints(3, 282, 314, 364, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contActualBuildingArea.setBounds(new Rectangle(10, 65, 138, 19));
        this.add(contActualBuildingArea, new KDLayout.Constraints(10, 65, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActualRoomArea.setBounds(new Rectangle(167, 67, 138, 19));
        this.add(contActualRoomArea, new KDLayout.Constraints(167, 67, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomState.setBounds(new Rectangle(167, 252, 138, 19));
        this.add(contRoomState, new KDLayout.Constraints(167, 252, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStandardRentType.setBounds(new Rectangle(167, 227, 138, 19));
        this.add(contStandardRentType, new KDLayout.Constraints(167, 227, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStandardRent.setBounds(new Rectangle(10, 227, 138, 19));
        this.add(contStandardRent, new KDLayout.Constraints(10, 227, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTenancyArea.setBounds(new Rectangle(10, 118, 138, 19));
        this.add(contTenancyArea, new KDLayout.Constraints(10, 118, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomInfo.setBounds(new Rectangle(13, 14, 293, 17));
        this.add(contRoomInfo, new KDLayout.Constraints(13, 14, 293, 17, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contApportionArea
        contApportionArea.setBoundEditor(txtApportionArea);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtBalconyArea);
        //contFloorHeight
        contFloorHeight.setBoundEditor(txtFloorHeight);
        //contFace
        contFace.setBoundEditor(f7Direction);
        //contOutlook
        contOutlook.setBoundEditor(f7Sight);
        //contRoomModel
        contRoomModel.setBoundEditor(f7RoomModel);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contRoomState
        contRoomState.setBoundEditor(comboTenancyState);
        //contStandardRentType
        contStandardRentType.setBoundEditor(comboStandardRentType);
        //contStandardRent
        contStandardRent.setBoundEditor(txtStandardRent);
        //contTenancyArea
        contTenancyArea.setBoundEditor(txtTenancyArea);
        //contRoomInfo
        contRoomInfo.setBoundEditor(txtRoomInfo);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.RoomDetailInfoUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "RoomDetailInfoUI");
    }




}