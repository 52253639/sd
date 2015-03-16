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
public abstract class AbstractChequeListUI extends com.kingdee.eas.framework.client.CoreBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChequeListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBookIn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDistribute;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBookIn;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDistribute;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVC;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVC;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChequeType;
    protected ActionBookIn actionBookIn = null;
    protected ActionDistribute actionDistribute = null;
    protected ActionVC actionVC = null;
    protected ActionBlankOut actionBlankOut = null;
    /**
     * output class constructor
     */
    public AbstractChequeListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChequeListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "ChequeQuery");
        //actionBookIn
        this.actionBookIn = new ActionBookIn(this);
        getActionManager().registerAction("actionBookIn", actionBookIn);
         this.actionBookIn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDistribute
        this.actionDistribute = new ActionDistribute(this);
        getActionManager().registerAction("actionDistribute", actionDistribute);
         this.actionDistribute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVC
        this.actionVC = new ActionVC(this);
        getActionManager().registerAction("actionVC", actionVC);
         this.actionVC.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBlankOut
        this.actionBlankOut = new ActionBlankOut(this);
        getActionManager().registerAction("actionBlankOut", actionBlankOut);
         this.actionBlankOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnBookIn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDistribute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBookIn = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDistribute = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnVC = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBlankOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemVC = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBlankOut = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.comboChequeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnBookIn.setName("btnBookIn");
        this.btnDistribute.setName("btnDistribute");
        this.menuItemBookIn.setName("menuItemBookIn");
        this.menuItemDistribute.setName("menuItemDistribute");
        this.btnVC.setName("btnVC");
        this.btnBlankOut.setName("btnBlankOut");
        this.menuItemVC.setName("menuItemVC");
        this.menuItemBlankOut.setName("menuItemBlankOut");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.comboChequeType.setName("comboChequeType");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"chequeType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"payer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"payTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"resume\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"capitalization\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"writtenOffer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"writtenOffTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"keeper.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"keepOrgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"verifyStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"verifier.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"verifyOrgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"verifyTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"limitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{chequeType}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{payer}</t:Cell><t:Cell>$Resource{payTime}</t:Cell><t:Cell>$Resource{resume}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{capitalization}</t:Cell><t:Cell>$Resource{writtenOffer.name}</t:Cell><t:Cell>$Resource{writtenOffTime}</t:Cell><t:Cell>$Resource{keeper.name}</t:Cell><t:Cell>$Resource{keepOrgUnit.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{verifyStatus}</t:Cell><t:Cell>$Resource{verifier.name}</t:Cell><t:Cell>$Resource{verifyOrgUnit.name}</t:Cell><t:Cell>$Resource{verifyTime}</t:Cell><t:Cell>$Resource{limitAmount}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","chequeType","number","status","payer","payTime","resume","amount","capitalization","writtenOffer.name","writtenOffTime","keeper.name","keepOrgUnit.name","creator.name","createTime","verifyStatus","verifier.name","verifyOrgUnit.name","verifyTime","limitAmount","description"});


        // pnlMain		
        this.pnlMain.setDividerLocation(200);
        // treeView
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnBookIn
        this.btnBookIn.setAction((IItemAction)ActionProxyFactory.getProxy(actionBookIn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBookIn.setText(resHelper.getString("btnBookIn.text"));		
        this.btnBookIn.setToolTipText(resHelper.getString("btnBookIn.toolTipText"));		
        this.btnBookIn.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgIcon_helpnotebook"));
        // btnDistribute
        this.btnDistribute.setAction((IItemAction)ActionProxyFactory.getProxy(actionDistribute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDistribute.setText(resHelper.getString("btnDistribute.text"));		
        this.btnDistribute.setToolTipText(resHelper.getString("btnDistribute.toolTipText"));		
        this.btnDistribute.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgList_taskdistribute"));
        // menuItemBookIn
        this.menuItemBookIn.setAction((IItemAction)ActionProxyFactory.getProxy(actionBookIn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBookIn.setText(resHelper.getString("menuItemBookIn.text"));		
        this.menuItemBookIn.setToolTipText(resHelper.getString("menuItemBookIn.toolTipText"));		
        this.menuItemBookIn.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgIcon_helpnotebook"));
        // menuItemDistribute
        this.menuItemDistribute.setAction((IItemAction)ActionProxyFactory.getProxy(actionDistribute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDistribute.setText(resHelper.getString("menuItemDistribute.text"));		
        this.menuItemDistribute.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgList_taskdistribute"));		
        this.menuItemDistribute.setToolTipText(resHelper.getString("menuItemDistribute.toolTipText"));
        // btnVC
        this.btnVC.setAction((IItemAction)ActionProxyFactory.getProxy(actionVC, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVC.setText(resHelper.getString("btnVC.text"));		
        this.btnVC.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_verificationinside"));		
        this.btnVC.setToolTipText(resHelper.getString("btnVC.toolTipText"));
        // btnBlankOut
        this.btnBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBlankOut.setText(resHelper.getString("btnBlankOut.text"));		
        this.btnBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));		
        this.btnBlankOut.setToolTipText(resHelper.getString("btnBlankOut.toolTipText"));
        // menuItemVC
        this.menuItemVC.setAction((IItemAction)ActionProxyFactory.getProxy(actionVC, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemVC.setText(resHelper.getString("menuItemVC.text"));		
        this.menuItemVC.setToolTipText(resHelper.getString("menuItemVC.toolTipText"));		
        this.menuItemVC.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_verificationinside"));
        // menuItemBlankOut
        this.menuItemBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBlankOut.setText(resHelper.getString("menuItemBlankOut.text"));		
        this.menuItemBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));		
        this.menuItemBlankOut.setToolTipText(resHelper.getString("menuItemBlankOut.toolTipText"));
        // kDPanel1
        // kDPanel2		
        this.kDPanel2.setPreferredSize(new Dimension(100,30));
        // comboChequeType		
        this.comboChequeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum").toArray());		
        this.comboChequeType.setPreferredSize(new Dimension(0,0));		
        this.comboChequeType.setMinimumSize(new Dimension(0,0));
        this.comboChequeType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboChequeType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        pnlMain.setBounds(new Rectangle(10, 10, 979, 611));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 979, 611, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDPanel1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblMain, BorderLayout.CENTER);
        kDPanel1.add(kDPanel2, BorderLayout.NORTH);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 768, 610));        comboChequeType.setBounds(new Rectangle(5, 7, 150, 19));
        kDPanel2.add(comboChequeType, new KDLayout.Constraints(5, 7, 150, 19, 0));

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
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemBookIn);
        menuBiz.add(menuItemDistribute);
        menuBiz.add(menuItemVC);
        menuBiz.add(menuItemBlankOut);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnBookIn);
        this.toolBar.add(btnDistribute);
        this.toolBar.add(btnVC);
        this.toolBar.add(btnBlankOut);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChequeListUIHandler";
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output comboChequeType_itemStateChanged method
     */
    protected void comboChequeType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("payer"));
        sic.add(new SelectorItemInfo("payTime"));
        sic.add(new SelectorItemInfo("resume"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("capitalization"));
        sic.add(new SelectorItemInfo("writtenOffer.name"));
        sic.add(new SelectorItemInfo("writtenOffTime"));
        sic.add(new SelectorItemInfo("keeper.name"));
        sic.add(new SelectorItemInfo("keepOrgUnit.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("verifyStatus"));
        sic.add(new SelectorItemInfo("verifier.name"));
        sic.add(new SelectorItemInfo("verifyOrgUnit.name"));
        sic.add(new SelectorItemInfo("verifyTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("chequeType"));
        sic.add(new SelectorItemInfo("limitAmount"));
        return sic;
    }        
    	

    /**
     * output actionBookIn_actionPerformed method
     */
    public void actionBookIn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDistribute_actionPerformed method
     */
    public void actionDistribute_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVC_actionPerformed method
     */
    public void actionVC_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBlankOut_actionPerformed method
     */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBookIn(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBookIn() {
    	return false;
    }
	public RequestContext prepareActionDistribute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDistribute() {
    	return false;
    }
	public RequestContext prepareActionVC(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVC() {
    	return false;
    }
	public RequestContext prepareActionBlankOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBlankOut() {
    	return false;
    }

    /**
     * output ActionBookIn class
     */     
    protected class ActionBookIn extends ItemAction {     
    
        public ActionBookIn()
        {
            this(null);
        }

        public ActionBookIn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBookIn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBookIn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBookIn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChequeListUI.this, "ActionBookIn", "actionBookIn_actionPerformed", e);
        }
    }

    /**
     * output ActionDistribute class
     */     
    protected class ActionDistribute extends ItemAction {     
    
        public ActionDistribute()
        {
            this(null);
        }

        public ActionDistribute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDistribute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDistribute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDistribute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChequeListUI.this, "ActionDistribute", "actionDistribute_actionPerformed", e);
        }
    }

    /**
     * output ActionVC class
     */     
    protected class ActionVC extends ItemAction {     
    
        public ActionVC()
        {
            this(null);
        }

        public ActionVC(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionVC.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVC.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVC.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChequeListUI.this, "ActionVC", "actionVC_actionPerformed", e);
        }
    }

    /**
     * output ActionBlankOut class
     */     
    protected class ActionBlankOut extends ItemAction {     
    
        public ActionBlankOut()
        {
            this(null);
        }

        public ActionBlankOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBlankOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChequeListUI.this, "ActionBlankOut", "actionBlankOut_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChequeListUI");
    }




}