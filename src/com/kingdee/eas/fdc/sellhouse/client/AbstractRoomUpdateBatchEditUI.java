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
public abstract class AbstractRoomUpdateBatchEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomUpdateBatchEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomForm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeliverHouseStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductDetail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEyeSignht;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoise;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPosseStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDecoraStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWindow;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomUsage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuildStruct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7roomForm;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFitmentStandard;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDeliverHouseStandard;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductDetail;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7EyeSignht;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Noise;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PosseStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7DecoraStd;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboWindow;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelLine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomUsage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildStruct;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected ActionSubmit actionSubmit = null;
    protected ActionBtnDelLine actionBtnDelLine = null;
    protected ActionAddLine actionAddLine = null;
    /**
     * output class constructor
     */
    public AbstractRoomUpdateBatchEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomUpdateBatchEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnDelLine
        this.actionBtnDelLine = new ActionBtnDelLine(this);
        getActionManager().registerAction("actionBtnDelLine", actionBtnDelLine);
         this.actionBtnDelLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDeliverHouseStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductDetail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEyeSignht = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoise = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPosseStd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDecoraStd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWindow = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contRoomUsage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbuildStruct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7roomForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFitmentStandard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDeliverHouseStandard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ProductDetail = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7EyeSignht = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Noise = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7PosseStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7DecoraStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboWindow = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.f7RoomUsage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuildStruct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contFace.setName("contFace");
        this.contFloorHeight.setName("contFloorHeight");
        this.contRoomForm.setName("contRoomForm");
        this.contOutlook.setName("contOutlook");
        this.contFitmentStandard.setName("contFitmentStandard");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contDeliverHouseStandard.setName("contDeliverHouseStandard");
        this.contProductType.setName("contProductType");
        this.contProductDetail.setName("contProductDetail");
        this.contHouseProperty.setName("contHouseProperty");
        this.contEyeSignht.setName("contEyeSignht");
        this.contNoise.setName("contNoise");
        this.contPosseStd.setName("contPosseStd");
        this.contDecoraStd.setName("contDecoraStd");
        this.contWindow.setName("contWindow");
        this.kDContainer1.setName("kDContainer1");
        this.kDSeparator2.setName("kDSeparator2");
        this.contRoomUsage.setName("contRoomUsage");
        this.contRoomModel.setName("contRoomModel");
        this.contbuildStruct.setName("contbuildStruct");
        this.f7Direction.setName("f7Direction");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.f7roomForm.setName("f7roomForm");
        this.f7Sight.setName("f7Sight");
        this.txtFitmentStandard.setName("txtFitmentStandard");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.txtDeliverHouseStandard.setName("txtDeliverHouseStandard");
        this.f7ProductType.setName("f7ProductType");
        this.f7ProductDetail.setName("f7ProductDetail");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.f7EyeSignht.setName("f7EyeSignht");
        this.f7Noise.setName("f7Noise");
        this.f7PosseStd.setName("f7PosseStd");
        this.f7DecoraStd.setName("f7DecoraStd");
        this.comboWindow.setName("comboWindow");
        this.tblMain.setName("tblMain");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnDelLine.setName("btnDelLine");
        this.f7RoomUsage.setName("f7RoomUsage");
        this.prmtRoomModel.setName("prmtRoomModel");
        this.prmtBuildStruct.setName("prmtBuildStruct");
        this.btnSubmit.setName("btnSubmit");
        // CoreUI		
        this.setPreferredSize(new Dimension(600,500));
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelLength(100);		
        this.contFace.setBoundLabelUnderline(true);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(100);		
        this.contFloorHeight.setBoundLabelUnderline(true);
        // contRoomForm		
        this.contRoomForm.setBoundLabelText(resHelper.getString("contRoomForm.boundLabelText"));		
        this.contRoomForm.setBoundLabelLength(100);		
        this.contRoomForm.setBoundLabelUnderline(true);
        // contOutlook		
        this.contOutlook.setBoundLabelText(resHelper.getString("contOutlook.boundLabelText"));		
        this.contOutlook.setBoundLabelLength(100);		
        this.contOutlook.setBoundLabelUnderline(true);
        // contFitmentStandard		
        this.contFitmentStandard.setBoundLabelText(resHelper.getString("contFitmentStandard.boundLabelText"));		
        this.contFitmentStandard.setBoundLabelUnderline(true);		
        this.contFitmentStandard.setBoundLabelLength(100);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelUnderline(true);		
        this.contBuildingProperty.setBoundLabelLength(100);
        // contDeliverHouseStandard		
        this.contDeliverHouseStandard.setBoundLabelText(resHelper.getString("contDeliverHouseStandard.boundLabelText"));		
        this.contDeliverHouseStandard.setBoundLabelLength(100);		
        this.contDeliverHouseStandard.setBoundLabelUnderline(true);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelUnderline(true);		
        this.contProductType.setBoundLabelLength(100);
        // contProductDetail		
        this.contProductDetail.setBoundLabelText(resHelper.getString("contProductDetail.boundLabelText"));		
        this.contProductDetail.setBoundLabelUnderline(true);		
        this.contProductDetail.setBoundLabelLength(100);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelUnderline(true);		
        this.contHouseProperty.setBoundLabelLength(100);
        // contEyeSignht		
        this.contEyeSignht.setBoundLabelText(resHelper.getString("contEyeSignht.boundLabelText"));		
        this.contEyeSignht.setBoundLabelUnderline(true);		
        this.contEyeSignht.setBoundLabelLength(100);
        // contNoise		
        this.contNoise.setBoundLabelText(resHelper.getString("contNoise.boundLabelText"));		
        this.contNoise.setBoundLabelUnderline(true);		
        this.contNoise.setBoundLabelLength(100);
        // contPosseStd		
        this.contPosseStd.setBoundLabelText(resHelper.getString("contPosseStd.boundLabelText"));		
        this.contPosseStd.setBoundLabelUnderline(true);		
        this.contPosseStd.setBoundLabelLength(100);
        // contDecoraStd		
        this.contDecoraStd.setBoundLabelText(resHelper.getString("contDecoraStd.boundLabelText"));		
        this.contDecoraStd.setBoundLabelUnderline(true);		
        this.contDecoraStd.setBoundLabelLength(100);
        // contWindow		
        this.contWindow.setBoundLabelText(resHelper.getString("contWindow.boundLabelText"));		
        this.contWindow.setBoundLabelUnderline(true);		
        this.contWindow.setBoundLabelLength(100);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDSeparator2
        // contRoomUsage		
        this.contRoomUsage.setBoundLabelText(resHelper.getString("contRoomUsage.boundLabelText"));		
        this.contRoomUsage.setBoundLabelLength(100);		
        this.contRoomUsage.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // contbuildStruct		
        this.contbuildStruct.setBoundLabelText(resHelper.getString("contbuildStruct.boundLabelText"));		
        this.contbuildStruct.setBoundLabelLength(100);		
        this.contbuildStruct.setBoundLabelUnderline(true);
        // f7Direction		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");		
        this.f7Direction.setCommitFormat("$number$");		
        this.f7Direction.setDisplayFormat("$name$");		
        this.f7Direction.setEditFormat("$number$");
        // txtFloorHeight
        // f7roomForm		
        this.f7roomForm.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery");		
        this.f7roomForm.setCommitFormat("$number$");		
        this.f7roomForm.setDisplayFormat("$name$");		
        this.f7roomForm.setEditFormat("$number$");
        // f7Sight		
        this.f7Sight.setDisplayFormat("$name$");		
        this.f7Sight.setEditFormat("$number$");		
        this.f7Sight.setCommitFormat("$number$");		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // txtFitmentStandard		
        this.txtFitmentStandard.setMaxLength(40);
        // f7BuildingProperty		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
        // txtDeliverHouseStandard		
        this.txtDeliverHouseStandard.setMaxLength(40);
        // f7ProductType		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
        // f7ProductDetail		
        this.f7ProductDetail.setDisplayFormat("$name$");		
        this.f7ProductDetail.setEditFormat("$number$");		
        this.f7ProductDetail.setCommitFormat("$number$");		
        this.f7ProductDetail.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
        // f7EyeSignht		
        this.f7EyeSignht.setDisplayFormat("$name$");		
        this.f7EyeSignht.setEditFormat("$number$");		
        this.f7EyeSignht.setCommitFormat("$number$");		
        this.f7EyeSignht.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // f7Noise		
        this.f7Noise.setDisplayFormat("$name$");		
        this.f7Noise.setEditFormat("$number$");		
        this.f7Noise.setCommitFormat("$number$");		
        this.f7Noise.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // f7PosseStd		
        this.f7PosseStd.setDisplayFormat("$name$");		
        this.f7PosseStd.setEditFormat("$number$");		
        this.f7PosseStd.setCommitFormat("$number$");		
        this.f7PosseStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // f7DecoraStd		
        this.f7DecoraStd.setDisplayFormat("$name$");		
        this.f7DecoraStd.setEditFormat("$number$");		
        this.f7DecoraStd.setCommitFormat("$number$");		
        this.f7DecoraStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // comboWindow		
        this.comboWindow.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum").toArray());
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomPropNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"floor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"serialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{roomPropNo}</t:Cell><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{serialNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

        // btnAddRoom
        this.btnAddRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        // btnDelLine
        this.btnDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnDelLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelLine.setText(resHelper.getString("btnDelLine.text"));
        // f7RoomUsage		
        this.f7RoomUsage.setDisplayFormat("$name$");		
        this.f7RoomUsage.setEditFormat("$number$");		
        this.f7RoomUsage.setCommitFormat("$number$");		
        this.f7RoomUsage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtRoomModel		
        this.prmtRoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");		
        this.prmtRoomModel.setCommitFormat("$number$");		
        this.prmtRoomModel.setDisplayFormat("$name$");		
        this.prmtRoomModel.setEditFormat("$number$");
        // prmtBuildStruct		
        this.prmtBuildStruct.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingStructureQuery");		
        this.prmtBuildStruct.setCommitFormat("$number$");		
        this.prmtBuildStruct.setDisplayFormat("$name$");		
        this.prmtBuildStruct.setEditFormat("$number$");
        // btnSubmit
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 600, 520));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 520));
        contFace.setBounds(new Rectangle(10, 373, 269, 19));
        this.add(contFace, new KDLayout.Constraints(10, 373, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFloorHeight.setBounds(new Rectangle(10, 470, 269, 19));
        this.add(contFloorHeight, new KDLayout.Constraints(10, 470, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomForm.setBounds(new Rectangle(312, 444, 269, 19));
        this.add(contRoomForm, new KDLayout.Constraints(312, 444, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOutlook.setBounds(new Rectangle(10, 325, 269, 19));
        this.add(contOutlook, new KDLayout.Constraints(10, 325, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentStandard.setBounds(new Rectangle(10, 494, 269, 19));
        this.add(contFitmentStandard, new KDLayout.Constraints(10, 494, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingProperty.setBounds(new Rectangle(312, 348, 269, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(312, 348, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDeliverHouseStandard.setBounds(new Rectangle(312, 493, 269, 19));
        this.add(contDeliverHouseStandard, new KDLayout.Constraints(312, 493, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProductType.setBounds(new Rectangle(10, 300, 269, 19));
        this.add(contProductType, new KDLayout.Constraints(10, 300, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProductDetail.setBounds(new Rectangle(312, 372, 269, 19));
        this.add(contProductDetail, new KDLayout.Constraints(312, 372, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHouseProperty.setBounds(new Rectangle(11, 445, 269, 19));
        this.add(contHouseProperty, new KDLayout.Constraints(11, 445, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEyeSignht.setBounds(new Rectangle(312, 324, 269, 19));
        this.add(contEyeSignht, new KDLayout.Constraints(312, 324, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNoise.setBounds(new Rectangle(312, 396, 269, 19));
        this.add(contNoise, new KDLayout.Constraints(312, 396, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPosseStd.setBounds(new Rectangle(312, 300, 269, 19));
        this.add(contPosseStd, new KDLayout.Constraints(312, 300, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDecoraStd.setBounds(new Rectangle(312, 420, 269, 19));
        this.add(contDecoraStd, new KDLayout.Constraints(312, 420, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWindow.setBounds(new Rectangle(312, 469, 269, 19));
        this.add(contWindow, new KDLayout.Constraints(312, 469, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(7, 4, 585, 285));
        this.add(kDContainer1, new KDLayout.Constraints(7, 4, 585, 285, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator2.setBounds(new Rectangle(5, 292, 591, 8));
        this.add(kDSeparator2, new KDLayout.Constraints(5, 292, 591, 8, 0));
        contRoomUsage.setBounds(new Rectangle(10, 350, 269, 19));
        this.add(contRoomUsage, new KDLayout.Constraints(10, 350, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(11, 397, 269, 19));
        this.add(contRoomModel, new KDLayout.Constraints(11, 397, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbuildStruct.setBounds(new Rectangle(11, 420, 269, 19));
        this.add(contbuildStruct, new KDLayout.Constraints(11, 420, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contFace
        contFace.setBoundEditor(f7Direction);
        //contFloorHeight
        contFloorHeight.setBoundEditor(txtFloorHeight);
        //contRoomForm
        contRoomForm.setBoundEditor(f7roomForm);
        //contOutlook
        contOutlook.setBoundEditor(f7Sight);
        //contFitmentStandard
        contFitmentStandard.setBoundEditor(txtFitmentStandard);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contDeliverHouseStandard
        contDeliverHouseStandard.setBoundEditor(txtDeliverHouseStandard);
        //contProductType
        contProductType.setBoundEditor(f7ProductType);
        //contProductDetail
        contProductDetail.setBoundEditor(f7ProductDetail);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contEyeSignht
        contEyeSignht.setBoundEditor(f7EyeSignht);
        //contNoise
        contNoise.setBoundEditor(f7Noise);
        //contPosseStd
        contPosseStd.setBoundEditor(f7PosseStd);
        //contDecoraStd
        contDecoraStd.setBoundEditor(f7DecoraStd);
        //contWindow
        contWindow.setBoundEditor(comboWindow);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(7, 4, 585, 285));        tblMain.setBounds(new Rectangle(1, 24, 579, 235));
        kDContainer1.getContentPane().add(tblMain, new KDLayout.Constraints(1, 24, 579, 235, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddRoom.setBounds(new Rectangle(383, 2, 90, 19));
        kDContainer1.getContentPane().add(btnAddRoom, new KDLayout.Constraints(383, 2, 90, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelLine.setBounds(new Rectangle(479, 2, 90, 19));
        kDContainer1.getContentPane().add(btnDelLine, new KDLayout.Constraints(479, 2, 90, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contRoomUsage
        contRoomUsage.setBoundEditor(f7RoomUsage);
        //contRoomModel
        contRoomModel.setBoundEditor(prmtRoomModel);
        //contbuildStruct
        contbuildStruct.setBoundEditor(prmtBuildStruct);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnSubmit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomUpdateBatchEditUIHandler";
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
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnDelLine_actionPerformed method
     */
    public void actionBtnDelLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionBtnDelLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnDelLine() {
    	return false;
    }
	public RequestContext prepareActionAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLine() {
    	return false;
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomUpdateBatchEditUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnDelLine class
     */     
    protected class ActionBtnDelLine extends ItemAction {     
    
        public ActionBtnDelLine()
        {
            this(null);
        }

        public ActionBtnDelLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnDelLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDelLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDelLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomUpdateBatchEditUI.this, "ActionBtnDelLine", "actionBtnDelLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAddLine class
     */     
    protected class ActionAddLine extends ItemAction {     
    
        public ActionAddLine()
        {
            this(null);
        }

        public ActionAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomUpdateBatchEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomUpdateBatchEditUI");
    }




}