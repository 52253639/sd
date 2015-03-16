/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractSupplierReviewGatherEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierReviewGatherEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLYGCScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLYHScore;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLYGCRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLYHRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusinessMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManagerPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkJob;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPartProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSplArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEvaluationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStorageNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStorageDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTemplate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewSupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBusinessMode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtManagerPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtManager;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkJob;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPartProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSplArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEvaluationType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStorageNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEntry;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStorageDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTemplate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtContractEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectContract;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLYGCScore;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLYHScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSrcGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSrcLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLevel;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsOver;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSrcGrade;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtGrade;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbIsPass;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSrcLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLYGCRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLYHRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSupplierReviewGatherEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierReviewGatherEditUI.class.getName());
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
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contLYGCScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLYHScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLYGCRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLYHRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBusinessMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManagerPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractorPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManager = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkJob = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPartProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSplArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEvaluationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStorageNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStorageDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBusinessMode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtManagerPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractorPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtManager = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkJob = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPartProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSplArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSupplierName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtEvaluationType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtStorageNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkStorageDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtTemplate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtContractEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSelectContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtLYGCScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLYHScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contSrcGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsPass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSrcLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsOver = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtSrcGrade = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtGrade = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbIsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSrcLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtLYGCRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLYHRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel2.setName("kDPanel2");
        this.contLYGCScore.setName("contLYGCScore");
        this.contLYHScore.setName("contLYHScore");
        this.kDPanel3.setName("kDPanel3");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLYGCRate.setName("contLYGCRate");
        this.contLYHRate.setName("contLYHRate");
        this.contAmount.setName("contAmount");
        this.contBizDate.setName("contBizDate");
        this.contBusinessMode.setName("contBusinessMode");
        this.contManagerPhone.setName("contManagerPhone");
        this.contContractorPhone.setName("contContractorPhone");
        this.contManager.setName("contManager");
        this.contContractor.setName("contContractor");
        this.contLinkJob.setName("contLinkJob");
        this.contLinkPhone.setName("contLinkPhone");
        this.contLinkPerson.setName("contLinkPerson");
        this.contPartProject.setName("contPartProject");
        this.contSplArea.setName("contSplArea");
        this.contInviteType.setName("contInviteType");
        this.contSupplierName.setName("contSupplierName");
        this.contSupplier.setName("contSupplier");
        this.contNumber.setName("contNumber");
        this.contEvaluationType.setName("contEvaluationType");
        this.contStorageNumber.setName("contStorageNumber");
        this.contEntry.setName("contEntry");
        this.contStorageDate.setName("contStorageDate");
        this.contTemplate.setName("contTemplate");
        this.contRemark.setName("contRemark");
        this.btnViewSupplier.setName("btnViewSupplier");
        this.pkBizDate.setName("pkBizDate");
        this.txtBusinessMode.setName("txtBusinessMode");
        this.txtManagerPhone.setName("txtManagerPhone");
        this.txtContractorPhone.setName("txtContractorPhone");
        this.txtManager.setName("txtManager");
        this.txtContractor.setName("txtContractor");
        this.txtLinkJob.setName("txtLinkJob");
        this.txtLinkPhone.setName("txtLinkPhone");
        this.txtLinkPerson.setName("txtLinkPerson");
        this.txtPartProject.setName("txtPartProject");
        this.txtSplArea.setName("txtSplArea");
        this.txtInviteType.setName("txtInviteType");
        this.txtSupplierName.setName("txtSupplierName");
        this.prmtSupplier.setName("prmtSupplier");
        this.txtNumber.setName("txtNumber");
        this.prmtEvaluationType.setName("prmtEvaluationType");
        this.txtStorageNumber.setName("txtStorageNumber");
        this.prmtEntry.setName("prmtEntry");
        this.pkStorageDate.setName("pkStorageDate");
        this.prmtTemplate.setName("prmtTemplate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtRemark.setName("txtRemark");
        this.kdtContractEntry.setName("kdtContractEntry");
        this.btnSelectContract.setName("btnSelectContract");
        this.kdtEntry.setName("kdtEntry");
        this.txtLYGCScore.setName("txtLYGCScore");
        this.txtLYHScore.setName("txtLYHScore");
        this.contSrcGrade.setName("contSrcGrade");
        this.contGrade.setName("contGrade");
        this.contIsPass.setName("contIsPass");
        this.contSrcLevel.setName("contSrcLevel");
        this.contLevel.setName("contLevel");
        this.cbIsOver.setName("cbIsOver");
        this.prmtSrcGrade.setName("prmtSrcGrade");
        this.prmtGrade.setName("prmtGrade");
        this.cbIsPass.setName("cbIsPass");
        this.prmtSrcLevel.setName("prmtSrcLevel");
        this.prmtLevel.setName("prmtLevel");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtLYGCRate.setName("txtLYGCRate");
        this.txtLYHRate.setName("txtLYHRate");
        this.txtAmount.setName("txtAmount");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // kDScrollPane2
        // kDPanel5		
        this.kDPanel5.setPreferredSize(new Dimension(1013,980));		
        this.kDPanel5.setMinimumSize(new Dimension(1013,980));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // contLYGCScore		
        this.contLYGCScore.setBoundLabelText(resHelper.getString("contLYGCScore.boundLabelText"));		
        this.contLYGCScore.setBoundLabelLength(160);		
        this.contLYGCScore.setBoundLabelUnderline(true);
        // contLYHScore		
        this.contLYHScore.setBoundLabelText(resHelper.getString("contLYHScore.boundLabelText"));		
        this.contLYHScore.setBoundLabelLength(160);		
        this.contLYHScore.setBoundLabelUnderline(true);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLYGCRate		
        this.contLYGCRate.setBoundLabelText(resHelper.getString("contLYGCRate.boundLabelText"));		
        this.contLYGCRate.setBoundLabelLength(160);		
        this.contLYGCRate.setBoundLabelUnderline(true);
        // contLYHRate		
        this.contLYHRate.setBoundLabelText(resHelper.getString("contLYHRate.boundLabelText"));		
        this.contLYHRate.setBoundLabelLength(160);		
        this.contLYHRate.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(160);		
        this.contAmount.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contBusinessMode		
        this.contBusinessMode.setBoundLabelText(resHelper.getString("contBusinessMode.boundLabelText"));		
        this.contBusinessMode.setBoundLabelLength(100);		
        this.contBusinessMode.setBoundLabelUnderline(true);
        // contManagerPhone		
        this.contManagerPhone.setBoundLabelText(resHelper.getString("contManagerPhone.boundLabelText"));		
        this.contManagerPhone.setBoundLabelLength(100);		
        this.contManagerPhone.setBoundLabelUnderline(true);
        // contContractorPhone		
        this.contContractorPhone.setBoundLabelText(resHelper.getString("contContractorPhone.boundLabelText"));		
        this.contContractorPhone.setBoundLabelLength(100);		
        this.contContractorPhone.setBoundLabelUnderline(true);
        // contManager		
        this.contManager.setBoundLabelText(resHelper.getString("contManager.boundLabelText"));		
        this.contManager.setBoundLabelLength(100);		
        this.contManager.setBoundLabelUnderline(true);
        // contContractor		
        this.contContractor.setBoundLabelText(resHelper.getString("contContractor.boundLabelText"));		
        this.contContractor.setBoundLabelLength(100);		
        this.contContractor.setBoundLabelUnderline(true);
        // contLinkJob		
        this.contLinkJob.setBoundLabelText(resHelper.getString("contLinkJob.boundLabelText"));		
        this.contLinkJob.setBoundLabelLength(100);		
        this.contLinkJob.setBoundLabelUnderline(true);
        // contLinkPhone		
        this.contLinkPhone.setBoundLabelText(resHelper.getString("contLinkPhone.boundLabelText"));		
        this.contLinkPhone.setBoundLabelLength(100);		
        this.contLinkPhone.setBoundLabelUnderline(true);
        // contLinkPerson		
        this.contLinkPerson.setBoundLabelText(resHelper.getString("contLinkPerson.boundLabelText"));		
        this.contLinkPerson.setBoundLabelLength(100);		
        this.contLinkPerson.setBoundLabelUnderline(true);
        // contPartProject		
        this.contPartProject.setBoundLabelText(resHelper.getString("contPartProject.boundLabelText"));		
        this.contPartProject.setBoundLabelLength(100);		
        this.contPartProject.setBoundLabelUnderline(true);
        // contSplArea		
        this.contSplArea.setBoundLabelText(resHelper.getString("contSplArea.boundLabelText"));		
        this.contSplArea.setBoundLabelLength(100);		
        this.contSplArea.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // contSupplierName		
        this.contSupplierName.setBoundLabelText(resHelper.getString("contSupplierName.boundLabelText"));		
        this.contSupplierName.setBoundLabelLength(100);		
        this.contSupplierName.setBoundLabelUnderline(true);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contEvaluationType		
        this.contEvaluationType.setBoundLabelText(resHelper.getString("contEvaluationType.boundLabelText"));		
        this.contEvaluationType.setBoundLabelLength(100);		
        this.contEvaluationType.setBoundLabelUnderline(true);
        // contStorageNumber		
        this.contStorageNumber.setBoundLabelText(resHelper.getString("contStorageNumber.boundLabelText"));		
        this.contStorageNumber.setBoundLabelLength(100);		
        this.contStorageNumber.setBoundLabelUnderline(true);
        // contEntry		
        this.contEntry.setBoundLabelText(resHelper.getString("contEntry.boundLabelText"));		
        this.contEntry.setBoundLabelLength(100);		
        this.contEntry.setBoundLabelUnderline(true);
        // contStorageDate		
        this.contStorageDate.setBoundLabelText(resHelper.getString("contStorageDate.boundLabelText"));		
        this.contStorageDate.setBoundLabelLength(100);		
        this.contStorageDate.setBoundLabelUnderline(true);
        // contTemplate		
        this.contTemplate.setBoundLabelText(resHelper.getString("contTemplate.boundLabelText"));		
        this.contTemplate.setBoundLabelLength(100);		
        this.contTemplate.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // btnViewSupplier		
        this.btnViewSupplier.setText(resHelper.getString("btnViewSupplier.text"));
        this.btnViewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewSupplier_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtBusinessMode		
        this.txtBusinessMode.setEnabled(false);
        // txtManagerPhone		
        this.txtManagerPhone.setMaxLength(200);		
        this.txtManagerPhone.setEnabled(false);
        // txtContractorPhone		
        this.txtContractorPhone.setMaxLength(200);		
        this.txtContractorPhone.setEnabled(false);
        // txtManager		
        this.txtManager.setMaxLength(200);		
        this.txtManager.setEnabled(false);
        // txtContractor		
        this.txtContractor.setMaxLength(200);		
        this.txtContractor.setEnabled(false);
        // txtLinkJob		
        this.txtLinkJob.setMaxLength(200);		
        this.txtLinkJob.setEnabled(false);
        // txtLinkPhone		
        this.txtLinkPhone.setMaxLength(200);		
        this.txtLinkPhone.setEnabled(false);
        // txtLinkPerson		
        this.txtLinkPerson.setMaxLength(200);		
        this.txtLinkPerson.setEnabled(false);
        // txtPartProject		
        this.txtPartProject.setMaxLength(200);
        // txtSplArea		
        this.txtSplArea.setEnabled(false);
        // txtInviteType		
        this.txtInviteType.setEnabled(false);
        // txtSupplierName		
        this.txtSupplierName.setEnabled(false);
        // prmtSupplier		
        this.prmtSupplier.setRequired(true);		
        this.prmtSupplier.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");		
        this.prmtSupplier.setCommitFormat("$number$");		
        this.prmtSupplier.setEditFormat("$number$");		
        this.prmtSupplier.setDisplayFormat("$number$");
        this.prmtSupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSupplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // prmtEvaluationType		
        this.prmtEvaluationType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.SupplierEvaluationTypeQuery");		
        this.prmtEvaluationType.setCommitFormat("$number$");		
        this.prmtEvaluationType.setEditFormat("$number$");		
        this.prmtEvaluationType.setDisplayFormat("$name$");		
        this.prmtEvaluationType.setRequired(true);
        this.prmtEvaluationType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEvaluationType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtStorageNumber		
        this.txtStorageNumber.setMaxLength(80);		
        this.txtStorageNumber.setRequired(true);		
        this.txtStorageNumber.setEnabled(false);
        // prmtEntry		
        this.prmtEntry.setRequired(true);		
        this.prmtEntry.setQueryInfo("com.kingdee.eas.basedata.person.app.F7PersonQuery");		
        this.prmtEntry.setCommitFormat("$name$");		
        this.prmtEntry.setEditFormat("$name$");		
        this.prmtEntry.setDisplayFormat("$name$");		
        this.prmtEntry.setEnabledMultiSelection(true);
        this.prmtEntry.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEntry_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkStorageDate		
        this.pkStorageDate.setRequired(true);
        // prmtTemplate		
        this.prmtTemplate.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.SupplierAppraiseTemplateQuery");		
        this.prmtTemplate.setCommitFormat("$number$");		
        this.prmtTemplate.setEditFormat("$number$");		
        this.prmtTemplate.setDisplayFormat("$name$");		
        this.prmtTemplate.setRequired(true);
        this.prmtTemplate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtTemplate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane1
        // txtRemark		
        this.txtRemark.setMaxLength(2000);		
        this.txtRemark.setRequired(true);
        // kdtContractEntry
		String kdtContractEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isHasContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contract\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractAmount\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"manager\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"managerPhone\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isHasContract}</t:Cell><t:Cell>$Resource{contract}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{manager}</t:Cell><t:Cell>$Resource{managerPhone}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtContractEntry.setFormatXml(resHelper.translateString("kdtContractEntry",kdtContractEntryStrXML));

        

        // btnSelectContract		
        this.btnSelectContract.setText(resHelper.getString("btnSelectContract.text"));
        this.btnSelectContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectContract_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guideName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fullNum\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{guideType}</t:Cell><t:Cell>$Resource{guideName}</t:Cell><t:Cell>$Resource{fullNum}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{score}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // txtLYGCScore		
        this.txtLYGCScore.setDataType(1);		
        this.txtLYGCScore.setPrecision(2);
        this.txtLYGCScore.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtLYGCScore_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLYHScore		
        this.txtLYHScore.setEnabled(false);		
        this.txtLYHScore.setDataType(1);		
        this.txtLYHScore.setPrecision(2);
        // contSrcGrade		
        this.contSrcGrade.setBoundLabelText(resHelper.getString("contSrcGrade.boundLabelText"));		
        this.contSrcGrade.setBoundLabelLength(100);		
        this.contSrcGrade.setBoundLabelUnderline(true);
        // contGrade		
        this.contGrade.setBoundLabelText(resHelper.getString("contGrade.boundLabelText"));		
        this.contGrade.setBoundLabelLength(100);		
        this.contGrade.setBoundLabelUnderline(true);
        // contIsPass		
        this.contIsPass.setBoundLabelText(resHelper.getString("contIsPass.boundLabelText"));		
        this.contIsPass.setBoundLabelLength(100);		
        this.contIsPass.setBoundLabelUnderline(true);
        // contSrcLevel		
        this.contSrcLevel.setBoundLabelText(resHelper.getString("contSrcLevel.boundLabelText"));		
        this.contSrcLevel.setBoundLabelLength(100);		
        this.contSrcLevel.setBoundLabelUnderline(true);
        // contLevel		
        this.contLevel.setBoundLabelText(resHelper.getString("contLevel.boundLabelText"));		
        this.contLevel.setBoundLabelLength(100);		
        this.contLevel.setBoundLabelUnderline(true);
        // cbIsOver		
        this.cbIsOver.setText(resHelper.getString("cbIsOver.text"));
        this.cbIsOver.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbIsOver_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSrcGrade		
        this.prmtSrcGrade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");		
        this.prmtSrcGrade.setCommitFormat("$number$");		
        this.prmtSrcGrade.setEditFormat("$number$");		
        this.prmtSrcGrade.setDisplayFormat("$name$");		
        this.prmtSrcGrade.setEnabled(false);
        // prmtGrade		
        this.prmtGrade.setDisplayFormat("$name$");		
        this.prmtGrade.setEditFormat("$number$");		
        this.prmtGrade.setCommitFormat("$number$");		
        this.prmtGrade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");		
        this.prmtGrade.setRequired(true);
        this.prmtGrade.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtGrade_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbIsPass		
        this.cbIsPass.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());		
        this.cbIsPass.setRequired(true);
        // prmtSrcLevel		
        this.prmtSrcLevel.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7LevelSetUpQuery");		
        this.prmtSrcLevel.setCommitFormat("$number$");		
        this.prmtSrcLevel.setEditFormat("$number$");		
        this.prmtSrcLevel.setDisplayFormat("$name$");		
        this.prmtSrcLevel.setEnabled(false);
        // prmtLevel		
        this.prmtLevel.setDisplayFormat("$name$");		
        this.prmtLevel.setEditFormat("$number$");		
        this.prmtLevel.setCommitFormat("$number$");		
        this.prmtLevel.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7LevelSetUpQuery");		
        this.prmtLevel.setRequired(true);
        // prmtCreator		
        this.prmtCreator.setRequired(true);		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setRequired(true);		
        this.pkCreateTime.setEnabled(false);
        // txtLYGCRate		
        this.txtLYGCRate.setDataType(1);		
        this.txtLYGCRate.setPrecision(2);
        this.txtLYGCRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtLYGCRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLYHRate		
        this.txtLYHRate.setDataType(1);		
        this.txtLYHRate.setPrecision(2);
        this.txtLYHRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtLYHRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAmount		
        this.txtAmount.setEnabled(false);		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setPrecision(2);
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
        this.setBounds(new Rectangle(10, 10, 1013, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 768));
        kDScrollPane2.setBounds(new Rectangle(0, 0, 1013, 768));
        this.add(kDScrollPane2, new KDLayout.Constraints(0, 0, 1013, 768, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDScrollPane2
        kDScrollPane2.getViewport().add(kDPanel5, null);
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 980));        kDPanel1.setBounds(new Rectangle(0, 0, 1013, 231));
        kDPanel5.add(kDPanel1, new KDLayout.Constraints(0, 0, 1013, 231, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel4.setBounds(new Rectangle(0, 242, 1013, 193));
        kDPanel5.add(kDPanel4, new KDLayout.Constraints(0, 242, 1013, 193, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel2.setBounds(new Rectangle(0, 439, 1013, 376));
        kDPanel5.add(kDPanel2, new KDLayout.Constraints(0, 439, 1013, 376, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLYGCScore.setBounds(new Rectangle(17, 824, 270, 19));
        kDPanel5.add(contLYGCScore, new KDLayout.Constraints(17, 824, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLYHScore.setBounds(new Rectangle(17, 846, 270, 19));
        kDPanel5.add(contLYHScore, new KDLayout.Constraints(17, 846, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel3.setBounds(new Rectangle(0, 869, 1013, 71));
        kDPanel5.add(kDPanel3, new KDLayout.Constraints(0, 869, 1013, 71, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(17, 943, 270, 19));
        kDPanel5.add(contCreator, new KDLayout.Constraints(17, 943, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(374, 943, 270, 19));
        kDPanel5.add(contCreateTime, new KDLayout.Constraints(374, 943, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLYGCRate.setBounds(new Rectangle(374, 824, 270, 19));
        kDPanel5.add(contLYGCRate, new KDLayout.Constraints(374, 824, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLYHRate.setBounds(new Rectangle(374, 846, 270, 19));
        kDPanel5.add(contLYHRate, new KDLayout.Constraints(374, 846, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(728, 846, 270, 19));
        kDPanel5.add(contAmount, new KDLayout.Constraints(728, 846, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 231));        contBizDate.setBounds(new Rectangle(729, 101, 270, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(729, 101, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBusinessMode.setBounds(new Rectangle(729, 79, 270, 19));
        kDPanel1.add(contBusinessMode, new KDLayout.Constraints(729, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contManagerPhone.setBounds(new Rectangle(372, 101, 270, 19));
        kDPanel1.add(contManagerPhone, new KDLayout.Constraints(372, 101, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractorPhone.setBounds(new Rectangle(372, 79, 270, 19));
        kDPanel1.add(contContractorPhone, new KDLayout.Constraints(372, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contManager.setBounds(new Rectangle(16, 101, 270, 19));
        kDPanel1.add(contManager, new KDLayout.Constraints(16, 101, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractor.setBounds(new Rectangle(16, 79, 270, 19));
        kDPanel1.add(contContractor, new KDLayout.Constraints(16, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkJob.setBounds(new Rectangle(729, 57, 270, 19));
        kDPanel1.add(contLinkJob, new KDLayout.Constraints(729, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkPhone.setBounds(new Rectangle(372, 57, 270, 19));
        kDPanel1.add(contLinkPhone, new KDLayout.Constraints(372, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkPerson.setBounds(new Rectangle(16, 57, 270, 19));
        kDPanel1.add(contLinkPerson, new KDLayout.Constraints(16, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPartProject.setBounds(new Rectangle(729, 35, 270, 19));
        kDPanel1.add(contPartProject, new KDLayout.Constraints(729, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSplArea.setBounds(new Rectangle(372, 35, 270, 19));
        kDPanel1.add(contSplArea, new KDLayout.Constraints(372, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(16, 35, 270, 19));
        kDPanel1.add(contInviteType, new KDLayout.Constraints(16, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierName.setBounds(new Rectangle(729, 13, 270, 19));
        kDPanel1.add(contSupplierName, new KDLayout.Constraints(729, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplier.setBounds(new Rectangle(372, 13, 270, 19));
        kDPanel1.add(contSupplier, new KDLayout.Constraints(372, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(16, 13, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(16, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEvaluationType.setBounds(new Rectangle(16, 123, 270, 19));
        kDPanel1.add(contEvaluationType, new KDLayout.Constraints(16, 123, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStorageNumber.setBounds(new Rectangle(16, 145, 270, 19));
        kDPanel1.add(contStorageNumber, new KDLayout.Constraints(16, 145, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEntry.setBounds(new Rectangle(729, 123, 270, 19));
        kDPanel1.add(contEntry, new KDLayout.Constraints(729, 123, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStorageDate.setBounds(new Rectangle(372, 145, 270, 19));
        kDPanel1.add(contStorageDate, new KDLayout.Constraints(372, 145, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTemplate.setBounds(new Rectangle(372, 123, 270, 19));
        kDPanel1.add(contTemplate, new KDLayout.Constraints(372, 123, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRemark.setBounds(new Rectangle(16, 167, 628, 46));
        kDPanel1.add(contRemark, new KDLayout.Constraints(16, 167, 628, 46, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewSupplier.setBounds(new Rectangle(650, 13, 63, 19));
        kDPanel1.add(btnViewSupplier, new KDLayout.Constraints(650, 13, 63, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contBusinessMode
        contBusinessMode.setBoundEditor(txtBusinessMode);
        //contManagerPhone
        contManagerPhone.setBoundEditor(txtManagerPhone);
        //contContractorPhone
        contContractorPhone.setBoundEditor(txtContractorPhone);
        //contManager
        contManager.setBoundEditor(txtManager);
        //contContractor
        contContractor.setBoundEditor(txtContractor);
        //contLinkJob
        contLinkJob.setBoundEditor(txtLinkJob);
        //contLinkPhone
        contLinkPhone.setBoundEditor(txtLinkPhone);
        //contLinkPerson
        contLinkPerson.setBoundEditor(txtLinkPerson);
        //contPartProject
        contPartProject.setBoundEditor(txtPartProject);
        //contSplArea
        contSplArea.setBoundEditor(txtSplArea);
        //contInviteType
        contInviteType.setBoundEditor(txtInviteType);
        //contSupplierName
        contSupplierName.setBoundEditor(txtSupplierName);
        //contSupplier
        contSupplier.setBoundEditor(prmtSupplier);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contEvaluationType
        contEvaluationType.setBoundEditor(prmtEvaluationType);
        //contStorageNumber
        contStorageNumber.setBoundEditor(txtStorageNumber);
        //contEntry
        contEntry.setBoundEditor(prmtEntry);
        //contStorageDate
        contStorageDate.setBoundEditor(pkStorageDate);
        //contTemplate
        contTemplate.setBoundEditor(prmtTemplate);
        //contRemark
        contRemark.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtRemark, null);
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 242, 1013, 193));        kdtContractEntry.setBounds(new Rectangle(13, 33, 984, 146));
        kDPanel4.add(kdtContractEntry, new KDLayout.Constraints(13, 33, 984, 146, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSelectContract.setBounds(new Rectangle(913, 12, 84, 19));
        kDPanel4.add(btnSelectContract, new KDLayout.Constraints(913, 12, 84, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 439, 1013, 376));        kdtEntry.setBounds(new Rectangle(13, 25, 984, 337));
        kDPanel2.add(kdtEntry, new KDLayout.Constraints(13, 25, 984, 337, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contLYGCScore
        contLYGCScore.setBoundEditor(txtLYGCScore);
        //contLYHScore
        contLYHScore.setBoundEditor(txtLYHScore);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 869, 1013, 71));        contSrcGrade.setBounds(new Rectangle(16, 14, 270, 19));
        kDPanel3.add(contSrcGrade, new KDLayout.Constraints(16, 14, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGrade.setBounds(new Rectangle(373, 14, 270, 19));
        kDPanel3.add(contGrade, new KDLayout.Constraints(373, 14, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPass.setBounds(new Rectangle(727, 14, 270, 19));
        kDPanel3.add(contIsPass, new KDLayout.Constraints(727, 14, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSrcLevel.setBounds(new Rectangle(16, 36, 270, 19));
        kDPanel3.add(contSrcLevel, new KDLayout.Constraints(16, 36, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLevel.setBounds(new Rectangle(373, 36, 270, 19));
        kDPanel3.add(contLevel, new KDLayout.Constraints(373, 36, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsOver.setBounds(new Rectangle(727, 36, 140, 19));
        kDPanel3.add(cbIsOver, new KDLayout.Constraints(727, 36, 140, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSrcGrade
        contSrcGrade.setBoundEditor(prmtSrcGrade);
        //contGrade
        contGrade.setBoundEditor(prmtGrade);
        //contIsPass
        contIsPass.setBoundEditor(cbIsPass);
        //contSrcLevel
        contSrcLevel.setBoundEditor(prmtSrcLevel);
        //contLevel
        contLevel.setBoundEditor(prmtLevel);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLYGCRate
        contLYGCRate.setBoundEditor(txtLYGCRate);
        //contLYHRate
        contLYHRate.setBoundEditor(txtLYHRate);
        //contAmount
        contAmount.setBoundEditor(txtAmount);

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
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("supplier.supplierBusinessMode.name", String.class, this.txtBusinessMode, "text");
		dataBinder.registerBinding("supplier.managerPhone", String.class, this.txtManagerPhone, "text");
		dataBinder.registerBinding("supplier.contractorPhone", String.class, this.txtContractorPhone, "text");
		dataBinder.registerBinding("supplier.manager", String.class, this.txtManager, "text");
		dataBinder.registerBinding("supplier.contractor", String.class, this.txtContractor, "text");
		dataBinder.registerBinding("supplier.authorizeJob", String.class, this.txtLinkJob, "text");
		dataBinder.registerBinding("supplier.authorizePhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("supplier.authorizePerson", String.class, this.txtLinkPerson, "text");
		dataBinder.registerBinding("supplier.partProject", String.class, this.txtPartProject, "text");
		dataBinder.registerBinding("supplier.inviteType.name", String.class, this.txtInviteType, "text");
		dataBinder.registerBinding("supplier.name", String.class, this.txtSupplierName, "text");
		dataBinder.registerBinding("supplier", com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("evaluationType", com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo.class, this.prmtEvaluationType, "data");
		dataBinder.registerBinding("supplier.storageNumber", String.class, this.txtStorageNumber, "text");
		dataBinder.registerBinding("supplier.storageDate", java.util.Date.class, this.pkStorageDate, "value");
		dataBinder.registerBinding("template", com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo.class, this.prmtTemplate, "data");
		dataBinder.registerBinding("description", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("lygcScore", java.math.BigDecimal.class, this.txtLYGCScore, "value");
		dataBinder.registerBinding("lyhRate", java.math.BigDecimal.class, this.txtLYHScore, "value");
		dataBinder.registerBinding("isOver", boolean.class, this.cbIsOver, "selected");
		dataBinder.registerBinding("srcGrade", com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo.class, this.prmtSrcGrade, "data");
		dataBinder.registerBinding("grade", com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo.class, this.prmtGrade, "data");
		dataBinder.registerBinding("isPass", boolean.class, this.cbIsPass, "selectedItem");
		dataBinder.registerBinding("srcLevel", com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo.class, this.prmtSrcLevel, "data");
		dataBinder.registerBinding("level", com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo.class, this.prmtLevel, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lygcRate", java.math.BigDecimal.class, this.txtLYGCRate, "value");
		dataBinder.registerBinding("lyhScroe", java.math.BigDecimal.class, this.txtLYHRate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierReviewGatherEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo)ov;
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
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.supplierBusinessMode.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.managerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.contractorPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.manager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.contractor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.authorizeJob", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.authorizePhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.authorizePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.partProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.inviteType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaluationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.storageNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.storageDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("template", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lygcScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lyhRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isOver", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("srcGrade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("srcLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lygcRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lyhScroe", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    		
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
     * output btnViewSupplier_actionPerformed method
     */
    protected void btnViewSupplier_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtSupplier_dataChanged method
     */
    protected void prmtSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtEvaluationType_dataChanged method
     */
    protected void prmtEvaluationType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtEntry_dataChanged method
     */
    protected void prmtEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtTemplate_dataChanged method
     */
    protected void prmtTemplate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnSelectContract_actionPerformed method
     */
    protected void btnSelectContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output txtLYGCScore_dataChanged method
     */
    protected void txtLYGCScore_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output cbIsOver_itemStateChanged method
     */
    protected void cbIsOver_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtGrade_dataChanged method
     */
    protected void prmtGrade_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtLYGCRate_dataChanged method
     */
    protected void txtLYGCRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtLYHRate_dataChanged method
     */
    protected void txtLYHRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("supplier.supplierBusinessMode.name"));
        sic.add(new SelectorItemInfo("supplier.managerPhone"));
        sic.add(new SelectorItemInfo("supplier.contractorPhone"));
        sic.add(new SelectorItemInfo("supplier.manager"));
        sic.add(new SelectorItemInfo("supplier.contractor"));
        sic.add(new SelectorItemInfo("supplier.authorizeJob"));
        sic.add(new SelectorItemInfo("supplier.authorizePhone"));
        sic.add(new SelectorItemInfo("supplier.authorizePerson"));
        sic.add(new SelectorItemInfo("supplier.partProject"));
        sic.add(new SelectorItemInfo("supplier.inviteType.name"));
        sic.add(new SelectorItemInfo("supplier.name"));
        sic.add(new SelectorItemInfo("supplier.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("evaluationType.*"));
        sic.add(new SelectorItemInfo("supplier.storageNumber"));
        sic.add(new SelectorItemInfo("supplier.storageDate"));
        sic.add(new SelectorItemInfo("template.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("lygcScore"));
        sic.add(new SelectorItemInfo("lyhRate"));
        sic.add(new SelectorItemInfo("isOver"));
        sic.add(new SelectorItemInfo("srcGrade.*"));
        sic.add(new SelectorItemInfo("grade.*"));
        sic.add(new SelectorItemInfo("isPass"));
        sic.add(new SelectorItemInfo("srcLevel.*"));
        sic.add(new SelectorItemInfo("level.*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lygcRate"));
        sic.add(new SelectorItemInfo("lyhScroe"));
        sic.add(new SelectorItemInfo("amount"));
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
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierReviewGatherEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}