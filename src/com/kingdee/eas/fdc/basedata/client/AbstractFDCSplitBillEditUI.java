/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

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
public abstract class AbstractFDCSplitBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCSplitBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUnSplitAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSplitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostBillName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAcctSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplitProj;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplitBotUp;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplitProd;
    protected javax.swing.JToolBar.Separator separator4;
    protected javax.swing.JToolBar.Separator separator5;
    protected javax.swing.JToolBar.Separator separator6;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImpContrSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplitProj;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplitBotUp;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplitProd;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAcctSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpContrSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewCostInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewCostInfo;
    protected com.kingdee.eas.fdc.basedata.FDCSplitBillInfo editData = null;
    protected ActionAcctSelect actionAcctSelect = null;
    protected ActionSplitProj actionSplitProj = null;
    protected ActionSplitBotUp actionSplitBotUp = null;
    protected ActionSplitProd actionSplitProd = null;
    protected ActionImpContrSplit actionImpContrSplit = null;
    protected ActionViewCostInfo actionViewCostInfo = null;
    /**
     * output class constructor
     */
    public AbstractFDCSplitBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCSplitBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAcctSelect
        this.actionAcctSelect = new ActionAcctSelect(this);
        getActionManager().registerAction("actionAcctSelect", actionAcctSelect);
         this.actionAcctSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitProj
        this.actionSplitProj = new ActionSplitProj(this);
        getActionManager().registerAction("actionSplitProj", actionSplitProj);
         this.actionSplitProj.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitBotUp
        this.actionSplitBotUp = new ActionSplitBotUp(this);
        getActionManager().registerAction("actionSplitBotUp", actionSplitBotUp);
         this.actionSplitBotUp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitProd
        this.actionSplitProd = new ActionSplitProd(this);
        getActionManager().registerAction("actionSplitProd", actionSplitProd);
         this.actionSplitProd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImpContrSplit
        this.actionImpContrSplit = new ActionImpContrSplit(this);
        getActionManager().registerAction("actionImpContrSplit", actionImpContrSplit);
         this.actionImpContrSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewCostInfo
        this.actionViewCostInfo = new ActionViewCostInfo(this);
        getActionManager().registerAction("actionViewCostInfo", actionViewCostInfo);
         this.actionViewCostInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCostBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtUnSplitAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSplitedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCostBillName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAcctSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplitProj = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplitBotUp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplitProd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator4 = new javax.swing.JToolBar.Separator();
        this.separator5 = new javax.swing.JToolBar.Separator();
        this.separator6 = new javax.swing.JToolBar.Separator();
        this.btnImpContrSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSplitProj = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplitBotUp = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplitProd = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAcctSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImpContrSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewCostInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewCostInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.dateAuditTime.setName("dateAuditTime");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtCostBillNumber.setName("txtCostBillNumber");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.txtAmount.setName("txtAmount");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.txtUnSplitAmount.setName("txtUnSplitAmount");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.txtSplitedAmount.setName("txtSplitedAmount");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.txtCostBillName.setName("txtCostBillName");
        this.kdtEntrys.setName("kdtEntrys");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.bizPromptLastUpdateUser.setName("bizPromptLastUpdateUser");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.txtDescription.setName("txtDescription");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.dateCreateTime.setName("dateCreateTime");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.dateLastUpdateTime.setName("dateLastUpdateTime");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.txtCompany.setName("txtCompany");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.txtNumber.setName("txtNumber");
        this.btnAcctSelect.setName("btnAcctSelect");
        this.btnSplitProj.setName("btnSplitProj");
        this.btnSplitBotUp.setName("btnSplitBotUp");
        this.btnSplitProd.setName("btnSplitProd");
        this.separator4.setName("separator4");
        this.separator5.setName("separator5");
        this.separator6.setName("separator6");
        this.btnImpContrSplit.setName("btnImpContrSplit");
        this.menuItemSplitProj.setName("menuItemSplitProj");
        this.menuItemSplitBotUp.setName("menuItemSplitBotUp");
        this.menuItemSplitProd.setName("menuItemSplitProd");
        this.menuItemAcctSelect.setName("menuItemAcctSelect");
        this.menuItemImpContrSplit.setName("menuItemImpContrSplit");
        this.btnViewCostInfo.setName("btnViewCostInfo");
        this.menuItemViewCostInfo.setName("menuItemViewCostInfo");
        // CoreUI		
        this.kDSeparator1.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemSubmit.setEnabled(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuView.setEnabled(false);		
        this.menuView.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemEdit.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.separator1.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuItemAddLine.setEnabled(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setEnabled(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.menuWorkflow.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // dateAuditTime		
        this.dateAuditTime.setEnabled(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);
        // txtCostBillNumber		
        this.txtCostBillNumber.setMaxLength(80);		
        this.txtCostBillNumber.setVisible(true);		
        this.txtCostBillNumber.setEnabled(false);		
        this.txtCostBillNumber.setHorizontalAlignment(2);		
        this.txtCostBillNumber.setRequired(false);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);
        // txtAmount		
        this.txtAmount.setVisible(true);		
        this.txtAmount.setHorizontalAlignment(2);		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setSupportedEmpty(true);		
        this.txtAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtAmount.setPrecision(2);		
        this.txtAmount.setRequired(true);		
        this.txtAmount.setEnabled(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);
        // txtUnSplitAmount		
        this.txtUnSplitAmount.setVisible(true);		
        this.txtUnSplitAmount.setHorizontalAlignment(2);		
        this.txtUnSplitAmount.setDataType(1);		
        this.txtUnSplitAmount.setSupportedEmpty(true);		
        this.txtUnSplitAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtUnSplitAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtUnSplitAmount.setPrecision(2);		
        this.txtUnSplitAmount.setRequired(true);		
        this.txtUnSplitAmount.setEnabled(false);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);
        // txtSplitedAmount		
        this.txtSplitedAmount.setVisible(true);		
        this.txtSplitedAmount.setHorizontalAlignment(2);		
        this.txtSplitedAmount.setDataType(1);		
        this.txtSplitedAmount.setSupportedEmpty(true);		
        this.txtSplitedAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtSplitedAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtSplitedAmount.setPrecision(2);		
        this.txtSplitedAmount.setRequired(true);		
        this.txtSplitedAmount.setEnabled(false);
        this.txtSplitedAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtSplitedAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setVisible(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);
        // txtCostBillName		
        this.txtCostBillName.setVisible(true);		
        this.txtCostBillName.setHorizontalAlignment(2);		
        this.txtCostBillName.setMaxLength(100);		
        this.txtCostBillName.setRequired(false);		
        this.txtCostBillName.setEnabled(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount.curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"costAccount.curProject.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"costAccount.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"costAccount.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"standard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"workLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"splitScale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"product\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"costAccount.curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"costAccount.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"splitType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"apportionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"apportionValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"directAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"apportionValueTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"directAmountTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"otherRatioTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{costAccount.curProject.number}</t:Cell><t:Cell>$Resource{costAccount.curProject.name}</t:Cell><t:Cell>$Resource{costAccount.number}</t:Cell><t:Cell>$Resource{costAccount.name}</t:Cell><t:Cell>$Resource{standard}</t:Cell><t:Cell>$Resource{workLoad}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{splitScale}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{product}</t:Cell><t:Cell>$Resource{costAccount.curProject.id}</t:Cell><t:Cell>$Resource{costAccount.id}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{splitType}</t:Cell><t:Cell>$Resource{apportionType.name}</t:Cell><t:Cell>$Resource{apportionValue}</t:Cell><t:Cell>$Resource{directAmount}</t:Cell><t:Cell>$Resource{apportionValueTotal}</t:Cell><t:Cell>$Resource{directAmountTotal}</t:Cell><t:Cell>$Resource{otherRatioTotal}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtEntrys_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
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
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setVisible(false);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelAlignment(7);
        // bizPromptLastUpdateUser		
        this.bizPromptLastUpdateUser.setEnabled(false);		
        this.bizPromptLastUpdateUser.setVisible(false);		
        this.bizPromptLastUpdateUser.setEditable(true);		
        this.bizPromptLastUpdateUser.setDisplayFormat("$name$");		
        this.bizPromptLastUpdateUser.setEditFormat("$number$");		
        this.bizPromptLastUpdateUser.setCommitFormat("$number$");
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelAlignment(7);
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.setVisible(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setHorizontalAlignment(2);		
        this.txtDescription.setText(resHelper.getString("txtDescription.text"));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setVisible(true);		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setCommitFormat("$number$");
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setVisible(false);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setEnabled(false);		
        this.kDLabelContainer12.setBoundLabelAlignment(7);
        // dateLastUpdateTime		
        this.dateLastUpdateTime.setEnabled(false);		
        this.dateLastUpdateTime.setVisible(false);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);		
        this.kDLabelContainer13.setBoundLabelAlignment(7);		
        this.kDLabelContainer13.setVisible(false);
        // txtCompany		
        this.txtCompany.setText(resHelper.getString("txtCompany.text"));		
        this.txtCompany.setEnabled(false);		
        this.txtCompany.setVisible(true);		
        this.txtCompany.setHorizontalAlignment(2);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);		
        this.kDLabelContainer14.setBoundLabelAlignment(7);		
        this.kDLabelContainer14.setVisible(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setText(resHelper.getString("txtNumber.text"));
        // btnAcctSelect
        this.btnAcctSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAcctSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAcctSelect.setText(resHelper.getString("btnAcctSelect.text"));		
        this.btnAcctSelect.setToolTipText(resHelper.getString("btnAcctSelect.toolTipText"));
        // btnSplitProj
        this.btnSplitProj.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitProj, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplitProj.setText(resHelper.getString("btnSplitProj.text"));		
        this.btnSplitProj.setToolTipText(resHelper.getString("btnSplitProj.toolTipText"));
        // btnSplitBotUp
        this.btnSplitBotUp.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitBotUp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplitBotUp.setText(resHelper.getString("btnSplitBotUp.text"));		
        this.btnSplitBotUp.setToolTipText(resHelper.getString("btnSplitBotUp.toolTipText"));
        // btnSplitProd
        this.btnSplitProd.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitProd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplitProd.setText(resHelper.getString("btnSplitProd.text"));		
        this.btnSplitProd.setToolTipText(resHelper.getString("btnSplitProd.toolTipText"));
        // separator4		
        this.separator4.setOrientation(1);
        // separator5		
        this.separator5.setOrientation(1);
        // separator6		
        this.separator6.setOrientation(1);
        // btnImpContrSplit
        this.btnImpContrSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpContrSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImpContrSplit.setText(resHelper.getString("btnImpContrSplit.text"));		
        this.btnImpContrSplit.setToolTipText(resHelper.getString("btnImpContrSplit.toolTipText"));		
        this.btnImpContrSplit.setVisible(false);
        // menuItemSplitProj
        this.menuItemSplitProj.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitProj, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplitProj.setText(resHelper.getString("menuItemSplitProj.text"));		
        this.menuItemSplitProj.setToolTipText(resHelper.getString("menuItemSplitProj.toolTipText"));
        // menuItemSplitBotUp
        this.menuItemSplitBotUp.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitBotUp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplitBotUp.setText(resHelper.getString("menuItemSplitBotUp.text"));
        // menuItemSplitProd
        this.menuItemSplitProd.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitProd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplitProd.setText(resHelper.getString("menuItemSplitProd.text"));
        // menuItemAcctSelect
        this.menuItemAcctSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAcctSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAcctSelect.setText(resHelper.getString("menuItemAcctSelect.text"));		
        this.menuItemAcctSelect.setToolTipText(resHelper.getString("menuItemAcctSelect.toolTipText"));
        // menuItemImpContrSplit
        this.menuItemImpContrSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpContrSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpContrSplit.setText(resHelper.getString("menuItemImpContrSplit.text"));		
        this.menuItemImpContrSplit.setToolTipText(resHelper.getString("menuItemImpContrSplit.toolTipText"));
        // btnViewCostInfo
        this.btnViewCostInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewCostInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewCostInfo.setText(resHelper.getString("btnViewCostInfo.text"));		
        this.btnViewCostInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // menuItemViewCostInfo		
        this.menuItemViewCostInfo.setText(resHelper.getString("menuItemViewCostInfo.text"));		
        this.menuItemViewCostInfo.setMnemonic(89);		
        this.menuItemViewCostInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_analyze"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(733, 571, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(733, 571, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(10, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(733, 32, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(733, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(371, 32, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(371, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(371, 10, 632, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(371, 10, 632, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(10, 59, 993, 482));
        this.add(kdtEntrys, new KDLayout.Constraints(10, 59, 993, 482, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(729, 527, 225, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(729, 527, 225, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(10, 549, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(723, 490, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(723, 490, 270, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(10, 571, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(10, 571, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(733, 549, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(733, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(446, 477, 224, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(446, 477, 224, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(372, 551, 270, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(372, 551, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(10, 490, 270, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(10, 490, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(dateAuditTime);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCostBillNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtAmount);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtUnSplitAmount);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtSplitedAmount);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtCostBillName);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptLastUpdateUser);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptCreator);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtDescription);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(dateCreateTime);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(dateLastUpdateTime);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtCompany);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtNumber);

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
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuItemAcctSelect);
        menuEdit.add(menuItemSplitProj);
        menuEdit.add(menuItemSplitBotUp);
        menuEdit.add(menuItemSplitProd);
        menuEdit.add(menuItemImpContrSplit);
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
        menuBiz.add(menuItemViewCostInfo);
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
        this.toolBar.add(btnAcctSelect);
        this.toolBar.add(separator4);
        this.toolBar.add(btnSplitProj);
        this.toolBar.add(btnSplitBotUp);
        this.toolBar.add(btnSplitProd);
        this.toolBar.add(separator5);
        this.toolBar.add(btnImpContrSplit);
        this.toolBar.add(separator6);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
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
        this.toolBar.add(btnViewCostInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.FDCSplitBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basedata.FDCSplitBillInfo)ov;
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
        }
    }

    /**
     * output txtSplitedAmount_dataChanged method
     */
    protected void txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editValueChanged method
     */
    protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_activeCellChanged method
     */
    protected void kdtEntrys_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
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
     * output actionAcctSelect_actionPerformed method
     */
    public void actionAcctSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitProj_actionPerformed method
     */
    public void actionSplitProj_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitBotUp_actionPerformed method
     */
    public void actionSplitBotUp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitProd_actionPerformed method
     */
    public void actionSplitProd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImpContrSplit_actionPerformed method
     */
    public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewCostInfo_actionPerformed method
     */
    public void actionViewCostInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAcctSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAcctSelect() {
    	return false;
    }
	public RequestContext prepareActionSplitProj(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplitProj() {
    	return false;
    }
	public RequestContext prepareActionSplitBotUp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplitBotUp() {
    	return false;
    }
	public RequestContext prepareActionSplitProd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplitProd() {
    	return false;
    }
	public RequestContext prepareActionImpContrSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImpContrSplit() {
    	return false;
    }
	public RequestContext prepareActionViewCostInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewCostInfo() {
    	return false;
    }

    /**
     * output ActionAcctSelect class
     */     
    protected class ActionAcctSelect extends ItemAction {     
    
        public ActionAcctSelect()
        {
            this(null);
        }

        public ActionAcctSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAcctSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAcctSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAcctSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillEditUI.this, "ActionAcctSelect", "actionAcctSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionSplitProj class
     */     
    protected class ActionSplitProj extends ItemAction {     
    
        public ActionSplitProj()
        {
            this(null);
        }

        public ActionSplitProj(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSplitProj.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitProj.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitProj.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillEditUI.this, "ActionSplitProj", "actionSplitProj_actionPerformed", e);
        }
    }

    /**
     * output ActionSplitBotUp class
     */     
    protected class ActionSplitBotUp extends ItemAction {     
    
        public ActionSplitBotUp()
        {
            this(null);
        }

        public ActionSplitBotUp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSplitBotUp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitBotUp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitBotUp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillEditUI.this, "ActionSplitBotUp", "actionSplitBotUp_actionPerformed", e);
        }
    }

    /**
     * output ActionSplitProd class
     */     
    protected class ActionSplitProd extends ItemAction {     
    
        public ActionSplitProd()
        {
            this(null);
        }

        public ActionSplitProd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSplitProd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitProd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitProd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillEditUI.this, "ActionSplitProd", "actionSplitProd_actionPerformed", e);
        }
    }

    /**
     * output ActionImpContrSplit class
     */     
    protected class ActionImpContrSplit extends ItemAction {     
    
        public ActionImpContrSplit()
        {
            this(null);
        }

        public ActionImpContrSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImpContrSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpContrSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpContrSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillEditUI.this, "ActionImpContrSplit", "actionImpContrSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewCostInfo class
     */     
    protected class ActionViewCostInfo extends ItemAction {     
    
        public ActionViewCostInfo()
        {
            this(null);
        }

        public ActionViewCostInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Y"));
            _tempStr = resHelper.getString("ActionViewCostInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCostInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCostInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillEditUI.this, "ActionViewCostInfo", "actionViewCostInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "FDCSplitBillEditUI");
    }




}