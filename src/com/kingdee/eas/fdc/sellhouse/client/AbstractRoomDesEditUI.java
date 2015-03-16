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
public abstract class AbstractRoomDesEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomDesEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnitFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumberFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnitTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumberTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorTo;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsConvertToChar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNumPrefixType;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForSHE;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForTen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForPPM;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnitFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiNumberFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnitTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiNumberTo;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomUsage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7EyeSihnht;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7DecoraStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PosseStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Noise;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomDesEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomDesEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contUnitFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumberFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnitTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumberTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contFloorFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsConvertToChar = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboNumPrefixType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsForSHE = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForTen = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForPPM = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiUnitFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiNumberFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spiUnitTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiNumberTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spiFloorFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiFloorTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomUsage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7EyeSihnht = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7DecoraStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7PosseStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Noise = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contUnitFrom.setName("contUnitFrom");
        this.contNumberFrom.setName("contNumberFrom");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.contFace.setName("contFace");
        this.contOutlook.setName("contOutlook");
        this.contRoomModel.setName("contRoomModel");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contUnitTo.setName("contUnitTo");
        this.contNumberTo.setName("contNumberTo");
        this.contHouseProperty.setName("contHouseProperty");
        this.contBuilding.setName("contBuilding");
        this.kDSeparator5.setName("kDSeparator5");
        this.kDSeparator6.setName("kDSeparator6");
        this.contFloorFrom.setName("contFloorFrom");
        this.contFloorTo.setName("contFloorTo");
        this.chkIsConvertToChar.setName("chkIsConvertToChar");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.comboNumPrefixType.setName("comboNumPrefixType");
        this.kDSeparator7.setName("kDSeparator7");
        this.contProductType.setName("contProductType");
        this.chkIsForSHE.setName("chkIsForSHE");
        this.chkIsForTen.setName("chkIsForTen");
        this.chkIsForPPM.setName("chkIsForPPM");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.spiUnitFrom.setName("spiUnitFrom");
        this.spiNumberFrom.setName("spiNumberFrom");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.f7Direction.setName("f7Direction");
        this.f7Sight.setName("f7Sight");
        this.f7RoomModel.setName("f7RoomModel");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.spiUnitTo.setName("spiUnitTo");
        this.spiNumberTo.setName("spiNumberTo");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.txtBuilding.setName("txtBuilding");
        this.spiFloorFrom.setName("spiFloorFrom");
        this.spiFloorTo.setName("spiFloorTo");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.f7ProductType.setName("f7ProductType");
        this.f7RoomUsage.setName("f7RoomUsage");
        this.f7EyeSihnht.setName("f7EyeSihnht");
        this.f7DecoraStd.setName("f7DecoraStd");
        this.f7PosseStd.setName("f7PosseStd");
        this.f7Noise.setName("f7Noise");
        // CoreUI		
        this.setPreferredSize(new Dimension(660,450));
        // contUnitFrom		
        this.contUnitFrom.setBoundLabelText(resHelper.getString("contUnitFrom.boundLabelText"));		
        this.contUnitFrom.setBoundLabelUnderline(true);		
        this.contUnitFrom.setBoundLabelLength(100);
        // contNumberFrom		
        this.contNumberFrom.setBoundLabelText(resHelper.getString("contNumberFrom.boundLabelText"));		
        this.contNumberFrom.setBoundLabelUnderline(true);		
        this.contNumberFrom.setBoundLabelLength(100);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelUnderline(true);		
        this.contBuildingArea.setBoundLabelLength(100);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelUnderline(true);		
        this.contRoomArea.setBoundLabelLength(100);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelUnderline(true);		
        this.contApportionArea.setBoundLabelLength(100);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelUnderline(true);		
        this.contBalconyArea.setBoundLabelLength(100);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelUnderline(true);		
        this.contFloorHeight.setBoundLabelLength(100);
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelUnderline(true);		
        this.contFace.setBoundLabelLength(100);
        // contOutlook		
        this.contOutlook.setBoundLabelText(resHelper.getString("contOutlook.boundLabelText"));		
        this.contOutlook.setBoundLabelUnderline(true);		
        this.contOutlook.setBoundLabelLength(100);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelUnderline(true);		
        this.contRoomModel.setBoundLabelLength(100);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelUnderline(true);		
        this.contBuildingProperty.setBoundLabelLength(100);
        // contUnitTo		
        this.contUnitTo.setBoundLabelText(resHelper.getString("contUnitTo.boundLabelText"));		
        this.contUnitTo.setBoundLabelUnderline(true);		
        this.contUnitTo.setBoundLabelLength(50);
        // contNumberTo		
        this.contNumberTo.setBoundLabelText(resHelper.getString("contNumberTo.boundLabelText"));		
        this.contNumberTo.setBoundLabelUnderline(true);		
        this.contNumberTo.setBoundLabelLength(50);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelUnderline(true);		
        this.contHouseProperty.setBoundLabelLength(100);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelUnderline(true);		
        this.contBuilding.setBoundLabelLength(100);
        // kDSeparator5
        // kDSeparator6
        // contFloorFrom		
        this.contFloorFrom.setBoundLabelText(resHelper.getString("contFloorFrom.boundLabelText"));		
        this.contFloorFrom.setBoundLabelUnderline(true);		
        this.contFloorFrom.setBoundLabelLength(100);
        // contFloorTo		
        this.contFloorTo.setBoundLabelText(resHelper.getString("contFloorTo.boundLabelText"));		
        this.contFloorTo.setBoundLabelUnderline(true);		
        this.contFloorTo.setBoundLabelLength(50);
        // chkIsConvertToChar		
        this.chkIsConvertToChar.setText(resHelper.getString("chkIsConvertToChar.text"));
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelUnderline(true);		
        this.contActualBuildingArea.setBoundLabelLength(100);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(100);
        // comboNumPrefixType		
        this.comboNumPrefixType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomNumPrefixEnum").toArray());		
        this.comboNumPrefixType.setSelectedIndex(0);
        // kDSeparator7
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // chkIsForSHE		
        this.chkIsForSHE.setText(resHelper.getString("chkIsForSHE.text"));
        // chkIsForTen		
        this.chkIsForTen.setText(resHelper.getString("chkIsForTen.text"));
        // chkIsForPPM		
        this.chkIsForPPM.setText(resHelper.getString("chkIsForPPM.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // spiUnitFrom
        // spiNumberFrom
        // txtBuildingArea		
        this.txtBuildingArea.setSupportedEmpty(true);		
        this.txtBuildingArea.setDataType(1);
        this.txtBuildingArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBuildingArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);
        this.txtRoomArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtRoomArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtApportionArea		
        this.txtApportionArea.setDataType(1);		
        this.txtApportionArea.setEnabled(false);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);
        // txtFloorHeight		
        this.txtFloorHeight.setDataType(1);
        // f7Direction		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");		
        this.f7Direction.setCommitFormat("$number$");		
        this.f7Direction.setEditFormat("$number$");		
        this.f7Direction.setDisplayFormat("$name$");
        // f7Sight		
        this.f7Sight.setCommitFormat("$number$");		
        this.f7Sight.setEditFormat("$number$");		
        this.f7Sight.setDisplayFormat("$name$");		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setCommitFormat("$name$");		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setEditFormat("$number$");
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setEditFormat("$number$");
        // spiUnitTo
        // spiNumberTo
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
        // txtBuilding
        // spiFloorFrom
        // spiFloorTo
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // f7ProductType		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");		
        this.f7ProductType.setRequired(true);
        // f7RoomUsage		
        this.f7RoomUsage.setDisplayFormat("$name$");		
        this.f7RoomUsage.setEditFormat("$number$");		
        this.f7RoomUsage.setCommitFormat("$number$");		
        this.f7RoomUsage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomUsageQuery");
        // f7EyeSihnht		
        this.f7EyeSihnht.setDisplayFormat("$name$");		
        this.f7EyeSihnht.setEditFormat("$number$");		
        this.f7EyeSihnht.setCommitFormat("$number$");		
        this.f7EyeSihnht.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.EyeSignhtQuery");
        // f7DecoraStd		
        this.f7DecoraStd.setDisplayFormat("$name$");		
        this.f7DecoraStd.setEditFormat("$number$");		
        this.f7DecoraStd.setCommitFormat("$number$");		
        this.f7DecoraStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.DecorationStandardQuery");
        // f7PosseStd		
        this.f7PosseStd.setEditFormat("$number$");		
        this.f7PosseStd.setCommitFormat("$number$");		
        this.f7PosseStd.setDisplayFormat("$name$");		
        this.f7PosseStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PossessionStandardQuery");
        // f7Noise		
        this.f7Noise.setCommitFormat("$number$");		
        this.f7Noise.setEditFormat("$number$");		
        this.f7Noise.setDisplayFormat("$name$");		
        this.f7Noise.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.NoiseQuery");
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
        this.setBounds(new Rectangle(10, 10, 660, 450));
        this.setLayout(null);
        contUnitFrom.setBounds(new Rectangle(10, 34, 181, 19));
        this.add(contUnitFrom, null);
        contNumberFrom.setBounds(new Rectangle(10, 82, 181, 19));
        this.add(contNumberFrom, null);
        contBuildingArea.setBounds(new Rectangle(10, 113, 270, 19));
        this.add(contBuildingArea, null);
        contRoomArea.setBounds(new Rectangle(351, 113, 270, 19));
        this.add(contRoomArea, null);
        contApportionArea.setBounds(new Rectangle(10, 138, 270, 19));
        this.add(contApportionArea, null);
        contBalconyArea.setBounds(new Rectangle(351, 138, 270, 19));
        this.add(contBalconyArea, null);
        contFloorHeight.setBounds(new Rectangle(10, 203, 270, 19));
        this.add(contFloorHeight, null);
        contFace.setBounds(new Rectangle(351, 203, 270, 19));
        this.add(contFace, null);
        contOutlook.setBounds(new Rectangle(10, 229, 270, 19));
        this.add(contOutlook, null);
        contRoomModel.setBounds(new Rectangle(351, 229, 270, 19));
        this.add(contRoomModel, null);
        contBuildingProperty.setBounds(new Rectangle(10, 256, 270, 19));
        this.add(contBuildingProperty, null);
        contUnitTo.setBounds(new Rectangle(205, 32, 152, 19));
        this.add(contUnitTo, null);
        contNumberTo.setBounds(new Rectangle(206, 82, 152, 19));
        this.add(contNumberTo, null);
        contHouseProperty.setBounds(new Rectangle(351, 256, 270, 19));
        this.add(contHouseProperty, null);
        contBuilding.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contBuilding, null);
        kDSeparator5.setBounds(new Rectangle(4, 193, 630, 11));
        this.add(kDSeparator5, null);
        kDSeparator6.setBounds(new Rectangle(4, 106, 620, 9));
        this.add(kDSeparator6, null);
        contFloorFrom.setBounds(new Rectangle(10, 57, 181, 19));
        this.add(contFloorFrom, null);
        contFloorTo.setBounds(new Rectangle(205, 57, 152, 19));
        this.add(contFloorTo, null);
        chkIsConvertToChar.setBounds(new Rectangle(371, 82, 140, 19));
        this.add(chkIsConvertToChar, null);
        contActualBuildingArea.setBounds(new Rectangle(10, 164, 270, 19));
        this.add(contActualBuildingArea, null);
        contActualRoomArea.setBounds(new Rectangle(351, 164, 270, 19));
        this.add(contActualRoomArea, null);
        comboNumPrefixType.setBounds(new Rectangle(371, 57, 170, 19));
        this.add(comboNumPrefixType, null);
        kDSeparator7.setBounds(new Rectangle(2, 362, 626, 4));
        this.add(kDSeparator7, null);
        contProductType.setBounds(new Rectangle(9, 370, 270, 19));
        this.add(contProductType, null);
        chkIsForSHE.setBounds(new Rectangle(345, 370, 140, 19));
        this.add(chkIsForSHE, null);
        chkIsForTen.setBounds(new Rectangle(7, 397, 140, 19));
        this.add(chkIsForTen, null);
        chkIsForPPM.setBounds(new Rectangle(345, 397, 140, 19));
        this.add(chkIsForPPM, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 283, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(351, 283, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(10, 310, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(351, 310, 270, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(10, 338, 270, 19));
        this.add(kDLabelContainer5, null);
        //contUnitFrom
        contUnitFrom.setBoundEditor(spiUnitFrom);
        //contNumberFrom
        contNumberFrom.setBoundEditor(spiNumberFrom);
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
        //contUnitTo
        contUnitTo.setBoundEditor(spiUnitTo);
        //contNumberTo
        contNumberTo.setBoundEditor(spiNumberTo);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contBuilding
        contBuilding.setBoundEditor(txtBuilding);
        //contFloorFrom
        contFloorFrom.setBoundEditor(spiFloorFrom);
        //contFloorTo
        contFloorTo.setBoundEditor(spiFloorTo);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contProductType
        contProductType.setBoundEditor(f7ProductType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(f7RoomUsage);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(f7EyeSihnht);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(f7DecoraStd);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(f7PosseStd);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(f7Noise);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomDesEditUIHandler";
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
     * output txtBuildingArea_dataChanged method
     */
    protected void txtBuildingArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtRoomArea_dataChanged method
     */
    protected void txtRoomArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomDesEditUI");
    }




}