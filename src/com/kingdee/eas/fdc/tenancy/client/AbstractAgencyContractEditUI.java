/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractAgencyContractEditUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAgencyContractEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHandler;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Handler;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Auditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Agency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSigner;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Signer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActingContent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialClause;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommisionStandard;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCommisionStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValue;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtValue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppPayDateType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAppPayDateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayOffsetDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartUsingDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartUsingDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStopUsingDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStopUsingDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStopper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Stopper;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtActingContent;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSpecialClause;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinPayOffsetDays;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.eas.fdc.tenancy.AgencyContractInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractAgencyContractEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAgencyContractEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contHandler = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Handler = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Auditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAgency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Agency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSigner = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Signer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contActingContent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialClause = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommisionStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCommisionStandard = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contAppPayDateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboAppPayDateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contPayOffsetDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartUsingDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkStartUsingDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contStopUsingDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkStopUsingDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contStopper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Stopper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtActingContent = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtSpecialClause = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.spinPayOffsetDays = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCreator.setName("contCreator");
        this.f7Creator.setName("f7Creator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contHandler.setName("contHandler");
        this.f7Handler.setName("f7Handler");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.f7Auditor.setName("f7Auditor");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contAuditTime.setName("contAuditTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.contSellProject.setName("contSellProject");
        this.f7SellProject.setName("f7SellProject");
        this.contAgency.setName("contAgency");
        this.f7Agency.setName("f7Agency");
        this.contSigner.setName("contSigner");
        this.f7Signer.setName("f7Signer");
        this.contActingContent.setName("contActingContent");
        this.contSpecialClause.setName("contSpecialClause");
        this.contCommisionStandard.setName("contCommisionStandard");
        this.comboCommisionStandard.setName("comboCommisionStandard");
        this.contValue.setName("contValue");
        this.txtValue.setName("txtValue");
        this.contAppPayDateType.setName("contAppPayDateType");
        this.comboAppPayDateType.setName("comboAppPayDateType");
        this.contPayOffsetDays.setName("contPayOffsetDays");
        this.contStartUsingDate.setName("contStartUsingDate");
        this.pkStartUsingDate.setName("pkStartUsingDate");
        this.contStopUsingDate.setName("contStopUsingDate");
        this.pkStopUsingDate.setName("pkStopUsingDate");
        this.contStopper.setName("contStopper");
        this.f7Stopper.setName("f7Stopper");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.txtActingContent.setName("txtActingContent");
        this.txtSpecialClause.setName("txtSpecialClause");
        this.txtDescription.setName("txtDescription");
        this.spinPayOffsetDays.setName("spinPayOffsetDays");
        this.pkCreateTime.setName("pkCreateTime");
        // CoreUI		
        this.setPreferredSize(new Dimension(650,550));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // f7Creator		
        this.f7Creator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Creator.setCommitFormat("$number$");		
        this.f7Creator.setDisplayFormat("$name$");		
        this.f7Creator.setEditFormat("$number$");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // contHandler		
        this.contHandler.setBoundLabelText(resHelper.getString("contHandler.boundLabelText"));		
        this.contHandler.setBoundLabelLength(100);		
        this.contHandler.setBoundLabelUnderline(true);
        // f7Handler		
        this.f7Handler.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Handler.setCommitFormat("$number$");		
        this.f7Handler.setDisplayFormat("$name$");		
        this.f7Handler.setEditFormat("$number$");
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // f7Auditor		
        this.f7Auditor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Auditor.setDisplayFormat("$name$");		
        this.f7Auditor.setEditFormat("$number$");		
        this.f7Auditor.setCommitFormat("$number$");
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // pkAuditTime
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setCommitFormat("$number$");		
        this.f7SellProject.setDisplayFormat("$name$");		
        this.f7SellProject.setEditFormat("$number$");		
        this.f7SellProject.setRequired(true);
        // contAgency		
        this.contAgency.setBoundLabelText(resHelper.getString("contAgency.boundLabelText"));		
        this.contAgency.setBoundLabelLength(100);		
        this.contAgency.setBoundLabelUnderline(true);
        // f7Agency		
        this.f7Agency.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.AgencyQuery");		
        this.f7Agency.setCommitFormat("$number$");		
        this.f7Agency.setEditFormat("$number$");		
        this.f7Agency.setDisplayFormat("$name$");		
        this.f7Agency.setRequired(true);
        // contSigner		
        this.contSigner.setBoundLabelText(resHelper.getString("contSigner.boundLabelText"));		
        this.contSigner.setBoundLabelLength(100);		
        this.contSigner.setBoundLabelUnderline(true);
        // f7Signer		
        this.f7Signer.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Signer.setEditFormat("$number$");		
        this.f7Signer.setDisplayFormat("$name$");		
        this.f7Signer.setCommitFormat("$number$");		
        this.f7Signer.setRequired(true);
        // contActingContent		
        this.contActingContent.setBoundLabelText(resHelper.getString("contActingContent.boundLabelText"));		
        this.contActingContent.setBoundLabelLength(100);		
        this.contActingContent.setBoundLabelUnderline(true);
        // contSpecialClause		
        this.contSpecialClause.setBoundLabelText(resHelper.getString("contSpecialClause.boundLabelText"));		
        this.contSpecialClause.setBoundLabelLength(100);		
        this.contSpecialClause.setBoundLabelUnderline(true);
        // contCommisionStandard		
        this.contCommisionStandard.setBoundLabelText(resHelper.getString("contCommisionStandard.boundLabelText"));		
        this.contCommisionStandard.setBoundLabelLength(100);		
        this.contCommisionStandard.setBoundLabelUnderline(true);
        // comboCommisionStandard		
        this.comboCommisionStandard.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.CommisionStandardEnum").toArray());
        // contValue		
        this.contValue.setBoundLabelText(resHelper.getString("contValue.boundLabelText"));		
        this.contValue.setBoundLabelLength(100);		
        this.contValue.setBoundLabelUnderline(true);
        // txtValue		
        this.txtValue.setRequired(true);		
        this.txtValue.setPrecision(2);		
        this.txtValue.setSupportedEmpty(true);		
        this.txtValue.setDataType(1);
        // contAppPayDateType		
        this.contAppPayDateType.setBoundLabelText(resHelper.getString("contAppPayDateType.boundLabelText"));		
        this.contAppPayDateType.setBoundLabelLength(100);		
        this.contAppPayDateType.setBoundLabelUnderline(true);
        // comboAppPayDateType		
        this.comboAppPayDateType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.AppPayDateTypeEnum").toArray());
        // contPayOffsetDays		
        this.contPayOffsetDays.setBoundLabelText(resHelper.getString("contPayOffsetDays.boundLabelText"));		
        this.contPayOffsetDays.setBoundLabelLength(100);		
        this.contPayOffsetDays.setBoundLabelUnderline(true);
        // contStartUsingDate		
        this.contStartUsingDate.setBoundLabelText(resHelper.getString("contStartUsingDate.boundLabelText"));		
        this.contStartUsingDate.setBoundLabelLength(100);		
        this.contStartUsingDate.setBoundLabelUnderline(true);
        // pkStartUsingDate
        // contStopUsingDate		
        this.contStopUsingDate.setBoundLabelText(resHelper.getString("contStopUsingDate.boundLabelText"));		
        this.contStopUsingDate.setBoundLabelLength(100);		
        this.contStopUsingDate.setBoundLabelUnderline(true);
        // pkStopUsingDate
        // contStopper		
        this.contStopper.setBoundLabelText(resHelper.getString("contStopper.boundLabelText"));		
        this.contStopper.setBoundLabelLength(100);		
        this.contStopper.setBoundLabelUnderline(true);
        // f7Stopper		
        this.f7Stopper.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Stopper.setCommitFormat("$number$");		
        this.f7Stopper.setEditFormat("$number$");		
        this.f7Stopper.setDisplayFormat("$name$");
        // kDScrollPane1
        // kDScrollPane2
        // kDScrollPane3
        // txtActingContent		
        this.txtActingContent.setMaxLength(1000);
        // txtSpecialClause		
        this.txtSpecialClause.setMaxLength(255);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // spinPayOffsetDays
        // pkCreateTime
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 650, 550));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(10, 325, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(368, 325, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contNumber, null);
        contHandler.setBounds(new Rectangle(10, 385, 270, 19));
        this.add(contHandler, null);
        contDescription.setBounds(new Rectangle(10, 445, 630, 83));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(10, 355, 270, 19));
        this.add(contAuditor, null);
        contName.setBounds(new Rectangle(368, 40, 270, 19));
        this.add(contName, null);
        contAuditTime.setBounds(new Rectangle(368, 355, 270, 19));
        this.add(contAuditTime, null);
        contSellProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSellProject, null);
        contAgency.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contAgency, null);
        contSigner.setBounds(new Rectangle(368, 64, 270, 19));
        this.add(contSigner, null);
        contActingContent.setBounds(new Rectangle(10, 100, 628, 92));
        this.add(contActingContent, null);
        contSpecialClause.setBounds(new Rectangle(10, 200, 629, 56));
        this.add(contSpecialClause, null);
        contCommisionStandard.setBounds(new Rectangle(10, 265, 270, 19));
        this.add(contCommisionStandard, null);
        contValue.setBounds(new Rectangle(368, 265, 270, 19));
        this.add(contValue, null);
        contAppPayDateType.setBounds(new Rectangle(10, 295, 270, 19));
        this.add(contAppPayDateType, null);
        contPayOffsetDays.setBounds(new Rectangle(368, 295, 270, 19));
        this.add(contPayOffsetDays, null);
        contStartUsingDate.setBounds(new Rectangle(368, 385, 270, 19));
        this.add(contStartUsingDate, null);
        contStopUsingDate.setBounds(new Rectangle(368, 415, 270, 19));
        this.add(contStopUsingDate, null);
        contStopper.setBounds(new Rectangle(10, 415, 270, 19));
        this.add(contStopper, null);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contHandler
        contHandler.setBoundEditor(f7Handler);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane3);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(txtDescription, null);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contAgency
        contAgency.setBoundEditor(f7Agency);
        //contSigner
        contSigner.setBoundEditor(f7Signer);
        //contActingContent
        contActingContent.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtActingContent, null);
        //contSpecialClause
        contSpecialClause.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtSpecialClause, null);
        //contCommisionStandard
        contCommisionStandard.setBoundEditor(comboCommisionStandard);
        //contValue
        contValue.setBoundEditor(txtValue);
        //contAppPayDateType
        contAppPayDateType.setBoundEditor(comboAppPayDateType);
        //contPayOffsetDays
        contPayOffsetDays.setBoundEditor(spinPayOffsetDays);
        //contStartUsingDate
        contStartUsingDate.setBoundEditor(pkStartUsingDate);
        //contStopUsingDate
        contStopUsingDate.setBoundEditor(pkStopUsingDate);
        //contStopper
        contStopper.setBoundEditor(f7Stopper);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("handler", com.kingdee.eas.base.permission.UserInfo.class, this.f7Handler, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.f7Auditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.f7SellProject, "data");
		dataBinder.registerBinding("agency", com.kingdee.eas.fdc.tenancy.AgencyInfo.class, this.f7Agency, "data");
		dataBinder.registerBinding("signer", com.kingdee.eas.base.permission.UserInfo.class, this.f7Signer, "data");
		dataBinder.registerBinding("commisionStandard", com.kingdee.eas.fdc.tenancy.CommisionStandardEnum.class, this.comboCommisionStandard, "selectedItem");
		dataBinder.registerBinding("value", java.math.BigDecimal.class, this.txtValue, "value");
		dataBinder.registerBinding("appPayDateType", com.kingdee.eas.fdc.tenancy.AppPayDateTypeEnum.class, this.comboAppPayDateType, "selectedItem");
		dataBinder.registerBinding("startUsingDate", java.util.Date.class, this.pkStartUsingDate, "value");
		dataBinder.registerBinding("stopUsingDate", java.util.Date.class, this.pkStopUsingDate, "value");
		dataBinder.registerBinding("stopper", com.kingdee.eas.base.permission.UserInfo.class, this.f7Stopper, "data");
		dataBinder.registerBinding("actingContent", String.class, this.txtActingContent, "text");
		dataBinder.registerBinding("specialClause", String.class, this.txtSpecialClause, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("payOffsetDays", int.class, this.spinPayOffsetDays, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.AgencyContractEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.AgencyContractInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handler", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commisionStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("value", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("appPayDateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startUsingDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stopUsingDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stopper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actingContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialClause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payOffsetDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("handler.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("agency.*"));
        sic.add(new SelectorItemInfo("signer.*"));
        sic.add(new SelectorItemInfo("commisionStandard"));
        sic.add(new SelectorItemInfo("value"));
        sic.add(new SelectorItemInfo("appPayDateType"));
        sic.add(new SelectorItemInfo("startUsingDate"));
        sic.add(new SelectorItemInfo("stopUsingDate"));
        sic.add(new SelectorItemInfo("stopper.*"));
        sic.add(new SelectorItemInfo("actingContent"));
        sic.add(new SelectorItemInfo("specialClause"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("payOffsetDays"));
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "AgencyContractEditUI");
    }




}