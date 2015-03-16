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
public abstract class AbstractSHEPayTypeEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHEPayTypeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblvalid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblInValid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblIsLoan;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbNo;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabEntry;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kdYesAndNo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbYes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAgio;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnADLine;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kdRoomYesAndNo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbRoomYes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbRoomNo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRMLine;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDes;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpValid;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblLoanBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAfmBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblLoanSchemen;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAfmScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtLoanBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtAfmBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtLoanScheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtAfmScheme;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcLoanPre;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcAFPre;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayList;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBizList;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgio;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteLine;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionDeleteLine actionDeleteLine = null;
    protected ActionInsertLine actionInsertLine = null;
    /**
     * output class constructor
     */
    public AbstractSHEPayTypeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHEPayTypeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteLine
        this.actionDeleteLine = new ActionDeleteLine(this);
        getActionManager().registerAction("actionDeleteLine", actionDeleteLine);
         this.actionDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsertLine
        this.actionInsertLine = new ActionInsertLine(this);
        getActionManager().registerAction("actionInsertLine", actionInsertLine);
         this.actionInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblvalid = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblInValid = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblIsLoan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tabEntry = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kdYesAndNo = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.rbYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.lblAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnADLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdRoomYesAndNo = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.rbRoomYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbRoomNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnRMLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dpValid = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDatePicker2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.lblLoanBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAfmBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblLoanSchemen = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAfmScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prtLoanBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prtAfmBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prtLoanScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prtAfmScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbcLoanPre = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbcAFPre = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblBizList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsertLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contDes.setName("contDes");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.lblProject.setName("lblProject");
        this.lblvalid.setName("lblvalid");
        this.lblInValid.setName("lblInValid");
        this.lblIsLoan.setName("lblIsLoan");
        this.rbNo.setName("rbNo");
        this.kDContainer1.setName("kDContainer1");
        this.tabEntry.setName("tabEntry");
        this.rbYes.setName("rbYes");
        this.lblAgio.setName("lblAgio");
        this.btnADLine.setName("btnADLine");
        this.rbRoomYes.setName("rbRoomYes");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.rbRoomNo.setName("rbRoomNo");
        this.btnRMLine.setName("btnRMLine");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDes.setName("txtDes");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prtSellProject.setName("prtSellProject");
        this.dpValid.setName("dpValid");
        this.kDDatePicker2.setName("kDDatePicker2");
        this.lblLoanBank.setName("lblLoanBank");
        this.lblAfmBank.setName("lblAfmBank");
        this.lblLoanSchemen.setName("lblLoanSchemen");
        this.lblAfmScheme.setName("lblAfmScheme");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.prtLoanBank.setName("prtLoanBank");
        this.prtAfmBank.setName("prtAfmBank");
        this.prtLoanScheme.setName("prtLoanScheme");
        this.prtAfmScheme.setName("prtAfmScheme");
        this.cbcLoanPre.setName("cbcLoanPre");
        this.cbcAFPre.setName("cbcAFPre");
        this.tblPayList.setName("tblPayList");
        this.tblBizList.setName("tblBizList");
        this.txtAgio.setName("txtAgio");
        this.btnAddLine.setName("btnAddLine");
        this.btnInsertLine.setName("btnInsertLine");
        this.btnDeleteLine.setName("btnDeleteLine");
        // CoreUI		
        this.setPreferredSize(new Dimension(665,620));		
        this.btnSave.setVisible(false);		
        this.btnSave.setEnabled(false);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelLength(100);		
        this.contDes.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // lblProject		
        this.lblProject.setBoundLabelText(resHelper.getString("lblProject.boundLabelText"));		
        this.lblProject.setBoundLabelLength(100);		
        this.lblProject.setBoundLabelUnderline(true);
        // lblvalid		
        this.lblvalid.setBoundLabelText(resHelper.getString("lblvalid.boundLabelText"));		
        this.lblvalid.setBoundLabelLength(100);		
        this.lblvalid.setBoundLabelUnderline(true);
        // lblInValid		
        this.lblInValid.setBoundLabelText(resHelper.getString("lblInValid.boundLabelText"));		
        this.lblInValid.setBoundLabelLength(100);		
        this.lblInValid.setBoundLabelUnderline(true);
        // lblIsLoan		
        this.lblIsLoan.setBoundLabelText(resHelper.getString("lblIsLoan.boundLabelText"));		
        this.lblIsLoan.setBoundLabelUnderline(true);		
        this.lblIsLoan.setBoundLabelLength(100);
        // rbNo		
        this.rbNo.setText(resHelper.getString("rbNo.text"));
        this.rbNo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    rbNo_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.rbNo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    rbNo_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // tabEntry
        // kdYesAndNo
        this.kdYesAndNo.add(this.rbNo);
        this.kdYesAndNo.add(this.rbYes);
        // rbYes		
        this.rbYes.setText(resHelper.getString("rbYes.text"));
        this.rbYes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    rbYes_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lblAgio		
        this.lblAgio.setBoundLabelText(resHelper.getString("lblAgio.boundLabelText"));		
        this.lblAgio.setBoundLabelLength(100);		
        this.lblAgio.setBoundLabelUnderline(true);
        // btnADLine
        this.btnADLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnADLine.setText(resHelper.getString("btnADLine.text"));
        // kdRoomYesAndNo
        this.kdRoomYesAndNo.add(this.rbRoomYes);
        this.kdRoomYesAndNo.add(this.rbRoomNo);
        // rbRoomYes		
        this.rbRoomYes.setText(resHelper.getString("rbRoomYes.text"));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // rbRoomNo		
        this.rbRoomNo.setText(resHelper.getString("rbRoomNo.text"));
        // btnRMLine
        this.btnRMLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRMLine.setText(resHelper.getString("btnRMLine.text"));
        // kDScrollPane1
        // txtDes		
        this.txtDes.setMaxLength(255);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // prtSellProject		
        this.prtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        // dpValid		
        this.dpValid.setRequired(true);
        // kDDatePicker2		
        this.kDDatePicker2.setRequired(true);
        // lblLoanBank		
        this.lblLoanBank.setBoundLabelText(resHelper.getString("lblLoanBank.boundLabelText"));		
        this.lblLoanBank.setBoundLabelLength(100);		
        this.lblLoanBank.setBoundLabelUnderline(true);
        // lblAfmBank		
        this.lblAfmBank.setBoundLabelText(resHelper.getString("lblAfmBank.boundLabelText"));		
        this.lblAfmBank.setBoundLabelLength(100);		
        this.lblAfmBank.setBoundLabelUnderline(true);
        // lblLoanSchemen		
        this.lblLoanSchemen.setBoundLabelText(resHelper.getString("lblLoanSchemen.boundLabelText"));		
        this.lblLoanSchemen.setBoundLabelLength(100);		
        this.lblLoanSchemen.setBoundLabelUnderline(true);
        // lblAfmScheme		
        this.lblAfmScheme.setBoundLabelText(resHelper.getString("lblAfmScheme.boundLabelText"));		
        this.lblAfmScheme.setBoundLabelLength(100);		
        this.lblAfmScheme.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // prtLoanBank		
        this.prtLoanBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");
        // prtAfmBank		
        this.prtAfmBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");
        // prtLoanScheme		
        this.prtLoanScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.LoanForF7Query");
        // prtAfmScheme		
        this.prtAfmScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AFMForF7Query");
        // cbcLoanPre
        // cbcAFPre
        // tblPayList
		String tblPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"month\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"day\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pro\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"term\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"collection\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"signcontracttime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{bizTime}</t:Cell><t:Cell>$Resource{month}</t:Cell><t:Cell>$Resource{day}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{pro}</t:Cell><t:Cell>$Resource{term}</t:Cell><t:Cell>$Resource{jiange}</t:Cell><t:Cell>$Resource{collection}</t:Cell><t:Cell>$Resource{signcontracttime}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPayList.setFormatXml(resHelper.translateString("tblPayList",tblPayListStrXML));
        this.tblPayList.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPayList_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblBizList
		String tblBizListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bizFlow\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"month\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"day\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bizFlow}</t:Cell><t:Cell>$Resource{bizTime}</t:Cell><t:Cell>$Resource{month}</t:Cell><t:Cell>$Resource{day}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBizList.setFormatXml(resHelper.translateString("tblBizList",tblBizListStrXML));
        this.tblBizList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBizList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // txtAgio		
        this.txtAgio.setDataType(1);
        // btnAddLine
        this.btnAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddLine.setText(resHelper.getString("btnAddLine.text"));		
        this.btnAddLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnInsertLine
        this.btnInsertLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsertLine.setText(resHelper.getString("btnInsertLine.text"));		
        this.btnInsertLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_insert"));
        // btnDeleteLine
        this.btnDeleteLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteLine.setText(resHelper.getString("btnDeleteLine.text"));		
        this.btnDeleteLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
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
        this.setBounds(new Rectangle(10, 10, 665, 620));
        this.setLayout(null);
        contDes.setBounds(new Rectangle(24, 101, 619, 69));
        this.add(contDes, null);
        contNumber.setBounds(new Rectangle(24, 11, 270, 19));
        this.add(contNumber, null);
        contName.setBounds(new Rectangle(373, 11, 270, 19));
        this.add(contName, null);
        lblProject.setBounds(new Rectangle(24, 33, 270, 19));
        this.add(lblProject, null);
        lblvalid.setBounds(new Rectangle(24, 55, 270, 19));
        this.add(lblvalid, null);
        lblInValid.setBounds(new Rectangle(373, 55, 270, 19));
        this.add(lblInValid, null);
        lblIsLoan.setBounds(new Rectangle(23, 174, 97, 19));
        this.add(lblIsLoan, null);
        rbNo.setBounds(new Rectangle(224, 174, 33, 19));
        this.add(rbNo, null);
        kDContainer1.setBounds(new Rectangle(23, 204, 618, 105));
        this.add(kDContainer1, null);
        tabEntry.setBounds(new Rectangle(24, 346, 617, 262));
        this.add(tabEntry, null);
        rbYes.setBounds(new Rectangle(126, 174, 33, 19));
        this.add(rbYes, null);
        lblAgio.setBounds(new Rectangle(373, 33, 270, 19));
        this.add(lblAgio, null);
        btnADLine.setBounds(new Rectangle(475, 318, 79, 19));
        this.add(btnADLine, null);
        rbRoomYes.setBounds(new Rectangle(126, 78, 33, 19));
        this.add(rbRoomYes, null);
        kDLabelContainer3.setBounds(new Rectangle(23, 78, 97, 19));
        this.add(kDLabelContainer3, null);
        rbRoomNo.setBounds(new Rectangle(224, 78, 33, 19));
        this.add(rbRoomNo, null);
        btnRMLine.setBounds(new Rectangle(562, 318, 79, 19));
        this.add(btnRMLine, null);
        //contDes
        contDes.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDes, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //lblProject
        lblProject.setBoundEditor(prtSellProject);
        //lblvalid
        lblvalid.setBoundEditor(dpValid);
        //lblInValid
        lblInValid.setBoundEditor(kDDatePicker2);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        lblLoanBank.setBounds(new Rectangle(3, 6, 270, 19));
        kDContainer1.getContentPane().add(lblLoanBank, null);
        lblAfmBank.setBounds(new Rectangle(336, 6, 270, 19));
        kDContainer1.getContentPane().add(lblAfmBank, null);
        lblLoanSchemen.setBounds(new Rectangle(3, 28, 270, 19));
        kDContainer1.getContentPane().add(lblLoanSchemen, null);
        lblAfmScheme.setBounds(new Rectangle(336, 28, 270, 19));
        kDContainer1.getContentPane().add(lblAfmScheme, null);
        kDLabelContainer1.setBounds(new Rectangle(3, 50, 270, 19));
        kDContainer1.getContentPane().add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(336, 50, 270, 19));
        kDContainer1.getContentPane().add(kDLabelContainer2, null);
        //lblLoanBank
        lblLoanBank.setBoundEditor(prtLoanBank);
        //lblAfmBank
        lblAfmBank.setBoundEditor(prtAfmBank);
        //lblLoanSchemen
        lblLoanSchemen.setBoundEditor(prtLoanScheme);
        //lblAfmScheme
        lblAfmScheme.setBoundEditor(prtAfmScheme);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(cbcLoanPre);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cbcAFPre);
        //tabEntry
        tabEntry.add(tblPayList, resHelper.getString("tblPayList.constraints"));
        tabEntry.add(tblBizList, resHelper.getString("tblBizList.constraints"));
        //lblAgio
        lblAgio.setBoundEditor(txtAgio);

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
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnDeleteLine);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeEditUIHandler";
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output rbNo_itemStateChanged method
     */
    protected void rbNo_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output rbNo_stateChanged method
     */
    protected void rbNo_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output rbYes_itemStateChanged method
     */
    protected void rbYes_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblPayList_tableClicked method
     */
    protected void tblPayList_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblPayList_editStopped method
     */
    protected void tblPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblBizList_editStopped method
     */
    protected void tblBizList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
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
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteLine_actionPerformed method
     */
    public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInsertLine_actionPerformed method
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteLine() {
    	return false;
    }
	public RequestContext prepareActionInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsertLine() {
    	return false;
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
            innerActionPerformed("eas", AbstractSHEPayTypeEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteLine class
     */     
    protected class ActionDeleteLine extends ItemAction {     
    
        public ActionDeleteLine()
        {
            this(null);
        }

        public ActionDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEPayTypeEditUI.this, "ActionDeleteLine", "actionDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionInsertLine class
     */     
    protected class ActionInsertLine extends ItemAction {     
    
        public ActionInsertLine()
        {
            this(null);
        }

        public ActionInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEPayTypeEditUI.this, "ActionInsertLine", "actionInsertLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SHEPayTypeEditUI");
    }




}