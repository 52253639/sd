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
public abstract class AbstractRoomEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomPic;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubarea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDeliverHouseStandard;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRoomState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGuardenArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUseArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFlatArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7roomForm;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGuardenArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUseArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFlatArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomForm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeliverHouseStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomState;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductDetail;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForSHE;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForTen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForPPM;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomUsage;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomPic;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRoomPic;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBuildingFloor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomPropNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomPropNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7EyeSihnht;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7DecoraStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PosseStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Noise;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWindow;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboWindow;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTenancyArea;
    protected com.kingdee.bos.ctrl.swing.KDContainer lblRoomChange;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAreaChageHis;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPageSetup
        String _tempStr = null;
        actionPageSetup.setEnabled(true);
        actionPageSetup.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionPageSetup.SHORT_DESCRIPTION");
        actionPageSetup.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPageSetup.LONG_DESCRIPTION");
        actionPageSetup.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPageSetup.NAME");
        actionPageSetup.putValue(ItemAction.NAME, _tempStr);
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionExitCurrent
        actionExitCurrent.setEnabled(true);
        actionExitCurrent.setDaemonRun(false);

        actionExitCurrent.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl W"));
        _tempStr = resHelper.getString("ActionExitCurrent.SHORT_DESCRIPTION");
        actionExitCurrent.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExitCurrent.LONG_DESCRIPTION");
        actionExitCurrent.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExitCurrent.NAME");
        actionExitCurrent.putValue(ItemAction.NAME, _tempStr);
         this.actionExitCurrent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExitCurrent.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionExitCurrent.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionHelp
        actionHelp.setEnabled(true);
        actionHelp.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionHelp.SHORT_DESCRIPTION");
        actionHelp.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionHelp.LONG_DESCRIPTION");
        actionHelp.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionHelp.NAME");
        actionHelp.putValue(ItemAction.NAME, _tempStr);
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAbout
        actionAbout.setEnabled(true);
        actionAbout.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAbout.SHORT_DESCRIPTION");
        actionAbout.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAbout.LONG_DESCRIPTION");
        actionAbout.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAbout.NAME");
        actionAbout.putValue(ItemAction.NAME, _tempStr);
         this.actionAbout.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAbout.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAbout.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionOnLoad
        actionOnLoad.setEnabled(true);
        actionOnLoad.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionOnLoad.SHORT_DESCRIPTION");
        actionOnLoad.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionOnLoad.LONG_DESCRIPTION");
        actionOnLoad.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionOnLoad.NAME");
        actionOnLoad.putValue(ItemAction.NAME, _tempStr);
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSendMessage
        actionSendMessage.setEnabled(true);
        actionSendMessage.setDaemonRun(false);

        actionSendMessage.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F4"));
        _tempStr = resHelper.getString("ActionSendMessage.SHORT_DESCRIPTION");
        actionSendMessage.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSendMessage.LONG_DESCRIPTION");
        actionSendMessage.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSendMessage.NAME");
        actionSendMessage.putValue(ItemAction.NAME, _tempStr);
         this.actionSendMessage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCalculator
        actionCalculator.setEnabled(true);
        actionCalculator.setDaemonRun(false);

        actionCalculator.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
        _tempStr = resHelper.getString("ActionCalculator.SHORT_DESCRIPTION");
        actionCalculator.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.LONG_DESCRIPTION");
        actionCalculator.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.NAME");
        actionCalculator.putValue(ItemAction.NAME, _tempStr);
         this.actionCalculator.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExport
        actionExport.setEnabled(true);
        actionExport.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionExport.SHORT_DESCRIPTION");
        actionExport.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExport.LONG_DESCRIPTION");
        actionExport.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExport.NAME");
        actionExport.putValue(ItemAction.NAME, _tempStr);
         this.actionExport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportSelected
        actionExportSelected.setEnabled(true);
        actionExportSelected.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionExportSelected.SHORT_DESCRIPTION");
        actionExportSelected.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportSelected.LONG_DESCRIPTION");
        actionExportSelected.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportSelected.NAME");
        actionExportSelected.putValue(ItemAction.NAME, _tempStr);
         this.actionExportSelected.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRegProduct
        actionRegProduct.setEnabled(true);
        actionRegProduct.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionRegProduct.SHORT_DESCRIPTION");
        actionRegProduct.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRegProduct.LONG_DESCRIPTION");
        actionRegProduct.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRegProduct.NAME");
        actionRegProduct.putValue(ItemAction.NAME, _tempStr);
         this.actionRegProduct.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPersonalSite
        actionPersonalSite.setEnabled(true);
        actionPersonalSite.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionPersonalSite.SHORT_DESCRIPTION");
        actionPersonalSite.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPersonalSite.LONG_DESCRIPTION");
        actionPersonalSite.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPersonalSite.NAME");
        actionPersonalSite.putValue(ItemAction.NAME, _tempStr);
         this.actionPersonalSite.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProcductVal
        actionProcductVal.setEnabled(true);
        actionProcductVal.setDaemonRun(false);

        _tempStr = resHelper.getString("actionProcductVal.SHORT_DESCRIPTION");
        actionProcductVal.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionProcductVal.LONG_DESCRIPTION");
        actionProcductVal.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionProcductVal.NAME");
        actionProcductVal.putValue(ItemAction.NAME, _tempStr);
         this.actionProcductVal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionCancel
        actionCancel.setEnabled(true);
        actionCancel.setDaemonRun(false);

        actionCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl -"));
        _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
        actionCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
        actionCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.NAME");
        actionCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCancelCancel
        actionCancelCancel.setEnabled(true);
        actionCancelCancel.setDaemonRun(false);

        actionCancelCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl +"));
        _tempStr = resHelper.getString("ActionCancelCancel.SHORT_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.LONG_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.NAME");
        actionCancelCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionFirst
        actionFirst.setEnabled(false);
        actionFirst.setDaemonRun(false);

        actionFirst.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl <"));
        _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
        actionFirst.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
        actionFirst.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.NAME");
        actionFirst.putValue(ItemAction.NAME, _tempStr);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPre
        actionPre.setEnabled(true);
        actionPre.setDaemonRun(false);

        actionPre.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl <"));
        _tempStr = resHelper.getString("ActionPre.SHORT_DESCRIPTION");
        actionPre.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPre.LONG_DESCRIPTION");
        actionPre.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPre.NAME");
        actionPre.putValue(ItemAction.NAME, _tempStr);
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionNext
        actionNext.setEnabled(true);
        actionNext.setDaemonRun(false);

        actionNext.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl >"));
        _tempStr = resHelper.getString("ActionNext.SHORT_DESCRIPTION");
        actionNext.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionNext.LONG_DESCRIPTION");
        actionNext.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionNext.NAME");
        actionNext.putValue(ItemAction.NAME, _tempStr);
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionLast
        actionLast.setEnabled(true);
        actionLast.setDaemonRun(false);

        actionLast.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl >"));
        _tempStr = resHelper.getString("ActionLast.SHORT_DESCRIPTION");
        actionLast.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLast.LONG_DESCRIPTION");
        actionLast.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLast.NAME");
        actionLast.putValue(ItemAction.NAME, _tempStr);
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(false);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(false);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCopy
        actionCopy.setEnabled(true);
        actionCopy.setDaemonRun(false);

        actionCopy.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift C"));
        _tempStr = resHelper.getString("ActionCopy.SHORT_DESCRIPTION");
        actionCopy.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopy.LONG_DESCRIPTION");
        actionCopy.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopy.NAME");
        actionCopy.putValue(ItemAction.NAME, _tempStr);
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionEdit
        actionEdit.setEnabled(true);
        actionEdit.setDaemonRun(false);

        actionEdit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemove
        actionRemove.setEnabled(false);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        actionAttachment.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift H"));
        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmitOption
        actionSubmitOption.setEnabled(true);
        actionSubmitOption.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionSubmitOption.SHORT_DESCRIPTION");
        actionSubmitOption.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmitOption.LONG_DESCRIPTION");
        actionSubmitOption.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmitOption.NAME");
        actionSubmitOption.putValue(ItemAction.NAME, _tempStr);
         this.actionSubmitOption.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.spiSerialNumber = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtRoomPic = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSubarea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDeliverHouseStandard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFitmentStandard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboRoomState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGuardenArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCarbarnArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUseArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFlatArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7roomForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSerialNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGuardenArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCarbarnArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUseArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFlatArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDeliverHouseStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.scPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7ProductDetail = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkIsForSHE = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForTen = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForPPM = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contTenancyState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboTenancyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7RoomUsage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contRoomPic = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRoomPic = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtBuildUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.lcBuildingFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7BuildingFloor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contRoomPropNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomPropNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7EyeSihnht = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7DecoraStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7PosseStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Noise = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contWindow = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboWindow = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contTenancyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTenancyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.lblRoomChange = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblAreaChageHis = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtBuilding.setName("txtBuilding");
        this.f7RoomModel.setName("f7RoomModel");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.spiSerialNumber.setName("spiSerialNumber");
        this.spiFloor.setName("spiFloor");
        this.txtRoomPic.setName("txtRoomPic");
        this.txtProjectNumber.setName("txtProjectNumber");
        this.txtProjectName.setName("txtProjectName");
        this.txtSubarea.setName("txtSubarea");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.txtDeliverHouseStandard.setName("txtDeliverHouseStandard");
        this.txtFitmentStandard.setName("txtFitmentStandard");
        this.comboRoomState.setName("comboRoomState");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtGuardenArea.setName("txtGuardenArea");
        this.txtCarbarnArea.setName("txtCarbarnArea");
        this.txtUseArea.setName("txtUseArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.txtFlatArea.setName("txtFlatArea");
        this.txtNumber.setName("txtNumber");
        this.txtCompany.setName("txtCompany");
        this.f7Direction.setName("f7Direction");
        this.f7Sight.setName("f7Sight");
        this.f7roomForm.setName("f7roomForm");
        this.f7ProductType.setName("f7ProductType");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.contCompany.setName("contCompany");
        this.contProjectNumber.setName("contProjectNumber");
        this.contProjectName.setName("contProjectName");
        this.contSubarea.setName("contSubarea");
        this.contBuilding.setName("contBuilding");
        this.contNumber.setName("contNumber");
        this.contUnit.setName("contUnit");
        this.contFloor.setName("contFloor");
        this.contSerialNumber.setName("contSerialNumber");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.contGuardenArea.setName("contGuardenArea");
        this.contCarbarnArea.setName("contCarbarnArea");
        this.contUseArea.setName("contUseArea");
        this.contFlatArea.setName("contFlatArea");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.contFace.setName("contFace");
        this.contOutlook.setName("contOutlook");
        this.contRoomModel.setName("contRoomModel");
        this.contRoomForm.setName("contRoomForm");
        this.contDeliverHouseStandard.setName("contDeliverHouseStandard");
        this.contFitmentStandard.setName("contFitmentStandard");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contHouseProperty.setName("contHouseProperty");
        this.contProductType.setName("contProductType");
        this.contRoomState.setName("contRoomState");
        this.kDPanel2.setName("kDPanel2");
        this.scPanel.setName("scPanel");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.f7ProductDetail.setName("f7ProductDetail");
        this.chkIsForSHE.setName("chkIsForSHE");
        this.chkIsForTen.setName("chkIsForTen");
        this.chkIsForPPM.setName("chkIsForPPM");
        this.contTenancyState.setName("contTenancyState");
        this.comboTenancyState.setName("comboTenancyState");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.f7RoomUsage.setName("f7RoomUsage");
        this.kDPanel3.setName("kDPanel3");
        this.contRoomPic.setName("contRoomPic");
        this.btnRoomPic.setName("btnRoomPic");
        this.prmtBuildUnit.setName("prmtBuildUnit");
        this.lcBuildingFloor.setName("lcBuildingFloor");
        this.f7BuildingFloor.setName("f7BuildingFloor");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.txtRoomNo.setName("txtRoomNo");
        this.contRoomPropNo.setName("contRoomPropNo");
        this.txtRoomPropNo.setName("txtRoomPropNo");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.f7EyeSihnht.setName("f7EyeSihnht");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.f7DecoraStd.setName("f7DecoraStd");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.f7PosseStd.setName("f7PosseStd");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.f7Noise.setName("f7Noise");
        this.contWindow.setName("contWindow");
        this.comboWindow.setName("comboWindow");
        this.contTenancyArea.setName("contTenancyArea");
        this.txtTenancyArea.setName("txtTenancyArea");
        this.lblRoomChange.setName("lblRoomChange");
        this.tblAreaChageHis.setName("tblAreaChageHis");
        // CoreUI		
        this.setPreferredSize(new Dimension(1024,768));
        this.btnPageSetup.setAction((IItemAction)ActionProxyFactory.getProxy(actionPageSetup, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPageSetup.setText(resHelper.getString("btnPageSetup.text"));		
        this.btnPageSetup.setToolTipText(resHelper.getString("btnPageSetup.toolTipText"));		
        this.btnPageSetup.setVisible(false);		
        this.menuFile.setText(resHelper.getString("menuFile.text"));		
        this.menuFile.setToolTipText(resHelper.getString("menuFile.toolTipText"));		
        this.menuFile.setMnemonic(70);
        this.menuItemPageSetup.setAction((IItemAction)ActionProxyFactory.getProxy(actionPageSetup, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPageSetup.setText(resHelper.getString("menuItemPageSetup.text"));		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemPageSetup.setToolTipText(resHelper.getString("menuItemPageSetup.toolTipText"));		
        this.menuItemPageSetup.setMnemonic(84);
        this.menuItemExitCurrent.setAction((IItemAction)ActionProxyFactory.getProxy(actionExitCurrent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExitCurrent.setText(resHelper.getString("menuItemExitCurrent.text"));		
        this.menuItemExitCurrent.setToolTipText(resHelper.getString("menuItemExitCurrent.toolTipText"));		
        this.menuItemExitCurrent.setMnemonic(88);		
        this.menuTool.setText(resHelper.getString("menuTool.text"));		
        this.menuTool.setToolTipText(resHelper.getString("menuTool.toolTipText"));		
        this.menuTool.setMnemonic(84);
        this.menuItemSendMessage.setAction((IItemAction)ActionProxyFactory.getProxy(actionSendMessage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSendMessage.setText(resHelper.getString("menuItemSendMessage.text"));		
        this.menuItemSendMessage.setToolTipText(resHelper.getString("menuItemSendMessage.toolTipText"));		
        this.menuItemSendMessage.setMnemonic(77);
        this.menuItemCalculator.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalculator, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCalculator.setText(resHelper.getString("menuItemCalculator.text"));		
        this.menuItemCalculator.setVisible(false);		
        this.menuHelp.setText(resHelper.getString("menuHelp.text"));		
        this.menuHelp.setMnemonic(72);
        this.menuItemHelp.setAction((IItemAction)ActionProxyFactory.getProxy(actionHelp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHelp.setText(resHelper.getString("menuItemHelp.text"));		
        this.menuItemHelp.setToolTipText(resHelper.getString("menuItemHelp.toolTipText"));		
        this.menuItemHelp.setMnemonic(72);
        this.menuItemRegPro.setAction((IItemAction)ActionProxyFactory.getProxy(actionRegProduct, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRegPro.setText(resHelper.getString("menuItemRegPro.text"));		
        this.menuItemRegPro.setToolTipText(resHelper.getString("menuItemRegPro.toolTipText"));
        this.menuItemPersonalSite.setAction((IItemAction)ActionProxyFactory.getProxy(actionPersonalSite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPersonalSite.setText(resHelper.getString("menuItemPersonalSite.text"));		
        this.menuItemPersonalSite.setToolTipText(resHelper.getString("menuItemPersonalSite.toolTipText"));
        this.menuitemProductval.setAction((IItemAction)ActionProxyFactory.getProxy(actionProcductVal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuitemProductval.setText(resHelper.getString("menuitemProductval.text"));		
        this.menuitemProductval.setToolTipText(resHelper.getString("menuitemProductval.toolTipText"));
        this.menuItemAbout.setAction((IItemAction)ActionProxyFactory.getProxy(actionAbout, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAbout.setText(resHelper.getString("menuItemAbout.text"));		
        this.menuItemAbout.setToolTipText(resHelper.getString("menuItemAbout.toolTipText"));		
        this.menuItemAbout.setMnemonic(65);
        this.btnAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNew.setText(resHelper.getString("btnAddNew.text"));		
        this.btnAddNew.setToolTipText(resHelper.getString("btnAddNew.toolTipText"));
        this.btnEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));
        this.btnCopy.setAction((IItemAction)ActionProxyFactory.getProxy(actionCopy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCopy.setText(resHelper.getString("btnCopy.text"));		
        this.btnCopy.setToolTipText(resHelper.getString("btnCopy.toolTipText"));
        this.btnRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemove.setText(resHelper.getString("btnRemove.text"));		
        this.btnRemove.setToolTipText(resHelper.getString("btnRemove.toolTipText"));
        this.btnCancelCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));		
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));
        this.btnFirst.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirst, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFirst.setText(resHelper.getString("btnFirst.text"));		
        this.btnFirst.setToolTipText(resHelper.getString("btnFirst.toolTipText"));
        this.btnPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPre.setText(resHelper.getString("btnPre.text"));		
        this.btnPre.setToolTipText(resHelper.getString("btnPre.toolTipText"));
        this.btnNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNext.setText(resHelper.getString("btnNext.text"));		
        this.btnNext.setToolTipText(resHelper.getString("btnNext.toolTipText"));
        this.btnLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLast.setText(resHelper.getString("btnLast.text"));		
        this.btnLast.setToolTipText(resHelper.getString("btnLast.toolTipText"));
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));		
        this.btnPrint.setToolTipText(resHelper.getString("btnPrint.toolTipText"));
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));		
        this.btnPrintPreview.setToolTipText(resHelper.getString("btnPrintPreview.toolTipText"));
        this.menuItemAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddNew.setText(resHelper.getString("menuItemAddNew.text"));		
        this.menuItemAddNew.setMnemonic(78);
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));
        this.menuItemSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuItemSubmit.setMnemonic(83);		
        this.kDSeparator2.setVisible(false);
        this.menuItemPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrint.setMnemonic(80);
        this.menuItemPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintPreview.setText(resHelper.getString("menuItemPrintPreview.text"));		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemPrintPreview.setMnemonic(86);		
        this.menuEdit.setText(resHelper.getString("menuEdit.text"));		
        this.menuEdit.setToolTipText(resHelper.getString("menuEdit.toolTipText"));		
        this.menuEdit.setMnemonic(69);
        this.menuItemCopy.setAction((IItemAction)ActionProxyFactory.getProxy(actionCopy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCopy.setText(resHelper.getString("menuItemCopy.text"));		
        this.menuItemCopy.setToolTipText(resHelper.getString("menuItemCopy.toolTipText"));		
        this.menuItemCopy.setMnemonic(67);		
        this.kDSeparator4.setVisible(false);		
        this.kDSeparator4.setEnabled(false);		
        this.menuView.setText(resHelper.getString("menuView.text"));		
        this.menuView.setToolTipText(resHelper.getString("menuView.toolTipText"));		
        this.menuView.setMnemonic(86);
        this.menuItemFirst.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirst, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFirst.setText(resHelper.getString("menuItemFirst.text"));		
        this.menuItemFirst.setMnemonic(70);
        this.menuItemPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPre.setText(resHelper.getString("menuItemPre.text"));		
        this.menuItemPre.setMnemonic(80);
        this.menuItemNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNext.setText(resHelper.getString("menuItemNext.text"));		
        this.menuItemNext.setMnemonic(78);
        this.menuItemLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLast.setText(resHelper.getString("menuItemLast.text"));		
        this.menuItemLast.setMnemonic(76);
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));
        this.menuItemEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setToolTipText(resHelper.getString("menuItemEdit.toolTipText"));		
        this.menuItemEdit.setMnemonic(69);
        this.menuItemRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRemove.setText(resHelper.getString("menuItemRemove.text"));		
        this.menuItemRemove.setToolTipText(resHelper.getString("menuItemRemove.toolTipText"));		
        this.menuItemRemove.setMnemonic(82);		
        this.rMenuItemSubmit.setText(resHelper.getString("rMenuItemSubmit.text"));		
        this.rMenuItemSubmit.setToolTipText(resHelper.getString("rMenuItemSubmit.toolTipText"));		
        this.rMenuItemSubmit.setSelected(true);		
        this.rMenuItemSubmit.setVisible(false);		
        this.rMenuItemSubmit.setEnabled(false);		
        this.rMenuItemSubmitAndAddNew.setText(resHelper.getString("rMenuItemSubmitAndAddNew.text"));		
        this.rMenuItemSubmitAndAddNew.setToolTipText(resHelper.getString("rMenuItemSubmitAndAddNew.toolTipText"));		
        this.rMenuItemSubmitAndAddNew.setVisible(false);		
        this.rMenuItemSubmitAndAddNew.setEnabled(false);		
        this.rMenuItemSubmitAndPrint.setText(resHelper.getString("rMenuItemSubmitAndPrint.text"));		
        this.rMenuItemSubmitAndPrint.setToolTipText(resHelper.getString("rMenuItemSubmitAndPrint.toolTipText"));		
        this.rMenuItemSubmitAndPrint.setVisible(false);		
        this.rMenuItemSubmitAndPrint.setEnabled(false);
        this.menuSubmitOption.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitOption, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSubmitOption.setText(resHelper.getString("menuSubmitOption.text"));		
        this.menuSubmitOption.setMnemonic(67);		
        this.chkMenuItemSubmitAndAddNew.setText(resHelper.getString("chkMenuItemSubmitAndAddNew.text"));		
        this.chkMenuItemSubmitAndAddNew.setMnemonic(69);		
        this.chkMenuItemSubmitAndPrint.setText(resHelper.getString("chkMenuItemSubmitAndPrint.text"));		
        this.chkMenuItemSubmitAndPrint.setMnemonic(80);
        this.MenuItemAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.MenuItemAttachment.setMnemonic(74);		
        this.menuBiz.setText(resHelper.getString("menuBiz.text"));		
        this.menuBiz.setMnemonic(79);
        this.menuItemCancelCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelCancel.setText(resHelper.getString("menuItemCancelCancel.text"));		
        this.menuItemCancelCancel.setMnemonic(83);
        this.menuItemCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancel.setText(resHelper.getString("menuItemCancel.text"));		
        this.menuItemCancel.setMnemonic(67);		
        this.separatorFW1.setOrientation(1);		
        this.separatorFW2.setOrientation(1);		
        this.separatorFW3.setOrientation(1);
        // txtBuilding
        // f7RoomModel		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setEditFormat("$number$");		
        this.f7RoomModel.setCommitFormat("$number$");		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");
        // txtFloorHeight		
        this.txtFloorHeight.setDataType(1);
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // txtApportionArea		
        this.txtApportionArea.setDataType(1);		
        this.txtApportionArea.setEnabled(false);
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
        // txtBuildingArea		
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
        // spiSerialNumber
        // spiFloor
        // txtRoomPic		
        this.txtRoomPic.setEnabled(false);
        // txtProjectNumber
        // txtProjectName
        // txtSubarea
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setDisplayFormat("$name$");
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
        // txtDeliverHouseStandard		
        this.txtDeliverHouseStandard.setMaxLength(40);
        // txtFitmentStandard		
        this.txtFitmentStandard.setMaxLength(40);
        // comboRoomState		
        this.comboRoomState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum").toArray());		
        this.comboRoomState.setEnabled(false);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);
        // txtGuardenArea		
        this.txtGuardenArea.setDataType(1);
        // txtCarbarnArea		
        this.txtCarbarnArea.setDataType(1);
        // txtUseArea		
        this.txtUseArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // txtFlatArea		
        this.txtFlatArea.setDataType(1);
        // txtNumber		
        this.txtNumber.setMaxLength(40);
        this.txtNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    txtNumber_KeyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtCompany
        // f7Direction		
        this.f7Direction.setCommitFormat("$number$");		
        this.f7Direction.setDisplayFormat("$name$");		
        this.f7Direction.setEditFormat("$number$");		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // f7Sight		
        this.f7Sight.setCommitFormat("$number$");		
        this.f7Sight.setDisplayFormat("$name$");		
        this.f7Sight.setEditFormat("$number$");		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // f7roomForm		
        this.f7roomForm.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery");		
        this.f7roomForm.setCommitFormat("$number$");		
        this.f7roomForm.setEditFormat("$number$");		
        this.f7roomForm.setDisplayFormat("$name$");
        // f7ProductType		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setRequired(true);
        // kDTabbedPane1
        // kDPanel1		
        this.kDPanel1.setBorder(null);
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contProjectNumber		
        this.contProjectNumber.setBoundLabelText(resHelper.getString("contProjectNumber.boundLabelText"));		
        this.contProjectNumber.setBoundLabelLength(100);		
        this.contProjectNumber.setBoundLabelUnderline(true);
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);
        // contSubarea		
        this.contSubarea.setBoundLabelText(resHelper.getString("contSubarea.boundLabelText"));		
        this.contSubarea.setBoundLabelLength(100);		
        this.contSubarea.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelUnderline(true);		
        this.contBuilding.setBoundLabelLength(100);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelLength(100);
        // contUnit		
        this.contUnit.setBoundLabelText(resHelper.getString("contUnit.boundLabelText"));		
        this.contUnit.setBoundLabelUnderline(true);		
        this.contUnit.setBoundLabelLength(100);
        // contFloor		
        this.contFloor.setBoundLabelText(resHelper.getString("contFloor.boundLabelText"));		
        this.contFloor.setBoundLabelUnderline(true);		
        this.contFloor.setBoundLabelLength(100);
        // contSerialNumber		
        this.contSerialNumber.setBoundLabelText(resHelper.getString("contSerialNumber.boundLabelText"));		
        this.contSerialNumber.setBoundLabelUnderline(true);		
        this.contSerialNumber.setBoundLabelLength(100);
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
        // contGuardenArea		
        this.contGuardenArea.setBoundLabelText(resHelper.getString("contGuardenArea.boundLabelText"));		
        this.contGuardenArea.setBoundLabelUnderline(true);		
        this.contGuardenArea.setBoundLabelLength(100);
        // contCarbarnArea		
        this.contCarbarnArea.setBoundLabelText(resHelper.getString("contCarbarnArea.boundLabelText"));		
        this.contCarbarnArea.setBoundLabelUnderline(true);		
        this.contCarbarnArea.setBoundLabelLength(100);
        // contUseArea		
        this.contUseArea.setBoundLabelText(resHelper.getString("contUseArea.boundLabelText"));		
        this.contUseArea.setBoundLabelUnderline(true);		
        this.contUseArea.setBoundLabelLength(100);
        // contFlatArea		
        this.contFlatArea.setBoundLabelText(resHelper.getString("contFlatArea.boundLabelText"));		
        this.contFlatArea.setBoundLabelUnderline(true);		
        this.contFlatArea.setBoundLabelLength(100);
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelUnderline(true);		
        this.contActualBuildingArea.setBoundLabelLength(100);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(100);
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
        // contRoomForm		
        this.contRoomForm.setBoundLabelText(resHelper.getString("contRoomForm.boundLabelText"));		
        this.contRoomForm.setBoundLabelUnderline(true);		
        this.contRoomForm.setBoundLabelLength(100);
        // contDeliverHouseStandard		
        this.contDeliverHouseStandard.setBoundLabelText(resHelper.getString("contDeliverHouseStandard.boundLabelText"));		
        this.contDeliverHouseStandard.setBoundLabelUnderline(true);		
        this.contDeliverHouseStandard.setBoundLabelLength(100);
        // contFitmentStandard		
        this.contFitmentStandard.setBoundLabelText(resHelper.getString("contFitmentStandard.boundLabelText"));		
        this.contFitmentStandard.setBoundLabelUnderline(true);		
        this.contFitmentStandard.setBoundLabelLength(100);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelUnderline(true);		
        this.contBuildingProperty.setBoundLabelLength(100);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelUnderline(true);		
        this.contHouseProperty.setBoundLabelLength(100);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contRoomState		
        this.contRoomState.setBoundLabelText(resHelper.getString("contRoomState.boundLabelText"));		
        this.contRoomState.setBoundLabelUnderline(true);		
        this.contRoomState.setBoundLabelLength(100);
        // kDPanel2
        // scPanel		
        this.scPanel.setPreferredSize(new Dimension(998,664));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // f7ProductDetail		
        this.f7ProductDetail.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery");		
        this.f7ProductDetail.setDisplayFormat("$name$");		
        this.f7ProductDetail.setEditFormat("$number$");		
        this.f7ProductDetail.setCommitFormat("$number$");		
        this.f7ProductDetail.setRequired(true);
        // chkIsForSHE		
        this.chkIsForSHE.setText(resHelper.getString("chkIsForSHE.text"));
        this.chkIsForSHE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsForSHE_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsForTen		
        this.chkIsForTen.setText(resHelper.getString("chkIsForTen.text"));
        this.chkIsForTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsForTen_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsForPPM		
        this.chkIsForPPM.setText(resHelper.getString("chkIsForPPM.text"));
        // contTenancyState		
        this.contTenancyState.setBoundLabelText(resHelper.getString("contTenancyState.boundLabelText"));		
        this.contTenancyState.setBoundLabelLength(100);		
        this.contTenancyState.setBoundLabelUnderline(true);
        // comboTenancyState		
        this.comboTenancyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.TenancyStateEnum").toArray());
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // f7RoomUsage		
        this.f7RoomUsage.setDisplayFormat("$name$");		
        this.f7RoomUsage.setEditFormat("$number$");		
        this.f7RoomUsage.setCommitFormat("$number$");		
        this.f7RoomUsage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomUsageQuery");
        // kDPanel3		
        this.kDPanel3.setPreferredSize(new Dimension(10,35));
        // contRoomPic		
        this.contRoomPic.setBoundLabelText(resHelper.getString("contRoomPic.boundLabelText"));		
        this.contRoomPic.setBoundLabelLength(100);		
        this.contRoomPic.setBoundLabelUnderline(true);
        // btnRoomPic		
        this.btnRoomPic.setText(resHelper.getString("btnRoomPic.text"));
        this.btnRoomPic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRoomPic_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtBuildUnit		
        this.prmtBuildUnit.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingUnitQuery");		
        this.prmtBuildUnit.setCommitFormat("$number$");		
        this.prmtBuildUnit.setEditFormat("$number$");		
        this.prmtBuildUnit.setDisplayFormat("$name$");
        // lcBuildingFloor		
        this.lcBuildingFloor.setBoundLabelText(resHelper.getString("lcBuildingFloor.boundLabelText"));		
        this.lcBuildingFloor.setBoundLabelLength(100);		
        this.lcBuildingFloor.setBoundLabelUnderline(true);
        // f7BuildingFloor		
        this.f7BuildingFloor.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingFloorEntryQuery");		
        this.f7BuildingFloor.setDisplayFormat("$flooralias$");		
        this.f7BuildingFloor.setEditFormat("$flooralias$");		
        this.f7BuildingFloor.setCommitFormat("$flooralias$");		
        this.f7BuildingFloor.setRequired(true);
        this.f7BuildingFloor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    f7BuildingFloor_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7BuildingFloor.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7BuildingFloor_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // txtRoomNo		
        this.txtRoomNo.setMaxLength(40);
        // contRoomPropNo		
        this.contRoomPropNo.setBoundLabelText(resHelper.getString("contRoomPropNo.boundLabelText"));		
        this.contRoomPropNo.setBoundLabelUnderline(true);		
        this.contRoomPropNo.setBoundLabelLength(100);
        // txtRoomPropNo		
        this.txtRoomPropNo.setMaxLength(40);		
        this.txtRoomPropNo.setRequired(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // f7EyeSihnht		
        this.f7EyeSihnht.setDisplayFormat("$name$");		
        this.f7EyeSihnht.setEditFormat("$number$");		
        this.f7EyeSihnht.setCommitFormat("$number$");		
        this.f7EyeSihnht.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.EyeSignhtQuery");
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // f7DecoraStd		
        this.f7DecoraStd.setDisplayFormat("$name$");		
        this.f7DecoraStd.setEditFormat("$number$");		
        this.f7DecoraStd.setCommitFormat("$number$");		
        this.f7DecoraStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.DecorationStandardQuery");
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // f7PosseStd		
        this.f7PosseStd.setEditFormat("$number$");		
        this.f7PosseStd.setCommitFormat("$number$");		
        this.f7PosseStd.setDisplayFormat("$name$");		
        this.f7PosseStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PossessionStandardQuery");
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // f7Noise		
        this.f7Noise.setCommitFormat("$number$");		
        this.f7Noise.setEditFormat("$number$");		
        this.f7Noise.setDisplayFormat("$name$");		
        this.f7Noise.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.NoiseQuery");
        // contWindow		
        this.contWindow.setBoundLabelText(resHelper.getString("contWindow.boundLabelText"));		
        this.contWindow.setBoundLabelLength(100);		
        this.contWindow.setBoundLabelUnderline(true);
        // comboWindow		
        this.comboWindow.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum").toArray());
        // contTenancyArea		
        this.contTenancyArea.setBoundLabelText(resHelper.getString("contTenancyArea.boundLabelText"));		
        this.contTenancyArea.setBoundLabelLength(100);		
        this.contTenancyArea.setBoundLabelUnderline(true);
        // txtTenancyArea
        // lblRoomChange		
        this.lblRoomChange.setTitle(resHelper.getString("lblRoomChange.title"));
        // tblAreaChageHis
		String tblAreaChageHisStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operationTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"actBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"actRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{operationTime}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actBuildingArea}</t:Cell><t:Cell>$Resource{actRoomArea}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAreaChageHis.setFormatXml(resHelper.translateString("tblAreaChageHis",tblAreaChageHisStrXML));

        

        this.tblAreaChageHis.checkParsed();
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
        this.setBounds(new Rectangle(10, 10, 1024, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1024, 768));
        kDTabbedPane1.setBounds(new Rectangle(11, 11, 1002, 745));
        this.add(kDTabbedPane1, new KDLayout.Constraints(11, 11, 1002, 745, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1001, 712));        contCompany.setBounds(new Rectangle(10, 10, 270, 19));
        kDPanel1.add(contCompany, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contProjectNumber.setBounds(new Rectangle(363, 10, 270, 19));
        kDPanel1.add(contProjectNumber, new KDLayout.Constraints(363, 10, 270, 19, 0));
        contProjectName.setBounds(new Rectangle(715, 10, 270, 19));
        kDPanel1.add(contProjectName, new KDLayout.Constraints(715, 10, 270, 19, 0));
        contSubarea.setBounds(new Rectangle(10, 40, 270, 19));
        kDPanel1.add(contSubarea, new KDLayout.Constraints(10, 40, 270, 19, 0));
        contBuilding.setBounds(new Rectangle(363, 40, 270, 19));
        kDPanel1.add(contBuilding, new KDLayout.Constraints(363, 40, 270, 19, 0));
        contNumber.setBounds(new Rectangle(715, 40, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(715, 40, 270, 19, 0));
        contUnit.setBounds(new Rectangle(10, 100, 270, 19));
        kDPanel1.add(contUnit, new KDLayout.Constraints(10, 100, 270, 19, 0));
        contFloor.setBounds(new Rectangle(363, 70, 270, 19));
        kDPanel1.add(contFloor, new KDLayout.Constraints(363, 70, 270, 19, 0));
        contSerialNumber.setBounds(new Rectangle(715, 70, 270, 19));
        kDPanel1.add(contSerialNumber, new KDLayout.Constraints(715, 70, 270, 19, 0));
        contBuildingArea.setBounds(new Rectangle(10, 130, 270, 19));
        kDPanel1.add(contBuildingArea, new KDLayout.Constraints(10, 130, 270, 19, 0));
        contRoomArea.setBounds(new Rectangle(363, 130, 270, 19));
        kDPanel1.add(contRoomArea, new KDLayout.Constraints(363, 130, 270, 19, 0));
        contApportionArea.setBounds(new Rectangle(715, 100, 270, 19));
        kDPanel1.add(contApportionArea, new KDLayout.Constraints(715, 100, 270, 19, 0));
        contBalconyArea.setBounds(new Rectangle(10, 160, 270, 19));
        kDPanel1.add(contBalconyArea, new KDLayout.Constraints(10, 160, 270, 19, 0));
        contGuardenArea.setBounds(new Rectangle(363, 160, 270, 19));
        kDPanel1.add(contGuardenArea, new KDLayout.Constraints(363, 160, 270, 19, 0));
        contCarbarnArea.setBounds(new Rectangle(715, 130, 270, 19));
        kDPanel1.add(contCarbarnArea, new KDLayout.Constraints(715, 130, 270, 19, 0));
        contUseArea.setBounds(new Rectangle(10, 190, 270, 19));
        kDPanel1.add(contUseArea, new KDLayout.Constraints(10, 190, 270, 19, 0));
        contFlatArea.setBounds(new Rectangle(363, 190, 270, 19));
        kDPanel1.add(contFlatArea, new KDLayout.Constraints(363, 190, 270, 19, 0));
        contActualBuildingArea.setBounds(new Rectangle(363, 220, 270, 19));
        kDPanel1.add(contActualBuildingArea, new KDLayout.Constraints(363, 220, 270, 19, 0));
        contActualRoomArea.setBounds(new Rectangle(10, 220, 270, 19));
        kDPanel1.add(contActualRoomArea, new KDLayout.Constraints(10, 220, 270, 19, 0));
        contFloorHeight.setBounds(new Rectangle(714, 160, 270, 19));
        kDPanel1.add(contFloorHeight, new KDLayout.Constraints(714, 160, 270, 19, 0));
        contFace.setBounds(new Rectangle(715, 190, 270, 19));
        kDPanel1.add(contFace, new KDLayout.Constraints(715, 190, 270, 19, 0));
        contOutlook.setBounds(new Rectangle(10, 250, 270, 19));
        kDPanel1.add(contOutlook, new KDLayout.Constraints(10, 250, 270, 19, 0));
        contRoomModel.setBounds(new Rectangle(363, 250, 270, 19));
        kDPanel1.add(contRoomModel, new KDLayout.Constraints(363, 250, 270, 19, 0));
        contRoomForm.setBounds(new Rectangle(715, 220, 270, 19));
        kDPanel1.add(contRoomForm, new KDLayout.Constraints(715, 220, 270, 19, 0));
        contDeliverHouseStandard.setBounds(new Rectangle(10, 280, 270, 19));
        kDPanel1.add(contDeliverHouseStandard, new KDLayout.Constraints(10, 280, 270, 19, 0));
        contFitmentStandard.setBounds(new Rectangle(363, 280, 270, 19));
        kDPanel1.add(contFitmentStandard, new KDLayout.Constraints(363, 280, 270, 19, 0));
        contBuildingProperty.setBounds(new Rectangle(715, 250, 270, 19));
        kDPanel1.add(contBuildingProperty, new KDLayout.Constraints(715, 250, 270, 19, 0));
        contHouseProperty.setBounds(new Rectangle(11, 310, 270, 19));
        kDPanel1.add(contHouseProperty, new KDLayout.Constraints(11, 310, 270, 19, 0));
        contProductType.setBounds(new Rectangle(363, 310, 270, 19));
        kDPanel1.add(contProductType, new KDLayout.Constraints(363, 310, 270, 19, 0));
        contRoomState.setBounds(new Rectangle(10, 340, 270, 19));
        kDPanel1.add(contRoomState, new KDLayout.Constraints(10, 340, 270, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(716, 280, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(716, 280, 270, 19, 0));
        chkIsForSHE.setBounds(new Rectangle(10, 431, 140, 19));
        kDPanel1.add(chkIsForSHE, new KDLayout.Constraints(10, 431, 140, 19, 0));
        chkIsForTen.setBounds(new Rectangle(363, 430, 140, 19));
        kDPanel1.add(chkIsForTen, new KDLayout.Constraints(363, 430, 140, 19, 0));
        chkIsForPPM.setBounds(new Rectangle(716, 400, 140, 19));
        kDPanel1.add(chkIsForPPM, new KDLayout.Constraints(716, 400, 140, 19, 0));
        contTenancyState.setBounds(new Rectangle(363, 370, 270, 19));
        kDPanel1.add(contTenancyState, new KDLayout.Constraints(363, 370, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(716, 340, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(716, 340, 270, 19, 0));
        lcBuildingFloor.setBounds(new Rectangle(363, 70, 270, 19));
        kDPanel1.add(lcBuildingFloor, new KDLayout.Constraints(363, 70, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(10, 70, 270, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(10, 70, 270, 19, 0));
        contRoomPropNo.setBounds(new Rectangle(363, 340, 270, 19));
        kDPanel1.add(contRoomPropNo, new KDLayout.Constraints(363, 340, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(11, 370, 270, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(11, 370, 270, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(716, 370, 270, 19));
        kDPanel1.add(kDLabelContainer5, new KDLayout.Constraints(716, 370, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(363, 400, 270, 19));
        kDPanel1.add(kDLabelContainer6, new KDLayout.Constraints(363, 400, 270, 19, 0));
        kDLabelContainer7.setBounds(new Rectangle(715, 310, 270, 19));
        kDPanel1.add(kDLabelContainer7, new KDLayout.Constraints(715, 310, 270, 19, 0));
        contWindow.setBounds(new Rectangle(10, 400, 270, 19));
        kDPanel1.add(contWindow, new KDLayout.Constraints(10, 400, 270, 19, 0));
        contTenancyArea.setBounds(new Rectangle(363, 100, 270, 19));
        kDPanel1.add(contTenancyArea, new KDLayout.Constraints(363, 100, 270, 19, 0));
        lblRoomChange.setBounds(new Rectangle(10, 489, 952, 199));
        kDPanel1.add(lblRoomChange, new KDLayout.Constraints(10, 489, 952, 199, 0));
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contProjectNumber
        contProjectNumber.setBoundEditor(txtProjectNumber);
        //contProjectName
        contProjectName.setBoundEditor(txtProjectName);
        //contSubarea
        contSubarea.setBoundEditor(txtSubarea);
        //contBuilding
        contBuilding.setBoundEditor(txtBuilding);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contUnit
        contUnit.setBoundEditor(prmtBuildUnit);
        //contFloor
        contFloor.setBoundEditor(spiFloor);
        //contSerialNumber
        contSerialNumber.setBoundEditor(spiSerialNumber);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contApportionArea
        contApportionArea.setBoundEditor(txtApportionArea);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtBalconyArea);
        //contGuardenArea
        contGuardenArea.setBoundEditor(txtGuardenArea);
        //contCarbarnArea
        contCarbarnArea.setBoundEditor(txtCarbarnArea);
        //contUseArea
        contUseArea.setBoundEditor(txtUseArea);
        //contFlatArea
        contFlatArea.setBoundEditor(txtFlatArea);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contFloorHeight
        contFloorHeight.setBoundEditor(txtFloorHeight);
        //contFace
        contFace.setBoundEditor(f7Direction);
        //contOutlook
        contOutlook.setBoundEditor(f7Sight);
        //contRoomModel
        contRoomModel.setBoundEditor(f7RoomModel);
        //contRoomForm
        contRoomForm.setBoundEditor(f7roomForm);
        //contDeliverHouseStandard
        contDeliverHouseStandard.setBoundEditor(txtDeliverHouseStandard);
        //contFitmentStandard
        contFitmentStandard.setBoundEditor(txtFitmentStandard);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contProductType
        contProductType.setBoundEditor(f7ProductType);
        //contRoomState
        contRoomState.setBoundEditor(comboRoomState);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(f7ProductDetail);
        //contTenancyState
        contTenancyState.setBoundEditor(comboTenancyState);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(f7RoomUsage);
        //lcBuildingFloor
        lcBuildingFloor.setBoundEditor(f7BuildingFloor);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtRoomNo);
        //contRoomPropNo
        contRoomPropNo.setBoundEditor(txtRoomPropNo);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(f7EyeSihnht);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(f7DecoraStd);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(f7PosseStd);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(f7Noise);
        //contWindow
        contWindow.setBoundEditor(comboWindow);
        //contTenancyArea
        contTenancyArea.setBoundEditor(txtTenancyArea);
        //lblRoomChange
lblRoomChange.getContentPane().setLayout(new BorderLayout(0, 0));        lblRoomChange.getContentPane().add(tblAreaChageHis, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(scPanel, BorderLayout.CENTER);
        kDPanel2.add(kDPanel3, BorderLayout.NORTH);
        //kDPanel3
        kDPanel3.setLayout(null);        contRoomPic.setBounds(new Rectangle(10, 10, 455, 23));
        kDPanel3.add(contRoomPic, null);
        btnRoomPic.setBounds(new Rectangle(489, 10, 105, 24));
        kDPanel3.add(btnRoomPic, null);
        //contRoomPic
        contRoomPic.setBoundEditor(txtRoomPic);

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
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
	        getActionManager().registerUIState(STATUS_ADDNEW, this.actionCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.actionCancelCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.actionRemove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionSubmit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionCancelCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionRemove, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_VIEW, this.actionEdit, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_VIEW, this.actionCopy, ActionStateConst.ENABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomEditUIHandler";
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
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		            this.actionCancel.setEnabled(false);
		            this.actionCancelCancel.setEnabled(false);
		            this.actionRemove.setEnabled(false);
		            this.actionEdit.setEnabled(false);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.actionEdit.setEnabled(false);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.actionSave.setEnabled(false);
		            this.actionSubmit.setEnabled(false);
		            this.actionCancel.setEnabled(false);
		            this.actionCancelCancel.setEnabled(false);
		            this.actionRemove.setEnabled(false);
		            this.actionEdit.setEnabled(true);
		            this.actionCopy.setEnabled(true);
        }
    }

    /**
     * output txtRoomArea_dataChanged method
     */
    protected void txtRoomArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtBuildingArea_dataChanged method
     */
    protected void txtBuildingArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtNumber_KeyReleased method
     */
    protected void txtNumber_KeyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output chkIsForSHE_actionPerformed method
     */
    protected void chkIsForSHE_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsForTen_actionPerformed method
     */
    protected void chkIsForTen_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRoomPic_actionPerformed method
     */
    protected void btnRoomPic_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7BuildingFloor_stateChanged method
     */
    protected void f7BuildingFloor_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output f7BuildingFloor_dataChanged method
     */
    protected void f7BuildingFloor_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
     * output actionPageSetup_actionPerformed method
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }
    	

    /**
     * output actionExitCurrent_actionPerformed method
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }
    	

    /**
     * output actionHelp_actionPerformed method
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }
    	

    /**
     * output actionAbout_actionPerformed method
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }
    	

    /**
     * output actionOnLoad_actionPerformed method
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }
    	

    /**
     * output actionSendMessage_actionPerformed method
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }
    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }
    	

    /**
     * output actionExport_actionPerformed method
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }
    	

    /**
     * output actionExportSelected_actionPerformed method
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }
    	

    /**
     * output actionRegProduct_actionPerformed method
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }
    	

    /**
     * output actionPersonalSite_actionPerformed method
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }
    	

    /**
     * output actionProcductVal_actionPerformed method
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }
    	

    /**
     * output actionCancelCancel_actionPerformed method
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }
    	

    /**
     * output actionPre_actionPerformed method
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }
    	

    /**
     * output actionNext_actionPerformed method
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }
    	

    /**
     * output actionLast_actionPerformed method
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output actionCopy_actionPerformed method
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
    	

    /**
     * output actionSubmitOption_actionPerformed method
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }
	public RequestContext prepareActionPageSetup(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPageSetup(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPageSetup() {
    	return false;
    }
	public RequestContext prepareActionExitCurrent(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExitCurrent(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExitCurrent() {
    	return false;
    }
	public RequestContext prepareActionHelp(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionHelp(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHelp() {
    	return false;
    }
	public RequestContext prepareActionAbout(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAbout(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAbout() {
    	return false;
    }
	public RequestContext prepareActionOnLoad(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionOnLoad(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOnLoad() {
    	return false;
    }
	public RequestContext prepareActionSendMessage(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSendMessage(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSendMessage() {
    	return false;
    }
	public RequestContext prepareActionCalculator(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCalculator(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalculator() {
    	return false;
    }
	public RequestContext prepareActionExport(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExport(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExport() {
    	return false;
    }
	public RequestContext prepareActionExportSelected(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExportSelected(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportSelected() {
    	return false;
    }
	public RequestContext prepareActionRegProduct(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRegProduct(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRegProduct() {
    	return false;
    }
	public RequestContext prepareActionPersonalSite(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPersonalSite(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonalSite() {
    	return false;
    }
	public RequestContext prepareactionProcductVal(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionProcductVal(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionProcductVal() {
    	return false;
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }
	public RequestContext prepareActionCancelCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancelCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelCancel() {
    	return false;
    }
	public RequestContext prepareActionFirst(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionFirst(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirst() {
    	return false;
    }
	public RequestContext prepareActionPre(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPre(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPre() {
    	return false;
    }
	public RequestContext prepareActionNext(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionNext(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNext() {
    	return false;
    }
	public RequestContext prepareActionLast(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionLast(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLast() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionCopy(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCopy(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCopy() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionEdit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }
	public RequestContext prepareActionSubmitOption(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmitOption(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitOption() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.RoomEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.RoomFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.RoomInfo objectValue = new com.kingdee.eas.fdc.sellhouse.RoomInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblAreaChageHis;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}