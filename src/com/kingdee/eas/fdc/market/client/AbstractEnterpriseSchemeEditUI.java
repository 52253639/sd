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
public abstract class AbstractEnterpriseSchemeEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEnterpriseSchemeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterprisePlan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contThemeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalFactAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResult;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRow;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contThemeDescription;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalPlanAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEnterprisePlan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtThemeName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalFactAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtResult;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtThemeDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalPlanAmount;
    protected com.kingdee.eas.fdc.market.EnterpriseSchemeInfo editData = null;
    protected ActionAddRow actionAddRow = null;
    /**
     * output class constructor
     */
    public AbstractEnterpriseSchemeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEnterpriseSchemeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterprisePlan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contThemeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalFactAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalContractAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalPayAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsEnd = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contResult = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contThemeDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contTotalPlanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtEnterprisePlan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtThemeName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTotalFactAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalPayAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtResult = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtThemeDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtTotalPlanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contState.setName("contState");
        this.contAuditTime.setName("contAuditTime");
        this.contSellProject.setName("contSellProject");
        this.contEnterprisePlan.setName("contEnterprisePlan");
        this.contThemeName.setName("contThemeName");
        this.contStartDate.setName("contStartDate");
        this.contEndDate.setName("contEndDate");
        this.contTotalFactAmount.setName("contTotalFactAmount");
        this.contTotalContractAmount.setName("contTotalContractAmount");
        this.contTotalPayAmount.setName("contTotalPayAmount");
        this.chkIsEnd.setName("chkIsEnd");
        this.contResult.setName("contResult");
        this.btnAddRow.setName("btnAddRow");
        this.btnRemoveRow.setName("btnRemoveRow");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contThemeDescription.setName("contThemeDescription");
        this.kdtEntry.setName("kdtEntry");
        this.contTotalPlanAmount.setName("contTotalPlanAmount");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.comboState.setName("comboState");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtEnterprisePlan.setName("prmtEnterprisePlan");
        this.txtThemeName.setName("txtThemeName");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        this.txtTotalFactAmount.setName("txtTotalFactAmount");
        this.txtTotalContractAmount.setName("txtTotalContractAmount");
        this.txtTotalPayAmount.setName("txtTotalPayAmount");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtResult.setName("txtResult");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtThemeDescription.setName("txtThemeDescription");
        this.txtTotalPlanAmount.setName("txtTotalPlanAmount");
        // CoreUI		
        this.btnRemove.setEnabled(false);		
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
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contEnterprisePlan		
        this.contEnterprisePlan.setBoundLabelText(resHelper.getString("contEnterprisePlan.boundLabelText"));		
        this.contEnterprisePlan.setBoundLabelLength(100);		
        this.contEnterprisePlan.setBoundLabelUnderline(true);
        // contThemeName		
        this.contThemeName.setBoundLabelText(resHelper.getString("contThemeName.boundLabelText"));		
        this.contThemeName.setBoundLabelLength(100);		
        this.contThemeName.setBoundLabelUnderline(true);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);		
        this.contStartDate.setBoundLabelUnderline(true);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(100);		
        this.contEndDate.setBoundLabelUnderline(true);
        // contTotalFactAmount		
        this.contTotalFactAmount.setBoundLabelText(resHelper.getString("contTotalFactAmount.boundLabelText"));		
        this.contTotalFactAmount.setBoundLabelLength(100);		
        this.contTotalFactAmount.setBoundLabelUnderline(true);
        // contTotalContractAmount		
        this.contTotalContractAmount.setBoundLabelText(resHelper.getString("contTotalContractAmount.boundLabelText"));		
        this.contTotalContractAmount.setBoundLabelLength(100);		
        this.contTotalContractAmount.setBoundLabelUnderline(true);
        // contTotalPayAmount		
        this.contTotalPayAmount.setBoundLabelText(resHelper.getString("contTotalPayAmount.boundLabelText"));		
        this.contTotalPayAmount.setBoundLabelLength(100);		
        this.contTotalPayAmount.setBoundLabelUnderline(true);
        // chkIsEnd		
        this.chkIsEnd.setText(resHelper.getString("chkIsEnd.text"));
        this.chkIsEnd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsEnd_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contResult		
        this.contResult.setBoundLabelText(resHelper.getString("contResult.boundLabelText"));		
        this.contResult.setBoundLabelLength(100);		
        this.contResult.setBoundLabelUnderline(true);
        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));		
        this.btnAddRow.setVisible(false);
        // btnRemoveRow
        this.btnRemoveRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveRow.setText(resHelper.getString("btnRemoveRow.text"));		
        this.btnRemoveRow.setVisible(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contThemeDescription		
        this.contThemeDescription.setBoundLabelText(resHelper.getString("contThemeDescription.boundLabelText"));		
        this.contThemeDescription.setBoundLabelLength(100);		
        this.contThemeDescription.setBoundLabelUnderline(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sellPlanID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"contentItem\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"accountView\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"channelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"mediaName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"planAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"isEnd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"startTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"endTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"factAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"contractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"contractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"payAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sellPlanID}</t:Cell><t:Cell>$Resource{contentItem}</t:Cell><t:Cell>$Resource{accountView}</t:Cell><t:Cell>$Resource{channelType}</t:Cell><t:Cell>$Resource{mediaName}</t:Cell><t:Cell>$Resource{planAmount}</t:Cell><t:Cell>$Resource{isEnd}</t:Cell><t:Cell>$Resource{startTime}</t:Cell><t:Cell>$Resource{endTime}</t:Cell><t:Cell>$Resource{factAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{contractName}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{payAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"sellPlanID","","","","","","isEnd","startTime","endTime","factAmount","beizhu","","contractName","contractAmount","payAmount"});


        // contTotalPlanAmount		
        this.contTotalPlanAmount.setBoundLabelText(resHelper.getString("contTotalPlanAmount.boundLabelText"));		
        this.contTotalPlanAmount.setBoundLabelLength(100);		
        this.contTotalPlanAmount.setBoundLabelUnderline(true);		
        this.contTotalPlanAmount.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEditable(false);		
        this.pkCreateTime.setEnabled(false);
        // txtNumber
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FullOrgUnitQuery");		
        this.prmtOrgUnit.setEnabled(false);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEditable(false);		
        this.pkAuditTime.setEnabled(false);
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setEnabled(false);
        // prmtEnterprisePlan		
        this.prmtEnterprisePlan.setDisplayFormat("$name$");		
        this.prmtEnterprisePlan.setEditFormat("$number%");		
        this.prmtEnterprisePlan.setCommitFormat("$number%");		
        this.prmtEnterprisePlan.setQueryInfo("com.kingdee.eas.fdc.market.app.EnterprisePlanQuery");		
        this.prmtEnterprisePlan.setEnabled(false);
        // txtThemeName		
        this.txtThemeName.setMaxLength(200);
        // pkStartDate
        // pkEndDate
        // txtTotalFactAmount		
        this.txtTotalFactAmount.setEnabled(false);		
        this.txtTotalFactAmount.setDataType(1);
        // txtTotalContractAmount		
        this.txtTotalContractAmount.setEnabled(false);		
        this.txtTotalContractAmount.setDataType(1);
        // txtTotalPayAmount		
        this.txtTotalPayAmount.setEnabled(false);		
        this.txtTotalPayAmount.setDataType(1);
        // kDScrollPane2
        // txtResult
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setEnabled(false);
        // kDScrollPane1
        // txtThemeDescription
        // txtTotalPlanAmount		
        this.txtTotalPlanAmount.setDataType(1);
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
        contCreator.setBounds(new Rectangle(17, 573, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(17, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(17, 595, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(17, 595, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(17, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(17, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(731, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(731, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(17, 32, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(17, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(731, 99, 270, 19));
        this.add(contState, new KDLayout.Constraints(731, 99, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(731, 595, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(731, 595, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(17, 54, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(17, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterprisePlan.setBounds(new Rectangle(731, 10, 270, 19));
        this.add(contEnterprisePlan, new KDLayout.Constraints(731, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contThemeName.setBounds(new Rectangle(374, 10, 270, 19));
        this.add(contThemeName, new KDLayout.Constraints(374, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStartDate.setBounds(new Rectangle(374, 32, 270, 19));
        this.add(contStartDate, new KDLayout.Constraints(374, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEndDate.setBounds(new Rectangle(731, 32, 270, 19));
        this.add(contEndDate, new KDLayout.Constraints(731, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalFactAmount.setBounds(new Rectangle(17, 76, 270, 19));
        this.add(contTotalFactAmount, new KDLayout.Constraints(17, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalContractAmount.setBounds(new Rectangle(374, 76, 270, 19));
        this.add(contTotalContractAmount, new KDLayout.Constraints(374, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalPayAmount.setBounds(new Rectangle(731, 76, 270, 19));
        this.add(contTotalPayAmount, new KDLayout.Constraints(731, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsEnd.setBounds(new Rectangle(17, 196, 272, 19));
        this.add(chkIsEnd, new KDLayout.Constraints(17, 196, 272, 19, KDLayout.Constraints.ANCHOR_LEFT));
        contResult.setBounds(new Rectangle(17, 487, 985, 78));
        this.add(contResult, new KDLayout.Constraints(17, 487, 985, 78, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddRow.setBounds(new Rectangle(858, 196, 70, 19));
        this.add(btnAddRow, new KDLayout.Constraints(858, 196, 70, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveRow.setBounds(new Rectangle(932, 196, 70, 19));
        this.add(btnRemoveRow, new KDLayout.Constraints(932, 196, 70, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(374, 573, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(374, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(374, 595, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(374, 595, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contThemeDescription.setBounds(new Rectangle(17, 98, 628, 100));
        this.add(contThemeDescription, new KDLayout.Constraints(17, 98, 628, 100, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntry.setBounds(new Rectangle(17, 220, 985, 259));
        this.add(kdtEntry, new KDLayout.Constraints(17, 220, 985, 259, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalPlanAmount.setBounds(new Rectangle(374, 54, 270, 19));
        this.add(contTotalPlanAmount, new KDLayout.Constraints(374, 54, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contState
        contState.setBoundEditor(comboState);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contEnterprisePlan
        contEnterprisePlan.setBoundEditor(prmtEnterprisePlan);
        //contThemeName
        contThemeName.setBoundEditor(txtThemeName);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //contTotalFactAmount
        contTotalFactAmount.setBoundEditor(txtTotalFactAmount);
        //contTotalContractAmount
        contTotalContractAmount.setBoundEditor(txtTotalContractAmount);
        //contTotalPayAmount
        contTotalPayAmount.setBoundEditor(txtTotalPayAmount);
        //contResult
        contResult.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtResult, null);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contThemeDescription
        contThemeDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtThemeDescription, null);
        //contTotalPlanAmount
        contTotalPlanAmount.setBoundEditor(txtTotalPlanAmount);

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
		dataBinder.registerBinding("isEnd", boolean.class, this.chkIsEnd, "selected");
		dataBinder.registerBinding("entry.sellPlanID", String.class, this.kdtEntry, "sellPlanID.text");
		dataBinder.registerBinding("entry.factAmount", java.math.BigDecimal.class, this.kdtEntry, "factAmount.text");
		dataBinder.registerBinding("entry.contractName", String.class, this.kdtEntry, "contractName.text");
		dataBinder.registerBinding("entry.contractAmount", java.math.BigDecimal.class, this.kdtEntry, "contractAmount.text");
		dataBinder.registerBinding("entry.payAmount", java.math.BigDecimal.class, this.kdtEntry, "payAmount.text");
		dataBinder.registerBinding("entry.beizhu", String.class, this.kdtEntry, "beizhu.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.market.EnterpriseSchemeEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.startTime", java.util.Date.class, this.kdtEntry, "startTime.text");
		dataBinder.registerBinding("entry.endTime", java.util.Date.class, this.kdtEntry, "endTime.text");
		dataBinder.registerBinding("entry.isEnd", com.kingdee.eas.fdc.market.IsFinishEnum.class, this.kdtEntry, "isEnd.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("enterprisePlan", com.kingdee.eas.fdc.market.EnterprisePlanInfo.class, this.prmtEnterprisePlan, "data");
		dataBinder.registerBinding("themeName", String.class, this.txtThemeName, "text");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkStartDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkEndDate, "value");
		dataBinder.registerBinding("totalFactAmount", java.math.BigDecimal.class, this.txtTotalFactAmount, "value");
		dataBinder.registerBinding("totalContractAmount", java.math.BigDecimal.class, this.txtTotalContractAmount, "value");
		dataBinder.registerBinding("totalPayAmount", java.math.BigDecimal.class, this.txtTotalPayAmount, "value");
		dataBinder.registerBinding("result", String.class, this.txtResult, "text");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("themeDescription", String.class, this.txtThemeDescription, "text");
		dataBinder.registerBinding("totalPlanAmount", java.math.BigDecimal.class, this.txtTotalPlanAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.EnterpriseSchemeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.EnterpriseSchemeInfo)ov;
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
		getValidateHelper().registerBindProperty("isEnd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.sellPlanID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.factAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.contractName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.contractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.payAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.startTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.endTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.isEnd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterprisePlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("themeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalFactAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalContractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalPayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("result", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("themeDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalPlanAmount", ValidateHelper.ON_SAVE);    		
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
     * output chkIsEnd_stateChanged method
     */
    protected void chkIsEnd_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here123
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here123
    }

    /**
     * output kdtEntry_tableClicked method
     */
    protected void kdtEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isEnd"));
    sic.add(new SelectorItemInfo("entry.sellPlanID"));
    sic.add(new SelectorItemInfo("entry.factAmount"));
    sic.add(new SelectorItemInfo("entry.contractName"));
    sic.add(new SelectorItemInfo("entry.contractAmount"));
    sic.add(new SelectorItemInfo("entry.payAmount"));
    sic.add(new SelectorItemInfo("entry.beizhu"));
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
    sic.add(new SelectorItemInfo("entry.startTime"));
    sic.add(new SelectorItemInfo("entry.endTime"));
    sic.add(new SelectorItemInfo("entry.isEnd"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("enterprisePlan.*"));
        sic.add(new SelectorItemInfo("themeName"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("totalFactAmount"));
        sic.add(new SelectorItemInfo("totalContractAmount"));
        sic.add(new SelectorItemInfo("totalPayAmount"));
        sic.add(new SelectorItemInfo("result"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("themeDescription"));
        sic.add(new SelectorItemInfo("totalPlanAmount"));
        return sic;
    }        
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRow() {
    	return false;
    }

    /**
     * output ActionAddRow class
     */     
    protected class ActionAddRow extends ItemAction {     
    
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEnterpriseSchemeEditUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "EnterpriseSchemeEditUI");
    }




}