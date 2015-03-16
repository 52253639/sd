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
public abstract class AbstractRoomListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblGraph;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditRoomBind;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMerge;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSeqAdjustmentShow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditRoomUpdate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditRoomPropNo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEditRoomBind;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMerge;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSeqAdjustmentShow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem1;
    protected ActionEditRoomBind actionEditRoomBind = null;
    protected ActionMerge actionMerge = null;
    protected ActionSplit actionSplit = null;
    protected SeqAdjustmentUIShow seqAdjustmentUIShow = null;
    protected ActionBatchModifyRoomPropNo actionBatchModifyRoomPropNo = null;
    protected ActionInit actionInit = null;
    protected ActionBatchModifyRoomUpdate actionBatchModifyRoomUpdate = null;
    /**
     * output class constructor
     */
    public AbstractRoomListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomQuery");
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

        actionHelp.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
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
        //actionView
        actionView.setEnabled(true);
        actionView.setDaemonRun(false);

        actionView.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
        _tempStr = resHelper.getString("ActionView.SHORT_DESCRIPTION");
        actionView.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.LONG_DESCRIPTION");
        actionView.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.NAME");
        actionView.putValue(ItemAction.NAME, _tempStr);
         this.actionView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemove
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRefresh
        actionRefresh.setEnabled(true);
        actionRefresh.setDaemonRun(false);

        actionRefresh.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
        _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
        actionRefresh.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
        actionRefresh.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.NAME");
        actionRefresh.putValue(ItemAction.NAME, _tempStr);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
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
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionLocate
        actionLocate.setEnabled(true);
        actionLocate.setDaemonRun(false);

        actionLocate.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F6"));
        _tempStr = resHelper.getString("ActionLocate.SHORT_DESCRIPTION");
        actionLocate.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLocate.LONG_DESCRIPTION");
        actionLocate.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLocate.NAME");
        actionLocate.putValue(ItemAction.NAME, _tempStr);
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionQuery
        actionQuery.setEnabled(true);
        actionQuery.setDaemonRun(false);

        actionQuery.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Q"));
        _tempStr = resHelper.getString("ActionQuery.SHORT_DESCRIPTION");
        actionQuery.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQuery.LONG_DESCRIPTION");
        actionQuery.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQuery.NAME");
        actionQuery.putValue(ItemAction.NAME, _tempStr);
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionImportData
        actionImportData.setEnabled(true);
        actionImportData.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionImportData.SHORT_DESCRIPTION");
        actionImportData.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImportData.LONG_DESCRIPTION");
        actionImportData.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImportData.NAME");
        actionImportData.putValue(ItemAction.NAME, _tempStr);
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportData
        actionExportData.setEnabled(true);
        actionExportData.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionExportData.SHORT_DESCRIPTION");
        actionExportData.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportData.LONG_DESCRIPTION");
        actionExportData.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportData.NAME");
        actionExportData.putValue(ItemAction.NAME, _tempStr);
         this.actionExportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionToExcel
        actionToExcel.setEnabled(true);
        actionToExcel.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionToExcel.SHORT_DESCRIPTION");
        actionToExcel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionToExcel.LONG_DESCRIPTION");
        actionToExcel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionToExcel.NAME");
        actionToExcel.putValue(ItemAction.NAME, _tempStr);
         this.actionToExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStartWorkFlow
        actionStartWorkFlow.setEnabled(true);
        actionStartWorkFlow.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionStartWorkFlow.SHORT_DESCRIPTION");
        actionStartWorkFlow.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionStartWorkFlow.LONG_DESCRIPTION");
        actionStartWorkFlow.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionStartWorkFlow.NAME");
        actionStartWorkFlow.putValue(ItemAction.NAME, _tempStr);
         this.actionStartWorkFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPublishReport
        actionPublishReport.setEnabled(true);
        actionPublishReport.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionPublishReport.SHORT_DESCRIPTION");
        actionPublishReport.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPublishReport.LONG_DESCRIPTION");
        actionPublishReport.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPublishReport.NAME");
        actionPublishReport.putValue(ItemAction.NAME, _tempStr);
         this.actionPublishReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        //actionMoveTree
        actionMoveTree.setEnabled(true);
        actionMoveTree.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionMoveTree.SHORT_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.LONG_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.NAME");
        actionMoveTree.putValue(ItemAction.NAME, _tempStr);
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionEditRoomBind
        this.actionEditRoomBind = new ActionEditRoomBind(this);
        getActionManager().registerAction("actionEditRoomBind", actionEditRoomBind);
         this.actionEditRoomBind.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMerge
        this.actionMerge = new ActionMerge(this);
        getActionManager().registerAction("actionMerge", actionMerge);
         this.actionMerge.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //seqAdjustmentUIShow
        this.seqAdjustmentUIShow = new SeqAdjustmentUIShow(this);
        getActionManager().registerAction("seqAdjustmentUIShow", seqAdjustmentUIShow);
         this.seqAdjustmentUIShow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchModifyRoomPropNo
        this.actionBatchModifyRoomPropNo = new ActionBatchModifyRoomPropNo(this);
        getActionManager().registerAction("actionBatchModifyRoomPropNo", actionBatchModifyRoomPropNo);
         this.actionBatchModifyRoomPropNo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInit
        this.actionInit = new ActionInit(this);
        getActionManager().registerAction("actionInit", actionInit);
         this.actionInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchModifyRoomUpdate
        this.actionBatchModifyRoomUpdate = new ActionBatchModifyRoomUpdate(this);
        getActionManager().registerAction("actionBatchModifyRoomUpdate", actionBatchModifyRoomUpdate);
         this.actionBatchModifyRoomUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.tblGraph = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnEditRoomBind = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMerge = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSeqAdjustmentShow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditRoomUpdate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditRoomPropNo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemEditRoomBind = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemMerge = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSeqAdjustmentShow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemInit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuItem1 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.tblGraph.setName("tblGraph");
        this.btnEditRoomBind.setName("btnEditRoomBind");
        this.btnSplit.setName("btnSplit");
        this.btnMerge.setName("btnMerge");
        this.btnSeqAdjustmentShow.setName("btnSeqAdjustmentShow");
        this.btnEditRoomUpdate.setName("btnEditRoomUpdate");
        this.btnEditRoomPropNo.setName("btnEditRoomPropNo");
        this.btnInit.setName("btnInit");
        this.menuItemEditRoomBind.setName("menuItemEditRoomBind");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemMerge.setName("menuItemMerge");
        this.menuItemSeqAdjustmentShow.setName("menuItemSeqAdjustmentShow");
        this.menuItemInit.setName("menuItemInit");
        this.kDMenuItem1.setName("kDMenuItem1");
        // CoreUI
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
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"subarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"unit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomPropNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"floor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"serialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"roomModel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"face\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"sight.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"buildingProperty.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"tenancyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"apportionArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"balconyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"guardenArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"carbarnArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"useArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"flatArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"houseProperty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"roomForm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"productDetial\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"sellState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"isForSHE\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"isForTen\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"isForPPM\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"roomUsage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"posseStd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"eyeSignht\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"decoraStd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"noise\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{subarea}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{roomPropNo}</t:Cell><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{serialNumber}</t:Cell><t:Cell>$Resource{roomModel.name}</t:Cell><t:Cell>$Resource{face}</t:Cell><t:Cell>$Resource{sight.name}</t:Cell><t:Cell>$Resource{buildingProperty.name}</t:Cell><t:Cell>$Resource{tenancyArea}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{apportionArea}</t:Cell><t:Cell>$Resource{balconyArea}</t:Cell><t:Cell>$Resource{guardenArea}</t:Cell><t:Cell>$Resource{carbarnArea}</t:Cell><t:Cell>$Resource{useArea}</t:Cell><t:Cell>$Resource{flatArea}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{houseProperty}</t:Cell><t:Cell>$Resource{roomForm}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{productDetial}</t:Cell><t:Cell>$Resource{sellState}</t:Cell><t:Cell>$Resource{isForSHE}</t:Cell><t:Cell>$Resource{isForTen}</t:Cell><t:Cell>$Resource{isForPPM}</t:Cell><t:Cell>$Resource{roomUsage}</t:Cell><t:Cell>$Resource{posseStd}</t:Cell><t:Cell>$Resource{eyeSignht}</t:Cell><t:Cell>$Resource{decoraStd}</t:Cell><t:Cell>$Resource{noise}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","subarea.name","building.name","buildUnit.name","displayName","roomNo","roomPropNo","floor","serialNumber","roomModel.name","direction.name","sight.name","buildingProperty.name","tenancyArea","buildingArea","roomArea","apportionArea","balconyArea","guardenArea","carbarnArea","useArea","flatArea","actualBuildingArea","actualRoomArea","houseProperty","roomForm.name","productType.name","productDetial.name","sellState","isForSHE","isForTen","isForPPM","roomUsage.name","posseStd.name","eyeSignht.name","decoraStd.name","noise.name"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        this.btnAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNew.setText(resHelper.getString("btnAddNew.text"));		
        this.btnAddNew.setToolTipText(resHelper.getString("btnAddNew.toolTipText"));
        this.btnView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnView.setText(resHelper.getString("btnView.text"));		
        this.btnView.setToolTipText(resHelper.getString("btnView.toolTipText"));
        this.btnEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));
        this.btnRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemove.setText(resHelper.getString("btnRemove.text"));		
        this.btnRemove.setToolTipText(resHelper.getString("btnRemove.toolTipText"));
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
        this.btnLocate.setAction((IItemAction)ActionProxyFactory.getProxy(actionLocate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLocate.setText(resHelper.getString("btnLocate.text"));		
        this.btnLocate.setToolTipText(resHelper.getString("btnLocate.toolTipText"));
        this.btnQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuery.setText(resHelper.getString("btnQuery.text"));		
        this.btnQuery.setToolTipText(resHelper.getString("btnQuery.toolTipText"));
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));		
        this.btnPrint.setToolTipText(resHelper.getString("btnPrint.toolTipText"));
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));		
        this.btnPrintPreview.setToolTipText(resHelper.getString("btnPrintPreview.toolTipText"));
        this.menuItemAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddNew.setText(resHelper.getString("menuItemAddNew.text"));		
        this.menuItemAddNew.setToolTipText(resHelper.getString("menuItemAddNew.toolTipText"));		
        this.menuItemAddNew.setMnemonic(78);
        this.menuItemImportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportData.setText(resHelper.getString("menuItemImportData.text"));		
        this.menuItemImportData.setToolTipText(resHelper.getString("menuItemImportData.toolTipText"));		
        this.menuItemImportData.setVisible(false);		
        this.menuItemImportData.setMnemonic(73);
        this.menuItemPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setToolTipText(resHelper.getString("menuItemPrint.toolTipText"));		
        this.menuItemPrint.setMnemonic(80);
        this.menuItemPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintPreview.setText(resHelper.getString("menuItemPrintPreview.text"));		
        this.menuItemPrintPreview.setToolTipText(resHelper.getString("menuItemPrintPreview.toolTipText"));		
        this.menuItemPrintPreview.setMnemonic(86);		
        this.menuEdit.setText(resHelper.getString("menuEdit.text"));		
        this.menuEdit.setMnemonic(69);
        this.menuItemEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setToolTipText(resHelper.getString("menuItemEdit.toolTipText"));		
        this.menuItemEdit.setMnemonic(69);
        this.menuItemRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRemove.setText(resHelper.getString("menuItemRemove.text"));		
        this.menuItemRemove.setToolTipText(resHelper.getString("menuItemRemove.toolTipText"));		
        this.menuItemRemove.setMnemonic(82);		
        this.menuView.setText(resHelper.getString("menuView.text"));		
        this.menuView.setMnemonic(86);
        this.menuItemView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemView.setText(resHelper.getString("menuItemView.text"));		
        this.menuItemView.setToolTipText(resHelper.getString("menuItemView.toolTipText"));		
        this.menuItemView.setMnemonic(86);
        this.menuItemLocate.setAction((IItemAction)ActionProxyFactory.getProxy(actionLocate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLocate.setText(resHelper.getString("menuItemLocate.text"));		
        this.menuItemLocate.setToolTipText(resHelper.getString("menuItemLocate.toolTipText"));		
        this.menuItemLocate.setMnemonic(76);
        this.menuItemQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuery.setText(resHelper.getString("menuItemQuery.text"));		
        this.menuItemQuery.setToolTipText(resHelper.getString("menuItemQuery.toolTipText"));		
        this.menuItemQuery.setMnemonic(81);
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setToolTipText(resHelper.getString("menuItemRefresh.toolTipText"));		
        this.menuItemRefresh.setMnemonic(82);
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));
        this.menuItemExportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExportData.setText(resHelper.getString("menuItemExportData.text"));		
        this.menuItemExportData.setToolTipText(resHelper.getString("menuItemExportData.toolTipText"));		
        this.menuItemExportData.setVisible(false);		
        this.menuItemExportData.setMnemonic(69);		
        this.menuTools.setText(resHelper.getString("menuTools.text"));		
        this.menuTools.setToolTipText(resHelper.getString("menuTools.toolTipText"));		
        this.menuTools.setEnabled(false);		
        this.menuTools.setVisible(false);
        this.menuItemStartWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionStartWorkFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemStartWorkFlow.setText(resHelper.getString("menuItemStartWorkFlow.text"));		
        this.menuItemStartWorkFlow.setToolTipText(resHelper.getString("menuItemStartWorkFlow.toolTipText"));
        this.menuItemPublishReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionPublishReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPublishReport.setText(resHelper.getString("menuItemPublishReport.text"));		
        this.menuItemPublishReport.setToolTipText(resHelper.getString("menuItemPublishReport.toolTipText"));		
        this.menuMail.setText(resHelper.getString("menuMail.text"));		
        this.menuMail.setToolTipText(resHelper.getString("menuMail.toolTipText"));
        this.menuItemToExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionToExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemToExcel.setText(resHelper.getString("menuItemToExcel.text"));		
        this.menuItemToExcel.setToolTipText(resHelper.getString("menuItemToExcel.toolTipText"));		
        this.menuItemToHTML.setText(resHelper.getString("menuItemToHTML.text"));		
        this.menuItemToHTML.setToolTipText(resHelper.getString("menuItemToHTML.toolTipText"));		
        this.menuItemToHTML.setEnabled(false);		
        this.menuItemCopyScreen.setText(resHelper.getString("menuItemCopyScreen.text"));		
        this.menuItemCopyScreen.setToolTipText(resHelper.getString("menuItemCopyScreen.toolTipText"));		
        this.menuItemCopyScreen.setEnabled(false);
        this.MenuItemAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.MenuItemAttachment.setMnemonic(74);		
        this.menuBiz.setText(resHelper.getString("menuBiz.text"));		
        this.menuBiz.setMnemonic(79);		
        this.menuBiz.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW2.setOrientation(1);		
        this.separatorFW2.setVisible(false);
        this.menuItemCancelCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelCancel.setText(resHelper.getString("menuItemCancelCancel.text"));		
        this.menuItemCancelCancel.setToolTipText(resHelper.getString("menuItemCancelCancel.toolTipText"));		
        this.menuItemCancelCancel.setMnemonic(83);
        this.menuItemCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancel.setText(resHelper.getString("menuItemCancel.text"));		
        this.menuItemCancel.setToolTipText(resHelper.getString("menuItemCancel.toolTipText"));		
        this.menuItemCancel.setMnemonic(67);
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));		
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));		
        this.btnCancel.setVisible(false);
        this.btnCancelCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));		
        this.btnCancelCancel.setVisible(false);		
        this.separatorFW1.setOrientation(1);		
        this.pnlMain.setDividerSize(8);		
        this.pnlMain.setDividerLocation(240);		
        this.treeView.setShowControlPanel(false);
        this.btnMoveTree.setAction((IItemAction)ActionProxyFactory.getProxy(actionMoveTree, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMoveTree.setText(resHelper.getString("btnMoveTree.text"));		
        this.btnMoveTree.setToolTipText(resHelper.getString("btnMoveTree.toolTipText"));		
        this.btnMoveTree.setVisible(false);
        this.menuItemMoveTree.setAction((IItemAction)ActionProxyFactory.getProxy(actionMoveTree, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMoveTree.setText(resHelper.getString("menuItemMoveTree.text"));		
        this.menuItemMoveTree.setVisible(false);		
        this.menuItemMoveTree.setMnemonic(77);		
        this.separatorEdit1.setVisible(false);
        // kDTabbedPane1
        // kDPanel1		
        this.kDPanel1.setMinimumSize(new Dimension(0,0));		
        this.kDPanel1.setPreferredSize(new Dimension(0,0));
        // kDScrollPane1		
        this.kDScrollPane1.setPreferredSize(new Dimension(30,30));
        // tblGraph
        this.tblGraph.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblGraph_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblGraph.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblGraph_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.tblGraph.checkParsed();
        this.tblGraph.getGroupManager().setGroup(true);
        // btnEditRoomBind
        this.btnEditRoomBind.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditRoomBind, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditRoomBind.setText(resHelper.getString("btnEditRoomBind.text"));		
        this.btnEditRoomBind.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // btnMerge
        this.btnMerge.setAction((IItemAction)ActionProxyFactory.getProxy(actionMerge, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMerge.setText(resHelper.getString("btnMerge.text"));		
        this.btnMerge.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // btnSeqAdjustmentShow
        this.btnSeqAdjustmentShow.setAction((IItemAction)ActionProxyFactory.getProxy(seqAdjustmentUIShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSeqAdjustmentShow.setText(resHelper.getString("btnSeqAdjustmentShow.text"));		
        this.btnSeqAdjustmentShow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_adduction"));
        // btnEditRoomUpdate
        this.btnEditRoomUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchModifyRoomUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditRoomUpdate.setText(resHelper.getString("btnEditRoomUpdate.text"));		
        this.btnEditRoomUpdate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
        // btnEditRoomPropNo
        this.btnEditRoomPropNo.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchModifyRoomPropNo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditRoomPropNo.setText(resHelper.getString("btnEditRoomPropNo.text"));		
        this.btnEditRoomPropNo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
        // btnInit
        this.btnInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInit.setText(resHelper.getString("btnInit.text"));		
        this.btnInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgIcon_update"));
        // menuItemEditRoomBind
        this.menuItemEditRoomBind.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditRoomBind, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEditRoomBind.setText(resHelper.getString("menuItemEditRoomBind.text"));		
        this.menuItemEditRoomBind.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));		
        this.menuItemSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // menuItemMerge
        this.menuItemMerge.setAction((IItemAction)ActionProxyFactory.getProxy(actionMerge, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMerge.setText(resHelper.getString("menuItemMerge.text"));		
        this.menuItemMerge.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // menuItemSeqAdjustmentShow
        this.menuItemSeqAdjustmentShow.setAction((IItemAction)ActionProxyFactory.getProxy(seqAdjustmentUIShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSeqAdjustmentShow.setText(resHelper.getString("menuItemSeqAdjustmentShow.text"));		
        this.menuItemSeqAdjustmentShow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_adduction"));
        // menuItemInit
        this.menuItemInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemInit.setText(resHelper.getString("menuItemInit.text"));
        // kDMenuItem1		
        this.kDMenuItem1.setText(resHelper.getString("kDMenuItem1.text"));
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(22, 1, 994, 585));
        this.add(pnlMain, new KDLayout.Constraints(22, 1, 994, 585, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDTabbedPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDTabbedPane1
        kDTabbedPane1.add(tblMain, resHelper.getString("tblMain.constraints"));
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDScrollPane1, BorderLayout.SOUTH);
        kDPanel1.add(tblGraph, BorderLayout.CENTER);

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(menuItemEditRoomBind);
        menuEdit.add(menuItemSplit);
        menuEdit.add(menuItemMerge);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemSeqAdjustmentShow);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemInit);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);
        menuHelp.add(kDMenuItem1);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnEditRoomBind);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnMerge);
        this.toolBar.add(btnSeqAdjustmentShow);
        this.toolBar.add(btnEditRoomUpdate);
        this.toolBar.add(btnEditRoomPropNo);
        this.toolBar.add(btnInit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tblGraph_tableSelectChanged method
     */
    protected void tblGraph_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblGraph_tableClicked method
     */
    protected void tblGraph_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

			public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("creator.*"));
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("CU.*"));
			sic.add(new SelectorItemInfo("sellOrder.*"));
			sic.add(new SelectorItemInfo("buildUnit.*"));
			sic.add(new SelectorItemInfo("roomModel.*"));
			sic.add(new SelectorItemInfo("buildingProperty.*"));
			sic.add(new SelectorItemInfo("attachmentEntry.*"));
			sic.add(new SelectorItemInfo("attachmentEntry.creator.*"));
			sic.add(new SelectorItemInfo("attachmentEntry.lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("attachmentEntry.CU.*"));
			sic.add(new SelectorItemInfo("attachmentEntry.room.*"));
			sic.add(new SelectorItemInfo("lastPurchase.*"));
			sic.add(new SelectorItemInfo("lastSignContract.*"));
			sic.add(new SelectorItemInfo("lastAreaCompensate.*"));
			sic.add(new SelectorItemInfo("direction.*"));
			sic.add(new SelectorItemInfo("sight.*"));
			sic.add(new SelectorItemInfo("lastKeepDownBill.*"));
			sic.add(new SelectorItemInfo("roomForm.*"));
			sic.add(new SelectorItemInfo("productType.*"));
			sic.add(new SelectorItemInfo("lastTenancy.*"));
			sic.add(new SelectorItemInfo("productDetail.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.creator.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.CU.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.handler.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.auditor.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.orgUnit.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.period.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.roomPriceBill.*"));
			sic.add(new SelectorItemInfo("shePriceHistoryEntrys.priceAdjustBill.*"));
			sic.add(new SelectorItemInfo("habitationRecords.*"));
			sic.add(new SelectorItemInfo("habitationRecords.roomJoin.*"));
			sic.add(new SelectorItemInfo("habitationRecords.joinVoucher.*"));
			sic.add(new SelectorItemInfo("roomUsage.*"));
			sic.add(new SelectorItemInfo("buildingFloor.*"));
			sic.add(new SelectorItemInfo("noise.*"));
			sic.add(new SelectorItemInfo("decoraStd.*"));
			sic.add(new SelectorItemInfo("eyeSignht.*"));
			sic.add(new SelectorItemInfo("posseStd.*"));
			sic.add(new SelectorItemInfo("roomHandover.*"));
			sic.add(new SelectorItemInfo("builStruct.*"));
			sic.add(new SelectorItemInfo("newNoise.*"));
			sic.add(new SelectorItemInfo("newSight.*"));
			sic.add(new SelectorItemInfo("newEyeSight.*"));
			sic.add(new SelectorItemInfo("newDecorastd.*"));
			sic.add(new SelectorItemInfo("newPossStd.*"));
			sic.add(new SelectorItemInfo("newRoomUsage.*"));
			sic.add(new SelectorItemInfo("newProduceRemark.*"));
			return sic;
		}

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("floor"));
        sic.add(new SelectorItemInfo("serialNumber"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("buildingProperty.name"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("apportionArea"));
        sic.add(new SelectorItemInfo("balconyArea"));
        sic.add(new SelectorItemInfo("guardenArea"));
        sic.add(new SelectorItemInfo("carbarnArea"));
        sic.add(new SelectorItemInfo("useArea"));
        sic.add(new SelectorItemInfo("flatArea"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("sellState"));
        sic.add(new SelectorItemInfo("sight.name"));
        sic.add(new SelectorItemInfo("productType.name"));
        sic.add(new SelectorItemInfo("isForSHE"));
        sic.add(new SelectorItemInfo("isForTen"));
        sic.add(new SelectorItemInfo("isForPPM"));
        sic.add(new SelectorItemInfo("houseProperty"));
        sic.add(new SelectorItemInfo("roomForm.name"));
        sic.add(new SelectorItemInfo("productDetial.name"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("subarea.name"));
        sic.add(new SelectorItemInfo("direction.name"));
        sic.add(new SelectorItemInfo("buildUnit.name"));
        sic.add(new SelectorItemInfo("roomNo"));
        sic.add(new SelectorItemInfo("roomPropNo"));
        sic.add(new SelectorItemInfo("roomUsage.name"));
        sic.add(new SelectorItemInfo("posseStd.name"));
        sic.add(new SelectorItemInfo("eyeSignht.name"));
        sic.add(new SelectorItemInfo("decoraStd.name"));
        sic.add(new SelectorItemInfo("noise.name"));
        sic.add(new SelectorItemInfo("tenancyArea"));
        sic.add(new SelectorItemInfo("displayName"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        return sorterFieldList; 
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
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionView_actionPerformed method
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
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
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
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
     * output actionLocate_actionPerformed method
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }
    	

    /**
     * output actionQuery_actionPerformed method
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
    	

    /**
     * output actionImportData_actionPerformed method
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
    	

    /**
     * output actionExportData_actionPerformed method
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }
    	

    /**
     * output actionToExcel_actionPerformed method
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }
    	

    /**
     * output actionStartWorkFlow_actionPerformed method
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }
    	

    /**
     * output actionPublishReport_actionPerformed method
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
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
     * output actionMoveTree_actionPerformed method
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }
    	

    /**
     * output actionEditRoomBind_actionPerformed method
     */
    public void actionEditRoomBind_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMerge_actionPerformed method
     */
    public void actionMerge_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output seqAdjustmentUIShow_actionPerformed method
     */
    public void seqAdjustmentUIShow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchModifyRoomPropNo_actionPerformed method
     */
    public void actionBatchModifyRoomPropNo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInit_actionPerformed method
     */
    public void actionInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchModifyRoomUpdate_actionPerformed method
     */
    public void actionBatchModifyRoomUpdate_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionView(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView() {
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
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRefresh(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
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
	public RequestContext prepareActionLocate(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionLocate(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLocate() {
    	return false;
    }
	public RequestContext prepareActionQuery(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionQuery(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuery() {
    	return false;
    }
	public RequestContext prepareActionImportData(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionImportData(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportData() {
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
	public RequestContext prepareActionExportData(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExportData(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportData() {
    	return false;
    }
	public RequestContext prepareActionToExcel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionToExcel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToExcel() {
    	return false;
    }
	public RequestContext prepareActionStartWorkFlow(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionStartWorkFlow(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStartWorkFlow() {
    	return false;
    }
	public RequestContext prepareActionPublishReport(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPublishReport(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPublishReport() {
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
	public RequestContext prepareActionMoveTree(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionMoveTree(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveTree() {
    	return false;
    }
	public RequestContext prepareActionEditRoomBind(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditRoomBind() {
    	return false;
    }
	public RequestContext prepareActionMerge(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMerge() {
    	return false;
    }
	public RequestContext prepareActionSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplit() {
    	return false;
    }
	public RequestContext prepareSeqAdjustmentUIShow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareSeqAdjustmentUIShow() {
    	return false;
    }
	public RequestContext prepareActionBatchModifyRoomPropNo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchModifyRoomPropNo() {
    	return false;
    }
	public RequestContext prepareActionInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInit() {
    	return false;
    }
	public RequestContext prepareActionBatchModifyRoomUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchModifyRoomUpdate() {
    	return false;
    }

    /**
     * output ActionEditRoomBind class
     */     
    protected class ActionEditRoomBind extends ItemAction {     
    
        public ActionEditRoomBind()
        {
            this(null);
        }

        public ActionEditRoomBind(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEditRoomBind.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditRoomBind.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditRoomBind.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "ActionEditRoomBind", "actionEditRoomBind_actionPerformed", e);
        }
    }

    /**
     * output ActionMerge class
     */     
    protected class ActionMerge extends ItemAction {     
    
        public ActionMerge()
        {
            this(null);
        }

        public ActionMerge(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMerge.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMerge.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMerge.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "ActionMerge", "actionMerge_actionPerformed", e);
        }
    }

    /**
     * output ActionSplit class
     */     
    protected class ActionSplit extends ItemAction {     
    
        public ActionSplit()
        {
            this(null);
        }

        public ActionSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output SeqAdjustmentUIShow class
     */     
    protected class SeqAdjustmentUIShow extends ItemAction {     
    
        public SeqAdjustmentUIShow()
        {
            this(null);
        }

        public SeqAdjustmentUIShow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("SeqAdjustmentUIShow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("SeqAdjustmentUIShow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("SeqAdjustmentUIShow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "SeqAdjustmentUIShow", "seqAdjustmentUIShow_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchModifyRoomPropNo class
     */     
    protected class ActionBatchModifyRoomPropNo extends ItemAction {     
    
        public ActionBatchModifyRoomPropNo()
        {
            this(null);
        }

        public ActionBatchModifyRoomPropNo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchModifyRoomPropNo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchModifyRoomPropNo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchModifyRoomPropNo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "ActionBatchModifyRoomPropNo", "actionBatchModifyRoomPropNo_actionPerformed", e);
        }
    }

    /**
     * output ActionInit class
     */     
    protected class ActionInit extends ItemAction {     
    
        public ActionInit()
        {
            this(null);
        }

        public ActionInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "ActionInit", "actionInit_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchModifyRoomUpdate class
     */     
    protected class ActionBatchModifyRoomUpdate extends ItemAction {     
    
        public ActionBatchModifyRoomUpdate()
        {
            this(null);
        }

        public ActionBatchModifyRoomUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchModifyRoomUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchModifyRoomUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchModifyRoomUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomListUI.this, "ActionBatchModifyRoomUpdate", "actionBatchModifyRoomUpdate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomListUI");
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
        return objectValue;
    }




}