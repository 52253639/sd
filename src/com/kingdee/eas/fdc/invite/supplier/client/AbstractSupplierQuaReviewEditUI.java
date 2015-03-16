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
public abstract class AbstractSupplierQuaReviewEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierQuaReviewEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDContainer conTemplateBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDContainer conReviewResult;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtScore;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTemplateBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtReviewResult;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSupplier;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTemplet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSupplier;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTemplet;
    protected com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo editData = null;
    protected ActionSupplierInfo actionSupplierInfo = null;
    protected ActionTemplet actionTemplet = null;
    /**
     * output class constructor
     */
    public AbstractSupplierQuaReviewEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierQuaReviewEditUI.class.getName());
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
        //actionSupplierInfo
        this.actionSupplierInfo = new ActionSupplierInfo(this);
        getActionManager().registerAction("actionSupplierInfo", actionSupplierInfo);
         this.actionSupplierInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTemplet
        this.actionTemplet = new ActionTemplet(this);
        getActionManager().registerAction("actionTemplet", actionTemplet);
         this.actionTemplet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conTemplateBill = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conReviewResult = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSupplierName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSupplierType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtTemplateBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtReviewResult = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSupplier = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTemplet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSupplier = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTemplet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contBizDate.setName("contBizDate");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contScore.setName("contScore");
        this.contAddress.setName("contAddress");
        this.contPerson.setName("contPerson");
        this.contSupplierName.setName("contSupplierName");
        this.contSupplierType.setName("contSupplierType");
        this.conTemplateBill.setName("conTemplateBill");
        this.contAttachment.setName("contAttachment");
        this.conReviewResult.setName("conReviewResult");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkBizDate.setName("pkBizDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.txtScore.setName("txtScore");
        this.txtAddress.setName("txtAddress");
        this.txtPerson.setName("txtPerson");
        this.txtSupplierName.setName("txtSupplierName");
        this.prmtSupplierType.setName("prmtSupplierType");
        this.kdtTemplateBill.setName("kdtTemplateBill");
        this.kdtReviewResult.setName("kdtReviewResult");
        this.btnSupplier.setName("btnSupplier");
        this.btnTemplet.setName("btnTemplet");
        this.menuItemSupplier.setName("menuItemSupplier");
        this.menuItemTemplet.setName("menuItemTemplet");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.menuTool.setEnabled(false);		
        this.MenuService.setEnabled(false);		
        this.MenuService.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuView.setEnabled(false);		
        this.menuView.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.MenuItemVoucher.setEnabled(false);		
        this.menuItemDelVoucher.setEnabled(false);		
        this.menuItemCreateTo.setEnabled(false);		
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
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contScore		
        this.contScore.setBoundLabelText(resHelper.getString("contScore.boundLabelText"));		
        this.contScore.setBoundLabelLength(100);		
        this.contScore.setBoundLabelUnderline(true);
        // contAddress		
        this.contAddress.setBoundLabelText(resHelper.getString("contAddress.boundLabelText"));		
        this.contAddress.setBoundLabelLength(100);		
        this.contAddress.setBoundLabelUnderline(true);
        // contPerson		
        this.contPerson.setBoundLabelText(resHelper.getString("contPerson.boundLabelText"));		
        this.contPerson.setBoundLabelLength(100);		
        this.contPerson.setBoundLabelUnderline(true);
        // contSupplierName		
        this.contSupplierName.setBoundLabelText(resHelper.getString("contSupplierName.boundLabelText"));		
        this.contSupplierName.setBoundLabelLength(100);		
        this.contSupplierName.setBoundLabelUnderline(true);
        // contSupplierType		
        this.contSupplierType.setBoundLabelText(resHelper.getString("contSupplierType.boundLabelText"));		
        this.contSupplierType.setBoundLabelLength(100);		
        this.contSupplierType.setBoundLabelUnderline(true);
        // conTemplateBill		
        this.conTemplateBill.setTitle(resHelper.getString("conTemplateBill.title"));		
        this.conTemplateBill.setEnableActive(false);
        // contAttachment		
        this.contAttachment.setBoundLabelText(resHelper.getString("contAttachment.boundLabelText"));		
        this.contAttachment.setBoundLabelUnderline(true);		
        this.contAttachment.setBoundLabelLength(1000);
        // conReviewResult		
        this.conReviewResult.setTitle(resHelper.getString("conReviewResult.title"));		
        this.conReviewResult.setEnableActive(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // txtScore
        this.txtScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtScore_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtAddress		
        this.txtAddress.setMaxLength(80);		
        this.txtAddress.setRequired(true);
        // txtPerson		
        this.txtPerson.setMaxLength(80);		
        this.txtPerson.setRequired(true);
        // txtSupplierName
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
        // kdtTemplateBill
		String kdtTemplateBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"dimension\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"target\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"proportion\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standard\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"score\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"syndic\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"syndicDept\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"syndicTime\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"info\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{dimension}</t:Cell><t:Cell>$Resource{target}</t:Cell><t:Cell>$Resource{proportion}</t:Cell><t:Cell>$Resource{standard}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{syndic}</t:Cell><t:Cell>$Resource{syndicDept}</t:Cell><t:Cell>$Resource{syndicTime}</t:Cell><t:Cell>$Resource{info}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtTemplateBill.setFormatXml(resHelper.translateString("kdtTemplateBill",kdtTemplateBillStrXML));
        this.kdtTemplateBill.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtTemplateBill_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtTemplateBill.putBindContents("editData",new String[] {"templateEntry.guideType","templateEntry.splAuditIndex","templateEntry.weight","templateEntry.fullNum","score","auditPerson","auditDept","auditTime","templateEntry"});


        // kdtReviewResult
		String kdtReviewResultStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"primaryState\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"whetherReview\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"raction\" t:width=\"155\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"supplierGrade\" t:width=\"210\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{primaryState}</t:Cell><t:Cell>$Resource{whetherReview}</t:Cell><t:Cell>$Resource{raction}</t:Cell><t:Cell>$Resource{supplierGrade}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtReviewResult.setFormatXml(resHelper.translateString("kdtReviewResult",kdtReviewResultStrXML));
        this.kdtReviewResult.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtReviewResult_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtReviewResult.putBindContents("editData",new String[] {"supplierType","beforeState","isAudit","score","grade",""});


        // btnSupplier
        this.btnSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplierInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSupplier.setText(resHelper.getString("btnSupplier.text"));
        // btnTemplet
        this.btnTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTemplet.setText(resHelper.getString("btnTemplet.text"));
        // menuItemSupplier
        this.menuItemSupplier.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplierInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSupplier.setText(resHelper.getString("menuItemSupplier.text"));		
        this.menuItemSupplier.setToolTipText(resHelper.getString("menuItemSupplier.toolTipText"));
        // menuItemTemplet
        this.menuItemTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTemplet.setText(resHelper.getString("menuItemTemplet.text"));		
        this.menuItemTemplet.setToolTipText(resHelper.getString("menuItemTemplet.toolTipText"));
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
        contCreator.setBounds(new Rectangle(20, 548, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(20, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(20, 587, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(20, 587, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(20, 59, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(20, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(723, 548, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(723, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(723, 587, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(723, 587, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contScore.setBounds(new Rectangle(723, 20, 270, 19));
        this.add(contScore, new KDLayout.Constraints(723, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAddress.setBounds(new Rectangle(371, 59, 270, 19));
        this.add(contAddress, new KDLayout.Constraints(371, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPerson.setBounds(new Rectangle(723, 59, 270, 19));
        this.add(contPerson, new KDLayout.Constraints(723, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplierName.setBounds(new Rectangle(20, 20, 270, 19));
        this.add(contSupplierName, new KDLayout.Constraints(20, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierType.setBounds(new Rectangle(371, 20, 270, 19));
        this.add(contSupplierType, new KDLayout.Constraints(371, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conTemplateBill.setBounds(new Rectangle(20, 245, 972, 280));
        this.add(conTemplateBill, new KDLayout.Constraints(20, 245, 972, 280, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAttachment.setBounds(new Rectangle(20, 208, 971, 19));
        this.add(contAttachment, new KDLayout.Constraints(20, 208, 971, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conReviewResult.setBounds(new Rectangle(20, 92, 970, 93));
        this.add(conReviewResult, new KDLayout.Constraints(20, 92, 970, 93, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contScore
        contScore.setBoundEditor(txtScore);
        //contAddress
        contAddress.setBoundEditor(txtAddress);
        //contPerson
        contPerson.setBoundEditor(txtPerson);
        //contSupplierName
        contSupplierName.setBoundEditor(txtSupplierName);
        //contSupplierType
        contSupplierType.setBoundEditor(prmtSupplierType);
        //conTemplateBill
conTemplateBill.getContentPane().setLayout(new BorderLayout(0, 0));        conTemplateBill.getContentPane().add(kdtTemplateBill, BorderLayout.CENTER);
        //conReviewResult
conReviewResult.getContentPane().setLayout(new BorderLayout(0, 0));        conReviewResult.getContentPane().add(kdtReviewResult, BorderLayout.CENTER);

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
        this.toolBar.add(btnSupplier);
        this.toolBar.add(btnTemplet);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("businessDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("score", java.math.BigDecimal.class, this.txtScore, "value");
		dataBinder.registerBinding("address", String.class, this.txtAddress, "text");
		dataBinder.registerBinding("person", String.class, this.txtPerson, "text");
		dataBinder.registerBinding("supplier.name", String.class, this.txtSupplierName, "text");
		dataBinder.registerBinding("indexValue.score", java.math.BigDecimal.class, this.kdtTemplateBill, "score.text");
		dataBinder.registerBinding("indexValue.auditTime", java.util.Date.class, this.kdtTemplateBill, "syndicTime.text");
		dataBinder.registerBinding("indexValue.auditDept", String.class, this.kdtTemplateBill, "syndicDept.text");
		dataBinder.registerBinding("indexValue.auditPerson", String.class, this.kdtTemplateBill, "syndic.text");
		dataBinder.registerBinding("indexValue.templateEntry.fullNum", String.class, this.kdtTemplateBill, "standard.text");
		dataBinder.registerBinding("indexValue.templateEntry.guideType", String.class, this.kdtTemplateBill, "dimension.text");
		dataBinder.registerBinding("indexValue.templateEntry.weight", java.math.BigDecimal.class, this.kdtTemplateBill, "proportion.text");
		dataBinder.registerBinding("indexValue.templateEntry.splAuditIndex", com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo.class, this.kdtTemplateBill, "target.text");
		dataBinder.registerBinding("indexValue", com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo.class, this.kdtTemplateBill, "userObject");
		dataBinder.registerBinding("indexValue.templateEntry", com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo.class, this.kdtTemplateBill, "info.text");
		dataBinder.registerBinding("auditResult.beforeState", String.class, this.kdtReviewResult, "primaryState.text");
		dataBinder.registerBinding("auditResult.isAudit", boolean.class, this.kdtReviewResult, "whetherReview.text");
		dataBinder.registerBinding("auditResult.score", java.math.BigDecimal.class, this.kdtReviewResult, "raction.text");
		dataBinder.registerBinding("auditResult.grade", String.class, this.kdtReviewResult, "supplierGrade.text");
		dataBinder.registerBinding("auditResult", com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryInfo.class, this.kdtReviewResult, "userObject");
		dataBinder.registerBinding("auditResult.supplierType", com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo.class, this.kdtReviewResult, "type.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierQuaReviewEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo)ov;
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
		getValidateHelper().registerBindProperty("businessDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.auditDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.auditPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.templateEntry.fullNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.templateEntry.guideType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.templateEntry.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.templateEntry.splAuditIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("indexValue.templateEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditResult.beforeState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditResult.isAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditResult.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditResult.grade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditResult", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditResult.supplierType", ValidateHelper.ON_SAVE);    		
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
     * output txtScore_actionPerformed method
     */
    protected void txtScore_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output kdtTemplateBill_editStopped method
     */
    protected void kdtTemplateBill_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtReviewResult_editStopped method
     */
    protected void kdtReviewResult_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("businessDate"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("score"));
        sic.add(new SelectorItemInfo("address"));
        sic.add(new SelectorItemInfo("person"));
        sic.add(new SelectorItemInfo("supplier.name"));
    sic.add(new SelectorItemInfo("indexValue.score"));
    sic.add(new SelectorItemInfo("indexValue.auditTime"));
    sic.add(new SelectorItemInfo("indexValue.auditDept"));
    sic.add(new SelectorItemInfo("indexValue.auditPerson"));
    sic.add(new SelectorItemInfo("indexValue.templateEntry.fullNum"));
    sic.add(new SelectorItemInfo("indexValue.templateEntry.guideType"));
    sic.add(new SelectorItemInfo("indexValue.templateEntry.weight"));
        sic.add(new SelectorItemInfo("indexValue.templateEntry.splAuditIndex.*"));
//        sic.add(new SelectorItemInfo("indexValue.templateEntry.splAuditIndex.number"));
        sic.add(new SelectorItemInfo("indexValue.*"));
//        sic.add(new SelectorItemInfo("indexValue.number"));
        sic.add(new SelectorItemInfo("indexValue.templateEntry.*"));
//        sic.add(new SelectorItemInfo("indexValue.templateEntry.number"));
    sic.add(new SelectorItemInfo("auditResult.beforeState"));
    sic.add(new SelectorItemInfo("auditResult.isAudit"));
    sic.add(new SelectorItemInfo("auditResult.score"));
    sic.add(new SelectorItemInfo("auditResult.grade"));
        sic.add(new SelectorItemInfo("auditResult.*"));
//        sic.add(new SelectorItemInfo("auditResult.number"));
        sic.add(new SelectorItemInfo("auditResult.supplierType.*"));
//        sic.add(new SelectorItemInfo("auditResult.supplierType.number"));
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
     * output actionSupplierInfo_actionPerformed method
     */
    public void actionSupplierInfo_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionSupplierInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSupplierInfo() {
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
     * output ActionSupplierInfo class
     */     
    protected class ActionSupplierInfo extends ItemAction {     
    
        public ActionSupplierInfo()
        {
            this(null);
        }

        public ActionSupplierInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSupplierInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplierInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplierInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierQuaReviewEditUI.this, "ActionSupplierInfo", "actionSupplierInfo_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractSupplierQuaReviewEditUI.this, "ActionTemplet", "actionTemplet_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierQuaReviewEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}