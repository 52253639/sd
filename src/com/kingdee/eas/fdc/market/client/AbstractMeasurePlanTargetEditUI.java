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
public abstract class AbstractMeasurePlanTargetEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMeasurePlanTargetEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMeasurePhases;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectAgin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersions;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionsName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMeasureType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustCause;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemarks;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsNew;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMeasurePhases;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectAgin;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersions;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionsName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMeasureType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtAdjustCause;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemarks;
    protected com.kingdee.bos.ctrl.swing.KDComboBox versionType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRow;
    protected com.kingdee.eas.fdc.market.MeasurePlanTargetInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMeasurePlanTargetEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMeasurePlanTargetEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAudit
        String _tempStr = null;
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMeasurePhases = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectAgin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersions = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersionsName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMeasureType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdjustCause = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemarks = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsNew = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtMeasurePhases = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProjectAgin = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtVersions = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVersionsName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboMeasureType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtAdjustCause = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRemarks = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.versionType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contState.setName("contState");
        this.contAuditTime.setName("contAuditTime");
        this.contMeasurePhases.setName("contMeasurePhases");
        this.contProjectAgin.setName("contProjectAgin");
        this.contVersions.setName("contVersions");
        this.contVersionsName.setName("contVersionsName");
        this.contMeasureType.setName("contMeasureType");
        this.contAdjustCause.setName("contAdjustCause");
        this.contTotalAmount.setName("contTotalAmount");
        this.contRemarks.setName("contRemarks");
        this.chkIsNew.setName("chkIsNew");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.kDPanel1.setName("kDPanel1");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.comboState.setName("comboState");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtMeasurePhases.setName("prmtMeasurePhases");
        this.prmtProjectAgin.setName("prmtProjectAgin");
        this.txtVersions.setName("txtVersions");
        this.txtVersionsName.setName("txtVersionsName");
        this.comboMeasureType.setName("comboMeasureType");
        this.txtAdjustCause.setName("txtAdjustCause");
        this.txtTotalAmount.setName("txtTotalAmount");
        this.txtRemarks.setName("txtRemarks");
        this.versionType.setName("versionType");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.kdtEntry.setName("kdtEntry");
        this.btnInsertRow.setName("btnInsertRow");
        this.btnRemoveRow.setName("btnRemoveRow");
        // CoreUI		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setEnabled(false);		
        this.contBizDate.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setEnabled(false);		
        this.contState.setVisible(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contMeasurePhases		
        this.contMeasurePhases.setBoundLabelText(resHelper.getString("contMeasurePhases.boundLabelText"));		
        this.contMeasurePhases.setBoundLabelLength(100);		
        this.contMeasurePhases.setBoundLabelUnderline(true);
        // contProjectAgin		
        this.contProjectAgin.setBoundLabelText(resHelper.getString("contProjectAgin.boundLabelText"));		
        this.contProjectAgin.setBoundLabelLength(100);		
        this.contProjectAgin.setBoundLabelUnderline(true);
        // contVersions		
        this.contVersions.setBoundLabelText(resHelper.getString("contVersions.boundLabelText"));		
        this.contVersions.setBoundLabelLength(100);		
        this.contVersions.setBoundLabelUnderline(true);
        // contVersionsName		
        this.contVersionsName.setBoundLabelText(resHelper.getString("contVersionsName.boundLabelText"));		
        this.contVersionsName.setBoundLabelLength(100);		
        this.contVersionsName.setBoundLabelUnderline(true);
        // contMeasureType		
        this.contMeasureType.setBoundLabelText(resHelper.getString("contMeasureType.boundLabelText"));		
        this.contMeasureType.setBoundLabelLength(100);		
        this.contMeasureType.setBoundLabelUnderline(true);		
        this.contMeasureType.setEnabled(false);
        // contAdjustCause		
        this.contAdjustCause.setBoundLabelText(resHelper.getString("contAdjustCause.boundLabelText"));		
        this.contAdjustCause.setBoundLabelLength(100);		
        this.contAdjustCause.setBoundLabelUnderline(true);
        // contTotalAmount		
        this.contTotalAmount.setBoundLabelText(resHelper.getString("contTotalAmount.boundLabelText"));		
        this.contTotalAmount.setBoundLabelLength(100);		
        this.contTotalAmount.setBoundLabelUnderline(true);		
        this.contTotalAmount.setEnabled(false);
        // contRemarks		
        this.contRemarks.setBoundLabelText(resHelper.getString("contRemarks.boundLabelText"));		
        this.contRemarks.setBoundLabelLength(100);		
        this.contRemarks.setBoundLabelUnderline(true);
        // chkIsNew		
        this.chkIsNew.setText(resHelper.getString("chkIsNew.text"));		
        this.chkIsNew.setEnabled(false);		
        this.chkIsNew.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setEnabled(false);		
        this.pkBizDate.setVisible(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.FullOrgUnitQuery");		
        this.prmtOrgUnit.setDisplayFormat("$name$");		
        this.prmtOrgUnit.setEditFormat("$number$");		
        this.prmtOrgUnit.setCommitFormat("$number$");
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);		
        this.comboState.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtMeasurePhases		
        this.prmtMeasurePhases.setQueryInfo("com.kingdee.eas.fdc.basedata.app.MeasurePhaseQuery");		
        this.prmtMeasurePhases.setEditFormat("$number$");		
        this.prmtMeasurePhases.setDisplayFormat("$name$");		
        this.prmtMeasurePhases.setCommitFormat("$number$");
        this.prmtMeasurePhases.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtMeasurePhases_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProjectAgin		
        this.prmtProjectAgin.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtProjectAgin.setCommitFormat("$number$");		
        this.prmtProjectAgin.setEditFormat("$number$");		
        this.prmtProjectAgin.setDisplayFormat("$name$");
        // txtVersions		
        this.txtVersions.setMaxLength(50);
        // txtVersionsName		
        this.txtVersionsName.setMaxLength(100);
        // comboMeasureType		
        this.comboMeasureType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.PhaseTypeEnum").toArray());		
        this.comboMeasureType.setEnabled(false);
        // txtAdjustCause
        // txtTotalAmount		
        this.txtTotalAmount.setEnabled(false);
        // txtRemarks
        // versionType		
        this.versionType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.VersionTypeEnum").toArray());		
        this.versionType.setRequired(true);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"productConstitute\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"productType\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"newAreaRange\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"acreage\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"quantity\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"price\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"sumAmount\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"id\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{productConstitute}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{newAreaRange}</t:Cell><t:Cell>$Resource{acreage}</t:Cell><t:Cell>$Resource{quantity}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{sumAmount}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{productConstitute_Row2}</t:Cell><t:Cell>$Resource{productType_Row2}</t:Cell><t:Cell>$Resource{newAreaRange_Row2}</t:Cell><t:Cell>$Resource{acreage_Row2}</t:Cell><t:Cell>$Resource{quantity_Row2}</t:Cell><t:Cell>$Resource{price_Row2}</t:Cell><t:Cell>$Resource{sumAmount_Row2}</t:Cell><t:Cell>$Resource{id_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnInsertRow
        this.btnInsertRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsertRow.setText(resHelper.getString("btnInsertRow.text"));
        this.btnInsertRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnInsertRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveRow
        this.btnRemoveRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveRow.setText(resHelper.getString("btnRemoveRow.text"));
        this.btnRemoveRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveRow_actionPerformed(e);
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(12, 573, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(12, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(12, 595, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(12, 595, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(12, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(943, 516, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(943, 516, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(733, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(733, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(12, 31, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(12, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(939, 544, 270, 19));
        this.add(contState, new KDLayout.Constraints(939, 544, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(733, 595, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(733, 595, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMeasurePhases.setBounds(new Rectangle(372, 9, 270, 19));
        this.add(contMeasurePhases, new KDLayout.Constraints(372, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProjectAgin.setBounds(new Rectangle(12, 53, 270, 19));
        this.add(contProjectAgin, new KDLayout.Constraints(12, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersions.setBounds(new Rectangle(733, 9, 270, 19));
        this.add(contVersions, new KDLayout.Constraints(733, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contVersionsName.setBounds(new Rectangle(733, 31, 270, 19));
        this.add(contVersionsName, new KDLayout.Constraints(733, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMeasureType.setBounds(new Rectangle(372, 31, 270, 19));
        this.add(contMeasureType, new KDLayout.Constraints(372, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAdjustCause.setBounds(new Rectangle(12, 75, 630, 56));
        this.add(contAdjustCause, new KDLayout.Constraints(12, 75, 630, 56, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalAmount.setBounds(new Rectangle(372, 53, 270, 19));
        this.add(contTotalAmount, new KDLayout.Constraints(372, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRemarks.setBounds(new Rectangle(12, 134, 630, 37));
        this.add(contRemarks, new KDLayout.Constraints(12, 134, 630, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsNew.setBounds(new Rectangle(938, 574, 140, 19));
        this.add(chkIsNew, new KDLayout.Constraints(938, 574, 140, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(733, 53, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(733, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(372, 573, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(372, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(372, 595, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(372, 595, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(12, 177, 991, 385));
        this.add(kDPanel1, new KDLayout.Constraints(12, 177, 991, 385, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contState
        contState.setBoundEditor(comboState);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contMeasurePhases
        contMeasurePhases.setBoundEditor(prmtMeasurePhases);
        //contProjectAgin
        contProjectAgin.setBoundEditor(prmtProjectAgin);
        //contVersions
        contVersions.setBoundEditor(txtVersions);
        //contVersionsName
        contVersionsName.setBoundEditor(txtVersionsName);
        //contMeasureType
        contMeasureType.setBoundEditor(comboMeasureType);
        //contAdjustCause
        contAdjustCause.setBoundEditor(txtAdjustCause);
        //contTotalAmount
        contTotalAmount.setBoundEditor(txtTotalAmount);
        //contRemarks
        contRemarks.setBoundEditor(txtRemarks);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(versionType);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(12, 177, 991, 385));        kdtEntry.setBounds(new Rectangle(12, 36, 964, 334));
        kDPanel1.add(kdtEntry, new KDLayout.Constraints(12, 36, 964, 334, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnInsertRow.setBounds(new Rectangle(799, 12, 86, 19));
        kDPanel1.add(btnInsertRow, new KDLayout.Constraints(799, 12, 86, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveRow.setBounds(new Rectangle(890, 12, 86, 19));
        kDPanel1.add(btnRemoveRow, new KDLayout.Constraints(890, 12, 86, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
		dataBinder.registerBinding("isNew", boolean.class, this.chkIsNew, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("measurePhases", com.kingdee.eas.fdc.basedata.MeasurePhaseInfo.class, this.prmtMeasurePhases, "data");
		dataBinder.registerBinding("projectAgin", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProjectAgin, "data");
		dataBinder.registerBinding("versions", String.class, this.txtVersions, "text");
		dataBinder.registerBinding("versionsName", String.class, this.txtVersionsName, "text");
		dataBinder.registerBinding("measureType", com.kingdee.eas.fdc.basedata.PhaseTypeEnum.class, this.comboMeasureType, "selectedItem");
		dataBinder.registerBinding("adjustCause", String.class, this.txtAdjustCause, "text");
		dataBinder.registerBinding("totalAmount", java.math.BigDecimal.class, this.txtTotalAmount, "value");
		dataBinder.registerBinding("remarks", String.class, this.txtRemarks, "text");
		dataBinder.registerBinding("versionType", com.kingdee.eas.fdc.market.VersionTypeEnum.class, this.versionType, "selectedItem");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MeasurePlanTargetEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.MeasurePlanTargetInfo)ov;
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
		getValidateHelper().registerBindProperty("isNew", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("measurePhases", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectAgin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versions", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionsName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("measureType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustCause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remarks", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    		
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
     * output prmtMeasurePhases_dataChanged method
     */
    protected void prmtMeasurePhases_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here11
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here11
    }

    /**
     * output kdtEntry_editStarted method
     */
    protected void kdtEntry_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnInsertRow_actionPerformed method
     */
    protected void btnInsertRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here111
    }

    /**
     * output btnRemoveRow_actionPerformed method
     */
    protected void btnRemoveRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here111
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isNew"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("measurePhases.*"));
        sic.add(new SelectorItemInfo("projectAgin.*"));
        sic.add(new SelectorItemInfo("versions"));
        sic.add(new SelectorItemInfo("versionsName"));
        sic.add(new SelectorItemInfo("measureType"));
        sic.add(new SelectorItemInfo("adjustCause"));
        sic.add(new SelectorItemInfo("totalAmount"));
        sic.add(new SelectorItemInfo("remarks"));
        sic.add(new SelectorItemInfo("versionType"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        return sic;
    }        
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MeasurePlanTargetEditUI");
    }




}