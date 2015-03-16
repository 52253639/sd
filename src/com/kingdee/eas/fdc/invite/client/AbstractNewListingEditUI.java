/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractNewListingEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewListingEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPnlTable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDTextField inivteOrg;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNoneSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportTemplet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSetZeroProAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportMaterial;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportTemplet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportPrice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAllSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoneSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportMaterial;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCutLine;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPasteLine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectNumber;
    protected com.kingdee.eas.fdc.invite.NewListingInfo editData = null;
    protected ActionColumnSetting actionColumnSetting = null;
    protected ActionImportExcel actionImportExcel = null;
    protected ActionImportTemplet actionImportTemplet = null;
    protected ActionCutLine actionCutLine = null;
    protected ActionPasteLine actionPasteLine = null;
    protected ActionUpLine actionUpLine = null;
    protected ActionDownLine actionDownLine = null;
    protected ActionExportPrice actionExportPrice = null;
    protected ActionAllSelect actionAllSelect = null;
    protected ActionNoneSelect actionNoneSelect = null;
    protected ActionTableSet actionTableSet = null;
    protected ActionSetZeroProAmount actionSetZeroProAmount = null;
    protected ActionImportMaterial actionImportMaterial = null;
    /**
     * output class constructor
     */
    public AbstractNewListingEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractNewListingEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionCopyLine
        actionCopyLine.setEnabled(true);
        actionCopyLine.setDaemonRun(false);

        actionCopyLine.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl O"));
        _tempStr = resHelper.getString("actionCopyLine.SHORT_DESCRIPTION");
        actionCopyLine.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionCopyLine.LONG_DESCRIPTION");
        actionCopyLine.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionCopyLine.NAME");
        actionCopyLine.putValue(ItemAction.NAME, _tempStr);
         this.actionCopyLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsertLine
        actionInsertLine.setEnabled(true);
        actionInsertLine.setDaemonRun(false);

        actionInsertLine.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift I"));
        _tempStr = resHelper.getString("ActionInsertLine.SHORT_DESCRIPTION");
        actionInsertLine.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionInsertLine.LONG_DESCRIPTION");
        actionInsertLine.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionInsertLine.NAME");
        actionInsertLine.putValue(ItemAction.NAME, _tempStr);
         this.actionInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionInsertLine.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionInsertLine.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemoveLine
        actionRemoveLine.setEnabled(true);
        actionRemoveLine.setDaemonRun(false);

        actionRemoveLine.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
        _tempStr = resHelper.getString("ActionRemoveLine.SHORT_DESCRIPTION");
        actionRemoveLine.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemoveLine.LONG_DESCRIPTION");
        actionRemoveLine.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemoveLine.NAME");
        actionRemoveLine.putValue(ItemAction.NAME, _tempStr);
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionColumnSetting
        this.actionColumnSetting = new ActionColumnSetting(this);
        getActionManager().registerAction("actionColumnSetting", actionColumnSetting);
         this.actionColumnSetting.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportExcel
        this.actionImportExcel = new ActionImportExcel(this);
        getActionManager().registerAction("actionImportExcel", actionImportExcel);
         this.actionImportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportTemplet
        this.actionImportTemplet = new ActionImportTemplet(this);
        getActionManager().registerAction("actionImportTemplet", actionImportTemplet);
         this.actionImportTemplet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCutLine
        this.actionCutLine = new ActionCutLine(this);
        getActionManager().registerAction("actionCutLine", actionCutLine);
         this.actionCutLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPasteLine
        this.actionPasteLine = new ActionPasteLine(this);
        getActionManager().registerAction("actionPasteLine", actionPasteLine);
         this.actionPasteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpLine
        this.actionUpLine = new ActionUpLine(this);
        getActionManager().registerAction("actionUpLine", actionUpLine);
         this.actionUpLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLine
        this.actionDownLine = new ActionDownLine(this);
        getActionManager().registerAction("actionDownLine", actionDownLine);
         this.actionDownLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportPrice
        this.actionExportPrice = new ActionExportPrice(this);
        getActionManager().registerAction("actionExportPrice", actionExportPrice);
         this.actionExportPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllSelect
        this.actionAllSelect = new ActionAllSelect(this);
        getActionManager().registerAction("actionAllSelect", actionAllSelect);
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoneSelect
        this.actionNoneSelect = new ActionNoneSelect(this);
        getActionManager().registerAction("actionNoneSelect", actionNoneSelect);
         this.actionNoneSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTableSet
        this.actionTableSet = new ActionTableSet(this);
        getActionManager().registerAction("actionTableSet", actionTableSet);
         this.actionTableSet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSetZeroProAmount
        this.actionSetZeroProAmount = new ActionSetZeroProAmount(this);
        getActionManager().registerAction("actionSetZeroProAmount", actionSetZeroProAmount);
         this.actionSetZeroProAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportMaterial
        this.actionImportMaterial = new ActionImportMaterial(this);
        getActionManager().registerAction("actionImportMaterial", actionImportMaterial);
         this.actionImportMaterial.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tabbedPnlTable = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.inivteOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnUpLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDownLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNoneSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnColumnSetting = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportTemplet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSetZeroProAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportMaterial = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemColumnSetting = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportTemplet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExportPrice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDownLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAllSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoneSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportMaterial = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCutLine = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPasteLine = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtInviteProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contName.setName("contName");
        this.contInviteType.setName("contInviteType");
        this.contCurProject.setName("contCurProject");
        this.tabbedPnlTable.setName("tabbedPnlTable");
        this.contCurrency.setName("contCurrency");
        this.contInviteProject.setName("contInviteProject");
        this.conInviteMode.setName("conInviteMode");
        this.conInviteOrg.setName("conInviteOrg");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.txtName.setName("txtName");
        this.prmtInviteType.setName("prmtInviteType");
        this.prmtCurProject.setName("prmtCurProject");
        this.prmtCurrency.setName("prmtCurrency");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.prmtInviteMode.setName("prmtInviteMode");
        this.inivteOrg.setName("inivteOrg");
        this.btnUpLevel.setName("btnUpLevel");
        this.btnDownLevel.setName("btnDownLevel");
        this.btnAllSelect.setName("btnAllSelect");
        this.btnNoneSelect.setName("btnNoneSelect");
        this.btnColumnSetting.setName("btnColumnSetting");
        this.btnImportExcel.setName("btnImportExcel");
        this.btnImportTemplet.setName("btnImportTemplet");
        this.btnExportPrice.setName("btnExportPrice");
        this.btnSetZeroProAmount.setName("btnSetZeroProAmount");
        this.btnImportMaterial.setName("btnImportMaterial");
        this.menuItemColumnSetting.setName("menuItemColumnSetting");
        this.menuItemImportExcel.setName("menuItemImportExcel");
        this.menuItemImportTemplet.setName("menuItemImportTemplet");
        this.menuItemExportPrice.setName("menuItemExportPrice");
        this.menuItemUpLevel.setName("menuItemUpLevel");
        this.menuItemDownLevel.setName("menuItemDownLevel");
        this.menuItemAllSelect.setName("menuItemAllSelect");
        this.menuItemNoneSelect.setName("menuItemNoneSelect");
        this.menuItemImportMaterial.setName("menuItemImportMaterial");
        this.menuItemCutLine.setName("menuItemCutLine");
        this.menuItemPasteLine.setName("menuItemPasteLine");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtInviteProjectNumber.setName("txtInviteProjectNumber");
        // CoreUI		
        this.setMinimumSize(new Dimension(1013,629));		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);
        this.menuItemCopyLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionCopyLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCopyLine.setText(resHelper.getString("menuItemCopyLine.text"));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // tabbedPnlTable
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // conInviteMode		
        this.conInviteMode.setBoundLabelText(resHelper.getString("conInviteMode.boundLabelText"));		
        this.conInviteMode.setBoundLabelUnderline(true);		
        this.conInviteMode.setBoundLabelLength(100);		
        this.conInviteMode.setEnabled(false);
        // conInviteOrg		
        this.conInviteOrg.setBoundLabelText(resHelper.getString("conInviteOrg.boundLabelText"));		
        this.conInviteOrg.setBoundLabelUnderline(true);		
        this.conInviteOrg.setBoundLabelLength(100);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // prmtInviteType		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.InviteTypeF7UI");		
        this.prmtInviteType.setRequired(true);
        // prmtCurProject		
        this.prmtCurProject.setEditFormat("$number$");		
        this.prmtCurProject.setDisplayFormat("$name$");		
        this.prmtCurProject.setCommitFormat("$number$");		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
        // prmtCurrency		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
        // prmtInviteProject		
        this.prmtInviteProject.setDisplayFormat("$name$");		
        this.prmtInviteProject.setEditFormat("$number$");		
        this.prmtInviteProject.setCommitFormat("$number$");		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");
        this.prmtInviteProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtInviteProject.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtInviteProject_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtInviteMode		
        this.prmtInviteMode.setDisplayFormat("$name$");		
        this.prmtInviteMode.setEditFormat("$number$");		
        this.prmtInviteMode.setCommitFormat("$number$");		
        this.prmtInviteMode.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteModeQuery");
        // inivteOrg		
        this.inivteOrg.setEnabled(false);
        // btnUpLevel
        this.btnUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpLevel.setText(resHelper.getString("btnUpLevel.text"));		
        this.btnUpLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upgrade"));		
        this.btnUpLevel.setToolTipText(resHelper.getString("btnUpLevel.toolTipText"));
        // btnDownLevel
        this.btnDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDownLevel.setText(resHelper.getString("btnDownLevel.text"));		
        this.btnDownLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_degrade"));		
        this.btnDownLevel.setToolTipText(resHelper.getString("btnDownLevel.toolTipText"));
        // btnAllSelect
        this.btnAllSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllSelect.setText(resHelper.getString("btnAllSelect.text"));		
        this.btnAllSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectall"));		
        this.btnAllSelect.setToolTipText(resHelper.getString("btnAllSelect.toolTipText"));
        // btnNoneSelect
        this.btnNoneSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoneSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNoneSelect.setText(resHelper.getString("btnNoneSelect.text"));		
        this.btnNoneSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteall"));		
        this.btnNoneSelect.setToolTipText(resHelper.getString("btnNoneSelect.toolTipText"));
        // btnColumnSetting
        this.btnColumnSetting.setAction((IItemAction)ActionProxyFactory.getProxy(actionColumnSetting, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnColumnSetting.setText(resHelper.getString("btnColumnSetting.text"));		
        this.btnColumnSetting.setToolTipText(resHelper.getString("btnColumnSetting.toolTipText"));		
        this.btnColumnSetting.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_organiselist"));
        // btnImportExcel
        this.btnImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));		
        this.btnImportExcel.setToolTipText(resHelper.getString("btnImportExcel.toolTipText"));		
        this.btnImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // btnImportTemplet
        this.btnImportTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportTemplet.setText(resHelper.getString("btnImportTemplet.text"));		
        this.btnImportTemplet.setToolTipText(resHelper.getString("btnImportTemplet.toolTipText"));		
        this.btnImportTemplet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importcyclostyle"));
        // btnExportPrice
        this.btnExportPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportPrice.setText(resHelper.getString("btnExportPrice.text"));		
        this.btnExportPrice.setToolTipText(resHelper.getString("btnExportPrice.toolTipText"));		
        this.btnExportPrice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_archive"));
        // btnSetZeroProAmount
        this.btnSetZeroProAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetZeroProAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSetZeroProAmount.setText(resHelper.getString("btnSetZeroProAmount.text"));		
        this.btnSetZeroProAmount.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_archive"));
        // btnImportMaterial
        this.btnImportMaterial.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportMaterial.setText(resHelper.getString("btnImportMaterial.text"));		
        this.btnImportMaterial.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_adduction"));
        // menuItemColumnSetting
        this.menuItemColumnSetting.setAction((IItemAction)ActionProxyFactory.getProxy(actionColumnSetting, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemColumnSetting.setText(resHelper.getString("menuItemColumnSetting.text"));		
        this.menuItemColumnSetting.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_organiselist"));		
        this.menuItemColumnSetting.setToolTipText(resHelper.getString("menuItemColumnSetting.toolTipText"));
        // menuItemImportExcel
        this.menuItemImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportExcel.setText(resHelper.getString("menuItemImportExcel.text"));		
        this.menuItemImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));		
        this.menuItemImportExcel.setToolTipText(resHelper.getString("menuItemImportExcel.toolTipText"));
        // menuItemImportTemplet
        this.menuItemImportTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportTemplet.setText(resHelper.getString("menuItemImportTemplet.text"));		
        this.menuItemImportTemplet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importcyclostyle"));		
        this.menuItemImportTemplet.setToolTipText(resHelper.getString("menuItemImportTemplet.toolTipText"));
        // menuItemExportPrice		
        this.menuItemExportPrice.setText(resHelper.getString("menuItemExportPrice.text"));		
        this.menuItemExportPrice.setToolTipText(resHelper.getString("menuItemExportPrice.toolTipText"));		
        this.menuItemExportPrice.setVisible(false);
        // menuItemUpLevel
        this.menuItemUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUpLevel.setText(resHelper.getString("menuItemUpLevel.text"));		
        this.menuItemUpLevel.setToolTipText(resHelper.getString("menuItemUpLevel.toolTipText"));		
        this.menuItemUpLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upgrade"));
        // menuItemDownLevel
        this.menuItemDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDownLevel.setText(resHelper.getString("menuItemDownLevel.text"));		
        this.menuItemDownLevel.setToolTipText(resHelper.getString("menuItemDownLevel.toolTipText"));		
        this.menuItemDownLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_degrade"));
        // menuItemAllSelect
        this.menuItemAllSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAllSelect.setText(resHelper.getString("menuItemAllSelect.text"));		
        this.menuItemAllSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectall"));		
        this.menuItemAllSelect.setToolTipText(resHelper.getString("menuItemAllSelect.toolTipText"));
        // menuItemNoneSelect
        this.menuItemNoneSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoneSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoneSelect.setText(resHelper.getString("menuItemNoneSelect.text"));		
        this.menuItemNoneSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteall"));		
        this.menuItemNoneSelect.setToolTipText(resHelper.getString("menuItemNoneSelect.toolTipText"));
        // menuItemImportMaterial
        this.menuItemImportMaterial.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportMaterial.setText(resHelper.getString("menuItemImportMaterial.text"));		
        this.menuItemImportMaterial.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_adduction"));
        // menuItemCutLine
        this.menuItemCutLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionCutLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCutLine.setText(resHelper.getString("menuItemCutLine.text"));
        // menuItemPasteLine
        this.menuItemPasteLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionPasteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPasteLine.setText(resHelper.getString("menuItemPasteLine.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // txtInviteProjectNumber		
        this.txtInviteProjectNumber.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 1013, 650));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 650));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(368, 96, 631, 19));
        this.add(contDescription, new KDLayout.Constraints(368, 96, 631, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(368, 10, 631, 19));
        this.add(contName, new KDLayout.Constraints(368, 10, 631, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(368, 68, 270, 19));
        this.add(contInviteType, new KDLayout.Constraints(368, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurProject.setBounds(new Rectangle(730, 39, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(730, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tabbedPnlTable.setBounds(new Rectangle(10, 131, 993, 512));
        this.add(tabbedPnlTable, new KDLayout.Constraints(10, 131, 993, 512, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurrency.setBounds(new Rectangle(10, 68, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(10, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProject.setBounds(new Rectangle(10, 37, 270, 19));
        this.add(contInviteProject, new KDLayout.Constraints(10, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteMode.setBounds(new Rectangle(9, 96, 270, 19));
        this.add(conInviteMode, new KDLayout.Constraints(9, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteOrg.setBounds(new Rectangle(730, 68, 270, 19));
        this.add(conInviteOrg, new KDLayout.Constraints(730, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(368, 37, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(368, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contName
        contName.setBoundEditor(txtName);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //conInviteMode
        conInviteMode.setBoundEditor(prmtInviteMode);
        //conInviteOrg
        conInviteOrg.setBoundEditor(inivteOrg);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtInviteProjectNumber);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemColumnSetting);
        menuBiz.add(menuItemImportExcel);
        menuBiz.add(menuItemImportTemplet);
        menuBiz.add(menuItemExportPrice);
        menuBiz.add(menuItemUpLevel);
        menuBiz.add(menuItemDownLevel);
        menuBiz.add(menuItemAllSelect);
        menuBiz.add(menuItemNoneSelect);
        menuBiz.add(menuItemImportMaterial);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        menuTable1.add(menuItemCutLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemPasteLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(btnUpLevel);
        this.toolBar.add(btnDownLevel);
        this.toolBar.add(btnAllSelect);
        this.toolBar.add(btnNoneSelect);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnColumnSetting);
        this.toolBar.add(btnImportExcel);
        this.toolBar.add(btnImportTemplet);
        this.toolBar.add(btnExportPrice);
        this.toolBar.add(btnSetZeroProAmount);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnImportMaterial);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("inviteProject.inviteMode", com.kingdee.eas.fdc.invite.InviteModeInfo.class, this.prmtInviteMode, "data");
		dataBinder.registerBinding("inviteProject.orgUnit.name", String.class, this.inivteOrg, "text");
		dataBinder.registerBinding("inviteProject.number", String.class, this.txtInviteProjectNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.NewListingEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.NewListingInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.orgUnit.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.number", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output prmtInviteProject_dataChanged method
     */
    protected void prmtInviteProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteProject_willShow method
     */
    protected void prmtInviteProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("inviteType.*"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("inviteProject.inviteMode"));
        sic.add(new SelectorItemInfo("inviteProject.orgUnit.name"));
        sic.add(new SelectorItemInfo("inviteProject.number"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionCopyLine_actionPerformed method
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }
    	

    /**
     * output actionInsertLine_actionPerformed method
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }
    	

    /**
     * output actionRemoveLine_actionPerformed method
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }
    	

    /**
     * output actionColumnSetting_actionPerformed method
     */
    public void actionColumnSetting_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportExcel_actionPerformed method
     */
    public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportTemplet_actionPerformed method
     */
    public void actionImportTemplet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCutLine_actionPerformed method
     */
    public void actionCutLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPasteLine_actionPerformed method
     */
    public void actionPasteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpLine_actionPerformed method
     */
    public void actionUpLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownLine_actionPerformed method
     */
    public void actionDownLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportPrice_actionPerformed method
     */
    public void actionExportPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllSelect_actionPerformed method
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoneSelect_actionPerformed method
     */
    public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTableSet_actionPerformed method
     */
    public void actionTableSet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetZeroProAmount_actionPerformed method
     */
    public void actionSetZeroProAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportMaterial_actionPerformed method
     */
    public void actionImportMaterial_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareactionCopyLine(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionCopyLine(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionCopyLine() {
    	return false;
    }
	public RequestContext prepareActionInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionInsertLine(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsertLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemoveLine(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLine() {
    	return false;
    }
	public RequestContext prepareActionColumnSetting(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionColumnSetting() {
    	return false;
    }
	public RequestContext prepareActionImportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportExcel() {
    	return false;
    }
	public RequestContext prepareActionImportTemplet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportTemplet() {
    	return false;
    }
	public RequestContext prepareActionCutLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCutLine() {
    	return false;
    }
	public RequestContext prepareActionPasteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPasteLine() {
    	return false;
    }
	public RequestContext prepareActionUpLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpLine() {
    	return false;
    }
	public RequestContext prepareActionDownLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDownLine() {
    	return false;
    }
	public RequestContext prepareActionExportPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportPrice() {
    	return false;
    }
	public RequestContext prepareActionAllSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllSelect() {
    	return false;
    }
	public RequestContext prepareActionNoneSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoneSelect() {
    	return false;
    }
	public RequestContext prepareActionTableSet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTableSet() {
    	return false;
    }
	public RequestContext prepareActionSetZeroProAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSetZeroProAmount() {
    	return false;
    }
	public RequestContext prepareActionImportMaterial(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportMaterial() {
    	return false;
    }

    /**
     * output ActionColumnSetting class
     */     
    protected class ActionColumnSetting extends ItemAction {     
    
        public ActionColumnSetting()
        {
            this(null);
        }

        public ActionColumnSetting(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl T"));
            _tempStr = resHelper.getString("ActionColumnSetting.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionColumnSetting.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionColumnSetting.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionColumnSetting", "actionColumnSetting_actionPerformed", e);
        }
    }

    /**
     * output ActionImportExcel class
     */     
    protected class ActionImportExcel extends ItemAction {     
    
        public ActionImportExcel()
        {
            this(null);
        }

        public ActionImportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl L"));
            _tempStr = resHelper.getString("ActionImportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionImportExcel", "actionImportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionImportTemplet class
     */     
    protected class ActionImportTemplet extends ItemAction {     
    
        public ActionImportTemplet()
        {
            this(null);
        }

        public ActionImportTemplet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl Y"));
            _tempStr = resHelper.getString("ActionImportTemplet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportTemplet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportTemplet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionImportTemplet", "actionImportTemplet_actionPerformed", e);
        }
    }

    /**
     * output ActionCutLine class
     */     
    protected class ActionCutLine extends ItemAction {     
    
        public ActionCutLine()
        {
            this(null);
        }

        public ActionCutLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl M"));
            _tempStr = resHelper.getString("ActionCutLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCutLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCutLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionCutLine", "actionCutLine_actionPerformed", e);
        }
    }

    /**
     * output ActionPasteLine class
     */     
    protected class ActionPasteLine extends ItemAction {     
    
        public ActionPasteLine()
        {
            this(null);
        }

        public ActionPasteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl F"));
            _tempStr = resHelper.getString("ActionPasteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPasteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPasteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionPasteLine", "actionPasteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionUpLine class
     */     
    protected class ActionUpLine extends ItemAction {     
    
        public ActionUpLine()
        {
            this(null);
        }

        public ActionUpLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl Q"));
            _tempStr = resHelper.getString("ActionUpLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionUpLine", "actionUpLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDownLine class
     */     
    protected class ActionDownLine extends ItemAction {     
    
        public ActionDownLine()
        {
            this(null);
        }

        public ActionDownLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl V"));
            _tempStr = resHelper.getString("ActionDownLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionDownLine", "actionDownLine_actionPerformed", e);
        }
    }

    /**
     * output ActionExportPrice class
     */     
    protected class ActionExportPrice extends ItemAction {     
    
        public ActionExportPrice()
        {
            this(null);
        }

        public ActionExportPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl X"));
            _tempStr = resHelper.getString("ActionExportPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionExportPrice", "actionExportPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionAllSelect class
     */     
    protected class ActionAllSelect extends ItemAction {     
    
        public ActionAllSelect()
        {
            this(null);
        }

        public ActionAllSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F"));
            _tempStr = resHelper.getString("ActionAllSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionAllSelect", "actionAllSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionNoneSelect class
     */     
    protected class ActionNoneSelect extends ItemAction {     
    
        public ActionNoneSelect()
        {
            this(null);
        }

        public ActionNoneSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
            _tempStr = resHelper.getString("ActionNoneSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoneSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoneSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionNoneSelect", "actionNoneSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionTableSet class
     */     
    protected class ActionTableSet extends ItemAction {     
    
        public ActionTableSet()
        {
            this(null);
        }

        public ActionTableSet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl K"));
            _tempStr = resHelper.getString("ActionTableSet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTableSet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTableSet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionTableSet", "actionTableSet_actionPerformed", e);
        }
    }

    /**
     * output ActionSetZeroProAmount class
     */     
    protected class ActionSetZeroProAmount extends ItemAction {     
    
        public ActionSetZeroProAmount()
        {
            this(null);
        }

        public ActionSetZeroProAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSetZeroProAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetZeroProAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetZeroProAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionSetZeroProAmount", "actionSetZeroProAmount_actionPerformed", e);
        }
    }

    /**
     * output ActionImportMaterial class
     */     
    protected class ActionImportMaterial extends ItemAction {     
    
        public ActionImportMaterial()
        {
            this(null);
        }

        public ActionImportMaterial(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportMaterial.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterial.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterial.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingEditUI.this, "ActionImportMaterial", "actionImportMaterial_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "NewListingEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}