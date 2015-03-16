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
public abstract class AbstractMarketManageEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketManageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contname;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmarketPlan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contorgName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtorgName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contschemeType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox schemeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbelongSystem;
    protected com.kingdee.bos.ctrl.swing.KDComboBox belongSystem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmovemntTheme;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmovemntTheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmarkettype;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmarkettype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanTotalMoney;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtplanTotalMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfactTotalMoney;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtfactTotalMoney;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl36;
    protected com.kingdee.bos.ctrl.swing.KDPanel planProcess;
    protected com.kingdee.bos.ctrl.swing.KDPanel planCustomer;
    protected com.kingdee.bos.ctrl.swing.KDPanel planCharge;
    protected com.kingdee.bos.ctrl.swing.KDContainer KDEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDPanel planEffect;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEffect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel planMedia;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFactPayment;
    protected com.kingdee.bos.ctrl.swing.KDContainer KDMedia;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtMedia;
    protected com.kingdee.bos.ctrl.swing.KDContainer KDCharge;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCharge;
    protected com.kingdee.bos.ctrl.swing.KDContainer KDCustomer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCustomer;
    protected com.kingdee.eas.fdc.market.MarketManageInfo editData = null;
    protected ActionAddCustomer actionAddCustomer = null;
    protected ActionDelCustomer actionDelCustomer = null;
    protected ActionAddMedia actionAddMedia = null;
    protected ActionDelMedia actionDelMedia = null;
    protected ActionAddCharge actionAddCharge = null;
    protected ActionDelCharge actionDelCharge = null;
    protected ActionPayment actionPayment = null;
    /**
     * output class constructor
     */
    public AbstractMarketManageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketManageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddCustomer
        this.actionAddCustomer = new ActionAddCustomer(this);
        getActionManager().registerAction("actionAddCustomer", actionAddCustomer);
         this.actionAddCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelCustomer
        this.actionDelCustomer = new ActionDelCustomer(this);
        getActionManager().registerAction("actionDelCustomer", actionDelCustomer);
         this.actionDelCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddMedia
        this.actionAddMedia = new ActionAddMedia(this);
        getActionManager().registerAction("actionAddMedia", actionAddMedia);
         this.actionAddMedia.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelMedia
        this.actionDelMedia = new ActionDelMedia(this);
        getActionManager().registerAction("actionDelMedia", actionDelMedia);
         this.actionDelMedia.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddCharge
        this.actionAddCharge = new ActionAddCharge(this);
        getActionManager().registerAction("actionAddCharge", actionAddCharge);
         this.actionAddCharge.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelCharge
        this.actionDelCharge = new ActionDelCharge(this);
        getActionManager().registerAction("actionDelCharge", actionDelCharge);
         this.actionDelCharge.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPayment
        this.actionPayment = new ActionPayment(this);
        getActionManager().registerAction("actionPayment", actionPayment);
         this.actionPayment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtmarketPlan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contorgName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtorgName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtsellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contschemeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.schemeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contbeginDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkbeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contbelongSystem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.belongSystem = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contmovemntTheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtmovemntTheme = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contmarkettype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtmarkettype = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contplanTotalMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtplanTotalMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contfactTotalMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtfactTotalMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.paneBIZLayerControl36 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.planProcess = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.planCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.planCharge = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.KDEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.planEffect = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtEffect = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.planMedia = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFactPayment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.KDMedia = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtMedia = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.KDCharge = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtCharge = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.KDCustomer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.pkBizDate.setName("pkBizDate");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtCompany.setName("txtCompany");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.dateLastUpdateTime.setName("dateLastUpdateTime");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.dateCreateTime.setName("dateCreateTime");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.bizPromptLastUpdateUser.setName("bizPromptLastUpdateUser");
        this.contname.setName("contname");
        this.txtname.setName("txtname");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.prmtmarketPlan.setName("prmtmarketPlan");
        this.contorgName.setName("contorgName");
        this.txtorgName.setName("txtorgName");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.prmtsellProject.setName("prmtsellProject");
        this.contschemeType.setName("contschemeType");
        this.schemeType.setName("schemeType");
        this.contbeginDate.setName("contbeginDate");
        this.pkbeginDate.setName("pkbeginDate");
        this.contendDate.setName("contendDate");
        this.pkendDate.setName("pkendDate");
        this.contbelongSystem.setName("contbelongSystem");
        this.belongSystem.setName("belongSystem");
        this.contmovemntTheme.setName("contmovemntTheme");
        this.txtmovemntTheme.setName("txtmovemntTheme");
        this.contmarkettype.setName("contmarkettype");
        this.prmtmarkettype.setName("prmtmarkettype");
        this.contplanTotalMoney.setName("contplanTotalMoney");
        this.txtplanTotalMoney.setName("txtplanTotalMoney");
        this.contfactTotalMoney.setName("contfactTotalMoney");
        this.txtfactTotalMoney.setName("txtfactTotalMoney");
        this.paneBIZLayerControl36.setName("paneBIZLayerControl36");
        this.planProcess.setName("planProcess");
        this.planCustomer.setName("planCustomer");
        this.planCharge.setName("planCharge");
        this.KDEntrys.setName("KDEntrys");
        this.kdtEntrys.setName("kdtEntrys");
        this.planEffect.setName("planEffect");
        this.kdtEffect.setName("kdtEffect");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.txtContractNumber.setName("txtContractNumber");
        this.txtDescription.setName("txtDescription");
        this.planMedia.setName("planMedia");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.txtFactPayment.setName("txtFactPayment");
        this.KDMedia.setName("KDMedia");
        this.kdtMedia.setName("kdtMedia");
        this.KDCharge.setName("KDCharge");
        this.kdtCharge.setName("kdtCharge");
        this.KDCustomer.setName("KDCustomer");
        this.kdtCustomer.setName("kdtCustomer");
        // CoreUI
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(false);		
        ((com.kingdee.bos.ctrl.swing.BasicFormattedTextField)this.pkBizDate.getEditor().getEditorComponent()).setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.pkBizDate.setRequired(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(false);		
        this.kDLabelContainer2.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtCompany		
        this.txtCompany.setText(resHelper.getString("txtCompany.text"));		
        this.txtCompany.setEnabled(false);		
        this.txtCompany.setVisible(false);		
        this.txtCompany.setHorizontalAlignment(2);		
        this.txtCompany.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.txtCompany.setRequired(false);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setVisible(false);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setEnabled(false);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);
        // dateLastUpdateTime		
        this.dateLastUpdateTime.setEnabled(false);		
        this.dateLastUpdateTime.setVisible(false);		
        this.dateLastUpdateTime.setRequired(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);		
        this.dateCreateTime.setRequired(false);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.setVisible(true);
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setVisible(true);		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setCommitFormat("$number$");		
        this.bizPromptAuditor.setRequired(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);		
        this.kDLabelContainer6.setVisible(true);		
        this.kDLabelContainer6.getBoundLabel().setForeground(new java.awt.Color(0,0,0));		
        this.kDLabelContainer6.setToolTipText(resHelper.getString("kDLabelContainer6.toolTipText"));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelAlignment(7);		
        this.kDLabelContainer7.setVisible(true);
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setRequired(false);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setVisible(false);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelAlignment(7);
        // bizPromptLastUpdateUser		
        this.bizPromptLastUpdateUser.setEnabled(false);		
        this.bizPromptLastUpdateUser.setVisible(false);		
        this.bizPromptLastUpdateUser.setEditable(true);		
        this.bizPromptLastUpdateUser.setDisplayFormat("$name$");		
        this.bizPromptLastUpdateUser.setEditFormat("$number$");		
        this.bizPromptLastUpdateUser.setCommitFormat("$number$");		
        this.bizPromptLastUpdateUser.setRequired(false);
        // contname		
        this.contname.setBoundLabelText(resHelper.getString("contname.boundLabelText"));		
        this.contname.setBoundLabelLength(100);		
        this.contname.setBoundLabelUnderline(true);		
        this.contname.setVisible(true);		
        this.contname.setBoundLabelAlignment(7);		
        this.contname.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtname		
        this.txtname.setVisible(true);		
        this.txtname.setHorizontalAlignment(2);		
        this.txtname.setMaxLength(100);		
        this.txtname.setRequired(false);		
        this.txtname.setEnabled(true);		
        this.txtname.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.getBoundLabel().setForeground(new java.awt.Color(0,0,0));		
        this.kDLabelContainer9.setVisible(true);
        // prmtmarketPlan		
        this.prmtmarketPlan.setQueryInfo("com.kingdee.eas.fdc.market.app.f7MovementPlanQuery");		
        this.prmtmarketPlan.setDisplayFormat("$name$");		
        this.prmtmarketPlan.setEditFormat("$number$");		
        this.prmtmarketPlan.setCommitFormat("$number$");		
        this.prmtmarketPlan.setRequired(false);		
        this.prmtmarketPlan.setForeground(new java.awt.Color(0,0,0));		
        this.prmtmarketPlan.setEnabled(true);		
        this.prmtmarketPlan.setVisible(true);
        this.prmtmarketPlan.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtmarketPlan_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contorgName		
        this.contorgName.setBoundLabelText(resHelper.getString("contorgName.boundLabelText"));		
        this.contorgName.setBoundLabelLength(100);		
        this.contorgName.setBoundLabelUnderline(true);		
        this.contorgName.setVisible(true);		
        this.contorgName.setBoundLabelAlignment(7);		
        this.contorgName.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtorgName		
        this.txtorgName.setVisible(true);		
        this.txtorgName.setHorizontalAlignment(2);		
        this.txtorgName.setMaxLength(100);		
        this.txtorgName.setRequired(false);		
        this.txtorgName.setEnabled(false);		
        this.txtorgName.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setVisible(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // prmtsellProject		
        this.prmtsellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtsellProject.setDisplayFormat("$name$");		
        this.prmtsellProject.setEditFormat("$number$");		
        this.prmtsellProject.setCommitFormat("$number$");		
        this.prmtsellProject.setVisible(true);		
        this.prmtsellProject.setRequired(false);		
        this.prmtsellProject.setForeground(new java.awt.Color(0,0,0));		
        this.prmtsellProject.setEnabled(false);
        // contschemeType		
        this.contschemeType.setBoundLabelText(resHelper.getString("contschemeType.boundLabelText"));		
        this.contschemeType.setBoundLabelLength(100);		
        this.contschemeType.setBoundLabelUnderline(true);		
        this.contschemeType.setVisible(true);		
        this.contschemeType.setBoundLabelAlignment(7);		
        this.contschemeType.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // schemeType		
        this.schemeType.setVisible(true);		
        this.schemeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.SchemeTypeEnum").toArray());		
        this.schemeType.setRequired(false);		
        this.schemeType.setForeground(new java.awt.Color(0,0,0));		
        this.schemeType.setEnabled(true);
        // contbeginDate		
        this.contbeginDate.setBoundLabelText(resHelper.getString("contbeginDate.boundLabelText"));		
        this.contbeginDate.setBoundLabelLength(100);		
        this.contbeginDate.setBoundLabelUnderline(true);		
        this.contbeginDate.setVisible(true);		
        this.contbeginDate.setBoundLabelAlignment(7);		
        this.contbeginDate.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // pkbeginDate		
        this.pkbeginDate.setVisible(true);		
        this.pkbeginDate.setRequired(false);		
        this.pkbeginDate.setEnabled(true);		
        ((com.kingdee.bos.ctrl.swing.BasicFormattedTextField)this.pkbeginDate.getEditor().getEditorComponent()).setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contendDate		
        this.contendDate.setBoundLabelText(resHelper.getString("contendDate.boundLabelText"));		
        this.contendDate.setBoundLabelLength(100);		
        this.contendDate.setBoundLabelUnderline(true);		
        this.contendDate.setVisible(true);		
        this.contendDate.setBoundLabelAlignment(7);		
        this.contendDate.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // pkendDate		
        this.pkendDate.setVisible(true);		
        this.pkendDate.setRequired(false);		
        this.pkendDate.setEnabled(true);		
        ((com.kingdee.bos.ctrl.swing.BasicFormattedTextField)this.pkendDate.getEditor().getEditorComponent()).setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contbelongSystem		
        this.contbelongSystem.setBoundLabelText(resHelper.getString("contbelongSystem.boundLabelText"));		
        this.contbelongSystem.setBoundLabelLength(100);		
        this.contbelongSystem.setBoundLabelUnderline(true);		
        this.contbelongSystem.setVisible(true);		
        this.contbelongSystem.setBoundLabelAlignment(7);		
        this.contbelongSystem.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // belongSystem		
        this.belongSystem.setVisible(true);		
        this.belongSystem.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());		
        this.belongSystem.setRequired(false);		
        this.belongSystem.setForeground(new java.awt.Color(0,0,0));		
        this.belongSystem.setEnabled(true);
        // contmovemntTheme		
        this.contmovemntTheme.setBoundLabelText(resHelper.getString("contmovemntTheme.boundLabelText"));		
        this.contmovemntTheme.setBoundLabelLength(100);		
        this.contmovemntTheme.setBoundLabelUnderline(true);		
        this.contmovemntTheme.setVisible(true);		
        this.contmovemntTheme.setBoundLabelAlignment(7);		
        this.contmovemntTheme.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtmovemntTheme		
        this.txtmovemntTheme.setVisible(true);		
        this.txtmovemntTheme.setHorizontalAlignment(2);		
        this.txtmovemntTheme.setMaxLength(100);		
        this.txtmovemntTheme.setRequired(false);		
        this.txtmovemntTheme.setEnabled(true);		
        this.txtmovemntTheme.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contmarkettype		
        this.contmarkettype.setBoundLabelText(resHelper.getString("contmarkettype.boundLabelText"));		
        this.contmarkettype.setBoundLabelLength(100);		
        this.contmarkettype.setBoundLabelUnderline(true);		
        this.contmarkettype.setVisible(true);		
        this.contmarkettype.setBoundLabelAlignment(7);		
        this.contmarkettype.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // prmtmarkettype		
        this.prmtmarkettype.setQueryInfo("com.kingdee.eas.fdc.market.app.MarketTypeQuery");		
        this.prmtmarkettype.setVisible(true);		
        this.prmtmarkettype.setEditable(true);		
        this.prmtmarkettype.setDisplayFormat("$name$");		
        this.prmtmarkettype.setEditFormat("$number$");		
        this.prmtmarkettype.setCommitFormat("$number$");		
        this.prmtmarkettype.setRequired(false);		
        this.prmtmarkettype.setEnabled(true);		
        this.prmtmarkettype.setForeground(new java.awt.Color(0,0,0));
        		prmtmarkettype.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.market.client.MarketTypeListUI prmtmarkettype_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtmarkettype_F7ListUI == null) {
					try {
						prmtmarkettype_F7ListUI = new com.kingdee.eas.fdc.market.client.MarketTypeListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtmarkettype_F7ListUI));
					prmtmarkettype_F7ListUI.setF7Use(true,ctx);
					prmtmarkettype.setSelector(prmtmarkettype_F7ListUI);
				}
			}
		});
					
        // contplanTotalMoney		
        this.contplanTotalMoney.setBoundLabelText(resHelper.getString("contplanTotalMoney.boundLabelText"));		
        this.contplanTotalMoney.setBoundLabelLength(100);		
        this.contplanTotalMoney.setBoundLabelUnderline(true);		
        this.contplanTotalMoney.setVisible(true);		
        this.contplanTotalMoney.setBoundLabelAlignment(7);		
        this.contplanTotalMoney.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtplanTotalMoney		
        this.txtplanTotalMoney.setVisible(true);		
        this.txtplanTotalMoney.setDataType(1);		
        this.txtplanTotalMoney.setSupportedEmpty(true);		
        this.txtplanTotalMoney.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtplanTotalMoney.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtplanTotalMoney.setPrecision(2);		
        this.txtplanTotalMoney.setRequired(false);		
        this.txtplanTotalMoney.setEnabled(false);		
        this.txtplanTotalMoney.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contfactTotalMoney		
        this.contfactTotalMoney.setBoundLabelText(resHelper.getString("contfactTotalMoney.boundLabelText"));		
        this.contfactTotalMoney.setBoundLabelLength(100);		
        this.contfactTotalMoney.setBoundLabelUnderline(true);		
        this.contfactTotalMoney.setVisible(true);		
        this.contfactTotalMoney.setBoundLabelAlignment(7);		
        this.contfactTotalMoney.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtfactTotalMoney		
        this.txtfactTotalMoney.setVisible(true);		
        this.txtfactTotalMoney.setDataType(1);		
        this.txtfactTotalMoney.setSupportedEmpty(true);		
        this.txtfactTotalMoney.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtfactTotalMoney.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtfactTotalMoney.setPrecision(2);		
        this.txtfactTotalMoney.setRequired(false);		
        this.txtfactTotalMoney.setEnabled(false);		
        this.txtfactTotalMoney.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // paneBIZLayerControl36		
        this.paneBIZLayerControl36.setVisible(true);
        // planProcess		
        this.planProcess.setVisible(true);
        // planCustomer		
        this.planCustomer.setVisible(true);
        // planCharge		
        this.planCharge.setVisible(true);
        // KDEntrys
        // kdtEntrys		
        this.kdtEntrys.setFormatXml(resHelper.getString("kdtEntrys.formatXml"));
        kdtEntrys.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntrys_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kdtEntrys.checkParsed();
        // planEffect		
        this.planEffect.setVisible(true);
        // kdtEffect		
        this.kdtEffect.setFormatXml(resHelper.getString("kdtEffect.formatXml"));

        

        this.kdtEffect.checkParsed();
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setToolTipText(resHelper.getString("kDLabelContainer11.toolTipText"));		
        this.kDLabelContainer11.setBoundLabelAlignment(7);		
        this.kDLabelContainer11.setVisible(true);		
        this.kDLabelContainer11.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtContractNumber		
        this.txtContractNumber.setVisible(true);		
        this.txtContractNumber.setEnabled(true);		
        this.txtContractNumber.setHorizontalAlignment(2);		
        this.txtContractNumber.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.txtContractNumber.setRequired(false);
        // txtDescription		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));		
        this.txtDescription.setRequired(false);		
        this.txtDescription.setMaxLength(255);
        // planMedia		
        this.planMedia.setVisible(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // txtFactPayment		
        this.txtFactPayment.setEnabled(false);		
        this.txtFactPayment.setDataType(1);		
        this.txtFactPayment.setPrecision(2);		
        this.txtFactPayment.setSupportedEmpty(true);
        // KDMedia
        // kdtMedia		
        this.kdtMedia.setFormatXml(resHelper.getString("kdtMedia.formatXml"));
        this.kdtMedia.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtMedia_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kdtMedia.checkParsed();
        // KDCharge
        // kdtCharge		
        this.kdtCharge.setFormatXml(resHelper.getString("kdtCharge.formatXml"));

        

        this.kdtCharge.checkParsed();
        // KDCustomer
        // kdtCustomer		
        this.kdtCustomer.setFormatXml(resHelper.getString("kdtCustomer.formatXml"));

        

        this.kdtCustomer.checkParsed();
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtname,prmtmarketPlan,txtorgName,prmtsellProject,schemeType,pkbeginDate,pkendDate,belongSystem,txtmovemntTheme,prmtmarkettype,txtContractNumber,txtplanTotalMoney,txtfactTotalMoney,txtFactPayment,txtDescription,kdtCharge,kdtCustomer,kdtEntrys,kdtEffect,kdtMedia,bizPromptCreator,bizPromptAuditor,dateCreateTime,dateLastUpdateTime,pkBizDate,txtCompany,bizPromptLastUpdateUser}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(10, 560, 225, 19));
        this.add(contBizDate, new KDLayout.Constraints(10, 560, 225, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(370, 556, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(370, 556, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(730, 549, 224, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(730, 549, 224, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(730, 523, 224, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(730, 523, 224, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(370, 527, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(370, 527, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(10, 135, 990, 48));
        this.add(kDLabelContainer6, new KDLayout.Constraints(10, 135, 990, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(10, 527, 225, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 527, 225, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(10, 560, 225, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 560, 225, 19, 0));
        contname.setBounds(new Rectangle(370, 10, 270, 19));
        this.add(contname, new KDLayout.Constraints(370, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(730, 10, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(730, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contorgName.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contorgName, new KDLayout.Constraints(10, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(370, 35, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(370, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contschemeType.setBounds(new Rectangle(730, 35, 270, 19));
        this.add(contschemeType, new KDLayout.Constraints(730, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbeginDate.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(contbeginDate, new KDLayout.Constraints(10, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contendDate.setBounds(new Rectangle(370, 60, 270, 19));
        this.add(contendDate, new KDLayout.Constraints(370, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbelongSystem.setBounds(new Rectangle(730, 60, 270, 19));
        this.add(contbelongSystem, new KDLayout.Constraints(730, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contmovemntTheme.setBounds(new Rectangle(10, 85, 270, 19));
        this.add(contmovemntTheme, new KDLayout.Constraints(10, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmarkettype.setBounds(new Rectangle(370, 85, 270, 19));
        this.add(contmarkettype, new KDLayout.Constraints(370, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contplanTotalMoney.setBounds(new Rectangle(10, 110, 270, 19));
        this.add(contplanTotalMoney, new KDLayout.Constraints(10, 110, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contfactTotalMoney.setBounds(new Rectangle(370, 110, 270, 19));
        this.add(contfactTotalMoney, new KDLayout.Constraints(370, 110, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        paneBIZLayerControl36.setBounds(new Rectangle(10, 200, 990, 310));
        this.add(paneBIZLayerControl36, new KDLayout.Constraints(10, 200, 990, 310, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(730, 85, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(730, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(730, 110, 270, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(730, 110, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCompany);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(dateLastUpdateTime);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dateCreateTime);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDescription);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptCreator);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptLastUpdateUser);
        //contname
        contname.setBoundEditor(txtname);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(prmtmarketPlan);
        //contorgName
        contorgName.setBoundEditor(txtorgName);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(prmtsellProject);
        //contschemeType
        contschemeType.setBoundEditor(schemeType);
        //contbeginDate
        contbeginDate.setBoundEditor(pkbeginDate);
        //contendDate
        contendDate.setBoundEditor(pkendDate);
        //contbelongSystem
        contbelongSystem.setBoundEditor(belongSystem);
        //contmovemntTheme
        contmovemntTheme.setBoundEditor(txtmovemntTheme);
        //contmarkettype
        contmarkettype.setBoundEditor(prmtmarkettype);
        //contplanTotalMoney
        contplanTotalMoney.setBoundEditor(txtplanTotalMoney);
        //contfactTotalMoney
        contfactTotalMoney.setBoundEditor(txtfactTotalMoney);
        //paneBIZLayerControl36
        paneBIZLayerControl36.add(planProcess, resHelper.getString("planProcess.constraints"));
        paneBIZLayerControl36.add(planCustomer, resHelper.getString("planCustomer.constraints"));
        paneBIZLayerControl36.add(planCharge, resHelper.getString("planCharge.constraints"));
        paneBIZLayerControl36.add(planEffect, resHelper.getString("planEffect.constraints"));
        paneBIZLayerControl36.add(planMedia, resHelper.getString("planMedia.constraints"));
        //planProcess
planProcess.setLayout(new BorderLayout(0, 0));        planProcess.add(KDCharge, BorderLayout.CENTER);
        //KDCharge
KDCharge.getContentPane().setLayout(new BorderLayout(0, 0));        KDCharge.getContentPane().add(kdtCharge, BorderLayout.CENTER);
        //planCustomer
planCustomer.setLayout(new BorderLayout(0, 0));        planCustomer.add(KDCustomer, BorderLayout.CENTER);
        //KDCustomer
KDCustomer.getContentPane().setLayout(new BorderLayout(0, 0));        KDCustomer.getContentPane().add(kdtCustomer, BorderLayout.CENTER);
        //planCharge
planCharge.setLayout(new BorderLayout(0, 0));        planCharge.add(KDEntrys, BorderLayout.CENTER);
        //KDEntrys
KDEntrys.getContentPane().setLayout(new BorderLayout(0, 0));        KDEntrys.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
        //planEffect
planEffect.setLayout(new BorderLayout(0, 0));        planEffect.add(kdtEffect, BorderLayout.CENTER);
        //planMedia
planMedia.setLayout(new BorderLayout(0, 0));        planMedia.add(KDMedia, BorderLayout.CENTER);
        //KDMedia
KDMedia.getContentPane().setLayout(new BorderLayout(0, 0));        KDMedia.getContentPane().add(kdtMedia, BorderLayout.CENTER);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtContractNumber);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtFactPayment);

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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("company.name", String.class, this.txtCompany, "text");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.dateLastUpdateTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptLastUpdateUser, "data");
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("marketPlan", com.kingdee.eas.fdc.market.MovementPlanInfo.class, this.prmtmarketPlan, "data");
		dataBinder.registerBinding("orgName", String.class, this.txtorgName, "text");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtsellProject, "data");
		dataBinder.registerBinding("schemeType", com.kingdee.eas.fdc.market.SchemeTypeEnum.class, this.schemeType, "selectedItem");
		dataBinder.registerBinding("beginDate", java.util.Date.class, this.pkbeginDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkendDate, "value");
		dataBinder.registerBinding("belongSystem", com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.class, this.belongSystem, "selectedItem");
		dataBinder.registerBinding("movemntTheme", String.class, this.txtmovemntTheme, "text");
		dataBinder.registerBinding("markettype", com.kingdee.eas.fdc.market.MarketTypeInfo.class, this.prmtmarkettype, "data");
		dataBinder.registerBinding("planTotalMoney", java.math.BigDecimal.class, this.txtplanTotalMoney, "value");
		dataBinder.registerBinding("factTotalMoney", java.math.BigDecimal.class, this.txtfactTotalMoney, "value");
		dataBinder.registerBinding("contractNumber", String.class, this.txtContractNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("factPayment", java.math.BigDecimal.class, this.txtFactPayment, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MarketManageEditUIHandler";
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
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.MarketManageInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
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
    protected void setAutoNumberByOrg(String orgType) {
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
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
        		setAutoNumberByOrg("Sale");
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketPlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("schemeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beginDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("belongSystem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("movemntTheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("markettype", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planTotalMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factTotalMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factPayment", ValidateHelper.ON_SAVE);    		
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
     * output prmtmarketPlan_dataChanged method
     */
    protected void prmtmarketPlan_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtMedia_editStopped method
     */
    protected void kdtMedia_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }


    /**
     * output kdtEntrys_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntrys_Changed(int rowIndex,int colIndex) throws Exception
    {
            kdtEntrys.getCell(rowIndex,"costName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntrys.getCell(rowIndex,"costNumber").getValue(),"name")));


    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("marketPlan.*"));
        sic.add(new SelectorItemInfo("orgName"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("schemeType"));
        sic.add(new SelectorItemInfo("beginDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("belongSystem"));
        sic.add(new SelectorItemInfo("movemntTheme"));
        sic.add(new SelectorItemInfo("markettype.*"));
        sic.add(new SelectorItemInfo("planTotalMoney"));
        sic.add(new SelectorItemInfo("factTotalMoney"));
        sic.add(new SelectorItemInfo("contractNumber"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("factPayment"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionAddCustomer_actionPerformed method
     */
    public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelCustomer_actionPerformed method
     */
    public void actionDelCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddMedia_actionPerformed method
     */
    public void actionAddMedia_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelMedia_actionPerformed method
     */
    public void actionDelMedia_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddCharge_actionPerformed method
     */
    public void actionAddCharge_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelCharge_actionPerformed method
     */
    public void actionDelCharge_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPayment_actionPerformed method
     */
    public void actionPayment_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCustomer() {
    	return false;
    }
	public RequestContext prepareActionDelCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelCustomer() {
    	return false;
    }
	public RequestContext prepareActionAddMedia(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddMedia() {
    	return false;
    }
	public RequestContext prepareActionDelMedia(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelMedia() {
    	return false;
    }
	public RequestContext prepareActionAddCharge(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCharge() {
    	return false;
    }
	public RequestContext prepareActionDelCharge(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelCharge() {
    	return false;
    }
	public RequestContext prepareActionPayment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPayment() {
    	return false;
    }

    /**
     * output ActionAddCustomer class
     */
    protected class ActionAddCustomer extends ItemAction
    {
        public ActionAddCustomer()
        {
            this(null);
        }

        public ActionAddCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionAddCustomer", "actionAddCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionDelCustomer class
     */
    protected class ActionDelCustomer extends ItemAction
    {
        public ActionDelCustomer()
        {
            this(null);
        }

        public ActionDelCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionDelCustomer", "actionDelCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionAddMedia class
     */
    protected class ActionAddMedia extends ItemAction
    {
        public ActionAddMedia()
        {
            this(null);
        }

        public ActionAddMedia(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddMedia.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddMedia.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddMedia.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionAddMedia", "actionAddMedia_actionPerformed", e);
        }
    }

    /**
     * output ActionDelMedia class
     */
    protected class ActionDelMedia extends ItemAction
    {
        public ActionDelMedia()
        {
            this(null);
        }

        public ActionDelMedia(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelMedia.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelMedia.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelMedia.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionDelMedia", "actionDelMedia_actionPerformed", e);
        }
    }

    /**
     * output ActionAddCharge class
     */
    protected class ActionAddCharge extends ItemAction
    {
        public ActionAddCharge()
        {
            this(null);
        }

        public ActionAddCharge(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCharge.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCharge.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCharge.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionAddCharge", "actionAddCharge_actionPerformed", e);
        }
    }

    /**
     * output ActionDelCharge class
     */
    protected class ActionDelCharge extends ItemAction
    {
        public ActionDelCharge()
        {
            this(null);
        }

        public ActionDelCharge(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelCharge.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCharge.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCharge.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionDelCharge", "actionDelCharge_actionPerformed", e);
        }
    }

    /**
     * output ActionPayment class
     */
    protected class ActionPayment extends ItemAction
    {
        public ActionPayment()
        {
            this(null);
        }

        public ActionPayment(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPayment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageEditUI.this, "ActionPayment", "actionPayment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketManageEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.MarketManageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.MarketManageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.MarketManageInfo objectValue = new com.kingdee.eas.fdc.market.MarketManageInfo();
        objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/market/MarketManage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.market.app.MarketManageQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtCharge;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("schemeType","0");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}