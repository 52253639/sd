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
public abstract class AbstractContractPayPlanNewEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractPayPlanNewEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0001;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCopyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0002;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0003;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkProgressPayout;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl21;
    protected com.kingdee.bos.ctrl.swing.KDPanel BIMUDF0006;
    protected com.kingdee.bos.ctrl.swing.KDPanel BIMUDF0007;
    protected com.kingdee.bos.ctrl.swing.KDButton btnFenqi;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPayByStages;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPayByStages_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSignAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kdfMadeRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kdfPayRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField kdContractCd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnloadLinkAssign;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPlanMingxi;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPlanMingxi_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPlam;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPlam_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAuditPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLoadPayLayout;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStatus;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_Submit;
    protected com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo editData = null;
    protected importReWu importReWu = null;
    protected addNew addNew = null;
    protected DeletePlanMingxi DeletePlanMingxi = null;
    protected ApproveSucc 已审批 = null;
    /**
     * output class constructor
     */
    public AbstractContractPayPlanNewEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractPayPlanNewEditUI.class.getName());
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
        //importReWu
        this.importReWu = new importReWu(this);
        getActionManager().registerAction("importReWu", importReWu);
         this.importReWu.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //addNew
        this.addNew = new addNew(this);
        getActionManager().registerAction("addNew", addNew);
         this.addNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //DeletePlanMingxi
        this.DeletePlanMingxi = new DeletePlanMingxi(this);
        getActionManager().registerAction("DeletePlanMingxi", DeletePlanMingxi);
         this.DeletePlanMingxi.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //已审批
        this.已审批 = new ApproveSucc(this);
        getActionManager().registerAction("已审批", 已审批);
         this.已审批.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.已审批.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.已审批.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.已审批.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBIMUDF0001 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCopyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBIMUDF0002 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contBIMUDF0003 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkProgressPayout = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.paneBIZLayerControl21 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.BIMUDF0006 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.BIMUDF0007 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnFenqi = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kdtPayByStages = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtSignAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdfMadeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdfPayRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdContractCd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnRemoveRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnloadLinkAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdtPlanMingxi = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtPlam = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtAuditPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnLoadPayLayout = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtStatus = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btn_Submit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.contBIMUDF0001.setName("contBIMUDF0001");
        this.txtCopyName.setName("txtCopyName");
        this.contBIMUDF0002.setName("contBIMUDF0002");
        this.txtContractPrice.setName("txtContractPrice");
        this.contBIMUDF0003.setName("contBIMUDF0003");
        this.chkProgressPayout.setName("chkProgressPayout");
        this.paneBIZLayerControl21.setName("paneBIZLayerControl21");
        this.BIMUDF0006.setName("BIMUDF0006");
        this.BIMUDF0007.setName("BIMUDF0007");
        this.btnFenqi.setName("btnFenqi");
        this.kdtPayByStages.setName("kdtPayByStages");
        this.txtSignAmount.setName("txtSignAmount");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kdAuditTime.setName("kdAuditTime");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kdfMadeRate.setName("kdfMadeRate");
        this.kdfPayRate.setName("kdfPayRate");
        this.kdContractCd.setName("kdContractCd");
        this.btnRemoveRow.setName("btnRemoveRow");
        this.btnAddRow.setName("btnAddRow");
        this.btnloadLinkAssign.setName("btnloadLinkAssign");
        this.kdtPlanMingxi.setName("kdtPlanMingxi");
        this.kdtPlam.setName("kdtPlam");
        this.txtAuditPerson.setName("txtAuditPerson");
        this.btnLoadPayLayout.setName("btnLoadPayLayout");
        this.txtStatus.setName("txtStatus");
        this.btn_Submit.setName("btn_Submit");
        // CoreUI		
        this.menuBar.setVisible(false);		
        this.menuFile.setVisible(false);		
        this.menuTool.setVisible(false);		
        this.menuHelp.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW7.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(105);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // contBIMUDF0001		
        this.contBIMUDF0001.setBoundLabelText(resHelper.getString("contBIMUDF0001.boundLabelText"));		
        this.contBIMUDF0001.setBoundLabelLength(110);		
        this.contBIMUDF0001.setBoundLabelUnderline(true);		
        this.contBIMUDF0001.setVisible(true);		
        this.contBIMUDF0001.setBoundLabelAlignment(7);		
        this.contBIMUDF0001.setForeground(new java.awt.Color(0,0,0));
        // txtCopyName		
        this.txtCopyName.setVisible(true);		
        this.txtCopyName.setHorizontalAlignment(2);		
        this.txtCopyName.setMaxLength(100);		
        this.txtCopyName.setRequired(false);		
        this.txtCopyName.setEnabled(true);		
        this.txtCopyName.setForeground(new java.awt.Color(0,0,0));
        // contBIMUDF0002		
        this.contBIMUDF0002.setBoundLabelText(resHelper.getString("contBIMUDF0002.boundLabelText"));		
        this.contBIMUDF0002.setBoundLabelLength(105);		
        this.contBIMUDF0002.setBoundLabelUnderline(true);		
        this.contBIMUDF0002.setVisible(true);		
        this.contBIMUDF0002.setBoundLabelAlignment(7);		
        this.contBIMUDF0002.setForeground(new java.awt.Color(0,0,0));
        // txtContractPrice		
        this.txtContractPrice.setVisible(true);		
        this.txtContractPrice.setHorizontalAlignment(2);		
        this.txtContractPrice.setDataType(1);		
        this.txtContractPrice.setSupportedEmpty(true);		
        this.txtContractPrice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtContractPrice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtContractPrice.setPrecision(2);		
        this.txtContractPrice.setRequired(false);		
        this.txtContractPrice.setEnabled(false);		
        this.txtContractPrice.setForeground(new java.awt.Color(0,0,0));
        // contBIMUDF0003		
        this.contBIMUDF0003.setBoundLabelText(resHelper.getString("contBIMUDF0003.boundLabelText"));		
        this.contBIMUDF0003.setBoundLabelLength(110);		
        this.contBIMUDF0003.setBoundLabelUnderline(true);		
        this.contBIMUDF0003.setVisible(true);		
        this.contBIMUDF0003.setBoundLabelAlignment(7);		
        this.contBIMUDF0003.setForeground(new java.awt.Color(0,0,0));
        // chkProgressPayout		
        this.chkProgressPayout.setText(resHelper.getString("chkProgressPayout.text"));		
        this.chkProgressPayout.setVisible(true);		
        this.chkProgressPayout.setHorizontalAlignment(2);		
        this.chkProgressPayout.setEnabled(true);		
        this.chkProgressPayout.setForeground(new java.awt.Color(0,0,0));
        this.chkProgressPayout.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkProgressPayout_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // paneBIZLayerControl21		
        this.paneBIZLayerControl21.setVisible(true);
        this.paneBIZLayerControl21.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    paneBIZLayerControl21_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // BIMUDF0006		
        this.BIMUDF0006.setVisible(true);		
        this.BIMUDF0006.setBorder(null);
        // BIMUDF0007		
        this.BIMUDF0007.setVisible(true);
        // btnFenqi		
        this.btnFenqi.setText(resHelper.getString("btnFenqi.text"));		
        this.btnFenqi.setVisible(true);		
        this.btnFenqi.setHorizontalAlignment(2);		
        this.btnFenqi.setEnabled(true);		
        this.btnFenqi.setForeground(new java.awt.Color(0,0,0));
        this.btnFenqi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFenqi_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFenqi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    btnFenqi_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kdtPayByStages
		String kdtPayByStagesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"month\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"payDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"payBili\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"payMoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{month}</t:Cell><t:Cell>$Resource{payDate}</t:Cell><t:Cell>$Resource{payBili}</t:Cell><t:Cell>$Resource{payMoney}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPayByStages.setFormatXml(resHelper.translateString("kdtPayByStages",kdtPayByStagesStrXML));
        this.kdtPayByStages.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPayByStages_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPayByStages.putBindContents("editData",new String[] {"month","payDate","payBili","payMoney","remark"});


        // txtSignAmount		
        this.txtSignAmount.setDataType(10);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // kdAuditTime
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(105);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(110);
        // kdfMadeRate
        // kdfPayRate
        // kdContractCd		
        this.kdContractCd.setVisible(false);
        // btnRemoveRow		
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
        // btnAddRow		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));		
        this.btnAddRow.setOpaque(true);		
        this.btnAddRow.setDisplayedMnemonicIndex(1);		
        this.btnAddRow.setSelected(true);		
        this.btnAddRow.setRolloverEnabled(true);
        this.btnAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnloadLinkAssign		
        this.btnloadLinkAssign.setText(resHelper.getString("btnloadLinkAssign.text"));
        this.btnloadLinkAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnloadLinkAssign_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdtPlanMingxi
		String kdtPlanMingxiStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>0</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"shixiang\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"shixiangmx\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"planStratDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"planEndDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"dayNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"planDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"fukuanBili\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"yingfuMoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isEdit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{shixiang}</t:Cell><t:Cell>$Resource{shixiangmx}</t:Cell><t:Cell>$Resource{planStratDate}</t:Cell><t:Cell>$Resource{planEndDate}</t:Cell><t:Cell>$Resource{dayNumber}</t:Cell><t:Cell>$Resource{planDate}</t:Cell><t:Cell>$Resource{fukuanBili}</t:Cell><t:Cell>$Resource{yingfuMoney}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isEdit}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"1\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPlanMingxi.setFormatXml(resHelper.translateString("kdtPlanMingxi",kdtPlanMingxiStrXML));
        this.kdtPlanMingxi.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtPlanMingxi_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtPlanMingxi.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPlanMingxi_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPlanMingxi_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPlanMingxi.putBindContents("editData",new String[] {"shixiang","shixiangmx","planStratDate","planEndDate","dayNumber","planDate","fukuanBili","yingfuMoney","remark","isEdit"});


        // kdtPlam
		String kdtPlamStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"planPayMonth\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"planPayBili\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"planPaymoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"leiJiMoney\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"leiJiBili\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"sfje\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"ljsfje\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"ljsfbl\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{planPayMonth}</t:Cell><t:Cell>$Resource{planPayBili}</t:Cell><t:Cell>$Resource{planPaymoney}</t:Cell><t:Cell>$Resource{leiJiMoney}</t:Cell><t:Cell>$Resource{leiJiBili}</t:Cell><t:Cell>$Resource{sfje}</t:Cell><t:Cell>$Resource{ljsfje}</t:Cell><t:Cell>$Resource{ljsfbl}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPlam.setFormatXml(resHelper.translateString("kdtPlam",kdtPlamStrXML));

                this.kdtPlam.putBindContents("editData",new String[] {"planPayMonth","planPayBili","planPaymoney","leiJiMoney","leiJiBili","sfje","ljsfje","ljsfbl","remark"});


        // txtAuditPerson
        // btnLoadPayLayout		
        this.btnLoadPayLayout.setText(resHelper.getString("btnLoadPayLayout.text"));		
        this.btnLoadPayLayout.setToolTipText(resHelper.getString("btnLoadPayLayout.toolTipText"));		
        this.btnLoadPayLayout.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        this.btnLoadPayLayout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLoadPayLayout_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtStatus		
        this.txtStatus.setVisible(false);		
        this.txtStatus.setText(resHelper.getString("txtStatus.text"));
        // btn_Submit		
        this.btn_Submit.setText(resHelper.getString("btn_Submit.text"));		
        this.btn_Submit.setToolTipText(resHelper.getString("btn_Submit.toolTipText"));		
        this.btn_Submit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_submit"));
        this.btn_Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btn_Submit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtCopyName,txtSignAmount,txtContractPrice,txtAuditPerson,kdAuditTime,kdfMadeRate,kdfPayRate,chkProgressPayout,kdtPlanMingxi,kdtPlam,btnFenqi,kdtPayByStages}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(40, 19, 270, 19));
        this.add(kDLabelContainer1, null);
        contBizDate.setBounds(new Rectangle(660, 19, 270, 19));
        this.add(contBizDate, null);
        contBIMUDF0001.setBounds(new Rectangle(348, 19, 270, 19));
        this.add(contBIMUDF0001, null);
        contBIMUDF0002.setBounds(new Rectangle(40, 46, 270, 19));
        this.add(contBIMUDF0002, null);
        contBIMUDF0003.setBounds(new Rectangle(348, 46, 270, 19));
        this.add(contBIMUDF0003, null);
        chkProgressPayout.setBounds(new Rectangle(660, 75, 142, 19));
        this.add(chkProgressPayout, null);
        paneBIZLayerControl21.setBounds(new Rectangle(40, 107, 972, 341));
        this.add(paneBIZLayerControl21, null);
        btnFenqi.setBounds(new Rectangle(40, 458, 120, 20));
        this.add(btnFenqi, null);
        kdtPayByStages.setBounds(new Rectangle(40, 469, 971, 395));
        kdtPayByStages_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPayByStages,new com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageInfo(),null,false);
        this.add(kdtPayByStages_detailPanel, null);
        kDLabelContainer3.setBounds(new Rectangle(660, 46, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(40, 75, 270, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(348, 75, 269, 19));
        this.add(kDLabelContainer5, null);
        kdContractCd.setBounds(new Rectangle(842, 83, 49, 21));
        this.add(kdContractCd, null);
        txtStatus.setBounds(new Rectangle(842, 116, 63, 19));
        this.add(txtStatus, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(txtSignAmount);
        //contBIMUDF0001
        contBIMUDF0001.setBoundEditor(txtCopyName);
        //contBIMUDF0002
        contBIMUDF0002.setBoundEditor(txtContractPrice);
        //contBIMUDF0003
        contBIMUDF0003.setBoundEditor(txtAuditPerson);
        //paneBIZLayerControl21
        paneBIZLayerControl21.add(BIMUDF0006, resHelper.getString("BIMUDF0006.constraints"));
        paneBIZLayerControl21.add(BIMUDF0007, resHelper.getString("BIMUDF0007.constraints"));
        //BIMUDF0006
        BIMUDF0006.setLayout(null);        btnRemoveRow.setBounds(new Rectangle(874, 2, 74, 19));
        BIMUDF0006.add(btnRemoveRow, null);
        btnAddRow.setBounds(new Rectangle(797, 2, 70, 19));
        BIMUDF0006.add(btnAddRow, null);
        btnloadLinkAssign.setBounds(new Rectangle(684, 2, 107, 19));
        BIMUDF0006.add(btnloadLinkAssign, null);
        kdtPlanMingxi.setBounds(new Rectangle(0, -1, 966, 312));
        kdtPlanMingxi_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPlanMingxi,new com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiInfo(),null,false);
        BIMUDF0006.add(kdtPlanMingxi_detailPanel, null);
        //BIMUDF0007
        BIMUDF0007.setLayout(null);        kdtPlam.setBounds(new Rectangle(1, 0, 963, 311));
        kdtPlam_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPlam,new com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamInfo(),null,false);
        BIMUDF0007.add(kdtPlam_detailPanel, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(kdAuditTime);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kdfMadeRate);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(kdfPayRate);

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
        this.toolBar.add(btn_Submit);
        this.toolBar.add(btnLoadPayLayout);
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
        this.toolBar.add(btnCopyLine);
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
		dataBinder.registerBinding("CopyName", String.class, this.txtCopyName, "text");
		dataBinder.registerBinding("ContractPrice", java.math.BigDecimal.class, this.txtContractPrice, "value");
		dataBinder.registerBinding("ProgressPayout", boolean.class, this.chkProgressPayout, "selected");
		dataBinder.registerBinding("PayByStage", com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageInfo.class, this.kdtPayByStages, "userObject");
		dataBinder.registerBinding("PayByStage.payDate", java.util.Date.class, this.kdtPayByStages, "payDate.text");
		dataBinder.registerBinding("PayByStage.month", java.util.Date.class, this.kdtPayByStages, "month.text");
		dataBinder.registerBinding("PayByStage.payBili", java.math.BigDecimal.class, this.kdtPayByStages, "payBili.text");
		dataBinder.registerBinding("PayByStage.payMoney", java.math.BigDecimal.class, this.kdtPayByStages, "payMoney.text");
		dataBinder.registerBinding("PayByStage.remark", String.class, this.kdtPayByStages, "remark.text");
		dataBinder.registerBinding("SignAmount", java.math.BigDecimal.class, this.txtSignAmount, "value");
		dataBinder.registerBinding("AuditTime", java.util.Date.class, this.kdAuditTime, "value");
		dataBinder.registerBinding("MadeRate", java.math.BigDecimal.class, this.kdfMadeRate, "value");
		dataBinder.registerBinding("PayRate", java.math.BigDecimal.class, this.kdfPayRate, "value");
		dataBinder.registerBinding("ContractCd", String.class, this.kdContractCd, "text");
		dataBinder.registerBinding("PlanMingxi.shixiang", String.class, this.kdtPlanMingxi, "shixiang.text");
		dataBinder.registerBinding("PlanMingxi.planStratDate", java.util.Date.class, this.kdtPlanMingxi, "planStratDate.text");
		dataBinder.registerBinding("PlanMingxi.planEndDate", java.util.Date.class, this.kdtPlanMingxi, "planEndDate.text");
		dataBinder.registerBinding("PlanMingxi.planDate", java.util.Date.class, this.kdtPlanMingxi, "planDate.text");
		dataBinder.registerBinding("PlanMingxi.dayNumber", java.math.BigDecimal.class, this.kdtPlanMingxi, "dayNumber.text");
		dataBinder.registerBinding("PlanMingxi.fukuanBili", java.math.BigDecimal.class, this.kdtPlanMingxi, "fukuanBili.text");
		dataBinder.registerBinding("PlanMingxi.yingfuMoney", java.math.BigDecimal.class, this.kdtPlanMingxi, "yingfuMoney.text");
		dataBinder.registerBinding("PlanMingxi.remark", String.class, this.kdtPlanMingxi, "remark.text");
		dataBinder.registerBinding("PlanMingxi", com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiInfo.class, this.kdtPlanMingxi, "userObject");
		dataBinder.registerBinding("PlanMingxi.isEdit", char.class, this.kdtPlanMingxi, "isEdit.text");
		dataBinder.registerBinding("PlanMingxi.shixiangmx", com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo.class, this.kdtPlanMingxi, "shixiangmx.text");
		dataBinder.registerBinding("Plam.planPayMonth", java.util.Date.class, this.kdtPlam, "planPayMonth.text");
		dataBinder.registerBinding("Plam.planPayBili", java.math.BigDecimal.class, this.kdtPlam, "planPayBili.text");
		dataBinder.registerBinding("Plam.planPaymoney", java.math.BigDecimal.class, this.kdtPlam, "planPaymoney.text");
		dataBinder.registerBinding("Plam.leiJiMoney", java.math.BigDecimal.class, this.kdtPlam, "leiJiMoney.text");
		dataBinder.registerBinding("Plam.leiJiBili", java.math.BigDecimal.class, this.kdtPlam, "leiJiBili.text");
		dataBinder.registerBinding("Plam.remark", String.class, this.kdtPlam, "remark.text");
		dataBinder.registerBinding("Plam.sfje", java.math.BigDecimal.class, this.kdtPlam, "sfje.text");
		dataBinder.registerBinding("Plam.ljsfje", java.math.BigDecimal.class, this.kdtPlam, "ljsfje.text");
		dataBinder.registerBinding("Plam.ljsfbl", java.math.BigDecimal.class, this.kdtPlam, "ljsfbl.text");
		dataBinder.registerBinding("Plam", com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamInfo.class, this.kdtPlam, "userObject");
		dataBinder.registerBinding("AuditPerson", String.class, this.txtAuditPerson, "text");
		dataBinder.registerBinding("Status", String.class, this.txtStatus, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.ContractPayPlanNewEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CopyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ContractPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ProgressPayout", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStage.payDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStage.month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStage.payBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStage.payMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStage.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SignAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AuditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("MadeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ContractCd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.shixiang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.planStratDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.planEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.planDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.dayNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.fukuanBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.yingfuMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.isEdit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.shixiangmx", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.planPayMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.planPayBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.planPaymoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.leiJiMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.leiJiBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.sfje", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.ljsfje", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.ljsfbl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AuditPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Status", ValidateHelper.ON_SAVE);    		
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
     * output chkProgressPayout_itemStateChanged method
     */
    protected void chkProgressPayout_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output paneBIZLayerControl21_stateChanged method
     */
    protected void paneBIZLayerControl21_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output btnFenqi_mouseClicked method
     */
    protected void btnFenqi_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFenqi_actionPerformed method
     */
    protected void btnFenqi_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kdtPayByStages_editStopped method
     */
    protected void kdtPayByStages_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnRemoveRow_actionPerformed method
     */
    protected void btnRemoveRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddRow_actionPerformed method
     */
    protected void btnAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnloadLinkAssign_actionPerformed method
     */
    protected void btnloadLinkAssign_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtPlanMingxi_editStopped method
     */
    protected void kdtPlanMingxi_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtPlanMingxi_editStarting method
     */
    protected void kdtPlanMingxi_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtPlanMingxi_tableClicked method
     */
    protected void kdtPlanMingxi_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnLoadPayLayout_actionPerformed method
     */
    protected void btnLoadPayLayout_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btn_Submit_actionPerformed method
     */
    protected void btn_Submit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("CopyName"));
        sic.add(new SelectorItemInfo("ContractPrice"));
        sic.add(new SelectorItemInfo("ProgressPayout"));
        sic.add(new SelectorItemInfo("PayByStage.*"));
