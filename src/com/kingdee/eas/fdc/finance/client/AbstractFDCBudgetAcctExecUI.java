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
public abstract class AbstractFDCBudgetAcctExecUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCBudgetAcctExecUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDPanel panel;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbYear;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbMonth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShowBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemHideBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeptQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewCost;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeptQuery;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowBlankRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHideBlankRow;
    protected ActionViewCost actionViewCost = null;
    protected ActionShowBlankRow actionShowBlankRow = null;
    protected ActionHideBlankRow actionHideBlankRow = null;
    protected ActionDeptQuery actionDeptQuery = null;
    /**
     * output class constructor
     */
    public AbstractFDCBudgetAcctExecUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractFDCBudgetAcctExecUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionViewCost
        this.actionViewCost = new ActionViewCost(this);
        getActionManager().registerAction("actionViewCost", actionViewCost);
         this.actionViewCost.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowBlankRow
        this.actionShowBlankRow = new ActionShowBlankRow(this);
        getActionManager().registerAction("actionShowBlankRow", actionShowBlankRow);
         this.actionShowBlankRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHideBlankRow
        this.actionHideBlankRow = new ActionHideBlankRow(this);
        getActionManager().registerAction("actionHideBlankRow", actionHideBlankRow);
         this.actionHideBlankRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeptQuery
        this.actionDeptQuery = new ActionDeptQuery(this);
        getActionManager().registerAction("actionDeptQuery", actionDeptQuery);
         this.actionDeptQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.lbYear = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lbMonth = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.menuItemShowBlankRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemHideBlankRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeptQuery = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewCost = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnDeptQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShowBlankRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHideBlankRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contProject.setName("contProject");
        this.panel.setName("panel");
        this.lbYear.setName("lbYear");
        this.lbMonth.setName("lbMonth");
        this.spMonth.setName("spMonth");
        this.spYear.setName("spYear");
        this.prmtProject.setName("prmtProject");
        this.menuItemShowBlankRow.setName("menuItemShowBlankRow");
        this.menuItemHideBlankRow.setName("menuItemHideBlankRow");
        this.menuItemDeptQuery.setName("menuItemDeptQuery");
        this.menuItemViewCost.setName("menuItemViewCost");
        this.btnDeptQuery.setName("btnDeptQuery");
        this.btnShowBlankRow.setName("btnShowBlankRow");
        this.btnHideBlankRow.setName("btnHideBlankRow");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"acctNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"acctName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dynCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"partB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conLatestAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"splitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lstAllCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lstAllPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lstAllNoPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lstAllPaidRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"waitCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"waitPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"toDateYearCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"toDateYearPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearActCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearActPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearBudgetCostBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yearBudgetPayBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"budgetCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"budgetPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shifu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"payDif\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"payDifRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"shifuDif\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"shifuDifRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"deptment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{dynCost}</t:Cell><t:Cell>$Resource{conNumber}</t:Cell><t:Cell>$Resource{conName}</t:Cell><t:Cell>$Resource{partB}</t:Cell><t:Cell>$Resource{conAmt}</t:Cell><t:Cell>$Resource{conLatestAmt}</t:Cell><t:Cell>$Resource{splitAmt}</t:Cell><t:Cell>$Resource{lstAllCost}</t:Cell><t:Cell>$Resource{lstAllPay}</t:Cell><t:Cell>$Resource{lstAllNoPaid}</t:Cell><t:Cell>$Resource{lstAllPaidRate}</t:Cell><t:Cell>$Resource{waitCost}</t:Cell><t:Cell>$Resource{waitPay}</t:Cell><t:Cell>$Resource{yearCost}</t:Cell><t:Cell>$Resource{yearPay}</t:Cell><t:Cell>$Resource{toDateYearCost}</t:Cell><t:Cell>$Resource{toDateYearPay}</t:Cell><t:Cell>$Resource{yearActCost}</t:Cell><t:Cell>$Resource{yearActPay}</t:Cell><t:Cell>$Resource{yearBudgetCostBalance}</t:Cell><t:Cell>$Resource{yearBudgetPayBalance}</t:Cell><t:Cell>$Resource{budgetCost}</t:Cell><t:Cell>$Resource{budgetPay}</t:Cell><t:Cell>$Resource{planCost}</t:Cell><t:Cell>$Resource{planPay}</t:Cell><t:Cell>$Resource{actCost}</t:Cell><t:Cell>$Resource{actPay}</t:Cell><t:Cell>$Resource{shifu}</t:Cell><t:Cell>$Resource{payDif}</t:Cell><t:Cell>$Resource{payDifRate}</t:Cell><t:Cell>$Resource{shifuDif}</t:Cell><t:Cell>$Resource{shifuDifRate}</t:Cell><t:Cell>$Resource{deptment}</t:Cell><t:Cell>$Resource{creator}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{acctNumber_Row2}</t:Cell><t:Cell>$Resource{acctName_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{dynCost_Row2}</t:Cell><t:Cell>$Resource{conNumber_Row2}</t:Cell><t:Cell>$Resource{conName_Row2}</t:Cell><t:Cell>$Resource{partB_Row2}</t:Cell><t:Cell>$Resource{conAmt_Row2}</t:Cell><t:Cell>$Resource{conLatestAmt_Row2}</t:Cell><t:Cell>$Resource{splitAmt_Row2}</t:Cell><t:Cell>$Resource{lstAllCost_Row2}</t:Cell><t:Cell>$Resource{lstAllPay_Row2}</t:Cell><t:Cell>$Resource{lstAllNoPaid_Row2}</t:Cell><t:Cell>$Resource{lstAllPaidRate_Row2}</t:Cell><t:Cell>$Resource{waitCost_Row2}</t:Cell><t:Cell>$Resource{waitPay_Row2}</t:Cell><t:Cell>$Resource{yearCost_Row2}</t:Cell><t:Cell>$Resource{yearPay_Row2}</t:Cell><t:Cell>$Resource{toDateYearCost_Row2}</t:Cell><t:Cell>$Resource{toDateYearPay_Row2}</t:Cell><t:Cell>$Resource{yearActCost_Row2}</t:Cell><t:Cell>$Resource{yearActPay_Row2}</t:Cell><t:Cell>$Resource{yearBudgetCostBalance_Row2}</t:Cell><t:Cell>$Resource{yearBudgetPayBalance_Row2}</t:Cell><t:Cell>$Resource{budgetCost_Row2}</t:Cell><t:Cell>$Resource{budgetPay_Row2}</t:Cell><t:Cell>$Resource{planCost_Row2}</t:Cell><t:Cell>$Resource{planPay_Row2}</t:Cell><t:Cell>$Resource{actCost_Row2}</t:Cell><t:Cell>$Resource{actPay_Row2}</t:Cell><t:Cell>$Resource{shifu_Row2}</t:Cell><t:Cell>$Resource{payDif_Row2}</t:Cell><t:Cell>$Resource{payDifRate_Row2}</t:Cell><t:Cell>$Resource{shifuDif_Row2}</t:Cell><t:Cell>$Resource{shifuDifRate_Row2}</t:Cell><t:Cell>$Resource{deptment_Row2}</t:Cell><t:Cell>$Resource{creator_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row3}</t:Cell><t:Cell>$Resource{acctNumber_Row3}</t:Cell><t:Cell>$Resource{acctName_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{dynCost_Row3}</t:Cell><t:Cell>$Resource{conNumber_Row3}</t:Cell><t:Cell>$Resource{conName_Row3}</t:Cell><t:Cell>$Resource{partB_Row3}</t:Cell><t:Cell>$Resource{conAmt_Row3}</t:Cell><t:Cell>$Resource{conLatestAmt_Row3}</t:Cell><t:Cell>$Resource{splitAmt_Row3}</t:Cell><t:Cell>$Resource{lstAllCost_Row3}</t:Cell><t:Cell>$Resource{lstAllPay_Row3}</t:Cell><t:Cell>$Resource{lstAllNoPaid_Row3}</t:Cell><t:Cell>$Resource{lstAllPaidRate_Row3}</t:Cell><t:Cell>$Resource{waitCost_Row3}</t:Cell><t:Cell>$Resource{waitPay_Row3}</t:Cell><t:Cell>$Resource{yearCost_Row3}</t:Cell><t:Cell>$Resource{yearPay_Row3}</t:Cell><t:Cell>$Resource{toDateYearCost_Row3}</t:Cell><t:Cell>$Resource{toDateYearPay_Row3}</t:Cell><t:Cell>$Resource{yearActCost_Row3}</t:Cell><t:Cell>$Resource{yearActPay_Row3}</t:Cell><t:Cell>$Resource{yearBudgetCostBalance_Row3}</t:Cell><t:Cell>$Resource{yearBudgetPayBalance_Row3}</t:Cell><t:Cell>$Resource{budgetCost_Row3}</t:Cell><t:Cell>$Resource{budgetPay_Row3}</t:Cell><t:Cell>$Resource{planCost_Row3}</t:Cell><t:Cell>$Resource{planPay_Row3}</t:Cell><t:Cell>$Resource{actCost_Row3}</t:Cell><t:Cell>$Resource{actPay_Row3}</t:Cell><t:Cell>$Resource{shifu_Row3}</t:Cell><t:Cell>$Resource{payDif_Row3}</t:Cell><t:Cell>$Resource{payDifRate_Row3}</t:Cell><t:Cell>$Resource{shifuDif_Row3}</t:Cell><t:Cell>$Resource{shifuDifRate_Row3}</t:Cell><t:Cell>$Resource{deptment_Row3}</t:Cell><t:Cell>$Resource{creator_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""});


        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);
        // panel
        // lbYear		
        this.lbYear.setText(resHelper.getString("lbYear.text"));
        // lbMonth		
        this.lbMonth.setText(resHelper.getString("lbMonth.text"));
        // spMonth		
        this.spMonth.setEnabled(false);
        // spYear		
        this.spYear.setEnabled(false);
        // prmtProject		
        this.prmtProject.setEnabled(false);
        // menuItemShowBlankRow
        this.menuItemShowBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShowBlankRow.setText(resHelper.getString("menuItemShowBlankRow.text"));		
        this.menuItemShowBlankRow.setMnemonic(83);
        // menuItemHideBlankRow
        this.menuItemHideBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHideBlankRow.setText(resHelper.getString("menuItemHideBlankRow.text"));		
        this.menuItemHideBlankRow.setMnemonic(72);
        // menuItemDeptQuery
        this.menuItemDeptQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeptQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeptQuery.setText(resHelper.getString("menuItemDeptQuery.text"));		
        this.menuItemDeptQuery.setMnemonic(68);
        // menuItemViewCost
        this.menuItemViewCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewCost.setText(resHelper.getString("menuItemViewCost.text"));		
        this.menuItemViewCost.setVisible(false);		
        this.menuItemViewCost.setEnabled(false);
        // btnDeptQuery
        this.btnDeptQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeptQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeptQuery.setText(resHelper.getString("btnDeptQuery.text"));
        // btnShowBlankRow
        this.btnShowBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShowBlankRow.setText(resHelper.getString("btnShowBlankRow.text"));
        // btnHideBlankRow
        this.btnHideBlankRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideBlankRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHideBlankRow.setText(resHelper.getString("btnHideBlankRow.text"));
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
        tblMain.setBounds(new Rectangle(11, 26, 994, 582));
        this.add(tblMain, new KDLayout.Constraints(11, 26, 994, 582, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(12, 7, 270, 19));
        this.add(contProject, new KDLayout.Constraints(12, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panel.setBounds(new Rectangle(395, 4, 368, 37));
        this.add(panel, new KDLayout.Constraints(395, 4, 368, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contProject
        contProject.setBoundEditor(prmtProject);
        //panel
        panel.setLayout(null);        lbYear.setBounds(new Rectangle(100, 7, 29, 19));
        panel.add(lbYear, null);
        lbMonth.setBounds(new Rectangle(200, 7, 27, 19));
        panel.add(lbMonth, null);
        spMonth.setBounds(new Rectangle(134, 6, 60, 19));
        panel.add(spMonth, null);
        spYear.setBounds(new Rectangle(10, 6, 84, 19));
        panel.add(spYear, null);

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
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemDeptQuery);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemShowBlankRow);
        menuBiz.add(menuItemHideBlankRow);
        menuBiz.add(menuItemViewCost);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnDeptQuery);
        this.toolBar.add(btnShowBlankRow);
        this.toolBar.add(btnHideBlankRow);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FDCBudgetAcctExecUIHandler";
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
     * output actionViewCost_actionPerformed method
     */
    public void actionViewCost_actionPerformed(ActionEvent e) throws Exception
    {
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
     * output actionDeptQuery_actionPerformed method
     */
    public void actionDeptQuery_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionDeptQuery(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeptQuery() {
    	return false;
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
            innerActionPerformed("eas", AbstractFDCBudgetAcctExecUI.this, "ActionViewCost", "actionViewCost_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractFDCBudgetAcctExecUI.this, "ActionShowBlankRow", "actionShowBlankRow_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCBudgetAcctExecUI.this, "ActionHideBlankRow", "actionHideBlankRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDeptQuery class
     */     
    protected class ActionDeptQuery extends ItemAction {     
    
        public ActionDeptQuery()
        {
            this(null);
        }

        public ActionDeptQuery(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeptQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeptQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeptQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBudgetAcctExecUI.this, "ActionDeptQuery", "actionDeptQuery_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCBudgetAcctExecUI");
    }




}