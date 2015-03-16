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
public abstract class AbstractRoomDesPaneUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomDesPaneUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDirection;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEyeSight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomUsage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPossStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoise;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDecoraStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingStruct;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGardenArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUseArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlateArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDirection;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEyeSight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomUsage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPossStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNoise;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDecoraStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingStruct;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedGardenArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedUseArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedPlanBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedPlanRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedPlateArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedIbasement;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedIbaInnside;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedInnside;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFormattedIbameasured;
    protected com.kingdee.eas.framework.DataBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomDesPaneUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomDesPaneUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDirection = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEyeSight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomUsage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPossStd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoise = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDecoraStd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingStruct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGardenArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUseArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCarbarnArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlateArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDirection = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtEyeSight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoomUsage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPossStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtNoise = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDecoraStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuildingStruct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFormattedActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedGardenArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedUseArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedPlanBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedPlanRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedCarbarnArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedPlateArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtRoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFormattedIbasement = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedIbaInnside = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedInnside = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFormattedIbameasured = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contProductType.setName("contProductType");
        this.contDirection.setName("contDirection");
        this.contEyeSight.setName("contEyeSight");
        this.contRoomUsage.setName("contRoomUsage");
        this.contSight.setName("contSight");
        this.contPossStd.setName("contPossStd");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contNoise.setName("contNoise");
        this.contDecoraStd.setName("contDecoraStd");
        this.contBuildingStruct.setName("contBuildingStruct");
        this.kDSeparator8.setName("kDSeparator8");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contGardenArea.setName("contGardenArea");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.contUseArea.setName("contUseArea");
        this.contPlanBuildingArea.setName("contPlanBuildingArea");
        this.contPlanRoomArea.setName("contPlanRoomArea");
        this.contCarbarnArea.setName("contCarbarnArea");
        this.contPlateArea.setName("contPlateArea");
        this.contRoomModel.setName("contRoomModel");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.prmtProductType.setName("prmtProductType");
        this.prmtDirection.setName("prmtDirection");
        this.prmtEyeSight.setName("prmtEyeSight");
        this.prmtRoomUsage.setName("prmtRoomUsage");
        this.prmtSight.setName("prmtSight");
        this.prmtPossStd.setName("prmtPossStd");
        this.prmtBuildingProperty.setName("prmtBuildingProperty");
        this.prmtNoise.setName("prmtNoise");
        this.prmtDecoraStd.setName("prmtDecoraStd");
        this.prmtBuildingStruct.setName("prmtBuildingStruct");
        this.txtFormattedActualBuildingArea.setName("txtFormattedActualBuildingArea");
        this.txtFormattedActualRoomArea.setName("txtFormattedActualRoomArea");
        this.txtFormattedGardenArea.setName("txtFormattedGardenArea");
        this.txtFormattedBuildingArea.setName("txtFormattedBuildingArea");
        this.txtFormattedRoomArea.setName("txtFormattedRoomArea");
        this.txtFormattedBalconyArea.setName("txtFormattedBalconyArea");
        this.txtFormattedUseArea.setName("txtFormattedUseArea");
        this.txtFormattedPlanBuildingArea.setName("txtFormattedPlanBuildingArea");
        this.txtFormattedPlanRoomArea.setName("txtFormattedPlanRoomArea");
        this.txtFormattedCarbarnArea.setName("txtFormattedCarbarnArea");
        this.txtFormattedPlateArea.setName("txtFormattedPlateArea");
        this.prmtRoomModel.setName("prmtRoomModel");
        this.txtFormattedIbasement.setName("txtFormattedIbasement");
        this.txtFormattedIbaInnside.setName("txtFormattedIbaInnside");
        this.txtFormattedInnside.setName("txtFormattedInnside");
        this.txtFormattedIbameasured.setName("txtFormattedIbameasured");
        // CoreUI
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contDirection		
        this.contDirection.setBoundLabelText(resHelper.getString("contDirection.boundLabelText"));		
        this.contDirection.setBoundLabelLength(100);		
        this.contDirection.setBoundLabelUnderline(true);
        // contEyeSight		
        this.contEyeSight.setBoundLabelText(resHelper.getString("contEyeSight.boundLabelText"));		
        this.contEyeSight.setBoundLabelLength(100);		
        this.contEyeSight.setBoundLabelUnderline(true);
        // contRoomUsage		
        this.contRoomUsage.setBoundLabelText(resHelper.getString("contRoomUsage.boundLabelText"));		
        this.contRoomUsage.setBoundLabelLength(100);		
        this.contRoomUsage.setBoundLabelUnderline(true);
        // contSight		
        this.contSight.setBoundLabelText(resHelper.getString("contSight.boundLabelText"));		
        this.contSight.setBoundLabelLength(100);		
        this.contSight.setBoundLabelUnderline(true);
        // contPossStd		
        this.contPossStd.setBoundLabelText(resHelper.getString("contPossStd.boundLabelText"));		
        this.contPossStd.setBoundLabelLength(100);		
        this.contPossStd.setBoundLabelUnderline(true);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // contNoise		
        this.contNoise.setBoundLabelText(resHelper.getString("contNoise.boundLabelText"));		
        this.contNoise.setBoundLabelLength(100);		
        this.contNoise.setBoundLabelUnderline(true);
        // contDecoraStd		
        this.contDecoraStd.setBoundLabelText(resHelper.getString("contDecoraStd.boundLabelText"));		
        this.contDecoraStd.setBoundLabelLength(100);		
        this.contDecoraStd.setBoundLabelUnderline(true);
        // contBuildingStruct		
        this.contBuildingStruct.setBoundLabelText(resHelper.getString("contBuildingStruct.boundLabelText"));		
        this.contBuildingStruct.setBoundLabelLength(100);		
        this.contBuildingStruct.setBoundLabelUnderline(true);
        // kDSeparator8
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelLength(100);		
        this.contActualBuildingArea.setBoundLabelUnderline(true);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelLength(100);		
        this.contActualRoomArea.setBoundLabelUnderline(true);
        // contGardenArea		
        this.contGardenArea.setBoundLabelText(resHelper.getString("contGardenArea.boundLabelText"));		
        this.contGardenArea.setBoundLabelLength(100);		
        this.contGardenArea.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(100);		
        this.contBalconyArea.setBoundLabelUnderline(true);
        // contUseArea		
        this.contUseArea.setBoundLabelText(resHelper.getString("contUseArea.boundLabelText"));		
        this.contUseArea.setBoundLabelLength(100);		
        this.contUseArea.setBoundLabelUnderline(true);
        // contPlanBuildingArea		
        this.contPlanBuildingArea.setBoundLabelText(resHelper.getString("contPlanBuildingArea.boundLabelText"));		
        this.contPlanBuildingArea.setBoundLabelLength(100);		
        this.contPlanBuildingArea.setBoundLabelUnderline(true);
        // contPlanRoomArea		
        this.contPlanRoomArea.setBoundLabelText(resHelper.getString("contPlanRoomArea.boundLabelText"));		
        this.contPlanRoomArea.setBoundLabelLength(100);		
        this.contPlanRoomArea.setBoundLabelUnderline(true);
        // contCarbarnArea		
        this.contCarbarnArea.setBoundLabelText(resHelper.getString("contCarbarnArea.boundLabelText"));		
        this.contCarbarnArea.setBoundLabelLength(100);		
        this.contCarbarnArea.setBoundLabelUnderline(true);
        // contPlateArea		
        this.contPlateArea.setBoundLabelText(resHelper.getString("contPlateArea.boundLabelText"));		
        this.contPlateArea.setBoundLabelLength(100);		
        this.contPlateArea.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(100);
        // prmtProductType		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.prmtProductType.setRequired(true);
        // prmtDirection		
        this.prmtDirection.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // prmtEyeSight		
        this.prmtEyeSight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtRoomUsage		
        this.prmtRoomUsage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");		
        this.prmtRoomUsage.setCommitFormat("$name$");		
        this.prmtRoomUsage.setDisplayFormat("$name$");		
        this.prmtRoomUsage.setEditFormat("$number$");
        // prmtSight		
        this.prmtSight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtPossStd		
        this.prmtPossStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtBuildingProperty		
        this.prmtBuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.prmtBuildingProperty.setRequired(true);
        // prmtNoise		
        this.prmtNoise.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtDecoraStd		
        this.prmtDecoraStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtBuildingStruct		
        this.prmtBuildingStruct.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingStructureQuery");
        // txtFormattedActualBuildingArea
        // txtFormattedActualRoomArea
        // txtFormattedGardenArea
        // txtFormattedBuildingArea
        // txtFormattedRoomArea
        // txtFormattedBalconyArea
        // txtFormattedUseArea
        // txtFormattedPlanBuildingArea
        // txtFormattedPlanRoomArea
        // txtFormattedCarbarnArea
        // txtFormattedPlateArea
        // prmtRoomModel		
        this.prmtRoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");
        // txtFormattedIbasement
        // txtFormattedIbaInnside
        // txtFormattedInnside
        // txtFormattedIbameasured
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
        this.setBounds(new Rectangle(10, 10, 990, 220));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 990, 220));
        contProductType.setBounds(new Rectangle(6, 6, 270, 19));
        this.add(contProductType, new KDLayout.Constraints(6, 6, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDirection.setBounds(new Rectangle(6, 29, 270, 19));
        this.add(contDirection, new KDLayout.Constraints(6, 29, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEyeSight.setBounds(new Rectangle(6, 52, 270, 19));
        this.add(contEyeSight, new KDLayout.Constraints(6, 52, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomUsage.setBounds(new Rectangle(6, 75, 270, 19));
        this.add(contRoomUsage, new KDLayout.Constraints(6, 75, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSight.setBounds(new Rectangle(359, 51, 270, 19));
        this.add(contSight, new KDLayout.Constraints(359, 51, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPossStd.setBounds(new Rectangle(359, 28, 270, 19));
        this.add(contPossStd, new KDLayout.Constraints(359, 28, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingProperty.setBounds(new Rectangle(359, 6, 270, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(359, 6, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNoise.setBounds(new Rectangle(713, 51, 270, 19));
        this.add(contNoise, new KDLayout.Constraints(713, 51, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDecoraStd.setBounds(new Rectangle(359, 73, 270, 19));
        this.add(contDecoraStd, new KDLayout.Constraints(359, 73, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingStruct.setBounds(new Rectangle(713, 29, 270, 19));
        this.add(contBuildingStruct, new KDLayout.Constraints(713, 29, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(3, 99, 984, 8));
        this.add(kDSeparator8, new KDLayout.Constraints(3, 99, 984, 8, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActualBuildingArea.setBounds(new Rectangle(713, 104, 270, 19));
        this.add(contActualBuildingArea, new KDLayout.Constraints(713, 104, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActualRoomArea.setBounds(new Rectangle(713, 127, 270, 19));
        this.add(contActualRoomArea, new KDLayout.Constraints(713, 127, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGardenArea.setBounds(new Rectangle(713, 196, 270, 19));
        this.add(contGardenArea, new KDLayout.Constraints(713, 196, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingArea.setBounds(new Rectangle(359, 104, 270, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(359, 104, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomArea.setBounds(new Rectangle(359, 127, 270, 19));
        this.add(contRoomArea, new KDLayout.Constraints(359, 127, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBalconyArea.setBounds(new Rectangle(713, 173, 270, 19));
        this.add(contBalconyArea, new KDLayout.Constraints(713, 173, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUseArea.setBounds(new Rectangle(359, 196, 270, 19));
        this.add(contUseArea, new KDLayout.Constraints(359, 196, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanBuildingArea.setBounds(new Rectangle(8, 105, 270, 19));
        this.add(contPlanBuildingArea, new KDLayout.Constraints(8, 105, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanRoomArea.setBounds(new Rectangle(8, 128, 270, 19));
        this.add(contPlanRoomArea, new KDLayout.Constraints(8, 128, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCarbarnArea.setBounds(new Rectangle(359, 173, 270, 19));
        this.add(contCarbarnArea, new KDLayout.Constraints(359, 173, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlateArea.setBounds(new Rectangle(8, 196, 270, 19));
        this.add(contPlateArea, new KDLayout.Constraints(8, 196, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(713, 6, 270, 19));
        this.add(contRoomModel, new KDLayout.Constraints(713, 6, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(8, 150, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(8, 150, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(8, 173, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(8, 173, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(359, 150, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(359, 150, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(713, 150, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(713, 150, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contProductType
        contProductType.setBoundEditor(prmtProductType);
        //contDirection
        contDirection.setBoundEditor(prmtDirection);
        //contEyeSight
        contEyeSight.setBoundEditor(prmtEyeSight);
        //contRoomUsage
        contRoomUsage.setBoundEditor(prmtRoomUsage);
        //contSight
        contSight.setBoundEditor(prmtSight);
        //contPossStd
        contPossStd.setBoundEditor(prmtPossStd);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(prmtBuildingProperty);
        //contNoise
        contNoise.setBoundEditor(prmtNoise);
        //contDecoraStd
        contDecoraStd.setBoundEditor(prmtDecoraStd);
        //contBuildingStruct
        contBuildingStruct.setBoundEditor(prmtBuildingStruct);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtFormattedActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtFormattedActualRoomArea);
        //contGardenArea
        contGardenArea.setBoundEditor(txtFormattedGardenArea);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtFormattedBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtFormattedRoomArea);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtFormattedBalconyArea);
        //contUseArea
        contUseArea.setBoundEditor(txtFormattedUseArea);
        //contPlanBuildingArea
        contPlanBuildingArea.setBoundEditor(txtFormattedPlanBuildingArea);
        //contPlanRoomArea
        contPlanRoomArea.setBoundEditor(txtFormattedPlanRoomArea);
        //contCarbarnArea
        contCarbarnArea.setBoundEditor(txtFormattedCarbarnArea);
        //contPlateArea
        contPlateArea.setBoundEditor(txtFormattedPlateArea);
        //contRoomModel
        contRoomModel.setBoundEditor(prmtRoomModel);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtFormattedIbasement);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtFormattedIbaInnside);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtFormattedInnside);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtFormattedIbameasured);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomDesPaneUIHandler";
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
        this.editData = (com.kingdee.eas.framework.DataBaseInfo)ov;
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomDesPaneUI");
    }




}