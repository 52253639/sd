/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

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
public abstract class AbstractProgrammingEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProgrammingEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAimCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCostVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtVerCompareEntry;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer conCompare;
    protected com.kingdee.bos.ctrl.swing.KDContainer conProgramming;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCompareEntry;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDPanel colorPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtVersion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAimCost;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtAimCoustVersion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionGroup;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker2;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSaleArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllAimCost;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditInvite;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefresh;
    protected javax.swing.JPopupMenu.Separator separator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExport;
    protected com.kingdee.eas.fdc.contract.programming.ProgrammingInfo editData = null;
    protected ActionImport actionImport = null;
    protected ActionExportPro actionExportPro = null;
    protected ActionRefresh actionRefresh = null;
    protected ActionEditInvite actionEditInvite = null;
    protected ActionComAddRow actionComAddRow = null;
    protected ActionComInsertRow actionComInsertRow = null;
    protected ActionComRemoveRow actionComRemoveRow = null;
    protected ActionCompare actionCompare = null;
    protected ActionViewAmount actionViewAmount = null;
    /**
     * output class constructor
     */
    public AbstractProgrammingEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProgrammingEditUI.class.getName());
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
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportPro
        this.actionExportPro = new ActionExportPro(this);
        getActionManager().registerAction("actionExportPro", actionExportPro);
         this.actionExportPro.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditInvite
        this.actionEditInvite = new ActionEditInvite(this);
        getActionManager().registerAction("actionEditInvite", actionEditInvite);
         this.actionEditInvite.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionComAddRow
        this.actionComAddRow = new ActionComAddRow(this);
        getActionManager().registerAction("actionComAddRow", actionComAddRow);
         this.actionComAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionComInsertRow
        this.actionComInsertRow = new ActionComInsertRow(this);
        getActionManager().registerAction("actionComInsertRow", actionComInsertRow);
         this.actionComInsertRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionComRemoveRow
        this.actionComRemoveRow = new ActionComRemoveRow(this);
        getActionManager().registerAction("actionComRemoveRow", actionComRemoveRow);
         this.actionComRemoveRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCompare
        this.actionCompare = new ActionCompare(this);
        getActionManager().registerAction("actionCompare", actionCompare);
         this.actionCompare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAmount
        this.actionViewAmount = new ActionViewAmount(this);
        getActionManager().registerAction("actionViewAmount", actionViewAmount);
         this.actionViewAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAimCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCostVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersionGroup = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtVerCompareEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.conCompare = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.conProgramming = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.pnlCostAccount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtCompareEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.colorPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtCostAccount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAimCost = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAimCoustVersion = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtVersionGroup = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDatePicker2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBuildArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSaleArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllAimCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditInvite = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRefresh = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator4 = new javax.swing.JPopupMenu.Separator();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contVersion.setName("contVersion");
        this.contProject.setName("contProject");
        this.contAimCost.setName("contAimCost");
        this.lblCostVersion.setName("lblCostVersion");
        this.contVersionGroup.setName("contVersionGroup");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kdtVerCompareEntry.setName("kdtVerCompareEntry");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.conCompare.setName("conCompare");
        this.conProgramming.setName("conProgramming");
        this.pnlCostAccount.setName("pnlCostAccount");
        this.txtDescription.setName("txtDescription");
        this.kdtCompareEntry.setName("kdtCompareEntry");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kdtEntries.setName("kdtEntries");
        this.colorPanel.setName("colorPanel");
        this.kdtCostAccount.setName("kdtCostAccount");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtVersion.setName("txtVersion");
        this.txtProjectName.setName("txtProjectName");
        this.prmtAimCost.setName("prmtAimCost");
        this.txtAimCoustVersion.setName("txtAimCoustVersion");
        this.txtVersionGroup.setName("txtVersionGroup");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.kDDatePicker2.setName("kDDatePicker2");
        this.txtBuildArea.setName("txtBuildArea");
        this.txtSaleArea.setName("txtSaleArea");
        this.txtAllAimCost.setName("txtAllAimCost");
        this.btnRefresh.setName("btnRefresh");
        this.btnEditInvite.setName("btnEditInvite");
        this.btnImport.setName("btnImport");
        this.btnExport.setName("btnExport");
        this.menuItemRefresh.setName("menuItemRefresh");
        this.separator4.setName("separator4");
        this.menuItemImport.setName("menuItemImport");
        this.menuItemExport.setName("menuItemExport");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnAddNew.setEnabled(false);		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrint.setEnabled(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemFirst.setEnabled(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));		
        this.menuItemRemove.setVisible(false);		
        this.menuItemRemove.setEnabled(false);		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuTable1.setEnabled(false);
        // kDTabbedPane1
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setVisible(false);		
        this.contNumber.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contAimCost		
        this.contAimCost.setBoundLabelText(resHelper.getString("contAimCost.boundLabelText"));		
        this.contAimCost.setBoundLabelLength(100);		
        this.contAimCost.setBoundLabelUnderline(true);
        // lblCostVersion		
        this.lblCostVersion.setBoundLabelText(resHelper.getString("lblCostVersion.boundLabelText"));		
        this.lblCostVersion.setBoundLabelLength(100);		
        this.lblCostVersion.setBoundLabelUnderline(true);
        // contVersionGroup		
        this.contVersionGroup.setBoundLabelText(resHelper.getString("contVersionGroup.boundLabelText"));		
        this.contVersionGroup.setBoundLabelLength(80);		
        this.contVersionGroup.setBoundLabelUnderline(true);		
        this.contVersionGroup.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setVisible(false);		
        this.kDLabelContainer1.setEnabled(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setVisible(false);		
        this.kDLabelContainer2.setEnabled(false);
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
        // kdtVerCompareEntry
		String kdtVerCompareEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"programmingContract\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"content\" t:width=\"750\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"reason\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{programmingContract}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{reason}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtVerCompareEntry.setFormatXml(resHelper.translateString("kdtVerCompareEntry",kdtVerCompareEntryStrXML));

        

        // kDScrollPane1
        // conCompare
        // conProgramming
        // pnlCostAccount
        // txtDescription		
        this.txtDescription.setMaxLength(50000);
        // kdtCompareEntry
		String kdtCompareEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"programmingContract\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"content\" t:width=\"750\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"reason\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{programmingContract}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{reason}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtCompareEntry.setFormatXml(resHelper.translateString("kdtCompareEntry",kdtCompareEntryStrXML));

                this.kdtCompareEntry.putBindContents("editData",new String[] {"programmingContract","content","reason"});


        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(380);		
        this.kDSplitPane1.setDividerSize(0);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol28\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol34\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol37\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol38\"><c:Protection hidden=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol40\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"longNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"contractType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"costAccount\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"workContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"supMaterial\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"paperDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"documentsAuditDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"resultAuditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"contractAuditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"enterAuditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"isInvite\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"inviteWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"inviteOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"buildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"isInput\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"quantities\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"controlAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"isCiting\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"signUpAmount\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"changeAmount\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"settleAmount\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"controlBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"attachment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"buildPerSquare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"soldPerSquare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"citeVersion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"headNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"longName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"sortNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" t:styleID=\"sCol37\" /><t:Column t:key=\"estimateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" t:styleID=\"sCol38\" /><t:Column t:key=\"isWTCiting\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"compare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" t:styleID=\"sCol40\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{contractType}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{workContent}</t:Cell><t:Cell>$Resource{supMaterial}</t:Cell><t:Cell>$Resource{paperDate}</t:Cell><t:Cell>$Resource{documentsAuditDate}</t:Cell><t:Cell>$Resource{resultAuditDate}</t:Cell><t:Cell>$Resource{contractAuditDate}</t:Cell><t:Cell>$Resource{enterAuditDate}</t:Cell><t:Cell>$Resource{isInvite}</t:Cell><t:Cell>$Resource{inviteWay}</t:Cell><t:Cell>$Resource{inviteOrg}</t:Cell><t:Cell>$Resource{buildPrice}</t:Cell><t:Cell>$Resource{isInput}</t:Cell><t:Cell>$Resource{quantities}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{controlAmount}</t:Cell><t:Cell>$Resource{isCiting}</t:Cell><t:Cell>$Resource{signUpAmount}</t:Cell><t:Cell>$Resource{changeAmount}</t:Cell><t:Cell>$Resource{settleAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{controlBalance}</t:Cell><t:Cell>$Resource{attachment}</t:Cell><t:Cell>$Resource{buildPerSquare}</t:Cell><t:Cell>$Resource{soldPerSquare}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{citeVersion}</t:Cell><t:Cell>$Resource{headNumber}</t:Cell><t:Cell>$Resource{longName}</t:Cell><t:Cell>$Resource{sortNumber}</t:Cell><t:Cell>$Resource{estimateAmount}</t:Cell><t:Cell>$Resource{isWTCiting}</t:Cell><t:Cell>$Resource{compare}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));
        this.kdtEntries.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtEntries_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntries.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntries_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntries.putBindContents("editData",new String[] {"longNumber","name","contractType","costAccountNames","workContent","supMaterial","paperDate","documentsAuditDate","resultAuditDate","contractAuditDate","enterAuditDate","isInvite","inviteWay","inviteOrg","buildPrice","isInput","quantities","unit","price","amount","controlAmount","isCiting","signUpAmount","changeAmount","settleAmount","balance","controlBalance","attachment","buildPerSquare","soldPerSquare","description","id","number","level","citeVersion","parent.longNumber","displayName","sortNumber","estimateAmount","isWTCiting","compare"});


        // colorPanel		
        this.colorPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("colorPanel.border.title")));
        // kdtCostAccount
		String kdtCostAccountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"costNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"assigned\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"assigning\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"proName\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{costNumber}</t:Cell><t:Cell>$Resource{costName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{assigned}</t:Cell><t:Cell>$Resource{assigning}</t:Cell><t:Cell>$Resource{proName}</t:Cell><t:Cell>$Resource{level}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtCostAccount.setFormatXml(resHelper.translateString("kdtCostAccount",kdtCostAccountStrXML));

        

        // txtNumber
        // txtName		
        this.txtName.setRequired(true);
        // txtVersion		
        this.txtVersion.setDataType(6);		
        this.txtVersion.setPrecision(1);		
        this.txtVersion.setEnabled(false);
        // txtProjectName		
        this.txtProjectName.setEnabled(false);
        // prmtAimCost		
        this.prmtAimCost.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.AIMCostF7Query");		
        this.prmtAimCost.setDisplayFormat("$versionName$");		
        this.prmtAimCost.setEditFormat("$versionNumber$");		
        this.prmtAimCost.setCommitFormat("$versionNumber$");		
        this.prmtAimCost.setRequired(true);
        // txtAimCoustVersion		
        this.txtAimCoustVersion.setDataType(6);		
        this.txtAimCoustVersion.setEnabled(false);		
        this.txtAimCoustVersion.setPrecision(1);
        // txtVersionGroup
        // kDDatePicker1		
        this.kDDatePicker1.setVisible(false);		
        this.kDDatePicker1.setEnabled(false);
        // kDDatePicker2		
        this.kDDatePicker2.setVisible(false);		
        this.kDDatePicker2.setEnabled(false);
        // txtBuildArea
        // txtSaleArea
        // txtAllAimCost
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
        // btnEditInvite
        this.btnEditInvite.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditInvite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditInvite.setText(resHelper.getString("btnEditInvite.text"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));		
        this.btnImport.setToolTipText(resHelper.getString("btnImport.toolTipText"));
        // btnExport
        this.btnExport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPro, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExport.setText(resHelper.getString("btnExport.text"));		
        this.btnExport.setToolTipText(resHelper.getString("btnExport.toolTipText"));
        // menuItemRefresh
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setToolTipText(resHelper.getString("menuItemRefresh.toolTipText"));
        // separator4
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setToolTipText(resHelper.getString("menuItemImport.toolTipText"));
        // menuItemExport
        this.menuItemExport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPro, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExport.setText(resHelper.getString("menuItemExport.text"));		
        this.menuItemExport.setToolTipText(resHelper.getString("menuItemExport.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 687));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 687));
        kDTabbedPane1.setBounds(new Rectangle(5, 95, 1000, 587));
        this.add(kDTabbedPane1, new KDLayout.Constraints(5, 95, 1000, 587, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(1200, 96, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(1200, 96, 270, 19, 0));
        contName.setBounds(new Rectangle(10, 40, 270, 20));
        this.add(contName, new KDLayout.Constraints(10, 40, 270, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersion.setBounds(new Rectangle(406, 40, 250, 20));
        this.add(contVersion, new KDLayout.Constraints(406, 40, 250, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(10, 10, 270, 20));
        this.add(contProject, new KDLayout.Constraints(10, 10, 270, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAimCost.setBounds(new Rectangle(10, 70, 270, 20));
        this.add(contAimCost, new KDLayout.Constraints(10, 70, 270, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblCostVersion.setBounds(new Rectangle(406, 70, 250, 20));
        this.add(lblCostVersion, new KDLayout.Constraints(406, 70, 250, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersionGroup.setBounds(new Rectangle(1200, 96, 270, 19));
        this.add(contVersionGroup, new KDLayout.Constraints(1200, 96, 270, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(1200, 96, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(1200, 96, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(1200, 96, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(1200, 96, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(406, 10, 250, 20));
        this.add(kDLabelContainer3, new KDLayout.Constraints(406, 10, 250, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(755, 10, 250, 20));
        this.add(kDLabelContainer4, new KDLayout.Constraints(755, 10, 250, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(755, 70, 250, 20));
        this.add(kDLabelContainer5, new KDLayout.Constraints(755, 70, 250, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtVerCompareEntry.setBounds(new Rectangle(296, 30, 86, 62));
        this.add(kdtVerCompareEntry, new KDLayout.Constraints(296, 30, 86, 62, 0));
        //kDTabbedPane1
        kDTabbedPane1.add(kDScrollPane1, resHelper.getString("kDScrollPane1.constraints"));
        kDTabbedPane1.add(conCompare, resHelper.getString("conCompare.constraints"));
        kDTabbedPane1.add(conProgramming, resHelper.getString("conProgramming.constraints"));
        kDTabbedPane1.add(pnlCostAccount, resHelper.getString("pnlCostAccount.constraints"));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //conCompare
conCompare.getContentPane().setLayout(new BorderLayout(0, 0));        conCompare.getContentPane().add(kdtCompareEntry, BorderLayout.CENTER);
        //conProgramming
conProgramming.getContentPane().setLayout(new BorderLayout(0, 0));        conProgramming.getContentPane().add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(kdtEntries, "top");
        kDSplitPane1.add(colorPanel, "bottom");
        colorPanel.setLayout(null);        //pnlCostAccount
pnlCostAccount.setLayout(new BorderLayout(0, 0));        pnlCostAccount.add(kdtCostAccount, BorderLayout.CENTER);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contProject
        contProject.setBoundEditor(txtProjectName);
        //contAimCost
        contAimCost.setBoundEditor(prmtAimCost);
        //lblCostVersion
        lblCostVersion.setBoundEditor(txtAimCoustVersion);
        //contVersionGroup
        contVersionGroup.setBoundEditor(txtVersionGroup);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDDatePicker1);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDDatePicker2);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtBuildArea);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtSaleArea);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtAllAimCost);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuView.add(menuItemRefresh);
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
        menuBiz.add(separator4);
        menuBiz.add(menuItemImport);
        menuBiz.add(menuItemExport);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnEditInvite);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnExport);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("compareEntry", com.kingdee.eas.fdc.contract.programming.ProgrammingCompareInfo.class, this.kdtCompareEntry, "userObject");
		dataBinder.registerBinding("compareEntry.programmingContract", String.class, this.kdtCompareEntry, "programmingContract.text");
		dataBinder.registerBinding("compareEntry.content", String.class, this.kdtCompareEntry, "content.text");
		dataBinder.registerBinding("compareEntry.reason", String.class, this.kdtCompareEntry, "reason.text");
		dataBinder.registerBinding("entries.longNumber", String.class, this.kdtEntries, "longNumber.text");
		dataBinder.registerBinding("entries.name", String.class, this.kdtEntries, "name.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.controlAmount", java.math.BigDecimal.class, this.kdtEntries, "controlAmount.text");
		dataBinder.registerBinding("entries.balance", java.math.BigDecimal.class, this.kdtEntries, "balance.text");
		dataBinder.registerBinding("entries.controlBalance", java.math.BigDecimal.class, this.kdtEntries, "controlBalance.text");
		dataBinder.registerBinding("entries.signUpAmount", java.math.BigDecimal.class, this.kdtEntries, "signUpAmount.text");
		dataBinder.registerBinding("entries.changeAmount", java.math.BigDecimal.class, this.kdtEntries, "changeAmount.text");
		dataBinder.registerBinding("entries.settleAmount", java.math.BigDecimal.class, this.kdtEntries, "settleAmount.text");
		dataBinder.registerBinding("entries.citeVersion", int.class, this.kdtEntries, "citeVersion.text");
		dataBinder.registerBinding("entries.isCiting", boolean.class, this.kdtEntries, "isCiting.text");
		dataBinder.registerBinding("entries.attachment", String.class, this.kdtEntries, "attachment.text");
		dataBinder.registerBinding("entries.description", String.class, this.kdtEntries, "remark.text");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries.number", String.class, this.kdtEntries, "number.text");
		dataBinder.registerBinding("entries.level", int.class, this.kdtEntries, "level.text");
		dataBinder.registerBinding("entries.parent.longNumber", String.class, this.kdtEntries, "headNumber.text");
		dataBinder.registerBinding("entries.displayName", String.class, this.kdtEntries, "longName.text");
		dataBinder.registerBinding("entries.sortNumber", int.class, this.kdtEntries, "sortNumber.text");
		dataBinder.registerBinding("entries.workContent", String.class, this.kdtEntries, "workContent.text");
		dataBinder.registerBinding("entries.supMaterial", String.class, this.kdtEntries, "supMaterial.text");
		dataBinder.registerBinding("entries.inviteWay", com.kingdee.eas.fdc.invite.InviteFormEnum.class, this.kdtEntries, "inviteWay.text");
		dataBinder.registerBinding("entries.buildPerSquare", java.math.BigDecimal.class, this.kdtEntries, "buildPerSquare.text");
		dataBinder.registerBinding("entries.soldPerSquare", java.math.BigDecimal.class, this.kdtEntries, "soldPerSquare.text");
		dataBinder.registerBinding("entries.inviteOrg", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.kdtEntries, "inviteOrg.text");
		dataBinder.registerBinding("entries.costAccountNames", String.class, this.kdtEntries, "costAccount.text");
		dataBinder.registerBinding("entries.estimateAmount", java.math.BigDecimal.class, this.kdtEntries, "estimateAmount.text");
		dataBinder.registerBinding("entries.contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.kdtEntries, "contractType.text");
		dataBinder.registerBinding("entries.isInvite", boolean.class, this.kdtEntries, "isInvite.text");
		dataBinder.registerBinding("entries.isWTCiting", boolean.class, this.kdtEntries, "isWTCiting.text");
		dataBinder.registerBinding("entries.compare", String.class, this.kdtEntries, "compare.text");
		dataBinder.registerBinding("entries.buildPrice", java.math.BigDecimal.class, this.kdtEntries, "buildPrice.text");
		dataBinder.registerBinding("entries.isInput", boolean.class, this.kdtEntries, "isInput.text");
		dataBinder.registerBinding("entries.quantities", java.math.BigDecimal.class, this.kdtEntries, "quantities.text");
		dataBinder.registerBinding("entries.unit", String.class, this.kdtEntries, "unit.text");
		dataBinder.registerBinding("entries.price", java.math.BigDecimal.class, this.kdtEntries, "price.text");
		dataBinder.registerBinding("entries.paperDate", java.util.Date.class, this.kdtEntries, "paperDate.text");
		dataBinder.registerBinding("entries.documentsAuditDate", java.util.Date.class, this.kdtEntries, "documentsAuditDate.text");
		dataBinder.registerBinding("entries.resultAuditDate", java.util.Date.class, this.kdtEntries, "resultAuditDate.text");
		dataBinder.registerBinding("entries.contractAuditDate", java.util.Date.class, this.kdtEntries, "contractAuditDate.text");
		dataBinder.registerBinding("entries.enterAuditDate", java.util.Date.class, this.kdtEntries, "enterAuditDate.text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("version", java.math.BigDecimal.class, this.txtVersion, "text");
		dataBinder.registerBinding("project.name", String.class, this.txtProjectName, "text");
		dataBinder.registerBinding("aimCost", com.kingdee.eas.fdc.aimcost.AimCostInfo.class, this.prmtAimCost, "data");
		dataBinder.registerBinding("versionGroup", String.class, this.txtVersionGroup, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDatePicker1, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDatePicker2, "value");
		dataBinder.registerBinding("buildArea", java.math.BigDecimal.class, this.txtBuildArea, "value");
		dataBinder.registerBinding("soldArea", java.math.BigDecimal.class, this.txtSaleArea, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.ProgrammingEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compareEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compareEntry.programmingContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compareEntry.content", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compareEntry.reason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.controlAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.balance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.controlBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.signUpAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.changeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.settleAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.citeVersion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.isCiting", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.attachment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.parent.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sortNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.workContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.supMaterial", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.inviteWay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.buildPerSquare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.soldPerSquare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.inviteOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costAccountNames", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.estimateAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.isInvite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.isWTCiting", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.compare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.buildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.isInput", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.quantities", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.paperDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.documentsAuditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.resultAuditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractAuditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.enterAuditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionGroup", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("soldArea", ValidateHelper.ON_SAVE);    		
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
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_activeCellChanged method
     */
    protected void kdtEntries_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_tableClicked method
     */
    protected void kdtEntries_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStopped method
     */
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStarted method
     */
    protected void kdtEntries_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStarting method
     */
    protected void kdtEntries_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("compareEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("compareEntry.programmingContract"));
    	sic.add(new SelectorItemInfo("compareEntry.content"));
    	sic.add(new SelectorItemInfo("compareEntry.reason"));
    	sic.add(new SelectorItemInfo("entries.longNumber"));
    	sic.add(new SelectorItemInfo("entries.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("entries.number"));
		}
    	sic.add(new SelectorItemInfo("entries.amount"));
    	sic.add(new SelectorItemInfo("entries.controlAmount"));
    	sic.add(new SelectorItemInfo("entries.balance"));
    	sic.add(new SelectorItemInfo("entries.controlBalance"));
    	sic.add(new SelectorItemInfo("entries.signUpAmount"));
    	sic.add(new SelectorItemInfo("entries.changeAmount"));
    	sic.add(new SelectorItemInfo("entries.settleAmount"));
    	sic.add(new SelectorItemInfo("entries.citeVersion"));
    	sic.add(new SelectorItemInfo("entries.isCiting"));
    	sic.add(new SelectorItemInfo("entries.attachment"));
    	sic.add(new SelectorItemInfo("entries.description"));
    	sic.add(new SelectorItemInfo("entries.id"));
    	sic.add(new SelectorItemInfo("entries.level"));
    	sic.add(new SelectorItemInfo("entries.parent.longNumber"));
    	sic.add(new SelectorItemInfo("entries.displayName"));
    	sic.add(new SelectorItemInfo("entries.sortNumber"));
    	sic.add(new SelectorItemInfo("entries.workContent"));
    	sic.add(new SelectorItemInfo("entries.supMaterial"));
    	sic.add(new SelectorItemInfo("entries.inviteWay"));
    	sic.add(new SelectorItemInfo("entries.buildPerSquare"));
    	sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.inviteOrg.id"));
			sic.add(new SelectorItemInfo("entries.inviteOrg.name"));
        	sic.add(new SelectorItemInfo("entries.inviteOrg.number"));
		}
    	sic.add(new SelectorItemInfo("entries.costAccountNames"));
    	sic.add(new SelectorItemInfo("entries.estimateAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.contractType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.contractType.id"));
			sic.add(new SelectorItemInfo("entries.contractType.name"));
        	sic.add(new SelectorItemInfo("entries.contractType.number"));
		}
    	sic.add(new SelectorItemInfo("entries.isInvite"));
    	sic.add(new SelectorItemInfo("entries.isWTCiting"));
    	sic.add(new SelectorItemInfo("entries.compare"));
    	sic.add(new SelectorItemInfo("entries.buildPrice"));
    	sic.add(new SelectorItemInfo("entries.isInput"));
    	sic.add(new SelectorItemInfo("entries.quantities"));
    	sic.add(new SelectorItemInfo("entries.unit"));
    	sic.add(new SelectorItemInfo("entries.price"));
    	sic.add(new SelectorItemInfo("entries.paperDate"));
    	sic.add(new SelectorItemInfo("entries.documentsAuditDate"));
    	sic.add(new SelectorItemInfo("entries.resultAuditDate"));
    	sic.add(new SelectorItemInfo("entries.contractAuditDate"));
    	sic.add(new SelectorItemInfo("entries.enterAuditDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("project.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("aimCost.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("aimCost.id"));
        	sic.add(new SelectorItemInfo("aimCost.number"));
        	sic.add(new SelectorItemInfo("aimCost.versionName"));
        	sic.add(new SelectorItemInfo("aimCost.versionNumber"));
		}
        sic.add(new SelectorItemInfo("versionGroup"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("buildArea"));
        sic.add(new SelectorItemInfo("soldArea"));
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
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportPro_actionPerformed method
     */
    public void actionExportPro_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditInvite_actionPerformed method
     */
    public void actionEditInvite_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionComAddRow_actionPerformed method
     */
    public void actionComAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionComInsertRow_actionPerformed method
     */
    public void actionComInsertRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionComRemoveRow_actionPerformed method
     */
    public void actionComRemoveRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCompare_actionPerformed method
     */
    public void actionCompare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAmount_actionPerformed method
     */
    public void actionViewAmount_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionExportPro(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportPro() {
    	return false;
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }
	public RequestContext prepareActionEditInvite(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditInvite() {
    	return false;
    }
	public RequestContext prepareActionComAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionComAddRow() {
    	return false;
    }
	public RequestContext prepareActionComInsertRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionComInsertRow() {
    	return false;
    }
	public RequestContext prepareActionComRemoveRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionComRemoveRow() {
    	return false;
    }
	public RequestContext prepareActionCompare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCompare() {
    	return false;
    }
	public RequestContext prepareActionViewAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAmount() {
    	return false;
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionExportPro class
     */     
    protected class ActionExportPro extends ItemAction {     
    
        public ActionExportPro()
        {
            this(null);
        }

        public ActionExportPro(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportPro.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPro.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPro.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionExportPro", "actionExportPro_actionPerformed", e);
        }
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output ActionEditInvite class
     */     
    protected class ActionEditInvite extends ItemAction {     
    
        public ActionEditInvite()
        {
            this(null);
        }

        public ActionEditInvite(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditInvite.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditInvite.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditInvite.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionEditInvite", "actionEditInvite_actionPerformed", e);
        }
    }

    /**
     * output ActionComAddRow class
     */     
    protected class ActionComAddRow extends ItemAction {     
    
        public ActionComAddRow()
        {
            this(null);
        }

        public ActionComAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionComAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionComAddRow", "actionComAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionComInsertRow class
     */     
    protected class ActionComInsertRow extends ItemAction {     
    
        public ActionComInsertRow()
        {
            this(null);
        }

        public ActionComInsertRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionComInsertRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComInsertRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComInsertRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionComInsertRow", "actionComInsertRow_actionPerformed", e);
        }
    }

    /**
     * output ActionComRemoveRow class
     */     
    protected class ActionComRemoveRow extends ItemAction {     
    
        public ActionComRemoveRow()
        {
            this(null);
        }

        public ActionComRemoveRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionComRemoveRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComRemoveRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComRemoveRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionComRemoveRow", "actionComRemoveRow_actionPerformed", e);
        }
    }

    /**
     * output ActionCompare class
     */     
    protected class ActionCompare extends ItemAction {     
    
        public ActionCompare()
        {
            this(null);
        }

        public ActionCompare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCompare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionCompare", "actionCompare_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAmount class
     */     
    protected class ActionViewAmount extends ItemAction {     
    
        public ActionViewAmount()
        {
            this(null);
        }

        public ActionViewAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingEditUI.this, "ActionViewAmount", "actionViewAmount_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "ProgrammingEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}