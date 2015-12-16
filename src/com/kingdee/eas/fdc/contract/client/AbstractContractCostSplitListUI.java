/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractCostSplitListUI extends com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractCostSplitListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewInvalid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMeasureSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMeasureSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClearSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewInvalid;
    protected ActionClearSplit actionClearSplit = null;
    protected ActionViewInvalid actionViewInvalid = null;
    protected ActionMeasureSplit actionMeasureSplit = null;
    protected ActionViewContract actionViewContract = null;
    /**
     * output class constructor
     */
    public AbstractContractCostSplitListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractCostSplitListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractCostSplitQuery");
        //actionClearSplit
        this.actionClearSplit = new ActionClearSplit(this);
        getActionManager().registerAction("actionClearSplit", actionClearSplit);
         this.actionClearSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewInvalid
        this.actionViewInvalid = new ActionViewInvalid(this);
        getActionManager().registerAction("actionViewInvalid", actionViewInvalid);
         this.actionViewInvalid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMeasureSplit
        this.actionMeasureSplit = new ActionMeasureSplit(this);
        getActionManager().registerAction("actionMeasureSplit", actionMeasureSplit);
         this.actionMeasureSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnClearSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewInvalid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMeasureSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemMeasureSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClearSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewInvalid = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnClearSplit.setName("btnClearSplit");
        this.btnViewInvalid.setName("btnViewInvalid");
        this.btnMeasureSplit.setName("btnMeasureSplit");
        this.btnViewContract.setName("btnViewContract");
        this.menuItemMeasureSplit.setName("menuItemMeasureSplit");
        this.menuItemClearSplit.setName("menuItemClearSplit");
        this.menuItemViewInvalid.setName("menuItemViewInvalid");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"costSplit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"isCostSplit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costSplit.splitState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"contractType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"partB.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"contractSource\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"landDeveloper.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"partC.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"costProperty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"hasSettled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"settleAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"contractPropert\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"costSplit.state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"isConfirm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"splitTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{costSplit.id}</t:Cell><t:Cell>$Resource{isCostSplit}</t:Cell><t:Cell>$Resource{costSplit.splitState}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{contractType.name}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{partB.name}</t:Cell><t:Cell>$Resource{contractSource}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{landDeveloper.name}</t:Cell><t:Cell>$Resource{partC.name}</t:Cell><t:Cell>$Resource{costProperty}</t:Cell><t:Cell>$Resource{hasSettled}</t:Cell><t:Cell>$Resource{settleAmt}</t:Cell><t:Cell>$Resource{contractPropert}</t:Cell><t:Cell>$Resource{costSplit.state}</t:Cell><t:Cell>$Resource{isConfirm}</t:Cell><t:Cell>$Resource{splitTime}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"costSplit.id","isCostSplit","costSplit.splitState","id","contractType.name","state","number","name","amount","bookedDate","auditTime","partB.name","contractSourceId.name","signDate","landDeveloper.name","partC.name","costProperty","hasSettled","settleAmt","contractPropert","costSplit.state","isConfirm","costSplit.createTime","curProject.id"});


        // btnClearSplit
        this.btnClearSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearSplit.setText(resHelper.getString("btnClearSplit.text"));		
        this.btnClearSplit.setToolTipText(resHelper.getString("btnClearSplit.toolTipText"));
        // btnViewInvalid
        this.btnViewInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewInvalid.setText(resHelper.getString("btnViewInvalid.text"));
        // btnMeasureSplit
        this.btnMeasureSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionMeasureSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMeasureSplit.setText(resHelper.getString("btnMeasureSplit.text"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));
        // menuItemMeasureSplit
        this.menuItemMeasureSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionMeasureSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMeasureSplit.setText(resHelper.getString("menuItemMeasureSplit.text"));		
        this.menuItemMeasureSplit.setMnemonic(77);
        // menuItemClearSplit
        this.menuItemClearSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClearSplit.setText(resHelper.getString("menuItemClearSplit.text"));		
        this.menuItemClearSplit.setToolTipText(resHelper.getString("menuItemClearSplit.toolTipText"));
        // menuItemViewInvalid
        this.menuItemViewInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewInvalid.setText(resHelper.getString("menuItemViewInvalid.text"));		
        this.menuItemViewInvalid.setToolTipText(resHelper.getString("menuItemViewInvalid.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(treeProject, "left");
        kDSplitPane1.add(mainPanel, "right");
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(tblMain, BorderLayout.CENTER);
        mainPanel.add(colorPanel, BorderLayout.SOUTH);
        colorPanel.setLayout(null);
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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemCostSplit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemMeasureSplit);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemClearSplit);
        menuBiz.add(menuItemViewInvalid);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnCostSplit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAddContent);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnClearSplit);
        this.toolBar.add(btnViewInvalid);
        this.toolBar.add(btnMeasureSplit);
        this.toolBar.add(btnViewContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractCostSplitListUIHandler";
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("contractType.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("costSplit.id"));
        sic.add(new SelectorItemInfo("costSplit.splitState"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("contractSourceId.name"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("landDeveloper.name"));
        sic.add(new SelectorItemInfo("partC.name"));
        sic.add(new SelectorItemInfo("costProperty"));
        sic.add(new SelectorItemInfo("contractPropert"));
        sic.add(new SelectorItemInfo("settleAmt"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("hasSettled"));
        sic.add(new SelectorItemInfo("costSplit.state"));
        sic.add(new SelectorItemInfo("isCostSplit"));
        sic.add(new SelectorItemInfo("isConfirm"));
        sic.add(new SelectorItemInfo("costSplit.createTime"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("auditTime"));
        return sic;
    }        
    	

    /**
     * output actionClearSplit_actionPerformed method
     */
    public void actionClearSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewInvalid_actionPerformed method
     */
    public void actionViewInvalid_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMeasureSplit_actionPerformed method
     */
    public void actionMeasureSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionClearSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearSplit() {
    	return false;
    }
	public RequestContext prepareActionViewInvalid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewInvalid() {
    	return false;
    }
	public RequestContext prepareActionMeasureSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMeasureSplit() {
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
     * output ActionClearSplit class
     */     
    protected class ActionClearSplit extends ItemAction {     
    
        public ActionClearSplit()
        {
            this(null);
        }

        public ActionClearSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
            _tempStr = resHelper.getString("ActionClearSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractCostSplitListUI.this, "ActionClearSplit", "actionClearSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewInvalid class
     */     
    protected class ActionViewInvalid extends ItemAction {     
    
        public ActionViewInvalid()
        {
            this(null);
        }

        public ActionViewInvalid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_lookup"));
            _tempStr = resHelper.getString("ActionViewInvalid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvalid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvalid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractCostSplitListUI.this, "ActionViewInvalid", "actionViewInvalid_actionPerformed", e);
        }
    }

    /**
     * output ActionMeasureSplit class
     */     
    protected class ActionMeasureSplit extends ItemAction {     
    
        public ActionMeasureSplit()
        {
            this(null);
        }

        public ActionMeasureSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMeasureSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMeasureSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMeasureSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractCostSplitListUI.this, "ActionMeasureSplit", "actionMeasureSplit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractCostSplitListUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractCostSplitListUI");
    }




}