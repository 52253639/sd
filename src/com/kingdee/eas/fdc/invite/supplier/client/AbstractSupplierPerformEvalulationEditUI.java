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
public abstract class AbstractSupplierPerformEvalulationEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierPerformEvalulationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPerformDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContact;
    protected com.kingdee.bos.ctrl.swing.KDContainer contConter;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPinggu;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCompleteAudit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGrade;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPerformDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContact;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTemplateBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSupplier;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTemplet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSupplier;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTemplet;
    protected com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo editData = null;
    protected ActionSupplier actionSupplier = null;
    protected ActionContract actionContract = null;
    protected ActionTemplet actionTemplet = null;
    /**
     * output class constructor
     */
    public AbstractSupplierPerformEvalulationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierPerformEvalulationEditUI.class.getName());
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
        //actionSupplier
        this.actionSupplier = new ActionSupplier(this);
        getActionManager().registerAction("actionSupplier", actionSupplier);
         this.actionSupplier.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContract
        this.actionContract = new ActionContract(this);
        getActionManager().registerAction("actionContract", actionContract);
         this.actionContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTemplet
        this.actionTemplet = new ActionTemplet(this);
        getActionManager().registerAction("actionTemplet", actionTemplet);
         this.actionTemplet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPerformDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conSupplierType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContact = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConter = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPinggu = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.chkIsCompleteAudit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkEditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSupplierName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtGrade = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPerformDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSupplierType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContact = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtTemplateBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtSupplierType = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTemplet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSupplier = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTemplet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contSupplierName.setName("contSupplierName");
        this.contGrade.setName("contGrade");
        this.contPerformDate.setName("contPerformDate");
        this.conSupplierType.setName("conSupplierType");
        this.conCurProject.setName("conCurProject");
        this.contContact.setName("contContact");
        this.contConter.setName("contConter");
        this.contAttachment.setName("contAttachment");
        this.contPinggu.setName("contPinggu");
        this.chkIsCompleteAudit.setName("chkIsCompleteAudit");
        this.prmtCreator.setName("prmtCreator");
        this.pkEditDate.setName("pkEditDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.txtSupplierName.setName("txtSupplierName");
        this.txtGrade.setName("txtGrade");
        this.pkPerformDate.setName("pkPerformDate");
        this.prmtSupplierType.setName("prmtSupplierType");
        this.prmtProject.setName("prmtProject");
        this.prmtContact.setName("prmtContact");
        this.kdtTemplateBill.setName("kdtTemplateBill");
        this.kdtSupplierType.setName("kdtSupplierType");
        this.btnSupplier.setName("btnSupplier");
        this.btnContract.setName("btnContract");
        this.btnTemplet.setName("btnTemplet");
        this.menuItemSupplier.setName("menuItemSupplier");
        this.menuItemContract.setName("menuItemContract");
        this.menuItemTemplet.setName("menuItemTemplet");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.menuItemSendMessage.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.kDMenuItemSendMessage.setVisible(false);		
        this.menuItemWorkFlowList.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contSupplierName		
        this.contSupplierName.setBoundLabelText(resHelper.getString("contSupplierName.boundLabelText"));		
        this.contSupplierName.setBoundLabelLength(100);		
        this.contSupplierName.setBoundLabelUnderline(true);
        // contGrade		
        this.contGrade.setBoundLabelText(resHelper.getString("contGrade.boundLabelText"));		
        this.contGrade.setBoundLabelLength(100);		
        this.contGrade.setBoundLabelUnderline(true);
        // contPerformDate		
        this.contPerformDate.setBoundLabelText(resHelper.getString("contPerformDate.boundLabelText"));		
        this.contPerformDate.setBoundLabelLength(100);		
        this.contPerformDate.setBoundLabelUnderline(true);
        // conSupplierType		
        this.conSupplierType.setBoundLabelText(resHelper.getString("conSupplierType.boundLabelText"));		
        this.conSupplierType.setBoundLabelLength(100);		
        this.conSupplierType.setBoundLabelUnderline(true);
        // conCurProject		
        this.conCurProject.setBoundLabelText(resHelper.getString("conCurProject.boundLabelText"));		
        this.conCurProject.setBoundLabelUnderline(true);		
        this.conCurProject.setBoundLabelLength(100);
        // contContact		
        this.contContact.setBoundLabelText(resHelper.getString("contContact.boundLabelText"));		
        this.contContact.setBoundLabelLength(100);		
        this.contContact.setBoundLabelUnderline(true);
        // contConter		
        this.contConter.setTitle(resHelper.getString("contConter.title"));
        // contAttachment		
        this.contAttachment.setBoundLabelText(resHelper.getString("contAttachment.boundLabelText"));		
        this.contAttachment.setBoundLabelUnderline(true);		
        this.contAttachment.setBoundLabelLength(1000);
        // contPinggu		
        this.contPinggu.setTitle(resHelper.getString("contPinggu.title"));
        // chkIsCompleteAudit		
        this.chkIsCompleteAudit.setText(resHelper.getString("chkIsCompleteAudit.text"));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkEditDate		
        this.pkEditDate.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // txtSupplierName		
        this.txtSupplierName.setMaxLength(80);		
        this.txtSupplierName.setEnabled(false);
        this.txtSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtSupplierName_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtGrade
        // pkPerformDate		
        this.pkPerformDate.setRequired(true);
        // prmtSupplierType		
        this.prmtSupplierType.setRequired(true);
        this.prmtSupplierType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSupplierType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtSupplierType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSupplierType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProject
        // prmtContact		
        this.prmtContact.setRequired(true);
        this.prmtContact.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContact_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtTemplateBill
		String kdtTemplateBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"audit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditIndex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"purview\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fullStarded\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditCur\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"info\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"ÆÀÉóÊ±¼ä\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{audit}</t:Cell><t:Cell>$Resource{auditIndex}</t:Cell><t:Cell>$Resource{purview}</t:Cell><t:Cell>$Resource{fullStarded}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{auditPerson}</t:Cell><t:Cell>$Resource{auditCur}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{info}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtTemplateBill.setFormatXml(resHelper.translateString("kdtTemplateBill",kdtTemplateBillStrXML));
        this.kdtTemplateBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtTemplateBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtTemplateBill.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtTemplateBill_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtTemplateBill.putBindContents("editData",new String[] {"template.guideType","template.splAuditIndex","template.weight","template.fullNum","score","auditPerson","auditDept","auditTime","description","template"});


        // kdtSupplierType
		String kdtSupplierTypeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"beforeState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"isEvalulation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"point\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"isGrade\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"description\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{beforeState}</t:Cell><t:Cell>$Resource{isEvalulation}</t:Cell><t:Cell>$Resource{point}</t:Cell><t:Cell>$Resource{isGrade}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSupplierType.setFormatXml(resHelper.translateString("kdtSupplierType",kdtSupplierTypeStrXML));
        this.kdtSupplierType.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtSupplierType_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtSupplierType.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSupplierType_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtSupplierType.putBindContents("editData",new String[] {"supplierType","beforeState","isAudit","score","grade","description",""});


        // btnSupplier
        this.btnSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplier, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSupplier.setText(resHelper.getString("btnSupplier.text"));		
        this.btnSupplier.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_check"));
        // btnContract
        this.btnContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnContract.setText(resHelper.getString("btnContract.text"));		
        this.btnContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_bill"));
        // btnTemplet
        this.btnTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTemplet.setText(resHelper.getString("btnTemplet.text"));		
        this.btnTemplet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_copy"));
        // menuItemSupplier
        this.menuItemSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplier, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSupplier.setText(resHelper.getString("menuItemSupplier.text"));		
        this.menuItemSupplier.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_check"));
        // menuItemContract
        this.menuItemContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContract.setText(resHelper.getString("menuItemContract.text"));		
        this.menuItemContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_bill"));
        // menuItemTemplet
        this.menuItemTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTemplet.setText(resHelper.getString("menuItemTemplet.text"));		
        this.menuItemTemplet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_copy"));
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
        contCreator.setBounds(new Rectangle(20, 531, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(20, 531, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(20, 570, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(20, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(723, 531, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(723, 531, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(723, 570, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(723, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplierName.setBounds(new Rectangle(20, 20, 270, 19));
        this.add(contSupplierName, new KDLayout.Constraints(20, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGrade.setBounds(new Rectangle(723, 20, 270, 19));
        this.add(contGrade, new KDLayout.Constraints(723, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPerformDate.setBounds(new Rectangle(723, 59, 270, 19));
        this.add(contPerformDate, new KDLayout.Constraints(723, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conSupplierType.setBounds(new Rectangle(369, 20, 270, 19));
        this.add(conSupplierType, new KDLayout.Constraints(369, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conCurProject.setBounds(new Rectangle(20, 59, 268, 19));
        this.add(conCurProject, new KDLayout.Constraints(20, 59, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContact.setBounds(new Rectangle(369, 59, 270, 19));
        this.add(contContact, new KDLayout.Constraints(369, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConter.setBounds(new Rectangle(20, 278, 972, 235));
        this.add(contConter, new KDLayout.Constraints(20, 278, 972, 235, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAttachment.setBounds(new Rectangle(20, 252, 972, 19));
        this.add(contAttachment, new KDLayout.Constraints(20, 252, 972, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPinggu.setBounds(new Rectangle(20, 131, 972, 116));
        this.add(contPinggu, new KDLayout.Constraints(20, 131, 972, 116, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsCompleteAudit.setBounds(new Rectangle(20, 98, 140, 19));
        this.add(chkIsCompleteAudit, new KDLayout.Constraints(20, 98, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkEditDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contSupplierName
        contSupplierName.setBoundEditor(txtSupplierName);
        //contGrade
        contGrade.setBoundEditor(txtGrade);
        //contPerformDate
        contPerformDate.setBoundEditor(pkPerformDate);
        //conSupplierType
        conSupplierType.setBoundEditor(prmtSupplierType);
        //conCurProject
        conCurProject.setBoundEditor(prmtProject);
        //contContact
        contContact.setBoundEditor(prmtContact);
        //contConter
contConter.getContentPane().setLayout(new BorderLayout(0, 0));        contConter.getContentPane().add(kdtTemplateBill, BorderLayout.CENTER);
        //contPinggu
contPinggu.getContentPane().setLayout(new BorderLayout(0, 0));        contPinggu.getContentPane().add(kdtSupplierType, BorderLayout.CENTER);

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
        menuBiz.add(menuItemSupplier);
        menuBiz.add(menuItemContract);
        menuBiz.add(menuItemTemplet);
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
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnSupplier);
        this.toolBar.add(btnContract);
        this.toolBar.add(btnTemplet);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isCompleteAudit", boolean.class, this.chkIsCompleteAudit, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkEditDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("supplier.name", String.class, this.txtSupplierName, "text");
		dataBinder.registerBinding("score", java.math.BigDecimal.class, this.txtGrade, "value");
		dataBinder.registerBinding("businessDate", java.util.Date.class, this.pkPerformDate, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("contract", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtContact, "data");
		dataBinder.registerBinding("auditValue", com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueInfo.class, this.kdtTemplateBill, "userObject");
		dataBinder.registerBinding("auditValue.template.guideType", String.class, this.kdtTemplateBill, "audit.text");
		dataBinder.registerBinding("auditValue.template.weight", java.math.BigDecimal.class, this.kdtTemplateBill, "purview.text");
		dataBinder.registerBinding("auditValue.template.fullNum", String.class, this.kdtTemplateBill, "fullStarded.text");
		dataBinder.registerBinding("auditValue.score", java.math.BigDecimal.class, this.kdtTemplateBill, "score.text");
		dataBinder.registerBinding("auditValue.auditPerson", String.class, this.kdtTemplateBill, "auditPerson.text");
		dataBinder.registerBinding("auditValue.auditDept", String.class, this.kdtTemplateBill, "auditCur.text");
		dataBinder.registerBinding("auditValue.auditTime", java.util.Date.class, this.kdtTemplateBill, "auditTime.text");
		dataBinder.registerBinding("auditValue.template", com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo.class, this.kdtTemplateBill, "info.text");
		dataBinder.registerBinding("auditValue.template.splAuditIndex", com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo.class, this.kdtTemplateBill, "auditIndex.text");
		dataBinder.registerBinding("auditValue.description", String.class, this.kdtTemplateBill, "description.text");
		dataBinder.registerBinding("auditBill", com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyInfo.class, this.kdtSupplierType, "userObject");
		dataBinder.registerBinding("auditBill.beforeState", String.class, this.kdtSupplierType, "beforeState.text");
		dataBinder.registerBinding("auditBill.isAudit", boolean.class, this.kdtSupplierType, "isEvalulation.text");
		dataBinder.registerBinding("auditBill.score", java.math.BigDecimal.class, this.kdtSupplierType, "point.text");
		dataBinder.registerBinding("auditBill.grade", String.class, this.kdtSupplierType, "isGrade.text");
		dataBinder.registerBinding("auditBill.supplierType", com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo.class, this.kdtSupplierType, "type.text");
		dataBinder.registerBinding("auditBill.description", String.class, this.kdtSupplierType, "description.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierPerformEvalulationEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo)ov;
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
		getValidateHelper().registerBindProperty("isCompleteAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("businessDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.template.guideType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.template.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.template.fullNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.auditPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.auditDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.template", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.template.splAuditIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditValue.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.beforeState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.isAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.grade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.supplierType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditBill.description", ValidateHelper.ON_SAVE);    		
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
     * output txtSupplierName_actionPerformed method
     */
    protected void txtSupplierName_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtSupplierType_dataChanged method
     */
    protected void prmtSupplierType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSupplierType_willShow method
     */
    protected void prmtSupplierType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtContact_dataChanged method
     */
    protected void prmtContact_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtTemplateBill_editStopped method
     */
    protected void kdtTemplateBill_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtTemplateBill_tableClicked method
     */
    protected void kdtTemplateBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtSupplierType_editStopped method
     */
    protected void kdtSupplierType_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSupplierType_tableClicked method
     */
    protected void kdtSupplierType_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isCompleteAudit"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("supplier.name"));
        sic.add(new SelectorItemInfo("score"));
        sic.add(new SelectorItemInfo("businessDate"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("contract.*"));
        sic.add(new SelectorItemInfo("auditValue.*"));
//        sic.add(new SelectorItemInfo("auditValue.number"));
    sic.add(new SelectorItemInfo("auditValue.template.guideType"));
    sic.add(new SelectorItemInfo("auditValue.template.weight"));
    sic.add(new SelectorItemInfo("auditValue.template.fullNum"));
    sic.add(new SelectorItemInfo("auditValue.score"));
    sic.add(new SelectorItemInfo("auditValue.auditPerson"));
    sic.add(new SelectorItemInfo("auditValue.auditDept"));
    sic.add(new SelectorItemInfo("auditValue.auditTime"));
        sic.add(new SelectorItemInfo("auditValue.template.*"));
//        sic.add(new SelectorItemInfo("auditValue.template.number"));
        sic.add(new SelectorItemInfo("auditValue.template.splAuditIndex.*"));
//        sic.add(new SelectorItemInfo("auditValue.template.splAuditIndex.number"));
    sic.add(new SelectorItemInfo("auditValue.description"));
        sic.add(new SelectorItemInfo("auditBill.*"));
//        sic.add(new SelectorItemInfo("auditBill.number"));
    sic.add(new SelectorItemInfo("auditBill.beforeState"));
    sic.add(new SelectorItemInfo("auditBill.isAudit"));
    sic.add(new SelectorItemInfo("auditBill.score"));
    sic.add(new SelectorItemInfo("auditBill.grade"));
        sic.add(new SelectorItemInfo("auditBill.supplierType.*"));
//        sic.add(new SelectorItemInfo("auditBill.supplierType.number"));
    sic.add(new SelectorItemInfo("auditBill.description"));
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
     * output actionSupplier_actionPerformed method
     */
    public void actionSupplier_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContract_actionPerformed method
     */
    public void actionContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTemplet_actionPerformed method
     */
    public void actionTemplet_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionSupplier(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSupplier() {
    	return false;
    }
	public RequestContext prepareActionContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContract() {
    	return false;
    }
	public RequestContext prepareActionTemplet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTemplet() {
    	return false;
    }

    /**
     * output ActionSupplier class
     */     
    protected class ActionSupplier extends ItemAction {     
    
        public ActionSupplier()
        {
            this(null);
        }

        public ActionSupplier(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSupplier.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplier.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplier.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierPerformEvalulationEditUI.this, "ActionSupplier", "actionSupplier_actionPerformed", e);
        }
    }

    /**
     * output ActionContract class
     */     
    protected class ActionContract extends ItemAction {     
    
        public ActionContract()
        {
            this(null);
        }

        public ActionContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierPerformEvalulationEditUI.this, "ActionContract", "actionContract_actionPerformed", e);
        }
    }

    /**
     * output ActionTemplet class
     */     
    protected class ActionTemplet extends ItemAction {     
    
        public ActionTemplet()
        {
            this(null);
        }

        public ActionTemplet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTemplet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTemplet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTemplet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierPerformEvalulationEditUI.this, "ActionTemplet", "actionTemplet_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierPerformEvalulationEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}