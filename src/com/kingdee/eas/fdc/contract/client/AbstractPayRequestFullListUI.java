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
public abstract class AbstractPayRequestFullListUI extends com.kingdee.eas.framework.client.CoreBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayRequestFullListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBg;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected ActionClose actionClose = null;
    protected ActionUnClose actionUnClose = null;
    protected ActionAudit actionAudit = null;
    protected ActionViewBg actionViewBg = null;
    /**
     * output class constructor
     */
    public AbstractPayRequestFullListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPayRequestFullListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "PayRequestBillQuery");
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnClose
        this.actionUnClose = new ActionUnClose(this);
        getActionManager().registerAction("actionUnClose", actionUnClose);
         this.actionUnClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBg
        this.actionViewBg = new ActionViewBg(this);
        getActionManager().registerAction("actionViewBg", actionViewBg);
         this.actionViewBg.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBg = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnClose.setName("btnClose");
        this.btnUnClose.setName("btnUnClose");
        this.btnAudit.setName("btnAudit");
        this.btnViewBg.setName("btnViewBg");
        this.menuItemClose.setName("menuItemClose");
        this.menuItemUnClose.setName("menuItemUnClose");
        this.menuItemAudit.setName("menuItemAudit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:NumberFormat>YYYY-MM-DD HH:mm:ss</c:NumberFormat></c:Style><c:Style id=\"sCol30\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"bookedDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"period\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"paymentType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"state\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"source\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"curProject.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"contractNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"contractName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"supplier.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"currency\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"srcOriginalAmount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"srcAmount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"deduct\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"guerden\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"originalAmount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"amount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"actPaiedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"actPaiedLocAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"payDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"settlementType.name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"recBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"recAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"attachment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"moneyDesc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"hasClosed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"currency.id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"currency.precision\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"hasAttachment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{paymentType.name}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{source}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{contractName}</t:Cell><t:Cell>$Resource{supplier.name}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{srcOriginalAmount}</t:Cell><t:Cell>$Resource{srcAmount}</t:Cell><t:Cell>$Resource{deduct}</t:Cell><t:Cell>$Resource{guerden}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{actPaiedAmount}</t:Cell><t:Cell>$Resource{actPaiedLocAmount}</t:Cell><t:Cell>$Resource{payDate}</t:Cell><t:Cell>$Resource{settlementType.name}</t:Cell><t:Cell>$Resource{recBank}</t:Cell><t:Cell>$Resource{recAccount}</t:Cell><t:Cell>$Resource{attachment}</t:Cell><t:Cell>$Resource{moneyDesc}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell><t:Cell>$Resource{hasClosed}</t:Cell><t:Cell>$Resource{currency.id}</t:Cell><t:Cell>$Resource{currency.precision}</t:Cell><t:Cell>$Resource{hasAttachment}</t:Cell><t:Cell>$Resource{contractId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","number","paymentType.name","state","source","curProject.name","contractNumber","contractName","supplier.name","currency.name","srcOriginalAmount","srcAmount","deduct","guerdon","originalAmount","amount","actPaiedAmount","actPaiedLocAmount","payDate","settlementType.name","recBank","recAccount","attachment","usage","description","creator.name","createTime","auditor.name","auditDate","hasClosed","currency.id","currency.precision","","contractId"});


        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));		
        this.btnClose.setToolTipText(resHelper.getString("btnClose.toolTipText"));
        // btnUnClose
        this.btnUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnClose.setText(resHelper.getString("btnUnClose.text"));		
        this.btnUnClose.setToolTipText(resHelper.getString("btnUnClose.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnViewBg
        this.btnViewBg.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBg.setText(resHelper.getString("btnViewBg.text"));
        // menuItemClose
        this.menuItemClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClose.setText(resHelper.getString("menuItemClose.text"));		
        this.menuItemClose.setToolTipText(resHelper.getString("menuItemClose.toolTipText"));
        // menuItemUnClose
        this.menuItemUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnClose.setText(resHelper.getString("menuItemUnClose.text"));		
        this.menuItemUnClose.setToolTipText(resHelper.getString("menuItemUnClose.toolTipText"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));		
        this.menuItemAudit.setMnemonic(65);
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
        tblMain.setBounds(new Rectangle(11, 9, 993, 609));
        this.add(tblMain, new KDLayout.Constraints(11, 9, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
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
        menuBiz.add(menuItemClose);
        menuBiz.add(menuItemUnClose);
        menuBiz.add(menuItemAudit);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnUnClose);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnViewBg);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.PayRequestFullListUIHandler";
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
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("curProject.name"));
        sic.add(new SelectorItemInfo("contractNumber"));
        sic.add(new SelectorItemInfo("contractName"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("supplier.name"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("payDate"));
        sic.add(new SelectorItemInfo("settlementType.name"));
        sic.add(new SelectorItemInfo("recBank"));
        sic.add(new SelectorItemInfo("recAccount"));
        sic.add(new SelectorItemInfo("attachment"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("hasClosed"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("period.number"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("usage"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("currency.id"));
        sic.add(new SelectorItemInfo("currency.precision"));
        sic.add(new SelectorItemInfo("source"));
        sic.add(new SelectorItemInfo("paymentType.name"));
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("actPaiedAmount"));
        sic.add(new SelectorItemInfo("actPaiedLocAmount"));
        sic.add(new SelectorItemInfo("srcOriginalAmount"));
        sic.add(new SelectorItemInfo("srcAmount"));
        sic.add(new SelectorItemInfo("deduct"));
        sic.add(new SelectorItemInfo("guerdon"));
        return sic;
    }        
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnClose_actionPerformed method
     */
    public void actionUnClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBg_actionPerformed method
     */
    public void actionViewBg_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }
	public RequestContext prepareActionUnClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnClose() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionViewBg(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBg() {
    	return false;
    }

    /**
     * output ActionClose class
     */     
    protected class ActionClose extends ItemAction {     
    
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestFullListUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output ActionUnClose class
     */     
    protected class ActionUnClose extends ItemAction {     
    
        public ActionUnClose()
        {
            this(null);
        }

        public ActionUnClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestFullListUI.this, "ActionUnClose", "actionUnClose_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestFullListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewBg class
     */     
    protected class ActionViewBg extends ItemAction {     
    
        public ActionViewBg()
        {
            this(null);
        }

        public ActionViewBg(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBg.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBg.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBg.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestFullListUI.this, "ActionViewBg", "actionViewBg_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "PayRequestFullListUI");
    }




}