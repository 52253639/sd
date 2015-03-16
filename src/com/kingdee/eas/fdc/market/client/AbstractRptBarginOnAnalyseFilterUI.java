/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractRptBarginOnAnalyseFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptBarginOnAnalyseFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPane;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promptSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModelType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductDetail;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductDetail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomForm;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomForm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDirection;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDirection;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSight;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinAreaCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompareMax;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxAreaCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompareMin;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdContainerPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioBuildPerPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioRoomPerPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioDealPerPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioStandardTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioDealTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioContactTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceCompareMin;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinPriceCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceCompareMax;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxPriceCount;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerDimension;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioBuilding;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioFloor;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioProductDetail;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioProductType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioRoomForm;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSight;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioDirection;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioRoomNoDimension;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSex;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIndustry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIndustry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerDegree;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promptCustomerDegree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerOrigin;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHabitation;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHabitation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerCtreatTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMinCustData;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker minCustDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaxCustData;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker maxCustDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerDimension;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioCustomerDegree;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton cadioCustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioCreator;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioTradeTimes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioProvince;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioHabitationArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSex;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioFamilyEarning;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioFirstReception;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioCustNoDimension;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTradeTimes;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTradeTimes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioEnterpriceSize;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioIndustry;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup areaGroup;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup roomGroup;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup priceGroup;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup customerGroup;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkOnlyMainCustomer;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioCustOriType;
    /**
     * output class constructor
     */
    public AbstractRptBarginOnAnalyseFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRptBarginOnAnalyseFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.tabPane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanelRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.promptSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoomModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoomModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProductDetail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProductDetail = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoomForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoomForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtDirection = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDirection = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainerArea = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtSight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.radioBuildingArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioRoomArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.txtMinAreaCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCompareMax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtMaxAreaCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCompareMin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdContainerPrice = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.radioBuildPerPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioRoomPerPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioDealPerPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioStandardTotalPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioDealTotalPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioContactTotalPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contPriceCompareMin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtMinPriceCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contPriceCompareMax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtMaxPriceCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.containerDimension = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.radioBuilding = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioFloor = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioHouseProperty = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioRoomModelType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioRoomModel = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioProductDetail = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioProductType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioBuildingProperty = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioRoomForm = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSight = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioDirection = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioRoomNoDimension = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contSex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contIndustry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtIndustry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCustomerDegree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.promptCustomerDegree = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCustomerOrigin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCustomerOrigin = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProvince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHabitation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHabitation = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSaleMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDContainerCtreatTime = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contMinCustData = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.minCustDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contMaxCustData = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.maxCustDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDContainerDimension = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.radioCustomerType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioCustomerDegree = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.cadioCustomerOrigin = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioCreator = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSaleMan = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioTradeTimes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioProvince = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioHabitationArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSex = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioFamilyEarning = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioFirstReception = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioCustNoDimension = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contTradeTimes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTradeTimes = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.radioEnterpriceSize = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioIndustry = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.areaGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.roomGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.priceGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.customerGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.checkOnlyMainCustomer = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.radioCustOriType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.tabPane.setName("tabPane");
        this.kDPanelRoom.setName("kDPanelRoom");
        this.kDPanelCustomer.setName("kDPanelCustomer");
        this.prmtBuilding.setName("prmtBuilding");
        this.contSellProject.setName("contSellProject");
        this.contBuilding.setName("contBuilding");
        this.promptSellProject.setName("promptSellProject");
        this.contRoomModel.setName("contRoomModel");
        this.prmtRoomModel.setName("prmtRoomModel");
        this.contRoomModelType.setName("contRoomModelType");
        this.prmtRoomModelType.setName("prmtRoomModelType");
        this.contProductDetail.setName("contProductDetail");
        this.prmtProductDetail.setName("prmtProductDetail");
        this.contProductType.setName("contProductType");
        this.prmtProductType.setName("prmtProductType");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.prmtBuildingProperty.setName("prmtBuildingProperty");
        this.contRoomForm.setName("contRoomForm");
        this.prmtRoomForm.setName("prmtRoomForm");
        this.contSight.setName("contSight");
        this.prmtDirection.setName("prmtDirection");
        this.contDirection.setName("contDirection");
        this.kDContainerArea.setName("kDContainerArea");
        this.prmtSight.setName("prmtSight");
        this.radioBuildingArea.setName("radioBuildingArea");
        this.radioRoomArea.setName("radioRoomArea");
        this.txtMinAreaCount.setName("txtMinAreaCount");
        this.contCompareMax.setName("contCompareMax");
        this.txtMaxAreaCount.setName("txtMaxAreaCount");
        this.contCompareMin.setName("contCompareMin");
        this.kdContainerPrice.setName("kdContainerPrice");
        this.radioBuildPerPrice.setName("radioBuildPerPrice");
        this.radioRoomPerPrice.setName("radioRoomPerPrice");
        this.radioDealPerPrice.setName("radioDealPerPrice");
        this.radioStandardTotalPrice.setName("radioStandardTotalPrice");
        this.radioDealTotalPrice.setName("radioDealTotalPrice");
        this.radioContactTotalPrice.setName("radioContactTotalPrice");
        this.contPriceCompareMin.setName("contPriceCompareMin");
        this.txtMinPriceCount.setName("txtMinPriceCount");
        this.contPriceCompareMax.setName("contPriceCompareMax");
        this.txtMaxPriceCount.setName("txtMaxPriceCount");
        this.containerDimension.setName("containerDimension");
        this.radioBuilding.setName("radioBuilding");
        this.radioFloor.setName("radioFloor");
        this.radioHouseProperty.setName("radioHouseProperty");
        this.radioRoomModelType.setName("radioRoomModelType");
        this.radioRoomModel.setName("radioRoomModel");
        this.radioProductDetail.setName("radioProductDetail");
        this.radioProductType.setName("radioProductType");
        this.radioBuildingProperty.setName("radioBuildingProperty");
        this.radioRoomForm.setName("radioRoomForm");
        this.radioSight.setName("radioSight");
        this.radioDirection.setName("radioDirection");
        this.radioRoomNoDimension.setName("radioRoomNoDimension");
        this.contCustomerType.setName("contCustomerType");
        this.comboCustomerType.setName("comboCustomerType");
        this.contSex.setName("contSex");
        this.comboSex.setName("comboSex");
        this.contIndustry.setName("contIndustry");
        this.prmtIndustry.setName("prmtIndustry");
        this.contCustomerDegree.setName("contCustomerDegree");
        this.promptCustomerDegree.setName("promptCustomerDegree");
        this.contCustomerOrigin.setName("contCustomerOrigin");
        this.prmtCustomerOrigin.setName("prmtCustomerOrigin");
        this.contProvince.setName("contProvince");
        this.prmtProvince.setName("prmtProvince");
        this.contHabitation.setName("contHabitation");
        this.prmtHabitation.setName("prmtHabitation");
        this.contSaleMan.setName("contSaleMan");
        this.prmtSaleMan.setName("prmtSaleMan");
        this.kDContainerCtreatTime.setName("kDContainerCtreatTime");
        this.contMinCustData.setName("contMinCustData");
        this.minCustDate.setName("minCustDate");
        this.contMaxCustData.setName("contMaxCustData");
        this.maxCustDate.setName("maxCustDate");
        this.kDContainerDimension.setName("kDContainerDimension");
        this.radioCustomerType.setName("radioCustomerType");
        this.radioCustomerDegree.setName("radioCustomerDegree");
        this.cadioCustomerOrigin.setName("cadioCustomerOrigin");
        this.radioCreator.setName("radioCreator");
        this.radioSaleMan.setName("radioSaleMan");
        this.radioTradeTimes.setName("radioTradeTimes");
        this.radioProvince.setName("radioProvince");
        this.radioHabitationArea.setName("radioHabitationArea");
        this.radioSex.setName("radioSex");
        this.radioFamilyEarning.setName("radioFamilyEarning");
        this.radioFirstReception.setName("radioFirstReception");
        this.radioCustNoDimension.setName("radioCustNoDimension");
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contTradeTimes.setName("contTradeTimes");
        this.txtTradeTimes.setName("txtTradeTimes");
        this.radioEnterpriceSize.setName("radioEnterpriceSize");
        this.radioIndustry.setName("radioIndustry");
        this.checkOnlyMainCustomer.setName("checkOnlyMainCustomer");
        this.radioCustOriType.setName("radioCustOriType");
        // CustomerQueryPanel
        // tabPane
        // kDPanelRoom		
        this.kDPanelRoom.setAutoscrolls(true);		
        this.kDPanelRoom.setBorder(null);
        // kDPanelCustomer		
        this.kDPanelCustomer.setAutoscrolls(true);
        // prmtBuilding		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$number$");		
        this.prmtBuilding.setCommitFormat("$number$");		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");
        this.prmtBuilding.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBuilding_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // promptSellProject		
        this.promptSellProject.setDisplayFormat("$name$");		
        this.promptSellProject.setEditFormat("$number$");		
        this.promptSellProject.setCommitFormat("$number$");		
        this.promptSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        this.promptSellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    promptSellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // prmtRoomModel		
        this.prmtRoomModel.setDisplayFormat("$name$");		
        this.prmtRoomModel.setEditFormat("$number$");		
        this.prmtRoomModel.setCommitFormat("$number$");		
        this.prmtRoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");
        // contRoomModelType		
        this.contRoomModelType.setBoundLabelText(resHelper.getString("contRoomModelType.boundLabelText"));		
        this.contRoomModelType.setBoundLabelLength(100);		
        this.contRoomModelType.setBoundLabelUnderline(true);
        // prmtRoomModelType		
        this.prmtRoomModelType.setDisplayFormat("$name$");		
        this.prmtRoomModelType.setEditFormat("$number$");		
        this.prmtRoomModelType.setCommitFormat("$number$");		
        this.prmtRoomModelType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");
        // contProductDetail		
        this.contProductDetail.setBoundLabelText(resHelper.getString("contProductDetail.boundLabelText"));		
        this.contProductDetail.setBoundLabelLength(100);		
        this.contProductDetail.setBoundLabelUnderline(true);
        // prmtProductDetail		
        this.prmtProductDetail.setDisplayFormat("$name$");		
        this.prmtProductDetail.setEditFormat("$number$");		
        this.prmtProductDetail.setCommitFormat("$number$");		
        this.prmtProductDetail.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery");
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // prmtProductType		
        this.prmtProductType.setDisplayFormat("$name$");		
        this.prmtProductType.setEditFormat("$number$");		
        this.prmtProductType.setCommitFormat("$number$");		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // prmtBuildingProperty		
        this.prmtBuildingProperty.setDisplayFormat("$name$");		
        this.prmtBuildingProperty.setEditFormat("$number$");		
        this.prmtBuildingProperty.setCommitFormat("$number$");		
        this.prmtBuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
        // contRoomForm		
        this.contRoomForm.setBoundLabelText(resHelper.getString("contRoomForm.boundLabelText"));		
        this.contRoomForm.setBoundLabelLength(100);		
        this.contRoomForm.setBoundLabelUnderline(true);
        // prmtRoomForm		
        this.prmtRoomForm.setDisplayFormat("$name$");		
        this.prmtRoomForm.setEditFormat("$number$");		
        this.prmtRoomForm.setCommitFormat("$number$");		
        this.prmtRoomForm.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery");
        // contSight		
        this.contSight.setBoundLabelText(resHelper.getString("contSight.boundLabelText"));		
        this.contSight.setBoundLabelLength(100);		
        this.contSight.setBoundLabelUnderline(true);
        // prmtDirection		
        this.prmtDirection.setDisplayFormat("$name$");		
        this.prmtDirection.setEditFormat("$number$");		
        this.prmtDirection.setCommitFormat("$number$");		
        this.prmtDirection.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // contDirection		
        this.contDirection.setBoundLabelText(resHelper.getString("contDirection.boundLabelText"));		
        this.contDirection.setBoundLabelUnderline(true);		
        this.contDirection.setBoundLabelLength(100);
        // kDContainerArea		
        this.kDContainerArea.setTitle(resHelper.getString("kDContainerArea.title"));		
        this.kDContainerArea.setEnableActive(false);
        // prmtSight		
        this.prmtSight.setDisplayFormat("$name$");		
        this.prmtSight.setEditFormat("$number$");		
        this.prmtSight.setCommitFormat("$number$");		
        this.prmtSight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // radioBuildingArea		
        this.radioBuildingArea.setText(resHelper.getString("radioBuildingArea.text"));
        // radioRoomArea		
        this.radioRoomArea.setText(resHelper.getString("radioRoomArea.text"));
        // txtMinAreaCount		
        this.txtMinAreaCount.setDataType(1);		
        this.txtMinAreaCount.setSupportedEmpty(true);		
        this.txtMinAreaCount.setPrecision(2);
        // contCompareMax		
        this.contCompareMax.setBoundLabelText(resHelper.getString("contCompareMax.boundLabelText"));		
        this.contCompareMax.setBoundLabelLength(50);
        // txtMaxAreaCount		
        this.txtMaxAreaCount.setDataType(1);		
        this.txtMaxAreaCount.setSupportedEmpty(true);		
        this.txtMaxAreaCount.setPrecision(2);
        // contCompareMin		
        this.contCompareMin.setBoundLabelText(resHelper.getString("contCompareMin.boundLabelText"));		
        this.contCompareMin.setBoundLabelLength(50);
        // kdContainerPrice		
        this.kdContainerPrice.setTitle(resHelper.getString("kdContainerPrice.title"));		
        this.kdContainerPrice.setEnableActive(false);
        // radioBuildPerPrice		
        this.radioBuildPerPrice.setText(resHelper.getString("radioBuildPerPrice.text"));
        // radioRoomPerPrice		
        this.radioRoomPerPrice.setText(resHelper.getString("radioRoomPerPrice.text"));
        // radioDealPerPrice		
        this.radioDealPerPrice.setText(resHelper.getString("radioDealPerPrice.text"));
        // radioStandardTotalPrice		
        this.radioStandardTotalPrice.setText(resHelper.getString("radioStandardTotalPrice.text"));
        // radioDealTotalPrice		
        this.radioDealTotalPrice.setText(resHelper.getString("radioDealTotalPrice.text"));
        // radioContactTotalPrice		
        this.radioContactTotalPrice.setText(resHelper.getString("radioContactTotalPrice.text"));
        // contPriceCompareMin		
        this.contPriceCompareMin.setBoundLabelText(resHelper.getString("contPriceCompareMin.boundLabelText"));		
        this.contPriceCompareMin.setBoundLabelLength(50);
        // txtMinPriceCount		
        this.txtMinPriceCount.setDataType(1);		
        this.txtMinPriceCount.setSupportedEmpty(true);		
        this.txtMinPriceCount.setPrecision(2);
        // contPriceCompareMax		
        this.contPriceCompareMax.setBoundLabelText(resHelper.getString("contPriceCompareMax.boundLabelText"));		
        this.contPriceCompareMax.setBoundLabelLength(50);
        // txtMaxPriceCount		
        this.txtMaxPriceCount.setDataType(1);		
        this.txtMaxPriceCount.setSupportedEmpty(true);		
        this.txtMaxPriceCount.setPrecision(2);
        // containerDimension		
        this.containerDimension.setTitle(resHelper.getString("containerDimension.title"));
        // radioBuilding		
        this.radioBuilding.setText(resHelper.getString("radioBuilding.text"));
        // radioFloor		
        this.radioFloor.setText(resHelper.getString("radioFloor.text"));
        // radioHouseProperty		
        this.radioHouseProperty.setText(resHelper.getString("radioHouseProperty.text"));
        // radioRoomModelType		
        this.radioRoomModelType.setText(resHelper.getString("radioRoomModelType.text"));
        // radioRoomModel		
        this.radioRoomModel.setText(resHelper.getString("radioRoomModel.text"));
        // radioProductDetail		
        this.radioProductDetail.setText(resHelper.getString("radioProductDetail.text"));
        // radioProductType		
        this.radioProductType.setText(resHelper.getString("radioProductType.text"));
        // radioBuildingProperty		
        this.radioBuildingProperty.setText(resHelper.getString("radioBuildingProperty.text"));
        // radioRoomForm		
        this.radioRoomForm.setText(resHelper.getString("radioRoomForm.text"));
        // radioSight		
        this.radioSight.setText(resHelper.getString("radioSight.text"));
        // radioDirection		
        this.radioDirection.setText(resHelper.getString("radioDirection.text"));
        // radioRoomNoDimension		
        this.radioRoomNoDimension.setText(resHelper.getString("radioRoomNoDimension.text"));		
        this.radioRoomNoDimension.setSelected(true);
        // contCustomerType		
        this.contCustomerType.setBoundLabelText(resHelper.getString("contCustomerType.boundLabelText"));		
        this.contCustomerType.setBoundLabelLength(100);		
        this.contCustomerType.setBoundLabelUnderline(true);
        // comboCustomerType		
        this.comboCustomerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum").toArray());		
        this.comboCustomerType.setSelectedIndex(0);
        this.comboCustomerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    boxCustomerType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSex		
        this.contSex.setBoundLabelText(resHelper.getString("contSex.boundLabelText"));		
        this.contSex.setBoundLabelUnderline(true);		
        this.contSex.setBoundLabelLength(100);		
        this.contSex.setVisible(false);
        // comboSex		
        this.comboSex.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());		
        this.comboSex.setSelectedIndex(0);
        // contIndustry		
        this.contIndustry.setBoundLabelText(resHelper.getString("contIndustry.boundLabelText"));		
        this.contIndustry.setBoundLabelLength(100);		
        this.contIndustry.setBoundLabelUnderline(true);		
        this.contIndustry.setVisible(false);
        // prmtIndustry		
        this.prmtIndustry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.IndustryQuery");		
        this.prmtIndustry.setCommitFormat("$number$");		
        this.prmtIndustry.setDisplayFormat("$name$");		
        this.prmtIndustry.setEditFormat("$number$");		
        this.prmtIndustry.setEditable(true);
        // contCustomerDegree		
        this.contCustomerDegree.setBoundLabelText(resHelper.getString("contCustomerDegree.boundLabelText"));		
        this.contCustomerDegree.setBoundLabelLength(100);		
        this.contCustomerDegree.setBoundLabelUnderline(true);
        // promptCustomerDegree		
        this.promptCustomerDegree.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerGradeQuery");		
        this.promptCustomerDegree.setDisplayFormat("$name$");		
        this.promptCustomerDegree.setEditFormat("$number$");		
        this.promptCustomerDegree.setCommitFormat("$number$");
        // contCustomerOrigin		
        this.contCustomerOrigin.setBoundLabelText(resHelper.getString("contCustomerOrigin.boundLabelText"));		
        this.contCustomerOrigin.setBoundLabelUnderline(true);		
        this.contCustomerOrigin.setBoundLabelLength(100);
        // prmtCustomerOrigin		
        this.prmtCustomerOrigin.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerOriginQuery");		
        this.prmtCustomerOrigin.setDisplayFormat("$name$");		
        this.prmtCustomerOrigin.setEditFormat("$number$");		
        this.prmtCustomerOrigin.setCommitFormat("$number$");
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelLength(100);		
        this.contProvince.setBoundLabelUnderline(true);
        // prmtProvince		
        this.prmtProvince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");		
        this.prmtProvince.setDisplayFormat("$name$");		
        this.prmtProvince.setEditFormat("$number$");		
        this.prmtProvince.setCommitFormat("$number$");
        // contHabitation		
        this.contHabitation.setBoundLabelText(resHelper.getString("contHabitation.boundLabelText"));		
        this.contHabitation.setBoundLabelUnderline(true);		
        this.contHabitation.setBoundLabelLength(100);
        // prmtHabitation		
        this.prmtHabitation.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HabitationAreaQuery");		
        this.prmtHabitation.setDisplayFormat("$name$");		
        this.prmtHabitation.setEditFormat("$number$");		
        this.prmtHabitation.setCommitFormat("$number$");
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelUnderline(true);		
        this.contSaleMan.setBoundLabelLength(100);
        // prmtSaleMan		
        this.prmtSaleMan.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtSaleMan.setDisplayFormat("$name$");		
        this.prmtSaleMan.setEditFormat("$number$");		
        this.prmtSaleMan.setCommitFormat("$number$");
        this.prmtSaleMan.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7salesman_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDContainerCtreatTime		
        this.kDContainerCtreatTime.setTitle(resHelper.getString("kDContainerCtreatTime.title"));		
        this.kDContainerCtreatTime.setEnableActive(false);
        // contMinCustData		
        this.contMinCustData.setBoundLabelText(resHelper.getString("contMinCustData.boundLabelText"));		
        this.contMinCustData.setBoundLabelLength(50);		
        this.contMinCustData.setBoundLabelUnderline(true);
        // minCustDate
        // contMaxCustData		
        this.contMaxCustData.setBoundLabelText(resHelper.getString("contMaxCustData.boundLabelText"));		
        this.contMaxCustData.setBoundLabelLength(50);		
        this.contMaxCustData.setBoundLabelUnderline(true);
        // maxCustDate
        // kDContainerDimension		
        this.kDContainerDimension.setTitle(resHelper.getString("kDContainerDimension.title"));		
        this.kDContainerDimension.setEnableActive(false);
        // radioCustomerType		
        this.radioCustomerType.setText(resHelper.getString("radioCustomerType.text"));
        // radioCustomerDegree		
        this.radioCustomerDegree.setText(resHelper.getString("radioCustomerDegree.text"));
        // cadioCustomerOrigin		
        this.cadioCustomerOrigin.setText(resHelper.getString("cadioCustomerOrigin.text"));
        // radioCreator		
        this.radioCreator.setText(resHelper.getString("radioCreator.text"));
        // radioSaleMan		
        this.radioSaleMan.setText(resHelper.getString("radioSaleMan.text"));
        // radioTradeTimes		
        this.radioTradeTimes.setText(resHelper.getString("radioTradeTimes.text"));
        // radioProvince		
        this.radioProvince.setText(resHelper.getString("radioProvince.text"));
        // radioHabitationArea		
        this.radioHabitationArea.setText(resHelper.getString("radioHabitationArea.text"));
        // radioSex		
        this.radioSex.setText(resHelper.getString("radioSex.text"));
        // radioFamilyEarning		
        this.radioFamilyEarning.setText(resHelper.getString("radioFamilyEarning.text"));
        // radioFirstReception		
        this.radioFirstReception.setText(resHelper.getString("radioFirstReception.text"));
        // radioCustNoDimension		
        this.radioCustNoDimension.setText(resHelper.getString("radioCustNoDimension.text"));		
        this.radioCustNoDimension.setSelected(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // contTradeTimes		
        this.contTradeTimes.setBoundLabelText(resHelper.getString("contTradeTimes.boundLabelText"));		
        this.contTradeTimes.setBoundLabelLength(100);		
        this.contTradeTimes.setBoundLabelUnderline(true);
        // txtTradeTimes		
        this.txtTradeTimes.setSupportedEmpty(true);
        // radioEnterpriceSize		
        this.radioEnterpriceSize.setText(resHelper.getString("radioEnterpriceSize.text"));
        // radioIndustry		
        this.radioIndustry.setText(resHelper.getString("radioIndustry.text"));
        // areaGroup
        this.areaGroup.add(this.radioBuildingArea);
        this.areaGroup.add(this.radioRoomArea);
        // roomGroup
        this.roomGroup.add(this.radioBuilding);
        this.roomGroup.add(this.radioFloor);
        this.roomGroup.add(this.radioHouseProperty);
        this.roomGroup.add(this.radioRoomModelType);
        this.roomGroup.add(this.radioRoomModel);
        this.roomGroup.add(this.radioProductDetail);
        this.roomGroup.add(this.radioProductType);
        this.roomGroup.add(this.radioBuildingProperty);
        this.roomGroup.add(this.radioRoomForm);
        this.roomGroup.add(this.radioSight);
        this.roomGroup.add(this.radioDirection);
        this.roomGroup.add(this.radioRoomNoDimension);
        // priceGroup
        this.priceGroup.add(this.radioBuildPerPrice);
        this.priceGroup.add(this.radioRoomPerPrice);
        this.priceGroup.add(this.radioDealPerPrice);
        this.priceGroup.add(this.radioStandardTotalPrice);
        this.priceGroup.add(this.radioDealTotalPrice);
        this.priceGroup.add(this.radioContactTotalPrice);
        // customerGroup
        this.customerGroup.add(this.radioCustomerType);
        this.customerGroup.add(this.radioCustomerDegree);
        this.customerGroup.add(this.cadioCustomerOrigin);
        this.customerGroup.add(this.radioCreator);
        this.customerGroup.add(this.radioSaleMan);
        this.customerGroup.add(this.radioTradeTimes);
        this.customerGroup.add(this.radioProvince);
        this.customerGroup.add(this.radioHabitationArea);
        this.customerGroup.add(this.radioSex);
        this.customerGroup.add(this.radioFamilyEarning);
        this.customerGroup.add(this.radioFirstReception);
        this.customerGroup.add(this.radioCustNoDimension);
        this.customerGroup.add(this.radioEnterpriceSize);
        this.customerGroup.add(this.radioIndustry);
        this.customerGroup.add(this.radioCustOriType);
        // checkOnlyMainCustomer		
        this.checkOnlyMainCustomer.setText(resHelper.getString("checkOnlyMainCustomer.text"));
        // radioCustOriType		
        this.radioCustOriType.setText(resHelper.getString("radioCustOriType.text"));
        this.radioCustOriType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioCustOriType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 460));
        this.setLayout(null);
        tabPane.setBounds(new Rectangle(6, 6, 589, 449));
        this.add(tabPane, null);
        //tabPane
        tabPane.add(kDPanelRoom, resHelper.getString("kDPanelRoom.constraints"));
        tabPane.add(kDPanelCustomer, resHelper.getString("kDPanelCustomer.constraints"));
        //kDPanelRoom
        kDPanelRoom.setLayout(null);        contSellProject.setBounds(new Rectangle(19, 9, 250, 19));
        kDPanelRoom.add(contSellProject, null);
        contBuilding.setBounds(new Rectangle(314, 9, 250, 19));
        kDPanelRoom.add(contBuilding, null);
        contRoomModel.setBounds(new Rectangle(19, 35, 250, 19));
        kDPanelRoom.add(contRoomModel, null);
        contRoomModelType.setBounds(new Rectangle(314, 35, 250, 19));
        kDPanelRoom.add(contRoomModelType, null);
        contProductDetail.setBounds(new Rectangle(19, 61, 250, 19));
        kDPanelRoom.add(contProductDetail, null);
        contProductType.setBounds(new Rectangle(314, 61, 250, 19));
        kDPanelRoom.add(contProductType, null);
        contBuildingProperty.setBounds(new Rectangle(19, 87, 250, 19));
        kDPanelRoom.add(contBuildingProperty, null);
        contRoomForm.setBounds(new Rectangle(314, 87, 250, 19));
        kDPanelRoom.add(contRoomForm, null);
        contSight.setBounds(new Rectangle(19, 113, 250, 19));
        kDPanelRoom.add(contSight, null);
        contDirection.setBounds(new Rectangle(314, 113, 250, 19));
        kDPanelRoom.add(contDirection, null);
        kDContainerArea.setBounds(new Rectangle(5, 139, 560, 70));
        kDPanelRoom.add(kDContainerArea, null);
        kdContainerPrice.setBounds(new Rectangle(5, 215, 560, 73));
        kDPanelRoom.add(kdContainerPrice, null);
        containerDimension.setBounds(new Rectangle(5, 293, 560, 124));
        kDPanelRoom.add(containerDimension, null);
        //contSellProject
        contSellProject.setBoundEditor(promptSellProject);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //contRoomModel
        contRoomModel.setBoundEditor(prmtRoomModel);
        //contRoomModelType
        contRoomModelType.setBoundEditor(prmtRoomModelType);
        //contProductDetail
        contProductDetail.setBoundEditor(prmtProductDetail);
        //contProductType
        contProductType.setBoundEditor(prmtProductType);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(prmtBuildingProperty);
        //contRoomForm
        contRoomForm.setBoundEditor(prmtRoomForm);
        //contSight
        contSight.setBoundEditor(prmtSight);
        //contDirection
        contDirection.setBoundEditor(prmtDirection);
        //kDContainerArea
        kDContainerArea.getContentPane().setLayout(null);        radioBuildingArea.setBounds(new Rectangle(168, 1, 87, 19));
        kDContainerArea.getContentPane().add(radioBuildingArea, null);
        radioRoomArea.setBounds(new Rectangle(301, 1, 88, 19));
        kDContainerArea.getContentPane().add(radioRoomArea, null);
        contCompareMax.setBounds(new Rectangle(280, 21, 150, 19));
        kDContainerArea.getContentPane().add(contCompareMax, null);
        contCompareMin.setBounds(new Rectangle(100, 21, 150, 19));
        kDContainerArea.getContentPane().add(contCompareMin, null);
        //contCompareMax
        contCompareMax.setBoundEditor(txtMaxAreaCount);
        //contCompareMin
        contCompareMin.setBoundEditor(txtMinAreaCount);
        //kdContainerPrice
        kdContainerPrice.getContentPane().setLayout(null);        radioBuildPerPrice.setBounds(new Rectangle(9, 3, 82, 19));
        kdContainerPrice.getContentPane().add(radioBuildPerPrice, null);
        radioRoomPerPrice.setBounds(new Rectangle(99, 3, 82, 19));
        kdContainerPrice.getContentPane().add(radioRoomPerPrice, null);
        radioDealPerPrice.setBounds(new Rectangle(189, 3, 82, 19));
        kdContainerPrice.getContentPane().add(radioDealPerPrice, null);
        radioStandardTotalPrice.setBounds(new Rectangle(279, 3, 82, 19));
        kdContainerPrice.getContentPane().add(radioStandardTotalPrice, null);
        radioDealTotalPrice.setBounds(new Rectangle(369, 3, 82, 19));
        kdContainerPrice.getContentPane().add(radioDealTotalPrice, null);
        radioContactTotalPrice.setBounds(new Rectangle(459, 3, 82, 19));
        kdContainerPrice.getContentPane().add(radioContactTotalPrice, null);
        contPriceCompareMin.setBounds(new Rectangle(100, 23, 150, 19));
        kdContainerPrice.getContentPane().add(contPriceCompareMin, null);
        contPriceCompareMax.setBounds(new Rectangle(280, 23, 150, 19));
        kdContainerPrice.getContentPane().add(contPriceCompareMax, null);
        //contPriceCompareMin
        contPriceCompareMin.setBoundEditor(txtMinPriceCount);
        //contPriceCompareMax
        contPriceCompareMax.setBoundEditor(txtMaxPriceCount);
        //containerDimension
        containerDimension.getContentPane().setLayout(null);        radioBuilding.setBounds(new Rectangle(43, 8, 81, 19));
        containerDimension.getContentPane().add(radioBuilding, null);
        radioFloor.setBounds(new Rectangle(225, 8, 81, 19));
        containerDimension.getContentPane().add(radioFloor, null);
        radioHouseProperty.setBounds(new Rectangle(408, 8, 81, 19));
        containerDimension.getContentPane().add(radioHouseProperty, null);
        radioRoomModelType.setBounds(new Rectangle(43, 29, 81, 19));
        containerDimension.getContentPane().add(radioRoomModelType, null);
        radioRoomModel.setBounds(new Rectangle(225, 29, 81, 19));
        containerDimension.getContentPane().add(radioRoomModel, null);
        radioProductDetail.setBounds(new Rectangle(408, 29, 81, 19));
        containerDimension.getContentPane().add(radioProductDetail, null);
        radioProductType.setBounds(new Rectangle(43, 50, 81, 19));
        containerDimension.getContentPane().add(radioProductType, null);
        radioBuildingProperty.setBounds(new Rectangle(225, 50, 81, 19));
        containerDimension.getContentPane().add(radioBuildingProperty, null);
        radioRoomForm.setBounds(new Rectangle(408, 50, 81, 19));
        containerDimension.getContentPane().add(radioRoomForm, null);
        radioSight.setBounds(new Rectangle(43, 73, 81, 19));
        containerDimension.getContentPane().add(radioSight, null);
        radioDirection.setBounds(new Rectangle(225, 73, 81, 19));
        containerDimension.getContentPane().add(radioDirection, null);
        radioRoomNoDimension.setBounds(new Rectangle(408, 73, 81, 19));
        containerDimension.getContentPane().add(radioRoomNoDimension, null);
        //kDPanelCustomer
        kDPanelCustomer.setLayout(null);        contCustomerType.setBounds(new Rectangle(19, 18, 250, 19));
        kDPanelCustomer.add(contCustomerType, null);
        contSex.setBounds(new Rectangle(312, 18, 250, 19));
        kDPanelCustomer.add(contSex, null);
        contIndustry.setBounds(new Rectangle(312, 18, 250, 19));
        kDPanelCustomer.add(contIndustry, null);
        contCustomerDegree.setBounds(new Rectangle(19, 43, 250, 19));
        kDPanelCustomer.add(contCustomerDegree, null);
        contCustomerOrigin.setBounds(new Rectangle(312, 43, 250, 19));
        kDPanelCustomer.add(contCustomerOrigin, null);
        contProvince.setBounds(new Rectangle(19, 68, 250, 19));
        kDPanelCustomer.add(contProvince, null);
        contHabitation.setBounds(new Rectangle(312, 68, 250, 19));
        kDPanelCustomer.add(contHabitation, null);
        contSaleMan.setBounds(new Rectangle(19, 93, 250, 19));
        kDPanelCustomer.add(contSaleMan, null);
        kDContainerCtreatTime.setBounds(new Rectangle(5, 175, 561, 65));
        kDPanelCustomer.add(kDContainerCtreatTime, null);
        kDContainerDimension.setBounds(new Rectangle(5, 249, 561, 165));
        kDPanelCustomer.add(kDContainerDimension, null);
        contCreator.setBounds(new Rectangle(312, 93, 250, 19));
        kDPanelCustomer.add(contCreator, null);
        contTradeTimes.setBounds(new Rectangle(19, 120, 250, 19));
        kDPanelCustomer.add(contTradeTimes, null);
        checkOnlyMainCustomer.setBounds(new Rectangle(19, 150, 140, 19));
        kDPanelCustomer.add(checkOnlyMainCustomer, null);
        //contCustomerType
        contCustomerType.setBoundEditor(comboCustomerType);
        //contSex
        contSex.setBoundEditor(comboSex);
        //contIndustry
        contIndustry.setBoundEditor(prmtIndustry);
        //contCustomerDegree
        contCustomerDegree.setBoundEditor(promptCustomerDegree);
        //contCustomerOrigin
        contCustomerOrigin.setBoundEditor(prmtCustomerOrigin);
        //contProvince
        contProvince.setBoundEditor(prmtProvince);
        //contHabitation
        contHabitation.setBoundEditor(prmtHabitation);
        //contSaleMan
        contSaleMan.setBoundEditor(prmtSaleMan);
        //kDContainerCtreatTime
        kDContainerCtreatTime.getContentPane().setLayout(null);        contMinCustData.setBounds(new Rectangle(74, 10, 177, 19));
        kDContainerCtreatTime.getContentPane().add(contMinCustData, null);
        contMaxCustData.setBounds(new Rectangle(287, 10, 199, 19));
        kDContainerCtreatTime.getContentPane().add(contMaxCustData, null);
        //contMinCustData
        contMinCustData.setBoundEditor(minCustDate);
        //contMaxCustData
        contMaxCustData.setBoundEditor(maxCustDate);
        //kDContainerDimension
        kDContainerDimension.getContentPane().setLayout(null);        radioCustomerType.setBounds(new Rectangle(43, 6, 140, 19));
        kDContainerDimension.getContentPane().add(radioCustomerType, null);
        radioCustomerDegree.setBounds(new Rectangle(229, 6, 140, 19));
        kDContainerDimension.getContentPane().add(radioCustomerDegree, null);
        cadioCustomerOrigin.setBounds(new Rectangle(415, 33, 140, 19));
        kDContainerDimension.getContentPane().add(cadioCustomerOrigin, null);
        radioCreator.setBounds(new Rectangle(43, 33, 140, 19));
        kDContainerDimension.getContentPane().add(radioCreator, null);
        radioSaleMan.setBounds(new Rectangle(229, 33, 140, 19));
        kDContainerDimension.getContentPane().add(radioSaleMan, null);
        radioTradeTimes.setBounds(new Rectangle(415, 60, 140, 19));
        kDContainerDimension.getContentPane().add(radioTradeTimes, null);
        radioProvince.setBounds(new Rectangle(43, 60, 140, 19));
        kDContainerDimension.getContentPane().add(radioProvince, null);
        radioHabitationArea.setBounds(new Rectangle(229, 60, 140, 19));
        kDContainerDimension.getContentPane().add(radioHabitationArea, null);
        radioSex.setBounds(new Rectangle(229, 114, 140, 19));
        kDContainerDimension.getContentPane().add(radioSex, null);
        radioFamilyEarning.setBounds(new Rectangle(43, 114, 140, 19));
        kDContainerDimension.getContentPane().add(radioFamilyEarning, null);
        radioFirstReception.setBounds(new Rectangle(43, 85, 140, 19));
        kDContainerDimension.getContentPane().add(radioFirstReception, null);
        radioCustNoDimension.setBounds(new Rectangle(415, 85, 140, 19));
        kDContainerDimension.getContentPane().add(radioCustNoDimension, null);
        radioEnterpriceSize.setBounds(new Rectangle(43, 114, 140, 19));
        kDContainerDimension.getContentPane().add(radioEnterpriceSize, null);
        radioIndustry.setBounds(new Rectangle(229, 114, 140, 19));
        kDContainerDimension.getContentPane().add(radioIndustry, null);
        radioCustOriType.setBounds(new Rectangle(415, 6, 140, 19));
        kDContainerDimension.getContentPane().add(radioCustOriType, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contTradeTimes
        contTradeTimes.setBoundEditor(txtTradeTimes);

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
	    return "com.kingdee.eas.fdc.market.app.RptBarginOnAnalyseFilterUIHandler";
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
     * output prmtBuilding_dataChanged method
     */
    protected void prmtBuilding_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output promptSellProject_dataChanged method
     */
    protected void promptSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output boxCustomerType_itemStateChanged method
     */
    protected void boxCustomerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7salesman_dataChanged method
     */
    protected void f7salesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output radioCustOriType_actionPerformed method
     */
    protected void radioCustOriType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "RptBarginOnAnalyseFilterUI");
    }




}