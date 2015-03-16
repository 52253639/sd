/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

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
public abstract class AbstractMarketSupplierEvaluationEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierEvaluationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTemplate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMarketsupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsupplierTemple;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTemplate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMarketsupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsupplierTemple;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtContractEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnALine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnILine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRLine;
    protected com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierEvaluationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierEvaluationEditUI.class.getName());
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
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
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
        this.contTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMarketsupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsupplierTemple = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtTemplate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMarketsupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsupplierTemple = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtContractEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnALine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnILine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.kDPanel3.setName("kDPanel3");
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
        this.contTemplate.setName("contTemplate");
        this.contMarketsupplier.setName("contMarketsupplier");
        this.contsupplierTemple.setName("contsupplierTemple");
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
        this.prmtTemplate.setName("prmtTemplate");
        this.prmtMarketsupplier.setName("prmtMarketsupplier");
        this.prmtsupplierTemple.setName("prmtsupplierTemple");
        this.kdtEntry.setName("kdtEntry");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.kdtContractEntry.setName("kdtContractEntry");
        this.btnALine.setName("btnALine");
        this.btnILine.setName("btnILine");
        this.btnRLine.setName("btnRLine");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
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
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
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
        this.contSupplier.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contEvaluationType		
        this.contEvaluationType.setBoundLabelText(resHelper.getString("contEvaluationType.boundLabelText"));		
        this.contEvaluationType.setBoundLabelLength(100);		
        this.contEvaluationType.setBoundLabelUnderline(true);
        // contTemplate		
        this.contTemplate.setBoundLabelText(resHelper.getString("contTemplate.boundLabelText"));		
        this.contTemplate.setBoundLabelLength(100);		
        this.contTemplate.setBoundLabelUnderline(true);		
        this.contTemplate.setVisible(false);
        // contMarketsupplier		
        this.contMarketsupplier.setBoundLabelText(resHelper.getString("contMarketsupplier.boundLabelText"));		
        this.contMarketsupplier.setBoundLabelLength(100);		
        this.contMarketsupplier.setBoundLabelUnderline(true);		
        this.contMarketsupplier.setVisible(true);
        // contsupplierTemple		
        this.contsupplierTemple.setBoundLabelText(resHelper.getString("contsupplierTemple.boundLabelText"));		
        this.contsupplierTemple.setBoundLabelLength(100);		
        this.contsupplierTemple.setBoundLabelUnderline(true);		
        this.contsupplierTemple.setVisible(true);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtBusinessMode		
        this.txtBusinessMode.setEnabled(false);
        // txtManagerPhone		
        this.txtManagerPhone.setMaxLength(200);		
        this.txtManagerPhone.setRequired(true);
        // txtContractorPhone		
        this.txtContractorPhone.setMaxLength(200);		
        this.txtContractorPhone.setRequired(true);
        // txtManager		
        this.txtManager.setMaxLength(200);		
        this.txtManager.setRequired(true);
        // txtContractor		
        this.txtContractor.setMaxLength(200);		
        this.txtContractor.setRequired(true);
        // txtLinkJob		
        this.txtLinkJob.setMaxLength(200);
        // txtLinkPhone		
        this.txtLinkPhone.setMaxLength(200);		
        this.txtLinkPhone.setRequired(true);
        // txtLinkPerson		
        this.txtLinkPerson.setMaxLength(200);		
        this.txtLinkPerson.setRequired(true);
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
        // prmtMarketsupplier		
        this.prmtMarketsupplier.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierStockQuery");		
        this.prmtMarketsupplier.setEditable(true);		
        this.prmtMarketsupplier.setDisplayFormat("$number$");		
        this.prmtMarketsupplier.setEditFormat("$number$");		
        this.prmtMarketsupplier.setCommitFormat("$number$");		
        this.prmtMarketsupplier.setRequired(false);
        // prmtsupplierTemple		
        this.prmtsupplierTemple.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSupplierAppraiseTemplateQuery");		
        this.prmtsupplierTemple.setEditable(true);		
        this.prmtsupplierTemple.setDisplayFormat("$name$");		
        this.prmtsupplierTemple.setEditFormat("$number$");		
        this.prmtsupplierTemple.setCommitFormat("$number$");		
        this.prmtsupplierTemple.setRequired(false);
        		prmtsupplierTemple.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSupplierAppraiseTemplateListUI prmtsupplierTemple_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtsupplierTemple_F7ListUI == null) {
					try {
						prmtsupplierTemple_F7ListUI = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSupplierAppraiseTemplateListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtsupplierTemple_F7ListUI));
					prmtsupplierTemple_F7ListUI.setF7Use(true,ctx);
					prmtsupplierTemple.setSelector(prmtsupplierTemple_F7ListUI);
				}
			}
		});
					
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"MguideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"MguideName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"threeBz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"Mweight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"isPass\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"auditPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"auditDep\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">评审维度</t:Cell><t:Cell t:configured=\"false\">指标名称</t:Cell><t:Cell t:configured=\"false\">3分标准/标准</t:Cell><t:Cell t:configured=\"false\">权重(%)</t:Cell><t:Cell t:configured=\"false\">情况描述</t:Cell><t:Cell t:configured=\"false\">评分</t:Cell><t:Cell t:configured=\"false\">合格与否</t:Cell><t:Cell t:configured=\"false\">评审人</t:Cell><t:Cell t:configured=\"false\">评审部门</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntry.putBindContents("editData",new String[] {"MguideType","MguideName","threeBz","Mweight","remark","score","isPass","auditPerson","auditDept"});


        this.kdtEntry.checkParsed();
        KDTextField kdtEntry_MguideType_TextField = new KDTextField();
        kdtEntry_MguideType_TextField.setName("kdtEntry_MguideType_TextField");
        kdtEntry_MguideType_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_MguideType_CellEditor = new KDTDefaultCellEditor(kdtEntry_MguideType_TextField);
        this.kdtEntry.getColumn("MguideType").setEditor(kdtEntry_MguideType_CellEditor);
        final KDBizPromptBox kdtEntry_MguideName_PromptBox = new KDBizPromptBox();
        kdtEntry_MguideName_PromptBox.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSplAuditIndexQuery");
        kdtEntry_MguideName_PromptBox.setVisible(true);
        kdtEntry_MguideName_PromptBox.setEditable(true);
        kdtEntry_MguideName_PromptBox.setDisplayFormat("$number$");
        kdtEntry_MguideName_PromptBox.setEditFormat("$number$");
        kdtEntry_MguideName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_MguideName_CellEditor = new KDTDefaultCellEditor(kdtEntry_MguideName_PromptBox);
        this.kdtEntry.getColumn("MguideName").setEditor(kdtEntry_MguideName_CellEditor);
        ObjectValueRender kdtEntry_MguideName_OVR = new ObjectValueRender();
        kdtEntry_MguideName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("MguideName").setRenderer(kdtEntry_MguideName_OVR);
        			kdtEntry_MguideName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSplAuditIndexListUI kdtEntry_MguideName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtEntry_MguideName_PromptBox_F7ListUI == null) {
					try {
						kdtEntry_MguideName_PromptBox_F7ListUI = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSplAuditIndexListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtEntry_MguideName_PromptBox_F7ListUI));
					kdtEntry_MguideName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtEntry_MguideName_PromptBox.setSelector(kdtEntry_MguideName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtEntry_threeBz_TextField = new KDTextField();
        kdtEntry_threeBz_TextField.setName("kdtEntry_threeBz_TextField");
        kdtEntry_threeBz_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_threeBz_CellEditor = new KDTDefaultCellEditor(kdtEntry_threeBz_TextField);
        this.kdtEntry.getColumn("threeBz").setEditor(kdtEntry_threeBz_CellEditor);
        KDFormattedTextField kdtEntry_Mweight_TextField = new KDFormattedTextField();
        kdtEntry_Mweight_TextField.setName("kdtEntry_Mweight_TextField");
        kdtEntry_Mweight_TextField.setVisible(true);
        kdtEntry_Mweight_TextField.setEditable(true);
        kdtEntry_Mweight_TextField.setHorizontalAlignment(2);
        kdtEntry_Mweight_TextField.setDataType(1);
        	kdtEntry_Mweight_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_Mweight_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_Mweight_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_Mweight_CellEditor = new KDTDefaultCellEditor(kdtEntry_Mweight_TextField);
        this.kdtEntry.getColumn("Mweight").setEditor(kdtEntry_Mweight_CellEditor);
        KDTextField kdtEntry_remark_TextField = new KDTextField();
        kdtEntry_remark_TextField.setName("kdtEntry_remark_TextField");
        kdtEntry_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry_remark_CellEditor = new KDTDefaultCellEditor(kdtEntry_remark_TextField);
        this.kdtEntry.getColumn("remark").setEditor(kdtEntry_remark_CellEditor);
        KDFormattedTextField kdtEntry_score_TextField = new KDFormattedTextField();
        kdtEntry_score_TextField.setName("kdtEntry_score_TextField");
        kdtEntry_score_TextField.setVisible(true);
        kdtEntry_score_TextField.setEditable(true);
        kdtEntry_score_TextField.setHorizontalAlignment(2);
        kdtEntry_score_TextField.setDataType(1);
        	kdtEntry_score_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntry_score_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntry_score_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_score_CellEditor = new KDTDefaultCellEditor(kdtEntry_score_TextField);
        this.kdtEntry.getColumn("score").setEditor(kdtEntry_score_CellEditor);
        KDComboBox kdtEntry_isPass_ComboBox = new KDComboBox();
        kdtEntry_isPass_ComboBox.setName("kdtEntry_isPass_ComboBox");
        kdtEntry_isPass_ComboBox.setVisible(true);
        kdtEntry_isPass_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());
        KDTDefaultCellEditor kdtEntry_isPass_CellEditor = new KDTDefaultCellEditor(kdtEntry_isPass_ComboBox);
        this.kdtEntry.getColumn("isPass").setEditor(kdtEntry_isPass_CellEditor);
        final KDBizPromptBox kdtEntry_auditPerson_PromptBox = new KDBizPromptBox();
        kdtEntry_auditPerson_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtEntry_auditPerson_PromptBox.setVisible(true);
        kdtEntry_auditPerson_PromptBox.setEditable(true);
        kdtEntry_auditPerson_PromptBox.setDisplayFormat("$number$");
        kdtEntry_auditPerson_PromptBox.setEditFormat("$number$");
        kdtEntry_auditPerson_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_auditPerson_CellEditor = new KDTDefaultCellEditor(kdtEntry_auditPerson_PromptBox);
        this.kdtEntry.getColumn("auditPerson").setEditor(kdtEntry_auditPerson_CellEditor);
        ObjectValueRender kdtEntry_auditPerson_OVR = new ObjectValueRender();
        kdtEntry_auditPerson_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("auditPerson").setRenderer(kdtEntry_auditPerson_OVR);
        final KDBizPromptBox kdtEntry_auditDep_PromptBox = new KDBizPromptBox();
        kdtEntry_auditDep_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
        kdtEntry_auditDep_PromptBox.setVisible(true);
        kdtEntry_auditDep_PromptBox.setEditable(true);
        kdtEntry_auditDep_PromptBox.setDisplayFormat("$number$");
        kdtEntry_auditDep_PromptBox.setEditFormat("$number$");
        kdtEntry_auditDep_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_auditDep_CellEditor = new KDTDefaultCellEditor(kdtEntry_auditDep_PromptBox);
        this.kdtEntry.getColumn("auditDep").setEditor(kdtEntry_auditDep_CellEditor);
        ObjectValueRender kdtEntry_auditDep_OVR = new ObjectValueRender();
        kdtEntry_auditDep_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("auditDep").setRenderer(kdtEntry_auditDep_OVR);
        // prmtCreator		
        this.prmtCreator.setRequired(true);		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setRequired(true);		
        this.pkCreateTime.setEnabled(false);
        // kdtContractEntry
		String kdtContractEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isHasContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contract\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractAmount\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isHasContract}</t:Cell><t:Cell>$Resource{contract}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtContractEntry.setFormatXml(resHelper.translateString("kdtContractEntry",kdtContractEntryStrXML));
        this.kdtContractEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtContractEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kdtContractEntry.checkParsed();
        // btnALine		
        this.btnALine.setText(resHelper.getString("btnALine.text"));
        this.btnALine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnALine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnILine		
        this.btnILine.setText(resHelper.getString("btnILine.text"));
        this.btnILine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnILine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRLine		
        this.btnRLine.setText(resHelper.getString("btnRLine.text"));
        this.btnRLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtEvaluationType,prmtsupplierTemple,prmtMarketsupplier,prmtSupplier,prmtTemplate,txtNumber,pkBizDate,prmtCreator,pkCreateTime,kdtEntry}));
        this.setFocusCycleRoot(true);
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
        kDPanel1.setBounds(new Rectangle(0, 0, 1013, 162));
        this.add(kDPanel1, new KDLayout.Constraints(0, 0, 1013, 162, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel2.setBounds(new Rectangle(0, 360, 1013, 376));
        this.add(kDPanel2, new KDLayout.Constraints(0, 360, 1013, 376, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(17, 742, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(17, 742, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(345, 742, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(345, 742, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel3.setBounds(new Rectangle(0, 163, 1013, 193));
        this.add(kDPanel3, new KDLayout.Constraints(0, 163, 1013, 193, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 162));        contBizDate.setBounds(new Rectangle(729, 104, 270, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(729, 104, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBusinessMode.setBounds(new Rectangle(729, 82, 270, 19));
        kDPanel1.add(contBusinessMode, new KDLayout.Constraints(729, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contManagerPhone.setBounds(new Rectangle(372, 104, 270, 19));
        kDPanel1.add(contManagerPhone, new KDLayout.Constraints(372, 104, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractorPhone.setBounds(new Rectangle(372, 82, 270, 19));
        kDPanel1.add(contContractorPhone, new KDLayout.Constraints(372, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contManager.setBounds(new Rectangle(16, 104, 270, 19));
        kDPanel1.add(contManager, new KDLayout.Constraints(16, 104, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractor.setBounds(new Rectangle(16, 82, 270, 19));
        kDPanel1.add(contContractor, new KDLayout.Constraints(16, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkJob.setBounds(new Rectangle(729, 60, 270, 19));
        kDPanel1.add(contLinkJob, new KDLayout.Constraints(729, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkPhone.setBounds(new Rectangle(372, 60, 270, 19));
        kDPanel1.add(contLinkPhone, new KDLayout.Constraints(372, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkPerson.setBounds(new Rectangle(16, 60, 270, 19));
        kDPanel1.add(contLinkPerson, new KDLayout.Constraints(16, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPartProject.setBounds(new Rectangle(729, 38, 270, 19));
        kDPanel1.add(contPartProject, new KDLayout.Constraints(729, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSplArea.setBounds(new Rectangle(372, 38, 270, 19));
        kDPanel1.add(contSplArea, new KDLayout.Constraints(372, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(16, 38, 270, 19));
        kDPanel1.add(contInviteType, new KDLayout.Constraints(16, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierName.setBounds(new Rectangle(729, 16, 270, 19));
        kDPanel1.add(contSupplierName, new KDLayout.Constraints(729, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplier.setBounds(new Rectangle(636, 73, 270, 19));
        kDPanel1.add(contSupplier, new KDLayout.Constraints(636, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(16, 16, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(16, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEvaluationType.setBounds(new Rectangle(16, 126, 270, 19));
        kDPanel1.add(contEvaluationType, new KDLayout.Constraints(16, 126, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTemplate.setBounds(new Rectangle(887, 131, 270, 19));
        kDPanel1.add(contTemplate, new KDLayout.Constraints(887, 131, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMarketsupplier.setBounds(new Rectangle(372, 16, 270, 19));
        kDPanel1.add(contMarketsupplier, new KDLayout.Constraints(372, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsupplierTemple.setBounds(new Rectangle(372, 126, 270, 19));
        kDPanel1.add(contsupplierTemple, new KDLayout.Constraints(372, 126, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contTemplate
        contTemplate.setBoundEditor(prmtTemplate);
        //contMarketsupplier
        contMarketsupplier.setBoundEditor(prmtMarketsupplier);
        //contsupplierTemple
        contsupplierTemple.setBoundEditor(prmtsupplierTemple);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 360, 1013, 376));        kdtEntry.setBounds(new Rectangle(13, 27, 984, 335));
        kDPanel2.add(kdtEntry, new KDLayout.Constraints(13, 27, 984, 335, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 163, 1013, 193));        kdtContractEntry.setBounds(new Rectangle(13, 33, 984, 145));
        kDPanel3.add(kdtContractEntry, new KDLayout.Constraints(13, 33, 984, 145, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnALine.setBounds(new Rectangle(764, 12, 75, 19));
        kDPanel3.add(btnALine, new KDLayout.Constraints(764, 12, 75, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnILine.setBounds(new Rectangle(843, 12, 75, 19));
        kDPanel3.add(btnILine, new KDLayout.Constraints(843, 12, 75, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRLine.setBounds(new Rectangle(922, 12, 75, 19));
        kDPanel3.add(btnRLine, new KDLayout.Constraints(922, 12, 75, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));

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
		dataBinder.registerBinding("Marketsupplier.supplierBusinessMode.name", String.class, this.txtBusinessMode, "text");
		dataBinder.registerBinding("Marketsupplier.managerPhone", String.class, this.txtManagerPhone, "text");
		dataBinder.registerBinding("Marketsupplier.contractorPhone", String.class, this.txtContractorPhone, "text");
		dataBinder.registerBinding("Marketsupplier.manager", String.class, this.txtManager, "text");
		dataBinder.registerBinding("Marketsupplier.contractor", String.class, this.txtContractor, "text");
		dataBinder.registerBinding("Marketsupplier.authorizeJob", String.class, this.txtLinkJob, "text");
		dataBinder.registerBinding("Marketsupplier.authorizePhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("Marketsupplier.authorizePerson", String.class, this.txtLinkPerson, "text");
		dataBinder.registerBinding("Marketsupplier.partProject", String.class, this.txtPartProject, "text");
		dataBinder.registerBinding("Marketsupplier.inviteType.name", String.class, this.txtInviteType, "text");
		dataBinder.registerBinding("Marketsupplier.name", String.class, this.txtSupplierName, "text");
		dataBinder.registerBinding("supplier", com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("evaluationType", com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo.class, this.prmtEvaluationType, "data");
		dataBinder.registerBinding("template", com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo.class, this.prmtTemplate, "data");
		dataBinder.registerBinding("Marketsupplier", com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo.class, this.prmtMarketsupplier, "data");
		dataBinder.registerBinding("supplierTemple", com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo.class, this.prmtsupplierTemple, "data");
		dataBinder.registerBinding("indexValue", com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationIndexValueInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("indexValue.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("indexValue.score", java.math.BigDecimal.class, this.kdtEntry, "score.text");
		dataBinder.registerBinding("indexValue.isPass", com.kingdee.util.enums.Enum.class, this.kdtEntry, "isPass.text");
		dataBinder.registerBinding("indexValue.auditPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.kdtEntry, "auditPerson.text");
		dataBinder.registerBinding("indexValue.auditDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.kdtEntry, "auditDep.text");
		dataBinder.registerBinding("indexValue.MguideType", String.class, this.kdtEntry, "MguideType.text");
		dataBinder.registerBinding("indexValue.MguideName", java.lang.Object.class, this.kdtEntry, "MguideName.text");
		dataBinder.registerBinding("indexValue.threeBz", String.class, this.kdtEntry, "threeBz.text");
		dataBinder.registerBinding("indexValue.Mweight", java.math.BigDecimal.class, this.kdtEntry, "Mweight.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierEvaluationEditUIHandler";
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
        this.prmtEvaluationType.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Purchase",editData.getString("number"));
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
    	if (editData == null) return;
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
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Purchase");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Purchase");
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
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.supplierBusinessMode.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.managerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.contractorPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.manager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.contractor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.authorizeJob", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.authorizePhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.authorizePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.partProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.inviteType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaluationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("template", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Marketsupplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierTemple", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.isPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.auditPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.auditDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.MguideType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.MguideName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.threeBz", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.Mweight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
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
     * output prmtTemplate_dataChanged method
     */
    protected void prmtTemplate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtContractEntry_editStopped method
     */
    protected void kdtContractEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnALine_actionPerformed method
     */
    protected void btnALine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnILine_actionPerformed method
     */
    protected void btnILine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRLine_actionPerformed method
     */
    protected void btnRLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("Marketsupplier.supplierBusinessMode.name"));
        sic.add(new SelectorItemInfo("Marketsupplier.managerPhone"));
        sic.add(new SelectorItemInfo("Marketsupplier.contractorPhone"));
        sic.add(new SelectorItemInfo("Marketsupplier.manager"));
        sic.add(new SelectorItemInfo("Marketsupplier.contractor"));
        sic.add(new SelectorItemInfo("Marketsupplier.authorizeJob"));
        sic.add(new SelectorItemInfo("Marketsupplier.authorizePhone"));
        sic.add(new SelectorItemInfo("Marketsupplier.authorizePerson"));
        sic.add(new SelectorItemInfo("Marketsupplier.partProject"));
        sic.add(new SelectorItemInfo("Marketsupplier.inviteType.name"));
        sic.add(new SelectorItemInfo("Marketsupplier.name"));
        sic.add(new SelectorItemInfo("supplier.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("evaluationType.*"));
        sic.add(new SelectorItemInfo("template.*"));
        sic.add(new SelectorItemInfo("Marketsupplier.*"));
        sic.add(new SelectorItemInfo("supplierTemple.*"));
        sic.add(new SelectorItemInfo("indexValue.*"));
//        sic.add(new SelectorItemInfo("indexValue.number"));
    sic.add(new SelectorItemInfo("indexValue.remark"));
    sic.add(new SelectorItemInfo("indexValue.score"));
    sic.add(new SelectorItemInfo("indexValue.isPass"));
        sic.add(new SelectorItemInfo("indexValue.auditPerson.*"));
//        sic.add(new SelectorItemInfo("indexValue.auditPerson.number"));
        sic.add(new SelectorItemInfo("indexValue.auditDept.*"));
//        sic.add(new SelectorItemInfo("indexValue.auditDept.number"));
    sic.add(new SelectorItemInfo("indexValue.MguideType"));
        sic.add(new SelectorItemInfo("indexValue.MguideName.*"));
//        sic.add(new SelectorItemInfo("indexValue.MguideName.number"));
    sic.add(new SelectorItemInfo("indexValue.threeBz"));
    sic.add(new SelectorItemInfo("indexValue.Mweight"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
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
        return new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.client", "MarketSupplierEvaluationEditUI");
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
        return com.kingdee.eas.fdc.invite.markesupplier.client.MarketSupplierEvaluationEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/invite/markesupplier/MarketSupplierEvaluation";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierEvaluationQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
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