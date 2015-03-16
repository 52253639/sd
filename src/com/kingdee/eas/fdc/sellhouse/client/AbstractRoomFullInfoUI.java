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
public abstract class AbstractRoomFullInfoUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomFullInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabBizInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRoomState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealPrice;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCalByRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNo;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomFullInfoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomFullInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.tabBizInfo = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contDealPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboRoomState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDealPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.chkIsCalByRoom = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contStandardPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtStandardPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBuildingArea.setName("contBuildingArea");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.contFace.setName("contFace");
        this.contOutlook.setName("contOutlook");
        this.contRoomModel.setName("contRoomModel");
        this.f7RoomModel.setName("f7RoomModel");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.contHouseProperty.setName("contHouseProperty");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.kDSeparator5.setName("kDSeparator5");
        this.tabBizInfo.setName("tabBizInfo");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.contDealPrice.setName("contDealPrice");
        this.contDealTotalAmount.setName("contDealTotalAmount");
        this.contRoomState.setName("contRoomState");
        this.txtDealTotalAmount.setName("txtDealTotalAmount");
        this.comboRoomState.setName("comboRoomState");
        this.txtDealPrice.setName("txtDealPrice");
        this.chkIsCalByRoom.setName("chkIsCalByRoom");
        this.contStandardPrice.setName("contStandardPrice");
        this.txtStandardPrice.setName("txtStandardPrice");
        this.contStandardTotalAmount.setName("contStandardTotalAmount");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.f7Direction.setName("f7Direction");
        this.f7Sight.setName("f7Sight");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtNo.setName("txtNo");
        this.txtRoomNo.setName("txtRoomNo");
        // CoreUI
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(50);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(50);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelLength(50);		
        this.contApportionArea.setBoundLabelUnderline(true);
        // txtApportionArea		
        this.txtApportionArea.setDataType(1);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(50);		
        this.contBalconyArea.setBoundLabelUnderline(true);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(50);		
        this.contFloorHeight.setBoundLabelUnderline(true);
        // txtFloorHeight		
        this.txtFloorHeight.setDataType(1);
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
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setCommitFormat("$number$");		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setEditFormat("$number$");
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(50);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setCommitFormat("$number$");
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelLength(50);		
        this.contHouseProperty.setBoundLabelUnderline(true);
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
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
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // contDealPrice		
        this.contDealPrice.setBoundLabelText(resHelper.getString("contDealPrice.boundLabelText"));		
        this.contDealPrice.setBoundLabelLength(50);		
        this.contDealPrice.setBoundLabelUnderline(true);
        // contDealTotalAmount		
        this.contDealTotalAmount.setBoundLabelText(resHelper.getString("contDealTotalAmount.boundLabelText"));		
        this.contDealTotalAmount.setBoundLabelLength(50);		
        this.contDealTotalAmount.setBoundLabelUnderline(true);
        // contRoomState		
        this.contRoomState.setBoundLabelText(resHelper.getString("contRoomState.boundLabelText"));		
        this.contRoomState.setBoundLabelLength(50);		
        this.contRoomState.setBoundLabelUnderline(true);
        // txtDealTotalAmount		
        this.txtDealTotalAmount.setDataType(1);
        // comboRoomState		
        this.comboRoomState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum").toArray());
        // txtDealPrice		
        this.txtDealPrice.setDataType(1);
        // chkIsCalByRoom		
        this.chkIsCalByRoom.setText(resHelper.getString("chkIsCalByRoom.text"));
        // contStandardPrice		
        this.contStandardPrice.setBoundLabelText(resHelper.getString("contStandardPrice.boundLabelText"));		
        this.contStandardPrice.setBoundLabelLength(50);		
        this.contStandardPrice.setBoundLabelUnderline(true);
        // txtStandardPrice		
        this.txtStandardPrice.setDataType(1);
        // contStandardTotalAmount		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contStandardTotalAmount.setBoundLabelLength(50);		
        this.contStandardTotalAmount.setBoundLabelUnderline(true);
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);
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
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(50);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(50);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // txtNo
        // txtRoomNo
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
        this.setBounds(new Rectangle(10, 10, 320, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 320, 629));
        contBuildingArea.setBounds(new Rectangle(11, 34, 138, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(11, 34, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomArea.setBounds(new Rectangle(167, 34, 138, 19));
        this.add(contRoomArea, new KDLayout.Constraints(167, 34, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApportionArea.setBounds(new Rectangle(10, 83, 138, 19));
        this.add(contApportionArea, new KDLayout.Constraints(10, 83, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBalconyArea.setBounds(new Rectangle(167, 83, 138, 19));
        this.add(contBalconyArea, new KDLayout.Constraints(167, 83, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFloorHeight.setBounds(new Rectangle(10, 113, 138, 19));
        this.add(contFloorHeight, new KDLayout.Constraints(10, 113, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFace.setBounds(new Rectangle(167, 113, 138, 19));
        this.add(contFace, new KDLayout.Constraints(167, 113, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOutlook.setBounds(new Rectangle(10, 138, 138, 19));
        this.add(contOutlook, new KDLayout.Constraints(10, 138, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(167, 138, 138, 19));
        this.add(contRoomModel, new KDLayout.Constraints(167, 138, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingProperty.setBounds(new Rectangle(10, 162, 138, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(10, 162, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHouseProperty.setBounds(new Rectangle(167, 162, 138, 19));
        this.add(contHouseProperty, new KDLayout.Constraints(167, 162, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator5.setBounds(new Rectangle(6, 107, 308, 10));
        this.add(kDSeparator5, new KDLayout.Constraints(6, 107, 308, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabBizInfo.setBounds(new Rectangle(3, 267, 314, 349));
        this.add(tabBizInfo, new KDLayout.Constraints(3, 267, 314, 349, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contActualBuildingArea.setBounds(new Rectangle(10, 59, 138, 19));
        this.add(contActualBuildingArea, new KDLayout.Constraints(10, 59, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActualRoomArea.setBounds(new Rectangle(167, 59, 138, 19));
        this.add(contActualRoomArea, new KDLayout.Constraints(167, 59, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDealPrice.setBounds(new Rectangle(10, 213, 138, 19));
        this.add(contDealPrice, new KDLayout.Constraints(10, 213, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealTotalAmount.setBounds(new Rectangle(167, 213, 138, 19));
        this.add(contDealTotalAmount, new KDLayout.Constraints(167, 213, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomState.setBounds(new Rectangle(167, 239, 138, 19));
        this.add(contRoomState, new KDLayout.Constraints(167, 239, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsCalByRoom.setBounds(new Rectangle(8, 240, 140, 19));
        this.add(chkIsCalByRoom, new KDLayout.Constraints(8, 240, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardPrice.setBounds(new Rectangle(10, 188, 138, 19));
        this.add(contStandardPrice, new KDLayout.Constraints(10, 188, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardTotalAmount.setBounds(new Rectangle(167, 188, 138, 19));
        this.add(contStandardTotalAmount, new KDLayout.Constraints(167, 188, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(12, 8, 138, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(12, 8, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(168, 7, 138, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(168, 7, 138, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //contDealPrice
        contDealPrice.setBoundEditor(txtDealPrice);
        //contDealTotalAmount
        contDealTotalAmount.setBoundEditor(txtDealTotalAmount);
        //contRoomState
        contRoomState.setBoundEditor(comboRoomState);
        //contStandardPrice
        contStandardPrice.setBoundEditor(txtStandardPrice);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNo);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtRoomNo);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomFullInfoUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomFullInfoUI");
    }




}