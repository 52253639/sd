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
public abstract class AbstractCommerceChangeNewEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCommerceChangeNewEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcCustomerCommerceChange;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcYingXiang;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceChanceStage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurpose;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEffectiveDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProbability;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStopReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceSrc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conReason;
    protected com.kingdee.bos.ctrl.swing.KDLabel rate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contClassify;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChanceStage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleMan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPurpose;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEffectiveDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtWorth;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtProbability;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReason;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCommerceSrc;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtClassify;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSign;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAreaScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotal;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStayArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntentionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrientations;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSign;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAreaScope;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFloorScope;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTotal;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPriceScope;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtWorkArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtStayArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIntentionType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrientations;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcTrack;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcQuestion;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrack;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtQuestion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddQuestion;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo editData = null;
    protected ActionAddCustomer actionAddCustomer = null;
    /**
     * output class constructor
     */
    public AbstractCommerceChangeNewEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCommerceChangeNewEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddCustomer
        this.actionAddCustomer = new ActionAddCustomer(this);
        getActionManager().registerAction("actionAddCustomer", actionAddCustomer);
         this.actionAddCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kdcCustomerCommerceChange = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdcYingXiang = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceChanceStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurpose = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEffectiveDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProbability = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStopReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceSrc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rate = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contClassify = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCommerceChanceStage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSaleMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPurpose = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCommerceChangeReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkEffectiveDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtWorth = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtProbability = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCommerceSrc = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtClassify = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSign = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAreaScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotal = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStayArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntentionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrientations = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSign = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoomType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAreaScope = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFloorScope = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtTotal = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPriceScope = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtWorkArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtStayArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtIntentionType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrientations = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdcTrack = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdcQuestion = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdcRoom = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTrack = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdtQuestion = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddQuestion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblRoom = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnDelRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdcCustomerCommerceChange.setName("kdcCustomerCommerceChange");
        this.kdcYingXiang.setName("kdcYingXiang");
        this.kDPanel1.setName("kDPanel1");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contCustomer.setName("contCustomer");
        this.contLevel.setName("contLevel");
        this.contCommerceChanceStage.setName("contCommerceChanceStage");
        this.contSaleMan.setName("contSaleMan");
        this.contPurpose.setName("contPurpose");
        this.contCommerceChangeReason.setName("contCommerceChangeReason");
        this.contEffectiveDate.setName("contEffectiveDate");
        this.contWorth.setName("contWorth");
        this.contProbability.setName("contProbability");
        this.contStatus.setName("contStatus");
        this.contDescription.setName("contDescription");
        this.contStopReason.setName("contStopReason");
        this.contCommerceSrc.setName("contCommerceSrc");
        this.conReason.setName("conReason");
        this.rate.setName("rate");
        this.contClassify.setName("contClassify");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtLevel.setName("prmtLevel");
        this.prmtCommerceChanceStage.setName("prmtCommerceChanceStage");
        this.prmtSaleMan.setName("prmtSaleMan");
        this.prmtPurpose.setName("prmtPurpose");
        this.prmtCommerceChangeReason.setName("prmtCommerceChangeReason");
        this.pkEffectiveDate.setName("pkEffectiveDate");
        this.txtWorth.setName("txtWorth");
        this.txtProbability.setName("txtProbability");
        this.comboStatus.setName("comboStatus");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtReason.setName("prmtReason");
        this.comboCommerceSrc.setName("comboCommerceSrc");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtReason.setName("txtReason");
        this.prmtClassify.setName("prmtClassify");
        this.contSign.setName("contSign");
        this.contRoomType.setName("contRoomType");
        this.contAreaScope.setName("contAreaScope");
        this.contFloorScope.setName("contFloorScope");
        this.contTotal.setName("contTotal");
        this.contPriceScope.setName("contPriceScope");
        this.contWorkArea.setName("contWorkArea");
        this.contStayArea.setName("contStayArea");
        this.contIntentionType.setName("contIntentionType");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contOrientations.setName("contOrientations");
        this.contProductType.setName("contProductType");
        this.prmtSign.setName("prmtSign");
        this.prmtRoomType.setName("prmtRoomType");
        this.prmtAreaScope.setName("prmtAreaScope");
        this.prmtFloorScope.setName("prmtFloorScope");
        this.prmtTotal.setName("prmtTotal");
        this.prmtPriceScope.setName("prmtPriceScope");
        this.prmtWorkArea.setName("prmtWorkArea");
        this.prmtStayArea.setName("prmtStayArea");
        this.prmtIntentionType.setName("prmtIntentionType");
        this.prmtBuildingProperty.setName("prmtBuildingProperty");
        this.prmtOrientations.setName("prmtOrientations");
        this.prmtProductType.setName("prmtProductType");
        this.kdcTrack.setName("kdcTrack");
        this.kdcQuestion.setName("kdcQuestion");
        this.kdcRoom.setName("kdcRoom");
        this.tblTrack.setName("tblTrack");
        this.btnAddTrack.setName("btnAddTrack");
        this.kdtQuestion.setName("kdtQuestion");
        this.btnAddQuestion.setName("btnAddQuestion");
        this.tblRoom.setName("tblRoom");
        this.btnDelRoom.setName("btnDelRoom");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnAddCustomer.setName("btnAddCustomer");
        // CoreUI
        // kdcCustomerCommerceChange		
        this.kdcCustomerCommerceChange.setTitle(resHelper.getString("kdcCustomerCommerceChange.title"));
        // kdcYingXiang		
        this.kdcYingXiang.setTitle(resHelper.getString("kdcYingXiang.title"));
        // kDPanel1
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contLevel		
        this.contLevel.setBoundLabelText(resHelper.getString("contLevel.boundLabelText"));		
        this.contLevel.setBoundLabelLength(100);		
        this.contLevel.setBoundLabelUnderline(true);		
        this.contLevel.setEnabled(false);
        // contCommerceChanceStage		
        this.contCommerceChanceStage.setBoundLabelText(resHelper.getString("contCommerceChanceStage.boundLabelText"));		
        this.contCommerceChanceStage.setBoundLabelLength(100);		
        this.contCommerceChanceStage.setBoundLabelUnderline(true);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(100);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // contPurpose		
        this.contPurpose.setBoundLabelText(resHelper.getString("contPurpose.boundLabelText"));		
        this.contPurpose.setBoundLabelLength(100);		
        this.contPurpose.setBoundLabelUnderline(true);
        // contCommerceChangeReason		
        this.contCommerceChangeReason.setBoundLabelText(resHelper.getString("contCommerceChangeReason.boundLabelText"));		
        this.contCommerceChangeReason.setBoundLabelLength(100);		
        this.contCommerceChangeReason.setBoundLabelUnderline(true);
        // contEffectiveDate		
        this.contEffectiveDate.setBoundLabelText(resHelper.getString("contEffectiveDate.boundLabelText"));		
        this.contEffectiveDate.setBoundLabelLength(100);		
        this.contEffectiveDate.setBoundLabelUnderline(true);
        // contWorth		
        this.contWorth.setBoundLabelText(resHelper.getString("contWorth.boundLabelText"));		
        this.contWorth.setBoundLabelLength(100);		
        this.contWorth.setBoundLabelUnderline(true);
        // contProbability		
        this.contProbability.setBoundLabelText(resHelper.getString("contProbability.boundLabelText"));		
        this.contProbability.setBoundLabelLength(100);		
        this.contProbability.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contStopReason		
        this.contStopReason.setBoundLabelText(resHelper.getString("contStopReason.boundLabelText"));		
        this.contStopReason.setBoundLabelLength(100);		
        this.contStopReason.setBoundLabelUnderline(true);
        // contCommerceSrc		
        this.contCommerceSrc.setBoundLabelText(resHelper.getString("contCommerceSrc.boundLabelText"));		
        this.contCommerceSrc.setBoundLabelLength(100);		
        this.contCommerceSrc.setBoundLabelUnderline(true);
        // conReason		
        this.conReason.setBoundLabelText(resHelper.getString("conReason.boundLabelText"));		
        this.conReason.setBoundLabelLength(100);		
        this.conReason.setBoundLabelUnderline(true);
        // rate		
        this.rate.setText(resHelper.getString("rate.text"));
        // contClassify		
        this.contClassify.setBoundLabelText(resHelper.getString("contClassify.boundLabelText"));		
        this.contClassify.setBoundLabelLength(100);		
        this.contClassify.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // prmtCustomer		
        this.prmtCustomer.setRequired(true);		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerForCommerceChanceQuery");
        // prmtLevel		
        this.prmtLevel.setEnabled(false);
        // prmtCommerceChanceStage		
        this.prmtCommerceChanceStage.setRequired(true);
        // prmtSaleMan		
        this.prmtSaleMan.setRequired(true);		
        this.prmtSaleMan.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtSaleMan.setCommitFormat("$name$");		
        this.prmtSaleMan.setDisplayFormat("$name$");		
        this.prmtSaleMan.setEditFormat("$name$");
        // prmtPurpose
        // prmtCommerceChangeReason		
        this.prmtCommerceChangeReason.setRequired(true);
        // pkEffectiveDate		
        this.pkEffectiveDate.setRequired(true);
        // txtWorth		
        this.txtWorth.setDataType(1);		
        this.txtWorth.setPrecision(2);
        // txtProbability		
        this.txtProbability.setDataType(1);		
        this.txtProbability.setPrecision(0);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum").toArray());
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtReason
        // comboCommerceSrc		
        this.comboCommerceSrc.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum").toArray());
        // kDScrollPane2
        // txtReason		
        this.txtReason.setMaxLength(255);
        // prmtClassify		
        this.prmtClassify.setQueryInfo("com.kingdee.eas.fdc.market.app.ChannelTypeTreeQuery");		
        this.prmtClassify.setRequired(true);		
        this.prmtClassify.setEnabled(false);
        // contSign		
        this.contSign.setBoundLabelText(resHelper.getString("contSign.boundLabelText"));		
        this.contSign.setBoundLabelLength(100);		
        this.contSign.setBoundLabelUnderline(true);
        // contRoomType		
        this.contRoomType.setBoundLabelText(resHelper.getString("contRoomType.boundLabelText"));		
        this.contRoomType.setBoundLabelLength(100);		
        this.contRoomType.setBoundLabelUnderline(true);
        // contAreaScope		
        this.contAreaScope.setBoundLabelText(resHelper.getString("contAreaScope.boundLabelText"));		
        this.contAreaScope.setBoundLabelLength(100);		
        this.contAreaScope.setBoundLabelUnderline(true);
        // contFloorScope		
        this.contFloorScope.setBoundLabelText(resHelper.getString("contFloorScope.boundLabelText"));		
        this.contFloorScope.setBoundLabelLength(100);		
        this.contFloorScope.setBoundLabelUnderline(true);
        // contTotal		
        this.contTotal.setBoundLabelText(resHelper.getString("contTotal.boundLabelText"));		
        this.contTotal.setBoundLabelLength(100);		
        this.contTotal.setBoundLabelUnderline(true);
        // contPriceScope		
        this.contPriceScope.setBoundLabelText(resHelper.getString("contPriceScope.boundLabelText"));		
        this.contPriceScope.setBoundLabelLength(100);		
        this.contPriceScope.setBoundLabelUnderline(true);
        // contWorkArea		
        this.contWorkArea.setBoundLabelText(resHelper.getString("contWorkArea.boundLabelText"));		
        this.contWorkArea.setBoundLabelLength(100);		
        this.contWorkArea.setBoundLabelUnderline(true);
        // contStayArea		
        this.contStayArea.setBoundLabelText(resHelper.getString("contStayArea.boundLabelText"));		
        this.contStayArea.setBoundLabelLength(100);		
        this.contStayArea.setBoundLabelUnderline(true);
        // contIntentionType		
        this.contIntentionType.setBoundLabelText(resHelper.getString("contIntentionType.boundLabelText"));		
        this.contIntentionType.setBoundLabelLength(100);		
        this.contIntentionType.setBoundLabelUnderline(true);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // contOrientations		
        this.contOrientations.setBoundLabelText(resHelper.getString("contOrientations.boundLabelText"));		
        this.contOrientations.setBoundLabelLength(100);		
        this.contOrientations.setBoundLabelUnderline(true);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // prmtSign
        // prmtRoomType
        // prmtAreaScope
        // prmtFloorScope
        // prmtTotal
        // prmtPriceScope
        // prmtWorkArea		
        this.prmtWorkArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");		
        this.prmtWorkArea.setDisplayFormat("$name$");		
        this.prmtWorkArea.setEditFormat("$name$");		
        this.prmtWorkArea.setCommitFormat("$name$");
        // prmtStayArea		
        this.prmtStayArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");		
        this.prmtStayArea.setEditFormat("$name$");		
        this.prmtStayArea.setDisplayFormat("$name$");		
        this.prmtStayArea.setCommitFormat("$name$");
        // prmtIntentionType		
        this.prmtIntentionType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");		
        this.prmtIntentionType.setCommitFormat("$name$");		
        this.prmtIntentionType.setDisplayFormat("$name$");		
        this.prmtIntentionType.setEditFormat("$name$");
        this.prmtIntentionType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtIntentionType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtIntentionType.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtIntentionType_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBuildingProperty		
        this.prmtBuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
        // prmtOrientations		
        this.prmtOrientations.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // prmtProductType		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
        // kdcTrack		
        this.kdcTrack.setTitle(resHelper.getString("kdcTrack.title"));
        // kdcQuestion		
        this.kdcQuestion.setTitle(resHelper.getString("kdcQuestion.title"));
        // kdcRoom		
        this.kdcRoom.setTitle(resHelper.getString("kdcRoom.title"));
        // tblTrack
		String tblTrackStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"trackNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"trackDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"saleMan\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"trackMediaChannel\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"trackType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"trackState\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"trackContext\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{trackNumber}</t:Cell><t:Cell>$Resource{trackDate}</t:Cell><t:Cell>$Resource{saleMan}</t:Cell><t:Cell>$Resource{trackMediaChannel}</t:Cell><t:Cell>$Resource{trackType}</t:Cell><t:Cell>$Resource{trackState}</t:Cell><t:Cell>$Resource{trackContext}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTrack.setFormatXml(resHelper.translateString("tblTrack",tblTrackStrXML));
        this.tblTrack.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTrack_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnAddTrack		
        this.btnAddTrack.setText(resHelper.getString("btnAddTrack.text"));
        this.btnAddTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddTrack_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdtQuestion
		String kdtQuestionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"quesNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quesChoose\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quesDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quesPerson\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quesRemark\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{quesNumber}</t:Cell><t:Cell>$Resource{quesChoose}</t:Cell><t:Cell>$Resource{quesDate}</t:Cell><t:Cell>$Resource{quesPerson}</t:Cell><t:Cell>$Resource{quesRemark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtQuestion.setFormatXml(resHelper.translateString("kdtQuestion",kdtQuestionStrXML));
        this.kdtQuestion.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtQuestion_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnAddQuestion		
        this.btnAddQuestion.setText(resHelper.getString("btnAddQuestion.text"));
        this.btnAddQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddQuestion_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblRoom
		String tblRoomStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{roomModel}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{standardPrice}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRoom.setFormatXml(resHelper.translateString("tblRoom",tblRoomStrXML));

        

        // btnDelRoom		
        this.btnDelRoom.setText(resHelper.getString("btnDelRoom.text"));
        this.btnDelRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddRoom		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        this.btnAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddCustomer
        this.btnAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        kdcCustomerCommerceChange.setBounds(new Rectangle(11, 7, 978, 177));
        this.add(kdcCustomerCommerceChange, null);
        kdcYingXiang.setBounds(new Rectangle(11, 185, 978, 64));
        this.add(kdcYingXiang, null);
        kDPanel1.setBounds(new Rectangle(3, 247, 1002, 380));
        this.add(kDPanel1, null);
        //kdcCustomerCommerceChange
        kdcCustomerCommerceChange.getContentPane().setLayout(null);        contNumber.setBounds(new Rectangle(5, 7, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contNumber, null);
        contName.setBounds(new Rectangle(693, 7, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contName, null);
        contCustomer.setBounds(new Rectangle(364, 7, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contCustomer, null);
        contLevel.setBounds(new Rectangle(5, 51, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contLevel, null);
        contCommerceChanceStage.setBounds(new Rectangle(364, 29, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contCommerceChanceStage, null);
        contSaleMan.setBounds(new Rectangle(693, 29, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contSaleMan, null);
        contPurpose.setBounds(new Rectangle(456, 223, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contPurpose, null);
        contCommerceChangeReason.setBounds(new Rectangle(609, 196, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contCommerceChangeReason, null);
        contEffectiveDate.setBounds(new Rectangle(364, 73, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contEffectiveDate, null);
        contWorth.setBounds(new Rectangle(324, 191, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contWorth, null);
        contProbability.setBounds(new Rectangle(6, 197, 259, 19));
        kdcCustomerCommerceChange.getContentPane().add(contProbability, null);
        contStatus.setBounds(new Rectangle(5, 73, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contStatus, null);
        contDescription.setBounds(new Rectangle(5, 95, 630, 50));
        kdcCustomerCommerceChange.getContentPane().add(contDescription, null);
        contStopReason.setBounds(new Rectangle(693, 51, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contStopReason, null);
        contCommerceSrc.setBounds(new Rectangle(5, 29, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contCommerceSrc, null);
        conReason.setBounds(new Rectangle(693, 95, 270, 50));
        kdcCustomerCommerceChange.getContentPane().add(conReason, null);
        rate.setBounds(new Rectangle(270, 198, 24, 19));
        kdcCustomerCommerceChange.getContentPane().add(rate, null);
        contClassify.setBounds(new Rectangle(364, 51, 270, 19));
        kdcCustomerCommerceChange.getContentPane().add(contClassify, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contLevel
        contLevel.setBoundEditor(prmtLevel);
        //contCommerceChanceStage
        contCommerceChanceStage.setBoundEditor(prmtCommerceChanceStage);
        //contSaleMan
        contSaleMan.setBoundEditor(prmtSaleMan);
        //contPurpose
        contPurpose.setBoundEditor(prmtPurpose);
        //contCommerceChangeReason
        contCommerceChangeReason.setBoundEditor(prmtCommerceChangeReason);
        //contEffectiveDate
        contEffectiveDate.setBoundEditor(pkEffectiveDate);
        //contWorth
        contWorth.setBoundEditor(txtWorth);
        //contProbability
        contProbability.setBoundEditor(txtProbability);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contStopReason
        contStopReason.setBoundEditor(prmtReason);
        //contCommerceSrc
        contCommerceSrc.setBoundEditor(comboCommerceSrc);
        //conReason
        conReason.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtReason, null);
        //contClassify
        contClassify.setBoundEditor(prmtClassify);
        //kdcYingXiang
        kdcYingXiang.getContentPane().setLayout(null);        contSign.setBounds(new Rectangle(8, 47, 270, 19));
        kdcYingXiang.getContentPane().add(contSign, null);
        contRoomType.setBounds(new Rectangle(354, 47, 270, 19));
        kdcYingXiang.getContentPane().add(contRoomType, null);
        contAreaScope.setBounds(new Rectangle(696, 47, 270, 19));
        kdcYingXiang.getContentPane().add(contAreaScope, null);
        contFloorScope.setBounds(new Rectangle(12, 71, 270, 19));
        kdcYingXiang.getContentPane().add(contFloorScope, null);
        contTotal.setBounds(new Rectangle(354, 83, 270, 19));
        kdcYingXiang.getContentPane().add(contTotal, null);
        contPriceScope.setBounds(new Rectangle(696, 71, 270, 19));
        kdcYingXiang.getContentPane().add(contPriceScope, null);
        contWorkArea.setBounds(new Rectangle(7, 12, 270, 19));
        kdcYingXiang.getContentPane().add(contWorkArea, null);
        contStayArea.setBounds(new Rectangle(362, 12, 270, 19));
        kdcYingXiang.getContentPane().add(contStayArea, null);
        contIntentionType.setBounds(new Rectangle(694, 12, 270, 19));
        kdcYingXiang.getContentPane().add(contIntentionType, null);
        contBuildingProperty.setBounds(new Rectangle(353, 109, 270, 19));
        kdcYingXiang.getContentPane().add(contBuildingProperty, null);
        contOrientations.setBounds(new Rectangle(696, 91, 270, 19));
        kdcYingXiang.getContentPane().add(contOrientations, null);
        contProductType.setBounds(new Rectangle(10, 92, 270, 19));
        kdcYingXiang.getContentPane().add(contProductType, null);
        //contSign
        contSign.setBoundEditor(prmtSign);
        //contRoomType
        contRoomType.setBoundEditor(prmtRoomType);
        //contAreaScope
        contAreaScope.setBoundEditor(prmtAreaScope);
        //contFloorScope
        contFloorScope.setBoundEditor(prmtFloorScope);
        //contTotal
        contTotal.setBoundEditor(prmtTotal);
        //contPriceScope
        contPriceScope.setBoundEditor(prmtPriceScope);
        //contWorkArea
        contWorkArea.setBoundEditor(prmtWorkArea);
        //contStayArea
        contStayArea.setBoundEditor(prmtStayArea);
        //contIntentionType
        contIntentionType.setBoundEditor(prmtIntentionType);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(prmtBuildingProperty);
        //contOrientations
        contOrientations.setBoundEditor(prmtOrientations);
        //contProductType
        contProductType.setBoundEditor(prmtProductType);
        //kDPanel1
        kDPanel1.setLayout(null);        kdcTrack.setBounds(new Rectangle(7, 5, 978, 167));
        kDPanel1.add(kdcTrack, null);
        kdcQuestion.setBounds(new Rectangle(511, 176, 475, 198));
        kDPanel1.add(kdcQuestion, null);
        kdcRoom.setBounds(new Rectangle(7, 176, 501, 198));
        kDPanel1.add(kdcRoom, null);
        //kdcTrack
        kdcTrack.getContentPane().setLayout(null);        tblTrack.setBounds(new Rectangle(3, 24, 969, 113));
        kdcTrack.getContentPane().add(tblTrack, null);
        btnAddTrack.setBounds(new Rectangle(878, 2, 83, 19));
        kdcTrack.getContentPane().add(btnAddTrack, null);
        //kdcQuestion
        kdcQuestion.getContentPane().setLayout(null);        kdtQuestion.setBounds(new Rectangle(4, 24, 447, 145));
        kdcQuestion.getContentPane().add(kdtQuestion, null);
        btnAddQuestion.setBounds(new Rectangle(368, 2, 83, 19));
        kdcQuestion.getContentPane().add(btnAddQuestion, null);
        //kdcRoom
        kdcRoom.getContentPane().setLayout(null);        tblRoom.setBounds(new Rectangle(2, 22, 484, 147));
        kdcRoom.getContentPane().add(tblRoom, null);
        btnDelRoom.setBounds(new Rectangle(403, 1, 83, 19));
        kdcRoom.getContentPane().add(btnDelRoom, null);
        btnAddRoom.setBounds(new Rectangle(314, 1, 83, 19));
        kdcRoom.getContentPane().add(btnAddRoom, null);

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
        this.toolBar.add(btnAddCustomer);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("customer", com.kingdee.eas.fdc.sellhouse.SHECustomerInfo.class, this.prmtCustomer, "data");
		dataBinder.registerBinding("newLevel", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtLevel, "data");
		dataBinder.registerBinding("commerceChanceStage", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtCommerceChanceStage, "data");
		dataBinder.registerBinding("saleMan", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSaleMan, "data");
		dataBinder.registerBinding("purpose", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtPurpose, "data");
		dataBinder.registerBinding("commerceChangeReason", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtCommerceChangeReason, "data");
		dataBinder.registerBinding("effectiveDate", java.util.Date.class, this.pkEffectiveDate, "value");
		dataBinder.registerBinding("worth", java.math.BigDecimal.class, this.txtWorth, "value");
		dataBinder.registerBinding("probability", java.math.BigDecimal.class, this.txtProbability, "value");
		dataBinder.registerBinding("status", com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("commerceSrc", com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum.class, this.comboCommerceSrc, "selectedItem");
		dataBinder.registerBinding("classify", com.kingdee.eas.fdc.market.ChannelTypeInfo.class, this.prmtClassify, "data");
		dataBinder.registerBinding("sign", com.kingdee.eas.fdc.sellhouse.SightRequirementInfo.class, this.prmtSign, "data");
		dataBinder.registerBinding("roomType", com.kingdee.eas.fdc.sellhouse.RoomModelInfo.class, this.prmtRoomType, "data");
		dataBinder.registerBinding("areaScope", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtAreaScope, "data");
		dataBinder.registerBinding("floorScope", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtFloorScope, "data");
		dataBinder.registerBinding("total", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtTotal, "data");
		dataBinder.registerBinding("priceScope", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtPriceScope, "data");
		dataBinder.registerBinding("workArea", com.kingdee.eas.basedata.assistant.RegionInfo.class, this.prmtWorkArea, "data");
		dataBinder.registerBinding("stayArea", com.kingdee.eas.basedata.assistant.RegionInfo.class, this.prmtStayArea, "data");
		dataBinder.registerBinding("intentionType", com.kingdee.eas.fdc.sellhouse.RoomModelInfo.class, this.prmtIntentionType, "data");
		dataBinder.registerBinding("buildingProperty", com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo.class, this.prmtBuildingProperty, "data");
		dataBinder.registerBinding("orientations", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtOrientations, "data");
		dataBinder.registerBinding("productType", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.prmtProductType, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CommerceChangeNewEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceChanceStage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceChangeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("worth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("probability", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceSrc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("classify", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sign", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("areaScope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("floorScope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("total", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceScope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stayArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intentionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildingProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orientations", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("productType", ValidateHelper.ON_SAVE);    		
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
     * output prmtIntentionType_willShow method
     */
    protected void prmtIntentionType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtIntentionType_willCommit method
     */
    protected void prmtIntentionType_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output tblTrack_tableClicked method
     */
    protected void tblTrack_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnAddTrack_actionPerformed method
     */
    protected void btnAddTrack_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtQuestion_tableClicked method
     */
    protected void kdtQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnAddQuestion_actionPerformed method
     */
    protected void btnAddQuestion_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelRoom_actionPerformed method
     */
    protected void btnDelRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddRoom_actionPerformed method
     */
    protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("customer.*"));
        sic.add(new SelectorItemInfo("newLevel.*"));
        sic.add(new SelectorItemInfo("commerceChanceStage.*"));
        sic.add(new SelectorItemInfo("saleMan.*"));
        sic.add(new SelectorItemInfo("purpose.*"));
        sic.add(new SelectorItemInfo("commerceChangeReason.*"));
        sic.add(new SelectorItemInfo("effectiveDate"));
        sic.add(new SelectorItemInfo("worth"));
        sic.add(new SelectorItemInfo("probability"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("commerceSrc"));
        sic.add(new SelectorItemInfo("classify.*"));
        sic.add(new SelectorItemInfo("sign.*"));
        sic.add(new SelectorItemInfo("roomType.*"));
        sic.add(new SelectorItemInfo("areaScope.*"));
        sic.add(new SelectorItemInfo("floorScope.*"));
        sic.add(new SelectorItemInfo("total.*"));
        sic.add(new SelectorItemInfo("priceScope.*"));
        sic.add(new SelectorItemInfo("workArea.*"));
        sic.add(new SelectorItemInfo("stayArea.*"));
        sic.add(new SelectorItemInfo("intentionType.*"));
        sic.add(new SelectorItemInfo("buildingProperty.*"));
        sic.add(new SelectorItemInfo("orientations.*"));
        sic.add(new SelectorItemInfo("productType.*"));
        return sic;
    }        
    	

    /**
     * output actionAddCustomer_actionPerformed method
     */
    public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCustomer() {
    	return false;
    }

    /**
     * output ActionAddCustomer class
     */     
    protected class ActionAddCustomer extends ItemAction {     
    
        public ActionAddCustomer()
        {
            this(null);
        }

        public ActionAddCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChangeNewEditUI.this, "ActionAddCustomer", "actionAddCustomer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CommerceChangeNewEditUI");
    }




}