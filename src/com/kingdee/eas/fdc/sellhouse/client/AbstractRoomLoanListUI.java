/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractRoomLoanListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomLoanListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchLoan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchReceive;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLoanProcess;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBankLoan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdateLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStopTransact;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalling;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchLoan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchReceive;
    protected ActionBatchLoan actionBatchLoan = null;
    protected ActionBatchReceiveBill actionBatchReceiveBill = null;
    protected ActionStopTransact actionStopTransact = null;
    protected ActionCalling actionCalling = null;
    protected ActionBankLoan actionBankLoan = null;
    protected ActionLoanProcess actionLoanProcess = null;
    protected ActionUpdateLoanAmount actionUpdateLoanAmount = null;
    /**
     * output class constructor
     */
    public AbstractRoomLoanListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomLoanListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "NewRoomLoanQuery");
        //actionBatchLoan
        this.actionBatchLoan = new ActionBatchLoan(this);
        getActionManager().registerAction("actionBatchLoan", actionBatchLoan);
         this.actionBatchLoan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchReceiveBill
        this.actionBatchReceiveBill = new ActionBatchReceiveBill(this);
        getActionManager().registerAction("actionBatchReceiveBill", actionBatchReceiveBill);
         this.actionBatchReceiveBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStopTransact
        this.actionStopTransact = new ActionStopTransact(this);
        getActionManager().registerAction("actionStopTransact", actionStopTransact);
         this.actionStopTransact.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCalling
        this.actionCalling = new ActionCalling(this);
        getActionManager().registerAction("actionCalling", actionCalling);
         this.actionCalling.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBankLoan
        this.actionBankLoan = new ActionBankLoan(this);
        getActionManager().registerAction("actionBankLoan", actionBankLoan);
         this.actionBankLoan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLoanProcess
        this.actionLoanProcess = new ActionLoanProcess(this);
        getActionManager().registerAction("actionLoanProcess", actionLoanProcess);
         this.actionLoanProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdateLoanAmount
        this.actionUpdateLoanAmount = new ActionUpdateLoanAmount(this);
        getActionManager().registerAction("actionUpdateLoanAmount", actionUpdateLoanAmount);
         this.actionUpdateLoanAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnBatchLoan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchReceive = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLoanProcess = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBankLoan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpdateLoanAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnStopTransact = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCalling = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatchLoan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBatchReceive = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnBatchLoan.setName("btnBatchLoan");
        this.btnBatchReceive.setName("btnBatchReceive");
        this.btnLoanProcess.setName("btnLoanProcess");
        this.btnBankLoan.setName("btnBankLoan");
        this.btnUpdateLoanAmount.setName("btnUpdateLoanAmount");
        this.btnStopTransact.setName("btnStopTransact");
        this.btnCalling.setName("btnCalling");
        this.menuItemBatchLoan.setName("menuItemBatchLoan");
        this.menuItemBatchReceive.setName("menuItemBatchReceive");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"customerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"phoneNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"loanNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"loanState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"curProcess\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"depositInDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"depositOutDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"promiseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"afmType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"afmAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"afm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"loanBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"loanYear\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"mmTypeId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"sellProjectId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"afmId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"createtime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customerName}</t:Cell><t:Cell>$Resource{phoneNumber}</t:Cell><t:Cell>$Resource{loanNumber}</t:Cell><t:Cell>$Resource{loanState}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{curProcess}</t:Cell><t:Cell>$Resource{depositInDate}</t:Cell><t:Cell>$Resource{depositOutDate}</t:Cell><t:Cell>$Resource{promiseDate}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{afmType}</t:Cell><t:Cell>$Resource{afmAmount}</t:Cell><t:Cell>$Resource{afm}</t:Cell><t:Cell>$Resource{loanBank}</t:Cell><t:Cell>$Resource{loanYear}</t:Cell><t:Cell>$Resource{roomId}</t:Cell><t:Cell>$Resource{mmTypeId}</t:Cell><t:Cell>$Resource{sellProjectId}</t:Cell><t:Cell>$Resource{afmId}</t:Cell><t:Cell>$Resource{createtime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","room.name","sign.customerNames","sign.customerPhone","number","aFMortgagedState","applicationDate","approach","depositInDate","depositOutDate","promiseDate","actualFinishDate","mmType.moneyType","actualLoanAmt","oRSOMortgaged.name","loanBank.name","loanFixedYear","room.id","mmType.id","sellProject.id","oRSOMortgaged.id","createTime"});

		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);
        // btnBatchLoan
        this.btnBatchLoan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchLoan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchLoan.setText(resHelper.getString("btnBatchLoan.text"));		
        this.btnBatchLoan.setToolTipText(resHelper.getString("btnBatchLoan.toolTipText"));
        // btnBatchReceive
        this.btnBatchReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchReceive.setText(resHelper.getString("btnBatchReceive.text"));		
        this.btnBatchReceive.setToolTipText(resHelper.getString("btnBatchReceive.toolTipText"));		
        this.btnBatchReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_gathering"));		
        this.btnBatchReceive.setEnabled(false);		
        this.btnBatchReceive.setVisible(false);
        // btnLoanProcess
        this.btnLoanProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionLoanProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLoanProcess.setText(resHelper.getString("btnLoanProcess.text"));
        // btnBankLoan
        this.btnBankLoan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBankLoan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBankLoan.setText(resHelper.getString("btnBankLoan.text"));
        // btnUpdateLoanAmount
        this.btnUpdateLoanAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateLoanAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateLoanAmount.setText(resHelper.getString("btnUpdateLoanAmount.text"));
        // btnStopTransact
        this.btnStopTransact.setAction((IItemAction)ActionProxyFactory.getProxy(actionStopTransact, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStopTransact.setText(resHelper.getString("btnStopTransact.text"));		
        this.btnStopTransact.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deletecredence"));
        // btnCalling
        this.btnCalling.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalling, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalling.setText(resHelper.getString("btnCalling.text"));		
        this.btnCalling.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_finitialize"));		
        this.btnCalling.setEnabled(false);		
        this.btnCalling.setVisible(false);
        // menuItemBatchLoan
        this.menuItemBatchLoan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchLoan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchLoan.setText(resHelper.getString("menuItemBatchLoan.text"));		
        this.menuItemBatchLoan.setMnemonic(76);		
        this.menuItemBatchLoan.setToolTipText(resHelper.getString("menuItemBatchLoan.toolTipText"));
        // menuItemBatchReceive
        this.menuItemBatchReceive.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchReceiveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchReceive.setText(resHelper.getString("menuItemBatchReceive.text"));		
        this.menuItemBatchReceive.setToolTipText(resHelper.getString("menuItemBatchReceive.toolTipText"));		
        this.menuItemBatchReceive.setMnemonic(82);		
        this.menuItemBatchReceive.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_gathering"));
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(10, 10, 999, 583));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 999, 583, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

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
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(menuItemBatchLoan);
        menuEdit.add(menuItemBatchReceive);
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
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnBatchLoan);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnBatchReceive);
        this.toolBar.add(btnLoanProcess);
        this.toolBar.add(btnBankLoan);
        this.toolBar.add(btnUpdateLoanAmount);
        this.toolBar.add(btnStopTransact);
        this.toolBar.add(btnCalling);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomLoanListUIHandler";
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("aFMortgagedState"));
        sic.add(new SelectorItemInfo("applicationDate"));
        sic.add(new SelectorItemInfo("approach"));
        sic.add(new SelectorItemInfo("promiseDate"));
        sic.add(new SelectorItemInfo("actualFinishDate"));
        sic.add(new SelectorItemInfo("mmType.moneyType"));
        sic.add(new SelectorItemInfo("actualLoanAmt"));
        sic.add(new SelectorItemInfo("oRSOMortgaged.name"));
        sic.add(new SelectorItemInfo("loanFixedYear"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("mmType.id"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("oRSOMortgaged.id"));
        sic.add(new SelectorItemInfo("room.name"));
        sic.add(new SelectorItemInfo("sign.customerNames"));
        sic.add(new SelectorItemInfo("sign.customerPhone"));
        sic.add(new SelectorItemInfo("loanBank.name"));
        sic.add(new SelectorItemInfo("depositInDate"));
        sic.add(new SelectorItemInfo("depositOutDate"));
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        
    	

    /**
     * output actionBatchLoan_actionPerformed method
     */
    public void actionBatchLoan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchReceiveBill_actionPerformed method
     */
    public void actionBatchReceiveBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionStopTransact_actionPerformed method
     */
    public void actionStopTransact_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCalling_actionPerformed method
     */
    public void actionCalling_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBankLoan_actionPerformed method
     */
    public void actionBankLoan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLoanProcess_actionPerformed method
     */
    public void actionLoanProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdateLoanAmount_actionPerformed method
     */
    public void actionUpdateLoanAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBatchLoan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchLoan() {
    	return false;
    }
	public RequestContext prepareActionBatchReceiveBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchReceiveBill() {
    	return false;
    }
	public RequestContext prepareActionStopTransact(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStopTransact() {
    	return false;
    }
	public RequestContext prepareActionCalling(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalling() {
    	return false;
    }
	public RequestContext prepareActionBankLoan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBankLoan() {
    	return false;
    }
	public RequestContext prepareActionLoanProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLoanProcess() {
    	return false;
    }
	public RequestContext prepareActionUpdateLoanAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateLoanAmount() {
    	return false;
    }

    /**
     * output ActionBatchLoan class
     */     
    protected class ActionBatchLoan extends ItemAction {     
    
        public ActionBatchLoan()
        {
            this(null);
        }

        public ActionBatchLoan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_editbatch"));
            _tempStr = resHelper.getString("ActionBatchLoan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchLoan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchLoan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionBatchLoan", "actionBatchLoan_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchReceiveBill class
     */     
    protected class ActionBatchReceiveBill extends ItemAction {     
    
        public ActionBatchReceiveBill()
        {
            this(null);
        }

        public ActionBatchReceiveBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgBill_icon_1"));
            _tempStr = resHelper.getString("ActionBatchReceiveBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchReceiveBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchReceiveBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionBatchReceiveBill", "actionBatchReceiveBill_actionPerformed", e);
        }
    }

    /**
     * output ActionStopTransact class
     */     
    protected class ActionStopTransact extends ItemAction {     
    
        public ActionStopTransact()
        {
            this(null);
        }

        public ActionStopTransact(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionStopTransact.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStopTransact.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStopTransact.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionStopTransact", "actionStopTransact_actionPerformed", e);
        }
    }

    /**
     * output ActionCalling class
     */     
    protected class ActionCalling extends ItemAction {     
    
        public ActionCalling()
        {
            this(null);
        }

        public ActionCalling(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCalling.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalling.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalling.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionCalling", "actionCalling_actionPerformed", e);
        }
    }

    /**
     * output ActionBankLoan class
     */     
    protected class ActionBankLoan extends ItemAction {     
    
        public ActionBankLoan()
        {
            this(null);
        }

        public ActionBankLoan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBankLoan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBankLoan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBankLoan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionBankLoan", "actionBankLoan_actionPerformed", e);
        }
    }

    /**
     * output ActionLoanProcess class
     */     
    protected class ActionLoanProcess extends ItemAction {     
    
        public ActionLoanProcess()
        {
            this(null);
        }

        public ActionLoanProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionLoanProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLoanProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLoanProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionLoanProcess", "actionLoanProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdateLoanAmount class
     */     
    protected class ActionUpdateLoanAmount extends ItemAction {     
    
        public ActionUpdateLoanAmount()
        {
            this(null);
        }

        public ActionUpdateLoanAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpdateLoanAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateLoanAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateLoanAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomLoanListUI.this, "ActionUpdateLoanAmount", "actionUpdateLoanAmount_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomLoanListUI");
    }




}