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
public abstract class AbstractOperationSetupEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOperationSetupEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea bizDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshortName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshortName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsetupValue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsetupValue;
    protected com.kingdee.eas.fdc.market.OperationSetupInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractOperationSetupEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOperationSetupEditUI.class.getName());
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
        actionFirst.setEnabled(true);
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
        actionCopy.setEnabled(false);
        actionCopy.setDaemonRun(false);

        actionCopy.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl C"));
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
        actionAttachment.setEnabled(false);
        actionAttachment.setDaemonRun(false);

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
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bizDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.contshortName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtshortName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contsetupValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtsetupValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.bizDescription.setName("bizDescription");
        this.kDLabel1.setName("kDLabel1");
        this.bizName.setName("bizName");
        this.contshortName.setName("contshortName");
        this.txtshortName.setName("txtshortName");
        this.contsetupValue.setName("contsetupValue");
        this.txtsetupValue.setName("txtsetupValue");
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
        this.menuHelp.setText(resHelper.getString("menuHelp.text"));		
        this.menuHelp.setMnemonic(72);
        this.menuItemHelp.setAction((IItemAction)ActionProxyFactory.getProxy(actionHelp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHelp.setText(resHelper.getString("menuItemHelp.text"));		
        this.menuItemHelp.setToolTipText(resHelper.getString("menuItemHelp.toolTipText"));		
        this.menuItemHelp.setMnemonic(72);
        this.menuItemAbout.setAction((IItemAction)ActionProxyFactory.getProxy(actionAbout, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAbout.setText(resHelper.getString("menuItemAbout.text"));		
        this.menuItemAbout.setToolTipText(resHelper.getString("menuItemAbout.toolTipText"));		
        this.menuItemAbout.setMnemonic(65);		
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
        this.btnAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNew.setText(resHelper.getString("btnAddNew.text"));		
        this.btnAddNew.setToolTipText(resHelper.getString("btnAddNew.toolTipText"));
        this.btnEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSave.setVisible(false);
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));
        this.btnCopy.setAction((IItemAction)ActionProxyFactory.getProxy(actionCopy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCopy.setText(resHelper.getString("btnCopy.text"));		
        this.btnCopy.setToolTipText(resHelper.getString("btnCopy.toolTipText"));		
        this.btnCopy.setVisible(false);
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
        this.btnFirst.setVisible(false);
        this.btnPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPre.setText(resHelper.getString("btnPre.text"));		
        this.btnPre.setToolTipText(resHelper.getString("btnPre.toolTipText"));		
        this.btnPre.setVisible(false);
        this.btnNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNext.setText(resHelper.getString("btnNext.text"));		
        this.btnNext.setToolTipText(resHelper.getString("btnNext.toolTipText"));		
        this.btnNext.setVisible(false);
        this.btnLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLast.setText(resHelper.getString("btnLast.text"));		
        this.btnLast.setToolTipText(resHelper.getString("btnLast.toolTipText"));		
        this.btnLast.setVisible(false);
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));		
        this.btnPrint.setToolTipText(resHelper.getString("btnPrint.toolTipText"));		
        this.btnPrint.setVisible(false);
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));		
        this.btnPrintPreview.setToolTipText(resHelper.getString("btnPrintPreview.toolTipText"));		
        this.btnPrintPreview.setVisible(false);
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
        this.menuItemCopy.setVisible(false);		
        this.kDSeparator4.setVisible(false);		
        this.kDSeparator4.setEnabled(false);		
        this.menuView.setText(resHelper.getString("menuView.text"));		
        this.menuView.setToolTipText(resHelper.getString("menuView.toolTipText"));		
        this.menuView.setMnemonic(86);		
        this.menuView.setVisible(false);
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
        this.btnAttachment.setVisible(false);
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
        this.menuSubmitOption.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
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
        this.menuBiz.setVisible(false);
        this.menuItemCancelCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelCancel.setText(resHelper.getString("menuItemCancelCancel.text"));		
        this.menuItemCancelCancel.setMnemonic(83);
        this.menuItemCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancel.setText(resHelper.getString("menuItemCancel.text"));		
        this.menuItemCancel.setMnemonic(67);		
        this.separatorFW1.setOrientation(1);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setOrientation(1);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setOrientation(1);		
        this.separatorFW3.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setBoundLabelAlignment(7);		
        this.contName.setVisible(true);		
        this.contName.setForeground(new java.awt.Color(0,0,0));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);		
        this.contNumber.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setVisible(true);		
        this.chkIsEnabled.setEnabled(true);		
        this.chkIsEnabled.setForeground(new java.awt.Color(0,0,0));		
        this.chkIsEnabled.setHorizontalAlignment(2);
        // bizDescription		
        this.bizDescription.setMaxLength(200);		
        this.bizDescription.setVisible(true);		
        this.bizDescription.setEnabled(true);		
        this.bizDescription.setForeground(new java.awt.Color(0,0,0));		
        this.bizDescription.setRequired(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setVisible(true);		
        this.kDLabel1.setForeground(new java.awt.Color(0,0,0));		
        this.kDLabel1.setHorizontalAlignment(2);
        // bizName		
        this.bizName.setRequired(true);		
        this.bizName.setMaxLength(80);		
        this.bizName.setVisible(true);		
        this.bizName.setEnabled(true);		
        this.bizName.setForeground(new java.awt.Color(0,0,0));
        // contshortName		
        this.contshortName.setBoundLabelText(resHelper.getString("contshortName.boundLabelText"));		
        this.contshortName.setBoundLabelLength(100);		
        this.contshortName.setBoundLabelUnderline(true);		
        this.contshortName.setVisible(true);		
        this.contshortName.setBoundLabelAlignment(7);		
        this.contshortName.setForeground(new java.awt.Color(0,0,0));
        // txtshortName		
        this.txtshortName.setVisible(true);		
        this.txtshortName.setHorizontalAlignment(2);		
        this.txtshortName.setMaxLength(100);		
        this.txtshortName.setRequired(false);		
        this.txtshortName.setEnabled(true);		
        this.txtshortName.setForeground(new java.awt.Color(0,0,0));
        // contsetupValue		
        this.contsetupValue.setBoundLabelText(resHelper.getString("contsetupValue.boundLabelText"));		
        this.contsetupValue.setBoundLabelLength(100);		
        this.contsetupValue.setBoundLabelUnderline(true);		
        this.contsetupValue.setVisible(true);		
        this.contsetupValue.setBoundLabelAlignment(7);		
        this.contsetupValue.setForeground(new java.awt.Color(0,0,0));
        // txtsetupValue		
        this.txtsetupValue.setVisible(true);		
        this.txtsetupValue.setHorizontalAlignment(2);		
        this.txtsetupValue.setMaxLength(100);		
        this.txtsetupValue.setRequired(false);		
        this.txtsetupValue.setEnabled(true);		
        this.txtsetupValue.setForeground(new java.awt.Color(0,0,0));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {bizDescription,txtNumber,bizName,chkIsEnabled,txtshortName,kDLabel1,txtsetupValue}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 290, 242));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, null);
        chkIsEnabled.setBounds(new Rectangle(235, 127, 140, 19));
        this.add(chkIsEnabled, null);
        bizDescription.setBounds(new Rectangle(10, 157, 270, 70));
        this.add(bizDescription, null);
        kDLabel1.setBounds(new Rectangle(10, 127, 100, 19));
        this.add(kDLabel1, null);
        contshortName.setBounds(new Rectangle(10, 99, 270, 19));
        this.add(contshortName, null);
        contsetupValue.setBounds(new Rectangle(10, 69, 270, 19));
        this.add(contsetupValue, null);
        //contName
        contName.setBoundEditor(bizName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contshortName
        contshortName.setBoundEditor(txtshortName);
        //contsetupValue
        contsetupValue.setBoundEditor(txtsetupValue);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
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
        menuHelp.add(menuItemAbout);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);

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
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCopy);
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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkIsEnabled, "selected");
		dataBinder.registerBinding("description", String.class, this.bizDescription, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.bizDescription, "text");
		dataBinder.registerBinding("name", String.class, this.bizName, "_multiLangItem");
		dataBinder.registerBinding("name", String.class, this.bizName, "text");
		dataBinder.registerBinding("shortName", String.class, this.txtshortName, "text");
		dataBinder.registerBinding("setupValue", String.class, this.txtsetupValue, "text");		
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
	    return "com.kingdee.eas.fdc.market.app.OperationSetupEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.bizDescription.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.OperationSetupInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shortName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("setupValue", ValidateHelper.ON_SAVE);    		
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
     * output txtNumber_validate() method
     */
    public void txtNumber_validate() throws Exception
    {
            if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) 
{
    com.kingdee.eas.util.client.MsgBox.showInfo(this,"编码不能为空！");com.kingdee.eas.util.SysUtil.abort();
}


    }

    /**
     * output bizName_validate() method
     */
    public void bizName_validate() throws Exception
    {
            if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(bizName.getDefaultLangItemData())) 
{
    com.kingdee.eas.util.client.MsgBox.showInfo(this,"名称不能为空！");com.kingdee.eas.util.SysUtil.abort();
}


    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("shortName"));
        sic.add(new SelectorItemInfo("setupValue"));
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
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "OperationSetupEditUI");
    }
    /**
     * output verifyInput method
     */
    protected void verifyInput(ActionEvent e) throws Exception
    {
        super.verifyInput(e);
        txtNumber_validate();
        bizName_validate();

    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.OperationSetupEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.OperationSetupFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.OperationSetupInfo objectValue = new com.kingdee.eas.fdc.market.OperationSetupInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
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