//        sic.add(new SelectorItemInfo("PayByStage.number"));
    sic.add(new SelectorItemInfo("PayByStage.payDate"));
    sic.add(new SelectorItemInfo("PayByStage.month"));
    sic.add(new SelectorItemInfo("PayByStage.payBili"));
    sic.add(new SelectorItemInfo("PayByStage.payMoney"));
    sic.add(new SelectorItemInfo("PayByStage.remark"));
        sic.add(new SelectorItemInfo("SignAmount"));
        sic.add(new SelectorItemInfo("AuditTime"));
        sic.add(new SelectorItemInfo("MadeRate"));
        sic.add(new SelectorItemInfo("PayRate"));
        sic.add(new SelectorItemInfo("ContractCd"));
    sic.add(new SelectorItemInfo("PlanMingxi.shixiang"));
    sic.add(new SelectorItemInfo("PlanMingxi.planStratDate"));
    sic.add(new SelectorItemInfo("PlanMingxi.planEndDate"));
    sic.add(new SelectorItemInfo("PlanMingxi.planDate"));
    sic.add(new SelectorItemInfo("PlanMingxi.dayNumber"));
    sic.add(new SelectorItemInfo("PlanMingxi.fukuanBili"));
    sic.add(new SelectorItemInfo("PlanMingxi.yingfuMoney"));
    sic.add(new SelectorItemInfo("PlanMingxi.remark"));
        sic.add(new SelectorItemInfo("PlanMingxi.*"));
