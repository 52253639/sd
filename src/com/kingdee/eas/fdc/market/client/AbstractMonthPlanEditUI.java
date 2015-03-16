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
public abstract class AbstractMonthPlanEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMonthPlanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersoin;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtProReference;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtPlanYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtPlanMonth;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersoin;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDComboBox versionType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPrePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreRecover;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.eas.fdc.market.MonthPlanInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMonthPlanEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMonthPlanEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersoin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtProReference = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPlanYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtPlanMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtVersoin = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.versionType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtPreArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPreCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPrePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPreAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPreRecover = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.contBookedDate.setName("contBookedDate");
        this.contSellProject.setName("contSellProject");
        this.contPlanYear.setName("contPlanYear");
        this.contPlanMonth.setName("contPlanMonth");
        this.contVersoin.setName("contVersoin");
        this.kdtProReference.setName("kdtProReference");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.pkBookedDate.setName("pkBookedDate");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtPlanYear.setName("txtPlanYear");
        this.txtPlanMonth.setName("txtPlanMonth");
        this.txtVersoin.setName("txtVersoin");
        this.kdtEntry.setName("kdtEntry");
        this.versionType.setName("versionType");
        this.txtPreArea.setName("txtPreArea");
        this.txtPreCount.setName("txtPreCount");
        this.txtPrePrice.setName("txtPrePrice");
        this.txtPreAmount.setName("txtPreAmount");
        this.txtPreRecover.setName("txtPreRecover");
        this.kDTextArea1.setName("kDTextArea1");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
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
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contBookedDate		
        this.contBookedDate.setBoundLabelText(resHelper.getString("contBookedDate.boundLabelText"));		
        this.contBookedDate.setBoundLabelLength(100);		
        this.contBookedDate.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contPlanYear		
        this.contPlanYear.setBoundLabelText(resHelper.getString("contPlanYear.boundLabelText"));		
        this.contPlanYear.setBoundLabelLength(100);		
        this.contPlanYear.setBoundLabelUnderline(true);
        // contPlanMonth		
        this.contPlanMonth.setBoundLabelText(resHelper.getString("contPlanMonth.boundLabelText"));		
        this.contPlanMonth.setBoundLabelLength(100);		
        this.contPlanMonth.setBoundLabelUnderline(true);
        // contVersoin		
        this.contVersoin.setBoundLabelText(resHelper.getString("contVersoin.boundLabelText"));		
        this.contVersoin.setBoundLabelLength(100);		
        this.contVersoin.setBoundLabelUnderline(true);
        // kdtProReference
		String kdtProReferenceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"year\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearValue\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"targetValue\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"planAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"actulAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"finishRate\" t:width=\"78\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"unfinished\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{year}</t:Cell><t:Cell>$Resource{yearValue}</t:Cell><t:Cell>$Resource{targetValue}</t:Cell><t:Cell>$Resource{planAmount}</t:Cell><t:Cell>$Resource{actulAmount}</t:Cell><t:Cell>$Resource{finishRate}</t:Cell><t:Cell>$Resource{unfinished}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtProReference.setFormatXml(resHelper.translateString("kdtProReference",kdtProReferenceStrXML));

        

        // kDTabbedPane1
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(false);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(false);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setVisible(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(100);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setEnabled(false);
        // txtNumber
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setEnabled(false);
        // txtName
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // pkBookedDate
        // prmtSellProject		
        this.prmtSellProject.setEnabled(false);
        // txtPlanYear
        this.txtPlanYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    txtPlanYear_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPlanMonth
        this.txtPlanMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    txtPlanMonth_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtVersoin		
        this.txtVersoin.setMaxLength(44);		
        this.txtVersoin.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"productType\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"areaRange\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"areaPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"areaActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"ploidyPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"ploidyActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"pricePlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"priceActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"amountPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"amountActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"recoverPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"recoverActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"preArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"preCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"prePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"preAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"preRecover\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"areaPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"areaActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"ploidyPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"ploidyActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"pricePlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"priceActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"amountPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"amountActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"recoverPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"recoverActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"areaPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"areaActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"ploidyPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"ploidyActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"pricePlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"priceActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"amountPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"amountActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"recoverPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"recoverActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"areaPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"areaActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"ploidyPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"ploidyActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" /><t:Column t:key=\"pricePlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" /><t:Column t:key=\"priceActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"amountPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"amountActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" /><t:Column t:key=\"recoverPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" /><t:Column t:key=\"recoverActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" /><t:Column t:key=\"areaPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" /><t:Column t:key=\"areaActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" /><t:Column t:key=\"ploidyPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" /><t:Column t:key=\"ploidyActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" /><t:Column t:key=\"pricePlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" /><t:Column t:key=\"priceActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" /><t:Column t:key=\"amountPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" /><t:Column t:key=\"amountActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" /><t:Column t:key=\"recoverPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" /><t:Column t:key=\"recoverActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" /><t:Column t:key=\"areaPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" /><t:Column t:key=\"areaActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" /><t:Column t:key=\"ploidyPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" /><t:Column t:key=\"ploidyActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" /><t:Column t:key=\"pricePlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"62\" /><t:Column t:key=\"priceActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"63\" /><t:Column t:key=\"amountPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"64\" /><t:Column t:key=\"amountActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"65\" /><t:Column t:key=\"recoverPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"66\" /><t:Column t:key=\"recoverActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"67\" /><t:Column t:key=\"areaPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"68\" /><t:Column t:key=\"areaActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"69\" /><t:Column t:key=\"ploidyPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"70\" /><t:Column t:key=\"ploidyActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"71\" /><t:Column t:key=\"pricePlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"72\" /><t:Column t:key=\"priceActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"73\" /><t:Column t:key=\"amountPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"74\" /><t:Column t:key=\"amountActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"75\" /><t:Column t:key=\"recoverPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"76\" /><t:Column t:key=\"recoverActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"77\" /><t:Column t:key=\"areaPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"78\" /><t:Column t:key=\"areaActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"79\" /><t:Column t:key=\"ploidyPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"80\" /><t:Column t:key=\"ploidyActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"81\" /><t:Column t:key=\"pricePlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"82\" /><t:Column t:key=\"priceActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"83\" /><t:Column t:key=\"amountPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"84\" /><t:Column t:key=\"amountActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"85\" /><t:Column t:key=\"recoverPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"86\" /><t:Column t:key=\"recoverActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"87\" /><t:Column t:key=\"areaPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"88\" /><t:Column t:key=\"areaActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"89\" /><t:Column t:key=\"ploidyPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"90\" /><t:Column t:key=\"ploidyActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"91\" /><t:Column t:key=\"pricePlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"92\" /><t:Column t:key=\"priceActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"93\" /><t:Column t:key=\"amountPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"94\" /><t:Column t:key=\"amountActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"95\" /><t:Column t:key=\"recoverPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"96\" /><t:Column t:key=\"recoverActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"97\" /><t:Column t:key=\"areaPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"98\" /><t:Column t:key=\"areaActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"99\" /><t:Column t:key=\"ploidyPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"100\" /><t:Column t:key=\"ploidyActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"101\" /><t:Column t:key=\"pricePlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"102\" /><t:Column t:key=\"priceActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"103\" /><t:Column t:key=\"amountPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"104\" /><t:Column t:key=\"amountActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"105\" /><t:Column t:key=\"recoverPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"106\" /><t:Column t:key=\"recoverActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"107\" /><t:Column t:key=\"areaPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"108\" /><t:Column t:key=\"areaActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"109\" /><t:Column t:key=\"ploidyPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"110\" /><t:Column t:key=\"ploidyActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"111\" /><t:Column t:key=\"pricePlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"112\" /><t:Column t:key=\"priceActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"113\" /><t:Column t:key=\"amountPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"114\" /><t:Column t:key=\"amountActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"115\" /><t:Column t:key=\"recoverPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"116\" /><t:Column t:key=\"recoverActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"117\" /><t:Column t:key=\"areaPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"118\" /><t:Column t:key=\"areaActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"119\" /><t:Column t:key=\"ploidyPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"120\" /><t:Column t:key=\"ploidyActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"121\" /><t:Column t:key=\"pricePlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"122\" /><t:Column t:key=\"priceActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"123\" /><t:Column t:key=\"amountPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"124\" /><t:Column t:key=\"amountActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"125\" /><t:Column t:key=\"recoverPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"126\" /><t:Column t:key=\"recoverActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"127\" /><t:Column t:key=\"areaPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"128\" /><t:Column t:key=\"areaActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"129\" /><t:Column t:key=\"ploidyPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"130\" /><t:Column t:key=\"ploidyActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"131\" /><t:Column t:key=\"pricePlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"132\" /><t:Column t:key=\"priceActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"133\" /><t:Column t:key=\"amountPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"134\" /><t:Column t:key=\"amountActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"135\" /><t:Column t:key=\"recoverPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"136\" /><t:Column t:key=\"recoverActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"137\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{areaRange}</t:Cell><t:Cell>$Resource{areaPlan}</t:Cell><t:Cell>$Resource{areaActual}</t:Cell><t:Cell>$Resource{ploidyPlan}</t:Cell><t:Cell>$Resource{ploidyActual}</t:Cell><t:Cell>$Resource{pricePlan}</t:Cell><t:Cell>$Resource{priceActual}</t:Cell><t:Cell>$Resource{amountPlan}</t:Cell><t:Cell>$Resource{amountActual}</t:Cell><t:Cell>$Resource{recoverPlan}</t:Cell><t:Cell>$Resource{recoverActual}</t:Cell><t:Cell>$Resource{preArea}</t:Cell><t:Cell>$Resource{preCount}</t:Cell><t:Cell>$Resource{prePrice}</t:Cell><t:Cell>$Resource{preAmount}</t:Cell><t:Cell>$Resource{preRecover}</t:Cell><t:Cell>$Resource{areaPlan1}</t:Cell><t:Cell>$Resource{areaActual1}</t:Cell><t:Cell>$Resource{ploidyPlan1}</t:Cell><t:Cell>$Resource{ploidyActual1}</t:Cell><t:Cell>$Resource{pricePlan1}</t:Cell><t:Cell>$Resource{priceActual1}</t:Cell><t:Cell>$Resource{amountPlan1}</t:Cell><t:Cell>$Resource{amountActual1}</t:Cell><t:Cell>$Resource{recoverPlan1}</t:Cell><t:Cell>$Resource{recoverActual1}</t:Cell><t:Cell>$Resource{areaPlan2}</t:Cell><t:Cell>$Resource{areaActual2}</t:Cell><t:Cell>$Resource{ploidyPlan2}</t:Cell><t:Cell>$Resource{ploidyActual2}</t:Cell><t:Cell>$Resource{pricePlan2}</t:Cell><t:Cell>$Resource{priceActual2}</t:Cell><t:Cell>$Resource{amountPlan2}</t:Cell><t:Cell>$Resource{amountActual2}</t:Cell><t:Cell>$Resource{recoverPlan2}</t:Cell><t:Cell>$Resource{recoverActual2}</t:Cell><t:Cell>$Resource{areaPlan3}</t:Cell><t:Cell>$Resource{areaActual3}</t:Cell><t:Cell>$Resource{ploidyPlan3}</t:Cell><t:Cell>$Resource{ploidyActual3}</t:Cell><t:Cell>$Resource{pricePlan3}</t:Cell><t:Cell>$Resource{priceActual3}</t:Cell><t:Cell>$Resource{amountPlan3}</t:Cell><t:Cell>$Resource{amountActual3}</t:Cell><t:Cell>$Resource{recoverPlan3}</t:Cell><t:Cell>$Resource{recoverActual3}</t:Cell><t:Cell>$Resource{areaPlan4}</t:Cell><t:Cell>$Resource{areaActual4}</t:Cell><t:Cell>$Resource{ploidyPlan4}</t:Cell><t:Cell>$Resource{ploidyActual4}</t:Cell><t:Cell>$Resource{pricePlan4}</t:Cell><t:Cell>$Resource{priceActual4}</t:Cell><t:Cell>$Resource{amountPlan4}</t:Cell><t:Cell>$Resource{amountActual4}</t:Cell><t:Cell>$Resource{recoverPlan4}</t:Cell><t:Cell>$Resource{recoverActual4}</t:Cell><t:Cell>$Resource{areaPlan5}</t:Cell><t:Cell>$Resource{areaActual5}</t:Cell><t:Cell>$Resource{ploidyPlan5}</t:Cell><t:Cell>$Resource{ploidyActual5}</t:Cell><t:Cell>$Resource{pricePlan5}</t:Cell><t:Cell>$Resource{priceActual5}</t:Cell><t:Cell>$Resource{amountPlan5}</t:Cell><t:Cell>$Resource{amountActual5}</t:Cell><t:Cell>$Resource{recoverPlan5}</t:Cell><t:Cell>$Resource{recoverActual5}</t:Cell><t:Cell>$Resource{areaPlan6}</t:Cell><t:Cell>$Resource{areaActual6}</t:Cell><t:Cell>$Resource{ploidyPlan6}</t:Cell><t:Cell>$Resource{ploidyActual6}</t:Cell><t:Cell>$Resource{pricePlan6}</t:Cell><t:Cell>$Resource{priceActual6}</t:Cell><t:Cell>$Resource{amountPlan6}</t:Cell><t:Cell>$Resource{amountActual6}</t:Cell><t:Cell>$Resource{recoverPlan6}</t:Cell><t:Cell>$Resource{recoverActual6}</t:Cell><t:Cell>$Resource{areaPlan7}</t:Cell><t:Cell>$Resource{areaActual7}</t:Cell><t:Cell>$Resource{ploidyPlan7}</t:Cell><t:Cell>$Resource{ploidyActual7}</t:Cell><t:Cell>$Resource{pricePlan7}</t:Cell><t:Cell>$Resource{priceActual7}</t:Cell><t:Cell>$Resource{amountPlan7}</t:Cell><t:Cell>$Resource{amountActual7}</t:Cell><t:Cell>$Resource{recoverPlan7}</t:Cell><t:Cell>$Resource{recoverActual7}</t:Cell><t:Cell>$Resource{areaPlan8}</t:Cell><t:Cell>$Resource{areaActual8}</t:Cell><t:Cell>$Resource{ploidyPlan8}</t:Cell><t:Cell>$Resource{ploidyActual8}</t:Cell><t:Cell>$Resource{pricePlan8}</t:Cell><t:Cell>$Resource{priceActual8}</t:Cell><t:Cell>$Resource{amountPlan8}</t:Cell><t:Cell>$Resource{amountActual8}</t:Cell><t:Cell>$Resource{recoverPlan8}</t:Cell><t:Cell>$Resource{recoverActual8}</t:Cell><t:Cell>$Resource{areaPlan9}</t:Cell><t:Cell>$Resource{areaActual9}</t:Cell><t:Cell>$Resource{ploidyPlan9}</t:Cell><t:Cell>$Resource{ploidyActual9}</t:Cell><t:Cell>$Resource{pricePlan9}</t:Cell><t:Cell>$Resource{priceActual9}</t:Cell><t:Cell>$Resource{amountPlan9}</t:Cell><t:Cell>$Resource{amountActual9}</t:Cell><t:Cell>$Resource{recoverPlan9}</t:Cell><t:Cell>$Resource{recoverActual9}</t:Cell><t:Cell>$Resource{areaPlan10}</t:Cell><t:Cell>$Resource{areaActual10}</t:Cell><t:Cell>$Resource{ploidyPlan10}</t:Cell><t:Cell>$Resource{ploidyActual10}</t:Cell><t:Cell>$Resource{pricePlan10}</t:Cell><t:Cell>$Resource{priceActual10}</t:Cell><t:Cell>$Resource{amountPlan10}</t:Cell><t:Cell>$Resource{amountActual10}</t:Cell><t:Cell>$Resource{recoverPlan10}</t:Cell><t:Cell>$Resource{recoverActual10}</t:Cell><t:Cell>$Resource{areaPlan11}</t:Cell><t:Cell>$Resource{areaActual11}</t:Cell><t:Cell>$Resource{ploidyPlan11}</t:Cell><t:Cell>$Resource{ploidyActual11}</t:Cell><t:Cell>$Resource{pricePlan11}</t:Cell><t:Cell>$Resource{priceActual11}</t:Cell><t:Cell>$Resource{amountPlan11}</t:Cell><t:Cell>$Resource{amountActual11}</t:Cell><t:Cell>$Resource{recoverPlan11}</t:Cell><t:Cell>$Resource{recoverActual11}</t:Cell><t:Cell>$Resource{areaPlan12}</t:Cell><t:Cell>$Resource{areaActual12}</t:Cell><t:Cell>$Resource{ploidyPlan12}</t:Cell><t:Cell>$Resource{ploidyActual12}</t:Cell><t:Cell>$Resource{pricePlan12}</t:Cell><t:Cell>$Resource{priceActual12}</t:Cell><t:Cell>$Resource{amountPlan12}</t:Cell><t:Cell>$Resource{amountActual12}</t:Cell><t:Cell>$Resource{recoverPlan12}</t:Cell><t:Cell>$Resource{recoverActual12}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type_Row2}</t:Cell><t:Cell>$Resource{productType_Row2}</t:Cell><t:Cell>$Resource{areaRange_Row2}</t:Cell><t:Cell>$Resource{areaPlan_Row2}</t:Cell><t:Cell>$Resource{areaActual_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan_Row2}</t:Cell><t:Cell>$Resource{ploidyActual_Row2}</t:Cell><t:Cell>$Resource{pricePlan_Row2}</t:Cell><t:Cell>$Resource{priceActual_Row2}</t:Cell><t:Cell>$Resource{amountPlan_Row2}</t:Cell><t:Cell>$Resource{amountActual_Row2}</t:Cell><t:Cell>$Resource{recoverPlan_Row2}</t:Cell><t:Cell>$Resource{recoverActual_Row2}</t:Cell><t:Cell>$Resource{preArea_Row2}</t:Cell><t:Cell>$Resource{preCount_Row2}</t:Cell><t:Cell>$Resource{prePrice_Row2}</t:Cell><t:Cell>$Resource{preAmount_Row2}</t:Cell><t:Cell>$Resource{preRecover_Row2}</t:Cell><t:Cell>$Resource{areaPlan1_Row2}</t:Cell><t:Cell>$Resource{areaActual1_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan1_Row2}</t:Cell><t:Cell>$Resource{ploidyActual1_Row2}</t:Cell><t:Cell>$Resource{pricePlan1_Row2}</t:Cell><t:Cell>$Resource{priceActual1_Row2}</t:Cell><t:Cell>$Resource{amountPlan1_Row2}</t:Cell><t:Cell>$Resource{amountActual1_Row2}</t:Cell><t:Cell>$Resource{recoverPlan1_Row2}</t:Cell><t:Cell>$Resource{recoverActual1_Row2}</t:Cell><t:Cell>$Resource{areaPlan2_Row2}</t:Cell><t:Cell>$Resource{areaActual2_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan2_Row2}</t:Cell><t:Cell>$Resource{ploidyActual2_Row2}</t:Cell><t:Cell>$Resource{pricePlan2_Row2}</t:Cell><t:Cell>$Resource{priceActual2_Row2}</t:Cell><t:Cell>$Resource{amountPlan2_Row2}</t:Cell><t:Cell>$Resource{amountActual2_Row2}</t:Cell><t:Cell>$Resource{recoverPlan2_Row2}</t:Cell><t:Cell>$Resource{recoverActual2_Row2}</t:Cell><t:Cell>$Resource{areaPlan3_Row2}</t:Cell><t:Cell>$Resource{areaActual3_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan3_Row2}</t:Cell><t:Cell>$Resource{ploidyActual3_Row2}</t:Cell><t:Cell>$Resource{pricePlan3_Row2}</t:Cell><t:Cell>$Resource{priceActual3_Row2}</t:Cell><t:Cell>$Resource{amountPlan3_Row2}</t:Cell><t:Cell>$Resource{amountActual3_Row2}</t:Cell><t:Cell>$Resource{recoverPlan3_Row2}</t:Cell><t:Cell>$Resource{recoverActual3_Row2}</t:Cell><t:Cell>$Resource{areaPlan4_Row2}</t:Cell><t:Cell>$Resource{areaActual4_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan4_Row2}</t:Cell><t:Cell>$Resource{ploidyActual4_Row2}</t:Cell><t:Cell>$Resource{pricePlan4_Row2}</t:Cell><t:Cell>$Resource{priceActual4_Row2}</t:Cell><t:Cell>$Resource{amountPlan4_Row2}</t:Cell><t:Cell>$Resource{amountActual4_Row2}</t:Cell><t:Cell>$Resource{recoverPlan4_Row2}</t:Cell><t:Cell>$Resource{recoverActual4_Row2}</t:Cell><t:Cell>$Resource{areaPlan5_Row2}</t:Cell><t:Cell>$Resource{areaActual5_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan5_Row2}</t:Cell><t:Cell>$Resource{ploidyActual5_Row2}</t:Cell><t:Cell>$Resource{pricePlan5_Row2}</t:Cell><t:Cell>$Resource{priceActual5_Row2}</t:Cell><t:Cell>$Resource{amountPlan5_Row2}</t:Cell><t:Cell>$Resource{amountActual5_Row2}</t:Cell><t:Cell>$Resource{recoverPlan5_Row2}</t:Cell><t:Cell>$Resource{recoverActual5_Row2}</t:Cell><t:Cell>$Resource{areaPlan6_Row2}</t:Cell><t:Cell>$Resource{areaActual6_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan6_Row2}</t:Cell><t:Cell>$Resource{ploidyActual6_Row2}</t:Cell><t:Cell>$Resource{pricePlan6_Row2}</t:Cell><t:Cell>$Resource{priceActual6_Row2}</t:Cell><t:Cell>$Resource{amountPlan6_Row2}</t:Cell><t:Cell>$Resource{amountActual6_Row2}</t:Cell><t:Cell>$Resource{recoverPlan6_Row2}</t:Cell><t:Cell>$Resource{recoverActual6_Row2}</t:Cell><t:Cell>$Resource{areaPlan7_Row2}</t:Cell><t:Cell>$Resource{areaActual7_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan7_Row2}</t:Cell><t:Cell>$Resource{ploidyActual7_Row2}</t:Cell><t:Cell>$Resource{pricePlan7_Row2}</t:Cell><t:Cell>$Resource{priceActual7_Row2}</t:Cell><t:Cell>$Resource{amountPlan7_Row2}</t:Cell><t:Cell>$Resource{amountActual7_Row2}</t:Cell><t:Cell>$Resource{recoverPlan7_Row2}</t:Cell><t:Cell>$Resource{recoverActual7_Row2}</t:Cell><t:Cell>$Resource{areaPlan8_Row2}</t:Cell><t:Cell>$Resource{areaActual8_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan8_Row2}</t:Cell><t:Cell>$Resource{ploidyActual8_Row2}</t:Cell><t:Cell>$Resource{pricePlan8_Row2}</t:Cell><t:Cell>$Resource{priceActual8_Row2}</t:Cell><t:Cell>$Resource{amountPlan8_Row2}</t:Cell><t:Cell>$Resource{amountActual8_Row2}</t:Cell><t:Cell>$Resource{recoverPlan8_Row2}</t:Cell><t:Cell>$Resource{recoverActual8_Row2}</t:Cell><t:Cell>$Resource{areaPlan9_Row2}</t:Cell><t:Cell>$Resource{areaActual9_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan9_Row2}</t:Cell><t:Cell>$Resource{ploidyActual9_Row2}</t:Cell><t:Cell>$Resource{pricePlan9_Row2}</t:Cell><t:Cell>$Resource{priceActual9_Row2}</t:Cell><t:Cell>$Resource{amountPlan9_Row2}</t:Cell><t:Cell>$Resource{amountActual9_Row2}</t:Cell><t:Cell>$Resource{recoverPlan9_Row2}</t:Cell><t:Cell>$Resource{recoverActual9_Row2}</t:Cell><t:Cell>$Resource{areaPlan10_Row2}</t:Cell><t:Cell>$Resource{areaActual10_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan10_Row2}</t:Cell><t:Cell>$Resource{ploidyActual10_Row2}</t:Cell><t:Cell>$Resource{pricePlan10_Row2}</t:Cell><t:Cell>$Resource{priceActual10_Row2}</t:Cell><t:Cell>$Resource{amountPlan10_Row2}</t:Cell><t:Cell>$Resource{amountActual10_Row2}</t:Cell><t:Cell>$Resource{recoverPlan10_Row2}</t:Cell><t:Cell>$Resource{recoverActual10_Row2}</t:Cell><t:Cell>$Resource{areaPlan11_Row2}</t:Cell><t:Cell>$Resource{areaActual11_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan11_Row2}</t:Cell><t:Cell>$Resource{ploidyActual11_Row2}</t:Cell><t:Cell>$Resource{pricePlan11_Row2}</t:Cell><t:Cell>$Resource{priceActual11_Row2}</t:Cell><t:Cell>$Resource{amountPlan11_Row2}</t:Cell><t:Cell>$Resource{amountActual11_Row2}</t:Cell><t:Cell>$Resource{recoverPlan11_Row2}</t:Cell><t:Cell>$Resource{recoverActual11_Row2}</t:Cell><t:Cell>$Resource{areaPlan12_Row2}</t:Cell><t:Cell>$Resource{areaActual12_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan12_Row2}</t:Cell><t:Cell>$Resource{ploidyActual12_Row2}</t:Cell><t:Cell>$Resource{pricePlan12_Row2}</t:Cell><t:Cell>$Resource{priceActual12_Row2}</t:Cell><t:Cell>$Resource{amountPlan12_Row2}</t:Cell><t:Cell>$Resource{amountActual12_Row2}</t:Cell><t:Cell>$Resource{recoverPlan12_Row2}</t:Cell><t:Cell>$Resource{recoverActual12_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type_Row3}</t:Cell><t:Cell>$Resource{productType_Row3}</t:Cell><t:Cell>$Resource{areaRange_Row3}</t:Cell><t:Cell>$Resource{areaPlan_Row3}</t:Cell><t:Cell>$Resource{areaActual_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan_Row3}</t:Cell><t:Cell>$Resource{ploidyActual_Row3}</t:Cell><t:Cell>$Resource{pricePlan_Row3}</t:Cell><t:Cell>$Resource{priceActual_Row3}</t:Cell><t:Cell>$Resource{amountPlan_Row3}</t:Cell><t:Cell>$Resource{amountActual_Row3}</t:Cell><t:Cell>$Resource{recoverPlan_Row3}</t:Cell><t:Cell>$Resource{recoverActual_Row3}</t:Cell><t:Cell>$Resource{preArea_Row3}</t:Cell><t:Cell>$Resource{preCount_Row3}</t:Cell><t:Cell>$Resource{prePrice_Row3}</t:Cell><t:Cell>$Resource{preAmount_Row3}</t:Cell><t:Cell>$Resource{preRecover_Row3}</t:Cell><t:Cell>$Resource{areaPlan1_Row3}</t:Cell><t:Cell>$Resource{areaActual1_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan1_Row3}</t:Cell><t:Cell>$Resource{ploidyActual1_Row3}</t:Cell><t:Cell>$Resource{pricePlan1_Row3}</t:Cell><t:Cell>$Resource{priceActual1_Row3}</t:Cell><t:Cell>$Resource{amountPlan1_Row3}</t:Cell><t:Cell>$Resource{amountActual1_Row3}</t:Cell><t:Cell>$Resource{recoverPlan1_Row3}</t:Cell><t:Cell>$Resource{recoverActual1_Row3}</t:Cell><t:Cell>$Resource{areaPlan2_Row3}</t:Cell><t:Cell>$Resource{areaActual2_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan2_Row3}</t:Cell><t:Cell>$Resource{ploidyActual2_Row3}</t:Cell><t:Cell>$Resource{pricePlan2_Row3}</t:Cell><t:Cell>$Resource{priceActual2_Row3}</t:Cell><t:Cell>$Resource{amountPlan2_Row3}</t:Cell><t:Cell>$Resource{amountActual2_Row3}</t:Cell><t:Cell>$Resource{recoverPlan2_Row3}</t:Cell><t:Cell>$Resource{recoverActual2_Row3}</t:Cell><t:Cell>$Resource{areaPlan3_Row3}</t:Cell><t:Cell>$Resource{areaActual3_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan3_Row3}</t:Cell><t:Cell>$Resource{ploidyActual3_Row3}</t:Cell><t:Cell>$Resource{pricePlan3_Row3}</t:Cell><t:Cell>$Resource{priceActual3_Row3}</t:Cell><t:Cell>$Resource{amountPlan3_Row3}</t:Cell><t:Cell>$Resource{amountActual3_Row3}</t:Cell><t:Cell>$Resource{recoverPlan3_Row3}</t:Cell><t:Cell>$Resource{recoverActual3_Row3}</t:Cell><t:Cell>$Resource{areaPlan4_Row3}</t:Cell><t:Cell>$Resource{areaActual4_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan4_Row3}</t:Cell><t:Cell>$Resource{ploidyActual4_Row3}</t:Cell><t:Cell>$Resource{pricePlan4_Row3}</t:Cell><t:Cell>$Resource{priceActual4_Row3}</t:Cell><t:Cell>$Resource{amountPlan4_Row3}</t:Cell><t:Cell>$Resource{amountActual4_Row3}</t:Cell><t:Cell>$Resource{recoverPlan4_Row3}</t:Cell><t:Cell>$Resource{recoverActual4_Row3}</t:Cell><t:Cell>$Resource{areaPlan5_Row3}</t:Cell><t:Cell>$Resource{areaActual5_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan5_Row3}</t:Cell><t:Cell>$Resource{ploidyActual5_Row3}</t:Cell><t:Cell>$Resource{pricePlan5_Row3}</t:Cell><t:Cell>$Resource{priceActual5_Row3}</t:Cell><t:Cell>$Resource{amountPlan5_Row3}</t:Cell><t:Cell>$Resource{amountActual5_Row3}</t:Cell><t:Cell>$Resource{recoverPlan5_Row3}</t:Cell><t:Cell>$Resource{recoverActual5_Row3}</t:Cell><t:Cell>$Resource{areaPlan6_Row3}</t:Cell><t:Cell>$Resource{areaActual6_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan6_Row3}</t:Cell><t:Cell>$Resource{ploidyActual6_Row3}</t:Cell><t:Cell>$Resource{pricePlan6_Row3}</t:Cell><t:Cell>$Resource{priceActual6_Row3}</t:Cell><t:Cell>$Resource{amountPlan6_Row3}</t:Cell><t:Cell>$Resource{amountActual6_Row3}</t:Cell><t:Cell>$Resource{recoverPlan6_Row3}</t:Cell><t:Cell>$Resource{recoverActual6_Row3}</t:Cell><t:Cell>$Resource{areaPlan7_Row3}</t:Cell><t:Cell>$Resource{areaActual7_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan7_Row3}</t:Cell><t:Cell>$Resource{ploidyActual7_Row3}</t:Cell><t:Cell>$Resource{pricePlan7_Row3}</t:Cell><t:Cell>$Resource{priceActual7_Row3}</t:Cell><t:Cell>$Resource{amountPlan7_Row3}</t:Cell><t:Cell>$Resource{amountActual7_Row3}</t:Cell><t:Cell>$Resource{recoverPlan7_Row3}</t:Cell><t:Cell>$Resource{recoverActual7_Row3}</t:Cell><t:Cell>$Resource{areaPlan8_Row3}</t:Cell><t:Cell>$Resource{areaActual8_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan8_Row3}</t:Cell><t:Cell>$Resource{ploidyActual8_Row3}</t:Cell><t:Cell>$Resource{pricePlan8_Row3}</t:Cell><t:Cell>$Resource{priceActual8_Row3}</t:Cell><t:Cell>$Resource{amountPlan8_Row3}</t:Cell><t:Cell>$Resource{amountActual8_Row3}</t:Cell><t:Cell>$Resource{recoverPlan8_Row3}</t:Cell><t:Cell>$Resource{recoverActual8_Row3}</t:Cell><t:Cell>$Resource{areaPlan9_Row3}</t:Cell><t:Cell>$Resource{areaActual9_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan9_Row3}</t:Cell><t:Cell>$Resource{ploidyActual9_Row3}</t:Cell><t:Cell>$Resource{pricePlan9_Row3}</t:Cell><t:Cell>$Resource{priceActual9_Row3}</t:Cell><t:Cell>$Resource{amountPlan9_Row3}</t:Cell><t:Cell>$Resource{amountActual9_Row3}</t:Cell><t:Cell>$Resource{recoverPlan9_Row3}</t:Cell><t:Cell>$Resource{recoverActual9_Row3}</t:Cell><t:Cell>$Resource{areaPlan10_Row3}</t:Cell><t:Cell>$Resource{areaActual10_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan10_Row3}</t:Cell><t:Cell>$Resource{ploidyActual10_Row3}</t:Cell><t:Cell>$Resource{pricePlan10_Row3}</t:Cell><t:Cell>$Resource{priceActual10_Row3}</t:Cell><t:Cell>$Resource{amountPlan10_Row3}</t:Cell><t:Cell>$Resource{amountActual10_Row3}</t:Cell><t:Cell>$Resource{recoverPlan10_Row3}</t:Cell><t:Cell>$Resource{recoverActual10_Row3}</t:Cell><t:Cell>$Resource{areaPlan11_Row3}</t:Cell><t:Cell>$Resource{areaActual11_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan11_Row3}</t:Cell><t:Cell>$Resource{ploidyActual11_Row3}</t:Cell><t:Cell>$Resource{pricePlan11_Row3}</t:Cell><t:Cell>$Resource{priceActual11_Row3}</t:Cell><t:Cell>$Resource{amountPlan11_Row3}</t:Cell><t:Cell>$Resource{amountActual11_Row3}</t:Cell><t:Cell>$Resource{recoverPlan11_Row3}</t:Cell><t:Cell>$Resource{recoverActual11_Row3}</t:Cell><t:Cell>$Resource{areaPlan12_Row3}</t:Cell><t:Cell>$Resource{areaActual12_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan12_Row3}</t:Cell><t:Cell>$Resource{ploidyActual12_Row3}</t:Cell><t:Cell>$Resource{pricePlan12_Row3}</t:Cell><t:Cell>$Resource{priceActual12_Row3}</t:Cell><t:Cell>$Resource{amountPlan12_Row3}</t:Cell><t:Cell>$Resource{amountActual12_Row3}</t:Cell><t:Cell>$Resource{recoverPlan12_Row3}</t:Cell><t:Cell>$Resource{recoverActual12_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"0\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"0\" t:right=\"27\" /><t:Block t:top=\"0\" t:left=\"28\" t:bottom=\"0\" t:right=\"37\" /><t:Block t:top=\"0\" t:left=\"38\" t:bottom=\"0\" t:right=\"47\" /><t:Block t:top=\"0\" t:left=\"48\" t:bottom=\"0\" t:right=\"57\" /><t:Block t:top=\"0\" t:left=\"58\" t:bottom=\"0\" t:right=\"67\" /><t:Block t:top=\"0\" t:left=\"68\" t:bottom=\"0\" t:right=\"77\" /><t:Block t:top=\"0\" t:left=\"78\" t:bottom=\"0\" t:right=\"87\" /><t:Block t:top=\"0\" t:left=\"88\" t:bottom=\"0\" t:right=\"97\" /><t:Block t:top=\"0\" t:left=\"98\" t:bottom=\"0\" t:right=\"107\" /><t:Block t:top=\"0\" t:left=\"108\" t:bottom=\"0\" t:right=\"117\" /><t:Block t:top=\"0\" t:left=\"118\" t:bottom=\"0\" t:right=\"127\" /><t:Block t:top=\"0\" t:left=\"128\" t:bottom=\"0\" t:right=\"137\" /><t:Block t:top=\"1\" t:left=\"3\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"1\" t:left=\"5\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"1\" t:left=\"7\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"1\" t:left=\"9\" t:bottom=\"1\" t:right=\"10\" /><t:Block t:top=\"1\" t:left=\"11\" t:bottom=\"1\" t:right=\"12\" /><t:Block t:top=\"1\" t:left=\"13\" t:bottom=\"2\" t:right=\"13\" /><t:Block t:top=\"1\" t:left=\"14\" t:bottom=\"2\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"1\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"1\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"1\" t:left=\"18\" t:bottom=\"1\" t:right=\"19\" /><t:Block t:top=\"1\" t:left=\"20\" t:bottom=\"1\" t:right=\"21\" /><t:Block t:top=\"1\" t:left=\"22\" t:bottom=\"1\" t:right=\"23\" /><t:Block t:top=\"1\" t:left=\"24\" t:bottom=\"1\" t:right=\"25\" /><t:Block t:top=\"1\" t:left=\"26\" t:bottom=\"1\" t:right=\"27\" /><t:Block t:top=\"1\" t:left=\"28\" t:bottom=\"1\" t:right=\"29\" /><t:Block t:top=\"1\" t:left=\"30\" t:bottom=\"1\" t:right=\"31\" /><t:Block t:top=\"1\" t:left=\"32\" t:bottom=\"1\" t:right=\"33\" /><t:Block t:top=\"1\" t:left=\"34\" t:bottom=\"1\" t:right=\"35\" /><t:Block t:top=\"1\" t:left=\"36\" t:bottom=\"1\" t:right=\"37\" /><t:Block t:top=\"1\" t:left=\"38\" t:bottom=\"1\" t:right=\"39\" /><t:Block t:top=\"1\" t:left=\"40\" t:bottom=\"1\" t:right=\"41\" /><t:Block t:top=\"1\" t:left=\"42\" t:bottom=\"1\" t:right=\"43\" /><t:Block t:top=\"1\" t:left=\"44\" t:bottom=\"1\" t:right=\"45\" /><t:Block t:top=\"1\" t:left=\"46\" t:bottom=\"1\" t:right=\"47\" /><t:Block t:top=\"1\" t:left=\"48\" t:bottom=\"1\" t:right=\"49\" /><t:Block t:top=\"1\" t:left=\"50\" t:bottom=\"1\" t:right=\"51\" /><t:Block t:top=\"1\" t:left=\"52\" t:bottom=\"1\" t:right=\"53\" /><t:Block t:top=\"1\" t:left=\"54\" t:bottom=\"1\" t:right=\"55\" /><t:Block t:top=\"1\" t:left=\"56\" t:bottom=\"1\" t:right=\"57\" /><t:Block t:top=\"1\" t:left=\"58\" t:bottom=\"1\" t:right=\"59\" /><t:Block t:top=\"1\" t:left=\"60\" t:bottom=\"1\" t:right=\"61\" /><t:Block t:top=\"1\" t:left=\"62\" t:bottom=\"1\" t:right=\"63\" /><t:Block t:top=\"1\" t:left=\"64\" t:bottom=\"1\" t:right=\"65\" /><t:Block t:top=\"1\" t:left=\"66\" t:bottom=\"1\" t:right=\"67\" /><t:Block t:top=\"1\" t:left=\"68\" t:bottom=\"1\" t:right=\"69\" /><t:Block t:top=\"1\" t:left=\"70\" t:bottom=\"1\" t:right=\"71\" /><t:Block t:top=\"1\" t:left=\"72\" t:bottom=\"1\" t:right=\"73\" /><t:Block t:top=\"1\" t:left=\"74\" t:bottom=\"1\" t:right=\"75\" /><t:Block t:top=\"1\" t:left=\"76\" t:bottom=\"1\" t:right=\"77\" /><t:Block t:top=\"1\" t:left=\"78\" t:bottom=\"1\" t:right=\"79\" /><t:Block t:top=\"1\" t:left=\"80\" t:bottom=\"1\" t:right=\"81\" /><t:Block t:top=\"1\" t:left=\"82\" t:bottom=\"1\" t:right=\"83\" /><t:Block t:top=\"1\" t:left=\"84\" t:bottom=\"1\" t:right=\"85\" /><t:Block t:top=\"1\" t:left=\"86\" t:bottom=\"1\" t:right=\"87\" /><t:Block t:top=\"1\" t:left=\"88\" t:bottom=\"1\" t:right=\"89\" /><t:Block t:top=\"1\" t:left=\"90\" t:bottom=\"1\" t:right=\"91\" /><t:Block t:top=\"1\" t:left=\"92\" t:bottom=\"1\" t:right=\"93\" /><t:Block t:top=\"1\" t:left=\"94\" t:bottom=\"1\" t:right=\"95\" /><t:Block t:top=\"1\" t:left=\"96\" t:bottom=\"1\" t:right=\"97\" /><t:Block t:top=\"1\" t:left=\"98\" t:bottom=\"1\" t:right=\"99\" /><t:Block t:top=\"1\" t:left=\"100\" t:bottom=\"1\" t:right=\"101\" /><t:Block t:top=\"1\" t:left=\"102\" t:bottom=\"1\" t:right=\"103\" /><t:Block t:top=\"1\" t:left=\"104\" t:bottom=\"1\" t:right=\"105\" /><t:Block t:top=\"1\" t:left=\"106\" t:bottom=\"1\" t:right=\"107\" /><t:Block t:top=\"1\" t:left=\"108\" t:bottom=\"1\" t:right=\"109\" /><t:Block t:top=\"1\" t:left=\"110\" t:bottom=\"1\" t:right=\"111\" /><t:Block t:top=\"1\" t:left=\"112\" t:bottom=\"1\" t:right=\"113\" /><t:Block t:top=\"1\" t:left=\"114\" t:bottom=\"1\" t:right=\"115\" /><t:Block t:top=\"1\" t:left=\"116\" t:bottom=\"1\" t:right=\"117\" /><t:Block t:top=\"1\" t:left=\"118\" t:bottom=\"1\" t:right=\"119\" /><t:Block t:top=\"1\" t:left=\"120\" t:bottom=\"1\" t:right=\"121\" /><t:Block t:top=\"1\" t:left=\"122\" t:bottom=\"1\" t:right=\"123\" /><t:Block t:top=\"1\" t:left=\"124\" t:bottom=\"1\" t:right=\"125\" /><t:Block t:top=\"1\" t:left=\"126\" t:bottom=\"1\" t:right=\"127\" /><t:Block t:top=\"1\" t:left=\"128\" t:bottom=\"1\" t:right=\"129\" /><t:Block t:top=\"1\" t:left=\"130\" t:bottom=\"1\" t:right=\"131\" /><t:Block t:top=\"1\" t:left=\"132\" t:bottom=\"1\" t:right=\"133\" /><t:Block t:top=\"1\" t:left=\"134\" t:bottom=\"1\" t:right=\"135\" /><t:Block t:top=\"1\" t:left=\"136\" t:bottom=\"1\" t:right=\"137\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

        

        // versionType		
        this.versionType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.VersionTypeEnum").toArray());		
        this.versionType.setRequired(true);
        // txtPreArea		
        this.txtPreArea.setDataType(1);
        this.txtPreArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPreArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPreCount		
        this.txtPreCount.setDataType(1);
        this.txtPreCount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPreCount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPrePrice		
        this.txtPrePrice.setDataType(1);
        this.txtPrePrice.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPrePrice_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPreAmount		
        this.txtPreAmount.setDataType(1);
        this.txtPreAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPreAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPreRecover		
        this.txtPreRecover.setDataType(1);
        this.txtPreRecover.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPreRecover_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTextArea1
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
        contCreator.setBounds(new Rectangle(15, 577, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(15, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(15, 599, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(15, 599, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(370, 577, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(370, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(370, 599, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(370, 599, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(15, 10, 303, 19));
        this.add(contNumber, new KDLayout.Constraints(15, 10, 303, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(726, 577, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(726, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(15, 33, 303, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(15, 33, 303, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(370, 10, 303, 19));
        this.add(contName, new KDLayout.Constraints(370, 10, 303, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(726, 599, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(726, 599, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBookedDate.setBounds(new Rectangle(726, 10, 270, 19));
        this.add(contBookedDate, new KDLayout.Constraints(726, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(370, 32, 303, 19));
        this.add(contSellProject, new KDLayout.Constraints(370, 32, 303, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanYear.setBounds(new Rectangle(15, 54, 303, 19));
        this.add(contPlanYear, new KDLayout.Constraints(15, 54, 303, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanMonth.setBounds(new Rectangle(370, 54, 303, 19));
        this.add(contPlanMonth, new KDLayout.Constraints(370, 54, 303, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersoin.setBounds(new Rectangle(726, 32, 270, 19));
        this.add(contVersoin, new KDLayout.Constraints(726, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtProReference.setBounds(new Rectangle(15, 79, 659, 131));
        this.add(kdtProReference, new KDLayout.Constraints(15, 79, 659, 131, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(14, 215, 981, 359));
        this.add(kDTabbedPane1, new KDLayout.Constraints(14, 215, 981, 359, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(726, 54, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(726, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(738, 166, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(738, 166, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(737, 179, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(737, 179, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(737, 199, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(737, 199, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(737, 194, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(737, 194, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(737, 217, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(737, 217, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(724, 79, 270, 128));
        this.add(kDLabelContainer7, new KDLayout.Constraints(724, 79, 270, 128, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contBookedDate
        contBookedDate.setBoundEditor(pkBookedDate);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contPlanYear
        contPlanYear.setBoundEditor(txtPlanYear);
        //contPlanMonth
        contPlanMonth.setBoundEditor(txtPlanMonth);
        //contVersoin
        contVersoin.setBoundEditor(txtVersoin);
        //kDTabbedPane1
        kDTabbedPane1.add(kdtEntry, resHelper.getString("kdtEntry.constraints"));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(versionType);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtPreArea);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtPreCount);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtPrePrice);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtPreAmount);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtPreRecover);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(kDTextArea1);

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
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkBookedDate, "value");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("planYear", int.class, this.txtPlanYear, "value");
		dataBinder.registerBinding("planMonth", int.class, this.txtPlanMonth, "value");
		dataBinder.registerBinding("versoin", String.class, this.txtVersoin, "text");
		dataBinder.registerBinding("versionType", com.kingdee.eas.fdc.market.VersionTypeEnum.class, this.versionType, "selectedItem");
		dataBinder.registerBinding("preArea", java.math.BigDecimal.class, this.txtPreArea, "value");
		dataBinder.registerBinding("preCount", java.math.BigDecimal.class, this.txtPreCount, "value");
		dataBinder.registerBinding("prePrice", java.math.BigDecimal.class, this.txtPrePrice, "value");
		dataBinder.registerBinding("preAmount", java.math.BigDecimal.class, this.txtPreAmount, "value");
		dataBinder.registerBinding("preRecover", java.math.BigDecimal.class, this.txtPreRecover, "value");
		dataBinder.registerBinding("remark", String.class, this.kDTextArea1, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MonthPlanEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.MonthPlanInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planYear", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versoin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preRecover", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    		
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
     * output txtPlanYear_stateChanged method
     */
    protected void txtPlanYear_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtPlanMonth_stateChanged method
     */
    protected void txtPlanMonth_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtPreArea_dataChanged method
     */
    protected void txtPreArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output txtPreCount_dataChanged method
     */
    protected void txtPreCount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output txtPrePrice_dataChanged method
     */
    protected void txtPrePrice_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output txtPreAmount_dataChanged method
     */
    protected void txtPreAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output txtPreRecover_dataChanged method
     */
    protected void txtPreRecover_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sellProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sellProject.id"));
        	sic.add(new SelectorItemInfo("sellProject.number"));
        	sic.add(new SelectorItemInfo("sellProject.name"));
		}
        sic.add(new SelectorItemInfo("planYear"));
        sic.add(new SelectorItemInfo("planMonth"));
        sic.add(new SelectorItemInfo("versoin"));
        sic.add(new SelectorItemInfo("versionType"));
        sic.add(new SelectorItemInfo("preArea"));
        sic.add(new SelectorItemInfo("preCount"));
        sic.add(new SelectorItemInfo("prePrice"));
        sic.add(new SelectorItemInfo("preAmount"));
        sic.add(new SelectorItemInfo("preRecover"));
        sic.add(new SelectorItemInfo("remark"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MonthPlanEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}