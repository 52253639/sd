/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractFDCDepConPayPlanEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCDepConPayPlanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayPlanCycle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeptment;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtdescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer creator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer auditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer creatorTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer auditorTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlBig;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbRed;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbPink;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayPlanCycle;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDeptment;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea description;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker picCreatorTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker picAuditorTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlContract;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlUnsettledCon;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlNoContract;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnCon;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblContract;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnUnC;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblUnsettledCon;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnNoC;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblNoContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPublish;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRevise;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnIntroPre;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPublish;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevise;
    protected com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo editData = null;
    protected ActionPublish actionPublish = null;
    protected ActionBack actionBack = null;
    protected ActionRevise actionRevise = null;
    protected ActionIntroPre actionIntroPre = null;
    protected ActionViewContract actionViewContract = null;
    /**
     * output class constructor
     */
    public AbstractFDCDepConPayPlanEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCDepConPayPlanEditUI.class.getName());
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
        //actionPublish
        this.actionPublish = new ActionPublish(this);
        getActionManager().registerAction("actionPublish", actionPublish);
         this.actionPublish.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBack
        this.actionBack = new ActionBack(this);
        getActionManager().registerAction("actionBack", actionBack);
         this.actionBack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRevise
        this.actionRevise = new ActionRevise(this);
        getActionManager().registerAction("actionRevise", actionRevise);
         this.actionRevise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIntroPre
        this.actionIntroPre = new ActionIntroPre(this);
        getActionManager().registerAction("actionIntroPre", actionIntroPre);
         this.actionIntroPre.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayPlanCycle = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDeptment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtdescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.creator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.auditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.creatorTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.auditorTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlBig = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.lbRed = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lbPink = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayPlanCycle = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDeptment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.description = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.promCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.promAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.picCreatorTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.picAuditorTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pnlContract = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlUnsettledCon = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlNoContract = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ctnCon = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.ctnUnC = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblUnsettledCon = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.ctnNoC = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblNoContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPublish = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRevise = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnIntroPre = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemPublish = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevise = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contCurProject.setName("contCurProject");
        this.contPayPlanCycle.setName("contPayPlanCycle");
        this.contDeptment.setName("contDeptment");
        this.kDLabel2.setName("kDLabel2");
        this.spMonth.setName("spMonth");
        this.kDLabel1.setName("kDLabel1");
        this.txtdescription.setName("txtdescription");
        this.creator.setName("creator");
        this.auditor.setName("auditor");
        this.creatorTime.setName("creatorTime");
        this.auditorTime.setName("auditorTime");
        this.pnlBig.setName("pnlBig");
        this.lbRed.setName("lbRed");
        this.lbPink.setName("lbPink");
        this.spYear.setName("spYear");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtCurProject.setName("prmtCurProject");
        this.prmtPayPlanCycle.setName("prmtPayPlanCycle");
        this.prmtDeptment.setName("prmtDeptment");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.description.setName("description");
        this.promCreator.setName("promCreator");
        this.promAuditor.setName("promAuditor");
        this.picCreatorTime.setName("picCreatorTime");
        this.picAuditorTime.setName("picAuditorTime");
        this.pnlContract.setName("pnlContract");
        this.pnlUnsettledCon.setName("pnlUnsettledCon");
        this.pnlNoContract.setName("pnlNoContract");
        this.ctnCon.setName("ctnCon");
        this.tblContract.setName("tblContract");
        this.ctnUnC.setName("ctnUnC");
        this.tblUnsettledCon.setName("tblUnsettledCon");
        this.ctnNoC.setName("ctnNoC");
        this.tblNoContract.setName("tblNoContract");
        this.btnViewContract.setName("btnViewContract");
        this.btnPublish.setName("btnPublish");
        this.btnBack.setName("btnBack");
        this.btnRevise.setName("btnRevise");
        this.btnIntroPre.setName("btnIntroPre");
        this.menuItemPublish.setName("menuItemPublish");
        this.menuItemRevise.setName("menuItemRevise");
        // CoreUI		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceUp.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setEnabled(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.menuItemMultiapprove.setEnabled(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemCreateTo.setEnabled(false);		
        this.btnCalculator.setVisible(false);		
        this.btnCalculator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);		
        this.contCurProject.setVisible(false);		
        this.contCurProject.setEnabled(false);
        // contPayPlanCycle		
        this.contPayPlanCycle.setBoundLabelText(resHelper.getString("contPayPlanCycle.boundLabelText"));		
        this.contPayPlanCycle.setBoundLabelLength(100);		
        this.contPayPlanCycle.setBoundLabelUnderline(true);
        // contDeptment		
        this.contDeptment.setBoundLabelText(resHelper.getString("contDeptment.boundLabelText"));		
        this.contDeptment.setBoundLabelLength(100);		
        this.contDeptment.setBoundLabelUnderline(true);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // spMonth
        this.spMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spMonth_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // txtdescription		
        this.txtdescription.setBoundLabelText(resHelper.getString("txtdescription.boundLabelText"));		
        this.txtdescription.setBoundLabelLength(100);		
        this.txtdescription.setBoundLabelUnderline(true);
        // creator		
        this.creator.setBoundLabelText(resHelper.getString("creator.boundLabelText"));		
        this.creator.setBoundLabelLength(100);		
        this.creator.setBoundLabelUnderline(true);
        // auditor		
        this.auditor.setBoundLabelText(resHelper.getString("auditor.boundLabelText"));		
        this.auditor.setBoundLabelUnderline(true);		
        this.auditor.setBoundLabelLength(100);
        // creatorTime		
        this.creatorTime.setBoundLabelText(resHelper.getString("creatorTime.boundLabelText"));		
        this.creatorTime.setBoundLabelUnderline(true);		
        this.creatorTime.setBoundLabelLength(100);
        // auditorTime		
        this.auditorTime.setBoundLabelText(resHelper.getString("auditorTime.boundLabelText"));		
        this.auditorTime.setBoundLabelLength(100);		
        this.auditorTime.setBoundLabelUnderline(true);
        // pnlBig		
        this.pnlBig.setAutoscrolls(true);
        this.pnlBig.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    pnlBig_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lbRed		
        this.lbRed.setText(resHelper.getString("lbRed.text"));		
        this.lbRed.setBackground(new java.awt.Color(236,236,232));		
        this.lbRed.setForeground(new java.awt.Color(255,0,0));
        // lbPink		
        this.lbPink.setText(resHelper.getString("lbPink.text"));		
        this.lbPink.setForeground(new java.awt.Color(255,119,119));
        // spYear
        this.spYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spYear_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);
        // prmtCurProject		
        this.prmtCurProject.setEditable(true);		
        this.prmtCurProject.setRequired(true);		
        this.prmtCurProject.setEditFormat("$number$");		
        this.prmtCurProject.setDisplayFormat("$name$");		
        this.prmtCurProject.setCommitFormat("$number$");
        // prmtPayPlanCycle		
        this.prmtPayPlanCycle.setDisplayFormat("$cycle$");		
        this.prmtPayPlanCycle.setEditFormat("$number$");		
        this.prmtPayPlanCycle.setCommitFormat("$number$");		
        this.prmtPayPlanCycle.setEnabled(false);
        // prmtDeptment		
        this.prmtDeptment.setDisplayFormat("$name$");		
        this.prmtDeptment.setEditFormat("$number$");		
        this.prmtDeptment.setCommitFormat("$number$");		
        this.prmtDeptment.setRequired(true);
        this.prmtDeptment.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtDeptment_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane1
        // description		
        this.description.setMaxLength(500);
        // promCreator		
        this.promCreator.setEnabled(false);		
        this.promCreator.setVisible(true);		
        this.promCreator.setEditable(true);		
        this.promCreator.setDisplayFormat("$name$");		
        this.promCreator.setEditFormat("$number$");		
        this.promCreator.setCommitFormat("$number$");		
        this.promCreator.setRequired(false);
        // promAuditor		
        this.promAuditor.setEnabled(false);		
        this.promAuditor.setVisible(true);		
        this.promAuditor.setEditable(true);		
        this.promAuditor.setDisplayFormat("$name$");		
        this.promAuditor.setEditFormat("$number$");		
        this.promAuditor.setCommitFormat("$number$");		
        this.promAuditor.setRequired(false);
        // picCreatorTime		
        this.picCreatorTime.setEnabled(false);		
        this.picCreatorTime.setVisible(true);
        // picAuditorTime		
        this.picAuditorTime.setVisible(true);		
        this.picAuditorTime.setEnabled(false);
        // pnlContract		
        this.pnlContract.setAutoscrolls(true);
        // pnlUnsettledCon
        // pnlNoContract
        // ctnCon
        // tblContract
		String tblContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"isBack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"isEdit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"project\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"conNumber\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"conName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"true\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"conPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"lastMonthPayable\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"lastMonthPay\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"lastMonthNopay\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"lastMonthEnRoute\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isBack}</t:Cell><t:Cell>$Resource{isEdit}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{conNumber}</t:Cell><t:Cell>$Resource{conName}</t:Cell><t:Cell>$Resource{conPrice}</t:Cell><t:Cell>$Resource{contractId}</t:Cell><t:Cell>$Resource{lastMonthPayable}</t:Cell><t:Cell>$Resource{lastMonthPay}</t:Cell><t:Cell>$Resource{lastMonthNopay}</t:Cell><t:Cell>$Resource{lastMonthEnRoute}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{isBack_Row2}</t:Cell><t:Cell>$Resource{isEdit_Row2}</t:Cell><t:Cell>$Resource{project_Row2}</t:Cell><t:Cell>$Resource{conNumber_Row2}</t:Cell><t:Cell>$Resource{conName_Row2}</t:Cell><t:Cell>$Resource{conPrice_Row2}</t:Cell><t:Cell>$Resource{contractId_Row2}</t:Cell><t:Cell>$Resource{lastMonthPayable_Row2}</t:Cell><t:Cell>$Resource{lastMonthPay_Row2}</t:Cell><t:Cell>$Resource{lastMonthNopay_Row2}</t:Cell><t:Cell>$Resource{lastMonthEnRoute_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblContract.setFormatXml(resHelper.translateString("tblContract",tblContractStrXML));
        this.tblContract.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblContract_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblContract.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblContract_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // ctnUnC
        // tblUnsettledCon
		String tblUnsettledConStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"isBack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"isEdit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"project\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"unsettledConNumber\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"unsettledConName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /><t:Column t:key=\"unsettledConestPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isBack}</t:Cell><t:Cell>$Resource{isEdit}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{unsettledConNumber}</t:Cell><t:Cell>$Resource{unsettledConName}</t:Cell><t:Cell>$Resource{unsettledConestPrice}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{isBack_Row2}</t:Cell><t:Cell>$Resource{isEdit_Row2}</t:Cell><t:Cell>$Resource{project_Row2}</t:Cell><t:Cell>$Resource{unsettledConNumber_Row2}</t:Cell><t:Cell>$Resource{unsettledConName_Row2}</t:Cell><t:Cell>$Resource{unsettledConestPrice_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblUnsettledCon.setFormatXml(resHelper.translateString("tblUnsettledCon",tblUnsettledConStrXML));
        this.tblUnsettledCon.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblUnsettledCon_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblUnsettledCon.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblUnsettledCon_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // ctnNoC
        // tblNoContract
		String tblNoContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"isBack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"isEdit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"project\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"payMatters\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"payMattersName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isBack}</t:Cell><t:Cell>$Resource{isEdit}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{payMatters}</t:Cell><t:Cell>$Resource{payMattersName}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{isBack_Row2}</t:Cell><t:Cell>$Resource{isEdit_Row2}</t:Cell><t:Cell>$Resource{project_Row2}</t:Cell><t:Cell>$Resource{payMatters_Row2}</t:Cell><t:Cell>$Resource{payMattersName_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblNoContract.setFormatXml(resHelper.translateString("tblNoContract",tblNoContractStrXML));
        this.tblNoContract.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblNoContract_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblNoContract.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblNoContract_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));
        // btnPublish
        this.btnPublish.setAction((IItemAction)ActionProxyFactory.getProxy(actionPublish, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPublish.setText(resHelper.getString("btnPublish.text"));
        // btnBack
        this.btnBack.setAction((IItemAction)ActionProxyFactory.getProxy(actionBack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBack.setText(resHelper.getString("btnBack.text"));		
        this.btnBack.setEnabled(false);		
        this.btnBack.setVisible(false);
        // btnRevise
        this.btnRevise.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRevise.setText(resHelper.getString("btnRevise.text"));
        // btnIntroPre
        this.btnIntroPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionIntroPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnIntroPre.setText(resHelper.getString("btnIntroPre.text"));
        // menuItemPublish
        this.menuItemPublish.setAction((IItemAction)ActionProxyFactory.getProxy(actionPublish, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPublish.setText(resHelper.getString("menuItemPublish.text"));
        // menuItemRevise
        this.menuItemRevise.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRevise.setText(resHelper.getString("menuItemRevise.text"));
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
        contCreateTime.setBounds(new Rectangle(371, 38, 189, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 38, 189, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurProject.setBounds(new Rectangle(733, 38, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(733, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayPlanCycle.setBounds(new Rectangle(10, 38, 270, 19));
        this.add(contPayPlanCycle, new KDLayout.Constraints(10, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDeptment.setBounds(new Rectangle(733, 10, 270, 19));
        this.add(contDeptment, new KDLayout.Constraints(733, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel2.setBounds(new Rectangle(566, 38, 15, 19));
        this.add(kDLabel2, new KDLayout.Constraints(566, 38, 15, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        spMonth.setBounds(new Rectangle(586, 38, 40, 19));
        this.add(spMonth, new KDLayout.Constraints(586, 38, 40, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(632, 38, 15, 19));
        this.add(kDLabel1, new KDLayout.Constraints(632, 38, 15, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtdescription.setBounds(new Rectangle(10, 66, 993, 60));
        this.add(txtdescription, new KDLayout.Constraints(10, 66, 993, 60, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        creator.setBounds(new Rectangle(10, 570, 270, 19));
        this.add(creator, new KDLayout.Constraints(10, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        auditor.setBounds(new Rectangle(10, 598, 270, 19));
        this.add(auditor, new KDLayout.Constraints(10, 598, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        creatorTime.setBounds(new Rectangle(733, 570, 270, 19));
        this.add(creatorTime, new KDLayout.Constraints(733, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        auditorTime.setBounds(new Rectangle(733, 597, 270, 19));
        this.add(auditorTime, new KDLayout.Constraints(733, 597, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlBig.setBounds(new Rectangle(10, 138, 991, 402));
        this.add(pnlBig, new KDLayout.Constraints(10, 138, 991, 402, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        lbRed.setBounds(new Rectangle(45, 543, 64, 19));
        this.add(lbRed, new KDLayout.Constraints(45, 543, 64, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        lbPink.setBounds(new Rectangle(156, 543, 134, 19));
        this.add(lbPink, new KDLayout.Constraints(156, 543, 134, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        //contCreateTime
        contCreateTime.setBoundEditor(spYear);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contPayPlanCycle
        contPayPlanCycle.setBoundEditor(prmtPayPlanCycle);
        //contDeptment
        contDeptment.setBoundEditor(prmtDeptment);
        //txtdescription
        txtdescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(description, null);
        //creator
        creator.setBoundEditor(promCreator);
        //auditor
        auditor.setBoundEditor(promAuditor);
        //creatorTime
        creatorTime.setBoundEditor(picCreatorTime);
        //auditorTime
        auditorTime.setBoundEditor(picAuditorTime);
        //pnlBig
        pnlBig.add(pnlContract, resHelper.getString("pnlContract.constraints"));
        pnlBig.add(pnlUnsettledCon, resHelper.getString("pnlUnsettledCon.constraints"));
        pnlBig.add(pnlNoContract, resHelper.getString("pnlNoContract.constraints"));
        //pnlContract
pnlContract.setLayout(new BorderLayout(0, 0));        pnlContract.add(ctnCon, BorderLayout.CENTER);
        //ctnCon
ctnCon.getContentPane().setLayout(new BorderLayout(0, 0));        ctnCon.getContentPane().add(tblContract, BorderLayout.CENTER);
        //pnlUnsettledCon
pnlUnsettledCon.setLayout(new BorderLayout(0, 0));        pnlUnsettledCon.add(ctnUnC, BorderLayout.CENTER);
        //ctnUnC
ctnUnC.getContentPane().setLayout(new BorderLayout(0, 0));        ctnUnC.getContentPane().add(tblUnsettledCon, BorderLayout.CENTER);
        //pnlNoContract
pnlNoContract.setLayout(new BorderLayout(0, 0));        pnlNoContract.add(ctnNoC, BorderLayout.CENTER);
        //ctnNoC
ctnNoC.getContentPane().setLayout(new BorderLayout(0, 0));        ctnNoC.getContentPane().add(tblNoContract, BorderLayout.CENTER);

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
        menuBiz.add(menuItemPublish);
        menuBiz.add(menuItemRevise);
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
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnPublish);
        this.toolBar.add(btnBack);
        this.toolBar.add(btnRevise);
        this.toolBar.add(btnIntroPre);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("month", int.class, this.spMonth, "value");
		dataBinder.registerBinding("year", int.class, this.spYear, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("payPlanCycle", com.kingdee.eas.fdc.basedata.PayPlanCycleInfo.class, this.prmtPayPlanCycle, "data");
		dataBinder.registerBinding("deptment", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtDeptment, "data");
		dataBinder.registerBinding("description", String.class, this.description, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.promCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.promAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.picCreatorTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.picAuditorTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FDCDepConPayPlanEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)ov;
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
		getValidateHelper().registerBindProperty("month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payPlanCycle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deptment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    		
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
     * output spMonth_stateChanged method
     */
    protected void spMonth_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output pnlBig_stateChanged method
     */
    protected void pnlBig_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spYear_stateChanged method
     */
    protected void spYear_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtDeptment_dataChanged method
     */
    protected void prmtDeptment_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblContract_editStopped method
     */
    protected void tblContract_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblContract_tableClicked method
     */
    protected void tblContract_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblUnsettledCon_editStopped method
     */
    protected void tblUnsettledCon_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblUnsettledCon_tableClicked method
     */
    protected void tblUnsettledCon_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblNoContract_editStopped method
     */
    protected void tblNoContract_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblNoContract_tableClicked method
     */
    protected void tblNoContract_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("month"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("payPlanCycle.*"));
        sic.add(new SelectorItemInfo("deptment.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditTime"));
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
     * output actionPublish_actionPerformed method
     */
    public void actionPublish_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBack_actionPerformed method
     */
    public void actionBack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRevise_actionPerformed method
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionIntroPre_actionPerformed method
     */
    public void actionIntroPre_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPublish(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPublish() {
    	return false;
    }
	public RequestContext prepareActionBack(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBack() {
    	return false;
    }
	public RequestContext prepareActionRevise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRevise() {
    	return false;
    }
	public RequestContext prepareActionIntroPre(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIntroPre() {
    	return false;
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }

    /**
     * output ActionPublish class
     */     
    protected class ActionPublish extends ItemAction {     
    
        public ActionPublish()
        {
            this(null);
        }

        public ActionPublish(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPublish.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPublish.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPublish.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCDepConPayPlanEditUI.this, "ActionPublish", "actionPublish_actionPerformed", e);
        }
    }

    /**
     * output ActionBack class
     */     
    protected class ActionBack extends ItemAction {     
    
        public ActionBack()
        {
            this(null);
        }

        public ActionBack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCDepConPayPlanEditUI.this, "ActionBack", "actionBack_actionPerformed", e);
        }
    }

    /**
     * output ActionRevise class
     */     
    protected class ActionRevise extends ItemAction {     
    
        public ActionRevise()
        {
            this(null);
        }

        public ActionRevise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRevise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCDepConPayPlanEditUI.this, "ActionRevise", "actionRevise_actionPerformed", e);
        }
    }

    /**
     * output ActionIntroPre class
     */     
    protected class ActionIntroPre extends ItemAction {     
    
        public ActionIntroPre()
        {
            this(null);
        }

        public ActionIntroPre(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionIntroPre.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntroPre.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntroPre.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCDepConPayPlanEditUI.this, "ActionIntroPre", "actionIntroPre_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCDepConPayPlanEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCDepConPayPlanEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}