//        sic.add(new SelectorItemInfo("PlanMingxi.number"));
    sic.add(new SelectorItemInfo("PlanMingxi.isEdit"));
        sic.add(new SelectorItemInfo("PlanMingxi.shixiangmx.*"));
//        sic.add(new SelectorItemInfo("PlanMingxi.shixiangmx.number"));
    sic.add(new SelectorItemInfo("Plam.planPayMonth"));
    sic.add(new SelectorItemInfo("Plam.planPayBili"));
    sic.add(new SelectorItemInfo("Plam.planPaymoney"));
    sic.add(new SelectorItemInfo("Plam.leiJiMoney"));
    sic.add(new SelectorItemInfo("Plam.leiJiBili"));
    sic.add(new SelectorItemInfo("Plam.remark"));
    sic.add(new SelectorItemInfo("Plam.sfje"));
    sic.add(new SelectorItemInfo("Plam.ljsfje"));
    sic.add(new SelectorItemInfo("Plam.ljsfbl"));
        sic.add(new SelectorItemInfo("Plam.*"));
//        sic.add(new SelectorItemInfo("Plam.number"));
        sic.add(new SelectorItemInfo("AuditPerson"));
        sic.add(new SelectorItemInfo("Status"));
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
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output importReWu_actionPerformed method
     */
    public void importReWu_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output addNew_actionPerformed method
     */
    public void addNew_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output deletePlanMingxi_actionPerformed method
     */
    public void deletePlanMingxi_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output approveSucc_actionPerformed method
     */
    public void approveSucc_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareimportReWu(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareimportReWu() {
    	return false;
    }
	public RequestContext prepareaddNew(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareaddNew() {
    	return false;
    }
	public RequestContext prepareDeletePlanMingxi(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareDeletePlanMingxi() {
    	return false;
    }
	public RequestContext prepareApproveSucc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareApproveSucc() {
    	return false;
    }

    /**
     * output importReWu class
     */     
    protected class importReWu extends ItemAction {     
    
        public importReWu()
        {
            this(null);
        }

        public importReWu(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("importReWu.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("importReWu.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("importReWu.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractPayPlanNewEditUI.this, "importReWu", "importReWu_actionPerformed", e);
        }
    }

    /**
     * output addNew class
     */     
    protected class addNew extends ItemAction {     
    
        public addNew()
        {
            this(null);
        }

        public addNew(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("addNew.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("addNew.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("addNew.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractPayPlanNewEditUI.this, "addNew", "addNew_actionPerformed", e);
        }
    }

    /**
     * output DeletePlanMingxi class
     */     
    protected class DeletePlanMingxi extends ItemAction {     
    
        public DeletePlanMingxi()
        {
            this(null);
        }

        public DeletePlanMingxi(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("DeletePlanMingxi.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DeletePlanMingxi.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DeletePlanMingxi.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractPayPlanNewEditUI.this, "DeletePlanMingxi", "deletePlanMingxi_actionPerformed", e);
        }
    }

    /**
     * output ApproveSucc class
     */     
    protected class ApproveSucc extends ItemAction {     
    
        public ApproveSucc()
        {
            this(null);
        }

        public ApproveSucc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ApproveSucc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ApproveSucc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ApproveSucc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractPayPlanNewEditUI.this, "ApproveSucc", "approveSucc_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ContractPayPlanNewEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}