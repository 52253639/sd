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
public abstract class AbstractFDCBudgetAcctEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCBudgetAcctEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVerNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShowBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemHideBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHideBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuery;
    protected com.kingdee.bos.ctrl.swing.KDPanel panel;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbYear;
    protected com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo editData = null;
    protected ActionShowBlankRow actionShowBlankRow = null;
    protected ActionHideBlankRow actionHideBlankRow = null;
    protected ActionQuery actionQuery = null;
    protected ActionViewCost actionViewCost = null;
    /**
     * output class constructor
     */
    public AbstractFDCBudgetAcctEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCBudgetAcctEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionShowBlankRow
        this.actionShowBlankRow = new ActionShowBlankRow(this);
        getActionManager().registerAction("actionShowBlankRow", actionShowBlankRow);
         this.actionShowBlankRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHideBlankRow
        this.actionHideBlankRow = new ActionHideBlankRow(this);
        getActionManager().registerAction("actionHideBlankRow", actionHideBlankRow);
         this.actionHideBlankRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuery
        this.actionQuery = new ActionQuery(this);
        getActionManager().registerAction("actionQuery", actionQuery);
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewCost
        this.actionViewCost = new ActionViewCost(this);
        getActionManager().registerAction("actionViewCost", actionViewCost);
         this.actionViewCost.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVerNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVerNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dpAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.menuItemShowBlankRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuery = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemHideBlankRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnShowBlankRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHideBlankRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.panel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lbMonth = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lbYear = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contNumber.setName("contNumber");
        this.contVerNumber.setName("contVerNumber");
        this.contProject.setName("contProject");
        this.tblMain.setName("tblMain");
        this.txtNumber.setName("txtNumber");
        this.txtVerNumber.setName("txtVerNumber");
        this.txtProject.setName("txtProject");
        this.contAuditDate.setName("contAuditDate");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.dpAuditDate.setName("dpAuditDate");
        this.menuItemShowBlankRow.setName("menuItemShowBlankRow");
        this.menuItemQuery.setName("menuItemQuery");
        this.menuItemHideBlankRow.setName("menuItemHideBlankRow");
        this.btnShowBlankRow.setName("btnShowBlankRow");
        this.btnHideBlankRow.setName("btnHideBlankRow");
        this.btnQuery.setName("btnQuery");
        this.panel.setName("panel");
        this.spYear.setName("spYear");
        this.spMonth.setName("spMonth");
        this.lbMonth.setName("lbMonth");
        this.lbYear.setName("lbYear");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelLength(100);
        // contVerNumber		
        this.contVerNumber.setBoundLabelText(resHelper.getString("contVerNumber.boundLabelText"));		
        this.contVerNumber.setBoundLabelUnderline(true);		
        this.contVerNumber.setBoundLabelLength(100);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setBoundLabelLength(100);
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"acctNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"acctName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dynCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"partB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conLatestAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"splitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"desc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deptment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{dynCost}</t:Cell><t:Cell>$Resource{conNumber}</t:Cell><t:Cell>$Resource{conName}</t:Cell><t:Cell>$Resource{partB}</t:Cell><t:Cell>$Resource{conAmt}</t:Cell><t:Cell>$Resource{conLatestAmt}</t:Cell><t:Cell>$Resource{splitAmt}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{deptment}</t:Cell><t:Cell>$Resource{creator}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{acctNumber_Row2}</t:Cell><t:Cell>$Resource{acctName_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{dynCost_Row2}</t:Cell><t:Cell>$Resource{conNumber_Row2}</t:Cell><t:Cell>$Resource{conName_Row2}</t:Cell><t:Cell>$Resource{partB_Row2}</t:Cell><t:Cell>$Resource{conAmt_Row2}</t:Cell><t:Cell>$Resource{conLatestAmt_Row2}</t:Cell><t:Cell>$Resource{splitAmt_Row2}</t:Cell><t:Cell>$Resource{desc_Row2}</t:Cell><t:Cell>$Resource{deptment_Row2}</t:Cell><t:Cell>$Resource{creator_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // txtNumber
        // txtVerNumber
        // txtProject
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelUnderline(true);		
        this.contAuditDate.setBoundLabelLength(100);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelLength(100);
        // prmtAuditor
        // dpAuditDate
        // menuItemShowBlankRow
        this.menuItemShowBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShowBlankRow.setText(resHelper.getString("menuItemShowBlankRow.text"));		
        this.menuItemShowBlankRow.setMnemonic(83);
        // menuItemQuery
        this.menuItemQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuery.setText(resHelper.getString("menuItemQuery.text"));		
        this.menuItemQuery.setMnemonic(81);
        // menuItemHideBlankRow
        this.menuItemHideBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHideBlankRow.setText(resHelper.getString("menuItemHideBlankRow.text"));		
        this.menuItemHideBlankRow.setMnemonic(72);
        // btnShowBlankRow
        this.btnShowBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShowBlankRow.setText(resHelper.getString("btnShowBlankRow.text"));
        // btnHideBlankRow
        this.btnHideBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHideBlankRow.setText(resHelper.getString("btnHideBlankRow.text"));
        // btnQuery
        this.btnQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuery.setText(resHelper.getString("btnQuery.text"));
        // panel
        // spYear
        // spMonth
        // lbMonth		
        this.lbMonth.setText(resHelper.getString("lbMonth.text"));
        // lbYear		
        this.lbYear.setText(resHelper.getString("lbYear.text"));
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
        contNumber.setBounds(new Rectangle(21, 38, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(21, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVerNumber.setBounds(new Rectangle(404, 38, 270, 19));
        this.add(contVerNumber, new KDLayout.Constraints(404, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(21, 10, 270, 19));
        this.add(contProject, new KDLayout.Constraints(21, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblMain.setBounds(new Rectangle(11, 70, 992, 508));
        this.add(tblMain, new KDLayout.Constraints(11, 70, 992, 508, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditDate.setBounds(new Rectangle(404, 592, 270, 19));
        this.add(contAuditDate, new KDLayout.Constraints(404, 592, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(21, 592, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(21, 592, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panel.setBounds(new Rectangle(404, 7, 270, 28));
        this.add(panel, new KDLayout.Constraints(404, 7, 270, 28, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contVerNumber
        contVerNumber.setBoundEditor(txtVerNumber);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contAuditDate
        contAuditDate.setBoundEditor(dpAuditDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //panel
        panel.setLayout(null);        spYear.setBounds(new Rectangle(1, 1, 71, 19));
        panel.add(spYear, null);
        spMonth.setBounds(new Rectangle(116, 1, 52, 19));
        panel.add(spMonth, null);
        lbMonth.setBounds(new Rectangle(173, 1, 24, 19));
        panel.add(lbMonth, null);
        lbYear.setBounds(new Rectangle(83, 1, 18, 19));
        panel.add(lbYear, null);

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
        menuView.add(menuItemQuery);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemShowBlankRow);
        menuBiz.add(menuItemHideBlankRow);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnShowBlankRow);
        this.toolBar.add(btnHideBlankRow);
        this.toolBar.add(btnQuery);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("verNumber", float.class, this.txtVerNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.dpAuditDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FDCBudgetAcctEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("verNumber"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        return sic;
    }        
    	

    /**
     * output actionShowBlankRow_actionPerformed method
     */
    public void actionShowBlankRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHideBlankRow_actionPerformed method
     */
    public void actionHideBlankRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuery_actionPerformed method
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewCost_actionPerformed method
     */
    public void actionViewCost_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionShowBlankRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowBlankRow() {
    	return false;
    }
	public RequestContext prepareActionHideBlankRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHideBlankRow() {
    	return false;
    }
	public RequestContext prepareActionQuery(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuery() {
    	return false;
    }
	public RequestContext prepareActionViewCost(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewCost() {
    	return false;
    }

    /**
     * output ActionShowBlankRow class
     */     
    protected class ActionShowBlankRow extends ItemAction {     
    
        public ActionShowBlankRow()
        {
            this(null);
        }

        public ActionShowBlankRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowBlankRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowBlankRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowBlankRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBudgetAcctEditUI.this, "ActionShowBlankRow", "actionShowBlankRow_actionPerformed", e);
        }
    }

    /**
     * output ActionHideBlankRow class
     */     
    protected class ActionHideBlankRow extends ItemAction {     
    
        public ActionHideBlankRow()
        {
            this(null);
        }

        public ActionHideBlankRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHideBlankRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHideBlankRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHideBlankRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBudgetAcctEditUI.this, "ActionHideBlankRow", "actionHideBlankRow_actionPerformed", e);
        }
    }

    /**
     * output ActionQuery class
     */     
    protected class ActionQuery extends ItemAction {     
    
        public ActionQuery()
        {
            this(null);
        }

        public ActionQuery(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBudgetAcctEditUI.this, "ActionQuery", "actionQuery_actionPerformed", e);
        }
    }

    /**
     * output ActionViewCost class
     */     
    protected class ActionViewCost extends ItemAction {     
    
        public ActionViewCost()
        {
            this(null);
        }

        public ActionViewCost(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewCost.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCost.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCost.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBudgetAcctEditUI.this, "ActionViewCost", "actionViewCost_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCBudgetAcctEditUI");
    }




}